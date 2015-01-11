/*
 * Copyright (C) 2013 shawware.com.au
 *
 * License: GNU General Public License V3 (or later)
 * http://www.gnu.org/copyleft/gpl.html
 */

package au.com.shawware.util;

import org.junit.Test;
import org.junit.Assert;

/**
 * Unit tests for {@link SwAssert}. 
 *
 * @author <a href="mailto:david.shaw@shawware.com.au">David Shaw</a>
 */
@SuppressWarnings("nls")
public class SwAssertTest
{
    @Test
    public void nullTests()
    {
        Assert.assertFalse("null test", SwAssert.isNotNull(null));
        Assert.assertTrue("non-null test", SwAssert.isNotNull(new Object()));
    }

    @Test
    public void emptyTests()
    {
        Assert.assertFalse("null test", SwAssert.isNotEmpty(null));
        Assert.assertFalse("empty string test", SwAssert.isNotEmpty(""));
        Assert.assertTrue("non-empty string test", SwAssert.isNotEmpty("a"));
    }
}
