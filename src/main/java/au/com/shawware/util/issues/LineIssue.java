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

/**
 * Defines an issue that can occur on a single line of input.
 */
public class LineIssue extends Issue
{
    /** The line number the issue occurred on. */
    private final int mLineNumber;

    /**
     * @param type the new issue's type
     * @param lineNumber the new issue's line number
     * @param desc the new issue's description
     */
    public LineIssue(final IssueType type, final String desc, final int lineNumber)
    {
        super(type, desc);

        assert lineNumber > 0;

        mLineNumber = lineNumber;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return mType.toString() + " on line " + mLineNumber + ": " + mDesc; //$NON-NLS-1$ //$NON-NLS-2$
    }
}
