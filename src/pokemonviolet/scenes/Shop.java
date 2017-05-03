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

/**
 *
 * @author Andres
 */
public class Shop extends Scene {
	private int[] fullInventory = {1, 2, 3, 5, 6, 7, 8, 9, 10, 19, 20, 21, 22, 23, 24, 32, 33, 34, 35, 36, 37, 38, 39, 40, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 57, 58, 59, 60};
	private ArrayList<Integer>[] perPocketInventory;
	private String[] categoryNames = {"Items", "Medicine", "PokeBalls"};
	private int selection;
	private int category;
	private int startIndexX;
	private int maxItemsPage;

	public Shop(Handler main) {
		super(main, "SHOP", false);
		
		perPocketInventory = new ArrayList[3];
		for (int i = 0; i < perPocketInventory.length; i++) {
			perPocketInventory[i] = new ArrayList<Integer>();
		}
		
		/*
		1 - Items
		2 - Medicine
		3 - Poké Balls
		*/
		int pocket;
		for (int i = 0; i < fullInventory.length; i++) {
			switch (new pokemonviolet.model.Item(fullInventory[i]).getPocket()) {
				case 1:
					pocket = 0;
				break;
				case 2:
					pocket = 1;
				break;
				case 3:
					pocket = 2;
				break;
				default:
					pocket = -1;
				break;
			}
			if (pocket != -1)  {
				perPocketInventory[pocket].add( fullInventory[i] );
			}
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
		if (selection == perPocketInventory[category].size()) {
			this.dispose();
		} else {
			main.gameState.add(new BuyDialog(main,perPocketInventory[category].get(selection)));
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
			if (selection == startIndexX) {
				startIndexX = startIndexX - 1;
			}
			selection = selection - 1;
		} else if (dir == KeyHandler.ACTION_DOWN) {
			if (selection - (maxItemsPage - 1) == startIndexX) {
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
			category = perPocketInventory.length - 1;
		} else if (category >= perPocketInventory.length) {
			category = 0;
		}
		
		if (selection > perPocketInventory[category].size()) {
			if (startIndexX > perPocketInventory[category].size() - maxItemsPage && perPocketInventory[category].size() > maxItemsPage) {
				startIndexX = perPocketInventory[category].size() - maxItemsPage;
			}
			selection = perPocketInventory[category].size();
		} else if (startIndexX < 0) {
			startIndexX = 0;
			selection = 0;
		}
	}

	@Override
	public BufferedImage getDisplay() throws IOException {
		BufferedImage display = new BufferedImage(resizedValue(ssX), resizedValue(ssY), BufferedImage.TYPE_INT_ARGB);
		Graphics g = display.getGraphics();

		int windowWidth = 120, windowHeight = (int) (ssY * 0.8);
		int theme = 0;
		g.drawImage(genWindow(theme, windowWidth, ssY - windowHeight - 1.5), resizedValue(ssX - windowWidth - 0.5), resizedValue(0.5), null);
		g.drawImage(genWindow(theme, windowWidth, windowHeight), resizedValue(ssX - windowWidth - 0.5), resizedValue(ssY-windowHeight - 0.5), null);
		g.drawImage(genWindow(theme, 65, 30), resizedValue(0.5), resizedValue(0.5), null);
		g.drawImage(genWindow(theme, 40, 40), resizedValue(12), resizedValue(33), null);
		g.drawImage(genWindow(theme, ssX - windowWidth - 1.5, 60), resizedValue(0.5), resizedValue(ssY - 60.5), null);
		g.drawImage(genWindow(theme, ssX - windowWidth - 1.5, 25), resizedValue(0.5), resizedValue(ssY - 61 - 25), null);
		
		g.setColor(Color.black);
		g.setFont(new Font("Arial", Font.BOLD, resizedValue(7.5)));
		g.drawString("$  "+main.getPlayer().getFunds(), resizedValue(7.5), resizedValue(18));
		
		g.setFont(new Font("Arial", Font.BOLD, resizedValue(10)));
		g.drawString(categoryNames[category], resizedValue(ssX - windowWidth + 10), resizedValue(20));
		
		if (selection != perPocketInventory[category].size()) {
			g.setFont(new Font("Arial", Font.PLAIN, resizedValue(7.5)));
			
			for (int i = 0; i < genMultilineText(new pokemonviolet.model.Item(perPocketInventory[category].get(selection)).getDescription(), 30).length; i++) {
				g.drawString(genMultilineText(new pokemonviolet.model.Item(perPocketInventory[category].get(selection)).getDescription(), 30)[i], resizedValue(7.5), resizedValue(ssY - 71 + 22.5 + (i * 10)));
			}
			
			g.drawImage(new pokemonviolet.model.Item(perPocketInventory[category].get(selection)).getImage(), resizedValue(17), resizedValue(38), null);
			
			g.setFont(new Font("Arial", Font.BOLD, resizedValue(10)));
			g.drawString(new pokemonviolet.model.Item(perPocketInventory[category].get(selection)).getNameSingular(), resizedValue(7.5), resizedValue(ssY - 70));
		}
		
		int maxIndex = startIndexX + maxItemsPage;
		if (maxIndex > perPocketInventory[category].size() + 1) {
			maxIndex = perPocketInventory[category].size() + 1;
		}
		
		g.setFont(new Font("Arial", Font.BOLD, resizedValue(10)));
		for (int i = startIndexX; i < maxIndex; i++) {
			if (i == perPocketInventory[category].size()) {
				g.drawString("CANCEL", resizedValue(ssX - windowWidth + 10), resizedValue((ssY / 2) - (windowHeight / 2) + 32.5 + ((i - startIndexX) * 10)));
			} else {
				g.drawString(new pokemonviolet.model.Item(perPocketInventory[category].get(i)).getNameSingular(), resizedValue(ssX - windowWidth + 10), resizedValue(((ssY / 2) - (windowHeight / 2) + 32.5) + ((i-startIndexX) * 10)));
				g.drawString("$"+new pokemonviolet.model.Item(perPocketInventory[category].get(i)).getPrice(), resizedValue(ssX - (windowWidth / 2) + 25), resizedValue(((ssY / 2) - (windowHeight / 2) + 32.5) + ((i-startIndexX) * 10)));
			}
		}

		
		int dispSelection = selection - startIndexX;
		g.drawImage(ImageIO.read(new File("assets/arrow.png")), resizedValue(ssX - windowWidth + 2.5), resizedValue(((ssY / 2) - (windowHeight / 2) + 23.5) + (dispSelection * 10)), resizedValue(10), resizedValue(10), null);

		return display;
	}

}
