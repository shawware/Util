/*
 * Copyright (C) 2018 shawware.com.au
 *
 * License: GNU General Public License V3 (or later)
 * https://www.gnu.org/copyleft/gpl.html
 */

package au.com.shawware.util.test;

import org.junit.Assert;
import org.junit.Test;

/**
 * Verify our configuration framework.
 */
@SuppressWarnings({ "nls", "boxing"})
public class ConfigurationUnitTests extends AbstractUnitTest
{
    @Test
    public void testMockConfiguration()
    {
        MockConfiguration cfg = new MockConfiguration();

        Assert.assertEquals(MockConfiguration.DEFAULT_STRING_VALUE, cfg.getString("iplus.fwk.util.empty"));
        Assert.assertEquals(MockConfiguration.DEFAULT_INTEGER_VALUE, cfg.getInteger("iplus.fwk.util.empty"));
        Assert.assertEquals(MockConfiguration.DEFAULT_BOOLEAN_VALUE, cfg.getBoolean("iplus.fwk.util.empty"));

        cfg.set("iplus.fwk.util.alpha", "alpha");
        Assert.assertEquals("alpha", cfg.getString("iplus.fwk.util.alpha"));
        verifyExceptionThrown(() -> cfg.getBoolean("iplus.fwk.util.alpha"),
                ClassCastException.class, "Cannot cast java.lang.String to java.lang.Boolean");
        verifyExceptionThrown(() -> cfg.getInteger("iplus.fwk.util.alpha"),
                ClassCastException.class, "Cannot cast java.lang.String to java.lang.Integer");

        cfg.set("iplus.fwk.util.beta", 25);
        Assert.assertEquals(25, cfg.getInteger("iplus.fwk.util.beta"));

        verifyExceptionThrown(() -> cfg.getBoolean("iplus.fwk.util.beta"),
                ClassCastException.class, "Cannot cast java.lang.Integer to java.lang.Boolean");
        verifyExceptionThrown(() -> cfg.getString("iplus.fwk.util.beta"),
                ClassCastException.class, "Cannot cast java.lang.Integer to java.lang.String");

        cfg.set("iplus.fwk.util.gamma", true);
        Assert.assertEquals(true, cfg.getBoolean("iplus.fwk.util.gamma"));

        verifyExceptionThrown(() -> cfg.getInteger("iplus.fwk.util.gamma"),
                ClassCastException.class, "Cannot cast java.lang.Boolean to java.lang.Integer");
        verifyExceptionThrown(() -> cfg.getString("iplus.fwk.util.gamma"),
                ClassCastException.class, "Cannot cast java.lang.Boolean to java.lang.String");
    }
}
