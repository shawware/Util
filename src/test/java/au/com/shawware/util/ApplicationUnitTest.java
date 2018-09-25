/*
 * Copyright (C) 2017 shawware.com.au
 *
 * License: GNU General Public License V3 (or later)
 * http://www.gnu.org/copyleft/gpl.html
 */

package au.com.shawware.util;

import org.junit.Assert;
import org.junit.Test;

import au.com.shawware.util.test.AbstractUnitTest;

/**
 * Exercise and verify our basic {@link IApplication} implementation.
 */
public class ApplicationUnitTest extends AbstractUnitTest
{
    @Test
    @SuppressWarnings("nls")
    public void testApplication()
    {
        IApplication a;

        a = new Application("Unit Test", "test");
        Assert.assertEquals("test", a.getShortName());
        Assert.assertEquals("Unit Test", a.getName());

        a = new Application("Unit Test", "Test");
        Assert.assertEquals("test", a.getShortName());
        Assert.assertEquals("Unit Test", a.getName());

        verifyExceptionThrown(() -> new Application(null, null), IllegalArgumentException.class, "Empty name");
        verifyExceptionThrown(() -> new Application("", null), IllegalArgumentException.class, "Empty name");
        verifyExceptionThrown(() -> new Application("A", null), IllegalArgumentException.class, "Invalid short name");
        verifyExceptionThrown(() -> new Application("A", ""), IllegalArgumentException.class, "Invalid short name");
        verifyExceptionThrown(() -> new Application("A", "\t"), IllegalArgumentException.class, "Invalid short name");
        verifyExceptionThrown(() -> new Application("A", "B C"), IllegalArgumentException.class, "Invalid short name");
    }
}
