package njala.st.pj.healthinsurance.controller;

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

import njala.st.pj.healthinsurance.model.Customer;
import njala.st.pj.healthinsurance.model.CustomerContribution;
import njala.st.pj.healthinsurance.model.CustomerPlans;
import njala.st.pj.healthinsurance.model.PremiumPlans;
import njala.st.pj.healthinsurance.model.User;
import njala.st.pj.healthinsurance.repository.CustomerContributionRepository;
import njala.st.pj.healthinsurance.repository.CustomerPlansRepository;
import njala.st.pj.healthinsurance.repository.CustomerRepository;
import njala.st.pj.healthinsurance.repository.PremiumPlansRepository;
import njala.st.pj.healthinsurance.repository.UserRepository;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;




@Controller
@RequestMapping("/customers")
public class CustomerController {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    CustomerRepository custRep;

    @Autowired
    UserRepository userRep;

    @Autowired
    PremiumPlansRepository planRep;

    @Autowired
    CustomerPlansRepository cplanRep;

    @Autowired
    CustomerContributionRepository cConRep;

    @GetMapping("/contributions")
    public String getContributions(Model model){
        model.addAttribute("contributions", cConRep.findAll());
        return "/customers/contributions";
    }

    @PostMapping("/contribute")
    public String payContribution(@RequestParam(name = "customer") Long customerId, @RequestParam(name = "plan") Long planId, @ModelAttribute CustomerContribution cc){
        Customer cus = custRep.findById(customerId).get();
        PremiumPlans plan = planRep.findById(planId).get();

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
