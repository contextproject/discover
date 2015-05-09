package models.database;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class to process the .comment files.
 */
public class CommentProcessor {

    /**
     * Database connector.
     */
    private DatabaseConnector databaseConnector = new DatabaseConnector();

    /**
     * Constructor.
     */
    public CommentProcessor() { }

    /**
     * Processes the comments in the provided folder.
     *
     * @param path path to the comment folder
     */
    public void processComments(final String path) {
        databaseConnector.makeConnection();

        createTable(new File(path));

        databaseConnector.closeConnection();
    }

    /**
     * Creates new MySQL table for the comments without features only if it did not exists.
     *
     * @param folder The folder where the comments are located
     */
    private void createTable(final File folder) {
        try {
            DatabaseMetaData dbm = databaseConnector.getConnection().getMetaData();
            ResultSet tables = dbm.getTables(null, null, "comments_without_features", null);
            if (!tables.next()) {
                System.out.println("Table does not exist, will create one.");
                databaseConnector.getStatement().execute("CREATE TABLE IF NOT EXISTS comments_without_features( "
                        + " track_id INT NOT NULL,"
                        + " comment_id INT NOT NULL,"
                        + " user_id INT NOT NULL,"
                        + " created_at DATETIME,"
                        + " timestamp INT,"
                        + " text LONGTEXT NOT NULL"
                        + ");");
                readFolder(folder);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * Read all the comments in this folder.
     *
     * @param folder The folder the comments are located in
     */
    private void readFolder(final File folder) {
        for (final File file : folder.listFiles()) {
            // ignore .DS_Store file
            Matcher matcher = Pattern.compile("\\Q.DS_Store\\E").matcher(file.toString());
            if (!matcher.find()) {
                readFile(file);
            }
        }
    }

    /**
     * Read a comment file and insert it into the database.
     *
     * @param file The comment file
     */
    private void readFile(final File file) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            List<String> lines = Files.readAllLines(Paths.get(file.toString()), Charset.defaultCharset());
            for (String line : lines) {
                // regular expression that matches to a comment with trackid, userid, etc.
                line = processLine(line);
                Pattern pattern = Pattern.compile("(\\d*) (\\d*) (\\d{4}.\\d{2}.\\d{2}) (\\d{2}.\\d{2}.\\d{2}) (.{5}) (-?\\d*|\\w*) (.*)");
                Matcher matcher = pattern.matcher(line);

                if (matcher.find()) {
                    databaseConnector.executeUpdate(stringBuilder.toString());
                    //executeQuery(stringBuilder.toString());
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
    private String processLine(final String line) {
        String result = line;
        // remove tabs from the string and replace them with a space
        result = result.replaceAll("\\t", " ");
        // process line so MySQL can handle the comment, escape ', " or \
        result = result.replaceAll("(?<!\\\\)(\\\\)(?![\\\\|\"|'])|(?<!\\\\)([\"|'])", "\\\\$0");

        return result;
    }

    /**
     * Extracts the track id from the file name.
     *
     * @param file The file object of the comment file
     * @return The track id
     */
    private String extractTrackID(final File file) {
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
    private String buildQuery(final Matcher matcher, final String trackid) {
        return ("INSERT INTO comments_without_features VALUES ("
                + trackid + ", "
                + matcher.group(1) + ", "
                + matcher.group(2) + ", "
                + "\'" + matcher.group(3).replaceAll("/", "-")
                + " " + matcher.group(4) + "\', "
                + (matcher.group(6).equals("None") ? -1 : matcher.group(6)) + ", "
                + "\'" + matcher.group(7) + "\');"
        );
    }
}
