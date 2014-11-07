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

    public ItemMedia(Path mediaFile)
    {
        this.mediaFile = mediaFile;
    }
    
    /**
     * Copy the media to the application's directory structure
     * @param targetDir the directory to move the media file to
     */
    public void importMedia(Path targetDir) throws IOException
    {
        Path mediaFileName = mediaFile.getFileName();
        Path targetPath = JFlavourPathManager.getAvailablePath(targetDir, mediaFileName);
        
        //overwrite existing file, if exists
        CopyOption[] options = new CopyOption[]{
          StandardCopyOption.REPLACE_EXISTING,
          StandardCopyOption.COPY_ATTRIBUTES
        }; 
        Files.copy(mediaFile, targetPath, options);
        mediaFile = targetPath;
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
