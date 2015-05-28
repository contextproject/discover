package models.seeker;

import models.snippet.TimedSnippet;

/**
 * Give a random snippet.
 */
public class RandomSeeker implements Seeker {

    /**
     * The id of the track
     */
    private int trackid;

    /**
     * The start of the snippet.
     */
    private int start;

    /**
     * The window of the snippet.
     */
    private int window;

    /**
     * Constructor.
     */
    public RandomSeeker(int trackid) {
        this.generate();
    }

    private void generate() {
        start = (int) Math.random() * 20000;
    }

    /**
     * Set the size of the window.
     *
     * @param window The new window in milliseconds
     */
    public void setWindow(final int window) {
        this.window = window;
    }


    @Override
    public TimedSnippet seek() {
        return new TimedSnippet(start);
    }
}
