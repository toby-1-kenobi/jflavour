/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sil.jflavourapi;

import java.beans.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import org.jdom2.*;
import org.jdom2.filter.ElementFilter;

/**
 *
 * @author toby
 */
public class JFlavourProjectBean implements Serializable, PropertyChangeListener
{
    
    private String name;
    private List<JFlavourItemBean> items;
    private boolean dirty;
    private PropertyChangeSupport propertySupport;
    
    private final String XML_PROJECT = "jFlavourProject";
    private final String XML_PROJECT_NAME = "projectName";
    private final String XML_ITEMS = "items";
    
    public static final String PROP_NAME = "name";
    public static final String PROP_ITEMS = "items";
    public static final String PROP_ITEM = "item";
    public static final String PROP_DIRTY = "dirty";
    
    public JFlavourProjectBean()
    {
        name = "";
        items = new ArrayList<JFlavourItemBean>(100);
        dirty = false;
        propertySupport = new PropertyChangeSupport(this);
    }
    
    public JFlavourProjectBean(Element domElement)
    {
        this();
        name = domElement.getChildText(XML_PROJECT_NAME);
        Element allItems = domElement.getChild(XML_ITEMS);
        for (Iterator<Element> it = allItems.getDescendants(new ElementFilter()); it.hasNext();) {
            JFlavourItemBean item = new JFlavourItemBean(it.next());
            item.addPropertyChangeListener(this);
            items.add(item);          
        }
    }
    
    public SortedSet<String> getCategories()
    {
        SortedSet<String> allCategories = new TreeSet<String>();
        for (JFlavourItemBean item : items) {
            allCategories.addAll(item.getCategories());
        }
        return allCategories;
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
        if (!dirty) {
            for (JFlavourItemBean item : this.items) {
                item.setDirty(false);
            }
        }
    }
    
    public boolean isDirty()
    {
        return dirty;
    }
    
    public Element toDomElement()
    {
        Element project = new Element(XML_PROJECT);
        project.addContent(new Element(XML_PROJECT_NAME).addContent(name));
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
        }
    }
}
