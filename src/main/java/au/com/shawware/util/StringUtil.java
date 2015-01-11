/*
 * Shawware Java Utility Code
 *
 * Copyright (C) 2010, 2015 shawware.com.au
 *
 * License: GNU General Public License V3 (or later)
 * http://www.gnu.org/copyleft/gpl.html
 */

package au.com.shawware.util;

/**
 * A set of utility functions based around strings.
 */
public class StringUtil
{
    /** Define the empty string. */
    private static final String EMPTY = ""; //$NON-NLS-1$
    /** Define the key element separator. */
    private static final String KEY_ELT_SEP = " :: "; //$NON-NLS-1$
    /** Define the comma separator. */
    private static final String COMMA_SEP = ", "; //$NON-NLS-1$

    /**
     * Converts the given array of objects to a single string of comma-separated values.
     * Each object is converted to a string using its {@link Object#toString()} method.
     * 
     * @param o the array of object to convert
     * 
     * @return the corresponding string
     */
    public static String toString(final Object[] o)
    {
        if ((o == null) || (o.length == 0))
        {
            return EMPTY;
        }
        final StringBuilder sb = new StringBuilder();
        final int max = o.length - 1;
        for (int i = 0; i <= max; i++)
        {
            sb.append((o[i] != null) ? o[i].toString() : EMPTY);
            if (i < max)
            {
                sb.append(COMMA_SEP);
            }
        }
        return sb.toString();
    }

    /**
     * Converts the given array of objects to a single string of comma-separated, quoted values.
     * Each object is converted to a string using its {@link Object#toString()} method.
     * Each object's string value is <em>quoted</em> before and after using the given quote character.
     * 
     * N.B. If an individual value contains the quote character itself, no special action is taken.
     * 
     * @param o the array of object to convert
     * @param quote the character to quote individual values with
     * 
     * @return the corresponding string
     * 
     * TODO: We use this for SQL quoting at present. Should we use a boolean?
     * TODO: For SQL quoting we should escape inner quotes.
     */
    public static String toString(final Object[] o, final char quote)
    {
        if ((o == null) || (o.length == 0))
        {
            return EMPTY;
        }
        final StringBuilder sb = new StringBuilder();
        final int max = o.length - 1;
        for (int i = 0; i <= max; i++)
        {
            sb.append(quote)
              .append((o[i] != null) ? o[i].toString() : EMPTY)
              .append(quote);
            if (i < max)
            {
                sb.append(COMMA_SEP);
            }
        }
        return sb.toString();
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
        return (s == null) || (s.length() == 0);
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
        return (s != null) && (s.length() > 0);
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
