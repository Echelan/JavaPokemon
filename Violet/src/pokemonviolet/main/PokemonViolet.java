/*
 *  Pokemon Violet - A University Project by Andres Movilla
 *  Pokemon COPYRIGHT 2002-2016 Pokemon.
 *  COPYRIGHT 1995-2016 Nintendo/Creatures Inc./GAME FREAK inc. TRADEMARK, REGISTERED TRADEMARK
 *  and Pokemon character names are trademarks of Nintendo.
 *  No copyright or trademark infringement is intended in using Pokemon content on Pokemon Violet.
 */
package pokemonviolet.main;

import java.awt.Graphics;
import java.awt.MediaTracker;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JWindow;
import pokemonviolet.model.Handler;

/**
 *
 * @author Andres
 */
public class PokemonViolet {

	private static Handler h;
	
	public static void main(String[] args) throws InterruptedException {

		SplashWindow s = new SplashWindow();

		pokemonviolet.data.NIC.loadData();

		h = new Handler();

		try {
			Thread.sleep(1000);
		} catch (InterruptedException ex) {
		}
		
		s.dispose();
		h.canContinue();
	}	
	
	private static class SplashWindow extends JWindow {

		BufferedImage image;
		MediaTracker media;

		/**
		 * Create a splash window.
		 */
		public SplashWindow() {
			media = new MediaTracker(this);

			try {
				image = ImageIO.read(new File("assets/splashImage.png"));
				media.addImage(image, 0);
				media.waitForID(0);
			} catch (Exception ex) {
			}

			setSize(image.getWidth(), image.getHeight());
			setLocationRelativeTo(null);
			setVisible(true);
		}

		@Override
		public void paint(Graphics g) {
			g.drawImage(image, 0, 0, this);
		}
	}
}
