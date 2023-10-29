package techlab.backend.service.useraccount;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import techlab.backend.dto.useraccount.UserAccountInfoDTO;

@RestController
@RequestMapping("/api/v1")
public class UserAccountController {

    private final UserAccountService userAccountService;

    public UserAccountController(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
    }

    @Operation(description = "Getting user account information")
    @GetMapping("/users/users-info/{email}")
    public ResponseEntity<UserAccountInfoDTO> getUserAccountInfo(@PathVariable String email) {
        UserAccountInfoDTO result = userAccountService.getUserAccountInfo(email);
        return ResponseEntity.ok(result);
    }
}
