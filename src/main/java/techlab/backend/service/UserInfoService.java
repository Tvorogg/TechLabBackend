package techlab.backend.service;

import techlab.backend.dto.*;

public interface UserInfoService {

    UserInfo getUserInfo(long id);
    UserInfo setUserInfo(long id, UserInfo userInfo);

    UserSignedUpResponseDto signUpUser(UserSignUpModel usernamePasswordDto);
    UserSignedInResponseDto signInUser(UserSignInModel usernamePasswordDto);



}
