package models.record;

import java.util.Map.Entry;

public class TrackEntry<K, V> implements Entry<K, V> {

    private final K key;
    private V value;

    public TrackEntry(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }

    public V setValue(V value) {
        V oldValue = this.value;
        this.value = value;
        return oldValue;
    }
}
