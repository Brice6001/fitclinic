package com.fitclinic.fitclinic.controller;




import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.fitclinic.fitclinic.repository.AppointmentRepository;
import com.fitclinic.fitclinic.repository.FeedbackRepository;
import com.fitclinic.fitclinic.repository.SessionRecordRepository;
import com.fitclinic.fitclinic.repository.UserRepository;

@Controller
@RequiredArgsConstructor
public class AdminController {

    private final UserRepository userRepository;
    private final AppointmentRepository appointmentRepository;
    private final SessionRecordRepository recordRepository;
    private final FeedbackRepository feedbackRepository;

    @GetMapping("/admin")
    public String dashboard(Model model) {
        model.addAttribute("users", userRepository.findAll());
        model.addAttribute("appointments", appointmentRepository.findAll());
        model.addAttribute("records", recordRepository.findAll());
        model.addAttribute("feedbacks", feedbackRepository.findAll());
        return "admin";
    }
}