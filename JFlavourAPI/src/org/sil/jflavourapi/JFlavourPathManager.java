/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sil.jflavourapi;

import java.nio.file.Path;
import java.nio.file.Paths;

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
}
