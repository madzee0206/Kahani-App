package kahani.accessibility;

public class UnicodeBrailleSupport implements BrailleSupport {

    private boolean available = true;

    @Override
    public boolean isAvailable() {
        return available;
    }

    @Override
    public void displayText(String text) {

        if (text == null || text.trim().isEmpty()) {
            return;
        }

        String braille = convertToBraille(text);

        System.out.println("----- KAHANI BRAILLE OUTPUT -----");
        System.out.println(braille);
        System.out.println("---------------------------------");
    }

    @Override
    public void clear() {
        System.out.println("Braille display cleared.");
    }

    @Override
    public void close() {
        available = false;
    }

    private String convertToBraille(String text) {

        StringBuilder result = new StringBuilder();

        for (char c : text.toLowerCase().toCharArray()) {

            switch (c) {

                case 'a': result.append("⠁"); break;
                case 'b': result.append("⠃"); break;
                case 'c': result.append("⠉"); break;
                case 'd': result.append("⠙"); break;
                case 'e': result.append("⠑"); break;
                case 'f': result.append("⠋"); break;
                case 'g': result.append("⠛"); break;
                case 'h': result.append("⠓"); break;
                case 'i': result.append("⠊"); break;
                case 'j': result.append("⠚"); break;

                case 'k': result.append("⠅"); break;
                case 'l': result.append("⠇"); break;
                case 'm': result.append("⠍"); break;
                case 'n': result.append("⠝"); break;
                case 'o': result.append("⠕"); break;
                case 'p': result.append("⠏"); break;
                case 'q': result.append("⠟"); break;
                case 'r': result.append("⠗"); break;
                case 's': result.append("⠎"); break;
                case 't': result.append("⠞"); break;

                case 'u': result.append("⠥"); break;
                case 'v': result.append("⠧"); break;
                case 'w': result.append("⠺"); break;
                case 'x': result.append("⠭"); break;
                case 'y': result.append("⠽"); break;
                case 'z': result.append("⠵"); break;

                case ' ': result.append(" "); break;
                case '.': result.append("⠲"); break;
                case ',': result.append("⠂"); break;
                case '?': result.append("⠦"); break;
                case '!': result.append("⠖"); break;

                default: result.append(c);
            }
        }

        return result.toString();
    }
}