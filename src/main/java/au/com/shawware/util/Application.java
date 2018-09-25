/*
 * Copyright (C) 2017 shawware.com.au
 *
 * License: GNU General Public License V3 (or later)
 * http://www.gnu.org/copyleft/gpl.html
 */

package au.com.shawware.util;

/**
 * Application API implementation.
 */
public class Application implements IApplication
{
    /** The application's full name. */
    private final String mName;
    /** The application's short name. */
    private final String mShortName;

    /**
     * Constructs and initialises a new instance.
     *
     * @param name the application's full name
     * @param shortName the application's short name
     */
    public Application(String name, String shortName)
    {
        if (StringUtil.isEmpty(name))
        {
            throw new IllegalArgumentException("Empty name"); //$NON-NLS-1$
        }
        if (StringUtil.isEmpty(shortName) || !shortName.matches("\\S+")) //$NON-NLS-1$
        {
            throw new IllegalArgumentException("Invalid short name"); //$NON-NLS-1$
        }
        this.mName      = name;
        this.mShortName = shortName.toLowerCase();
    }

    @Override
    public String getName()
    {
        return mName;
    }

    @Override
    public String getShortName()
    {
        return mShortName;
    }

    @Override
    public String toString()
    {
        return StringUtil.toString(mShortName, mName);
    }
}
