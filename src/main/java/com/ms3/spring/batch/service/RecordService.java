package com.ms3.spring.batch.service;

import com.ms3.spring.batch.dto.RecordDTO;
import org.springframework.batch.core.BatchStatus;

import java.util.List;

public interface RecordService {
    BatchStatus loadBatch();
    List<RecordDTO> findAll();
}
