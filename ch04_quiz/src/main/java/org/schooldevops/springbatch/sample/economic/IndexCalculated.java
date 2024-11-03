package org.schooldevops.springbatch.sample.economic;

import lombok.Data;

// 계산된 중간 정보를 저장하는 객체
@Data
public class IndexCalculated {

    // 읽어들인 지수 정보
    private CompositeIndex compositeIndex;

    // 전월 대비 ratio 정보를 저장할 데이터
    private CompositeIndex ratio;
}
