package com.ms3.spring.batch;

import com.ms3.spring.batch.model.Record;
import com.ms3.spring.batch.repository.RecordRepository;
import com.ms3.spring.batch.service.RecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ScriptUtils;

import javax.sql.DataSource;
import javax.transaction.Transactional;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.LineNumberReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Slf4j
@SpringBootApplication(scanBasePackages = "com.ms3.spring.batch")
@EnableJpaRepositories(basePackages = "com.ms3.spring.batch.repository")
public class BatchApplication {

	public static void main(String[] args) {
		SpringApplication.run(BatchApplication.class, args);
	}

	@Value("${db.url}")
	private String dataSourceUrl;

	@Value("${init.db.script}")
	private String initDbScript;

	@Value("${drop.db.script}")
	private String dropDbScript;

	@Value("${path.config}")
	private String dbResourcePath;

	@Bean
	public CommandLineRunner initDb() {
		return args -> {
			// Purge last transaction records
			purgeOldFiles();

			// Initialize spring batch tables
			try (Connection conn = DriverManager.getConnection(dataSourceUrl)){
				log.info("Connection to SQLite has been established.");
				ScriptUtils.executeSqlScript(conn, new EncodedResource(new ClassPathResource(dropDbScript)));
				ScriptUtils.executeSqlScript(conn, new EncodedResource(new ClassPathResource(initDbScript)));
				log.info("SQL script execution successful.");
			} catch (SQLException e) {
				log.error(e.getMessage());
			}
		};
	}

	private void purgeOldFiles(){
		File dataLog = new File(dbResourcePath+"\\data.log");
		File badDataCsv = new File(dbResourcePath+"\\data-bad.csv");

		if (dataLog.exists()) dataLog.delete();
		if(badDataCsv.exists()) dataLog.delete();
	}
}
