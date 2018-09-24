/*
 * Shawware Java Utility Code
 *
 * Copyright (C) 2010, 2015, 2018 shawware.com.au
 *
 * License: GNU General Public License V3 (or later)
 * http://www.gnu.org/copyleft/gpl.html
 */

package au.com.shawware.util.time;

import java.time.Duration;
import java.time.ZonedDateTime;

import au.com.shawware.util.StringUtil;

/**
 * Time source for use in tests that simulates time passing quickly.
 * 
 * To assist with running tests that effectively span long periods
 * of time very quickly, we simulate the passing of time. The client
 * may specify how much time elapses between each call to the time
 * source.
 *
 * @author <a href="mailto:david.shaw@shawware.com.au">David Shaw</a>
 */
public class TestTime implements ITimeSource
{
    /** The interval between clock ticks, ie. each call to this source. */
    private final Duration mInterval;
    /** The current time. */
    private ZonedDateTime mCurrentTime;

    /**
     * Constructs a new time source.
     * 
     * @param increment the interval between each call (in milliseconds)
     */
    public TestTime(Duration interval)
    {
        this(ZonedDateTime.now(), interval);
    }

    public TestTime(ZonedDateTime start, Duration interval)
    {
        mCurrentTime = start;
        mInterval    = interval;
    }

    /* (non-Javadoc)
     * @see au.com.shawware.util.time.ITimeSource#getTimeInMillis()
     */
    @Override
    public long getTimeInMillis()
    {
        long currentTime = mCurrentTime.toInstant().toEpochMilli();
        tick();
        return currentTime;
    }

    /* (non-Javadoc)
     * @see au.com.shawware.util.time.ITimeSource#getDateTime()
     */
    @Override
    public ZonedDateTime getDateTime()
    {
        ZonedDateTime currentTime = mCurrentTime;
        tick();
        return currentTime;
    }

    /**
     * Causes the internal clock to tick, ie. an interval to pass.
     * 
     * @return The clock holding the next instant of time.
     */
    private void tick()
    {
        mCurrentTime = mCurrentTime.plus(mInterval);
    }

    @Override
    public String toString()
    {
        return StringUtil.toString(mCurrentTime, mInterval); 
    }
}
