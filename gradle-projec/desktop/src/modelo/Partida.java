package modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

import modelo.persistencia.DAOPartida;


public class Partida {
	private int id;
	private int numRonda;
	private Recursos produccion;
	private int generales;
	private int financieros;
	private int krystallium;
	private int progresoKrystallium;
	private static final int CONVERSION_KRYSTALLIUM = 5;
	private int puntuacionFinal;
	
	private LinkedList<CartaDesarrollo> mazo;
	private LinkedList<CartaDesarrollo> descartes;
	private ArrayList<CartaDesarrollo> planificacion;
	private ArrayList<CartaDesarrollo> construccion;
	private LinkedList<CartaDesarrollo> completadas;
	private CartaImperio cartaImperio;
	private Escenario escenario;
	
	public Partida(Escenario escenario, ArrayList<CartaDesarrollo> cartasDesarrollo) {
		this.escenario = escenario;
		
		numRonda = 1; 
		generales = 0;
		financieros = 0;
		krystallium = 0;
		progresoKrystallium = 0;
		mazo = new LinkedList<CartaDesarrollo>();
		//System.out.println("Cartas en el hashmap "+cartasDesarrollo.size());
		for (CartaDesarrollo c : cartasDesarrollo) {
			/*if(entry.getValue().getCosteConstruccion().get(Recurso.FINANCIERO)>0 ||
					entry.getValue().getRecompensa().get(Recurso.FINANCIERO)>0) {
				entry.getValue().setCopias(10);
			}*/
		    for(int copias = 0; copias<c.getCopias(); copias++) {
		    	CartaDesarrollo copiada = new CartaDesarrollo(c);
		    	c.setIdCopia(copias);
		    	mazo.add(copiada);
		    }
		}
		System.out.println("MAZO.SIZE: "+mazo.size());
		Collections.shuffle(mazo);
		Collections.shuffle(mazo);
		descartes = new LinkedList<CartaDesarrollo>();
		planificacion = new ArrayList<CartaDesarrollo>();
		construccion = new ArrayList<CartaDesarrollo>();
		completadas = new LinkedList<CartaDesarrollo>();
		if(escenario.getCartaImperio()!=null) {
			cartaImperio = escenario.getCartaImperio();
			produccion = cartaImperio.getProduccionInicial();
		}
		
	}
	public int getNumRonda() {
		return numRonda;
	}
	public int getProgresoKrystallium() {
		return progresoKrystallium;
	}
	public void setProgresoKrystallium(int progresoKrystallium) {
		this.progresoKrystallium = progresoKrystallium;
	}
	public Recursos getProduccion() {
		return produccion;
	}
	public int getGenerales() {
		return generales;
	}
	public int getFinancieros() {
		return financieros;
	}
	public int getKrystallium() {
		return krystallium;
	}
	public int getPuntuacionFinal() {
		return puntuacionFinal;
	}
	public LinkedList<CartaDesarrollo> getMazo() {
		return mazo;
	}
	public LinkedList<CartaDesarrollo> getDescartes() {
		return descartes;
	}
	public ArrayList<CartaDesarrollo> getPlanificacion() {
		return planificacion;
	}
	public ArrayList<CartaDesarrollo> getConstruccion() {
		return construccion;
	}
	public LinkedList<CartaDesarrollo> getCompletadas() {
		return completadas;
	}
	public CartaImperio getCartaImperio() {
		return cartaImperio;
	}
	public Escenario getEscenario() {
		return escenario;
	}
	public ArrayList<CartaDesarrollo> nuevaPlanificacion() {
		for(int i = 0; i < 5; i++) {
			CartaDesarrollo c = getMazo().remove(0);
			c.setEstado(EstadoCarta.EN_PLANIFICACION);
			planificacion.add(c);
		}
		return planificacion;
	}
	public void robarCartaEscenario(CartaDesarrollo c) {
		getMazo().remove(c);
		c.setEstado(EstadoCarta.EN_CONSTRUCCION);
		getConstruccion().add(c);
		
	}
	public void planificarConstruccion(CartaDesarrollo c) {
		getPlanificacion().remove(c);
		c.setEstado(EstadoCarta.EN_CONSTRUCCION);
		getConstruccion().add(c);
		
	}
	public void reciclarCarta(CartaDesarrollo c) {

		getPlanificacion().remove(c);
		c.setEstado(EstadoCarta.EN_DESCARTE);
		getDescartes().add(c);
		
		
		
	}
	public void descartarCartaEnConstruccion(CartaDesarrollo c) {

		getConstruccion().remove(c);
		c.setEstado(EstadoCarta.EN_DESCARTE);
		getDescartes().add(c);
		conversionKrystallium(1);
		
	}
	

	public Recurso completarCarta(CartaDesarrollo c) {

		getConstruccion().remove(c);
		c.setEstado(EstadoCarta.COMPLETADA);
		getCompletadas().add(c);
		Recurso recompensa = canjearRecompensa(c);
		System.out.println("recompensa canjeada, fgk "+financieros+""+generales+""+krystallium);
		return recompensa;
		
	}
	
	public void descartar(CartaDesarrollo c) {
		getPlanificacion().remove(c);
		c.setEstado(EstadoCarta.EN_DESCARTE);
		getDescartes().add(c);
		
	}
	
	private Recurso canjearRecompensa(CartaDesarrollo c) {
		Recursos recompensa = c.getRecompensa();
		financieros+= recompensa.get(Recurso.FINANCIERO);
		generales+= recompensa.get(Recurso.GENERAL);
		krystallium+= recompensa.get(Recurso.KRYSTALLIUM);
		return c.getRecompensa().recompensa();
		
	}
	public void conversionKrystallium(int i) {
		progresoKrystallium += i;
		int temp = progresoKrystallium/CONVERSION_KRYSTALLIUM;
		krystallium+= temp;
		progresoKrystallium = progresoKrystallium%CONVERSION_KRYSTALLIUM;
		
	}
	
	public void calcularProduccion(LinkedList<CartaDesarrollo> completadas) {
		Recursos iniciales = cartaImperio.getProduccionInicial();
		Recursos p = new Recursos(iniciales);

		int contRecursos;
		for (CartaDesarrollo c : completadas) {
			contRecursos = 0;
			if (c.getMultProduccion()!=null) {
				switch (c.getMultProduccion()) {
				case ESTRUCTURA:
					for (CartaDesarrollo c2 : completadas) {
						if (c2.getTipo()==TipoCarta.ESTRUCTURA) {
							contRecursos++;
						}
					}
					break;
				case VEHICULO:
					for (CartaDesarrollo c2 : completadas) {
						if (c2.getTipo()==TipoCarta.VEHICULO) {
							contRecursos++;
						}
					}
					break;
				case INVESTIGACION:
					for (CartaDesarrollo c2 : completadas) {
						if (c2.getTipo()==TipoCarta.INVESTIGACION) {
							contRecursos++;
						}
					}
					break;
				case PROYECTO:
					for (CartaDesarrollo c2 : completadas) {
						if (c2.getTipo()==TipoCarta.PROYECTO) {
							contRecursos++;
						}
					}
					break;
				case DESCUBRIMIENTO:
					for (CartaDesarrollo c2 : completadas) {
						if (c2.getTipo()==TipoCarta.DESCUBRIMIENTO) {
							contRecursos++;
						}
					}
					break;
					
				}
				Recursos producidosCarta = new Recursos(c.getProduccion());
				
				p.sumarRecursos(producidosCarta.multiplicarRecursos(contRecursos));
			}else {
				Recursos producidosCarta = new Recursos(c.getProduccion());
				p.sumarRecursos(producidosCarta);
			}
			
			
		}
		produccion = p;
	}
	public void nuevaRonda() {
		numRonda++;
		
	}
	
	public int calcularPuntuacion(LinkedList<CartaDesarrollo> completadas) {
		int puntuacion = 0;
		int contador = 0;
		for (CartaDesarrollo carta : completadas) {
			switch (carta.getTipoPuntos()) {
				case ESTRUCTURA:
					contador = 0;
					for (CartaDesarrollo cartaTipo : completadas) {
						if(cartaTipo.getTipo() == TipoCarta.ESTRUCTURA) {
							contador++;
						}
					}
					puntuacion += carta.getPuntos()*contador;
					break;
				case VEHICULO:
					contador = 0;
					for (CartaDesarrollo cartaTipo : completadas) {
						if(cartaTipo.getTipo() == TipoCarta.VEHICULO) {
							contador++;
						}
					}
					puntuacion += carta.getPuntos()*contador;
					break;
				case INVESTIGACION:
					contador = 0;
					for (CartaDesarrollo cartaTipo : completadas) {
						if(cartaTipo.getTipo() == TipoCarta.INVESTIGACION) {
							contador++;
						}
					}
					puntuacion += carta.getPuntos()*contador;
					break;
				case PROYECTO:
					contador = 0;
					for (CartaDesarrollo cartaTipo : completadas) {
						if(cartaTipo.getTipo() == TipoCarta.PROYECTO) {
							contador++;
						}
					}
					puntuacion += carta.getPuntos()*contador;
					break;
				case DESCUBRIMIENTO:
					contador = 0;
					for (CartaDesarrollo cartaTipo : completadas) {
						if(cartaTipo.getTipo() == TipoCarta.DESCUBRIMIENTO) {
							contador++;
						}
					}
					puntuacion += carta.getPuntos()*contador;
					break;
				case FINANCIEROS:
					contador = getFinancieros();
					puntuacion += carta.getPuntos()*contador;
					break;
				case GENERALES:
					contador = getGenerales();
					puntuacion += carta.getPuntos()*contador;
					break;
				case PLANOS:
					puntuacion += carta.getPuntos();
					break;
				default:
					break;
			}

		}
		puntuacionFinal = puntuacion+financieros+generales;
		return puntuacionFinal;
	}
	public void colocarFinanciero() {
		financieros--;
		
	}
	public void colocarGeneral() {
		generales--;
		
	}
	public void colocarKrystallium() {
		krystallium--;
		
	}
	public void recibirFinanciero() {
		financieros++;
		
	}
	public void recibirGeneral() {
		generales++;
		
	}
	public void guardarPartida() {
		int id = DAOPartida.guardarPartida(this);
		this.id = id;
	}
	public int getId() {
		return id;
	}
	public int getIdEscenario() {
		return escenario.getId();
	}
	public void setCartaImperio(CartaImperio c) {
		cartaImperio=c;
		
	}
}
