package com.example.demosirma.csvdata;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Builder
@ToString
public class CSVRecord {
    private String empID;
    private String projectID;
    private LocalDate dateFrom;
    private LocalDate dateTo;
}
