package in.reinventing.user_service.config;

import in.reinventing.user_service.entity.infa.User;
import in.reinventing.user_service.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(username)
                .orElseThrow(()->
                        new UsernameNotFoundException(String.format("User %s not found!",username)));
        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority("ROLE_"+user.getRole());
        Collection<SimpleGrantedAuthority>  grantedAuthority = new ArrayList<>(Collections.singleton(simpleGrantedAuthority));
        return new org.springframework.security.core.userdetails.User(username,user.getPassword(),grantedAuthority);
    }
}
