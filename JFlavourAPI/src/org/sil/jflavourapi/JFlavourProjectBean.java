/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sil.jflavourapi;

import java.awt.event.ActionListener;
import java.beans.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import javax.swing.Timer;
import java.util.TreeSet;
import java.util.UUID;
import org.jdom2.*;
import org.jdom2.filter.ElementFilter;

/**
 *
 * @author Toby Anderson
 */
public class JFlavourProjectBean implements Serializable, PropertyChangeListener
{
    
    private String name;
    private UUID id;
    private List<JFlavourItemBean> items;
    private boolean dirty;
    private PropertyChangeSupport propertySupport;
    private Timer saveTimer;
    
    private final String XML_PROJECT = "jFlavourProject";
    private final String XML_PROJECT_NAME = "projectName";
    private final String XML_PROJECT_ID = "projectUUID";
    private final String XML_ITEMS = "items";
    
    public static final String PROP_NAME = "name";
    public static final String PROP_ITEMS = "items";
    public static final String PROP_ITEM = "item";
    public static final String PROP_DIRTY = "dirty";
    
    /**
     * Create a new empty nameless JFlavour project
     */
    public JFlavourProjectBean()
    {
        name = "";
        id = UUID.randomUUID();
        items = new ArrayList<JFlavourItemBean>(100);
        dirty = false;
        propertySupport = new PropertyChangeSupport(this);
        // set the timer to be for 5 seconds without repreating
        saveTimer = new Timer(5000, null);
        saveTimer.setRepeats(false);
        saveTimer.setActionCommand(id.toString());
    }
    
    /**
     * Construct a JFlavour project from a well formed XML document.
     * The XML must be in the same format as that returned by {@link #toDomElement()}
     * @param domElement The XML document from which to construct the project
     */
    public JFlavourProjectBean(Element domElement)
    {
        this();
        name = domElement.getChildText(XML_PROJECT_NAME);
        id = UUID.fromString(domElement.getChildText(XML_PROJECT_ID));
        saveTimer.setActionCommand(id.toString());
        Element allItems = domElement.getChild(XML_ITEMS);
        for (Iterator<Element> it = allItems.getDescendants(new ElementFilter()); it.hasNext();) {
            JFlavourItemBean item = new JFlavourItemBean(it.next());
            item.addPropertyChangeListener(this);
            items.add(item);          
        }
    }
    
    public SortedSet<Category> getCategories()
    {
        SortedSet<Category> allCategories = new TreeSet<Category>();
        for (JFlavourItemBean item : items) {
            allCategories.addAll(item.getCategories());
        }
        return allCategories;
    }
    
    public Set<JFlavourItemBean> getItemsInCategory(Category category)
    {
        Set<JFlavourItemBean> items = new HashSet<JFlavourItemBean>();
        for (JFlavourItemBean item : items) {
            if (item.getCategories().contains(category)) {
                items.add(item);
            }
        }
        return items;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String value)
    {
        String oldValue = name;
        name = value;
        setDirty(true);
        propertySupport.firePropertyChange(PROP_NAME, oldValue, name);
    }
    
    public void addPropertyChangeListener(PropertyChangeListener listener)
    {
        propertySupport.addPropertyChangeListener(listener);
    }
    
    public void removePropertyChangeListener(PropertyChangeListener listener)
    {
        propertySupport.removePropertyChangeListener(listener);
    }
    
    public void addActionListener(ActionListener listener)
    {
        saveTimer.addActionListener(listener);
    }
    
    public void removeActionListener(ActionListener listener)
    {
        saveTimer.removeActionListener(listener);
    }

    /**
     * @return the items
     */
    public List<JFlavourItemBean> getItems()
    {
        return items;
    }
    
    public JFlavourItemBean getItems(int index)
    {
        return items.get(index);
    }

    /**
     * @param items the items to set
     */
    public void setItems(List<JFlavourItemBean> items)
    {
        List<JFlavourItemBean> oldItems = this.items;
        for (JFlavourItemBean item : oldItems) {
            item.removePropertyChangeListener(this);
        }
        this.items = items;
        setDirty(true);
        for (JFlavourItemBean item : this.items) {
            item.addPropertyChangeListener(this);
        }
        propertySupport.firePropertyChange(PROP_ITEMS, oldItems, items);
    }
    
    public void setItems(int index, JFlavourItemBean item)
    {
        JFlavourItemBean oldItem = this.items.get(index);
        oldItem.removePropertyChangeListener(this);
        item.addPropertyChangeListener(this);
        this.items.set(index, item);
        setDirty(true);
        propertySupport.firePropertyChange(PROP_ITEM, oldItem, item);
    }
    
    public void addItem(JFlavourItemBean item)
    {
        List<JFlavourItemBean> oldItems = this.items;
        this.items.add(item);
        setDirty(true);
        item.addPropertyChangeListener(this);
        propertySupport.firePropertyChange(PROP_ITEMS, oldItems, items);
    }

    /**
     * @return true if this item has been set as dirty, otherwise false
     */
    public boolean getDirty()
    {
        return dirty;
    }

    /**
     * @param dirty true to set this item as dirty, false to set it as clean
     */
    public void setDirty(boolean dirty)
    {
        if (this.dirty != dirty) {
            this.dirty = dirty;
            propertySupport.firePropertyChange(PROP_DIRTY, new Boolean(!dirty), new Boolean(dirty));
            if (!saveTimer.isRunning()) {
                saveTimer.start();
            }
        }
        if (!dirty) {
            for (JFlavourItemBean item : this.items) {
                item.setDirty(false);
            }
        }
    }
    
    /**
     * check if this item has been modified since last save.
     * @return true if this item has been set as dirty, otherwise false
     */
    public boolean isDirty()
    {
        return dirty;
    }
    
    public UUID getId()
    {
        return id;
    }
    
    /**
     * Build an XML representation of this project.
     * @return the root element of the XML document built
     */
    public Element toDomElement()
    {
        Element project = new Element(XML_PROJECT);
        project.addContent(new Element(XML_PROJECT_NAME).addContent(name));
        project.addContent(new Element(XML_PROJECT_ID).addContent(id.toString()));
        Element itemList = new Element(XML_ITEMS);
        for (Iterator<JFlavourItemBean> it = items.iterator(); it.hasNext();) {
            JFlavourItemBean item = it.next();
            itemList.addContent(item.toDomElement());
        }
        project.addContent(itemList);
        return project;
    }

    @Override
    public void propertyChange(PropertyChangeEvent pce)
    {
        // the project should be listening to all it's items,
        // if any of them become dirty, the project is dirty.
        if (pce.getPropertyName().equals(JFlavourItemBean.PROP_DIRTY) && ((Boolean)pce.getNewValue()).booleanValue()) {
            setDirty(true);
            if (!saveTimer.isRunning()) {
                saveTimer.start();
            }
        }
    }
}
