package kahani.ml;

import kahani.data.LearningRecord;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataMiner {

    public Map<String, Integer> genreCounts(List<LearningRecord> records) {
        Map<String, Integer> counts = new HashMap<>();

        for (LearningRecord record : records) {
            String genre = record.getGenre();
            counts.put(genre, counts.getOrDefault(genre, 0) + 1);
        }

        return counts;
    }

    public double averageTime(List<LearningRecord> records) {
        if (records.isEmpty()) {
            return 0.0;
        }

        double total = 0.0;
        for (LearningRecord record : records) {
            total += record.getTimeSpentMinutes();
        }

        return total / records.size();
    }

    public double averageQuizAccuracy(List<LearningRecord> records) {
        if (records.isEmpty()) {
            return 0.0;
        }

        double total = 0.0;
        for (LearningRecord record : records) {
            total += record.getQuizAccuracy();
        }

        return total / records.size();
    }

    public String describe(List<LearningRecord> records) {
        return "Records: " + records.size() +
                ", Average time: " + String.format("%.2f", averageTime(records)) +
                " min, Average quiz accuracy: " +
                String.format("%.2f", averageQuizAccuracy(records) * 100) + "%";
    }
}
