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
import javax.imageio.ImageIO;
import pokemonviolet.control.KeyHandler;
import pokemonviolet.model.Handler;

/**
 *
 * @author Andres
 */
public class UseDialog extends Scene{

	private int itemID;
	private int choice;
	private boolean inCombat;
	private Combat combat;
	private String[] choices = {"Use", "Cancel"};
	
	public UseDialog(Handler main, int id) {
		super(main, "BUY", false);
		
		this.choice = 0;
		this.itemID = id;
		this.inCombat = false;
		this.combat = null;
	}
	
	public UseDialog(Handler main, int id, Combat cmb) {
		super(main, "BUY", false);
		
		this.choice = 0;
		this.itemID = id;
		this.inCombat = true;
		this.combat = cmb;
	}

	@Override
	public void receiveKeyAction(int action, int state) {
		if (state == KeyHandler.STATE_RELEASE) {
			if (action == KeyHandler.ACTION_A) {
				accept();
			} else if (action == KeyHandler.ACTION_B) {
				cancel();
			} else if (action == KeyHandler.ACTION_START) {
			}
		} else if (state == KeyHandler.STATE_PRESS) {
			if (action == KeyHandler.ACTION_UP || action == KeyHandler.ACTION_DOWN || action == KeyHandler.ACTION_LEFT || action == KeyHandler.ACTION_RIGHT) {
				move(action);
			}
		}
	}

	@Override
	protected void accept() {
		if (choice == 0) {
			if (inCombat) {
				if (new pokemonviolet.model.Item(itemID).getUseInBattle() == 1) {
					main.gameState.add(new Team(main, combat, itemID));
				} else if (new pokemonviolet.model.Item(itemID).getUseInBattle() == 2) {
					if (new pokemonviolet.model.Item(itemID).getPokeRate() != 0) {
						combat.throwBall(itemID);
						main.getPlayer().subItem(itemID);
						this.dispose();
						if (inCombat) {
							main.clearStates("COMBAT");
						}
					}
				}
			} else if (!inCombat) {
				if (new pokemonviolet.model.Item(itemID).getUseOutBattle() == 1) {
					main.gameState.add(new Team(main, combat, itemID));
				} else if (new pokemonviolet.model.Item(itemID).getUseOutBattle() == 2) {
					if (itemID < 4) {
						main.getPlayer().setSpawnSteps(main.getPlayer().getSpawnSteps() + (int) new pokemonviolet.model.Item(itemID).getValue());
					} else {
						for (int i = 0; i < main.getPlayer().getNumPokemonTeam(); i++) {
							main.getPlayer().getTeam()[i].revive(1.0);
						}
					}
					main.getPlayer().subItem(itemID);
					this.dispose();
				} else if (new pokemonviolet.model.Item(itemID).getUseOutBattle() == 3) {
					main.gameState.add(new Team(main, combat, itemID));
				} else if (new pokemonviolet.model.Item(itemID).getUseOutBattle() == 4) {
					main.gameState.add(new Team(main, combat, itemID));
				}
			}
		}
	}

	@Override
	protected void cancel() {
		this.dispose();
	}

	@Override
	protected void start() {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	protected void move(int dir) {
		if (dir == KeyHandler.ACTION_UP) {
			choice = choice - 1;
		} else if (dir == KeyHandler.ACTION_DOWN) {
			choice = choice + 1;
		}
		
		if (choice < 0) {
			choice = 1;
		} else if (choice > 1) {
			choice = 0;
		}
	}

	@Override
	public BufferedImage getDisplay() throws IOException {
		BufferedImage display = new BufferedImage(resizedValue(ssX), resizedValue(ssY), BufferedImage.TYPE_INT_ARGB);
		Graphics g = display.getGraphics();

		int x = ssX - 80;
		g.drawImage(genWindow(0, 60, 45), resizedValue(x), resizedValue(81), null);
		
		g.setColor(Color.black);
		g.setFont(new Font("Arial", Font.BOLD, resizedValue(10)));
		
		for (int i = 0; i < choices.length; i++) {
			g.drawString(choices[i], resizedValue(x + 15), resizedValue(101.5 + (i * 15)));
		}
		
		g.drawImage(ImageIO.read(new File("assets/arrow.png")), resizedValue(x + 5), resizedValue(101.5 + (choice * 15) - 9), resizedValue(10), resizedValue(10), null);
		
		return display;
	}
	
}
