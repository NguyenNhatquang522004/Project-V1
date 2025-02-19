package com.example.my_app.modules.Register;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.my_app.Enum.StatusRole;
import com.example.my_app.Mapper.RegisterMapper;
import com.example.my_app.Repository.RoleRepository;
import com.example.my_app.Repository.UserRepository;
import com.example.my_app.model.Role_Permission.Role;
import com.example.my_app.model.User.User;
import com.example.my_app.modules.Register.DTO.RegisterStepOneDTO;
import com.example.my_app.modules.Register.DTO.RegisterStepThreeDTO;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RegisterServices {

    PasswordEncoder passwordEncoder;

    UserRepository userRepository;

    RoleRepository roleRepository;

    RegisterMapper userMapper;

    @Autowired
    public RegisterServices(UserRepository userRepository, RegisterMapper userMapper, PasswordEncoder passwordEncoder,
            RoleRepository roleRepository) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    public boolean CheckEmail(String data) throws Exception {
        try {
            Optional<User> searchUser = userRepository.findByEmail(data);
            return searchUser.isPresent();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    Optional<User> handleFetchByEmail(String data) throws Exception {
        return userRepository.findByEmail(data);
    }

    boolean handleaddUser(RegisterStepOneDTO data) throws Exception {
        try {
            User user = userMapper.toUser(data);
            userRepository.save(user);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }

    }

    boolean handleUpdateUser(RegisterStepOneDTO data, User user) throws Exception {
        try {
            data.setCode(null);
            data.setCode_expired(null);
            userMapper.updateUser(user, data);
            userRepository.save(user);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }

    }

    boolean handleUpdateUser(RegisterStepThreeDTO data, User user) throws Exception {
        try {

            Role role = new Role();
            role.setRole_user(user);
            role.setDescription(StatusRole.customers);
            roleRepository.save(role);
            userMapper.UpdateCreatAccountUser(user, data);
            userRepository.save(user);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }

    }

    boolean handleValidTime(LocalDateTime data) throws Exception {
        try {
            LocalDateTime now = LocalDateTime.now();
            return Math.abs(Duration.between(data, now).toMinutes()) <= 30 ? true : false;
        } catch (Exception e) {
            return false;
        }
    }

    String handleHashPassword(String data) throws Exception {
        try {
            String Hash = passwordEncoder.encode(data);
            return Hash;
        } catch (Exception e) {
            return null;
        }
    }
}
