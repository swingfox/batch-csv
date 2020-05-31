package com.ms3.spring.batch.processor;

import com.ms3.spring.batch.model.Record;
import com.ms3.spring.batch.utils.FileWriter;
import org.junit.Rule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.batch.item.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class ProcessorTest {
    private Validator validator;

    @Mock
    private FileWriter csvWriter;


    @InjectMocks
    private Processor processor;

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        processor = new Processor(validator, csvWriter);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    public void testProcess_WithViolation(){
        exceptionRule.expect(ParseException.class);
        exceptionRule.expectMessage("CSV record parse exception.");


        Exception exception = assertThrows(ParseException.class, () -> {
            final Record record = new Record();
            processor.process(record);
        });

        String expectedMessage = "CSV record parse exception.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testProcess_NoViolation(){
        final Record record = new Record();
        record.setA("test");
        record.setB("test");
        record.setC("test@gmail.com");
        record.setD("test");
        record.setE("test");
        record.setF("test");
        record.setG("test");
        record.setH("true");
        record.setI("true");
        record.setJ("test");

        processor.process(record);
    }
}