package com.dailycodework.dream_shops.data;

//import com.dailycodework.dream_shops.model.Role;
import com.dailycodework.dream_shops.model.User;
import com.dailycodework.dream_shops.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Transactional
@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationListener<ApplicationReadyEvent> {
    private final UserRepository userRepository;
   // private final RoleRepository roleRepository;
   // private final PasswordEncoder passwordEncoder;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
    // Set<String> defaultRoles =  Set.of("ROLE_ADMIN", "ROLE_USER");
      createDefaultUserIfNotExits();
        //createDefaultRoleIfNotExits(defaultRoles);
       //createDefaultAdminIfNotExits();
    }


    private void createDefaultUserIfNotExits(){
        //Role userRole = roleRepository.findByName("ROLE_USER").get();
        for (int i = 1; i<=5; i++){
            String defaultEmail = "user"+i+"@email.com";
            if (userRepository.existsByEmail(defaultEmail)){
                continue;
            }
            User user = new User();
            user.setFirstName("The User");
            user.setLastName("User" + i);
            user.setEmail(defaultEmail);
            user.setPassword("123456");
            //user.setRoles(Set.of(userRole));
            userRepository.save(user);
            System.out.println("Default vet user " + i + " created successfully.");
        }
    }

}