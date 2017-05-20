/*
 * Copyright (C) 2017 shawware.com.au
 *
 * License: GNU General Public License V3 (or later)
 * http://www.gnu.org/copyleft/gpl.html
 */

package au.com.shawware.util;

/**
 * Verifies that a string is not empty.
 *
 * @author <a href="mailto:david.shaw@shawware.com.au">David Shaw</a>
 */
public class NotEmpty implements IValidator<String>
{
    /** A description of the entity being validated. */
    private String mEntityDescription;

    /**
     * @param description the entity being validated
     */
    public NotEmpty(String description)
    {
        super();
        mEntityDescription = description;
    }

    /* (non-Javadoc)
     * @see au.com.shawware.util.IValidator#validate(java.lang.Object)
     */
    @Override
    public void validate(String s)
        throws IllegalArgumentException
    {
        if (StringUtil.isEmpty(s)) {
            throw new IllegalArgumentException("empty " + mEntityDescription); //$NON-NLS-1$
        }
    }
}
