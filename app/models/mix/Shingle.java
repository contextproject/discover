package models.mix;

import java.util.ArrayList;
import java.util.List;

/**
 * The ShingleSet class contains a sorted set of shingles. *
 * 
 * @since 21-05-2015
 * @version 26-05-2015
 * 
 * @author stefan boodt
 * @author arthur hovenesyan
 */
public class Shingle {

    /**
     * The data that the shingle consists of and operates on.
     */
    private List<Double> data;

    /**
     * @param second
     *            The data to be stored in the shingle.
     */
    public Shingle(final List<Double> second) {
        setData(second);
    }

    /**
     * Creates a Shingle that is an exact copy of the given shingle. Calling
     * this method creates a hard copy of the shingle given. That means that the
     * data is being copied so you can alter the data of one shingle without
     * altering the content of the other.
     * 
     * @param shingle
     *            The Shingle to copy.
     */
    protected Shingle(final Shingle shingle) {
        List<Double> data = new ArrayList<Double>(shingle.size());
        for (Double f : shingle.data) {
            data.add(f);
        }
        setData(data);
    }

    /**
     * Calculates the Jaccard distance between this set and the other set.
     * 
     * @param other
     *            The other set of shingles that this set will be compared to.
     * @return The Jaccard distance between this set and the other set.
     */
    public double jaccardDistance(final Shingle other) {
        Shingle part = this.copy();
        part.getData().retainAll(other.getData());
        part.unique();
        final Shingle union = union(other);
        final double unionsize = union.size();
        final double ans;
        // Some safety for the double rounding errors.
        if (union.size() == 0) {
            ans = 1.0;
        } else {
            ans = (double) part.size() / unionsize;
        }
        return 1.0 - ans;
    }

    /**
     * Unionizes two Shingles. This method uses a functional way of using to
     * ensure that both of the shingles are unaltered. The resulting Shingle
     * is not only a union of the two but also has a unique dataset, meaning a
     * dataset that is free of duplicates.
     * 
     * @param other
     *            The other shingle.
     * @return The union of the two shingles.
     */
    public Shingle union(final Shingle other) {
        List<Double> union = new ArrayList<Double>(this.size() + other.size());
        for (Double f : data) {
            union.add(f);
        }
        for (Double f : other.getData()) {
            union.add(f);
        }
        Shingle shingle = new Shingle(union);
        shingle.unique();
        return shingle;
    }

    /**
     * Returns the size of the Shingle.
     * @return The size of the data in the shingle.
     */
    public int size() {
        return data.size();
    }

    /**
     * Adds float e to the data of the shingle.
     * @param e The float you want to add.
     */
    public void add(final Double e) {
        data.add(e);
    }
    
    /**
     * Retrieves the data used by the Shingle.
     * @return The data the shingle operates on.
     */
    public List<Double> getData() {
        return data;
    }

    /**
     * Changes the data this Shingle operates on to the given list.
     * Since this is changing the current Shingle this should be used with
     * caution.
     * @param second The new data to operate on.
     */
    public void setData(final List<Double> second) {
        this.data = second;
    }
    
    /**
     * {@inheritDoc}
     * 
     * <p>
     * The toString method of the class Shingle returns the string
     * "Shingle()" with the string representation of the data inside of
     * the parenthesis. Actually it simply mentions all values there and
     * ignores the toString method of the data.
     * </p>
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("Shingle(");
        final int prefixlength = builder.length();
        for (Double f : data) {
            builder.append(f.toString() + ", ");
        }
        if (builder.length() > prefixlength + 2) {
            builder.delete(builder.length() - 2, builder.length());
        }
        builder.append(")");
        return builder.toString();
    }

    /**
     * Makes this Shingle unique by removing all doubles from the data.
     */
    protected void unique() {
        List<Double> unique = new ArrayList<Double>(size());
        for (Double f : data) {
            if (!unique.contains(f)) {
                unique.add(f);
            }
        }
        this.setData(unique);
    }

    /**
     * Copies this Shingle. Every subclass should overwrite this method.
     * 
     * @return The copy of this Shingle.
     */
    protected Shingle copy() {
        return new Shingle(this);
    }
    
    /**
     * {@inheritDoc}
     * 
     * <p>
     * Checks if other is equal to the current Shingle.
     * Two Shingles are considered equal if and only if
     * <ul>
     *  <li> The jaccard distance between them is 0.0 </li>
     *  <li> If both are Shingles </li>
     * </ul>
     * </p>
     * <p>
     * Note that this means that the order of the elements in the data is
     * not important. Using the {@link List#equals(Object)} method is accepting
     * that the order is important.
     * </p>
     */
    @Override
    public boolean equals(final Object other) {
        /*
         * This could be done without if but that would make the code more
         * unreadable and makes the code have 4 paths instead of 3 (guess
         * pathcounting is difficult to do automatically).
         */
        if (other instanceof Shingle) {
            final Shingle that = (Shingle) other;
            return this.jaccardDistance(that) == 0.0;
        }
        return false;
    }
    
    /**
     * {@inheritDoc}
     * 
     * <p>
     * The hashcode of a Shingle, is equivalent to the
     * hashCode of it's data. Therefore this method is equivalent to
     * {@link #getData()} and then calling {@link #hashCode()} on it.
     * </p>
     */
    @Override
    public int hashCode() {
        return getData().hashCode();
    }
}
