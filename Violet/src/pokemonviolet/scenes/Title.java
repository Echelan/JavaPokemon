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
import pokemonviolet.control.KeyHandler;
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
	private int[] konamiCode = {KeyHandler.ACTION_UP, KeyHandler.ACTION_UP, KeyHandler.ACTION_DOWN, KeyHandler.ACTION_DOWN, KeyHandler.ACTION_LEFT, KeyHandler.ACTION_RIGHT, KeyHandler.ACTION_LEFT, KeyHandler.ACTION_RIGHT, KeyHandler.ACTION_B, KeyHandler.ACTION_A, KeyHandler.ACTION_START};
	private ArrayList<Integer> konamiInput;

	public Title(Handler main, boolean canKonami) {
		super(main, "TITLE", true);

		konamiExecuted = !canKonami;

		konamiInput = new ArrayList<Integer>();

		ready = false;
		startDisplay = 6;

		logoY = -200;
		finalLogoY = 0;

		blackBarY = ssY + 25;
		finalBlackBarY = ssY - 25;

		arcanineX = ssX;
		finalArcanineX = ssX - 80;

		growlitheX = ssX + 60;
		finalGrowlitheX = finalArcanineX + 30;

		logoY = -50;
	}

	@Override
	public void receiveKeyAction(int action, int state) {
		boolean konamid = false;
		if (!konamiExecuted) {
			if (state == KeyHandler.STATE_RELEASE) {
				if (action == konamiCode[konamiInput.size()]) {
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

		if (!konamid && state == KeyHandler.STATE_RELEASE) {
			if (action == KeyHandler.ACTION_START) {
				start();
			} else if (action == KeyHandler.ACTION_A) {
				accept();
			} else if (action == KeyHandler.ACTION_B) {
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
			main.gameState.add(new Gender(main));
		} else {
			accept();
		}
	}

	@Override
	protected void cancel() {
		System.exit(0);
	}

	@Override
	protected void move(int dir) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public BufferedImage getDisplay() throws IOException {
		BufferedImage display = new BufferedImage(resizedValue(ssX), resizedValue(ssY), BufferedImage.TYPE_INT_ARGB);
		Graphics g = display.getGraphics();

		g.drawImage(ImageIO.read(new File("assets/title/background.png")), 0, 0, resizedValue(ssX), resizedValue(ssY), null);

		ready = true;

		if (blackBarY != finalBlackBarY) {
			blackBarY = blackBarY - 10;
			if (ready) {
				ready = false;
			}
		}
		g.setColor(Color.black);
		g.fillRect(0, resizedValue(blackBarY), resizedValue(ssX), resizedValue(ssY));
		g.setColor(Color.white);
		g.setFont(new Font("Arial", Font.BOLD, resizedValue(7.5)));
		g.drawString("Andrés Movilla", resizedValue(10), resizedValue(blackBarY + 20));

		if (arcanineX != finalArcanineX) {
			arcanineX = arcanineX - 10;
			if (ready) {
				ready = false;
			}
		}
		g.drawImage(ImageIO.read(new File("assets/pokemon/59ffn.png")), resizedValue(arcanineX), resizedValue(ssY - 110), resizedValue(80), resizedValue(80), null);

		if (arcanineX == finalArcanineX) {
			if (growlitheX != finalGrowlitheX) {
				growlitheX = growlitheX - 10;
				if (ready) {
					ready = false;
				}
			}
		}
		g.drawImage(ImageIO.read(new File("assets/pokemon/58ffn.png")), resizedValue(growlitheX), resizedValue(ssY - 85), resizedValue(80), resizedValue(80), null);

		if (logoY != finalLogoY) {
			logoY = logoY + 10;
			if (ready) {
				ready = false;
			}
		}
		g.drawImage(ImageIO.read(new File("assets/title/violetPokemonLogo.png")), resizedValue((ssX / 2) - 20 - (681.5 / 4) / 2), resizedValue(logoY), resizedValue(681.5 / 4), resizedValue(393 / 4), null);

		if (ready) {
			startDisplay = startDisplay - 1;
			if (startDisplay < 2) {
				if (startDisplay == 0) {
					startDisplay = 6;
				}
				g.setFont(new Font("Arial", Font.BOLD, resizedValue(12.5)));
				g.drawString("Press Start", resizedValue(17.5), resizedValue(125));
			}

			if (logoY != finalLogoY) {
				logoY = logoY + 10;
				if (ready) {
					ready = false;
				}
			}
			g.drawImage(ImageIO.read(new File("assets/buttonStart.png")), resizedValue( (ssX / 2) - 30), resizedValue(100), resizedValue(70), resizedValue(30), null);
		}

		return display;
	}

}
