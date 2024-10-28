package org.schooldevops.springbatch.sample.processor;

import lombok.extern.slf4j.Slf4j;
import org.schooldevops.springbatch.sample.domain.Customer;
import org.springframework.batch.item.ItemProcessor;

import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class AggregateCustomerProcessor implements ItemProcessor<Customer, Customer> {

    ConcurrentHashMap<String, Integer> aggregateCustomers;

    public AggregateCustomerProcessor(ConcurrentHashMap<String, Integer> aggregateCustomers){
        this.aggregateCustomers = aggregateCustomers;
    }

    @Override
    public Customer process(Customer item) throws Exception {
        aggregateCustomers.putIfAbsent("TOTAL_CUSTOMERS", 0);
        aggregateCustomers.putIfAbsent("TOTAL_AGES", 0); // 아무것도 없을때 초기값 0으로 넣기
        log.info("ages: {}", aggregateCustomers.get("TOTAL_AGES"));
        aggregateCustomers.put("TOTAL_CUSTOMERS", aggregateCustomers.get("TOTAL_CUSTOMERS") + 1);
        aggregateCustomers.put("TOTAL_AGES", aggregateCustomers.get("TOTAL_AGES") + item.getAge());
        log.info("ages after: {}", aggregateCustomers.get("TOTAL_AGES"));
        return item;
    }
}
