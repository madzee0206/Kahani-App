package kahani.accessibility;

import kahani.model.Scene;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class WindowsSpeechSupport implements SpeechSupport {

    @Override
    public void speak(Scene scene) {

        if (scene == null) {
            return;
        }

        String text = scene.getText();

        if (text == null || text.trim().isEmpty()) {
            return;
        }

        try {

            String encodedText = Base64.getEncoder()
                    .encodeToString(text.getBytes(StandardCharsets.UTF_8));

            String command =
                    "$text = [System.Text.Encoding]::UTF8.GetString(" +
                    "[System.Convert]::FromBase64String('" +
                    encodedText +
                    "')); " +
                    "Add-Type -AssemblyName System.Speech; " +
                    "$speaker = New-Object System.Speech.Synthesis.SpeechSynthesizer; " +
                    "$speaker.Speak($text); " +
                    "$speaker.Dispose();";

            ProcessBuilder processBuilder = new ProcessBuilder(
                    "powershell.exe",
                    "-NoProfile",
                    "-ExecutionPolicy",
                    "Bypass",
                    "-Command",
                    command
            );

            processBuilder.redirectErrorStream(true);

            Process process = processBuilder.start();

            new Thread(() -> {
                try {
                    process.waitFor();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }).start();

        } catch (Exception e) {
            System.out.println("Speech error: " + e.getMessage());
        }
    }
}