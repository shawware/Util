/*
 * Copyright (C) 2017 shawware.com.au
 *
 * License: GNU General Public License V3 (or later)
 * http://www.gnu.org/copyleft/gpl.html
 */

package au.com.shawware.util;

/**
 * The validation / rule API.
 *
 * @author <a href="mailto:david.shaw@shawware.com.au">David Shaw</a>
 *
 * @param <Entity> the entity to be validated
 */
public interface IValidator<Entity>
{
    /**
     * Validate the given entity. If the entity is valid, return normally.
     * 
     * @param entity the entity to be validated
     * 
     * @throws IllegalArgumentException if the given entity is invalid
     */
    void validate(Entity entity)
        throws IllegalArgumentException;
}
