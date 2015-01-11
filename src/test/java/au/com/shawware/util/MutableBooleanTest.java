/*
 * Copyright (C) 2013 shawware.com.au
 *
 * License: GNU General Public License V3 (or later)
 * http://www.gnu.org/copyleft/gpl.html
 */

package au.com.shawware.util;

import static org.hamcrest.CoreMatchers.equalTo;

import org.junit.Test;
import org.junit.Assert;

/**
 * Unit tests for {@link MutableBoolean}. 
 *
 * @author <a href="mailto:david.shaw@shawware.com.au">David Shaw</a>
 */
@SuppressWarnings("nls")
public class MutableBooleanTest
{
    /**
     * Test the constructor.
     */
    @Test
    @SuppressWarnings("boxing")
    public void constructorTests()
    {
        Assert.assertThat("no value constructor", false, equalTo(new MutableBoolean().getValue()));

        constructorTest(false);
        constructorTest(true);
    }

    /**
     * Tests the correct value is set when the given value is passed to a constructor.
     * 
     * @param value the initial value
     */
    @SuppressWarnings("boxing")
    private void constructorTest(final boolean value)
    {
        final MutableBoolean mb = new MutableBoolean(value);
        Assert.assertThat("constructor(" + value + ")", value, equalTo(mb.getValue()));
    }

    /**
     * Test the {@link MutableBoolean#getValue()} and {@link MutableBoolean#setValue(bool)}.
     */
    @Test
    @SuppressWarnings("boxing")
    public void valueTests()
    {
        final MutableBoolean mb = new MutableBoolean();

        mb.setValue(true);
        Assert.assertThat("setValue(true)", true, equalTo(mb.getValue()));
        Assert.assertThat("toString(true)", "true", equalTo(mb.toString()));
        mb.setValue(false);
        Assert.assertThat("setValue(false)", false, equalTo(mb.getValue()));
        Assert.assertThat("toString(false)", "false", equalTo(mb.toString()));
    }

    /**
     * Test the {@link MutableBoolean#toggle()}.
     */
    @Test
    @SuppressWarnings("boxing")
    public void toggleTests()
    {
        final MutableBoolean mb = new MutableBoolean();

        Assert.assertThat("toggle 1", false, equalTo(mb.getValue()));
        mb.toggle();
        Assert.assertThat("toggle 2", true, equalTo(mb.getValue()));
        mb.toggle();
        Assert.assertThat("toggle 3", false, equalTo(mb.getValue()));
    }

    /**
     * Test the {@link MutableBoolean#equals(Object)}.
     */
    @Test
    public void equalsTests()
    {
        final MutableBoolean mbA = new MutableBoolean();
        final MutableBoolean mbB = new MutableBoolean();

        Assert.assertFalse(mbA.equals(null));

        Assert.assertTrue(mbA.equals(mbA));
        Assert.assertTrue(mbA.equals(mbB));
        Assert.assertTrue(mbB.equals(mbA));

        mbB.setValue(true);
        Assert.assertFalse(mbA.equals(mbB));
        Assert.assertFalse(mbB.equals(mbA));

        mbA.setValue(true);
        Assert.assertTrue(mbA.equals(mbB));
        Assert.assertTrue(mbB.equals(mbA));

        mbB.setValue(false);
        Assert.assertFalse(mbA.equals(mbB));
        Assert.assertFalse(mbB.equals(mbA));

        // Different class!
        Assert.assertFalse(mbA.equals(new Boolean(mbA.getValue())));
    }
}
