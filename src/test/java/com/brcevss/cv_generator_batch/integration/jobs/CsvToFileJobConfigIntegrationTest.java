package com.brcevss.cv_generator_batch.integration.jobs;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;


@SpringBootTest
@SpringJUnitConfig
public class CsvToFileJobConfigIntegrationTest {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job csvToFile;

/*    @Test
    public void testCsvToFileJob() throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("startTime", System.currentTimeMillis())
                .toJobParameters();

        jobLauncher.run(csvToFile, jobParameters);
    }*/
}
