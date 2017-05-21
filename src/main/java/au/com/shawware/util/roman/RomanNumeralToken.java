/*
 * Copyright (C) 2017 shawware.com.au
 *
 * License: GNU General Public License V3 (or later)
 * http://www.gnu.org/copyleft/gpl.html
 */

package au.com.shawware.util.roman;

import au.com.shawware.util.StringUtil;

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
     * The names of tokens of lesser value that this token is incompatible with
     */
    private final String[] mIncompatibleTokens;

    /**
     * Constructs a new token.
     * 
     * @param token the Roman value of this token
     * @param maxCount the maximum number of times this token can appear
     * @param arabicValue the Arabic value of this token
     * @param incompatibleTokens incompatible tokens
     */
    public RomanNumeralToken(String token, int maxCount, int arabicValue, String[] incompatibleTokens)
    {
        mToken              = token;
        mMaxCount           = maxCount;
        mArabicValue        = arabicValue;
        mIncompatibleTokens = incompatibleTokens;
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

    /**
     * @return The tokens of lesser value that are incompatible with this token.
     */
    public String[] getIncompatibleTokens()
    {
        return mIncompatibleTokens;
    }

    @SuppressWarnings("nls")
    @Override
    public String toString()
    {
        return "[" + mToken + ", " + mMaxCount + ", " + mArabicValue + ", " + StringUtil.toString(mIncompatibleTokens) + "]";
    }
}
