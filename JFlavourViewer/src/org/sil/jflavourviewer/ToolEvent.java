/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.sil.jflavourviewer;

import java.awt.event.ActionEvent;
import org.sil.jflavourapi.InterModuleEvent;
import org.sil.jflavourapi.JFlavourProjectBean;

/**
 *
 * @author toby
 */
public class ToolEvent implements InterModuleEvent
{
    private ActionEvent event;
    private String identifier;
    private JFlavourProjectBean project;
    
    public ToolEvent(ActionEvent event, String identifier, JFlavourProjectBean project)
    {
        this.event = event;
        this.identifier = identifier;
        this.project = project;
    }

    @Override
    public ActionEvent getEvent()
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

    @Override
    public JFlavourProjectBean getProject()
    {
        return project;
    }
    
}
