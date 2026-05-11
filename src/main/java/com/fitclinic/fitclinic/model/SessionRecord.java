package com.fitclinic.fitclinic.model;



import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "session_records")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SessionRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appointment_id", unique = true, nullable = false)
    private Appointment appointment;

    @Column(length = 2000)
    private String subjective;
    @Column(length = 2000)
    private String objective;
    @Column(length = 2000)
    private String assessment;
    @Column(length = 2000)
    private String plan;

    private LocalDateTime createdAt;

    @PrePersist
    void onCreate() {
        createdAt = LocalDateTime.now();
    }
}