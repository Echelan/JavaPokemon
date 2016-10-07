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
	int selection;
	String returnState;
	int itemID;
	
	public LearnMove(Handler main, Pokemon subject, PokemonMove object, String returnState) {
		super(main, "LEARNMOVE", true);
		
		this.subject = subject;
		this.object = object;
		this.returnState = returnState;
		this.itemID = -1;
		
		this.selection = 0;
	}
	
	public LearnMove(Handler main, Pokemon subject, PokemonMove object, String returnState, int itemID) {
		super(main, "LEARNMOVE", true);
		
		this.subject = subject;
		this.object = object;
		this.returnState = returnState;
		this.itemID = itemID;
		
		this.selection = 0;
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
		if (selection < 4) {
			subject.setMove(selection, object);
			main.clearStates(returnState);
			if (itemID != -1) {
				main.player.subItem(itemID);
			}
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
		
		if (selection < 0){
			selection = 0;
		} else if (selection > 4) {
			selection = 4;
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
		
		g.drawImage(ImageIO.read(new File("assets/pokemonBack.png")), 0, 0, ssX, ssY, null);
		
		g.setFont(new Font("Arial", Font.BOLD, 25));
		g.setColor(Color.black);
		
		g.drawImage(genWindow(7, 170, 170), 5, 5, null);
		g.drawImage(subject.getFrontImage(), 5 + (170 / 2) - (SPRITE_WIDTH / 2), 5 + (170 / 2) - (SPRITE_HEIGHT / 2), SPRITE_WIDTH, SPRITE_HEIGHT, null);
		
		g.drawImage(genWindow(7, ssX - 185, ssY - 61), 180, 5, null);
		
		g.drawImage(genWindow(7, 170, 50), 5, 180, null);
		g.drawString(subject.getNameNick(), 20, 214);
		
		g.setFont(new Font("Arial", Font.BOLD, 25));
		for (int i = 0; i < subject.getNumMoves() + 1; i++) {
			if (i < subject.getNumMoves()) {
				int x = 190, y = 30 + (i * 30);
				g.drawString(subject.getMoveSet()[i].getNameDisplay(), x + 20, y + 25);
			} else {
				int x = 190, y = 30 + (i * 30);
				g.setColor(Color.blue);
				g.drawString(object.getNameDisplay(), x + 20, y + 25);
				g.setColor(Color.black);
			}
					
		}
		
		g.setFont(new Font("Arial", Font.BOLD, 15));
		g.drawImage(genWindow(7, ssX - 2, 85), 1, ssY - 86, null);
		
		PokemonMove target;
		if (selection == 4) {
			target = object;
		} else {
			target = subject.getMoveSet()[selection];
		}
		if (target != null) {
			g.drawString(target.getNameDisplay(), 16, ssY - 63);
			g.drawString("Power: "+target.getDmgBase(), 136, ssY - 63);
			g.drawString("Accuracy: "+target.getAccuracy(), 226, ssY - 63);
			g.drawString("PP: "+target.getPP()+"/"+target.getPPMax(), 356, ssY - 63);
			g.setFont(new Font("Arial", Font.PLAIN, 15));

			int charsInLine = 60;
			String fullD = target.getDescription();
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

				g.drawString(prefix + fullD.substring(thisLineFirstChar, thisLineLastChar+1) + suffix, 15, ssY - 43 + (i * 20));
			}	
		}
		g.setFont(new Font("Arial", Font.BOLD, 25));
		
		int x = 195, y = 40 + (selection * 30);
		g.drawImage(ImageIO.read(new File("assets/arrow.png")), x, y, 20, 20, null);
		
		return tempStitched;
	}

}
