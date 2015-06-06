package models.record;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * Second attempt at the Track object class. This class should be easier to extend.
 */
public class Track2 implements Map {

    private ArrayList<TrackEntry<Object, Object>> values = new ArrayList<TrackEntry<Object, Object>>();

    public int size() {
        return values.size();
    }

    public boolean isEmpty() {
        return values.isEmpty();
    }

    public boolean containsKey(Object key) {
        for (TrackEntry<Object, Object> trackEntry : values) {
            if (trackEntry.getKey().equals(key)) {
                return true;
            }
        }
        return false;
    }

    public boolean containsValue(Object value) {
        for (TrackEntry<Object, Object> trackEntry : values) {
            if (trackEntry.getValue().equals(value)) {
                return true;
            }
        }
        return false;
    }

    public Object get(Object key) {
        for (TrackEntry<Object, Object> trackEntry : values) {
            if (trackEntry.getKey().equals(key)) {
                return trackEntry.getValue();
            }
        }
        return null;
    }

    public Object put(Object key, Object value) {
        for (TrackEntry<Object, Object> trackEntry : values) {
            if (trackEntry.getKey().equals(key)) {
                if (trackEntry.getValue().getClass().equals(value.getClass())) {
                    return trackEntry.setValue(value);
                } else {
                    return null;
                }
            }
        }
        add(key, value);
        return null;
    }

    private void add(Object key, Object value) {
        values.add(new TrackEntry<Object, Object>(key, value));
    }

    public Object remove(Object key) {
        for (TrackEntry<Object, Object> trackEntry : values) {
            if (trackEntry.getKey().equals(key)) {
                Object value = trackEntry.getValue();
                values.remove(trackEntry);
                return value;
            }
        }
        return null;
    }

    public void putAll(@Nonnull Map m) {

    }

    public void clear() {
        values.clear();
    }

    public Set keySet() {
        return null;
    }

    public Collection values() {
        return null;
    }

    public Set<Entry> entrySet() {
        return null;
    }
}
