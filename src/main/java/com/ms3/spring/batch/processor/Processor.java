package com.ms3.spring.batch.processor;

import com.ms3.spring.batch.model.Record;
import com.ms3.spring.batch.utils.FileWriter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

@Slf4j
@Component
@AllArgsConstructor
public class Processor implements ItemProcessor<Record, Record> {

    private Validator validator;

    private FileWriter csvWriter;

    @Override
    public Record process(Record record) throws ParseException {
        Set<ConstraintViolation<Record>> violations = validator.validate(record);
        log.debug("Process Record: " + record);
        if(!violations.isEmpty()){
            log.error("Validation error: " + record);
            csvWriter.writeFile(getRecordCSVLog(record));
            throw new ParseException("CSV record parse exception.");
        }
        return violations.isEmpty() ? record : null;
    }

    private String getRecordCSVLog(Record record) {
        StringBuilder sb = new StringBuilder();
        sb.append(record.getA()).append(",");
        sb.append(record.getB()).append(",");
        sb.append(record.getC()).append(",");
        sb.append(record.getD()).append(",");
        sb.append("\"").append(record.getE()).append("\"").append(",");
        sb.append(record.getF()).append(",");
        sb.append(record.getG()).append(",");
        sb.append(record.getH()).append(",");
        sb.append(record.getI()).append(",");
        sb.append(record.getJ());
        sb.append(System.lineSeparator());

        log.info("String Builder: " + sb.toString());
        return sb.toString();
    }
}
