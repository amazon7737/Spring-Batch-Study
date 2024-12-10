package org.schooldevops.springbatch.sample.jobs;

import lombok.extern.slf4j.Slf4j;
import org.schooldevops.springbatch.sample.domain.Customer;
import org.springframework.batch.item.ItemProcessor;

@Slf4j
public class CustomerItemProcessor implements ItemProcessor<Customer, Customer> {
    @Override
    public Customer process(Customer item) throws Exception {
        log.info("Item Processor ------- {}" ,item
        );
        return item;
    }
}
