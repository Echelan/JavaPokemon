/*
 *  Pokemon Violet - A University Project by Andres Movilla
 *  Pokemon COPYRIGHT 2002-2016 Pokemon.
 *  COPYRIGHT 1995-2016 Nintendo/Creatures Inc./GAME FREAK inc. TRADEMARK, REGISTERED TRADEMARK
 *  and Pokemon character names are trademarks of Nintendo.
 *  No copyright or trademark infringement is intended in using Pokemon content on Pokemon Violet.
 */
package pokemonviolet.scenes;

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
public abstract class Scene {
	protected static final int ssX=pokemonviolet.model.Handler.SCREEN_SIZE_X, ssY=pokemonviolet.model.Handler.SCREEN_SIZE_Y;
	protected final Handler main;
	private final String name;
	private final boolean full;

	public Scene(Handler main, String name, boolean full) {
		this.main = main;
		this.name = name;
		this.full = full;
	}
	
	public abstract void receiveKeyAction(String action,String state);
	
	protected abstract void accept();
	
	protected abstract void cancel();
	
	protected abstract void start();
	
	protected abstract void dispose();
	
	protected abstract void move(String dir);

	public abstract BufferedImage getDisplay();
	
	/**
	 * Generates a window with the given theme and given dimensions.
	 * @param theme Window theming.
	 * @param dimX Window dimension in X.
	 * @param dimY Window dimension in Y.
	 * @return Window Buffered Image.
	 * @throws IOException 
	 */
	protected BufferedImage genWindow(int theme, int dimX, int dimY) throws IOException{
		BufferedImage window = new BufferedImage( dimX, dimY, BufferedImage.TYPE_INT_ARGB);
		Graphics g = window.getGraphics();
		
		float RESIZE = 2.0f;
		int pieceDim = (int)(RESIZE*7);
		
		if (dimX<pieceDim*2){
			dimX=pieceDim*2;
		}
		
		if (dimY<pieceDim*2){
			dimY=pieceDim*2;
		}
		
		int numTilesX=dimX/pieceDim, numTilesY=dimY/pieceDim;
		
		for (int i = 0; i < numTilesY+1; i++) {
			for (int j = 0; j < numTilesX+1; j++) {
				int pieceX=0, pieceY=0;
				int thisY=i*pieceDim, thisX=j*pieceDim;
			
				if (i>0){
					pieceY=pieceY+1;
				}

				if (i==numTilesY){
					pieceY=pieceY+1;
					thisY=dimY-pieceDim;
				}

				if (j>0){
					pieceX=pieceX+1;
				}

				if (j==numTilesX){
					pieceX=pieceX+1;
					thisX=dimX-pieceDim;
				}
				
				g.drawImage(ImageIO.read(new File("assets/windows/"+theme+".png")).getSubimage(7*pieceX, 7*pieceY, 7, 7), thisX,thisY,pieceDim,pieceDim,null);
			}
		}
		
		return window;
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the full
	 */
	public boolean isFull() {
		return full;
	}
	
}
