package org.schooldevops.springbatch.sample.jobs;

import org.schooldevops.springbatch.sample.domain.Customer;
import org.springframework.batch.item.ItemProcessor;

/**
 * 이름, 성별을 소문자로 변경하는 ItemProcessor
 */
public class LowerCaseItemProcessor implements ItemProcessor<Customer, Customer> {
    @Override
    public Customer process(Customer item) throws Exception {
        item.setName(item.getName().toLowerCase());
        item.setGender(item.getGender().toLowerCase());
        return item;
    }
}
