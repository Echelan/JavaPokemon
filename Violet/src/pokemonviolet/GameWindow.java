/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokemonviolet;


import java.awt.Color;
import java.awt.event.*;
import javax.swing.*;


/**
 * @author movillaf
 */
public class GameWindow extends JFrame implements WindowListener, ActionListener {
	
	private final GameDisplay screen;
	
	public GameWindow() {
        setLayout(null);
        setSize(600,500);
        setTitle("Pokemon Violet [GAME]");
        setResizable(false);
        setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		screen = new GameDisplay();
		screen.setBounds(1, 1, 592, 469);
		screen.setBackground(Color.black);
		add(screen);
		
        setVisible(true);
	}
	
	
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
}
