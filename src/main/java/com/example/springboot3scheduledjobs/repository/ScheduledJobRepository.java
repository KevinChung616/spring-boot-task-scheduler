package com.example.springboot3scheduledjobs.repository;

import com.example.springboot3scheduledjobs.entity.ScheduledJob;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduledJobRepository extends JpaRepository<ScheduledJob, Long> {
    List<ScheduledJob> findByActive(boolean active);
}
