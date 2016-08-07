/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testamovilla;

/**
 *
 * @author Andres
 */
public class Pokemon {
    int id;
	String nombre;
	TipoPokemon tipo1;
	TipoPokemon tipo2;

  public Pokemon(int id, String nombre, TipoPokemon tipo1, TipoPokemon tipo2) {
	this.id = id;
	this.nombre = nombre;
	this.tipo1 = tipo1;
	this.tipo2 = tipo2;
  }
	
}
