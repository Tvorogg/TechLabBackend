package techlab.backend.service.useraccount;

import org.springframework.stereotype.Service;
import techlab.backend.dto.useraccount.UserAccountInfoDTO;
import techlab.backend.repository.jpa.courses.CoursesRepository;
import techlab.backend.repository.jpa.security.UserSecurity;
import techlab.backend.repository.jpa.security.UserSecurityRepository;

import java.util.Optional;

@Service
public class UserAccountServiceImpl implements UserAccountService {

    private final UserSecurityRepository userSecurityRepository;
    private final CoursesRepository coursesRepository;

    public UserAccountServiceImpl(UserSecurityRepository userSecurityRepository, CoursesRepository coursesRepository) {
        this.userSecurityRepository = userSecurityRepository;
        this.coursesRepository = coursesRepository;
    }


    @Override
    public UserAccountInfoDTO getUserAccountInfo(String email) {
        Optional<UserSecurity> userSecurities = userSecurityRepository.findByEmail(email);
        return userSecurities.map(userSecurity -> new UserAccountInfoDTO(
                userSecurity.getUserUniqueId(),
                userSecurity.getName(),
                userSecurity.getEmail(),
                userSecurity.getRole(),
                userSecurity.getStatus(),
                userSecurity.getRegisteredAt(),
                userSecurity.getCourses()
        )).orElse(null);
    }
}
