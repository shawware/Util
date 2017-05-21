/*
 * Copyright (C) 2017 shawware.com.au
 *
 * License: GNU General Public License V3 (or later)
 * http://www.gnu.org/copyleft/gpl.html
 */

package au.com.shawware.util.roman;

import java.util.Map;

import au.com.shawware.util.IValidator;
import au.com.shawware.util.ValueCounter;

/**
 * Verifies that no token appears too many times.
 *
 * @author <a href="mailto:david.shaw@shawware.com.au">David Shaw</a>
 */
public class MaximumsHonoured implements IValidator<String[]>
{
    /** The map of tokens to their properties. */
    private final Map<String, RomanNumeralToken> mTokens;

    /**
     * Constructs a new validator.
     * 
     * @param tokens the map of tokens to their properties
     */
    public MaximumsHonoured(Map<String, RomanNumeralToken> tokens)
    {
        mTokens = tokens;
    }

    /* (non-Javadoc)
     * @see au.com.shawware.util.roman.ITokensValidator#validate(java.lang.String[])
     */
    @SuppressWarnings("nls")
    @Override
    public void validate(String[] tokens) throws IllegalArgumentException
    {
        ValueCounter<String> counts = TokenCounter.countTokens(tokens);
        for (String token : mTokens.keySet())
        {
            if (counts.count(token) > mTokens.get(token).getMaxCount())
            {
                throw new IllegalArgumentException("too many " + token);
            }
        }
    }
}
