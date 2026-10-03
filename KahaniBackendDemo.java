package kahani.app;

import kahani.accessibility.AccessibilityService;
import kahani.data.LearningDataStore;
import kahani.data.LearningRecord;
import kahani.ml.DataMiner;
import kahani.ml.StoryRecommender;
import kahani.model.Scene;
import kahani.model.Story;
import kahani.model.StoryRepository;
import kahani.optimization.GradientDescent;
import kahani.optimization.OptimizationResult;
import kahani.quiz.Quiz;

import java.io.IOException;
import java.util.List;

public class KahaniBackendDemo {

    public static void main(String[] args) {
        try {
            StoryRepository repository = new StoryRepository();

            System.out.println("===== KAHANI BACKEND DEMO =====");

            System.out.println("\n1. STORIES");
            for (Story story : repository.getAllStories()) {
                System.out.println(story);
                System.out.println("   " + story.getLearningMessage());
            }

            Story selected = repository.findById(1);

            System.out.println("\n2. STORY SCENES");
            for (Scene scene : selected.getScenes()) {
                System.out.println(scene.getTitle());
                System.out.println("Text: " + scene.getText());
                System.out.println("Sign: " + scene.getSignCue());
            }

            System.out.println("\n3. ACCESSIBILITY");
            AccessibilityService accessibility = new AccessibilityService();
            accessibility.setLargeText(true);
            accessibility.setSpeechEnabled(true);
            Scene firstScene = selected.getScenes().get(0);
            System.out.println("Large text: " + accessibility.isLargeText());
            System.out.println("Sign representation: " +
                    accessibility.getSignRepresentation(firstScene));
            accessibility.speak(firstScene);

            System.out.println("\n4. QUIZ");
            Quiz quiz = new Quiz(selected.getId());
            int[] answers = {0, 1};
            int score = quiz.calculateScore(answers);
            System.out.println("Score: " + score + "/" +
                    quiz.getQuestions().size());

            System.out.println("\n5. LEARNING DATA");
            LearningDataStore store = new LearningDataStore();

            store.addRecord(new LearningRecord(
                    101, 1, 7, 4, 2, 2, 5.5, "Animals"
            ));
            store.addRecord(new LearningRecord(
                    101, 2, 7, 4, 1, 2, 6.2, "Animals"
            ));
            store.addRecord(new LearningRecord(
                    101, 3, 7, 4, 2, 2, 7.1, "Adventure"
            ));

            DataMiner miner = new DataMiner();
            System.out.println(miner.describe(store.getRecords()));
            System.out.println("Genre counts: " +
                    miner.genreCounts(store.getRecords()));

            System.out.println("\n6. ML RECOMMENDATION");
            StoryRecommender recommender = new StoryRecommender();

            List<Story> recommendations = recommender.recommend(
                    7,
                    "Animals",
                    repository.getAllStories(),
                    store.getRecords()
            );

            for (Story story : recommendations) {
                System.out.println("Recommended: " + story.getTitle());
            }

            System.out.println("\n7. OPTIMIZATION");
            GradientDescent gd = new GradientDescent();

            OptimizationResult result =
                    gd.optimize(10.0, 0.1, 30);

            System.out.println(result);

            System.out.println("\n8. SAVE DATA");
            store.saveCsv("kahani_learning_data.csv");
            System.out.println("Saved: kahani_learning_data.csv");

            System.out.println("\nKAHANI backend is ready for GUI integration.");

        } catch (IOException e) {
            System.out.println("File error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
        }
    }
}
