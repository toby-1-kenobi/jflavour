/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sil.jflavourapi;

import java.nio.file.Path;
import java.nio.file.Paths;
import org.apache.commons.io.FilenameUtils;

/**
 * Manages paths for storage and retreival of JFlavour application data
 * @author toby
 */
public class JFlavourPathManager
{
    
    public static Path getDataDirectory()
    {
        Path directory;
        String os = (System.getProperty("os.name")).toUpperCase();
        if (os.contains("WIN")) {
            directory = Paths.get(System.getenv("AppData"), "JFlavour");
        }
        else if (os.contains("LINUX")) {
            directory = Paths.get(System.getProperty("user.home"), ".local", "share", "JFlavour");
        }
        else if (os.contains("MAC")) {
            directory = Paths.get(System.getProperty("user.home"), "Library", "Application Support", "JFlavour");
        }
        else {
            directory = Paths.get(System.getProperty("user.home"), "/.JFlavour");
        }
        return directory;
    }
    
    /**
     * Return a Path to a file that does not exists given a directory and target filename.
     * If the filename is not already used in a given directory then that filename is returned, otherwise a similar
     * filename should be found that is not used in that directory
     * @param directory The directory in which the availabl filename must be found
     * @param targetFileName the target filename to base the search on
     * @return the available Path
     */
    public static Path getAvailablePath(Path directory, Path targetFileName)
    {
        if (directory.resolve(targetFileName).toFile().exists())
        {
            int i = 1;
            String baseName = FilenameUtils.getBaseName(targetFileName.toString());
            String extension = FilenameUtils.getExtension(targetFileName.toString());
            while (directory.resolve(baseName + i + '.' + extension).toFile().exists())
            {
                ++i;
            }
            //TODO: We've just found a path that is available, bt from this point on it may become unavaible
            // at any time until it is used. So really we should use some sort of lock on the filesystem for this filename.
            targetFileName = Paths.get(baseName + i + '.' + extension);
        }
        return directory.resolve(targetFileName);
    }
}
