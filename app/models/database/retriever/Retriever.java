package models.database.retriever;

import models.record.Record;

/**
 * A Retriever interface.
 */
public interface Retriever {

    /**
     * Retrieves information of the track provided.
     *
     * @return The Record entity of the track
     */
    Record retrieve();
}
