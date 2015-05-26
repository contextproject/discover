package models.database.retriever;

import controllers.Application;
import models.database.DatabaseConnector;

import java.sql.ResultSet;

/**
 * Class to retrieve the information of the track from the database.
 */
public class TrackRetriever implements Retriever {

    /**
     * The DatabaseConnector object.
     */
    private DatabaseConnector databaseConnector;

    /**
     * The ResultSet object of the query.
     */
    private ResultSet resultSet;

    /**
     * The id of the track.
     */
    private int trackid;

    /**
     * Constructor.
     *
     * @param trackid The id of the track
     */
    public TrackRetriever(final int trackid) {
        this.trackid = trackid;
        this.databaseConnector = Application.getDatabaseConnector();
    }

    /**
     * Get all the information of the track from the database.
     */
    public void getAll() {
        resultSet = databaseConnector.executeQuery(
                "SELECT * FROM tracks WHERE track_id = " + trackid
        );
    }

    /**
     * Get the user id of the track from the database.
     */
    public void getUserID() {
        resultSet = databaseConnector.executeQuery(
                "SELECT user_id FROM tracks WHERE track_id = " + trackid
        );
    }

    /**
     * Get the duration of the track from the database.
     */
    public void getDuration() {
        resultSet = databaseConnector.executeQuery(
                "SELECT duration FROM tracks WHERE track_id = " + trackid
        );
    }


    @Override
    public ResultSet retrieve() {
        return resultSet;
    }
}
