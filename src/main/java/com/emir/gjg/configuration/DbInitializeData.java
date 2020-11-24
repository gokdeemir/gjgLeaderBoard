package com.emir.gjg.configuration;


import com.emir.gjg.enums.Role;
import com.emir.gjg.model.entity.User;
import com.emir.gjg.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Emir Gökdemir
 * on 17 Nis 2020
 */
@Component
public class DbInitializeData {
    @Autowired
    private UserRepository userRepository;

    @PostConstruct
    private void postConstruct() {

        List<User> initialUsers = new ArrayList<>();
        if (!userRepository.existsByEmail("admin@gjg.com")) {
            User admin = new User();
            admin.setEmail("admin@gjg.com");
            admin.setPassword("$2a$10$J56.OenNhKniOPgfqLCLZu7mjo08DXlxucvXrt/hn3uBQeWM.2G7.");
            admin.setName("admin");
            admin.setSurname("admin");
            admin.setRole(Role.ADMIN);
            admin.setConfirmed(true);
            admin.setUserName("admin");
            admin.setScore(120D);
            admin.setCountry(new Locale("tr"));
            initialUsers.add(admin);
        }
        if (!userRepository.existsByEmail("basic@gjg.com")) {
            User basicUser = new User();
            basicUser.setEmail("basic@gjg.com");
            basicUser.setPassword("$2a$10$J56.OenNhKniOPgfqLCLZu7mjo08DXlxucvXrt/hn3uBQeWM.2G7.");
            basicUser.setName("default");
            basicUser.setSurname("basic");
            basicUser.setRole(Role.BASIC_USER);
            basicUser.setConfirmed(true);
            basicUser.setUserName("basicUser");
            basicUser.setScore(100D);
            basicUser.setCountry(new Locale("tr"));
            initialUsers.add(basicUser);
        }
        if (!userRepository.existsByEmail("manager@gjg.com")) {
            User manager = new User();
            manager.setEmail("manager@gjg.com");
            manager.setPassword("$2a$10$J56.OenNhKniOPgfqLCLZu7mjo08DXlxucvXrt/hn3uBQeWM.2G7.");
            manager.setName("default");
            manager.setSurname("manager");
            manager.setConfirmed(true);
            manager.setRole(Role.MANAGER);
            manager.setUserName("manager");
            manager.setScore(15D);
            manager.setCountry(new Locale("tr"));
            initialUsers.add(manager);
        }
        if (!initialUsers.isEmpty()) {
            userRepository.saveAll(initialUsers);
        }
    }
}
