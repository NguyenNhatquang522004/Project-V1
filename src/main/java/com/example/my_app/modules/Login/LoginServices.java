package com.example.my_app.modules.Login;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Service;

import com.example.my_app.Enum.StatusRole;
import com.example.my_app.Enum.StatusUserEntry;
import com.example.my_app.Mapper.UserMapper;
import com.example.my_app.Repository.Role.RoleCustom;
import com.example.my_app.Repository.User.UserRepository;
import com.example.my_app.model.Role_Permission.Role;
import com.example.my_app.model.User.User;
import com.example.my_app.modules.Login.DTO.GetDataGoogleDTO;
import com.example.my_app.modules.Login.DTO.LoginNormalDTO;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LoginServices {
    UserRepository userRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;
    RoleCustom roleCustom;

    @Autowired
    public LoginServices(UserMapper userMapper, UserRepository userRepository, PasswordEncoder passwordEncoder,
            RoleCustom roleCustom) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.roleCustom = roleCustom;
    }

    public Optional<User> handleFetchByEmail(LoginNormalDTO data) throws Exception {
        return userRepository.findByEmail(data.getEmail());
    }

    public boolean handleDecode(String request, String user_data) throws Exception {
        return passwordEncoder.matches(request, user_data);
    }

    @Transactional
    public boolean handleAddUser(GetDataGoogleDTO request) throws Exception {
        try {
            User user = new User();
            user.setUrl(request.getPicture());
            user.setStatusEntry(StatusUserEntry.Google);
            user.setUsername(request.getName());
            user.setEmail(request.getEmail());
            Role initRole = roleCustom.handleDefaultPermissionRole(StatusRole.Customers, user);
            if (initRole == null) {
                return false;
            }
            user.setUser_role(initRole);
            userRepository.save(user);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean handleFindbyEmailAndStatusEntry(GetDataGoogleDTO request) throws Exception {
        try {
            Optional<User> searchUser = userRepository.findByEmailAndStatusEntry(request.getEmail(),
                    StatusUserEntry.Google);
            return searchUser.isPresent();
        } catch (Exception e) {
            return false;
        }
    }
}
