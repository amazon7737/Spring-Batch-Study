package org.schooldevops.springbatch.sample.readers;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.batch.MyBatisPagingItemReader;
import org.schooldevops.springbatch.sample.config.MybatisJobConfig;
import org.schooldevops.springbatch.sample.domain.Customer;
import org.schooldevops.springbatch.sample.domain.Student;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;


@SpringBootTest
public class itemReaderTest {

    @Autowired
    MybatisJobConfig mybatisJobConfig;

    @Test
    void studentListReaderRunningTest() throws Exception {
        MyBatisPagingItemReader<Student> students = mybatisJobConfig.studentMyBatisItemReader();
        Assertions.assertNotNull(students);
    }

    @Test
    void studentListWriterRunningTest() {
        FlatFileItemWriter<Student> result = mybatisJobConfig.cursorFlatFileWriter();
        Assertions.assertNotNull(result);
    }

    @DisplayName("학생 목록 csv 파일 내 데이터가 정상적으로 읽어졌는지 체크")
    @Test
    void studentListReadingAndCheckNotNullTest() throws Exception {
        FlatFileItemReader<Student> result = mybatisJobConfig.flatFileItemReader();
//        MyBatisPagingItemReader<Student> students = mybatisJobConfig.studentMyBatisItemReader();

        System.out.println(result.getClass().getResource("./student.csv"));

        Assertions.assertEquals("String", result.getName());
    }



}
