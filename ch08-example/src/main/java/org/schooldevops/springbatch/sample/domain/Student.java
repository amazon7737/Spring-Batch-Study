package org.schooldevops.springbatch.sample.domain;

import lombok.Data;

@Data
public class Student {
    private Long studentId;
    private String name;
    private String department;
}
