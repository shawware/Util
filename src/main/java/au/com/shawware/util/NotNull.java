/*
 * Copyright (C) 2018 shawware.com.au
 *
 * License: GNU General Public License V3 (or later)
 * http://www.gnu.org/copyleft/gpl.html
 */

package au.com.shawware.util;

/**
 * Verifies that a value is not <code>null</code>.
 *
 * @author <a href="mailto:david.shaw@shawware.com.au">David Shaw</a>
 * 
 * @param <Entity> the type of entity being validated
 */
public class NotNull<Entity> implements IValidator<Entity>
{
    /** A description of the entity being validated. */
    private String mEntityDescription;

    /**
     * @param description the entity being validated
     */
    public NotNull(String description)
    {
        super();
        mEntityDescription = description;
    }

    /* (non-Javadoc)
     * @see au.com.shawware.util.IValidator#validate(java.lang.Object)
     */
    @Override
    public void validate(Entity entity)
        throws IllegalArgumentException
    {
        if (entity == null)
        {
            throw new IllegalArgumentException("null " + mEntityDescription); //$NON-NLS-1$
        }
    }
}
