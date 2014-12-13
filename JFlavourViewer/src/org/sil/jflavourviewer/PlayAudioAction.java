/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.sil.jflavourviewer;

import java.io.IOException;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;
import org.openide.util.HelpCtx;
import org.openide.util.actions.NodeAction;

/**
 *
 * @author toby
 */
public class PlayAudioAction extends NodeAction
{

    @Override
    protected void performAction(Node[] nodes)
    {
        for (int i = 0; i < nodes.length; i++) {
            if (nodes[i] instanceof ViewerItemNode)
            {
                try {
                    ((ViewerItemNode)nodes[i]).getItem().getDefaultAudio().play();
                } catch (IOException ex) {
                    Exceptions.printStackTrace(ex);
                }
            }
        }
    }

    @Override
    protected boolean enable(Node[] nodes)
    {
        // one and only one ViewerItemNode selected
        int count = 0;
        for (int i = 0; i < nodes.length; i++) {
            if (nodes[i] instanceof ViewerItemNode) ++count;
        }
        return count == 1;
    }

    @Override
    public String getName()
    {
        return "play";
    }

    @Override
    public HelpCtx getHelpCtx()
    {
        return HelpCtx.DEFAULT_HELP;
    }
    
}
