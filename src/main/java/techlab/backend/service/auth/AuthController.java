package techlab.backend.service.auth;

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

    @PostMapping("/signup")
    public ResponseEntity<UserSignedUpResponseDto> signUp(@RequestBody UserSignUpRequest request) {
        return ResponseEntity.ok(authService.signUpUser(request));
    }

    @PostMapping("/signin")
    public ResponseEntity<UserSignedInResponseDto> signIn(@RequestBody UserSignInRequest request) {
        return ResponseEntity.ok(authService.signInUser(request));
    }

    @GetMapping("/users/{email}")
    public ResponseEntity<UserInfo> getUserInfoById(@PathVariable String email) {
        UserInfo result = authService.getUserInfo(email);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/admin/users-info/{email}")
    public ResponseEntity<UserInfo> adminGetUserInfo(@PathVariable String email) {
        UserInfo result = authService.getUserInfo(email);
        return ResponseEntity.ok(result);
    }
}
