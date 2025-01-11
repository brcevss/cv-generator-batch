package com.brcevss.cv_generator_batch.jobs;

import ch.qos.logback.classic.Logger;
import com.brcevss.cv_generator_batch.jobs.writer.CvPdfWriter;
import com.brcevss.cv_generator_batch.model.Candidat;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableBatchProcessing
public class CsvToFileJobConfig {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private PlatformTransactionManager platformTransactionManager;

    @Autowired
    private CvPdfWriter cvPdfWriter;

    private static final Logger log = (Logger) LoggerFactory.getLogger(CsvToFileJobConfig.class);

    @Bean
    public Job csvToFile() {
        log.info("Initialisation du job csvToFile...");
        return new JobBuilder("csvToFileJob", jobRepository)
                .start(csvToFileStep())
                .listener(new JobCompletionNotificationListener())
                .build();
    }

    @Bean
    public Step csvToFileStep() {
        log.info("Initialisation du step csvToFileStep...");
        return new StepBuilder("csvToFileStep", jobRepository)
                .<Candidat, Candidat>chunk(10, platformTransactionManager)
                .reader(csvReader())
                .writer(cvPdfWriter)
                .faultTolerant()
                .skip(Exception.class)
                .skipLimit(10)
                .listener(new ChunkListener() {
                    @Override
                    public void beforeChunk(ChunkContext context) {
                        log.info("Début du traitement d'un chunk.");
                    }

                    @Override
                    public void afterChunk(ChunkContext context) {
                        log.info("Fin du traitement d'un chunk.");
                    }

                    @Override
                    public void afterChunkError(ChunkContext context) {
                        log.error("Erreur lors du traitement d'un chunk.");
                    }
                })
                .listener(new ItemReadListener<Candidat>() {
                    @Override
                    public void beforeRead() {
                        log.info("Début de la lecture d'un item.");
                    }

                    @Override
                    public void afterRead(Candidat item) {
                        log.info("Fin de la lecture d'un item: {}", item);
                    }

                    @Override
                    public void onReadError(Exception ex) {
                        log.error("Erreur lors de la lecture d'un item: {}", ex.getMessage());
                    }
                })
                .listener(new ItemWriteListener<Candidat>() {
                    @Override
                    public void beforeWrite(Chunk<? extends Candidat> items) {
                        log.info("Début de l'écriture des items.");
                    }

                    @Override
                    public void afterWrite(Chunk<? extends Candidat> items) {
                        log.info("Fin de l'écriture des items.");
                    }

                    @Override
                    public void onWriteError(Exception ex, Chunk<? extends Candidat> items) {
                        log.error("Erreur lors de l'écriture des items: " + ex.getMessage());
                    }
                })
                .build();
    }

    @Bean
    public FlatFileItemReader<Candidat> csvReader() {
        log.info("Initialisation du reader csvReader...");
        return new FlatFileItemReaderBuilder<Candidat>()
                .name("csvReader")
                .linesToSkip(1)
                .resource(new ClassPathResource("csv/candidats.csv"))
                .delimited()
                .names("id", "prenom", "nom", "email", "competences")
                .fieldSetMapper(new BeanWrapperFieldSetMapper<>() {{
                    setTargetType(Candidat.class);
                }})
                .build();
    }

    public static class JobCompletionNotificationListener implements JobExecutionListener {
        private static final Logger log = (Logger) LoggerFactory.getLogger(JobCompletionNotificationListener.class);

        @Override
        public void beforeJob(JobExecution jobExecution) {
            log.info("Début du job: " + jobExecution.getJobInstance().getJobName());
        }

        @Override
        public void afterJob(JobExecution jobExecution) {
            if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
                log.info("Job terminé avec succès: {}", jobExecution.getJobInstance().getJobName());
            } else {
                log.error("Job terminé avec des erreurs: {}", jobExecution.getJobInstance().getJobName());
            }
        }
    }
}

