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

	private String query;
	
	/**
	 * Constructor.
	 */
	public GeneralTrackSelector(String query) {
		this.databaseConnector = Application.getDatabaseConnector();
		this.query = query;
	}
	
	public TrackList execute() {
		return new TrackList(databaseConnector.executeQuery(query));
	}
	
	public List<RecTuple> asWeightedList(double weight) {
		TrackList unweighted = this.execute();
		System.out.println("Selector 1: " + unweighted.size());
		ArrayList<RecTuple> weighted = new ArrayList<RecTuple>(unweighted.size());
		for (int i = 0; i < unweighted.size(); i++) {
			RecTuple rt = new RecTuple(unweighted.get(i), weight);
			weighted.add(rt);
		}
		return weighted;
	}
}
