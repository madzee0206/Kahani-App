package kahani.accessibility;

import kahani.model.Scene;

public class AccessibilityService implements SignLanguageSupport, SpeechSupport {

    private boolean largeText;
    private boolean speechEnabled;

    private final WindowsSpeechSupport speechSupport;
    private final BrailleSupport brailleSupport;

    public AccessibilityService() {
        largeText = false;
        speechEnabled = false;

        speechSupport = new WindowsSpeechSupport();
        brailleSupport = new UnicodeBrailleSupport();
    }

    public void setLargeText(boolean enabled) {
        largeText = enabled;
    }

    public boolean isLargeText() {
        return largeText;
    }

    public void setSpeechEnabled(boolean enabled) {
        speechEnabled = enabled;
    }

    public boolean isSpeechEnabled() {
        return speechEnabled;
    }

    public boolean isBrailleEnabled() {
        return brailleSupport != null && brailleSupport.isAvailable();
    }

    public void displayBraille(String text) {
        if (brailleSupport != null && brailleSupport.isAvailable()) {
            brailleSupport.displayText(text);
        }
    }

    public void clearBraille() {
        if (brailleSupport != null) {
            brailleSupport.clear();
        }
    }

    public void closeBraille() {
        if (brailleSupport != null) {
            brailleSupport.close();
        }
    }

    @Override
    public String getSignRepresentation(Scene scene) {
        return scene.getSignCue();
    }

    @Override
    public void speak(Scene scene) {
        if (speechEnabled && scene != null) {
            speechSupport.speak(scene);
        }
    }
}