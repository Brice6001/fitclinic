package com.fitclinic.fitclinic.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fitclinic.fitclinic.model.Feedback;

public interface FeedbackRepository extends JpaRepository<Feedback, String> {
    Optional<Feedback> findByAppointmentId(String appointmentId);
}