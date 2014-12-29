/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sil.jflavournodeprojectmanager;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import javax.swing.ActionMap;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.explorer.ExplorerManager;
import org.openide.explorer.ExplorerUtils;
import org.openide.explorer.view.BeanTreeView;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;
import org.openide.windows.TopComponent;
import org.openide.util.NbBundle.Messages;
import org.sil.jflavourapi.JFlavourProjectBean;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(
        dtd = "-//org.sil.jflavournodeprojectmanager//JFlavourNodeProjectManager//EN",
        autostore = false
)
@TopComponent.Description(
        preferredID = "JFlavourNodeProjectManagerTopComponent",
        //iconBase="SET/PATH/TO/ICON/HERE", 
        persistenceType = TopComponent.PERSISTENCE_ALWAYS
)
@TopComponent.Registration(mode = "explorer", openAtStartup = true)
@ActionID(category = "Window", id = "org.sil.jflavournodeprojectmanager.JFlavourNodeProjectManagerTopComponent")
@ActionReference(path = "Menu/Window" /*, position = 333 */)
@TopComponent.OpenActionRegistration(
        displayName = "#CTL_JFlavourNodeProjectManagerAction",
        preferredID = "JFlavourNodeProjectManagerTopComponent"
)
@Messages({
    "CTL_JFlavourNodeProjectManagerAction=JFlavourNodeProjectManager",
    "CTL_JFlavourNodeProjectManagerTopComponent=JFlavourNodeProjectManager Window",
    "HINT_JFlavourNodeProjectManagerTopComponent=This is a JFlavourNodeProjectManager window"
})
public final class JFlavourNodeProjectManagerTopComponent extends TopComponent implements ExplorerManager.Provider, ActionListener, PropertyChangeListener
{
    
    private JButton btnNewProject;
    private ProjectNode root;
    
    public JFlavourNodeProjectManagerTopComponent()
    {
        initComponents();
        setName(Bundle.CTL_JFlavourNodeProjectManagerTopComponent());
        setToolTipText(Bundle.HINT_JFlavourNodeProjectManagerTopComponent());

        setLayout(new BorderLayout());
        add(new BeanTreeView(), BorderLayout.CENTER);
        
        btnNewProject = new JButton("New Project");
        btnNewProject.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                createNewProject();
            }
        });
        add(btnNewProject, BorderLayout.NORTH);
        
        ActionMap actionMap = this.getActionMap();
        actionMap.put("delete", new ProjectDeleteAction());
        associateLookup(ExplorerUtils.createLookup(explorerManager, actionMap));
        
        try {
            root = new ProjectNode();
            explorerManager.setRootContext(root);
        } catch (IOException ex) {
            Node noProjects = new AbstractNode(Children.LEAF);
            noProjects.setDisplayName("No Projects");
            noProjects.setShortDescription("No projects could be loaded.");
            explorerManager.setRootContext(noProjects);
            Exceptions.printStackTrace(ex);
            root = null;
        }
        explorerManager.addPropertyChangeListener(this);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    private void createNewProject()
    {
        String name = JOptionPane.showInputDialog(this, "What is the project's name?", "New Project Name", JOptionPane.QUESTION_MESSAGE);
        if (name != null)
        {
            JFlavourProjectBean project = new JFlavourProjectBean();
            project.setName(name);
            project.addActionListener(this);
            ProjectNodeFactory.addToCache(project);
            ProjectNodeFactory.saveProject(project);
            ProjectNodeFactory.writeCache();
            root.refresh();
        }
    }
    
    @Override
    public void componentOpened()
    {
        // TODO add custom code on component opening
    }

    @Override
    public void componentClosed()
    {
        // TODO add custom code on component closing
    }

    void writeProperties(java.util.Properties p)
    {
        // better to version settings since initial version as advocated at
        // http://wiki.apidesign.org/wiki/PropertyFiles
        p.setProperty("version", "1.0");
        // TODO store your settings
    }

    void readProperties(java.util.Properties p)
    {
        String version = p.getProperty("version");
        // TODO read your settings according to their version
    }
    
    private final ExplorerManager explorerManager = new ExplorerManager();

    @Override
    public ExplorerManager getExplorerManager()
    {
        return explorerManager;
    }
    
    @Override
    public void actionPerformed(ActionEvent ae)
    {
        String projectId = ae.getActionCommand();
        ProjectNodeFactory.saveProject(UUID.fromString(projectId));
    }

    @Override
    public void propertyChange(PropertyChangeEvent pce)
    {
        if (pce.getPropertyName() == ExplorerManager.PROP_SELECTED_NODES)
        {
            // check that the project parent of any selected node is also selected
            // and that only one project is selected
            Node[] selected = (Node[])pce.getNewValue();
            Set<ProjectNode> projectParents = projectsWithSelectedChildren(Arrays.asList(selected));
            if (projectParents.size() > 1)
            {
                // nodes under more than one project are selected
                // so keep the most recent selected
                List oldSelected = Arrays.asList((Node[])pce.getOldValue());
                // get the difference - the nodes just selected
                List<Node> newlySelected = new ArrayList<Node>(1);
                for (int i = 0; i < selected.length; i++) {
                    Node node = selected[i];
                    if (!oldSelected.contains(node)) newlySelected.add(node);
                }
                projectParents = projectsWithSelectedChildren(newlySelected);
                // if there's still more than one project we'll just take the first one
            }
            ProjectNode activeProject = (ProjectNode)projectParents.toArray()[0];
            Set<Node> forSelection = new HashSet<Node>(selected.length);
            forSelection.add(activeProject);
            for (int i = 0; i < selected.length; i++) {
                if (getProjectParent(selected[i]) == activeProject) {
                    forSelection.add(selected[i]);
                }
            }
            try {
                // set the selected nodes
                Node[] forSelectionA = new Node[forSelection.size()];
                explorerManager.setSelectedNodes(forSelection.toArray(forSelectionA));
            } catch (PropertyVetoException ex) {
                Exceptions.printStackTrace(ex);
            }
        }
    }
    
    private Set<ProjectNode> projectsWithSelectedChildren(List<Node> selectedNodes)
    {
        Set<ProjectNode> projectParents = new HashSet<ProjectNode>(2);
        for (Node node : selectedNodes) {
            ProjectNode pNode = getProjectParent(node);
            if (!pNode.isRoot()) projectParents.add(pNode);
        }
        return projectParents;
    }
    
    private ProjectNode getProjectParent(Node node)
    {
        while (!(node instanceof ProjectNode)) node = node.getParentNode();
        return (ProjectNode)node;
    }
    
    public void refreshTree()
    {
        root.refresh();
    }
}
