/*
 * Copyright (C) 2018 shawware.com.au
 *
 * License: GNU General Public License V3 (or later)
 * http://www.gnu.org/copyleft/gpl.html
 */

package au.com.shawware.util;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple set of {@link Result}s that can be combined to form an overall result.
 *
 * @param <ResultType> the type of result being managed
 */
public class ResultSet<ResultType extends SwResult> implements IResultCollection<ResultType>
{
    /** The collection of results. */
    private final List<ResultType> mResults;

    /**
     * Constructs a new, empty result set.
     */
    public ResultSet()
    {
        mResults = new ArrayList<>();
    }

    @Override
    public boolean hasResult()
    {
        return (mResults.size() > 0);
    }

    @Override
    public boolean isEmpty()
    {
        return (mResults.size() == 0);
    }

    @Override
    public boolean isSuccessful()
        throws IllegalStateException
    {
        if (isEmpty())
        {
            throw new IllegalStateException("empty result set"); //$NON-NLS-1$
        }
        return mResults.parallelStream().allMatch(SwResult::isSuccessful);
    }

    @Override
    public boolean isPartiallySuccessful()
        throws IllegalStateException
    {
        if (isEmpty())
        {
            throw new IllegalStateException("empty result set"); //$NON-NLS-1$
        }
        return mResults.parallelStream().anyMatch(SwResult::isSuccessful);
    }

    @Override
    public boolean isUnsuccessful()
        throws IllegalStateException
    {
        if (isEmpty())
        {
            throw new IllegalStateException("empty result set"); //$NON-NLS-1$
        }
        return mResults.parallelStream().anyMatch(SwResult::isUnsuccessful);
    }

    /**
     * Adds a new result to this result set.
     *
     * @param result the additional result (must not be null)
     *
     * @throws IllegalArgumentException null result
     */
    public void addResult(ResultType result)
        throws IllegalArgumentException
    {
        if (result == null)
        {
            throw new IllegalArgumentException("null result"); //$NON-NLS-1$
        }
        mResults.add(result);
    }

    @Override
    public List<ResultType> getResults()
    {
        return mResults;
    }

    @Override
    public void reset()
    {
        mResults.clear();
    }

    @Override
    public String toString()
    {
        return StringUtil.toString(mResults);
    }
}
