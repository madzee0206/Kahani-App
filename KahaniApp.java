package kahani.app;

import kahani.accessibility.AccessibilityService;
import kahani.data.LearningDataStore;
import kahani.data.LearningRecord;
import kahani.ml.DataMiner;
import kahani.ml.StoryRecommender;
import kahani.optimization.GradientDescent;
import kahani.optimization.OptimizationResult;
import kahani.model.Scene;
import kahani.model.Story;
import kahani.model.StoryRepository;
import kahani.quiz.Quiz;
import kahani.quiz.QuizQuestion;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * KAHANI - Stories Beyond Words
 *
 * Child-facing accessible storytelling application.
 * The technical ML and optimization components remain in the backend and
 * are intentionally not exposed as child-facing menu items.
 */
public class KahaniApp extends JFrame {

    // ---------- Base theme ----------
    private static final Color BASE_BG = new Color(18, 17, 38);
    private static final Color BASE_BG_2 = new Color(27, 24, 53);
    private static final Color BASE_PANEL = new Color(37, 33, 68);
    private static final Color BASE_PANEL_2 = new Color(47, 42, 83);
    private static final Color BASE_PURPLE = new Color(153, 108, 255);
    private static final Color BASE_PURPLE_2 = new Color(109, 73, 204);
    private static final Color BASE_PINK = new Color(255, 112, 185);
    private static final Color BASE_BLUE = new Color(87, 174, 255);
    private static final Color BASE_MINT = new Color(91, 218, 181);
    private static final Color BASE_YELLOW = new Color(255, 204, 86);
    private static final Color BASE_TEXT = new Color(245, 242, 255);
    private static final Color BASE_MUTED = new Color(177, 169, 202);
    private static final Color BASE_BORDER = new Color(74, 67, 112);

    private final StoryRepository repository = new StoryRepository();
    private final AccessibilityService accessibility = new AccessibilityService();
    private final LearningDataStore learningStore = new LearningDataStore();
    private final DataMiner dataMiner = new DataMiner();
    private final StoryRecommender recommender = new StoryRecommender();
    private final GradientDescent optimizer = new GradientDescent();
    private final List<Story> stories;

    private JPanel content;
    private JPanel headerLeft;
    private JLabel pageTitle;
    private JLabel pageSubtitle;

    private boolean largeText = false;
    private boolean highContrast = false;
    private int currentAge = 8;
    private String preferredGenre = "Animals";

    private int storiesCompleted = 0;
    private int totalQuizScore = 0;
    private int totalQuestions = 0;

    public KahaniApp() {
        stories = repository.getAllStories();

        setTitle("KAHANI - A Little World of Stories");
        setSize(1220, 760);
        setMinimumSize(new Dimension(1050, 680));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        loadExistingLearningData();
        buildInterface();
        showHome();
    }

    private void loadExistingLearningData() {
        try {
            learningStore.loadCsv("kahani_learning_data.csv");
        } catch (IOException ignored) {
            // A new installation can start without a CSV file.
        }
    }

    // ---------- Theme helpers ----------

    private Color bg() {
        return highContrast ? Color.BLACK : BASE_BG;
    }

    private Color bg2() {
        return highContrast ? new Color(12, 12, 12) : BASE_BG_2;
    }

    private Color panel() {
        return highContrast ? new Color(20, 20, 20) : BASE_PANEL;
    }

    private Color panel2() {
        return highContrast ? new Color(35, 35, 35) : BASE_PANEL_2;
    }

    private Color purple() {
        return highContrast ? new Color(255, 215, 0) : BASE_PURPLE;
    }

    private Color purple2() {
        return highContrast ? new Color(255, 180, 0) : BASE_PURPLE_2;
    }

    private Color pink() {
        return highContrast ? Color.YELLOW : BASE_PINK;
    }

    private Color blue() {
        return highContrast ? Color.CYAN : BASE_BLUE;
    }

    private Color mint() {
        return highContrast ? Color.GREEN : BASE_MINT;
    }

    private Color yellow() {
        return highContrast ? Color.YELLOW : BASE_YELLOW;
    }

    private Color textColor() {
        return Color.WHITE;
    }

    private Color muted() {
        return highContrast ? new Color(225, 225, 225) : BASE_MUTED;
    }

    private Color borderColor() {
        return highContrast ? Color.WHITE : BASE_BORDER;
    }

    // ---------- Main layout ----------

    private void buildInterface() {
        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(bg());

        root.add(createSidebar(), BorderLayout.WEST);

        JPanel right = new JPanel(new BorderLayout());
        right.setBackground(bg());
        right.add(createTopBar(), BorderLayout.NORTH);

        content = new JPanel(new BorderLayout());
        content.setBackground(bg());
        content.setBorder(new EmptyBorder(20, 25, 22, 25));
        right.add(content, BorderLayout.CENTER);

        root.add(right, BorderLayout.CENTER);
        setContentPane(root);
    }

    private JPanel createSidebar() {
        JPanel side = new JPanel(new BorderLayout());
        side.setPreferredSize(new Dimension(205, 0));
        side.setBackground(highContrast ? Color.BLACK : new Color(14, 13, 30));
        side.setBorder(new EmptyBorder(24, 16, 20, 16));

        JPanel top = new JPanel();
        top.setOpaque(false);
        top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));

        JLabel logo = label("KAHANI", largeText ? 29 : 27, true, textColor());
        JLabel logoSub = label("STORIES BEYOND WORDS", 9, true, purple());
        logo.setAlignmentX(Component.LEFT_ALIGNMENT);
        logoSub.setAlignmentX(Component.LEFT_ALIGNMENT);

        top.add(logo);
        top.add(Box.createVerticalStrut(2));
        top.add(logoSub);
        top.add(Box.createVerticalStrut(28));

        top.add(sideButton("⌂  HOME", "A little world of stories", e -> showHome()));
        top.add(sideButton("▣  STORY LIBRARY", "Explore stories", e -> showStories()));
        top.add(sideButton("◉  ACCESSIBILITY", "See, hear and read", e -> showAccessibility()));
        top.add(sideButton("▥  MY PROGRESS", "Learning journey", e -> showProgress()));

        side.add(top, BorderLayout.NORTH);

        JPanel bottom = new JPanel();
        bottom.setOpaque(false);
        bottom.setLayout(new BoxLayout(bottom, BoxLayout.Y_AXIS));

        JLabel help = label("Accessible story\nlearning", 11, false, muted());
        help.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel status = label("●  Learning mode", 11, true, mint());
        status.setAlignmentX(Component.LEFT_ALIGNMENT);

        bottom.add(help);
        bottom.add(Box.createVerticalStrut(12));
        bottom.add(status);
        side.add(bottom, BorderLayout.SOUTH);

        return side;
    }

    private JButton sideButton(String title, String hint, java.awt.event.ActionListener action) {
        String html = "<html><div style='text-align:left'><b>" + title +
                "</b><br><font size='2' color='#C9C2DE'>" + hint + "</font></div></html>";

        KahaniButton button = new KahaniButton(
                html,
                highContrast ? new Color(18, 18, 18) : new Color(22, 20, 43),
                highContrast ? new Color(50, 50, 50) : new Color(55, 45, 92),
                textColor(),
                12
        );
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 62));
        button.setPreferredSize(new Dimension(170, 62));
        button.setBorder(new EmptyBorder(8, 11, 8, 11));
        button.addActionListener(action);
        return button;
    }

    private JPanel createTopBar() {
        JPanel bar = new JPanel(new BorderLayout());
        bar.setBackground(bg());
        bar.setBorder(new EmptyBorder(17, 25, 10, 10));

        headerLeft = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        headerLeft.setOpaque(false);

        pageTitle = label("Welcome to KAHANI", largeText ? 24 : 22, true, textColor());
        pageSubtitle = label("", 12, false, muted());
        headerLeft.add(pageTitle);
        headerLeft.add(pageSubtitle);

        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 9, 0));
        right.setOpaque(false);

        JButton textButton = topButton(largeText ? "Large text: ON" : "Large text");
        textButton.addActionListener(e -> {
            largeText = !largeText;
            accessibility.setLargeText(largeText);
            rebuildAndShow("accessibility");
        });

        JButton contrast = topButton(highContrast ? "Contrast: ON" : "High contrast");
        contrast.addActionListener(e -> {
            highContrast = !highContrast;
            rebuildAndShow("accessibility");
        });

        right.add(textButton);
        right.add(contrast);
        bar.add(headerLeft, BorderLayout.WEST);
        bar.add(right, BorderLayout.EAST);
        return bar;
    }

    private JButton topButton(String text) {
        return new KahaniButton(text, panel2(), highContrast ? new Color(65, 65, 65) : new Color(67, 56, 112), textColor(), 10);
    }

    private void rebuildAndShow(String page) {
        buildInterface();
        switch (page) {
            case "stories" -> showStories();
            case "accessibility" -> showAccessibility();
            case "progress" -> showProgress();
            default -> showHome();
        }
    }

    // ---------- HOME ----------

    private void showHome() {
        setHeader("A little world of stories", "Stories, visual learning and accessible play for every child.");
        content.removeAll();

        JPanel page = new JPanel(new BorderLayout(0, 18));
        page.setOpaque(false);

        page.add(createHero(), BorderLayout.NORTH);

        JPanel title = new JPanel(new BorderLayout());
        title.setOpaque(false);
        title.add(label("Continue exploring", largeText ? 22 : 20, true, textColor()), BorderLayout.NORTH);
        title.add(label("Choose a story and begin your next little adventure.", 12, false, muted()), BorderLayout.SOUTH);
        page.add(title, BorderLayout.CENTER);

        List<Story> recommended = getRecommendedStories();
        int cardCount = Math.min(3, Math.max(1, recommended.size()));
        JPanel cards = new JPanel(new GridLayout(1, cardCount, 16, 0));
        cards.setOpaque(false);
        for (int i = 0; i < cardCount; i++) {
            cards.add(storyCard(recommended.get(i), false));
        }
        page.add(cards, BorderLayout.SOUTH);

        content.add(page, BorderLayout.CENTER);
        refresh();
    }

    private List<Story> getRecommendedStories() {
        List<LearningRecord> records = learningStore.getRecords();
        return recommender.recommend(currentAge, preferredGenre, stories, records);
    }

    private OptimizationResult runLearningOptimization() {
        List<LearningRecord> records = learningStore.getRecords();
        if (records.size() < 2) {
            return null;
        }

        double[] x = new double[records.size()];
        double[] y = new double[records.size()];

        for (int i = 0; i < records.size(); i++) {
            LearningRecord record = records.get(i);
            x[i] = record.getTimeSpentMinutes();
            y[i] = record.getQuizAccuracy();
        }

        double weight = 0.0;
        double bias = dataMiner.averageQuizAccuracy(records);

        for (int i = 0; i < 25; i++) {
            double[] updated = optimizer.linearRegressionStep(x, y, weight, bias, 0.01);
            weight = updated[0];
            bias = updated[1];
        }

        double averageTime = dataMiner.averageTime(records);
        double predictedAccuracy = weight * averageTime + bias;

        return new OptimizationResult(
                averageTime,
                predictedAccuracy,
                dataMiner.averageQuizAccuracy(records),
                predictedAccuracy,
                25
        );
    }

    private JPanel createHero() {
        RoundedPanel hero = new RoundedPanel(highContrast ? new Color(22, 22, 22) : new Color(40, 34, 75), 27);
        hero.setLayout(new BorderLayout(25, 0));
        hero.setBorder(new EmptyBorder(23, 27, 23, 27));

        Story featured = stories.isEmpty() ? null : stories.get(0);
        if (featured == null) return hero;

        JPanel text = new JPanel();
        text.setOpaque(false);
        text.setLayout(new BoxLayout(text, BoxLayout.Y_AXIS));

        text.add(label("FEATURED STORY", 10, true, purple()));
        text.add(Box.createVerticalStrut(7));
        text.add(label(featured.getTitle(), largeText ? 31 : 28, true, textColor()));
        text.add(Box.createVerticalStrut(8));
        text.add(htmlLabel(featured.getLearningMessage(), 13, muted(), 500));
        text.add(Box.createVerticalStrut(12));
        text.add(label("Ages " + featured.getMinAge() + "–" + featured.getMaxAge() + "  •  " + featured.getDifficulty(), 11, true, mint()));
        text.add(Box.createVerticalStrut(16));

        JButton read = primaryButton("Read this story  →");
        read.addActionListener(e -> showStory(featured));
        text.add(read);

        StoryArtPanel art = new StoryArtPanel(featured.getId(), 320, 185);
        hero.add(text, BorderLayout.CENTER);
        hero.add(art, BorderLayout.EAST);
        return hero;
    }

    // ---------- STORY LIBRARY ----------

    private void showStories() {
        setHeader("Story Library", "Find a story, explore its scenes and try the little quiz.");
        content.removeAll();

        JPanel page = new JPanel(new BorderLayout(0, 15));
        page.setOpaque(false);

        JPanel filters = new RoundedPanel(panel(), 17);
        filters.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        filters.setBorder(new EmptyBorder(2, 8, 2, 8));

        JLabel searchLabel = label("Search", 12, true, textColor());
        JTextField search = new JTextField(18);
        search.setToolTipText("Search by story title");
        styleTextField(search);

        JLabel ageLabel = label("Age", 12, true, textColor());
        JComboBox<Integer> ageBox = new JComboBox<>(new Integer[]{6, 7, 8, 9, 10, 11, 12});
        ageBox.setSelectedItem(currentAge);
        styleCombo(ageBox);

        JLabel genreLabel = label("Genre", 12, true, textColor());
        JComboBox<String> genreBox = new JComboBox<>(new String[]{"All", "Animals", "Adventure"});
        styleCombo(genreBox);

        JButton all = smallButton("Show all");
        JButton suitable = primaryButton("For my age");

        filters.add(searchLabel);
        filters.add(search);
        filters.add(ageLabel);
        filters.add(ageBox);
        filters.add(genreLabel);
        filters.add(genreBox);
        filters.add(all);
        filters.add(suitable);

        page.add(filters, BorderLayout.NORTH);

        JPanel grid = new JPanel(new GridLayout(0, 3, 16, 16));
        grid.setOpaque(false);

        JScrollPane scroll = new JScrollPane(grid);
        scroll.setBorder(null);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        page.add(scroll, BorderLayout.CENTER);

        JPanel note = new RoundedPanel(panel(), 16);
        note.setLayout(new BorderLayout(12, 0));
        note.setBorder(new EmptyBorder(12, 16, 12, 16));
        note.add(label("ACCESSIBILITY FIRST", 10, true, mint()), BorderLayout.WEST);
        note.add(label("Every story contains readable scenes, visual descriptions and sign-language cues.", 11, false, muted()), BorderLayout.CENTER);
        page.add(note, BorderLayout.SOUTH);

        Runnable update = () -> {
            String query = search.getText().trim().toLowerCase();
            int selectedAge = (Integer) ageBox.getSelectedItem();
            String selectedGenre = String.valueOf(genreBox.getSelectedItem());

            List<Story> filtered = new ArrayList<>();
            for (Story story : stories) {
                boolean matchesText = query.isEmpty() || story.getTitle().toLowerCase().contains(query);
                boolean matchesGenre = selectedGenre.equals("All") || story.getGenre().equalsIgnoreCase(selectedGenre);
                boolean matchesAge = story.matchesAge(selectedAge);
                if (matchesText && matchesGenre && matchesAge) {
                    filtered.add(story);
                }
            }
            if (selectedGenre.equals("All") && query.isEmpty() && selectedAge == currentAge) {
                // The normal age view remains useful, but Show all can override it.
            }
            fillStoryGrid(grid, filtered);
        };

        Runnable showAll = () -> {
            String query = search.getText().trim().toLowerCase();
            String selectedGenre = String.valueOf(genreBox.getSelectedItem());
            List<Story> filtered = new ArrayList<>();
            for (Story story : stories) {
                boolean matchesText = query.isEmpty() || story.getTitle().toLowerCase().contains(query);
                boolean matchesGenre = selectedGenre.equals("All") || story.getGenre().equalsIgnoreCase(selectedGenre);
                if (matchesText && matchesGenre) filtered.add(story);
            }
            fillStoryGrid(grid, filtered);
        };

        all.addActionListener(e -> showAll.run());
        suitable.addActionListener(e -> {
            currentAge = (Integer) ageBox.getSelectedItem();
            update.run();
        });
        search.getDocument().addDocumentListener(new SimpleDocumentListener(update));
        genreBox.addActionListener(e -> update.run());

        // Initial library order is personalized by the existing ML recommender.
        fillStoryGrid(grid, getRecommendedStories());

        content.add(page, BorderLayout.CENTER);
        refresh();
    }

    private void fillStoryGrid(JPanel grid, List<Story> list) {
        grid.removeAll();
        int rows = Math.max(1, (int) Math.ceil(list.size() / 3.0));
        grid.setLayout(new GridLayout(rows, 3, 16, 16));

        if (list.isEmpty()) {
            RoundedPanel empty = new RoundedPanel(panel(), 20);
            empty.setLayout(new BoxLayout(empty, BoxLayout.Y_AXIS));
            empty.setBorder(new EmptyBorder(28, 28, 28, 28));
            empty.add(label("No stories found", 18, true, textColor()));
            empty.add(Box.createVerticalStrut(8));
            empty.add(label("Try another age, genre or search word.", 12, false, muted()));
            grid.add(empty);
        } else {
            for (Story story : list) {
                grid.add(storyCard(story, true));
            }
            int emptyCells = rows * 3 - list.size();
            for (int i = 0; i < emptyCells; i++) grid.add(new JPanel() {{ setOpaque(false); }});
        }
        grid.revalidate();
        grid.repaint();
    }

    private JPanel storyCard(Story story, boolean libraryCard) {
        RoundedPanel card = new RoundedPanel(panel(), 21);
        card.setLayout(new BorderLayout(0, 9));
        card.setBorder(new EmptyBorder(11, 11, 13, 11));
        card.setPreferredSize(new Dimension(300, libraryCard ? 355 : 315));

        StoryArtPanel art = new StoryArtPanel(story.getId(), libraryCard ? 270 : 250, libraryCard ? 150 : 140);
        card.add(art, BorderLayout.NORTH);

        JPanel body = new JPanel();
        body.setOpaque(false);
        body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));

        body.add(label(story.getGenre().toUpperCase(), 9, true, pink()));
        body.add(Box.createVerticalStrut(3));
        body.add(label(story.getTitle(), largeText ? 18 : 17, true, textColor()));
        body.add(Box.createVerticalStrut(5));
        body.add(label("Ages " + story.getMinAge() + "–" + story.getMaxAge() + "  •  " + story.getDifficulty(), 10, false, muted()));
        body.add(Box.createVerticalStrut(3));
        body.add(label(story.getScenes().size() + " scenes  •  visual learning", 10, false, muted()));
        body.add(Box.createVerticalStrut(7));
        body.add(htmlLabel(story.getLearningMessage(), 10, muted(), 255));
        body.add(Box.createVerticalStrut(9));

        JButton read = primaryButton("Open story  →");
        read.addActionListener(e -> showStory(story));
        body.add(read);

        card.add(body, BorderLayout.CENTER);
        return card;
    }

    // ---------- STORY READER ----------

    private void showStory(Story story) {
        setHeader(story.getTitle(), story.getGenre() + " story  •  Ages " + story.getMinAge() + "–" + story.getMaxAge());
        content.removeAll();

        JPanel page = new JPanel(new BorderLayout(0, 13));
        page.setOpaque(false);

        JPanel top = new JPanel(new BorderLayout());
        top.setOpaque(false);
        top.add(label(story.getLearningMessage(), 12, false, muted()), BorderLayout.WEST);
        top.add(label(story.getScenes().size() + " scenes", 11, true, mint()), BorderLayout.EAST);
        page.add(top, BorderLayout.NORTH);

        RoundedPanel sceneArea = new RoundedPanel(bg2(), 25);
        sceneArea.setLayout(new BorderLayout(20, 0));
        sceneArea.setBorder(new EmptyBorder(19, 21, 19, 21));

        JLabel sceneTitle = label("", largeText ? 23 : 21, true, textColor());
        JTextArea sceneText = textArea("", largeText ? 19 : 15, textColor());
        JLabel visual = label("", 12, false, muted());
        JLabel sign = label("", 13, true, pink());
        JLabel progress = label("", 11, true, mint());

        JPanel left = new JPanel();
        left.setOpaque(false);
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
        left.add(sceneTitle);
        left.add(Box.createVerticalStrut(13));
        left.add(sceneText);
        left.add(Box.createVerticalStrut(15));
        left.add(visual);
        left.add(Box.createVerticalStrut(10));
        left.add(sign);
        left.add(Box.createVerticalStrut(12));
        left.add(progress);

        StoryArtPanel art = new StoryArtPanel(story.getId(), 335, 230);
        sceneArea.add(left, BorderLayout.CENTER);
        sceneArea.add(art, BorderLayout.EAST);
        page.add(sceneArea, BorderLayout.CENTER);

        JButton back = smallButton("← Story Library");
        JButton previous = smallButton("← Previous");
        JButton signButton = smallButton("🤟 Sign cue");
        JButton speak = smallButton("🔊 Read aloud");
        JButton braille = smallButton("⠃ Braille");
        JButton next = primaryButton("Next scene  →");
        JButton quiz = primaryButton("Take quiz");

        JPanel controls = new JPanel(new BorderLayout());
        controls.setOpaque(false);
        JPanel leftButtons = new JPanel(new FlowLayout(FlowLayout.LEFT, 7, 0));
        leftButtons.setOpaque(false);
        leftButtons.add(back);
        leftButtons.add(previous);
        leftButtons.add(signButton);
        leftButtons.add(speak);
        leftButtons.add(braille);

        JPanel rightButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 7, 0));
        rightButtons.setOpaque(false);
        rightButtons.add(next);
        rightButtons.add(quiz);
        controls.add(leftButtons, BorderLayout.WEST);
        controls.add(rightButtons, BorderLayout.EAST);
        page.add(controls, BorderLayout.SOUTH);

        final int[] index = {0};
        updateScene(story, 0, sceneTitle, sceneText, visual, sign, progress, art, previous, next);

        previous.addActionListener(e -> {
            if (index[0] > 0) {
                index[0]--;
                updateScene(story, index[0], sceneTitle, sceneText, visual, sign, progress, art, previous, next);
            }
        });

        next.addActionListener(e -> {
            if (index[0] < story.getScenes().size() - 1) {
                index[0]++;
                updateScene(story, index[0], sceneTitle, sceneText, visual, sign, progress, art, previous, next);
            } else {
                JOptionPane.showMessageDialog(this,
                        "You reached the end of the story!\n\nNow let's see what you remember.",
                        "Story complete", JOptionPane.INFORMATION_MESSAGE);
                showQuiz(story);
            }
        });

        back.addActionListener(e -> showStories());
        quiz.addActionListener(e -> showQuiz(story));

        signButton.addActionListener(e -> {
            Scene scene = story.getScenes().get(index[0]);
            JOptionPane.showMessageDialog(this,
                    "SIGN-LANGUAGE CUE\n\n" + accessibility.getSignRepresentation(scene) +
                            "\n\nVISUAL IDEA\n" + scene.getVisualCue(),
                    "Visual Sign Support", JOptionPane.INFORMATION_MESSAGE);
        });

        speak.addActionListener(e -> {
            Scene scene = story.getScenes().get(index[0]);
            accessibility.setSpeechEnabled(true);
            accessibility.speak(scene);
            JOptionPane.showMessageDialog(this,
                    "Speech support received the current scene text.\n\n" + scene.getText() +
                            "\n\nThe accessibility service has processed this scene.",
                    "Speech Support", JOptionPane.INFORMATION_MESSAGE);
        });

        braille.addActionListener(e -> {
            Scene scene = story.getScenes().get(index[0]);
            accessibility.displayBraille(scene.getText());
            JOptionPane.showMessageDialog(this,
                    "Braille support received the current scene text.\n\n" +
                            "The Unicode Braille output has been sent to the Braille support layer.\n\n" +
                            "Check the console to view the Braille representation.",
                    "Braille Support", JOptionPane.INFORMATION_MESSAGE);
        });

        content.add(page, BorderLayout.CENTER);
        refresh();
    }

    private void updateScene(Story story, int index, JLabel title, JTextArea text,
                             JLabel visual, JLabel sign, JLabel progress, StoryArtPanel art,
                             JButton previous, JButton next) {
        Scene scene = story.getScenes().get(index);
        title.setText("Scene " + (index + 1) + "  •  " + scene.getTitle());
        text.setText(scene.getText());
        visual.setText("VISUAL DESCRIPTION  •  " + scene.getVisualCue());
        sign.setText("SIGN CUE  •  " + scene.getSignCue());
        progress.setText("SCENE " + (index + 1) + " OF " + story.getScenes().size());
        art.setSceneIndex(index);
        previous.setEnabled(index > 0);
        previous.setForeground(index > 0 ? textColor() : muted());
        next.setText(index == story.getScenes().size() - 1 ? "Finish story  →" : "Next scene  →");
        art.repaint();
    }

    // ---------- QUIZ ----------

    private void showQuiz(Story story) {
        setHeader("Little Quiz", "A short activity to help the child remember the story.");
        content.removeAll();

        JPanel page = new JPanel(new BorderLayout(0, 13));
        page.setOpaque(false);

        RoundedPanel intro = new RoundedPanel(panel(), 18);
        intro.setLayout(new BorderLayout());
        intro.setBorder(new EmptyBorder(13, 17, 13, 17));
        intro.add(label("What do you remember about " + story.getTitle() + "?", largeText ? 20 : 18, true, textColor()), BorderLayout.CENTER);
        intro.add(label("2 questions", 11, true, mint()), BorderLayout.EAST);
        page.add(intro, BorderLayout.NORTH);

        Quiz quiz = new Quiz(story.getId());
        List<QuizQuestion> questions = quiz.getQuestions();
        List<ButtonGroup> groups = new ArrayList<>();

        JPanel list = new JPanel();
        list.setOpaque(false);
        list.setLayout(new BoxLayout(list, BoxLayout.Y_AXIS));

        for (int i = 0; i < questions.size(); i++) {
            QuizQuestion q = questions.get(i);
            RoundedPanel box = new RoundedPanel(panel(), 18);
            box.setLayout(new BoxLayout(box, BoxLayout.Y_AXIS));
            box.setBorder(new EmptyBorder(14, 16, 14, 16));
            box.setAlignmentX(Component.LEFT_ALIGNMENT);

            box.add(label((i + 1) + ". " + q.getQuestion(), largeText ? 16 : 14, true, textColor()));
            box.add(Box.createVerticalStrut(9));

            ButtonGroup group = new ButtonGroup();
            String[] options = q.getOptions();
            for (int j = 0; j < options.length; j++) {
                JRadioButton radio = new JRadioButton(options[j]);
                radio.setActionCommand(String.valueOf(j));
                radio.setForeground(textColor());
                radio.setBackground(panel());
                radio.setFont(new Font("SansSerif", Font.PLAIN, largeText ? 16 : 13));
                radio.setFocusPainted(true);
                group.add(radio);
                box.add(radio);
                box.add(Box.createVerticalStrut(4));
            }
            groups.add(group);
            list.add(box);
            list.add(Box.createVerticalStrut(10));
        }

        JScrollPane scroll = new JScrollPane(list);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(bg());
        scroll.getVerticalScrollBar().setUnitIncrement(15);
        page.add(scroll, BorderLayout.CENTER);

        JButton back = smallButton("← Back to story");
        JButton submit = primaryButton("Finish quiz");
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        bottom.setOpaque(false);
        bottom.add(back);
        bottom.add(submit);
        page.add(bottom, BorderLayout.SOUTH);

        back.addActionListener(e -> showStory(story));
        submit.addActionListener(e -> finishQuiz(story, quiz, groups));

        content.add(page, BorderLayout.CENTER);
        refresh();
    }

    private void finishQuiz(Story story, Quiz quiz, List<ButtonGroup> groups) {
        int[] answers = new int[groups.size()];
        Arrays.fill(answers, -1);

        for (int i = 0; i < groups.size(); i++) {
            ButtonModel selected = groups.get(i).getSelection();
            if (selected != null) answers[i] = Integer.parseInt(selected.getActionCommand());
        }

        int score = quiz.calculateScore(answers);
        storiesCompleted++;
        totalQuizScore += score;
        totalQuestions += quiz.getQuestions().size();
        preferredGenre = story.getGenre();

        learningStore.addRecord(new LearningRecord(
                101, story.getId(), currentAge, story.getScenes().size(),
                score, quiz.getQuestions().size(), 1.0, story.getGenre()
        ));

        try {
            learningStore.saveCsv("kahani_learning_data.csv");
        } catch (IOException ignored) {
        }

        int percent = quiz.getQuestions().isEmpty() ? 0 :
                (int) Math.round(score * 100.0 / quiz.getQuestions().size());

        JOptionPane.showMessageDialog(this,
                "Your score: " + score + " / " + quiz.getQuestions().size() +
                        "\nAccuracy: " + percent + "%" +
                        "\n\nGreat work! Your learning record has been saved.",
                "Quiz Complete", JOptionPane.INFORMATION_MESSAGE);

        showProgress();
    }

    // ---------- ACCESSIBILITY ----------

    private void showAccessibility() {
        setHeader("Accessibility Centre", "Choose how KAHANI presents stories so more children can experience them comfortably.");
        content.removeAll();

        JPanel page = new JPanel(new BorderLayout(0, 15));
        page.setOpaque(false);

        RoundedPanel intro = new RoundedPanel(highContrast ? new Color(20, 20, 20) : new Color(41, 35, 77), 23);
        intro.setLayout(new BorderLayout(20, 0));
        intro.setBorder(new EmptyBorder(20, 22, 20, 22));

        JPanel copy = new JPanel();
        copy.setOpaque(false);
        copy.setLayout(new BoxLayout(copy, BoxLayout.Y_AXIS));
        copy.add(label("Stories should not have only one way to be experienced.", largeText ? 20 : 18, true, textColor()));
        copy.add(Box.createVerticalStrut(8));
        copy.add(htmlLabel("KAHANI combines readable text, visual descriptions, sign-language cues, large text and speech-support preparation.", 13, muted(), 600));
        intro.add(copy, BorderLayout.CENTER);
        intro.add(new AccessibilityArt(), BorderLayout.EAST);
        page.add(intro, BorderLayout.NORTH);

        JPanel cards = new JPanel(new GridLayout(2, 2, 14, 14));
        cards.setOpaque(false);

        cards.add(accessCard("LARGE TEXT", "Increase text size across story and quiz screens.", largeText, e -> {
            largeText = !largeText;
            accessibility.setLargeText(largeText);
            rebuildAndShow("accessibility");
        }));

        cards.add(accessCard("HIGH CONTRAST", "Increase visual separation between text, controls and backgrounds.", highContrast, e -> {
            highContrast = !highContrast;
            rebuildAndShow("accessibility");
        }));

        cards.add(accessCard("SIGN SUPPORT", "Each scene contains a structured sign-language cue and a visual description.", true, e -> {
            Story story = stories.isEmpty() ? null : stories.get(0);
            if (story != null) {
                Scene scene = story.getScenes().get(0);
                JOptionPane.showMessageDialog(this,
                        "Example visual support\n\nSIGN CUE\n" + scene.getSignCue() +
                                "\n\nVISUAL DESCRIPTION\n" + scene.getVisualCue(),
                        "Sign & Visual Support", JOptionPane.INFORMATION_MESSAGE);
            }
        }));

        cards.add(accessCard("SPEECH SUPPORT", "Current story text can be passed to the speech-support service.", accessibility.isSpeechEnabled(), e -> {
            accessibility.setSpeechEnabled(!accessibility.isSpeechEnabled());
            showAccessibility();
        }));

        cards.add(accessCard("BRAILLE SUPPORT", "Convert the current story text into Unicode Braille for software testing and accessibility development.", accessibility.isBrailleEnabled(), e -> {
            if (!accessibility.isBrailleEnabled()) {
                JOptionPane.showMessageDialog(this,
                        "Braille support is currently unavailable.",
                        "Braille Support", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            Story story = stories.isEmpty() ? null : stories.get(0);
            if (story != null && !story.getScenes().isEmpty()) {
                accessibility.displayBraille(story.getScenes().get(0).getText());
                JOptionPane.showMessageDialog(this,
                        "Braille support is ready.\n\nThe first scene of the example story was converted to Unicode Braille.\nCheck the console for the output.",
                        "Braille Support", JOptionPane.INFORMATION_MESSAGE);
            }
        }));

        page.add(cards, BorderLayout.CENTER);

        RoundedPanel status = new RoundedPanel(panel(), 17);
        status.setLayout(new BorderLayout());
        status.setBorder(new EmptyBorder(13, 16, 13, 16));
        status.add(label("CURRENT ACCESSIBILITY SETTINGS", 10, true, mint()), BorderLayout.WEST);
        status.add(label(
                "Large text: " + (largeText ? "ON" : "OFF") +
                        "   •   High contrast: " + (highContrast ? "ON" : "OFF") +
                        "   •   Speech support: " + (accessibility.isSpeechEnabled() ? "ON" : "READY") +
                        "   •   Braille support: " + (accessibility.isBrailleEnabled() ? "READY" : "OFF") +
                        "   •   Sign cues: AVAILABLE",
                11, false, muted()), BorderLayout.CENTER);
        page.add(status, BorderLayout.SOUTH);

        content.add(page, BorderLayout.CENTER);
        refresh();
    }

    private JPanel accessCard(String title, String text, boolean active, java.awt.event.ActionListener action) {
        RoundedPanel card = new RoundedPanel(panel(), 20);
        card.setLayout(new BorderLayout(0, 10));
        card.setBorder(new EmptyBorder(17, 18, 17, 18));

        card.add(label(title, 14, true, active ? mint() : textColor()), BorderLayout.NORTH);
        card.add(htmlLabel(text, 12, muted(), 400), BorderLayout.CENTER);

        JButton button = primaryButton(active ? "Enabled ✓" : "Enable");
        button.addActionListener(action);
        card.add(button, BorderLayout.SOUTH);
        return card;
    }

    // ---------- PROGRESS ----------

    private void showProgress() {
        setHeader("My Progress", "A simple learning journey showing stories completed and quiz activity.");
        content.removeAll();

        JPanel page = new JPanel(new BorderLayout(0, 16));
        page.setOpaque(false);

        int sessionAccuracy = totalQuestions == 0 ? 0 :
                (int) Math.round(totalQuizScore * 100.0 / totalQuestions);
        double minedAccuracy = dataMiner.averageQuizAccuracy(learningStore.getRecords()) * 100.0;
        double averageTime = dataMiner.averageTime(learningStore.getRecords());
        OptimizationResult optimizationResult = runLearningOptimization();

        JPanel stats = new JPanel(new GridLayout(1, 4, 12, 0));
        stats.setOpaque(false);
        stats.add(statCard("STORIES", String.valueOf(storiesCompleted), "completed this session", purple()));
        stats.add(statCard("QUIZ", sessionAccuracy + "%", "current session accuracy", pink()));
        stats.add(statCard("RECORDS", String.valueOf(learningStore.size()), "learning records", blue()));
        stats.add(statCard("FAVOURITE", preferredGenre, "recent story type", mint()));
        page.add(stats, BorderLayout.NORTH);

        RoundedPanel analytics = new RoundedPanel(panel(), 22);
        analytics.setLayout(new BorderLayout(25, 0));
        analytics.setBorder(new EmptyBorder(19, 21, 19, 21));

        JPanel left = new JPanel();
        left.setOpaque(false);
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
        left.add(label("Learning snapshot", largeText ? 20 : 18, true, textColor()));
        left.add(Box.createVerticalStrut(12));
        left.add(label("Stored-data quiz accuracy", 12, false, muted()));
        left.add(label(String.format("%.1f%%", minedAccuracy), largeText ? 31 : 28, true, purple()));
        left.add(Box.createVerticalStrut(10));
        left.add(label("Average recorded time", 12, false, muted()));
        left.add(label(String.format("%.1f minutes", averageTime), 21, true, textColor()));
        left.add(Box.createVerticalStrut(12));
        left.add(label("Progress is built from completed story and quiz records.", 11, false, muted()));
        left.add(Box.createVerticalStrut(10));
        if (optimizationResult == null) {
            left.add(label("Optimization model: waiting for more learning records.", 11, false, muted()));
        } else {
            double optimizedAccuracy = Math.max(0.0, Math.min(1.0, optimizationResult.getFinalLoss()));
            left.add(label("Optimization model: learning pattern tuned", 11, true, mint()));
            left.add(label(String.format("Estimated accuracy at average time: %.1f%%", optimizedAccuracy * 100.0), 11, false, muted()));
        }
        analytics.add(left, BorderLayout.CENTER);

        JPanel genres = new JPanel();
        genres.setOpaque(false);
        genres.setLayout(new BoxLayout(genres, BoxLayout.Y_AXIS));
        genres.add(label("Story preferences", 16, true, textColor()));
        genres.add(Box.createVerticalStrut(10));

        Map<String, Integer> counts = dataMiner.genreCounts(learningStore.getRecords());
        if (counts.isEmpty()) {
            genres.add(label("No stored learning records yet.", 12, false, muted()));
        } else {
            for (Map.Entry<String, Integer> entry : counts.entrySet()) {
                genres.add(label(entry.getKey() + "  •  " + entry.getValue() + " record(s)", 12, false, muted()));
                genres.add(Box.createVerticalStrut(7));
            }
        }
        analytics.add(genres, BorderLayout.EAST);
        page.add(analytics, BorderLayout.CENTER);

        RoundedPanel note = new RoundedPanel(highContrast ? new Color(25, 25, 25) : new Color(35, 32, 60), 17);
        note.setLayout(new BorderLayout(14, 0));
        note.setBorder(new EmptyBorder(13, 16, 13, 16));
        note.add(label("LEARNING DATA", 10, true, yellow()), BorderLayout.WEST);
        note.add(label("The backend records story and quiz activity so learning patterns can be studied without adding technical complexity to the child-facing interface.", 11, false, muted()), BorderLayout.CENTER);
        page.add(note, BorderLayout.SOUTH);

        content.add(page, BorderLayout.CENTER);
        refresh();
    }

    private JPanel statCard(String title, String value, String hint, Color accent) {
        RoundedPanel p = new RoundedPanel(panel(), 18);
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBorder(new EmptyBorder(14, 15, 14, 15));
        p.add(label(title, 9, true, accent));
        p.add(Box.createVerticalStrut(7));
        p.add(label(value, largeText ? 22 : 20, true, textColor()));
        p.add(Box.createVerticalStrut(4));
        p.add(label(hint, 10, false, muted()));
        return p;
    }

    // ---------- UI helpers ----------

    private void setHeader(String title, String subtitle) {
        if (pageTitle == null || headerLeft == null) return;
        pageTitle.setText(title);
        pageTitle.setFont(new Font("SansSerif", Font.BOLD, largeText ? 24 : 22));
        pageTitle.setForeground(textColor());
        pageSubtitle.setText("  " + subtitle);
        pageSubtitle.setFont(new Font("SansSerif", Font.PLAIN, largeText ? 13 : 12));
        pageSubtitle.setForeground(muted());
        headerLeft.revalidate();
        headerLeft.repaint();
    }

    private JLabel label(String text, int size, boolean bold, Color color) {
        JLabel l = new JLabel(text);
        l.setFont(new Font("SansSerif", bold ? Font.BOLD : Font.PLAIN, size));
        l.setForeground(color);
        return l;
    }

    private JLabel htmlLabel(String text, int size, Color color, int width) {
        String safe = text == null ? "" : text.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
        JLabel l = new JLabel("<html><div style='width:" + width + "px;line-height:145%;'>" + safe + "</div></html>");
        l.setFont(new Font("SansSerif", Font.PLAIN, size));
        l.setForeground(color);
        return l;
    }

    private JTextArea textArea(String text, int size, Color color) {
        JTextArea area = new JTextArea(text);
        area.setFont(new Font("SansSerif", Font.PLAIN, size));
        area.setForeground(color);
        area.setBackground(bg2());
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setEditable(false);
        area.setFocusable(false);
        area.setBorder(null);
        area.setRows(5);
        return area;
    }

    private void styleTextField(JTextField field) {
        field.setBackground(panel2());
        field.setForeground(textColor());
        field.setCaretColor(textColor());
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(borderColor()),
                new EmptyBorder(6, 8, 6, 8)
        ));
        field.setFont(new Font("SansSerif", Font.PLAIN, largeText ? 15 : 13));
    }

    private void styleCombo(JComboBox<?> box) {
        box.setBackground(panel2());
        box.setForeground(textColor());
        box.setFont(new Font("SansSerif", Font.PLAIN, largeText ? 14 : 12));
    }

    private JButton primaryButton(String text) {
        return new KahaniButton(text, purple2(), highContrast ? new Color(255, 210, 0) : new Color(132, 89, 239), Color.WHITE, 12);
    }

    private JButton smallButton(String text) {
        return new KahaniButton(text, panel2(), highContrast ? new Color(65, 65, 65) : new Color(75, 62, 125), textColor(), 11);
    }

    private void refresh() {
        content.revalidate();
        content.repaint();
    }

    // ---------- Main ----------

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            } catch (Exception ignored) {
            }
            new KahaniApp().setVisible(true);
        });
    }

    // ---------- Document listener ----------

    private static class SimpleDocumentListener implements javax.swing.event.DocumentListener {
        private final Runnable action;

        SimpleDocumentListener(Runnable action) {
            this.action = action;
        }

        public void insertUpdate(javax.swing.event.DocumentEvent e) { action.run(); }
        public void removeUpdate(javax.swing.event.DocumentEvent e) { action.run(); }
        public void changedUpdate(javax.swing.event.DocumentEvent e) { action.run(); }
    }

    // ---------- Custom button ----------

    private static class KahaniButton extends JButton {
        private final Color baseColor;
        private final Color hoverColor;
        private final Color textColor;
        private final int radius;
        private boolean hovering = false;

        KahaniButton(String text, Color baseColor, Color hoverColor, Color textColor, int radius) {
            super(text);
            this.baseColor = baseColor;
            this.hoverColor = hoverColor;
            this.textColor = textColor;
            this.radius = radius;

            setForeground(textColor);
            setFont(new Font("SansSerif", Font.BOLD, 12));
            setFocusPainted(false);
            setBorderPainted(false);
            setContentAreaFilled(false);
            setOpaque(false);
            setCursor(new Cursor(Cursor.HAND_CURSOR));
            setMargin(new Insets(5, 10, 5, 10));

            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    hovering = true;
                    repaint();
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    hovering = false;
                    repaint();
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            Color fill = isEnabled() ? (hovering ? hoverColor : baseColor) : new Color(70, 70, 70);
            Color fg = isEnabled() ? textColor : new Color(170, 170, 170);
            setForeground(fg);
            g2.setColor(fill);
            g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
            g2.setColor(new Color(255, 255, 255, 35));
            g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
            g2.dispose();
            super.paintComponent(g);
        }
    }

    // ---------- Rounded panel ----------

    private static class RoundedPanel extends JPanel {
        private final Color color;
        private final int radius;

        RoundedPanel(Color color, int radius) {
            this.color = color;
            this.radius = radius;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(color);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
            super.paintComponent(g2);
            g2.dispose();
        }
    }

    // ---------- Story artwork ----------

    private static class StoryArtPanel extends JPanel {
        private final int storyId;
        private final int preferredW;
        private final int preferredH;
        private int sceneIndex = 0;

        StoryArtPanel(int storyId, int w, int h) {
            this.storyId = storyId;
            this.preferredW = w;
            this.preferredH = h;
            setPreferredSize(new Dimension(w, h));
            setMinimumSize(new Dimension(180, 120));
            setOpaque(false);
        }

        void setSceneIndex(int index) {
            sceneIndex = index;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int w = getWidth();
            int h = getHeight();
            g2.setColor(new Color(26, 28, 58));
            g2.fillRoundRect(0, 0, w, h, 24, 24);

            // Stars
            g2.setColor(new Color(170, 145, 255));
            for (int i = 0; i < 9; i++) {
                int x = 18 + ((i * 61 + storyId * 19) % Math.max(30, w - 30));
                int y = 14 + ((i * 29 + sceneIndex * 17) % Math.max(25, h / 2));
                g2.fillOval(x, y, 3, 3);
            }

            if (storyId == 1) {
                drawLionMouse(g2, w, h);
            } else if (storyId == 2) {
                drawFox(g2, w, h);
            } else {
                drawForest(g2, w, h);
            }
            g2.dispose();
        }

        private void drawLionMouse(Graphics2D g2, int w, int h) {
            int groundY = h - 48;
            g2.setColor(new Color(40, 80, 67));
            g2.fillOval(w / 2 - 80, groundY - 12, 170, 50);

            g2.setColor(new Color(75, 154, 91));
            g2.fillRect(35, groundY - 78, 23, 80);
            g2.fillOval(18, groundY - 105, 60, 48);

            int cx = w / 2 + 4;
            int cy = groundY - 48;
            g2.setColor(new Color(238, 171, 62));
            g2.fillOval(cx - 45, cy - 45, 90, 90);
            g2.setColor(new Color(248, 190, 71));
            g2.fillOval(cx - 27, cy - 25, 54, 54);
            g2.setColor(new Color(43, 47, 62));
            g2.fillOval(cx - 12, cy - 3, 5, 5);
            g2.fillOval(cx + 11, cy - 3, 5, 5);

            int mx = w - 74;
            int my = groundY - 27;
            g2.setColor(new Color(188, 194, 204));
            g2.fillOval(mx - 18, my - 10, 37, 20);
            g2.setColor(new Color(242, 131, 176));
            g2.fillOval(mx + 9, my - 15, 10, 10);
        }

        private void drawFox(Graphics2D g2, int w, int h) {
            int groundY = h - 45;
            g2.setColor(new Color(76, 164, 96));
            for (int i = 0; i < 5; i++) {
                g2.fillOval(20 + i * 55, groundY - 35, 90, 55);
            }

            int x = w / 2;
            int y = groundY - 60;
            Polygon earHead = new Polygon();
            earHead.addPoint(x - 55, y + 5);
            earHead.addPoint(x - 30, y - 55);
            earHead.addPoint(x, y - 35);
            earHead.addPoint(x + 30, y - 55);
            earHead.addPoint(x + 55, y + 5);
            earHead.addPoint(x + 35, y + 38);
            earHead.addPoint(x - 35, y + 38);
            g2.setColor(new Color(235, 122, 58));
            g2.fillPolygon(earHead);

            g2.setColor(new Color(255, 221, 188));
            g2.fillOval(x - 27, y + 2, 54, 40);
            g2.setColor(new Color(45, 44, 58));
            g2.fillOval(x - 10, y + 15, 5, 5);
            g2.fillOval(x + 8, y + 15, 5, 5);
        }

        private void drawForest(Graphics2D g2, int w, int h) {
            int groundY = h - 40;
            g2.setColor(new Color(51, 105, 83));
            g2.fillOval(10, groundY - 55, 110, 75);
            g2.fillOval(w - 120, groundY - 55, 110, 75);
            g2.setColor(new Color(65, 128, 91));
            g2.fillRect(35, groundY - 70, 24, 90);
            g2.fillRect(w - 59, groundY - 70, 24, 90);

            g2.setColor(new Color(246, 208, 105));
            g2.fillRoundRect(w / 2 - 47, groundY - 75, 94, 62, 7, 7);
            g2.setColor(new Color(78, 72, 126));
            g2.drawLine(w / 2 - 25, groundY - 43, w / 2 + 10, groundY - 25);
            g2.drawLine(w / 2 + 10, groundY - 25, w / 2 + 32, groundY - 51);
            g2.setColor(new Color(208, 166, 70));
            Polygon stand = new Polygon();
            stand.addPoint(w / 2 - 12, groundY - 13);
            stand.addPoint(w / 2 + 12, groundY - 13);
            stand.addPoint(w / 2 + 3, groundY + 14);
            stand.addPoint(w / 2 - 18, groundY + 14);
            g2.fillPolygon(stand);
        }
    }

    private static class AccessibilityArt extends JPanel {
        AccessibilityArt() {
            setPreferredSize(new Dimension(160, 110));
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(new Color(26, 28, 58));
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 22, 22);
            g2.setColor(new Color(153, 108, 255));
            g2.fillOval(50, 20, 60, 60);
            g2.setColor(Color.WHITE);
            g2.fillOval(66, 34, 8, 8);
            g2.fillOval(87, 34, 8, 8);
            g2.setColor(new Color(91, 218, 181));
            g2.fillRoundRect(31, 77, 98, 8, 5, 5);
            g2.dispose();
        }
    }
}
