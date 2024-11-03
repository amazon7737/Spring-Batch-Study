package org.schooldevops.springbatch.sample.customer.callback;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.file.FlatFileFooterCallback;

import java.io.IOException;
import java.io.Writer;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j // 로깅 기능을 위한 어노테이션
public class CustomerFooter implements FlatFileFooterCallback {

    // 고객 데이터를 집계하여 저장하는 ConcurrentHashMap
    ConcurrentHashMap<String, Integer> aggregateCustomers;

    // 생성자: 집계 데이터가 저장된 Map을 받아 초기화
    public CustomerFooter(ConcurrentHashMap<String, Integer> aggregateCustomers){
        this.aggregateCustomers = aggregateCustomers;
    }

    // 파일의 마지막에 추가할 푸터 내용을 작성
    @Override
    public void writeFooter(Writer writer) throws IOException {
        // "총 고객 수"와 "총 나이" 정보를 작성
        writer.write("총 고객 수: " + aggregateCustomers.get("TOTAL_CUSTOMERS"));
        writer.write(System.lineSeparator()); // 줄 바꿈 추가
        writer.write("총 나이: " + aggregateCustomers.get("TOTAL_AGES"));
    }
}
