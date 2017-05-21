/*
 * Copyright (C) 2017 shawware.com.au
 *
 * License: GNU General Public License V3 (or later)
 * http://www.gnu.org/copyleft/gpl.html
 */

package au.com.shawware.util.roman;

/**
 * The validation API for a set of tokens.
 *
 * @author <a href="mailto:david.shaw@shawware.com.au">David Shaw</a>
 */
public interface ITokensValidator
{
    /**
     * Validates the given set of tokens and returns normally if they are okay.
     * 
     * @param tokens the tokens to validate
     * 
     * @throws IllegalArgumentException if an error is detected
     */
    public void validate(String[] tokens)
        throws IllegalArgumentException;
}
