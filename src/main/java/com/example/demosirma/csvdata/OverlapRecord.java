package com.example.demosirma.csvdata;

import lombok.*;

@Getter
@Builder
public class OverlapRecord implements Comparable<OverlapRecord> {
    private String employeeID1;
    private String employeeID2;
    private String projectID;
    private int daysWorked;

    public OverlapRecord(String employeeID1, String employeeID2, String projectID, int daysWorked) {
        this.employeeID1 = employeeID1;
        this.employeeID2 = employeeID2;
        this.projectID = projectID;
        this.daysWorked = daysWorked;
    }

    @Override
    public int compareTo(OverlapRecord other) {
        // Compare by daysWorked in descending order
        return Integer.compare(other.daysWorked, this.daysWorked);
    }
}
