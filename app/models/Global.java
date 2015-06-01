package models;

import com.fasterxml.jackson.databind.ObjectMapper;
import models.database.DatabaseConnector;
import play.Application;
import play.GlobalSettings;
import play.Logger;

/**
 * The global class of the play application.
 */
public class Global extends GlobalSettings {

    /**
     * DatabaseConnector object to the database.
     */
    private DatabaseConnector databaseConnector = new DatabaseConnector();

    /**
     * The ObjectMapper object used for JsonNode creations.
     */
    private ObjectMapper mapper = new ObjectMapper();

    /**
     * The method that gets called on the start of the play application.
     * The connection to the database gets established here.
     *
     * @param app The play application
     */
    public void onStart(final Application app) {
        String url = "jdbc:mysql://188.166.78.36/contextbase";
        String username = "context";
        String password = "password";

        databaseConnector.loadDrivers();
        databaseConnector.makeConnection(url, username, password);

        controllers.Application.setDatabaseConnector(databaseConnector);
        controllers.Application.setObjectMapper(mapper);

        controllers.Recommender.initialize();
    }

    /**
     * The method that gets called on the stop of the play application.
     * The connection to the database gets terminated here.
     *
     * @param app The play application
     */
    public void onStop(final Application app) {
        Logger.info("Application shutdown...");

        databaseConnector.closeConnection();
    }
}
