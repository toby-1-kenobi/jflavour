/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.sil.jflavourviewer;

import javax.swing.JOptionPane;
import org.openide.nodes.Node;
import org.openide.util.HelpCtx;
import org.openide.util.actions.NodeAction;
import org.sil.jflavourviewer.api.ViewerAction;

/**
 *
 * @author toby
 */
public class ItemDeleteAction extends NodeAction implements ViewerAction
{
    
    Node[] selected = new Node[0];

    @Override
    protected void performAction(Node[] nodes)
    {
        String question = "Delete these items?";
        if (nodes.length == 1) {
            question = "Delete item " + nodes[0].getDisplayName() + "?";
        }
        int response = JOptionPane.showConfirmDialog(null, question, "Delete Items", JOptionPane.OK_CANCEL_OPTION);
        if (response == JOptionPane.OK_OPTION)
        {
            for (int i = 0; i < nodes.length; ++i) {
                ViewerItemNode node = (ViewerItemNode)nodes[i];
                node.destroy();
            }
        }
    }
    
    @Override
    public void performAction()
    {
        performAction(selected);
    }

    @Override
    protected boolean enable(Node[] nodes)
    {
        for (int i = 0; i < nodes.length; i++) {
            Node node = nodes[i];
            if (node instanceof ViewerItemNode) return true;
        }
        return false;
    }

    @Override
    public String getName()
    {
        return "delete";
    }

    @Override
    public HelpCtx getHelpCtx()
    {
        return HelpCtx.DEFAULT_HELP;
    }

    @Override
    public boolean setEnabled(Node[] selected)
    {
        this.selected = selected;
        boolean enable = enable(selected);
        setEnabled(enable);
        return enable;
    }
    
}
