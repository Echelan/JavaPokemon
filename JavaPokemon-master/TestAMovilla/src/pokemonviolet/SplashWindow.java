/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokemonviolet;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import javax.swing.JWindow;

/**
 *
 * @author Andres
 */
public class SplashWindow extends JWindow{
	
	Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
	Image logo; 
	MediaTracker media;
	SplashWindow()
	{    
		try {	
			logo  = Toolkit.getDefaultToolkit().createImage("VioletSMALL.png"); 	 	
			media = new MediaTracker(this);
			media.addImage(logo,0);
			media.waitForID(0);
			this.setSize(logo.getWidth(null),logo.getHeight(null));
			this.setLocationRelativeTo(null);
			setVisible(true);
		}catch (Exception ex){
			System.out.println (ex.getMessage());
		}
	}
	
	public void paint (Graphics g)
	{
	    g.drawImage(logo,0,0,this);
	}
}
