/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.sil.jflavournodeprojectmanager;

import java.io.IOException;
import javax.swing.Action;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;
import org.openide.util.actions.SystemAction;
import org.openide.util.lookup.Lookups;
import org.sil.jflavourapi.JFlavourProjectBean;

/**
 *
 * @author toby
 */
public class ProjectNode extends AbstractNode
{
    
    private boolean isRoot;
    private JFlavourProjectBean project;
     
    public ProjectNode(JFlavourProjectBean project) {
        super (Children.create(new CategoryNodeFactory(project), true), Lookups.singleton(project));
        setDisplayName (project.getName());
        isRoot = false;
        this.project = project;
    }
    
    public ProjectNode() throws IOException {
        super (Children.create(new ProjectNodeFactory(), true));
        setDisplayName ("Projects");
        isRoot = true;
    }
    
    @Override
    public boolean canDestroy()
    {
        return true;
    }
    
    @Override
    public void destroy()
    {
        project.delete();
        ProjectNodeFactory.deleteSavedProject(project);
        Node parent = this.getParentNode();
        parent.getChildren().remove(new Node[]{this});
        ((ProjectNode)parent).refresh();
    }
    
    public boolean refresh()
    {
        if (isRoot) {
            try {
                setChildren(Children.create(new ProjectNodeFactory(), true));
            } catch (IOException ex) {
                Exceptions.printStackTrace(ex);
                return false;
            }
        } else {
            setChildren(Children.create(new CategoryNodeFactory(project), true));
        }
        return true;
    }
    
    @Override
    public Action[] getActions(boolean context)
    {
        if (!context && !isRoot) {
            return new Action[]
            {
                SystemAction.get( DeleteAction.class )
            };
        } else {
            return super.getActions(context);
        }
    }
}
