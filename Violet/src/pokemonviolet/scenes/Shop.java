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
	public void receiveKeyAction(String action, String state) {
		if (state.compareTo("RELEASE") == 0) {
			if (action.compareTo("A") == 0) {
				accept();
			} else if (action.compareTo("B") == 0) {
				cancel();
			} else if (action.compareTo("START") == 0) {
			}
		} else if (state.compareTo("PRESS") == 0) {
			if (action.compareTo("UP") == 0 || action.compareTo("DOWN") == 0 || action.compareTo("LEFT") == 0 || action.compareTo("RIGHT") == 0) {
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
	protected void dispose() {
		main.gameState.remove(main.gameState.size() - 1);
	}

	@Override
	protected void move(String dir) {
		if (dir.compareTo("UP") == 0) {
			if (selection == startIndexX) {
				startIndexX = startIndexX - 1;
			}
			selection = selection - 1;
		} else if (dir.compareTo("DOWN") == 0) {
			if (selection-(maxItemsPage-1) == startIndexX) {
				startIndexX = startIndexX + 1;
			}
			selection = selection + 1;
		} else if (dir.compareTo("LEFT") == 0) {
			category = category - 1;
			startIndexX = 0;
			selection = 0;
		} else if (dir.compareTo("RIGHT") == 0) {
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
	public BufferedImage getDisplay() {
		BufferedImage tempStitched = new BufferedImage(ssX, ssY, BufferedImage.TYPE_INT_ARGB);
		Graphics g = tempStitched.getGraphics();

		int windowWidth = (int) (120 * RESIZE), windowHeight = (int) (ssY * 0.8);
		try {
			int theme = 0;
			g.drawImage(genWindow(theme, windowWidth, ssY - windowHeight - 3), ssX - windowWidth - 1, 1, null);
			g.drawImage(genWindow(theme, windowWidth, windowHeight), ssX - windowWidth - 1, ssY-windowHeight - 1, null);
			g.drawImage(genWindow(theme, 130, 60), 1, 1, null);
			g.drawImage(genWindow(theme, 80, 80), 24, 66, null);
			g.drawImage(genWindow(theme, ssX - windowWidth - 3, 120), 1, ssY - 121, null);
			g.drawImage(genWindow(theme, ssX - windowWidth - 3, 50), 1, ssY - 122 - 50, null);
		} catch (IOException ex) {
		}
		
		g.setColor(Color.black);
		g.setFont(new Font("Arial", Font.BOLD, 15));
		g.drawString("$  "+main.player.getFunds(), 15, 36);
		
		g.setFont(new Font("Arial", Font.BOLD, 20));
		g.drawString(categoryNames[category], ssX - windowWidth + 20, 40);
		
		if (selection != perPocketInventory[category].size()) {
			g.setFont(new Font("Arial", Font.PLAIN, 15));
			int charsInLine = 30;
			String fullD = new pokemonviolet.model.Item(perPocketInventory[category].get(selection)).getDescription();
			for (int i = 0; i < (fullD.length() / charsInLine) + 1; i++) {
				String prefix = "", suffix = "";

				int thisLineFirstChar = i * charsInLine;
				int thisLinePrevChar = thisLineFirstChar - 1;
				int thisLineLastChar = ((i + 1) * charsInLine) - 2;
				int thisLineNextChar = thisLineLastChar + 1;

				if (thisLineFirstChar != 0){
					if (fullD.charAt(thisLinePrevChar) != ' ') {
						prefix = "" + fullD.charAt(thisLinePrevChar);
					}
				}

				if (thisLineNextChar < fullD.length()) {
					if (fullD.charAt(thisLineNextChar) != ' ' && fullD.charAt(thisLineLastChar) != ' ') {
						suffix = "-";
					}
				} else {
					thisLineLastChar = fullD.length() - 1;
				}

				g.drawString(prefix + fullD.substring(thisLineFirstChar, thisLineLastChar+1) + suffix, 15, ssY - 142 + 45 + (i * 20));
			}		

			try{
				g.drawImage(new pokemonviolet.model.Item(perPocketInventory[category].get(selection)).getImage(), 34, 76, null);
			} catch ( IOException ex) {

			}
			
			g.setFont(new Font("Arial", Font.BOLD, 20));
			g.drawString(new pokemonviolet.model.Item(perPocketInventory[category].get(selection)).getNameSingular(), 15, ssY - 140);
		}
		
		int maxIndex = startIndexX + maxItemsPage;
		if (maxIndex > perPocketInventory[category].size() + 1) {
			maxIndex = perPocketInventory[category].size() + 1;
		}
		
		g.setFont(new Font("Arial", Font.BOLD, 20));
		for (int i = startIndexX; i < maxIndex; i++) {
			if (i == perPocketInventory[category].size()) {
				g.drawString("CANCEL", ssX - windowWidth + 20, ((ssY / 2) - (windowHeight / 2) + 65) + ((i-startIndexX) * 20));
			} else {
				g.drawString(new pokemonviolet.model.Item(perPocketInventory[category].get(i)).getNameSingular(), ssX - windowWidth + 20, ((ssY / 2) - (windowHeight / 2) + 65) + ((i-startIndexX) * 20));
				g.drawString("$"+new pokemonviolet.model.Item(perPocketInventory[category].get(i)).getPrice(), ssX - (windowWidth / 2) + 50, ((ssY / 2) - (windowHeight / 2) + 65) + ((i-startIndexX) * 20));
			}
		}

		try {
			int dispSelection = selection - startIndexX;
			g.drawImage(ImageIO.read(new File("assets/arrow.png")), ssX - windowWidth + 5, ((ssY / 2) - (windowHeight / 2) + 47) + (dispSelection * 20), 20, 20, null);
		} catch (IOException ex) {
		}

		return tempStitched;
	}

}
