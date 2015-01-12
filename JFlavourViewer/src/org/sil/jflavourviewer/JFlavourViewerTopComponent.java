/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sil.jflavourviewer;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.swing.ActionMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.openide.filesystems.FileObject;
import org.openide.util.LookupEvent;
import org.openide.util.NbBundle;
import org.openide.windows.TopComponent;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.explorer.ExplorerManager;
import org.openide.explorer.ExplorerUtils;
import org.openide.explorer.view.IconView;
import org.openide.filesystems.FileChangeAdapter;
import org.openide.filesystems.FileEvent;
import org.openide.filesystems.FileUtil;
import org.openide.loaders.DataFolder;
import org.openide.loaders.DataObject;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.util.Lookup;
import org.openide.util.LookupListener;
import org.openide.util.Utilities;
import org.openide.windows.WindowManager;
import org.sil.jflavourapi.CentralLookup;
import org.sil.jflavourapi.JFlavourItemBean;
import org.sil.jflavourapi.JFlavourProjectBean;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(dtd = "-//org.sil.jflavourviewer//JFlavourViewer//EN",
autostore = false)
@TopComponent.Description(preferredID = "JFlavourViewerTopComponent",
//iconBase="SET/PATH/TO/ICON/HERE", 
persistenceType = TopComponent.PERSISTENCE_ALWAYS)
@TopComponent.Registration(mode = "editor", openAtStartup = true)
@ActionID(category = "Window", id = "org.sil.jflavourviewer.JFlavourViewerTopComponent")
@ActionReference(path = "Menu/Window" /*, position = 333 */)
@TopComponent.OpenActionRegistration(displayName = "#CTL_JFlavourViewerAction",
preferredID = "JFlavourViewerTopComponent")
public final class JFlavourViewerTopComponent extends TopComponent implements LookupListener, PropertyChangeListener, ExplorerManager.Provider
{

    public JFlavourViewerTopComponent()
    {
        initComponents();
        setLayout(new BorderLayout());
        labelActiveProject = new JLabel("No active project");
        panelTools = new JPanel();
        panelTools.setLayout(new FlowLayout());
        add(labelActiveProject, BorderLayout.NORTH);
        add(panelTools, BorderLayout.SOUTH);
        add(new IconView(), BorderLayout.CENTER);
        
        systemFsTools = FileUtil.getConfigFile(TOOLS_PATH);
        setName(NbBundle.getMessage(JFlavourViewerTopComponent.class, "CTL_JFlavourViewerTopComponent"));
        setDisplayName("Project Viewer");
        setToolTipText("Here are the items in the active project");
        
        // add listener to monitor change in the System FileSystem
        systemFsTools.addFileChangeListener(new FileChangeAdapter() {
            // Only interested when other modules create or delete files in our directory
            // then we just rebuild the tools control panel
            @Override
            public void fileDataCreated(FileEvent fe) {initControlPanel();}
            @Override
            public void fileDeleted(FileEvent fe) {initControlPanel();}
        });
        
        ActionMap actionMap = getActionMap();
        actionMap.put("delete", new ItemDeleteAction());
        associateLookup(ExplorerUtils.createLookup(explorerManager, actionMap));
        explorerManager.addPropertyChangeListener(this);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
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
            .addGap(0, 346, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    /**
     * This method is reads a directory in the SystemFilesytem to build the
     * buttons for a control panel that depends on unknown modules
     */
    private void initControlPanel()
    {
        // start fresh each time we build this
        panelTools.removeAll();
        selectionDependantTools.clear();
        projectDependantTools.clear();
        // go to the folder where we can find the info for the buttons we're making
        DataFolder toolsFolder = DataFolder.findFolder(systemFsTools);
        DataObject[] children = toolsFolder.getChildren();
        // we're assuming each child in the folder represents one button
        for (DataObject child : children) {
            FileObject file = child.getPrimaryFile();
            // get the atributes that tell us what the button will be like
            Object actionMethodName = file.getAttribute(ATTR_NAME_BUTTON_ACTION_NAME);
            Object btnText = file.getAttribute(ATTR_NAME_BUTTON_NAME);
            Object btnAlwaysActive = file.getAttribute(ATTR_NAME_BUTTON_BOOL_PROPERTY);
            JButton btn = new JButton();
            if (btnText != null) {
                btn.setText((String)btnText);
            }
            if (actionMethodName != null) {
                final String identifier = (String)actionMethodName;
                btn.addActionListener(new java.awt.event.ActionListener()
                    {
                        @Override
                        public void actionPerformed(ActionEvent e)
                        {
                            // collect a container of selected items
                            Node[] selected = explorerManager.getSelectedNodes();
                            List<JFlavourItemBean> selectedItems = new ArrayList<JFlavourItemBean>(selected.length);
                            for (int i = 0; i < selected.length; i++) {
                                selectedItems.add(((ViewerItemNode)selected[i]).getItem());
                            }
                            // Add a ToolEvent to the Central Lookup
                            CentralLookup.getDefault().add(new ToolEvent(e, identifier, activeProject, selectedItems));
                        }
                    });
            } else {
                btn.setEnabled(false);
            }
            if (btnAlwaysActive != null) {
                // keep track of the buttons that need to be disabled or enabled
                if (!((Boolean)btnAlwaysActive))
                {
                    selectionDependantTools.add(btn);
                    Node[] selected = explorerManager.getSelectedNodes();
                    if (selected.length > 0) btn.setEnabled(true);
                    else btn.setEnabled(false);
                } else {
                    // otherwise it's a project dependant tool
                    projectDependantTools.add(btn);
                    if (activeProject == null) btn.setEnabled(false);
                }
            }
            panelTools.add(btn);
        }
        panelTools.revalidate();
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    private JPanel panelTools;
    private JLabel labelActiveProject;
    
    private JFlavourProjectBean activeProject;
    
    private Lookup.Result<JFlavourProjectBean> projectResult = null;
    private FileObject systemFsTools;
    
    // keep a list of components that can only be used when items are selected
    // that way we can enable and disbale them as needed
    private List<JComponent> selectionDependantTools = new ArrayList<JComponent>();
    // also a list of components that can only be used when a project is selected
    private List<JComponent> projectDependantTools = new ArrayList<JComponent>();
    
    // Todo: put these in a seperate package that can be public API
    public static final String TOOLS_PATH = "UI/JFlavourTools";
    public static final String ATTR_NAME_BUTTON_NAME = "text";
    public static final String ATTR_NAME_BUTTON_ACTION = "action";
    public static final String ATTR_NAME_BUTTON_ACTION_NAME = "actionName";
    public static final String ATTR_NAME_BUTTON_BOOL_PROPERTY = "alwaysEnabled";
    
    @Override
    public void componentOpened()
    {
        projectResult = Utilities.actionsGlobalContext().lookupResult(JFlavourProjectBean.class);
        projectResult.addLookupListener (this);
        initControlPanel();
    }

    @Override
    public void componentClosed()
    {
        projectResult.removeLookupListener(this);
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

    @Override
    public void resultChanged(LookupEvent le)
    {
        Collection<? extends JFlavourProjectBean> allProjects = projectResult.allInstances();
        if (!allProjects.isEmpty()) {
            if (activeProject != null) activeProject.removePropertyChangeListener(this);
            activeProject = allProjects.iterator().next();
            activeProject.addPropertyChangeListener(this);
            labelActiveProject.setText(activeProject.getName());
            for (JComponent tool : projectDependantTools) {
                tool.setEnabled(true);
            }
            explorerManager.setRootContext(new AbstractNode(Children.create(new ViewerItemNodeFactory(activeProject, WindowManager.getDefault().findTopComponent("JFlavourNodeProjectManagerTopComponent")), true)));
            explorerManager.getRootContext().setDisplayName("Items from selected categories");
        } else {
            // do nothing if no projects are in the lookup
        }
    }
    
    private final ExplorerManager explorerManager = new ExplorerManager();

    @Override
    public ExplorerManager getExplorerManager()
    {
        return explorerManager;
    }
    
    private void refreshIconView()
    {
        explorerManager.setRootContext(new AbstractNode(Children.create(new ViewerItemNodeFactory(activeProject, WindowManager.getDefault().findTopComponent("JFlavourNodeProjectManagerTopComponent")), true)));
    }

    @Override
    public void propertyChange(PropertyChangeEvent pce)
    {
        // if the active project gets "deleted" the viewer needs to set to null
        if (pce.getPropertyName().equals(JFlavourProjectBean.PROP_DELETED) && ((Boolean)pce.getNewValue())) {
            activeProject.removePropertyChangeListener(this);
            activeProject = null;
            labelActiveProject.setText("no project selected");
            for (JComponent tool : projectDependantTools) {
                tool.setEnabled(false);
            }
            explorerManager.setRootContext(new AbstractNode(Children.LEAF));
        }
        // if the selection of nodes for this component has changed
        if (pce.getPropertyName().equals(ExplorerManager.PROP_SELECTED_NODES))
        {
            Node[] selected = explorerManager.getSelectedNodes();
            if (selected.length > 0)
            {
                for (JComponent tool : selectionDependantTools) {
                    tool.setEnabled(true);
                }
            } else {
                for (JComponent tool : selectionDependantTools) {
                    tool.setEnabled(false);
                }
            }
            refreshIconView();
        }
        // if an item has been added to or deleted from the project then refresh the nodes
        if (pce.getPropertyName().equals(JFlavourProjectBean.PROP_ITEM) || pce.getPropertyName().equals(JFlavourProjectBean.PROP_ITEMS))
        {
            refreshIconView();
        }
    }
}
