package models.record;

/**
 * Class to represent a record in the database.
 */
public interface Record {

    /**
     * Equals method.
     *
     * @param o The object to compare with
     * @return True if this object is the same as the other object
     */
    boolean equals(Object o);

    /**
     * Creates a hash code of the object.
     *
     * @return Hash code of the object
     */
    int hashCode();
}
