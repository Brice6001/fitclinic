package com.fitclinic.fitclinic.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fitclinic.fitclinic.model.User;
import com.fitclinic.fitclinic.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;

    public User register(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already in use");
        }
        user.setRole(User.Role.CLIENT);
        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public User getById(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<User> findByRole(User.Role role) {
        return userRepository.findAll().stream()
                .filter(u -> u.getRole() == role)
                .toList();
    }

    public User update(String id, User updated) {
        User existing = getById(id);
        existing.setFullName(updated.getFullName());
        existing.setEmergencyContact(updated.getEmergencyContact());
        existing.setGoals(updated.getGoals());
        existing.setMedicalNotes(updated.getMedicalNotes());
        existing.setMembershipTier(updated.getMembershipTier());
        return userRepository.save(existing);
    }

    public void deleteById(String id) {
        userRepository.deleteById(id);
    }
}