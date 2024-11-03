package org.schooldevops.springbatch.sample.customer.aggregator;

import org.schooldevops.springbatch.sample.customer.Customer;
import org.springframework.batch.item.file.transform.LineAggregator;

// Customer 객체를 CSV 형식의 한 줄로 변환하는 클래스
public class CustomerLineAggregator implements LineAggregator<Customer> {

    // Customer 객체의 데이터를 한 줄로 묶어 반환
    @Override
    public String aggregate(Customer item) {
        // Customer 객체의 이름과 나이를 쉼표로 구분하여 문자열로 반환
        return item.getName() + "," + item.getAge();
    }
}
