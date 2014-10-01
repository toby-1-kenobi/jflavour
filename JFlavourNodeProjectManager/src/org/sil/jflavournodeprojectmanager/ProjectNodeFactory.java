/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.sil.jflavournodeprojectmanager;

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
public class ProjectNodeFactory extends ChildFactory<JFlavourProjectBean>
{
    
    private static Path dataDirectory;
    public static final String PROJECT_ID_FILENAME = "projects.xml";
    public static final String XML_PROJECT_LIST = "jFlavourProjectList";
    public static final String XML_PROJECT = "jFlavourProject";
    public static final String XML_PROJECT_NAME = "jFlavourProjectName";
    public static final String XML_PROJECT_ID = "jFlavourProjectID";
    public static final String PROJECT_FILE_EXT = "jfp";
    
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
                    String name = projectNode.getChild(XML_PROJECT_NAME).getText();
                    Path projectPath = dataDirectory.resolve(id.toString() + '.' + PROJECT_FILE_EXT);
                    if (projectCache.containsKey(id)) {
                        list.add(projectCache.get(id));
                    }
                    else if(Files.isRegularFile(projectPath) && Files.isReadable(projectPath)) {
                        Document projectDoc = builder.build(projectPath.toFile());
                        Element projectRoot = projectDoc.getContent(new ElementFilter()).get(0);
                        JFlavourProjectBean project = new JFlavourProjectBean(projectRoot);
                        projectCache.put(id, project);
                        list.add(project);
                    }
                    else {
                        throw new IOException("could not read JFlavour project " + name + " from " + projectPath);
                    }
                }
            } catch(JDOMException x) {
                System.err.format("Load project IDs JDOMException: %s%n", x);
            } catch(NullPointerException x) {
                System.err.format("Load project IDs NullPointerException: %s%n", x);
            } catch(IOException x) {
                System.err.format("Load project IDs IOException: %s%n", x);
            }
        }
        return true;
    }
    
    @Override
    protected Node createNodeForKey(JFlavourProjectBean key) {
        return new ProjectNode(key);
    }
    
}
