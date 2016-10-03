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

	private int[] inventory = {1, 2, 3, 5, 6, 7, 8, 9, 10, 19, 20, 21, 22, 23, 24, 32, 33, 34, 35, 36, 37, 38, 39, 40, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 57, 58, 59, 60};
	private int selection;
	private int startIndexX;
	private int maxItemsPage;

	public Shop(Handler main) {
		super(main, "SHOP", false);

		this.selection = 0;
		this.startIndexX = 0;
		this.maxItemsPage = 10;
	}

	@Override
	public void receiveKeyAction(String action, String state) {
		if (state.compareTo("RELEASE") == 0) {
			if (action.compareTo("A") == 0) {
				accept();
			} else if (action.compareTo("B") == 0) {
				cancel();
			} else if (action.compareTo("START") == 0) {
			} else if (action.compareTo("UP") == 0 || action.compareTo("DOWN") == 0) {
				move(action);
			}
		}
	}

	@Override
	protected void accept() {
		main.player.addItem(inventory[selection]);
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
			selection = selection - 1;
		} else if (dir.compareTo("DOWN") == 0) {
			selection = selection + 1;
		}

		int dispSelection = startIndexX - selection;
		if (dispSelection > maxItemsPage) {
			selection = selection - 1;
			startIndexX = startIndexX + 1;
		} else if (dispSelection < 0) {
			selection = selection + 1;
			startIndexX = startIndexX - 1;
		}

		if (startIndexX > inventory.length - maxItemsPage) {
			startIndexX = inventory.length - maxItemsPage;
		} else if (startIndexX < 0) {
			startIndexX = 0;
		}

	}

	@Override
	public BufferedImage getDisplay() {
		BufferedImage tempStitched = new BufferedImage(ssX, ssY, BufferedImage.TYPE_INT_ARGB);
		Graphics g = tempStitched.getGraphics();

		float RESIZE = 2.0f;
		int windowWidth = (int) (120 * RESIZE), windowHeight = ssY;
		try {
			g.drawImage(genWindow(0, windowWidth, windowHeight), ssX - windowWidth, 0, null);
		} catch (IOException ex) {
		}

		g.setColor(Color.black);
		g.setFont(new Font("Arial", Font.BOLD, 25));
		for (int i = startIndexX; i < startIndexX + maxItemsPage; i++) {
			g.drawString(new pokemonviolet.model.Item(inventory[i]).getNameSingular(), ssX - windowWidth + 30, (int) ((ssY / 2) - (windowHeight / 2)) + 40 + (i * 25));
		}

		try {
			int dispSelection = startIndexX - selection;
			g.drawImage(ImageIO.read(new File("assets/arrow.png")), ssX - windowWidth + 10, (int) ((ssY / 2) - (windowHeight / 2)) + 22 + (dispSelection * 25), 20, 20, null);
		} catch (IOException ex) {
		}

		return tempStitched;

	}

}
