/*
 * Copyright (C) 2013, 2014 shawware.com.au
 *
 * License: GNU General Public License V3 (or later)
 * http://www.gnu.org/copyleft/gpl.html
 */

package au.com.shawware.util;

import static org.hamcrest.CoreMatchers.equalTo;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Unit tests for {@link ValueCounter}. 
 *
 * @author <a href="mailto:david.shaw@shawware.com.au">David Shaw</a>
 */
@SuppressWarnings({ "boxing", "nls", "static-method" })
public class ValueCounterTest
{
    /** The first value. */
    private static final String VALUE_1 = "Test-1";
    /** The second value. */
    private static final String VALUE_2 = "Test-2";
    /** The third value. */
    private static final String VALUE_3 = "Test-3";

    /**
     * Test out the ValueCounter interface.
     */
    @Test
    public void counterTests()
    {
        final ValueCounter<String> ctr = new ValueCounter<String>();
        checkCounts(ctr, 0, 0, 0, 0, 0);

        ctr.initialiseCount(VALUE_2);
        checkCounts(ctr, 1, 0, 0, 0, 0);

        ctr.countValue(VALUE_1);
        checkCounts(ctr, 2, 1, 0, 0, 1);

        ctr.countValue(VALUE_1, 3);
        ctr.countValue(VALUE_2);
        checkCounts(ctr, 2, 4, 1, 0, 5);

        ctr.countValue(VALUE_2, 4);
        ctr.countValue(VALUE_2);
        checkCounts(ctr, 2, 4, 6, 0, 10);

        ctr.initialiseCount(VALUE_3);
        ctr.countValue(VALUE_2);
        ctr.countValue(VALUE_2);
        ctr.countValue(VALUE_1);
        checkCounts(ctr, 3, 5, 8, 0, 13);

        ctr.countValue(VALUE_3);
        checkCounts(ctr, 3, 5, 8, 1, 14);

        ctr.countValue(VALUE_3, 2);
        checkCounts(ctr, 3, 5, 8, 3, 16);
    }

    /**
     * Checks the given counter has the expected dimension and counts.
     * 
     * @param ctr the counter to test
     * @param expectedSize the expected number of values
     * @param expectedCount1 the expected count of value 1
     * @param expectedCount2 the expected count of value 2
     * @param expectedCount3 the expected count of value 3
     * @param expectedTotal the expected total count for all values
     */
    private void checkCounts(final ValueCounter<String> ctr,
            final int expectedSize, final int expectedCount1,
            final int expectedCount2, final int expectedCount3,
            final int expectedTotal)
    {
        assertThat(ctr.values().size(), equalTo(expectedSize));
        assertThat(ctr.count(VALUE_1), equalTo(expectedCount1));
        assertThat(ctr.count(VALUE_2), equalTo(expectedCount2));
        assertThat(ctr.count(VALUE_3), equalTo(expectedCount3));
        assertThat(ctr.totalCount(), equalTo(expectedTotal));
    }
}
