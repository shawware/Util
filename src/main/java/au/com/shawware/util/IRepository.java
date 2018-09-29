/*
 * Copyright (C) 2017 shawware.com.au
 *
 * License: GNU General Public License V3 (or later)
 * http://www.gnu.org/copyleft/gpl.html
 */

package au.com.shawware.util;

/**
 * Simple repository API for abstracting the storage mechanism.
 *
 * @param <KeyType> the generic key type
 * @param <ValueType> the generic value type
 */
public interface IRepository<KeyType, ValueType> extends ISource<KeyType, ValueType>
{
    /**
     * Adds the given object to the repository at the given key.
     *
     * @param key the key to use
     * @param value the object to add
     * 
     * @throws IllegalArgumentException null or empty key
     */
    void put(KeyType key, ValueType value)
        throws IllegalArgumentException;
}
