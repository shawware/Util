/*
 * Copyright (C) 2013 shawware.com.au
 *
 * License: GNU General Public License V3 (or later)
 * http://www.gnu.org/copyleft/gpl.html
 */

package au.com.shawware.util;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;

import org.junit.Test;

/**
 * Unit tests for {@link SwPropertyUtils}. 
 *
 * @author <a href="mailto:david.shaw@shawware.com.au">David Shaw</a>
 */
@SuppressWarnings({ "nls", "static-method" })
public class SwPropertyUtilsTest
{
    /**
     * Test {@link SwPropertyUtils#appendSuffix(String, String)} with valid arguments.
     */
    @Test
    public void appendTests()
    {
        appendTest("a", "b", "a.b");
        appendTest("a.", "b", "a.b");
        appendTest("a", ".b", "a.b");

        appendTest("a.b", "c", "a.b.c");
        appendTest("a.b.", "c", "a.b.c");
        appendTest("a.b", ".c", "a.b.c");

        appendTest("alpha.beta.gamma", "delta", "alpha.beta.gamma.delta");
    }

    /**
     * Test {@link SwPropertyUtils#appendSuffix(String, String)} with invalid arguments.
     */
    @Test
    public void invalidArgumentTests()
    {
        invalidArgumentTest(null, "ok", "empty name");
        invalidArgumentTest("", "ok", "empty name");
        invalidArgumentTest("ok", null, "empty suffix");
        invalidArgumentTest("ok", "", "empty suffix");
    }

    /**
     * Tests that {@link SwPropertyUtils#appendSuffix(String, String)}
     * correctly combines the given name and suffix.
     * 
     * @param name - the name to test
     * @param suffix - the suffix to test
     * @param expectedResult - the expected result
     */
    private void appendTest(final String name, final String suffix, final String expectedResult)
    {
        final String result = SwPropertyUtils.appendSuffix(name, suffix);
        assertThat("appendSuffix(\"" + name + "\", \"" + suffix + "\") failed", result, equalTo(expectedResult));
    }

    /**
     * Tests that {@link SwPropertyUtils#appendSuffix(String, String)} fails
     * correctly (with the given error) for the given name and suffix.
     * 
     * @param name - the name to test
     * @param suffix - the suffix to test
     * @param error - the expected error
     */
    private void invalidArgumentTest(final String name, final String suffix, final String error)
    {
        try
        {
            SwPropertyUtils.appendSuffix(name, suffix);
            fail("appendSuffix(\"" + name + "\", \"" + suffix + "\") was successful");
        }
        catch (AssertionError e)
        {
            assertThat(e.getMessage(), equalTo(error));
        }
    }
}
