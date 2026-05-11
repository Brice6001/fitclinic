package com.fitclinic.fitclinic.service;



import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fitclinic.fitclinic.model.Feedback;
import com.fitclinic.fitclinic.repository.FeedbackRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;

    @Transactional(readOnly = true)
    public Feedback getById(String id) {
        return feedbackRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Feedback not found"));
    }

    @Transactional(readOnly = true)
    public List<Feedback> findAll() {
        return feedbackRepository.findAll();
    }

    public Feedback submit(String feedbackId, int rating, String comments) {
        Feedback feedback = getById(feedbackId);
        feedback.setRating(rating);
        feedback.setComments(comments);
        feedback.setSubmittedAt(LocalDateTime.now());
        Feedback saved = feedbackRepository.save(feedback);

        if (rating <= 3) {
            System.out.println("MANAGER ALERT: Low rating " + rating +
                    " from client " + feedback.getClient().getFullName());
        }
        return saved;
    }

    public void deleteById(String id) {
        feedbackRepository.deleteById(id);
    }
}