package com.ms3.spring.batch.service;

import com.ms3.spring.batch.dto.RecordDTO;
import com.ms3.spring.batch.mapper.RecordMapper;
import com.ms3.spring.batch.repository.RecordRepository;
import com.ms3.spring.batch.utils.FileWriter;
import com.ms3.spring.batch.service.RecordService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class RecordServiceImpl implements RecordService {

    private RecordRepository recordRepository;

    private RecordMapper recordMapper = RecordMapper.INSTANCE;

    private JobLauncher jobLauncher;

    private Job job;

    private JobExplorer jobExplorer;

    @Qualifier("csvWriter")
    private FileWriter csvWriter;

    @Qualifier("logWriter")
    private FileWriter logWriter;

    @SneakyThrows
    @Override
    public BatchStatus loadBatch() {
        csvWriter.writeFile("A,B,C,D,E,F,G,H,I,J" + System.lineSeparator());
        return runBatchJob();
    }

    @Override
    public List<RecordDTO> findAll() {
        return recordRepository.findAll().stream().map(rec -> recordMapper.recordToRecordDTO(rec)).collect(Collectors.toList());
    }

    private void showBatchJobMetadata() {
        List<JobInstance> lastExecutedJobs = jobExplorer.getJobInstances(job.getName(), 0, Integer.MAX_VALUE);

        Optional<JobExecution> lastExecutedJob = lastExecutedJobs
                .stream()
                .map(jobExplorer::getJobExecutions)
                .flatMap(jes -> jes.stream())
                .filter(je -> BatchStatus.COMPLETED.equals(je.getStatus()))
                .findFirst();

        log.debug("job execution: " + lastExecutedJob);

        lastExecutedJob
                .get()
                .getStepExecutions()
                .stream().forEach(e -> {
            StringBuilder sb = new StringBuilder();
            log.debug("# of records received: " + e.getReadCount());
            log.debug("# of records successful: " + e.getWriteCount());
            log.debug("# of records failed: " + e.getSkipCount());

            sb.append("# of records received: ").append(e.getReadCount()).append(System.lineSeparator())
              .append("# of records successful: ").append(e.getWriteCount()).append(System.lineSeparator())
              .append("# of records failed: ").append(e.getSkipCount());

            logWriter.writeFile(sb.toString());
        });
    }

    protected JobParameters createJobParams(){
        return new JobParameters();
    }

    private BatchStatus runBatchJob() throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {
        JobExecution jobExecution = jobLauncher.run(job, createJobParams());
        log.debug("Job Execution: " + jobExecution.getStatus());

        log.debug("Batch is Running...");
        while(jobExecution.isRunning()){
            log.info("...");
        }

        showBatchJobMetadata();

        return jobExecution.getStatus();
    }
}
