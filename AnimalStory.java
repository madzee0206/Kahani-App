package kahani.model;

import java.util.List;

public class AnimalStory extends Story {
    public AnimalStory(int id, String title, int minAge, int maxAge,
                       String difficulty, List<Scene> scenes) {
        super(id, title, "Animals", minAge, maxAge, difficulty, scenes);
    }

    @Override
    public String getLearningMessage() {
        return "Animal stories encourage kindness, observation and simple moral learning.";
    }
}
