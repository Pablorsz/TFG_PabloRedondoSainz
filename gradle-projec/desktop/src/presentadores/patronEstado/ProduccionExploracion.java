package presentadores.patronEstado;

import com.badlogic.gdx.math.Vector3;

import modelo.Recurso;
import presentadores.Presentador;

public class ProduccionExploracion extends FasePartida {

	public ProduccionExploracion(Presentador p) {
		super(p);
		textoFase = "Produccion Exploracion";
		if (p.getNumRonda()==p.getMAX_RONDAS()) {
			botonFlujo = "Finalizar partida ";

			this.p.flujoColor(new Vector3(0.3f,1f,0.3f));
			
		}else {
			botonFlujo = "Comenzar nueva\nronda";
			this.p.flujoColor(new Vector3(0.86f,0.62f,0.41f));
			
		}
		estado = EstadoPartida.COLOCANDO_RECURSOS;
		recursoActual = Recurso.EXPLORACION;
	}

	@Override
	public void jugarFase() {
			System.out.println("Se va a producir EXPLORACION");
			p.actualizarProduccion();
			recursosRestantes = p.getProduccion(recursoActual);
			if (p.getRecursosRestantes() >=5) {
				p.getPartida().recibirGeneral();
				p.getVista().actualizarGenerales(p.getPartida().getGenerales());
				
			}
			if (p.getRecursosRestantes() == 0) {
				estado = EstadoPartida.SIN_PRODUCCION;
				
			}else {
				p.getVista().prepararColocacionRecursos();
			}

			actualizarEstado(estado);
		
	}


	@Override
	public void cambiarDeFase() {

		if (recursosRestantes>0) {
			p.conversionKrystallium(recursosRestantes);
		}
		if(p.getNumRonda()<p.getMAX_RONDAS()) {
			actualizarEstado(EstadoPartida.JUGANDO_CARTAS);
			p.cambiarFase(new PrimeraPlanificacion(p));
			p.getPartida().nuevaRonda();
			p.getFase().jugarFase();
			p.limpiarZonaConstruccion();
		}else {
			actualizarEstado(EstadoPartida.FIN);
			p.cambiarFase(new Final(p));
			p.getFase().jugarFase();
		}
	}

}
