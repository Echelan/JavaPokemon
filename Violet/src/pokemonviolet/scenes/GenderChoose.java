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
	private String playerName;
	
	public GenderChoose(Handler main) {
		super(main, "GENDER", true);
		playerName="Player";
	}

	@Override
	public void receiveKeyAction(String action, String state) {
		if (state.compareTo("PRESS")==0){
			if (action.compareTo("LEFT")==0 || action.compareTo("RIGHT")==0){
				move(action);
			}else if (action.compareTo("A")==0){
				this.dispose();
			}
		}
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
	protected void dispose() {
		if (playerName.compareTo("")==0){
			playerName="Red";
		}
		main.startGame(playerName,genders[chosen]);
		main.gameState.remove(main.gameState.size()-1);
		main.gameState.add(new Game(main));
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
		
		try {
			g.drawImage(ImageIO.read(new File("assets/maleChar.png")), 0+50,50,characterWidth,characterHeight,null);
			if (chosen==0){
				g.setColor(Color.WHITE);
			}else{
				g.setColor(Color.GRAY);
			}
			g.drawString("Boy", 0+50+(characterWidth/3), ssY-20);
		} catch (IOException ex) {
		}
		
		
		try {
			g.drawImage(ImageIO.read(new File("assets/femaleChar.png")), (ssX/2)+50,50,characterWidth,characterHeight,null);
			if (chosen==1){
				g.setColor(Color.WHITE);
			}else{
				g.setColor(Color.GRAY);
			}
			g.drawString("Girl", (ssX/2)+50+(characterWidth/3), ssY-20);
		} catch (IOException ex) {
		}
		
		return display;
	}

	
}
