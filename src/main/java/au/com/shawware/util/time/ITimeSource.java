/*
 * Shawware Java Utility Code
 *
 * Copyright (C) 2010, 2015, 2018 shawware.com.au
 *
 * License: GNU General Public License V3 (or later)
 * http://www.gnu.org/copyleft/gpl.html
 */

package au.com.shawware.util.time;

import java.time.ZonedDateTime;

/**
 * Defines a source of time.
 *
 * @author <a href="mailto:david.shaw@shawware.com.au">David Shaw</a>
 */
public interface ITimeSource
{
    /**
     * @return The current time in milli-seconds since a fixed epoch.
     */
    public long getTimeInMillis();
    
    /**
     * @return The current date / time.
     */
    public ZonedDateTime getDateTime();
}
