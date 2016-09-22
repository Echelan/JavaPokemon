/*
 *  Pokemon Violet - A University Project by Andres Movilla
 *  Pokemon COPYRIGHT 2002-2016 Pokemon.
 *  COPYRIGHT 1995-2016 Nintendo/Creatures Inc./GAME FREAK inc. TRADEMARK, REGISTERED TRADEMARK
 *  and Pokemon character names are trademarks of Nintendo.
 *  No copyright or trademark infringement is intended in using Pokemon content on Pokemon Violet.
 */
package pokemonviolet;

import java.awt.Color;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * 
 * @author Andres
 */
public class ClassTestWindow extends JFrame implements WindowListener, ActionListener {
	
	//<editor-fold defaultstate="collapsed" desc="Attributes">
		/**
		 * Events Display.
		 */
		private final JTextArea eventsTextDisplay;
	//</editor-fold>
	
	/**
	 * Create a new ClassTestWindow.
	 */
    public ClassTestWindow(){
        setLayout(null);
        setSize(600,500);
        setTitle("Pokemon Violet [CLASS TEST]");
        setResizable(false);
        setLocationRelativeTo(null);
	//	setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	//	setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		
		eventsTextDisplay = new JTextArea();
		eventsTextDisplay.setBounds(350, 10, 235, 160);
		eventsTextDisplay.setEditable(false);
		add(eventsTextDisplay);
	
		setVisible(true);
	//	setVisible(false);
	//	setVisible(visible);
    }

	/**
	 * Displays given event text.
	 * @param event Text to display on Display.
	 */
	public void displayEvent(String event) {
		eventsTextDisplay.setText(event + "\n" + eventsTextDisplay.getText());
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {

	}
	
	// <editor-fold defaultstate="collapsed" desc="Overriden JFrame Methods"> 
		@Override
		public void windowClosing(WindowEvent e) {
			dispose();
			System.exit(0);
		}

		@Override
		public void windowOpened(WindowEvent e) {
		}


		@Override
		public void windowActivated(WindowEvent e) {
		}


		@Override
		public void windowIconified(WindowEvent e) {
		}


		@Override
		public void windowDeiconified(WindowEvent e) {
		}


		@Override
		public void windowDeactivated(WindowEvent e) {
		}


		@Override
		public void windowClosed(WindowEvent e) {
		}
		// </editor-fold>
}
