/*
 * Copyright (C) 2007 shawware.com.au
 *
 * License: GNU General Public License V3 (or later)
 * http://www.gnu.org/copyleft/gpl.html
 */

package au.com.shawware.util;

/**
 * A set of utility methods for manipulating properties.
 *
 * @author <a href="mailto:david.shaw@shawware.com.au">David Shaw</a>
 */
public final class SwPropertyUtils
{
    /** The separator between elements of a property name. */
    private static final char ELT_SEPARATOR = '.';

    /**
     * Creates a new property name based on the given name and suffix.
     * The suffix is appended to the name (and separated by the default separator).
     * 
     * @param name the base property name - must not be empty
     * @param suffix the suffix to be appended - must not be empty
     * 
     * @return The new property name.
     */
    public static String appendSuffix(final String name, final String suffix)
    {
        assert SwAssert.isNotEmpty(name) : "empty name"; //$NON-NLS-1$
        assert SwAssert.isNotEmpty(suffix) : "empty suffix"; //$NON-NLS-1$

        final StringBuffer sb = new StringBuffer();
        sb.append(name);
        if ((name.charAt(name.length() - 1) != ELT_SEPARATOR) &&
        	(suffix.charAt(0) != ELT_SEPARATOR))
        {
        	sb.append(ELT_SEPARATOR);
        }
        sb.append(suffix);
        return sb.toString();
    }
    
    /**
     * Default constructor. Private to prevent use.
     */
    private SwPropertyUtils()
    {
        // Do nothing.
    }
}