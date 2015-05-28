package models.database.processor;

import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;

/**
 * Class to process the feature essentia of the track.
 */
public class FeatureProcessor extends Processor {

    /**
     * To prepare a MySQL query.
     */
    private PreparedStatement preparedStatement;

    /**
     * TrackProcessor constructor.
     *
     * @param folder The folder containing the .track files
     * @throws java.io.IOException If something goes wrong with reading the folder
     */
    public FeatureProcessor(final File folder) throws IOException {
        super(folder);
    }

    /**
     * Reads a file.
     *
     * @param file The file to be read
     * @throws IOException IOException
     */
    protected void readFile(final File file) throws IOException {
        // do something
    }
}
