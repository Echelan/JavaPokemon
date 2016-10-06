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
import pokemonviolet.model.Pokemon;
import pokemonviolet.model.PokemonMove;

/**
 *
 * @author Andres
 */
public class LearnMove extends Scene {
	private static final int SPRITE_WIDTH = (int) (80 * RESIZE);
	private static final int SPRITE_HEIGHT = (int) (80 * RESIZE);

	Pokemon subject;
	PokemonMove object;
	int selectionX;
	int selectionY;
	
	public LearnMove(Handler main, Pokemon subject, PokemonMove object) {
		super(main, "LEARNMOVE", true);
		
		this.subject = subject;
		this.object = object;
		
		this.selectionX = 0;
		this.selectionY = 0;
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
			if (action.compareTo("A") != 0 && action.compareTo("B") != 0 && action.compareTo("START") != 0) {
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
		} else if (dir.compareTo("LEFT") == 0) {
			selectionX = selectionX - 1;
		} else if (dir.compareTo("RIGHT") == 0) {
			selectionX = selectionX + 1;
		}
		
		if (selectionX < 0){
			selectionX = 0;
		} else if (selectionX > 1) {
			selectionX = 1;
		}
		
		if (selectionY < 0){
			selectionY = 0;
		} else if (selectionY > 2) {
			selectionY = 2;
		}
	}

	@Override
	protected void start() {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public BufferedImage getDisplay() throws IOException {
		BufferedImage tempStitched = new BufferedImage(ssX, ssY, BufferedImage.TYPE_INT_ARGB);
		Graphics g = tempStitched.getGraphics();
		
		g.drawImage(ImageIO.read(new File("assets/maleBack.png")), 0, 0, ssX, ssY, null);
		
		g.setFont(new Font("Arial", Font.BOLD, 25));
		g.setColor(Color.black);
		
		g.drawImage(genWindow(7, 170, 170), 5, 5, null);
		g.drawImage(subject.getFrontImage(), 5 + (170 / 2) - (SPRITE_WIDTH / 2), 5 + (170 / 2) - (SPRITE_HEIGHT / 2), SPRITE_WIDTH, SPRITE_HEIGHT, null);
		
		g.drawImage(genWindow(7, ssX - 185, 250), 180, 5, null);
		
		g.drawImage(genWindow(7, 170, 50), 5, 180, null);
		g.drawString(subject.getNameNick(), 20, 214);
		
		g.setFont(new Font("Arial", Font.BOLD, 15));
		for (int i = 0; i < subject.getNumMoves() + 1; i++) {
			if (i < subject.getNumMoves()) {
				int x = 190 + ((int) Math.floor(i % 2) * 135), y = (30 + ((int) Math.floor(i / 2) * 45));
				g.drawImage(genWindow(5, 130, 40), x, y, null);
				g.drawString(subject.getMoveSet()[i].getNameDisplay(), x + 20, y + 25);
			} else {
				int x = 190 + ((int) Math.floor(i % 2) * 135), y = (30 + ((int) Math.floor(i / 2) * 45));
				g.drawImage(genWindow(6, 130, 40), x + 65, y, null);
				g.drawString(object.getNameDisplay(), x + 65 + 20, y + 25);
			}
					
		}
		
		g.drawImage(genWindow(7, ssX - 2, 85), 1, ssY - 86, null);
		
		if (selectionY == 2) {
			g.drawString(object.getNameDisplay(), 16, ssY - 63);
			g.drawString("Power: "+object.getDmgBase(), 76, ssY - 63);
			g.drawString("Accuracy: "+object.getAccuracy(), 146, ssY - 63);
			g.drawString("PP: "+object.getPP()+"/"+object.getPPMax(), 216, ssY - 63);
			g.setFont(new Font("Arial", Font.PLAIN, 20));
			
			int charsInLine = 30;
			String fullD = object.getDescription();
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
			
			g.setFont(new Font("Arial", Font.BOLD, 25));
		} else {
			int pos = (selectionY * 2) + selectionX;
			g.drawString(subject.getMoveSet()[pos].getNameDisplay(), 16, ssY - 63);
			g.drawString("Power: "+subject.getMoveSet()[pos].getDmgBase(), 76, ssY - 63);
			g.drawString("Accuracy: "+subject.getMoveSet()[pos].getAccuracy(), 146, ssY - 63);
			g.drawString("PP: "+subject.getMoveSet()[pos].getPP()+"/"+subject.getMoveSet()[pos].getPPMax(), 216, ssY - 63);
			g.setFont(new Font("Arial", Font.PLAIN, 20));
			
			int charsInLine = 30;
			String fullD = subject.getMoveSet()[pos].getDescription();
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
			
			g.setFont(new Font("Arial", Font.BOLD, 25));
		}
		
		int x = 195 + (selectionX * 135) + 10, y = (-7 + (selectionY * 45)) + 25;
		if (selectionY == 2) {
			x = 200 + (int) (1.5f * 135) + 10;
		}
		g.drawImage(ImageIO.read(new File("assets/arrow.png")), x, y, 20, 20, null);
		
		return tempStitched;
	}

}
