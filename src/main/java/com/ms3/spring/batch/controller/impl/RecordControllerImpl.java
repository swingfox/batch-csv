package com.ms3.spring.batch.controller.impl;

import com.ms3.spring.batch.controller.RecordController;
import com.ms3.spring.batch.dto.RecordDTO;
import com.ms3.spring.batch.service.RecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
public class RecordControllerImpl implements RecordController {
    @Autowired
    private RecordService recordService;

    @GetMapping("/load")
    @Override
    public BatchStatus load() {
        return recordService.loadBatch();
    }

    @GetMapping("/record")
    @Override
    public List<RecordDTO> findAll() {
        return recordService.findAll();
    }
}
