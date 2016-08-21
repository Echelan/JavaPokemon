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
	static final private int POKEIMGBIGWIDTH = 160;
	static final private int POKEIMGBIGHEIGHT = 160;
	static final private int POKEIMGSMALLWIDTH = 40;
	static final private int POKEIMGSMALLHEIGHT = 40;
	static final private int ITEMIMGWIDTH = 30;
	static final private int ITEMIMGHEIGHT = 30;
    
    private Pokemon currentPokemon;
    private static BufferedImage allPokemonBig;
    private static BufferedImage allPokemonSmall;
    private static BufferedImage allItems;
	private ImageIcon pokemonImgDisplay;
	private ImageIcon[] playerTeamPokemonImgDisplay;
    private final JTextField pokemonNameDisplay;
    private final JTextField pokemonWeightDisplay;
    private final JTextField pokemonHeightDisplay;
	private final JTextField throwDialog;
	private final JTextField buyDialog;
	private final JTextField amountDialog;
    private final JButton walkBtn;
    private final JButton evolveBtn;
	private final JButton throwPokeBtn;
	private final JButton throwGreatBtn;
	private final JButton throwUltraBtn;
	private final JButton throwMasterBtn;
	private final JButton buyPokeBtn;
	private final JButton buyGreatBtn;
	private final JButton buyUltraBtn;
	private final JButton buyMasterBtn;
	private final JLabel[] imgContainerTeam;
	private final JLabel imgContainerEnemy;
	private final JTextField amountDisplayPoke;
	private final JTextField amountDisplayGreat;
	private final JTextField amountDisplayUltra;
	private final JTextField amountDisplayMaster;
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
		
		throwDialog = new JTextField("Throw:");
        throwDialog.setBounds(10, 260, 60, 50);
		throwDialog.setEditable(false);
		add(throwDialog);
		
		int xItem = (int)(Math.floor((double)(new Item("POKEBALL").getId()-1)%24)*ITEMIMGWIDTH);
		int yItem = (int)(Math.floor((double)(new Item("POKEBALL").getId()-1)/24)*ITEMIMGHEIGHT);
		
        throwPokeBtn = new JButton(new ImageIcon (allItems.getSubimage(xItem, yItem, ITEMIMGWIDTH, ITEMIMGHEIGHT)));
		throwPokeBtn.addActionListener(this);
        throwPokeBtn.setBounds(80, 260, 50, 50);
        add(throwPokeBtn);
     
		xItem = (int)(Math.floor((double)(new Item("GREATBALL").getId()-1)%24)*ITEMIMGWIDTH);
		yItem = (int)(Math.floor((double)(new Item("GREATBALL").getId()-1)/24)*ITEMIMGHEIGHT);
		
        throwGreatBtn = new JButton(new ImageIcon (allItems.getSubimage(xItem, yItem, ITEMIMGWIDTH, ITEMIMGHEIGHT)));
		throwGreatBtn.addActionListener(this);
        throwGreatBtn.setBounds(140, 260, 50, 50);
        add(throwGreatBtn);
     
		xItem = (int)(Math.floor((double)(new Item("ULTRABALL").getId()-1)%24)*ITEMIMGWIDTH);
		yItem = (int)(Math.floor((double)(new Item("ULTRABALL").getId()-1)/24)*ITEMIMGHEIGHT);
     
        throwUltraBtn = new JButton(new ImageIcon (allItems.getSubimage(xItem, yItem, ITEMIMGWIDTH, ITEMIMGHEIGHT)));
		throwUltraBtn.addActionListener(this);
        throwUltraBtn.setBounds(200, 260, 50, 50);
        add(throwUltraBtn);
     
		xItem = (int)(Math.floor((double)(new Item("MASTERBALL").getId()-1)%24)*ITEMIMGWIDTH);
		yItem = (int)(Math.floor((double)(new Item("MASTERBALL").getId()-1)/24)*ITEMIMGHEIGHT);
     
        throwMasterBtn = new JButton(new ImageIcon (allItems.getSubimage(xItem, yItem, ITEMIMGWIDTH, ITEMIMGHEIGHT)));
		throwMasterBtn.addActionListener(this);
        throwMasterBtn.setBounds(260, 260, 50, 50);
        add(throwMasterBtn);
		
		buyDialog = new JTextField("Buy:");
        buyDialog.setBounds(10, 320, 60, 50);
		buyDialog.setEditable(false);
		add(buyDialog);
		
		xItem = (int)(Math.floor((double)(new Item("POKEBALL").getId()-1)%24)*ITEMIMGWIDTH);
		yItem = (int)(Math.floor((double)(new Item("POKEBALL").getId()-1)/24)*ITEMIMGHEIGHT);
		
        buyPokeBtn = new JButton(new ImageIcon (allItems.getSubimage(xItem, yItem, ITEMIMGWIDTH, ITEMIMGHEIGHT)));
		buyPokeBtn.addActionListener(this);
        buyPokeBtn.setBounds(80, 320, 50, 50);
        add(buyPokeBtn);
     
		xItem = (int)(Math.floor((double)(new Item("GREATBALL").getId()-1)%24)*ITEMIMGWIDTH);
		yItem = (int)(Math.floor((double)(new Item("GREATBALL").getId()-1)/24)*ITEMIMGHEIGHT);
		
        buyGreatBtn = new JButton(new ImageIcon (allItems.getSubimage(xItem, yItem, ITEMIMGWIDTH, ITEMIMGHEIGHT)));
		buyGreatBtn.addActionListener(this);
        buyGreatBtn.setBounds(140, 320, 50, 50);
        add(buyGreatBtn);
     
		xItem = (int)(Math.floor((double)(new Item("ULTRABALL").getId()-1)%24)*ITEMIMGWIDTH);
		yItem = (int)(Math.floor((double)(new Item("ULTRABALL").getId()-1)/24)*ITEMIMGHEIGHT);
     
        buyUltraBtn = new JButton(new ImageIcon (allItems.getSubimage(xItem, yItem, ITEMIMGWIDTH, ITEMIMGHEIGHT)));
		buyUltraBtn.addActionListener(this);
        buyUltraBtn.setBounds(200, 320, 50, 50);
        add(buyUltraBtn);
     
		xItem = (int)(Math.floor((double)(new Item("MASTERBALL").getId()-1)%24)*ITEMIMGWIDTH);
		yItem = (int)(Math.floor((double)(new Item("MASTERBALL").getId()-1)/24)*ITEMIMGHEIGHT);
     
        buyMasterBtn = new JButton(new ImageIcon (allItems.getSubimage(xItem, yItem, ITEMIMGWIDTH, ITEMIMGHEIGHT)));
		buyMasterBtn.addActionListener(this);
        buyMasterBtn.setBounds(260, 320, 50, 50);
        add(buyMasterBtn);
		
		amountDialog = new JTextField("Amount:");
        amountDialog.setBounds(10, 380, 60, 50);
		amountDialog.setEditable(false);
		add(amountDialog);
		
		amountDisplayPoke = new JTextField("0");
        amountDisplayPoke.setBounds(80, 380, 50, 50);
		amountDisplayPoke.setEditable(false);
		add(amountDisplayPoke);
		
		amountDisplayGreat = new JTextField("0");
        amountDisplayGreat.setBounds(140, 380, 50, 50);
		amountDisplayGreat.setEditable(false);
		add(amountDisplayGreat);
		
		amountDisplayUltra = new JTextField("0");
        amountDisplayUltra.setBounds(200, 380, 50, 50);
		amountDisplayUltra.setEditable(false);
		add(amountDisplayUltra);
		
		amountDisplayMaster = new JTextField("0");
        amountDisplayMaster.setBounds(260, 380, 50, 50);
		amountDisplayMaster.setEditable(false);
		add(amountDisplayMaster);
		
        walkBtn = new JButton();
        walkBtn.setText("Walk");
		walkBtn.addActionListener(this);
        walkBtn.setBounds(10, 180, 160, 70);
        add(walkBtn);
		
		evolveBtn = new JButton();
        evolveBtn.setText("Evolve wild Pokemon");
		evolveBtn.addActionListener(this);
        evolveBtn.setBounds(180, 180, 160, 70);
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
		
		imgContainerEnemy = new JLabel();
		int xBig = (int)(Math.floor((double)(151)%10)*POKEIMGBIGWIDTH);
		int yBig = (int)(Math.floor((double)(151)/10)*POKEIMGBIGHEIGHT);
		pokemonImgDisplay = new ImageIcon (allPokemonBig.getSubimage(xBig, yBig, POKEIMGBIGWIDTH, POKEIMGBIGHEIGHT));
		imgContainerEnemy.setIcon(pokemonImgDisplay);
		imgContainerEnemy.setBounds(180,10,POKEIMGBIGWIDTH,POKEIMGBIGHEIGHT);
		add(imgContainerEnemy);
		
		imgContainerTeam = new JLabel[6];
		playerTeamPokemonImgDisplay = new ImageIcon[6];
		int xSmall = (int)(Math.floor((double)(152)%10)*POKEIMGSMALLWIDTH);
		int ySmall = (int)(Math.floor((double)(152)/10)*POKEIMGSMALLHEIGHT);
		for (int i = 0; i < playerTeamPokemonImgDisplay.length; i++) {
			imgContainerTeam[i] = new JLabel();
			playerTeamPokemonImgDisplay[i] = new ImageIcon (allPokemonSmall.getSubimage(xSmall, ySmall, POKEIMGSMALLWIDTH, POKEIMGSMALLHEIGHT));
			imgContainerTeam[i].setIcon(playerTeamPokemonImgDisplay[i]);
			int xCalc = 350 + ((i%3)*(POKEIMGSMALLWIDTH+5));
			int yCalc = 180 + (int)((float)Math.floor(i/3)*(POKEIMGSMALLWIDTH+5));
			imgContainerTeam[i].setBounds(xCalc,yCalc,POKEIMGSMALLWIDTH,POKEIMGSMALLHEIGHT);
			add(imgContainerTeam[i]);
		}
		
		player = new Jugador ("Red",1);
		player.addItem("POKEBALL",15);
		updateTeamImg();
		updateBallAmounts();
		
        setVisible(true);
    }

    public static void main(String[] args) throws InterruptedException{
		
		SplashWindow splash = new SplashWindow();
		
		try { 
			allPokemonBig = ImageIO.read(new File("pokemonIconsBig.png"));
			allPokemonSmall = ImageIO.read(new File("pokemonIconsSmall.png"));
			allItems = ImageIO.read(new File("itemsIcons.png"));
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

		evolveBtn.setEnabled(false);

		int xBig = (int)(Math.floor((double)(151)%10)*POKEIMGBIGWIDTH);
		int yBig = (int)(Math.floor((double)(151)/10)*POKEIMGBIGHEIGHT);
		pokemonImgDisplay = new ImageIcon (allPokemonBig.getSubimage(xBig, yBig, POKEIMGBIGWIDTH, POKEIMGBIGHEIGHT));
		imgContainerEnemy.setIcon(pokemonImgDisplay);
	}

	public void throwBtnPress(Item ball) {
		boolean canDo = player.subItem(ball.getId());
		if (canDo){
			displayEvent("You throw a " + ball.getNameSingular() + "!");
			System.out.println(ball.getPokeRate());
			int shakes = 0;
			boolean caught = currentPokemon.tryCatch(ball.getPokeRate());

			if (caught){
				caught = currentPokemon.tryCatch(ball.getPokeRate());
				shakes = shakes + 1;

				if (caught){
					caught = currentPokemon.tryCatch(ball.getPokeRate());
					shakes = shakes + 1;

					if (caught){
						caught = currentPokemon.tryCatch(ball.getPokeRate());
						shakes = shakes + 1;

						if (caught){
							displayEvent("Gotcha! " + currentPokemon.getNameSpecies()+ " was caught!");

							currentPokemon.setBallType(ball.getNameInternal());
							int result = player.addPokemon(currentPokemon);
							switch (result) {
								case 1:
									displayEvent(currentPokemon.getNameSpecies()+ " was sent to the PC.");
								break;
								case 2:
									displayEvent("PC full! " + currentPokemon.getNameSpecies()+ " was released.");
								break;
								default:
									updateTeamImg();
								break;
							}

							cleanPokemonInfo();
						}
					}
				}
			}

			if (!caught){
				displayEvent("The Pokemon broke free after " + shakes + " shakes!");
			}
			updateBallAmounts();
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

		if (currentPokemon.getCanEvolve()){
			evolveBtn.setEnabled(true);
		}

		int xBig = (int)(Math.floor((double)(currentPokemon.getId()-1)%10)*POKEIMGBIGWIDTH);
		int yBig = (int)(Math.floor((double)(currentPokemon.getId()-1)/10)*POKEIMGBIGHEIGHT);
		pokemonImgDisplay = new ImageIcon (allPokemonBig.getSubimage(xBig, yBig, POKEIMGBIGWIDTH, POKEIMGBIGHEIGHT));
		imgContainerEnemy.setIcon(pokemonImgDisplay);
	}
	
	public void updateTeamImg(){
		for (int i = 0; i < player.getEquipo().length; i++) {
			if (i < player.getNumPokemonTeam()){
				int thisPokemonID = player.getEquipo()[i].getId();
				
				int xSmall = (int)(Math.floor((double)(thisPokemonID-1)%10)*POKEIMGSMALLWIDTH);
				int ySmall = (int)(Math.floor((double)(thisPokemonID-1)/10)*POKEIMGSMALLHEIGHT);
				playerTeamPokemonImgDisplay[i] = new ImageIcon (allPokemonSmall.getSubimage(xSmall, ySmall, POKEIMGSMALLWIDTH, POKEIMGSMALLHEIGHT));
				imgContainerTeam[i].setIcon(playerTeamPokemonImgDisplay[i]);
			}else{
				int xSmall = (int)(Math.floor((double)(152)%10)*POKEIMGSMALLWIDTH);
				int ySmall = (int)(Math.floor((double)(152)/10)*POKEIMGSMALLHEIGHT);
				playerTeamPokemonImgDisplay[i] = new ImageIcon (allPokemonSmall.getSubimage(xSmall, ySmall, POKEIMGSMALLWIDTH, POKEIMGSMALLHEIGHT));
				imgContainerTeam[i].setIcon(playerTeamPokemonImgDisplay[i]);
			}
		}
	}
	
	public void updateBallAmounts(){
		amountDisplayGreat.setText(""+player.getAmountItem("GREATBALL"));
		amountDisplayPoke.setText(""+player.getAmountItem("POKEBALL"));
		amountDisplayUltra.setText(""+player.getAmountItem("ULTRABALL"));
		amountDisplayMaster.setText(""+player.getAmountItem("MASTERBALL"));
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == walkBtn){
			walkBtnPress();
		}else if (e.getSource() == evolveBtn){
			evolveBtnPress();
		}else if (e.getSource() == throwGreatBtn){
			throwBtnPress(new Item("GREATBALL"));
		}else if (e.getSource() == throwPokeBtn){
			throwBtnPress(new Item("POKEBALL"));
		}else if (e.getSource() == throwMasterBtn){
			throwBtnPress(new Item("MASTERBALL"));
		}else if (e.getSource() == throwUltraBtn){
			throwBtnPress(new Item("ULTRABALL"));
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
