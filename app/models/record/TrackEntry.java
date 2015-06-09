package models.record;

/**
 * An entry for a Track object.
 */
public class TrackEntry {

    /**
     * The key of the entry.
     */
    private final String key;

    /**
     * The value of the entry.
     */
    private Object value;

    /**
     * Constructor, generates an entry object for the Track.
     *
     * @param key   The key of the entry.
     * @param value The value of the entry.
     */
    public TrackEntry(final String key, final Object value) {
        this.key = key;
        this.value = value;
    }

    /**
     * Getter of the key of the entry.
     *
     * @return The key
     */
    public String getKey() {
        return key;
    }

    /**
     * Getter of the value of the entry.
     *
     * @return The value
     */
    public Object getValue() {
        return value;
    }

    /**
     * Setter of the value of the entry.
     *
     * @param value The new value
     * @return The old value
     */
    public Object setValue(final Object value) {
        Object oldValue = this.value;
        this.value = value;
        return oldValue;
    }
}
