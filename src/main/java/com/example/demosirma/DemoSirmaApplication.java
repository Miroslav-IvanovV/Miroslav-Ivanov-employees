package com.example.demosirma;

import com.example.demosirma.service.CSVProcessor;
import com.example.demosirma.service.TimeTracker;
import com.example.demosirma.ui.FileSelectionUI;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;

@SpringBootApplication
public class DemoSirmaApplication {

    public static void main(String[] args) throws Exception {
        FileSelectionUI ui = new FileSelectionUI();
        ui.setVisible(true);

        while (ui.getSelectedFile() == null) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        File selectedFile = ui.getSelectedFile();
        FileSelectionUI.displayPojoListInTable(TimeTracker.findOverlaps(CSVProcessor.processCSV(selectedFile)));
    }
}
