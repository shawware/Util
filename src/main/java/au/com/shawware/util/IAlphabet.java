/*
 * Copyright (C) 2017 shawware.com.au
 *
 * License: GNU General Public License V3 (or later)
 * http://www.gnu.org/copyleft/gpl.html
 */

package au.com.shawware.util;

/**
 * Specifies an API for a "alphabet", ie. a set of entities of the given type.
 *
 * @author <a href="mailto:david.shaw@shawware.com.au">David Shaw</a>
 * 
 * @param <Entity> 
 */
public interface IAlphabet<Entity>
{
    /**
     * Does the given entity belong to this alphabet?
     * 
     * @param entity the entity to test
     * 
     * @return Whether the given entity belongs to the alphabet.
     */
    public boolean contains(Entity entity);
}
