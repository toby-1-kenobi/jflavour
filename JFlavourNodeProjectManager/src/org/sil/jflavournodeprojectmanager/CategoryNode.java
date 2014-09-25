/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.sil.jflavournodeprojectmanager;

import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.util.lookup.Lookups;

/**
 *
 * @author toby
 */
public class CategoryNode extends AbstractNode
{
     
    public CategoryNode(String category) {
        super (Children.create(new ItemChildFactory(category), true), Lookups.singleton(category));
        setDisplayName (category);
    }
}
