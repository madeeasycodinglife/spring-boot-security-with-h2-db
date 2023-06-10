package com.madeeasy;

import com.madeeasy.entity.Role;
import com.madeeasy.entity.User;
import com.madeeasy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
@RestController
public class SecurityApplication implements ApplicationRunner {

    @Autowired
    private UserRepository userRepository;

    public static void main(String[] args) {
        SpringApplication.run(SecurityApplication.class, args);
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER')")
    public String user() {
        return "user";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String admin() {
        return "admin";
    }

    @GetMapping("/")
    @PreAuthorize("permitAll()")
    public String forAll() {
        return "Welcome to Spring Boot";
    }


	@Override
	public void run(ApplicationArguments args) throws Exception {
		Set<Role> roleAdmin = new HashSet<>(Collections.singletonList(Role.ROLE_ADMIN));
		Set<Role> roleUser = new HashSet<>(Collections.singletonList(Role.ROLE_USER));

		User user = new User();
		user.setName("user");
		user.setPassword("user");
		user.setRoles(roleUser);
		userRepository.save(user);

		User admin = new User();
		admin.setName("admin");
		admin.setPassword("admin");
		admin.setRoles(roleAdmin);
		userRepository.save(admin);
	}

}
