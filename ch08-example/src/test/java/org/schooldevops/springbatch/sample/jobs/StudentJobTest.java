package org.schooldevops.springbatch.sample.jobs;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.schooldevops.springbatch.sample.config.MybatisJobConfig;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.PlatformTransactionManager;

@SpringBootTest
public class StudentJobTest {

    @Autowired
    MybatisJobConfig mybatisJobConfig;

    @Autowired
    JobRepository jobRepository;

    @Autowired
    PlatformTransactionManager transactionManager;


    @Test
    void jdbcCursorStepRunningTest() throws Exception {
        Step step = mybatisJobConfig.jdbcCursorStep(jobRepository, transactionManager);

        Assertions.assertNotNull(step);

    }

}
