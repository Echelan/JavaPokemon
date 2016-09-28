/*
 *  Pokemon Violet - A University Project by Andres Movilla
 *  Pokemon COPYRIGHT 2002-2016 Pokemon.
 *  COPYRIGHT 1995-2016 Nintendo/Creatures Inc./GAME FREAK inc. TRADEMARK, REGISTERED TRADEMARK
 *  and Pokemon character names are trademarks of Nintendo.
 *  No copyright or trademark infringement is intended in using Pokemon content on Pokemon Violet.
 */
package pokemonviolet;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import javax.imageio.ImageIO;

/**
 *
 * @author Andres
 */
public class Combat {

	private final Player player;
	private final Trainer enemy;
	private Pokemon currentPlayerPokemon;
	private Pokemon currentEnemyPokemon;
	private final ArrayList<String> displayTextQueue;
	private final int maxX = 480, maxY = 320;
	private boolean waitingAction;
	private int optionsPrimary;
	private int optionsSecondary;
	private String currentMenu;
	private boolean inRound;
	private int displayHealthEnemy,displayHealthPlayer;
	private boolean doneHealthEnemy, doneHealthPlayer;
	private static final int RESIZE = 2;
	private static final int SPRITE_WIDTH = 80*RESIZE;
	private static final int SPRITE_HEIGHT = 80*RESIZE;
	private int turnNum;
	private boolean canDispose;
	private boolean catching;
	
	public Combat(Player player, Trainer enemy) {
		this.player = player;
		this.enemy = enemy;
		this.waitingAction = false;
		this.catching = false;
		this.canDispose = false;
		this.inRound = false;
		this.optionsSecondary = 0;
		this.optionsPrimary = 0;
		this.turnNum = 0;
		this.currentMenu="MAIN";
				
		this.currentEnemyPokemon = enemy.getTeam()[enemy.getCurrentPokemon()];
		this.currentPlayerPokemon = player.getTeam()[player.getCurrentPokemon()];
		
		currentEnemyPokemon.setAccuracy(1);
		currentPlayerPokemon.setAccuracy(1);
		
		currentEnemyPokemon.setEvasion(1);
		currentPlayerPokemon.setEvasion(1);
		
		this.displayHealthEnemy = currentEnemyPokemon.getCurHP();
		this.displayHealthPlayer = currentPlayerPokemon.getCurHP();
		this.doneHealthEnemy = true;
		this.doneHealthPlayer = true;
		
		displayTextQueue = new ArrayList<String>();
		displayTextQueue.add("A wild "+currentEnemyPokemon.getNameNick()+" appeared!");
		
		this.player.setInCombat(true);
		this.player.setSpawnSteps(this.player.roll(1,2,3));
	}
	
	public BufferedImage displayImage(){
		BufferedImage display = new BufferedImage( maxX, maxY, BufferedImage.TYPE_INT_RGB);
		Graphics g = display.getGraphics();
		
		//<editor-fold defaultstate="collapsed" desc="Background">
			try{
				g.drawImage(ImageIO.read(new File("assets/combat/background.png")), 0, 0, maxX, maxY, null);
			}catch(IOException ex){
			}

			try{
				g.drawImage(ImageIO.read(new File("assets/combat/grassunderground1.png")), 220, 85, 128*RESIZE, 36*RESIZE, null);
				g.drawImage(ImageIO.read(new File("assets/combat/grassunderground2.png")), 10, 180, 128*RESIZE, 36*RESIZE, null);
			}catch(IOException ex){
			}
		//</editor-fold>
		
		//<editor-fold defaultstate="collapsed" desc="Pokemon Sprites Display">
			try{
				String suffix1="";
				String suffix2="";

				if (currentPlayerPokemon.getGender().compareTo("Male")==0 || currentPlayerPokemon.getGender().compareTo("Genderless")==0){
					suffix1=suffix1+"m";
				}else{
					suffix1=suffix1+"f";
				}

				if (currentEnemyPokemon.getGender().compareTo("Male")==0 || currentEnemyPokemon.getGender().compareTo("Genderless")==0){
					suffix2=suffix2+"m";
				}else{
					suffix2=suffix2+"f";
				}

				suffix1 = suffix1+"b";
				suffix2 = suffix2+"f";

				if (currentPlayerPokemon.isShiny()){
					suffix1=suffix1+"s";
				}else{
					suffix1=suffix1+"n";
				}

				if (currentEnemyPokemon.isShiny()){
					suffix2=suffix2+"s";
				}else{
					suffix2=suffix2+"n";
				}

				g.drawImage(ImageIO.read(new File("assets/Pokemon Sprites/"+currentPlayerPokemon.getId()+suffix1+".png")), 64, 64, SPRITE_WIDTH, SPRITE_HEIGHT, null);

				g.drawImage(ImageIO.read(new File("assets/Pokemon Sprites/"+currentEnemyPokemon.getId()+suffix2+".png")), maxX-50-SPRITE_WIDTH, 18, SPRITE_WIDTH, SPRITE_HEIGHT, null);

			}catch(IOException ex){
			}
		//</editor-fold>
		
		try{
			g.drawImage(ImageIO.read(new File("assets/combat/dialogdisplay.png")), 0, maxY-(48*RESIZE), 240*RESIZE, 48*RESIZE, null);
		}catch(IOException ex){
		}
		
		g.setFont(new Font("Arial",0,18));	
		g.drawString(displayTextQueue.get(0), 30, maxY-60);
		
		//<editor-fold defaultstate="collapse" desc="Health Display">
			if (!this.doneHealthEnemy){
				if (this.displayHealthEnemy < currentEnemyPokemon.getCurHP()){
					this.displayHealthEnemy = this.displayHealthEnemy+1;
				}else if (this.displayHealthEnemy > currentEnemyPokemon.getCurHP()){
					this.displayHealthEnemy = this.displayHealthEnemy-1;
				}else{
					this.doneHealthEnemy=true;
				}
			}

			if (!this.doneHealthPlayer){
				if (this.displayHealthPlayer < currentPlayerPokemon.getCurHP()){
					this.displayHealthPlayer = this.displayHealthPlayer+1;
				}else if (this.displayHealthPlayer > currentPlayerPokemon.getCurHP()){
					this.displayHealthPlayer = this.displayHealthPlayer-1;
				}else{
					this.doneHealthPlayer=true;
				}
			}

			g.setColor(Color.green);
			if ( (float)(float)this.displayHealthEnemy/(float)currentEnemyPokemon.getStatHP() < (float)0.5){
				g.setColor(Color.orange);
				if ( (float)(float)this.displayHealthEnemy/(float)currentEnemyPokemon.getStatHP() < (float)0.25){
					g.setColor(Color.red);
				}
			}
			g.fillRect(86, 52, (int)((float)100*(float)((float)this.displayHealthEnemy/(float)currentEnemyPokemon.getStatHP())), 10);

			g.setColor(Color.green);
			if ( (float)(float)this.displayHealthPlayer/(float)currentPlayerPokemon.getStatHP() < (float)0.5){
				g.setColor(Color.orange);
				if ( (float)(float)this.displayHealthPlayer/(float)currentPlayerPokemon.getStatHP() < (float)0.25){
					g.setColor(Color.red);
				}
			}
			g.fillRect(355, 177, (int)((float)100*(float)((float)this.displayHealthPlayer/(float)currentPlayerPokemon.getStatHP())), 10);

			g.setColor(Color.blue);
			g.fillRect(320, 207, (int)((float)140*(float)((float)currentPlayerPokemon.getCurEXP()/(float)currentPlayerPokemon.getMaxEXP())), 10);
		//</editor-fold>
		
		//<editor-fold defaultstate="collapsed" desc="Pokeballs Display">
			try{
				BufferedImage allBalls = ImageIO.read(new File("assets/combat/pokeballui.png"));
				int dimX=9, dimY=9;
				int x,y, id;

				y = 3;
				for (int i = 0; i < 6; i++) {
				x = 12+(i*dimX*RESIZE);
					if (i<enemy.getNumPokemonTeam()){
						if (enemy.getTeam()[i].isFainted()){
							id = 2;
						}else{
							id = 1;
						}
					}else{
						id = 0;
					}
					g.drawImage(allBalls.getSubimage(id*9, 0, dimX, dimY),x,y,dimX*RESIZE,dimY*RESIZE,null);
				}


				y = (int)(maxY/2)-33;
				for (int i = 0; i < 6; i++) {
					x = maxX-(104*RESIZE)+75+(i*dimX*RESIZE);
					if (i<enemy.getNumPokemonTeam()){
						if (enemy.getTeam()[i].isFainted()){
							id = 2;
						}else{
							id = 1;
						}
					}else{
						id = 0;
					}
					g.drawImage(allBalls.getSubimage(id*9, 0, dimX, dimY),x,y,dimX*RESIZE,dimY*RESIZE,null);
				}
			}catch(IOException ex){

			}
		//</editor-fold>
		
		try{
			g.drawImage(ImageIO.read(new File("assets/combat/enemydisplay.png")),10,20,100*RESIZE,29*RESIZE,null);
			g.drawImage(ImageIO.read(new File("assets/combat/playerdisplay.png")),maxX-(104*RESIZE)-10,(int)(maxY/2)-15,104*RESIZE,37*RESIZE,null);
		}catch(IOException ex){
		}
		
		g.setColor(Color.black);
		g.drawString(currentEnemyPokemon.getNameNick(), 25,45);
		g.drawString("Lv: "+currentEnemyPokemon.getLevel(), 130, 45);
		
		g.drawString(currentPlayerPokemon.getNameNick(), maxX-(104*RESIZE)+20,(int)(maxY/2)+10);
		g.drawString("Lv: "+currentPlayerPokemon.getLevel(), maxX-(104*RESIZE)+140,(int)(maxY/2)+10);
		
		g.drawString(currentPlayerPokemon.getCurHP()+"/"+currentPlayerPokemon.getStatHP(), maxX-(104*RESIZE)+125,(int)(maxY/2)+45);
		
		
		//<editor-fold defaultstate="collapsed" desc="UI Display">
			try{
				if (waitingAction){
					if (currentMenu.compareTo("MAIN")==0){
						//<editor-fold defaultstate="collapsed" desc="Main UI Display">
							int uiW = (120*RESIZE), uiH = (48*RESIZE);
							g.drawImage(ImageIO.read(new File("assets/combat/uioptionsdisplay.png")), maxX-uiW, maxY-uiH, uiW, uiH, null);

							g.setColor(Color.black);
							g.drawString("FIGHT", maxX-215, maxY-55);
							g.drawString("POKéBALL", maxX-105, maxY-55);
						//	g.drawString("POKéMON", maxX-215, maxY-20);
							g.drawString("RUN", maxX-105, maxY-20);

							g.drawImage(ImageIO.read(new File("assets/combat/arrow.png")), maxX-230+(int)(Math.floor(optionsPrimary/2)*110), maxY-70+(int)(Math.floor(optionsPrimary%2)*30), 20, 20, null);
						//</editor-fold>
					}else if (currentMenu.compareTo("MOVES")==0){
						//<editor-fold defaultstate="collapsed" desc="Moves UI Display">
							int uiW = (240*RESIZE), uiH = (48*RESIZE);
							g.drawImage(ImageIO.read(new File("assets/combat/uimovesdisplay.png")), maxX-uiW, maxY-uiH, uiW, uiH, null);

							g.drawImage(ImageIO.read(new File("assets/combat/arrow.png")), 10+(int)(Math.floor(optionsSecondary/2)*140), maxY-75+(int)(Math.floor(optionsSecondary%2)*40), 20, 20, null);
							for (int i = 0; i < 4; i++) {
								String moveName;
								if (i < currentPlayerPokemon.getNumMoves()){
									moveName = currentPlayerPokemon.getMoveSet()[i].getNameDisplay();
									if (moveName == null || moveName.compareTo("")==0){
										moveName = "--";
									}
								}else{
									moveName = "--";
								}
								g.setColor(Color.black);
								g.drawString(moveName, 30+(int)(Math.floor(i/2)*140), maxY-60+(int)(Math.floor(i%2)*40));
							}
							if (optionsSecondary<currentPlayerPokemon.getNumMoves()){
								g.setColor(Color.black);
								g.drawString("PP  "+currentPlayerPokemon.getMoveSet()[optionsSecondary].getPP()+ " / "+currentPlayerPokemon.getMoveSet()[optionsSecondary].getPPMax(), maxX-140, maxY-55);
								g.drawString("TYPE  "+PokemonType.getNameDisplay(currentPlayerPokemon.getMoveSet()[optionsSecondary].getType()), maxX-140, maxY-20);
							}
						//</editor-fold>
					}else if (currentMenu.compareTo("BALLS")==0){
						//<editor-fold defaultstate="collapsed" desc="Balls UI Display">
							int uiW = (240*RESIZE), uiH = (48*RESIZE);
							g.drawImage(ImageIO.read(new File("assets/combat/uimovesdisplay.png")), maxX-uiW, maxY-uiH, uiW, uiH, null);

							g.drawImage(ImageIO.read(new File("assets/combat/arrow.png")), 10+(int)(Math.floor(optionsSecondary/2)*140), maxY-75+(int)(Math.floor(optionsSecondary%2)*40), 20, 20, null);

							String[] pokeTypes = {"POKEBALL","GREATBALL","ULTRABALL","PREMIERBALL","MASTERBALL"};
							String[] order = new String[4];
							int counter = 0;
							int numBalls = 0;
							for (int i = 0; i < pokeTypes.length; i++) {
								if (numBalls<4){
									boolean foundOne = false;
									String option="--";
									while (counter<5 && !foundOne){
										if (player.getAmountItem(pokeTypes[counter])>0){
											option = new Item(pokeTypes[counter]).getNameSingular();
											order[numBalls] = pokeTypes[counter];
											foundOne = true;
										}
										counter=counter+1;
									}

									if (option.compareTo("--")!=0){
										g.setColor(Color.black);
										g.drawString(option, 30+(int)(Math.floor(numBalls/2)*140), maxY-60+(int)(Math.floor(numBalls%2)*40));
										numBalls = numBalls+1;
									}else{
										
									}
								}
							}
							
							if (optionsSecondary<numBalls){
								g.setColor(Color.black);
								g.drawString("x "+player.getAmountItem(order[optionsSecondary]), maxX-100, maxY-40);
							}
							
							for (int i = numBalls; i < 4; i++) {
								g.setColor(Color.black);
								g.drawString("--", 30+(int)(Math.floor(numBalls/2)*140), maxY-60+(int)(Math.floor(numBalls%2)*40));
								numBalls = numBalls+1;
							}
							
						//</editor-fold>
					}
				}
			}catch(IOException ex){
			}
		//</editor-fold>
		
		return display;
	}

	public void accept(){
		if (waitingAction){
			if (currentMenu.compareTo("MAIN")==0){
				switch (optionsPrimary) {
					case 0:
						this.currentMenu="MOVES";
					break;
					case 1:
						// POKEMON MENU
					break;
					case 2:
						this.currentMenu="BALLS";
					break;
					case 3:
						this.dispose();
					break;
					default:
						
					break;
				}
			}else if (currentMenu.compareTo("MOVES")==0){
				if (optionsSecondary<currentPlayerPokemon.getNumMoves()){
					waitingAction = false;
					displayTextQueue.remove(0);
					if (currentPlayerPokemon.getMoveSet()[optionsSecondary].getPP()>0){
						inRound = true;
						nextTurn();
					}else{
						displayTextQueue.add("There is no PP left for this move!");
					}
				}
			}else if (currentMenu.compareTo("BALLS")==0){
				String[] pokeTypes = {"POKEBALL","GREATBALL","ULTRABALL","PREMIERBALL","MASTERBALL"};
				String[] order = new String[4];
				int counter = 0;
				int numBalls = 0;
				for (int i = 0; i < pokeTypes.length; i++) {
					if (numBalls<4){
						boolean foundOne = false;
						String option="--";
						while (counter<5 && !foundOne){
							if (player.getAmountItem(pokeTypes[counter])>0){
								option = new Item(pokeTypes[counter]).getNameSingular();
								order[numBalls] = pokeTypes[counter];
								foundOne = true;
							}
							counter=counter+1;
						}
					}
				}
				if (optionsSecondary<numBalls){
					waitingAction = false;
					displayTextQueue.remove(0);
					inRound = true;
					throwBall(order[optionsSecondary]);
						
				}
			}
		}else if (inRound){
			if (this.doneHealthEnemy && this.doneHealthPlayer){
				displayTextQueue.remove(0);
				if (currentEnemyPokemon.isFainted()){
					displayTextQueue.add(currentEnemyPokemon.getNameNick() +" fainted!");
					displayTextQueue.add(currentPlayerPokemon.getNameNick() +" gained "+currentEnemyPokemon.getExpGain()+" EXP!");
					boolean levelUp = currentPlayerPokemon.setCurEXP(currentPlayerPokemon.getCurEXP()+currentEnemyPokemon.getExpGain());
					if (levelUp){
						displayTextQueue.add(currentPlayerPokemon.getNameNick()+"is now level "+currentPlayerPokemon.getLevel()+"!");
					}
					if (enemy.getCurrentPokemon()+1<enemy.getNumPokemonTeam()){
						enemy.setCurrentPokemon(enemy.getCurrentPokemon()+1);
					}
					currentMenu = "MAIN";
					turnNum = 0;
					inRound = false;
				}else if (currentPlayerPokemon.isFainted()){
					if (player.getCurrentPokemon()!= -1){
						displayTextQueue.add("That's enough, "+currentPlayerPokemon.getNameNick()+"!");
						currentPlayerPokemon = player.getTeam()[player.getCurrentPokemon()];
						displayTextQueue.add("Go! "+currentPlayerPokemon.getNameNick()+"!");
					}else{
						displayTextQueue.add(player.getName()+ " wiped out!");
					}
					currentMenu = "MAIN";
					turnNum = 0;
					inRound = false;
				}else if (displayTextQueue.isEmpty()){
					if (turnNum == 2){
						currentMenu = "MAIN";
						turnNum = 0;
						inRound = false;
						displayTextQueue.add("");
						accept();
					}else{
						nextTurn();
					}
				}
			}
		}else{
			displayTextQueue.remove(0);
			if (displayTextQueue.isEmpty()){
				if (!currentEnemyPokemon.isFainted() && !currentPlayerPokemon.isFainted()){
					waitingAction = true;
					displayTextQueue.add("What will "+currentPlayerPokemon.getNameNick()+ " do?");
				}else if (!canDispose){
					canDispose = true;
					Random rnd = new Random();
					int roll = rnd.nextInt(100);
					if( roll == 0){
						displayTextQueue.add("Found a Masterball!");
						player.addItem("MASTERBALL",1);
					}else if (roll>90){
						displayTextQueue.add("Found an Ultraball!");
						player.addItem("ULTRABALL",1);
					}else if (roll>50){
						displayTextQueue.add("Found a Greatball!");
						player.addItem("GREATBALL",1);
					}else{
						displayTextQueue.add("Found a Pokeball!");
						player.addItem("POKEBALL",1);
					}
				}else{
					player.pokemonCenter();
					this.dispose();
				}
			}
		}
	}
	
	public void cancel(){
		if (waitingAction){
			if (currentMenu.compareTo("MAIN")==0){
				optionsPrimary = 3;
			}else{
				currentMenu = "MAIN";
			}
		}
	}
	
	private void throwBall(String internalName){
		player.subItem(internalName);
		boolean caught = currentEnemyPokemon.doCatch(internalName);
		displayTextQueue.add(player.getName()+" throws a "+new Item(internalName).getNameSingular()+"!");
		if (caught){
			displayTextQueue.add("Gotcha! "+currentEnemyPokemon+" was caught!");
		}
	}
	
	private void nextTurn(){
		if (currentEnemyPokemon.getStatSpeed() >= currentPlayerPokemon.getStatSpeed()){
			if (turnNum == 0){
				Random rnd = new Random();
				int randomMove = (rnd.nextInt(currentEnemyPokemon.getNumMoves()));
				doTurn(currentEnemyPokemon,currentPlayerPokemon,randomMove);
			}else{
				doTurn(currentPlayerPokemon,currentEnemyPokemon,optionsSecondary);
			}
			turnNum = turnNum+1;
		}else if (currentEnemyPokemon.getStatSpeed() < currentPlayerPokemon.getStatSpeed()){
			if (turnNum == 1){
				Random rnd = new Random();
				int randomMove = (rnd.nextInt(currentEnemyPokemon.getNumMoves()));
				doTurn(currentEnemyPokemon,currentPlayerPokemon,randomMove);
			}else{
				doTurn(currentPlayerPokemon,currentEnemyPokemon,optionsSecondary);
			}
			turnNum = turnNum+1;
		}
	}
	
	private void doTurn(Pokemon attacker, Pokemon attacked, int moveNum){
		
		displayTextQueue.add(attacker.getNameNick()+" used "+attacker.getMoveSet()[moveNum].getNameDisplay()+"!");
		
		float hitChance = (attacker.getMoveSet()[moveNum].getAccuracy()/100)*(attacker.getAccuracy()/attacked.getEvasion());
		
		Random rnd = new Random();
		float roll = (rnd.nextInt(100))/100;
		if (hitChance >= 1 || roll<=hitChance ){
			int[] moveDamage = attacker.getDamage(moveNum, attacked.getTypes());
			attacker.getMoveSet()[moveNum].setPP(attacker.getMoveSet()[moveNum].getPP()-1);
			
			if(moveDamage[2]==0){
				displayTextQueue.add(attacked.getNameNick()+" is immune!");
			}else if(moveDamage[2]>10){
				displayTextQueue.add("It's super effective!");
			}else if(moveDamage[2]<10){
				displayTextQueue.add("It's not very effective...");
			}
			
			if (moveDamage[1]==1){
				displayTextQueue.add("A critical hit!");
			}
			
			this.doneHealthEnemy = false;
			this.doneHealthPlayer = false;
			
			attacked.setCurHP(attacked.getCurHP()-moveDamage[0]);
		}else{
			displayTextQueue.add(attacker.getNameNick()+"'s attack missed!");
		}
	}
	
	public void move(String dir){
		if (waitingAction){
			int x,y;
			if (currentMenu.compareTo("MAIN")==0){
				y = (optionsPrimary%2);
				x = (int)Math.floor(optionsPrimary/2);
				
			}else{
				y = (optionsSecondary%2);
				x = (int)Math.floor(optionsSecondary/2);
			}
			switch(dir){
				case "LEFT":
					x = x-1;
				break;
				case "UP":
					y = y-1;
				break;
				case "RIGHT":
					x = x+1;
				break;
				case "DOWN":
					y = y+1;
				break;
			}

			if (x<0){
				x = 1;
			}else if (x>1){
				x = 0;
			}
			
			if (y<0){
				y = 1;
			}else if (y>1){
				y = 0;
			}

			if (currentMenu.compareTo("MAIN")==0){
				optionsPrimary = (x*2)+y;
			}else{
				optionsSecondary = (x*2)+y;
			}
			
		}
	}
	
	private void dispose(){
		Game.currentBattle = null;
		this.player.setInCombat(false);
	}
	
}
