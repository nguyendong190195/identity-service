package com.vn.identity_service.configuration;

import com.vn.identity_service.entity.User;
import com.vn.identity_service.enums.Role;
import com.vn.identity_service.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;

@Slf4j
@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ApplicationInitConfig {
    @Autowired
    PasswordEncoder passwordEncoder;
    @Bean
    ApplicationRunner applicationRunner(UserRepository repository) {
        return args -> {
            if (repository.findByUsername("admin@gmail.com").isEmpty()) {
                var role = new HashSet<String>();
                role.add(Role.ADMIN.name());
                User user = User.builder()
                        .username("admin@gmail.com")
                        .password(passwordEncoder.encode("admin@123"))
//                        .roles(role)
                        .build();
                repository.save(user);
                log.warn("Đã tạo tài khoản admin@gmail.com");
            }
        };
    }
}
