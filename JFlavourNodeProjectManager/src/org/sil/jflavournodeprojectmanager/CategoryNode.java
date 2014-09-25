/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.sil.jflavournodeprojectmanager;

import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.util.lookup.Lookups;
import org.sil.jflavourapi.JFlavourProjectBean;

/**
 *
 * @author toby
 */
public class CategoryNode extends AbstractNode
{
     
    public CategoryNode(String category, JFlavourProjectBean project) {
        super (Children.create(new ItemNodeFactory(category, project), true), Lookups.singleton(category));
        setDisplayName (category);
    }
}
