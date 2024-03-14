package com.example.demosirma.csvdata;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class CSVData {
    private List<CSVRecord> records;
}
