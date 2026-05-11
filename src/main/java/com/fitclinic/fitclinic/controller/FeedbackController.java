package com.fitclinic.fitclinic.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.fitclinic.fitclinic.service.FeedbackService;

@Controller
@RequestMapping("/feedback")
@RequiredArgsConstructor
public class FeedbackController {

    private final FeedbackService feedbackService;

    // List all feedback
    @GetMapping
    public String listAll(Model model) {
        model.addAttribute("feedbacks", feedbackService.findAll());
        return "feedback-list";
    }

    // Submit feedback form (for a specific feedback entry)
    @GetMapping("/{feedbackId}")
    public String showForm(@PathVariable String feedbackId, Model model) {
        model.addAttribute("feedback", feedbackService.getById(feedbackId));
        return "feedback-form";
    }

    @PostMapping("/{feedbackId}")
    public String submit(@PathVariable String feedbackId,
                         @RequestParam int rating,
                         @RequestParam String comments) {
        feedbackService.submit(feedbackId, rating, comments);
        return "redirect:/feedback";
    }

    // Delete feedback
    @GetMapping("/{id}/delete")
    public String delete(@PathVariable String id) {
        feedbackService.deleteById(id);
        return "redirect:/feedback";
    }
}