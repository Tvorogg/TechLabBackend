package techlab.backend.service.useraccount;

import techlab.backend.dto.security.*;
import techlab.backend.dto.useraccount.UserAccountInfoDTO;
import techlab.backend.repository.jpa.courses.Courses;

import java.util.List;

public interface UserAccountService {
    UserAccountInfoDTO getUserAccountInfo(String email);
}
