package models.database.retriever;

import controllers.Application;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TrackRetriever implements Retriever {

    /**
     * The ResultSet object of the query.
     */
    private ResultSet resultSet;

    /**
     * The PreparedStatement object for the query
     */
    private PreparedStatement preparedStatement;

    /**
     * The id of the track.
     */
    private int trackid;

    /**
     * Constructor.
     *
     * @param trackid The id of the track
     */
    public TrackRetriever(int trackid) {
        this.trackid = trackid;
        String query = "SELECT ? FROM tracks WHERE track_id = " + trackid;
        try {
            preparedStatement = Application.getDatabaseConnector().getConnection().prepareStatement(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get all the information of the track from the database.
     */
    public void getAll() throws SQLException {
        preparedStatement.setString(0, "*");
        resultSet = preparedStatement.executeQuery();
    }

    /**
     * Get the user id of the track from the database.
     */
    public void getUserID() {

    }


    @Override
    public ResultSet retrieve() {
        return resultSet;
    }
}
