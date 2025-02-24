package com.example.my_app.modules.Auth;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.my_app.Repository.User.UserRepository;
import com.example.my_app.model.User.User;

@Service
public class CustomUserDetailsService implements UserDetailsService {
        @Autowired
        UserRepository userRepository;

        @Override
        public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
                User user = userRepository.findByEmail(email).orElseThrow(
                                () -> new UsernameNotFoundException("User Not Found with username: " + email));
                List<GrantedAuthority> authorities = Optional.ofNullable(user.getUser_role())
                                .stream()
                                .map(role -> new SimpleGrantedAuthority(
                                                "ROLE_" + role.getDescription().toString().toUpperCase()))
                                .collect(Collectors.toList());
                return new org.springframework.security.core.userdetails.User(
                                user.getEmail(),
                                user.getPassword(),
                                authorities);
        }
}
