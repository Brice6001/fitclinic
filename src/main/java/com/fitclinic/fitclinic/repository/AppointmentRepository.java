package com.fitclinic.fitclinic.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fitclinic.fitclinic.model.Appointment;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AppointmentRepository extends JpaRepository<Appointment, String> {
    List<Appointment> findByClientId(String clientId);
    List<Appointment> findByTrainerId(String trainerId);

    @Query("SELECT a FROM Appointment a WHERE a.trainer.id = :trainerId " +
           "AND a.status = 'SCHEDULED' " +
           "AND ((a.startTime < :end AND a.endTime > :start))")
    Optional<Appointment> findConflicting(@Param("trainerId") String trainerId,
                                          @Param("start") LocalDateTime start,
                                          @Param("end") LocalDateTime end);
}