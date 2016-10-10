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
	
	private static final int SPRITE_WIDTH = 80;
	private static final int SPRITE_HEIGHT = 80;

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
		BufferedImage tempStitched = new BufferedImage(resizedValue(ssX), resizedValue(ssY), BufferedImage.TYPE_INT_ARGB);
		Graphics g = tempStitched.getGraphics();
		
		g.drawImage(ImageIO.read(new File("assets/pokemonBack.png")), 0, 0, resizedValue(ssX), resizedValue(ssY), null);
		
		g.setFont(new Font("Arial", Font.BOLD, resizedValue(12.5)));
		g.setColor(Color.black);
		
		g.drawImage(genWindow(7, 85, 85), resizedValue(2.5), resizedValue(2.5), null);
		g.drawImage(subject.getFrontImage(), resizedValue(2.5 + (85 / 2) - (SPRITE_WIDTH / 2)), resizedValue(2.5 + (85 / 2) - (SPRITE_HEIGHT / 2)), resizedValue(SPRITE_WIDTH), resizedValue(SPRITE_HEIGHT), null);
		
		g.drawImage(genWindow(7, ssX - 92.5, ssY - 47.5), resizedValue(90), resizedValue(2.5), null);
		
		g.drawImage(genWindow(7, 85, 25), resizedValue(2.5), resizedValue(90), null);
		g.drawString(subject.getNameNick(), resizedValue(10), resizedValue(107));
		
		g.setFont(new Font("Arial", Font.BOLD, resizedValue(12.5)));
		for (int i = 0; i < subject.getNumMoves() + 1; i++) {
			if (i < subject.getNumMoves()) {
				int x = 95, y = 15 + (i * 15);
				g.drawString(subject.getMoveSet()[i].getNameDisplay(), resizedValue(x + 10), resizedValue(y + 12.5));
			} else {
				int x = 95, y = 15 + (i * 15);
				g.setColor(Color.blue);
				g.drawString(object.getNameDisplay(), resizedValue(x + 10), resizedValue(y + 12.5));
				g.setColor(Color.black);
			}
					
		}
		
		g.setFont(new Font("Arial", Font.BOLD, resizedValue(7.5)));
		g.drawImage(genWindow(7, ssX - 1, 42.5), resizedValue(0.5), resizedValue(ssY - 43), null);
		
		PokemonMove target;
		if (selection == 4) {
			target = object;
		} else {
			target = subject.getMoveSet()[selection];
		}
		if (target != null) {
			g.drawString(target.getNameDisplay(), resizedValue(8), resizedValue(ssY - 31.5));
			g.drawString("Power: "+target.getDmgBase(), resizedValue(68), resizedValue(ssY - 31.5));
			g.drawString("Accuracy: "+target.getAccuracy(), resizedValue(113), resizedValue(ssY - 31.5));
			g.drawString("PP: "+target.getPP()+"/"+target.getPPMax(), resizedValue(178), resizedValue(ssY - 31.5));
			g.setFont(new Font("Arial", Font.PLAIN, resizedValue(7.5)));

			for (int i = 0; i < genMultilineText(target.getDescription(), 60).length; i++) {
				g.drawString(genMultilineText(target.getDescription(), 60)[i], resizedValue(7.5), resizedValue(ssY - 21.5 + (i * 10)));
			}
			
		}
		g.setFont(new Font("Arial", Font.BOLD, 25));
		
		g.drawImage(ImageIO.read(new File("assets/arrow.png")), resizedValue(90), resizedValue(20 + (selection * 15)), resizedValue(10), resizedValue(10), null);
		
		return tempStitched;
	}

}
