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
	private int optionsMain;
	private int optionsMoves;
	private int optionsBalls;
	private int optionsPokemon;
	private String currentMenu;
	private boolean inRound;
	private int displayHealthEnemy,displayHealthPlayer, displayExpPlayer;
	private boolean doneHealthEnemy, doneHealthPlayer, doneExpPlayer;
	private static final int RESIZE = 2;
	private static final int SPRITE_WIDTH = 80*RESIZE;
	private static final int SPRITE_HEIGHT = 80*RESIZE;
	private int turnNum;
	private boolean canDispose;
	private final int[] caught;
	private final int finalPlayerX, finalEnemyX;
	private int currentEnemyX, currentPlayerX;
	private boolean ready;
	private final boolean wildBattle;
	
	public Combat(Player player, Trainer enemy, boolean wildBattle) {
		this.player = player;
		this.enemy = enemy;
		this.waitingAction = false;
		this.caught = new int[6];
		this.canDispose = false;
		this.inRound = false;
		this.optionsMoves = 0;
		this.optionsMain = 0;
		this.optionsBalls = 0;
		this.optionsPokemon = 0;
		this.turnNum = 0;
		this.currentMenu="MAIN";
		this.ready = false;
		this.wildBattle = wildBattle;
		
		
		this.finalEnemyX = 220;
		this.currentEnemyX = this.finalEnemyX-500;
		
		this.finalPlayerX = 10;
		this.currentPlayerX = this.finalPlayerX+500;
		
		for (int i = 0; i < enemy.getNumPokemonTeam(); i++) {
			this.caught[i] = 0;
			enemy.getTeam()[i].setAccuracy(1);
			enemy.getTeam()[i].setEvasion(1);
		}
		
		for (int i = 0; i < player.getNumPokemonTeam(); i++) {
			player.getTeam()[i].setAccuracy(1);
			player.getTeam()[i].setEvasion(1);
		}
		
		this.currentEnemyPokemon = enemy.getTeam()[enemy.getCurrentPokemon()];
		this.currentPlayerPokemon = player.getTeam()[player.getCurrentPokemon()];
		
		this.displayHealthEnemy = currentEnemyPokemon.getCurHP();
		this.displayExpPlayer = currentPlayerPokemon.getCurEXP();
		this.displayHealthPlayer = currentPlayerPokemon.getCurHP();
		this.doneHealthEnemy = true;
		this.doneHealthPlayer = true;
		this.doneExpPlayer = true;
		
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
				g.drawImage(ImageIO.read(new File("assets/combat/grassunderground1.png")), this.currentEnemyX, 85, 128*RESIZE, 36*RESIZE, null);
				g.drawImage(ImageIO.read(new File("assets/combat/grassunderground2.png")), this.currentPlayerX, 180, 128*RESIZE, 36*RESIZE, null);
			}catch(IOException ex){
			}
		//</editor-fold>
		
		//<editor-fold defaultstate="collapsed" desc="Pokemon Sprites Display">
			try{
				if (this.displayHealthEnemy != 0 && this.caught[enemy.getCurrentPokemon()]!=1){
					String suffix2="";

					if (currentEnemyPokemon.getGender().compareTo("Male")==0 || currentEnemyPokemon.getGender().compareTo("Genderless")==0){
						suffix2=suffix2+"m";
					}else{
						suffix2=suffix2+"f";
					}
					suffix2 = suffix2+"f";
					if (currentEnemyPokemon.isShiny()){
						suffix2=suffix2+"s";
					}else{
						suffix2=suffix2+"n";
					}

					g.drawImage(ImageIO.read(new File("assets/Pokemon Sprites/"+currentEnemyPokemon.getId()+suffix2+".png")), this.currentEnemyX+50, 0, SPRITE_WIDTH, SPRITE_HEIGHT, null);
				}else if (this.caught[enemy.getCurrentPokemon()]==1){
					int x = (this.currentEnemyX+50+(SPRITE_WIDTH/2)+20-(14*RESIZE)), y = (18+SPRITE_HEIGHT-50-(14*RESIZE));
					g.drawImage(ImageIO.read(new File("assets/combat/pokeballactive.png")).getSubimage(0, 0, 14, 14), x,y, 14*RESIZE, 14*RESIZE, null);
				}
				if (this.displayHealthPlayer != 0){
					String suffix1="";
					if (currentPlayerPokemon.getGender().compareTo("Male")==0 || currentPlayerPokemon.getGender().compareTo("Genderless")==0){
						suffix1=suffix1+"m";
					}else{
						suffix1=suffix1+"f";
					}

					suffix1 = suffix1+"b";

					if (currentPlayerPokemon.isShiny()){
						suffix1=suffix1+"s";
					}else{
						suffix1=suffix1+"n";
					}

					g.drawImage(ImageIO.read(new File("assets/Pokemon Sprites/"+currentPlayerPokemon.getId()+suffix1+".png")), this.currentPlayerX+54, 64, SPRITE_WIDTH, SPRITE_HEIGHT, null);
				}
			}catch(IOException ex){
			}
			
			try{
				g.drawImage(ImageIO.read(new File("assets/combat/dialogdisplay.png")), 0, maxY-(48*RESIZE), 240*RESIZE, 48*RESIZE, null);
			}catch(IOException ex){
			}

		//</editor-fold>
		
		if (this.currentEnemyX != this.finalEnemyX || this.currentPlayerX != this.finalPlayerX){
			if (this.currentEnemyX != this.finalEnemyX){
				this.currentEnemyX = this.currentEnemyX+20;
			}
			
			if (this.currentPlayerX != this.finalPlayerX){
				this.currentPlayerX = this.currentPlayerX-20;
			}
			
			if (this.currentEnemyX == this.finalEnemyX && this.currentPlayerX == this.finalPlayerX){
				this.ready=true;
			}
		}else{
			g.setFont(new Font("Arial",0,18));
			if (displayTextQueue.size()>0){
				g.drawString(displayTextQueue.get(0), 30, maxY-60);
			}

			//<editor-fold defaultstate="collapse" desc="Health & Experience Display">
				if (!this.doneHealthEnemy){
					int delta = Math.abs(this.displayHealthEnemy - currentEnemyPokemon.getCurHP());
					delta = (int)Math.ceil((float)delta/(float)20);
					if (this.displayHealthEnemy < currentEnemyPokemon.getCurHP()){
						this.displayHealthEnemy = this.displayHealthEnemy+delta;
					}else if (this.displayHealthEnemy > currentEnemyPokemon.getCurHP()){
						this.displayHealthEnemy = this.displayHealthEnemy-delta;
					}else{
						this.doneHealthEnemy=true;
					}
				}

				if (!this.doneHealthPlayer){
					int delta = Math.abs(this.displayHealthPlayer - currentPlayerPokemon.getCurHP());
					delta = (int)Math.ceil((float)delta/(float)20);
					if (this.displayHealthPlayer < currentPlayerPokemon.getCurHP()){
						this.displayHealthPlayer = this.displayHealthPlayer+delta;
					}else if (this.displayHealthPlayer > currentPlayerPokemon.getCurHP()){
						this.displayHealthPlayer = this.displayHealthPlayer-delta;
					}else{
						this.doneHealthPlayer=true;
					}
				}

				if (!this.doneExpPlayer){
					int delta = Math.abs(this.displayExpPlayer - currentPlayerPokemon.getCurEXP());
					delta = (int)Math.ceil((float)delta/5);
					if (this.displayExpPlayer < currentPlayerPokemon.getCurEXP()){
						this.displayExpPlayer = this.displayExpPlayer+delta;
					}else if (this.displayExpPlayer > currentPlayerPokemon.getCurEXP()){
						this.displayExpPlayer = 0;
					}else{
						this.doneExpPlayer=true;
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
				g.fillRect(320, 207, (int)((float)140*(float)((float)displayExpPlayer/(float)currentPlayerPokemon.getMaxEXP())), 10);
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
						if (i<player.getNumPokemonTeam()){
							if (player.getTeam()[i].isFainted()){
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
			g.drawString("Lv: "+currentEnemyPokemon.getLevel(), 140, 45);

			g.drawString(currentPlayerPokemon.getNameNick(), maxX-(104*RESIZE)+20,(int)(maxY/2)+10);
			g.drawString("Lv: "+currentPlayerPokemon.getLevel(), maxX-(104*RESIZE)+130,(int)(maxY/2)+10);

			g.drawString(displayHealthPlayer+"/"+currentPlayerPokemon.getStatHP(), maxX-(104*RESIZE)+125,(int)(maxY/2)+45);


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
								g.drawString("POKéMON", maxX-215, maxY-20);
								g.drawString("RUN", maxX-105, maxY-20);

								g.drawImage(ImageIO.read(new File("assets/combat/arrow.png")), maxX-230+(int)(Math.floor(optionsMain%2)*110), maxY-70+(int)(Math.floor(optionsMain/2)*30), 20, 20, null);
							//</editor-fold>
						}else if (currentMenu.compareTo("MOVES")==0){
							//<editor-fold defaultstate="collapsed" desc="Moves UI Display">
								int uiW = (240*RESIZE), uiH = (48*RESIZE);
								g.drawImage(ImageIO.read(new File("assets/combat/uimovesdisplay.png")), maxX-uiW, maxY-uiH, uiW, uiH, null);

								g.drawImage(ImageIO.read(new File("assets/combat/arrow.png")), 10+(int)(Math.floor(optionsMoves%2)*140), maxY-75+(int)(Math.floor(optionsMoves/2)*40), 20, 20, null);
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
									g.drawString(moveName, 30+(int)(Math.floor(i%2)*140), maxY-60+(int)(Math.floor(i/2)*40));
								}
								if (optionsMoves<currentPlayerPokemon.getNumMoves()){
									g.setColor(Color.black);
									g.drawString("PP  "+currentPlayerPokemon.getMoveSet()[optionsMoves].getPP()+ " / "+currentPlayerPokemon.getMoveSet()[optionsMoves].getPPMax(), maxX-140, maxY-55);
									g.drawString("TYPE  "+PokemonType.getNameDisplay(currentPlayerPokemon.getMoveSet()[optionsMoves].getType()), maxX-140, maxY-20);
								}
							//</editor-fold>
						}else if (currentMenu.compareTo("BALLS")==0){
							//<editor-fold defaultstate="collapsed" desc="Balls UI Display">
								int uiW = (240*RESIZE), uiH = (48*RESIZE);
								g.drawImage(ImageIO.read(new File("assets/combat/uimovesdisplay.png")), maxX-uiW, maxY-uiH, uiW, uiH, null);

								g.drawImage(ImageIO.read(new File("assets/combat/arrow.png")), 10+(int)(Math.floor(optionsBalls%2)*140), maxY-75+(int)(Math.floor(optionsBalls/2)*40), 20, 20, null);

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
											g.drawString(option, 30+(int)(Math.floor(numBalls%2)*140), maxY-60+(int)(Math.floor(numBalls/2)*40));
											numBalls = numBalls+1;
										}else{

										}
									}
								}

								if (optionsBalls<numBalls){
									g.setColor(Color.black);
									g.drawString("x "+player.getAmountItem(order[optionsBalls]), maxX-100, maxY-40);
								}

								for (int i = numBalls; i < 4; i++) {
									g.setColor(Color.black);
									g.drawString("--", 30+(int)(Math.floor(numBalls%2)*140), maxY-60+(int)(Math.floor(numBalls/2)*40));
									numBalls = numBalls+1;
								}

							//</editor-fold>
						}else if (currentMenu.compareTo("POKEMON")==0 || currentMenu.compareTo("POKEMONF")==0){
							//<editor-fold defaultstate="collapsed" desc="Pokemon UI Display">
								int uiW = (110*RESIZE), uiH = (110*RESIZE);	
								g.drawImage(ImageIO.read(new File("assets/combat/extrabackground.png")), (maxX/2)-(uiW/2), (maxY/2)-(uiH/2), uiW, uiH, null);

								for (int i = 0; i < player.getNumPokemonTeam(); i++) {
									g.drawString(player.getTeam()[i].getNameNick(), (maxX/2)-(uiW/2)+30, (maxY/2)-(uiH/2)+40+(i*30));
								}
								for (int i = player.getNumPokemonTeam(); i < 6; i++) {
									g.drawString("--", (maxX/2)-(uiW/2)+30, (maxY/2)-(uiH/2)+40+(i*30));
								}
								g.drawImage(ImageIO.read(new File("assets/combat/arrow.png")), (maxX/2)-(uiW/2)+15, (maxY/2)-(uiH/2)-5+((optionsPokemon+1)*30), 20, 20, null);

							//</editor-fold>
						}
					}
				}catch(IOException ex){
				}
			//</editor-fold>
		}
		return display;
	}

	public void accept(){
		if (!this.ready){
			this.currentEnemyX=this.finalEnemyX;
			this.currentPlayerX=this.finalPlayerX;
			this.ready=true;
		}else{
			if (waitingAction){
				//<editor-fold defaultstate="collapsed" desc="User Input Menus">
					if (currentMenu.compareTo("MAIN")==0){
						switch (optionsMain) {
							case 0:
								this.currentMenu="MOVES";
							break;
							case 1:
								this.currentMenu="BALLS";
							break;
							case 2:
								this.currentMenu="POKEMON";
							break;
							case 3:
								this.dispose();
							break;
							default:

							break;
						}
					}else if (currentMenu.compareTo("MOVES")==0){
						if (optionsMoves<currentPlayerPokemon.getNumMoves()){
							waitingAction = false;
							displayTextQueue.remove(0);
							if (currentPlayerPokemon.getMoveSet()[optionsMoves].getPP()>0){
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
								while (counter<5 && !foundOne){
									if (player.getAmountItem(pokeTypes[counter])>0){
										order[numBalls] = pokeTypes[counter];
										foundOne = true;
										numBalls=numBalls+1;
									}
									counter=counter+1;
								}
							}
						}
						if (optionsBalls<numBalls){
							waitingAction = false;
							displayTextQueue.remove(0);
							inRound = true;
							throwBall(order[optionsBalls]);
						}
					}else if (currentMenu.compareTo("POKEMON")==0 || currentMenu.compareTo("POKEMONF")==0){
						displayTextQueue.remove(0);
						if (currentMenu.compareTo("POKEMONF")==0){
							currentMenu = "MAIN";
						}else{
							waitingAction = false;
							inRound = true;
							turnNum = turnNum+1;
						}
						if (currentMenu.compareTo("POKEMON")==0){
							displayTextQueue.add("That's enough, "+currentPlayerPokemon.getNameNick()+", come back!");
						}else{
						//	displayTextQueue.add("You did good, "+currentPlayerPokemon.getNameNick()+".");
						}
						player.setCurrentPokemon(optionsPokemon);
						currentPlayerPokemon = player.getTeam()[player.getCurrentPokemon()];
						displayTextQueue.add("Go! "+currentPlayerPokemon.getNameNick()+"!");
						this.displayHealthPlayer = currentPlayerPokemon.getCurHP();
						this.displayExpPlayer = currentPlayerPokemon.getCurEXP();
					}
				//</editor-fold>
			}else if (inRound){
				if (this.doneHealthEnemy && this.doneHealthPlayer){
					displayTextQueue.remove(0);
					if (this.caught[enemy.getCurrentPokemon()]==1){
						currentMenu = "MAIN";
						turnNum = 0;
						inRound = false;
					}else if (currentEnemyPokemon.isFainted()){
						String prefix;
						if (this.wildBattle){
							prefix = "Wild ";
						}else{
							prefix = "Foe ";
						}
						displayTextQueue.add(prefix+currentEnemyPokemon.getNameNick() +" fainted!");
						/*
						*/
						currentMenu = "MAIN";
						turnNum = 0;
						inRound = false;
						
					}else if (currentPlayerPokemon.isFainted()){
						displayTextQueue.add(currentEnemyPokemon.getNameNick() +" fainted!");
						turnNum = 0;
						inRound = false;
					}else{
						if (displayTextQueue.isEmpty()){
							if (turnNum<2){
								nextTurn();
							}else{
								currentMenu = "MAIN";
								turnNum = 0;
								inRound = false;
								displayTextQueue.add("");
								accept();
							}
						}
					}
				}
			}else{
				if (this.doneExpPlayer){
					displayTextQueue.remove(0);
					if (displayTextQueue.isEmpty()){
						if (!currentEnemyPokemon.isFainted() && caught[enemy.getCurrentPokemon()]==0 && !currentPlayerPokemon.isFainted()){
							waitingAction = true;
							displayTextQueue.add("What will "+currentPlayerPokemon.getNameNick()+ " do?");
						}else if (currentEnemyPokemon.isFainted() && enemy.getCurrentPokemon()+1<enemy.getNumPokemonTeam()){
							
							displayTextQueue.add(currentPlayerPokemon.getNameNick() +" gained "+currentEnemyPokemon.getExpGain()+" EXP!");
							boolean levelUp = currentPlayerPokemon.setCurEXP(currentPlayerPokemon.getCurEXP()+currentEnemyPokemon.getExpGain());
							this.doneExpPlayer=false;
							if (levelUp){
								displayTextQueue.add(currentPlayerPokemon.getNameNick()+" is now level "+currentPlayerPokemon.getLevel()+"!");
							}
						
							enemy.setCurrentPokemon(enemy.getCurrentPokemon()+1);
							currentEnemyPokemon = enemy.getTeam()[enemy.getCurrentPokemon()];
							this.displayHealthEnemy = currentEnemyPokemon.getCurHP();

							if (this.wildBattle){
								displayTextQueue.add("A wild "+currentEnemyPokemon.getNameNick()+" rushed at you!");
							}else{
								displayTextQueue.add("Foe "+enemy.getName()+" sent out "+currentEnemyPokemon.getNameNick()+"!");
							}
						}else if (caught[enemy.getCurrentPokemon()]==1 && enemy.getCurrentPokemon()+1<enemy.getNumPokemonTeam()){
							
							displayTextQueue.add(currentPlayerPokemon.getNameNick() +" gained "+currentEnemyPokemon.getExpGain()+" EXP!");
							boolean levelUp = currentPlayerPokemon.setCurEXP(currentPlayerPokemon.getCurEXP()+currentEnemyPokemon.getExpGain());
							this.doneExpPlayer=false;
							if (levelUp){
								displayTextQueue.add(currentPlayerPokemon.getNameNick()+" is now level "+currentPlayerPokemon.getLevel()+"!");
							}
						
							enemy.setCurrentPokemon(enemy.getCurrentPokemon()+1);
							currentEnemyPokemon = enemy.getTeam()[enemy.getCurrentPokemon()];
							this.displayHealthEnemy = currentEnemyPokemon.getCurHP();

							if (this.wildBattle){
								displayTextQueue.add("A wild "+currentEnemyPokemon.getNameNick()+" rushed at you!");
							}else{
								displayTextQueue.add("Foe "+enemy.getName()+" sent out "+currentEnemyPokemon.getNameNick()+"!");
							}
						}else if (currentPlayerPokemon.isFainted() && player.getNextAvailablePokemon()!= -1){
								displayTextQueue.add("What Pokemon will you send out next?");
								currentMenu = "POKEMONF";
								waitingAction = true;
						}else if (!canDispose){
							canDispose = true;
							if (currentPlayerPokemon.isFainted()){
								displayTextQueue.add(player.getName()+ " wiped out!");
							}else{
								if (this.caught[enemy.getCurrentPokemon()]!=1){
									displayTextQueue.add(currentPlayerPokemon.getNameNick() +" gained "+currentEnemyPokemon.getExpGain()+" EXP!");
									boolean levelUp = currentPlayerPokemon.setCurEXP(currentPlayerPokemon.getCurEXP()+currentEnemyPokemon.getExpGain());
									this.doneExpPlayer=false;
									if (levelUp){
										displayTextQueue.add(currentPlayerPokemon.getNameNick()+" is now level "+currentPlayerPokemon.getLevel()+"!");
									}
								}
								Random rnd = new Random();
								int lootRoll = rnd.nextInt(100);
								if( lootRoll == 0){
									displayTextQueue.add("Found a Masterball!");
									player.addItem("MASTERBALL",1);
								}else if (lootRoll<10){
									displayTextQueue.add("Found an Ultraball!");
									player.addItem("ULTRABALL",1);
								}else if (lootRoll<40){
									displayTextQueue.add("Found a Greatball!");
									player.addItem("GREATBALL",1);
								}else if (lootRoll<50){
									displayTextQueue.add("Found a Premier Ball!");
									player.addItem("PREMIERBALL",1);
								}else{
									displayTextQueue.add("Found a Pokeball!");
									player.addItem("POKEBALL",1);
								}
							}
						}else{
							player.pokemonCenter();
							this.dispose();
						}
					}
				}
			}
		}
	}
	
	public void cancel(){
		if (waitingAction){
			if (currentMenu.compareTo("MAIN")==0){
				optionsMain = 3;
			}else if (currentMenu.compareTo("POKEMONF")!=0){
				currentMenu = "MAIN";
			}
		}
	}
	
	private void throwBall(String internalName){
		turnNum = turnNum+1;
		player.subItem(internalName);
		int shakes = currentEnemyPokemon.doCatch(internalName);
		displayTextQueue.add(player.getName()+" throws a "+new Item(internalName).getNameSingular()+"!");
		switch (shakes) {
			case 0:
				displayTextQueue.add(currentEnemyPokemon.getNameNick()+" escaped!");
				this.caught[enemy.getCurrentPokemon()]=2;
			break;
			case 1:
				displayTextQueue.add("The "+new Item(internalName).getNameSingular()+" broke!");
				this.caught[enemy.getCurrentPokemon()]=2;
			break;
			case 2:
				displayTextQueue.add("Couldn't catch "+currentEnemyPokemon.getNameNick()+"!");
				this.caught[enemy.getCurrentPokemon()]=2;
			break;
			case 3:
				displayTextQueue.add("Oh no! It was so close too!");
				this.caught[enemy.getCurrentPokemon()]=2;
			break;
			case 4:
				displayTextQueue.add("Gotcha! "+currentEnemyPokemon.getNameNick()+" was caught!");
				this.caught[enemy.getCurrentPokemon()]=1;
				player.addPokemon(currentEnemyPokemon);
			break;
		}
	}
	
	private void nextTurn(){
		if (this.caught[enemy.getCurrentPokemon()]==2){
			this.caught[enemy.getCurrentPokemon()]=0;
			Random rnd = new Random();
			int randomMove = (rnd.nextInt(currentEnemyPokemon.getNumMoves()));
				String prefix;
				if (this.wildBattle){
					prefix = "Wild ";
				}else{
					prefix = "Foe ";
				}
			doTurn(currentEnemyPokemon,currentPlayerPokemon,randomMove,prefix);
			turnNum = turnNum+1;
		}else if (currentEnemyPokemon.getStatSpeed() >= currentPlayerPokemon.getStatSpeed()){
			if (turnNum == 0){
				Random rnd = new Random();
				int randomMove = (rnd.nextInt(currentEnemyPokemon.getNumMoves()));
				String prefix;
				if (this.wildBattle){
					prefix = "Wild ";
				}else{
					prefix = "Foe ";
				}
				doTurn(currentEnemyPokemon,currentPlayerPokemon,randomMove,prefix);
			}else{
				doTurn(currentPlayerPokemon,currentEnemyPokemon,optionsMoves,"");
			}
			turnNum = turnNum+1;
		}else if (currentEnemyPokemon.getStatSpeed() < currentPlayerPokemon.getStatSpeed()){
			if (turnNum == 1){
				Random rnd = new Random();
				int randomMove = (rnd.nextInt(currentEnemyPokemon.getNumMoves()));
				String prefix;
				if (this.wildBattle){
					prefix = "Wild ";
				}else{
					prefix = "Foe ";
				}
				doTurn(currentEnemyPokemon,currentPlayerPokemon,randomMove,prefix);
			}else{
				doTurn(currentPlayerPokemon,currentEnemyPokemon,optionsMoves,"");
			}
			turnNum = turnNum+1;
		}
	}
	
	private void doTurn(Pokemon attacker, Pokemon attacked, int moveNum, String prefix){
		
		displayTextQueue.add(prefix+attacker.getNameNick()+" used "+attacker.getMoveSet()[moveNum].getNameDisplay()+"!");
		
		float hitChance = (attacker.getMoveSet()[moveNum].getAccuracy()/100)*(attacker.getAccuracy()/attacked.getEvasion());
		
		Random rnd = new Random();
		float roll = (rnd.nextInt(100))/100;
		if (hitChance >= 1 || roll<=hitChance ){
			int[] moveDamage = attacker.getDamage(moveNum, attacked.getTypes());
			attacker.getMoveSet()[moveNum].setPP(attacker.getMoveSet()[moveNum].getPP()-1);
			
			if(moveDamage[2]==0){
				displayTextQueue.add(attacked.getNameNick()+" is immune!");
			}else{
				if (moveDamage[1]==1){
					displayTextQueue.add("A critical hit!");
				}
				if(moveDamage[2]>10){
					displayTextQueue.add("It's super effective!");
				}else if(moveDamage[2]<10){
					displayTextQueue.add("It's not very effective...");
				}
			}
			
			this.doneHealthEnemy = false;
			this.doneHealthPlayer = false;
			
			attacked.setCurHP(attacked.getCurHP()-moveDamage[0]);
		}else{
			displayTextQueue.add(prefix+attacker.getNameNick()+"'s attack missed!");
		}
	}
	
	public void move(String dir){
		if (waitingAction){
			int x,y;
			if (currentMenu.compareTo("MAIN")==0){
				//<editor-fold defaultstate="collapsed" desc="Main">
					x = (optionsMain%2);
					y = (int)Math.floor(optionsMain/2);

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
					
					optionsMain = (y*2)+x;
				//</editor-fold>
			}else if (currentMenu.compareTo("POKEMON")==0 || currentMenu.compareTo("POKEMONF")==0){
				//<editor-fold defaultstate="collapsed" desc="Pokemon">
					x=0;
					y=optionsPokemon;

					switch(dir){
						case "UP":
							y = y-1;
						break;
						case "DOWN":
							y = y+1;
						break;
					}

					if (y<0){
						y = 5;
					}else if (y>5){
						y = 0;
					}

					optionsPokemon = y;
				//</editor-fold>
			}else if (currentMenu.compareTo("MOVES")==0){
				//<editor-fold defaultstate="collapsed" desc="Moves">
					x = (optionsMoves%2);
					y = (int)Math.floor(optionsMoves/2);

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
					
					optionsMoves = (y*2)+x;
				//</editor-fold>
			}else if (currentMenu.compareTo("BALLS")==0){
				//<editor-fold defaultstate="collapsed" desc="Balls">
					x = (optionsBalls%2);
					y = (int)Math.floor(optionsBalls/2);

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
					
					optionsBalls = (y*2)+x;
				//</editor-fold>
			}
		}
	}
	
	private void dispose(){
		Game.currentBattle = null;
		this.player.setInCombat(false);
	}
	
}
