/*
 * Copyright (C) 2017 shawware.com.au
 *
 * License: GNU General Public License V3 (or later)
 * http://www.gnu.org/copyleft/gpl.html
 */

package au.com.shawware.util;

/**
 * Define an API that describes an application.
 */
public interface IApplication
{
    /**
     * The full name of an application. Can be any string, ie. can
     * contain whitespace, capitalisation, etc.
     *
     * @return The application's full name.
     */
    String getName();

    /**
     * The short name for an application. Will be a single lower
     * case word with no whitespace.
     *
     * @return The application's short name.
     */
    String getShortName();
}
