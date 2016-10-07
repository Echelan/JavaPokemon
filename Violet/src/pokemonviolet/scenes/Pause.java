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

/**
 *
 * @author Andres
 */
public class Pause extends Scene {

//	private String[] options = {"PokeDex", "Heal", "Shop", "Pokemon", "PC", "Bag", "Controls"};
	private String[] options = {"PokeDex", "Heal", "Shop", "Pokemon", "Bag", "Controls"};
	private int selection;
	private boolean showControls;

	public Pause(Handler main) {
		super(main, "PAUSE", false);

		this.showControls = false;
		this.selection = 0;
	}

	@Override
	public void receiveKeyAction(String action, String state) {
		if (state.compareTo("RELEASE") == 0) {
			if (showControls) {
				showControls = false;
			} else {
				if (action.compareTo("A") == 0) {
					accept();
				} else if (action.compareTo("B") == 0) {
					cancel();
				} else if (action.compareTo("START") == 0) {
					start();
				} else if (action.compareTo("UP") == 0 || action.compareTo("DOWN") == 0) {
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
		} else if (options[selection].compareTo("Controls") == 0) {
			showControls = true;
		}
	}

	@Override
	protected void cancel() {
		this.dispose();
	}

	@Override
	protected void move(String dir) {
		if (dir.compareTo("UP") == 0) {
			selection = selection - 1;
		} else if (dir.compareTo("DOWN") == 0) {
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
		BufferedImage tempStitched = new BufferedImage(ssX, ssY, BufferedImage.TYPE_INT_ARGB);
		Graphics g = tempStitched.getGraphics();

		int windowWidth = (int) (90 * RESIZE), windowHeight = (int) (127 * RESIZE);
		g.drawImage(genWindow(4, windowWidth, windowHeight), ssX - windowWidth - 1, (int) ((ssY / 2) - (windowHeight / 2)), null);

		g.setColor(Color.black);
		g.setFont(new Font("Arial", Font.BOLD, 25));
		for (int i = 0; i < options.length; i++) {
			g.drawString(options[i], ssX - windowWidth + 30, (int) ((ssY / 2) - (windowHeight / 2)) + 40 + (i * 25));
		}

		g.drawImage(ImageIO.read(new File("assets/arrow.png")), ssX - windowWidth + 10, (int) ((ssY / 2) - (windowHeight / 2)) + 22 + (selection * 25), 20, 20, null);

		if (showControls) {
			int dimX = 433, dimY = 314;
			g.drawImage(ImageIO.read(new File("assets/controls.png")), (ssX / 2) - (dimX / 2), (ssY / 2) - (dimY / 2), null);
		}
		
		return tempStitched;
	}

}
