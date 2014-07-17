/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sil.jflavourapi;

import java.beans.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author toby
 */
public class JFlavourItemBean implements Serializable
{
    
    public static final String PROP_LABEL = "label";
    private String label;
    private List<String> categories;
    private List<String> audioFilePaths;
    private List<String> imageFilePaths;
    private PropertyChangeSupport propertySupport;
    
    public JFlavourItemBean()
    {
        label = "";
        categories = new ArrayList<String>(5);
        audioFilePaths = new ArrayList<String>(5);
        imageFilePaths = new ArrayList<String>(5);
        propertySupport = new PropertyChangeSupport(this);
    }
    
    public String getLabel()
    {
        return label;
    }
    
    public void setLabel(String value)
    {
        String oldValue = label;
        label = value;
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
    }
    
    public void setCategories(int index, String category)
    {
        audioFilePaths.set(index, category);
    }

    /**
     * @return the audioFilePaths
     */
    public List<String> getAudioFilePaths()
    {
        return audioFilePaths;
    }
    
    public String getAudioFilePaths(int index)
    {
        return audioFilePaths.get(index);
    }

    /**
     * @param audioFilePaths the audioFilePaths to set
     */
    public void setAudioFilePaths(List<String> audioFilePaths)
    {
        this.audioFilePaths = audioFilePaths;
    }
    
    public void setAudioFilePaths(int index, String path)
    {
        audioFilePaths.set(index, path);
    }

    /**
     * @return the imageFilePaths
     */
    public List<String> getImageFilePaths()
    {
        return imageFilePaths;
    }
    
    public String getImageFilePaths(int index)
    {
        return imageFilePaths.get(index);
    }

    /**
     * @param imageFilePaths the imageFilePaths to set
     */
    public void setImageFilePaths(List<String> imageFilePaths)
    {
        this.imageFilePaths = imageFilePaths;
    }
    
    public void setImageFilePaths(int index, String path)
    {
        imageFilePaths.set(index, path);
    }
    
    public void playAudio(int index)
    {
    }
}
