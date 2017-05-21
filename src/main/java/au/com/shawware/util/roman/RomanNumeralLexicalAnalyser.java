/*
 * Copyright (C) 2017 shawware.com.au
 *
 * License: GNU General Public License V3 (or later)
 * http://www.gnu.org/copyleft/gpl.html
 */

package au.com.shawware.util.roman;

import java.util.ArrayList;
import java.util.List;

import au.com.shawware.util.IValidator;
import au.com.shawware.util.MatchesAlphabet;
import au.com.shawware.util.NotEmpty;

/**
 * Provides the lexical analysis of a Roman numeral.
 *
 * @author <a href="mailto:david.shaw@shawware.com.au">David Shaw</a>
 */
public class RomanNumeralLexicalAnalyser
{
    /**
     * The set of rules for validating a Roman number as a whole. 
     */
    private final List<IValidator<String>> mLexicalRules;

    /**
     * Constructs a new parser.
     */
    public RomanNumeralLexicalAnalyser()
    {
        mLexicalRules = new ArrayList<IValidator<String>>();
        mLexicalRules.add(new NotEmpty("roman number")); //$NON-NLS-1$
        mLexicalRules.add(new MatchesAlphabet(new RomanNumeralAlphabet()));
    }

    /**
     * Breaks the given Roman number into a set of lexical tokens.
     * 
     * @param number the Roman number to analyse.
     * 
     * @return The list of tokens.
     * 
     * @throws IllegalArgumentException if the basic lexical analysis finds an error
     */
    public String[] analyse(String number)
    {
        for (IValidator<String> rule : mLexicalRules) {
            rule.validate(number);
        }

        List<String> tokens = new ArrayList<String>();
        int length = number.length();
        for (int i = 0; i < length; i++)
        {
            char current = number.charAt(i);
            String token = null;
            // If there a next character and we could be at the start of a 2-char token.
            if ((i < (length - 1)) && ((current == 'I') || (current == 'X') || (current == 'C')))
            {
                char next = number.charAt(i + 1);
                if (current == 'I')
                {
                    token = extractTwoCharacterToken(current, next, 'V', 'X');
                }
                else if (current == 'X')
                {
                    token = extractTwoCharacterToken(current, next, 'L', 'C');
                }
                else if (current == 'C')
                {
                    token = extractTwoCharacterToken(current, next, 'D', 'M');
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
                token = String.valueOf(current);
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
    @SuppressWarnings("static-method")
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
