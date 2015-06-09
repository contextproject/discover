package models.record;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * Second attempt at the Track object class. This class should be easier to extend.
 */
public class Track2 implements Comparable<Track2> {

    /**
     * The entries of the Track.
     */
    private ArrayList<TrackEntry> entries = new ArrayList<TrackEntry>();

    /**
     * The amount of entries stored in this Track object.
     *
     * @return The amount of entries
     */
    public int size() {
        return entries.size();
    }

    /**
     * Determines if this Track object contains any entries.
     *
     * @return True if this Track object contains zero entries.
     */
    public boolean isEmpty() {
        return entries.isEmpty();
    }

    /**
     * Determines if this Track object contains an entry with the provided key.
     *
     * @param key The key of the entry
     * @return True if this Track object contains the entry
     */
    public boolean containsKey(final Object key) {
        for (TrackEntry entry : entries) {
            if (entry.getKey().equals(key)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Determines if this Track object contains an entry with the provided value.
     *
     * @param value The value of the entry
     * @return True if this Track object contain the entry
     */
    public boolean containsValue(final Object value) {
        for (TrackEntry entry : entries) {
            if (entry.getValue().equals(value)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get the value of the entry of the provided key.
     *
     * @param key The key of the entry
     * @return The value of the entry
     */
    public Object get(final Object key) {
        for (TrackEntry entry : entries) {
            if (entry.getKey().equals(key)) {
                return entry.getValue();
            }
        }
        return null;
    }

    /**
     * Store the provided key and value as entry in this Track object.
     *
     * @param key   The key of the entry
     * @param value The value of the entry
     * @return The old value of the key
     */
    public Object put(final String key, final Object value) {
        for (TrackEntry entry : entries) {
            if (entry.getKey().equals(key)) {
                if (entry.getValue().getClass().equals(value.getClass())) {
                    return entry.setValue(value);
                } else {
                    return null;
                }
            }
        }
        add(key, value);
        return null;
    }

    /**
     * Add a new entry to this Track object.
     *
     * @param key   The key of the entry
     * @param value The value of the entry
     */
    private void add(final String key, final Object value) {
        entries.add(new TrackEntry(key, value));
    }

    /**
     * Remove an entry of this Track object.
     *
     * @param key The key of the entry
     * @return The value of the entry
     */
    public Object remove(final Object key) {
        for (TrackEntry entry : entries) {
            if (entry.getKey().equals(key)) {
                Object value = entry.getValue();
                entries.remove(entry);
                return value;
            }
        }
        return null;
    }

    /**
     * Add all entries from the provided map to this Track.
     *
     * @param track The track with the entries to add
     */
    public void putAll(final Track2 track) {
        Set<TrackEntry> entries = track.entrySet();
        for (TrackEntry entry : entries) {
            put(entry.getKey(), entry.getValue());
        }
    }

    /**
     * Clear the entries of this Track object.
     */
    public void clear() {
        entries.clear();
    }

    /**
     * Get the set of keys from this Track object.
     *
     * @return A set of keys
     */
    public Set keySet() {
        Set<Object> result = new TreeSet<Object>();
        for (TrackEntry entry : entries) {
            result.add(entry.getKey());
        }
        return result;
    }

    /**
     * Get a collection of values from this Track object.
     *
     * @return A collection of values
     */
    public Collection values() {
        Collection<Object> result = new ArrayList<Object>();
        for (TrackEntry entry : entries) {
            result.add(entry.getValue());
        }
        return result;
    }

    /**
     * Get the entries of this Track.
     *
     * @return The entries
     */
    public Set<TrackEntry> entrySet() {
        Set<TrackEntry> result = new HashSet<TrackEntry>();
        for (TrackEntry entry : entries) {
            result.add(entry);
        }
        return result;
    }

    /**
     * Add an additional score to the objects existing score.
     *
     * @param addition The additional score that needs to be added.
     */
    public void addScoreToTrack(final double addition) {
        if (containsKey("score")) {
            this.put("score", (Double) this.get("score") + addition);
        } else {
            throw new NullPointerException("The Track does not contain a entry called: \"score\"");
        }
    }

    /**
     * Compares two Track objects with each other.
     * The Track with the higher score get sorted before the other.
     *
     * @param o The other Track
     * @return the value 0 if the scores are numerically equal;
     * a value greater than 0 if this score is numerically less than the other score;
     * and a value greater less 0 if this score is numerically greater than the other score.
     */
    public int compareTo(@Nonnull final Track2 o) {
        double compare = (Double) this.get("score") - (Double) o.get("score");
        if (compare > 0) {
            return -1;
        } else if (compare < 0) {
            return 1;
        }
        return 0;
    }
}
