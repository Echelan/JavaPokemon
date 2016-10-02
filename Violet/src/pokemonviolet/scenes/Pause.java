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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import pokemonviolet.model.Handler;

/**
 *
 * @author Andres
 */
public class Pause extends Scene{
	
	private String[] options = {"Heal","PokeDex","Pokemon","PC","Bag"};
	private int selection = 0;
	
	public Pause(Handler main) {
		super(main, "PAUSE", false);
	}

	@Override
	public void receiveKeyAction(String action, String state) {
		if (state.compareTo("RELEASE")==0){
			if (action.compareTo("A")==0){
				accept();
			}else if(action.compareTo("B")==0){
				cancel();
			}else if(action.compareTo("START")==0){
				start();
			}else if(action.compareTo("UP")==0 || action.compareTo("DOWN")==0){
				move(action);
			}
		}
	}

	@Override
	protected void accept() {
		if (options[selection].compareTo("Heal")==0){
			main.player.pokemonCenter();
			this.dispose();
		}
	}

	@Override
	protected void cancel() {
		this.dispose();
	}

	@Override
	protected void dispose() {
		main.gameState.remove(main.gameState.size()-1);
	}

	@Override
	protected void move(String dir) {
		if(dir.compareTo("UP")==0){
			selection = selection-1;
		}else if(dir.compareTo("DOWN")==0){
			selection = selection+1;
		}
		if (selection>=options.length){
			selection=0;
		}else if(selection<0){
			selection = options.length-1;
		}
	}

	@Override
	protected void start() {
		this.dispose();
	}

	@Override
	public BufferedImage getDisplay() {
		BufferedImage tempStitched = new BufferedImage( ssX, ssY, BufferedImage.TYPE_INT_ARGB);
		Graphics g = tempStitched.getGraphics();
		
		float RESIZE = 2.0f;
		int windowWidth=(int)(90*RESIZE), windowHeight=(int)(127*RESIZE);
		try {
			g.drawImage(ImageIO.read(new File("assets/pauseui.png")), ssX-windowWidth,(int)((ssY/2)-(windowHeight/2)),windowWidth,windowHeight,null);
		} catch (IOException ex) {
		}
		
		g.setColor(Color.black);
		g.setFont(new Font("Arial",Font.BOLD,25));
		for (int i = 0; i < options.length; i++) {
			g.drawString(options[i],ssX-windowWidth+30,(int)((ssY/2)-(windowHeight/2))+40+(i*25));
		}
		
		try {
			g.drawImage(ImageIO.read(new File("assets/arrow.png")), ssX-windowWidth+10, (int)((ssY/2)-(windowHeight/2))+22+(selection*25), 20, 20, null);
		} catch (IOException ex) {
			Logger.getLogger(Pause.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		return tempStitched;
	}
	
}
