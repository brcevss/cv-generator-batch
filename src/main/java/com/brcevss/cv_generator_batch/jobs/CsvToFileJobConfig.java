package com.brcevss.cv_generator_batch.jobs;

import com.brcevss.cv_generator_batch.model.Candidat;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
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

@Slf4j
@Configuration
@EnableBatchProcessing
public class CsvToFileJobConfig {

    private static final Logger log = LoggerFactory.getLogger(CsvToFileJobConfig.class);

    @Autowired
    public JobRepository jobRepository;

    @Autowired
    public PlatformTransactionManager platformTransactionManager;

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
                .writer(listCandidatWriter())
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
