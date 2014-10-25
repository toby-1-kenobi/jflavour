/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.sil.jflavournodeprojectmanager;

import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Node;
import org.sil.jflavourapi.Category;
import org.sil.jflavourapi.JFlavourProjectBean;

/**
 * A factory to create project nodes.
 * It needs to read the project data from an XML file
 * @author toby
 */
public class CategoryNodeFactory extends ChildFactory<String>
{
    
    JFlavourProjectBean project;
    
    public CategoryNodeFactory(JFlavourProjectBean project)
    {
        super();
        this.project = project;
    }

    @Override
    protected boolean createKeys(List<String> list)
    {
        SortedSet<Category> allCategories = project.getCategories();
        for (Iterator<Category> it = allCategories.iterator(); it.hasNext();) {
            list.add(it.next().toString());
        }
        return true;
    }
    
    @Override
    protected Node createNodeForKey(String key) {
        return new CategoryNode(new Category(key), project);
    }
    
}
