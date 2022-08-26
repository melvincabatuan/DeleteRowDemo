package ph.edu.dlsu.lbycpei;


/*
CsvLoader.java
- This class handles CSV file loading and writing
- It also provides the TableView with ObservableList
Author: MKC
 */

import com.opencsv.*;
import com.opencsv.exceptions.CsvValidationException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class CsvHandler {

    public static final String CSV_FILENAME = "data/ECE_CpE_Thesis_Groups_2nd_Term_AY2021_2022.csv";
    public static final String[] HEADER = {"Thesis Title", "Grp", "Trm", "SY", "No.", "Members",
            "Adviser", "Chair of Panel", "Panelist1", "Panelist2", "Status"};
    private ObservableList<ThesisRecord> database;

    public CsvHandler() {
        loadCsv();
    }

    // Loads the CSV file
    public void loadCsv() {
        database = FXCollections.observableArrayList();

        try (CSVReader reader = new CSVReaderBuilder(
                new FileReader(CSV_FILENAME))
                .withSkipLines(1)
                .build()) // Skip header or 1st row
        {
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) //Read one line at a time
            {
                System.out.println(Arrays.toString(nextLine));
                ThesisRecord thesisRecord = new ThesisRecord(nextLine);
                database.add(thesisRecord);
            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
    }

    public void deleteSelected(ArrayList<String> selectedIDs) {
        for (String selectedID : selectedIDs) {
            database.removeIf(record -> record.getTitle().equals(selectedID));
        }
        writeCsv();
    }

    public void writeCsv() {
        try (FileWriter writer = new FileWriter(CSV_FILENAME)) {
            BufferedWriter bw = new BufferedWriter(writer);
            bw.write(String.join(",", HEADER));
            for (ThesisRecord record : database) {
                bw.write(String.join(",", wrapComma(record.toArray())));
                bw.newLine();
            }
            bw.close();
        } catch (IOException e) {
            System.err.format("IOException: %s%n", e);
        }
        System.out.println("Writing success!");
    }

    public void writeRecord(String[] record) {
        String filename = CSV_FILENAME;
        try (FileWriter writer = new FileWriter(filename, true)) {
            BufferedWriter bw = new BufferedWriter(writer);
            if (!newLineExists(filename)) {
                bw.newLine();
            }
            bw.write(String.join(",", wrapComma(record)));
            bw.close();
        } catch (IOException e) {
            System.err.format("IOException: %s%n", e);
        }
        System.out.println("Writing success!");
    }


    private String[] wrapComma(String[] record) {
        String[] result = new String[record.length];
        // Wraps string entries containing comma with quotes "
        for (int i = 0; i < record.length; i++) {
            if (record[i].contains(",")) {
                result[i] = "\"" + record[i] + "\"";
            } else {
                result[i] = record[i];
            }
        }
        return result;
    }

    private boolean newLineExists(String filename) throws IOException {
        // Checks for end of file new line
        // Source: https://stackoverflow.com/questions/28795440/check-if-a-new-line-exists-at-end-of-file
        File file = new File(filename);
        RandomAccessFile fileHandler = new RandomAccessFile(file, "r");
        long fileLength = fileHandler.length() - 1;
        if (fileLength < 0) {
            fileHandler.close();
            return true;
        }
        fileHandler.seek(fileLength);
        byte readByte = fileHandler.readByte();
        fileHandler.close();

        return readByte == 0xA || readByte == 0xD;
    }

    public ObservableList<ThesisRecord> getDatabase() {
        return database;
    }

}
