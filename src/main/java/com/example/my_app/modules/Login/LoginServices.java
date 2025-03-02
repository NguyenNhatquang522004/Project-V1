package com.example.my_app.modules.Login;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Service;

import com.example.my_app.Enum.Role_Permission.StatusRole;
import com.example.my_app.Enum.user.StatusUserEntry;
import com.example.my_app.Mapper.UserMapper;
import com.example.my_app.Repository.CustomRepository.RoleCustom;
import com.example.my_app.Repository.User.UserRepository;
import com.example.my_app.model.Role_Permission.Permission;
import com.example.my_app.model.Role_Permission.Role;
import com.example.my_app.model.User.User;
import com.example.my_app.modules.Login.DTO.GetDataGoogleDTO;
import com.example.my_app.modules.Login.DTO.LoginNormalDTO;

import jakarta.servlet.http.Cookie;
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

    @Transactional
    public Optional<User> handleFetchByEmail(LoginNormalDTO data) throws Exception {
        return userRepository.findByEmail(data.getEmail());
    }

    @Transactional
    public boolean handleDecode(String request, String user_data) throws Exception {
        return passwordEncoder.matches(request, user_data);
    }

    @Transactional
    public Optional<User> handleAddUser(GetDataGoogleDTO request) throws Exception {
        try {
            User user = new User();
            user.setUrl(request.getPicture());
            user.setStatusEntry(StatusUserEntry.Google);
            user.setUsername(request.getName());
            user.setEmail(request.getEmail());
            Role initRole = roleCustom.handleDefaultPermissionRole(StatusRole.Customers, user);
            if (initRole == null) {
                return null;
            }
            user.setUser_role(initRole);
            userRepository.save(user);
            return Optional.of(user);
        } catch (Exception e) {
            return null;
        }
    }

    @Transactional
    public boolean handleFindbyEmailAndStatusEntry(GetDataGoogleDTO request) throws Exception {
        try {
            Optional<User> searchUser = userRepository.findByEmailAndStatusEntry(request.getEmail(),
                    StatusUserEntry.Google);
            return searchUser.isPresent();
        } catch (Exception e) {
            return false;
        }
    }

    @Transactional
    public Set<String> handleGetPermisson(Optional<User> data) throws Exception {
        return data
                .map(User::getUser_role) // Get Role from User
                .map(Role::getRole_permission) // Get Set<Permission> from Role (wrapped in //
                .orElse(Set.of()) // If missing, use an empty Set
                .stream() // Convert Set<Permission> to Stream<Permission>
                .map(Permission::getDescription) // Get StatusPermission from Permission
                .map(Enum::name) // Get the enum name as a String
                .collect(Collectors.toSet()); // Collect into a Set<String>
    }

    public Cookie handleCookie(String name, String data, int age, boolean Secure, boolean HttpOnly) {
        Cookie cookie = new Cookie(name, data);
        cookie.setMaxAge(36000);
        cookie.setSecure(Secure);
        cookie.setHttpOnly(HttpOnly);
        return cookie;
    }
}
