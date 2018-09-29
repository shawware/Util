/*
 * Copyright (C) 2018 shawware.com.au
 *
 * License: GNU General Public License V3 (or later)
 * http://www.gnu.org/copyleft/gpl.html
 */

package au.com.shawware.util.cache;

import au.com.shawware.util.StringUtil;

/**
 * A cached value and its expiry time.
 *
 * @author <a href="mailto:david.shaw@shawware.com.au">David Shaw</a>
 *
 * @param <ValueType> the value type
 */
public class CacheValue<ValueType>
{
    /** The current value. */
    private ValueType mValue;
    /** The current expiry time. */
    private long mExpiryTime;

    /**
     * Constructs a new cache value from the given data.
     * 
     * @param value the initial value
     * @param expiryTime the initial expiry time
     */
    public CacheValue(ValueType value, long expiryTime)
    {
        mValue      = value;
        mExpiryTime = expiryTime;
    }

    /**
     * @return The value.
     */
    public ValueType getValue()
    {
        return mValue;
    }

    /**
     * @return The expiry time.
     */
    public long getExpiryTime()
    {
        return mExpiryTime;
    }

    /**
     * Updates the value and expiry time.
     * 
     * @param value the value to set
     * @param expiryTime the initial expiry time
     */
    public void setValue(ValueType value, long expiryTime)
    {
        mValue      = value;
        mExpiryTime = expiryTime;
    }

    @Override
    @SuppressWarnings("boxing")
    public String toString()
    {
        return StringUtil.toString(mValue, mExpiryTime);
    }
}
