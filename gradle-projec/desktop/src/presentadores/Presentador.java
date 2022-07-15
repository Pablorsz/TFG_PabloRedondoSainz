package presentadores;

import java.util.ArrayList;
import java.util.LinkedList;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import modelo.CartaDesarrollo;
import modelo.CartaImperio;
import modelo.Escenario;
import modelo.EstadoCarta;
import modelo.Partida;
import modelo.Recurso;
import modelo.Recursos;
import presentadores.patronEstado.EstadoPartida;
import presentadores.patronEstado.FasePartida;
import presentadores.patronEstado.Inicio;
import vistas.VistaEscenarios;
import vistas.VistaPartida;
import vistas.WonderfulWorld;

public class Presentador {
	private final int MAX_RONDAS = 4;
	private WonderfulWorld game;
	private Partida partida;
	private VistaPartida vista;
	private FasePartida faseActual;
	
	public Presentador(WonderfulWorld game) {
		this.game = game;
		faseActual = new Inicio(this);
		
	}
	
	public int getMAX_RONDAS() {
		return MAX_RONDAS;
	}

	public ArrayList<Escenario> cargarEscenarios(){
		return Escenario.getEscenarios();
	};
	
	public void cargarLineasRecursos(){
		Recursos.getLineasRecursos();
	};
	public ArrayList<CartaDesarrollo> cargarCartasDesarrollo(){
		return CartaDesarrollo.getCartasDesarrollo();
	};
	public ArrayList<CartaImperio> cargarCartasImperio(){
		return CartaImperio.getCartasImperio();
	};

	public FasePartida getFase() {
		return faseActual;
	}


	public void cambiarFase(FasePartida fase) {
		this.faseActual = fase;
	}


	public EstadoPartida getEstado() {
		return faseActual.getEstado();
	}


	public Recurso getRecursoActual() {
		return faseActual.getRecursoActual();
	}


	public void setRecursoActual(Recurso recurso) {
		faseActual.setRecursoActual(recurso);
	}


	public int getRecursosRestantes() {
		return faseActual.getRecursosRestantes();
	}


	public void setRecursosRestantes(int recursosRestantes) {
		faseActual.setRecursosRestantes(recursosRestantes);
	}

	public VistaPartida getVista() {
		return vista;
	}
	public void setVista(VistaPartida vista) {
		this.vista = vista;
		
	}
	
	public Partida crearPartida(Escenario escenario,ArrayList<CartaDesarrollo> cartasDesarrollo) {

		partida = new Partida(escenario, cartasDesarrollo);
		return partida;
	}

	public void nuevaPlanificacion() {
		vista.nuevaPlanificacion(partida.nuevaPlanificacion());
		
	} 

	public void planificarConstuccion(CartaDesarrollo c) {
		partida.planificarConstruccion(c);
		if(finPlanificacion()) {
			vista.permitirSiguienteFase(true);
		}
		if(getPlanificacion().size()<2) {
			vista.activarDraft(false);
		}
	}

	public void descartar(CartaDesarrollo c) {
		partida.descartar(c);
		if(finPlanificacion()) {
			vista.permitirSiguienteFase(true);
		}
	}

	public void reciclarCarta(CartaDesarrollo c) {
		partida.reciclarCarta(c);

		if(getPlanificacion().size()<2) {
			vista.activarDraft(false);
		}
		//colocarRecursos(c.getReciclaje(),1);
		
	}
	
	public void descartarCartaEnConstruccion(CartaDesarrollo c) {
		partida.descartarCartaEnConstruccion(c);
		vista.actualizarKrystallium(partida.getKrystallium(), partida.getProgresoKrystallium());
		
	}

	public void actualizarProduccion() {
		partida.calcularProduccion(partida.getCompletadas());
		Recursos producidos = partida.getProduccion();
		System.out.println("Producimos los recursos "+producidos.toString());
		
	}


	public void siguienteFase() {
		if(getRecursosRestantes()>0) {
			vista.avisoRecursosRestantes();
		}else {
			faseActual.cambiarDeFase();
			
		}
	}
	

	public void permitirSiguienteFase(boolean permitir) {
			vista.permitirSiguienteFase(permitir);
		
	}
	


	public void finDePartida() {
		boolean instantDefeat = false;
		for  (CartaDesarrollo carta : partida.getConstruccion()) {
			if(carta.getIdCopia()==-1) {
				carta.setColor(0.9f,0.3f,0.3f,1);
				instantDefeat = true;
			}else {
				System.out.println("esta carta deberia desaparecer "+carta.getNombre());
				carta.addAction(Actions.sequence(Actions.fadeOut(1),Actions.removeActor()));
			}
		}
		if(instantDefeat) {
			vista.mostrarDerrota();
			
		}else {
			int puntos = partida.calcularPuntuacion(partida.getCompletadas());
			vista.mostrarFinPartida(puntos);
			System.out.println("Enhorabuena completaste una partida");
			partida.guardarPartida();
		}
		
	}


	public int getNumRonda() {
		return partida.getNumRonda();
	}


	public void prepararZonaConstruccion(ClickListener listener) {	
		for  (CartaDesarrollo carta : partida.getConstruccion()) {
			if(carta.getProgresoConstruccion().get(getRecursoActual())>0) {
				carta.setCabenRecursos(true);
				carta.clearListeners();
				carta.addListener(listener);
				carta.setColor(1f,1f,1f,1);
			}else {
				carta.setCabenRecursos(false);
				carta.setColor(0.6f,0.6f,0.6f,1);
			}
		}
	}


	public void completarCarta(CartaDesarrollo c) {
		Recurso r = partida.completarCarta(c);
		vista.completarCarta(c);
		switch (r) {
			case FINANCIERO:
				vista.actualizarFinancieros(partida.getFinancieros());
				break;
			case GENERAL:
				vista.actualizarGenerales(partida.getGenerales());
				break;
			case KRYSTALLIUM:
				vista.actualizarKrystallium(partida.getKrystallium(), partida.getProgresoKrystallium());
				break;
			default:
				break;
		}		
	}

	public ArrayList<CartaDesarrollo> getConstruccion() {
		return partida.getConstruccion();
	}

	public int getProduccion(Recurso r) {
		return partida.getProduccion().get(r);
	}

	public void colocarRecursos(CartaDesarrollo c) {
		Image recursoImagen;
		if (faseActual.krystalliumActivo()) {
			recursoImagen = vista.crearImagenRecurso(Recurso.KRYSTALLIUM);			
		}else {
			recursoImagen = vista.crearImagenRecurso(getRecursoActual());
			
		}
		
		c.colocarRecurso(getRecursoActual(),recursoImagen);
		recursoColocado();
		if(faseActual.krystalliumActivo()) {
			vista.actualizarKrystallium(partida.getKrystallium(), partida.getProgresoKrystallium());
		}

		if(c.getProgresoConstruccion().terminado()) {
			completarCarta(c);
		}

	}

	private void recursoColocado() {
		faseActual.recursoColocado();
		
	}

	public String getTextoFase() {
		return faseActual.getTextoFase();
	}

	public String getBotonFase() {
		return faseActual.getBotonFlujo();
	}

	public void actualizarInfo() {
		vista.actualizarInfo();
		if (getRecursosRestantes()==0&&getEstado()!=EstadoPartida.JUGANDO_CARTAS) {
			
		}
		
	}

	public void actualizarEstado(EstadoPartida estado) {
		faseActual.actualizarEstado(estado);
		
	}
	
	public String getTextoEstado() {
		return faseActual.getTextoEstado();
	}


	public boolean finPlanificacion() {
		if (faseActual.getEstado()==EstadoPartida.DRAFT) {
			return false;
		}
		return partida.getPlanificacion().isEmpty();
	}


	public void actualizarFase() {
		faseActual.actualizarFase();
		
	}


	public void conversionKrystallium(int recursosRestantes) {
		partida.conversionKrystallium(recursosRestantes);
		vista.actualizarKrystallium(getKrystallium(),partida.getProgresoKrystallium());
		
	}

	public void limpiarZonaConstruccion() {
		for(CartaDesarrollo carta : partida.getConstruccion()) {
			carta.setCabenRecursos(false);
			carta.setColor(1,1,1,1);
		}
	}


	public ArrayList<CartaDesarrollo> getPlanificacion() {
		return partida.getPlanificacion();
	}


	public CartaDesarrollo robar() {

		CartaDesarrollo c = partida.getMazo().remove(0);
		//c.setImagen(new Texture(Gdx.files.internal("Cartas/"+c.getNombre()+".jpg")));
		System.out.println("robada "+c.getNombre());
		return c;
	}


	public void moverAPlanificacion(CartaDesarrollo c) {
		c.setEstado(EstadoCarta.EN_PLANIFICACION);
		partida.getPlanificacion().add(c);
		 
	}


	public Partida getPartida() {
		return partida;
	}


	public int getFinancieros() {
		return partida.getFinancieros();
	}


	public int getGenerales() {
		return partida.getGenerales();
	}


	public int getKrystallium() {
		return partida.getKrystallium();
	}


	public void setKrystalliumActivo(boolean flag) {
		faseActual.setKrystalliumAvtivo(flag);
		
	}


	public void displayPopupKrystallium() {
		vista.displayPopupKrystallium();
		
	}

	public Escenario getEscenario() {
		return partida.getEscenario();
	}
	

	public CartaImperio getCartaImeperio() {
		return partida.getCartaImperio();
	}


	public void robarCartaEscenario(CartaDesarrollo c) {
		partida.robarCartaEscenario(c);
		
	}
	
	private boolean colocandoRecursos() {
		return getEstado()==EstadoPartida.COLOCANDO_RECURSOS;
	}

	public LinkedList<CartaDesarrollo> getCompletadas() {
		return partida.getCompletadas();
	}

	public boolean krystalliumActivo() {
		return faseActual.krystalliumActivo() ;
	}

	public void mostrarOpcionesClicked(InputEvent event) {

		if(getEstado()!=EstadoPartida.JUGANDO_CARTAS) {
			return;
		}
		try {
			CartaDesarrollo c = (CartaDesarrollo)event.getTarget();
			if (c.getEstado()==EstadoCarta.EN_PLANIFICACION) {
				vista.mostrarAcciones(c);
			}else if (c.getEstado()==EstadoCarta.EN_CONSTRUCCION) {
				if(c.getIdCopia()>=0) {
					vista.mostrarDescartar(c);
					
				}else {
					vista.crearLabel(event.getTarget(),"No se puede descartar esta carta porque es necesaria su construcción");
				}
			}
		} catch (Exception e) {
			System.out.println("Click en un botón");
		}
		
	}

	public void colocarKrystalliumClicked(InputEvent event) {
		switch (event.getTarget().getName()) {
		case "MATERIAL":
			if(colocandoRecursos()&&getRecursoActual()==Recurso.MATERIAL) {
				setRecursosRestantes(0);
				displayPopupKrystallium();
			}else {
				setRecursosRestantes(getKrystallium());
				setRecursoActual(Recurso.MATERIAL);
				setKrystalliumActivo(true);
			}
			break;
		case "ENERGIA":
			if(colocandoRecursos()&&getRecursoActual()==Recurso.ENERGIA) {
				setRecursosRestantes(0);
				displayPopupKrystallium();
			}else {
				setRecursosRestantes(getKrystallium());
				setRecursoActual(Recurso.ENERGIA);
				setKrystalliumActivo(true);
			}
			break;
		case "CIENCIA":
			if(colocandoRecursos()&&getRecursoActual()==Recurso.CIENCIA) {
				setRecursosRestantes(0);
				displayPopupKrystallium();
			}else {
				setRecursosRestantes(getKrystallium());
				setRecursoActual(Recurso.CIENCIA);
				setKrystalliumActivo(true);
			}
			break;

		case "ORO":
			if(colocandoRecursos()&&getRecursoActual()==Recurso.ORO) {
				setRecursosRestantes(0);
				displayPopupKrystallium();
			}else {
				setRecursosRestantes(getKrystallium());
				setRecursoActual(Recurso.ORO);
				setKrystalliumActivo(true);
			}
			break;

		case "EXPLORACION":
			if(colocandoRecursos()&&getRecursoActual()==Recurso.EXPLORACION) {
				setRecursosRestantes(0);
				displayPopupKrystallium();
			}else {
				setRecursosRestantes(getKrystallium());
				setRecursoActual(Recurso.EXPLORACION);
				setKrystalliumActivo(true);
			}
			break;

		case "KRYSTALLIUM":
			if(colocandoRecursos()&&getRecursoActual()==Recurso.KRYSTALLIUM) {
				setRecursosRestantes(0);
				displayPopupKrystallium();
			}else {
				setRecursosRestantes(getKrystallium());
				setRecursoActual(Recurso.KRYSTALLIUM);
				setKrystalliumActivo(true);
			}
			break;
		case "KRYSTALLIUMPOPUP":
			if(colocandoRecursos()&&krystalliumActivo()) {
				setRecursosRestantes(0);
				displayPopupKrystallium();
			}
			break;
		default:
			break;
		}
		vista.prepararColocacionRecursos();
		
	}

	public void colocarRecursosClicked(InputEvent event) {
		CartaDesarrollo clicked = ((CartaDesarrollo)event.getTarget());
		if(colocandoRecursos()
				&& clicked.getEstado()==EstadoCarta.EN_CONSTRUCCION
				/*&& clicked.cabenRecursos()*/) {
			if(clicked.cabenRecursos()) {
				if(getRecursosRestantes()>0) {
					colocarRecursos(clicked);
					//vista.setRecursosRestantes(); 
					vista.prepararColocacionRecursos();
				}
			}else {
				vista.crearLabel(event.getTarget(), "¡No se necesita más "+ getRecursoActual()+"!");
			}
		}else {
			System.out.println("click invisible si lo ves cagaste");
		}
		System.out.println("El estado de la partida es:" + getEstado());
	}

	public void financieroListenerClicked(InputEvent event) {
		if(getPartida().getFinancieros()<=0){
			vista.crearLabel(event.getTarget(), "¡No tienes Financieros!");
			return;
		}
		if (colocandoRecursos()) {
			if(getRecursoActual()==Recurso.FINANCIERO) {
				setRecursosRestantes(0);
				vista.prepararColocacionRecursos();
				return;
				
			}
			vista.crearLabel(event.getTarget(), "¡Termina de colocar los recursos!");
			return;
		}
		
		boolean financierosDisponibles =  false;
		for (CartaDesarrollo carta : getConstruccion()) {
			if(carta.getProgresoConstruccion().get(Recurso.FINANCIERO)>0) {
				financierosDisponibles = true;
			}					
		}
		if(!financierosDisponibles) {
			return;
			
		}
		setRecursoActual(Recurso.FINANCIERO);
		setRecursosRestantes(getFinancieros());
		vista.prepararColocacionRecursos();
		
		
	}

	public void colocarGeneralClicked(InputEvent event) {

		
			
		if(getPartida().getGenerales()<=0){
			System.out.println("Consigue primero algun general");
			vista.crearLabel(event.getTarget(), "¡No tienes Generales!");
			return;
		}
		
		if (colocandoRecursos()) {
			if(getRecursoActual()==Recurso.GENERAL) {
				setRecursosRestantes(0);
				vista.prepararColocacionRecursos();
				return;
			}
			vista.crearLabel(event.getTarget(), "¡Termina de colocar los recursos!");
			return;
		}

		boolean generalesDisponibles =  false;
		for (CartaDesarrollo carta : getConstruccion()) {
			if(carta.getProgresoConstruccion().get(Recurso.GENERAL)>0) {
				generalesDisponibles = true;
			}					
		}
		if(!generalesDisponibles) {
			return;
			
		}
		setRecursoActual(Recurso.GENERAL);
		setRecursosRestantes(getGenerales());
		vista.prepararColocacionRecursos();
		
	}

	public void reciclajeClicked(CartaDesarrollo c) {
		System.out.println("estado de la partida: "+getEstado());
		//setEstado(EstadoPartida.COLOCANDO_RECURSOS);
		if (getConstruccion().isEmpty()) {
			vista.crearLabel(c, "Construye una carta primero");
			return;
		}
		if (c.getEstado()==EstadoCarta.EN_PLANIFICACION) {
			reciclarCarta(c);
			//estado.setText("Colocando "+c.getReciclaje());
			//estado.setVisible(true);
			setRecursoActual(c.getReciclaje());
			setRecursosRestantes(1);
			vista.prepararColocacionRecursos();
			
		}else if (c.getEstado()==EstadoCarta.EN_CONSTRUCCION) {
			descartarCartaEnConstruccion(c);
		}
		c.addAction(Actions.sequence(Actions.fadeOut(0.25f),Actions.removeActor()));
		vista.retirarCarta(c);
		
	}

	public void botonDraftClicked(InputEvent event) {
		if(colocandoRecursos()) {
			return;
		}
		if(getEstado()==EstadoPartida.DRAFT) {
			vista.terminarDraft();
			actualizarEstado(EstadoPartida.JUGANDO_CARTAS);
			
		}else {
			actualizarEstado(EstadoPartida.DRAFT);
			vista.prepararDraft();
		}
		
	}

	public void actualizarAJugandoCartas() {
		actualizarEstado(EstadoPartida.JUGANDO_CARTAS);
		
	}

	public void toggleKrystalliumPopup(InputEvent event) {
		if(!colocandoRecursos()) {
			if(getPartida().getKrystallium()<=0){
				vista.crearLabel(event.getTarget(), "¡No tienes Krystallium!");
				return;
			}
			if (colocandoRecursos()) {
				if(getRecursoActual()==Recurso.KRYSTALLIUM) {
					setRecursosRestantes(0);
					vista.prepararColocacionRecursos();
				}
				vista.crearLabel(event.getTarget(), "Debes utilizar primero los recursos");
				System.out.println("Utiliza todos los recursos primero");
				return;
			}
			
			boolean krystalliumDisponibles =  false;
			for (CartaDesarrollo carta : getConstruccion()) {
				if(carta.getProgresoConstruccion().getAny()>0) {
					krystalliumDisponibles = true;
				}					
			}
			if(!krystalliumDisponibles) {
				return;
				
			}

			setRecursosRestantes(getKrystallium());
			setRecursoActual(Recurso.KRYSTALLIUM);
			setKrystalliumActivo(true);
			displayPopupKrystallium();
			}
		
	}

	public void convertirSobrantesEnKrystalliumClicked(InputEvent event) {
		if(krystalliumActivo()) {
			return;
		} else if(colocandoRecursos()){
			conversionKrystallium(getRecursosRestantes());
			setRecursosRestantes(0);
			vista.prepararColocacionRecursos();
			
		}
		
	}
	
	public ArrayList<CartaDesarrollo> getCartasEscenario() {
		ArrayList<CartaDesarrollo> cartasEsenario = new ArrayList<>();
		for (Integer id : getEscenario().getCartasIniciales()) {
			for (CartaDesarrollo c : getPartida().getMazo()) {
				if(id==c.getId()) {
					robarCartaEscenario(c);
					c.setIdCopia(-1);
					cartasEsenario.add(c);
					break;
					
				}
			}
		}
		
		return cartasEsenario;
	}

	public void actualizarAColocandoRecursos() {
		actualizarEstado(EstadoPartida.COLOCANDO_RECURSOS);
		
	}

	public String getRangoObtenido(int puntos) {
		if(puntos>=getEscenario().getBronce()) {
			return "Bronce";
		}if(puntos>=getEscenario().getPlata()) {
			return "Plata";
		}if(puntos>=getEscenario().getOro()) {
			return "Oro";
		}
		return "Novato";
	}

	public void cartaImperioSeleccionada(CartaImperio c) {
		partida.setCartaImperio(c);
		
	}

	public void actualizarListeners() {
		vista.actualizarListeners();
		
	}

	public void mayoriaCientifica() {
		vista.seleccionarMayoria();
		
	}

	public void addFinanciero() {
		partida.recibirFinanciero();
		vista.actualizarFinancieros(getFinancieros());
		
	}

	
	public void addGeneral() {
		partida.recibirGeneral();
		vista.actualizarGenerales(getGenerales());
	}

	public void mostrarEscenarios() {	
		cargarLineasRecursos();
		cargarCartasDesarrollo();
		cargarCartasImperio();
		game.setScreen(new VistaEscenarios(this,cargarEscenarios()));
	
	}
	
	public void comenzarPartida(String e) {
		Escenario escenario = cargarEscenarios().get(Integer.parseInt(e)-1);
		crearPartida(escenario,cargarCartasDesarrollo());
		game.setScreen(new VistaPartida(this));
	}

	public void flujoColor(Vector3 color) {
		vista.pintarFlujo(color);
		
	}

	public void moverInfoPlanificacion() {
		vista.moverInfoPlanificacion();
		
	}

	public void moverInfoProduccion() {
		vista.moverInfoProduccion();
		
	}

	public void actualizarFinancierosLabel() {
		vista.actualizarFinancieros(partida.getFinancieros());
		
	}
	
	public void actualizargeneralesLabel() {
		vista.actualizarGenerales(partida.getFinancieros());
		
	}


}
	

