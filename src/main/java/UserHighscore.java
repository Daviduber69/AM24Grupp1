public class UserHighscore implements Comparable<UserHighscore> {
    private int score;
    private String name;

    public UserHighscore(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public String getName() {
        return name;
    }

    @Override
    public int compareTo(UserHighscore o) {
        return this.getScore() - o.getScore();
    }

    @Override
    public String toString() {
        return String.format("Name: %s Score: %d%n",name,score);
    }
}
