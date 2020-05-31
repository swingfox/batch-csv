package com.ms3.spring.batch.mapper;

import com.ms3.spring.batch.dto.RecordDTO;
import com.ms3.spring.batch.model.Record;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface RecordMapper {
    RecordMapper INSTANCE = Mappers.getMapper(RecordMapper.class);

    RecordDTO recordToRecordDTO(Record record);
    Record recordDtoToRecord(RecordDTO recordDTO);
}
