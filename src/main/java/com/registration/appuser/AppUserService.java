package com.registration.appuser;

import com.registration.registration.token.ConfirmationToken;
import com.registration.registration.token.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AppUserService implements UserDetailsService {

    private final static String USER_NOT_FOUND = "User with EIN \"%s\" not found!";

    private final AppUserRepository appUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfirmationTokenService confirmationTokenService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return appUserRepository.findUserByUsername(username).orElseThrow(()
                -> new UsernameNotFoundException(
                String.format(USER_NOT_FOUND, username)
        ));
    }

    public int enableAppUser(String email) {
        return appUserRepository.enableAppUser(email);
    }

    public String signUpUser(AppUser user) {
        boolean emailExists = appUserRepository
                .findByEmail(user.getEmail())
                .isPresent();
        if(emailExists)
        {
            throw new IllegalStateException("email already taken");
        }
        boolean isUserExist=appUserRepository.findUserByUsername(user.getUsername()).isPresent();
        if(isUserExist)
        {
            throw new IllegalStateException("user already exists");
        }
        String encodedPassord=bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassord);
        appUserRepository.save(user);

        String token= UUID.randomUUID().toString();
        ConfirmationToken confirmationToken=new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                user
                );
        confirmationTokenService.saveConfirmationToken(confirmationToken);
        //TODO Send email
        return token;
    }
}
