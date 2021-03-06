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
public class Center extends Scene{
	
	private int animFrame1;
	private int animFrame2;
	private BufferedImage animImg;
	private int countdown;

	public Center(Handler main) {
		super(main, "CENTER", false);
		
		main.getPlayer().pokemonCenter();
		
		countdown = 100;
		animFrame1 = 0;
		animFrame2 = 6;
		try {
			animImg = ImageIO.read(new File("assets/ballAnim.png"));
		} catch (IOException ex) {
		}
	}

	@Override
	public void receiveKeyAction(int action, int state) {
		
	}

	@Override
	protected void accept() {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	protected void cancel() {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	protected void start() {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	protected void move(int dir) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public BufferedImage getDisplay() throws IOException {
		BufferedImage display = new BufferedImage(resizedValue(ssX), resizedValue(ssY), BufferedImage.TYPE_INT_ARGB);
		Graphics g = display.getGraphics();
		
		int dimX = (ssX / 2) + 25, dimY = 30;
		int posX = (ssX / 2) - (dimX / 2), posY = (ssY / 2) - (dimY / 2);
		g.drawImage(genWindow(2, dimX, dimY), resizedValue(posX), resizedValue(posY), null);
		
		g.setColor(Color.black);
		g.setFont(new Font("Arial", Font.BOLD, resizedValue(10)));
		g.drawString( "Healing Pokemon...", resizedValue((posX + 17.5) + 10), resizedValue(posY + 20));
		
		float yDisplace = (float) Math.pow(animFrame1 - 6, 2) * 0.25f;
		g.drawImage(animImg.getSubimage(animFrame1 * 16, 0, 16, 16), resizedValue(posX + 5), resizedValue(posY + 2.5 + yDisplace), resizedValue(16), resizedValue(16), null);
		animFrame1 = animFrame1 + 1;
		if (animFrame1 > 11) {
			animFrame1 = 0;
		}
		
		yDisplace = (float) Math.pow(animFrame2 - 6, 2) * 0.25f;
		g.drawImage(animImg.getSubimage(animFrame2 * 16, 0, 16, 16), resizedValue(posX + dimX - 20), resizedValue(posY + 2.5 + yDisplace), resizedValue(16), resizedValue(16), null);
		animFrame2 = animFrame2 + 1;
		if (animFrame2 > 11) {
			animFrame2 = 0;
		}
		
		countdown = countdown - 1;
		if (countdown < 0) {
			this.dispose();
		}
		
		return display;
	}
	
}
