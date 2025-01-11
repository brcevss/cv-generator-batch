package com.brcevss.cv_generator_batch.unit.jobs;

import com.brcevss.cv_generator_batch.jobs.writer.CvPdfWriter;
import com.brcevss.cv_generator_batch.model.Candidat;
import com.brcevss.cv_generator_batch.jobs.CsvToFileJobConfig;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.tasklet.TaskletStep;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.transaction.PlatformTransactionManager;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@EnableBatchProcessing
public class CsvToFileJobConfigTest {

    @InjectMocks
    private CsvToFileJobConfig csvToFileJobConfig;

    @Mock
    private JobRepository jobRepository;

    @Mock
    private PlatformTransactionManager platformTransactionManager;

    @Mock
    private CvPdfWriter cvPdfWriter;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCsvToFileJob() {
        Job job = csvToFileJobConfig.csvToFile();
        assertNotNull(job);
    }

    @Test
    public void testCsvToFileStep() {
        TaskletStep step = (TaskletStep) csvToFileJobConfig.csvToFileStep();
        assertNotNull(step);
    }

    @Test
    public void testCsvReader() {
        FlatFileItemReader<Candidat> reader = csvToFileJobConfig.csvReader();
        assertNotNull(reader);
    }
}

