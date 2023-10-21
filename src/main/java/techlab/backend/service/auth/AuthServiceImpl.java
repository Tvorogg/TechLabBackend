package techlab.backend.service.auth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import techlab.backend.dto.*;
import org.springframework.stereotype.Component;
import techlab.backend.repository.jpa.security.UserSecurity;
import techlab.backend.repository.jpa.security.UserSecurityRepository;
import techlab.backend.security.JwtTokenProvider;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class AuthServiceImpl implements AuthService {

    private final Map<Long, UserInfo> users = new ConcurrentHashMap<>();
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserSecurityRepository userSecurityRepository;
    private final JwtTokenProvider jwtTokenProvider;

    { // test
        users.put(1L, new UserInfo("Peter", "qwerty", "piter@mail.ru", 3432));
    }

    public AuthServiceImpl(PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, UserSecurityRepository userSecurityRepository, JwtTokenProvider jwtTokenProvider) {
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.userSecurityRepository = userSecurityRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public UserInfo getUserInfo(String email) {

        UserSecurity userSecurity = userSecurityRepository.findByName("admin").orElseThrow(() ->
                new RuntimeException("No user is found"));
        log.info(String.valueOf(userSecurity));
        return users.get(1L);
    }

    @Override
    public UserSignedUpResponseDto signUpUser(UserSignUpRequest usernamePasswordDto) {

        UserSecurity userSecuritysave = new UserSecurity();
        userSecuritysave.setName(usernamePasswordDto.username());
        userSecuritysave.setEmail(usernamePasswordDto.email());
        userSecuritysave.setPassword(passwordEncoder.encode(usernamePasswordDto.password()));
        userSecuritysave.setStatus("active");
        userSecuritysave.setRole("user");

        userSecurityRepository.saveAndFlush(userSecuritysave);
        return new UserSignedUpResponseDto(usernamePasswordDto.username(), true, "USER");
    }

    @Override
    public UserSignedInResponseDto signInUser(UserSignInRequest usernamePasswordDto) {

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(usernamePasswordDto.username(),
                    usernamePasswordDto.password()));
            UserSecurity user = userSecurityRepository.findByName(usernamePasswordDto.username()).orElseThrow(() ->
                    new UsernameNotFoundException("No user is found"));

            log.info("PostMapping UserSecurity = " + user);
            String token = jwtTokenProvider.createToken(usernamePasswordDto.username(), user.getRole());
            return new UserSignedInResponseDto(user.getName(), token);
        } catch (AuthenticationException e) {
            throw new RuntimeException("Auth failed");
        }
    }
}
