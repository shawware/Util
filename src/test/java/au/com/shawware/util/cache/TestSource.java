/*
 * Copyright (C) 2018 shawware.com.au
 *
 * License: GNU General Public License V3 (or later)
 * http://www.gnu.org/copyleft/gpl.html
 */

package au.com.shawware.util.cache;

import java.util.HashMap;
import java.util.Map;

import au.com.shawware.util.ISource;
import au.com.shawware.util.MutableInteger;
import au.com.shawware.util.NotEmpty;

/**
 * Implements a source of {@link MutableInteger}s for test purposes.
 * Each time a value is retrieved it is incremented to simulate a new value.
 *
 * @author <a href="mailto:david.shaw@shawware.com.au">David Shaw</a>
 */
@SuppressWarnings("nls")
public class TestSource implements ISource<String, MutableInteger>
{
    /** A rule for validating the source key. */
    private static final NotEmpty NOT_EMPTY = new NotEmpty("source key");

    /** The set of values in this source. */
    private final Map<String, MutableInteger> mValues;

    /**
     * Constructs a new test source.
     */
    public TestSource()
    {
        mValues = new HashMap<>();
    }

    /**
     * Adds a new key to this source with the given initial value.
     * That is, expands which values to deliver a value for.
     * 
     * @param key the key
     * @param value the initial value for this key
     * 
     * @throws IllegalArgumentException empty key or key is already present
     */
    /*package*/ void addValue(String key, int value)
        throws IllegalArgumentException
    {
        NOT_EMPTY.validate(key);
        if (mValues.containsKey(key))
        {
            throw new IllegalArgumentException("Source key is already present: " + key);
        }
        mValues.put(key, new MutableInteger(value));
    }

    @Override
    public MutableInteger get(String key)
    {
        NOT_EMPTY.validate(key);
        if (!mValues.containsKey(key))
        {
            throw new IllegalArgumentException("Unknown source key: " + key);
        }
        MutableInteger value = mValues.get(key);
        value.increment();
        return value;
    }
}
