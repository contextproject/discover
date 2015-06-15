package models.record;

/**
 * Custom made key to prevent ClassCastExceptions in the Track object.
 *
 * @param <T> The type of the value
 */
public class Key<T> {

    /**
     * The identifier of the key.
     */
    private final String identifier;

    /**
     * The class type of the key.
     */
    private final Class<T> type;

    /**
     * Constructor.
     *
     * @param identifier The identifier of the key
     * @param type       The class type of the key
     */
    public Key(final String identifier, final Class<T> type) {
        this.identifier = identifier;
        this.type = type;
    }

    /**
     * Equals method.
     *
     * @param object The other object
     * @return True if keys are equals
     */
    public boolean equals(final Object object) {
        if (object instanceof Key) {
            Key other = (Key) object;
            return this.getIdentifier().equals(other.getIdentifier())
                    && this.getType().equals(other.getType());
        }
        return false;
    }

    /**
     * Hashcode method.
     *
     * @return The hashcode of this key object
     */
    public int hashCode() {
        return this.getIdentifier().hashCode();
    }

    /**
     * Getter of the identifier of the key.
     *
     * @return The identifier
     */
    public String getIdentifier() {
        return identifier;
    }

    /**
     * Getter of the class type of the key.
     *
     * @return The class type
     */
    public Class<T> getType() {
        return type;
    }
}
