package kahani.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class Story {
    private final int id;
    private final String title;
    private final String genre;
    private final int minAge;
    private final int maxAge;
    private final String difficulty;
    private final List<Scene> scenes;

    protected Story(int id, String title, String genre, int minAge, int maxAge,
                    String difficulty, List<Scene> scenes) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.minAge = minAge;
        this.maxAge = maxAge;
        this.difficulty = difficulty;
        this.scenes = new ArrayList<>(scenes);
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getGenre() {
        return genre;
    }

    public int getMinAge() {
        return minAge;
    }

    public int getMaxAge() {
        return maxAge;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public List<Scene> getScenes() {
        return Collections.unmodifiableList(scenes);
    }

    public abstract String getLearningMessage();

    public boolean matchesAge(int age) {
        return age >= minAge && age <= maxAge;
    }

    @Override
    public String toString() {
        return title + " | " + genre + " | Ages " + minAge + "-" + maxAge;
    }
}
