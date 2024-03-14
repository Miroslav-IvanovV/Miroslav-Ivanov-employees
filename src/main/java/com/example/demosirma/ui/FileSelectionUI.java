package com.example.demosirma.ui;

import com.example.demosirma.csvdata.OverlapRecord;
import lombok.Getter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Set;

public class FileSelectionUI extends JFrame {
    private JLabel label;
    private JButton button;
    // Method to get the selected file
    @Getter
    private File selectedFile;
    private JTable table;

    public FileSelectionUI() {
        table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table);
        button = new JButton("Select File");

        setTitle("File Selection UI");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create components
        label = new JLabel("No file selected.");
        button = new JButton("Select File");

        // Add button click listener
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(FileSelectionUI.this);
                if (result == JFileChooser.APPROVE_OPTION) {
                    selectedFile = fileChooser.getSelectedFile();
                    label.setText("Selected file: " + selectedFile.getAbsolutePath());
                }
            }
        });

        // Set layout
        setLayout(new BorderLayout());

        // Add components to the content pane
        add(label, BorderLayout.CENTER);
        add(button, BorderLayout.SOUTH);
    }

    public static void displayPojoListInTable(Set<OverlapRecord> pojoList) {
        // Create table model with column names
        DefaultTableModel model = new DefaultTableModel(
                new Object[]{"Employee ID #1", "Employee ID #2", "Project ID", "Days worked"}, 0);

        // Add data from POJOs to the table model
        for (OverlapRecord pojo : pojoList) {
            model.addRow(new Object[]{pojo.getEmployeeID1(), pojo.getEmployeeID2(), pojo.getProjectID(), pojo.getDaysWorked()});
        }

        // Set the table model
        JFrame frame = new JFrame();
        frame.setLayout(new BorderLayout());
        frame.add(new JScrollPane(new JTable(model)), BorderLayout.CENTER);
        frame.setTitle("POJO List");
        frame.setSize(800, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

}