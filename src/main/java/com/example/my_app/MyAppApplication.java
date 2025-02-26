package com.example.my_app;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import com.example.my_app.Enum.StatusPermission;
import com.example.my_app.Enum.StatusRole;
import com.example.my_app.Repository.Permission.PermissionRepository;
import com.example.my_app.Repository.Role.RoleCustom;
import com.example.my_app.Repository.Role.RoleRepository;
import com.example.my_app.Repository.User.UserRepository;
import com.example.my_app.model.Role_Permission.Permission;
import com.example.my_app.model.Role_Permission.Role;
import com.example.my_app.model.User.User;

import jakarta.persistence.EntityManager;

@SpringBootApplication
@EnableCaching
@EntityScan(basePackages = "com.example.my_app.model")
@EnableJpaRepositories(basePackages = "com.example.my_app.Repository")
public class MyAppApplication implements CommandLineRunner {

	@Autowired
	PermissionRepository permissionRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	RoleCustom roleCustom;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	EntityManager entityManager;

	public static void main(String[] args) {
		SpringApplication.run(MyAppApplication.class, args);
	}

	@Override
	@Transactional
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
