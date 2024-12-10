package org.schooldevops.springbatch.sample.jobs;

import org.schooldevops.springbatch.sample.domain.Customer;
import org.springframework.batch.item.ItemProcessor;

public class After20YearsItemProcessor implements ItemProcessor<Customer, Customer> {
    @Override
    public Customer process(Customer item) throws Exception {
        item.setAge(item.getAge() + 20);
        return item;
    }
}
