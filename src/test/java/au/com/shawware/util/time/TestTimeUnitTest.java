/*
 * Copyright (C) 2018 shawware.com.au
 *
 * License: GNU General Public License V3 (or later)
 * http://www.gnu.org/copyleft/gpl.html
 */

package au.com.shawware.util.time;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.Assert;
import org.junit.Test;

/**
 * Exercises the {@link TestTime} class.
 *
 * @author <a href="mailto:david.shaw@shawware.com.au">David Shaw</a>
 */
public class TestTimeUnitTest
{
    /**
     * Verify the test implementation of {@link ITimeSource}.
     */
    @Test
    @SuppressWarnings({ "nls", "static-method" })
    public void testImpl()
    {
        final String[] dateFixtures =
        {
            "2018-09-23T17:00:00Z",
            "2018-09-23T17:05:00Z",
            "2018-09-23T17:10:00Z",
            "2018-09-23T17:15:00Z",
        };
        
        ITimeSource time = new TestTime(ZonedDateTime.parse(dateFixtures[0]), Duration.ofMinutes(5));

        for (String date : dateFixtures)
        {
            Assert.assertEquals(date, time.getDateTime().format(DateTimeFormatter.ISO_INSTANT));
        }

        final long[] millisFixtures =
        {
            1537722000000L,
            1537722600000L,
            1537723200000L,
            1537723800000L,
        };

        time = new TestTime(ZonedDateTime.parse(dateFixtures[0]), Duration.ofMinutes(10));

        for (long millis : millisFixtures)
        {
            Assert.assertEquals(millis, time.getTimeInMillis());
        }
    }
}
