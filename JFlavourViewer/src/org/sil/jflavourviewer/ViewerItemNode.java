/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.sil.jflavourviewer;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.swing.Action;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.util.actions.SystemAction;
import org.openide.util.lookup.Lookups;
import org.sil.jflavourapi.ItemImage;
import org.sil.jflavourapi.JFlavourItemBean;
import org.sil.jflavourapi.JFlavourProjectBean;

/**
 *
 * @author toby
 */
public class ViewerItemNode extends AbstractNode
{
    private ViewerItem item;
    private JFlavourProjectBean activeProject;
     
    public ViewerItemNode(ViewerItem item, JFlavourProjectBean activeProject)
    {
        super (Children.LEAF, Lookups.singleton(item));
        this.item = item;
        this.activeProject = activeProject;
        setDisplayName (item.item.getLabel());
    }
    
    @Override
    public Image getIcon(int type)
    {
        // ignore the type parameter - I want these icons bigger!
        ItemImage image = item.item.getDefaultImage();
        if (image != null) {
            try {
                image.load();
            } catch (IOException ex) {
                return super.getIcon(type);
            }
            BufferedImage bufferedImage = image.getBufferedImage();
            int w = bufferedImage.getWidth();
            int h = bufferedImage.getHeight();
            if (w > h) {
                return bufferedImage.getScaledInstance(128, Math.round((128.0f * h)/w), Image.SCALE_FAST);
            } else {
                return bufferedImage.getScaledInstance(Math.round((128.0f * w)/h), 128, Image.SCALE_FAST);
            }
        } else {
            return super.getIcon(type);
        }
    }
    
    @Override
    public void destroy()
    {
        item.item.delete();
        activeProject.removeItem(item.item);
        Node parent = this.getParentNode();
        parent.getChildren().remove(new Node[]{this});
        
    }
    
    @Override
    public Action[] getActions(boolean context)
    {
        if (!context) {
            return new Action[]
            {
                SystemAction.get( ItemDeleteAction.class )
            };
        } else {
            return super.getActions(context);
        }
    }
    
    @Override
    public Action getPreferredAction()
    {
        return SystemAction.get(PlayAudioAction.class);
    }
    
    public JFlavourItemBean getItem()
    {
        return this.item.item;
    }
}
