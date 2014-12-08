/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.sil.jflavourviewer;

import org.sil.jflavourapi.JFlavourItemBean;

/**
 * A wrapper for JFlavourItemBean. It's needed because otherwise items selected
 * in the viewer and items selected in the project manager would get confused
 * in the global lookup.
 * @author toby
 */
public class ViewerItem
{
    public JFlavourItemBean item;

    public ViewerItem(JFlavourItemBean item)
    {
        this.item = item;
    }
    
}
