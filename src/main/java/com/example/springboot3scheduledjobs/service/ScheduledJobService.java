package com.example.springboot3scheduledjobs.service;

import com.example.springboot3scheduledjobs.entity.ScheduledJob;
import com.example.springboot3scheduledjobs.repository.ScheduledJobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ScheduledJobService {

    private final ScheduledJobRepository scheduledJobRepository;

    private final JobScheduler jobScheduler;

    @Autowired
    public ScheduledJobService(ScheduledJobRepository scheduledJobRepository, JobScheduler jobScheduler) {
        this.scheduledJobRepository = scheduledJobRepository;
        this.jobScheduler = jobScheduler;
    }

    public ScheduledJob createScheduledJob(ScheduledJob scheduledJob) {
        scheduledJob.setActive(true); // Set the job to active by default
        ScheduledJob savedJob = scheduledJobRepository.save(scheduledJob);
        jobScheduler.scheduleJob(savedJob); // Schedule the job immediately
        return savedJob;
    }

    public Optional<ScheduledJob> updateScheduledJob(Long jobId, ScheduledJob scheduledJob) {
        Optional<ScheduledJob> optionalJob = scheduledJobRepository.findById(jobId);
        if (optionalJob.isPresent()) {
            ScheduledJob existingJob = optionalJob.get();
            existingJob.setCronExpression(scheduledJob.getCronExpression());
            existingJob.setMessage(scheduledJob.getMessage());
            existingJob.setActive(scheduledJob.isActive());
            ScheduledJob savedJob = scheduledJobRepository.save(existingJob);

            // Reschedule the job based on the new active status and cron expression
            jobScheduler.unscheduleJob(existingJob);
            if (existingJob.isActive()) {
                jobScheduler.scheduleJob(existingJob);
            }

            return Optional.of(savedJob);
        }
        return Optional.empty();
    }

    public List<ScheduledJob> getAllJobs() {
        return scheduledJobRepository.findAll();
    }

    public boolean deleteScheduledJob(Long jobId) {
        Optional<ScheduledJob> optionalJob = scheduledJobRepository.findById(jobId);
        if (optionalJob.isPresent()) {
            ScheduledJob job = optionalJob.get();
            jobScheduler.unscheduleJob(job); // Unschedule the job
            scheduledJobRepository.deleteById(jobId); // Delete the job
            return true;
        }
        return false;
    }


}
