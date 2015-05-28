package models.record;

import javax.persistence.Entity;
import java.sql.ResultSet;
import java.sql.SQLException;

@Entity
public class Track implements Record {

    private int trackid;

    public Track() {}

    public Track(ResultSet resultSet) {
        try {
            while (resultSet.next()) {
                trackid = resultSet.getInt("track_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getTrackID() {
        return trackid;
    }
}
