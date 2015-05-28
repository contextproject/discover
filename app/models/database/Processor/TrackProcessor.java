package models.database.processor;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class to process the .track files.
 */
public class TrackProcessor extends Processor {

    /**
     * To prepare a MySQL query.
     */
    private PreparedStatement preparedStatement;

    /**
     * TrackProcessor constructor.
     *
     * @param folder The folder containing the .track files
     * @throws IOException If something goes wrong with reading the folder
     */
    public TrackProcessor(final File folder) throws IOException {
        super(folder);
    }

    /**
     * Reads a file and passes each line to the readLine method.
     *
     * @param file The file to be read
     * @throws IOException IOException
     */
    protected void readFile(final File file) throws IOException {
        List<String> lines = Files.readAllLines(file.toPath(), Charset.defaultCharset());
        for (String line : lines) {
            readLine(line, file);
        }
    }

    /**
     * Reads a line from a .track files.
     *
     * @param line The line from a .track file
     * @param file The file of the line
     */
    protected void readLine(final String line, final File file) {
        prepareStatement(file);
        Matcher matcher = Pattern.compile(generateRegex()).matcher(line);
        if (matcher.find()) {
            prepareStatement(matcher);
        } else {
            // print the line for debugging purposes
            System.err.println("The following line failed to get processed! " + line);
        }
    }

    /**
     * Sets the PreparedStament object up.
     *
     * @param file The current file
     */
    private void prepareStatement(final File file) {
        String query = "INSERT INTO tracks "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            preparedStatement = getDatabaseConnector().getConnection().prepareStatement(query);
            preparedStatement.setObject(1, getTrackid(file));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Fills the missing fields in of the PreparedStatement object.
     *
     * @param matcher The matcher object of the line from the .track file
     */
    private void prepareStatement(final Matcher matcher) {
        try {
            for (int i = 1; i < matcher.groupCount() + 1; i++) {
                switch (i) {
                    case 1:
                        preparedStatement.setObject(i + 1, matcher.group(i).replaceAll("/", "-"));
                        break;
                    case 4:
                    case 5:
                    case 6:
                        preparedStatement.setObject(i + 1, convertBoolean(matcher.group(i)));
                        break;
                    default:
                        preparedStatement.setObject(i + 1, matcher.group(i));
                        break;
                }
            }
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Generates the regex to match a line in the .track on.
     *
     * @return The regex to match a line in the .track on
     */
    private String generateRegex() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("([\\d: \\/]+).{6}");
        for (int i = 0; i < 20; i++) {
            stringBuilder.append("\\t([^\\t]*)");
        }
        return stringBuilder.toString();
    }

    /**
     * Converts true or false to a MySQL tinyint.
     *
     * @param bool The original boolean
     * @return The tinyint of the boolean
     */
    private String convertBoolean(final String bool) {
        if (bool.equals("True")) {
            return "1";
        }
        return "0";
    }
}
