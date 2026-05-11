package com.fitclinic.fitclinic.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.fitclinic.fitclinic.model.Appointment;
import com.fitclinic.fitclinic.service.AppointmentService;
import com.fitclinic.fitclinic.service.UserService;
import com.fitclinic.fitclinic.model.User;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;
    private final UserService userService;

    // Book form
    @GetMapping("/book")
    public String showBookForm(Model model) {
        model.addAttribute("clients", userService.findByRole(User.Role.CLIENT));
        model.addAttribute("trainers", userService.findByRole(User.Role.TRAINER));
        model.addAttribute("physios", userService.findByRole(User.Role.PHYSIOTHERAPIST));
        return "book";
    }

    @PostMapping("/book")
    public String book(@RequestParam String clientId,
                       @RequestParam String staffId,
                       @RequestParam String type,
                       @RequestParam LocalDateTime start,
                       @RequestParam LocalDateTime end) {
        Appointment.Type apptType = Appointment.Type.valueOf(type);
        appointmentService.book(clientId, staffId, start, end, apptType);
        return "redirect:/appointments";
    }

    // List all appointments
    @GetMapping
    public String listAll(Model model) {
        model.addAttribute("appointments", appointmentService.findAll());
        return "appointments-list";
    }

    // Appointments for a client
    @GetMapping("/client/{clientId}")
    public String clientAppointments(@PathVariable String clientId, Model model) {
        model.addAttribute("appointments", appointmentService.getByClient(clientId));
        return "appointments";
    }

    // Edit form
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable String id, Model model) {
        Appointment appt = appointmentService.getById(id);
        model.addAttribute("appointment", appt);
        model.addAttribute("clients", userService.findByRole(User.Role.CLIENT));
        model.addAttribute("trainers", userService.findByRole(User.Role.TRAINER));
        model.addAttribute("physios", userService.findByRole(User.Role.PHYSIOTHERAPIST));
        return "appointment-edit";
    }

    // Update
    @PostMapping("/{id}/edit")
    public String update(@PathVariable String id,
                         @RequestParam String clientId,
                         @RequestParam String staffId,
                         @RequestParam String type,
                         @RequestParam LocalDateTime start,
                         @RequestParam LocalDateTime end) {
        appointmentService.update(id, clientId, staffId, type, start, end);
        return "redirect:/appointments";
    }

    // Delete
    @GetMapping("/{id}/delete")
    public String delete(@PathVariable String id) {
        appointmentService.deleteById(id);
        return "redirect:/appointments";
    }
}