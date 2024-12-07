package com.backend.backend.security.model.entity;

import com.backend.backend.security.model.enumeration.UserRoleEnum;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Entity
public class AdminRequests {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "admin_requests_seq_gen")
    @SequenceGenerator(
            name = "admin_requests_seq_gen",
            sequenceName = "admin_requests_seq",
            allocationSize = 1)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @Column(name = "request_date", nullable = false)
    private Date requestDate;

    @Column(name = "is_approved", nullable = true)
    private Boolean isApproved = null;
}
