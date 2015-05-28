package models.database.retriever;

import controllers.Application;
import models.database.DatabaseConnector;
import models.record.Track;

/**
 * Class to retrieve the information of the track from the database.
 */
public class TrackRetriever implements Retriever {

    /**
     * The DatabaseConnector object.
     */
    private DatabaseConnector databaseConnector;

    /**
     * The Track entity.
     */
    private Track track;

    /**
     * Constructor.
     *
     * @param trackid The id of the track
     */
    public TrackRetriever(final int trackid) {
        this.databaseConnector = Application.getDatabaseConnector();
        track = new Track(databaseConnector.executeQuery(
                "SELECT * FROM tracks WHERE track_id = " + trackid
        ));
    }

    /**
     * Get all the information of the track from the database.
     */
    public Track getAll() {
        return track;
    }

    /**
     * Get the user id of the track from the database.
     */
    public int getTrackID() {
        return track.getTrackID();
    }

    /**
     * Retrieves information of the track provided.
     *
     * @return The Record entity of the track
     */
    @Override
    public Track retrieve() {
        return track;
    }
}
