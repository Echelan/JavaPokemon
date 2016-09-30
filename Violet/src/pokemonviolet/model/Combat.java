/*
 *  Pokemon Violet - A University Project by Andres Movilla
 *  Pokemon COPYRIGHT 2002-2016 Pokemon.
 *  COPYRIGHT 1995-2016 Nintendo/Creatures Inc./GAME FREAK inc. TRADEMARK, REGISTERED TRADEMARK
 *  and Pokemon character names are trademarks of Nintendo.
 *  No copyright or trademark infringement is intended in using Pokemon content on Pokemon Violet.
 */
package pokemonviolet.model;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Andres
 */
public class Combat {

	private Pokemon currentPlayerPokemon;
	private Pokemon currentEnemyPokemon;
	private boolean ready;
	private final ArrayList<String> displayTextQueue;
	private boolean waitingAction;
	private String currentMenu;
	private int displayHealthEnemy,displayHealthPlayer, displayExpPlayer;
	private boolean doneHealthEnemy, doneHealthPlayer, doneExpPlayer;
	private final int[] caught;
	private final Player player;
	private final Trainer enemy;
	private int optionsMain;
	private int optionsMoves;
	private int optionsBalls;
	private int optionsPokemon;
	
	private boolean inRound;
	private int turnNum;
	private boolean canDispose;
	private final boolean wildBattle;
	private final int maxX, maxY;
	
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
		
		maxX=Game.SCREEN_SIZE_X;
		maxY=Game.SCREEN_SIZE_Y;
		
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
		
		Game.gameState.add("COMBAT");
	}
	
	public void accept(){
		if (!this.ready){
			pokemonviolet.view.displayParser.CUR_ENEMY_X = pokemonviolet.view.displayParser.FINAL_ENEMY_X;
			pokemonviolet.view.displayParser.CUR_PLAYER_X = pokemonviolet.view.displayParser.FINAL_PLAYER_X;
			this.setReady(true);
		}else{
			if (isWaitingAction()){
				//<editor-fold defaultstate="collapsed" desc="User Input Menus">
					if (getCurrentMenu().compareTo("MAIN")==0){
						switch (getOptionsMain()) {
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
					}else if (getCurrentMenu().compareTo("MOVES")==0){
						if (getOptionsMoves()<getCurrentPlayerPokemon().getNumMoves()){
							waitingAction = false;
							getDisplayTextQueue().remove(0);
							if (getCurrentPlayerPokemon().getMoveSet()[getOptionsMoves()].getPP()>0){
								inRound = true;
								nextTurn();
							}else{
								getDisplayTextQueue().add("There is no PP left for this move!");
							}
						}
					}else if (getCurrentMenu().compareTo("BALLS")==0){
						String[] pokeTypes = {"POKEBALL","GREATBALL","ULTRABALL","PREMIERBALL","MASTERBALL"};
						String[] order = new String[4];
						int counter = 0;
						int numBalls = 0;
						for (int i = 0; i < pokeTypes.length; i++) {
							if (numBalls<4){
								boolean foundOne = false;
								while (counter<5 && !foundOne){
									if (getPlayer().getAmountItem(pokeTypes[counter])>0){
										order[numBalls] = pokeTypes[counter];
										foundOne = true;
										numBalls=numBalls+1;
									}
									counter=counter+1;
								}
							}
						}
						if (getOptionsBalls()<numBalls){
							waitingAction = false;
							getDisplayTextQueue().remove(0);
							inRound = true;
							throwBall(order[getOptionsBalls()]);
						}
					}else if (getCurrentMenu().compareTo("POKEMON")==0 || getCurrentMenu().compareTo("POKEMONF")==0){
						getDisplayTextQueue().remove(0);
						if (getCurrentMenu().compareTo("POKEMONF")==0){
							currentMenu = "MAIN";
						}else{
							waitingAction = false;
							inRound = true;
							turnNum = turnNum+1;
						}
						if (getCurrentMenu().compareTo("POKEMON")==0){
							getDisplayTextQueue().add("That's enough, "+getCurrentPlayerPokemon().getNameNick()+", come back!");
						}else{
						//	displayTextQueue.add("You did good, "+currentPlayerPokemon.getNameNick()+".");
						}
						getPlayer().setCurrentPokemon(getOptionsPokemon());
						currentPlayerPokemon = getPlayer().getTeam()[getPlayer().getCurrentPokemon()];
						getDisplayTextQueue().add("Go! "+getCurrentPlayerPokemon().getNameNick()+"!");
						this.setDisplayHealthPlayer(getCurrentPlayerPokemon().getCurHP());
						this.setDisplayExpPlayer(getCurrentPlayerPokemon().getCurEXP());
					}
				//</editor-fold>
			}else if (inRound){
				if (this.isDoneHealthEnemy() && this.isDoneHealthPlayer()){
					getDisplayTextQueue().remove(0);
					if (this.getCaught()[getEnemy().getCurrentPokemon()]==1){
						currentMenu = "MAIN";
						turnNum = 0;
						inRound = false;
					}else if (getCurrentEnemyPokemon().isFainted()){
						String prefix;
						if (this.wildBattle){
							prefix = "Wild ";
						}else{
							prefix = "Foe ";
						}
						getDisplayTextQueue().add(prefix+getCurrentEnemyPokemon().getNameNick() +" fainted!");
						/*
						*/
						currentMenu = "MAIN";
						turnNum = 0;
						inRound = false;
						
					}else if (getCurrentPlayerPokemon().isFainted()){
						getDisplayTextQueue().add(getCurrentEnemyPokemon().getNameNick() +" fainted!");
						turnNum = 0;
						inRound = false;
					}else{
						if (getDisplayTextQueue().isEmpty()){
							if (turnNum<2){
								nextTurn();
							}else{
								currentMenu = "MAIN";
								turnNum = 0;
								inRound = false;
								getDisplayTextQueue().add("");
								accept();
							}
						}
					}
				}
			}else{
				if (this.isDoneExpPlayer()){
					getDisplayTextQueue().remove(0);
					if (getDisplayTextQueue().isEmpty()){
						if (!currentEnemyPokemon.isFainted() && getCaught()[getEnemy().getCurrentPokemon()]==0 && !currentPlayerPokemon.isFainted()){
							waitingAction = true;
							getDisplayTextQueue().add("What will "+getCurrentPlayerPokemon().getNameNick()+ " do?");
						}else if (getCurrentEnemyPokemon().isFainted() && getEnemy().getCurrentPokemon()+1<getEnemy().getNumPokemonTeam()){
							
							getDisplayTextQueue().add(getCurrentPlayerPokemon().getNameNick() +" gained "+getCurrentEnemyPokemon().getExpGain()+" EXP!");
							boolean levelUp = getCurrentPlayerPokemon().setCurEXP(getCurrentPlayerPokemon().getCurEXP()+getCurrentEnemyPokemon().getExpGain());
							this.setDoneExpPlayer(false);
							if (levelUp){
								getDisplayTextQueue().add(getCurrentPlayerPokemon().getNameNick()+" is now level "+getCurrentPlayerPokemon().getLevel()+"!");
							}
						
							getEnemy().setCurrentPokemon(getEnemy().getCurrentPokemon()+1);
							currentEnemyPokemon = getEnemy().getTeam()[getEnemy().getCurrentPokemon()];
							this.setDisplayHealthEnemy(getCurrentEnemyPokemon().getCurHP());

							if (this.wildBattle){
								getDisplayTextQueue().add("A wild "+getCurrentEnemyPokemon().getNameNick()+" rushed at you!");
							}else{
								getDisplayTextQueue().add("Foe "+getEnemy().getName()+" sent out "+getCurrentEnemyPokemon().getNameNick()+"!");
							}
						}else if (getCaught()[getEnemy().getCurrentPokemon()]==1 && getEnemy().getCurrentPokemon()+1<getEnemy().getNumPokemonTeam()){
							
							getDisplayTextQueue().add(getCurrentPlayerPokemon().getNameNick() +" gained "+getCurrentEnemyPokemon().getExpGain()+" EXP!");
							boolean levelUp = getCurrentPlayerPokemon().setCurEXP(getCurrentPlayerPokemon().getCurEXP()+getCurrentEnemyPokemon().getExpGain());
							this.setDoneExpPlayer(false);
							if (levelUp){
								getDisplayTextQueue().add(getCurrentPlayerPokemon().getNameNick()+" is now level "+getCurrentPlayerPokemon().getLevel()+"!");
							}
						
							getEnemy().setCurrentPokemon(getEnemy().getCurrentPokemon()+1);
							currentEnemyPokemon = getEnemy().getTeam()[getEnemy().getCurrentPokemon()];
							this.setDisplayHealthEnemy(getCurrentEnemyPokemon().getCurHP());

							if (this.wildBattle){
								getDisplayTextQueue().add("A wild "+getCurrentEnemyPokemon().getNameNick()+" rushed at you!");
							}else{
								getDisplayTextQueue().add("Foe "+getEnemy().getName()+" sent out "+getCurrentEnemyPokemon().getNameNick()+"!");
							}
						}else if (getCurrentPlayerPokemon().isFainted() && getPlayer().getNextAvailablePokemon()!= -1){
								getDisplayTextQueue().add("What Pokemon will you send out next?");
								currentMenu = "POKEMONF";
								waitingAction = true;
						}else if (!canDispose){
							canDispose = true;
							if (getCurrentPlayerPokemon().isFainted()){
								getDisplayTextQueue().add(getPlayer().getName()+ " wiped out!");
							}else{
								if (this.getCaught()[getEnemy().getCurrentPokemon()]!=1){
									getDisplayTextQueue().add(getCurrentPlayerPokemon().getNameNick() +" gained "+getCurrentEnemyPokemon().getExpGain()+" EXP!");
									boolean levelUp = getCurrentPlayerPokemon().setCurEXP(getCurrentPlayerPokemon().getCurEXP()+getCurrentEnemyPokemon().getExpGain());
									this.setDoneExpPlayer(false);
									if (levelUp){
										getDisplayTextQueue().add(getCurrentPlayerPokemon().getNameNick()+" is now level "+getCurrentPlayerPokemon().getLevel()+"!");
									}
								}
								Random rnd = new Random();
								int lootRoll = rnd.nextInt(100);
								if( lootRoll == 0){
									getDisplayTextQueue().add("Found a Masterball!");
									getPlayer().addItem("MASTERBALL",1);
								}else if (lootRoll<10){
									getDisplayTextQueue().add("Found an Ultraball!");
									getPlayer().addItem("ULTRABALL",1);
								}else if (lootRoll<40){
									getDisplayTextQueue().add("Found a Greatball!");
									getPlayer().addItem("GREATBALL",1);
								}else if (lootRoll<50){
									getDisplayTextQueue().add("Found a Premier Ball!");
									getPlayer().addItem("PREMIERBALL",1);
								}else{
									getDisplayTextQueue().add("Found a Pokeball!");
									getPlayer().addItem("POKEBALL",1);
								}
							}
						}else{
							getPlayer().pokemonCenter();
							this.dispose();
						}
					}
				}
			}
		}
	}
	
	public void cancel(){
		if (isWaitingAction()){
			if (getCurrentMenu().compareTo("MAIN")==0){
				optionsMain = 3;
			}else if (getCurrentMenu().compareTo("POKEMONF")!=0){
				currentMenu = "MAIN";
			}
		}
	}
	
	private void throwBall(String internalName){
		turnNum = turnNum+1;
		getPlayer().subItem(internalName);
		int shakes = getCurrentEnemyPokemon().doCatch(internalName);
		getDisplayTextQueue().add(getPlayer().getName()+" throws a "+new Item(internalName).getNameSingular()+"!");
		switch (shakes) {
			case 0:
				getDisplayTextQueue().add(getCurrentEnemyPokemon().getNameNick()+" escaped!");
				this.caught[getEnemy().getCurrentPokemon()]=2;
			break;
			case 1:
				getDisplayTextQueue().add("The "+new Item(internalName).getNameSingular()+" broke!");
				this.caught[getEnemy().getCurrentPokemon()]=2;
			break;
			case 2:
				getDisplayTextQueue().add("Couldn't catch "+getCurrentEnemyPokemon().getNameNick()+"!");
				this.caught[getEnemy().getCurrentPokemon()]=2;
			break;
			case 3:
				getDisplayTextQueue().add("Oh no! It was so close too!");
				this.caught[getEnemy().getCurrentPokemon()]=2;
			break;
			case 4:
				getDisplayTextQueue().add("Gotcha! "+getCurrentEnemyPokemon().getNameNick()+" was caught!");
				this.caught[getEnemy().getCurrentPokemon()]=1;
				getPlayer().addPokemon(getCurrentEnemyPokemon());
			break;
		}
	}
	
	private void nextTurn(){
		if (this.getCaught()[getEnemy().getCurrentPokemon()]==2){
			this.caught[getEnemy().getCurrentPokemon()]=0;
			Random rnd = new Random();
			int randomMove = (rnd.nextInt(getCurrentEnemyPokemon().getNumMoves()));
				String prefix;
				if (this.wildBattle){
					prefix = "Wild ";
				}else{
					prefix = "Foe ";
				}
			doTurn(getCurrentEnemyPokemon(), getCurrentPlayerPokemon(),randomMove,prefix);
			turnNum = turnNum+1;
		}else if (getCurrentEnemyPokemon().getStatSpeed() >= getCurrentPlayerPokemon().getStatSpeed()){
			if (turnNum == 0){
				Random rnd = new Random();
				int randomMove = (rnd.nextInt(getCurrentEnemyPokemon().getNumMoves()));
				String prefix;
				if (this.wildBattle){
					prefix = "Wild ";
				}else{
					prefix = "Foe ";
				}
				doTurn(getCurrentEnemyPokemon(), getCurrentPlayerPokemon(),randomMove,prefix);
			}else{
				doTurn(getCurrentPlayerPokemon(), getCurrentEnemyPokemon(), getOptionsMoves(),"");
			}
			turnNum = turnNum+1;
		}else if (getCurrentEnemyPokemon().getStatSpeed() < getCurrentPlayerPokemon().getStatSpeed()){
			if (turnNum == 1){
				Random rnd = new Random();
				int randomMove = (rnd.nextInt(getCurrentEnemyPokemon().getNumMoves()));
				String prefix;
				if (this.wildBattle){
					prefix = "Wild ";
				}else{
					prefix = "Foe ";
				}
				doTurn(getCurrentEnemyPokemon(), getCurrentPlayerPokemon(),randomMove,prefix);
			}else{
				doTurn(getCurrentPlayerPokemon(), getCurrentEnemyPokemon(), getOptionsMoves(),"");
			}
			turnNum = turnNum+1;
		}
	}
	
	private void doTurn(Pokemon attacker, Pokemon attacked, int moveNum, String prefix){
		
		getDisplayTextQueue().add(prefix+attacker.getNameNick()+" used "+attacker.getMoveSet()[moveNum].getNameDisplay()+"!");
		
		float hitChance = (attacker.getMoveSet()[moveNum].getAccuracy()/100)*(attacker.getAccuracy()/attacked.getEvasion());
		
		Random rnd = new Random();
		float roll = (rnd.nextInt(100))/100;
		if (hitChance >= 1 || roll<=hitChance ){
			int[] moveDamage = attacker.getDamage(moveNum, attacked.getTypes());
			attacker.getMoveSet()[moveNum].setPP(attacker.getMoveSet()[moveNum].getPP()-1);
			
			if(moveDamage[2]==0){
				getDisplayTextQueue().add(attacked.getNameNick()+" is immune!");
			}else{
				if (moveDamage[1]==1){
					getDisplayTextQueue().add("A critical hit!");
				}
				if(moveDamage[2]>10){
					getDisplayTextQueue().add("It's super effective!");
				}else if(moveDamage[2]<10){
					getDisplayTextQueue().add("It's not very effective...");
				}
			}
			
			this.setDoneHealthEnemy(false);
			this.setDoneHealthPlayer(false);
			
			attacked.setCurHP(attacked.getCurHP()-moveDamage[0]);
		}else{
			getDisplayTextQueue().add(prefix+attacker.getNameNick()+"'s attack missed!");
		}
	}
	
	public void move(String dir){
		if (isWaitingAction()){
			int x,y;
			if (getCurrentMenu().compareTo("MAIN")==0){
				//<editor-fold defaultstate="collapsed" desc="Main">
					x = (getOptionsMain()%2);
					y = (int)Math.floor(getOptionsMain()/2);

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
			}else if (getCurrentMenu().compareTo("POKEMON")==0 || getCurrentMenu().compareTo("POKEMONF")==0){
				//<editor-fold defaultstate="collapsed" desc="Pokemon">
					x=0;
					y=getOptionsPokemon();

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
			}else if (getCurrentMenu().compareTo("MOVES")==0){
				//<editor-fold defaultstate="collapsed" desc="Moves">
					x = (getOptionsMoves()%2);
					y = (int)Math.floor(getOptionsMoves()/2);

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
			}else if (getCurrentMenu().compareTo("BALLS")==0){
				//<editor-fold defaultstate="collapsed" desc="Balls">
					x = (getOptionsBalls()%2);
					y = (int)Math.floor(getOptionsBalls()/2);

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
		this.getPlayer().setInCombat(false);
		Game.gameState.remove(Game.gameState.size()-1);
	}
	
	public void receiveKeyAction(String action,String state){
		if (state.compareTo("RELEASE")==0){
			if (action.compareTo("A")==0){
				accept();
			}else if (action.compareTo("B")==0){
				cancel();
			}
		}else if(state.compareTo("PRESS")==0){
			if (action.compareTo("A")!=0 && action.compareTo("B")!=0){
				move(action);
			}
		}
	}
	
	//<editor-fold defaultstate="collapsed" desc="Getters & Setters">

		/**
		 * @return the optionsMain
		 */
		public int getOptionsMain() {
			return optionsMain;
		}

		/**
		 * @return the optionsMoves
		 */
		public int getOptionsMoves() {
			return optionsMoves;
		}

		/**
		 * @return the optionsBalls
		 */
		public int getOptionsBalls() {
			return optionsBalls;
		}

		/**
		 * @return the optionsPokemon
		 */
		public int getOptionsPokemon() {
			return optionsPokemon;
		}

		/**
		 * @param aDisplayHealthEnemy the displayHealthEnemy to set
		 */
		public void setDisplayHealthEnemy(int aDisplayHealthEnemy) {
			displayHealthEnemy = aDisplayHealthEnemy;
		}

		/**
		 * @param aDisplayHealthPlayer the displayHealthPlayer to set
		 */
		public void setDisplayHealthPlayer(int aDisplayHealthPlayer) {
			displayHealthPlayer = aDisplayHealthPlayer;
		}

		/**
		 * @param aDisplayExpPlayer the displayExpPlayer to set
		 */
		public void setDisplayExpPlayer(int aDisplayExpPlayer) {
			displayExpPlayer = aDisplayExpPlayer;
		}

		/**
		 * @return the doneHealthEnemy
		 */
		public boolean isDoneHealthEnemy() {
			return doneHealthEnemy;
		}

		/**
		 * @return the doneHealthPlayer
		 */
		public boolean isDoneHealthPlayer() {
			return doneHealthPlayer;
		}

		/**
		 * @return the doneExpPlayer
		 */
		public boolean isDoneExpPlayer() {
		   return doneExpPlayer;
	   }

		/**
		 * @return the currentPlayerPokemon
		 */
		public Pokemon getCurrentPlayerPokemon() {
			return currentPlayerPokemon;
		}

		/**
		 * @return the currentEnemyPokemon
		 */
		public Pokemon getCurrentEnemyPokemon() {
			return currentEnemyPokemon;
		}

		/**
		 * @return the displayTextQueue
		 */
		public ArrayList<String> getDisplayTextQueue() {
			return displayTextQueue;
		}

		/**
		 * @param aReady the ready to set
		 */
		public void setReady(boolean aReady) {
			ready = aReady;
		}

		/**
		 * @return the waitingAction
		 */
		public boolean isWaitingAction() {
			return waitingAction;
		}

		/**
		 * @return the currentMenu
		 */
		public String getCurrentMenu() {
			return currentMenu;
		}

		/**
		 * @return the displayHealthEnemy
		 */
		public int getDisplayHealthEnemy() {
			return displayHealthEnemy;
		}

		/**
		 * @return the displayHealthPlayer
		 */
		public int getDisplayHealthPlayer() {
			return displayHealthPlayer;
		}

		/**
		 * @return the displayExpPlayer
		 */
		public int getDisplayExpPlayer() {
			return displayExpPlayer;
		}

		/**
		 * @return the caught
		 */
		public int[] getCaught() {
			return caught;
		}

		/**
		 * @return the player
		 */
		public Player getPlayer() {
			return player;
		}

		/**
		 * @return the enemy
		 */
		public Trainer getEnemy() {
			return enemy;
		}

		/**
		 * @param aDoneHealthEnemy the doneHealthEnemy to set
		 */
		public void setDoneHealthEnemy(boolean aDoneHealthEnemy) {
			doneHealthEnemy = aDoneHealthEnemy;
		}

		/**
		 * @param aDoneHealthPlayer the doneHealthPlayer to set
		 */
		public void setDoneHealthPlayer(boolean aDoneHealthPlayer) {
			doneHealthPlayer = aDoneHealthPlayer;
		}

		/**
		 * @param aDoneExpPlayer the doneExpPlayer to set
		 */
		public void setDoneExpPlayer(boolean aDoneExpPlayer) {
			doneExpPlayer = aDoneExpPlayer;
		}
	//</editor-fold>
}
