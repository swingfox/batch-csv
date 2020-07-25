package com.ms3.spring.batch.service;

import com.ms3.spring.batch.dto.RecordDTO;
import com.ms3.spring.batch.mapper.RecordMapper;
import com.ms3.spring.batch.mapper.RecordMapperImpl;
import com.ms3.spring.batch.model.Record;
import com.ms3.spring.batch.repository.RecordRepository;
import com.ms3.spring.batch.service.RecordService;
import com.ms3.spring.batch.utils.CsvWriter;
import com.ms3.spring.batch.utils.FileWriter;
import com.ms3.spring.batch.utils.LogWriter;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.batch.core.*;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.util.ReflectionUtils;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
class RecordServiceTest {

    @Mock
    private CsvWriter csvWriter;

    @Mock
    private JobLauncher jobLauncher;

    @Mock
    private Job job;

    @Mock
    private JobExecution jobExecution;

    @Mock
    private JobExplorer jobExplorer;

    @Mock
    private RecordRepository recordRepository;

    @Mock
    private RecordMapper recordMapper;

    @Mock
    private LogWriter logWriter;

    private RecordServiceImpl recordServiceImpl;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        recordServiceImpl = spy(new RecordServiceImpl(recordRepository,
                recordMapper,
                jobLauncher,
                job,
                jobExplorer,
                csvWriter,
                logWriter));

    }

    @Test
    void testLoadBatch() throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {

        // init
        final JobInstance jobInstance = mock(JobInstance.class);
        final List<JobInstance> lastExecutedJobs = Arrays.asList(jobInstance);
        final Optional<JobExecution> lastExecutedJob = Optional.of(jobExecution);
        final StepExecution stepExecution = mock(StepExecution.class);
        final Collection<StepExecution> stepExecutions = Arrays.asList(stepExecution);
        final List<JobExecution> jobExecutions = Arrays.asList(jobExecution);
        final JobParameters parameters = new JobParameters();

        // stub
        when(recordServiceImpl.createJobParams()).thenReturn(parameters);
        when(csvWriter.writeFile(anyString())).thenReturn(true);
        when(jobLauncher.run(job, parameters)).thenReturn(jobExecution);
        when(jobExecution.isRunning()).thenReturn(Boolean.TRUE).thenReturn(Boolean.FALSE);
        when(jobExecution.getStatus()).thenReturn(BatchStatus.STARTED).thenReturn(BatchStatus.COMPLETED).thenReturn(BatchStatus.COMPLETED);
        when(jobExplorer.getJobInstances(job.getName(),0, Integer.MAX_VALUE)).thenReturn(lastExecutedJobs);
        when(jobExplorer.getJobExecutions(lastExecutedJobs.get(0))).thenReturn(jobExecutions);
        when(jobExecutions.get(0).getStatus()).thenReturn(BatchStatus.COMPLETED);

        when(jobExecution.getStatus()).thenReturn(BatchStatus.COMPLETED);

        when(jobExecution.getStatus()).thenReturn(BatchStatus.COMPLETED);
        when(lastExecutedJob.get().getStepExecutions()).thenReturn(stepExecutions);

        when(stepExecution.getReadCount()).thenReturn(1);
        when(stepExecution.getWriteCount()).thenReturn(1);
        when(stepExecution.getSkipCount()).thenReturn(1);

        // execute
        BatchStatus batchStatus = recordServiceImpl.loadBatch();
        assertEquals(batchStatus.COMPLETED, BatchStatus.COMPLETED);
    }

    @Test
    void findAll() {
        // stub
        when(recordRepository.findAll()).thenReturn(Arrays.asList(new Record()));
        List<RecordDTO> recordDTOS = recordServiceImpl.findAll();

        // execute
        assertNotNull(recordDTOS);
    }
}