/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testamovilla;

/*
import java.awt.Canvas;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
*/
import java.awt.Dimension;
import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JLabel;


/**
 *p
 * 
 * @author movillaf
 */
public class GameWindow {

    JFrame gameWindow;
    
    public GameWindow() {
        gameWindow = new JFrame();
        Dimension dims = new Dimension(600,500);
        
        gameWindow.setVisible(true);
        gameWindow.setSize(dims);
        gameWindow.setTitle("JavaPokemon");
        gameWindow.setResizable(false);
        gameWindow.setLocation(200, 200);
    }
    
    public void showMenu(){
        JLabel title = new JLabel("Texto");
        gameWindow.getContentPane().add(title);
    }
}
