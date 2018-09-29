/*
 * Copyright (C) 2017 shawware.com.au
 *
 * License: GNU General Public License V3 (or later)
 * http://www.gnu.org/copyleft/gpl.html
 */
package au.com.shawware.util.test;

import au.com.shawware.util.IConfiguration;
import au.com.shawware.util.StringUtil;

/**
 * Mock implementation of an {@link IConfiguration} repository.
 * Use with care, ie. you must retrieve a value according to the type you added it with.
 */
@SuppressWarnings("boxing")
public class MockConfiguration extends MockRepository<String, Object> implements IConfiguration
{
    /** The default value to use when a key is not present. */
    /*package*/ static final String DEFAULT_STRING_VALUE = StringUtil.EMPTY;
    /** The default value to use when a key is not present. */
    /*package*/ static final int DEFAULT_INTEGER_VALUE = 0;
    /** The default value to use when a key is not present. */
    /*package*/ static final boolean DEFAULT_BOOLEAN_VALUE = false;

    /**
     * Constructs and initialises a new instance.
     */
    public MockConfiguration() {}

    /**
     * Sets the value for the given key.
     *
     * @param key the property key
     * @param value the new value
     */
    public void set(String key, String value)
    {
        repository.put(key, value);
    }

    /**
     * Sets the value for the given key.
     *
     * @param key the property key
     * @param value the new value
     */
    public void set(String key, int value)
    {
        repository.put(key, value);
    }

    /**
     * Sets the value for the given key.
     *
     * @param key the property key
     * @param value the new value
     */
    public void set(String key, boolean value)
    {
        repository.put(key, value);
    }

    @Override
    public String getString(String key)
    {
        return getValue(String.class, key, DEFAULT_STRING_VALUE);
    }

    @Override
    public int getInteger(String key)
    {
        return getValue(Integer.class, key, DEFAULT_INTEGER_VALUE);
    }

    @Override
    public boolean getBoolean(String key)
    {
        return getValue(Boolean.class, key, DEFAULT_BOOLEAN_VALUE);
    }

    /**
     * Abstracts the common code for retrieving a value from the repo.
     *
     * @param clazz the value's type
     * @param key the value's key
     * @param defaultValue the default value to use if not present
     * @param <T> the value's type
     *
     * @return The value.
     */
    private <T> T getValue(Class<T> clazz, String key, T defaultValue)
    {
        T value;
        if (repository.containsKey(key))
        {
            value = clazz.cast(repository.get(key));
        }
        else
        {
            value = defaultValue;
        }
        return value;
    }
}
