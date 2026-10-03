package kahani.data;

public class LearningRecord {
    private final int userId;
    private final int storyId;
    private final int age;
    private final int scenesCompleted;
    private final int quizScore;
    private final int totalQuestions;
    private final double timeSpentMinutes;
    private final String genre;

    public LearningRecord(int userId, int storyId, int age, int scenesCompleted,
                          int quizScore, int totalQuestions,
                          double timeSpentMinutes, String genre) {
        this.userId = userId;
        this.storyId = storyId;
        this.age = age;
        this.scenesCompleted = scenesCompleted;
        this.quizScore = quizScore;
        this.totalQuestions = totalQuestions;
        this.timeSpentMinutes = timeSpentMinutes;
        this.genre = genre;
    }

    public int getUserId() {
        return userId;
    }

    public int getStoryId() {
        return storyId;
    }

    public int getAge() {
        return age;
    }

    public int getScenesCompleted() {
        return scenesCompleted;
    }

    public int getQuizScore() {
        return quizScore;
    }

    public int getTotalQuestions() {
        return totalQuestions;
    }

    public double getTimeSpentMinutes() {
        return timeSpentMinutes;
    }

    public String getGenre() {
        return genre;
    }

    public double getQuizAccuracy() {
        if (totalQuestions == 0) {
            return 0.0;
        }
        return (double) quizScore / totalQuestions;
    }

    public String toCsv() {
        return userId + "," + storyId + "," + age + "," +
                scenesCompleted + "," + quizScore + "," +
                totalQuestions + "," + timeSpentMinutes + "," + genre;
    }

    public static String csvHeader() {
        return "userId,storyId,age,scenesCompleted,quizScore,totalQuestions,timeSpentMinutes,genre";
    }

    @Override
    public String toString() {
        return "User " + userId +
                " | Story " + storyId +
                " | Quiz " + quizScore + "/" + totalQuestions +
                " | Time " + timeSpentMinutes + " min";
    }
}
