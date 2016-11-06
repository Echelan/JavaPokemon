
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
import pokemonviolet.control.KeyHandler;
import javax.imageio.ImageIO;
import pokemonviolet.model.Handler;
import pokemonviolet.model.Item;

/**
 *
 * @author Andres
 */
public class Bag extends Scene {
	private ArrayList<Item>[] inventory;
	private static String[] namePockets = {"Items","Medicine","Pokéballs","TMs & HMs","Battle Items","Key Items"};
	private int selection;
	private int category;
	private int startIndexX;
	private int maxItemsPage;
	private boolean inCombat;
	private Combat combat;

	public Bag(Handler main, Combat cmb) {
		super(main, "BAG", false);
		
		this.inventory = main.getPlayer().getBag();
		
		this.combat = cmb;
		this.inCombat = true;
		if (this.combat == null) {
			this.inCombat = false;
		}
		
		this.selection = 0;
		this.category = 0;
		this.startIndexX = 0;
		this.maxItemsPage = 11;
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
		if (selection == inventory[category].size()) {
			if (inCombat) {
				combat.cancelExtraAction();
			}
			this.dispose();
		} else {
			if (inCombat && inventory[category].get(selection).getUseInBattle() != 0) {
				main.gameState.add(new UseDialog(main,inventory[category].get(selection).getId(), this.combat));
			} else if (!inCombat && inventory[category].get(selection).getUseOutBattle() != 0) {
				main.gameState.add(new UseDialog(main,inventory[category].get(selection).getId()));
			}
		}
	}

	@Override
	protected void cancel() {
		if (inCombat) {
			combat.cancelExtraAction();
		}
		this.dispose();
	}

	@Override
	protected void move(int dir) {
		if (dir == KeyHandler.ACTION_UP) {
			if (selection == startIndexX) {
				startIndexX = startIndexX - 1;
			}
			selection = selection - 1;
		} else if (dir == KeyHandler.ACTION_DOWN) {
			if (selection-(maxItemsPage-1) == startIndexX) {
				startIndexX = startIndexX + 1;
			}
			selection = selection + 1;
		} else if (dir == KeyHandler.ACTION_LEFT) {
			category = category - 1;
			startIndexX = 0;
			selection = 0;
		} else if (dir == KeyHandler.ACTION_RIGHT) {
			category = category + 1;
			startIndexX = 0;
			selection = 0;
		}
		
		if (category < 0) {
			category = inventory.length - 1;
		} else if (category >= inventory.length) {
			category = 0;
		}
		
		if (selection > inventory[category].size()) {
			if (startIndexX > inventory[category].size() - maxItemsPage && inventory[category].size() > maxItemsPage) {
				startIndexX = inventory[category].size() - maxItemsPage;
			}
			selection = inventory[category].size();
		} else if (startIndexX < 0) {
			startIndexX = 0;
			selection = 0;
		}
	}

	@Override
	protected void start() {
	}

	@Override
	public BufferedImage getDisplay() throws IOException {
		BufferedImage tempStitched = new BufferedImage(resizedValue(ssX), resizedValue(ssY), BufferedImage.TYPE_INT_ARGB);
		Graphics g = tempStitched.getGraphics();

		int windowWidth = 120, windowHeight = (int) (ssY * 0.8);
		int theme = 3;
		g.drawImage(genWindow(theme, windowWidth, ssY - windowHeight - 1.5), resizedValue(ssX - windowWidth - 0.5), resizedValue(0.5), null);
		g.drawImage(genWindow(theme, windowWidth, windowHeight), resizedValue(ssX - windowWidth - 0.5), resizedValue(ssY - windowHeight - 0.5), null);
		g.drawImage(genWindow(theme, 70, 30), resizedValue(0.5), resizedValue(0.5), null);
		g.drawImage(genWindow(theme, 40, 40), resizedValue(12), resizedValue(33), null);
		g.drawImage(genWindow(theme, ssX - windowWidth - 1.5, 60), resizedValue(0.5), resizedValue(ssY - 60 - 0.5), null);
		g.drawImage(genWindow(theme, ssX - windowWidth - 1.5, 25), resizedValue(0.5), resizedValue(ssY - 60 - 1 - 25), null);
		
		g.setColor(Color.black);
		g.setFont(new Font("Arial", Font.BOLD, resizedValue(7.5)));
		g.drawString(main.getPlayer().getName()+"'s Bag", resizedValue(7.5), resizedValue(18));
		
		g.setFont(new Font("Arial", Font.BOLD, resizedValue(10)));
		g.drawString(namePockets[category], resizedValue(ssX - windowWidth + 10), resizedValue(20));
		
		if (selection != inventory[category].size()) {
			g.setFont(new Font("Arial", Font.PLAIN, resizedValue(7.5)));
			
			for (int i = 0; i < genMultilineText(inventory[category].get(selection).getDescription(), 30).length; i++) {
				g.drawString(genMultilineText(inventory[category].get(selection).getDescription(), 30)[i], resizedValue(7.5), resizedValue(ssY - 71 + 22.5 + (i * 10)));
			}
			
			g.drawImage(inventory[category].get(selection).getImage(), resizedValue(17), resizedValue(38), null);
			
			g.setFont(new Font("Arial", Font.BOLD, resizedValue(10)));
			g.drawString(inventory[category].get(selection).getNameSingular(), resizedValue(7.5), resizedValue(ssY - 70));
		}
		
		int maxIndex = startIndexX + maxItemsPage;
		if (maxIndex > inventory[category].size() + 1) {
			maxIndex = inventory[category].size() + 1;
		}
		
		g.setFont(new Font("Arial", Font.BOLD, resizedValue(10)));
		for (int i = startIndexX; i < maxIndex; i++) {
			if (i == inventory[category].size()) {
				g.drawString("CANCEL", resizedValue(ssX - windowWidth + 10), resizedValue(((ssY / 2) - (windowHeight / 2) + 32.5) + ((i-startIndexX) * 10)));
			} else {
				g.drawString(inventory[category].get(i).getNameSingular(), resizedValue(ssX - windowWidth + 10), resizedValue(((ssY / 2) - (windowHeight / 2) + 32.5) + ((i - startIndexX) * 10)));
				g.drawString("x"+inventory[category].get(i).getAmount(), resizedValue(ssX - (windowWidth / 2) + 25), resizedValue(((ssY / 2) - (windowHeight / 2) + 32.5) + ((i - startIndexX) * 10)));
			}
		}
		int dispSelection = selection - startIndexX;
		g.drawImage(ImageIO.read(new File("assets/arrow.png")), resizedValue(ssX - windowWidth + 2.5), resizedValue(((ssY / 2) - (windowHeight / 2) + 23.5) + (dispSelection * 10)), resizedValue(10), resizedValue(10), null);

		return tempStitched;
	}
}
