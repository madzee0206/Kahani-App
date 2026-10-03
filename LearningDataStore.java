package kahani.data;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class LearningDataStore {
    private final List<LearningRecord> records = new ArrayList<>();

    public void addRecord(LearningRecord record) {
        records.add(record);
    }

    public List<LearningRecord> getRecords() {
        return new ArrayList<>(records);
    }

    public int size() {
        return records.size();
    }

    public double averageQuizAccuracy() {
        if (records.isEmpty()) {
            return 0.0;
        }

        double total = 0.0;
        for (LearningRecord record : records) {
            total += record.getQuizAccuracy();
        }
        return total / records.size();
    }

    public void saveCsv(String fileName) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            writer.println(LearningRecord.csvHeader());
            for (LearningRecord record : records) {
                writer.println(record.toCsv());
            }
        }
    }

    public void loadCsv(String fileName) throws IOException {
        records.clear();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            boolean first = true;

            while ((line = reader.readLine()) != null) {
                if (first) {
                    first = false;
                    continue;
                }

                if (line.isBlank()) {
                    continue;
                }

                String[] p = line.split(",", -1);
                if (p.length != 8) {
                    continue;
                }

                records.add(new LearningRecord(
                        Integer.parseInt(p[0]),
                        Integer.parseInt(p[1]),
                        Integer.parseInt(p[2]),
                        Integer.parseInt(p[3]),
                        Integer.parseInt(p[4]),
                        Integer.parseInt(p[5]),
                        Double.parseDouble(p[6]),
                        p[7]
                ));
            }
        }
    }
}
