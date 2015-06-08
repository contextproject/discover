package models.database.retriever;

import java.util.ArrayList;
import java.util.List;

import controllers.Application;
import models.database.DatabaseConnector;
import models.recommender.RecTuple;
import models.utility.TrackList;

public class GeneralTrackSelector {

    /** The DatabaseConnector object */
    private DatabaseConnector databaseConnector;


    private static GeneralTrackSelector instance = new GeneralTrackSelector();

    /**
     * Constructor of the class.
     * 
     * @param query
     *            The query used for selecting from the database.
     */
    private GeneralTrackSelector() {
        this.databaseConnector = Application.getDatabaseConnector();
    }

    /**
     * Execute the object query to receive a TrackList with the results.
     * 
     * @return TrackList with the results
     */
    public TrackList execute(final String query) {
        return new TrackList(databaseConnector.executeQuery(query));
    }

    /**
     * Creates a list of weighted Track objects (RecTuple).
     * 
     * @param weight
     *            The weight that every track will receive when created.
     * @return List of RecTuples
     */
    public List<RecTuple> asWeightedList(final String query, final double weight) {
        TrackList unweighted = this.execute(query);
        ArrayList<RecTuple> weighted = new ArrayList<RecTuple>(
                unweighted.size());
        for (int i = 0; i < unweighted.size(); i++) {
            RecTuple rt = new RecTuple(unweighted.get(i), weight);
            weighted.add(rt);
        }
        return weighted;
    }

    /**
     * Singleton method to get the instance of GeneralTrackSelector.
     * 
     * @return the GeneralTrackSelector object.
     */
    public static GeneralTrackSelector getInstance() {
       return instance;
    }
}
