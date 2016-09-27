/*
 *  Pokemon Violet - A University Project by Andres Movilla
 *  Pokemon COPYRIGHT 2002-2016 Pokemon.
 *  COPYRIGHT 1995-2016 Nintendo/Creatures Inc./GAME FREAK inc. TRADEMARK, REGISTERED TRADEMARK
 *  and Pokemon character names are trademarks of Nintendo.
 *  No copyright or trademark infringement is intended in using Pokemon content on Pokemon Violet.
 */
package pokemonviolet;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import javax.imageio.ImageIO;

/**
 *
 * @author Andres
 */
public class Combat {

	private final Player player;
	private final Trainer enemy;
	private final Pokemon currentPlayerPokemon;
	private final Pokemon currentEnemyPokemon;
	private final BufferedImage display;
	private final ArrayList<String> displayTextQueue;
	private final int maxX = 480, maxY = 320;
	private boolean waitingAction;
	private int optionsMain;
	private int optionsMoves;
	private static final int RESIZE = 2;
	private static final int SPRITE_WIDTH = 80*RESIZE;
	private static final int SPRITE_HEIGHT = 80*RESIZE;
	
	public Combat(Player player, Trainer enemy) {
		this.player = player;
		this.enemy = enemy;
		this.display = new BufferedImage( maxX, maxY, BufferedImage.TYPE_INT_RGB);
		this.waitingAction = false;
		this.optionsMoves = -1;
		this.optionsMain = -1;
		
		this.currentEnemyPokemon = enemy.getTeam()[enemy.getCurrentPokemon()];
		this.currentPlayerPokemon = player.getTeam()[player.getCurrentPokemon()];
		
		currentEnemyPokemon.setAccuracy(1);
		currentPlayerPokemon.setAccuracy(1);
		
		currentEnemyPokemon.setEvasion(1);
		currentPlayerPokemon.setEvasion(1);
		
		displayTextQueue = new ArrayList<String>();
		displayTextQueue.add("A wild "+currentEnemyPokemon.getNameNick()+" appeared!");
		
		refreshDisplayImage();
		
		this.player.setInCombat(true);
		this.player.setSpawnSteps(this.player.roll(1,2,3));
	}
	
	private void refreshDisplayImage(){
		Graphics g = getDisplay().getGraphics();
	//	g.clearRect(getDisplay().getMinX(), getDisplay().getMinY(), getDisplay().getWidth(), getDisplay().getHeight());
		
		//<editor-fold defaultstate="collapsed" desc="Background">
			try{
				g.drawImage(ImageIO.read(new File("assets/combat/background.png")), 0, 0, maxX, maxY, null);
			}catch(IOException ex){
			}

			try{
				g.drawImage(ImageIO.read(new File("assets/combat/grassunderground1.png")), 220, 85, 128*RESIZE, 36*RESIZE, null);
				g.drawImage(ImageIO.read(new File("assets/combat/grassunderground2.png")), 10, 180, 128*RESIZE, 36*RESIZE, null);
			}catch(IOException ex){
			}
		//</editor-fold>
		
		//<editor-fold defaultstate="collapsed" desc="Pokemon Sprites Display">
			try{
				String suffix1="";
				String suffix2="";

				if (currentPlayerPokemon.getGender().compareTo("Male")==0 || currentPlayerPokemon.getGender().compareTo("Genderless")==0){
					suffix1=suffix1+"m";
				}else{
					suffix1=suffix1+"f";
				}

				if (currentEnemyPokemon.getGender().compareTo("Male")==0 || currentEnemyPokemon.getGender().compareTo("Genderless")==0){
					suffix2=suffix2+"m";
				}else{
					suffix2=suffix2+"f";
				}

				suffix1 = suffix1+"b";
				suffix2 = suffix2+"f";

				if (currentPlayerPokemon.isShiny()){
					suffix1=suffix1+"s";
				}else{
					suffix1=suffix1+"n";
				}

				if (currentEnemyPokemon.isShiny()){
					suffix2=suffix2+"s";
				}else{
					suffix2=suffix2+"n";
				}

				g.drawImage(ImageIO.read(new File("assets/Pokemon Sprites/"+currentPlayerPokemon.getId()+suffix1+".png")), 64, 64, SPRITE_WIDTH, SPRITE_HEIGHT, null);

				g.drawImage(ImageIO.read(new File("assets/Pokemon Sprites/"+currentEnemyPokemon.getId()+suffix2+".png")), maxX-50-SPRITE_WIDTH, 18, SPRITE_WIDTH, SPRITE_HEIGHT, null);

			}catch(IOException ex){
			}
		//</editor-fold>
		
		try{
			g.drawImage(ImageIO.read(new File("assets/combat/dialogdisplay.png")), 0, maxY-(48*RESIZE), 240*RESIZE, 48*RESIZE, null);
		}catch(IOException ex){
		}
		
		g.setFont(new Font("Arial",0,18));	
		g.drawString(displayTextQueue.get(0), 30, maxY-60);
		
		g.setColor(Color.green);
		g.fillRect(86, 52, (int)((float)100*(float)((float)currentEnemyPokemon.getCurHP()/(float)currentEnemyPokemon.getStatHP())), 10);
		g.fillRect(355, 177, (int)((float)100*(float)((float)currentPlayerPokemon.getCurHP()/(float)currentPlayerPokemon.getStatHP())), 10);
		
		g.setColor(Color.blue);
		g.fillRect(320, 207, (int)((float)140*(float)((float)currentPlayerPokemon.getCurEXP()/(float)currentPlayerPokemon.getMaxEXP())), 10);
		
		try{
			g.drawImage(ImageIO.read(new File("assets/combat/enemydisplay.png")),10,20,100*RESIZE,29*RESIZE,null);
			g.drawImage(ImageIO.read(new File("assets/combat/playerdisplay.png")),maxX-(104*RESIZE)-10,(int)(maxY/2)-15,104*RESIZE,37*RESIZE,null);
		}catch(IOException ex){
		}
		
		//<editor-fold defaultstate="collapsed" desc="UI Display">
			try{
				if (waitingAction){
					if (optionsMoves == -1){
						int uiW = (120*RESIZE), uiH = (48*RESIZE);
						g.drawImage(ImageIO.read(new File("assets/combat/uioptionsdisplay.png")), maxX-uiW, maxY-uiH, uiW, uiH, null);
						
						g.setColor(Color.black);
						g.drawString("FIGHT", maxX-215, maxY-55);
						g.drawString("BAG", maxX-105, maxY-55);
						g.drawString("POKéMON", maxX-215, maxY-20);
						g.drawString("RUN", maxX-105, maxY-20);
						
						g.drawImage(ImageIO.read(new File("assets/combat/arrow.png")), maxX-230+(int)(Math.floor(optionsMain/2)*110), maxY-70+(int)(Math.floor(optionsMain%2)*30), 20, 20, null);
					}else{
						int uiW = (240*RESIZE), uiH = (48*RESIZE);
						g.drawImage(ImageIO.read(new File("assets/combat/uimovesdisplay.png")), maxX-uiW, maxY-uiH, uiW, uiH, null);

						g.drawImage(ImageIO.read(new File("assets/combat/arrow.png")), 10+(int)(Math.floor(optionsMoves/2)*140), maxY-75+(int)(Math.floor(optionsMoves%2)*40), 20, 20, null);
						for (int i = 0; i < 4; i++) {
							String moveName;
							if (i < currentPlayerPokemon.getNumMoves()){
								moveName = currentPlayerPokemon.getMoveSet()[i].getNameDisplay();
								if (moveName == null || moveName.compareTo("")==0){
									moveName = "--";
								}
							}else{
								moveName = "--";
							}
							g.setColor(Color.black);
							g.drawString(moveName, 30+(int)(Math.floor(i/2)*140), maxY-60+(int)(Math.floor(i%2)*40));
						}
						if (optionsMoves<currentPlayerPokemon.getNumMoves()){
							g.setColor(Color.black);
							g.drawString("PP  "+currentPlayerPokemon.getMoveSet()[optionsMoves].getPP()+ " / "+currentPlayerPokemon.getMoveSet()[optionsMoves].getPPMax(), maxX-140, maxY-55);
							g.drawString("TYPE  "+PokemonType.getNameDisplay(currentPlayerPokemon.getMoveSet()[optionsMoves].getType()), maxX-140, maxY-20);
						}

					}
				}
			}catch(IOException ex){
			}
		//</editor-fold>
	}

	public void accept(){
		if (waitingAction){
			if (optionsMoves == -1){
				if (optionsMain == 0){
					optionsMoves = 0;
				}else if (optionsMain == 3){
					this.dispose();
				}
			}else{
				if (optionsMoves<currentPlayerPokemon.getNumMoves()){
					waitingAction = false;
					if (currentPlayerPokemon.getMoveSet()[optionsMoves].getPP()>0){
						doRound();
					}else{
						displayTextQueue.remove(0);
						displayTextQueue.add("There is no PP left for this move!");
					}
				}
			}
		}else{
			displayTextQueue.remove(0);
			if (displayTextQueue.isEmpty()){
				if (!currentEnemyPokemon.isFainted() && !currentPlayerPokemon.isFainted()){
					waitingAction = true;
					displayTextQueue.add("What will "+currentPlayerPokemon.getNameNick()+ " do?");
					optionsMain = 0;
				}else{
					this.dispose();
				}
			}
		}
		refreshDisplayImage();
	}
	
	public void cancel(){
		if (waitingAction){
			if (optionsMoves == -1){
				optionsMain = 3;
			}else{
				optionsMoves = -1;
			}
		}
		
		refreshDisplayImage();
	}
	
	private void doRound(){
		displayTextQueue.remove(0);
		Random rnd = new Random();
		int randomMove = (rnd.nextInt(currentEnemyPokemon.getNumMoves()));
		int turnNum = 0;
		while (turnNum<2 && !currentEnemyPokemon.isFainted() && !currentPlayerPokemon.isFainted()){
			if (currentEnemyPokemon.getStatSpeed() >= currentPlayerPokemon.getStatSpeed()){
				if (turnNum == 0){
					doTurn(currentEnemyPokemon,currentPlayerPokemon,randomMove);
				}else{
					doTurn(currentPlayerPokemon,currentEnemyPokemon,optionsMoves);
				}
				turnNum = turnNum+1;
			}else if (currentEnemyPokemon.getStatSpeed() < currentPlayerPokemon.getStatSpeed()){
				if (turnNum == 1){
					doTurn(currentEnemyPokemon,currentPlayerPokemon,randomMove);
				}else{
					doTurn(currentPlayerPokemon,currentEnemyPokemon,optionsMoves);
				}
				turnNum = turnNum+1;
			}
		}
		optionsMoves = -1;
		if (currentEnemyPokemon.isFainted()){
			displayTextQueue.add(currentEnemyPokemon.getNameNick() +"fainted!");
			displayTextQueue.add(currentPlayerPokemon.getNameNick() +"gained "+currentEnemyPokemon.getExpGain()+" EXP!");
			boolean levelUp = currentPlayerPokemon.setCurEXP(currentPlayerPokemon.getCurEXP()+currentEnemyPokemon.getExpGain());
			if (levelUp){
				displayTextQueue.add(currentPlayerPokemon.getNameNick()+"is now level "+currentPlayerPokemon.getLevel()+"!");
			}
		}
		if (currentPlayerPokemon.isFainted()){
			System.exit(0);
		}
	}
	
	private void doTurn(Pokemon attacker, Pokemon attacked, int moveNum){
		
		displayTextQueue.add(attacker.getNameNick()+" used "+attacker.getMoveSet()[moveNum].getNameDisplay()+"!");
		
		
		float hitChance = (attacker.getMoveSet()[moveNum].getAccuracy()/100)*(attacker.getAccuracy()/attacked.getEvasion());
		
		Random rnd = new Random();
		float roll = (rnd.nextInt(100))/100;
		if (hitChance >= 1 || roll<=hitChance ){
			int[] moveDamage = attacker.getDamage(moveNum, attacked.getTypes());
			attacker.getMoveSet()[moveNum].setPP(attacker.getMoveSet()[moveNum].getPP()-1);
			if(moveDamage[2]==0){
				displayTextQueue.add(attacked.getNameNick()+" is immune!");
			}else if(moveDamage[2]>10){
				displayTextQueue.add("It's super effective!");
			}else if(moveDamage[2]<10){
				displayTextQueue.add("It's not very effective...");
			}
			if (moveDamage[1]==1){
				displayTextQueue.add("A critical hit!");
			}
			attacked.setCurHP(attacked.getCurHP()-moveDamage[0]);
		}else{
			displayTextQueue.add(attacker.getNameNick()+"'s attack missed!");
		}
	}
	
	public void move(String dir){
		if (waitingAction){
			int x,y;
			if (optionsMoves == -1){
				y = (optionsMain%2);
				x = (int)Math.floor(optionsMain/2);
				
			}else{
				y = (optionsMoves%2);
				x = (int)Math.floor(optionsMoves/2);
			}
			switch(dir){
				case "LEFT":
					x = x-1;
				break;
				case "UP":
					y = y-1;
				break;
				case "RIGHT":
					x = x+1;
				break;
				case "DOWN":
					y = y+1;
				break;
			}

			if (x<0){
				x = 1;
			}else if (x>1){
				x = 0;
			}
			
			if (y<0){
				y = 1;
			}else if (y>1){
				y = 0;
			}

			if (optionsMoves == -1){
				optionsMain = (x*2)+y;
			}else{
				optionsMoves = (x*2)+y;
			}
			refreshDisplayImage();
		}
	}
	
	/**
	 * @return the display
	 */
	public BufferedImage getDisplay() {
		return display;
	}
	
	private void dispose(){
		Game.currentBattle = null;
		this.player.setInCombat(false);
	}
	
}
