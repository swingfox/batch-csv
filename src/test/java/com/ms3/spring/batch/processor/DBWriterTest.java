package com.ms3.spring.batch.processor;

import com.ms3.spring.batch.model.Record;
import com.ms3.spring.batch.repository.RecordRepository;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class DBWriterTest {
    @Mock
    private RecordRepository recordRepository;

    @InjectMocks
    private DBWriter dbWriter;


    @Test
    public void testWrite() throws Exception {
        // init
        final List<Record> recordList = Arrays.asList(mock(Record.class));

        // stub
        when(recordRepository.saveAll(recordList)).thenReturn(recordList);

        // execute
        dbWriter.write(recordList);
    }
}