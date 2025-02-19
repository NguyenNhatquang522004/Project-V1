package com.example.my_app.modules.Login;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.my_app.Mapper.LoginMapper;
import com.example.my_app.Repository.UserRepository;
import com.example.my_app.model.User.User;
import com.example.my_app.modules.Login.DTO.LoginNormalDTO;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LoginServices {
    UserRepository userRepository;
    LoginMapper loginMapper;
    PasswordEncoder passwordEncoder;

    @Autowired
    public LoginServices(LoginMapper loginMapper, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.loginMapper = loginMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<User> handleFetchByEmail(LoginNormalDTO data) throws Exception {
        return userRepository.findByEmail(data.getEmail());
    }

    public boolean handleDecode(String request, String user_data) throws Exception {
        return passwordEncoder.matches(request, user_data);
    }

}
