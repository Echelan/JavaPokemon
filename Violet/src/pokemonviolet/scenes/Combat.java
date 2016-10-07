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
		private static final int SPRITE_WIDTH = (int) (80 * RESIZE);
		private static final int SPRITE_HEIGHT = (int) (80 * RESIZE);
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
		private final boolean[] caught;
		private final Player player;
		private final Trainer enemy;
		private int optionsMain;
		private int optionsMoves;
		private boolean inRound;
		private int turnNum;
		private boolean canDispose;
		private final boolean wildBattle;
		private final int maxX, maxY;
		private String selfPrefix, enemyPrefix;
		private boolean forceEnemyTurn;
	//</editor-fold>

	public Combat(Player player, Trainer enemy, boolean wildBattle, Handler main) {
		super(main, "COMBAT", true);
		
		this.player = player;
		this.enemy = enemy;
		this.waitingAction = false;
		this.caught = new boolean[6];
		this.canDispose = false;
		this.inRound = false;
		this.optionsMoves = 0;
		this.optionsMain = 0;
		this.turnNum = 0;
		this.currentMenu = "MAIN";
		this.ready = false;
		this.wildBattle = wildBattle;
		this.forceEnemyTurn = false;

		selfPrefix = this.player.getName() + "'s";

		if (this.wildBattle) {
			enemyPrefix = "Wild";
		} else {
			enemyPrefix = "Foe";
		}

		maxX = Handler.SCREEN_SIZE_X;
		maxY = Handler.SCREEN_SIZE_Y;

		for (int i = 0; i < enemy.getNumPokemonTeam(); i++) {
			this.caught[i] = false;
			enemy.getTeam()[i].setAccuracy(1);
			enemy.getTeam()[i].setEvasion(1);
			player.setPokeDexValue(enemy.getTeam()[i].getId() - 1, 1);
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

	public void throwBall(int itemID) {
		inRound = true;
		waitingAction = false;
		displayTextQueue.remove(0);
		turnNum = turnNum + 1;
		int shakes = currentEnemyPokemon.doCatch(itemID);
		displayTextQueue.add(player.getName() + " throws a " + new Item(itemID).getNameSingular() + "!");

		switch (shakes) {
			case 0:
				displayTextQueue.add(currentEnemyPokemon.getNameNick() + " escaped!");
				forceEnemyTurn = true;
				break;
			case 1:
				displayTextQueue.add("The " + new Item(itemID).getNameSingular() + " broke!");
				forceEnemyTurn = true;
				break;
			case 2:
				displayTextQueue.add("Couldn't catch " + currentEnemyPokemon.getNameNick() + "!");
				forceEnemyTurn = true;
				break;
			case 3:
				displayTextQueue.add("Oh no! It was so close too!");
				forceEnemyTurn = true;
				break;
			case 4:
				displayTextQueue.add("Gotcha! " + currentEnemyPokemon.getNameNick() + " was caught!");
				this.caught[enemy.getCurrentPokemon()] = true;
				player.addPokemon(currentEnemyPokemon);
				break;
		}
	}

	public void usedItem() {
		inRound = true;
		waitingAction = false;
		displayTextQueue.remove(0);
		turnNum = turnNum + 1;
		displayTextQueue.add("");
		forceEnemyTurn = true;
		doneHealthPlayer = false;
	}
	
	private void nextTurn() {
		if (forceEnemyTurn) {
			forceEnemyTurn = false;
			Random rnd = new Random();
			int randomMove = (rnd.nextInt(currentEnemyPokemon.getNumMoves()));
			
			doTurn(currentEnemyPokemon, currentPlayerPokemon, randomMove, enemyPrefix, selfPrefix);
			turnNum = 2;
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
						main.gameState.add(new Bag(main, this));
						break;
					case 2:
						this.currentMenu = "POKEMON";
						main.gameState.add(new Team(main, this, true, player.getCurrentPokemon()));
						break;
					case 3:
						this.dispose();
						break;
					default:

						break;
				}
			} else if (currentMenu.compareTo("MOVES") == 0) {
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
		}

		private void subActionRound() {
			if (doneHealthEnemy && doneHealthPlayer) {
				displayTextQueue.remove(0);
				if (caught[enemy.getCurrentPokemon()]) {
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
					if (!currentEnemyPokemon.isFainted() && !caught[enemy.getCurrentPokemon()] && !currentPlayerPokemon.isFainted()) {
						waitingAction = true;
						displayTextQueue.add("What will " + currentPlayerPokemon.getNameNick() + " do?");
					} else if (currentEnemyPokemon.isFainted() && enemy.getCurrentPokemon() + 1 < enemy.getNumPokemonTeam()) {
						enemyFaint();
						nextEnemy();
					} else if (caught[enemy.getCurrentPokemon()] && enemy.getCurrentPokemon() + 1 < enemy.getNumPokemonTeam()) {
						nextEnemy();
					} else if (currentPlayerPokemon.isFainted() && player.getFirstAvailablePokemon() != -1) {
						displayTextQueue.add("What Pokemon will you send out next?");
						currentMenu = "POKEMONF";
						main.gameState.add(new Team(main, this, false, player.getCurrentPokemon()));
						waitingAction = true;
					} else if (!canDispose) {
						canDispose = true;
						if (currentPlayerPokemon.isFainted()) {
							displayTextQueue.add(player.getName() + " blacked out!");
							displayTextQueue.add(player.getName() + " was left with $"+player.blackOut()+"!");
						} else {
							if (!this.caught[enemy.getCurrentPokemon()]) {
								enemyFaint();

								displayTextQueue.add(player.getName() + " earned $" + enemy.getReward() + "!");
								player.setFunds(player.getFunds()+enemy.getReward());
							} else {
								displayTextQueue.add("");
							}
						}
					} else {
						this.dispose();
					}
				}
			}
		}
	//</editor-fold>

	private void enemyFaint() {

		currentPlayerPokemon.addEVAttack(currentEnemyPokemon.getYieldAttack());
		currentPlayerPokemon.addEVDefense(currentEnemyPokemon.getYieldDefense());
		currentPlayerPokemon.addEVHP(currentEnemyPokemon.getYieldHP());
		currentPlayerPokemon.addEVSpAtk(currentEnemyPokemon.getYieldSpAtk());
		currentPlayerPokemon.addEVSpDef(currentEnemyPokemon.getYieldSpDef());
		currentPlayerPokemon.addEVSpeed(currentEnemyPokemon.getYieldSpeed());
		
		displayTextQueue.add(currentPlayerPokemon.getNameNick() + " gained " + currentEnemyPokemon.getExpGain() + " EXP!");

		String result = currentPlayerPokemon.setCurEXP(currentPlayerPokemon.getCurEXP() + currentEnemyPokemon.getExpGain());

		System.out.println(result);

		doneExpPlayer = false;
		if (result.compareTo("") != 0) {
			displayTextQueue.add(currentPlayerPokemon.getNameNick() + " is now level " + currentPlayerPokemon.getLevel() + "!");
			if (result.compareTo("Level up!") != 0) {
				if (result.split(":")[0].compareTo("add") == 0) {
					displayTextQueue.add(currentPlayerPokemon.getNameNick() + " learned " + result.split(":")[1] + "!");
				} else if (result.split(":")[0].compareTo("wants") == 0) {
					main.gameState.add(new LearnMove(main, currentPlayerPokemon, new pokemonviolet.model.PokemonMove(result.split(":")[1]), "COMBAT"));
				}
			}
		}
	}
	
	private void nextEnemy() {
		enemy.setCurrentPokemon(enemy.getCurrentPokemon() + 1);
		currentEnemyPokemon = enemy.getTeam()[enemy.getCurrentPokemon()];
		displayHealthEnemy = currentEnemyPokemon.getCurHP();

		if (this.wildBattle) {
			displayTextQueue.add("A wild " + currentEnemyPokemon.getNameNick() + " rushed at you!");
		} else {
			displayTextQueue.add("Foe " + enemy.getName() + " sent out " + currentEnemyPokemon.getNameNick() + "!");
		}
	}
			
	public void receiveNewPokemon(int selection) {
		displayTextQueue.remove(0);
		if (currentMenu.compareTo("POKEMONF") == 0) {
			currentMenu = "MAIN";
		} else {
			waitingAction = false;
			inRound = true;
			forceEnemyTurn = true;
		}
		if (currentMenu.compareTo("POKEMON") == 0) {
			displayTextQueue.add("That's enough, " + currentPlayerPokemon.getNameNick() + ", come back!");
		} else {
			//	displayTextQueue.add("You did good, "+currentPlayerPokemon.getNameNick()+".");
		}
		player.setCurrentPokemon(selection);
		currentPlayerPokemon = player.getTeam()[player.getCurrentPokemon()];
		displayTextQueue.add("Go! " + currentPlayerPokemon.getNameNick() + "!");
		displayHealthPlayer = currentPlayerPokemon.getCurHP();
		displayExpPlayer = currentPlayerPokemon.getCurEXP();
	}
	
	public void cancelExtraAction() {
		currentMenu = "MAIN";
	}

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
			}
		}
	}

	@Override
	public void dispose() {
//		main.player.setInCombat(false);
		main.gameState.remove(main.gameState.size() - 1);
		
//		if (currentPlayerPokemon.isWantingToLearn()) {
//			main.gameState.add(new LearnMove(main,currentPlayerPokemon));
//		}
		
//		if (currentPlayerPokemon.isWaitingToEvolve()) {
//			main.gameState.add(new Evolution);
//		}

		if (currentPlayerPokemon.isFainted()) {
			main.gameState.add(new Center(main));
		}
	}

	@Override
	protected void start() {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
	
	@Override
	public BufferedImage getDisplay() throws IOException {
		int ssX = pokemonviolet.model.Handler.SCREEN_SIZE_X, ssY = pokemonviolet.model.Handler.SCREEN_SIZE_Y;
		BufferedImage display = new BufferedImage(ssX, ssY, BufferedImage.TYPE_INT_ARGB);
		Graphics g = display.getGraphics();

		//<editor-fold defaultstate="collapsed" desc="Background">
		g.drawImage(ImageIO.read(new File("assets/combat/background.png")), 0, 0, ssX, ssY, null);

		g.drawImage(ImageIO.read(new File("assets/combat/grassunderground1.png")), curEnemyX, 85, (int) (128 * RESIZE), (int) (36 * RESIZE), null);
		g.drawImage(ImageIO.read(new File("assets/combat/grassunderground2.png")), curPlayerX, 180, (int) (128 * RESIZE), (int) (36 * RESIZE), null);
		//</editor-fold>

		boolean curCaught = caught[enemy.getCurrentPokemon()];

		//<editor-fold defaultstate="collapsed" desc="Pokemon Sprites Display">
		if (displayHealthEnemy != 0 && !curCaught) {
			g.drawImage(currentEnemyPokemon.getFrontImage(), curEnemyX + 50, 0, SPRITE_WIDTH, SPRITE_HEIGHT, null);
		} else if (curCaught) {
			int x = (curEnemyX + 50 + (SPRITE_WIDTH / 2) + 20 - (int) (14 * RESIZE)), y = (18 + SPRITE_HEIGHT - 50 - (int) (14 * RESIZE));
			int pokeX = 0;

			if (currentEnemyPokemon.getBallType() == null) {
				currentEnemyPokemon.setBallType("POKEBALL");
			}

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
			g.drawImage(ImageIO.read(new File("assets/combat/pokeballactive.png")).getSubimage(pokeX * 14, 0, 14, 14), x, y, (int) (14 * RESIZE), (int) (14 * RESIZE), null);
		}
		if (displayHealthPlayer != 0) {
			g.drawImage(currentPlayerPokemon.getBackImage(), curPlayerX + 54, 64, SPRITE_WIDTH, SPRITE_HEIGHT, null);
		}
		
		//</editor-fold>
		
		g.drawImage(ImageIO.read(new File("assets/combat/dialogdisplay.png")), 0, ssY - (int) (48 * RESIZE), (int) (240 * RESIZE), (int) (48 * RESIZE), null);
		
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
			g.setFont(new Font("Arial", Font.PLAIN, 18));
			if (!displayTextQueue.isEmpty()) {
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
				if (delta < 20) {
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
				} else {
					displayExpPlayer = currentPlayerPokemon.getCurEXP();
				}
			}

			g.setColor(Color.green);
			if ((float) (float) displayHealthEnemy / (float) currentEnemyPokemon.getStatHP() < 0.5f) {
				g.setColor(Color.orange);
				if ((float) (float) displayHealthEnemy / (float) currentEnemyPokemon.getStatHP() < 0.25f) {
					g.setColor(Color.red);
				}
			}
			g.fillRect(86, 52, (int) ((float) 100 * (float) ((float) displayHealthEnemy / (float) currentEnemyPokemon.getStatHP())), 10);

			g.setColor(Color.green);
			if ((float) displayHealthPlayer / (float) currentPlayerPokemon.getStatHP() < 0.5f) {
				g.setColor(Color.orange);
				if ((float) displayHealthPlayer / (float) currentPlayerPokemon.getStatHP() < 0.25f) {
					g.setColor(Color.red);
				}
			}
			g.fillRect(355, 177, (int) ((float) 100 * (float) ((float) displayHealthPlayer / (float) currentPlayerPokemon.getStatHP())), 10);

			g.setColor(Color.blue);
			g.fillRect(320, 207, (int) ((float) 140 * (float) ((float) displayExpPlayer / (float) currentPlayerPokemon.getMaxEXP())), 10);
			//</editor-fold>

			//<editor-fold defaultstate="collapsed" desc="Pokeballs Display">
			BufferedImage allBalls = ImageIO.read(new File("assets/combat/pokeballui.png"));
			int dimX = 9, dimY = 9;
			int x, y, id;

			if (!wildBattle) {
				y = 3;
				for (int i = 0; i < 6; i++) {
					x = 12 + (int) (i * dimX * RESIZE);
					if (i < enemy.getNumPokemonTeam()) {
						if (enemy.getTeam()[i].isFainted()) {
							id = 2;
						} else {
							id = 1;
						}
					} else {
						id = 0;
					}
					g.drawImage(allBalls.getSubimage(id * 9, 0, dimX, dimY), x, y, (int) (dimX * RESIZE), (int) (dimY * RESIZE), null);
				}
			}

			y = (int) (ssY / 2) - 33;
			for (int i = 0; i < 6; i++) {
				x = ssX - (int) (104 * RESIZE) + 75 + (int) (i * dimX * RESIZE);
				if (i < player.getNumPokemonTeam()) {
					if (player.getTeam()[i].isFainted()) {
						id = 2;
					} else {
						id = 1;
					}
				} else {
					id = 0;
				}
				g.drawImage(allBalls.getSubimage(id * 9, 0, dimX, dimY), x, y, (int) (dimX * RESIZE), (int) (dimY * RESIZE), null);
			}
			//</editor-fold>

			g.drawImage(ImageIO.read(new File("assets/combat/enemydisplay.png")), 10, 20, (int) (100 * RESIZE), (int) (29 * RESIZE), null);
			g.drawImage(ImageIO.read(new File("assets/combat/playerdisplay.png")), ssX - (int) (104 * RESIZE) - 10, (int) (ssY / 2) - 15, (int) (104 * RESIZE), (int) (37 * RESIZE), null);
			
			g.setColor(Color.black);
			g.drawString(currentEnemyPokemon.getNameNick(), 25, 45);
			g.drawString("Lv: " + currentEnemyPokemon.getLevel(), 140, 45);

			g.drawString(currentPlayerPokemon.getNameNick(), ssX - (int) (104 * RESIZE) + 20, (int) (ssY / 2) + 10);
			g.drawString("Lv: " + currentPlayerPokemon.getLevel(), ssX - (int) (104 * RESIZE) + 130, (int) (ssY / 2) + 10);

			g.drawString(displayHealthPlayer + "/" + currentPlayerPokemon.getStatHP(), ssX - (int) (104 * RESIZE) + 125, (int) (ssY / 2) + 45);

			//<editor-fold defaultstate="collapsed" desc="UI Display">
			if (waitingAction) {
				if (currentMenu.compareTo("MAIN") == 0) {
					//<editor-fold defaultstate="collapsed" desc="Main UI Display">
					int uiW = (int) (120 * RESIZE), uiH = (int) (48 * RESIZE);

					g.drawImage(genWindow(0, uiW, uiH), ssX - uiW, ssY - uiH, null);

					g.setColor(Color.black);
					g.drawString("FIGHT", ssX - 215, ssY - 55);
					g.drawString("BAG", ssX - 105, ssY - 55);
					g.drawString("POKéMON", ssX - 215, ssY - 20);
					g.drawString("RUN", ssX - 105, ssY - 20);

					g.drawImage(ImageIO.read(new File("assets/arrow.png")), ssX - 230 + (int) (Math.floor(optionsMain % 2) * 110), ssY - 70 + (int) (Math.floor(optionsMain / 2) * 30), 20, 20, null);
					//</editor-fold>
				} else if (currentMenu.compareTo("MOVES") == 0) {
					//<editor-fold defaultstate="collapsed" desc="Moves UI Display">
					int ui1W = (int) (80 * RESIZE), uiH = (int) (48 * RESIZE), ui2W = (int) (160 * RESIZE);

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
				}
			}
			//</editor-fold>
		}
		return display;
	}

}
