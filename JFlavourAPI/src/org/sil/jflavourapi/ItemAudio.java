/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.sil.jflavourapi;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author toby
 */
public class ItemAudio extends ItemMedia
{
    
    private Clip audio;

    public ItemAudio(Path audioFile)
    {
        super(audioFile);
    }

    @Override
    public void load() throws IOException
    {
        if (audio == null) {
            try {
                audio = AudioSystem.getClip();
                AudioInputStream soundIn = AudioSystem.getAudioInputStream(mediaFile.toFile());
                AudioFormat format = soundIn.getFormat();
                DataLine.Info info = new DataLine.Info(Clip.class, format);
                audio = (Clip)AudioSystem.getLine(info);
                audio.open(soundIn);
            } catch (LineUnavailableException ex) {
                Logger.getLogger(ItemAudio.class.getName()).log(Level.SEVERE, null, ex);
            } catch (UnsupportedAudioFileException ex) {
                Logger.getLogger(ItemAudio.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void unload()
    {
        audio = null;
    }
    
    public void play() throws IOException
    {
        load();
        audio.start();
    }
    
    public void stop()
    {
        if (audio.isRunning()) audio.stop();
    }
}
