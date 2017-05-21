/*
 * Copyright (C) 2017 shawware.com.au
 *
 * License: GNU General Public License V3 (or later)
 * http://www.gnu.org/copyleft/gpl.html
 */

package au.com.shawware.util.roman;

import au.com.shawware.util.IValidator;
import au.com.shawware.util.StringUtil;

/**
 * Verifies that a set of tokens is not empty.
 *
 * @author <a href="mailto:david.shaw@shawware.com.au">David Shaw</a>
 */
public class NotEmpty implements IValidator<String[]>
{
    /* (non-Javadoc)
     * @see au.com.shawware.util.roman.ITokensValidator#validate(au.com.shawware.util.roman.RomanNumeralToken[])
     */
    @SuppressWarnings("nls")
    @Override
    public void validate(String[] tokens)
            throws IllegalArgumentException
    {
        if ((tokens == null) || (tokens.length == 0))
        {
            throw new IllegalArgumentException("empty set of tokens");
        }
        for (int i = 0; i < tokens.length; i++)
        {
            if (StringUtil.isEmpty(tokens[i]))
            {
                throw new IllegalArgumentException("token " + i + " is empty");
            }
        }
    }
}
