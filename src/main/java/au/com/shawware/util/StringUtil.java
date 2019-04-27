/*
 * Shawware Java Utility Code
 *
 * Copyright (C) 2010, 2015 shawware.com.au
 *
 * License: GNU General Public License V3 (or later)
 * http://www.gnu.org/copyleft/gpl.html
 */

package au.com.shawware.util;

import java.lang.reflect.Array;
import java.util.StringJoiner;

/**
 * A set of utility functions based around strings.
 */
public class StringUtil
{
    /** Define the comma separator. */
    public static final String COMMA_SEP = ", "; //$NON-NLS-1$

    /** Define the empty string. */
    public static final String EMPTY = ""; //$NON-NLS-1$

    /** Define the key element separator. */
    private static final String KEY_ELT_SEP = " :: "; //$NON-NLS-1$
    /** Define the null string. */
    private static final String NULL = "null"; //$NON-NLS-1$
    /** Define the left structure delimiter. */
    private static final String STRUCTURE_DELIMITER_LEFT = "{"; //$NON-NLS-1$
    /** Define the right structure delimiter. */
    private static final String STRUCTURE_DELIMITER_RIGHT = "}"; //$NON-NLS-1$
    /** Define the left array delimiter. */
    private static final String ARRAY_DELIMITER_LEFT = "["; //$NON-NLS-1$
    /** Define the right array delimiter. */
    private static final String ARRAY_DELIMITER_RIGHT = "]"; //$NON-NLS-1$


    /**
     * Combine the given values into a single string. This is intended to be
     * used to provide a consistent means for classes to present their state
     * when they override {@link Object#toString()}.
     *
     * The given values are assumed to be the class's attributes. The class
     * is treated roughly like a structure and these attributes are delimited
     * by curly braces. If an attribute is an array, then its elements are
     * handled separately and delimited by square brackets.
     *
     * Null values are included as "null" and all values are separated by
     * commas.
     *
     * @param values the values to include in the result
     *
     * @return The formatted result.
     */
    public static String toString(Object... values)
    {
        if ((values == null) || (values.length == 0))
        {
            throw new IllegalArgumentException("No values supplied"); //$NON-NLS-1$
        }
        StringJoiner sj = new StringJoiner(COMMA_SEP, STRUCTURE_DELIMITER_LEFT, STRUCTURE_DELIMITER_RIGHT);
        for (Object value : values)
        {
            if (value == null)
            {
                sj.add(NULL);
            }
            else if (value.getClass().isArray())
            {
                StringJoiner asj = new StringJoiner(COMMA_SEP, ARRAY_DELIMITER_LEFT, ARRAY_DELIMITER_RIGHT);
                // Use reflection to handle scalar and object arrays.
                int length = Array.getLength(value);
                for (int i = 0; i < length; i++)
                {
                    Object element = Array.get(value, i);
                    String elementAsString = (element == null) ? NULL : element.toString();
                    asj.add(elementAsString);
                }
                sj.add(asj.toString());
            }
            else
            {
                sj.add(value.toString());
            }
        }
        return sj.toString();
    }

    /**
     * Converts the given array of objects to a single string of comma-separated values.
     * Each object is converted to a string using its {@link Object#toString()} method.
     * 
     * @param array the array of object to convert
     * 
     * @return the corresponding string
     */
    public static String arrayToString(final Object[] array)
    {
        if ((array == null) || (array.length == 0))
        {
            return EMPTY;
        }
        final StringJoiner sj = new StringJoiner(COMMA_SEP);
        for (Object o : array)
        {
            sj.add((o != null) ? o.toString() : EMPTY);
        }
        return sj.toString();
    }

    /**
     * Converts the given array of objects to a single string of comma-separated, quoted values.
     * Each object is converted to a string using its {@link Object#toString()} method.
     * Each object's string value is <em>quoted</em> before and after using the given quote character.
     * 
     * N.B. If an individual value contains the quote character itself, no special action is taken.
     * 
     * @param array the array of objects to convert
     * @param quote the character to quote individual values with
     * 
     * @return the corresponding string
     * 
     * TODO: We use this for SQL quoting at present. Should we use a boolean?
     * TODO: For SQL quoting we should escape inner quotes.
     */
    public static String toString(final Object[] array, final char quote)
    {
        if ((array == null) || (array.length == 0))
        {
            return EMPTY;
        }
        final StringJoiner sj = new StringJoiner(COMMA_SEP);
        for (Object o : array)
        {
            String s = (o != null) ? o.toString() : EMPTY;
            s = quote + s + quote;
            sj.add(s);
        }
        return sj.toString();
    }

    /**
     * Whether the given string is empty.
     * 
     * @param s the string to test
     * 
     * @return <code>true</code> if the string is empty, <code>false</code> otherwise
     */
    public static boolean isEmpty(final String s)
    {
        return (s == null) || s.isEmpty();
    }

    /**
     * Whether the given string is not empty.
     * 
     * @param s the string to test
     * 
     * @return <code>true</code> if the string is not empty, <code>false</code> otherwise
     */
    public static boolean isNotEmpty(final String s)
    {
        return (s != null) && !s.isEmpty();
    }

    /**
     * Whether the given array of strings is empty.
     * 
     * @param a the strings to test
     * 
     * @return <code>true</code> if all the strings are empty, <code>false</code> otherwise
     */
    public static boolean areEmpty(final String[] a)
    {
        if (a == null)
        {
            return true;
        }
        for (String s : a)
        {
            if (!isEmpty(s))
            {
                return false;
            }
        }
        return true;
    }

    /**
     * Creates a unique key for the given two components.
     * 
     * @param s1 the first component
     * @param s2 the second component
     *
     * @return the key
     */
    public static String generateKey(final String s1, final String s2)
    {
        final String k1 = (s1 == null) ? EMPTY : s1;
        final String k2 = (s2 == null) ? EMPTY : s2;

        return k1 + KEY_ELT_SEP + k2;
    }

    /**
     * Creates a unique key for the given three components.
     * 
     * @param s1 the first component
     * @param s2 the second component
     * @param s3 the third component
     *
     * @return the key
     */
    public static String generateKey(final String s1, final String s2, final String s3)
    {
        final String k1 = (s1 == null) ? EMPTY : s1;
        final String k2 = (s2 == null) ? EMPTY : s2;
        final String k3 = (s3 == null) ? EMPTY : s3;

        return k1 + KEY_ELT_SEP + k2 + KEY_ELT_SEP + k3;
    }
}
