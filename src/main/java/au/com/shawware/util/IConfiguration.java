/*
 * Copyright (C) 2017 shawware.com.au
 *
 * License: GNU General Public License V3 (or later)
 * http://www.gnu.org/copyleft/gpl.html
 */

package au.com.shawware.util;

/**
 * Our configuration property API - simple key/value pairs.
 *
 * This is deliberately very simple, ie. it has limited functionality.
 * There's no point re-inventing the wheel. If we need something more robust
 * then we should look at adapting something like Apache Commons Configuration.
 *
 * TODO: do we need to support default values?
 * TODO: what happens if value exists but fails to parse into type?
 * TODO: consider &lt;T&gt; get(Class&lt;T&gt; clazz, String key)
 */
public interface IConfiguration
{
    /**
     * Looks up the value at the given key.
     *
     * @param key the property key
     *
     * @return The corresponding value.
     */
    String getString(String key);

    /**
     * Looks up the value at the given key.
     *
     * @param key the property key
     *
     * @return The corresponding integer value.
     */
    int getInteger(String key);

    /**
     * Looks up the value at the given key.
     *
     * @param key the property key
     *
     * @return The corresponding boolean value.
     */
    boolean getBoolean(String key);
}
