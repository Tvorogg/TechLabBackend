package techlab.backend.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import techlab.backend.repository.jpa.security.UserSecurity;
import techlab.backend.repository.jpa.security.UserSecurityRepository;

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

        return userSecurity;
    }
}
