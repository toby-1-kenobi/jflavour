/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sil.jflavouritemeditor;

import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.windows.TopComponent;
import org.openide.util.NbBundle.Messages;
import org.openide.util.LookupListener;
import org.sil.jflavourapi.Category;
import org.sil.jflavourapi.CentralLookup;
import org.sil.jflavourapi.InterModuleEvent;
import org.sil.jflavourapi.ItemImage;
import org.sil.jflavourapi.JFlavourItemBean;
import org.sil.jflavourapi.JFlavourProjectBean;

/**
 * Top component which displays something.
*/
@ConvertAsProperties(
        dtd = "-//org.sil.jflavouritemeditor//JFlavourItemEditor//EN",
        autostore = false
)
@TopComponent.Description(
        preferredID = "JFlavourItemEditorTopComponent",
        //iconBase="SET/PATH/TO/ICON/HERE", 
        persistenceType = TopComponent.PERSISTENCE_NEVER
)
@TopComponent.Registration(mode = "properties", openAtStartup = false)
@ActionID(category = "window", id = "org.sil.jflavouritemeditor.JFlavourItemEditorTopComponent")
@ActionReference(path = "Menu/Window" /*, position = 333 */)
@TopComponent.OpenActionRegistration(displayName = "#CTL_JFlavourItemEditorAction")
@Messages({
    "CTL_JFlavourItemEditorAction=JFlavourItemEditor",
    "CTL_JFlavourItemEditorTopComponent=JFlavourItemEditor Window",
    "HINT_JFlavourItemEditorTopComponent=This is a JFlavourItemEditor window"
})
public final class JFlavourItemEditorTopComponent extends TopComponent
{
    
    public JFlavourItemEditorTopComponent()
    {
        this(new JFlavourItemBean());
    }

    public JFlavourItemEditorTopComponent(JFlavourItemBean item)
    {
        initComponents();
        imagePreview = new ImagePanel();
        panelImagePreview.add(imagePreview);
        setName(Bundle.CTL_JFlavourItemEditorTopComponent());
        setToolTipText(Bundle.HINT_JFlavourItemEditorTopComponent());
        putClientProperty(TopComponent.PROP_MAXIMIZATION_DISABLED, Boolean.TRUE);
        this.item = item;
        populateFromItem();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        imageChooser = new javax.swing.JFileChooser();
        txtItemLabel = new javax.swing.JTextField();
        btnCancel = new javax.swing.JButton();
        btnApply = new javax.swing.JButton();
        btnOk = new javax.swing.JButton();
        panelCategories = new javax.swing.JPanel();
        labelCategories = new javax.swing.JLabel();
        txtCategories = new javax.swing.JTextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        panelCategoriesList = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        panelImages = new javax.swing.JPanel();
        labelImages = new javax.swing.JLabel();
        btnBrowseImages = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        panelImagesList = new javax.swing.JPanel();
        panelAudio = new javax.swing.JPanel();
        labelAudio = new javax.swing.JLabel();
        btnBrowseAudio = new javax.swing.JButton();
        jScrollPane6 = new javax.swing.JScrollPane();
        panelAudioList = new javax.swing.JPanel();
        panelImagePreview = new javax.swing.JPanel();
        btnRevert = new javax.swing.JButton();

        imageChooser.setFileFilter(new FileNameExtensionFilter(
            "Image files", ImageIO.getReaderFileSuffixes()));
    imageChooser.setToolTipText(org.openide.util.NbBundle.getMessage(JFlavourItemEditorTopComponent.class, "JFlavourItemEditorTopComponent.imageChooser.toolTipText")); // NOI18N
    imageChooser.setMultiSelectionEnabled(true);

    txtItemLabel.addActionListener(new java.awt.event.ActionListener()
    {
        public void actionPerformed(java.awt.event.ActionEvent evt)
        {
            txtItemLabelActionPerformed(evt);
        }
    });

    org.openide.awt.Mnemonics.setLocalizedText(btnCancel, "Cancel");
    btnCancel.addActionListener(new java.awt.event.ActionListener()
    {
        public void actionPerformed(java.awt.event.ActionEvent evt)
        {
            btnCancelActionPerformed(evt);
        }
    });

    org.openide.awt.Mnemonics.setLocalizedText(btnApply, "Apply");
    btnApply.addActionListener(new java.awt.event.ActionListener()
    {
        public void actionPerformed(java.awt.event.ActionEvent evt)
        {
            applyToItem(evt);
        }
    });

    org.openide.awt.Mnemonics.setLocalizedText(btnOk, org.openide.util.NbBundle.getMessage(JFlavourItemEditorTopComponent.class, "JFlavourItemEditorTopComponent.btnOk.text")); // NOI18N

    org.openide.awt.Mnemonics.setLocalizedText(labelCategories, "Categories");

    txtCategories.addActionListener(new java.awt.event.ActionListener()
    {
        public void actionPerformed(java.awt.event.ActionEvent evt)
        {
            txtCategoriesActionPerformed(evt);
        }
    });

    panelCategoriesList.setLayout(new javax.swing.BoxLayout(panelCategoriesList, javax.swing.BoxLayout.Y_AXIS));
    jScrollPane4.setViewportView(panelCategoriesList);

    org.openide.awt.Mnemonics.setLocalizedText(jLabel1, "use comma between");

    javax.swing.GroupLayout panelCategoriesLayout = new javax.swing.GroupLayout(panelCategories);
    panelCategories.setLayout(panelCategoriesLayout);
    panelCategoriesLayout.setHorizontalGroup(
        panelCategoriesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(panelCategoriesLayout.createSequentialGroup()
            .addContainerGap()
            .addGroup(panelCategoriesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING)
                .addComponent(labelCategories, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtCategories)
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addContainerGap())
    );
    panelCategoriesLayout.setVerticalGroup(
        panelCategoriesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(panelCategoriesLayout.createSequentialGroup()
            .addContainerGap()
            .addComponent(labelCategories)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 263, Short.MAX_VALUE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(txtCategories, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jLabel1)
            .addContainerGap())
    );

    org.openide.awt.Mnemonics.setLocalizedText(labelImages, "Images");

    org.openide.awt.Mnemonics.setLocalizedText(btnBrowseImages, "Browse...");
    btnBrowseImages.addActionListener(new java.awt.event.ActionListener()
    {
        public void actionPerformed(java.awt.event.ActionEvent evt)
        {
            btnBrowseImagesActionPerformed(evt);
        }
    });

    panelImagesList.setLayout(new javax.swing.BoxLayout(panelImagesList, javax.swing.BoxLayout.Y_AXIS));
    jScrollPane5.setViewportView(panelImagesList);

    javax.swing.GroupLayout panelImagesLayout = new javax.swing.GroupLayout(panelImages);
    panelImages.setLayout(panelImagesLayout);
    panelImagesLayout.setHorizontalGroup(
        panelImagesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelImagesLayout.createSequentialGroup()
            .addContainerGap()
            .addGroup(panelImagesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                .addComponent(labelImages, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnBrowseImages, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelImagesLayout.createSequentialGroup()
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
            .addContainerGap())
    );
    panelImagesLayout.setVerticalGroup(
        panelImagesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(panelImagesLayout.createSequentialGroup()
            .addContainerGap()
            .addComponent(labelImages)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(btnBrowseImages)
            .addContainerGap())
    );

    org.openide.awt.Mnemonics.setLocalizedText(labelAudio, "Audio");

    org.openide.awt.Mnemonics.setLocalizedText(btnBrowseAudio, "Browse...");

    panelAudioList.setLayout(new javax.swing.BoxLayout(panelAudioList, javax.swing.BoxLayout.Y_AXIS));
    jScrollPane6.setViewportView(panelAudioList);

    javax.swing.GroupLayout panelAudioLayout = new javax.swing.GroupLayout(panelAudio);
    panelAudio.setLayout(panelAudioLayout);
    panelAudioLayout.setHorizontalGroup(
        panelAudioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelAudioLayout.createSequentialGroup()
            .addContainerGap()
            .addGroup(panelAudioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                .addComponent(jScrollPane6)
                .addComponent(labelAudio, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnBrowseAudio, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addContainerGap())
    );
    panelAudioLayout.setVerticalGroup(
        panelAudioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(panelAudioLayout.createSequentialGroup()
            .addContainerGap()
            .addComponent(labelAudio)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(btnBrowseAudio)
            .addContainerGap())
    );

    javax.swing.GroupLayout panelImagePreviewLayout = new javax.swing.GroupLayout(panelImagePreview);
    panelImagePreview.setLayout(panelImagePreviewLayout);
    panelImagePreviewLayout.setHorizontalGroup(
        panelImagePreviewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGap(0, 149, Short.MAX_VALUE)
    );
    panelImagePreviewLayout.setVerticalGroup(
        panelImagePreviewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGap(0, 100, Short.MAX_VALUE)
    );

    org.openide.awt.Mnemonics.setLocalizedText(btnRevert, org.openide.util.NbBundle.getMessage(JFlavourItemEditorTopComponent.class, "JFlavourItemEditorTopComponent.btnRevert.text_1")); // NOI18N
    btnRevert.addActionListener(new java.awt.event.ActionListener()
    {
        public void actionPerformed(java.awt.event.ActionEvent evt)
        {
            populateFromItem(evt);
        }
    });

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
    this.setLayout(layout);
    layout.setHorizontalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                .addGroup(layout.createSequentialGroup()
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(txtItemLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(panelImagePreview, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(panelCategories, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(panelImages, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 48, Short.MAX_VALUE)
                    .addComponent(panelAudio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(btnRevert)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(btnApply, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(btnCancel)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(btnOk, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addContainerGap(55, Short.MAX_VALUE))
    );
    layout.setVerticalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(txtItemLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(panelImagePreview, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(panelAudio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(panelImages, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(panelCategories, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(btnCancel)
                .addComponent(btnApply)
                .addComponent(btnOk)
                .addComponent(btnRevert))
            .addContainerGap())
    );
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnCancelActionPerformed
    {//GEN-HEADEREND:event_btnCancelActionPerformed
        
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnBrowseImagesActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnBrowseImagesActionPerformed
    {//GEN-HEADEREND:event_btnBrowseImagesActionPerformed
        int dialogReturn = imageChooser.showOpenDialog(this);
        if (dialogReturn == javax.swing.JFileChooser.APPROVE_OPTION)
        {
            File[] selected = imageChooser.getSelectedFiles();
            ImageNode iNode = null;
            for (int i = 0; i < selected.length; ++i) {
                iNode = new ImageNode(new ItemImage(selected[i].toPath()));
                panelImagesList.add(iNode);  
                setFormDirty();
            }
            if (!(iNode == null)) iNode.checkForDefault(panelImagesList);
            panelImagesList.revalidate();
        }
    }//GEN-LAST:event_btnBrowseImagesActionPerformed

    private void txtItemLabelActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_txtItemLabelActionPerformed
    {//GEN-HEADEREND:event_txtItemLabelActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtItemLabelActionPerformed

    private void txtCategoriesActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_txtCategoriesActionPerformed
    {//GEN-HEADEREND:event_txtCategoriesActionPerformed
        String text = txtCategories.getText();
        String[] newCategories = text.split(",");
        // We need to make sure we're not adding to the list one that's already there
        Component[] children = panelCategoriesList.getComponents();
        List<Category> existingCategories = new ArrayList<Category>(children.length);
        for (Component component : children) {
            if (component instanceof CategoryNode)
            {
                existingCategories.add(((CategoryNode)component).getCategory());
            }
        }
        // Now add to the list categories that aren't already there
        for (String categoryName : newCategories) {
            Category category = new Category(categoryName);
            if (!existingCategories.contains(category)) {
                panelCategoriesList.add(new CategoryNode(category));
                setFormDirty();
            }
        }
        panelCategoriesList.revalidate();
        txtCategories.setText("");
    }//GEN-LAST:event_txtCategoriesActionPerformed

    private void applyToItem(java.awt.event.ActionEvent evt)//GEN-FIRST:event_applyToItem
    {//GEN-HEADEREND:event_applyToItem
        // Apply the label to the item
        item.setLabel(txtItemLabel.getText());
        
        // Apply the listed categories to the item
        // TODO?: get tree lock for panelCategoriesList
        Component[] categoriesChildren = panelCategoriesList.getComponents();
        List<Category> newCategories = new ArrayList<Category>(categoriesChildren.length);
        for (Component component : categoriesChildren) {
            if (component instanceof CategoryNode)
            {
                newCategories.add(((CategoryNode)component).getCategory());
            }
        }
        item.setCategories(newCategories);
        
        // Apply the listed images to the item
        Component[] imageChildren = panelImagesList.getComponents();
        ItemImage defaultImage = null;
        List<ItemImage> newImages = new ArrayList<ItemImage>(imageChildren.length);
        for (Component component : imageChildren) {
            if (component instanceof ImageNode)
            {
                newImages.add(((ImageNode)component).getImage());
            }
        }
        item.setImages(newImages);
        
        setFormClean();
    }//GEN-LAST:event_applyToItem

    private void populateFromItem(java.awt.event.ActionEvent evt)//GEN-FIRST:event_populateFromItem
    {//GEN-HEADEREND:event_populateFromItem
        populateFromItem();
    }//GEN-LAST:event_populateFromItem


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnApply;
    private javax.swing.JButton btnBrowseAudio;
    private javax.swing.JButton btnBrowseImages;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnOk;
    private javax.swing.JButton btnRevert;
    private javax.swing.JFileChooser imageChooser;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JLabel labelAudio;
    private javax.swing.JLabel labelCategories;
    private javax.swing.JLabel labelImages;
    private javax.swing.JPanel panelAudio;
    private javax.swing.JPanel panelAudioList;
    private javax.swing.JPanel panelCategories;
    private javax.swing.JPanel panelCategoriesList;
    private javax.swing.JPanel panelImagePreview;
    private javax.swing.JPanel panelImages;
    private javax.swing.JPanel panelImagesList;
    private javax.swing.JTextField txtCategories;
    private javax.swing.JTextField txtItemLabel;
    // End of variables declaration//GEN-END:variables
    private ImagePanel imagePreview;
    
    private JFlavourItemBean item;
    
    private static InterModuleEventHandler imeHandler = new InterModuleEventHandler();
    
    public static void startHandlingInterModuleEvents()
    {
        imeHandler.startListening();
    }
    
    public static void stopHandlingInterModuleEvents()
    {
        imeHandler.stopListening();
    }
    
    @Override
    public void componentOpened()
    {
        // TODO add custom code on component opening
    }

    @Override
    public void componentClosed()
    {
        // TODO add custom code on component closing
    }

    void writeProperties(java.util.Properties p)
    {
        // better to version settings since initial version as advocated at
        // http://wiki.apidesign.org/wiki/PropertyFiles
        p.setProperty("version", "1.0");
        // TODO store your settings
    }

    void readProperties(java.util.Properties p)
    {
        String version = p.getProperty("version");
        // TODO read your settings according to their version
    }
    
    /**
     * create a new item and open in the editor window
     */
    public static void editNewItem(JFlavourProjectBean project)
    {
        JFlavourItemBean newItem = new JFlavourItemBean();
        project.addItem(newItem);
        TopComponent itemEditor = new JFlavourItemEditorTopComponent(newItem);
        itemEditor.open();
        itemEditor.requestActive();
    }
    
    private void populateFromItem()
    {
        txtItemLabel.setText(item.getLabel());
        panelCategoriesList.removeAll();
        for (Iterator<Category> it = item.getCategories().iterator(); it.hasNext();) {
            panelCategoriesList.add(new CategoryNode(it.next()));
        }
        panelCategoriesList.revalidate();
        setFormClean();
    }
    
    private void setFormDirty()
    {
        btnApply.setEnabled(true);
        btnCancel.setEnabled(true);
    }
    
    private void setFormClean()
    {
        btnApply.setEnabled(false);
        btnCancel.setEnabled(false);
    }
    
    private static class InterModuleEventHandler implements LookupListener
    {
        
        private Lookup.Result<InterModuleEvent> result = null;
        public final String MODULE_ID = "org.sil.jflavouritemeditor.JFlavourItemEditorTopComponent";
        public final String NEW_ITEM_ACTION_ID = "editNewItem";
        
        public InterModuleEventHandler()
        {
            result = CentralLookup.getDefault().lookupResult(InterModuleEvent.class);
        }
        
        public void startListening()
        {
            result.addLookupListener (this);
        }
        
        public void stopListening()
        {
            result.removeLookupListener (this);
        }
        
        @Override
        public void resultChanged(LookupEvent le)
        {
            Collection<? extends InterModuleEvent> allEvents = result.allInstances();
            if (!allEvents.isEmpty()) {
                InterModuleEvent event = allEvents.iterator().next();
                if (event.hasIdentifier(MODULE_ID + '.' + NEW_ITEM_ACTION_ID))
                {
                    JFlavourItemEditorTopComponent.editNewItem(event.getProject());
                    CentralLookup.getDefault().remove(event);
                }
            }
        }
    }
    
    private abstract class EditorNode extends JPanel
    {
        protected JButton makeDeleteButton()
        {
            JButton deleteBtn = new JButton();
            deleteBtn.addActionListener(new ActionListener() {
                @Override
		public void actionPerformed(ActionEvent e) {
                    Component actionSource = (Component)(e.getSource());
                    // remove the CategoryNode from its parent
                    Container node = actionSource.getParent();
                    Container parent = node.getParent();
                    parent.remove(node);
                    checkForDefault(parent);
                    parent.revalidate();
                    parent.repaint();
		}
            });
            deleteBtn.setIcon(new ImageIcon(getClass().getResource("/org/sil/jflavouritemeditor/images/delete.png")));
            return deleteBtn;
        }
        
        protected JToggleButton makeDefaultButton()
        {
            JToggleButton defaultBtn = new JToggleButton();
            defaultBtn.addActionListener(new ActionListener() {
                @Override
		public void actionPerformed(ActionEvent e) {
                    JToggleButton actionSource = (JToggleButton)(e.getSource());
                    if(!actionSource.isSelected()) {
                        // Cannot manually unset default toggle, so toggle again if unset
                        actionSource.setSelected(true);
                    } else {
                        // otherwise unset all other default toggles in this list
                        actionSource.setIcon(new ImageIcon(getClass().getResource("/org/sil/jflavouritemeditor/images/tick_colour.png")));
                        Icon blackTick = new ImageIcon(getClass().getResource("/org/sil/jflavouritemeditor/images/tick_black.png"));
                        Container node = actionSource.getParent();
                        Component[] allNodes = node.getParent().getComponents();
                        for (int i = 0; i < allNodes.length; ++i) 
                        {
                            try {
                                HasDefaultButton nodeD = (HasDefaultButton)allNodes[i];
                                if (nodeD != node) {
                                    nodeD.setDefault(false, blackTick);
                                }
                            } catch (ClassCastException cce) {
                                // somehow we've got a node that doesn't implement the HasDefaultButton interface
                                // so we'll just ignore this one
                            }
                        }
                    }
		}
            });
            defaultBtn.setIcon(new ImageIcon(getClass().getResource("/org/sil/jflavouritemeditor/images/tick_black.png")));
            return defaultBtn;
        }
        
        public void checkForDefault(Container nodeParent)
        {
            HasDefaultButton defaultNode = null;
            Component[] allNodes = nodeParent.getComponents();
            for (int i = allNodes.length - 1; i >=0 ; --i)
            {
                try {
                    defaultNode = (HasDefaultButton)allNodes[i];
                    if (defaultNode.isDefault()) {
                        // one of the nodes is default, that's all we need
                        // assume the other nodes aren't - it's handled by the behaviour of the default toggle button
                        return;
                    }
                } catch (ClassCastException cce) {
                    // we've got a node that doesn't implement the HasDefaultButton interface
                    // so we'll just ignore this one
                }
            }
            // if we reach here it means we haven't found a default node
            // if the variable defaultNode is not null it means we found at least one node with a default button
            // so make it default so one is default.
            if (!(defaultNode == null)) defaultNode.setDefault(true, new ImageIcon(getClass().getResource("/org/sil/jflavouritemeditor/images/tick_colour.png")));
        }
    }
    
    private interface HasDefaultButton
    {
        public boolean isDefault();
        
        public void setDefault(boolean defaultValue, Icon setIcon);
    }
    
    private class CategoryNode extends EditorNode
    {
        private Category category;
        
        public CategoryNode(Category category)
        {
            this.category = category;
            this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
            this.add(new JLabel(this.category.toString()));
            this.add(makeDeleteButton());
        }
        
        public Category getCategory()
        {
            return category;
        }
    }
    
    private class ImageNode extends EditorNode implements HasDefaultButton
    {
        private ItemImage image;
        private JToggleButton defaultButton;
        private boolean isDefault;
        
        public ImageNode(ItemImage image)
        {
            this.image = image;
            defaultButton = makeDefaultButton();
            isDefault = false;
            this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
            this.add(new JLabel(this.image.toShortString()));
            this.add(defaultButton);
            this.add(makeDeleteButton());
        }

        @Override
        public boolean isDefault()
        {
            return isDefault;
        }

        @Override
        public void setDefault(boolean defaultValue, Icon setIcon)
        {
            defaultButton.setSelected(defaultValue);
            if (!(setIcon == null)) defaultButton.setIcon(setIcon);
            isDefault = defaultValue;
        }
        
        public ItemImage getImage()
        {
            return image;
        }
        
    }
}
