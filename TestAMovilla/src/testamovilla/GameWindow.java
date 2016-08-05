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

    JFrame gameWindow;
    
    public GameWindow() {
        gameWindow = new JFrame();
        Dimension dims1 = new Dimension(600,500);
        
        gameWindow.setVisible(true);
        gameWindow.setSize(dims1);
        gameWindow.setTitle("JavaPokemon");
        gameWindow.setResizable(false);
        gameWindow.setLocation(200, 200);
       
        
        Canvas canvas1;
        canvas1 = new Canvas();
    //    Dimension dims2 = new Dimension(100,100);
     //   canvas1.setSize(dims2);
    //    canvas1.setLocation(100, 100);
    //canvas1.setBounds(5, 5, 50, 50);
        canvas1.setBackground( Color.getHSBColor((float)33.6, (float)63, (float)46.7) );
    //    Color.getHSBColor((float)(33.6/360), (float)(63/100), (float)(46.7/100));
        gameWindow.getContentPane().add(canvas1);
        
    }
    /*
    public void showMenu(){
        JLabel title = new JLabel("Texto");
        gameWindow.getContentPane().add(title);
    }
    */
}
