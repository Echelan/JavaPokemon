/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokemonviolet;


import java.awt.ComponentOrientation;
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
    
    public Pokemon currentPokemon;
    private final JTextField pokemonNameDisplay;
    private final JButton walkBtn;
    private static BufferedImage allPokemonImgMH;
	private ImageIcon pokemonImgDisplay;
	private final JLabel imgContainerLabel;
	private final JTextArea eventsTextDisplay;
	private int stepsToSpawn = calcSteps();
			
  //  void performactionthing(ActionEvent evt){
  //      Random rnd = new Random();
  //      currentPokemon = new Pokemon(rnd.nextInt(151));
  //  }
    
    public GameWindow(){
		
        setLayout(null);
        setVisible(true);
        setSize(600,500);
        setTitle("Pokemon Violet");
        setResizable(false);
        setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
     
        walkBtn = new JButton();
        walkBtn.setText("Walk");
		/*
        walkBtn.addActionListener(new ActionListener() {
			walkBtnPress(e);
        });
		*/
		walkBtn.addActionListener(this);
        walkBtn.setBounds(10, 10, 160, 70);
        add(walkBtn);
        
        pokemonNameDisplay = new JTextField();
        pokemonNameDisplay.setBounds(10,100,160,70);
		pokemonNameDisplay.setEditable(false);
        add(pokemonNameDisplay);
        
		eventsTextDisplay = new JTextArea();
		eventsTextDisplay.setBounds(360, 10, 230, 160);
		eventsTextDisplay.setEditable(false);
		add(eventsTextDisplay);
		
		imgContainerLabel = new JLabel();
		int x = (int)(Math.floor((double)(151)%10)*POKEIMGWIDTH);
		int y = (int)(Math.floor((double)(151)/10)*POKEIMGHEIGHT);
		pokemonImgDisplay = new ImageIcon (allPokemonImgMH.getSubimage(x, y, POKEIMGWIDTH, POKEIMGHEIGHT));
		imgContainerLabel.setIcon(pokemonImgDisplay);
		imgContainerLabel.setBounds(180,10,POKEIMGWIDTH,POKEIMGHEIGHT);
		add(imgContainerLabel);
			
		
    }

    public static void main(String[] args) throws InterruptedException{
		
		SplashWindow splash = new SplashWindow();
		
		try { 
			allPokemonImgMH = ImageIO.read(new File("MH all151.png"));
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

	public void walkBtnPress(ActionEvent ae) {
		stepsToSpawn = stepsToSpawn - 1;
		if (stepsToSpawn == 0){
			Random rnd = new Random();
			currentPokemon = new Pokemon(rnd.nextInt(150)+1);
			
			pokemonNameDisplay.setText(currentPokemon.getSpeciesName());
			
			int x = (int)(Math.floor((double)(currentPokemon.getId()-1)%10)*POKEIMGWIDTH);
			int y = (int)(Math.floor((double)(currentPokemon.getId()-1)/10)*POKEIMGHEIGHT);
			pokemonImgDisplay = new ImageIcon (allPokemonImgMH.getSubimage(x, y, POKEIMGWIDTH, POKEIMGHEIGHT));
			imgContainerLabel.setIcon(pokemonImgDisplay);
			
			eventsTextDisplay.setText("A wild " + currentPokemon.getSpeciesName() + " appeared!\n" + eventsTextDisplay.getText());
			
			stepsToSpawn = calcSteps();
		}else{
			currentPokemon = null;
			pokemonNameDisplay.setText(" ");
			
			int x = (int)(Math.floor((double)(151)%10)*POKEIMGWIDTH);
			int y = (int)(Math.floor((double)(151)/10)*POKEIMGHEIGHT);
			pokemonImgDisplay = new ImageIcon (allPokemonImgMH.getSubimage(x, y, POKEIMGWIDTH, POKEIMGHEIGHT));
			imgContainerLabel.setIcon(pokemonImgDisplay);
			
			eventsTextDisplay.setText("You walk through the grass.\n" + eventsTextDisplay.getText());
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == walkBtn){
			walkBtnPress(e);
		}
	}
	public int calcSteps(){
		int steps = 1;
		int numDados = 2;
		int numLados = 3;
		
		Random rnd = new Random();

		for (int i = 0; i < numDados; i++) {
		  steps = steps + (rnd.nextInt(numLados)+1);
		}
	//	System.out.println(steps);
		return steps;
	}
}
