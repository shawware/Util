/*
 * Copyright (C) 2017 shawware.com.au
 *
 * License: GNU General Public License V3 (or later)
 * http://www.gnu.org/copyleft/gpl.html
 */

package au.com.shawware.util.roman;

import java.util.HashMap;
import java.util.Map;

import au.com.shawware.util.ValueCounter;

/**
 * Roman Numeral Parser - converts Roman numerals to decimal.
 * 
 *   M, C, X and I can occur individually a maximum of three times
 *   D, L, V can occur individually a maximum of once
 *   If IV is present, I and V cannot be present
 *   If IX is present, I, IV and V cannot be present
 *   If XL is present, X and L cannot be present
 *   If XC is present, X, XL and L cannot be present
 *   If CD is present, C and D cannot be present
 *   If CM is present, C, CD and D cannot be present
 *
 * These rules mean a range of [1..3999]
 *
 * @author <a href="mailto:david.shaw@shawware.com.au">David Shaw</a>
 */
//@SuppressWarnings({ "boxing" })
public class RomanNumeralParser
{
    /** Specify the valid tokens and their properties. */
    @SuppressWarnings("nls")
    private static RomanNumeralToken[] TOKENS = {
        new RomanNumeralToken("I",  3,    1),
        new RomanNumeralToken("IV", 1,    4),
        new RomanNumeralToken("V",  1,    5),
        new RomanNumeralToken("IX", 1,    9),
        new RomanNumeralToken("X",  3,   10),
        new RomanNumeralToken("XL", 1,   40),
        new RomanNumeralToken("L",  1,   50),
        new RomanNumeralToken("XC", 1,   90),
        new RomanNumeralToken("C",  3,  100),
        new RomanNumeralToken("CD", 1,  400),
        new RomanNumeralToken("D",  1,  500),
        new RomanNumeralToken("CM", 1,  900),
        new RomanNumeralToken("M",  3, 1000),
    };

    /**
     * The map of the token to their properties.
     */
    private final Map<String, RomanNumeralToken> mTokens;

    /**
     * The tokeniser.
     */
    private final RomanNumeralLexicalAnalyser mAnalyser;

    /**
     * Constructs a new parser.
     */
    public RomanNumeralParser()
    {
        mTokens = new HashMap<String, RomanNumeralToken>();
        for (int i = 0; i < TOKENS.length; i++)
        {
            mTokens.put(TOKENS[i].getToken(), TOKENS[i]);
        }
        mAnalyser = new RomanNumeralLexicalAnalyser();
    }

    /**
     * Parses the given Roman number and converts it to decimal.
     * 
     * @param number the Roman number to convert
     * 
     * @return The corresponding decimal value
     * 
     * @throws IllegalArgumentException the given number is invalid
     */
    public int parse(String number)
        throws IllegalArgumentException
    {
        String[] tokens = mAnalyser.analyse(number);
        return parseTokens(tokens);
    }

    /**
     * Verify the syntax of the lexical tokens. If the syntax is valid,
     * return their Arabic value. Otherwise throw an exception.
     * 
     * @param tokens the lexical tokens
     * 
     * @return The Arabic value of the tokens.
     * 
     * @throws IllegalArgumentException invalid syntax
     */
    private int parseTokens(String[] tokens)
    {
        if (tokens.length == 1)
        {
            return mTokens.get(tokens[0]).getArabicValue();
        }

        ValueCounter<String> counts = new ValueCounter<String>();
        for (int i = 0; i < TOKENS.length; i++)
        {
            counts.initialiseCount(TOKENS[i].getToken());
        }

        // Handle the first token separately.
        int previousValue = mTokens.get(tokens[0]).getArabicValue();
        counts.countValue(tokens[0]);
        int arabicNumber = previousValue;

        // Process the subsequent tokens.
        for (int i = 1; i < tokens.length; i++)
        {
            int currentValue = mTokens.get(tokens[i]).getArabicValue();
            if (currentValue > previousValue)
            {
                throw new IllegalArgumentException(tokens[i-1] + " comes before " + tokens[i]);
            }
            previousValue = currentValue;

            counts.countValue(tokens[i]);
            arabicNumber += currentValue;
        }

        // Verify the counts - check the maximum is not exceeded.
        for (int i = 0; i < TOKENS.length; i++)
        {
            if (counts.count(TOKENS[i].getToken()) > TOKENS[i].getMaxCount())
            {
                throw new IllegalArgumentException("too many " + TOKENS[i].getToken());
            }
        }

        // Verify the incompatible tokens as per the rules
        checkForIncompatible(counts, "CM", new String[]{"C", "CD", "D"});
        checkForIncompatible(counts, "CD", new String[]{"C", "D"});
        checkForIncompatible(counts, "XC", new String[]{"X", "XL", "L"});
        checkForIncompatible(counts, "XL", new String[]{"X", "L"});
        checkForIncompatible(counts, "IX", new String[]{"I", "IV", "V"});
        checkForIncompatible(counts, "IV", new String[]{"I", "V"});

        // If we get here it is a valid number so we return its Arabic value.
        return arabicNumber;
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
