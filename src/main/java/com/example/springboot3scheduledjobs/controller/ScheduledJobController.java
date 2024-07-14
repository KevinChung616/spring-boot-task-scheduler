package com.example.springboot3scheduledjobs.controller;

import com.example.springboot3scheduledjobs.entity.ScheduledJob;
import com.example.springboot3scheduledjobs.service.ScheduledJobService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/jobs")
public class ScheduledJobController {

    @Autowired
    private ScheduledJobService scheduledJobService;

    // Get all jobs
    @GetMapping
    public List<ScheduledJob> getAllJobs() {
        return scheduledJobService.getAllJobs();
    }

    // Create a new job
    @PostMapping
    public ResponseEntity<ScheduledJob> createJob(@RequestBody ScheduledJob scheduledJob) {
        ScheduledJob createdJob = scheduledJobService.createScheduledJob(scheduledJob);
        return ResponseEntity.ok(createdJob);
    }

    // Update an existing job
    @PutMapping("/{jobId}")
    public ResponseEntity<ScheduledJob> updateJob(@PathVariable Long jobId, @RequestBody ScheduledJob scheduledJob) {
        Optional<ScheduledJob> updatedJob = scheduledJobService.updateScheduledJob(jobId, scheduledJob);
        return updatedJob.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Delete a job
    @DeleteMapping("/{jobId}")
    public ResponseEntity<Void> deleteJob(@PathVariable Long jobId) {
        boolean isDeleted = scheduledJobService.deleteScheduledJob(jobId);
        if (isDeleted) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
