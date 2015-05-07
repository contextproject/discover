package models.snippet;

import java.util.Random;

public class RandomSnippet {

    private double songStart;
    private double songEnd;
    private double window;
    private Random random;

    public RandomSnippet() {
        random = new Random();
        window = 5000;
        this.generate();
    }

    public void generate() {
        songStart = random.nextDouble() * 20000;
        songEnd = songStart + window;
    }

    /**
     * Set the size of the window
     *
     * @param duration , size of the new window in milliseconds.
     */
    public void setWindow(double duration) {
        window = duration;
    }

    public double getStart() {
        return songStart;
    }

    public double getEnd() {
        return songEnd;
    }
}