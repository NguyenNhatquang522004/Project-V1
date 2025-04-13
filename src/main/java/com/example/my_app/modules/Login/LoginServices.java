package com.example.my_app.modules.Login;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.my_app.Enum.Role_Permission.StatusRole;
import com.example.my_app.Enum.user.StatusUserEntry;
import com.example.my_app.Mapper.User.UserMapper;
import com.example.my_app.Repository.Employee.EmployeeRepository;
import com.example.my_app.Repository.User.UserRepository;
import com.example.my_app.custom.CustomRepository.RoleCustom;
import com.example.my_app.model.Admin.Department;
import com.example.my_app.model.Admin.Employee;
import com.example.my_app.model.Role_Permission.Permission;
import com.example.my_app.model.Role_Permission.Role;
import com.example.my_app.model.Role_Permisson_Admin.Admin_Permisson;
import com.example.my_app.model.Role_Permisson_Admin.Admin_Role;
import com.example.my_app.model.User.User;
import com.example.my_app.modules.Login.DTO.LoginNormalDTO;

import jakarta.servlet.http.Cookie;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LoginServices {
    UserRepository userRepository;
    UserMapper userMapper;

    RoleCustom roleCustom;
    EmployeeRepository employeeRepository;

    @Autowired
    public LoginServices(UserMapper userMapper, UserRepository userRepository,
            RoleCustom roleCustom, EmployeeRepository employeeRepository) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;

        this.roleCustom = roleCustom;
        this.employeeRepository = employeeRepository;
    }

    @Transactional
    public Optional<User> handleFetchByEmail(LoginNormalDTO data) throws Exception {
        return userRepository.findByEmail(data.getEmail());
    }

    @Transactional
    public Optional<Employee> handleFetcAdminhByEmail(LoginNormalDTO data) throws Exception {
        return employeeRepository.findByEmail(data.getEmail());
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

    @Transactional
    public Set<String> handleGetAdminPermisson(Employee data) throws Exception {
        return data.getEmployee_Role().stream()
                .flatMap(adminRole -> adminRole.getAdmin_Role_Permisson().stream())
                .map(Admin_Permisson::getTitle)
                .collect(Collectors.toSet());

    }

    @Transactional
    public Set<String> handleGetDeparment(Employee data) throws Exception {
        return data.getEmployee_Role().stream()
                .map(Admin_Role::getRole_Department)
                .map(Department::getDescription)
                .map(Enum::name)
                .collect(Collectors.toSet());

    }

    public Cookie handleCookie(String name, String data, int age, boolean Secure, boolean HttpOnly) {

        Cookie cookie = new Cookie(name, data);
        cookie.setMaxAge(36000);
        cookie.setSecure(Secure);
        cookie.setHttpOnly(HttpOnly);
        return cookie;
    }

}
