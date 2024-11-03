package org.schooldevops.springbatch.sample.economic;

import lombok.Builder;
import lombok.Data;

// 지수 (전달대비 정보를 저장)
// @Builder 은 Lombok에서 제공하는 빌더 패턴을 사용
// 결과를 문자로 변경하여 출력하기 위해 사용
@Builder
@Data
public class IndexResult {
    private String name;
    private String month_3;
    private String month_4;
    private String month_5;
    private String month_6;
    private String month_7;
    private String month_8;
}
