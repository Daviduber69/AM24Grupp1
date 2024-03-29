import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Highscore {
    private final List<Integer> highscore;
    Path filePathHard = Paths.get("hardhighscore.txt");
    Path filePathEasy = Paths.get("easyhighscore.txt");

    public Highscore() {
        this.highscore = new ArrayList<>();
    }

    public void addScore(int score) {
        highscore.add(score);
    }

    public int showHighscore() {
        if (highscore.isEmpty()) {
            return 0;
        }
        highscore.sort(Collections.reverseOrder());
        return highscore.get(0);
    }

    public void saveHardHighscore() {
        if (Files.exists(filePathHard)) {
            try (
                    BufferedWriter writer = Files.newBufferedWriter((filePathHard)
                            ,StandardCharsets.UTF_8
                            ,StandardOpenOption.APPEND)) {
                if (showHighscore() > 0) {
                    writer.write(Integer.toString(showHighscore()));
                    writer.newLine();
                    highscore.clear();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            try {
                Files.createFile(filePathHard);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public List<Integer> printHardHighscore() {
        String line;
        List<Integer> hardHighscores = new ArrayList<>();
        try (BufferedReader inputReader = Files.newBufferedReader(filePathHard)) {
            while ((line = inputReader.readLine()) != null) {
                int score = Integer.parseInt(line);
                hardHighscores.add(score);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        hardHighscores.sort(Collections.reverseOrder());
        return hardHighscores.subList(0, Math.min(5,hardHighscores.size()));
    }
    public void saveEasyHighscore() {
        if (Files.exists(filePathEasy)) {
            try (
                    BufferedWriter writer = Files.newBufferedWriter((filePathEasy)
                            ,StandardCharsets.UTF_8
                            ,StandardOpenOption.APPEND)) {
                if (showHighscore() > 0) {
                    writer.write(Integer.toString(showHighscore()));
                    writer.newLine();
                    highscore.clear();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            try {
                Files.createFile(filePathEasy);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public List<Integer> printEasyHighscore() {
        String line;
        List<Integer> easyHighscores = new ArrayList<>();
        try (BufferedReader inputReader = Files.newBufferedReader(filePathEasy)) {
            while ((line = inputReader.readLine()) != null) {
                int score = Integer.parseInt(line);
                easyHighscores.add(score);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        easyHighscores.sort(Collections.reverseOrder());
        return easyHighscores.subList(0, Math.min(5, easyHighscores.size()));
    }
}
