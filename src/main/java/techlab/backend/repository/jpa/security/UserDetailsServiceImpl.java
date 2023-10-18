package techlab.backend.repository.jpa.security;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service(value = "userDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserSecurityRepository userSecurityRepository;

    public UserDetailsServiceImpl(UserSecurityRepository userSecurityRepository) {
        this.userSecurityRepository = userSecurityRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserSecurity userSecurity = userSecurityRepository.findByName(username).orElseThrow( ()->
                new UsernameNotFoundException("No user is found"));
        //UserDetails userDetailsReeturn = UserDetailsImpl.fromUser(userSecurity);
        //return userDetailsReeturn;
        return new User(userSecurity.getName(), userSecurity.getPassword(), Collections.singleton(new SimpleGrantedAuthority(userSecurity.getRole())));
    }
}
