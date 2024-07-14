package com.example.springboot3scheduledjobs.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Entity
@Table(name = "scheduled_jobs")
@Data
@ToString
public class ScheduledJob {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private String cronExpression;

    private String message;

    private boolean active;

}
