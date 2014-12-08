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
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author toby
 */
public class ItemAudio extends ItemMedia
{
    
    private Clip audio;
    private AudioFormat audioFormat;
    private AudioInputStream soundIn;
    private AudioInputStream undecodedSoundIn;

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
                soundIn = AudioSystem.getAudioInputStream(mediaFile.toFile());
                audioFormat = soundIn.getFormat();
                DataLine.Info info = new DataLine.Info(Clip.class, audioFormat);
                if (!AudioSystem.isLineSupported(info))
                {
                    // try decoding
                    AudioFormat decodedFormat = new AudioFormat(
                            AudioFormat.Encoding.PCM_SIGNED,
                            audioFormat.getSampleRate(),
                            16,
                            audioFormat.getChannels(),
                            audioFormat.getChannels() * 2,
                            audioFormat.getSampleRate(),
                            false);
                    audioFormat = decodedFormat;
                    undecodedSoundIn = soundIn;
                    soundIn = AudioSystem.getAudioInputStream(decodedFormat, undecodedSoundIn);
                    info = new DataLine.Info(Clip.class, audioFormat);
                }
                try {
                    // plan A: use a clip
                    audio = (Clip)AudioSystem.getLine(info);
                    audio.open(soundIn);
                } catch (Exception e) {
                    // something went wrong with plan A
                    // go to plan B
                    audio = null;
                    // we have set audioFormat and soundIn so when it's time to play
                    // we'll use these without a clip with the rawPlay method
                }
            } catch (UnsupportedAudioFileException ex) {
                unload();
                Logger.getLogger(ItemAudio.class.getName()).log(Level.SEVERE, null, ex);
            } catch (LineUnavailableException ex) {
                unload();
                Logger.getLogger(ItemAudio.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void unload()
    {
        try {
            if (soundIn != null) soundIn.close();
            if (undecodedSoundIn != null) undecodedSoundIn.close();
        } catch (IOException e) {
            // so we couldn't close one or more of these streams
            // what can you do?
        }
        audio = null;
        audioFormat = null;
        soundIn = null;
        undecodedSoundIn = null;
    }
    
    public void play() throws IOException
    {
        load();
        if (audio != null) {
            audio.start();
        } else if (audioFormat != null && soundIn != null) {
            try {
                // plan B
                rawPlay(audioFormat, soundIn);
            } catch (LineUnavailableException ex) {
                Logger.getLogger(ItemAudio.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            // we didn't manage to load the audio
        }
    }
    
    private void rawPlay(AudioFormat targetFormat, AudioInputStream din) throws IOException,                                                                                                LineUnavailableException
    {
        byte[] data = new byte[4096];
        SourceDataLine line = getLine(targetFormat); 
        if (line != null)
        {
          // Start
          line.start();
          int nBytesRead = 0, nBytesWritten = 0;
          while (nBytesRead != -1)
          {
              nBytesRead = din.read(data, 0, data.length);
              if (nBytesRead != -1) nBytesWritten = line.write(data, 0, nBytesRead);
          }
          // Stop
          line.drain();
          line.stop();
          line.close();
          din.close();
        }
    }
    
    private SourceDataLine getLine(AudioFormat audioFormat) throws LineUnavailableException
    {
        SourceDataLine res = null;
        DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
        res = (SourceDataLine) AudioSystem.getLine(info);
        res.open(audioFormat);
        return res;
    } 
    
    public void stop()
    {
        if (audio.isRunning()) audio.stop();
    }
}
