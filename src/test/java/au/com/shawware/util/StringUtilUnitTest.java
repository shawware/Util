/*
 * Copyright (C) 2015 shawware.com.au
 *
 * License: GNU General Public License V3 (or later)
 * http://www.gnu.org/copyleft/gpl.html
 */

package au.com.shawware.util;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for {@link StringUtil}.
 *
 * @author <a href="mailto:david.shaw@shawware.com.au">David Shaw</a>
 */
@SuppressWarnings("nls")
public class StringUtilUnitTest
{
    /**
     * Test {@link StringUtil#isEmpty(String)} and {@link StringUtil#isNotEmpty(String)}.
     */
    @Test
    public void emptyString()
    {
        Assert.assertTrue(StringUtil.isEmpty(null));
        Assert.assertTrue(StringUtil.isEmpty(""));
        Assert.assertFalse(StringUtil.isEmpty("foo"));

        Assert.assertFalse(StringUtil.isNotEmpty(null));
        Assert.assertFalse(StringUtil.isNotEmpty(""));
        Assert.assertTrue(StringUtil.isNotEmpty("foo"));
    }

    /**
     * Test {@link StringUtil#areEmpty(String[])}.
     */
    @Test
    public void emptyStrings()
    {
        Assert.assertTrue(StringUtil.areEmpty(null));
        Assert.assertTrue(StringUtil.areEmpty(new String[0]));
        Assert.assertTrue(StringUtil.areEmpty(new String[1]));
        Assert.assertTrue(StringUtil.areEmpty(new String[]{}));
        Assert.assertTrue(StringUtil.areEmpty(new String[]{null}));
        Assert.assertTrue(StringUtil.areEmpty(new String[]{""}));
        Assert.assertTrue(StringUtil.areEmpty(new String[]{"", null}));
        Assert.assertTrue(StringUtil.areEmpty(new String[]{null, ""}));
        Assert.assertFalse(StringUtil.areEmpty(new String[]{null, "foo"}));
        Assert.assertFalse(StringUtil.areEmpty(new String[]{"", "foo"}));
        Assert.assertFalse(StringUtil.areEmpty(new String[]{"foo", null}));
        Assert.assertFalse(StringUtil.areEmpty(new String[]{"foo", ""}));
        Assert.assertFalse(StringUtil.areEmpty(new String[]{"foo", "bar"}));
    }

    /**
     * Test {@link StringUtil#generateKey(String, String)}.
     */
    @Test
    public void twoPartKeys()
    {
        final String[][] data = new String[][]
        {
            { null, null, " :: " },
            { null, "",   " :: " },
            { "",   null, " :: " },
            { "",   "",   " :: " },
            { "a",  null, "a :: " },
            { "a",  "",   "a :: " },
            { null, "b",  " :: b" },
            { null, "b",  " :: b" },
            { "a",  "b",   "a :: b" },
        };

        for (int i=0; i<data.length; i++)
        {
            final String s1 = data[i][0];
            final String s2 = data[i][1];
            final String expectedKey = data[i][2];
            final String key = StringUtil.generateKey(s1, s2);
            Assert.assertEquals("key[" + i + "]", expectedKey, key);
        }
    }


    /**
     * Test {@link StringUtil#generateKey(String, String, String)}.
     */
    @Test
    public void threePartKeys()
    {
        final String[][] data = new String[][]
        {
            { null, null, null, " ::  :: " },
            { "",   null, null, " ::  :: " },
            { null, "",   null, " ::  :: " },
            { null, null, "",   " ::  :: " },
            { "",   "",   null, " ::  :: " },
            { null, "",   "",   " ::  :: " },
            { "",   null, "",   " ::  :: " },
            { "",   "",   "",   " ::  :: " },
            { "a",  "",   "",   "a ::  :: " },
            { "",   "b",  "",   " :: b :: " },
            { "",   "",   "c",  " ::  :: c" },
            { "a",  "",   "c",  "a ::  :: c" },
            { "",   "b",  "c",  " :: b :: c" },
            { "a",  "b",  "",   "a :: b :: " },
            { "a",  "b",  "c",  "a :: b :: c" },
        };

        for (int i=0; i<data.length; i++)
        {
            final String s1 = data[i][0];
            final String s2 = data[i][1];
            final String s3 = data[i][2];
            final String expectedKey = data[i][3];
            final String key = StringUtil.generateKey(s1, s2, s3);
            Assert.assertEquals("key[" + i + "]", expectedKey, key);
        }
    }

    /**
     * Test {@link StringUtil#toString(Object[])}.
     */
    @Test
    public void toStringTest()
    {
        Assert.assertEquals("", StringUtil.toString(null));
        Assert.assertEquals("", StringUtil.toString(new Object[0]));
        Assert.assertEquals("", StringUtil.toString(new Object[]{}));
        Assert.assertEquals("", StringUtil.toString(new Object[1]));

        @SuppressWarnings("boxing")
        final Object[][] data = new Object[][]
        {
            { new String[]{ "", "" }, ", "},
            { new String[]{ "a", "" }, "a, "},
            { new String[]{ "", "b" }, ", b"},
            { new String[]{ "a", "b" }, "a, b"},
            { new String[]{ "a", "b", "c" }, "a, b, c"},
            { new Object[]{ "", 2 }, ", 2"},
            { new Object[]{ "1", 2 }, "1, 2"},
            { new Object[]{ 1, "" }, "1, "},
            { new Object[]{ 1, 2 }, "1, 2"},
            { new Object[]{ 1, 2, 3 }, "1, 2, 3"},
        };

        for (int i=0; i<data.length; i++)
        {
            final Object[] o  = (Object[])data[i][0];
            final String expected = (String)data[i][1];
            final String actual = StringUtil.toString(o);
            Assert.assertEquals("key[" + i + "]", expected, actual);
        }
    }

    /**
     * Test {@link StringUtil#toString(Object[], char)}.
     */
    @Test
    @SuppressWarnings("boxing")
    public void toStringWithQuotesTest()
    {
        Assert.assertEquals("", StringUtil.toString(null, '\''));
        Assert.assertEquals("", StringUtil.toString(new Object[0], '\''));
        Assert.assertEquals("", StringUtil.toString(new Object[]{}, '\''));
        Assert.assertEquals("''", StringUtil.toString(new Object[1], '\''));

        final Object[][] data = new Object[][]
        {
            { '\'', new String[]{ "", "" }, "'', ''"},
            { '\'', new String[]{ "a", "" }, "'a', ''"},
            { '\'', new String[]{ "", "b" }, "'', 'b'"},
            { '\'', new String[]{ "a", "b" }, "'a', 'b'"},
            { '%',  new String[]{ "a", "b", "c" }, "%a%, %b%, %c%"},
            { '\'', new Object[]{ "", 2 }, "'', '2'"},
            { '\'', new Object[]{ "1", 2 }, "'1', '2'"},
            { '\'', new Object[]{ 1, "" }, "'1', ''"},
            { '\'', new Object[]{ 1, 2 }, "'1', '2'"},
            { '&',  new Object[]{ 1, 2, 3 }, "&1&, &2&, &3&"},
        };

        for (int i=0; i<data.length; i++)
        {
            final Character quote  = (Character)data[i][0];
            final Object[] o  = (Object[])data[i][1];
            final String expected = (String)data[i][2];
            final String actual = StringUtil.toString(o, quote);
            Assert.assertEquals("key[" + i + "]", expected, actual);
        }
    }
}
