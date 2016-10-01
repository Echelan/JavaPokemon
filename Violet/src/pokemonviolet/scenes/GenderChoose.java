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
public class GenderChoose extends Scene{
	
	private int chosen=0;
	private String[] genders = {"Male","Female"};
	private String[] name = {"Boy","Girl"};
	
	public GenderChoose(Handler main) {
		super(main, "GENDER", true);
	}

	@Override
	public void receiveKeyAction(String action, String state) {
		if (state.compareTo("RELEASE")==0){
			if (action.compareTo("LEFT")==0 || action.compareTo("RIGHT")==0){
				move(action);
			}else if (action.compareTo("A")==0){
				accept();
			}else if (action.compareTo("B")==0){
				cancel();
			}
		}
	}

	@Override
	protected void accept() {
		this.dispose();
		main.gameState.add(new StarterChoose(main, genders[chosen]));
	}

	@Override
	protected void cancel() {
		this.dispose();
		main.gameState.add(new Title(main,false));
	}

	@Override
	protected void dispose() {
		main.gameState.remove(main.gameState.size()-1);
	}

	@Override
	protected void move(String dir) {
		if (dir.compareTo("LEFT")==0){
			chosen=chosen-1;
		}else if (dir.compareTo("RIGHT")==0){
			chosen=chosen+1;
		}
		
		if (chosen<0){
			chosen=1;
		}else if(chosen>1){
			chosen=0;
		}
	}
	
	@Override
	public BufferedImage getDisplay() {
		BufferedImage display = new BufferedImage( ssX, ssY, BufferedImage.TYPE_INT_RGB);
		Graphics g = display.getGraphics();
		
		try {
			g.drawImage(ImageIO.read(new File("assets/title/background.png")), 0,0,null);
		} catch (IOException ex) {
		}
		
		g.setFont(new Font("Arial",Font.BOLD,30));
		g.drawString("Are you boy or a girl?", ssX/5, 30);
		
		float resize=2.5f;
		int characterWidth=(int)(40*resize), characterHeight=(int)(82*resize);
		
		for (int i = 0; i < genders.length; i++) {
			int x = 75+(i*(ssX/2));
			try {
				g.drawImage(ImageIO.read(new File("assets/"+genders[i].toLowerCase()+"Char.png")), x,50,characterWidth,characterHeight,null);
				if (chosen==i){
					g.setColor(Color.WHITE);
				}else{
					g.setColor(Color.GRAY);
				}
				g.drawString(name[i], x+(characterWidth/4), ssY-20);
			} catch (IOException ex) {
			}
		}
		
		return display;
	}

	@Override
	protected void start() {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	
}
