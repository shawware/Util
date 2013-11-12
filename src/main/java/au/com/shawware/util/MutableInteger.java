/*
 * Copyright (C) 2013 shawware.com.au
 *
 * License: GNU General Public License V3 (or later)
 * http://www.gnu.org/copyleft/gpl.html
 */

package au.com.shawware.util;

import java.util.Collection;
import java.util.Map;

/**
 * An {@link Integer} value that can change over time.
 * 
 * The original motivation for this class was to permit {@link Collection}s
 * to hold values that could be modified, rather than replaced with new
 * values, when the need arose. For example, a {@link Map} of counters.
 * 
 * The interface is kept fairly basic. If you need more, take a look at
 * Apache Commons Lang MutableInt (and others).
 *
 * @author <a href="mailto:david.shaw@shawware.com.au">David Shaw</a>
 */
public class MutableInteger
{
    /** The current value. */
    private int mValue;

    /**
     * Construct a new instance, initialised to 0.
     */
    public MutableInteger()
    {
        this(0);
    }

    /**
     * Construct a new instance, initialised to the given value.
     * 
     * @param value the initial value
     */
    public MutableInteger(final int value)
    {
        setValue(value);
    }

    /**
     * @return The current value
     */
    public int getValue()
    {
        return mValue;
    }

    /**
     * Set the current value to the given value.
     * 
     * @param value the new value
     */
    public void setValue(final int value)
    {
        mValue = value;
    }

    /**
     * Increment the current value.
     */
    public void increment()
    {
        mValue++;
    }

    /**
     * Decrement the current value.
     */
    public void decrement()
    {
        mValue--;
    }

    @Override
    public int hashCode()
    {
        return Integer.valueOf(mValue).hashCode();
    }

    @Override
    public boolean equals(final Object that)
    {
        if (this == that)
        {
            return true;
        }
        if (that == null)
        {
            return false;
        }
        if (getClass() != that.getClass())
        {
            return false;
        }
        if (mValue != ((MutableInteger)that).getValue())
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return Integer.toString(mValue);
    }
}
