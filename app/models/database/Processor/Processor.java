package models.database.processor;

import controllers.Application;
import models.database.DatabaseConnector;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class to read a given folder.
 */
public class Processor {

    /**
     * DatabaseConnector object to the database.
     */
    private DatabaseConnector databaseConnector;

    /**
     * Constructor.
     *
     * @param folder The folder to be read.
     * @throws IOException IOException
     */
    public Processor(final File folder) throws IOException {
        databaseConnector = Application.getDatabaseConnector();
        readFolder(folder);
    }

    /**
     * Reads a folder and passes each file to the readFile method.
     *
     * @param folder The folder to be read
     * @throws IOException IOException
     */
    protected void readFolder(final File folder) throws IOException {
        File[] files = folder.listFiles();
        assert files != null;
        for (File file : files) {
            if (file.isFile()) {
                Matcher matcher = Pattern.compile("\\Q.DS_Store\\E").matcher(file.toString());
                if (!matcher.find()) {
                    readFile(file);
                }
            }
        }
    }

    /**
     * Reads a file.
     *
     * @param file The file to be read
     * @throws IOException IOException
     */
    protected void readFile(final File file) throws IOException {}

    /**
     * Extracts the track id from the file name.
     *
     * @param file The file object of the comment file
     * @return The track id
     */
    protected String getTrackid(final File file) {
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(file.getName());
        if (matcher.find()) {
            return matcher.group(0);
        }
        return "-1";
    }

    /**
     * Getter of the DatabaseConnector object.
     *
     * @return The DatabaseConnector object
     */
    protected DatabaseConnector getDatabaseConnector() {
        return databaseConnector;
    }
}
