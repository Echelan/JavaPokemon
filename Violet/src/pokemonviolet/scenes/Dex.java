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
public class Dex extends Scene {

	private int selectionX;
	private int selectionY;
	private int maxIndex;
	private int maxRows;
	private int maxCols;
	private int numLastRow;
	private int baseY;
	
	public Dex(Handler main) {
		super(main, "POKEDEX", true);
		
		this.selectionX = 1;
		this.selectionY = 1;
		this.baseY = 100;
		
		for (int i = 0; i < main.player.getPokeDex().length; i++) {
			if (main.player.getPokeDex()[i]) {
				this.maxIndex = i;
			}
		}
		this.maxIndex = this.maxIndex + 1;
		this.maxCols = 6;
		this.maxRows = (int) Math.floor(this.maxIndex / this.maxCols) + 1;
		this.numLastRow = (int) Math.floor(this.maxIndex % this.maxCols);
	}

	@Override
	public void receiveKeyAction(String action, String state) {
		if (state.compareTo("RELEASE") == 0) {
			if (action.compareTo("A") == 0) {
			} else if (action.compareTo("B") == 0) {
				cancel();
			} else if (action.compareTo("START") == 0) {
			} else{
				move(action);
			}
		}
	}

	@Override
	protected void accept() {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	protected void cancel() {
		this.dispose();
	}

	@Override
	protected void dispose() {
		main.gameState.remove(main.gameState.size() - 1);
	}

	@Override
	protected void move(String dir) {
		if (dir.compareTo("UP") == 0) {
			selectionY = selectionY - 1;
		} else if (dir.compareTo("DOWN") == 0) {
			selectionY = selectionY + 1;
		} else if (dir.compareTo("RIGHT") == 0) {
			selectionX = selectionX + 1;
		} else if (dir.compareTo("LEFT") == 0) {
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

	@Override
	protected void start() {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public BufferedImage getDisplay() {
		BufferedImage tempStitched = new BufferedImage(ssX, ssY, BufferedImage.TYPE_INT_ARGB);
		Graphics g = tempStitched.getGraphics();

		try {
			g.drawImage(ImageIO.read(new File("assets/"+main.player.getGender().toLowerCase()+"Back.png")), 0, 0, ssX, ssY, null);
		} catch (IOException ex) {
		}
		
		baseY = (selectionY - 1) * 75;

		for (int i = 0; i < maxIndex; i++) {
			int x = (int) Math.floor(i % maxCols), y = (int) Math.floor(i / maxCols);
			try {
				g.drawImage(genWindow(7, 70, 70), 21 + (x * 75), baseY + (y * 75), null);
				if (main.player.getPokeDex()[i]) {
					g.drawImage(pokemonviolet.data.NIC.pokemonIcons.getSubimage( (int) Math.floor(i % 10) * 40, (int) Math.floor(i / 10) * 40, 40, 40), 21 + (x * 75) + 5, baseY + (y * 75) + 5, (int) (40 * 1.5f), (int) (40 * 1.5f), null);
				} else {
					g.drawImage(ImageIO.read(new File("assets/pokemon/blank.png")), 21 + 8 + (x * 75) + 5, baseY + 8 + (y * 75) + 5, (int) (36 * 1.25f), (int) (36 * 1.25f), null);
				}
			} catch (IOException ex) {
			}
		}
		
		try {
			g.drawImage(ImageIO.read(new File("assets/hand.png")), 21 + 28 + ((selectionX - 1) * 75), (ssY / 2) - 40, (int) (20 * RESIZE), (int) (22 * RESIZE), null);
		} catch (IOException ex) {
			System.err.println("!");
		}

		try {
			g.drawImage(genWindow(5, 120, 50), 1, 1, null);
			g.drawImage(genWindow(5, ssX - 123, 80), 122, 1, null);
		} catch (IOException ex) {
		}
		
		g.setColor(Color.black);
		g.setFont(new Font("Arial",Font.BOLD, 20));
		
		g.drawString("PokeDex", 15, 33);
		
		int curIndex = ((selectionY - 1) * maxCols) + selectionX;
		
		if (main.player.getPokeDex()[curIndex - 1]) {
			pokemonviolet.model.Pokemon indexPokemon = new pokemonviolet.model.Pokemon(curIndex);
			g.drawString(indexPokemon.getNameSpecies(), 140, 33);
			g.drawString(indexPokemon.getKind() + " Pokemon", 280, 33);
			g.drawString(indexPokemon.getHeight() + "m", 150, 55);
			g.drawString(indexPokemon.getWeight() + "kg", 220, 55);
			for (int i = 0; i < indexPokemon.getTypes().length; i++) {
				if (indexPokemon.getTypes()[i] != null) {
					g.drawString(pokemonviolet.model.PokemonType.getNameDisplay(indexPokemon.getTypes()[i]), 290 + (i * 80), 55);
				}
			}
		}
		

		return tempStitched;
	}

}
