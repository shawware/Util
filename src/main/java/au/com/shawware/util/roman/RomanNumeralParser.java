/*
 * Copyright (C) 2017 shawware.com.au
 *
 * License: GNU General Public License V3 (or later)
 * http://www.gnu.org/copyleft/gpl.html
 */

package au.com.shawware.util.roman;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import au.com.shawware.util.IValidator;

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
public class RomanNumeralParser
{
    /** Specify the valid tokens and their properties. */
    @SuppressWarnings("nls")
    private static RomanNumeralToken[] TOKENS = {
        new RomanNumeralToken("I",  3,    1, new String[]{}),
        new RomanNumeralToken("IV", 1,    4, new String[]{"I"}),
        new RomanNumeralToken("V",  1,    5, new String[]{"IV"}),
        new RomanNumeralToken("IX", 1,    9, new String[]{"V", "IV", "I"}),
        new RomanNumeralToken("X",  3,   10, new String[]{}),
        new RomanNumeralToken("XL", 1,   40, new String[]{"X"}),
        new RomanNumeralToken("L",  1,   50, new String[]{"XL"}),
        new RomanNumeralToken("XC", 1,   90, new String[]{"L", "XL", "X"}),
        new RomanNumeralToken("C",  3,  100, new String[]{}),
        new RomanNumeralToken("CD", 1,  400, new String[]{"C"}),
        new RomanNumeralToken("D",  1,  500, new String[]{"CD"}),
        new RomanNumeralToken("CM", 1,  900, new String[]{"D", "CD", "C"}),
        new RomanNumeralToken("M",  3, 1000, new String[]{}),
    };

    /**
     * The map of the token to their properties.
     */
    private final Map<String, RomanNumeralToken> mTokens;

    /**
     * The lexical analyser.
     */
    private final RomanNumeralLexicalAnalyser mAnalyser;

    /**
     * The set of rules to validate the tokens against.
     */
    private final List<IValidator<String[]>> mParsingRules;

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
        mParsingRules = new ArrayList<IValidator<String[]>>();
        mParsingRules.add(new NotEmpty());
        mParsingRules.add(new OrderedByValue(mTokens));
        mParsingRules.add(new MaximumsHonoured(mTokens));
        mParsingRules.add(new Compatible(mTokens));
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
        parseTokens(tokens);
        return calculateValue(tokens);
    }

    /**
     * Verify the syntax of the lexical tokens. If the syntax is valid,
     * return normally, Otherwise throw an exception.
     * 
     * @param tokens the lexical tokens
     * 
     * @throws IllegalArgumentException invalid syntax
     */
    private void parseTokens(String[] tokens)
    {
        for (IValidator<String[]> rule : mParsingRules)
        {
            rule.validate(tokens);
        }
    }

    /**
     * Calculates the Arabic value of the given Roman numeral tokens.
     * 
     * @param tokens the Roman numeral tokens
     * 
     * @return The corresponding Arabic value.
     */
    private int calculateValue(String[] tokens)
    {
        int arabicNumber = 0;
        for (int i = 0; i < tokens.length; i++)
        {
            arabicNumber += mTokens.get(tokens[i]).getArabicValue();
        }
        return arabicNumber;
    }
}
