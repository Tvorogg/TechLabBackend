package techlab.backend.service;

import techlab.backend.dto.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class Controller {

    private final UserInfoService userInfoService;

    public Controller(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    @PostMapping("/signup")
    public ResponseEntity<UserSignedUpResponseDto> signUp(@RequestBody UserSignUpModel userSignUpModel) {
        return ResponseEntity.ok(userInfoService.signUpUser(userSignUpModel));
    }

    @PostMapping("/signin")
    public ResponseEntity<UserSignedInResponseDto> signIn(@RequestBody UserSignInModel userSignInModel) {
        return ResponseEntity.ok(userInfoService.signInUser(userSignInModel));
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserInfo> getUserInfoById(@PathVariable long id) {

        UserInfo result = userInfoService.getUserInfo(id);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/users/{id}")
    public ResponseEntity<UserInfo> setUserInfoById(@PathVariable long id, @RequestBody UserInfo userInfo) {

        UserInfo result = userInfoService.setUserInfo(id, userInfo);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/admin/{id}")
    public ResponseEntity<UserInfo> getUserInfo(@PathVariable long id) {

        UserInfo result = userInfoService.getUserInfo(id);
        return ResponseEntity.ok(result);
    }
}

