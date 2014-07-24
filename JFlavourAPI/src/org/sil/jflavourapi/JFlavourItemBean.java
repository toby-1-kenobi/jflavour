/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sil.jflavourapi;

import java.beans.*;
import java.io.Serializable;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.jdom2.Element;
import org.jdom2.filter.ElementFilter;

/**
 *
 * @author toby
 */
public class JFlavourItemBean implements Serializable
{
    
    public static final String PROP_LABEL = "label";
    public static final String PROP_DIRTY = "dirty";
    private String label;
    private List<String> categories;
    private List<Path> audioFilePaths;
    private List<Path> imageFilePaths;
    private boolean dirty;
    private PropertyChangeSupport propertySupport;
    
    private final String XML_ITEM = "jFlavourItem";
    private final String XML_LABEL = "itemLabel";
    private final String XML_CATEGORY = "itemCategory";
    private final String XML_AUDIO = "itemAudio";
    private final String XML_IMAGE = "itemImage";
    private final String XML_PATH = "path";
    
    public JFlavourItemBean()
    {
        label = "";
        categories = new ArrayList<String>(5);
        audioFilePaths = new ArrayList<Path>(5);
        imageFilePaths = new ArrayList<Path>(5);
        dirty = false;
        propertySupport = new PropertyChangeSupport(this);
    }
    
    public JFlavourItemBean(Element domElement)
    {
        this();
        label = domElement.getChildText(XML_LABEL);
        Element categories = domElement.getChild(XML_CATEGORY);
        for (Iterator<Element> it = categories.getDescendants(new ElementFilter(XML_PATH)); it.hasNext();) {
            this.categories.add(it.next().getText());          
        }
        Element audio = domElement.getChild(XML_AUDIO);
        for (Iterator<Element> it = categories.getDescendants(new ElementFilter(XML_PATH)); it.hasNext();) {
            this.audioFilePaths.add(Paths.get(it.next().getText()));          
        }
        Element images = domElement.getChild(XML_IMAGE);
        for (Iterator<Element> it = categories.getDescendants(new ElementFilter(XML_PATH)); it.hasNext();) {
            this.imageFilePaths.add(Paths.get(it.next().getText()));          
        }
        
    }
    
    public String getLabel()
    {
        return label;
    }
    
    public void setLabel(String value)
    {
        String oldValue = label;
        label = value;
        setDirty(true);
        propertySupport.firePropertyChange(PROP_LABEL, oldValue, label);
    }
    
    public void addPropertyChangeListener(PropertyChangeListener listener)
    {
        propertySupport.addPropertyChangeListener(listener);
    }
    
    public void removePropertyChangeListener(PropertyChangeListener listener)
    {
        propertySupport.removePropertyChangeListener(listener);
    }

    /**
     * @return the categories
     */
    public List<String> getCategories()
    {
        return categories;
    }
    
    public String getCategories(int index)
    {
        return categories.get(index);
    }

    /**
     * @param categories the categories to set
     */
    public void setCategories(List<String> categories)
    {
        this.categories = categories;
        setDirty(true);
    }
    
    public void setCategories(int index, String category)
    {
        this.categories.set(index, category);
        setDirty(true);
    }
    
    public void addCategory(String category)
    {
        this.categories.add(category);
        setDirty(true);
    }
    
    public void removeCategory(int index)
    {
        this.categories.remove(index);
        setDirty(true);
    }

    /**
     * @return the audioFilePaths
     */
    public List<Path> getAudioFilePaths()
    {
        return audioFilePaths;
    }
    
    public Path getAudioFilePaths(int index)
    {
        return audioFilePaths.get(index);
    }

    /**
     * @param audioFilePaths the audioFilePaths to set
     */
    public void setAudioFilePaths(List<Path> audioFilePaths)
    {
        this.audioFilePaths = audioFilePaths;
        setDirty(true);
    }
    
    public void setAudioFilePaths(int index, Path path)
    {
        audioFilePaths.set(index, path);
        setDirty(true);
    }

    /**
     * @return the imageFilePaths
     */
    public List<Path> getImageFilePaths()
    {
        return imageFilePaths;
    }
    
    public Path getImageFilePaths(int index)
    {
        return imageFilePaths.get(index);
    }

    /**
     * @param imageFilePaths the imageFilePaths to set
     */
    public void setImageFilePaths(List<Path> imageFilePaths)
    {
        this.imageFilePaths = imageFilePaths;
        setDirty(true);
    }
    
    public void setImageFilePaths(int index, Path path)
    {
        imageFilePaths.set(index, path);
        setDirty(true);
    }

    /**
     * @return the dirty
     */
    public boolean getDirty()
    {
        return dirty;
    }

    /**
     * @param dirty the dirty to set
     */
    public void setDirty(boolean dirty)
    {
        if (this.dirty != dirty) {
            this.dirty = dirty;
            propertySupport.firePropertyChange(PROP_DIRTY, new Boolean(!dirty), new Boolean(dirty));
        }
    }
    
    public boolean isDirty()
    {
        return dirty;
    }
    
    public void playAudio(int index)
    {
    }
    
    public Element toDomElement()
    {
        Element itemElement = new Element(XML_ITEM);
        itemElement.addContent(new Element(XML_LABEL).addContent(label));
        Element categoryList = new Element(XML_CATEGORY);
        for (Iterator<String> it = categories.iterator(); it.hasNext();) {
            categoryList.addContent(new Element(XML_PATH).addContent(it.next()));
        }
        itemElement.addContent(categoryList);
        Element audioList = new Element(XML_CATEGORY);
        for (Iterator<Path> it = audioFilePaths.iterator(); it.hasNext();) {
            audioList.addContent(new Element(XML_PATH).addContent(it.next().toString()));
        }
        itemElement.addContent(audioList);
        Element imageList = new Element(XML_CATEGORY);
        for (Iterator<Path> it = imageFilePaths.iterator(); it.hasNext();) {
            imageList.addContent(new Element(XML_PATH).addContent(it.next().toString()));
        }
        itemElement.addContent(imageList);
        return itemElement;
    }
}
