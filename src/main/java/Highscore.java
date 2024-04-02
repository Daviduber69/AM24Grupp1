import javax.swing.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Highscore {
    private List<UserHighscore> highscore;
    Path filePathHard = Paths.get("hardhighscore.txt");
    Path filePathEasy = Paths.get("easyhighscore.txt");

    public Highscore() {
        this.highscore = new ArrayList<>();
    }

    public List<UserHighscore> getHighscore() {
        return highscore;
    }

    public void addScore(int score) {
        String name = JOptionPane.showInputDialog("Enter your name: ");
        if(name.isEmpty()){
            name = "Unknown";
        }
        name = name.replaceAll("\\s+", "");
        highscore.add(new UserHighscore(name, score));
    }

    public int showHighscore() {
        if (highscore.isEmpty()) {
            return 0;
        }
        highscore.sort(Collections.reverseOrder());
        return highscore.get(0).getScore();
    }

    public void saveHardHighscore() {
        if (!Files.exists(filePathHard)) {
            try {
                Files.createFile(filePathHard);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            try (
                    BufferedWriter writer = Files.newBufferedWriter((filePathHard)
                            , StandardCharsets.UTF_8
                            , StandardOpenOption.APPEND)) {
                if (showHighscore() > 0) {
                    for (UserHighscore userhighscore : highscore) {
                        writer.write(userhighscore.getName() + " " + userhighscore.getScore() + "\n");
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }

    public List<UserHighscore> printHardHighscore() {
        List<UserHighscore> hardHighscores = new ArrayList<>();
        String line;
        if (!Files.exists(filePathHard)) {
            try {
                Files.createFile(filePathHard);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            try (BufferedReader inputReader = Files.newBufferedReader(filePathHard)) {
                while ((line = inputReader.readLine()) != null) {
                    String[] arr = line.split(" ");
                    String name = arr[0];
                    int score = Integer.parseInt(arr[1]);
                    hardHighscores.add(new UserHighscore(name, score));
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            hardHighscores.sort(Collections.reverseOrder());
        }
        return hardHighscores.subList(0, Math.min(5, hardHighscores.size()));
    }

    public void saveEasyHighscore() {
        if (!Files.exists(filePathEasy)) {
            try {
                Files.createFile(filePathEasy);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            try (
                    BufferedWriter writer = Files.newBufferedWriter((filePathEasy)
                            , StandardCharsets.UTF_8
                            , StandardOpenOption.APPEND)) {
                if (showHighscore() > 0) {
                    writer.write(Integer.toString(showHighscore()));
                    writer.newLine();
                    highscore.clear();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public List<Integer> printEasyHighscore() {
        String line;
        List<Integer> easyHighscores = new ArrayList<>();
        if (!Files.exists(filePathEasy)) {
            try {
                Files.createFile(filePathEasy);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            try (BufferedReader inputReader = Files.newBufferedReader(filePathEasy)) {
                while ((line = inputReader.readLine()) != null) {
                    int score = Integer.parseInt(line);
                    easyHighscores.add(score);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        easyHighscores.sort(Collections.reverseOrder());
        return easyHighscores.subList(0, Math.min(5, easyHighscores.size()));
    }

}