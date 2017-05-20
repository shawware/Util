/*
 * Copyright (C) 2017 shawware.com.au
 *
 * License: GNU General Public License V3 (or later)
 * http://www.gnu.org/copyleft/gpl.html
 */

package au.com.shawware.util.roman;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import au.com.shawware.util.IValidator;
import au.com.shawware.util.MatchesAlphabet;
import au.com.shawware.util.NotEmpty;
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
@SuppressWarnings({ "boxing" })
public class RomanNumeralParser
{
    /**
     * Roman numerals are based on a set of tokens, not just its alphabet.
     */
    private static String[] TOKENS = { "I", "IV", "V", "IX", "X", "XL", "L", "XC", "C", "CD", "D", "CM", "M" };

    /**
     * The maximum number of times a token can appear.
     */
    private static int[] MAX_COUNTS = { 3, 1, 1, 1, 3, 1, 1, 1, 3, 1, 1, 1, 3 };

    /**
     * The Arabic value of each token.
     */
    private static int[] VALUES = { 1, 4, 5, 9, 10, 40, 50, 90, 100, 400, 500, 900, 1000 };

    /**
     * The map of the tokens to their value.
     */
    private final Map<String, Integer> mTokenValues;

    /**
     * The set of rules for validating a Roman number as a whole. 
     */
    private final List<IValidator<String>> mNumberRules;

    /**
     * The tokeniser.
     */
    private final RomanNumeralTokeniser mTokeniser;

    /**
     * Constructs a new parser.
     */
    public RomanNumeralParser()
    {
        mTokenValues = new HashMap<String, Integer>();
        for (int i = 0; i < TOKENS.length; i++)
        {
            mTokenValues.put(TOKENS[i], VALUES[i]);
        }
        mNumberRules = new ArrayList<IValidator<String>>();
        mNumberRules.add(new NotEmpty("roman number")); //$NON-NLS-1$
        mNumberRules.add(new MatchesAlphabet(new RomanNumeralAlphabet()));
        mTokeniser = new RomanNumeralTokeniser();
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
        for (IValidator<String> rule : mNumberRules) {
            rule.validate(number);
        }
        String[] tokens = mTokeniser.tokenise(number);
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
            return mTokenValues.get(tokens[0]).intValue();
        }

        ValueCounter<String> counts = new ValueCounter<String>();
        for (int i = 0; i < TOKENS.length; i++)
        {
            counts.initialiseCount(TOKENS[i]);
        }

        // Handle the first token separately.
        Integer previousValue = mTokenValues.get(tokens[0]);
        counts.countValue(tokens[0]);
        int arabicNumber = previousValue.intValue();

        // Process the subsequent tokens.
        for (int i = 1; i < tokens.length; i++)
        {
            Integer currentValue = mTokenValues.get(tokens[i]);
            if (currentValue > previousValue)
            {
                throw new IllegalArgumentException(tokens[i-1] + " comes before " + tokens[i]);
            }
            previousValue = currentValue;

            counts.countValue(tokens[i]);
            arabicNumber += currentValue.intValue();
        }

        // Verify the counts - check the maximum is not exceeded.
        for (int i = 0; i < TOKENS.length; i++)
        {
            if (counts.count(TOKENS[i]) > MAX_COUNTS[i])
            {
                throw new IllegalArgumentException("too many " + TOKENS[i]);
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
