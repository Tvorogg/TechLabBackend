package techlab.backend.service.auth;

import techlab.backend.dto.*;

public interface AuthService {

    UserSignedUpResponseDto signUpUser(UserSignUpRequest usernamePasswordDto);
    UserSignedInResponseDto signInUser(UserSignInRequest usernamePasswordDto);

    UserInfo getUserInfo(String email);
}
