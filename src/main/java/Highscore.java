import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Highscore {
    private List<Integer> highscore;

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
                BufferedWriter writer = Files.newBufferedWriter(Paths.get("highscore.txt"),StandardCharsets.UTF_8
                , StandardOpenOption.APPEND)) {
                if(showHighscore()>0){
                    writer.write(Integer.toString(showHighscore()));
                    writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public int printHighscore(){
        String line;
        int maxScore = 0;
        try (BufferedReader inputReader = Files.newBufferedReader(Paths.get("highscore.txt"))) {
            while((line= inputReader.readLine())!=null){
                int max = Integer.parseInt(line);
                maxScore = Math.max(maxScore,max);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return maxScore;
    }
}
