/*
 * Copyright (C) 2018 shawware.com.au
 *
 * License: GNU General Public License V3 (or later)
 * http://www.gnu.org/copyleft/gpl.html
 */

package au.com.shawware.util.cache;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import au.com.shawware.util.ISource;
import au.com.shawware.util.NotNull;
import au.com.shawware.util.StringUtil;
import au.com.shawware.util.time.ITimeSource;

/**
 * Implements a simple, time-based cache. 
 *
 * @author <a href="mailto:david.shaw@shawware.com.au">David Shaw</a>
 *
 * @param <KeyType> the key type
 * @param <ValueType> the value type
 */
public class Cache<KeyType, ValueType> implements ICache<KeyType, ValueType>
{
    /** A rule for validating the cache keys. */
    private final NotNull<KeyType> NOT_NULL = new NotNull<>("cache key"); //$NON-NLS-1$

    /** The values held in this cache. */
    private final Map<KeyType, CachedValue<ValueType>> mValues;
    /** The maximum lifetime of an individual value. */
    private final Duration mLifetime;
    /** The clock to use to manage cache lifetimes. */
    private final ITimeSource mClock;
    /** The source of values to store in the cache. */
    private final ISource<KeyType, ValueType> mSource;

    /**
     * Constructs a new cache.
     * 
     * @param lifetime the maximum life time of a value in the cache
     * @param clock the source of time
     * @param source where to source the actual values
     */
    public Cache(Duration lifetime, ITimeSource clock, ISource<KeyType, ValueType> source)
    {
        mValues   = new HashMap<>();
        mLifetime = lifetime;
        mClock    = clock;
        mSource   = source;
    }

    @Override
    public ValueType get(KeyType key)
    {
        NOT_NULL.validate(key);

        ValueType value;
        if (!mValues.containsKey(key) ||
            (mValues.get(key).getExpiryTime() <= mClock.getTimeInMillis()))
        {
            value = refresh(key);
        }
        else
        {
            value = mValues.get(key).getValue();
        }
        return value;
    }

    @Override
    public ValueType refresh(KeyType key)
    {
        NOT_NULL.validate(key);

        ValueType value = mSource.get(key);
        Instant now = Instant.ofEpochMilli(mClock.getTimeInMillis());
        Instant expiry = now.plus(mLifetime);
        CachedValue<ValueType> cacheValue = new CachedValue<>(value, expiry.toEpochMilli());
        mValues.put(key, cacheValue);
        return value;
    }

    @Override
    public void refreshAll()
    {
        mValues.keySet().forEach(this::refresh);
    }

    @Override
    public String toString()
    {
        return StringUtil.toString(mLifetime, mValues);
    }
}
