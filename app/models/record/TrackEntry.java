package models.record;

import java.util.Map.Entry;

/**
 * An entry for the Track object.
 *
 * @param <K> The key of the entry
 * @param <V> The value of the entry
 */
public class TrackEntry<K, V> implements Entry<K, V> {

    /**
     * The key of the entry.
     */
    private final K key;

    /**
     * The value of the entry.
     */
    private V value;

    /**
     * Constructor, generates an entry object for the Track.
     *
     * @param key   The key of the entry.
     * @param value The value of the entry.
     */
    public TrackEntry(K key, V value) {
        this.key = key;
        this.value = value;
    }

    /**
     * Getter of the key of the entry.
     *
     * @return The key
     */
    public K getKey() {
        return key;
    }

    /**
     * Getter of the value of the entry.
     *
     * @return The value
     */
    public V getValue() {
        return value;
    }

    /**
     * Setter of the value of the entry.
     *
     * @param value The new value
     * @return The old value
     */
    public V setValue(V value) {
        V oldValue = this.value;
        this.value = value;
        return oldValue;
    }
}
