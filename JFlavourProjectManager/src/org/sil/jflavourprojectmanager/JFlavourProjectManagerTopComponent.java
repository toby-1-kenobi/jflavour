/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sil.jflavourprojectmanager;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.DefaultListModel;
import org.openide.util.NbBundle;
import org.openide.windows.TopComponent;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.util.lookup.Lookups;
import org.sil.jflavourapi.JFlavourProjectBean;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(dtd = "-//org.sil.jflavourprojectmanager//JFlavourProjectManager//EN",
autostore = false)
@TopComponent.Description(preferredID = "JFlavourProjectManagerTopComponent",
//iconBase="SET/PATH/TO/ICON/HERE", 
persistenceType = TopComponent.PERSISTENCE_ALWAYS)
@TopComponent.Registration(mode = "explorer", openAtStartup = true)
@ActionID(category = "Window", id = "org.sil.jflavourprojectmanager.JFlavourProjectManagerTopComponent")
@ActionReference(path = "Menu/Window" /*, position = 333 */)
@TopComponent.OpenActionRegistration(displayName = "#CTL_JFlavourProjectManagerAction",
preferredID = "JFlavourProjectManagerTopComponent")
public final class JFlavourProjectManagerTopComponent extends TopComponent implements PropertyChangeListener
{

    public JFlavourProjectManagerTopComponent()
    {
        initComponents();
        setName(NbBundle.getMessage(JFlavourProjectManagerTopComponent.class, "CTL_JFlavourProjectManagerTopComponent"));
        setToolTipText(NbBundle.getMessage(JFlavourProjectManagerTopComponent.class, "HINT_JFlavourProjectManagerTopComponent"));
        projectsListModel = new DefaultListModel();
        populateProjectList();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSplitPane1 = new javax.swing.JSplitPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        jPanel1 = new javax.swing.JPanel();
        tfProjectName = new javax.swing.JTextField();
        btnProjectExport = new javax.swing.JButton();
        btnImport = new javax.swing.JButton();
        btnNew = new javax.swing.JButton();

        jSplitPane1.setDividerLocation(100);

        jList1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(jList1);

        jSplitPane1.setLeftComponent(jScrollPane1);

        tfProjectName.setText(org.openide.util.NbBundle.getMessage(JFlavourProjectManagerTopComponent.class, "JFlavourProjectManagerTopComponent.tfProjectName.text")); // NOI18N
        tfProjectName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfProjectNameActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(btnProjectExport, org.openide.util.NbBundle.getMessage(JFlavourProjectManagerTopComponent.class, "JFlavourProjectManagerTopComponent.btnProjectExport.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(btnImport, org.openide.util.NbBundle.getMessage(JFlavourProjectManagerTopComponent.class, "JFlavourProjectManagerTopComponent.btnImport.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(btnNew, org.openide.util.NbBundle.getMessage(JFlavourProjectManagerTopComponent.class, "JFlavourProjectManagerTopComponent.btnNew.text")); // NOI18N
        btnNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(tfProjectName, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)
                    .addComponent(btnProjectExport, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnImport, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnNew, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(44, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tfProjectName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnProjectExport)
                .addGap(18, 18, 18)
                .addComponent(btnImport)
                .addGap(18, 18, 18)
                .addComponent(btnNew)
                .addContainerGap(116, Short.MAX_VALUE))
        );

        jSplitPane1.setRightComponent(jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

private void tfProjectNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfProjectNameActionPerformed
// TODO add your handling code here:
}//GEN-LAST:event_tfProjectNameActionPerformed

private void btnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewActionPerformed
    newProject();
}//GEN-LAST:event_btnNewActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnImport;
    private javax.swing.JButton btnNew;
    private javax.swing.JButton btnProjectExport;
    private javax.swing.JList jList1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JTextField tfProjectName;
    // End of variables declaration//GEN-END:variables
    private DefaultListModel<String> projectsListModel;
    private JFlavourProjectBean currentProject;
    
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
    
    private void populateProjectList()
    {
        // TODO populate the project list with persisting projects
    }
    
    private void newProject()
    {
        currentProject = new JFlavourProjectBean();
        currentProject.addPropertyChangeListener(this);
        currentProject.setName("New Project");
        associateLookup(Lookups.singleton(currentProject));
        saveProject();
        tfProjectName.grabFocus();
        projectsListModel.addElement(currentProject.getName());
    }
    
    private void saveProject()
    {
        // TODO save current project (output to XML file?)
    }

    @Override
    public void propertyChange(PropertyChangeEvent arg0)
    {
        if (arg0.getPropertyName().equals(JFlavourProjectBean.PROP_NAME)) {
            tfProjectName.setText(currentProject.getName());
        }
    }
}
