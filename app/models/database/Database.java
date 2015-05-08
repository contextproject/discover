package models.database;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class to connect to the database.
 */
public class Database {

    /**
     * Connection object to the database.
     */
    private Connection connection;

    /**
     * Statement object of the connection.
     */
    private Statement statement;

    public Database() {
        loadDrivers();
        makeConnection();
    }

    public ResultSet getComments(int trackid) {
        ResultSet result = null;
        try {
            String query = "SELECT user_id, timestamp, text FROM comments_without_features WHERE track_id = " + trackid;
            result = statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Read all the comments in this folder.
     *
     * @param folder The folder the comments are located in
     */
    public void readFolder(final File folder) {
        System.out.println("Importing folder...");
        for (final File file : folder.listFiles()) {
            Matcher matcher = Pattern.compile("\\Q.DS_Store\\E").matcher(file.toString());
            // ignore .DS_Store file
            if(!matcher.find()) {
                readFile(file);
            }
        }
        System.out.println("Done importing!");
    }

    /**
     * Read a comment file and insert it into the database.
     *
     * @param file The comment file
     */
    public void readFile(final File file) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(file.toString()), Charset.defaultCharset());
            StringBuilder stringBuilder = new StringBuilder();
            for (String line : lines) {
                // regular expression that matches to a comment with trackid, userid, etc.
                Pattern pattern = Pattern.compile("(\\d*) (\\d*) (\\d{4}.\\d{2}.\\d{2}) (\\d{2}.\\d{2}.\\d{2}) (.{5}) (-?\\d*|\\w*) (.*)");
                Matcher matcher = pattern.matcher(processLine(line));

                if (matcher.find()) {
                    executeQuery(stringBuilder.toString());
                    stringBuilder.setLength(0);
                    stringBuilder.insert(0, buildQuery(matcher, extractTrackID(file)));
                } else {
                    // part of a comment is on a new line, add this part to the previous comment
                    stringBuilder.setLength(stringBuilder.length() - 3);
                    stringBuilder.append(" ").append(line).append("\');");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Process a line so it can be processed easier.
     *
     * @param line Original line
     * @return Processed line
     */
    public String processLine(final String line) {
        String result = line;
        // remove tabs from the string and replace them with a space
        result = result.replaceAll("\\t", " ");
        // process line so MySQL can handle the comment, escape ', " or \
        result = result.replaceAll("(?<!\\\\)(\\\\)(?![\\\\|\"|'])|(?<!\\\\)([\"|'])", "\\\\$0");

        return result;
    }

    /**
     * Executes the given query string.
     *
     * @param query The query string
     */
    public void executeQuery(final String query) {
        if (!query.equals("")) {
            try {
                statement.executeUpdate(query);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Extracts the track id from the file name.
     *
     * @param file The file object of the comment file
     * @return The track id
     */
    public String extractTrackID(final File file) {
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(file.toString());
        if (matcher.find()) {
            return matcher.group(0);
        } else {
            return "-1";
        }

    }

    /**
     * Builds the query for MySQL.
     *
     * @param matcher Matcher object to extract the info
     * @param trackid Track id of the comment
     * @return String that can be executed as MySQL command
     */
    public String buildQuery(final Matcher matcher, final String trackid) {
        return ("INSERT INTO comments_without_features VALUES ("
                + trackid + ", "
                + matcher.group(1) + ", "
                + matcher.group(2) + ", "
                + "\'" + matcher.group(3).replaceAll("/", "-")
                + " " + matcher.group(4) + "\', "
                + processTimestamp(matcher.group(6)) + ", "
                + "\'" + matcher.group(7) + "\');"
        );
    }

    /**
     * Processes the timestamp of a comment for the MySQL database.
     *
     * @param timestamp The raw timestamp
     * @return The processed timestamp
     */
    public String processTimestamp(final String timestamp) {
        if (timestamp.equals("None")) {
            return "-1";
        } else {
            return timestamp;
        }
    }

    /**
     * Loading the drivers to connect to a MySQL database.
     */
    public void loadDrivers() {
        try {
            System.out.println("Loading driver...");
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Driver loaded!");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Cannot find the driver in the classpath!", e);
        }
    }

    /**
     * Make a connection with the database.
     */
    public void makeConnection() {
        try {
            System.out.println("Connecting database...");

            String url = "jdbc:mysql://localhost:3306/contextbase";
            String username = "context";
            String password = "password";

            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Database connected!");
            statement = connection.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException("Cannot connect the database!", e);
        }
    }

    /**
     * Close the connection with the database.
     */
    public void closeConnection() {
        System.out.println("Closing the connection.");
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException ignore) {
            }
        }
    }
}
