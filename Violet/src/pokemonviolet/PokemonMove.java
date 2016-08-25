/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokemonviolet;

import java.util.Random;

/**
 *
 * @author Andres
 */
public class PokemonMove {
        static final private int NUMATTRIB = 13;

        private final int id;
        private String nameDisplay;
        private String nameInternal;
        private PokemonType type;
        private String description;
        private String category;	
        private int PP;
        private int PPMax;
        private int dmgBase;
        private int numDice;
        private int numSides;
        private int accuracy;
        private String flags;

        public PokemonMove(int id){
            this.id = id;

            boolean couldCreate = readInfo( this.id );

            if (!couldCreate){
                    System.err.println("Could not find Move with id " + id + ".");
            }
        }

        public PokemonMove(String nameInternal){
            this.nameInternal = nameInternal;
            this.id = getMoveID(nameInternal);

            boolean couldCreate = readInfo( this.id );

            if (!couldCreate){
                    System.err.println("Could not find Move with id " + id + ".");
            }
        }
  
        private boolean readInfo(int id){
                boolean success = false;

                String[] iteminfo = Game.INFOMOVES.get(id-1).split(";");
                for (int i = 0; i < NUMATTRIB; i++){
                        String[] partes = iteminfo[i].split("=");
                        if (partes[0].compareTo("internalName")==0){
                                this.nameInternal = partes[1];
                        }else if (partes[0].compareTo("displayName")==0){
                                this.nameDisplay = partes[1];
                        }else if (partes[0].compareTo("basePower")==0){
                                this.dmgBase = Integer.parseInt(partes[1]);
                        }else if (partes[0].compareTo("code")==0){
                //		this.dmgBase = Integer.parseInt(partes[1]);
                        }else if (partes[0].compareTo("type")==0){
                //		this.dmgBase = Integer.parseInt(partes[1]);
                        }else if (partes[0].compareTo("category")==0){
                                this.category = partes[1];
                        }else if (partes[0].compareTo("description")==0){
                                this.description = partes[1];
                        }else if (partes[0].compareTo("accuracy")==0){
                                this.accuracy = Integer.parseInt(partes[1]);
                        }else if (partes[0].compareTo("totalPP")==0){
                                this.PPMax = Integer.parseInt(partes[1]);
                        }else if (partes[0].compareTo("effectChance")==0){
                //		this.dmgBase = Integer.parseInt(partes[1]);
                        }else if (partes[0].compareTo("target")==0){
                //		this.dmgBase = Integer.parseInt(partes[1]);
                        }else if (partes[0].compareTo("priority")==0){
                //		this.dmgBase = Integer.parseInt(partes[1]);
                        }else if (partes[0].compareTo("flags")==0){
                                this.flags = partes[1];
                        }
                }
                success = true;

                return success;
        }

	private int getMoveID(String internalName){
            int id = 0;
            boolean foundMove = false;
            System.out.println("Searching for " + internalName);
            while (foundMove == false && id < Game.INFOMOVES.size()){
                String[] moveinfo = Game.INFOMOVES.get(id).split(";");
                int attribComp = 0;
                while (attribComp < NUMATTRIB && foundMove == false){
                        String[] partes = moveinfo[attribComp].split("=");
                        if (partes[0].compareTo("internalName")==0){
                            if (partes[1].compareTo(internalName)==0){
                                foundMove = true;
                            }else{
                                attribComp = attribComp + 100;
                            }
                        }else{
                            attribComp = attribComp + 1;
                        }
                }
                id = id + 1;
            }
            if (!foundMove){
                System.err.println("Could not find move " + internalName + ".");
            }


            return id;
	}
  
	public int calcDaño(){
	  int daño = dmgBase;
	  //random rnd = new random();
	  Random rnd = new Random();

	  for (int i = 0; i < numDice; i++) {
		daño = daño + (rnd.nextInt(numSides)+1);
	  }

	  return daño;
	}

	public int getId() {
		return id;
	}

	public String getNameDisplay() {
		return nameDisplay;
	}

	public String getNameInternal() {
		return nameInternal;
	}

	public PokemonType getType() {
		return type;
	}

	public String getDescription() {
		return description;
	}

	public String getCategory() {
		return category;
	}

	public int getPP() {
		return PP;
	}

	public void setPP(int PP) {
		this.PP = PP;
	}

	public int getPPMax() {
		return PPMax;
	}

	public void setPPMax(int PPMax) {
		this.PPMax = PPMax;
	}
  
  
}
