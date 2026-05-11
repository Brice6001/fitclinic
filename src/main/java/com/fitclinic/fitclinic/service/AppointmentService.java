package com.fitclinic.fitclinic.service;



import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fitclinic.fitclinic.model.Appointment;
import com.fitclinic.fitclinic.model.User;
import com.fitclinic.fitclinic.repository.AppointmentRepository;
import com.fitclinic.fitclinic.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;

    public Appointment book(String clientId, String trainerId,
                            LocalDateTime start, LocalDateTime end,
                            Appointment.Type type) {
        User client = userRepository.findById(clientId).orElseThrow();
        User trainer = userRepository.findById(trainerId).orElseThrow();

        if (appointmentRepository.findConflicting(trainerId, start, end).isPresent()) {
            throw new IllegalStateException("Trainer is busy at this time");
        }

        Appointment appointment = Appointment.builder()
                .client(client)
                .trainer(trainer)
                .startTime(start)
                .endTime(end)
                .type(type)
                .status(Appointment.Status.SCHEDULED)
                .build();
        return appointmentRepository.save(appointment);
    }

    @Transactional(readOnly = true)
    public List<Appointment> findAll() {
        return appointmentRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Appointment getById(String id) {
        return appointmentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Appointment not found"));
    }

    @Transactional(readOnly = true)
    public List<Appointment> getByClient(String clientId) {
        return appointmentRepository.findByClientId(clientId);
    }

    @Transactional(readOnly = true)
    public List<Appointment> getByTrainer(String trainerId) {
        return appointmentRepository.findByTrainerId(trainerId);
    }

    public Appointment update(String id, String clientId, String staffId, String typeStr,
                              LocalDateTime start, LocalDateTime end) {
        Appointment appt = getById(id);
        appt.setClient(userRepository.findById(clientId).orElseThrow());
        appt.setTrainer(userRepository.findById(staffId).orElseThrow());
        appt.setType(Appointment.Type.valueOf(typeStr));
        appt.setStartTime(start);
        appt.setEndTime(end);
        return appointmentRepository.save(appt);
    }

    public void deleteById(String id) {
        appointmentRepository.deleteById(id);
    }
}