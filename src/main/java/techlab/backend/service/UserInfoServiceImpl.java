package techlab.backend.service;

import techlab.backend.dto.*;
import org.springframework.stereotype.Component;
import techlab.backend.repository.jpa.security.UserSecurity;
import techlab.backend.repository.jpa.security.UserSecurityRepository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class UserInfoServiceImpl implements UserInfoService {

    private final Map<Long, UserInfo> users = new ConcurrentHashMap<>();
    //    private final InMemoryUserDetailsManager inMemoryUserDetailsManager;
//    private final PasswordEncoder passwordEncoder;
//    private final UserDetailsService userDetailsService;
    private final UserSecurityRepository userSecurityRepository;

    { // test
        users.put(1L, new UserInfo("Peter", "qwerty", "piter@mail.ru", 3432));
    }

    public UserInfoServiceImpl(UserSecurityRepository userSecurityRepository) {
//        this.inMemoryUserDetailsManager = inMemoryUserDetailsManager;
        this.userSecurityRepository = userSecurityRepository;
    }

    @Override
    public UserInfo getUserInfo(long id) {

//        UserSecurity userSecuritysave = new UserSecurity();
//        userSecuritysave.setName("user1");
//        userSecuritysave.setEmail("user1@mail");
//        userSecuritysave.setPassword("pass");
//        userSecuritysave.setRole("user");
//
//        userSecurityRepository.saveAndFlush(userSecuritysave);

        UserSecurity userSecurity = userSecurityRepository.findByName("admin").orElseThrow( ()->
                new RuntimeException("No user is found"));
        System.out.println(userSecurity);
        return users.get(id);
    }

    @Override
    public UserInfo setUserInfo(long id, UserInfo userInfo) {
        users.put(id, userInfo);
        return users.get(id);
    }

    @Override
    public UserSignedUpResponseDto signUpUser(UserSignUpModel usernamePasswordDto) {

//        UserDetails user = User.builder()
//                .username(usernamePasswordDto.username())
//                //.password(passwordEncoder.encode(usernamePasswordDto.password()))
//                .roles("USER")
//                .build();

        //inMemoryUserDetailsManager.createUser(user);
        return new UserSignedUpResponseDto(usernamePasswordDto.username(), true, "USER");
    }

    @Override
    public UserSignedInResponseDto signInUser(UserSignInModel usernamePasswordDto) {
        return new UserSignedInResponseDto(usernamePasswordDto.username(), "gesg34g3gh");
    }

}
