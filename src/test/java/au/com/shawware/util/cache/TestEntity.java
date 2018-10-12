/*
 * Copyright (C) 2018 shawware.com.au
 *
 * License: GNU General Public License V3 (or later)
 * http://www.gnu.org/copyleft/gpl.html
 */

package au.com.shawware.util.cache;

/**
 * A simple entity to facilitate testing different source and cache types.
 *
 * @author <a href="mailto:david.shaw@shawware.com.au">David Shaw</a>
 */
public class TestEntity
{
    /** The entity's name */
    private final String mName;

    /**
     * Construct a new entity.
     * 
     * @param name the entity's name
     */
    public TestEntity(String name)
    {
        super();
        mName = name;
    }

    /**
     * @return The entity's name.
     */
    public String getName()
    {
        return mName;
    }

    @Override
    public String toString()
    {
        return mName;
    }
}
