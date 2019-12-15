package com.xebia.fs101.writerpad.config;

import com.xebia.fs101.writerpad.api.representations.UserRequest;
import com.xebia.fs101.writerpad.domain.User;
import com.xebia.fs101.writerpad.domain.WriterpadRole;
import com.xebia.fs101.writerpad.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ApplicationStartupRunner implements CommandLineRunner {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        Optional<User> admin = Optional.ofNullable(userRepository
                .findByUsernameOrEmail("admin", "admin@writerpad"));
        if (!admin.isPresent()) {
            UserRequest userRequest =
                    new UserRequest("admin",
                            "admin@writerpad",
                            "p@ssw0rd",
                            WriterpadRole.ADMIN);
            User adminUser = userRequest.toUser(passwordEncoder);
            userRepository.save(adminUser);
        }
    }
}
