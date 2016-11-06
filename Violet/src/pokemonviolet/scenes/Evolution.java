/*
 *  Pokemon Violet - A University Project by Andres Movilla
 *  Pokemon COPYRIGHT 2002-2016 Pokemon.
 *  COPYRIGHT 1995-2016 Nintendo/Creatures Inc./GAME FREAK inc. TRADEMARK, REGISTERED TRADEMARK
 *  and Pokemon character names are trademarks of Nintendo.
 *  No copyright or trademark infringement is intended in using Pokemon content on Pokemon Violet.
 */
package pokemonviolet.scenes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import pokemonviolet.model.Handler;
import pokemonviolet.control.KeyHandler;
import pokemonviolet.model.Pokemon;

/**
 *
 * @author Andres
 */
public class Evolution extends Scene {
	private static final int SPRITE_WIDTH = 80;
	private static final int SPRITE_HEIGHT = 80;

	private Pokemon subject;
	private BufferedImage oldImage;
	private BufferedImage newImage;
	private int timer;
	
	public Evolution(Handler main, Pokemon subject) {
		super(main, "EVOLUTION", true);
		
		this.subject = subject;
		this.timer = 0;
		try {
			this.oldImage = subject.getFrontImage();
			this.newImage = new pokemonviolet.model.Pokemon(subject.getNextEvolutionId()).getFrontImage();
		} catch (IOException ex) {
			
		}
	}

	@Override
	public void receiveKeyAction(int action, int state) {		
		if (state == KeyHandler.STATE_RELEASE) {
			if (action == KeyHandler.ACTION_A) {
				accept();
			}
		}
	}

	@Override
	protected void accept() {
		if (timer > 90) {
			subject.evolve(subject.getNextEvolutionId());
			subject.setNextEvolutionId(-1);
			this.dispose();
		}
	}

	@Override
	protected void cancel() {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	protected void move(int dir) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	protected void start() {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public BufferedImage getDisplay() throws IOException {
		BufferedImage tempStitched = new BufferedImage(resizedValue(ssX), resizedValue(ssY), BufferedImage.TYPE_INT_ARGB);
		Graphics g = tempStitched.getGraphics();
		
		g.setColor(Color.black);
		g.fillRect(0, 0, resizedValue(ssX), resizedValue(ssY));
		
		timer = timer + 1;
		if (timer < 100) {
			if (timer % 2 == 0) {
				g.drawImage(newImage, resizedValue((ssX / 2) - (SPRITE_WIDTH / 2)), resizedValue((ssY / 2) - (SPRITE_HEIGHT / 2)), null);
			} else if (timer % 2 == 1) {
				g.drawImage(oldImage, resizedValue((ssX / 2) - (SPRITE_WIDTH / 2)), resizedValue((ssY / 2) - (SPRITE_HEIGHT / 2)), null);
			}
		} else {
			g.drawImage(newImage, resizedValue((ssX / 2) - (SPRITE_WIDTH / 2)), resizedValue((ssY / 2) - (SPRITE_HEIGHT / 2)), null);
			
			if (timer > 80  && timer < 83) {
				g.setColor(Color.white);
				g.fillRect(0, 0, resizedValue(ssX), resizedValue(ssY));
			}
		}
		
		return tempStitched;		
	}

}
