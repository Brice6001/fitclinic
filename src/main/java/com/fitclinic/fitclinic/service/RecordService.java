package com.fitclinic.fitclinic.service;



import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fitclinic.fitclinic.model.Appointment;
import com.fitclinic.fitclinic.model.Feedback;
import com.fitclinic.fitclinic.model.SessionRecord;
import com.fitclinic.fitclinic.repository.AppointmentRepository;
import com.fitclinic.fitclinic.repository.FeedbackRepository;
import com.fitclinic.fitclinic.repository.SessionRecordRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class RecordService {

    private final SessionRecordRepository recordRepository;
    private final AppointmentRepository appointmentRepository;
    private final FeedbackRepository feedbackRepository;

    public SessionRecord create(String appointmentId, String subjective,
                                String objective, String assessment, String plan) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new IllegalArgumentException("Appointment not found"));

        if (recordRepository.findByAppointmentId(appointmentId).isPresent()) {
            throw new IllegalStateException("Record already exists");
        }

        SessionRecord record = SessionRecord.builder()
                .appointment(appointment)
                .subjective(subjective)
                .objective(objective)
                .assessment(assessment)
                .plan(plan)
                .build();
        record = recordRepository.save(record);

        // Auto‑complete appointment and create empty feedback
        appointment.setStatus(Appointment.Status.COMPLETED);
        appointmentRepository.save(appointment);

        Feedback feedback = Feedback.builder()
                .appointment(appointment)
                .client(appointment.getClient())
                .rating(0)
                .comments("")
                .build();
        feedbackRepository.save(feedback);

        return record;
    }

    @Transactional(readOnly = true)
    public List<SessionRecord> findAll() {
        return recordRepository.findAll();
    }

    @Transactional(readOnly = true)
    public SessionRecord getById(String id) {
        return recordRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Record not found"));
    }

    public SessionRecord update(String id, String subjective, String objective,
                                String assessment, String plan) {
        SessionRecord rec = getById(id);
        rec.setSubjective(subjective);
        rec.setObjective(objective);
        rec.setAssessment(assessment);
        rec.setPlan(plan);
        return recordRepository.save(rec);
    }

    public void deleteById(String id) {
        recordRepository.deleteById(id);
    }
}