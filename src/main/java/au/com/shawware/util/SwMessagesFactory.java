/*
 * Copyright (C) 2007 shawware.com.au
 *
 * License: GNU General Public License V3 (or later)
 * http://www.gnu.org/copyleft/gpl.html
 */

package au.com.shawware.util;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * This class constructs messages classes for plug-ins.
 *
 * @author <a href="mailto:david.shaw@shawware.com.au">David Shaw</a>
 */
public final class SwMessagesFactory
{
    /** The map of plug-ins to messages. */
    private static Map<String, SwMessages> sMessages;
    
    /**
     * Returns a message handler for the given plug-in and properties file.
     * 
     * @param plugin the plug-in ID - must not be empty
     * @param resources the plug-in's resource bundle - must not be null
     * 
     * @return The plug-ins message handler.
     */
    // TODO: synch
    public static SwMessages getMessages(final String plugin, final ResourceBundle resources)
    {
        assert SwAssert.isNotEmpty(plugin) : "empty plug-in"; //$NON-NLS-1$
        assert SwAssert.isNotNull(resources) : "null resource bundle"; //$NON-NLS-1$
        if (sMessages == null)
        {
            sMessages = new HashMap<String, SwMessages>();
        }
        if (!sMessages.containsKey(plugin))
        {
            sMessages.put(plugin, new SwMessages(plugin, resources));
        }
        return sMessages.get(plugin);
    }
}