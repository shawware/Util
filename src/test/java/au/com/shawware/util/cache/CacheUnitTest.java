/*
 * Copyright (C) 2018 shawware.com.au
 *
 * License: GNU General Public License V3 (or later)
 * http://www.gnu.org/copyleft/gpl.html
 */

package au.com.shawware.util.cache;

import java.time.Duration;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import au.com.shawware.util.MutableInteger;
import au.com.shawware.util.test.AbstractUnitTest;
import au.com.shawware.util.time.ITimeSource;
import au.com.shawware.util.time.TestTime;

/**
 * Exercises and verifies our {@link ICache} API.
 *
 * @author <a href="mailto:david.shaw@shawware.com.au">David Shaw</a>
 */
@SuppressWarnings({"nls", "static-method"})
public class CacheUnitTest extends AbstractUnitTest
{
    /** How long a value is held in the cache before being refreshed. */
    private static int sValueHeldFor;
    /** The lifetime of an entity in the cache. */
    private static Duration sCacheLifeTime;
    /** The test clock. */
    private static ITimeSource sTestClock;
    
    @BeforeClass
    public static void setupTestFixtures()
    {
        // Set up the test fixtures.
        final int cacheMinutes       = 5;
        final int clockTickMinutes   = 1;
        final Duration testClockTick = Duration.ofMinutes(clockTickMinutes);
        
        sValueHeldFor  = cacheMinutes / clockTickMinutes;
        sCacheLifeTime = Duration.ofMinutes(cacheMinutes);
        sTestClock     = new TestTime(testClockTick);
    }

    @Test
    public void verifyCacheWithIdenticalKeyTypes()
    {
        final int initialValue = 42;
        TestSource source      = new TestSource();

        ICache<String, MutableInteger> cache = new Cache<>(sCacheLifeTime, sTestClock, source, key -> key);

        verifyErrorHandling(cache, "a", "a");

        source.addValue("k1", initialValue);
        source.addValue("k2", initialValue);

        verifyCache(cache, initialValue, "k1", "k2");
    }

    @Test
    public void verifyCacheWithDifferentKeyTypes()
    {
        final int initialValue = 101;
        TestSource source      = new TestSource();

        ICache<TestEntity, MutableInteger> cache = new Cache<>(sCacheLifeTime, sTestClock, source, TestEntity::getName);

        TestEntity entityA = new TestEntity("a");
        verifyErrorHandling(cache, entityA, "a");

        TestEntity one = new TestEntity("e1");
        TestEntity two = new TestEntity("e2");

        source.addValue("e1", initialValue);
        source.addValue("e2", initialValue);

        verifyCache(cache, initialValue, one, two);
    }

    /**
     * Verify the error handling for the given cache.
     * 
     * @param cache the cache under test
     * @param missingKey a key that is not present in the cache
     * @param keyValue the value of that key (as a String)
     * 
     * @param <CacheKeyType> the cache key type
     */
    private <CacheKeyType> void verifyErrorHandling(ICache<CacheKeyType, MutableInteger> cache, CacheKeyType missingKey, String keyValue)
    {
        verifyExceptionThrown(() -> cache.get(null),           IllegalArgumentException.class, "null cache key");
        verifyExceptionThrown(() -> cache.get(missingKey),     IllegalArgumentException.class, "Unknown source key: " + keyValue);
        verifyExceptionThrown(() -> cache.refresh(null),       IllegalArgumentException.class, "null cache key");
        verifyExceptionThrown(() -> cache.refresh(missingKey), IllegalArgumentException.class, "Unknown source key: " + keyValue);
    }

    /**
     * Verify the cache behaviour for the given cache.
     * 
     * @param cache the cache under test
     * @param initialValue the initial value for a cached value
     * @param key1 the first key to a value present in the source
     * @param key2 the second key to a value present in the source
     * 
     * @param <CacheKeyType> the cache key type
     */
    private <CacheKeyType> void verifyCache(ICache<CacheKeyType, MutableInteger> cache, int initialValue, CacheKeyType key1, CacheKeyType key2)
    {
        MutableInteger mi = null;

        for (int i=0; i<sValueHeldFor; i++)
        {
            mi = cache.get(key1);
            Assert.assertEquals(initialValue + 1, mi.getValue());
        }

        mi = cache.get(key1);
        Assert.assertEquals(initialValue + 2, mi.getValue());
        mi = cache.get(key2);
        Assert.assertEquals(initialValue + 1, mi.getValue());

        mi = cache.refresh(key1);
        Assert.assertEquals(initialValue + 3, mi.getValue());
        mi = cache.get(key2);
        Assert.assertEquals(initialValue + 1, mi.getValue());

        cache.refreshAll();
        mi = cache.get(key1);
        Assert.assertEquals(initialValue + 4, mi.getValue());
        mi = cache.get(key2);
        Assert.assertEquals(initialValue + 2, mi.getValue());
    }

    @Test
    public void verifyTestSource()
    {
        TestSource source = new TestSource();

        verifyExceptionThrown(() -> source.get(null), IllegalArgumentException.class, "empty source key");
        verifyExceptionThrown(() -> source.get("a"),  IllegalArgumentException.class, "Unknown source key: a");
        
        source.addValue("a", 0);

        verifyExceptionThrown(() -> source.addValue(null, 0), IllegalArgumentException.class, "empty source key");
        verifyExceptionThrown(() -> source.addValue("a", 0),  IllegalArgumentException.class, "Source key is already present: a");
        
        source.addValue("key", -1);
        for (int i=0; i<10; i++)
        {
            Assert.assertEquals(i, source.get("key").getValue());
        }
    }
}
