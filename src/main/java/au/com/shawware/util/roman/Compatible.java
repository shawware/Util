/*
 * Copyright (C) 2017 shawware.com.au
 *
 * License: GNU General Public License V3 (or later)
 * http://www.gnu.org/copyleft/gpl.html
 */

package au.com.shawware.util.roman;

import java.util.Map;

import au.com.shawware.util.ValueCounter;

/**
 * Verifies the set of tokens are compatible with each other:
 *
 *   If IV is present, I and V cannot be present
 *   If IX is present, I, IV and V cannot be present
 *   If XL is present, X and L cannot be present
 *   If XC is present, X, XL and L cannot be present
 *   If CD is present, C and D cannot be present
 *   If CM is present, C, CD and D cannot be present
 *
 * @author <a href="mailto:david.shaw@shawware.com.au">David Shaw</a>
 */
public class Compatible implements ITokensValidator
{
    /** The map of tokens to their properties. */
    private final Map<String, RomanNumeralToken> mTokens;

    /**
     * Constructs a new validator.
     * 
     * @param tokens the map of tokens to their properties
     */
    public Compatible(Map<String, RomanNumeralToken> tokens)
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

        // Verify the incompatible tokens as per the rules
        checkForIncompatible(counts, "CM", new String[]{"C", "CD", "D"});
        checkForIncompatible(counts, "CD", new String[]{"C", "D"});
        checkForIncompatible(counts, "XC", new String[]{"X", "XL", "L"});
        checkForIncompatible(counts, "XL", new String[]{"X", "L"});
        checkForIncompatible(counts, "IX", new String[]{"I", "IV", "V"});
        checkForIncompatible(counts, "IV", new String[]{"I", "V"});
    }

    /**
     * Checks for incompatible tokens.
     * 
     * @param counts the token counts
     * @param token the token to test
     * @param incompatible the set of tokens it is incompatible with
     * 
     * @throws IllegalArgumentException an incompatibility is found
     */
    @SuppressWarnings({ "static-method", "nls" })
    private void checkForIncompatible(ValueCounter<String> counts, String token, String[] incompatible)
    {
        if (counts.count(token) > 0)
        {
            for (int i = 0; i < incompatible.length; i++)
            {
                if (counts.count(incompatible[i]) > 0)
                {
                    throw new IllegalArgumentException(token + " is incompatible with " + incompatible[i]);
                }
            }
        }
    }
}
