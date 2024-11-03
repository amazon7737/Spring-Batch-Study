package org.schooldevops.springbatch.sample.jobs.chunk;

import lombok.extern.slf4j.Slf4j;
import org.schooldevops.springbatch.sample.economic.CompositeIndex;
import org.schooldevops.springbatch.sample.economic.IndexResult;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@Configuration
public class CompositeIndexJobConfig {

    public static final int CHUNK_SIZE = 100;
    public static final String READ_ENCODING = "EUC-KR";
    public static final String WRITE_ENCODING = "UTF-8";
    public static final String COMPOSITE_INDEX_CHUNK_JOB = "COMPOSITE_INDEX_CHUNK_JOB";

    @Autowired
    @Qualifier("rateCalcItemProcessor")
    private ItemProcessor<CompositeIndex, IndexResult> rateCalcItemProcessor;

    private String headerStr = "";

    @Bean
    public FlatFileItemReader<CompositeIndex> flatFileItemReader(){
        return new FlatFileItemReaderBuilder<CompositeIndex>()
                .name("FlatFileItemReader")
                .resource(new ClassPathResource("./경기종합지수_2020100__구성지표_시계열__10차__20241022145538.csv"))
                .encoding(READ_ENCODING)
                .linesToSkip(1) // 1줄 건너뛰기 (키 값 이름에서 오류)
                .skippedLinesCallback(it -> it.lines().forEach(header -> headerStr = header))
                .delimited().delimiter(",")
                .names("name", "month_3", "month_4", "month_5", "month_6", "month_7", "month_8")
                .targetType(CompositeIndex.class)
                .build();
    }

    @Bean
    public FlatFileItemWriter<IndexResult> flatFileItemWriter(){
        return new FlatFileItemWriterBuilder<IndexResult>()
                .name("flatFileItemWriter")
                .resource(new FileSystemResource("./output/result.csv"))
                .encoding(WRITE_ENCODING)
                .delimited().delimiter(",")
                .names("name", "month_3", "month_4", "month_5", "month_6", "month_7", "month_8")
                .append(false)
                .headerCallback(it -> it.write(headerStr))
                .build();
    }

    @Bean
    public Step flatFileStep(JobRepository jobRepository, PlatformTransactionManager transactionManager){
        log.info("-------------- Init flatFileStep ----------------");

        return new StepBuilder("flatFileStep", jobRepository)
                .<CompositeIndex, IndexResult>chunk(CHUNK_SIZE, transactionManager)
                .reader(flatFileItemReader())
                .processor(rateCalcItemProcessor)
                .writer(flatFileItemWriter())
                .build();
    }

    @Bean
    public Job flatFileJob(Step flatFileStep, JobRepository jobRepository){
        log.info("------------ Init flatFileJob ---------------");

        return new JobBuilder(COMPOSITE_INDEX_CHUNK_JOB, jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(flatFileStep)
                .build();
    }


}
