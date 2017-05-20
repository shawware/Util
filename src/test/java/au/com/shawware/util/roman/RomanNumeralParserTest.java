/*
 * Copyright (C) 2017 shawware.com.au
 *
 * License: GNU General Public License V3 (or later)
 * http://www.gnu.org/copyleft/gpl.html
 */

package au.com.shawware.util.roman;

import org.junit.Test;
import org.junit.Assert;

/**
 * Unit Tests for {@link RomanNumeralParser}
 *
 * @author <a href="mailto:david.shaw@shawware.com.au">David Shaw</a>
 */
@SuppressWarnings({ "boxing", "nls", "static-method" })
public class RomanNumeralParserTest
{
    /**
     * Test {@link RomanNumeralParser#parse(String)} with valid Roman numerals.
     */
    @Test
    public void testValidNumbers()
    {
        /*
         * Start with fine-grained testing to verify the algorithm.
         * Then become more coarse-grained to make sure we didn't miss something.
         */
        Object[][] fixture = {
                { "I",                  1 },
                { "II",                 2 },
                { "III",                3 },
                { "IV",                 4 },
                { "V",                  5 },
                { "VI",                 6 },
                { "VII",                7 },
                { "VIII",               8 },
                { "IX",                 9 },
                { "X",                 10 },
                { "XII",               12 },
                { "XIV",               14 },
                { "XV",                15 },
                { "XVIII",             18 },
                { "XIX",               19 },
                { "XX",                20 },
                { "XXI",               21 },
                { "XXIV",              24 },
                { "XXV",               25 },
                { "XXVI",              26 },
                { "XXIX",              29 },
                { "XXX",               30 },
                { "XXXII",             32 },
                { "XXXVII",            37 },
                { "XL",                40 },
                { "XLI",               41 },
                { "XLV",               45 },
                { "XLVIII",            48 },
                { "XLIX",              49 },
                { "L",                 50 },
                { "LI",                51 },
                { "LV",                55 },
                { "LIX",               59 },
                { "LX",                60 },
                { "LXVIII",            68 },
                { "LXXIII",            73 },
                { "LXXXIV",            84 },
                { "XC",                90 },
                { "XCIII",             93 },
                { "XCIX",              99 },
                { "C",                100 },
                { "CXXXVII",          137 },
                { "CXLII",            142 },
                { "CL",               150 },
                { "CLXV",             165 },
                { "CLXXXVIII",        188 },
                { "CXCIX",            199},
                { "CC",               200 },
                { "CCCIII",           303 },
                { "CDLIX",            459 },
                { "DI",               501 },
                { "DCXXXIV",          634 },
                { "DCCCXLIX",         849 },
                { "CMXXVII",          927 },
                { "CMXCIX",           999 },
                { "M",               1000 },
                { "MXXIV",           1024 },
                { "MCCCXLV",         1345 },
                { "MDCLXXII",        1672 },
                { "MCMLXX",          1970 },
                { "MMXVII",          2017 },
                { "MMXLVIII",        2048 },
                { "MMCDLXXI",        2471 },
                { "MMDCLXVI",        2666 },
                { "MMCMXXIV",        2924 },
                { "MMM",             3000 },
                { "MMMCCXXXIX",      3239 },
                { "MMMCDXCIII",      3493 },
                { "MMMDCCCLXXXVIII", 3888 },
                { "MMMCMLIV",        3954 },
                { "MMMCMXCIX",       3999 },
        };
        RomanNumeralParser parser = new RomanNumeralParser();
        for (int i=0; i<fixture.length; i++)
        {
            String romanNumeral = (String)fixture[i][0];
            int arabicNumber = ((Integer)fixture[i][1]).intValue();
            Assert.assertEquals(arabicNumber, parser.parse(romanNumeral));
        }
    }

    /**
     * Test {@link RomanNumeralParser#parse(String)} with invalid Roman numerals.
     */
    @Test
    public void testInvalidNumbers()
    {
        String[][] fixture = {
                { null,         "empty roman number" },
                { "",           "empty roman number" },
                { " ",          "invalid character ' '" },
                { "A",          "invalid character 'A'" },
                { "VIIB",       "invalid character 'B'" },
                { "IIII",       "too many I" },
                { "CXIVIV",     "too many IV" },
                { "IL",         "I comes before L" },
                { "MDCVX",      "V comes before X" },
                { "CXIVI",      "IV is incompatible with I"  },
                { "LXVIV",      "IV is incompatible with V"  },
                { "IXI",        "IX is incompatible with I"  },
                { "LIXV",       "IX is incompatible with V"  },
                { "LIXIV",      "IX is incompatible with IV" },
                { "CXLXXII",    "XL is incompatible with X"  },
                { "DCLXLIII",   "XL is incompatible with L"  },
                { "CXCXI",      "XC is incompatible with X"  },
                { "DCXCLIII",   "XC is incompatible with L"  },
                { "DCXCXLIV",   "XC is incompatible with XL" },
                { "MCDCLXXII",  "CD is incompatible with C"  },
                { "MDCDLXXII",  "CD is incompatible with D"  },
                { "CMCLX",      "CM is incompatible with C"  },
                { "CMCDI",      "CM is incompatible with CD" },
                { "CMDIV",      "CM is incompatible with D"  },
                { "DCCXLXX",    "XL is incompatible with X"  },
                { "MMMMI",      "too many M" },
        };
        RomanNumeralParser parser = new RomanNumeralParser();
        for (int i=0; i<fixture.length; i++)
        {
            try
            {
               parser.parse(fixture[i][0]);
               Assert.fail();
            }
            catch (IllegalArgumentException e)
            {
                Assert.assertTrue(e.getMessage().contains(fixture[i][1]));
            }
        }
    }
}
