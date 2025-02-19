package com.example.my_app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;

import com.example.my_app.Enum.StatusPermission;
import com.example.my_app.Repository.PermissionRepository;
import com.example.my_app.model.Role_Permission.Permission;

@SpringBootApplication
@EnableCaching
@ComponentScan("com.example.my_app.Mapper")
public class MyAppApplication implements CommandLineRunner {

	@Autowired
	PermissionRepository permissionRepository;

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
	}

}
