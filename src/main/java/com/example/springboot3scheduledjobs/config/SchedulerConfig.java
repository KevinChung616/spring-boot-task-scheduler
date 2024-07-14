package com.example.springboot3scheduledjobs.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Configuration
public class SchedulerConfig {
    @Bean
    public TaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(10); // Set the number of threads in the pool
        scheduler.setThreadNamePrefix("task-scheduler-"); // Set the prefix for thread names
        scheduler.initialize();
        return scheduler;
    }
}
