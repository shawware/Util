/*
 * Copyright (C) 2007, 2015 shawware.com.au
 *
 * License: GNU General Public License V3 (or later)
 * http://www.gnu.org/copyleft/gpl.html
 */

package au.com.shawware.util;

/**
 * A set of tests and assertions. Client programmers can use the
 * tests and wrap them in their own assertions. Or use the
 * assertions directly. Test methods begin with "is" or "are".
 * 
 * Use: java -ea to enable the assertions (in the VM).
 *
 * @author <a href="mailto:david.shaw@shawware.com.au">David Shaw</a>
 */
public final class SwAssert
{
    /**
     * Asserts that the given boolean is <code>true</code>.
     * 
     * @param b the boolean to test
     */
    public static void True(final boolean b)
    {
        True(b, "untrue"); //$NON-NLS-1$
    }

    /**
     * Asserts that the given boolean is <code>true</code>.
     * 
     * @param b the boolean to test
     * @param error the error to report if the boolean is <code>false</code>
     */
    public static void True(final boolean b, final String error)
    {
        assert b : error;
    }

    /**
     * Asserts that the given boolean is <code>false</code>.
     * 
     * @param b the boolean to test
     */
    public static void False(final boolean b)
    {
        False(b, "true"); //$NON-NLS-1$
    }

    /**
     * Asserts that the given boolean is <code>false</code>.
     * 
     * @param b the boolean to test
     * @param error the error to report if the boolean is <code>true</code>
     */
    public static void False(final boolean b, final String error)
    {
        assert !b : error;
    }

    /**
     * Determines whether the given string is not empty.
     * That is, it is neither <code>null</code> or of length 0.
     * 
     * @param s the string to test
     * 
     * @return Whether the given string is not empty.
     */
    public static boolean isNotEmpty(final String s)
    {
        return ((s != null) && (s.length() > 0));
    }

    /**
     * Asserts that the given string is not empty.
     * That is, it is neither <code>null</code> or of length 0.
     * 
     * @param s the string to test
     */
    public static void notEmpty(final String s)
    {
        assert isNotEmpty(s) : "empty string"; //$NON-NLS-1$
    }

    /**
     * Determines whether the given object is not <code>null</code>.
     * 
     * @param o the object to test
     * 
     * @return Whether the given object is not <code>null</code>.
     */
    public static boolean isNotNull(final Object o)
    {
        return (o != null);
    }

    /**
     * Asserts that the given object is not <code>null</code>.
     * 
     * @param o the object to test
     */
    public static void notNull(final Object o)
    {
        assert isNotNull(o) : "null object"; //$NON-NLS-1$
    }
}