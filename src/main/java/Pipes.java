    import java.awt.*;

    public class Pipes {
        private int x;
        private int upperPipeY;
        private int lowerPipeY;
        private boolean passed;

        public Pipes(int x, int upperPipeY, int lowerPipeY, boolean passed) {
            this.x = x;
            this.upperPipeY = upperPipeY;
            this.lowerPipeY = lowerPipeY;
            this.passed = passed;
        }

        public int getX() {
            return x;
        }

        public int getUpperPipeY() {
            return upperPipeY;
        }

        public int getLowerPipeY() {
            return lowerPipeY;
        }

        public boolean isPassed() {
            return passed;
        }

        // Setter method for setting the passed state
        public void setPassed(boolean passed) {
            this.passed = passed;
        }

        public void setX(int x) {
            this.x = x;
        }

        public void setUpperPipeY(int upperPipeY) {
            this.upperPipeY = upperPipeY;
        }

        public void setLowerPipeY(int lowerPipeY) {
            this.lowerPipeY = lowerPipeY;
        }
    }

