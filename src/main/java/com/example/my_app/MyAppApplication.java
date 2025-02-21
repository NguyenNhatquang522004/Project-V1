package com.example.my_app;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.my_app.Enum.StatusPermission;
import com.example.my_app.Enum.StatusRole;
import com.example.my_app.Repository.Permission.PermissionRepository;
import com.example.my_app.Repository.Role.RoleCustom;
import com.example.my_app.Repository.User.UserRepository;
import com.example.my_app.model.Role_Permission.Permission;
import com.example.my_app.model.Role_Permission.Role;
import com.example.my_app.model.User.User;

@SpringBootApplication
@EnableCaching
public class MyAppApplication implements CommandLineRunner {

	@Autowired
	PermissionRepository permissionRepository;

	@Autowired
	UserRepository userRepository;
	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	RoleCustom roleCustom;

	public static void main(String[] args) {
		SpringApplication.run(MyAppApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		for (StatusPermission value : StatusPermission.values()) {
			Permission permission = new Permission();
			permission.setDescription(value);
			permissionRepository.save(permission);
		}

		User user = new User();
		user.setPassword(passwordEncoder.encode("123"));
		user.setEmail("nguyennhatquang522004@gmail.com");
		user.setUsername("nguyennhatquang");
		userRepository.save(user);
		Role check = roleCustom.handleDefaultPermissionRole(StatusRole.Customers, user);
		user.setUser_role(check);
		userRepository.save(user);
	}

}
