package org.rishbootdev.healthsphere.authorizationModels;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(unique = true, nullable = false)
    private String userId;

    public User(String email,String passwordHash,Role role,String userId){
        this.email=email;
        this.passwordHash=passwordHash;
        this.role=role;
        this.userId=userId;
    }
}

