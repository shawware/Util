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

import java.util.HashMap;
import java.util.Map;

/**
 * Defines a simple holder for line-number based issues.
 */
@SuppressWarnings("boxing") // We're happy for line numbers to be boxed.
public class LineIssueHolder extends IssueHolder
{
    /** Maps line number to warning. */
    private final Map<Integer, LineIssue> mWarnings;
    /** Maps line number to error. */
    private final Map<Integer, LineIssue> mErrors;

    /**
     * Constructs a new issue holder.
     */
    public LineIssueHolder()
    {
        super();
        mWarnings = new HashMap<Integer, LineIssue>();
        mErrors = new HashMap<Integer, LineIssue>();
    }

    /**
     * Adds a warning to this issue holder.
     * 
     * @param lineNumber the line number the warning occurred on
     * @param desc the warning description
     */
    public void addWarning(final int lineNumber, final String desc)
    {
        final LineIssue warning = new LineIssue(IssueType.WARNING, desc, lineNumber);
        addIssue(warning);
        mWarnings.put(lineNumber, warning);
    }

    /**
     * Adds an error to this issue holder.
     * 
     * @param lineNumber the line number the error occurred on
     * @param desc the error description
     */
    public void addError(final int lineNumber, final String desc)
    {
        final LineIssue error = new LineIssue(IssueType.ERROR, desc, lineNumber);
        addIssue(error);
        mErrors.put(lineNumber, error);
    }

    /**
     * Reports whether this issue holder has a warning on the given line number.
     * 
     * @param lineNumber the line number to test
     * 
     * @return Whether a warning is present on the given line number.
     */
    public boolean hasWarning(final int lineNumber)
    {
        return mWarnings.containsKey(lineNumber);
    }

    /**
     * Reports whether this issue holder has an error on the given line number.
     * 
     * @param lineNumber the line number to test
     * 
     * @return Whether an error is present on the given line number.
     */
    public boolean hasError(final int lineNumber)
    {
        return mErrors.containsKey(lineNumber);
    }

    /**
     * Returns the warning at the given line number.
     * 
     * @param lineNumber the line number to look for
     * 
     * @return The warning at the given line number.
     */
    public String warningAt(final int lineNumber)
    {
        return mWarnings.get(lineNumber).getDescription();
    }

    /**
     * Returns the error at the given line number.
     * 
     * @param lineNumber the line number to look for
     * 
     * @return The error at the given line number.
     */
    public String errorAt(final int lineNumber)
    {
        return mErrors.get(lineNumber).getDescription();
    }
}
