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

    /** Query used for selecting from the database.*/
    private String query;

    /**
     * Constructor of the class.
     * @param query The query used for selecting from the database.
     */
    public GeneralTrackSelector(final String query) {
        this.databaseConnector = Application.getDatabaseConnector();
        this.query = query;
    }

    /**
     * Execute the object query to receive a TrackList with the results.
     * 
     * @return TrackList with the results
     */
    public TrackList execute() {
        return new TrackList(databaseConnector.executeQuery(query));
    }

    /**
     *  Creates a list of weighted Track objects (RecTuple).
     *  
     * @param weight The weight that every track will receive when created.
     * @return List of RecTuples
     */
    public List<RecTuple> asWeightedList(final double weight) {
        TrackList unweighted = this.execute();
        System.out.println("Selector 1: " + unweighted.size());
        ArrayList<RecTuple> weighted = new ArrayList<RecTuple>(
                unweighted.size());
        for (int i = 0; i < unweighted.size(); i++) {
            RecTuple rt = new RecTuple(unweighted.get(i), weight);
            weighted.add(rt);
        }
        return weighted;
    }
}
