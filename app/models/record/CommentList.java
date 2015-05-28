package models.record;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CommentList extends ArrayList<Comment> {

    public CommentList(ResultSet resultSet) {
        try {
            while(resultSet.next()) {
                add(new Comment(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
