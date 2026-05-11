package com.fitclinic.fitclinic.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fitclinic.fitclinic.model.SessionRecord;

public interface SessionRecordRepository extends JpaRepository<SessionRecord, String> {
    Optional<SessionRecord> findByAppointmentId(String appointmentId);
}

