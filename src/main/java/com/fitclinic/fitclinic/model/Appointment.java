package com.fitclinic.fitclinic.model;


import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "appointments")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    private User client;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trainer_id", nullable = false)
    private User trainer;

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    @Enumerated(EnumType.STRING)
    private Type type;

    @Enumerated(EnumType.STRING)
    private Status status = Status.SCHEDULED;

    public enum Type { PERSONAL_TRAINING, PHYSIO, NUTRITION }
    public enum Status { SCHEDULED, COMPLETED, CANCELLED }
    
    @OneToOne(mappedBy = "appointment", fetch = FetchType.LAZY)
    private SessionRecord sessionRecord;

    @OneToOne(mappedBy = "appointment", fetch = FetchType.LAZY)
    private Feedback feedback;
}