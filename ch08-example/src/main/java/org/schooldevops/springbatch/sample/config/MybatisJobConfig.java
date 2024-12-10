package org.schooldevops.springbatch.sample.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.batch.MyBatisBatchItemWriter;
import org.mybatis.spring.batch.MyBatisPagingItemReader;
import org.mybatis.spring.batch.builder.MyBatisBatchItemWriterBuilder;
import org.mybatis.spring.batch.builder.MyBatisPagingItemReaderBuilder;
import org.schooldevops.springbatch.sample.domain.Customer;
import org.schooldevops.springbatch.sample.domain.Student;
import org.schooldevops.springbatch.sample.jobs.After20YearsItemProcessor;
import org.schooldevops.springbatch.sample.jobs.LowerCaseItemProcessor;
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
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.batch.item.support.builder.CompositeItemProcessorBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.List;

@Slf4j
@Configuration
public class MybatisJobConfig {
    public static final int CHUNK_SIZE = 2;
    public static final String ENCODING = "UTF-8";
    public static final String MYBATIS_CHUNK_JOB = "MYBATIS_CHUNK_JOB";

    @Autowired
    DataSource dataSource;

    @Autowired
    SqlSessionFactory sqlSessionFactory;

    @Bean
    public MyBatisPagingItemReader<Student> studentMyBatisItemReader() throws Exception {
        return new MyBatisPagingItemReaderBuilder<Student>()
                .sqlSessionFactory(sqlSessionFactory)
                .pageSize(CHUNK_SIZE)
                .queryId("org.schooldevops.springbatch.sample.jobs.selectStudents")
                .build();
    }

    @Bean
    public FlatFileItemWriter<Student> cursorFlatFileWriter() {
        return new FlatFileItemWriterBuilder<Student>()
                .name("cursorFlatFileItemWriter")
                .resource(new FileSystemResource("./output/student_new.csv"))
                .encoding(ENCODING)
                .delimited().delimiter("\t")
                .names("StudentId", "Name", "Department")
                .build();
    }

//    @Bean
//    public CompositeItemProcessor<Student, Student> compositeItemProcessor() {
//        return new CompositeItemProcessorBuilder<Customer, Customer>()
//                .delegates(List.of(
//                        new LowerCaseItemProcessor(),
//                        new After20YearsItemProcessor()
//                ))
//                .build();
//    }

    @Bean
    public Step jdbcCursorStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) throws Exception {
        log.info("----------- Init customerJdbcCursorStep ------------");
        return new StepBuilder("customerJdbcCursorStep", jobRepository)
                .<Student, Student>chunk(CHUNK_SIZE, transactionManager)
                .reader(studentMyBatisItemReader())
//                .processor(compositeItemProcessor())
                .writer(cursorFlatFileWriter())
                .build();
    }

    // student csv 파일을 읽어오는 ItemReader
    @Bean
    public FlatFileItemReader<Student> flatFileItemReader() {
        return new FlatFileItemReaderBuilder<Student>()
                .name("studentReader")
                .resource(new ClassPathResource("./student.csv"))
                .encoding(ENCODING)
                .delimited().delimiter(",")
                .names("studentId", "name", "department")
                .targetType(Student.class)
                .build();
    }

//    @Bean
//    public Job jdbcCursorPagingJob(Step studentJdbcCursorStep, JobRepository jobRepository) {
//        log.info("------------ Init customerJdbcCursorPagingJob ------------");
//        return new JobBuilder(MYBATIS_CHUNK_JOB, jobRepository)
//                .incrementer(new RunIdIncrementer())
//                .start(studentJdbcCursorStep)
//                .build();
//    }

//    @Bean
//    public MyBatisBatchItemWriter<Student> task07mybatisItemWriter() {
//        return new MyBatisBatchItemWriterBuilder<Customer>()
//                .sqlSessionFactory(sqlSessionFactory)
//                .statementId("insertStudents")
//                .build();
//    }
}
