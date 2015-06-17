package models;

import controllers.RecommenderController;
import models.database.DatabaseConnector;
import play.Application;
import play.GlobalSettings;
import play.Logger;

/**
 * The global class of the play application.
 */
public class Global extends GlobalSettings {

    /**
     * This method gets called on the start of the play application.
     * The connection to the database is established here and a profile
     * for the current session is created.
     *
     * @param app The play application
     */
    public void onStart(final Application app) {
        String url = "jdbc:mysql://188.166.78.36/contextbase";
        String username = "context";
        String password = "password";

        DatabaseConnector databaseConnector = DatabaseConnector.getConnector();
        databaseConnector.loadDrivers();
        databaseConnector.makeConnection(url, username, password);

        RecommenderController.initialize();
    }

    /**
     * This method gets called on the stop of the play application.
     * The connection to the database is terminated here.
     *
     * @param app The play application
     */
    public void onStop(final Application app) {
        Logger.info("Application shutdown...");

        DatabaseConnector.getConnector().closeConnection();
    }
}
