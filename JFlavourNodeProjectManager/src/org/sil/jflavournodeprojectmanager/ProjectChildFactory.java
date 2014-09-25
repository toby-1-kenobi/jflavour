/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.sil.jflavournodeprojectmanager;

import java.util.List;
import org.openide.nodes.ChildFactory;
import org.sil.jflavourapi.JFlavourProjectBean;

/**
 * A factory to create project nodes.
 * It needs to read the project data from an XML file
 * @author toby
 */
public class ProjectChildFactory extends ChildFactory<JFlavourProjectBean>
{
    
    public ProjectChildFactory()
    {
        super();
    }

    @Override
    protected boolean createKeys(List<JFlavourProjectBean> list)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
