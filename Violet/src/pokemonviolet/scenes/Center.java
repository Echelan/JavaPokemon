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
import static pokemonviolet.scenes.Scene.ssX;

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
		
		main.player.pokemonCenter();
		
		countdown = 100;
		animFrame1 = 0;
		animFrame2 = 6;
		try {
			animImg = ImageIO.read(new File("assets/ballAnim.png"));
		} catch (IOException ex) {
		}
	}

	@Override
	public void receiveKeyAction(String action, String state) {
		
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
	protected void dispose() {
		main.gameState.remove(main.gameState.size() - 1);
	}

	@Override
	protected void move(String dir) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public BufferedImage getDisplay() {
		BufferedImage tempStitched = new BufferedImage(ssX, ssY, BufferedImage.TYPE_INT_ARGB);
		Graphics g = tempStitched.getGraphics();
		
		int dimX = (int) (ssX / 2) + 50, dimY = (int) (30 * RESIZE);
		int posX = (ssX / 2) - (dimX / 2), posY = (ssY / 2) - (dimY / 2);
		try {
			g.drawImage(genWindow(2, dimX, dimY), posX, posY, null);
		} catch (IOException ex) {
		}
		
		g.setColor(Color.black);
		g.setFont(new Font("Arial", Font.BOLD, 20));
		g.drawString( "Healing Pokemon...", posX + 35 + 20, posY + 40);
		
		int yDisplace = (int) (((int) Math.pow(animFrame1 - 6, 2)) * 0.5f);
		g.drawImage(animImg.getSubimage(animFrame1 * 16, 0, 16, 16), posX + 10, posY + 5 + yDisplace, (int) (16 * RESIZE), (int) (16 * RESIZE), null);
		animFrame1 = animFrame1 + 1;
		if (animFrame1 > 11) {
			animFrame1 = 0;
		}
		
		yDisplace = (int) (((int) Math.pow(animFrame2 - 6, 2)) * 0.5f);
		g.drawImage(animImg.getSubimage(animFrame2 * 16, 0, 16, 16), posX + dimX - 40, posY + 5 + yDisplace, (int) (16 * RESIZE), (int) (16 * RESIZE), null);
		animFrame2 = animFrame2 + 1;
		if (animFrame2 > 11) {
			animFrame2 = 0;
		}
		
		countdown = countdown - 1;
		if (countdown < 0) {
			this.dispose();
		}
		
		return tempStitched;
	}
	
}
