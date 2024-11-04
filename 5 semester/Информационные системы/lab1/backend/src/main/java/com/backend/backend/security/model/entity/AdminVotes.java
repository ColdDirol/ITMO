package com.backend.backend.security.model.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class AdminVotes {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "voted_admins_seq_gen")
    @SequenceGenerator(
            name = "voted_admins_seq_gen",
            sequenceName = "voted_admins_seq",
            allocationSize = 1)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "request_id", nullable = false)
    private AdminRequests request;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "admin_id", nullable = false)
    private Users admin;

    @Column(name="vote", nullable = false)
    private Boolean vote;
}
