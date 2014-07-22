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
public class JFlavourProjectBean implements Serializable
{
    
    private String name;
    private List<JFlavourItemBean> items;
    private PropertyChangeSupport propertySupport;
    
    private final String XML_PROJECT = "jFlavourProject";
    private final String XML_PROJECT_NAME = "projectName";
    private final String XML_ITEMS = "items";
    
    public static final String PROP_NAME = "name";
    public static final String PROP_ITEMS = "items";
    public static final String PROP_ITEM = "item";
    
    public JFlavourProjectBean()
    {
        name = "";
        items = new ArrayList<JFlavourItemBean>(100);
        propertySupport = new PropertyChangeSupport(this);
    }
    
    public JFlavourProjectBean(Element domElement)
    {
        super();
        name = domElement.getChildText(XML_PROJECT_NAME);
        Element allItems = domElement.getChild(XML_ITEMS);
        for (Iterator<Element> it = allItems.getDescendants(new ElementFilter()); it.hasNext();) {
            items.add(new JFlavourItemBean(it.next()));          
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
        this.items = items;
        propertySupport.firePropertyChange(PROP_ITEMS, oldItems, items);
    }
    
    public void setItems(int index, JFlavourItemBean item)
    {
        JFlavourItemBean oldItem = this.items.get(index);
        this.items.set(index, item);
        propertySupport.firePropertyChange(PROP_ITEM, oldItem, item);
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
}
