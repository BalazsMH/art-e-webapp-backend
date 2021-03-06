package com.arte.backend.security;

import com.arte.backend.model.database.entity.UserData;
import com.arte.backend.model.database.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserData user = userRepository.findByEmail(email)
                .orElseThrow(()->new UsernameNotFoundException("Email: " + email + "not found"));
        return new User(user.getUserName(),
                        user.getPassword(),
                        user.getRoles().stream().map((role)->new SimpleGrantedAuthority(role.name())).collect(Collectors.toList()));
    }
}
