/*
 *  Pokemon Violet - A University Project by Andres Movilla
 *  Pokemon COPYRIGHT 2002-2016 Pokemon.
 *  COPYRIGHT 1995-2016 Nintendo/Creatures Inc./GAME FREAK inc. TRADEMARK, REGISTERED TRADEMARK
 *  and Pokemon character names are trademarks of Nintendo.
 *  No copyright or trademark infringement is intended in using Pokemon content on Pokemon Violet.
 */
package pokemonviolet.view;


import pokemonviolet.control.KeyHandler;
import pokemonviolet.view.GameDisplay;
import java.awt.Color;
import java.awt.event.*;
import javax.swing.*;
import pokemonviolet.model.Handler;


/**
 * @author movillaf
 */
public class GameWindow extends JFrame implements WindowListener, ActionListener {
	
	// <editor-fold defaultstate="collapsed" desc="Attributes">
		/**
		 * Canvas.
		 */
		private final GameDisplay screen;
	// </editor-fold>
	
	/**
	 * Create a new GameWindow.
	 */
	public GameWindow() {
		setLayout(null);
		setSize(Handler.SCREEN_SIZE_X+8,Handler.SCREEN_SIZE_Y+31);
		setTitle("Pokemon Violet [GAME]");
		setResizable(false);
		setLocationRelativeTo(null);
	//	setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	//	setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		
		ImageIcon icon = new ImageIcon("assets/Nabu.png");
		this.setIconImage(icon.getImage());
	
		screen = new GameDisplay();
		screen.setBounds(1, 1, Handler.SCREEN_SIZE_X, Handler.SCREEN_SIZE_Y);
		screen.setBackground(Color.black);
		screen.setFocusable(false);
		add(screen);
		
		//Movement Mover = new KeyHandler();
		addKeyListener(new KeyHandler());
		
		Thread screenThread = new Thread(screen);
		
		setVisible(true);
	//	setVisible(false);
	//	setVisible(visible);
		
		screenThread.start();
	}
	
	//<editor-fold defaultstate="collapsed" desc="Overriden JFrame Methods">
		@Override
		public void actionPerformed(ActionEvent e) {

		}

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
	//</editor-fold>
}
