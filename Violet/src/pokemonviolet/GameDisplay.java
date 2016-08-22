/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokemonviolet;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Andres
 */
public class GameDisplay extends Canvas {

	BufferedImage playerSprite;
	
	
//	public GameDisplay(GraphicsConfiguration gc) {
	public GameDisplay() {
	//	super(gc);
		try {
			playerSprite = ImageIO.read(new File("player.png"));
		} catch (IOException ex) {
		}
	}
	
	public void paint(Graphics g){
		g.drawImage(playerSprite, 100, 100, this);
	}
	
}
