package techlab.backend.service.auth;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import techlab.backend.dto.security.*;
import techlab.backend.repository.jpa.courses.Courses;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Operation(description = "Register a new user by username and password")
    @PostMapping("/signup")
    public ResponseEntity<UserSignedUpResponseDto> signUp(@RequestBody UserSignUpRequest request) {
        return ResponseEntity.ok(authService.signUpUser(request));
    }

    @Operation(description = "Log in user and get access JWT token in response")
    @PostMapping("/signin")
    public ResponseEntity<UserSignedInResponseDto> signIn(@RequestBody UserSignInRequest request) {
        return ResponseEntity.ok(authService.signInUser(request));
    }

    @Hidden
    @GetMapping("/users/{email}")
    public ResponseEntity<UserInfo> getUserInfoById(@PathVariable String email) {
        UserInfo result = authService.getUserInfo(email);
        return ResponseEntity.ok(result);
    }

    @Operation(description = "Getting all registration information of all users, requires the 'admin' authorities")
    @GetMapping("/admin/users-info")
    public ResponseEntity<List<UserSecurityResponseDTO>> adminGetUserInfo() {
        List<UserSecurityResponseDTO> result = authService.getAllUsers();
        return ResponseEntity.ok(result);
    }

    @Operation(description = "Getting all registration information of all users, requires the 'admin' authorities")
    @GetMapping("/admin/courses-info")
    public ResponseEntity<List<Courses>> adminGetCoursesInfo() {
        List<Courses> result = authService.getAllCourses();
        return ResponseEntity.ok(result);
    }

}
