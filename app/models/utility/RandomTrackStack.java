package models.utility;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Stack;

import models.database.DatabaseConnector;
import models.record.Track;

/**
 * The RandomTrackStack is used to store tracks requested from database. This is
 * done to save processing time on multiple random track requests from the
 * database. The stack has an autofill option that automatically fills the stack
 * with new randomly sorted tracks if set to true.
 */
public class RandomTrackStack {

    /**
     * The stack object.
     */
    private Stack<Track> stack;

    /**
     * The query for requesting tracks.
     */
    private String query;

    /**
     * Boolean value used for turning the autofill on and off.
     */
    private boolean autofill;

    /**
     * Constructor of the class.
     * @param limit The amount of tracks that the stack needs to hold.
     * @param bool Whether the object needs to refill automatically itself.
     */
    public RandomTrackStack(final int limit, final boolean bool) {
        this.stack = new Stack<Track>();
        this.autofill = bool;
        this.query = "SELECT * FROM tracks ORDER BY RAND() LIMIT " + limit;
        this.push(DatabaseConnector.getConnector().executeQuery(this.query));
    }

    /**
     * Removes the item on the top of the stack.
     * @return The Track on top of the stack.
     */
    public Track pop() {
        if (!stack.isEmpty()) {
            return stack.pop();
        } else {
            if (autofill) {
                this.push(DatabaseConnector.getConnector().executeQuery(this.query));
                return stack.pop();
            } else {
                throw new NullPointerException("The Stack is out of Tracks and will not "
                        + "be refilled because the autofill is set to " + autofill + ".");
            }
        }
    }

    /**
     * Pops multiple random tracks from the stack.
     * @param amount The amount of tracks that need to be returned.
     * @return TrackList object containing the tracks returned.
     */
    public TrackList pop(final int amount) {
        TrackList list = new TrackList();
        for (int i = 0; i < amount; i++) {
            list.add(this.pop());
        }
        return list;
    }

    /**
     * Adds a Track to the stack.
     * @param track The Track object being added.
     */
    public void push(final Track track) {
        stack.push(track);
    }

    /**
     * Pushes a complete ResultSet onto the stack.
     * @param rs The ResultSet object.
     */
    public void push(final ResultSet rs) {
        try {
            while (rs.next()) {
                Track track = new Track();
                track.put(Track.ID, rs.getInt("track_id"));
                track.put(Track.DURATION, rs.getInt("duration"));
                stack.push(track);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the size of the stack.
     * @return Size of the stack as int.
     */
    public int size() {
        return stack.size();
    }

    /**
     * Stack empty check.
     * @return Returns true if stack is empty else false.
     */
    public boolean isEmpty() {
        return stack.isEmpty();
    }

}
