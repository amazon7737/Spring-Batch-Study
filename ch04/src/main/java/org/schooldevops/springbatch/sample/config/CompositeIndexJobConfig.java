package org.schooldevops.springbatch.sample.config;

import lombok.extern.slf4j.Slf4j;
import org.schooldevops.springbatch.sample.domain.CompositeIndex;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@Configuration
public class CompositeIndexJobConfig {

    public static final int CHUNK_SIZE = 100;
    public static final String ENCODING = "EUC-KR";
    public static final String FLAT_FILE_CHUNK_JOB = "FLAT_FILE_CHUNK_JOB";

    @Bean
    public FlatFileItemReader<CompositeIndex> flatFileItemReader(){
        return new FlatFileItemReaderBuilder<CompositeIndex>()
                .name("FlatFileItemReader")
                .resource(new ClassPathResource("./경기종합지수_2020100__구성지표_시계열__10차__20241022145538.csv"))
                .encoding(ENCODING)
                .delimited().delimiter(",")
                .names("index", "number")
                .targetType(CompositeIndex.class)
                .build();
    }

    @Bean
    public FlatFileItemWriter<CompositeIndex> flatFileItemWriter(){
        return new FlatFileItemWriterBuilder<CompositeIndex>()
                .name("flatFileItemWriter")
                .resource(new FileSystemResource("./output/result.csv"))
                .encoding(ENCODING)
                .delimited().delimiter("\t")
                .names("", "")
                .build();
    }

    @Bean
    public Step flatFileStep(JobRepository jobRepository, PlatformTransactionManager transactionManager){
        log.info("-------------- Init flatFileStep ----------------");

        return new StepBuilder("flatFileStep", jobRepository)
                .<CompositeIndex, CompositeIndex>chunk(CHUNK_SIZE, transactionManager)
                .reader(flatFileItemReader())
                .writer(flatFileItemWriter())
                .build();
    }

    @Bean
    public Job flatFileJob(Step flatFileStep, JobRepository jobRepository){
        log.info("------------ Init flatFileJob ---------------");

        return new JobBuilder(FLAT_FILE_CHUNK_JOB, jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(flatFileStep)
                .build();
    }


}
