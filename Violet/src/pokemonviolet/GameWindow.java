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
    private final JTextField pokemonWeightDisplay;
    private final JTextField pokemonHeightDisplay;
    private final JButton walkBtn;
    private final JButton throwBtn;
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
		walkBtn.addActionListener(this);
        walkBtn.setBounds(10, 10, 160, 70);
        add(walkBtn);
		
		throwBtn = new JButton();
        throwBtn.setText("Throw Pokeball");
		throwBtn.addActionListener(this);
        throwBtn.setBounds(180, 190, 160, 70);
		throwBtn.setEnabled(false);
        add(throwBtn);
        
        pokemonNameDisplay = new JTextField();
        pokemonNameDisplay.setBounds(10,100,160,70);
		pokemonNameDisplay.setEditable(false);
        add(pokemonNameDisplay);
        
        pokemonHeightDisplay = new JTextField();
        pokemonHeightDisplay.setBounds(10,190,160,70);
		pokemonHeightDisplay.setEditable(false);
        add(pokemonHeightDisplay);
        
        pokemonWeightDisplay = new JTextField();
        pokemonWeightDisplay.setBounds(10,280,160,70);
		pokemonWeightDisplay.setEditable(false);
        add(pokemonWeightDisplay);
        
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

	public void displayEvent(String event) {
		
		eventsTextDisplay.setText(event + "\n" + eventsTextDisplay.getText());
		
	}
	
	public void walkBtnPress() {
		stepsToSpawn = stepsToSpawn - 1;
		if (stepsToSpawn == 0){
			Random rnd = new Random();
			currentPokemon = new Pokemon(rnd.nextInt(150)+1);
			
			pokemonNameDisplay.setText(currentPokemon.getSpeciesName());
			pokemonHeightDisplay.setText(currentPokemon.getHeight() + " m.");
			pokemonWeightDisplay.setText(currentPokemon.getWeight() + " kg.");
			
			throwBtn.setEnabled(true);
			
			int x = (int)(Math.floor((double)(currentPokemon.getId()-1)%10)*POKEIMGWIDTH);
			int y = (int)(Math.floor((double)(currentPokemon.getId()-1)/10)*POKEIMGHEIGHT);
			pokemonImgDisplay = new ImageIcon (allPokemonImgMH.getSubimage(x, y, POKEIMGWIDTH, POKEIMGHEIGHT));
			imgContainerLabel.setIcon(pokemonImgDisplay);
				
			displayEvent("A wild " + currentPokemon.getSpeciesName() + " appeared!");
			
			stepsToSpawn = calcSteps();
		}else{
			currentPokemon = null;
			pokemonNameDisplay.setText(" ");
			pokemonHeightDisplay.setText(" ");
			pokemonWeightDisplay.setText(" ");
			
			throwBtn.setEnabled(false);
			
			int x = (int)(Math.floor((double)(151)%10)*POKEIMGWIDTH);
			int y = (int)(Math.floor((double)(151)/10)*POKEIMGHEIGHT);
			pokemonImgDisplay = new ImageIcon (allPokemonImgMH.getSubimage(x, y, POKEIMGWIDTH, POKEIMGHEIGHT));
			imgContainerLabel.setIcon(pokemonImgDisplay);
			
			displayEvent("You walk through the grass.");
		}
	}

	public void throwBtnPress() {
		throwBtn.setEnabled(false);
		displayEvent("You throw a Pokeball!");
		
		boolean caught = currentPokemon.tryCatch();
		
		if (caught){
			displayEvent("*shake*");
			caught = currentPokemon.tryCatch();
			try {
				Thread.sleep(200);
			} catch (InterruptedException ex) {
			}
				
			if (caught){
				displayEvent("*shake*");
				caught = currentPokemon.tryCatch();
				try {
					Thread.sleep(200);
				} catch (InterruptedException ex) {
				}
				
				if (caught){
					displayEvent("*shake*");
					caught = currentPokemon.tryCatch();
					try {
						Thread.sleep(200);
					} catch (InterruptedException ex) {
					}
				
					if (caught){
						try {
							Thread.sleep(200);
						} catch (InterruptedException ex) {
						}

						displayEvent("Gotcha! " + currentPokemon.getSpeciesName() + " was caught!");
						walkBtnPress();
					}
				}
			}
		}
		
		if (!caught){
			throwBtn.setEnabled(true);
			displayEvent("The Pokemon breaks free!");
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == walkBtn){
			walkBtnPress();
		}else if (e.getSource() == throwBtn){
			throwBtnPress();
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
