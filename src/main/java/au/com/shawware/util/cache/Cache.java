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
 * @param <CacheKeyType> the key type for the cache
 * @param <SourceKeyType> the key type for the source
 * @param <ValueType> the value type
 */
public class Cache<CacheKeyType, SourceKeyType, ValueType> implements ICache<CacheKeyType, ValueType>
{
    /** A rule for validating the cache keys. */
    private final NotNull<CacheKeyType> NOT_NULL = new NotNull<>("cache key"); //$NON-NLS-1$

    /** The values held in this cache. */
    private final Map<SourceKeyType, CachedValue<ValueType>> mValues;
    /** The maximum lifetime of an individual value. */
    private final Duration mLifetime;
    /** The clock to use to manage cache lifetimes. */
    private final ITimeSource mClock;
    /** The source of values to store in the cache. */
    private final ISource<SourceKeyType, ValueType> mSource;
    /** The transformer that maps cache keys to source keys. */
    private final ITypeTransformer<CacheKeyType, SourceKeyType> mTransformer;

    /**
     * Constructs a new cache.
     * 
     * @param lifetime the maximum life time of a value in the cache
     * @param clock the source of time
     * @param source where to source the actual values
     */
    public Cache(Duration lifetime, ITimeSource clock, ISource<SourceKeyType, ValueType> source, ITypeTransformer<CacheKeyType, SourceKeyType> transformer)
    {
        mValues      = new HashMap<>();
        mLifetime    = lifetime;
        mClock       = clock;
        mSource      = source;
        mTransformer = transformer;
    }

    @Override
    public ValueType get(CacheKeyType key)
    {
        NOT_NULL.validate(key);

        SourceKeyType transformedKey = mTransformer.transform(key);
        
        ValueType value;
        if (!mValues.containsKey(transformedKey) ||
            (mValues.get(transformedKey).getExpiryTime() <= mClock.getTimeInMillis()))
        {
            value = refreshFromSource(transformedKey);
        }
        else
        {
            value = mValues.get(transformedKey).getValue();
        }
        return value;
    }

    @Override
    public ValueType refresh(CacheKeyType key)
    {
        NOT_NULL.validate(key);

        SourceKeyType transformedKey = mTransformer.transform(key);

        return refreshFromSource(transformedKey);
    }

    /**
     * Refreshes the value for the given key.
     * 
     * @param key the source key
     * 
     * @return The latest value.
     */
    private ValueType refreshFromSource(SourceKeyType key)
    {
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
        mValues.keySet().forEach(this::refreshFromSource);
    }

    @Override
    public String toString()
    {
        return StringUtil.toString(mLifetime, mValues);
    }
}
