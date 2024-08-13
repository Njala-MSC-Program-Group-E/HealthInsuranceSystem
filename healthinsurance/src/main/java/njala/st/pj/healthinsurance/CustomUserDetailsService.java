package njala.st.pj.healthinsurance;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import njala.st.pj.healthinsurance.component.UserRepository;
import njala.st.pj.healthinsurance.model.User;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // User user = userRepository.findByUsername(username)
        //     .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        
        return new org.springframework.security.core.userdetails.User("admin", "admin", 
                Collections.singletonList(new SimpleGrantedAuthority("Admin")));
        // return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), 
        //         Collections.singletonList(new SimpleGrantedAuthority(user.getRole())));
    }
}

