/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.sil.jflavournodeprojectmanager;

import java.util.List;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Node;
import org.sil.jflavourapi.Category;
import org.sil.jflavourapi.JFlavourItemBean;
import org.sil.jflavourapi.JFlavourProjectBean;

/**
 * A factory to create project nodes.
 * It needs to read the project data from an XML file
 * @author toby
 */
public class ItemNodeFactory extends ChildFactory<JFlavourItemBean>
{
    
    JFlavourProjectBean project;
    Category category;
    
    public ItemNodeFactory(Category category, JFlavourProjectBean project)
    {
        super();
        this.project = project;
        this.category = category;
    }

    @Override
    protected boolean createKeys(List<JFlavourItemBean> list)
    {
        list.addAll(project.getItemsInCategory(category));
        return true;
    }
    
    @Override
    protected Node createNodeForKey(JFlavourItemBean key) {
        return new ItemNode(key);
    }
    
}
