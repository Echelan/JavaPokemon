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
public class Summary extends Scene {
	
	private static final int SPRITE_WIDTH = 80;
	private static final int SPRITE_HEIGHT = 80;
	
	private int state;
	private Pokemon subject;
	private int selection;
	
	private boolean isLearning;
	PokemonMove object;
	String returnState;
	int itemID;
	
	public Summary(Handler main, Pokemon subject) {
		super(main, "SUMMARY", true);
		
		this.isLearning = false;
		this.state = 0;
		this.selection = 0;
		this.subject = subject;
	}
	
	public Summary(Handler main, Pokemon subject, PokemonMove object, String returnState) {
		super(main, "SUMMARY", true);
		
		this.isLearning = true;
		this.subject = subject;
		this.object = object;
		this.returnState = returnState;
		this.itemID = -1;
		this.state = 1;
		this.selection = 0;
	}

	public Summary(Handler main, Pokemon subject, PokemonMove object, String returnState, int itemID) {
		super(main, "SUMMARY", true);
		
		this.isLearning = true;
		this.subject = subject;
		this.object = object;
		this.returnState = returnState;
		this.itemID = itemID;
		this.state = 1;
		this.selection = 0;
	}
	
	@Override
	public void receiveKeyAction(String action, String state) {
		if (state.compareTo("RELEASE") == 0) {
			if (action.compareTo("A") == 0) {
				accept();
			} else if (action.compareTo("B") == 0) {
				cancel();
			} else if (action.compareTo("START") == 0) {
			}
		} else if (state.compareTo("PRESS") == 0) {
			if (action.compareTo("UP") == 0 || action.compareTo("DOWN") == 0 || action.compareTo("LEFT") == 0 || action.compareTo("RIGHT") == 0) {
				move(action);
			}
		}
	}

	@Override
	protected void accept() {
		if (isLearning) {
			if (selection < 4) {
				subject.setMove(selection, object);
				main.clearStates(returnState);
				if (itemID != -1) {
					main.getPlayer().subItem(itemID);
				}
			}
		}
	}

	@Override
	protected void cancel() {
		this.dispose();
	}

	@Override
	protected void start() {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	protected void move(String dir) {
		if (!isLearning) {
			if (dir.compareTo("LEFT") == 0) {
				state = state - 1;
			} else if (dir.compareTo("RIGHT") == 0) {
				state = state + 1;
			}
			if (state < 0) {
				state = 1;
			} else if (state > 1) {
				state = 0;
			}
		}
		
		if (dir.compareTo("UP") == 0) {
			selection = selection - 1;
		} else if (dir.compareTo("DOWN") == 0) {
			selection = selection + 1;
		}
		if (!isLearning) {
			if (selection < 0) {
				selection = 0;
			} else if (selection > subject.getNumMoves() - 1) {
				selection = subject.getNumMoves() - 1;
			}
		} else {
			if (selection < 0) {
				selection = 0;
			} else if (selection > subject.getNumMoves()) {
				selection = subject.getNumMoves();
			}
		}
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
		
		g.drawImage(genWindow(7, 85, 25), resizedValue(2.5), resizedValue(90), null);
		g.drawString(subject.getNameNick(), resizedValue(10), resizedValue(107));
		
		g.drawImage(genWindow(7, ssX - 92.5, ssY - 47.5), resizedValue(90), resizedValue(2.5), null);
		g.drawImage(genWindow(7, ssX - 1, 42.5), resizedValue(0.5), resizedValue(ssY - 43), null);
		if (state == 1) {
			g.setFont(new Font("Arial", Font.BOLD, resizedValue(12.5)));
			for (int i = 0; i < subject.getNumMoves() + 1; i++) {
				if (i < subject.getNumMoves()) {
					int x = 95, y = 15 + (i * 15);
					g.drawString(subject.getMoveSet()[i].getNameDisplay(), resizedValue(x + 10), resizedValue(y + 12.5));
				}
			}
			if (isLearning) {
				int x = 95, y = 15 + (4 * 15);
				g.setColor(Color.blue);
				g.drawString(object.getNameDisplay(), resizedValue(x + 10), resizedValue(y + 12.5));
				g.setColor(Color.black);
			}
			
			g.setFont(new Font("Arial", Font.BOLD, resizedValue(7.5)));
			
			PokemonMove target = null;
			if (selection < subject.getNumMoves()) {
				target = subject.getMoveSet()[selection];
			} else if (isLearning) {
				target = object;
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
			g.drawImage(ImageIO.read(new File("assets/arrow.png")), resizedValue(98), resizedValue(18.5 + (selection * 15)), resizedValue(10), resizedValue(10), null);
		} else {
			g.setColor(subject.getColorHP());
			g.fillRect(resizedValue(130), resizedValue(13.5), resizedValue(1.75), resizedValue(19.5));
			g.setColor(subject.getColorAttack());
			g.fillRect(resizedValue(139.5), resizedValue(9.5), resizedValue(1.75), resizedValue(26.5));
			g.setColor(subject.getColorDefense());
			g.fillRect(resizedValue(149), resizedValue(14), resizedValue(1.75), resizedValue(19.5));
			
			g.setColor(subject.getColorSpeed());
			g.fillRect(resizedValue(169), resizedValue(14), resizedValue(1.75), resizedValue(19));
			g.setColor(subject.getColorSpAtk());
			g.fillRect(resizedValue(179), resizedValue(9.5), resizedValue(1.75), resizedValue(26.5));
			g.setColor(subject.getColorSpDef());
			g.fillRect(resizedValue(188.5), resizedValue(13.5), resizedValue(1.75), resizedValue(19.5));
			
			g.setColor(Color.black);
			g.drawImage(ImageIO.read(new File("assets/DNA.png")), resizedValue(100), resizedValue(9), resizedValue(120), resizedValue(27.2), null);
			
			g.setColor(subject.getColor());
			g.fillRect(resizedValue(ssX - 30), resizedValue(45), resizedValue(15), resizedValue(15));
			if (subject.getGender().compareTo("Female") == 0) {
				g.setColor(Color.pink);
			} else if (subject.getGender().compareTo("Male") == 0) {
				g.setColor(Color.blue);
			} else {
				g.setColor(Color.gray);
			}
			g.fillRect(resizedValue(ssX - 30), resizedValue(65), resizedValue(15), resizedValue(15));
			
			g.drawImage(new pokemonviolet.model.Item(subject.getBallType()).getImage(),resizedValue(ssX - 30 - 8), resizedValue(80), null);
			
			g.setColor(Color.black);
			g.setFont(new Font("Arial", Font.BOLD, resizedValue(9)));
			g.drawString("Level "+subject.getLevel(), resizedValue(100), resizedValue(65));
			g.drawString("HP " + subject.getCurHP() + "/" + subject.getMaxHP(), resizedValue(100), resizedValue(80));
			g.drawString((subject.getCurEXP()/(float)subject.getMaxEXP()*100)+"% to level " + (subject.getLevel() + 1), resizedValue(100), resizedValue(95));
			
			g.setFont(new Font("Arial", Font.BOLD, resizedValue(7.5)));
			g.drawString(subject.getKind()+ " Pokemon", resizedValue(8), resizedValue(ssY - 31.5));
			g.drawString(subject.getWeight()+" kg", resizedValue(80), resizedValue(ssY - 31.5));
			g.drawString(subject.getHeight()+" m", resizedValue(120), resizedValue(ssY - 31.5));
			String typeText = "";
			typeText = typeText + subject.getTypes()[0];
			if (subject.getTypes()[1] != null) {
				typeText = typeText + " " +subject.getTypes()[1];
			}
			g.drawString(typeText, resizedValue(158), resizedValue(ssY - 31.5));
			g.setFont(new Font("Arial", Font.PLAIN, resizedValue(6.5)));

			for (int i = 0; i < genMultilineText(subject.getPokeEntry(), 80).length; i++) {
				g.drawString(genMultilineText(subject.getPokeEntry(), 80)[i], resizedValue(7.5), resizedValue(ssY - 21.5 + (i * 9)));
			}
		}
		return tempStitched;
	}
	
}
