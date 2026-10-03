package kahani.ml;

import kahani.data.LearningRecord;
import kahani.model.Story;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class StoryRecommender {

    public List<Story> recommend(int age, String preferredGenre,
                                 List<Story> stories,
                                 List<LearningRecord> records) {

        List<ScoredStory> scored = new ArrayList<>();

        for (Story story : stories) {
            double score = 0.0;

            if (story.matchesAge(age)) {
                score += 3.0;
            }

            if (story.getGenre().equalsIgnoreCase(preferredGenre)) {
                score += 3.0;
            }

            int interactions = 0;
            double accuracy = 0.0;

            for (LearningRecord record : records) {
                if (record.getStoryId() == story.getId()) {
                    interactions++;
                    accuracy += record.getQuizAccuracy();
                }
            }

            if (interactions > 0) {
                accuracy /= interactions;
                score += accuracy * 2.0;
            }

            scored.add(new ScoredStory(story, score));
        }

        scored.sort(Comparator.comparingDouble(ScoredStory::score).reversed());

        List<Story> result = new ArrayList<>();
        for (ScoredStory item : scored) {
            result.add(item.story());
        }

        return result;
    }

    private record ScoredStory(Story story, double score) {}
}
