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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Defines a simple holder for issues that track number of warnings and errors.
 */
public class IssueHolder
{
    /** The number of warnings found. */
    private int mNumWarnings;
    /** The number of errors found. */
    private int mNumErrors;
    /** The issues in the order they were added. */
    private final List<Issue> mIssues;

    /**
     * Construct a new issue holder.
     */
    public IssueHolder()
    {
        mNumWarnings = 0;
        mNumErrors = 0;
        mIssues = new ArrayList<Issue>();
    }

    /**
     * Returns a boolean indicating whether this issue holder is "okay". That
     * is, there are no warnings or errors.
     * 
     * @return the state of this issue holder
     */
    public final boolean isOkay()
    {
        return ((mNumWarnings == 0) && (mNumErrors == 0));
    }

    /**
     * Returns the number of warnings in this holder.
     * 
     * @return The number of warnings in this holder.
     */
    public final int numberOfWarnings()
    {
        return mNumWarnings;
    }

    /**
     * Returns the number of errors in this holder.
     * 
     * @return The number of errors in this holder.
     */
    public final int numberOfErrors()
    {
        return mNumErrors;
    }

    /**
     * Adds a warning to this holder.
     * 
     * @param desc the warning description
     */
    public final void addWarning(final String desc)
    {
        final Issue warning = new Issue(IssueType.WARNING, desc);
        addIssue(warning);
    }

    /**
     * Adds an error to this holder.
     * 
     * @param desc the error description
     */
    public final void addError(final String desc)
    {
        final Issue error = new Issue(IssueType.ERROR, desc);
        addIssue(error);
    }

    /**
     * Adds the given issue to this holder.
     * 
     * @param issue the issue to add
     */
    protected final void addIssue(final Issue issue)
    {
        if (issue.mType.equals(IssueType.WARNING))
        {
            mNumWarnings++;
        }
        else
        {
            mNumErrors++;
        }
        mIssues.add(issue);
    }

    /**
     * Returns an iterator over the issues in this holder.
     * 
     * @return The issue iterator.
     */
    public final Iterator<Issue> issues()
    {
        return mIssues.iterator();
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        final StringBuilder sb = new StringBuilder();
        for (final Issue i : mIssues)
        {
            sb.append(i + "\n"); //$NON-NLS-1$
        }
        return sb.toString();
    }
}
