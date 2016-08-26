/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
    private static final int POKEIMGBIGWIDTH = 160;
    private static final int POKEIMGBIGHEIGHT = 160;
    private static final int POKEIMGSMALLWIDTH = 40;
    private static final int POKEIMGSMALLHEIGHT = 40;
    private static final int ITEMIMGWIDTH = 30;
    private static final int ITEMIMGHEIGHT = 30;
    
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
    private final JTextField moneyDialog;
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
	
    /**
	* Create Class Test Window.
	*/
    public ClassTestWindow(){
        try {
                allPokemonBig = ImageIO.read(new File("pokemonIconsBig.png"));
                allPokemonSmall = ImageIO.read(new File("pokemonIconsSmall.png"));
                allItems = ImageIO.read(new File("itemsIcons.png"));
        } catch (IOException ex) {

        }
		
        setLayout(null);
        setSize(600,500);
        setTitle("Pokemon Violet [CLASS TEST]");
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
        throwPokeBtn.setEnabled(false);
        add(throwPokeBtn);
     
        xItem = (int)(Math.floor((double)(new Item("GREATBALL").getId()-1)%24)*ITEMIMGWIDTH);
        yItem = (int)(Math.floor((double)(new Item("GREATBALL").getId()-1)/24)*ITEMIMGHEIGHT);
		
        throwGreatBtn = new JButton(new ImageIcon (allItems.getSubimage(xItem, yItem, ITEMIMGWIDTH, ITEMIMGHEIGHT)));
        throwGreatBtn.addActionListener(this);
        throwGreatBtn.setBounds(140, 260, 50, 50);
        throwGreatBtn.setEnabled(false);
        add(throwGreatBtn);
     
        xItem = (int)(Math.floor((double)(new Item("ULTRABALL").getId()-1)%24)*ITEMIMGWIDTH);
        yItem = (int)(Math.floor((double)(new Item("ULTRABALL").getId()-1)/24)*ITEMIMGHEIGHT);
     
        throwUltraBtn = new JButton(new ImageIcon (allItems.getSubimage(xItem, yItem, ITEMIMGWIDTH, ITEMIMGHEIGHT)));
        throwUltraBtn.addActionListener(this);
        throwUltraBtn.setBounds(200, 260, 50, 50);
        throwUltraBtn.setEnabled(false);
        add(throwUltraBtn);
     
        xItem = (int)(Math.floor((double)(new Item("MASTERBALL").getId()-1)%24)*ITEMIMGWIDTH);
        yItem = (int)(Math.floor((double)(new Item("MASTERBALL").getId()-1)/24)*ITEMIMGHEIGHT);
     
        throwMasterBtn = new JButton(new ImageIcon (allItems.getSubimage(xItem, yItem, ITEMIMGWIDTH, ITEMIMGHEIGHT)));
        throwMasterBtn.addActionListener(this);
        throwMasterBtn.setBounds(260, 260, 50, 50);
        throwMasterBtn.setEnabled(false);
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
        buyPokeBtn.setEnabled(false);
        add(buyPokeBtn);
     
        xItem = (int)(Math.floor((double)(new Item("GREATBALL").getId()-1)%24)*ITEMIMGWIDTH);
        yItem = (int)(Math.floor((double)(new Item("GREATBALL").getId()-1)/24)*ITEMIMGHEIGHT);
		
        buyGreatBtn = new JButton(new ImageIcon (allItems.getSubimage(xItem, yItem, ITEMIMGWIDTH, ITEMIMGHEIGHT)));
        buyGreatBtn.addActionListener(this);
        buyGreatBtn.setBounds(140, 320, 50, 50);
        buyGreatBtn.setEnabled(false);
        add(buyGreatBtn);
     
        xItem = (int)(Math.floor((double)(new Item("ULTRABALL").getId()-1)%24)*ITEMIMGWIDTH);
        yItem = (int)(Math.floor((double)(new Item("ULTRABALL").getId()-1)/24)*ITEMIMGHEIGHT);
     
        buyUltraBtn = new JButton(new ImageIcon (allItems.getSubimage(xItem, yItem, ITEMIMGWIDTH, ITEMIMGHEIGHT)));
        buyUltraBtn.addActionListener(this);
        buyUltraBtn.setBounds(200, 320, 50, 50);
        buyUltraBtn.setEnabled(false);
        add(buyUltraBtn);
     
        xItem = (int)(Math.floor((double)(new Item("MASTERBALL").getId()-1)%24)*ITEMIMGWIDTH);
        yItem = (int)(Math.floor((double)(new Item("MASTERBALL").getId()-1)/24)*ITEMIMGHEIGHT);
     
        buyMasterBtn = new JButton(new ImageIcon (allItems.getSubimage(xItem, yItem, ITEMIMGWIDTH, ITEMIMGHEIGHT)));
        buyMasterBtn.addActionListener(this);
        buyMasterBtn.setBounds(260, 320, 50, 50);
        buyMasterBtn.setEnabled(false);
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
        pokemonNameDisplay.setForeground(Color.black);
        pokemonNameDisplay.setBackground(UIManager.getColor("TextField.background"));
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

        moneyDialog = new JTextField("$ 0");
        moneyDialog.setBounds(490, 180, 95, 85);
        moneyDialog.setEditable(false);
        add(moneyDialog);

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

        updateTeamImg();
        updateBallAmounts();
        updateButtons();
		
        setVisible(true);
    }

	/**
	 * Displays given event text.
	 * @param event Text to display on Display.
	 */
	public void displayEvent(String event) {
		eventsTextDisplay.setText(event + "\n" + eventsTextDisplay.getText());
	}
	
	/**
	 * Walk button press event method.
	 * Takes a step, spawns Pokemon if need be, updates buttons.
	 */
	public void walkBtnPress() {
		Game.stepsToSpawn = Game.stepsToSpawn - 1;
		
		Game.player.setDinero(Game.player.getDinero()+100);
		moneyDialog.setText("$ " + Game.player.getDinero());
		
		updateButtons();
		
		if (Game.stepsToSpawn == 0){
			Random rnd = new Random();
			Game.currentPokemon = new Pokemon(rnd.nextInt(150)+1);
			
			updateWildPokemon();
					
			displayEvent("A wild " + Game.currentPokemon.getNameSpecies()+ " appeared!");
			
			Game.stepsToSpawn = Game.roll(1,2,3);
		}else{
			cleanPokemonInfo();
			
			displayEvent("You walk through the grass.");
		}
	}
	
	/**
	 * Update buttons depending on situation.
	 */
	public void updateButtons(){
		if (Game.currentPokemon == null){
			evolveBtn.setEnabled(false);
			throwGreatBtn.setEnabled(false);
			throwMasterBtn.setEnabled(false);
			throwPokeBtn.setEnabled(false);
			throwUltraBtn.setEnabled(false);
		}else{
			if (Game.currentPokemon.getCanEvolve()){
				evolveBtn.setEnabled(true);
			}else{
				evolveBtn.setEnabled(false);
			}
			if (Game.player.getAmountItem("GREATBALL")>0){
				throwGreatBtn.setEnabled(true);
			}else{
				throwGreatBtn.setEnabled(false);
			}
			if (Game.player.getAmountItem("POKEBALL")>0){
				throwPokeBtn.setEnabled(true);
			}else{
				throwPokeBtn.setEnabled(false);
			}
			if (Game.player.getAmountItem("ULTRABALL")>0){
				throwUltraBtn.setEnabled(true);
			}else{
				throwUltraBtn.setEnabled(false);
			}
			if (Game.player.getAmountItem("MASTERBALL")>0){
				throwMasterBtn.setEnabled(true);
			}else{
				throwMasterBtn.setEnabled(false);
			}
		}
		if (Game.player.getDinero()>=new Item("GREATBALL").getPrice()){
			buyGreatBtn.setEnabled(true);
		}else{
			buyGreatBtn.setEnabled(false);
		}
		if (Game.player.getDinero()>=new Item("POKEBALL").getPrice()){
			buyPokeBtn.setEnabled(true);
		}else{
			buyPokeBtn.setEnabled(false);
		}
		if (Game.player.getDinero()>=new Item("ULTRABALL").getPrice()){
			buyUltraBtn.setEnabled(true);
		}else{
			buyUltraBtn.setEnabled(false);
		}
		if (Game.player.getDinero()>=new Item("MASTERBALL").getPrice()){
			buyMasterBtn.setEnabled(true);
		}else{
			buyMasterBtn.setEnabled(false);
		}
	}
	
	/**
	 * Clean current Pokemon and all UI information about it.
	 */
	public void cleanPokemonInfo(){
		Game.currentPokemon = null;
		pokemonNameDisplay.setText(" ");
		pokemonHeightDisplay.setText(" ");
		pokemonWeightDisplay.setText(" ");
		
		pokemonNameDisplay.setForeground(Color.black);
		pokemonNameDisplay.setBackground(UIManager.getColor("TextField.background"));
		
		updateButtons();
		
		int xBig = (int)(Math.floor((double)(151)%10)*POKEIMGBIGWIDTH);
		int yBig = (int)(Math.floor((double)(151)/10)*POKEIMGBIGHEIGHT);
		pokemonImgDisplay = new ImageIcon (allPokemonBig.getSubimage(xBig, yBig, POKEIMGBIGWIDTH, POKEIMGBIGHEIGHT));
		imgContainerEnemy.setIcon(pokemonImgDisplay);
	}

	/**
	 * Throw button press event method.
	 * Checks if Player can throw given Pokeball, does shake checks and adds Pokemon to player if need be.
	 * @param ball 
	 */
	public void throwBtnPress(Item ball) {
		boolean canDo = Game.player.subItem(ball.getId());
		if (canDo){
			displayEvent("You throw a " + ball.getNameSingular() + "!");
			int shakes = 0;
			boolean caught = Game.currentPokemon.tryCatch(ball.getPokeRate());

			if (caught){
				caught = Game.currentPokemon.tryCatch(ball.getPokeRate());
				shakes = shakes + 1;

				if (caught){
					caught = Game.currentPokemon.tryCatch(ball.getPokeRate());
					shakes = shakes + 1;

					if (caught){
						caught = Game.currentPokemon.tryCatch(ball.getPokeRate());
						shakes = shakes + 1;

						if (caught){
							displayEvent("Gotcha! " + Game.currentPokemon.getNameSpecies()+ " was caught!");

							Game.currentPokemon.setBallType(ball.getNameInternal());
							int result = Game.player.addPokemon(Game.currentPokemon);
							switch (result) {
								case 1:
									displayEvent(Game.currentPokemon.getNameSpecies()+ " was sent to the PC.");
								break;
								case 2:
									displayEvent("PC full! " + Game.currentPokemon.getNameSpecies()+ " was released.");
								break;
								default:
									updateTeamImg();
								break;
							}
							
							Game.player.setDinero(Game.player.getDinero() + (int)(((float)Math.floor(Game.currentPokemon.getId()/10))*100));
							moneyDialog.setText("$ " + Game.player.getDinero());
							
							cleanPokemonInfo();
						}
					}
				}
			}

			if (!caught){
				displayEvent("The Pokemon broke free after " + shakes + " shakes!");
			}
			updateBallAmounts();
			updateButtons();
		}
	}
	
	/**
	 * Evolves current wild Pokemon.
	 */
	public void evolveBtnPress(){
		String pastName = Game.currentPokemon.getNameSpecies();
		boolean couldEvolve = Game.currentPokemon.evolve();
		
		if (couldEvolve){
			updateWildPokemon();
			displayEvent(pastName + " evolved to a " + Game.currentPokemon.getNameSpecies() + "!");
		}else{
			displayEvent(Game.currentPokemon.getNameSpecies() + " couldn't evolve!");
		}
	}
	
	/**
	 * Update UI with current wild Pokemon information.
	 */
	public void updateWildPokemon(){
			
		pokemonNameDisplay.setText(Game.currentPokemon.getNameSpecies());
		pokemonHeightDisplay.setText(Game.currentPokemon.getHeight() + " m.");
		pokemonWeightDisplay.setText(Game.currentPokemon.getWeight() + " kg.");
		
		float hue, saturation, brightness;
		switch(Game.currentPokemon.getColor()){
			case "Black":
				hue = (float)0/360;
				saturation = (float)0/100;
				brightness = (float)0/100;
				
				pokemonNameDisplay.setForeground(Color.white);
				pokemonNameDisplay.setBackground(Color.getHSBColor(hue, saturation, brightness));
			break;
			case "Blue":
				hue = (float)209/360;
				saturation = (float)100/100;
				brightness = (float)100/100;
				
				pokemonNameDisplay.setForeground(Color.white);
				pokemonNameDisplay.setBackground(Color.getHSBColor(hue, saturation, brightness));
			break;
			case "Brown":
				hue = (float)30.2/360;
				saturation = (float)74.5/100;
				brightness = (float)64.7/100;
				
				pokemonNameDisplay.setForeground(Color.white);
				pokemonNameDisplay.setBackground(Color.getHSBColor(hue, saturation, brightness));
			break;
			case "Gray":
				hue = (float)0/360;
				saturation = (float)0/100;
				brightness = (float)50.2/100;
				
				pokemonNameDisplay.setForeground(Color.black);
				pokemonNameDisplay.setBackground(Color.getHSBColor(hue, saturation, brightness));
			break;
			case "Green":
				hue = (float)120/360;
				saturation = (float)75.6/100;
				brightness = (float)80.4/100;
				
				pokemonNameDisplay.setForeground(Color.black);
				pokemonNameDisplay.setBackground(Color.getHSBColor(hue, saturation, brightness));
			break;
			case "Pink":
				hue = (float)349.5/360;
				saturation = (float)24.7/100;
				brightness = (float)100/100;
				
				pokemonNameDisplay.setForeground(Color.black);
				pokemonNameDisplay.setBackground(Color.getHSBColor(hue, saturation, brightness));
			break;
			case "Purple":
				hue = (float)297.2/360;
				saturation = (float)100/100;
				brightness = (float)82.7/100;
				
				pokemonNameDisplay.setForeground(Color.white);
				pokemonNameDisplay.setBackground(Color.getHSBColor(hue, saturation, brightness));
			break;
			case "Red":
				hue = (float)0/360;
				saturation = (float)55.1/100;
				brightness = (float)80.4/100;
				
				pokemonNameDisplay.setForeground(Color.white);
				pokemonNameDisplay.setBackground(Color.getHSBColor(hue, saturation, brightness));
			break;
			case "White":
				hue = (float)0/360;
				saturation = (float)0/100;
				brightness = (float)100/100;
				
				pokemonNameDisplay.setForeground(Color.black);
				pokemonNameDisplay.setBackground(Color.getHSBColor(hue, saturation, brightness));
			break;
			case "Yellow":
				hue = (float)50.1/360;
				saturation = (float)100/100;
				brightness = (float)100/100;
				
				pokemonNameDisplay.setForeground(Color.black);
				pokemonNameDisplay.setBackground(Color.getHSBColor(hue, saturation, brightness));
			break;
			default:
				pokemonNameDisplay.setForeground(Color.black);
				pokemonNameDisplay.setBackground(UIManager.getColor("TextField.background"));
			break;
		}
		
		updateButtons();
		
		int xBig = (int)(Math.floor((double)(Game.currentPokemon.getId()-1)%10)*POKEIMGBIGWIDTH);
		int yBig = (int)(Math.floor((double)(Game.currentPokemon.getId()-1)/10)*POKEIMGBIGHEIGHT);
		pokemonImgDisplay = new ImageIcon (allPokemonBig.getSubimage(xBig, yBig, POKEIMGBIGWIDTH, POKEIMGBIGHEIGHT));
		imgContainerEnemy.setIcon(pokemonImgDisplay);
	}
	
	/**
	 * Update player team UI to accommodate new player team.
	 */
	public void updateTeamImg(){
		for (int i = 0; i < Game.player.getEquipo().length; i++) {
			if (i < Game.player.getNumPokemonTeam()){
				int thisPokemonID = Game.player.getEquipo()[i].getId();
				
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
	
	/**
	 * Update UI with current amount of each type of Pokeball.
	 */
	public void updateBallAmounts(){
		amountDisplayGreat.setText(""+Game.player.getAmountItem("GREATBALL"));
		amountDisplayPoke.setText(""+Game.player.getAmountItem("POKEBALL"));
		amountDisplayUltra.setText(""+Game.player.getAmountItem("ULTRABALL"));
		amountDisplayMaster.setText(""+Game.player.getAmountItem("MASTERBALL"));
	}
	
	/**
	 * Tries to buy item with given internal name, adds item if applicable.
	 * Updates UI accordingly.
	 * @param internalName Item internal name to buy.
	 */
	public void buyItem(String internalName){
		if (Game.player.reduceMoney(new Item(internalName).getPrice())){
			Game.player.addItem(internalName);
			moneyDialog.setText("$ " + Game.player.getDinero());
			updateBallAmounts();
			updateButtons();
		}
	}
	
	// <editor-fold defaultstate="collapsed" desc="Overriden JFrame Methods"> 
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
			}else if (e.getSource() == buyGreatBtn){
				buyItem("GREATBALL");
			}else if (e.getSource() == buyPokeBtn){
				buyItem("POKEBALL");
			}else if (e.getSource() == buyMasterBtn){
				buyItem("MASTERBALL");
			}else if (e.getSource() == buyUltraBtn){
				buyItem("ULTRABALL");
			}
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
		// </editor-fold>
}
