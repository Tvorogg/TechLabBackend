package techlab.backend.repository.jpa.security;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserSecurityRepository extends JpaRepository<UserSecurity, Long> {

    Optional<UserSecurity> findByName(String name);
    Optional<UserSecurity> findByEmail(String email);

    List<UserSecurity> findAllByIdBetween(Long id0, long id1);
}
