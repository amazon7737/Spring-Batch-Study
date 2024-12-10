package org.schooldevops.springbatch.sample.service;

import lombok.extern.slf4j.Slf4j;
import org.schooldevops.springbatch.sample.domain.Customer;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
public class CustomService {
    public Map<String, String> processToOtherService(Customer item) {

        log.info("Call API to OtherService...");

        return Map.of("code", "200", "message", "OK");
    }
}
