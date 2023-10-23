package techlab.backend.service.auth;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import techlab.backend.dto.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @Operation(description = "Getting all registration information of the user by email, requires the 'admin' authorities")
    @GetMapping("/admin/users-info/{email}")
    public ResponseEntity<UserInfo> adminGetUserInfo(@PathVariable String email) {
        UserInfo result = authService.getUserInfo(email);
        return ResponseEntity.ok(result);
    }
}
