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
public class Dex extends Scene {
	
	private static final int SPRITE_WIDTH = 80;
	private static final int SPRITE_HEIGHT = 80;

	private int selectionX;
	private int selectionY;
	private int maxIndex;
	private final int maxRows;
	private final int maxCols;
	private final int numLastRow;
	private float baseY;
	private boolean showDescrip;
	
	public Dex(Handler main) {
		super(main, "POKEDEX", true);
		
		this.selectionX = 1;
		this.selectionY = 1;
		this.baseY = 50;
		this.showDescrip = false;
		
		for (int i = 0; i < main.getPlayer().getPokeDex().length; i++) {
			if (main.getPlayer().getPokeDex()[i] != 0) {
				this.maxIndex = i;
			}
		}
		this.maxIndex = this.maxIndex + 1;
		this.maxCols = 6;
		this.maxRows = (int) Math.floor(this.maxIndex / this.maxCols) + 1;
		this.numLastRow = (int) Math.floor(this.maxIndex % this.maxCols);
	}

	@Override
	public void receiveKeyAction(int action, int state) {
		if (state == KeyHandler.STATE_RELEASE) {
			if (action == KeyHandler.ACTION_A) {
				accept();
			} else if (action == KeyHandler.ACTION_B) {
				cancel();
			}
		} else if (state == KeyHandler.STATE_PRESS) {
			if (action != KeyHandler.ACTION_A && action != KeyHandler.ACTION_B && action != KeyHandler.ACTION_START) {
				move(action);
			}
		}
	}

	@Override
	protected void accept() {
		int curIndex = ((selectionY - 1) * maxCols) + selectionX;
		if (main.getPlayer().getPokeDex()[curIndex - 1] != 0) {
			this.showDescrip = true;
		}
	}

	@Override
	protected void cancel() {
		if (this.showDescrip) {
			this.showDescrip = false;
		} else {
			this.dispose();
		}
	}

	@Override
	protected void move(int dir) {
		if (!showDescrip) {
			if (dir == KeyHandler.ACTION_UP) {
				selectionY = selectionY - 1;
			} else if (dir == KeyHandler.ACTION_DOWN) {
				selectionY = selectionY + 1;
			} else if (dir == KeyHandler.ACTION_RIGHT) {
				selectionX = selectionX + 1;
			} else if (dir == KeyHandler.ACTION_LEFT) {
				selectionX = selectionX - 1;
			}

			if (selectionY < 1) {
				selectionY = 1;
			} else if (selectionY > maxRows) {
				selectionY = maxRows;
			}

			if (selectionX < 1) {
				selectionX = 1;
			} else if (selectionX > maxCols) {
				selectionX = maxCols;
			}

			if (selectionY == maxRows) {
				if (selectionX > numLastRow) {
					selectionX = numLastRow;
				}
			}
		}
	}

	@Override
	protected void start() {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public BufferedImage getDisplay() throws IOException {
		BufferedImage tempStitched = new BufferedImage(resizedValue(ssX), resizedValue(ssY), BufferedImage.TYPE_INT_ARGB);
		Graphics g = tempStitched.getGraphics();

		g.drawImage(ImageIO.read(new File("assets/"+main.getPlayer().getGender().toLowerCase()+"Back.png")), 0, 0, resizedValue(ssX), resizedValue(ssY), null);
		
		baseY = 75 + (selectionY - 0.5f) * -1 * 37.5f;

		for (int i = 0; i < maxIndex; i++) {
			int x = (int) Math.floor(i % maxCols), y = (int) Math.floor(i / maxCols);
			g.drawImage(genWindow(7, 35, 35), resizedValue(10.5 + (x * 37.5)), resizedValue(baseY + (y * 37.5)), null);
			if (main.getPlayer().getPokeDex()[i] != 0) {
				g.drawImage(new pokemonviolet.model.Pokemon(i + 1).getIcon(), resizedValue(10.5 + (x * 37.5) - 2.5), resizedValue(baseY + (y * 37.5) - 2.5), null);
			} else {
				g.drawImage(ImageIO.read(new File("assets/pokemon/blank.png")), resizedValue(10.5 + 6.5 + (x * 37.5)), resizedValue(baseY + 6.5 + (y * 37.5)), resizedValue(18 * 1.25f), resizedValue(18 * 1.25f), null);
			}
		}
		
		g.drawImage(ImageIO.read(new File("assets/hand.png")), resizedValue(10.5 + 14 + (selectionX - 1) * 37.5), resizedValue((ssY / 2) - 35), resizedValue(20), resizedValue(22), null);

		g.drawImage(genWindow(3, 60, 25), resizedValue(0.5), resizedValue(0.5), null);
		if (this.showDescrip) {
			g.drawImage(genWindow(3, ssX - 61.5, 70), resizedValue(61), resizedValue(0.5), null);
		} else {
			g.drawImage(genWindow(3, ssX - 61.5, 40), resizedValue(61), resizedValue(0.5), null);
		}
		
		g.setColor(Color.black);
		g.setFont(new Font("Arial",Font.BOLD, resizedValue(10)));
		
		g.drawString("PokeDex", resizedValue(7.5), resizedValue(16.5));
		
		int curIndex = ((selectionY - 1) * maxCols) + selectionX;
		
		if (main.getPlayer().getPokeDex()[curIndex - 1] != 0) {
			pokemonviolet.model.Pokemon indexPokemon = new pokemonviolet.model.Pokemon(curIndex);
				g.drawString(indexPokemon.getNameSpecies(), resizedValue(70), resizedValue(16.5));
			if (this.showDescrip) {
				g.setFont(new Font("Arial", Font.PLAIN, resizedValue(7.5)));
				
				g.drawImage(genWindow(7, 85, 85), resizedValue(2.5), resizedValue(ssY - 85 - 2.5), null);
				g.drawImage(indexPokemon.getFrontImage(), resizedValue(2.5 + (85 / 2) - (SPRITE_WIDTH / 2)), resizedValue(ssY - 85 - 2.5 + (85 / 2) - (SPRITE_HEIGHT / 2)), null);
				
				if (main.getPlayer().getPokeDex()[curIndex - 1] == 2) {
					
					for (int i = 0; i < genMultilineText(indexPokemon.getPokeEntry(), 46).length; i++) {
						g.drawString(genMultilineText(indexPokemon.getPokeEntry(), 46)[i], resizedValue(75), resizedValue(27.5 + (i * 10)));
					}
					
				} else {
					g.drawString("No data.", resizedValue(110), resizedValue(27.5));
				}
				g.setFont(new Font("Arial",Font.BOLD, resizedValue(10)));
			} else {
				if (main.getPlayer().getPokeDex()[curIndex - 1] == 2) {
					g.drawString(indexPokemon.getKind() + " Pokemon", resizedValue(140), resizedValue(16.5));
					g.drawString(indexPokemon.getHeight() + "m", resizedValue(75), resizedValue(27.5));
					g.drawString(indexPokemon.getWeight() + "kg", resizedValue(110), resizedValue(27.5));
					for (int i = 0; i < indexPokemon.getTypes().length; i++) {
						if (indexPokemon.getTypes()[i] != null) {
							g.drawString(pokemonviolet.model.PokemonType.getNameDisplay(indexPokemon.getTypes()[i]), resizedValue(145 + (i * 40)), resizedValue(27.5));
						}
					}
				} else {
					g.drawString("No data.", resizedValue(110), resizedValue(27.5));
				}
			}
		}
		
		return tempStitched;
	}

}
