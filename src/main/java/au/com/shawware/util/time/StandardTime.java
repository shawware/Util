/*
 * Shawware Java Utility Code
 *
 * Copyright (C) 2010, 2015, 2018 shawware.com.au
 *
 * License: GNU General Public License V3 (or later)
 * http://www.gnu.org/copyleft/gpl.html
 */

package au.com.shawware.util.time;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

/**
 * Retrieves the time since the Unix epoch in UTC.
 *
 * @author <a href="mailto:david.shaw@shawware.com.au">David Shaw</a>
 */
public class StandardTime implements ITimeSource
{
    /**
     * Constructs a new time source.
     */
    public StandardTime()
    {
        // Nothing to do at the moment.
    }

    /* (non-Javadoc)
     * @see au.com.shawware.util.time.ITimeSource#getTimeInMillis()
     */
    @Override
    public long getTimeInMillis()
    {
        return Instant.now().toEpochMilli();
    }

    /* (non-Javadoc)
     * @see au.com.shawware.util.time.ITimeSource#getDateTime()
     */
    @Override
    public ZonedDateTime getDateTime()
    {
        return ZonedDateTime.now();
    }
}
