/*
 *  Pokemon Violet - A University Project by Andres Movilla
 *  Pokemon COPYRIGHT 2002-2016 Pokemon.
 *  COPYRIGHT 1995-2016 Nintendo/Creatures Inc./GAME FREAK inc. TRADEMARK, REGISTERED TRADEMARK
 *  and Pokemon character names are trademarks of Nintendo.
 *  No copyright or trademark infringement is intended in using Pokemon content on Pokemon Violet.
 */
package pokemonviolet.scenes;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import javax.imageio.ImageIO;
import pokemonviolet.model.Handler;
import pokemonviolet.model.Item;
import pokemonviolet.model.Player;
import pokemonviolet.model.Pokemon;
import pokemonviolet.model.Trainer;

/**
 *
 * @author Andres
 */
public class Combat extends Scene {

	//<editor-fold defaultstate="collapsed" desc="Attributes">
		private static final int RESIZE = 2;
		private static final int SPRITE_WIDTH = 80 * RESIZE;
		private static final int SPRITE_HEIGHT = 80 * RESIZE;
		public final int finalPlayerX = 10, finalEnemyX = 220;
		public int curPlayerX = finalPlayerX + 500, curEnemyX = finalEnemyX - 500;
		private Pokemon currentPlayerPokemon;
		private Pokemon currentEnemyPokemon;
		private boolean ready;
		private final ArrayList<String> displayTextQueue;
		private boolean waitingAction;
		private String currentMenu;
		private int displayHealthEnemy, displayHealthPlayer, displayExpPlayer;
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
		private String selfPrefix, enemyPrefix;
	//</editor-fold>

	public Combat(Player player, Trainer enemy, boolean wildBattle, Handler main) {
		super(main, "COMBAT", true);
		
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
		this.currentMenu = "MAIN";
		this.ready = false;
		this.wildBattle = wildBattle;

		selfPrefix = this.player.getName() + "'s";

		if (this.wildBattle) {
			enemyPrefix = "Wild";
		} else {
			enemyPrefix = "Foe";
		}

		maxX = Handler.SCREEN_SIZE_X;
		maxY = Handler.SCREEN_SIZE_Y;

		for (int i = 0; i < enemy.getNumPokemonTeam(); i++) {
			this.caught[i] = 0;
			enemy.getTeam()[i].setAccuracy(1);
			enemy.getTeam()[i].setEvasion(1);
		}

		for (int i = 0; i < player.getNumPokemonTeam(); i++) {
			player.getTeam()[i].setAccuracy(1);
			player.getTeam()[i].setEvasion(1);
		}

		player.setCurrentPokemon(player.getFirstAvailablePokemon());
		this.currentEnemyPokemon = enemy.getTeam()[enemy.getCurrentPokemon()];
		this.currentPlayerPokemon = player.getTeam()[player.getCurrentPokemon()];

		this.displayHealthEnemy = currentEnemyPokemon.getCurHP();
		this.displayExpPlayer = currentPlayerPokemon.getCurEXP();
		this.displayHealthPlayer = currentPlayerPokemon.getCurHP();
		this.doneHealthEnemy = true;
		this.doneHealthPlayer = true;
		this.doneExpPlayer = true;

		displayTextQueue = new ArrayList<String>();
		displayTextQueue.add("A wild " + currentEnemyPokemon.getNameNick() + " appeared!");

		this.player.setSpawnSteps(this.player.roll(2, 2, 4));
	}

	private void throwBall(String internalName) {
		turnNum = turnNum + 1;
		player.subItem(internalName);
		int shakes = currentEnemyPokemon.doCatch(internalName);
		displayTextQueue.add(player.getName() + " throws a " + new Item(internalName).getNameSingular() + "!");

		switch (shakes) {
			case 0:
				displayTextQueue.add(currentEnemyPokemon.getNameNick() + " escaped!");
				this.caught[enemy.getCurrentPokemon()] = 2;
				break;
			case 1:
				displayTextQueue.add("The " + new Item(internalName).getNameSingular() + " broke!");
				this.caught[enemy.getCurrentPokemon()] = 2;
				break;
			case 2:
				displayTextQueue.add("Couldn't catch " + currentEnemyPokemon.getNameNick() + "!");
				this.caught[enemy.getCurrentPokemon()] = 2;
				break;
			case 3:
				displayTextQueue.add("Oh no! It was so close too!");
				this.caught[enemy.getCurrentPokemon()] = 2;
				break;
			case 4:
				displayTextQueue.add("Gotcha! " + currentEnemyPokemon.getNameNick() + " was caught!");
				this.caught[enemy.getCurrentPokemon()] = 1;
				player.addPokemon(currentEnemyPokemon);
				break;
		}
	}

	private void nextTurn() {
		if (this.caught[enemy.getCurrentPokemon()] == 2) {
			this.caught[enemy.getCurrentPokemon()] = 0;
			Random rnd = new Random();
			int randomMove = (rnd.nextInt(currentEnemyPokemon.getNumMoves()));
			
			doTurn(currentEnemyPokemon, currentPlayerPokemon, randomMove, enemyPrefix, selfPrefix);
			turnNum = turnNum + 1;
		} else if (currentEnemyPokemon.getStatSpeed() >= currentPlayerPokemon.getStatSpeed()) {
			if (turnNum == 0) {
				Random rnd = new Random();
				int randomMove = (rnd.nextInt(currentEnemyPokemon.getNumMoves()));
				
				doTurn(currentEnemyPokemon, currentPlayerPokemon, randomMove, enemyPrefix, selfPrefix);
			} else {
				doTurn(currentPlayerPokemon, currentEnemyPokemon, optionsMoves, selfPrefix, enemyPrefix);
			}
			turnNum = turnNum + 1;
		} else if (currentEnemyPokemon.getStatSpeed() < currentPlayerPokemon.getStatSpeed()) {
			if (turnNum == 1) {
				Random rnd = new Random();
				int randomMove = (rnd.nextInt(currentEnemyPokemon.getNumMoves()));
				
				doTurn(currentEnemyPokemon, currentPlayerPokemon, randomMove, enemyPrefix, selfPrefix);
			} else {
				doTurn(currentPlayerPokemon, currentEnemyPokemon, optionsMoves, selfPrefix, enemyPrefix);
			}
			turnNum = turnNum + 1;
		}
	}

	private void doTurn(Pokemon attacker, Pokemon attacked, int moveNum, String attackerPrefix, String attackedPrefix) {
		displayTextQueue.add(attackerPrefix + " " + attacker.getNameNick() + " used " + attacker.getMoveSet()[moveNum].getNameDisplay() + "!");

		float hitChance = (attacker.getMoveSet()[moveNum].getAccuracy() / 100) * (attacker.getAccuracy() / attacked.getEvasion());

		Random rnd = new Random();
		float roll = (rnd.nextInt(100)) / 100;
		if (hitChance >= 1 || roll <= hitChance) {
			int[] moveDamage = attacker.getDamage(moveNum, attacked.getTypes());
			attacker.getMoveSet()[moveNum].setPP(attacker.getMoveSet()[moveNum].getPP() - 1);

			if (moveDamage[2] == 0) {
				displayTextQueue.add(attackedPrefix + " " + attacked.getNameNick() + " is immune!");
			} else {
				if (moveDamage[1] == 1) {
					displayTextQueue.add("A critical hit!");
				}
				if (moveDamage[2] > 10) {
					displayTextQueue.add("It's super effective!");
				} else if (moveDamage[2] < 10) {
					displayTextQueue.add("It's not very effective...");
				}
			}

			doneHealthEnemy = false;
			doneHealthPlayer = false;

			attacked.setCurHP(attacked.getCurHP() - moveDamage[0]);
		} else {
			displayTextQueue.add(attackerPrefix + " " + attacker.getNameNick() + "'s attack missed!");
		}
	}

	@Override
	public void receiveKeyAction(String action, String state) {
		if (state.compareTo("RELEASE") == 0) {
			if (action.compareTo("A") == 0) {
				accept();
			} else if (action.compareTo("B") == 0) {
				cancel();
			}
		} else if (state.compareTo("PRESS") == 0) {
			if (action.compareTo("A") != 0 && action.compareTo("B") != 0 && action.compareTo("START") != 0) {
				move(action);
			}
		}
	}

	@Override
	protected void accept() {
		if (!this.ready) {
			curEnemyX = finalEnemyX;
			curPlayerX = finalPlayerX;
			ready = true;
		} else if (waitingAction) {
			subActionWaiting();
		} else if (inRound) {
			subActionRound();
		} else {
			subActionElse();
		}
	}
	//<editor-fold defaultstate="collapsed" desc="subActions">
		private void subActionWaiting() {
			if (currentMenu.compareTo("MAIN") == 0) {
				switch (optionsMain) {
					case 0:
						this.currentMenu = "MOVES";
						break;
					case 1:
						this.currentMenu = "BALLS";
						break;
					case 2:
						this.currentMenu = "POKEMON";
						break;
					case 3:
						this.dispose();
						break;
					default:

						break;
				}
			} else if (currentMenu.compareTo("MOVES") == 0) {
				subSubActionMoves();
			} else if (currentMenu.compareTo("BALLS") == 0) {
				subSubActionBalls();
			} else if (currentMenu.compareTo("POKEMON") == 0 || currentMenu.compareTo("POKEMONF") == 0) {
				subSubActionPokemon();
			}
		}

		//<editor-fold defaultstate="collapsed" desc="subSubActionsWaiting">
			private void subSubActionMoves() {
				if (optionsMoves < currentPlayerPokemon.getNumMoves()) {
					waitingAction = false;
					displayTextQueue.remove(0);
					if (currentPlayerPokemon.getMoveSet()[optionsMoves].getPP() > 0) {
						inRound = true;
						nextTurn();
					} else {
						displayTextQueue.add("There is no PP left for this move!");
					}
				}
			}

			private void subSubActionPokemon() {
				if (optionsPokemon < player.getNumPokemonTeam() && player.getCurrentPokemon() != optionsPokemon) {
					if (!player.getTeam()[optionsPokemon].isFainted()) {
						displayTextQueue.remove(0);
						if (currentMenu.compareTo("POKEMONF") == 0) {
							currentMenu = "MAIN";
						} else {
							waitingAction = false;
							inRound = true;
							turnNum = turnNum + 1;
						}
						if (currentMenu.compareTo("POKEMON") == 0) {
							displayTextQueue.add("That's enough, " + currentPlayerPokemon.getNameNick() + ", come back!");
						} else {
							//	displayTextQueue.add("You did good, "+currentPlayerPokemon.getNameNick()+".");
						}
						player.setCurrentPokemon(optionsPokemon);
						currentPlayerPokemon = player.getTeam()[player.getCurrentPokemon()];
						displayTextQueue.add("Go! " + currentPlayerPokemon.getNameNick() + "!");
						displayHealthPlayer = currentPlayerPokemon.getCurHP();
						displayExpPlayer = currentPlayerPokemon.getCurEXP();
					}
				}
			}

			private void subSubActionBalls() {
			String[] pokeTypes = {"POKEBALL", "GREATBALL", "ULTRABALL", "PREMIERBALL", "MASTERBALL"};
			String[] order = new String[4];
			int counter = 0;
			int numBalls = 0;
			for (int i = 0; i < pokeTypes.length; i++) {
				if (numBalls < 4) {
					boolean foundOne = false;
					while (counter < 5 && !foundOne) {
						if (player.searchItem(pokeTypes[counter]) != null) {
							order[numBalls] = pokeTypes[counter];
							foundOne = true;
							numBalls = numBalls + 1;
						}
						counter = counter + 1;
					}
				}
			}
			if (optionsBalls < numBalls) {
				waitingAction = false;
				displayTextQueue.remove(0);
				inRound = true;
				throwBall(order[optionsBalls]);
			}
		}
		//</editor-fold>

		private void subActionRound() {
			if (doneHealthEnemy && doneHealthPlayer) {
				displayTextQueue.remove(0);
				if (caught[enemy.getCurrentPokemon()] == 1) {
					currentMenu = "MAIN";
					turnNum = 0;
					inRound = false;
				} else if (currentEnemyPokemon.isFainted()) {
					displayTextQueue.add(enemyPrefix + " "  + currentEnemyPokemon.getNameNick() + " fainted!");

					currentMenu = "MAIN";
					turnNum = 0;
					inRound = false;
				} else if (currentPlayerPokemon.isFainted()) {
					displayTextQueue.add(selfPrefix + " " + currentPlayerPokemon.getNameNick() + " fainted!");
					
					currentMenu = "MAIN";
					turnNum = 0;
					inRound = false;
				} else if (displayTextQueue.isEmpty()) {
					if (turnNum < 2) {
						nextTurn();
					} else {
						currentMenu = "MAIN";
						turnNum = 0;
						inRound = false;
						displayTextQueue.add("");
						accept();
					}
				}
			}
		}

		private void subActionElse() {
			if (doneExpPlayer) {
				displayTextQueue.remove(0);
				if (displayTextQueue.isEmpty()) {
					if (!currentEnemyPokemon.isFainted() && caught[enemy.getCurrentPokemon()] == 0 && !currentPlayerPokemon.isFainted()) {
						waitingAction = true;
						displayTextQueue.add("What will " + currentPlayerPokemon.getNameNick() + " do?");
					} else if (currentEnemyPokemon.isFainted() && enemy.getCurrentPokemon() + 1 < enemy.getNumPokemonTeam()) {
						subSubActionEnemyFaint();
					} else if (caught[enemy.getCurrentPokemon()] == 1 && enemy.getCurrentPokemon() + 1 < enemy.getNumPokemonTeam()) {
						subSubActionEnemyCaught();
					} else if (currentPlayerPokemon.isFainted() && player.getFirstAvailablePokemon() != -1) {
						displayTextQueue.add("What Pokemon will you send out next?");
						currentMenu = "POKEMONF";
						waitingAction = true;
					} else if (!canDispose) {
//						TODO: WHEN PLAYER BLACKS OUT, MENU POPS UP
						subSubActionPreDispose();
					} else {
						this.dispose();
					}
				}
			}
		}

		//<editor-fold defaultstate="collapsed" desc="subSubActionsElse">
			private void subSubActionEnemyFaint() {
				displayTextQueue.add(currentPlayerPokemon.getNameNick() + " gained " + currentEnemyPokemon.getExpGain() + " EXP!");
				boolean levelUp = currentPlayerPokemon.setCurEXP(currentPlayerPokemon.getCurEXP() + currentEnemyPokemon.getExpGain());
				doneExpPlayer = false;
				if (levelUp) {
					displayTextQueue.add(currentPlayerPokemon.getNameNick() + " is now level " + currentPlayerPokemon.getLevel() + "!");
				}

				enemy.setCurrentPokemon(enemy.getCurrentPokemon() + 1);
				currentEnemyPokemon = enemy.getTeam()[enemy.getCurrentPokemon()];
				displayHealthEnemy = currentEnemyPokemon.getCurHP();

				if (this.wildBattle) {
					displayTextQueue.add("A wild " + currentEnemyPokemon.getNameNick() + " rushed at you!");
				} else {
					displayTextQueue.add("Foe " + enemy.getName() + " sent out " + currentEnemyPokemon.getNameNick() + "!");
				}
			}

			private void subSubActionEnemyCaught() {
				displayTextQueue.add(currentPlayerPokemon.getNameNick() + " gained " + currentEnemyPokemon.getExpGain() + " EXP!");
				boolean levelUp = currentPlayerPokemon.setCurEXP(currentPlayerPokemon.getCurEXP() + currentEnemyPokemon.getExpGain());
				doneExpPlayer = false;
				if (levelUp) {
					displayTextQueue.add(currentPlayerPokemon.getNameNick() + " is now level " + currentPlayerPokemon.getLevel() + "!");
				}

				enemy.setCurrentPokemon(enemy.getCurrentPokemon() + 1);
				currentEnemyPokemon = enemy.getTeam()[enemy.getCurrentPokemon()];
				displayHealthEnemy = currentEnemyPokemon.getCurHP();

				if (this.wildBattle) {
					displayTextQueue.add("A wild " + currentEnemyPokemon.getNameNick() + " rushed at you!");
				} else {
					displayTextQueue.add("Foe " + enemy.getName() + " sent out " + currentEnemyPokemon.getNameNick() + "!");
				}
			}

			private void subSubActionPreDispose() {
				canDispose = true;
				if (currentPlayerPokemon.isFainted()) {
					displayTextQueue.add(player.getName() + " blacked out!");
					displayTextQueue.add(player.getName() + " was left with $"+player.blackOut()+"!");
				} else {
					if (this.caught[enemy.getCurrentPokemon()] != 1) {
						displayTextQueue.add(currentPlayerPokemon.getNameNick() + " gained " + currentEnemyPokemon.getExpGain() + " EXP!");
						boolean levelUp = currentPlayerPokemon.setCurEXP(currentPlayerPokemon.getCurEXP() + currentEnemyPokemon.getExpGain());
						doneExpPlayer = false;
						if (levelUp) {
							displayTextQueue.add(currentPlayerPokemon.getNameNick() + " is now level " + currentPlayerPokemon.getLevel() + "!");
						}
					}
					displayTextQueue.add(player.getName() + " earned $" + enemy.getReward() + "!");
					player.setFunds(player.getFunds()+enemy.getReward());
				}
			}
		//</editor-fold>
	//</editor-fold>

	@Override
	protected void cancel() {
		if (waitingAction) {
			if (currentMenu.compareTo("MAIN") == 0) {
				optionsMain = 3;
			} else if (currentMenu.compareTo("POKEMONF") != 0) {
				currentMenu = "MAIN";
			}
		}
	}
	
	@Override
	public void move(String dir) {
		if (waitingAction) {
			int x, y;
			if (currentMenu.compareTo("MAIN") == 0) {
				//<editor-fold defaultstate="collapsed" desc="Main">
				x = (optionsMain % 2);
				y = (int) Math.floor(optionsMain / 2);

				switch (dir) {
					case "LEFT":
						x = x - 1;
						break;
					case "UP":
						y = y - 1;
						break;
					case "RIGHT":
						x = x + 1;
						break;
					case "DOWN":
						y = y + 1;
						break;
				}

				if (x < 0) {
					x = 1;
				} else if (x > 1) {
					x = 0;
				}

				if (y < 0) {
					y = 1;
				} else if (y > 1) {
					y = 0;
				}

				optionsMain = (y * 2) + x;
				//</editor-fold>
			} else if (currentMenu.compareTo("POKEMON") == 0 || currentMenu.compareTo("POKEMONF") == 0) {
				//<editor-fold defaultstate="collapsed" desc="Pokemon">
				x = 0;
				y = optionsPokemon;

				switch (dir) {
					case "UP":
						y = y - 1;
						break;
					case "DOWN":
						y = y + 1;
						break;
				}

				if (y < 0) {
					y = 5;
				} else if (y > 5) {
					y = 0;
				}

				optionsPokemon = y;
				//</editor-fold>
			} else if (currentMenu.compareTo("MOVES") == 0) {
				//<editor-fold defaultstate="collapsed" desc="Moves">
				x = (optionsMoves % 2);
				y = (int) Math.floor(optionsMoves / 2);

				switch (dir) {
					case "LEFT":
						x = x - 1;
						break;
					case "UP":
						y = y - 1;
						break;
					case "RIGHT":
						x = x + 1;
						break;
					case "DOWN":
						y = y + 1;
						break;
				}

				if (x < 0) {
					x = 1;
				} else if (x > 1) {
					x = 0;
				}

				if (y < 0) {
					y = 1;
				} else if (y > 1) {
					y = 0;
				}

				optionsMoves = (y * 2) + x;
				//</editor-fold>
			} else if (currentMenu.compareTo("BALLS") == 0) {
				//<editor-fold defaultstate="collapsed" desc="Balls">
				x = (optionsBalls % 2);
				y = (int) Math.floor(optionsBalls / 2);

				switch (dir) {
					case "LEFT":
						x = x - 1;
						break;
					case "UP":
						y = y - 1;
						break;
					case "RIGHT":
						x = x + 1;
						break;
					case "DOWN":
						y = y + 1;
						break;
				}

				if (x < 0) {
					x = 1;
				} else if (x > 1) {
					x = 0;
				}

				if (y < 0) {
					y = 1;
				} else if (y > 1) {
					y = 0;
				}

				optionsBalls = (y * 2) + x;
				//</editor-fold>
			}
		}
	}

	@Override
	protected void dispose() {
//		main.player.setInCombat(false);
		main.gameState.remove(main.gameState.size() - 1);
	}

	@Override
	protected void start() {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
	
	@Override
	public BufferedImage getDisplay() {
		int ssX = pokemonviolet.model.Handler.SCREEN_SIZE_X, ssY = pokemonviolet.model.Handler.SCREEN_SIZE_Y;
		BufferedImage display = new BufferedImage(ssX, ssY, BufferedImage.TYPE_INT_ARGB);
		Graphics g = display.getGraphics();

		//<editor-fold defaultstate="collapsed" desc="Background">
		try {
			g.drawImage(ImageIO.read(new File("assets/combat/background.png")), 0, 0, ssX, ssY, null);
		} catch (IOException ex) {
		}

		try {
			g.drawImage(ImageIO.read(new File("assets/combat/grassunderground1.png")), curEnemyX, 85, 128 * RESIZE, 36 * RESIZE, null);
			g.drawImage(ImageIO.read(new File("assets/combat/grassunderground2.png")), curPlayerX, 180, 128 * RESIZE, 36 * RESIZE, null);
		} catch (IOException ex) {
		}
		//</editor-fold>

		int curCaught = caught[enemy.getCurrentPokemon()];

		//<editor-fold defaultstate="collapsed" desc="Pokemon Sprites Display">
		try {
			if (displayHealthEnemy != 0 && curCaught != 1) {
				g.drawImage(currentEnemyPokemon.getFrontImage(), curEnemyX + 50, 0, SPRITE_WIDTH, SPRITE_HEIGHT, null);
			} else if (curCaught == 1) {
				int x = (curEnemyX + 50 + (SPRITE_WIDTH / 2) + 20 - (14 * RESIZE)), y = (18 + SPRITE_HEIGHT - 50 - (14 * RESIZE));
				int pokeX = 0;
				switch (currentEnemyPokemon.getBallType()) {
					case "GREATBALL":
						pokeX = 1;
						break;
					case "PREMIERBALL":
						pokeX = 2;
						break;
					case "ULTRABALL":
						pokeX = 3;
						break;
					case "MASTERBALL":
						pokeX = 4;
						break;
				}
				g.drawImage(ImageIO.read(new File("assets/combat/pokeballactive.png")).getSubimage(pokeX * 14, 0, 14, 14), x, y, 14 * RESIZE, 14 * RESIZE, null);
			}
			if (displayHealthPlayer != 0) {
				g.drawImage(currentPlayerPokemon.getBackImage(), curPlayerX + 54, 64, SPRITE_WIDTH, SPRITE_HEIGHT, null);
			}
		} catch (IOException ex) {
		}

		try {
			g.drawImage(ImageIO.read(new File("assets/combat/dialogdisplay.png")), 0, ssY - (48 * RESIZE), 240 * RESIZE, 48 * RESIZE, null);
		} catch (IOException ex) {
		}

		//</editor-fold>
		if (curEnemyX != finalEnemyX || curPlayerX != finalPlayerX) {
			if (curEnemyX != finalEnemyX) {
				curEnemyX = curEnemyX + 20;
			}

			if (curPlayerX != finalPlayerX) {
				curPlayerX = curPlayerX - 20;
			}

			if (curEnemyX == finalEnemyX && curPlayerX == finalPlayerX) {
				ready = true;
			}
		} else {
			g.setFont(new Font("Arial", 0, 18));
			if (displayTextQueue.size() > 0) {
				g.drawString(displayTextQueue.get(0), 30, ssY - 60);
			}

			//<editor-fold defaultstate="collapse" desc="Health & Experience Display">
			if (!doneHealthEnemy) {
				int delta = Math.abs(displayHealthEnemy - currentEnemyPokemon.getCurHP());
				delta = (int) Math.ceil((float) delta / (float) 20);
				if (displayHealthEnemy < currentEnemyPokemon.getCurHP()) {
					displayHealthEnemy = displayHealthEnemy + delta;
				} else if (displayHealthEnemy > currentEnemyPokemon.getCurHP()) {
					displayHealthEnemy = displayHealthEnemy - delta;
				} else {
					doneHealthEnemy = true;
				}
			}

			if (!doneHealthPlayer) {
				int delta = Math.abs(displayHealthPlayer - currentPlayerPokemon.getCurHP());
				delta = (int) Math.ceil((float) delta / (float) 20);
				if (displayHealthPlayer < currentPlayerPokemon.getCurHP()) {
					displayHealthPlayer = displayHealthPlayer + delta;
				} else if (displayHealthPlayer > currentPlayerPokemon.getCurHP()) {
					displayHealthPlayer = displayHealthPlayer - delta;
				} else {
					doneHealthPlayer = true;
				}
			}

			if (!doneExpPlayer) {
				int delta = Math.abs(displayExpPlayer - currentPlayerPokemon.getCurEXP());
				delta = (int) Math.ceil((float) delta / 5);
				if (displayExpPlayer < currentPlayerPokemon.getCurEXP()) {
					displayExpPlayer = displayExpPlayer + delta;
				} else if (displayExpPlayer > currentPlayerPokemon.getCurEXP()) {
					displayExpPlayer = displayExpPlayer + delta;
					if (displayExpPlayer > currentPlayerPokemon.getMaxEXP()) {
						displayExpPlayer = 0;
					}
				} else {
					doneExpPlayer = true;
				}
			}

			g.setColor(Color.green);
			if ((float) (float) displayHealthEnemy / (float) currentEnemyPokemon.getStatHP() < (float) 0.5) {
				g.setColor(Color.orange);
				if ((float) (float) displayHealthEnemy / (float) currentEnemyPokemon.getStatHP() < (float) 0.25) {
					g.setColor(Color.red);
				}
			}
			g.fillRect(86, 52, (int) ((float) 100 * (float) ((float) displayHealthEnemy / (float) currentEnemyPokemon.getStatHP())), 10);

			g.setColor(Color.green);
			if ((float) (float) displayHealthPlayer / (float) currentPlayerPokemon.getStatHP() < (float) 0.5) {
				g.setColor(Color.orange);
				if ((float) (float) displayHealthPlayer / (float) currentPlayerPokemon.getStatHP() < (float) 0.25) {
					g.setColor(Color.red);
				}
			}
			g.fillRect(355, 177, (int) ((float) 100 * (float) ((float) displayHealthPlayer / (float) currentPlayerPokemon.getStatHP())), 10);

			g.setColor(Color.blue);
			g.fillRect(320, 207, (int) ((float) 140 * (float) ((float) displayExpPlayer / (float) currentPlayerPokemon.getMaxEXP())), 10);
			//</editor-fold>

			//<editor-fold defaultstate="collapsed" desc="Pokeballs Display">
			try {
				BufferedImage allBalls = ImageIO.read(new File("assets/combat/pokeballui.png"));
				int dimX = 9, dimY = 9;
				int x, y, id;

				if (!wildBattle) {
					y = 3;
					for (int i = 0; i < 6; i++) {
						x = 12 + (i * dimX * RESIZE);
						if (i < enemy.getNumPokemonTeam()) {
							if (enemy.getTeam()[i].isFainted()) {
								id = 2;
							} else {
								id = 1;
							}
						} else {
							id = 0;
						}
						g.drawImage(allBalls.getSubimage(id * 9, 0, dimX, dimY), x, y, dimX * RESIZE, dimY * RESIZE, null);
					}
				}

				y = (int) (ssY / 2) - 33;
				for (int i = 0; i < 6; i++) {
					x = ssX - (104 * RESIZE) + 75 + (i * dimX * RESIZE);
					if (i < player.getNumPokemonTeam()) {
						if (player.getTeam()[i].isFainted()) {
							id = 2;
						} else {
							id = 1;
						}
					} else {
						id = 0;
					}
					g.drawImage(allBalls.getSubimage(id * 9, 0, dimX, dimY), x, y, dimX * RESIZE, dimY * RESIZE, null);
				}
			} catch (IOException ex) {

			}
			//</editor-fold>

			try {
				g.drawImage(ImageIO.read(new File("assets/combat/enemydisplay.png")), 10, 20, 100 * RESIZE, 29 * RESIZE, null);
				g.drawImage(ImageIO.read(new File("assets/combat/playerdisplay.png")), ssX - (104 * RESIZE) - 10, (int) (ssY / 2) - 15, 104 * RESIZE, 37 * RESIZE, null);
			} catch (IOException ex) {
			}

			g.setColor(Color.black);
			g.drawString(currentEnemyPokemon.getNameNick(), 25, 45);
			g.drawString("Lv: " + currentEnemyPokemon.getLevel(), 140, 45);

			g.drawString(currentPlayerPokemon.getNameNick(), ssX - (104 * RESIZE) + 20, (int) (ssY / 2) + 10);
			g.drawString("Lv: " + currentPlayerPokemon.getLevel(), ssX - (104 * RESIZE) + 130, (int) (ssY / 2) + 10);

			g.drawString(displayHealthPlayer + "/" + currentPlayerPokemon.getStatHP(), ssX - (104 * RESIZE) + 125, (int) (ssY / 2) + 45);

			//<editor-fold defaultstate="collapsed" desc="UI Display">
			try {
				if (waitingAction) {
					if (currentMenu.compareTo("MAIN") == 0) {
						//<editor-fold defaultstate="collapsed" desc="Main UI Display">
						int uiW = (120 * RESIZE), uiH = (48 * RESIZE);

						g.drawImage(genWindow(0, uiW, uiH), ssX - uiW, ssY - uiH, null);

						g.setColor(Color.black);
						g.drawString("FIGHT", ssX - 215, ssY - 55);
						g.drawString("POKéBALL", ssX - 105, ssY - 55);
						g.drawString("POKéMON", ssX - 215, ssY - 20);
						g.drawString("RUN", ssX - 105, ssY - 20);

						g.drawImage(ImageIO.read(new File("assets/arrow.png")), ssX - 230 + (int) (Math.floor(optionsMain % 2) * 110), ssY - 70 + (int) (Math.floor(optionsMain / 2) * 30), 20, 20, null);
						//</editor-fold>
					} else if (currentMenu.compareTo("MOVES") == 0) {
						//<editor-fold defaultstate="collapsed" desc="Moves UI Display">
						int ui1W = (80 * RESIZE), uiH = (48 * RESIZE), ui2W = (160 * RESIZE);

						g.drawImage(genWindow(0, ui1W, uiH), ssX - ui1W, ssY - uiH, null);
						g.drawImage(genWindow(0, ui2W, uiH), 0, ssY - uiH, null);

						g.drawImage(ImageIO.read(new File("assets/arrow.png")), 10 + (int) (Math.floor(optionsMoves % 2) * 140), ssY - 75 + (int) (Math.floor(optionsMoves / 2) * 40), 20, 20, null);
						for (int i = 0; i < 4; i++) {
							String moveName;
							if (i < currentPlayerPokemon.getNumMoves()) {
								moveName = currentPlayerPokemon.getMoveSet()[i].getNameDisplay();
								if (moveName == null || moveName.compareTo("") == 0) {
									moveName = "--";
								}
							} else {
								moveName = "--";
							}
							g.setColor(Color.black);
							g.drawString(moveName, 30 + (int) (Math.floor(i % 2) * 140), ssY - 60 + (int) (Math.floor(i / 2) * 40));
						}
						if (optionsMoves < currentPlayerPokemon.getNumMoves()) {
							g.setColor(Color.black);
							g.drawString("PP  " + currentPlayerPokemon.getMoveSet()[optionsMoves].getPP() + " / " + currentPlayerPokemon.getMoveSet()[optionsMoves].getPPMax(), ssX - 140, ssY - 55);
							g.drawString("TYPE  " + pokemonviolet.model.PokemonType.getNameDisplay(currentPlayerPokemon.getMoveSet()[optionsMoves].getType()), ssX - 140, ssY - 20);
						}
						//</editor-fold>
					} else if (currentMenu.compareTo("BALLS") == 0) {
						//<editor-fold defaultstate="collapsed" desc="Balls UI Display">
						int ui1W = (80 * RESIZE), uiH = (48 * RESIZE), ui2W = (160 * RESIZE);

						g.drawImage(genWindow(0, ui1W, uiH), ssX - ui1W, ssY - uiH, null);
						g.drawImage(genWindow(0, ui2W, uiH), 0, ssY - uiH, null);
						
						g.drawImage(ImageIO.read(new File("assets/arrow.png")), 10 + (int) (Math.floor(optionsBalls % 2) * 140), ssY - 75 + (int) (Math.floor(optionsBalls / 2) * 40), 20, 20, null);

						String[] pokeTypes = {"POKEBALL", "GREATBALL", "ULTRABALL", "PREMIERBALL", "MASTERBALL"};
						String[] order = new String[4];
						int counter = 0;
						int numBalls = 0;
						for (int i = 0; i < pokeTypes.length; i++) {
							if (numBalls < 4) {
								boolean foundOne = false;
								String option = "--";
								while (counter < 5 && !foundOne) {
									if (player.searchItem(pokeTypes[counter]) != null) {
										option = new pokemonviolet.model.Item(pokeTypes[counter]).getNameSingular();
										order[numBalls] = pokeTypes[counter];
										foundOne = true;
									}
									counter = counter + 1;
								}

								if (option.compareTo("--") != 0) {
									g.setColor(Color.black);
									g.drawString(option, 30 + (int) (Math.floor(numBalls % 2) * 140), ssY - 60 + (int) (Math.floor(numBalls / 2) * 40));
									numBalls = numBalls + 1;
								} else {

								}
							}
						}

						if (optionsBalls < numBalls) {
							g.setColor(Color.black);
							g.drawString("x " + player.searchItem(order[optionsBalls]).getAmount(), ssX - 100, ssY - 40);
						}

						for (int i = numBalls; i < 4; i++) {
							g.setColor(Color.black);
							g.drawString("--", 30 + (int) (Math.floor(numBalls % 2) * 140), ssY - 60 + (int) (Math.floor(numBalls / 2) * 40));
							numBalls = numBalls + 1;
						}

						//</editor-fold>
					} else if (currentMenu.compareTo("POKEMON") == 0 || currentMenu.compareTo("POKEMONF") == 0) {
						//<editor-fold defaultstate="collapsed" desc="Pokemon UI Display">
						int uiW = (110 * RESIZE), uiH = (110 * RESIZE);
						g.drawImage(genWindow(5, uiW, uiH), (ssX / 2) - (uiW / 2), (ssY / 2) - (uiH / 2), uiW, uiH, null);

						for (int i = 0; i < player.getNumPokemonTeam(); i++) {
							if (player.getTeam()[i].isFainted()) {
								g.setColor(Color.orange);
							} else if (i == player.getCurrentPokemon()) {
								g.setColor(Color.blue);
							} else {
								g.setColor(Color.black);
							}
							g.drawString(player.getTeam()[i].getNameNick(), (ssX / 2) - (uiW / 2) + 30, (ssY / 2) - (uiH / 2) + 40 + (i * 30));
						}
						g.setColor(Color.black);
						for (int i = player.getNumPokemonTeam(); i < 6; i++) {
							g.drawString("--", (ssX / 2) - (uiW / 2) + 30, (ssY / 2) - (uiH / 2) + 40 + (i * 30));
						}
						g.drawImage(ImageIO.read(new File("assets/arrow.png")), (ssX / 2) - (uiW / 2) + 15, (ssY / 2) - (uiH / 2) - 5 + ((optionsPokemon + 1) * 30), 20, 20, null);

						//</editor-fold>
					}
				}
			} catch (IOException ex) {
			}
			//</editor-fold>
		}
		return display;
	}

}
