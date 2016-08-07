/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testamovilla;

import java.util.Random;

/**
 *
 * @author Andres
 */
public class MovimientoPokemon {
    final String nombre;
    final TipoPokemon tipo;
    int PP;
    final int PPMax;
	private final int dañoBase;
	private final int numDados;
	private final int numLados;

  public MovimientoPokemon(String nombre, TipoPokemon tipo, int PP, int PPMax, int dañoBase, int numDados, int numLados) {
	this.nombre = nombre;
	this.tipo = tipo;
	this.PP = PP;
	this.PPMax = PPMax;
	this.dañoBase = dañoBase;
	this.numDados = numDados;
	this.numLados = numLados;
  }
  
  public int calcDaño(){
	int daño = dañoBase;
	//random rnd = new random();
	Random rnd = new Random();
	
	for (int i = 0; i < numDados; i++) {
	  daño = daño + (rnd.nextInt(numLados)+1);
	}
	
	return daño;
  }

  public void setPP(int PP) {
	this.PP = PP;
  }

  public String getNombre() {
	return nombre;
  }

  public TipoPokemon getTipo() {
	return tipo;
  }

  public int getPP() {
	return PP;
  }

  public int getPPMax() {
	return PPMax;
  }
}
