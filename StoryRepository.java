package kahani.model;

import java.util.ArrayList;
import java.util.List;

public class StoryRepository {
    private final List<Story> stories = new ArrayList<>();

    public StoryRepository() {
        loadDefaultStories();
    }

    private void loadDefaultStories() {
        List<Scene> lionScenes = new ArrayList<>();
        lionScenes.add(new Scene(
                "A Quiet Afternoon",
                "A lion was sleeping peacefully under a large tree.",
                "LION + SLEEP + TREE",
                "Lion resting under a tree"
        ));
        lionScenes.add(new Scene(
                "A Tiny Visitor",
                "A little mouse ran across the lion and woke him up.",
                "MOUSE + RUN + LION + WAKE",
                "Mouse near the lion"
        ));
        lionScenes.add(new Scene(
                "A Promise",
                "The lion let the mouse go. The mouse promised to help one day.",
                "LION + HELP + MOUSE + PROMISE",
                "Lion and mouse together"
        ));
        lionScenes.add(new Scene(
                "The Rescue",
                "Later, the mouse chewed a net and helped the lion escape.",
                "MOUSE + CHEW + NET + HELP + LION",
                "Mouse freeing the lion"
        ));

        stories.add(new AnimalStory(
                1, "The Lion and the Mouse", 6, 8, "Easy", lionScenes
        ));

        List<Scene> foxScenes = new ArrayList<>();
        foxScenes.add(new Scene(
                "The Fox Is Hungry",
                "A clever fox was looking for food in the forest.",
                "FOX + HUNGRY + SEARCH",
                "Fox walking through a forest"
        ));
        foxScenes.add(new Scene(
                "The Grapes",
                "The fox saw grapes hanging high above him.",
                "FOX + SEE + GRAPES + HIGH",
                "Grapes hanging from a vine"
        ));
        foxScenes.add(new Scene(
                "Many Attempts",
                "He jumped again and again, but could not reach them.",
                "FOX + JUMP + AGAIN + CANNOT",
                "Fox trying to reach grapes"
        ));
        foxScenes.add(new Scene(
                "The Lesson",
                "The fox walked away and learned to accept what he could not change.",
                "FOX + WALK + AWAY + LEARN",
                "Fox leaving the vineyard"
        ));

        stories.add(new AnimalStory(
                2, "The Clever Fox", 7, 10, "Medium", foxScenes
        ));

        List<Scene> forestScenes = new ArrayList<>();
        forestScenes.add(new Scene(
                "The Map",
                "Maya found an old map that showed a hidden forest path.",
                "GIRL + FIND + MAP + FOREST",
                "Child holding an old map"
        ));
        forestScenes.add(new Scene(
                "The Path",
                "She followed the path and discovered a stream.",
                "GIRL + WALK + PATH + STREAM",
                "Path beside a stream"
        ));
        forestScenes.add(new Scene(
                "The Choice",
                "Maya had to choose between two paths and used the clues on her map.",
                "GIRL + TWO PATHS + CHOOSE + MAP",
                "Two paths in the forest"
        ));
        forestScenes.add(new Scene(
                "The Discovery",
                "She reached a peaceful garden and returned home with a new story to tell.",
                "GARDEN + DISCOVER + RETURN + STORY",
                "A peaceful hidden garden"
        ));

        stories.add(new AdventureStory(
                3, "The Secret Forest", 9, 12, "Medium", forestScenes
        ));
    }

    public List<Story> getAllStories() {
        return new ArrayList<>(stories);
    }

    public Story findById(int id) {
        for (Story story : stories) {
            if (story.getId() == id) {
                return story;
            }
        }
        return null;
    }

    public List<Story> findByGenre(String genre) {
        List<Story> result = new ArrayList<>();
        for (Story story : stories) {
            if (story.getGenre().equalsIgnoreCase(genre)) {
                result.add(story);
            }
        }
        return result;
    }

    public List<Story> findForAge(int age) {
        List<Story> result = new ArrayList<>();
        for (Story story : stories) {
            if (story.matchesAge(age)) {
                result.add(story);
            }
        }
        return result;
    }
}
