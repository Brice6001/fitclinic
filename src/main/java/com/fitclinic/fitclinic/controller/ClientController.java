package com.fitclinic.fitclinic.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.fitclinic.fitclinic.model.*;

import com.fitclinic.fitclinic.repository.*;
import java.util.List;

@Controller
@RequestMapping("/client")
@RequiredArgsConstructor
public class ClientController {

    private final UserRepository userRepository;
    private final AppointmentRepository appointmentRepository;
    private final SessionRecordRepository recordRepository;
    private final FeedbackRepository feedbackRepository;

    // Show login form
    @GetMapping("/login")
    public String loginForm() {
        return "client-login";
    }

    // Process login (by email)
    @PostMapping("/login")
    public String login(@RequestParam String email) {
        User client = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Client not found"));
        if (client.getRole() != User.Role.CLIENT) {
            throw new IllegalArgumentException("Access denied – staff must use the admin panel");
        }
        return "redirect:/client/" + client.getId() + "/dashboard";
    }

    // Client dashboard
    @GetMapping("/{id}/dashboard")
    public String dashboard(@PathVariable String id, Model model) {
        User client = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Client not found"));
        List<Appointment> appointments = appointmentRepository.findByClientId(id);

        model.addAttribute("client", client);
        model.addAttribute("appointments", appointments);
        return "client-dashboard";
    }
}