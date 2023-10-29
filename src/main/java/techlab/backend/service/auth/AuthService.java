package techlab.backend.service.auth;

import techlab.backend.dto.security.*;
import techlab.backend.repository.jpa.courses.Courses;

import java.util.List;

public interface AuthService {

    UserSignedUpResponseDto signUpUser(UserSignUpRequest usernamePasswordDto);
    UserSignedInResponseDto signInUser(UserSignInRequest usernamePasswordDto);

    UserInfo getUserInfo(String email);

    List<UserSecurityResponseDTO> getAllUsers();
    List<Courses> getAllCourses();
}
