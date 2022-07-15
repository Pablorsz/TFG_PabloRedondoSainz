package presentadores.patronEstado;

import modelo.Recurso;
import presentadores.Presentador;

public abstract class FasePartida {
	protected Presentador p;
	protected String textoFase;
	protected String botonFlujo;
	protected String textoEstado;

	protected EstadoPartida estado;
	protected Recurso recursoActual;
	protected int recursosRestantes;
	private boolean flagKystallium;
	
	public FasePartida(Presentador p) {
		this.p = p;
		flagKystallium = false;
	}
	public abstract void jugarFase();
	public void actualizarFase() {
		actualizarEstado(estado);
	};
	public String getTextoFase() {
		return textoFase;
	};
	public  String getBotonFlujo() {
		return botonFlujo;
	};

	public EstadoPartida getEstado() {
		return estado;
	};
	public void setEstado(EstadoPartida estado) {
		this.estado = estado;
	};

	public String getTextoEstado() {
		return textoEstado;
	}
	
	public Recurso getRecursoActual() {
		return recursoActual;
	}
	public void setRecursoActual(Recurso recursoActual) {
		this.recursoActual = recursoActual;
	}
	public int getRecursosRestantes() {
		return recursosRestantes;
	}
	public void setRecursosRestantes(int recursosRestantes) {
		this.recursosRestantes = recursosRestantes;
	}
	public void setTextoFase(String textoFase) {
		this.textoFase = textoFase;
	}
	public void setBotonFlujo(String botonFlujo) {
		this.botonFlujo = botonFlujo;
	}
	public void setTextoEstado(String textoEstado) {
		this.textoEstado = textoEstado;
	}
	public void actualizarEstado(EstadoPartida estadoPartida) {
		estado = estadoPartida;
		switch (estadoPartida) {
			case COLOCANDO_RECURSOS:
				textoEstado ="Colocando "+ p.getRecursoActual()+
						": Quedan "+p.getRecursosRestantes();
				break;
			case JUGANDO_CARTAS:
				textoEstado ="Planificando... ";
				break;
			case DRAFT:
				textoEstado ="Drafteando ";
				break;
			case FIN:
				textoEstado ="Fin ";
				break;
			case ENTRE_PRODUCCIONES:
				textoEstado ="Esperando...";
				break;
			case SIN_PRODUCCION:
				textoEstado ="No se produce "+p.getRecursoActual();
				break;
		}
		p.actualizarInfo();
	}
	public void recursoColocado() {
		switch (recursoActual) {
		case FINANCIERO:
			p.getPartida().colocarFinanciero();
			p.actualizarFinancierosLabel();
			break;
		case GENERAL:
			p.getPartida().colocarGeneral();
			p.actualizargeneralesLabel();
			break;
		default:
			if(flagKystallium) {
				p.getPartida().colocarKrystallium();
				if(p.getPartida().getKrystallium()==0) {
					p.displayPopupKrystallium();
				}
			}
			break;
		}

		p.actualizarInfo();
		recursosRestantes--;
		
		
	}
	public abstract void cambiarDeFase();
	public void setKrystalliumAvtivo(boolean flagKrystallium) {
		this.flagKystallium = flagKrystallium;
	}
	
	public boolean krystalliumActivo() {
		return flagKystallium;
	}
}
