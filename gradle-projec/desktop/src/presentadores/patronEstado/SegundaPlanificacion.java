package presentadores.patronEstado;

import com.badlogic.gdx.math.Vector3;

import presentadores.Presentador;

public class SegundaPlanificacion extends FasePartida {
	public SegundaPlanificacion(Presentador p) {
		super(p);
		textoFase = "Fase de Planificacion 2";
		botonFlujo = "Comenzar Produccion: \n Producir Material";
		estado = EstadoPartida.JUGANDO_CARTAS;
		this.p.flujoColor(new Vector3(1,1,1));
		
	}

	@Override
	public void jugarFase() {
		p.permitirSiguienteFase(false);
		p.nuevaPlanificacion();
		actualizarEstado(estado);
		
	}
	@Override
	public void cambiarDeFase() {
		p.cambiarFase(new ProduccionMaterial(p));
		actualizarEstado(estado);
		p.limpiarZonaConstruccion();
		p.getFase().jugarFase();
			
		
	}

}
