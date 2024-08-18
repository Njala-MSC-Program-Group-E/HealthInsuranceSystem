package njala.st.pj.healthinsurance.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import njala.st.pj.healthinsurance.component.CustomerRepository;
import njala.st.pj.healthinsurance.component.UserRepository;
import njala.st.pj.healthinsurance.model.Customer;
import njala.st.pj.healthinsurance.model.User;

import org.springframework.web.bind.annotation.PostMapping;



@Controller
@RequestMapping("/customers")
public class CustomerController {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    CustomerRepository custRep;

    @Autowired
    UserRepository userRep;

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
    
    
}
