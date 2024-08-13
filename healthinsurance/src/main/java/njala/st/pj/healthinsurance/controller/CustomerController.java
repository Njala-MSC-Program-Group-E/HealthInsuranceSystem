package njala.st.pj.healthinsurance.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import njala.st.pj.healthinsurance.component.CustomerRepository;
import njala.st.pj.healthinsurance.model.Customer;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@Controller
@RequestMapping("/customers")
public class CustomerController {
    @Autowired
    CustomerRepository custRep;

    @GetMapping("/")
    public String getCustomers(Model model) {
        model.addAttribute("customers", custRep.findAll());
        return "customers/listcustomers";
    }
    @GetMapping("/add")
    public String addCustomer(Model model) {
        model.addAttribute("customer", new Customer());
        return "customers/customerform";
    }
    @PostMapping(value = "/save", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public String saveCustomer(@ModelAttribute("customer") Customer entity, Model model) {
        custRep.saveAndFlush(entity);
        
        return "redirect: ";
    }
    
    
}
