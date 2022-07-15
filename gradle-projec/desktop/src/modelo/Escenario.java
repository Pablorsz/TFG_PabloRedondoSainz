package modelo;

import java.util.ArrayList;


import modelo.persistencia.DAOEscenario;

public class Escenario {

	private static ArrayList<Escenario> escenarios;
	
	private int id;
	private String nombre;
	private String descripcion;
	private ArrayList<Integer> cartasIniciales;
	private CartaImperio cartaImperio;
	
	private int bronce;
	private int plata;
	private int oro;
	
	public Escenario(int id, String nombre, String descripcion, ArrayList<Integer> idCartas, int idImperio,
			int bronce, int plata, int oro) {
		this.id = id;
		this.nombre = nombre;
		this.cartasIniciales = idCartas;
		this.bronce = bronce;
		this.plata = plata;
		this.oro = oro;
		this.descripcion = descripcion;	
		if (idImperio>0) {
			this.cartaImperio = CartaImperio.getCartaImperio(idImperio);
		}
		
	}
	
	public int getId() {
		return id;
	}
	public String getNombre() {
		return nombre;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public ArrayList<Integer> getCartasIniciales() {
		return cartasIniciales;
	}
	public static ArrayList<Escenario> getEscenarios() {
		if (escenarios==null) {
			escenarios = DAOEscenario.cargarEscenarios();;
		}
		return escenarios;
	}
	public CartaImperio getCartaImperio() {
		return cartaImperio;
	}
	
	public int getBronce() {
		return bronce;
	}

	public int getPlata() {
		return plata;
	}

	public int getOro() {
		return oro;
	}

	public static Escenario getEscenario(String numEscenario) {
		return escenarios.get(Integer.parseInt(numEscenario));
	}

}
