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
 * Defines the issue types.
 */
public enum IssueType
{
    /**
     * Warn of an issue where processing can proceed.
     */
    WARNING,
    /**
     * Report an error that requires processing stop.
     */
    ERROR
}
