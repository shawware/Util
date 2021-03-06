/*
 * Copyright (C) 2013, 2014 shawware.com.au
 *
 * License: GNU General Public License V3 (or later)
 * http://www.gnu.org/copyleft/gpl.html
 */

package au.com.shawware.util;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;
import org.junit.Assert;

/**
 * Unit tests for {@link MutableInteger}.
 *
 * @author <a href="mailto:david.shaw@shawware.com.au">David Shaw</a>
 */
@SuppressWarnings({ "boxing", "nls", "static-method" })
public class MutableIntegerTest
{
    /**
     * Test the constructor.
     */
    @Test
    public void constructorTests()
    {
        assertThat("no value constructor", 0, equalTo(new MutableInteger().getValue()));

        constructorTest(0);
        constructorTest(7);
        constructorTest(-11);
    }

    /**
     * Tests the correct value is set when the given value is passed to a constructor.
     * 
     * @param value the initial value
     */
    private void constructorTest(final int value)
    {
        final MutableInteger mi = new MutableInteger(value);
        assertThat("constructor(" + value + ")", value, equalTo(mi.getValue()));
    }

    /**
     * Test the {@link MutableInteger#getValue()} and {@link MutableInteger#setValue(int)}.
     */
    @Test
    public void valueTests()
    {
        final MutableInteger mi = new MutableInteger();

        mi.setValue(7);
        assertThat("setValue(7)", 7, equalTo(mi.getValue()));
        assertThat("toString(7)", "7", equalTo(mi.toString()));
        mi.setValue(-11);
        assertThat("setValue(-11)", -11, equalTo(mi.getValue()));
        assertThat("toString(-11)", "-11", equalTo(mi.toString()));
        mi.setValue(0);
        assertThat("setValue(0)", 0, equalTo(mi.getValue()));
        assertThat("toString(0)", "0", equalTo(mi.toString()));
    }

    /**
     * Test the {@link MutableInteger#increment()}, {@link MutableInteger#incrementBy(int)},
     * {@link MutableInteger#decrement()} and {@link MutableInteger#decrementBy(int)}.
     */
    @Test
    public void incDecTests()
    {
        final MutableInteger mi = new MutableInteger();

        mi.increment();
        assertThat("inc-dec 1", 1, equalTo(mi.getValue()));
        mi.incrementBy(1);
        assertThat("inc-dec 2", 2, equalTo(mi.getValue()));
        mi.increment();
        assertThat("inc-dec 3", 3, equalTo(mi.getValue()));
        mi.decrement();
        assertThat("inc-dec 4", 2, equalTo(mi.getValue()));
        mi.decrementBy(1);
        assertThat("inc-dec 5", 1, equalTo(mi.getValue()));
        mi.decrement();
        assertThat("inc-dec 6", 0, equalTo(mi.getValue()));
        mi.decrement();
        assertThat("inc-dec 7", -1, equalTo(mi.getValue()));
        mi.decrement();
        assertThat("inc-dec 8", -2, equalTo(mi.getValue()));
        mi.increment();
        assertThat("inc-dec 9", -1, equalTo(mi.getValue()));
        mi.incrementBy(5);
        assertThat("inc-dec 10", 4, equalTo(mi.getValue()));
        mi.decrementBy(8);
        assertThat("inc-dec 11", -4, equalTo(mi.getValue()));
    }

    /**
     * Test the {@link MutableInteger#equals(Object)}.
     */
    @Test
    @SuppressWarnings("unlikely-arg-type")
    public void equalsTests()
    {
        final MutableInteger miA = new MutableInteger();
        final MutableInteger miB = new MutableInteger();

        Assert.assertFalse(miA.equals(null));

        Assert.assertTrue(miA.equals(miA));
        Assert.assertTrue(miA.equals(miB));
        Assert.assertTrue(miB.equals(miA));

        miB.setValue(5);
        Assert.assertFalse(miA.equals(miB));
        Assert.assertFalse(miB.equals(miA));

        miA.setValue(-3);
        Assert.assertFalse(miA.equals(miB));
        Assert.assertFalse(miB.equals(miA));

        miA.setValue(miB.getValue());
        Assert.assertTrue(miA.equals(miB));
        Assert.assertTrue(miB.equals(miA));

        // Different class!
        Assert.assertFalse(miA.equals(new Integer(miA.getValue())));
    }
}
