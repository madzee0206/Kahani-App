package kahani.quiz;

public class QuizQuestion {
    private final String question;
    private final String[] options;
    private final int correctIndex;

    public QuizQuestion(String question, String[] options, int correctIndex) {
        if (options == null || options.length < 2) {
            throw new IllegalArgumentException("At least two options are required.");
        }
        if (correctIndex < 0 || correctIndex >= options.length) {
            throw new IllegalArgumentException("Invalid correct option.");
        }
        this.question = question;
        this.options = options.clone();
        this.correctIndex = correctIndex;
    }

    public String getQuestion() {
        return question;
    }

    public String[] getOptions() {
        return options.clone();
    }

    public boolean isCorrect(int selectedIndex) {
        return selectedIndex == correctIndex;
    }
}
