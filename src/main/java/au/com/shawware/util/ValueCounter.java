/*
 * Copyright (C) 2013 shawware.com.au
 *
 * License: GNU General Public License V3 (or later)
 * http://www.gnu.org/copyleft/gpl.html
 */

package au.com.shawware.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 *
 * Counts the number of occurrences of the set of values of type T.
 * 
 * @param <T> the type of the values to count
 *
 * @author <a href="mailto:david.shaw@shawware.com.au">David Shaw</a>
 */
public class ValueCounter<T>
{
    /** The values and their counts. */
    private final Map<T, MutableInteger> mValues;

    /**
     * Constructs a new, empty counter.
     */
    public ValueCounter()
    {
        mValues = new HashMap<T, MutableInteger>();
    }

    /**
     * Counts an instance of the given value.
     * 
     * @param value the value to count
     */
    public void countValue(final T value)
    {
        final MutableInteger mi;
        if (mValues.containsKey(value))
        {
            mi = mValues.get(value);
        }
        else
        {
            mi = new MutableInteger();
            mValues.put(value, mi);
        }
        mi.increment();
    }

    /**
     * The set of values currently in this counter.
     * 
     * @return The set of values.
     */
    public Set<T> values()
    {
        return mValues.keySet();
    }

    /**
     * The current count for the given value.
     * 
     * @param value the value to return the count for
     * 
     * @return The given value's current count
     */
    public int count(final T value)
    {
        return mValues.containsKey(value) ? mValues.get(value).getValue() : 0;
    }
}
