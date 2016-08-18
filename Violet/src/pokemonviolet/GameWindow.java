/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokemonviolet;

import java.awt.Dimension;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;


/**
 * @author movillaf
 */
public class GameWindow extends JFrame implements WindowListener, ActionListener {

    /**
     * @param args the command line arguments
     */
    
    public Pokemon enemy;
    final JTextField PokeInfo;
    final JButton spawnPokemans;
    
    void performactionthing(ActionEvent evt){
        Random rnd = new Random();
        enemy = new Pokemon(rnd.nextInt(151));
    }
    
    public GameWindow(){
		
        setLayout(null);
        setVisible(true);
        setSize(600,500);
        setTitle("Pokemon Violet");
        setResizable(false);
        setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
     
        spawnPokemans = new JButton();
        spawnPokemans.setText("Spawn");
        spawnPokemans.addActionListener(this);
        spawnPokemans.setBounds(100, 200, 150, 60);
        add(spawnPokemans);
        
        PokeInfo = new JTextField();
        PokeInfo.setBounds(40,50,150,60);
        add(PokeInfo);
        
    }

    public static void main(String[] args) throws InterruptedException{
		
		SplashWindow splash = new SplashWindow();
		
		Thread.sleep(100);
		
		splash.setVisible(false);
		splash = null;
		GameWindow gameWindow=new GameWindow();
		
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

	@Override
	public void actionPerformed(ActionEvent ae) {
		Random rnd = new Random();
		enemy = new Pokemon(rnd.nextInt(151));
	}
        
}
