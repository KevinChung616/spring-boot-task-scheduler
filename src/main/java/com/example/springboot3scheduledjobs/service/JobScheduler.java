package com.example.springboot3scheduledjobs.service;

import com.example.springboot3scheduledjobs.entity.ScheduledJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.concurrent.ScheduledFuture;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
public class JobScheduler {

    @Autowired
    private TaskScheduler taskScheduler;

    // Map to store the scheduled tasks with job IDs as keys
    private Map<Long, ScheduledFuture<?>> scheduledTasks = new ConcurrentHashMap<>();

    // Method to schedule a job
    public void scheduleJob(ScheduledJob job) {
        if (job.isActive()) {
            Runnable task = () -> sendNotification(job);
            // Schedule the task using the cron expression
            ScheduledFuture<?> scheduledTask = taskScheduler.schedule(task, new CronTrigger(job.getCronExpression()));
            // Store the scheduled task in the map
            scheduledTasks.put(job.getId(), scheduledTask);
        }
    }

    // Method to unschedule a job
    public void unscheduleJob(ScheduledJob job) {
        // Retrieve the scheduled task from the map
        ScheduledFuture<?> scheduledTask = scheduledTasks.get(job.getId());
        if (scheduledTask != null) {
            // Cancel the scheduled task
            scheduledTask.cancel(false);
            // Remove the task from the map
            scheduledTasks.remove(job.getId());
        }
    }

    private void sendNotification(ScheduledJob job) {
        // Implement your notification logic here
        log.info("Sending notification: {}", job.getMessage());
    }
}
