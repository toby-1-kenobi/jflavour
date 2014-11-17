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
            setVisible(true);
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
            Dimension boundingBox = this.getPreferredSize();
            // scale the image to fit in the panel
            double scaleFactor = Math.max(image.getWidth() / boundingBox.getWidth(), image.getHeight() / boundingBox.getHeight());
            g.drawImage(
                    image,
                    0,
                    0,
                    (int) Math.round(image.getWidth() / scaleFactor),
                    (int) Math.round(image.getHeight() / scaleFactor),
                    0,
                    0,
                    image.getWidth(),
                    image.getHeight(),
                    null
            );
        }
    }

}
