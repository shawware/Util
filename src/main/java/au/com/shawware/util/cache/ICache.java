/*
 * Copyright (C) 2018 shawware.com.au
 *
 * License: GNU General Public License V3 (or later)
 * http://www.gnu.org/copyleft/gpl.html
 */

package au.com.shawware.util.cache;

/**
 * Specifies the Cache API. This is intended to be used where: (a) the
 * values being stored are expensive to retrieve or generate; and (b)
 * they have a known lifetime or are requested more often than they change.
 *
 * @author <a href="mailto:david.shaw@shawware.com.au">David Shaw</a>
 *
 * @param <KeyType> the key type
 * @param <ValueType> the value type
 */
public interface ICache<KeyType, ValueType>
{
    /**
     * Retrieves the value for the given key. The first retrieval
     * can be expensive. Subsequent retrievals should be fast
     * until the value expires.
     * 
     * @param key the key to lookup
     * 
     * @return The corresponding value.
     * 
     * @throws IllegalArgumentException null or unknown key
     */
    public ValueType get(KeyType key)
        throws IllegalArgumentException;

    /**
     * Refreshes the value corresponding to the key whether
     * or not is has expired in the cache. This can be expensive.
     * 
     * @param key the key to refresh
     * 
     * @return the refreshed value
     * 
     * @throws IllegalArgumentException null or unknown key
     */
    public ValueType refresh(KeyType key)
        throws IllegalArgumentException;

    /**
     * Refreshes all the values in the cache whether or not they
     * have expired. This can be expensive.
     */
    public void refreshAll();
}
