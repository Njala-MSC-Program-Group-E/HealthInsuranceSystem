package njala.st.pj.healthinsurance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomAuthenticationProvider customAuthenticationProvider;

    // Method to configure AuthenticationManagerBuilder
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(customAuthenticationProvider);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/login", "/h2-console/**").permitAll()  // Allow access to the login page
                .requestMatchers("/app/login").permitAll()  // Allow access to the login page
                
                .anyRequest().authenticated()            // All other requests require authentication
            )
            .formLogin(form -> form
                .loginPage("/app/login")
                .defaultSuccessUrl("/app/home")                    // Custom login page
                .permitAll()
            )
            .logout(logout -> logout
                .permitAll()                            // Allow logout for everyone
            );
        //     .csrf().disable()  // Disables CSRF protection, be cautious with this in production
        // .authorizeRequests()
        //     .antMatchers("/login", "/error").permitAll()  // Permit access to the login and error pages
        //     .anyRequest().authenticated()
        // .and()
        // .formLogin()
        //     .loginPage("/login")  // Specify the custom login page
        //     .defaultSuccessUrl("/")  // Redirect after successful login
        //     .permitAll()
        // .and()
        // .logout()
        //     .logoutUrl("/logout")
        //     .logoutSuccessUrl("/login?logout")
        //     .permitAll();

        return http.build();
    }

//     @Override
// protected void configure(HttpSecurity http) throws Exception {
//     http
//         .csrf().disable()  // Disables CSRF protection, be cautious with this in production
//         .authorizeRequests()
//             .antMatchers("/login", "/error").permitAll()  // Permit access to the login and error pages
//             .anyRequest().authenticated()
//         .and()
//         .formLogin()
//             .loginPage("/login")  // Specify the custom login page
//             .defaultSuccessUrl("/")  // Redirect after successful login
//             .permitAll()
//         .and()
//         .logout()
//             .logoutUrl("/logout")
//             .logoutSuccessUrl("/login?logout")
//             .permitAll();
// }
}