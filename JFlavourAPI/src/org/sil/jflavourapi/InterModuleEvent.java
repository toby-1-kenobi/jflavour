/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.sil.jflavourapi;

import java.awt.Event;

/**
 * This class defines something that modules can pass to each other through lookups
 * to "poke" each other when certain events happen
 * @author toby
 */
public interface InterModuleEvent
{    
    public Event getEvent();
    
    public String getIdentifier();
    
    public boolean hasIdentifier(String id);
}
