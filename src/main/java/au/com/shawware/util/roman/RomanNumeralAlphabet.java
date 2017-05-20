/*
 * Copyright (C) 2017 shawware.com.au
 *
 * License: GNU General Public License V3 (or later)
 * http://www.gnu.org/copyleft/gpl.html
 */

package au.com.shawware.util.roman;

import java.util.HashSet;
import java.util.Set;

import au.com.shawware.util.IAlphabet;

/**
 * Specifies the Roman Numeral Alphabet.
 *
 * @author <a href="mailto:david.shaw@shawware.com.au">David Shaw</a>
 */
public class RomanNumeralAlphabet implements IAlphabet<Character>
{
    /**
     * The Roman numeral alphabet.
     */
    private static char[] ALPHABET = { 'I', 'V', 'X', 'L', 'C', 'D', 'M' };

    /**
     * The alphabet as a set.
     */
    private final Set<Character> mAlphabet;

    /**
     * Constructs a new alphabet.
     */
    public RomanNumeralAlphabet()
    {
        mAlphabet = new HashSet<Character>();
        for (int i = 0; i < ALPHABET.length; i++)
        {
            mAlphabet.add(ALPHABET[i]);
        }
    }

    @Override
    public boolean contains(Character c)
    {
        return mAlphabet.contains(c);
    }
}
