/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.sil.jflavournodeprojectmanager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.filter.ElementFilter;
import org.jdom2.input.SAXBuilder;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.util.lookup.Lookups;
import org.sil.jflavourapi.JFlavourPathManager;
import org.sil.jflavourapi.JFlavourProjectBean;

/**
 * A factory to create project nodes.
 * It needs to read the project data from an XML file
 * @author toby
 */
public class ProjectChildFactory extends ChildFactory<JFlavourProjectBean>
{
    
    private Path dataDirectory;
    private final String PROJECT_ID_FILENAME = "projects.xml";
    private final String XML_PROJECT_LIST = "jFlavourProjectList";
    private final String XML_PROJECT = "jFlavourProject";
    private final String XML_PROJECT_NAME = "jFlavourProjectName";
    private final String XML_PROJECT_ID = "jFlavourProjectID";
    private final String PROJECT_FILE_EXT = "jfp";
    
    public ProjectChildFactory() throws IOException
    {
        super();
        dataDirectory = JFlavourPathManager.getDataDirectory();
        Files.createDirectories(dataDirectory);
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
                    if(Files.isRegularFile(projectPath) && Files.isReadable(projectPath)) {
                        Document projectDoc = builder.build(projectPath.toFile());
                        Element projectRoot = projectDoc.getContent(new ElementFilter()).get(0);
                        JFlavourProjectBean project = new JFlavourProjectBean(projectRoot);
                        list.add(project);
                    }
                    else {
                        throw new IOException("could not read JFlavour project at " + projectPath);
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
