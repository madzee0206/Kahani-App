package kahani.model;

public class Scene {
    private final String title;
    private final String text;
    private final String signCue;
    private final String visualCue;

    public Scene(String title, String text, String signCue, String visualCue) {
        this.title = title;
        this.text = text;
        this.signCue = signCue;
        this.visualCue = visualCue;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public String getSignCue() {
        return signCue;
    }

    public String getVisualCue() {
        return visualCue;
    }

    @Override
    public String toString() {
        return title + ": " + text;
    }
}
