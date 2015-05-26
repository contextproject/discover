package models.database.retriever;

import java.sql.ResultSet;

/**
 * A Retriever interface.
 */
public interface Retriever {

    /**
     * Retrieves information of the track provided.
     *
     * @return The ResultSet
     */
    ResultSet retrieve();
}
