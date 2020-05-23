/*
 * Copyright (C) 2013 shawware.com.au
 *
 * License: GNU General Public License V3 (or later)
 * http://www.gnu.org/copyleft/gpl.html
 */

package au.com.shawware.util;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import org.junit.Test;

/**
 * Unit tests for {@link SwException}.
 * 
 * It seems that JUnit @Rule ExpectedException does not work with {@link AssertionError}s.
 * Consequently our tests need a bit more work. We explore both @Test and manual tests.
 *
 * @author <a href="mailto:david.shaw@shawware.com.au">David Shaw</a>
 */
@SuppressWarnings({ "nls", "static-method" })
public class SwExceptionTest
{
    /**
     * Test the various constructors with invalid arguments.
     */
    @Test
    public void constructorErrorTests()
    {
        try
        {
            new SwException(null);
            fail("construction with null message was successful");
        }
        catch (AssertionError e)
        {
            assertThat(e.getMessage(), equalTo("non-empty message"));
        }

        try
        {
            new SwException("");
            fail("construction with empty message was successful");
        }
        catch (AssertionError e)
        {
            assertThat(e.getMessage(), equalTo("non-empty message"));
        }

        try
        {
            new SwException(null, null);
            fail("construction with null message and cause was successful");
        }
        catch (AssertionError e)
        {
            assertThat(e.getMessage(), equalTo("non-empty message"));
        }

        try
        {
            new SwException("", null);
            fail("construction with empty message and null cause was successful");
        }
        catch (AssertionError e)
        {
            assertThat(e.getMessage(), equalTo("non-empty message"));
        }

        try
        {
            new SwException(null, new Throwable("dummy"));
            fail("construction with null message and non-null cause was successful");
        }
        catch (AssertionError e)
        {
            assertThat(e.getMessage(), equalTo("non-empty message"));
        }

        try
        {
            new SwException("", new Throwable("dummy"));
            fail("construction with empty message and non-null cause was successful");
        }
        catch (AssertionError e)
        {
            assertThat(e.getMessage(), equalTo("non-empty message"));
        }

        try
        {
            new SwException("something", null);
            fail("construction with valid message and null cause was successful");
        }
        catch (AssertionError e)
        {
            assertThat(e.getMessage(), equalTo("null cause"));
        }
    }

    /**
     * Test the various constructors with valid arguments.
     */
    @Test
    public void constructorTests()
    {
        final String msg = "okay";
        final Throwable err = new Throwable(msg);

        SwException ok;

        ok = new SwException(msg);
        assertThat(ok.getMessage(), equalTo(msg));
        assertNull("non-null cause", ok.getCause());

        ok = new SwException(msg, err);
        assertThat(ok.getMessage(), equalTo(msg));
        assertNotNull("null cause", ok.getCause());
        assertThat(ok.getCause().getMessage(), equalTo(msg));
    }

    /**
     * Test the constructor throws an exception with invalid arguments.
     * 
     * @throws AssertionError the generated exception
     */
    @Test (expected = AssertionError.class)
    public void nullMessageTest()
        throws AssertionError
    {
        new SwException(null);
        fail("no assertion error thrown");
    }

    /**
     * Test the constructor throws an exception with invalid arguments.
     * 
     * @throws AssertionError the generated exception
     */
    @Test (expected = AssertionError.class)
    public void emptyMessageTest()
        throws AssertionError
    {
        new SwException("");
        fail("no assertion error thrown");
    }

    /**
     * Test the constructor throws an exception with invalid arguments.
     * 
     * @throws AssertionError the generated exception
     */
    @Test (expected = AssertionError.class)
    public void nullCauseTest()
        throws AssertionError
    {
        new SwException("something", null);
        fail("no assertion error thrown");
    }
}
