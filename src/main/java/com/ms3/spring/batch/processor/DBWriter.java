package com.ms3.spring.batch.processor;

import com.ms3.spring.batch.model.Record;
import com.ms3.spring.batch.repository.RecordRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class DBWriter implements ItemWriter<Record> {

    private RecordRepository recordRepository;

    @Autowired
    public DBWriter(RecordRepository recordRepository){
        this.recordRepository = recordRepository;
    }

    @Override
    public void write(List<? extends Record> records) throws Exception {
        log.info("Data saved for Records: " + records);
        recordRepository.saveAll(records);
    }
}
