package kahani.model;

import java.util.List;

public class AdventureStory extends Story {
    public AdventureStory(int id, String title, int minAge, int maxAge,
                          String difficulty, List<Scene> scenes) {
        super(id, title, "Adventure", minAge, maxAge, difficulty, scenes);
    }

    @Override
    public String getLearningMessage() {
        return "Adventure stories encourage curiosity, problem solving and exploration.";
    }
}
