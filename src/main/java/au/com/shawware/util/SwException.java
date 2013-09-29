/*
 * Copyright (C) 2007 shawware.com.au
 *
 * License: GNU General Public License V3 (or later)
 * http://www.gnu.org/copyleft/gpl.html
 */

package au.com.shawware.util;

/**
 * Defines the super-class class for all Shawware exceptions.
 * We enforce the guideline that every exception has a message.
 *
 * @author <a href="mailto:david.shaw@shawware.com.au">David Shaw</a>
 */
public class SwException extends Exception
{
    /**
     * The (generated) serialization version ID. 
     */
    private static final long serialVersionUID = 3381404193827685120L;

	/**
     * Constructs a new exception with the given message.
     * 
     * @param message the explanation of the exception - must not be empty
     */
    public SwException(final String message)
    {
        super(message);
        // We'd like to run the parameter tests first, but calls to super() must precede them.
    	assert SwAssert.isNotEmpty(message) : "non-empty message";
    }

    /**
     * Constructs a new exception with the given message and cause.
     * 
     * @param message the explanation of the exception - must not be empty
     * @param cause the cause of the exception - must not be null
     */
    public SwException(final String message, final Throwable cause)
    {
        super(message, cause);
        // We'd like to run the parameter tests first, but calls to super() must precede them.
    	assert SwAssert.isNotEmpty(message) : "non-empty message";
    	assert SwAssert.isNotNull(cause) : "null cause";
    }
}