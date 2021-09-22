package com.registration.appuser;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@Entity
public class AppUser implements UserDetails {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE
            , generator = "appUserSequence"
    )
    @SequenceGenerator(
            sequenceName = "appUserSequence",
            allocationSize = 1,
            name = "appUserSequence"
    )
    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private int ein;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private AppUserRole appUserRole;
    private Boolean isLocked = false;
    private Boolean enabled = false;
    private Boolean isCredentialsExpired = false;


    public AppUser(String firstName, String lastName, String username, int ein, AppUserRole appUserRole, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.ein = ein;
        this.appUserRole = appUserRole;
        this.email = email;
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority =
                new SimpleGrantedAuthority(appUserRole.name());
        return Collections.singletonList(authority);
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public int getEin() {
        return ein;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !isLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !isCredentialsExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
