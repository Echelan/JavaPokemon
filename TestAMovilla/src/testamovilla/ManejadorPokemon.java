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
public class ManejadorPokemon {
  
  final TipoPokemon[] listaTipos = new TipoPokemon[18];
  final Pokemon[] listaPokemon = new Pokemon[151];
  final MovimientoPokemon[] listaMovimiento = new MovimientoPokemon[18];
  
  public ManejadorPokemon() {
	
	String[] nombres = {
	  "???","Acero","Agua","Bicho","Dragón","Eléctrico","Fantasma","Fuego","Hielo","Lucha","Normal","Planta",
	  "Psíquico","Roca","Siniestro","Tierra","Veneno","Volador"
	};
	
	for (int i = 0; i < nombres.length; i++) {
	  listaTipos[i] = new TipoPokemon(i,nombres[i]);
	}
	
  }
  
}
