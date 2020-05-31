package com.ms3.spring.batch.config;

import com.ms3.spring.batch.model.Record;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import javax.xml.bind.ValidationException;

@Configuration
@EnableBatchProcessing
public class SpringBatchConfig {

    @Bean
    public Job job(JobBuilderFactory jobBuilderFactory,
                   StepBuilderFactory stepBuilderFactory,
                   ItemReader<Record> itemReader,
                   ItemProcessor<Record, Record> itemProcessor,
                   ItemWriter<Record> itemWriter){
        Step step = null;

        step = stepBuilderFactory.get("ETL-file-load")
                .<Record,Record>chunk(100)
                .reader(itemReader)
                .processor(itemProcessor)
                .writer(itemWriter)
                .faultTolerant()
                .skip(ParseException.class)
                .skipLimit(10000)
                .noRollback(ValidationException.class)
                .build();

        return jobBuilderFactory.get("ETL-Record")
                .incrementer(new RunIdIncrementer())
                .start(step)
                .build();
    }

    @Bean
    public FlatFileItemReader<Record> itemReader(@Value("${csv.file}") Resource resource){
        FlatFileItemReader<Record> flatFileItemReader = new FlatFileItemReader<>();
        flatFileItemReader.setResource(resource);
        flatFileItemReader.setName("Record-Reader");
        flatFileItemReader.setLinesToSkip(1);
        flatFileItemReader.setLineMapper(lineMapper());
        return flatFileItemReader;
    }

    private LineMapper<Record> lineMapper() {
        DefaultLineMapper<Record> defaultLineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();

        lineTokenizer.setDelimiter(",");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames(new String[] {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"});

        BeanWrapperFieldSetMapper<Record> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(Record.class);

        defaultLineMapper.setLineTokenizer(lineTokenizer);
        defaultLineMapper.setFieldSetMapper(fieldSetMapper);

        return defaultLineMapper;
    }
}
