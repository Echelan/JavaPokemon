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
public class BuyDialog extends Scene{

	private int itemID;
	private int itemAmount;
	private int choice;
	private int curPrice;
	private int itemPrice;
	private String[] choices = {"Buy", "Cancel"};
	
	public BuyDialog(Handler main, int id) {
		super(main, "BUY", false);
		
		this.choice = 0;
		this.itemID = id;
		this.itemAmount = 1;
		this.itemPrice = new pokemonviolet.model.Item(this.itemID).getPrice();
		this.curPrice = this.itemAmount * this.itemPrice;
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
		if (choice == 0) {
			if (main.player.getFunds() >= curPrice) {
				main.player.setFunds(main.player.getFunds() - curPrice);
				main.player.addItem(new pokemonviolet.model.Item(itemID, itemAmount));
				this.dispose();
			}
		} else if (choice == 1) {
			this.dispose();
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
	protected void dispose() {
		main.gameState.remove(main.gameState.size() - 1);
	}

	@Override
	protected void move(String dir) {
		if (dir.compareTo("UP") == 0) {
			choice = choice - 1;
		} else if (dir.compareTo("DOWN") == 0) {
			choice = choice + 1;
		} else if (dir.compareTo("LEFT") == 0) {
			itemAmount = itemAmount - 1;
		} else if (dir.compareTo("RIGHT") == 0) {
			itemAmount = itemAmount + 1;
		}
		
		if (choice < 0) {
			choice = 1;
		} else if (choice > 1) {
			choice = 0;
		}
		
		if (itemAmount > 99) {
			itemAmount = 99;
		} else if (itemAmount < 1) {
			itemAmount = 1;
		}
		
		this.curPrice = this.itemAmount * this.itemPrice;
	}

	@Override
	public BufferedImage getDisplay() {
		BufferedImage tempStitched = new BufferedImage(ssX, ssY, BufferedImage.TYPE_INT_ARGB);
		Graphics g = tempStitched.getGraphics();

		int x = ssX - 160;
		try {
			g.drawImage(genWindow(0, 120, 60), x, 40, null);
			g.drawImage(genWindow(0, 120, 60), x, 101, null);
			g.drawImage(genWindow(0, 120, 90), x, 162, null);
		} catch (IOException ex) {
		}
		
		g.setColor(Color.black);
		g.setFont(new Font("Arial", Font.BOLD, 20));
		g.drawString("x"+itemAmount, x + 20, 80);
		
		if (curPrice > main.player.getFunds()) {
			g.setColor(Color.red);
		}
		g.drawString("$  "+curPrice, x + 20, 141);
		
		g.setColor(Color.black);
		for (int i = 0; i < choices.length; i++) {
			g.drawString(choices[i], x + 30, 203 + (i * 30));
		}
		
		try {
			g.drawImage(ImageIO.read(new File("assets/arrow.png")), x + 10, 203 + (choice * 30) - 15, 20, 20, null);
		} catch (IOException ex) {
		}
		
		return tempStitched;
	}
	
}
