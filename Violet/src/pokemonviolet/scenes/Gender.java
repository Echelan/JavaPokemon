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
public class Gender extends Scene {

	private int chosen = 0;
	private String[] genders = {"Male", "Female"};
	private String[] name = {"Boy", "Girl"};

	public Gender(Handler main) {
		super(main, "GENDER", true);
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
			if (action.compareTo("UP") == 0 || action.compareTo("DOWN") == 0) {
				move(action);
			}
		}
	}

	@Override
	protected void accept() {
		this.dispose();
		main.gameState.add(new Starter(main, genders[chosen]));
	}

	@Override
	protected void cancel() {
		this.dispose();
		main.gameState.add(new Title(main, false));
	}

	@Override
	protected void move(String dir) {
		if (dir.compareTo("UP") == 0) {
			chosen = chosen - 1;
		} else if (dir.compareTo("DOWN") == 0) {
			chosen = chosen + 1;
		}

		if (chosen < 0) {
			chosen = 1;
		} else if (chosen > 1) {
			chosen = 0;
		}
	}

	@Override
	protected void start() {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public BufferedImage getDisplay() throws IOException {
		BufferedImage display = new BufferedImage(resizedValue(ssX), resizedValue(ssY), BufferedImage.TYPE_INT_ARGB);
		Graphics g = display.getGraphics();

		g.drawImage(ImageIO.read(new File("assets/title/background.png")), 0, 0, null);

		g.setFont(new Font("Arial", Font.BOLD, resizedValue(15)));
		g.drawString("Are you boy or a girl?", resizedValue(ssX / 5), resizedValue(15));

		int characterWidth = 40, characterHeight = 82;

		
		g.drawImage(ImageIO.read(new File("assets/" + genders[chosen].toLowerCase() + "Char.png")), resizedValue(50 + (ssX / 2)), resizedValue(25), resizedValue(characterWidth), resizedValue(characterHeight), null);
		for (int i = 0; i < genders.length; i++) {
			int y = 55 + (i*35);
			if (chosen == i) {
				g.setColor(Color.WHITE);
			} else {
				g.setColor(Color.GRAY);
			}
			g.drawString(name[i], resizedValue(60), resizedValue(y));
		}

		g.drawImage(ImageIO.read(new File("assets/moveUp.png")), resizedValue(30), resizedValue(ssY - 40), resizedValue(70), resizedValue(35), null);
		g.drawImage(ImageIO.read(new File("assets/moveDown.png")), resizedValue(105), resizedValue(ssY - 40), resizedValue(70), resizedValue(35), null);
		g.drawImage(ImageIO.read(new File("assets/buttonA.png")), resizedValue(180), resizedValue(ssY - 40), resizedValue(35), resizedValue(35), null);

		return display;
	}

}
