package com.fitclinic.fitclinic.model;



import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(unique = true, nullable = false)
    private String email;

    private String password;          // plain for now
    private String fullName;

    @Enumerated(EnumType.STRING)
    private Role role;

    // Profile fields merged
    private String emergencyContact;
    private String goals;
    @Column(length = 1000)
    private String medicalNotes;
    private String membershipTier = "BASIC";
    private int loyaltyPoints = 0;

    private LocalDateTime createdAt;

    @PrePersist
    void onCreate() {
        createdAt = LocalDateTime.now();
    }

    public enum Role {
        CLIENT, TRAINER, PHYSIOTHERAPIST, ADMIN
    }
}