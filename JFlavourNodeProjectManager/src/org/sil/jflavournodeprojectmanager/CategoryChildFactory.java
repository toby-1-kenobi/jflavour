/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.sil.jflavournodeprojectmanager;

import java.io.IOException;
import java.util.List;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Node;
import org.sil.jflavourapi.JFlavourProjectBean;

/**
 * A factory to create project nodes.
 * It needs to read the project data from an XML file
 * @author toby
 */
public class CategoryChildFactory extends ChildFactory<String>
{
    
    JFlavourProjectBean project;
    
    public CategoryChildFactory(JFlavourProjectBean project)
    {
        super();
        this.project = project;
    }

    @Override
    protected boolean createKeys(List<String> list)
    {
        list.addAll(project.getCategories());
        return true;
    }
    
    @Override
    protected Node createNodeForKey(String key) {
        return new CategoryNode(key, project);
    }
    
}
