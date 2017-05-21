/*
 * Copyright (C) 2017 shawware.com.au
 *
 * License: GNU General Public License V3 (or later)
 * http://www.gnu.org/copyleft/gpl.html
 */

package au.com.shawware.util.roman;

import java.util.Map;

import au.com.shawware.util.IValidator;

/**
 * Verifies that the tokens are monotonically decreasing in value.
 * That is, each token is greater than or equal to the previous one.
 *
 * @author <a href="mailto:david.shaw@shawware.com.au">David Shaw</a>
 */
public class OrderedByValue implements IValidator<String[]>
{
    /** The map of tokens to their properties. */
    private final Map<String, RomanNumeralToken> mTokens;

    /**
     * Constructs a new validator.
     * 
     * @param tokens the map of tokens to their properties
     */
    public OrderedByValue(Map<String, RomanNumeralToken> tokens)
    {
        mTokens = tokens;
    }

    /* (non-Javadoc)
     * @see au.com.shawware.util.roman.ITokensValidator#validate(java.lang.String[])
     */
    @SuppressWarnings("nls")
    @Override
    public void validate(String[] tokens)
        throws IllegalArgumentException
    {
        if (tokens.length > 1)
        {
            int previousValue = mTokens.get(tokens[0]).getArabicValue();
            for (int i = 1; i < tokens.length; i++)
            {
                int currentValue = mTokens.get(tokens[i]).getArabicValue();
                if (currentValue > previousValue)
                {
                    throw new IllegalArgumentException(tokens[i-1] + " comes before " + tokens[i]);
                }
                previousValue = currentValue;
            }
        }
    }
}

