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

/**
 * Roman Numeral Parser - converts Roman numerals to decimal.
 * 
 * Parsing enforces the rules of the code assignment:
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
public class RomanNumeralParser
{
    /**
     * The Roman numeral alphabet.
     */
    private static char[] ALPHABET = { 'I', 'V', 'X', 'L', 'C', 'D', 'M' };

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
     * The alphabet as a set.
     */
    private Set<Character> mAlphabet;

    /**
     * The map of the tokens to their value.
     */
    private Map<String, Integer> mTokenValues;

    /**
     * Constructs a new parser.
     */
    public RomanNumeralParser()
    {
        mAlphabet = new HashSet<Character>();
        for (int i = 0; i < ALPHABET.length; i++)
        {
            mAlphabet.add(ALPHABET[i]);
        }
        mTokenValues = new HashMap<String, Integer>();
        for (int i = 0; i < TOKENS.length; i++)
        {
            mTokenValues.put(TOKENS[i], VALUES[i]);
        }
    }

    /**
     * Determines whether the given character is a valid Roman numeral.
     * 
     * @param c the character to test
     * 
     * @return Whether the character is valid.
     */
    public boolean validNumeral(char c)
    {
        return mAlphabet.contains(c);
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
        if ((number == null) || (number.length() == 0))
        {
            throw new IllegalArgumentException("empty number");
        }
        return parseTokens(tokenise(number));
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

        // Initialise the counts of each token.
        Map<String, Integer> counts = new HashMap<String, Integer>();
        for (int i = 0; i < TOKENS.length; i++)
        {
            counts.put(TOKENS[i], 0);
        }

        // Handle the first token separately.
        Integer previousValue = mTokenValues.get(tokens[0]);
        counts.put(tokens[0], 1);
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

            counts.put(tokens[i], counts.get(tokens[i]) + 1);
            arabicNumber += currentValue.intValue();
        }

        // Verify the counts - check the maximum is not exceeded.
        for (int i = 0; i < TOKENS.length; i++)
        {
            if (counts.get(TOKENS[i]) > MAX_COUNTS[i])
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
    private void checkForIncompatible(Map<String, Integer> counts, String token, String[] incompatible)
    {
        if (counts.get(token) > 0)
        {
            for (int i = 0; i < incompatible.length; i++)
            {
                if (counts.get(incompatible[i]) > 0)
                {
                    throw new IllegalArgumentException(token + " is incompatible with " + incompatible[i]);
                }
            }
        }
    }

    /**
     * Breaks the given Roman number into a set of lexical tokens.
     * 
     * @param number the Roman number to analyse.
     * 
     * @return The list of tokens.
     * 
     * @throws IllegalArgumentException invalid character encountered
     */
    private String[] tokenise(String number)
    {
        List<String> tokens = new ArrayList<String>();
        int length = number.length();
        for (int i = 0; i < length; i++)
        {
            char c = number.charAt(i);
            if (!validNumeral(c))
            {
                throw new IllegalArgumentException("invalid character '" + c + "' at index " + i);
            }
            String token = null;
            // If there a next character and we could be at the start of a 2-char token.
            if ((i < (length - 1)) && ((c == 'I') || (c == 'X') || (c == 'C')))
            {
                char next = number.charAt(i + 1);
                if (c == 'I')
                {
                    token = extractTwoCharacterToken(c, next, 'V', 'X');
                }
                else if (c == 'X')
                {
                    token = extractTwoCharacterToken(c, next, 'L', 'C');
                }
                else if (c == 'C')
                {
                    token = extractTwoCharacterToken(c, next, 'D', 'M');
                }
                // If we found a 2-char token, skip past the second char
                if (token != null)
                {
                    i++;
                }
            }
            // If token is unset, it was not a 2-char token, so we just use the current char.
            if (token == null)
            {
                token = String.valueOf(c);
            }
            tokens.add(token);
        }
        String[] result = new String[tokens.size()];
        tokens.toArray(result);
        return result;
    }

    /**
     * Determines whether there is a two-character token in the input.
     * 
     * @param current the current character
     * @param next the next character
     * @param secondA the first possibility for the second character
     * @param secondB the second possibility for the second character
     * 
     * @return The two-character token if it's present, otherwise null.
     */
    private String extractTwoCharacterToken(char current, char next, char secondA, char secondB)
    {
        StringBuffer buf = new StringBuffer();
        boolean is2Digit = false;
        buf.append(current);
        if (next == secondA)
        {
            is2Digit = true;
            buf.append(secondA);
        }
        else if (next == secondB)
        {
            is2Digit = true;
            buf.append(secondB);
        }
        return is2Digit ? buf.toString() : null;
    }
}