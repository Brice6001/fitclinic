package com.fitclinic.fitclinic.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.fitclinic.fitclinic.service.RecordService;

@Controller
@RequestMapping("/records")
@RequiredArgsConstructor
public class RecordController {

    private final RecordService recordService;

    // Create form
    @GetMapping("/create/{appointmentId}")
    public String showRecordForm(@PathVariable String appointmentId, Model model) {
        model.addAttribute("appointmentId", appointmentId);
        return "record-form";
    }

    @PostMapping("/create")
    public String create(@RequestParam String appointmentId,
                         @RequestParam String subjective,
                         @RequestParam String objective,
                         @RequestParam String assessment,
                         @RequestParam String plan) {
        recordService.create(appointmentId, subjective, objective, assessment, plan);
        return "redirect:/records";
    }

    // List all records
    @GetMapping
    public String listAll(Model model) {
        model.addAttribute("records", recordService.findAll());
        return "records-list";
    }

    // View one
    @GetMapping("/{id}")
    public String view(@PathVariable String id, Model model) {
        model.addAttribute("record", recordService.getById(id));
        return "record-view";   // optional, we can reuse edit form as view
    }

    // Edit form
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable String id, Model model) {
        model.addAttribute("record", recordService.getById(id));
        return "record-edit";
    }

    // Update record
    @PostMapping("/{id}/edit")
    public String update(@PathVariable String id,
                         @RequestParam String subjective,
                         @RequestParam String objective,
                         @RequestParam String assessment,
                         @RequestParam String plan) {
        recordService.update(id, subjective, objective, assessment, plan);
        return "redirect:/records";
    }

    // Delete record
    @GetMapping("/{id}/delete")
    public String delete(@PathVariable String id) {
        recordService.deleteById(id);
        return "redirect:/records";
    }
}