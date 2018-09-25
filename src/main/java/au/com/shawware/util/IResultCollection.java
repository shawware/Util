/*
 * Copyright (C) 2018 shawware.com.au
 *
 * License: GNU General Public License V3 (or later)
 * http://www.gnu.org/copyleft/gpl.html
 */

package au.com.shawware.util;

import java.util.List;

/**
 * Specify the common API for our result collections.
 *
 * @param <ResultType> the type of result being managed by the collection
 */
public interface IResultCollection<ResultType>
{
    /**
     * Whether this collection contains a result.
     *
     * @return Whether this collection contains a result.
     */
    boolean hasResult();

    /**
     * Whether this result collection is empty. That is, whether it does not contain a result.
     *
     * @return Whether this result collection is empty.
     */
    boolean isEmpty();

    /**
     * Whether the overall result is success.
     *
     * @return The overall result.
     *
     * @throws IllegalStateException when called on an empty result collection
     */
    boolean isSuccessful()
        throws IllegalStateException;

    /**
     * Whether at least one of the member results is success.
     *
     * @return Whether at least one of the member results is success.
     *
     * @throws IllegalStateException when called on an empty result collection
     */
    boolean isPartiallySuccessful()
        throws IllegalStateException;

    /**
     * Whether the overall result is failure.
     *
     * @return The negated overall result.
     *
     * @throws IllegalStateException when called on an empty result collection
     */
    boolean isUnsuccessful()
        throws IllegalStateException;

    /**
     * The current results. This is never null, but can be empty.
     *
     * @return The current results.
     */
    List<ResultType> getResults();

    /**
     * Resets this result collection. That is, throws away all existing results.
     */
    void reset();
}
