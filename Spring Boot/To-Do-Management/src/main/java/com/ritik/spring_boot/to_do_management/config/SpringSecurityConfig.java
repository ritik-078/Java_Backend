package com.ritik.spring_boot.to_do_management.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

//SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception: This method configures
//the security settings using the HttpSecurity object.
//
//        http.csrf().disable(): This disables Cross-Site Request Forgery (CSRF) protection.
//While useful in some scenarios, disabling CSRF can make the application vulnerable to certain
//types of attacks. It's important to ensure that disabling CSRF is appropriate for your application's
//security needs.
//
//authorizeHttpRequests((authorize) -> { authorize.anyRequest().authenticated(); }):
//This configures the authorization rules. Here, it states that any request to the application
//must be authenticated. This means that all endpoints are secured and require the user to be
//authenticated.
//
//        .httpBasic(Customizer.withDefaults()): This enables HTTP Basic authentication with
//default settings. HTTP Basic authentication is a simple authentication scheme built into the
//HTTP protocol. It’s not recommended for production use without TLS (HTTPS) because it transmits
//credentials in an easily decodable form.
//
//        return http.build();: This finalizes the security configuration and returns the configured
//SecurityFilterChain bean.


@Configuration
@EnableMethodSecurity
@AllArgsConstructor
public class SpringSecurityConfig {

    private UserDetailsService userDetailsService;

    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    // Setting up basic http authentication to every http url of the project
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeHttpRequests((authorize) -> {

                    // Role Based Authentication
//                    authorize.requestMatchers(HttpMethod.POST, "/todos/**").hasRole("ADMIN");
//                    authorize.requestMatchers(HttpMethod.PUT, "/todos/**").hasRole("ADMIN");
//                    authorize.requestMatchers(HttpMethod.DELETE, "/todos/**").hasRole("ADMIN");
//                    authorize.requestMatchers(HttpMethod.GET, "/todos/**").hasAnyRole("ADMIN", "USER");
//                    authorize.requestMatchers(HttpMethod.PATCH, "/todos/**").hasAnyRole("ADMIN", "USER");
//                    authorize.requestMatchers(HttpMethod.GET, "/api/**").permitAll();
                    authorize.anyRequest().authenticated();
                }).httpBasic(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

//    @Bean
//    public UserDetailsService userDetailsService(){
//
//        UserDetails ritik = User.builder()
//                .username("ritik")
//                .password(passwordEncoder().encode("1234"))
//                .roles("USER")
//                .build();
//
//        UserDetails admin = User.builder()
//                .username("admin")
//                .password(passwordEncoder().encode("admin"))
//                .roles("ADMIN")
//                .build();
//
//        return new InMemoryUserDetailsManager(ritik, admin);
//    }
}
