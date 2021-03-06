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
import pokemonviolet.control.KeyHandler;
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
	public void receiveKeyAction(int action, int state) {
		if (state == KeyHandler.STATE_RELEASE) {
			if (action == KeyHandler.ACTION_A) {
				accept();
			} else if (action == KeyHandler.ACTION_B) {
				cancel();
			} else if (action == KeyHandler.ACTION_START) {
			}
		} else if (state == KeyHandler.STATE_PRESS) {
			if (action == KeyHandler.ACTION_UP || action == KeyHandler.ACTION_DOWN || action == KeyHandler.ACTION_LEFT || action == KeyHandler.ACTION_RIGHT) {
				move(action);
			}
		}
	}

	@Override
	protected void accept() {
		if (choice == 0) {
			if (main.getPlayer().getFunds() >= curPrice) {
				main.getPlayer().setFunds(main.getPlayer().getFunds() - curPrice);
				main.getPlayer().addItem(new pokemonviolet.model.Item(itemID, itemAmount));
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
	protected void move(int dir) {
		if (dir == KeyHandler.ACTION_UP) {
			choice = choice - 1;
		} else if (dir == KeyHandler.ACTION_DOWN) {
			choice = choice + 1;
		} else if (dir == KeyHandler.ACTION_LEFT) {
			itemAmount = itemAmount - 1;
		} else if (dir == KeyHandler.ACTION_RIGHT) {
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
	public BufferedImage getDisplay() throws IOException {
		BufferedImage display = new BufferedImage(resizedValue(ssX), resizedValue(ssY), BufferedImage.TYPE_INT_ARGB);
		Graphics g = display.getGraphics();

		int x = ssX - 80;
		g.drawImage(genWindow(0, 60, 30), resizedValue(x), resizedValue(20), null);
		g.drawImage(genWindow(0, 60, 30), resizedValue(x), resizedValue(50 + 0.5), null);
		g.drawImage(genWindow(0, 60, 45), resizedValue(x), resizedValue(81), null);
		
		g.setColor(Color.black);
		g.setFont(new Font("Arial", Font.BOLD, resizedValue(10)));
		g.drawString("x"+itemAmount, resizedValue(x + 10), resizedValue(40));
		
		if (curPrice > main.getPlayer().getFunds()) {
			g.setColor(Color.red);
		}
		g.drawString("$  "+curPrice, resizedValue(x + 10), resizedValue(70 + 0.5));
		
		g.setColor(Color.black);
		for (int i = 0; i < choices.length; i++) {
			g.drawString(choices[i], resizedValue(x + 15), resizedValue(100 + 1.5 + (i * 15)));
		}
		
		g.drawImage(ImageIO.read(new File("assets/arrow.png")), resizedValue(x + 5), resizedValue(100 + 1.5 + (choice * 15) - 7.5), resizedValue(10), resizedValue(10), null);
		
		return display;
	}
	
}
