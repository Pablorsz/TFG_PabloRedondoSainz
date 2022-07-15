package presentadores.patronEstado;

import com.badlogic.gdx.math.Vector3;

import presentadores.Presentador;

public class PrimeraPlanificacion extends FasePartida {
	public PrimeraPlanificacion(Presentador p) {
		super(p);
		textoFase = "Fase de Planificacion 1";
		botonFlujo = "Nueva Planificacion";
		estado = EstadoPartida.JUGANDO_CARTAS;
		this.p.flujoColor(new Vector3(0.86f,0.62f,0.42f));
		this.p.moverInfoPlanificacion();
		
	}

	@Override
	public void jugarFase() {
		p.permitirSiguienteFase(false);
		p.nuevaPlanificacion();
		actualizarEstado(estado);
		
	}
	@Override
	public void cambiarDeFase() {
		p.cambiarFase(new SegundaPlanificacion(p));
		actualizarEstado(estado);
		p.getFase().jugarFase();
			
		
	}

}
