package com.ms3.spring.batch.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class LogWriter implements FileWriter {
    @Override
    public boolean writeFile(String text) {
        try (java.io.FileWriter fileWriter = new java.io.FileWriter("C:\\Users\\David-PC\\Documents\\batch\\src\\main\\resources\\data.log", false)) {
            fileWriter.write(text);
        } catch (IOException e) {
            log.info(e.getMessage());
            return false;
        }
        return true;
    }
}
