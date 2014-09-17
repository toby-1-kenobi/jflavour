/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sil.jflavouritemeditor;

import org.openide.modules.ModuleInstall;
import org.openide.util.LookupListener;

public class Installer extends ModuleInstall

{

    @Override
    public void restored()
    {
        JFlavourItemEditorTopComponent.startHandlingInterModuleEvents();
    }

}
