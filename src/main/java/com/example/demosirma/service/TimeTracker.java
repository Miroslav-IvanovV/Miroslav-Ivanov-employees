package com.example.demosirma.service;

import com.example.demosirma.csvdata.CSVData;
import com.example.demosirma.csvdata.CSVRecord;
import com.example.demosirma.csvdata.OverlapRecord;

import java.time.LocalDate;
import java.util.*;

public class TimeTracker {

    public static Set<OverlapRecord> findOverlaps(CSVData records) {
        Map<String, Map<String, Integer>> overlapMap = new HashMap<>();
        List<CSVRecord> record = records.getRecords();

        // Populate overlapMap with the sum of overlapping days for each pair of employees on each project
        for (int i = 0; i < record.size(); i++) {
            CSVRecord record1 = record.get(i);
            String projectID = record1.getProjectID();
            String empID1 = record1.getEmpID();
            LocalDate dateFrom1 = record1.getDateFrom();
            LocalDate dateTo1 = record1.getDateTo();

            // Check for overlaps with subsequent record in the same project
            for (int j = i + 1; j < record.size(); j++) {
                CSVRecord record2 = record.get(j);
                if (record2.getProjectID().equals(projectID)) { // Only consider record in the same project
                    String empID2 = record2.getEmpID();
                    LocalDate dateFrom2 = record2.getDateFrom();
                    LocalDate dateTo2 = record2.getDateTo();

                    // Calculate overlap
                    int overlapDays = calculateOverlap(dateFrom1, dateTo1, dateFrom2, dateTo2);

                    if (overlapDays > 0) {
                        String employeePair = empID1.compareTo(empID2) < 0 ? empID1 + ", " + empID2 : empID2 + ", " + empID1;
                        if (!overlapMap.containsKey(employeePair)) {
                            overlapMap.put(employeePair, new HashMap<>());
                        }
                        Map<String, Integer> projectOverlapMap = overlapMap.get(employeePair);
                        projectOverlapMap.put(projectID, overlapDays);
                    }
                }
            }
        }

        // Convert overlapMap to OverlapRecord list
        Set<OverlapRecord> overlaps = new TreeSet<>();
        for (String employeePair : overlapMap.keySet()) {
            Map<String, Integer> projectOverlapMap = overlapMap.get(employeePair);
            for (String projectID : projectOverlapMap.keySet()) {
                int daysWorked = projectOverlapMap.get(projectID);
                overlaps.add(new OverlapRecord(employeePair.split(", ")[0], employeePair.split(", ")[1], projectID, daysWorked));
            }
        }

        return overlaps;
    }

    // Calculate overlap between two date ranges
    private static int calculateOverlap(LocalDate dateFrom1, LocalDate dateTo1, LocalDate dateFrom2, LocalDate dateTo2) {
        LocalDate latestStart = dateFrom1.isAfter(dateFrom2) ? dateFrom1 : dateFrom2;
        LocalDate earliestEnd = dateTo1.isBefore(dateTo2) ? dateTo1 : dateTo2;
        int overlap = (int) latestStart.until(earliestEnd.plusDays(1), java.time.temporal.ChronoUnit.DAYS);
        return Math.max(overlap, 0);
    }
}
