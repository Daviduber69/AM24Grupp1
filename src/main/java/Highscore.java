import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Highscore {
    private List<Integer> highscore;
    Path filePath = Paths.get("highscore.txt");
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
        Collections.sort(highscore, Collections.reverseOrder());
        return highscore.get(0);
    }
    public void saveHighscore() {
        try (
                BufferedWriter writer = Files.newBufferedWriter((filePath), StandardCharsets.UTF_8,
        StandardOpenOption.APPEND)) {
            if(showHighscore()>0){
                writer.write(Integer.toString(showHighscore()));
                writer.newLine();
                highscore.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int printHighscore() {
        String line;
        int maxScore = 0;
        if (Files.exists(filePath)) {
            try (BufferedReader inputReader = Files.newBufferedReader(filePath)) {
                while ((line = inputReader.readLine()) != null) {
                    int max = Integer.parseInt(line);
                    maxScore = Math.max(maxScore, max);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            try {
                Files.createFile(filePath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return maxScore;
    }
}
