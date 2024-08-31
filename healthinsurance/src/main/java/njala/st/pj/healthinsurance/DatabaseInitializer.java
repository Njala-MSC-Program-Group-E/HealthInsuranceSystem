package njala.st.pj.healthinsurance;

import java.util.HashSet;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import njala.st.pj.healthinsurance.model.Permission;
import njala.st.pj.healthinsurance.model.User;
import njala.st.pj.healthinsurance.repository.UserRepository;

@Configuration
public class DatabaseInitializer {

    @Bean
    public CommandLineRunner initializeDatabase(UserRepository userAccountRepository, BCryptPasswordEncoder passwordEncoder) {
        return args -> {
            
            if (!userAccountRepository.existsByUsername("admin")) {
                String encodedPassword = passwordEncoder.encode("Admin");
                User user = new User();
                user.setUsername("admin");
                user.setPassword(encodedPassword);
                Set<Permission> pp = new HashSet<>();
                pp.add(Permission.Admin);
                user.setPermissions(pp);

                userAccountRepository.save(user);
            }
        };
    }
}

