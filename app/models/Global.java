package models;

import models.database.DatabaseConnector;
import play.Application;
import play.GlobalSettings;
import play.Logger;

public class Global extends GlobalSettings {

    public DatabaseConnector databaseConnector = new DatabaseConnector();

    public void onStart(Application app) {
        String url = "jdbc:mysql://188.166.78.36/contextbase";
        String username = "context";
        String password = "password";

        databaseConnector.loadDrivers();
        databaseConnector.makeConnection(url, username, password);

        controllers.Application.setDatabaseConnector(databaseConnector);
    }

    public void onStop(Application app) {
        Logger.info("Application shutdown...");

        databaseConnector.closeConnection();
    }
}