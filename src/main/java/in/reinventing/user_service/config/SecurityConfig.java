package in.reinventing.user_service.config;

import lombok.AllArgsConstructor;
import org.springframework.cglib.proxy.NoOp;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
@AllArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity)  throws Exception {
       return httpSecurity
               .csrf(csrf->csrf.disable())
               .headers(headers->headers.frameOptions(frame-> frame.disable()))
                //.authenticationProvider(authenticationProvider())
               //.userDetailsService(customUserDetailsService)
                .authorizeHttpRequests(
                        authorizationManagerRequestMatcherRegistry ->
                                authorizationManagerRequestMatcherRegistry
                                        .requestMatchers("/auth/**","/h2-console/**","/error/**","/accuweather-data.json").permitAll()
//                                        .requestMatchers("/auth").permitAll()
                                        .requestMatchers("/user/**").hasRole("USER")
                                        .requestMatchers("/admin/**").hasRole("ADMIN")
                                        .anyRequest().authenticated()
                                        //.anyRequest().permitAll()
                )
               . httpBasic(Customizer.withDefaults())
               //.sessionManagement(s->s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
               .build();
    }

//    @Bean
//    public AuthenticationProvider authenticationProvider(){
//        DaoAuthenticationProvider authenticationProvider=new DaoAuthenticationProvider(customUserDetailsService);
//        authenticationProvider.setPasswordEncoder(passwordEncoder());
//        return authenticationProvider;
//    }
//
//    @Bean
//    public AuthenticationManager authenticationManager(
//            AuthenticationConfiguration config) throws Exception {
//        return config.getAuthenticationManager();
//    }

//    @Bean
//    public PasswordEncoder passwordEncoder(){
//        return NoOpPasswordEncoder.getInstance();
//    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
