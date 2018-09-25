/*
 * Copyright (C) 2018 shawware.com.au
 *
 * License: GNU General Public License V3 (or later)
 * http://www.gnu.org/copyleft/gpl.html
 */

package au.com.shawware.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple map of {@link Result}s that can be combined to form an overall result.
 * The map functionality allows each result to be identified and accessed by a key.
 *
 * @param <KeyType> the key type to use to manage results
 * @param <ResultType> the type of result being managed
 */
public class ResultMap<KeyType, ResultType extends SwResult> implements IResultCollection<ResultType>
{
    /** The collection of results. */
    private final Map<KeyType, ResultType> mResults;

    /**
     * Constructs a new, empty result map.
     */
    public ResultMap()
    {
        mResults = new HashMap<>();
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
            throw new IllegalStateException("empty result map"); //$NON-NLS-1$
        }
        return mResults.values().parallelStream().allMatch(SwResult::isSuccessful);
    }

    @Override
    public boolean isPartiallySuccessful()
        throws IllegalStateException
    {
        if (isEmpty())
        {
            throw new IllegalStateException("empty result set"); //$NON-NLS-1$
        }
        return mResults.values().parallelStream().anyMatch(SwResult::isSuccessful);
    }

    @Override
    public boolean isUnsuccessful()
        throws IllegalStateException
    {
        if (isEmpty())
        {
            throw new IllegalStateException("empty result map"); //$NON-NLS-1$
        }
        return mResults.values().parallelStream().anyMatch(SwResult::isUnsuccessful);
    }

    /**
     * Adds a new result to this result set.
     *
     * @param key the key to bind the result to
     * @param result the additional result (must not be null)
     *
     * @throws IllegalArgumentException null key or result
     */
    public void addResult(KeyType key, ResultType result)
            throws IllegalArgumentException
    {
        if (key == null)
        {
            throw new IllegalArgumentException("null key"); //$NON-NLS-1$
        }
        if (result == null)
        {
            throw new IllegalArgumentException("null result"); //$NON-NLS-1$
        }
        mResults.put(key, result);
    }

    /**
     * Whether there is a result bound to the given key.
     *
     * @param key the key to test
     *
     * @return  Whether there is a result bound to the given key.
     *
     * @throws IllegalArgumentException null key
     */
    public boolean hasResult(KeyType key)
        throws IllegalArgumentException
    {
        if (key == null)
        {
            throw new IllegalArgumentException("null key"); //$NON-NLS-1$
        }
        return mResults.containsKey(key);
    }

    /**
     * Returns the result bound to the given key.
     *
     * @param key the result's key
     *
     * @return The corresponding result.
     *
     * @throws IllegalArgumentException null or unknown key
     */
    public ResultType getResult(KeyType key)
        throws IllegalArgumentException
    {
        if (key == null)
        {
            throw new IllegalArgumentException("null key"); //$NON-NLS-1$
        }
        if (!mResults.containsKey(key))
        {
            throw new IllegalArgumentException("unknown key: " + key); //$NON-NLS-1$
        }
        return mResults.get(key);
    }

    @Override
    public List<ResultType> getResults()
    {
        return new ArrayList<>(mResults.values());
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
