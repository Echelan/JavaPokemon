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
public class StarterChoose extends Scene {

	private int[] pokeID = {1, 4, 7};
	private String[] pokeNames = {"Bulbasaur", "Charmander", "Squirtle"};
	private int chosen;

	private boolean konamiExecuted;
	private String[] konamiCode = {"UP", "UP", "DOWN", "DOWN", "LEFT", "RIGHT", "LEFT", "RIGHT", "B", "A", "START"};
	private ArrayList<String> konamiInput;
	private String playerGender;

	public StarterChoose(Handler main, String gender) {
		super(main, "STARTER", true);

		playerGender = gender;

		konamiInput = new ArrayList<String>();

		chosen = 0;

	}

	@Override
	public void receiveKeyAction(String action, String state) {
		boolean konamid = false;
		if (!konamiExecuted) {
			if (state.compareTo("RELEASE") == 0) {
				if (action.compareTo(konamiCode[konamiInput.size()]) == 0) {
					konamiInput.add(action);
					konamid = true;
					if (konamiInput.size() == konamiCode.length) {
						konamiInput.clear();
						pokeID[0] = 135;
						pokeID[1] = 136;
						pokeID[2] = 134;

						pokeNames[0] = "Jolteon";
						pokeNames[1] = "Flareon";
						pokeNames[2] = "Vaporeon";

						konamiExecuted = true;
					}
				} else {
					konamiInput.clear();
				}
			}
		}

		if (!konamid) {
			if (state.compareTo("RELEASE") == 0) {
				if (action.compareTo("A") == 0) {
					accept();
				} else if (action.compareTo("B") == 0) {
					cancel();
				}
			} else if (state.compareTo("PRESS") == 0) {
				if (action.compareTo("LEFT") == 0 || action.compareTo("RIGHT") == 0) {
					move(action);
				}
			}
		}
	}

	@Override
	protected void accept() {
		this.dispose();
		main.startGame("Player", playerGender, pokeID[chosen]);
		main.gameState.add(new Game(main));
	}

	@Override
	protected void cancel() {
		this.dispose();
		main.gameState.add(new GenderChoose(main));
	}

	@Override
	protected void dispose() {
		main.gameState.remove(main.gameState.size() - 1);
	}

	@Override
	protected void move(String dir) {
		if (dir.compareTo("LEFT") == 0) {
			chosen = chosen - 1;
		} else if (dir.compareTo("RIGHT") == 0) {
			chosen = chosen + 1;
		}

		if (chosen < 0) {
			chosen = 2;
		} else if (chosen > 2) {
			chosen = 0;
		}
	}

	@Override
	protected void start() {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
	
	@Override
	public BufferedImage getDisplay() {
		BufferedImage display = new BufferedImage(ssX, ssY, BufferedImage.TYPE_INT_ARGB);
		Graphics g = display.getGraphics();

		try {
			g.drawImage(ImageIO.read(new File("assets/title/background.png")), 0, 0, null);
		} catch (IOException ex) {
		}

		g.setFont(new Font("Arial", Font.BOLD, 30));
		g.drawString("Choose your starter!", ssX / 5, 30);

		float resize = 2.0f;
		int characterWidth = (int) (80 * resize), characterHeight = (int) (80 * resize);

		g.setFont(new Font("Arial", Font.BOLD, 15));
		for (int i = 0; i < pokeID.length; i++) {
			int x = 5 + (i * (ssX / 3));
			
			try {
				g.drawImage(ImageIO.read(new File("assets/pokemon/" + pokeID[i] + "mfn.png")), x, ssY / 5, characterWidth, characterHeight, null);
				if (chosen == i) {
					g.setColor(Color.WHITE);
				} else {
					g.setColor(Color.GRAY);
				}
				g.drawString(pokeNames[i], x + (characterWidth / 4), ssY / 5 + characterHeight + 5);
			} catch (IOException ex) {
			}
		}
			
		try{
			g.drawImage(ImageIO.read(new File("assets/moveLeft.png")), 60, ssY-80, 140, 70, null);
			g.drawImage(ImageIO.read(new File("assets/moveRight.png")), 210, ssY-80, 140, 70, null);
			g.drawImage(ImageIO.read(new File("assets/buttonB.png")), 360, ssY-80, 70, 70, null);
		}catch(IOException ex){

		}

		return display;
	}

}
