package org.schooldevops.springbatch.sample.economic;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("rateCalcItemProcessor")
@Slf4j
public class RateCalcItemProcessor implements ItemProcessor<CompositeIndex, IndexResult> {

    @Override
    public IndexResult process(CompositeIndex item) throws Exception {
        CompositeIndex rateIndex = new CompositeIndex();
        rateIndex.setName(item.getName());
        rateIndex.setMonth_3((item.getMonth_3() - item.getMonth_3()) / 100f);
        rateIndex.setMonth_3((item.getMonth_4() - item.getMonth_3()) / 100f);
        rateIndex.setMonth_3((item.getMonth_5() - item.getMonth_4()) / 100f);
        rateIndex.setMonth_3((item.getMonth_6() - item.getMonth_5()) / 100f);
        rateIndex.setMonth_3((item.getMonth_7() - item.getMonth_6()) / 100f);
        rateIndex.setMonth_3((item.getMonth_8() - item.getMonth_7()) / 100f);
        IndexCalculated indexCalculated = new IndexCalculated();
        indexCalculated.setCompositeIndex(item);
        indexCalculated.setRatio(rateIndex);

        log.info("-------- : " + indexCalculated);

        return IndexResult.builder()
                .name(item.getName())
                .month_3(makeReportValue(indexCalculated.getCompositeIndex().getMonth_3(), indexCalculated.getRatio().getMonth_3()))
                .month_4(makeReportValue(indexCalculated.getCompositeIndex().getMonth_4(), indexCalculated.getRatio().getMonth_4()))
                .month_5(makeReportValue(indexCalculated.getCompositeIndex().getMonth_5(), indexCalculated.getRatio().getMonth_5()))
                .month_6(makeReportValue(indexCalculated.getCompositeIndex().getMonth_6(), indexCalculated.getRatio().getMonth_6()))
                .month_7(makeReportValue(indexCalculated.getCompositeIndex().getMonth_7(), indexCalculated.getRatio().getMonth_7()))
                .month_8(makeReportValue(indexCalculated.getCompositeIndex().getMonth_8(), indexCalculated.getRatio().getMonth_8()))
                .build();
    }


    public String makeReportValue(float value, float rate){
        return "%s(%.2f)".formatted(value, rate); // 소수점 2자리로 자름
    }
}
