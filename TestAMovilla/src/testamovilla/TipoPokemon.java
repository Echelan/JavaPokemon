/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testamovilla;

/**
 *
 * @author Andres
 * ???
 * Acero
 * Agua
 * Bicho
 * Dragón
 * Eléctrico
 * Fantasma
 * Fuego
 * Hielo
 * Lucha
 * Normal
 * Planta
 * Psíquico
 * Roca
 * Siniestro
 * Tierra
 * Veneno
 * Volador
 */
public class TipoPokemon {
    final int id;
    final String nombre;
    final int[] Fortalezas = new int[5];
    final int[] Debilidades = new int[5];
    final int[] Inmunidades = new int[5];

    public TipoPokemon(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }
	
	public void setFortalezas(int[] Fortalezas){
	  for (int i = 0; i < 5; i++) {
		this.Fortalezas[i] = Fortalezas[i];
	  }
	}
	
	public void setDebilidades(int[] Debilidades){
	  for (int i = 0; i < 5; i++) {
		this.Debilidades[i] = Debilidades[i];
	  }
	}
	
	public void setInmunidades(int[] Inmunidades){
	  for (int i = 0; i < 5; i++) {
		this.Inmunidades[i] = Inmunidades[i];
	  }
	}
	
	public int getId() {
	  return id;
	}

	public String getNombre() {
	  return nombre;
	}

	public int[] getFortalezas() {
	  return Fortalezas;
	}

	public int[] getDebilidades() {
	  return Debilidades;
	}

	public int[] getInmunidades() {
	  return Inmunidades;
	}
	
}
