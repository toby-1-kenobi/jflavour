/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.sil.jflavourviewer;

import java.awt.Event;
import org.sil.jflavourapi.InterModuleEvent;

/**
 *
 * @author toby
 */
public class ToolEvent implements InterModuleEvent
{
    private Event event;
    private String identifier;
    
    public ToolEvent(Event event, String identifier)
    {
        this.event = event;
        this.identifier = identifier;
    }

    @Override
    public Event getEvent()
    {
        return event;
    }

    @Override
    public String getIdentifier()
    {
        return identifier;
    }

    @Override
    public boolean hasIdentifier(String id)
    {
        return identifier.equals(id);
    }
    
}
