package org.schooldevops.springbatch.sample.Callback;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.file.FlatFileFooterCallback;

import java.io.IOException;
import java.io.Writer;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class IndexFooter implements FlatFileFooterCallback {

    ConcurrentHashMap<String, Integer> aggregateIndex;

    public IndexFooter(ConcurrentHashMap<String, Integer> aggregateIndex){
        this.aggregateIndex = aggregateIndex;
    }

    @Override
    public void writeFooter(Writer writer) throws IOException {
        writer.write("전월 대비 비율: " + aggregateIndex.get(""));
    }
}
