/*
 * Copyright (C) 2017 shawware.com.au
 *
 * License: GNU General Public License V3 (or later)
 * http://www.gnu.org/copyleft/gpl.html
 */

package au.com.shawware.util;

/**
 * A place to hold an application so that any layer of the program can access it.
 *
 * Note: there is no guarantee that any layer has set the application.
 * Note: this is not thread-safe; if you request the application before it is set,
 * you will receive <code>null</code>.
 */
public class ApplicationStore
{
    /** The application instance if any. */
    private static IApplication application;

    /**
     * @return The application instance.
     */
    public static IApplication getApplication()
    {
        return application;
    }

    /**
     * Sets the held application for later use.
     *
     * @param application the new instance
     */
    public static void setApplication(IApplication application)
    {
        ApplicationStore.application = application;
    }
}
