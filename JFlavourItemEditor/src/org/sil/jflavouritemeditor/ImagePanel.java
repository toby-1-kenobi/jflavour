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
    // the bounding box for the image
    private final int WIDTH = 205;
    private final int HEIGHT = 128;
    // the size of the image after it is scald to fit the bounding box
    private int sWidth = 205;
    private int sHeight = 128;
    
    public void setImage (BufferedImage newImage)
    {
        image = newImage;
        if (image != null)
        {
            float xFactor = image.getWidth() / (float)WIDTH;
            float yFactor = image.getHeight() / (float)HEIGHT;
            float factor = Math.max(xFactor, yFactor);
            sWidth = Math.round(image.getWidth() / factor);
            sHeight = Math.round(image.getHeight() / factor);
            
            this.setMinimumSize(new Dimension(WIDTH, HEIGHT));
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
            g.drawImage(image, 0, 0, sWidth, sHeight, 0, 0, image.getWidth(), image.getHeight(), null);
        }
    }

}
