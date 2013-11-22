/*
 * Copyright (C) 2013 shawware.com.au
 *
 * License: GNU General Public License V3 (or later)
 * http://www.gnu.org/copyleft/gpl.html
 */

package au.com.shawware.util;

/**
 * An {@link Boolean} value that can change over time.
 * Similar to {@link MutableInteger}.
 * Perhaps we should look at abstracting common code via a template?
 * 
 * The interface is kept fairly basic. If you need more, take a look at
 * Apache Commons Lang MutableBoolean (and others).
 *
 * @author <a href="mailto:david.shaw@shawware.com.au">David Shaw</a>
 */
public class MutableBoolean
{
    /* The current value. */
    private boolean mValue;

    /**
     * Default constructor. Initial value is <code>false</code>.
     */
    public MutableBoolean()
    {
        this(false);
    }

    /**
     * Constructs a new instance with the given value.
     *
     * @param value the initial value
     */
    public MutableBoolean(final boolean value)
    {
        super();
        mValue = value;
    }

    /**
     * @return The current value
     */
    public boolean getValue()
    {
        return mValue;
    }

    /**
     * Set the current value to the given value.
     * 
     * @param value the new value
     */
    public void setValue(final boolean value)
    {
        mValue = value;
    }

    /**
     * Toggles (logically negates) the current value
     */
    public void toggle()
    {
        mValue = !mValue;
    }

    /**
     * @return Whether the current value is <code>true</code>
     */
    public boolean isTrue()
    {
        return mValue;
    }

    /**
     * @return Whether the current value is <code>false</code>
     */
    public boolean isFalse()
    {
        return !mValue;
    }

    @Override
    public int hashCode()
    {
        return Boolean.valueOf(mValue).hashCode();
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
        if (mValue != ((MutableBoolean)that).getValue())
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return Boolean.toString(mValue);
    }
}
