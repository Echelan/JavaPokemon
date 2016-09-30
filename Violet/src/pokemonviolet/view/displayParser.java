/*
 *  Pokemon Violet - A University Project by Andres Movilla
 *  Pokemon COPYRIGHT 2002-2016 Pokemon.
 *  COPYRIGHT 1995-2016 Nintendo/Creatures Inc./GAME FREAK inc. TRADEMARK, REGISTERED TRADEMARK
 *  and Pokemon character names are trademarks of Nintendo.
 *  No copyright or trademark infringement is intended in using Pokemon content on Pokemon Violet.
 */
package pokemonviolet.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Andres
 */
public abstract class displayParser {
	
	public static int curMapX, curMapY;
	private static final int COMBAT_RESIZE = 2;
	private static final int SPRITE_WIDTH = 80*COMBAT_RESIZE;
	private static final int SPRITE_HEIGHT = 80*COMBAT_RESIZE;
	public static final int FINAL_PLAYER_X = 10, FINAL_ENEMY_X = 220;
	public static int CUR_PLAYER_X = FINAL_PLAYER_X+500, CUR_ENEMY_X = FINAL_ENEMY_X-500;
	
	public static BufferedImage displayImage(){
		int ssX=pokemonviolet.model.Game.SCREEN_SIZE_X, ssY=pokemonviolet.model.Game.SCREEN_SIZE_Y;
		
		BufferedImage display = new BufferedImage( ssX, ssY, BufferedImage.TYPE_INT_RGB);
		Graphics g = display.getGraphics();
		
		String currentState=pokemonviolet.model.Game.gameState.get(pokemonviolet.model.Game.gameState.size()-1);
		
		if (currentState.compareTo("GAME")==0){
			
			int allW = pokemonviolet.model.Map.MAP_TOTAL_SIZE_X*3, allH = pokemonviolet.model.Map.MAP_TOTAL_SIZE_Y*3;
			g.drawImage(gameAllMaps(), curMapX, curMapY, allW, allH, null);
			g.drawImage(pokemonviolet.model.Game.player.getCurFrameImage(), ssX/2, ssY/2,(int)(pokemonviolet.model.Game.player.SPRITE_X*pokemonviolet.model.Game.player.SPRITE_RESIZE),(int)(pokemonviolet.model.Game.player.SPRITE_Y*pokemonviolet.model.Game.player.SPRITE_RESIZE), null);
			
		}else if(currentState.compareTo("COMBAT")==0){
			
			g.drawImage(combatDisplayImage(),0,0,ssX, ssY,null);
			
		}else if(currentState.compareTo("TITLE")==0){
			
		}
		
		return display;
	}
	
	private static BufferedImage gameAllMaps(){
		int allW = pokemonviolet.model.Map.MAP_TOTAL_SIZE_X*3, allH = pokemonviolet.model.Map.MAP_TOTAL_SIZE_Y*3;
		BufferedImage tempStitched = new BufferedImage( allW, allH, BufferedImage.TYPE_INT_RGB);
		Graphics g = tempStitched.getGraphics();
		
		for (int i = 0; i < pokemonviolet.model.Game.displayedMaps.length; i++) {
			for (int j = 0; j < pokemonviolet.model.Game.displayedMaps[i].length; j++) {
				g.drawImage( pokemonviolet.model.Game.displayedMaps[i][j].getImage(), (int)(i*pokemonviolet.model.Map.MAP_TOTAL_SIZE_X), (int)(j*pokemonviolet.model.Map.MAP_TOTAL_SIZE_Y),(int)(pokemonviolet.model.Map.MAP_TOTAL_SIZE_X), (int)(pokemonviolet.model.Map.MAP_TOTAL_SIZE_Y), null);
			}
		}
		
		return tempStitched;
	}
		
	public static BufferedImage combatDisplayImage(){
		int ssX=pokemonviolet.model.Game.SCREEN_SIZE_X, ssY=pokemonviolet.model.Game.SCREEN_SIZE_Y;
		BufferedImage display = new BufferedImage( ssX, ssY, BufferedImage.TYPE_INT_RGB);
		Graphics g = display.getGraphics();
		
		//<editor-fold defaultstate="collapsed" desc="Background">
			try{
				g.drawImage(ImageIO.read(new File("assets/combat/background.png")), 0, 0, ssX, ssY, null);
			}catch(IOException ex){
			}

			try{
				g.drawImage(ImageIO.read(new File("assets/combat/grassunderground1.png")), CUR_ENEMY_X, 85, 128*COMBAT_RESIZE, 36*COMBAT_RESIZE, null);
				g.drawImage(ImageIO.read(new File("assets/combat/grassunderground2.png")), CUR_PLAYER_X, 180, 128*COMBAT_RESIZE, 36*COMBAT_RESIZE, null);
			}catch(IOException ex){
			}
		//</editor-fold>
		
		int displayHealthEnemy = pokemonviolet.model.Game.currentBattle.getDisplayHealthEnemy();
		int displayHealthPlayer = pokemonviolet.model.Game.currentBattle.getDisplayHealthPlayer();
		int displayExpPlayer = pokemonviolet.model.Game.currentBattle.getDisplayExpPlayer();
		int curCaught = pokemonviolet.model.Game.currentBattle.getCaught()[pokemonviolet.model.Game.currentBattle.getEnemy().getCurrentPokemon()];
				
		//<editor-fold defaultstate="collapsed" desc="Pokemon Sprites Display">
			try{
				if (displayHealthEnemy != 0 && curCaught!=1){
					String suffix2="";

					if (pokemonviolet.model.Game.currentBattle.getCurrentEnemyPokemon().getGender().compareTo("Male")==0 || pokemonviolet.model.Game.currentBattle.getCurrentEnemyPokemon().getGender().compareTo("Genderless")==0){
						suffix2=suffix2+"m";
					}else{
						suffix2=suffix2+"f";
					}
					suffix2 = suffix2+"f";
					if (pokemonviolet.model.Game.currentBattle.getCurrentEnemyPokemon().isShiny()){
						suffix2=suffix2+"s";
					}else{
						suffix2=suffix2+"n";
					}

					g.drawImage(ImageIO.read(new File("assets/Pokemon Sprites/"+pokemonviolet.model.Game.currentBattle.getCurrentEnemyPokemon().getId()+suffix2+".png")), CUR_ENEMY_X+50, 0, SPRITE_WIDTH, SPRITE_HEIGHT, null);
				}else if (curCaught==1){
					int x = (CUR_ENEMY_X+50+(SPRITE_WIDTH/2)+20-(14*COMBAT_RESIZE)), y = (18+SPRITE_HEIGHT-50-(14*COMBAT_RESIZE));
					g.drawImage(ImageIO.read(new File("assets/combat/pokeballactive.png")).getSubimage(0, 0, 14, 14), x,y, 14*COMBAT_RESIZE, 14*COMBAT_RESIZE, null);
				}
				if (displayHealthPlayer != 0){
					String suffix1="";
					if (pokemonviolet.model.Game.currentBattle.getCurrentPlayerPokemon().getGender().compareTo("Male")==0 || pokemonviolet.model.Game.currentBattle.getCurrentPlayerPokemon().getGender().compareTo("Genderless")==0){
						suffix1=suffix1+"m";
					}else{
						suffix1=suffix1+"f";
					}

					suffix1 = suffix1+"b";

					if (pokemonviolet.model.Game.currentBattle.getCurrentPlayerPokemon().isShiny()){
						suffix1=suffix1+"s";
					}else{
						suffix1=suffix1+"n";
					}

					g.drawImage(ImageIO.read(new File("assets/Pokemon Sprites/"+pokemonviolet.model.Game.currentBattle.getCurrentPlayerPokemon().getId()+suffix1+".png")), CUR_PLAYER_X+54, 64, SPRITE_WIDTH, SPRITE_HEIGHT, null);
				}
			}catch(IOException ex){
			}
			
			try{
				g.drawImage(ImageIO.read(new File("assets/combat/dialogdisplay.png")), 0, ssY-(48*COMBAT_RESIZE), 240*COMBAT_RESIZE, 48*COMBAT_RESIZE, null);
			}catch(IOException ex){
			}

		//</editor-fold>
		
		if (CUR_ENEMY_X != FINAL_ENEMY_X || CUR_PLAYER_X != FINAL_PLAYER_X){
			if (CUR_ENEMY_X != FINAL_ENEMY_X){
				CUR_ENEMY_X = CUR_ENEMY_X+20;
			}
			
			if (CUR_PLAYER_X != FINAL_PLAYER_X){
				CUR_PLAYER_X = CUR_PLAYER_X-20;
			}
			
			if (CUR_ENEMY_X == FINAL_ENEMY_X && CUR_PLAYER_X == FINAL_PLAYER_X){
				pokemonviolet.model.Game.currentBattle.setReady(true);
			}
		}else{
			g.setFont(new Font("Arial",0,18));
			if (pokemonviolet.model.Game.currentBattle.getDisplayTextQueue().size()>0){
				g.drawString(pokemonviolet.model.Game.currentBattle.getDisplayTextQueue().get(0), 30, ssY-60);
			}

			//<editor-fold defaultstate="collapse" desc="Health & Experience Display">
				if (!pokemonviolet.model.Game.currentBattle.isDoneHealthEnemy()){
					int delta = Math.abs(displayHealthEnemy - pokemonviolet.model.Game.currentBattle.getCurrentEnemyPokemon().getCurHP());
					delta = (int)Math.ceil((float)delta/(float)20);
					if (displayHealthEnemy < pokemonviolet.model.Game.currentBattle.getCurrentEnemyPokemon().getCurHP()){
						pokemonviolet.model.Game.currentBattle.setDisplayHealthEnemy(displayHealthEnemy+delta);
					}else if (displayHealthEnemy > pokemonviolet.model.Game.currentBattle.getCurrentEnemyPokemon().getCurHP()){
						pokemonviolet.model.Game.currentBattle.setDisplayHealthEnemy(displayHealthEnemy-delta);
					}else{
						pokemonviolet.model.Game.currentBattle.setDoneHealthEnemy(true);
					}
				}

				if (!pokemonviolet.model.Game.currentBattle.isDoneHealthPlayer()){
					int delta = Math.abs(displayHealthPlayer - pokemonviolet.model.Game.currentBattle.getCurrentPlayerPokemon().getCurHP());
					delta = (int)Math.ceil((float)delta/(float)20);
					if (displayHealthPlayer < pokemonviolet.model.Game.currentBattle.getCurrentPlayerPokemon().getCurHP()){
						pokemonviolet.model.Game.currentBattle.setDisplayHealthPlayer(displayHealthPlayer+delta);
					}else if (displayHealthPlayer > pokemonviolet.model.Game.currentBattle.getCurrentPlayerPokemon().getCurHP()){
						pokemonviolet.model.Game.currentBattle.setDisplayHealthPlayer(displayHealthPlayer-delta);
					}else{
						pokemonviolet.model.Game.currentBattle.setDoneHealthPlayer(true);
					}
				}

				if (!pokemonviolet.model.Game.currentBattle.isDoneExpPlayer()){
					int delta = Math.abs(displayExpPlayer - pokemonviolet.model.Game.currentBattle.getCurrentPlayerPokemon().getCurEXP());
					delta = (int)Math.ceil((float)delta/5);
					if (displayExpPlayer < pokemonviolet.model.Game.currentBattle.getCurrentPlayerPokemon().getCurEXP()){
						pokemonviolet.model.Game.currentBattle.setDisplayExpPlayer(displayExpPlayer+delta);
					}else if (displayExpPlayer > pokemonviolet.model.Game.currentBattle.getCurrentPlayerPokemon().getCurEXP()){
						pokemonviolet.model.Game.currentBattle.setDisplayExpPlayer(0);
					}else{
						pokemonviolet.model.Game.currentBattle.setDoneExpPlayer(true);
					}
				}

				g.setColor(Color.green);
				if ( (float)(float)displayHealthEnemy/(float)pokemonviolet.model.Game.currentBattle.getCurrentEnemyPokemon().getStatHP() < (float)0.5){
					g.setColor(Color.orange);
					if ( (float)(float)displayHealthEnemy/(float)pokemonviolet.model.Game.currentBattle.getCurrentEnemyPokemon().getStatHP() < (float)0.25){
						g.setColor(Color.red);
					}
				}
				g.fillRect(86, 52, (int)((float)100*(float)((float)displayHealthEnemy/(float)pokemonviolet.model.Game.currentBattle.getCurrentEnemyPokemon().getStatHP())), 10);

				g.setColor(Color.green);
				if ( (float)(float)displayHealthPlayer/(float)pokemonviolet.model.Game.currentBattle.getCurrentPlayerPokemon().getStatHP() < (float)0.5){
					g.setColor(Color.orange);
					if ( (float)(float)displayHealthPlayer/(float)pokemonviolet.model.Game.currentBattle.getCurrentPlayerPokemon().getStatHP() < (float)0.25){
						g.setColor(Color.red);
					}
				}
				g.fillRect(355, 177, (int)((float)100*(float)((float)displayHealthPlayer/(float)pokemonviolet.model.Game.currentBattle.getCurrentPlayerPokemon().getStatHP())), 10);

				g.setColor(Color.blue);
				g.fillRect(320, 207, (int)((float)140*(float)((float)displayExpPlayer/(float)pokemonviolet.model.Game.currentBattle.getCurrentPlayerPokemon().getMaxEXP())), 10);
			//</editor-fold>

			//<editor-fold defaultstate="collapsed" desc="Pokeballs Display">
				try{
					BufferedImage allBalls = ImageIO.read(new File("assets/combat/pokeballui.png"));
					int dimX=9, dimY=9;
					int x,y, id;

					y = 3;
					for (int i = 0; i < 6; i++) {
					x = 12+(i*dimX*COMBAT_RESIZE);
						if (i<pokemonviolet.model.Game.currentBattle.getEnemy().getNumPokemonTeam()){
							if (pokemonviolet.model.Game.currentBattle.getEnemy().getTeam()[i].isFainted()){
								id = 2;
							}else{
								id = 1;
							}
						}else{
							id = 0;
						}
						g.drawImage(allBalls.getSubimage(id*9, 0, dimX, dimY),x,y,dimX*COMBAT_RESIZE,dimY*COMBAT_RESIZE,null);
					}


					y = (int)(ssY/2)-33;
					for (int i = 0; i < 6; i++) {
						x = ssX-(104*COMBAT_RESIZE)+75+(i*dimX*COMBAT_RESIZE);
						if (i<pokemonviolet.model.Game.currentBattle.getPlayer().getNumPokemonTeam()){
							if (pokemonviolet.model.Game.currentBattle.getPlayer().getTeam()[i].isFainted()){
								id = 2;
							}else{
								id = 1;
							}
						}else{
							id = 0;
						}
						g.drawImage(allBalls.getSubimage(id*9, 0, dimX, dimY),x,y,dimX*COMBAT_RESIZE,dimY*COMBAT_RESIZE,null);
					}
				}catch(IOException ex){

				}
			//</editor-fold>

			try{
				g.drawImage(ImageIO.read(new File("assets/combat/enemydisplay.png")),10,20,100*COMBAT_RESIZE,29*COMBAT_RESIZE,null);
				g.drawImage(ImageIO.read(new File("assets/combat/playerdisplay.png")),ssX-(104*COMBAT_RESIZE)-10,(int)(ssY/2)-15,104*COMBAT_RESIZE,37*COMBAT_RESIZE,null);
			}catch(IOException ex){
			}

			g.setColor(Color.black);
			g.drawString(pokemonviolet.model.Game.currentBattle.getCurrentEnemyPokemon().getNameNick(), 25,45);
			g.drawString("Lv: "+pokemonviolet.model.Game.currentBattle.getCurrentEnemyPokemon().getLevel(), 140, 45);

			g.drawString(pokemonviolet.model.Game.currentBattle.getCurrentPlayerPokemon().getNameNick(), ssX-(104*COMBAT_RESIZE)+20,(int)(ssY/2)+10);
			g.drawString("Lv: "+pokemonviolet.model.Game.currentBattle.getCurrentPlayerPokemon().getLevel(), ssX-(104*COMBAT_RESIZE)+130,(int)(ssY/2)+10);

			g.drawString(displayHealthPlayer+"/"+pokemonviolet.model.Game.currentBattle.getCurrentPlayerPokemon().getStatHP(), ssX-(104*COMBAT_RESIZE)+125,(int)(ssY/2)+45);


			//<editor-fold defaultstate="collapsed" desc="UI Display">
				try{
					if (pokemonviolet.model.Game.currentBattle.isWaitingAction()){
						if (pokemonviolet.model.Game.currentBattle.getCurrentMenu().compareTo("MAIN")==0){
							//<editor-fold defaultstate="collapsed" desc="Main UI Display">
								int uiW = (120*COMBAT_RESIZE), uiH = (48*COMBAT_RESIZE);
								g.drawImage(ImageIO.read(new File("assets/combat/uioptionsdisplay.png")), ssX-uiW, ssY-uiH, uiW, uiH, null);

								g.setColor(Color.black);
								g.drawString("FIGHT", ssX-215, ssY-55);
								g.drawString("POKéBALL", ssX-105, ssY-55);
								g.drawString("POKéMON", ssX-215, ssY-20);
								g.drawString("RUN", ssX-105, ssY-20);

								g.drawImage(ImageIO.read(new File("assets/combat/arrow.png")), ssX-230+(int)(Math.floor(pokemonviolet.model.Game.currentBattle.getOptionsMain()%2)*110), ssY-70+(int)(Math.floor(pokemonviolet.model.Game.currentBattle.getOptionsMain()/2)*30), 20, 20, null);
							//</editor-fold>
						}else if (pokemonviolet.model.Game.currentBattle.getCurrentMenu().compareTo("MOVES")==0){
							//<editor-fold defaultstate="collapsed" desc="Moves UI Display">
								int uiW = (240*COMBAT_RESIZE), uiH = (48*COMBAT_RESIZE);
								g.drawImage(ImageIO.read(new File("assets/combat/uimovesdisplay.png")), ssX-uiW, ssY-uiH, uiW, uiH, null);

								g.drawImage(ImageIO.read(new File("assets/combat/arrow.png")), 10+(int)(Math.floor(pokemonviolet.model.Game.currentBattle.getOptionsMoves()%2)*140), ssY-75+(int)(Math.floor(pokemonviolet.model.Game.currentBattle.getOptionsMoves()/2)*40), 20, 20, null);
								for (int i = 0; i < 4; i++) {
									String moveName;
									if (i < pokemonviolet.model.Game.currentBattle.getCurrentPlayerPokemon().getNumMoves()){
										moveName = pokemonviolet.model.Game.currentBattle.getCurrentPlayerPokemon().getMoveSet()[i].getNameDisplay();
										if (moveName == null || moveName.compareTo("")==0){
											moveName = "--";
										}
									}else{
										moveName = "--";
									}
									g.setColor(Color.black);
									g.drawString(moveName, 30+(int)(Math.floor(i%2)*140), ssY-60+(int)(Math.floor(i/2)*40));
								}
								if (pokemonviolet.model.Game.currentBattle.getOptionsMoves()<pokemonviolet.model.Game.currentBattle.getCurrentPlayerPokemon().getNumMoves()){
									g.setColor(Color.black);
									g.drawString("PP  "+pokemonviolet.model.Game.currentBattle.getCurrentPlayerPokemon().getMoveSet()[pokemonviolet.model.Game.currentBattle.getOptionsMoves()].getPP()+ " / "+pokemonviolet.model.Game.currentBattle.getCurrentPlayerPokemon().getMoveSet()[pokemonviolet.model.Game.currentBattle.getOptionsMoves()].getPPMax(), ssX-140, ssY-55);
									g.drawString("TYPE  "+pokemonviolet.model.PokemonType.getNameDisplay(pokemonviolet.model.Game.currentBattle.getCurrentPlayerPokemon().getMoveSet()[pokemonviolet.model.Game.currentBattle.getOptionsMoves()].getType()), ssX-140, ssY-20);
								}
							//</editor-fold>
						}else if (pokemonviolet.model.Game.currentBattle.getCurrentMenu().compareTo("BALLS")==0){
							//<editor-fold defaultstate="collapsed" desc="Balls UI Display">
								int uiW = (240*COMBAT_RESIZE), uiH = (48*COMBAT_RESIZE);
								g.drawImage(ImageIO.read(new File("assets/combat/uimovesdisplay.png")), ssX-uiW, ssY-uiH, uiW, uiH, null);

								g.drawImage(ImageIO.read(new File("assets/combat/arrow.png")), 10+(int)(Math.floor(pokemonviolet.model.Game.currentBattle.getOptionsBalls()%2)*140), ssY-75+(int)(Math.floor(pokemonviolet.model.Game.currentBattle.getOptionsBalls()/2)*40), 20, 20, null);

								String[] pokeTypes = {"POKEBALL","GREATBALL","ULTRABALL","PREMIERBALL","MASTERBALL"};
								String[] order = new String[4];
								int counter = 0;
								int numBalls = 0;
								for (int i = 0; i < pokeTypes.length; i++) {
									if (numBalls<4){
										boolean foundOne = false;
										String option="--";
										while (counter<5 && !foundOne){
											if (pokemonviolet.model.Game.currentBattle.getPlayer().getAmountItem(pokeTypes[counter])>0){
												option = new pokemonviolet.model.Item(pokeTypes[counter]).getNameSingular();
												order[numBalls] = pokeTypes[counter];
												foundOne = true;
											}
											counter=counter+1;
										}

										if (option.compareTo("--")!=0){
											g.setColor(Color.black);
											g.drawString(option, 30+(int)(Math.floor(numBalls%2)*140), ssY-60+(int)(Math.floor(numBalls/2)*40));
											numBalls = numBalls+1;
										}else{

										}
									}
								}

								if (pokemonviolet.model.Game.currentBattle.getOptionsBalls()<numBalls){
									g.setColor(Color.black);
									g.drawString("x "+pokemonviolet.model.Game.currentBattle.getPlayer().getAmountItem(order[pokemonviolet.model.Game.currentBattle.getOptionsBalls()]), ssX-100, ssY-40);
								}

								for (int i = numBalls; i < 4; i++) {
									g.setColor(Color.black);
									g.drawString("--", 30+(int)(Math.floor(numBalls%2)*140), ssY-60+(int)(Math.floor(numBalls/2)*40));
									numBalls = numBalls+1;
								}

							//</editor-fold>
						}else if (pokemonviolet.model.Game.currentBattle.getCurrentMenu().compareTo("POKEMON")==0 || pokemonviolet.model.Game.currentBattle.getCurrentMenu().compareTo("POKEMONF")==0){
							//<editor-fold defaultstate="collapsed" desc="Pokemon UI Display">
								int uiW = (110*COMBAT_RESIZE), uiH = (110*COMBAT_RESIZE);	
								g.drawImage(ImageIO.read(new File("assets/combat/extrabackground.png")), (ssX/2)-(uiW/2), (ssY/2)-(uiH/2), uiW, uiH, null);

								for (int i = 0; i < pokemonviolet.model.Game.currentBattle.getPlayer().getNumPokemonTeam(); i++) {
									g.drawString(pokemonviolet.model.Game.currentBattle.getPlayer().getTeam()[i].getNameNick(), (ssX/2)-(uiW/2)+30, (ssY/2)-(uiH/2)+40+(i*30));
								}
								for (int i = pokemonviolet.model.Game.currentBattle.getPlayer().getNumPokemonTeam(); i < 6; i++) {
									g.drawString("--", (ssX/2)-(uiW/2)+30, (ssY/2)-(uiH/2)+40+(i*30));
								}
								g.drawImage(ImageIO.read(new File("assets/combat/arrow.png")), (ssX/2)-(uiW/2)+15, (ssY/2)-(uiH/2)-5+((pokemonviolet.model.Game.currentBattle.getOptionsPokemon()+1)*30), 20, 20, null);

							//</editor-fold>
						}
					}
				}catch(IOException ex){
				}
			//</editor-fold>
		}
		return display;
	}

}
