package com.hrms.pms.pms_app;

import com.hrms.pms.pms_app.pms.config.AppConstants;
import com.hrms.pms.pms_app.pms.entities.Role;
import com.hrms.pms.pms_app.pms.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.UUID;

@SpringBootApplication
public class PmsAppApplication implements CommandLineRunner {

	@Autowired
	private RoleRepository roleRepository;

	public static void main(String[] args) {

		SpringApplication.run(PmsAppApplication.class, args);


	}

	@Override
	public void run(String... args) throws Exception {
		roleRepository.findByName("ROLE_"+AppConstants.ADMIN_ROLE).ifPresentOrElse(role -> {
			System.out.println("Admin Role Already Exists: " + role.getName());

		}, ()->{
			Role role = new Role();
			role.setName("ROLE_"+AppConstants.ADMIN_ROLE);
			role.setId(UUID.randomUUID());
			roleRepository.save(role);
		});

		roleRepository.findByName("ROLE_"+AppConstants.HR_ROLE).ifPresentOrElse(role -> {
			System.out.println("HR Role Already Exists: " + role.getName());

		}, ()->{
			Role role = new Role();
			role.setName("ROLE_"+AppConstants.HR_ROLE);
			role.setId(UUID.randomUUID());
			roleRepository.save(role);
		});

		roleRepository.findByName("ROLE_"+AppConstants.EMP_ROLE).ifPresentOrElse(role -> {
			System.out.println("EMP Role Already Exists: " + role.getName());

		}, ()->{
			Role role = new Role();
			role.setName("ROLE_"+AppConstants.EMP_ROLE);
			role.setId(UUID.randomUUID());
			roleRepository.save(role);
		});
	}
}
