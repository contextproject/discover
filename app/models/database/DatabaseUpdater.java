package models.database;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.fasterxml.jackson.databind.JsonNode;

import controllers.Application;
import models.record.Track;

public class DatabaseUpdater {

    private static DatabaseUpdater instance;

    private StringBuilder query ; // = "INSERT IGNORE INTO tracks (,,,,) VALUES (,,,,)";

    private DatabaseConnector dbconnector;

    private DatabaseUpdater() {
        this.dbconnector = DatabaseConnector.getConnector();
        query = new StringBuilder();
    }

    public static DatabaseUpdater getInstance() {
        if (instance == null) {
            instance = new DatabaseUpdater();
        }
        return instance;
    }

    public void updateIfNeeded(JsonNode json) {

    }

    public String buildQuery(JsonNode json) {
        StringBuilder res = new StringBuilder();
        String query = "INSERT IGNORE INTO tracks ";
        String colums = "(track_id, created_at, "
                        + "user_id, duration, commentable, streamable, " //6
                        + "downloadable, genre, label_name, track_type, bpm, title, " //7
                        + " original_content_size, license, " //5
                        + "playback_count, favoritings_count, download_count, comment_count) "; //4
        res.append("INSERT IGNORE INTO tracks ");
        res.append("(track_id, created_at, "
                        + "user_id, duration, commentable, streamable, " //6
                        + "downloadable, genre, label_name, track_type, bpm, title, " //7
                        + " original_content_size, license, " //5
                        + "playback_count, favoritings_count, download_count, comment_count) ");
        String values = "VALUES (" + 
                        json.get("id").asInt() + ", '" + 
                        json.get("created_at").asText() + "', " + 
                        json.get("user_id").asInt() + ", " + 
                        json.get("duration").asInt() + ", " + 
                        json.get("commentable").asBoolean() + ", " + 
                        json.get("streamable").asBoolean() + ", " + 
                        json.get("downloadable").asBoolean()+ ", '" + 
                        json.get("genre").asText() + "', " + 
                        json.get("label_name").asText() + "', '" + 
                        json.get("track_type").asText() + "', " + 
                        json.get("bpm").asDouble() + ", '" + 
                        json.get("title").asText()+ "', " + 
                        json.get("original_content_size").asText() + "', '" + 
                        json.get("license").asText() + "', '" + 
                        json.get("playback_count").asText() + "', '" + 
                        json.get("favoritings_count").asText() + "', " +
                        json.get("download_count").asInt() + ", " + 
                        json.get("comment_count").asInt() +")";
        try {
            PreparedStatement ps = Application.getDatabaseConnector().getConnection().prepareStatement(res.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return query + colums + values;
    }

    // private void check(Track track) {
    // update(track);
    // insert(track);
    // }
    //
    // private void update(Track track) {
    // }

    private void insert(Track track) {
        this.dbconnector.executeQuery(query.toString());
    }
}
