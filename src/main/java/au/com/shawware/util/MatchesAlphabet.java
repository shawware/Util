/*
 * Copyright (C) 2017 shawware.com.au
 *
 * License: GNU General Public License V3 (or later)
 * http://www.gnu.org/copyleft/gpl.html
 */

package au.com.shawware.util;

/**
 * Implements an alphabet of characters to validate a string against.
 * 
 * @author <a href="mailto:david.shaw@shawware.com.au">David Shaw</a>
 */
public class MatchesAlphabet implements IValidator<String>
{
    /** The alphabet we must match against. */
    private final IAlphabet<Character> mAlphabet;

    /**
     * Constructs a new alphabet matcher.
     * 
     * @param alphabet the alphabet to match against
     */
    public MatchesAlphabet(IAlphabet<Character> alphabet)
    {
        mAlphabet = alphabet;
    }

    @Override
    public void validate(String s)
        throws IllegalArgumentException
    {
        int length = s.length();
        for (int i = 0; i < length; i++)
        {
            char c = s.charAt(i);
            if (!mAlphabet.contains(c))
            {
                throw new IllegalArgumentException("invalid character '" + c + "' at index " + i); //$NON-NLS-1$ //$NON-NLS-2$
            }
        }
    }
}
