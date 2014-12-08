/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sil.jflavourapi;

import java.beans.*;
import java.io.IOException;
import java.io.Serializable;
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
    private List<Category> categories;
    private List<ItemAudio> audio;
    private int defaultAudioIndex;
    private List<ItemImage> images;
    private int defaultImageIndex;
    private boolean dirty;
    private PropertyChangeSupport propertySupport;
    
    private final String XML_ITEM = "jFlavourItem";
    private final String XML_ITEM_LABEL = "itemLabel";
    private final String XML_CATEGORY = "itemCategory";
    private final String XML_AUDIO = "itemAudio";
    private final String XML_IMAGE = "itemImage";
    private final String XML_LABEL = "label";
    private final String XML_PATH = "path";
    private final String XML_DEFAULT_ATTR = "default";
    
    public JFlavourItemBean()
    {
        label = "";
        categories = new ArrayList<Category>(5);
        audio = new ArrayList<ItemAudio>(5);
        images = new ArrayList<ItemImage>(5);
        dirty = false;
        propertySupport = new PropertyChangeSupport(this);
    }
    
    public JFlavourItemBean(Element domElement)
    {
        this();
        label = domElement.getChildText(XML_ITEM_LABEL);
        Element categories = domElement.getChild(XML_CATEGORY);
        for (Iterator<Element> it = categories.getDescendants(new ElementFilter(XML_LABEL)); it.hasNext();) {
            this.categories.add(new Category(it.next().getText()));          
        }
        Element audio = domElement.getChild(XML_AUDIO);
        for (Iterator<Element> it = audio.getDescendants(new ElementFilter(XML_PATH)); it.hasNext();) {
            Element next = it.next();
            ItemAudio newAudio = new ItemAudio(Paths.get(next.getText()));
            this.audio.add(newAudio);  
            if (Boolean.parseBoolean(next.getAttributeValue(XML_DEFAULT_ATTR)))
            {
                setDefaultAudio(newAudio);
            }          
        }
        Element images = domElement.getChild(XML_IMAGE);
        for (Iterator<Element> it = images.getDescendants(new ElementFilter(XML_PATH)); it.hasNext();) {
            Element next = it.next();
            ItemImage newImage = new ItemImage(Paths.get(next.getText()));
            this.images.add(newImage);  
            if (Boolean.parseBoolean(next.getAttributeValue(XML_DEFAULT_ATTR)))
            {
                setDefaultImage(newImage);
            }
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
    public List<Category> getCategories()
    {
        return categories;
    }
    
    public Category getCategories(int index)
    {
        return categories.get(index);
    }

    /**
     * @param categories the categories to set
     */
    public void setCategories(List<Category> categories)
    {
        this.categories = categories;
        setDirty(true);
    }
    
    public void setCategories(int index, Category category)
    {
        this.categories.set(index, category);
        setDirty(true);
    }
    
    public void addCategory(Category category)
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
     * @return the audio
     */
    public List<ItemAudio> getAudio()
    {
        return audio;
    }
    
    public ItemAudio getAudio(int index)
    {
        return audio.get(index);
    }

    /**
     * @param audio the audio to set
     */
    public void setAudio(List<ItemAudio> audio)
    {
        this.audio = audio;
        setDirty(true);
    }
    
    public void setAudio(int index, ItemAudio audio)
    {
        this.audio.set(index, audio);
        setDirty(true);
    }

    public void setDefaultAudio(ItemAudio defaultAudio)
    {
        defaultAudioIndex = audio.indexOf(defaultAudio);
    }

    public ItemAudio getDefaultAudio()
    {
        return audio.get(defaultAudioIndex);
    }

    /**
     * @return the images
     */
    public List<ItemImage> getImages()
    {
        return images;
    }
    
    public ItemImage getImages(int index)
    {
        return images.get(index);
    }

    /**
     * @param images the images to set
     */
    public void setImages(List<ItemImage> images)
    {
        for (ItemImage image : this.images) {
            try {
                image.dispose();
            } catch (IOException ex) {
                // could not delete the file associated with the old image object
                //TODO: warn the user that there is clutter happening
            }
        }
        this.images = images;
        setDirty(true);
    }
    
    public void setImages(int index, ItemImage image)
    {
        try {
            images.get(index).dispose();
        } catch (IOException ex) {
            // could not delete the file associated with the old image object
            //TODO: warn the user that there is clutter happening
        }
        images.set(index, image);
        setDirty(true);
    }
    
    public void setDefaultImage(ItemImage defaultImage)
    {
        defaultImageIndex = images.indexOf(defaultImage);
        setDirty(true);
    }
    
    public ItemImage getDefaultImage()
    {
        try {
            return images.get(defaultImageIndex);
        } catch (Exception e) {
            return null;
        }
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
        itemElement.addContent(new Element(XML_ITEM_LABEL).addContent(label));
        Element categoryList = new Element(XML_CATEGORY);
        for (Iterator<Category> it = categories.iterator(); it.hasNext();) {
            categoryList.addContent(new Element(XML_LABEL).addContent(it.next().toString()));
        }
        itemElement.addContent(categoryList);
        
        Element audioList = new Element(XML_AUDIO);
        for (Iterator<ItemAudio> it = audio.iterator(); it.hasNext();) {
            ItemAudio next = it.next();
            audioList.addContent(new Element(XML_PATH).addContent(next.toString()).setAttribute(XML_DEFAULT_ATTR, Boolean.toString(next == getDefaultAudio())));
        }
        itemElement.addContent(audioList);
        
        Element imageList = new Element(XML_IMAGE);
        for (Iterator<ItemImage> it = images.iterator(); it.hasNext();) {
            ItemImage next = it.next();
            imageList.addContent(new Element(XML_PATH).addContent(next.toString()).setAttribute(XML_DEFAULT_ATTR, Boolean.toString(next == getDefaultImage())));
        }
        itemElement.addContent(imageList);
        
        return itemElement;
    }
}
