package com.brcevss.cv_generator_batch.controller;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class JobController {

    private static final Logger log = LoggerFactory.getLogger(JobController.class);

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job csvToFile;

    @GetMapping("/run-job")
    public String runJob() {
        log.info("Début du lancement du job...");
        try {
            JobParameters jobParameters = new JobParametersBuilder()
                    .addLong("startTime", System.currentTimeMillis())
                    .toJobParameters();

            jobLauncher.run(csvToFile, jobParameters);

            log.info("Job lancé avec succès");
            return "Job lancé avec succès";

        } catch (Exception e) {
            log.error("Erreur lors du lancement du job : {}", e.getMessage(), e);
            return "Erreur lors du lancement du job : " + e.getMessage();
        }
    }
}
