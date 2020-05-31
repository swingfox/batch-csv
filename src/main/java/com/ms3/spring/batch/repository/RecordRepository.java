package com.ms3.spring.batch.repository;

import com.ms3.spring.batch.model.Record;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecordRepository extends JpaRepository<Record, Integer> {

}
