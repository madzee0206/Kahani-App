package kahani.accessibility;

public interface BrailleSupport {

    boolean isAvailable();

    void displayText(String text);

    void clear();

    void close();
}