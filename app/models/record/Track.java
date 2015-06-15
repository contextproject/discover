package models.record;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Second attempt at the Track object class. This class should be easier to extend.
 */
public class Track implements Comparable<Track> {

    /**
     * The entries of the Track.
     */
    private final Map<Key<?>, Object> entries = new HashMap<>();

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
     * @param <T> The type of the value of the entry
     * @return True if this Track object contains the entry
     */
    public <T> boolean containsKey(final Key<T> key) {
        return entries.containsKey(key);
    }

    /**
     * Determines if this Track object contains an entry with the provided value.
     *
     * @param value The value of the entry
     * @return True if this Track object contain the entry
     */
    public boolean containsValue(final Object value) {
        return entries.containsValue(value);
    }

    /**
     * Get the value of the entry of the provided key.
     *
     * @param key The key of the entry
     * @param <T> The type of the value of the entry
     * @return The value of the entry
     */
    public <T> T get(final Key<T> key) {
        return key.getType().cast(entries.get(key));
    }

    /**
     * Store the provided key and value as entry in this Track object.
     *
     * @param key   The key of the entry
     * @param value The value of the entry
     * @param <T>   The class type of the value
     */
    public <T> void put(final Key<T> key, final T value) {
        entries.put(key, value);
    }

    /**
     * Removes an entry from the Track object and return the removed value.
     *
     * @param key The key of the entry
     * @param <T> The class type of the value
     * @return The value
     */
    public <T> T remove(final Key<T> key) {
        return key.getType().cast(entries.remove(key));
    }

    /**
     * Clear the entries of this Track object.
     */
    public void clear() {
        entries.clear();
    }

    /**
     * Adds all entries from the provided Track to this Track.
     *
     * @param track The track
     */
    public void putAll(final Track track) {
        Iterator<Map.Entry<Key<?>, Object>> it = track.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Key<?>, Object> entry = it.next();
            Key key = entry.getKey();
            Object value = entry.getValue();
            put(key, value);
            it.remove();
        }
    }

    /**
     * Get all the entries from this Track.
     *
     * @return The set of entries
     */
    public Set<Map.Entry<Key<?>, Object>> entrySet() {
        return entries.entrySet();
    }

    @Override
    public int compareTo(@Nonnull final Track other) {
        return this.get(new Key<>("score", Double.class)).compareTo(
                other.get(new Key<>("score", Double.class)));
    }

    @Override
    public boolean equals(final Object object) {
        if (object instanceof Track) {
            Track other = (Track) object;
            boolean result = true;
            Iterator it = this.entries.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry entry = (Map.Entry) it.next();
                for (Object o : other.entries.entrySet()) {
                    Map.Entry otherentry = (Map.Entry) o;
                    if (entry.getKey().equals(otherentry.getKey())
                            && !entry.getValue().equals(otherentry.getValue())) {
                        result = false;
                        break;
                    }
                }
                it.remove();
            }

            return result;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int result = 0;
        for (Map.Entry entry : entries.entrySet()) {
            result += entry.getValue().hashCode();
        }
        return result;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("[");
        Iterator it = entries.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            result.append(entry.getKey());
            result.append(" = ");
            result.append(entry.getValue());
            it.remove();
        }
        result.append("]");
        return result.toString();
    }
}
