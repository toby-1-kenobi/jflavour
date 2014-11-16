/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.sil.jflavouritemeditor;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

/**
 * A JPanel that you can put an image into.
 * @author Toby Anderson with the help of bcash
 * http://stackoverflow.com/questions/299495/how-to-add-an-image-to-a-jpanel
 */
public class ImagePanel extends JPanel
{

    private BufferedImage image = null;
    
    public void setImage (BufferedImage newImage)
    {
        image = newImage;
        if (image != null)
        {
            this.setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
            this.setMinimumSize(new Dimension(64, 64));
            setVisible(true);
            //this.getGraphics().drawImage(image, 0, 0, null);
            //revalidate();
        }
        else
        {
            setVisible(false);
        }
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image != null)
        {
            g.drawImage(image, 0, 0, null);
        }
    }

}
