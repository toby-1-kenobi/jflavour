/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.sil.jflavourapi;

import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

/**
 *
 * @author toby
 */
public abstract class ItemMedia
{
    
    protected Path mediaFile;
    protected boolean imported;

    public ItemMedia(Path mediaFile)
    {
        this.mediaFile = mediaFile;
        imported = false;
    }
    
    /**
     * Copy the media to the application's directory structure
     * @param targetDir the directory to move the media file to
     */
    public void importMedia(Path targetDir) throws IOException
    {
        // don't import if the media is already in the target directory
        if (targetDir.compareTo(mediaFile.getParent()) != 0)
        {
            Path mediaFileName = mediaFile.getFileName();
            Path targetPath = JFlavourPathManager.getAvailablePath(targetDir, mediaFileName);

            //overwrite existing file, if exists
            CopyOption[] options = new CopyOption[]{
              StandardCopyOption.REPLACE_EXISTING,
              StandardCopyOption.COPY_ATTRIBUTES
            }; 
            Files.copy(mediaFile, targetPath, options);
            
            // for the strange case that the image file was already imported, but to a different directory
            // we now call the dispose method to remove the old image and leave the copy we just made.
            dispose();
            
            mediaFile = targetPath;
            imported = true;
        }
    }
    
    /**
     * Load the media into memory so it is ready for presenting. If it is already loaded do nothing.
     * @throws java.io.IOException
     */
    public abstract void load() throws IOException;
    
    /**
     * unload the media from memory. If it is not loaded do nothing.
     */
    public abstract void unload();
    
    public void dispose() throws IOException
    {
        if (imported)
        {
            Files.deleteIfExists(mediaFile);
        }
    }
    
    @Override
    public String toString()
    {
        return mediaFile.toString();
    }
    
    public String toShortString()
    {
        return mediaFile.getFileName().toString();
    }
    
}
