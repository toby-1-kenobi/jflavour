/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.sil.jflavournodeprojectmanager;

import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.util.lookup.Lookups;
import org.sil.jflavourapi.JFlavourItemBean;

/**
 *
 * @author toby
 */
public class ItemNode extends AbstractNode
{
     
    public ItemNode(JFlavourItemBean item) {
        super (Children.LEAF, Lookups.singleton(item));
        setDisplayName (item.getLabel());
    }
}
