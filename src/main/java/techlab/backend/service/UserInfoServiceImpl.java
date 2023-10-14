package techlab.backend.service;

import techlab.backend.dto.*;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class UserInfoServiceImpl implements UserInfoService {

    private final Map<Long, UserInfo> users = new ConcurrentHashMap<>();
    private final InMemoryUserDetailsManager inMemoryUserDetailsManager;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;

    { // test
        users.put(1L, new UserInfo("Peter", "qwerty", "piter@mail.ru", 3432));
    }

    public UserInfoServiceImpl(InMemoryUserDetailsManager inMemoryUserDetailsManager, PasswordEncoder passwordEncoder, UserDetailsService userDetailsService) {
        this.inMemoryUserDetailsManager = inMemoryUserDetailsManager;
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public UserInfo getUserInfo(long id) {
        return users.get(id);
    }

    @Override
    public UserInfo setUserInfo(long id, UserInfo userInfo) {
        users.put(id, userInfo);
        return users.get(id);
    }

    @Override
    public UserSignedUpResponseDto signUpUser(UserSignUpModel usernamePasswordDto) {

        UserDetails user = User.builder()
                .username(usernamePasswordDto.username())
                .password(passwordEncoder.encode(usernamePasswordDto.password()))
                .roles("USER")
                .build();

        inMemoryUserDetailsManager.createUser(user);
        return new UserSignedUpResponseDto(usernamePasswordDto.username(), true, "USER");
    }

    @Override
    public UserSignedInResponseDto signInUser(UserSignInModel usernamePasswordDto) {
        return new UserSignedInResponseDto(usernamePasswordDto.username(), "gesg34g3gh");
    }

}
