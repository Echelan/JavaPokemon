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
	static private int POKEIMGBIGWIDTH = 160;
	static private int POKEIMGBIGHEIGHT = 160;
	static private int POKEIMGSMALLWIDTH = 40;
	static private int POKEIMGSMALLHEIGHT = 40;
    
    private Pokemon currentPokemon;
    private final JTextField pokemonNameDisplay;
    private final JTextField pokemonWeightDisplay;
    private final JTextField pokemonHeightDisplay;
    private final JButton walkBtn;
    private final JButton throwBtn;
    private final JButton evolveBtn;
    private static BufferedImage allPokemonBig;
    private static BufferedImage allPokemonSmall;
	private ImageIcon pokemonImgDisplay;
	private ImageIcon[] playerTeamPokemonImgDisplay;
	private final JLabel[] teamImgContainerLabel;
	private final JLabel imgContainerLabel;
	private final JTextArea eventsTextDisplay;
	private int stepsToSpawn = calcSteps();
	private final Jugador player;
    
    public GameWindow(){
		
        setLayout(null);
        setSize(600,500);
        setTitle("Pokemon Violet");
        setResizable(false);
        setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
     
        walkBtn = new JButton();
        walkBtn.setText("Walk");
		walkBtn.addActionListener(this);
        walkBtn.setBounds(10, 180, 160, 70);
        add(walkBtn);
		
		throwBtn = new JButton();
        throwBtn.setText("Throw Pokeball");
		throwBtn.addActionListener(this);
        throwBtn.setBounds(180, 180, 160, 70);
		throwBtn.setEnabled(false);
        add(throwBtn);
		
		evolveBtn = new JButton();
        evolveBtn.setText("Evolve wild Pokemon");
		evolveBtn.addActionListener(this);
        evolveBtn.setBounds(10, 260, 160, 70);
		evolveBtn.setEnabled(false);
        add(evolveBtn);
        
        pokemonNameDisplay = new JTextField();
        pokemonNameDisplay.setBounds(10,10,160,47);
		pokemonNameDisplay.setEditable(false);
        add(pokemonNameDisplay);
        
        pokemonHeightDisplay = new JTextField();
        pokemonHeightDisplay.setBounds(10,67,160,47);
		pokemonHeightDisplay.setEditable(false);
        add(pokemonHeightDisplay);
        
        pokemonWeightDisplay = new JTextField();
        pokemonWeightDisplay.setBounds(10,123,160,47);
		pokemonWeightDisplay.setEditable(false);
        add(pokemonWeightDisplay);
        
		eventsTextDisplay = new JTextArea();
		eventsTextDisplay.setBounds(350, 10, 235, 160);
		eventsTextDisplay.setEditable(false);
		add(eventsTextDisplay);
		
		imgContainerLabel = new JLabel();
		int xBig = (int)(Math.floor((double)(151)%10)*POKEIMGBIGWIDTH);
		int yBig = (int)(Math.floor((double)(151)/10)*POKEIMGBIGHEIGHT);
		pokemonImgDisplay = new ImageIcon (allPokemonBig.getSubimage(xBig, yBig, POKEIMGBIGWIDTH, POKEIMGBIGHEIGHT));
		imgContainerLabel.setIcon(pokemonImgDisplay);
		imgContainerLabel.setBounds(180,10,POKEIMGBIGWIDTH,POKEIMGBIGHEIGHT);
		add(imgContainerLabel);
		
		teamImgContainerLabel = new JLabel[6];
		playerTeamPokemonImgDisplay = new ImageIcon[6];
		int xSmall = (int)(Math.floor((double)(152)%10)*POKEIMGSMALLWIDTH);
		int ySmall = (int)(Math.floor((double)(152)/10)*POKEIMGSMALLHEIGHT);
		for (int i = 0; i < playerTeamPokemonImgDisplay.length; i++) {
			teamImgContainerLabel[i] = new JLabel();
			playerTeamPokemonImgDisplay[i] = new ImageIcon (allPokemonSmall.getSubimage(xSmall, ySmall, POKEIMGSMALLWIDTH, POKEIMGSMALLHEIGHT));
			teamImgContainerLabel[i].setIcon(playerTeamPokemonImgDisplay[i]);
			int xCalc = 350 + ((i%3)*(POKEIMGSMALLWIDTH+5));
			int yCalc = 180 + (int)((float)Math.floor(i/3)*(POKEIMGSMALLWIDTH+5));
			teamImgContainerLabel[i].setBounds(xCalc,yCalc,POKEIMGSMALLWIDTH,POKEIMGSMALLHEIGHT);
			add(teamImgContainerLabel[i]);
		}
		
		player = new Jugador ("Red",1);
		updateTeamImg();
		
        setVisible(true);
    }

    public static void main(String[] args) throws InterruptedException{
		
		SplashWindow splash = new SplashWindow();
		
		try { 
			allPokemonBig = ImageIO.read(new File("pokemonIconsBig.png"));
			allPokemonSmall = ImageIO.read(new File("pokemonIconsSmall.png"));
		} catch (IOException ex) {
			Logger.getLogger(GameWindow.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		Thread.sleep(100);
		
		splash.setVisible(false);
		splash = null;
		
		GameWindow gameWindow=new GameWindow();
		
    }

	public void displayEvent(String event) {
		eventsTextDisplay.setText(event + "\n" + eventsTextDisplay.getText());
	}
	
	public void walkBtnPress() {
		stepsToSpawn = stepsToSpawn - 1;
		if (stepsToSpawn == 0){
			Random rnd = new Random();
			currentPokemon = new Pokemon(rnd.nextInt(150)+1);
			
			
			updateWildPokemon();
					
			displayEvent("A wild " + currentPokemon.getNameSpecies()+ " appeared!");
			
			stepsToSpawn = calcSteps();
		}else{
			cleanPokemonInfo();
			
			displayEvent("You walk through the grass.");
		}
	}
	
	public void cleanPokemonInfo(){
		currentPokemon = null;
		pokemonNameDisplay.setText(" ");
		pokemonHeightDisplay.setText(" ");
		pokemonWeightDisplay.setText(" ");

		throwBtn.setEnabled(false);
		evolveBtn.setEnabled(false);

		int xBig = (int)(Math.floor((double)(151)%10)*POKEIMGBIGWIDTH);
		int yBig = (int)(Math.floor((double)(151)/10)*POKEIMGBIGHEIGHT);
		pokemonImgDisplay = new ImageIcon (allPokemonBig.getSubimage(xBig, yBig, POKEIMGBIGWIDTH, POKEIMGBIGHEIGHT));
		imgContainerLabel.setIcon(pokemonImgDisplay);
	}

	public void throwBtnPress() {
		throwBtn.setEnabled(false);
		displayEvent("You throw a Pokeball!");
		int shakes = 0;
		boolean caught = currentPokemon.tryCatch();
		
		if (caught){
			caught = currentPokemon.tryCatch();
			shakes = shakes + 1;
			
			if (caught){
				caught = currentPokemon.tryCatch();
				shakes = shakes + 1;
				
				if (caught){
					caught = currentPokemon.tryCatch();
					shakes = shakes + 1;
					
					if (caught){
						displayEvent("Gotcha! " + currentPokemon.getNameSpecies()+ " was caught!");
						
						int result = player.addPokemon(currentPokemon);
						if (result == 1){
							displayEvent(currentPokemon.getNameSpecies()+ " was sent to the PC.");
						}else if (result == 2){
							displayEvent("PC full! " + currentPokemon.getNameSpecies()+ " was released.");
						}else{
							updateTeamImg();
						}
						
						cleanPokemonInfo();
					}
				}
			}
		}
		
		if (!caught){
			throwBtn.setEnabled(true);
			displayEvent("The Pokemon broke free after " + shakes + " shakes!");
		}
	}
	
	public void evolveBtnPress(){
		String pastName = currentPokemon.getNameSpecies();
		boolean couldEvolve = currentPokemon.evolve();
		
		if (couldEvolve){
			evolveBtn.setEnabled(false);
			updateWildPokemon();
			displayEvent(pastName + " evolved to a " + currentPokemon.getNameSpecies() + "!");
		}else{
			displayEvent(currentPokemon.getNameSpecies() + " couldn't evolve!");
		}
	}
	
	public void updateWildPokemon(){
			
		pokemonNameDisplay.setText(currentPokemon.getNameSpecies());
		pokemonHeightDisplay.setText(currentPokemon.getHeight() + " m.");
		pokemonWeightDisplay.setText(currentPokemon.getWeight() + " kg.");

		throwBtn.setEnabled(true);
		if (currentPokemon.getCanEvolve()){
			evolveBtn.setEnabled(true);
		}

		int xBig = (int)(Math.floor((double)(currentPokemon.getId()-1)%10)*POKEIMGBIGWIDTH);
		int yBig = (int)(Math.floor((double)(currentPokemon.getId()-1)/10)*POKEIMGBIGHEIGHT);
		pokemonImgDisplay = new ImageIcon (allPokemonBig.getSubimage(xBig, yBig, POKEIMGBIGWIDTH, POKEIMGBIGHEIGHT));
		imgContainerLabel.setIcon(pokemonImgDisplay);
	}
	
	public void updateTeamImg(){
		for (int i = 0; i < player.getEquipo().length; i++) {
			if (i < player.getNumPokemonTeam()){
				int thisPokemonID = player.getEquipo()[i].getId();
				
				int xSmall = (int)(Math.floor((double)(thisPokemonID-1)%10)*POKEIMGSMALLWIDTH);
				int ySmall = (int)(Math.floor((double)(thisPokemonID-1)/10)*POKEIMGSMALLHEIGHT);
				playerTeamPokemonImgDisplay[i] = new ImageIcon (allPokemonSmall.getSubimage(xSmall, ySmall, POKEIMGSMALLWIDTH, POKEIMGSMALLHEIGHT));
				teamImgContainerLabel[i].setIcon(playerTeamPokemonImgDisplay[i]);
			}else{
				int xSmall = (int)(Math.floor((double)(152)%10)*POKEIMGSMALLWIDTH);
				int ySmall = (int)(Math.floor((double)(152)/10)*POKEIMGSMALLHEIGHT);
				playerTeamPokemonImgDisplay[i] = new ImageIcon (allPokemonSmall.getSubimage(xSmall, ySmall, POKEIMGSMALLWIDTH, POKEIMGSMALLHEIGHT));
				teamImgContainerLabel[i].setIcon(playerTeamPokemonImgDisplay[i]);
			}
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == walkBtn){
			walkBtnPress();
		}else if (e.getSource() == throwBtn){
			throwBtnPress();
		}else if (e.getSource() == evolveBtn){
			evolveBtnPress();
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
