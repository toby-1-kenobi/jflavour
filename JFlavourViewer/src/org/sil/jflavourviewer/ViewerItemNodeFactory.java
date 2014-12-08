/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.sil.jflavourviewer;

import java.util.Collection;
import java.util.List;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Node;
import org.openide.util.Lookup;
import org.sil.jflavourapi.Category;
import org.sil.jflavourapi.JFlavourItemBean;
import org.sil.jflavourapi.JFlavourProjectBean;

/**
 *
 * @author toby
 */
public class ViewerItemNodeFactory extends ChildFactory<ViewerItem>
{
    
    private JFlavourProjectBean activeProject;

    public ViewerItemNodeFactory(JFlavourProjectBean activeProject)
    {
        this.activeProject = activeProject;
    }

    @Override
    protected boolean createKeys(List<ViewerItem> list)
    {
        Collection<? extends Category> selectedCategories = Lookup.getDefault().lookupAll(Category.class);
        Collection<? extends JFlavourItemBean> selectedItems = Lookup.getDefault().lookupAll(JFlavourItemBean.class);
        // if no items are catefories are selected in the prject manager
        // then we simply give all the items in the active project
        if (selectedCategories.isEmpty() && selectedItems.isEmpty()) {
            for (JFlavourItemBean item : activeProject.getItems()) {
                list.add(new ViewerItem(item));
            }
        }
        // otherwise we give all the items of the selected categories
        // and all the individually selected items
        else {
            for (Category category : selectedCategories) {
                for (JFlavourItemBean item : activeProject.getItemsInCategory(category)) {
                    list.add(new ViewerItem(item));
                }
            }
            for (JFlavourItemBean item : selectedItems) {
                list.add(new ViewerItem(item));
            }
        }
        return true;
    }
    
    @Override
    protected Node createNodeForKey(ViewerItem key) {
        return new ViewerItemNode(key);
    }
    
}
