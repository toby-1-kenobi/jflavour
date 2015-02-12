/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.sil.jflavourviewer.api;

import javax.swing.Action;
import org.openide.nodes.Node;

/**
 * This defines an action that will be added to a button on JFlavourViewerTopComponent's toolbar.
 * The action need to be put into the central lookup to register.
 * The JFlavourViewerTopComponent will call setEnabled(Node[]) every time the node selection changes in the viewer
 * and provide the selected nodes to the action.
 * @author Toby Anderson
 */
public interface ViewerAction extends Action
{    

    /**
     * Set this action to be enabled or not depending on the set of Nodes that are selected.
     * As a side effect this object should remember which nodes were passed to this method the last time it was called
     * and when performAction() is called if this action operates on nodes then these nodes should be the ones operated on.
     * @param selected the set of nodes that are selected
     * @return true if enabled, false if not
     */
    public boolean setEnabled(Node[] selected);
}
