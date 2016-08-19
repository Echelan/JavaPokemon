/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokemonviolet;


import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.*;


/**
 * @author movillaf
 */
public class GameWindow extends JFrame implements WindowListener, ActionListener {

    /**
     * @param args the command line arguments
     */
	static private int POKEIMGWIDTH = 160;
	static private int POKEIMGHEIGHT = 160;
    
    public Pokemon enemy;
    final JTextField PokeInfo;
    final JButton spawnPokemans;
    private static BufferedImage allPokemonMH;
	private ImageIcon pokemonImg;
	private final JLabel imgContainer;
			
  //  void performactionthing(ActionEvent evt){
  //      Random rnd = new Random();
  //      enemy = new Pokemon(rnd.nextInt(151));
  //  }
    
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
        spawnPokemans.setBounds(10, 10, 160, 70);
        add(spawnPokemans);
        
        PokeInfo = new JTextField();
        PokeInfo.setBounds(10,100,160,70);
		PokeInfo.setEditable(false);
        add(PokeInfo);
        
		imgContainer = new JLabel(pokemonImg);
		imgContainer.setBounds(180,10,POKEIMGWIDTH,POKEIMGHEIGHT);
		add(imgContainer);
			
		
    }

    public static void main(String[] args) throws InterruptedException{
		
		SplashWindow splash = new SplashWindow();
		
		try { 
			allPokemonMH = ImageIO.read(new File("MH all151.png"));
		} catch (IOException ex) {
			Logger.getLogger(GameWindow.class.getName()).log(Level.SEVERE, null, ex);
		}
		
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
		enemy = new Pokemon(rnd.nextInt(150)+1);
		PokeInfo.setText(enemy.getSpeciesName());
		int x = (int)(Math.floor((double)(enemy.getId()-1)%10)*POKEIMGWIDTH);
		int y = (int)(Math.floor((double)(enemy.getId()-1)/10)*POKEIMGHEIGHT);

		pokemonImg = new ImageIcon (allPokemonMH.getSubimage(x, y, POKEIMGWIDTH, POKEIMGHEIGHT));
		imgContainer.setIcon(pokemonImg);
		
	}
        
}
