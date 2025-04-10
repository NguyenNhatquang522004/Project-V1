package com.example.my_app.modules.Register;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.my_app.Enum.Role_Permission.StatusRole;
import com.example.my_app.Enum.user.StatusUserEntry;
import com.example.my_app.Mapper.User.UserMapper;
import com.example.my_app.Repository.Employee.EmployeeRepository;
import com.example.my_app.Repository.Role.RoleRepository;
import com.example.my_app.Repository.User.UserRepository;
import com.example.my_app.custom.CustomRepository.RoleCustom;
import com.example.my_app.model.Admin.Employee;
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

    UserMapper userMapper;

    RoleCustom roleCustom;

    EmployeeRepository employeeRepository;

    @Autowired
    public RegisterServices(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder,
            RoleRepository roleRepository, RoleCustom roleCustom, EmployeeRepository employeeRepository) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.roleCustom = roleCustom;
        this.employeeRepository = employeeRepository;
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

    public boolean CheckEmailAdmin(String data) throws Exception {
        try {
            Optional<Employee> searchUser = employeeRepository.findByEmail(data);
            return searchUser.isPresent();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    Optional<User> handleFetchByEmail(String data) throws Exception {
        return userRepository.findByEmail(data);
    }

    Optional<Employee> handleFetchByEmailAdmin(String data) throws Exception {
        return employeeRepository.findByEmail(data);
    }

    @Transactional
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

    @Transactional
    boolean handleaddUseradmin(RegisterStepOneDTO data) throws Exception {
        try {
            Employee user = new Employee();
            user.setCode(data.getCode());
            user.setCode_expired(data.getCode_expired());
            user.setEmail(data.getEmail());
            employeeRepository.saveAndFlush(user);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }

    }

    @Transactional
    boolean handleUpLocalDateTimeUser(RegisterStepOneDTO data, User user) throws Exception {
        try {
            data.setCode(null);
            data.setCode_expired(null);
            userMapper.upLocalDateTimeUser(user, data);
            userRepository.save(user);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }

    }

    @Transactional
    boolean handleUpLocalDateTimeUser(RegisterStepThreeDTO data, User user) throws Exception {
        try {

            Role initRole = roleCustom.handleDefaultPermissionRole(StatusRole.Customers, user);
            if (initRole == null) {
                return false;
            }
            data.setUser_role(initRole);
            data.setStatusEntry(StatusUserEntry.Local);
            userMapper.UpLocalDateTimeCreatAccountUser(user, data);
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
