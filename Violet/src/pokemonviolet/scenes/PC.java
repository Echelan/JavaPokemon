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
import pokemonviolet.control.KeyHandler;
import javax.imageio.ImageIO;
import pokemonviolet.model.Handler;

/**
 *
 * @author Andres
 */
public class PC extends Scene {

	private int action;
	private boolean selAction;
	private int[] selectionX;
	private int[] selectionY;
	private int startIndex;
	private int maxItemsPage;
	private int[] maxRows;
	private int swapSelect;
	
	public PC(Handler main) {
		super(main, "PC", true);
		
		this.startIndex = 0;
		
		this.maxRows = new int[2];
		this.maxRows[0] = 2;
		this.maxRows[1] = 3;
		
		this.selAction = true;
		this.maxItemsPage = 3;
		this.action = 0;
		this.swapSelect = -1;
		
		this.selectionX = new int[2];
		this.selectionX[0] = 0;
		this.selectionX[1] = 0;
		
		this.selectionY = new int[2];
		this.selectionY[0] = 0;
		this.selectionY[1] = 0;
	}

	@Override
	public void receiveKeyAction(int action, int state) {
		if (state == KeyHandler.STATE_RELEASE) {
			if (action == KeyHandler.ACTION_A) {
				accept();
			} else if (action == KeyHandler.ACTION_B) {
				cancel();
			} else if (action == KeyHandler.ACTION_START) {
				start();
			}
		} else if (state == KeyHandler.STATE_PRESS) {
			if (action == KeyHandler.ACTION_UP || action == KeyHandler.ACTION_DOWN || action == KeyHandler.ACTION_LEFT || action == KeyHandler.ACTION_RIGHT) {
				move(action);
			}
		}
	}

	@Override
	protected void accept() {
		if (this.selAction) {
			this.selAction = false;
		} else {
			int id = selectionX[action] + (selectionY[action] * maxRows[action]);
			if (action == 0) {
				main.getPlayer().depositPC(id);
				if (swapSelect != -1) {
					action = 1;
					main.getPlayer().withdrawPC(swapSelect);
					swapSelect = -1;
				}
			} else if (action == 1) {
				if (main.getPlayer().getPC().size() > 0) {
					if (main.getPlayer().getNumPokemonTeam() < main.getPlayer().getTeam().length) {
						main.getPlayer().withdrawPC(id);
					} else {
						swapSelect = id;
						action = 0;
					}
				}
			}
		}
	}

	@Override
	protected void cancel() {
		if (this.selAction) {
			this.dispose();
		} else {
			this.selAction = true;
		}
	}

	@Override
	protected void start() {
		int id = selectionX[action] + (selectionY[action] * maxRows[action]);
		if (action == 0 && main.getPlayer().getTeam()[id] != null) {
			main.gameState.add(new Summary(main, main.getPlayer().getTeam()[id]));
		} else if (action == 1 && main.getPlayer().getPC().get(id) != null) {
			main.gameState.add(new Summary(main, main.getPlayer().getPC().get(id)));
		}
	}

	@Override
	protected void move(int dir) {
		if (this.selAction) {
			if (dir == KeyHandler.ACTION_LEFT) {
				action = action - 1;
			} else if (dir == KeyHandler.ACTION_RIGHT) {
				action = action + 1;
			}
			
			if (action < 0) {
				action = 1;
			} else if (action > 1) {
				action = 0;
			}
		} else {
			if (dir == KeyHandler.ACTION_UP || dir == KeyHandler.ACTION_DOWN) {
				if (dir == KeyHandler.ACTION_UP) {
					if (action == 1) {
						if (selectionY[action] == startIndex) {
							startIndex = startIndex - 1;
						}
					}
					selectionY[action] = selectionY[action] - 1;
				} else if (dir == KeyHandler.ACTION_DOWN) {
					if (action == 1) {
						if (selectionY[action] - (maxItemsPage - 1) == startIndex) {
							startIndex = startIndex + 1;
						}
					}
					selectionY[action] = selectionY[action] + 1;
				}
			} else if (dir == KeyHandler.ACTION_LEFT || dir == KeyHandler.ACTION_RIGHT) {
				if (dir == KeyHandler.ACTION_LEFT) {
					selectionX[action] = selectionX[action] - 1;
				} else if (dir == KeyHandler.ACTION_RIGHT) {
					selectionX[action] = selectionX[action] + 1;
				}
			}
			if (selectionX[action] < 0) {
				selectionX[action] = maxRows[action];
			} else if (selectionX[action] > maxRows[action]) {
				selectionX[action] = 0;
			}
			if (selectionY[action] < 0) {
				selectionY[action] = 0;
			} else {
				if (action == 0) {
					if (selectionY[action] > 3) {
						selectionY[action] = 3;
					}
				} else if (action == 1) {
					if (selectionY[action] > (int) (Math.floor((main.getPlayer().getPC().size() - 1) / maxRows[1]))) {
						selectionY[action] = (int) (Math.floor((main.getPlayer().getPC().size() - 1) / maxRows[1]));
					}
				}
			}
			if (action == 1) {
				if (selectionY[action] == (int) (Math.floor((main.getPlayer().getPC().size() - 1) / maxRows[1]))) {
					if (selectionX[action] > (int) (Math.floor((main.getPlayer().getPC().size() - 1) % maxRows[1]))) {
						selectionX[action] = (int) (Math.floor((main.getPlayer().getPC().size() - 1) % maxRows[1]));
					}
				}
			}
		}
	}

	@Override
	public BufferedImage getDisplay() throws IOException {
		BufferedImage tempStitched = new BufferedImage(resizedValue(ssX), resizedValue(ssY), BufferedImage.TYPE_INT_ARGB);
		Graphics g = tempStitched.getGraphics();
		
		g.drawImage(ImageIO.read(new File("assets/"+main.getPlayer().getGender().toLowerCase()+"Back.png")), 0, 0, resizedValue(ssX), resizedValue(ssY), null);
		
		g.setColor(Color.white);
		g.setFont(new Font("Arial",Font.BOLD, resizedValue(7.5)));
		
		int dimX = 62, dimY = 35;
		for (int i = 0; i < main.getPlayer().getPC().size(); i++) {
			int x = 20 + (int) (Math.floor(i % maxRows[1]) * (dimX + 5)), y = 34 + (int) (Math.floor(i / maxRows[1]) * (dimY + 5)) - ((selectionY[1] - 1) * (dimY + 5));
			g.drawImage(ImageIO.read(new File("assets/pcPokemon.png")), resizedValue(x), resizedValue(y), resizedValue(dimX), resizedValue(dimY), null);
				
			g.drawImage(main.getPlayer().getPC().get(i).getIcon(), resizedValue(x - 4), resizedValue(y - 1), null);
			g.drawString(main.getPlayer().getPC().get(i).getNameNick(), resizedValue(x + 4), resizedValue(y + 10));
			g.drawString("Lv. "+main.getPlayer().getPC().get(i).getLevel(), resizedValue(x + 35), resizedValue(y + 28));
		}
		
		g.setColor(Color.black);
		g.setFont(new Font("Arial",Font.BOLD, resizedValue(10)));
		g.drawImage(genWindow(3, 70, 25), resizedValue(0.5), resizedValue(0.5), null);
		g.drawString(main.getPlayer().getName()+"'s PC", resizedValue(7.5), resizedValue(16.5));
		
		
		if (this.action == 1) {
			g.setColor(Color.gray);
			g.drawImage(genWindow(5, 70, 25), resizedValue(71), resizedValue(0.5), null);
		} else if (this.action == 0) {
			g.setColor(Color.black);
			g.drawImage(genWindow(6, 70, 25), resizedValue(71), resizedValue(0.5), null);
		}
		g.drawString("Team", resizedValue(78), resizedValue(16.5));
		
		if (this.action == 1) {
			g.setColor(Color.black);
			g.drawImage(genWindow(6, 70, 25), resizedValue(141.5), resizedValue(0.5), null);
		} else if (this.action == 0) {
			g.setColor(Color.gray);
			g.drawImage(genWindow(5, 70, 25), resizedValue(141.5), resizedValue(0.5), null);
		}
		g.drawString("PC", resizedValue(148.5), resizedValue(16.5));
		
		g.setColor(Color.white);
		g.setFont(new Font("Arial",Font.BOLD, resizedValue(7.5)));
		if (this.action == 0) {
			g.drawImage(genWindow(5, 142, ssY - 26.5), resizedValue(0.5), resizedValue(26), null);
			for (int i = 0; i < main.getPlayer().getTeam().length; i++) {
				int x = 7 + (int) (Math.floor(i % maxRows[0]) * (dimX + 5)), y = 34 + (int) (Math.floor(i / maxRows[0]) * (dimY + 5));
				if (i < main.getPlayer().getNumPokemonTeam()) {
					g.drawImage(ImageIO.read(new File("assets/pcPokemon.png")), resizedValue(x), resizedValue(y), resizedValue(dimX), resizedValue(dimY), null);

					g.drawImage(main.getPlayer().getTeam()[i].getIcon(), resizedValue(x - 4), resizedValue(y - 1), null);
					g.drawString(main.getPlayer().getTeam()[i].getNameNick(), resizedValue(x + 4), resizedValue(y + 10));
					g.drawString("Lv. "+main.getPlayer().getTeam()[i].getLevel(), resizedValue(x + 35), resizedValue(y + 28));
				} else {
					g.drawImage(ImageIO.read(new File("assets/pcPokemonBlank.png")), resizedValue(x), resizedValue(y), resizedValue(dimX), resizedValue(dimY), null);
				}
			}
		}
		
		if (!this.selAction) {
			if (this.action == 0) {
				int x = 17 + (int) (selectionX[action] * (dimX + 5)), y = 17 + (int) (selectionY[action] * (dimY + 5));
				g.drawImage(ImageIO.read(new File("assets/hand.png")), resizedValue(x), resizedValue(y), resizedValue(20), resizedValue(22), null);
			} else if (this.action == 1 && main.getPlayer().getPC().size() > 0) {
				int x = 30 + (int) (selectionX[action] * (dimX + 5)), y = 17 + (dimY);
				g.drawImage(ImageIO.read(new File("assets/hand.png")), resizedValue(x), resizedValue(y), resizedValue(20), resizedValue(22), null);
			}
		}
		
		return tempStitched;
	}
	
}
