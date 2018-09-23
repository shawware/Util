/*
 * Copyright (C) 2017 shawware.com.au
 *
 * License: GNU General Public License V3 (or later)
 * http://www.gnu.org/copyleft/gpl.html
 */
package au.com.shawware.util.test;

import java.util.concurrent.Callable;

import org.junit.Assert;

/**
 * Consolidates common unit test code (for those unit tests that need it).
 * This class is intended to be sub-classed by unit test classes.
 */
@SuppressWarnings({ "nls", "static-method" })
public abstract class AbstractUnitTest
{
    /**
     * Verifies the given code fails as expected.
     *
     * @param code the code under test
     * @param type the type of the exception expected to be thrown
     * @param text the expected error message
     */
    protected final void verifyExceptionThrown(Runnable code, Class<? extends Throwable> type, String text)
    {
        try
        {
            code.run();
            Assert.fail("unexpected success");
        }
        catch (RuntimeException e)
        {
            // This is to be expected, so we verify the exception.
            verifyException(e, type, text);
        }
    }

    /**
     * Verifies the given code fails as expected.
     *
     * @param code the code under test
     * @param type the type of the exception expected to be thrown
     * @param text the expected error message
     */
    protected final void verifyCheckedExceptionThrown(Callable<?> code, Class<? extends Throwable> type, String text)
    {
        try
        {
            code.call();
            Assert.fail("unexpected success");
        }
        catch (Exception e)
        {
            // This is to be expected, so we verify the exception.
            verifyException(e, type, text);
        }
    }

    /**
     * Verifies the given exception has the expected type and message.
     *
     * @param e the exception under test
     * @param type the expected exception type
     * @param text the expected error message
     */
    private void verifyException(Exception e, Class<? extends Throwable> type, String text)
    {
        Assert.assertEquals(type, e.getClass());
        Assert.assertEquals(text, e.getMessage());
    }
}
