package com.registration.registration;


import com.registration.appuser.AppUserRole;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class RegistrationRequest {
        private final String firstName;
        private final String lastName;
        private final String username;
        private final int EIN;
        private final AppUserRole appUserRole;
        private final String email;
        private final String password;

}
