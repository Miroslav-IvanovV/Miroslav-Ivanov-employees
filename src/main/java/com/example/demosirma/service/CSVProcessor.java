package com.example.demosirma.service;

import com.example.demosirma.csvdata.CSVData;
import com.example.demosirma.csvdata.CSVRecord;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CSVProcessor {
    public static CSVData processCSV(File file) throws Exception {
        List<CSVRecord> records = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            br.readLine(); // Skip header if present
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    LocalDate dateTo = ParseDate(parts[3]);
                    LocalDate dateFrom = ParseDate(parts[2]);
                    records.add(buildCSVRecord(parts, dateFrom, dateTo));
                }
            }
        }

        CSVData csvData = new CSVData();
        csvData.setRecords(records);
        return csvData;
    }

    private static CSVRecord buildCSVRecord(String[] parts, LocalDate dateFrom, LocalDate dateTo) {
        return CSVRecord.builder()
                .empID(parts[0])
                .projectID(parts[1])
                .dateFrom(dateFrom)
                .dateTo(dateTo)
                .build();
    }

    public static LocalDate ParseDate(String part) {
        List<String> dateFormats = Arrays.asList("yyyy-MM-dd", "dd/MM/yyyy", "MM-dd-yyyy");
        LocalDateTime now = LocalDateTime.now();
        LocalDate date = null;
        for (String format : dateFormats) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
                date = part.trim().equalsIgnoreCase("NULL") ? now.toLocalDate() : LocalDate.parse(part.trim(), formatter);
                break;
            } catch (DateTimeParseException e) {
                // Try next format
            }
        }
        return date;
    }
}
