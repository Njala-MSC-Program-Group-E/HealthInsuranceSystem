package njala.st.pj.healthinsurance.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import njala.st.pj.healthinsurance.StringToPermissionSetConverter;
import njala.st.pj.healthinsurance.component.CustomerRepository;
import njala.st.pj.healthinsurance.component.UserRepository;
import njala.st.pj.healthinsurance.model.Customer;
import njala.st.pj.healthinsurance.model.Permission;
import njala.st.pj.healthinsurance.model.User;

@Controller
@RequestMapping("/users")
public class UsersController {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRep;

    @GetMapping("/")
    public String getCustomers(Model model) {
        model.addAttribute("users", userRep.findAll());
        List<String> permissions = Arrays.stream(Permission.values()).map(Enum::name).collect(Collectors.toList());
        model.addAttribute("permissions", permissions);
        List<String> tee = new ArrayList<>();
        tee.add("My Test");
        model.addAttribute("tee", tee);
        // model.addAttribute("permissions", Permission.values());
        return "users/listusers";
    }
    @PostMapping(value = "/save", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public String saveCustomer(@RequestParam("permissions") String[] permissions,@ModelAttribute User entity, Model model) {
        if(entity.getPassword() != null && entity.getId() == 0){
            String hashedPassword = passwordEncoder.encode(entity.getPassword());
            entity.setPassword(hashedPassword);
        }else if(entity.getNewpassword() != null && entity.getId() != 0){
            String hashedPassword = passwordEncoder.encode(entity.getNewpassword());
            entity.setPassword(hashedPassword);
        }
        // Convert the permissions from String array to Set<Permission>
        Set<Permission> permissionSet = Arrays.stream(permissions)
                                              .map(Permission::valueOf)
                                              .collect(Collectors.toSet());
        entity.setPermissions(permissionSet);
        userRep.saveAndFlush(entity);
        
        return "redirect: ";
    }
}
