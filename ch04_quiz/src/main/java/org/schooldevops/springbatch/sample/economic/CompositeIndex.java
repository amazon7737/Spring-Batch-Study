package org.schooldevops.springbatch.sample.economic;

import lombok.Data;

// 지수 데이터를 읽어들여서 저장할 객체
@Data
public class CompositeIndex {
    private String name;
    private float month_3;
    private float month_4;
    private float month_5;
    private float month_6;
    private float month_7;
    private float month_8;

}
