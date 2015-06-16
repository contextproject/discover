package models.utility;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Stack;

import models.database.DatabaseConnector;
import models.record.Track;

public class GlobalTrackStack {

    private DatabaseConnector dbconnector;

    private Stack<Track> stack;

    private String query;

    private boolean autofill;

    public GlobalTrackStack(int limit, boolean bool) {
        this.dbconnector = DatabaseConnector.getConnector();
        this.stack = new Stack<Track>();
        this.autofill = bool;
        this.query = "SELECT DISTINCT *" 
                + " FROM tracks"
                + " ORDER BY RAND()"
                + " LIMIT " + limit;
        this.push(dbconnector.executeQuery(this.query));
    }

    public Track pop() {
        if (!stack.isEmpty())
            return stack.pop();
        else {
            if (autofill) {
                this.push(dbconnector.executeQuery(this.query));
                if (this.isEmpty()) {
                    throw new NullPointerException("The Stack is out of Tracks and cannot be refilled.");
                }
                return stack.pop();
            } else {
                throw new NullPointerException(
                        "The Stack is out of Tracks and will not be refilled because the autofill is set to " + autofill + ".");
            }
        }

    }

    public TrackList pop(int amount) {
        TrackList list = new TrackList();
        for (int i = 0; i < amount; i++) {
            list.add(stack.pop());
        }
        return list;
    }

    public void push(Track track) {
        stack.push(track);
    }

    public void push(ResultSet rs) {
        try {
            while (rs.next()) {
                stack.push(new Track(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int size() {
        return stack.size();
    }

    public boolean isEmpty() {
        return stack.isEmpty();
    }

}
