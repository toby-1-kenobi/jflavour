/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sil.jflavourprojectmanager;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.AbstractMap;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import javax.swing.DefaultListModel;
import org.openide.util.NbBundle;
import org.openide.windows.TopComponent;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;
import org.sil.jflavourapi.JFlavourProjectBean;
import org.jdom2.*;
import org.jdom2.filter.ElementFilter;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(dtd = "-//org.sil.jflavourprojectmanager//JFlavourProjectManager//EN",
autostore = false)
@TopComponent.Description(preferredID = "JFlavourProjectManagerTopComponent",
//iconBase="SET/PATH/TO/ICON/HERE", 
persistenceType = TopComponent.PERSISTENCE_ALWAYS)
@TopComponent.Registration(mode = "properties", openAtStartup = true)
@ActionID(category = "Window", id = "org.sil.jflavourprojectmanager.JFlavourProjectManagerTopComponent")
@ActionReference(path = "Menu/Window" /*, position = 333 */)
@TopComponent.OpenActionRegistration(displayName = "#CTL_JFlavourProjectManagerAction",
preferredID = "JFlavourProjectManagerTopComponent")
public final class JFlavourProjectManagerTopComponent extends TopComponent implements PropertyChangeListener
{

    public JFlavourProjectManagerTopComponent() throws IOException
    {
        initComponents();
        setName(NbBundle.getMessage(JFlavourProjectManagerTopComponent.class, "CTL_JFlavourProjectManagerTopComponent"));
        setDisplayName("Project Manager");
        setToolTipText("Manage all your JFlavour projects here");
        randGenerator = new Random();
        dataDirectory = getDataDirectory();
        Files.createDirectories(dataDirectory);
        projectsListModel = new DefaultListModel<ProjectListEntry>();
        projectList.setModel(projectsListModel);
        projectCache = new HashMap<Integer, JFlavourProjectBean>();
        lookupContent = new InstanceContent();
        associateLookup(new AbstractLookup(lookupContent));
        loadProjectIDs();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSplitPane1 = new javax.swing.JSplitPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        projectList = new javax.swing.JList();
        jPanel1 = new javax.swing.JPanel();
        tfProjectName = new javax.swing.JTextField();
        btnProjectExport = new javax.swing.JButton();
        btnImport = new javax.swing.JButton();
        btnNew = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();

        jSplitPane1.setDividerLocation(100);

        projectList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        projectList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                projectListValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(projectList);

        jSplitPane1.setLeftComponent(jScrollPane1);

        tfProjectName.setText(org.openide.util.NbBundle.getMessage(JFlavourProjectManagerTopComponent.class, "JFlavourProjectManagerTopComponent.tfProjectName.text")); // NOI18N
        tfProjectName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tfProjectNameKeyTyped(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(btnProjectExport, org.openide.util.NbBundle.getMessage(JFlavourProjectManagerTopComponent.class, "JFlavourProjectManagerTopComponent.btnProjectExport.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(btnImport, org.openide.util.NbBundle.getMessage(JFlavourProjectManagerTopComponent.class, "JFlavourProjectManagerTopComponent.btnImport.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(btnNew, org.openide.util.NbBundle.getMessage(JFlavourProjectManagerTopComponent.class, "JFlavourProjectManagerTopComponent.btnNew.text")); // NOI18N
        btnNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(btnDelete, org.openide.util.NbBundle.getMessage(JFlavourProjectManagerTopComponent.class, "JFlavourProjectManagerTopComponent.btnDelete.text")); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(btnDelete, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(tfProjectName, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)
                    .addComponent(btnProjectExport, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnImport, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnNew, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(202, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tfProjectName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnProjectExport)
                .addGap(18, 18, 18)
                .addComponent(btnImport)
                .addGap(18, 18, 18)
                .addComponent(btnNew)
                .addGap(18, 18, 18)
                .addComponent(btnDelete)
                .addContainerGap(68, Short.MAX_VALUE))
        );

        jSplitPane1.setRightComponent(jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

private void btnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewActionPerformed
    newProject();
}//GEN-LAST:event_btnNewActionPerformed

private void tfProjectNameKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfProjectNameKeyTyped
    currentProject.setName(tfProjectName.getText());
}//GEN-LAST:event_tfProjectNameKeyTyped

private void projectListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_projectListValueChanged
    int selectedIndex = projectList.getSelectedIndex();
    if (selectedIndex >= 0) { // if there's a selected project
        ProjectListEntry entry =  projectsListModel.get(selectedIndex);
        JFlavourProjectBean project;
        try {
            int id = entry.getKey().intValue();
            project = loadProject(id);
            setCurrentProject(project, id);
            tfProjectName.grabFocus();
        } catch (Exception x) {
            StackTraceElement[] stackTrace = x.getStackTrace();
            System.err.format("Load project Exception: %s %s:%d%n", x, stackTrace[0].getFileName(), stackTrace[0].getLineNumber());
            return;
        }
    }
}//GEN-LAST:event_projectListValueChanged

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnImport;
    private javax.swing.JButton btnNew;
    private javax.swing.JButton btnProjectExport;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JList projectList;
    private javax.swing.JTextField tfProjectName;
    // End of variables declaration//GEN-END:variables
    private DefaultListModel<ProjectListEntry> projectsListModel;
    private JFlavourProjectBean currentProject;
    private int currentProjectId;
    private Map<Integer, JFlavourProjectBean> projectCache;
    private InstanceContent lookupContent;
    private Random randGenerator;
    private Path dataDirectory;
    private final String PROJECT_ID_FILENAME = "projects.xml";
    
    private final String XML_PROJECT_LIST = "jFlavourProjectList";
    private final String XML_PROJECT = "jFlavourProject";
    private final String XML_PROJECT_NAME = "jFlavourProjectName";
    private final String XML_PROJECT_ID = "jFlavourProjectID";
    
    private final String PROJECT_FILE_EXT = "jfp";
    
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
    
    private void newProject()
    {
        // make a new project
        JFlavourProjectBean project = new JFlavourProjectBean();
        project.setName("New Project");
        int projectID = getNewProjectID();
        // put the project into the list of available projects and select it
        projectsListModel.addElement(new ProjectListEntry(projectID, project.getName()));
        projectList.setSelectedIndex(projectsListModel.getSize()- 1);
        // also save the new list of projects
        saveProjectIDs();
        // put the project in the cache so we wont need to load it from file again
        projectCache.put(projectID, project);
        setCurrentProject(project, projectID);
        tfProjectName.grabFocus();
        // save it to disk
        saveProject(currentProject, projectID);
    }
    
    private void saveProject(JFlavourProjectBean project, int projectID)
    {
        Document projectDoc = new Document(currentProject.toDomElement());
        XMLOutputter xout = new XMLOutputter(Format.getPrettyFormat());
        Path projectPath = dataDirectory.resolve(Integer.toString(projectID) + '.' + PROJECT_FILE_EXT);
        try {
            BufferedWriter writer = Files.newBufferedWriter(projectPath, Charset.forName("UTF-8"));
            xout.output(projectDoc, writer);
        } catch (IOException x) {
            System.err.format(" Save project IOException: %s%n", x);
        }
    }
    
    private JFlavourProjectBean loadProject(int id) throws JDOMException, IOException
    {
        if (projectCache.containsKey(id)) {
            return projectCache.get(id);
        } else {
            Path projectPath = dataDirectory.resolve(Integer.toString(id) + '.' + PROJECT_FILE_EXT);
            if(Files.isRegularFile(projectPath) && Files.isReadable(projectPath)) {
                SAXBuilder builder = new SAXBuilder();
                Document projectDoc = builder.build(projectPath.toFile());
                Element root = projectDoc.getContent(new ElementFilter()).get(0);
                JFlavourProjectBean project = new JFlavourProjectBean(root);
                projectCache.put(id, project);
                return project;
            }
            else {
                throw new IOException("could not read JFlavour project at " + projectPath);
            }
        }
    }
    
    private void saveProjectIDs()
    {
        Element root = new Element(XML_PROJECT_LIST);
        Document projectIdDoc = new Document(root);
        for (Enumeration<ProjectListEntry> projectEnum = projectsListModel.elements(); projectEnum.hasMoreElements();) {
            ProjectListEntry proj = projectEnum.nextElement();
            Element projectElem = new Element(XML_PROJECT);
            projectElem.addContent(new Element(XML_PROJECT_ID).addContent(proj.getKey().toString()));
            projectElem.addContent(new Element(XML_PROJECT_NAME).addContent(proj.getValue()));
            root.addContent(projectElem);
        }
        XMLOutputter xout = new XMLOutputter(Format.getPrettyFormat());
        Path projectIdPath = dataDirectory.resolve(PROJECT_ID_FILENAME);
        try {
            BufferedWriter writer = Files.newBufferedWriter(projectIdPath, Charset.forName("UTF-8"));
            xout.output(projectIdDoc, writer);
        } catch (IOException x) {
            System.err.format("Save project IDs IOException: %s%n", x);
        }
    }
    
    private void loadProjectIDs()
    {
        Path projectIdPath = dataDirectory.resolve(PROJECT_ID_FILENAME);
        if(Files.isRegularFile(projectIdPath) && Files.isReadable(projectIdPath)) {
            try {
                SAXBuilder builder = new SAXBuilder();
                Document projects = builder.build(projectIdPath.toFile());
                Element root = projects.getContent(new ElementFilter()).get(0);
                List<Element> allProjects = root.getChildren(XML_PROJECT);
                for (Iterator<Element> it = allProjects.iterator(); it.hasNext();) {
                    Element projectNode = it.next();
                    int id = Integer.parseInt(projectNode.getChild(XML_PROJECT_ID).getText());
                    String name = projectNode.getChild(XML_PROJECT_NAME).getText();
                    projectsListModel.addElement(new ProjectListEntry(id, name));
                }
            } catch(JDOMException x) {
                System.err.format("Load project IDs JDOMException: %s%n", x);
            } catch(NullPointerException x) {
                System.err.format("Load project IDs NullPointerException: %s%n", x);
            } catch(IOException x) {
                System.err.format("Load project IDs IOException: %s%n", x);
            }
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent arg0)
    {
        if (arg0.getPropertyName().equals(JFlavourProjectBean.PROP_NAME)) {
            // the name has changed for the project listened to
            int selectedIndex = projectList.getSelectedIndex();
            if (selectedIndex >= 0) { // if there's a selected project
                // set the text field to the projects new name
                tfProjectName.setText(currentProject.getName());
                // replace the old with the new in the project list
                projectsListModel.get(selectedIndex).setValue(currentProject.getName());
            }
            saveProjectIDs();
        }
        saveProject(currentProject, currentProjectId);
    }
    
    private int getNewProjectID()
    {
        int newID = randGenerator.nextInt(100);
        Set<Integer> existingIDs = new HashSet<Integer>();
        for (Enumeration<ProjectListEntry> projectEnum = projectsListModel.elements(); projectEnum.hasMoreElements();) {
            existingIDs.add(projectEnum.nextElement().getKey());
        }
        while (existingIDs.contains(new Integer(newID))) {            
            ++newID;
        }
        return newID;
    }
    
    private Path getDataDirectory()
    {
        Path directory;
        String os = (System.getProperty("os.name")).toUpperCase();
        if (os.contains("WIN")) {
            directory = Paths.get(System.getenv("AppData"), "JFlavour");
        }
        else if (os.contains("LINUX")) {
            directory = Paths.get(System.getProperty("user.home"), ".local", "share", "JFlavour");
        }
        else if (os.contains("MAC")) {
            directory = Paths.get(System.getProperty("user.home"), "Library", "Application Support", "JFlavour");
        }
        else {
            directory = Paths.get(System.getProperty("user.home"), "/.JFlavour");
        }
        return directory;
    }
    
    private void setCurrentProject(JFlavourProjectBean project, int projectID)
    {
        // remove the current project from the lookup
        if (currentProject != null) {
            lookupContent.remove(currentProject);
            currentProject.removePropertyChangeListener(this);
        }
        // this top component listens for changes in the project's properties
        project.addPropertyChangeListener(this);
        // let other modules see that this is the current project
        lookupContent.add(project);
        currentProject = project;
        currentProjectId = projectID;
        // set the text field to the projects new name
        tfProjectName.setText(currentProject.getName());
    }
    
    private class ProjectListEntry extends AbstractMap.SimpleEntry<Integer, String>
    {
        
        public ProjectListEntry(Integer key, String value)
        {
            super(key, value);
        }
        
        @Override
        public String toString()
        {
            return this.getValue();
        }
    }
}
