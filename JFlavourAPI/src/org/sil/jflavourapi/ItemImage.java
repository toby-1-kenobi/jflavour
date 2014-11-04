/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.sil.jflavourapi;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import javax.imageio.ImageIO;

/**
 *
 * @author toby
 */
public class ItemImage extends ItemMedia
{
    
    private BufferedImage image = null;
    
    public ItemImage(Path imageFile)
    {
        super(imageFile);
    }

    @Override
    public void load() throws IOException
    {
        if (image == null) {
            image = ImageIO.read(mediaFile.toFile());
        }
    }

    @Override
    public void unload()
    {
        image = null;
    }
    
    public BufferedImage getImage()
    {
        return image;
    }
    
}
