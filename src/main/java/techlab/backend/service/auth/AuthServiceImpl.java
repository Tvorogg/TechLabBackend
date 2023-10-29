package techlab.backend.service.auth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import techlab.backend.dto.security.*;
import techlab.backend.repository.jpa.courses.Courses;
import techlab.backend.repository.jpa.courses.CoursesRepository;
import techlab.backend.repository.jpa.security.UserSecurity;
import techlab.backend.repository.jpa.security.UserSecurityRepository;
import techlab.backend.security.JwtTokenProvider;
import techlab.backend.service.snowflake.SnowFlake;

import java.time.OffsetDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Slf4j
@Component
public class AuthServiceImpl implements AuthService {

    private final Map<Long, UserInfo> users = new ConcurrentHashMap<>();
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserSecurityRepository userSecurityRepository;
    private final CoursesRepository coursesRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final SnowFlake snowFlake;

    { // test
        users.put(1L, new UserInfo("Peter", "qwerty", "piter@mail.ru", 3432));
    }

    public AuthServiceImpl(PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, UserSecurityRepository userSecurityRepository, CoursesRepository coursesRepository, JwtTokenProvider jwtTokenProvider, SnowFlake snowFlake) {
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.userSecurityRepository = userSecurityRepository;
        this.coursesRepository = coursesRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.snowFlake = snowFlake;
    }

    public UserInfo getUserInfo(String email) {

        UserSecurity userSecurity = userSecurityRepository.findByName("admin").orElseThrow(() ->
                new RuntimeException("No user is found"));
        log.info(String.valueOf(userSecurity));
        return users.get(1L);
    }

    public List<UserSecurityResponseDTO> getAllUsers() {
        List<UserSecurity> userSecurities = userSecurityRepository.findAllByIdBetween(1L, 111L);
        return userSecurities.stream()
                .map(user -> new UserSecurityResponseDTO(
                        user.getId(),
                        user.getUserUniqueId(),
                        user.getName(),
                        user.getEmail(),
                        user.getRole(),
                        user.getStatus(),
                        user.getRegisteredAt(),
                        user.getCourses()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public List<Courses> getAllCourses() {
        List<Courses> courses = coursesRepository.findAllByIdBetween(0L, 111L);
        log.info(String.valueOf(courses));
        return courses;
    }


    @Override
    public UserSignedUpResponseDto signUpUser(UserSignUpRequest usernamePasswordDto) {

        UserSecurity userSecuritysave = new UserSecurity();
        userSecuritysave.setName(usernamePasswordDto.username());
        userSecuritysave.setEmail(usernamePasswordDto.email());
        userSecuritysave.setPassword(passwordEncoder.encode(usernamePasswordDto.password()));
        userSecuritysave.setStatus("active");
        userSecuritysave.setRole("user");
        userSecuritysave.setUserUniqueId(snowFlake.nextId());
        userSecuritysave.setRegisteredAt(OffsetDateTime.now());


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
