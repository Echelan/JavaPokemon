/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testamovilla;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JWindow;

/**
 *
 * @author movillaf
 */
public class TestAMovilla {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException {
		
		SplashWindow splash = new SplashWindow();
		ManejadorPokemon manejadorPokemon = new ManejadorPokemon();
		Thread.sleep(5000);
		GameWindow gameWindow = new GameWindow();
		splash.setVisible(false);
		splash = null;
    }
    
}