/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.sil.jflavournodeprojectmanager;

import java.io.IOException;
import javax.swing.JOptionPane;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;
import org.openide.util.HelpCtx;
import org.openide.util.actions.NodeAction;

/**
 *
 * @author toby
 */
public class ProjectDeleteAction extends NodeAction
{

    @Override
    protected void performAction(Node[] nodes)
    {
        String question = "Delete these projects?";
        if (nodes.length == 1) {
            question = "Delete project " + nodes[0].getDisplayName() + "?";
        }
        int response = JOptionPane.showConfirmDialog(null, question, "Delete Project", JOptionPane.OK_CANCEL_OPTION);
        if (response == JOptionPane.OK_OPTION)
        {
            for (int i = 0; i < nodes.length; ++i) {
                ProjectNode node = (ProjectNode)nodes[i];
                node.destroy();
            }
        }
    }

    @Override
    protected boolean enable(Node[] nodes)
    {
        for (int i = 0; i < nodes.length; i++) {
            Node node = nodes[i];
            if (node instanceof ProjectNode) return true;
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

}
