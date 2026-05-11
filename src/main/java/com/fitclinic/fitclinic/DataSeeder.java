package com.fitclinic.fitclinic;


import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.fitclinic.fitclinic.model.User;
import com.fitclinic.fitclinic.repository.UserRepository;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;

    @Override
    public void run(String... args) {
        if (userRepository.count() == 0) {
            User trainer1 = User.builder()
                    .email("alex@fitclinic.com")
                    .password("pass")
                    .fullName("Alex Trainer")
                    .role(User.Role.TRAINER)
                    .build();
            User physio1 = User.builder()
                    .email("jordan@fitclinic.com")
                    .password("pass")
                    .fullName("Jordan Physio")
                    .role(User.Role.PHYSIOTHERAPIST)
                    .build();
            User client = User.builder()
                    .email("jane@example.com")
                    .password("pass")
                    .fullName("Jane Client")
                    .role(User.Role.CLIENT)
                    .build();
            userRepository.saveAll(List.of(trainer1, physio1, client));
        }
    }
}