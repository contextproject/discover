package models.database;

import models.record.Track;

public class DatabaseUpdater {

    private DatabaseUpdater instance;
    
    private String query = "INSERT IGNORE INTO tracks (,,,,) VALUES (,,,,)";
    
    private DatabaseConnector dbconnector;
    
    private DatabaseUpdater() {
        this.dbconnector = DatabaseConnector.getConnector();
    }
    
    public DatabaseUpdater getInstance() {
        if(instance == null) {
            instance = new DatabaseUpdater(); 
        }
        return instance;
    }
    
    public void updateIfNeeded(Track track) {
        check(track);
    }
    
    private void check(Track track) {
        update(track);
        insert(track);
    }

    private void update(Track track) {
        
    }
    
    private void insert(Track track) {
        this.dbconnector.executeQuery(query);
    }
    
    
}
