package com.example.demo.bootstrap;

import com.example.demo.entity.Entry;
import com.example.demo.entity.User;
import com.example.demo.repository.EntryRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class InitData implements CommandLineRunner {

    private final UserRepository userRepository;
    private final EntryRepository entryRepository;
    private final PasswordEncoder passwordEncoder;

    public InitData(UserRepository userRepository, EntryRepository entryRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.entryRepository = entryRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        for (int i = 1; i <= 5; i++) {
            User user = new User();
            user.setUsername("user" + i);
            user.setEmail("user" + i + "@example.com");
            user.setPassword(passwordEncoder.encode("password" + i));

            userRepository.save(user);
            for (int j = 1; j <= 5; j++) {
                Entry entry = new Entry();
                entry.setTitle("Title " + j + " for User " + i);
                entry.setContent("This is content for Entry " + j + " of User " + i);
                entry.setSummary("Summary for Entry " + j);
                entry.setOwner(user);

                entryRepository.save(entry);
            }
        }
    }
}
