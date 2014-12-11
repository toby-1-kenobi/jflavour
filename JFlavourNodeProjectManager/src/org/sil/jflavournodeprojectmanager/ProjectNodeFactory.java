/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.sil.jflavournodeprojectmanager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.filter.ElementFilter;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Node;
import org.sil.jflavourapi.JFlavourPathManager;
import org.sil.jflavourapi.JFlavourProjectBean;

/**
 * A factory to create project nodes.
 * It needs to read the project data from an XML file
 * @author toby
 */
public class ProjectNodeFactory extends ChildFactory<JFlavourProjectBean> implements ActionListener
{
    
    private static Path dataDirectory;
    public static final String PROJECT_ID_FILENAME = "projects.xml";
    public static final String XML_PROJECT_LIST = "jFlavourProjectList";
    public static final String XML_PROJECT = "jFlavourProject";
    public static final String XML_PROJECT_NAME = "jFlavourProjectName";
    public static final String XML_PROJECT_ID = "jFlavourProjectID";
    
    private static Map<UUID, JFlavourProjectBean> projectCache = new HashMap<UUID, JFlavourProjectBean>();
    
    public ProjectNodeFactory() throws IOException
    {
        super();
        dataDirectory = JFlavourPathManager.getDataDirectory();
        Files.createDirectories(dataDirectory);
    }
    
    public static void addToCache(JFlavourProjectBean project)
    {
        projectCache.put(project.getId(), project);
    }
    
    public static JFlavourProjectBean getProject(UUID id)
    {
        if (projectCache.containsKey(id))
        {
            return projectCache.get(id);
        }
        else
        {
            try {
                // warning! If a project is loaded this way it doesn't yet have a listener to listen for changes
                // so autosave wont work for this project until a listener is added
                return loadProject(id);
            } catch (IOException ex) {
                System.err.format("Loading project - IOException: %s%n", ex);
                return null;
            } catch (JDOMException ex) {
                System.err.format("Loading project - JDOMException: %s%n", ex);
                return null;
            }
        }
    }
    
    /**
     * Write a file that lists the IDs and names of available projects.
     * The content of the projects are not saved in this file
     */
    public static void writeCache()
    {
        Element root = new Element(XML_PROJECT_LIST);
        Document projectIdDoc = new Document(root);
        for (Map.Entry<UUID, JFlavourProjectBean> projectCacheEntry : projectCache.entrySet()) {
            Element projectElem = new Element(XML_PROJECT);
            projectElem.addContent(new Element(XML_PROJECT_ID).addContent(projectCacheEntry.getKey().toString()));
            projectElem.addContent(new Element(XML_PROJECT_NAME).addContent(projectCacheEntry.getValue().getName()));
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

    @Override
    protected boolean createKeys(List<JFlavourProjectBean> list)
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
                    UUID id = UUID.fromString(projectNode.getChild(XML_PROJECT_ID).getText());
                    if (projectCache.containsKey(id)) {
                        // if we already have this project loaded get it from the cache
                        list.add(projectCache.get(id));
                    }
                    // otherwise read it from the file
                    else {
                        JFlavourProjectBean project = loadProject(id);
                        project.addActionListener(this);
                        projectCache.put(id, project);
                        list.add(project);
                    }
                }
            } catch(JDOMException x) {
                System.err.format("Loading project or project IDs - JDOMException: %s%n", x);
            } catch(NullPointerException x) {
                System.err.format("Loading project IDs - NullPointerException: %s%n", x);
            } catch(IOException x) {
                System.err.format("Loading project or project IDs - IOException: %s%n", x);
            }
        }
        return true;
    }
    
    private static JFlavourProjectBean loadProject(UUID id) throws IOException, JDOMException
    {
        // the content of the project is stored in a file named by the project id
        Path projectPath = JFlavourPathManager.getProjectFile(id);
        if(Files.isRegularFile(projectPath) && Files.isReadable(projectPath))
        {
            SAXBuilder builder = new SAXBuilder();
            Document projectDoc = builder.build(projectPath.toFile());
            Element projectRoot = projectDoc.getContent(new ElementFilter()).get(0);
            JFlavourProjectBean project = new JFlavourProjectBean(projectRoot);
            return project;
        }
        else
        {
            throw new IOException("could not read JFlavour project from " + projectPath);
        }
    }
    
    public static boolean saveProject(JFlavourProjectBean project)
    {
        Document projectDoc = new Document(project.toDomElement());
        XMLOutputter xout = new XMLOutputter(Format.getPrettyFormat());
        Path projectPath = JFlavourPathManager.getProjectFile(project.getId());
        try {
            BufferedWriter writer = Files.newBufferedWriter(projectPath, Charset.forName("UTF-8"));
            xout.output(projectDoc, writer);
            project.setDirty(false);
        } catch (IOException x) {
            System.err.format(" Save project IOException: %s%n", x);
            return false;
        }
        return true;
    }
    
    public static boolean deleteSavedProject(JFlavourProjectBean project)
    {
        Path projectPath = JFlavourPathManager.getProjectFile(project.getId());
        try {
            projectCache.remove(project.getId());
            writeCache();
            Files.deleteIfExists(projectPath);
            return true;
        } catch (IOException x) {
            System.err.format(" Delete project IOException: %s%n", x);
            return false;
        }
    }
    
    public static boolean saveProject(UUID projectID)
    {
        if (projectCache.containsKey(projectID))
        {
            return saveProject(projectCache.get(projectID));
        }
        else
        {
            System.err.print("attempting to save project not loaded: " + projectID.toString());
            return false;
        }
    }
    
    @Override
    protected Node createNodeForKey(JFlavourProjectBean key) {
        return new ProjectNode(key);
    }

    @Override
    public void actionPerformed(ActionEvent ae)
    {
        String projectId = ae.getActionCommand();
        ProjectNodeFactory.saveProject(UUID.fromString(projectId));
    }
    
}
