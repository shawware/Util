/*
 * Shawware Java Utility Code - Issues
 *
 * Utility classes for managing issues / errors when processing data.
 * Designed to be used when processing should continue after an issue / error has occurred.
 * This allows more than one issue / error to be found on a single pass through the data.
 * Provides supports for general processing and line-based processing (of text-based data).
 *
 * Copyright (C) 2010, 2015 shawware.com.au
 *
 * License: GNU General Public License V3 (or later)
 * http://www.gnu.org/copyleft/gpl.html
 */

package au.com.shawware.util.issues;

import au.com.shawware.util.SwAssert;

/**
 * Defines a standard issue.
 */
public class Issue
{
    /** The issue's type. */
    protected final IssueType mType;
    /** The issue's description. */
    protected final String mDesc;

    /**
     * Constructs a new issue.
     * 
     * @param type the new issue's type
     * @param desc the new issue's description
     */
    public Issue(final IssueType type, final String desc)
    {
        super();

        assert SwAssert.isNotNull(type);
        assert SwAssert.isNotEmpty(desc);

        mType = type;
        mDesc = desc;
    }

    /**
     * @return this issue's type
     */
    public final IssueType getType()
    {
        return mType;
    }

    /**
     * @return this issue's description
     */
    public final String getDescription()
    {
        return mDesc;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return mType.toString() + ": " + mDesc; //$NON-NLS-1$
    }
}
