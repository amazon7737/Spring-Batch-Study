package org.schooldevops.springbatch.sample.customer.callback;

import org.springframework.batch.item.file.FlatFileHeaderCallback;

import java.io.IOException;
import java.io.Writer;

// CSV 파일의 헤더를 작성하는 콜백 클래스
public class CustomerHeader implements FlatFileHeaderCallback {

    // FlatFileHeaderCallback 인터페이스의 writeHeader 메서드를 구현하여 파일 헤더 작성
    @Override
    public void writeHeader(Writer writer) throws IOException {
        // 파일의 첫 줄에 "ID,AGE"라는 헤더를 작성함
        writer.write("ID,AGE");
    }

}
