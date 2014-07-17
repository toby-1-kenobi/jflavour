/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sil.jflavourapi;

import java.beans.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 *
 * @author toby
 */
public class JFlavourProjectBean implements Serializable
{
    
    public static final String PROP_NAME = "name";
    public static final String PROP_ITEMS = "items";
    public static final String PROP_ITEM = "item";
    private String name;
    private List<JFlavourItemBean> items;
    private PropertyChangeSupport propertySupport;
    
    public JFlavourProjectBean()
    {
        name = "";
        items = new ArrayList<JFlavourItemBean>(100);
        propertySupport = new PropertyChangeSupport(this);
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
}
