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
public class Title extends Scene {

	private int blackBarY, logoY, growlitheX, arcanineX, startDisplay;
	private final int finalBlackBarY, finalLogoY, finalArcanineX, finalGrowlitheX;
	private boolean ready;

	private boolean konamiExecuted;
	private String[] konamiCode = {"UP", "UP", "DOWN", "DOWN", "LEFT", "RIGHT", "LEFT", "RIGHT", "B", "A", "START"};
	private ArrayList<String> konamiInput;

	public Title(Handler main, boolean canKonami) {
		super(main, "TITLE", true);

		konamiExecuted = !canKonami;

		konamiInput = new ArrayList<String>();

		ready = false;
		startDisplay = 6;

		logoY = -400;
		finalLogoY = 00;

		blackBarY = ssY + 50;
		finalBlackBarY = ssY - 50;

		arcanineX = ssX;
		finalArcanineX = ssX - 160;

		growlitheX = ssX + 120;
		finalGrowlitheX = finalArcanineX + 60;

		logoY = -100;
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
						new pokemonviolet.builder.MapBuilder(javax.swing.JFrame.DISPOSE_ON_CLOSE);
						konamiExecuted = true;
					}
				} else {
					konamiInput.clear();
				}
			}
		}

		if (!konamid && state.compareTo("RELEASE") == 0) {
			if (action.compareTo("START") == 0) {
				start();
			} else if (action.compareTo("A") == 0) {
				accept();
			} else if (action.compareTo("B") == 0) {
				cancel();
			}
		}
	}

	@Override
	protected void accept() {
		logoY = finalLogoY;
		arcanineX = finalArcanineX;
		growlitheX = finalGrowlitheX;
		blackBarY = finalBlackBarY;
		ready = true;
	}

	@Override
	protected void start() {
		if (ready) {
			this.dispose();
		} else {
			accept();
		}
	}

	@Override
	protected void cancel() {
		System.exit(0);
	}

	@Override
	protected void dispose() {
		main.gameState.remove(main.gameState.size() - 1);
		main.gameState.add(new GenderChoose(main));
	}

	@Override
	protected void move(String dir) {
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

		ready = true;

		if (blackBarY != finalBlackBarY) {
			blackBarY = blackBarY - 20;
			if (ready) {
				ready = false;
			}
		}
		g.setColor(Color.black);
		g.fillRect(0, blackBarY, ssX, ssY);
		g.setColor(Color.white);
		g.setFont(new Font("Arial", Font.BOLD, 15));
		g.drawString("Andrés Movilla", 20, blackBarY + 40);

		try {
			if (arcanineX != finalArcanineX) {
				arcanineX = arcanineX - 20;
				if (ready) {
					ready = false;
				}
			}
			g.drawImage(ImageIO.read(new File("assets/pokemon/59ffn.png")), arcanineX, ssY - 220, 160, 160, null);
		} catch (IOException ex) {
		}

		try {
			if (arcanineX == finalArcanineX) {
				if (growlitheX != finalGrowlitheX) {
					growlitheX = growlitheX - 20;
					if (ready) {
						ready = false;
					}
				}
			}
			g.drawImage(ImageIO.read(new File("assets/pokemon/58ffn.png")), growlitheX, ssY - 170, 160, 160, null);
		} catch (IOException ex) {
		}

		try {
			if (logoY != finalLogoY) {
				logoY = logoY + 20;
				if (ready) {
					ready = false;
				}
			}
			g.drawImage(ImageIO.read(new File("assets/title/violetLogo.png")), (ssX / 2) - 40 - (int) ((int) (1363 / 4) / 2), logoY, (int) (1363 / 4), (int) (786 / 4), null);
		} catch (IOException ex) {
		}

		if (ready) {
			startDisplay = startDisplay - 1;
			if (startDisplay < 2) {
				if (startDisplay == 0) {
					startDisplay = 6;
				}
				g.setFont(new Font("Arial", Font.BOLD, 25));
				g.drawString("Press Start", 35, 250);
			}
		}

		return display;
	}

}
