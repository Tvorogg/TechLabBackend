package techlab.backend.repository.jpa.security;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class UserSecurity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="name")
    private String name;

    @Column(name="email")
    private String email;

    @Column(name="role")
    private String role;

    @Column(name="password")
    private String password;

    @Column(name="status")
    private String status;

    public UserSecurity() {

    }
}
