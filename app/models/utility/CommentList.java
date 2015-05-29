package models.utility;

import models.record.Comment;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * The commentlist class.
 */
public class CommentList extends ArrayList<Comment> {

	/**
	 * Makes a new CommentList over a given resultset.
	 * 
	 * @param resultSet
	 *            Set of comment pulled out of the database.
	 */
	public CommentList(final ResultSet resultSet) {
		try {
			while (resultSet.next()) {
				add(new Comment(resultSet));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
