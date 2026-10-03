package kahani.quiz;

import java.util.ArrayList;
import java.util.List;

public class Quiz {
    private final int storyId;
    private final List<QuizQuestion> questions = new ArrayList<>();

    public Quiz(int storyId) {
        this.storyId = storyId;
        createQuestions();
    }

    private void createQuestions() {
        if (storyId == 1) {
            questions.add(new QuizQuestion(
                    "Who helped the lion?",
                    new String[]{"Mouse", "Fox", "Rabbit", "Bird"}, 0
            ));
            questions.add(new QuizQuestion(
                    "Where was the lion sleeping?",
                    new String[]{"Near a river", "Under a tree", "In a cave", "On a hill"}, 1
            ));
        } else if (storyId == 2) {
            questions.add(new QuizQuestion(
                    "What did the fox want?",
                    new String[]{"Grapes", "Milk", "Fish", "Bread"}, 0
            ));
            questions.add(new QuizQuestion(
                    "What lesson did the fox learn?",
                    new String[]{"To fly", "To accept what he could not change",
                            "To swim", "To sleep"}, 1
            ));
        } else {
            questions.add(new QuizQuestion(
                    "What did Maya find?",
                    new String[]{"A key", "An old map", "A crown", "A boat"}, 1
            ));
            questions.add(new QuizQuestion(
                    "What helped Maya choose the path?",
                    new String[]{"The map", "A song", "A clock", "A bird"}, 0
            ));
        }
    }

    public int getStoryId() {
        return storyId;
    }

    public List<QuizQuestion> getQuestions() {
        return new ArrayList<>(questions);
    }

    public int calculateScore(int[] answers) {
        int score = 0;
        int count = Math.min(answers.length, questions.size());

        for (int i = 0; i < count; i++) {
            if (questions.get(i).isCorrect(answers[i])) {
                score++;
            }
        }
        return score;
    }
}
