package njala.st.pj.healthinsurance.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import njala.st.pj.healthinsurance.model.Customer;
import njala.st.pj.healthinsurance.model.CustomerPlans;
import njala.st.pj.healthinsurance.model.PremiumPlans;
import njala.st.pj.healthinsurance.repository.CustomerPlansRepository;
import njala.st.pj.healthinsurance.repository.CustomerRepository;
import njala.st.pj.healthinsurance.repository.PremiumPlansRepository;


@Controller
@RequestMapping("/plans")
public class PremiumPlanController {
    @Autowired
    PremiumPlansRepository planRepo;

    @Autowired
    CustomerRepository custRep;
    @Autowired
    CustomerPlansRepository cplanRep;

    @GetMapping("/")
    public String getPlans(Model model) {
        model.addAttribute("plans", planRepo.findAll());
        model.addAttribute("customers", custRep.findAll());
        return "plans/index";
    }
    
    @PostMapping(value = "/save", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public String saveCustomer(@ModelAttribute PremiumPlans entity, Model model) {
        
        planRepo.saveAndFlush(entity);
        
        return "redirect: ";
    }
    @PostMapping("/subscribe")
    public String SubscribeToPlan(@RequestParam(name = "customer") Long[] customerIds, @RequestParam(name = "plan") Long planId){
        
        PremiumPlans plan = planRepo.findById(planId).get();
        for (Long customerId : customerIds) {
            CustomerPlans cPlans = new CustomerPlans();
            Customer cus = custRep.findById(customerId).get();
            cPlans.setCustomer(cus);
            cPlans.setPlan(plan);
            cplanRep.save(cPlans);
            cus.addPlan(cPlans);
            plan.addCustomer(cPlans);
            custRep.save(cus);
        }
        planRepo.save(plan);
        return "redirect: ";
    }
}
