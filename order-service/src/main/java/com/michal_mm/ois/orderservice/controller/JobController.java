package com.michal_mm.ois.orderservice.controller;

import com.michal_mm.ois.orderservice.service.OrderService;
import org.jobrunr.jobs.JobId;
import org.jobrunr.scheduling.JobScheduler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.random.RandomGenerator;

import static java.time.Instant.now;

@RestController()
@RequestMapping("/jobs")
public class JobController {

    private final OrderService orderService;
    private final JobScheduler jobScheduler;


    public JobController(OrderService orderService, JobScheduler jobScheduler) {
        this.orderService = orderService;
        this.jobScheduler = jobScheduler;
    }

    @GetMapping("/{aJob}")
    public String scheduleJob(@PathVariable String aJob) throws InterruptedException {
        if (aJob != null && aJob.equals("no-schedule")){
            orderService.doSimpleJob("NOT-SCHEDULED");
            return "no-op";
        } else {
        JobId scheduledJobId = jobScheduler.schedule(now().plusSeconds(20),
                () -> orderService.doSimpleJob(aJob));

        return "Scheduled a job [" + aJob + "] + " + scheduledJobId.toString();
        }
    }

    @GetMapping
    public String scheduleJobWithRandomName() {
        String name = "TEST-JOB-" + String.format(""+ RandomGenerator.getDefault().nextInt(100_000), "%6d");

        JobId scheduledJobId = jobScheduler.schedule(now().plusSeconds(20),
                () -> orderService.doSimpleJob(name));

        return "RANDOM Scheduled a job [" + name + "] + " + scheduledJobId.toString();
    }
}
