/*
 * Copyright (C) 2017 shawware.com.au
 *
 * License: GNU General Public License V3 (or later)
 * http://www.gnu.org/copyleft/gpl.html
 */

package au.com.shawware.util.roman;

import au.com.shawware.util.AbstractAlphabet;

/**
 * Specifies the Roman Numeral Alphabet.
 *
 * @author <a href="mailto:david.shaw@shawware.com.au">David Shaw</a>
 */
public class RomanNumeralAlphabet extends AbstractAlphabet
{
    /**
     * The Roman numeral alphabet.
     */
    private static String ALPHABET = "IVXLCDM"; //$NON-NLS-1$

    /**
     * Constructs a new alphabet.
     */
    public RomanNumeralAlphabet()
    {
        super(ALPHABET);
    }
}
