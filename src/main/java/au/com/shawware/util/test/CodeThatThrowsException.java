/*
 * Copyright (C) 2017 shawware.com.au
 *
 * License: GNU General Public License V3 (or later)
 * http://www.gnu.org/copyleft/gpl.html
 */
package au.com.shawware.util.test;

/**
 * Functional interface representing code that throws a checked exception.
 */
public interface CodeThatThrowsException
{
    /**
     * Execute the given code.
     *
     * @throws Exception error during execution
     */
    void execute()
        throws Exception;
}
