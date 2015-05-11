package models;

import models.database.DatabaseConnector;
import models.database.ScriptRunner;
import play.Application;
import play.GlobalSettings;
import play.Logger;

import java.io.BufferedReader;
import java.io.FileReader;

public class Global extends GlobalSettings {

    public DatabaseConnector databaseConnector = new DatabaseConnector();

    public void onStart(Application app) {
        Logger.info("Application has started");

        databaseConnector.loadDrivers();
        databaseConnector.makeConnection();

        ScriptRunner runner = new ScriptRunner(databaseConnector.getConnection(), false, true);
        runner.setDelimiter(";", true);
        try {
            runner.runScript(new BufferedReader(new FileReader("/resources/contextbase.sql")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onStop(Application app) {
        Logger.info("Application shutdown...");

        databaseConnector.closeConnection();
    }
}