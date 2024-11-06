package com.ecommerce.config;

import com.ecommerce.model.Role;
import com.ecommerce.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
public class RoleConfig {

    @Bean
    public CommandLineRunner setupDefaultRoles(RoleRepository roleRepository) {
        return args -> {
            List<String> roles = Arrays.asList("ROLE_ADMIN", "ROLE_USER");
            for (String roleName : roles) {
                if (roleRepository.findByName(roleName).isEmpty()) {
                    Role role = new Role();
                    role.setName(roleName);
                    roleRepository.save(role);
                }
            }
        };
    }

}
