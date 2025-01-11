package com.brcevss.cv_generator_batch.unit.controller;

import com.brcevss.cv_generator_batch.controller.JobController;

import org.junit.jupiter.api.BeforeEach;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.launch.JobLauncher;


public class JobControllerTest {

    @InjectMocks
    private JobController jobController;

    @Mock
    private JobLauncher jobLauncher;

    @Mock
    private Job csvToFile;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

/*    @Test
    public void testRunJob() throws Exception {
        when(jobLauncher.run(any(Job.class), any(JobParameters.class))).thenReturn(null);

        String result = jobController.runJob();
        assertEquals("Job lancé avec succès", result);

        verify(jobLauncher, times(1)).run(any(Job.class), any(JobParameters.class));
    }

    @Test
    public void testRunJobWithException() throws Exception {
        when(jobLauncher.run(any(Job.class), any(JobParameters.class))).thenThrow(new RuntimeException("Test exception"));

        String result = jobController.runJob();
        assertEquals("Erreur lors du lancement du job : Test exception", result);

        verify(jobLauncher, times(1)).run(any(Job.class), any(JobParameters.class));
    }*/
}

