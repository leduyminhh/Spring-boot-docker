package com.leduyminh.userservice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.leduyminh.userservice.entities.Role;
import com.leduyminh.userservice.repository.inf.RoleRepo;
import com.leduyminh.userservice.service.inf.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.event.EventListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
@EnableEurekaClient
@EnableJpaAuditing
public class UserMgtServiceApplication {

	@Autowired
	RoleRepo roleRepo;

	public static void main(String[] args) {
		SpringApplication.run(UserMgtServiceApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void doSomethingAfterStartup() {
		initDataRoles();
	}

	private void initDataRoles() {
		List<Role> roleList = new ArrayList<>();

		if (roleRepo.count() == 0L) {
			Role role = new Role();
			role.setName("ROLE_ADMIN");
			role.setDescription("Full permission");
			roleList.add(role);

			Role role1 = new Role();
			role1.setName("ROLE_USER");
			role1.setDescription("User permission");
			roleList.add(role1);

			roleRepo.saveAll(roleList);
		}
	}
}
