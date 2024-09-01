package njala.st.pj.healthinsurance.controller;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import njala.st.pj.healthinsurance.Utils;
import njala.st.pj.healthinsurance.model.Customer;
import njala.st.pj.healthinsurance.model.CustomerClaim;
import njala.st.pj.healthinsurance.model.CustomerContribution;
import njala.st.pj.healthinsurance.model.CustomerPlans;
import njala.st.pj.healthinsurance.model.PremiumPlans;
import njala.st.pj.healthinsurance.model.User;
import njala.st.pj.healthinsurance.repository.CustomerClaimsRepository;
import njala.st.pj.healthinsurance.repository.CustomerContributionRepository;
import njala.st.pj.healthinsurance.repository.CustomerPlansRepository;
import njala.st.pj.healthinsurance.repository.CustomerRepository;
import njala.st.pj.healthinsurance.repository.PremiumPlansRepository;
import njala.st.pj.healthinsurance.repository.UserRepository;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


/**
 * This class handle all request for customer.
 * it includes request for creating customer, updating customer information, paying customer claims, etc.
 */

@Controller
@RequestMapping("/customers")
public class CustomerController {
    //Password Encoder used
    @Autowired
    private PasswordEncoder passwordEncoder;

    //Customer Repository
    @Autowired
    CustomerRepository custRep;

    //User Repository
    @Autowired
    UserRepository userRep;

    //Plans or policy repository
    @Autowired
    PremiumPlansRepository planRep;

    //Customer enrollment into plans repository
    @Autowired
    CustomerPlansRepository cplanRep;

    //Customer contribution repository
    @Autowired
    CustomerContributionRepository cConRep;

    //Customer claims repository
    @Autowired
    CustomerClaimsRepository cClaRep;

    @GetMapping("/claims")
    public String getClaimss(Model model){
        model.addAttribute("claims", cClaRep.findAll());
        return "/customers/claims";
    }

    @PostMapping("/payclaim")
    public String payClaim(@RequestParam(name = "claim") Long claimId, @RequestParam(name = "paidAmount") BigDecimal payAmount,Model model){
        CustomerClaim cc = cClaRep.findById(claimId).get();
        if(cc.getApprovedAmount() == null || cc.getApprovedAmount().equals(BigDecimal.ZERO)){
            model.addAttribute("error", "The Claim has not been approved");
            model.addAttribute("next", "/customers/claims");
            return "errors";
        }
        cc.setPaidAmount(payAmount);
        cc.setPayDate(Date.valueOf(LocalDate.now()));
        cClaRep.save(cc);
        
        return "redirect:/customers/claims";
    }

    @PostMapping("/approveclaim")
    public String approveClaim(@RequestParam(name = "claim") Long claimId, @RequestParam(name = "approvedAmount") BigDecimal approvedAmount){
        CustomerClaim cc = cClaRep.findById(claimId).get();
        cc.setApprovedAmount(approvedAmount);
        cc.setApprovedDate(Date.valueOf(LocalDate.now()));
        cClaRep.save(cc);
        
        return "redirect:/customers/claims";
    }

    @PostMapping("/claim")
    public String applyClaim(@RequestParam(name = "customer") Long customerId, @RequestParam(name = "plan") Long planId, @ModelAttribute CustomerClaim cc,Model model){
        Customer cus = custRep.findById(customerId).get();
        PremiumPlans plan = planRep.findById(planId).get();

        CustomerPlans cPlans = cplanRep.findByCustomerAndPlan(cus,plan).get();

        int contributionCounts = cConRep.countByCustomerAndPlan(cus, plan);

        if(contributionCounts < plan.getMinContributionDuration()){
            model.addAttribute("error", "Customer has not contributed enough to claim.");
            model.addAttribute("next", "/customers");
            return "errors";
        }

        Double totalContribution = cConRep.SumByCustomerAndPlan(cus, plan);
        Double totalClaims = cClaRep.SumByCustomerPlan(cPlans);

        Double maxClaims = Utils.CalculateCustomerClaim(totalContribution, totalClaims, plan);

        if(BigDecimal.valueOf(maxClaims).compareTo(cc.getClaimAmount()) == -1){
            model.addAttribute("error", "Customer has claim more recently hence need to contribute more to be able  to claim.");
            model.addAttribute("next", "/customers");
            return "errors";
        }

        cc.setCustomerPlans(cPlans);
        cc.setClaimDate(Date.valueOf(LocalDate.now()));

        cClaRep.save(cc);
        
        return "redirect:/customers/claims";
    }

    @GetMapping("/contributions")
    public String getContributions(Model model){
        model.addAttribute("contributions", cConRep.findAll());
        return "/customers/contributions";
    }

    @PostMapping("/contribute")
    public String payContribution(@RequestParam(name = "customer") Long customerId, @RequestParam(name = "plan") Long planId, @ModelAttribute CustomerContribution cc, Model model){
        Customer cus = custRep.findById(customerId).get();
        PremiumPlans plan = planRep.findById(planId).get();

        if(cc.getAmount() < plan.getMinContribution()){
            model.addAttribute("error", "Customer cannot contribute below the minimum for the plan.");
            model.addAttribute("next", "/customers/");
            return "errors";
        }

        cc.setCustomer(cus);
        cc.setPlan(plan);
        cConRep.save(cc);
        
        planRep.save(plan);
        custRep.save(cus);
        return "redirect: ";
    }

    @GetMapping("/")
    public String getCustomers(Model model) {
        model.addAttribute("customers", custRep.findAll());
        model.addAttribute("plans", planRep.findAll());
        return "customers/listcustomers";
    }
    @GetMapping("/add")
    public String addCustomer(Model model) {
        model.addAttribute("customer", new Customer());
        return "customers/customerform";
    }
    @PostMapping(value = "/save", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public String saveCustomer(@ModelAttribute User user,@ModelAttribute("customer") Customer entity, Model model) {
        if(user.getPassword() != null && user.getId() == 0){
            String hashedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(hashedPassword);
        }else if(user.getNewpassword() != null && user.getId() != 0){
            String hashedPassword = passwordEncoder.encode(user.getNewpassword());
            user.setPassword(hashedPassword);
        }
        User svUser = userRep.save(user);
        entity.setUserAccount(svUser);
        custRep.saveAndFlush(entity);
        
        return "redirect: ";
    }

    @PostMapping("/subscribe")
    public String SubscribeToPlan(@RequestParam(name = "customer") Long customerId, @RequestParam(name = "plan") Long[] planIds){
        Customer cus = custRep.findById(customerId).get();
        for (Long planId : planIds) {
            PremiumPlans plan = planRep.findById(planId).get();
            CustomerPlans cPlans = new CustomerPlans();
            cPlans.setCustomer(cus);
            cPlans.setPlan(plan);
            cplanRep.save(cPlans);
            cus.addPlan(cPlans);
            plan.addCustomer(cPlans);
            planRep.save(plan);
        }
        custRep.save(cus);
        return "redirect: ";
    }
    
    
    
}
