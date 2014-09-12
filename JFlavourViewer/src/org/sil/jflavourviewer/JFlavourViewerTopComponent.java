/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sil.jflavourviewer;

import java.awt.FlowLayout;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import org.openide.filesystems.FileObject;
import org.openide.util.LookupEvent;
import org.openide.util.NbBundle;
import org.openide.windows.TopComponent;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.filesystems.FileChangeAdapter;
import org.openide.filesystems.FileEvent;
import org.openide.filesystems.FileUtil;
import org.openide.loaders.DataFolder;
import org.openide.loaders.DataObject;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;
import org.openide.util.LookupListener;
import org.openide.util.Utilities;
import org.sil.jflavourapi.JFlavourProjectBean;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(dtd = "-//org.sil.jflavourviewer//JFlavourViewer//EN",
autostore = false)
@TopComponent.Description(preferredID = "JFlavourViewerTopComponent",
//iconBase="SET/PATH/TO/ICON/HERE", 
persistenceType = TopComponent.PERSISTENCE_ALWAYS)
@TopComponent.Registration(mode = "explorer", openAtStartup = true)
@ActionID(category = "Window", id = "org.sil.jflavourviewer.JFlavourViewerTopComponent")
@ActionReference(path = "Menu/Window" /*, position = 333 */)
@TopComponent.OpenActionRegistration(displayName = "#CTL_JFlavourViewerAction",
preferredID = "JFlavourViewerTopComponent")
public final class JFlavourViewerTopComponent extends TopComponent implements LookupListener
{

    public JFlavourViewerTopComponent()
    {
        initComponents();
        panelTools.setLayout(new FlowLayout());
        systemFsTools = FileUtil.getConfigFile(TOOLS_PATH);
        initControlPanel();
        setName(NbBundle.getMessage(JFlavourViewerTopComponent.class, "CTL_JFlavourViewerTopComponent"));
        setDisplayName("Project Viewer");
        setToolTipText("Here are the items in the active project");
        categoriesListModel = new DefaultListModel<String>();
        categoryList.setModel(categoriesListModel);
        
        // add listener to monitor change in the System FileSystem
        systemFsTools.addFileChangeListener(new FileChangeAdapter() {
            // Only interested when other modules create or delete files in our directory
            // then we just rebuild the tools control panel
            @Override
            public void fileDataCreated(FileEvent fe) {initControlPanel();}
            @Override
            public void fileDeleted(FileEvent fe) {initControlPanel();}
        });
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        jSplitPane1 = new javax.swing.JSplitPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        categoryList = new javax.swing.JList();
        tmpLabel = new javax.swing.JLabel();
        panelTools = new javax.swing.JPanel();

        jSplitPane1.setDividerLocation(100);

        jScrollPane1.setViewportView(categoryList);

        jSplitPane1.setLeftComponent(jScrollPane1);

        org.openide.awt.Mnemonics.setLocalizedText(tmpLabel, org.openide.util.NbBundle.getMessage(JFlavourViewerTopComponent.class, "JFlavourViewerTopComponent.tmpLabel.text")); // NOI18N
        jSplitPane1.setRightComponent(tmpLabel);

        javax.swing.GroupLayout panelToolsLayout = new javax.swing.GroupLayout(panelTools);
        panelTools.setLayout(panelToolsLayout);
        panelToolsLayout.setHorizontalGroup(
            panelToolsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelToolsLayout.setVerticalGroup(
            panelToolsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
            .addComponent(panelTools, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jSplitPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(panelTools, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                // do the work of seperating the method name from the class name here
                String fullMethodName = ((String)actionMethodName);
                int seperator = fullMethodName.lastIndexOf('.');
                String className = fullMethodName.substring(0, seperator);
                String methodName = fullMethodName.substring(seperator + 1);
                final Method action;
                try {
                    action = Class.forName(className).getMethod(methodName);
                    // now we have the handler method we can attach it to the button with an action listener
                    btn.addActionListener(new java.awt.event.ActionListener()
                        {
                            @Override
                            public void actionPerformed(java.awt.event.ActionEvent evt)
                            {
                                try {
                                    action.invoke(null);
                                } catch (Exception ex) {
                                    Exceptions.printStackTrace(ex);  
                                }
                            }
                        });
                } catch (ClassNotFoundException ex) {
                    btn.setEnabled(false);
                    Exceptions.printStackTrace(ex);
                } catch (NoSuchMethodException ex) {
                    btn.setEnabled(false);
                    Exceptions.printStackTrace(ex);
                }
            } else {
                btn.setEnabled(false);
            }
            if (btnAlwaysActive != null) {
                // keep track of the buttons that need to be disabled or enabled
                if (!((Boolean)btnAlwaysActive).booleanValue())
                {
                    selectionDependantTools.add(btn);
                    btn.setEnabled(false);
                    //Todo: enable if selection exists
                }
            }
            panelTools.add(btn);
        }
        panelTools.revalidate();
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JList categoryList;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JPanel panelTools;
    private javax.swing.JLabel tmpLabel;
    // End of variables declaration//GEN-END:variables
    private Lookup.Result<JFlavourProjectBean> result = null;
    private DefaultListModel<String> categoriesListModel;
    private FileObject systemFsTools;
    
    // keep a list of components that can only be used when items are selected
    // that way we can enable and disbale them as needed
    private List<JComponent> selectionDependantTools = new ArrayList<JComponent>();
    
    // Todo: put these in a seperate package that can be public API
    public static final String TOOLS_PATH = "UI/JFlavourTools";
    public static final String ATTR_NAME_BUTTON_NAME = "text";
    public static final String ATTR_NAME_BUTTON_ACTION = "action";
    public static final String ATTR_NAME_BUTTON_ACTION_NAME = "actionName";
    public static final String ATTR_NAME_BUTTON_BOOL_PROPERTY = "alwaysEnabled";
    
    @Override
    public void componentOpened()
    {
        result = Utilities.actionsGlobalContext().lookupResult(JFlavourProjectBean.class);
        result.addLookupListener (this);
    }

    @Override
    public void componentClosed()
    {
        result.removeLookupListener(this);
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
        Collection<? extends JFlavourProjectBean> allProjects = result.allInstances();
        if (!allProjects.isEmpty()) {
            JFlavourProjectBean project = allProjects.iterator().next();
            SortedSet<String> categories = project.getCategories();
            categoriesListModel.clear();
            for (Iterator<String> it = categories.iterator(); it.hasNext();) {
                categoriesListModel.addElement(it.next());
            }
            tmpLabel.setText(project.getName());
        } else {
            // do nothing if no projects are in the lookup
        }
    }
}
