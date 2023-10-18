package techlab.backend.repository.jpa.security;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.*;

@Data
public class UserDetailsImpl implements UserDetails {

    private final String name;
    private final String password;
    private final boolean isActive;
    private final List<SimpleGrantedAuthority> authorities;

    public UserDetailsImpl(String name, String password, boolean isActive, List<SimpleGrantedAuthority> authorities) {
        this.name = name;
        this.password = password;
        this.isActive = isActive;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isActive;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isActive;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isActive;
    }

    @Override
    public boolean isEnabled() {
        return isActive;
    }



    public static UserDetails fromUser(UserSecurity user) {
        return new User(user.getName(), user.getPassword(),
                true,
                true,
                true,
                true,
                Collections.singleton(new SimpleGrantedAuthority(user.getRole())));

    }
}
