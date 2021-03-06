package models.utility;

import models.record.Comment;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Class to represent the comments of a track.
 */
public class CommentList extends ArrayList<Comment> {
    
    /**
     * The serial number.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Creates an empty comment list.
     */
    public CommentList() {
        
    }

    /**
     * Constructor. Generates an object with all the comments from the ResultSet.
     *
     * @param resultSet The ResultSet containing the comments
     */
    public CommentList(final ResultSet resultSet) {
        this();
        try {
            while (resultSet.next()) {
                add(new Comment(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
