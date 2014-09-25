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
public class JFlavourProjectNode extends AbstractNode
{
     
    public JFlavourProjectNode(JFlavourProjectBean project) {
        super (Children.create(new CategoryChildFactory(), true), Lookups.singleton(obj));
        setDisplayName ("Event " + project.getName());
    }
    
    public JFlavourProjectNode() {
        super (Children.create(new ProjectChildFactory(), true));
        setDisplayName ("Projects");
    }
}
