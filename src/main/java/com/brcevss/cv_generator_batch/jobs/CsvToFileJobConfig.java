package com.brcevss.cv_generator_batch.jobs;

import ch.qos.logback.classic.Logger;
import com.brcevss.cv_generator_batch.jobs.writer.CvPdfWriter;
import com.brcevss.cv_generator_batch.model.Candidat;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.support.ListItemWriter;
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
        return new JobBuilder("csvToFileJob", jobRepository)
                .start(csvToFileStep())
                .build();
    }

    @Bean
    public Step csvToFileStep() {
        return new StepBuilder("csvToFileStep", jobRepository)
                .<Candidat, Candidat>chunk(10, platformTransactionManager)
                .reader(csvReader())
                .writer(cvPdfWriter)
                .build();
    }

    @Bean
    public FlatFileItemReader<Candidat> csvReader() {
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

    @Bean
    public ListItemWriter<Candidat> listCandidatWriter() {
        return new ListItemWriter<>() {
            @Override
            public void write(Chunk<? extends Candidat> items) {
                items.forEach(item -> log.info("Candidat lu : {}", item));
            }
        };
    }
}
