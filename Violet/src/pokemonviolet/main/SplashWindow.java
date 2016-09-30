/*
 *  Pokemon Violet - A University Project by Andres Movilla
 *  Pokemon COPYRIGHT 2002-2016 Pokemon.
 *  COPYRIGHT 1995-2016 Nintendo/Creatures Inc./GAME FREAK inc. TRADEMARK, REGISTERED TRADEMARK
 *  and Pokemon character names are trademarks of Nintendo.
 *  No copyright or trademark infringement is intended in using Pokemon content on Pokemon Violet.
 */
package pokemonviolet.main;

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
	
	Image logo; 
	MediaTracker media;
	
	/**
	 * Create a splash window.
	 */
	public SplashWindow(){
		try {	
			logo  = Toolkit.getDefaultToolkit().createImage("splashImage.png"); 	 	
			media = new MediaTracker(this);
			media.addImage(logo,0);
			media.waitForID(0);
			this.setSize(logo.getWidth(null),logo.getHeight(null));
			this.setLocationRelativeTo(null);
			setVisible(true);
		}catch (Exception ex){
		//	System.out.println (ex.getMessage());
		}
	}
	
	public void paint (Graphics g)
	{
	    g.drawImage(logo,0,0,this);
	}
}
