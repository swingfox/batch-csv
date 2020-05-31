package com.ms3.spring.batch.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.fasterxml.jackson.annotation.JsonInclude.Include;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
public class RecordDTO {
    private Long id;
    private String A;
    private String B;
    private String C;
    private String D;
    private String E;
    private String F;
    private String G;
    private String H;
}
