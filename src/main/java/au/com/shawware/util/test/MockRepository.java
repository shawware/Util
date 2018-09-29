/*
 * Copyright (C) 2017 shawware.com.au
 *
 * License: GNU General Public License V3 (or later)
 * http://www.gnu.org/copyleft/gpl.html
 */
package au.com.shawware.util.test;


import java.util.HashMap;
import java.util.Map;

import au.com.shawware.util.IRepository;

/**
 * Mock implementation of an {@link IRepository} for use in testing.
 *
 * @param <KeyType> the type of keys used to index values held in the repository
 * @param <ValueType> the type of values held in the repository
 */
public class MockRepository<KeyType, ValueType> implements IRepository<KeyType, ValueType>
{
    /** The internal (in-memory) repository of key/value pairs. */
    protected final Map<KeyType, ValueType> repository;

    /**
     * Constructs and initialises a new instance.
     */
    public MockRepository()
    {
        repository = new HashMap<>();
    }

    @Override
    public ValueType get(KeyType key)
    {
        return repository.get(key);
    }

    @Override
    public void put(KeyType key, ValueType value)
    {
        repository.put(key, value);
    }
}
