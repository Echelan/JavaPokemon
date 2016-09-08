/*
 *  Pokemon Violet - A University Project by Andres Movilla
 *  Pokemon COPYRIGHT 2002-2016 Pokemon.
 *  COPYRIGHT 1995-2016 Nintendo/Creatures Inc./GAME FREAK inc. TRADEMARK, REGISTERED TRADEMARK
 *  and Pokemon character names are trademarks of Nintendo.
 *  No copyright or trademark infringement is intended in using Pokemon content on Pokemon Violet.
 */
package pokemonviolet;

import java.awt.Canvas;
import java.awt.Graphics;

/**
 *
 * @author Andres
 */
public class GameDisplay extends Canvas implements Runnable {
	
	// <editor-fold defaultstate="collapsed" desc="Attributes">
		/**
		 * Sprite for Player.
		 * <p> One frame dimensions: 20x20. </p>
		 */
	//	private static BufferedImage playerSprite;
		/**
		 * Current frame on Player Sprite.
		 */
	//	private static Image currentPlayerFrame;
		/**
		 * Current map.
		 */
	//	private static BufferedImage mapRegion;
		/**
		 * Current bounds.
		 */
	//	private static String[][] bounds;
	// </editor-fold>
	
	/**
	 * Create a GameDisplay custom canvas.
	 */
	public GameDisplay(){
	//	try {
	//		playerSprite = ImageIO.read(new File("player.png"));
	//		mapRegion = MapBuilder.getMapRegion(1);
	//		bounds = MapBuilder.getMapBounds(1);
	//	} catch (IOException ex) {
	//	}
	}
	
	public static boolean getCanMove(String direction){
		boolean canMove = false;
		
		switch (direction){
			case "LEFT":
				if (Game.displayedMaps[1][1].getBounds()[Game.player.getxTile()-1][Game.player.getyTile()].substring(0, 1).compareTo("0")==0){
					canMove = true;
				}
			break;
			case "RIGHT":
				if (Game.displayedMaps[1][1].getBounds()[Game.player.getxTile()+1][Game.player.getyTile()].substring(0, 1).compareTo("0")==0){
					canMove = true;
				}
			break;
			case "UP":
				if (Game.displayedMaps[1][1].getBounds()[Game.player.getxTile()][Game.player.getyTile()-1].substring(0, 1).compareTo("0")==0){
					canMove = true;
				}
			break;
			case "DOWN":
				if (Game.displayedMaps[1][1].getBounds()[Game.player.getxTile()][Game.player.getyTile()+1].substring(0, 1).compareTo("0")==0){
					canMove = true;
				}
			break;
		}
		
	
		return canMove;
	}
	
	public void paint(Graphics g){
		/*
		int lengthOfMap = 29;
		int counter = 0;
		g.setColor(Color.ORANGE);
		for (int i = 0; i < lengthOfMap; i++) {
			for (int j = 0; j < lengthOfMap; j++) {
				if (counter%2 == 0){
					g.fillRect(0+(i*20), 0+(j*20), 20, 20);
				}
				counter = counter+1;
			}
		}
		*/
		/*
		g.drawImage(mapRegion, 0, 0, this);
		g.drawImage(currentPlayerFrame, Game.player.getX(),Game.player.getY(),(int)(Player.SPRITE_X*Player.SPRITE_RESIZE),(int)(Player.SPRITE_Y*Player.SPRITE_RESIZE),this);
		
	*/
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				g.drawImage(Game.displayedMaps[i][j].getImage(), Game.displayedMaps[i][j].getX(), Game.displayedMaps[i][j].getY(), this);
			}
		}
		g.drawImage(Game.player.getCurFrameImage(), this.getWidth()/2, this.getHeight()/2,(int)(Game.player.SPRITE_X*Game.player.SPRITE_RESIZE),(int)(Game.player.SPRITE_Y*Game.player.SPRITE_RESIZE), this);
	}
	
    @Override
    public void run() {
        while (true){
			requestFocus();
/*
			
			int xPlayerIMG = 0;
			int yPlayerIMG = 0;
			if (Game.player.getvDirection().compareTo("") != 0){
				Game.player.setCurFrame(Game.player.getCurFrame()+1);
				if (Game.player.isRunning()){
					xPlayerIMG = xPlayerIMG + 60;
				}
			}


			xPlayerIMG = xPlayerIMG + (Game.player.getCurFrame()*20);
			yPlayerIMG = yPlayerIMG + (Game.player.getCurAnim()*20);
			currentPlayerFrame = Game.player.SPRITE_SHEET.getSubimage(xPlayerIMG, yPlayerIMG, 20, 20);

				);
           */

			repaint();
			try {
				Thread.sleep(60);
			} catch (InterruptedException ex) {
			}
        }
    }
	
}
