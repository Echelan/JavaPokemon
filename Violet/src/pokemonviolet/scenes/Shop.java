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
import pokemonviolet.model.Handler;
import static pokemonviolet.scenes.Scene.ssX;

/**
 *
 * @author Andres
 */
public class Shop extends Scene {
	private int[] fullInventory = {1, 2, 3, 5, 6, 7, 8, 9, 10, 19, 20, 21, 22, 23, 24, 32, 33, 34, 35, 36, 37, 38, 39, 40, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 57, 58, 59, 60};
	private int[][] perPocketInventory;
	private int[] perPocketSize;
	private String[] categoryNames = {"Items", "Medicine", "PokeBalls"};
	private int selection;
	private int category;
	private int startIndexX;
	private int maxItemsPage;

	public Shop(Handler main) {
		super(main, "SHOP", false);
		perPocketInventory = new int[3][50];
		perPocketSize = new int[3];
		for (int i = 0; i < perPocketSize.length; i++) {
			perPocketSize[i] = 0;
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
				perPocketInventory[pocket][perPocketSize[pocket]] = fullInventory[i];
				perPocketSize[pocket] = perPocketSize[pocket] + 1;
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
		main.gameState.add(new BuyDialog(main,perPocketInventory[category][selection]));
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
		
		if (selection >= perPocketSize[category]) {
			if (startIndexX > perPocketSize[category] - maxItemsPage && perPocketSize[category] > maxItemsPage) {
				startIndexX = perPocketSize[category] - maxItemsPage;
			}
			selection = perPocketSize[category] - 1;
		} else if (startIndexX < 0) {
			startIndexX = 0;
			selection = 0;
		}
	}

	@Override
	public BufferedImage getDisplay() {
		BufferedImage tempStitched = new BufferedImage(ssX, ssY, BufferedImage.TYPE_INT_ARGB);
		Graphics g = tempStitched.getGraphics();

		float RESIZE = 2.0f;
		int windowWidth = (int) (120 * RESIZE), windowHeight = (int) (ssY * 0.8);
		try {
			g.drawImage(genWindow(0, windowWidth, ssY - windowHeight - 3), ssX - windowWidth - 1, 1, null);
			g.drawImage(genWindow(0, windowWidth, windowHeight), ssX - windowWidth - 1, ssY-windowHeight - 1, null);
			g.drawImage(genWindow(0, 130, 60), 1, 1, null);
			g.drawImage(genWindow(0, 80, 80), 24, 66, null);
			g.drawImage(genWindow(0, ssX - windowWidth - 3, 120), 1, ssY - 121, null);
			g.drawImage(genWindow(0, ssX - windowWidth - 3, 50), 1, ssY - 122 - 50, null);
		} catch (IOException ex) {
		}
		
		g.setColor(Color.black);
		g.setFont(new Font("Arial", Font.BOLD, 15));
		g.drawString("$  "+main.player.getFunds(), 15, 36);
		
		g.setFont(new Font("Arial", Font.BOLD, 20));
		g.drawString(categoryNames[category], ssX - windowWidth + 20, 36);
		
		g.setFont(new Font("Arial", Font.PLAIN, 15));
		int charsInLine = 30;
		String fullD = new pokemonviolet.model.Item(perPocketInventory[category][selection]).getDescription();
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
			g.drawImage(new pokemonviolet.model.Item(perPocketInventory[category][selection]).getImage(), 34, 76, null);
		} catch ( IOException ex) {
			
		}
			
		int maxIndex = startIndexX + maxItemsPage;
		if (maxIndex > perPocketSize[category]) {
			maxIndex = perPocketSize[category];
		}
		
		g.setFont(new Font("Arial", Font.BOLD, 20));
		g.drawString(new pokemonviolet.model.Item(perPocketInventory[category][selection]).getNameSingular(), 15, ssY - 140);
		for (int i = startIndexX; i < maxIndex; i++) {
			g.drawString(new pokemonviolet.model.Item(perPocketInventory[category][i]).getNameSingular(), ssX - windowWidth + 20, ((ssY / 2) - (windowHeight / 2) + 65) + ((i-startIndexX) * 20));
			g.drawString("$"+new pokemonviolet.model.Item(perPocketInventory[category][i]).getPrice(), ssX - (windowWidth / 2) + 50, ((ssY / 2) - (windowHeight / 2) + 65) + ((i-startIndexX) * 20));
		}

		try {
			int dispSelection = selection - startIndexX;
			g.drawImage(ImageIO.read(new File("assets/arrow.png")), ssX - windowWidth + 5, ((ssY / 2) - (windowHeight / 2) + 47) + (dispSelection * 20), 20, 20, null);
		} catch (IOException ex) {
		}

		return tempStitched;
	}

}
