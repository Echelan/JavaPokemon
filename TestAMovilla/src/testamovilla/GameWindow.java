/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testamovilla;

/*
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
import java.awt.Canvas;
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

    private JFrame gameFrame;
    public Canvas gameCanvas;
    
    public GameWindow() {
        gameFrame = new JFrame();
        Dimension dims1 = new Dimension(600,500);
        
        gameFrame.setVisible(true);
        gameFrame.setSize(dims1);
        gameFrame.setTitle("JavaPokemon");
        gameFrame.setResizable(false);
        gameFrame.setLocation(200, 200);
       
        gameCanvas = new Canvas();
        gameCanvas.setBackground(Color.green);
        gameFrame.getContentPane().add(gameCanvas);
        
    }
    /*
    public void showMenu(){
        JLabel title = new JLabel("Texto");
        gameWindow.getContentPane().add(title);
    }
    */
}