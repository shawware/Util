/*
 * Copyright (C) 2017 shawware.com.au
 *
 * License: GNU General Public License V3 (or later)
 * http://www.gnu.org/copyleft/gpl.html
 */

package au.com.shawware.util.roman;

import au.com.shawware.util.ValueCounter;

/**
 * Counts the number of each token in a token set.
 *
 * @author <a href="mailto:david.shaw@shawware.com.au">David Shaw</a>
 */
public class TokenCounter
{
    /**
     * Counts the number of each token in the given non-empty set of tokens.
     * 
     * @param tokens the set of tokens to count (must be non-empty)
     * 
     * @return The counts.
     */
    public static ValueCounter<String> countTokens(String[] tokens)
    {
        ValueCounter<String> counts = new ValueCounter<String>();
        for (int i = 0; i < tokens.length; i++)
        {
            counts.countValue(tokens[i]);
        }
        return counts;
    }
}
