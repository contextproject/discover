package models.record;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Second attempt at the Track object class. This class should be easier to extend.
 */
public class Track2 {

    /**
     * The entries of the Track.
     */
    private ArrayList<TrackEntry<Object, Object>> entries =
            new ArrayList<TrackEntry<Object, Object>>();

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
    public boolean containsKey(Object key) {
        for (TrackEntry<Object, Object> entry : entries) {
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
    public boolean containsValue(Object value) {
        for (TrackEntry<Object, Object> entry : entries) {
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
    public Object get(Object key) {
        for (TrackEntry<Object, Object> entry : entries) {
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
    public Object put(Object key, Object value) {
        for (TrackEntry<Object, Object> entry : entries) {
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
    private void add(Object key, Object value) {
        entries.add(new TrackEntry<Object, Object>(key, value));
    }

    /**
     * Remove an entry of this Track object.
     *
     * @param key The key of the entry
     * @return The value of the entry
     */
    public Object remove(Object key) {
        for (TrackEntry<Object, Object> entry : entries) {
            if (entry.getKey().equals(key)) {
                Object value = entry.getValue();
                entries.remove(entry);
                return value;
            }
        }
        return null;
    }

    /**
     * Clear the entries of this Track object.
     */
    public void clear() {
        entries.clear();
    }

    /**
     * Get the entries
     */
    public Set<TrackEntry> entrySet() {
        Set<TrackEntry> result = new HashSet<TrackEntry>();
        for(TrackEntry entry : entries) {
            result.add(entry);
        }
        return result;
    }
}
