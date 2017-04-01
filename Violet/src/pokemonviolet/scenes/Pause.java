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
import pokemonviolet.control.KeyHandler;
import javax.imageio.ImageIO;
import pokemonviolet.model.Handler;

/**
 *
 * @author Andres
 */
public class Pause extends Scene {

	private String[] options = {"PokeDex", "Heal", "Shop", "Pokemon", "PC", "Bag", "Controls", "Exit"};
	private int selection;
	private boolean showControls;

	public Pause(Handler main) {
		super(main, "PAUSE", false);

		this.showControls = false;
		this.selection = 0;
	}

	@Override
	public void receiveKeyAction(int action, int state) {
		if (state == KeyHandler.STATE_RELEASE) {
			if (showControls) {
				showControls = false;
			} else {
				if (action == KeyHandler.ACTION_A) {
					accept();
				} else if (action == KeyHandler.ACTION_B) {
					cancel();
				} else if (action == KeyHandler.ACTION_START) {
					start();
				} else if (action == KeyHandler.ACTION_UP || action == KeyHandler.ACTION_DOWN) {
					move(action);
				}
			}
		}
	}

	@Override
	protected void accept() {
		if (options[selection].compareTo("Heal") == 0) {
			main.gameState.add(new Center(main));
		} else if (options[selection].compareTo("Shop") == 0) {
			main.gameState.add(new Shop(main));
		} else if (options[selection].compareTo("Bag") == 0) {
			main.gameState.add(new Bag(main, null));
		} else if (options[selection].compareTo("Pokemon") == 0) {
			main.gameState.add(new Team(main));
		} else if (options[selection].compareTo("PokeDex") == 0) {
			main.gameState.add(new Dex(main));
		} else if (options[selection].compareTo("PC") == 0) {
			main.gameState.add(new PC(main));
		} else if (options[selection].compareTo("Controls") == 0) {
			showControls = true;
		} else if (options[selection].compareTo("Exit") == 0) {
			main.clearStates("");
			main.gameState.add(new Title(main, false));
		}
	}

	@Override
	protected void cancel() {
		this.dispose();
	}

	@Override
	protected void move(int dir) {
		if (dir == KeyHandler.ACTION_UP) {
			selection = selection - 1;
		} else if (dir == KeyHandler.ACTION_DOWN) {
			selection = selection + 1;
		}
		if (selection >= options.length) {
			selection = 0;
		} else if (selection < 0) {
			selection = options.length - 1;
		}
	}

	@Override
	protected void start() {
		this.dispose();
	}

	@Override
	public BufferedImage getDisplay() throws IOException {
		BufferedImage display = new BufferedImage(resizedValue(ssX), resizedValue(ssY), BufferedImage.TYPE_INT_ARGB);
		Graphics g = display.getGraphics();

		int windowWidth = 90, windowHeight = 127;
		g.drawImage(genWindow(4, windowWidth, windowHeight), resizedValue(ssX - windowWidth - 0.5), resizedValue((ssY / 2) - (windowHeight / 2)), null);

		g.setColor(Color.black);
		g.setFont(new Font("Arial", Font.BOLD, resizedValue(12.5)));
		for (int i = 0; i < options.length; i++) {
			g.drawString(options[i], resizedValue(ssX - windowWidth + 15), resizedValue(((ssY / 2) - (windowHeight / 2)) + 20 + (i * 12.5)));
		}

		g.drawImage(ImageIO.read(new File("assets/arrow.png")), resizedValue(ssX - windowWidth + 5), resizedValue(((ssY / 2) - (windowHeight / 2)) + 11 + (selection * 12.5)), resizedValue(10), resizedValue(10), null);

		if (showControls) {
			g.drawImage(ImageIO.read(new File("assets/controls.png")), resizedValue((ssX / 2) - (216.5 / 2)), resizedValue((ssY / 2) - (157 / 2)), resizedValue(216.5), resizedValue(157), null);
		}
		
		return display;
	}

}
