import java.awt.*;

public class Pipes {
    private int x;
    private int yTop;
    private int yBottom;
    private boolean passed;

    public Pipes(int x, int yTop, int yBottom){
        this.x = x;
        this.yTop = yTop;
        this.yBottom = yBottom;
        this.passed = false;
    }

    public int getX() {
        return x;
    }

    public int getyTop() {
        return yTop;
    }

    public int getyBottom() {
        return yBottom;
    }

    public boolean isPassed() {
        return passed;
    }
    public void setPassed(boolean passed) {
        this.passed = passed;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setyTop(int yTop) {
        this.yTop = yTop;
    }

    public void setyBottom(int yBottom) {
        this.yBottom = yBottom;
    }

}
