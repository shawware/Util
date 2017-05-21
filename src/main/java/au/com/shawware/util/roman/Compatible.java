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
public class Compatible implements IValidator<String[]>
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

        for (int i = 0; i < tokens.length; i++)
        {
            String[] incompatibleTokens = mTokens.get(tokens[i]).getIncompatibleTokens();
            for (int j = 0; j < incompatibleTokens.length; j++)
            {
                if (counts.count(incompatibleTokens[j]) > 0)
                {
                    throw new IllegalArgumentException(tokens[i] + " is incompatible with " + incompatibleTokens[j]);
                }
            }
        }
    }
}
