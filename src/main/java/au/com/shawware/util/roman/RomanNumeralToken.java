/*
 * Copyright (C) 2017 shawware.com.au
 *
 * License: GNU General Public License V3 (or later)
 * http://www.gnu.org/copyleft/gpl.html
 */

package au.com.shawware.util.roman;

/**
 * Holds the key properties of a Roman numeral token.
 *
 * @author <a href="mailto:david.shaw@shawware.com.au">David Shaw</a>
 */
public class RomanNumeralToken
{
    /**
     * The Roman numeral token, eg. I, IV
     */
    private final String mToken;

    /**
     * The maximum number of times this token can appear in a Roman numeral.
     */
    private final int mMaxCount;

    /**
     * The Arabic value of this token;
     */
    private final int mArabicValue;

    /**
     * Constructs a new token.
     * 
     * @param token the Roman value of this token
     * @param maxCount the maximum number of times this token can appear
     * @param arabicValue the Arabic value of this token
     */
    public RomanNumeralToken(String token, int maxCount, int arabicValue)
    {
        mToken       = token;
        mMaxCount    = maxCount;
        mArabicValue = arabicValue;
    }

    /**
     * @return The Roman value of this token.
     */
    public String getToken()
    {
        return mToken;
    }

    /**
     * @return The maximum number of times this token can appear in a Roman numeral.
     */
    public int getMaxCount()
    {
        return mMaxCount;
    }

    /**
     * @return The Arabic value of this token.
     */
    public int getArabicValue()
    {
        return mArabicValue;
    }

    @SuppressWarnings("nls")
    @Override
    public String toString()
    {
        return "[" + mToken + ", " + mMaxCount + ", " + mArabicValue + "]";
    }
}
