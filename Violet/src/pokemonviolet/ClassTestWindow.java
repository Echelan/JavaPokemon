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
		//<editor-fold defaultstate="collapsed" desc="Statics">
			/**
			 * Monster Hunter Pokemon Images Width.
			 */
			private static final int POKE_IMG_BIG_WIDTH = 160;
			/**
			 * Monster Hunter Pokemon Images Height.
			 */
			private static final int POKE_IMG_BIG_HEIGHT = 160;
			/**
			 * Pokemon Icon Images Width.
			 */
			private static final int POKE_IMG_SMALL_WIDTH = 40;
			/**
			 * Pokemon Icon Images Height.
			 */
			private static final int POKE_IMG_SMALL_HEIGHT = 40;
			/**
			 * Item Image Width.
			 */
			private static final int ITEM_IMG_WIDTH = 30;
			/**
			 * Item Image Height.
			 */
			private static final int ITEM_IMG_HEIGHT = 30;
		//</editor-fold>
		//<editor-fold defaultstate="collapsed" desc="All Elements Images">
			/**
			 * All Monster Hunter Pokemon Image.
			 */
			private static BufferedImage allPokemonBig;
			/**
			 * All Icon Pokemon Image.
			 */
			private static BufferedImage allPokemonSmall;
			/**
			 * All Items Image.
			 */
			private static BufferedImage allItems;
		//</editor-fold>
		//<editor-fold defaultstate="collapsed" desc="Current Pokemon UI">
				/**
				 * Current Pokemon Image.
				 */
				private ImageIcon pokemonImgDisplay;
				/**
				 * Current Pokemon Name Display.
				 */
				private final JTextField pokemonNameDisplay;
				/**
				 * Current Pokemon Weight Display.
				 */
				private final JTextField pokemonWeightDisplay;
				/**
				 * Current Pokemon Height Display.
				 */
				private final JTextField pokemonHeightDisplay;
			//</editor-fold>
		//<editor-fold defaultstate="collapsed" desc="Pokeball Throw">
			/**
			 * 'Throw' JLabel.
			 */
			private final JTextField throwDialog;
			/**
			 * Throw Pokeball Button.
			 */
			private final JButton throwPokeBtn;
			/**
			 * Throw Greatball Button.
			 */
			private final JButton throwGreatBtn;
			/**
			 * Throw Ultraball Button.
			 */
			private final JButton throwUltraBtn;
			/**
			 * Throw Masterball Button.
			 */
			private final JButton throwMasterBtn;
		//</editor-fold>
		//<editor-fold defaultstate="collapsed" desc="Pokeball Buy">
			/**
			 * 'Buy' JLabel.
			 */
			private final JTextField buyDialog;
			/**
			 * Buy Pokeball Button.
			 */
			private final JButton buyPokeBtn;
			/**
			 * Buy Greatball Button.
			 */
			private final JButton buyGreatBtn;
			/**
			 * Buy Ultraball Button.
			 */
			private final JButton buyUltraBtn;
			/**
			 * Buy Masterball Button.
			 */
			private final JButton buyMasterBtn;
		//</editor-fold>
		//<editor-fold defaultstate="collapsed" desc="Pokeball Amounts">
			/**
			 * 'Amount' JLabel.
			 */
			private final JTextField amountDialog;
			/**
			 * Amount Pokeball Label.
			 */
			private final JTextField amountDisplayPoke;
			/**
			 * Amount Greatball Label.
			 */
			private final JTextField amountDisplayGreat;
			/**
			 * Amount Ultraball Label.
			 */
			private final JTextField amountDisplayUltra;
			/**
			 * Amount Masterball Label.
			 */
			private final JTextField amountDisplayMaster;
		//</editor-fold>
		//<editor-fold defaultstate="collapsed" desc="General UI">
			/**
			 * Group of Player Pokemon Team Images.
			 */
			private ImageIcon[] playerTeamPokemonImgDisplay;
			/**
			 * Group of Player Pokemon Team Labels.
			 */
			private final JLabel[] imgContainerTeam;
			/**
			 * Player Current Money Display.
			 */
			private final JTextField moneyDialog;
			/**
			 * Walk Button.
			 */
			private final JButton walkBtn;
			/**
			 * Evolve Current Pokemon Button.
			 */
			private final JButton evolveBtn;
			/**
			 * Current Pokemon Image.
			 */
			private final JLabel imgContainerEnemy;
			/**
			 * Events Display.
			 */
			private final JTextArea eventsTextDisplay;
		//</editor-fold>
	//</editor-fold>
	
	/**
	 * Create a new ClassTestWindow.
	 * @param operation JFrame default close operation.
	 * @param visible This JFrame visibility.
	 */
    public ClassTestWindow(int operation, boolean visible){
        setLayout(null);
        setSize(600,500);
        setTitle("Pokemon Violet [CLASS TEST]");
        setResizable(false);
        setLocationRelativeTo(null);
	//	setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	//	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	//	setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setDefaultCloseOperation(operation);
		
        try {
			allPokemonBig = ImageIO.read(new File("pokemonIconsBig.png"));
			allPokemonSmall = ImageIO.read(new File("pokemonIconsSmall.png"));
			allItems = ImageIO.read(new File("itemsIcons.png"));
        } catch (IOException ex) {

        }
		
		//<editor-fold defaultstate="collapsed" desc="Formatting Code">
			throwDialog = new JTextField("Throw:");
			throwDialog.setBounds(10, 260, 60, 50);
			throwDialog.setEditable(false);
			add(throwDialog);

			int xItem = (int)(Math.floor((double)(new Item("POKEBALL").getId()-1)%24)*ITEM_IMG_WIDTH);
			int yItem = (int)(Math.floor((double)(new Item("POKEBALL").getId()-1)/24)*ITEM_IMG_HEIGHT);

			throwPokeBtn = new JButton(new ImageIcon (allItems.getSubimage(xItem, yItem, ITEM_IMG_WIDTH, ITEM_IMG_HEIGHT)));
			throwPokeBtn.addActionListener(this);
			throwPokeBtn.setBounds(80, 260, 50, 50);
			throwPokeBtn.setEnabled(false);
			add(throwPokeBtn);

			xItem = (int)(Math.floor((double)(new Item("GREATBALL").getId()-1)%24)*ITEM_IMG_WIDTH);
			yItem = (int)(Math.floor((double)(new Item("GREATBALL").getId()-1)/24)*ITEM_IMG_HEIGHT);

			throwGreatBtn = new JButton(new ImageIcon (allItems.getSubimage(xItem, yItem, ITEM_IMG_WIDTH, ITEM_IMG_HEIGHT)));
			throwGreatBtn.addActionListener(this);
			throwGreatBtn.setBounds(140, 260, 50, 50);
			throwGreatBtn.setEnabled(false);
			add(throwGreatBtn);

			xItem = (int)(Math.floor((double)(new Item("ULTRABALL").getId()-1)%24)*ITEM_IMG_WIDTH);
			yItem = (int)(Math.floor((double)(new Item("ULTRABALL").getId()-1)/24)*ITEM_IMG_HEIGHT);

			throwUltraBtn = new JButton(new ImageIcon (allItems.getSubimage(xItem, yItem, ITEM_IMG_WIDTH, ITEM_IMG_HEIGHT)));
			throwUltraBtn.addActionListener(this);
			throwUltraBtn.setBounds(200, 260, 50, 50);
			throwUltraBtn.setEnabled(false);
			add(throwUltraBtn);

			xItem = (int)(Math.floor((double)(new Item("MASTERBALL").getId()-1)%24)*ITEM_IMG_WIDTH);
			yItem = (int)(Math.floor((double)(new Item("MASTERBALL").getId()-1)/24)*ITEM_IMG_HEIGHT);

			throwMasterBtn = new JButton(new ImageIcon (allItems.getSubimage(xItem, yItem, ITEM_IMG_WIDTH, ITEM_IMG_HEIGHT)));
			throwMasterBtn.addActionListener(this);
			throwMasterBtn.setBounds(260, 260, 50, 50);
			throwMasterBtn.setEnabled(false);
			add(throwMasterBtn);

			buyDialog = new JTextField("Buy:");
			buyDialog.setBounds(10, 320, 60, 50);
			buyDialog.setEditable(false);
			add(buyDialog);

			xItem = (int)(Math.floor((double)(new Item("POKEBALL").getId()-1)%24)*ITEM_IMG_WIDTH);
			yItem = (int)(Math.floor((double)(new Item("POKEBALL").getId()-1)/24)*ITEM_IMG_HEIGHT);

			buyPokeBtn = new JButton(new ImageIcon (allItems.getSubimage(xItem, yItem, ITEM_IMG_WIDTH, ITEM_IMG_HEIGHT)));
			buyPokeBtn.addActionListener(this);
			buyPokeBtn.setBounds(80, 320, 50, 50);
			buyPokeBtn.setEnabled(false);
			add(buyPokeBtn);

			xItem = (int)(Math.floor((double)(new Item("GREATBALL").getId()-1)%24)*ITEM_IMG_WIDTH);
			yItem = (int)(Math.floor((double)(new Item("GREATBALL").getId()-1)/24)*ITEM_IMG_HEIGHT);

			buyGreatBtn = new JButton(new ImageIcon (allItems.getSubimage(xItem, yItem, ITEM_IMG_WIDTH, ITEM_IMG_HEIGHT)));
			buyGreatBtn.addActionListener(this);
			buyGreatBtn.setBounds(140, 320, 50, 50);
			buyGreatBtn.setEnabled(false);
			add(buyGreatBtn);

			xItem = (int)(Math.floor((double)(new Item("ULTRABALL").getId()-1)%24)*ITEM_IMG_WIDTH);
			yItem = (int)(Math.floor((double)(new Item("ULTRABALL").getId()-1)/24)*ITEM_IMG_HEIGHT);

			buyUltraBtn = new JButton(new ImageIcon (allItems.getSubimage(xItem, yItem, ITEM_IMG_WIDTH, ITEM_IMG_HEIGHT)));
			buyUltraBtn.addActionListener(this);
			buyUltraBtn.setBounds(200, 320, 50, 50);
			buyUltraBtn.setEnabled(false);
			add(buyUltraBtn);

			xItem = (int)(Math.floor((double)(new Item("MASTERBALL").getId()-1)%24)*ITEM_IMG_WIDTH);
			yItem = (int)(Math.floor((double)(new Item("MASTERBALL").getId()-1)/24)*ITEM_IMG_HEIGHT);

			buyMasterBtn = new JButton(new ImageIcon (allItems.getSubimage(xItem, yItem, ITEM_IMG_WIDTH, ITEM_IMG_HEIGHT)));
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
			int xBig = (int)(Math.floor((double)(151)%10)*POKE_IMG_BIG_WIDTH);
			int yBig = (int)(Math.floor((double)(151)/10)*POKE_IMG_BIG_HEIGHT);
			pokemonImgDisplay = new ImageIcon (allPokemonBig.getSubimage(xBig, yBig, POKE_IMG_BIG_WIDTH, POKE_IMG_BIG_HEIGHT));
			imgContainerEnemy.setIcon(pokemonImgDisplay);
			imgContainerEnemy.setBounds(180,10,POKE_IMG_BIG_WIDTH,POKE_IMG_BIG_HEIGHT);
			add(imgContainerEnemy);

			imgContainerTeam = new JLabel[6];
			playerTeamPokemonImgDisplay = new ImageIcon[6];
			int xSmall = (int)(Math.floor((double)(152)%10)*POKE_IMG_SMALL_WIDTH);
			int ySmall = (int)(Math.floor((double)(152)/10)*POKE_IMG_SMALL_HEIGHT);
			for (int i = 0; i < playerTeamPokemonImgDisplay.length; i++) {
				imgContainerTeam[i] = new JLabel();
				playerTeamPokemonImgDisplay[i] = new ImageIcon (allPokemonSmall.getSubimage(xSmall, ySmall, POKE_IMG_SMALL_WIDTH, POKE_IMG_SMALL_HEIGHT));
				imgContainerTeam[i].setIcon(playerTeamPokemonImgDisplay[i]);
				int xCalc = 350 + ((i%3)*(POKE_IMG_SMALL_WIDTH+5));
				int yCalc = 180 + (int)((float)Math.floor(i/3)*(POKE_IMG_SMALL_WIDTH+5));
				imgContainerTeam[i].setBounds(xCalc,yCalc,POKE_IMG_SMALL_WIDTH,POKE_IMG_SMALL_HEIGHT);
				add(imgContainerTeam[i]);
			}
		//</editor-fold>	
			
        updateTeamImg();
        updateBallAmounts();
        updateButtons();
		
	//	setVisible(true);
	//	setVisible(false);
		setVisible(visible);
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
		Game.player.setSpawnSteps(Game.player.getSpawnSteps()-1);
		
		Game.player.setFunds(Game.player.getFunds()+100);
		moneyDialog.setText("$ " + Game.player.getFunds());
		
		updateButtons();
		
		if (Game.player.getSpawnSteps() == 0){
			Random rnd = new Random();
			Game.enemyPokemon = new Pokemon(rnd.nextInt(150)+1);
			
			updateWildPokemon();
					
			displayEvent("A wild " + Game.enemyPokemon.getNameSpecies()+ " appeared!");
			
			Game.player.setSpawnSteps(Game.player.roll(1,2,3));
		}else{
			cleanPokemonInfo();
			
			displayEvent("You walk through the grass.");
		}
	}
	
	/**
	 * Update buttons depending on situation.
	 */
	public void updateButtons(){
		if (Game.enemyPokemon == null){
			evolveBtn.setEnabled(false);
			throwGreatBtn.setEnabled(false);
			throwMasterBtn.setEnabled(false);
			throwPokeBtn.setEnabled(false);
			throwUltraBtn.setEnabled(false);
		}else{
			if (Game.enemyPokemon.getCanEvolve()){
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
		if (Game.player.getFunds()>=new Item("GREATBALL").getPrice()){
			buyGreatBtn.setEnabled(true);
		}else{
			buyGreatBtn.setEnabled(false);
		}
		if (Game.player.getFunds()>=new Item("POKEBALL").getPrice()){
			buyPokeBtn.setEnabled(true);
		}else{
			buyPokeBtn.setEnabled(false);
		}
		if (Game.player.getFunds()>=new Item("ULTRABALL").getPrice()){
			buyUltraBtn.setEnabled(true);
		}else{
			buyUltraBtn.setEnabled(false);
		}
		if (Game.player.getFunds()>=new Item("MASTERBALL").getPrice()){
			buyMasterBtn.setEnabled(true);
		}else{
			buyMasterBtn.setEnabled(false);
		}
	}
	
	/**
	 * Clean current Pokemon and all UI information about it.
	 */
	public void cleanPokemonInfo(){
		Game.enemyPokemon = null;
		pokemonNameDisplay.setText(" ");
		pokemonHeightDisplay.setText(" ");
		pokemonWeightDisplay.setText(" ");
		
		pokemonNameDisplay.setForeground(Color.black);
		pokemonNameDisplay.setBackground(UIManager.getColor("TextField.background"));
		
		updateButtons();
		
		int xBig = (int)(Math.floor((double)(151)%10)*POKE_IMG_BIG_WIDTH);
		int yBig = (int)(Math.floor((double)(151)/10)*POKE_IMG_BIG_HEIGHT);
		pokemonImgDisplay = new ImageIcon (allPokemonBig.getSubimage(xBig, yBig, POKE_IMG_BIG_WIDTH, POKE_IMG_BIG_HEIGHT));
		imgContainerEnemy.setIcon(pokemonImgDisplay);
	}

	/**
	 * Throw button press event method.
	 * Checks if Player can throw given Pokeball, does shake checks and adds Pokemon to player if need be.
	 * @param ball Pokeball to be thrown.
	 */
	public void throwBtnPress(Item ball) {
		boolean canDo = Game.player.subItem(ball.getId());
		if (canDo){
			displayEvent("You throw a " + ball.getNameSingular() + "!");
			int shakes = 0;
			boolean caught = Game.enemyPokemon.tryCatch(ball.getPokeRate());

			if (caught){
				caught = Game.enemyPokemon.tryCatch(ball.getPokeRate());
				shakes = shakes + 1;

				if (caught){
					caught = Game.enemyPokemon.tryCatch(ball.getPokeRate());
					shakes = shakes + 1;

					if (caught){
						caught = Game.enemyPokemon.tryCatch(ball.getPokeRate());
						shakes = shakes + 1;

						if (caught){
							displayEvent("Gotcha! " + Game.enemyPokemon.getNameSpecies()+ " was caught!");

							Game.enemyPokemon.setBallType(ball.getNameInternal());
							int result = Game.player.addPokemon(Game.enemyPokemon);
							switch (result) {
								case 1:
									displayEvent(Game.enemyPokemon.getNameSpecies()+ " was sent to the PC.");
								break;
								case 2:
									displayEvent("PC full! " + Game.enemyPokemon.getNameSpecies()+ " was released.");
								break;
								default:
									updateTeamImg();
								break;
							}
							
							Game.player.setFunds(Game.player.getFunds() + (int)(((float)Math.floor(Game.enemyPokemon.getId()/10))*100));
							moneyDialog.setText("$ " + Game.player.getFunds());
							
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
		String pastName = Game.enemyPokemon.getNameSpecies();
		boolean couldEvolve = Game.enemyPokemon.evolve();
		
		if (couldEvolve){
			updateWildPokemon();
			displayEvent(pastName + " evolved to a " + Game.enemyPokemon.getNameSpecies() + "!");
		}else{
			displayEvent(Game.enemyPokemon.getNameSpecies() + " couldn't evolve!");
		}
	}
	
	/**
	 * Update UI with current wild Pokemon information.
	 */
	public void updateWildPokemon(){
			
		pokemonNameDisplay.setText(Game.enemyPokemon.getNameSpecies());
		pokemonHeightDisplay.setText(Game.enemyPokemon.getHeight() + " m.");
		pokemonWeightDisplay.setText(Game.enemyPokemon.getWeight() + " kg.");
		
		float hue, saturation, brightness;
		switch(Game.enemyPokemon.getColor()){
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
		
		int xBig = (int)(Math.floor((double)(Game.enemyPokemon.getId()-1)%10)*POKE_IMG_BIG_WIDTH);
		int yBig = (int)(Math.floor((double)(Game.enemyPokemon.getId()-1)/10)*POKE_IMG_BIG_HEIGHT);
		pokemonImgDisplay = new ImageIcon (allPokemonBig.getSubimage(xBig, yBig, POKE_IMG_BIG_WIDTH, POKE_IMG_BIG_HEIGHT));
		imgContainerEnemy.setIcon(pokemonImgDisplay);
	}
	
	/**
	 * Update player team UI to accommodate new player team.
	 */
	public void updateTeamImg(){
		for (int i = 0; i < Game.player.getTeam().length; i++) {
			if (i < Game.player.getNumPokemonTeam()){
				int thisPokemonID = Game.player.getTeam()[i].getId();
				
				int xSmall = (int)(Math.floor((double)(thisPokemonID-1)%10)*POKE_IMG_SMALL_WIDTH);
				int ySmall = (int)(Math.floor((double)(thisPokemonID-1)/10)*POKE_IMG_SMALL_HEIGHT);
				playerTeamPokemonImgDisplay[i] = new ImageIcon (allPokemonSmall.getSubimage(xSmall, ySmall, POKE_IMG_SMALL_WIDTH, POKE_IMG_SMALL_HEIGHT));
				imgContainerTeam[i].setIcon(playerTeamPokemonImgDisplay[i]);
			}else{
				int xSmall = (int)(Math.floor((double)(152)%10)*POKE_IMG_SMALL_WIDTH);
				int ySmall = (int)(Math.floor((double)(152)/10)*POKE_IMG_SMALL_HEIGHT);
				playerTeamPokemonImgDisplay[i] = new ImageIcon (allPokemonSmall.getSubimage(xSmall, ySmall, POKE_IMG_SMALL_WIDTH, POKE_IMG_SMALL_HEIGHT));
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
			moneyDialog.setText("$ " + Game.player.getFunds());
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
