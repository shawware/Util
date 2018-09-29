/*
 * Copyright (C) 2018 shawware.com.au
 *
 * License: GNU General Public License V3 (or later)
 * http://www.gnu.org/copyleft/gpl.html
 */

package au.com.shawware.util;

/**
 * Specifies the API for retrieving a value by a key.
 *
 * @author <a href="mailto:david.shaw@shawware.com.au">David Shaw</a>
 *
 * @param <KeyType> the generic key type
 * @param <ValueType> the generic value type
 */
public interface ISource<KeyType, ValueType>
{
    /**
     * Retrieves the object (if any) at the given key.
     *
     * @param key the key to lookup
     *
     * @return The corresponding object (or <code>null</code>)
     * 
     * @throws IllegalArgumentException null, empty or unknown key
     */
    ValueType get(KeyType key)
        throws IllegalArgumentException;
}
