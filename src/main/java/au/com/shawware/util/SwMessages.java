/*
 * Copyright (C) 2007 shawware.com.au
 *
 * License: GNU General Public License V3 (or later)
 * http://www.gnu.org/copyleft/gpl.html
 */

package au.com.shawware.util;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * A utility class for managing messages for a plug-in.
 * Assumes a single properties file per plug-in.
 * Do not create directly. Retrieve one from {@link SwMessagesFactory}.
 *
 * @author <a href="mailto:david.shaw@shawware.com.au">David Shaw</a>
 */
public final class SwMessages
{
    /** The resource bundle. */
    private final ResourceBundle mResourceBundle;
    /** The plug-in ID. */
    private final String mPlugin;

    /**
     * Constructs a new message handler on the given plug-in.
     * Package visibility to force creation via the factory.
     * 
     * @param plugin the plug-in to work with - must be non-empty
     * @param resources the plug-in's resource bundle - must be non-null
     */
    /* package */ SwMessages(final String plugin, final ResourceBundle resources)
    {
        assert SwAssert.isNotEmpty(plugin) : "empty plug-in"; //$NON-NLS-1$
        assert SwAssert.isNotNull(resources) : "null resources"; //$NON-NLS-1$

        mPlugin = plugin;
        mResourceBundle = resources;
    }

    /**
     * Returns the string corresponding to the given key.
     * Prefixes the key with the plug-in ID for this instance.
     * Wraps the key in exclamation marks and returns it if it can't be found.
     * 
     * @param key the key to lookup - must be non-empty
     * 
     * @return The message corresponding to the given key.
     */
    public String getString(final String key)
    {
        assert SwAssert.isNotEmpty(key) : "empty key"; //$NON-NLS-1$
        final String fullKey = SwPropertyUtils.appendSuffix(mPlugin, key); 
        try
        {
            return mResourceBundle.getString(fullKey); 
        }
        catch (MissingResourceException e)
        {
            return '!' + fullKey + '!';
        }
    }
}