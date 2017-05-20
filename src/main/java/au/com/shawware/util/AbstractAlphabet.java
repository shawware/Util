/*
 * Copyright (C) 2017 shawware.com.au
 *
 * License: GNU General Public License V3 (or later)
 * http://www.gnu.org/copyleft/gpl.html
 */

package au.com.shawware.util;

/**
 * Implements the common elements of a string alphabet.
 *
 * @author <a href="mailto:david.shaw@shawware.com.au">David Shaw</a>
 */
@SuppressWarnings("boxing")
public abstract class AbstractAlphabet implements IAlphabet<Character>
{
    /** The alphabet in use. */
    private final String mAlphabet;

    /**
     * Constructs a new alphabet based on the characters in the given string.
     * 
     * The client programmer must specify a non-null alphabet.
     * Processing will be inefficient if the given alphabet contains duplicates.
     * 
     * @param alphabet the set of characters
     */
    public AbstractAlphabet(String alphabet)
    {
        mAlphabet = alphabet;
    }

    @Override
    public boolean contains(Character c)
    {
        return (mAlphabet.indexOf(c) >= 0);
    }
}
