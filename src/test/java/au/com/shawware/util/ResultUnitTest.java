/*
 * Copyright (C) 2018 shawware.com.au
 *
 * License: GNU General Public License V3 (or later)
 * http://www.gnu.org/copyleft/gpl.html
 */

package au.com.shawware.util;

import java.util.List;
import java.util.stream.IntStream;

import org.junit.Assert;
import org.junit.Test;

import au.com.shawware.util.test.AbstractUnitTest;

/**
 * Exercise and verify our {@link SwResult} and related classes.
 */
@SuppressWarnings({ "nls", "static-method" })
public class ResultUnitTest extends AbstractUnitTest
{
    @Test
    public void testTheBasics()
    {
        Assert.assertTrue(SwResult.SUCCESS.isSuccessful());
        Assert.assertFalse(SwResult.SUCCESS.isUnsuccessful());
        Assert.assertFalse(SwResult.SUCCESS.isIncomplete());
        Assert.assertFalse(SwResult.SUCCESS.isUnsupported());
        Assert.assertEquals(SwResult.CODE_OK, SwResult.SUCCESS.getCode());

        Assert.assertFalse(SwResult.INCOMPLETE.isSuccessful());
        Assert.assertTrue(SwResult.INCOMPLETE.isUnsuccessful());
        Assert.assertTrue(SwResult.INCOMPLETE.isIncomplete());
        Assert.assertFalse(SwResult.INCOMPLETE.isUnsupported());
        Assert.assertEquals(SwResult.CODE_ERROR_INCOMPLETE, SwResult.INCOMPLETE.getCode());

        SwResult unsupported = SwResult.unsupportedOperation("Missing");
        Assert.assertFalse(unsupported.isSuccessful());
        Assert.assertTrue(unsupported.isUnsuccessful());
        Assert.assertFalse(unsupported.isIncomplete());
        Assert.assertTrue(unsupported.isUnsupported());
        Assert.assertEquals(SwResult.CODE_ERROR_UNSUPPORTED, unsupported.getCode());

        SwResult failed = SwResult.failedWith(100, "Unknown");
        Assert.assertFalse(failed.isSuccessful());
        Assert.assertTrue(failed.isUnsuccessful());
        Assert.assertFalse(failed.isIncomplete());
        Assert.assertFalse(failed.isUnsupported());
        Assert.assertEquals(100, failed.getCode());
    }

    @Test
    public void testCodeRange()
    {
        Assert.assertTrue(SwResult.SUCCESS.hasStandardCode());
        Assert.assertTrue(SwResult.INCOMPLETE.hasStandardCode());
        SwResult error = SwResult.failedWith(SwResult.CODE_ERROR_INVALID_INPUT, "Out of range");
        Assert.assertTrue(error.hasStandardCode());
        error = SwResult.failedWith(-20000, "Low level");
        Assert.assertFalse(error.hasStandardCode());
        error = SwResult.failedWith(100, "App specific");
        Assert.assertFalse(error.hasStandardCode());
    }

    @Test
    public void testStandardisation()
    {
        SwResult standardisedResult = SwResult.standardiseCode(SwResult.SUCCESS, true);
        Assert.assertTrue(standardisedResult.isSuccessful());
        Assert.assertTrue(standardisedResult.hasStandardCode());
        Assert.assertEquals(SwResult.CODE_OK, standardisedResult.getCode());

        SwResult result = SwResult.failedWith(SwResult.CODE_ERROR_INCOMPLETE, "Incomplete");
        standardisedResult = SwResult.standardiseCode(result, true);
        Assert.assertTrue(standardisedResult.isUnsuccessful());
        Assert.assertTrue(standardisedResult.hasStandardCode());
        Assert.assertEquals(SwResult.CODE_ERROR_INCOMPLETE, standardisedResult.getCode());

        result = SwResult.failedWith(100, "Who knows");
        standardisedResult = SwResult.standardiseCode(result, true);
        Assert.assertTrue(standardisedResult.isUnsuccessful());
        Assert.assertTrue(standardisedResult.hasStandardCode());
        Assert.assertEquals(SwResult.CODE_ERROR_UNRECOVERABLE, standardisedResult.getCode());

        standardisedResult = SwResult.standardiseCode(result, false);
        Assert.assertTrue(standardisedResult.isUnsuccessful());
        Assert.assertTrue(standardisedResult.hasStandardCode());
        Assert.assertEquals(SwResult.CODE_ERROR_UNEXPECTED, standardisedResult.getCode());
    }

    @Test
    public void testResultSet()
    {
        ResultSet<SwResult> set = new ResultSet<>();

        // Verify error checks
        verifyExceptionThrown(set::isSuccessful,         IllegalStateException.class,    "empty result set");
        verifyExceptionThrown(set::isUnsuccessful,       IllegalStateException.class,    "empty result set");
        verifyExceptionThrown(() -> set.addResult(null), IllegalArgumentException.class, "null result");

        // Verify empty set
        verifyResultCollection(set, true, 0, false, false);

        set.reset();
        verifyResultCollection(set, true, 0, false, false);

        set.addResult(SwResult.SUCCESS);
        verifyResultCollection(set, false, 1, true, true);

        set.reset();
        verifyResultCollection(set, true, 0, false, false);

        set.addResult(SwResult.INCOMPLETE);
        verifyResultCollection(set, false, 1, false, false);

        set.reset();
        verifyResultCollection(set, true, 0, false, false);

        IntStream.range(0, 10).forEach((i) -> set.addResult(SwResult.SUCCESS));
        verifyResultCollection(set, false, 10, true, true);

        set.addResult(SwResult.INCOMPLETE);
        verifyResultCollection(set, false, 11, false, true);

        set.reset();
        verifyResultCollection(set, true, 0, false, false);

        IntStream.range(0, 10).forEach((i) -> set.addResult(SwResult.failedWith(i, null)));
        verifyResultCollection(set, false, 10, false, false);

        set.addResult(SwResult.SUCCESS);
        verifyResultCollection(set, false, 11, false, true);
    }

    @Test
    public void testResultMap()
    {
        ResultMap<String, SwResult> map = new ResultMap<>();

        // Verify error checks
        verifyExceptionThrown(map::isSuccessful,         IllegalStateException.class,    "empty result map");
        verifyExceptionThrown(map::isUnsuccessful,       IllegalStateException.class,    "empty result map");

        verifyExceptionThrown(() -> map.addResult(null, null), IllegalArgumentException.class, "null key");
        verifyExceptionThrown(() -> map.addResult("a", null),  IllegalArgumentException.class, "null result");

        // Verify empty map
        verifyResultCollection(map, true, 0, false, false);
        verifyMapEntry(map, "alpha", false, null);
        verifyMapEntry(map, "beta", false, null);

        map.reset();
        verifyResultCollection(map, true, 0, false, false);

        map.addResult("alpha", SwResult.SUCCESS);
        verifyResultCollection(map, false, 1, true, true);
        verifyMapEntry(map, "alpha", true, SwResult.SUCCESS);
        verifyMapEntry(map, "beta", false, null);

        map.reset();
        verifyResultCollection(map, true, 0, false, false);
        verifyMapEntry(map, "alpha", false, null);
        verifyMapEntry(map, "beta", false, null);

        map.addResult("beta", SwResult.INCOMPLETE);
        verifyResultCollection(map, false, 1, false, false);
        verifyMapEntry(map, "alpha", false, null);
        verifyMapEntry(map, "beta", true, SwResult.INCOMPLETE);

        map.reset();
        verifyResultCollection(map, true, 0, false, false);
        verifyMapEntry(map, "alpha", false, null);
        verifyMapEntry(map, "beta", false, null);

        IntStream.range(0, 10).forEach((i) -> map.addResult(String.valueOf(i), SwResult.SUCCESS));
        verifyResultCollection(map, false, 10, true, true);

        map.addResult("gamma", SwResult.INCOMPLETE);
        verifyResultCollection(map, false, 11, false, true);
        verifyMapEntry(map, "gamma", true, SwResult.INCOMPLETE);

        map.reset();
        verifyResultCollection(map, true, 0, false, false);
        verifyMapEntry(map, "alpha", false, null);
        verifyMapEntry(map, "beta", false, null);

        IntStream.range(0, 10).forEach((i) -> map.addResult(String.valueOf(i), SwResult.failedWith(i, null)));
        verifyResultCollection(map, false, 10, false, false);

        map.addResult("gamma", SwResult.SUCCESS);
        verifyResultCollection(map, false, 11, false, true);
        verifyMapEntry(map, "gamma", true, SwResult.SUCCESS);
    }

    /**
     * Verifies the given map has or does not have a result at the given key.
     *
     * @param map the map under test
     * @param key the result's key
     * @param shouldBePresent whether a result is expected for that key
     * @param expectedResult the expected result (if any)
     */
    private void verifyMapEntry(ResultMap<String, SwResult> map, String key, boolean shouldBePresent, SwResult expectedResult)
    {
        if (shouldBePresent)
        {
            Assert.assertTrue(map.hasResult(key));
            SwResult result = map.getResult(key);
            Assert.assertNotNull(result);
            Assert.assertEquals(expectedResult.toString(), result.toString());
        }
        else
        {
            Assert.assertFalse(map.hasResult(key));
            verifyExceptionThrown(() -> map.getResult(key), IllegalArgumentException.class, "unknown key: " + key);
        }
    }

    /**
     * Verify the given result collection is as expected.
     *
     * @param collection the result collection under test
     * @param shouldBeEmpty whether the set is expected to be empty
     * @param expectedSize the expected number of results
     * @param expectedOverallResult the expected overall result (if not empty)
     * @param atLeastOneSuccess whether one element result is successful (if collection is not empty)
     */
    @SuppressWarnings("boxing")
    private void verifyResultCollection(IResultCollection<SwResult> collection, boolean shouldBeEmpty, int expectedSize,
                                        boolean expectedOverallResult, boolean atLeastOneSuccess)
    {
        Assert.assertEquals(shouldBeEmpty,  collection.isEmpty());
        Assert.assertEquals(!shouldBeEmpty, collection.hasResult());
        List<SwResult> results = collection.getResults();
        Assert.assertNotNull(results);
        Assert.assertEquals(expectedSize, results.size());
        if (!shouldBeEmpty)
        {
            Assert.assertEquals(expectedOverallResult,  collection.isSuccessful());
            Assert.assertEquals(atLeastOneSuccess,      collection.isPartiallySuccessful());
            Assert.assertEquals(!expectedOverallResult, collection.isUnsuccessful());
        }
    }
}
