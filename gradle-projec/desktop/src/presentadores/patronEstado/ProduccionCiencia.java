package presentadores.patronEstado;

import com.badlogic.gdx.math.Vector3;

import modelo.Recurso;
import presentadores.Presentador;

public class ProduccionCiencia extends FasePartida {

	public ProduccionCiencia(Presentador p) {
		super(p);
		textoFase = "Produccion Ciencia";
		botonFlujo = "Producir Oro";
		estado = EstadoPartida.COLOCANDO_RECURSOS;
		recursoActual = Recurso.CIENCIA;
		this.p.flujoColor(new Vector3(1f,1f,0f));
	}

	@Override
	public void jugarFase() {
		p.actualizarProduccion();
		recursosRestantes = p.getProduccion(recursoActual);
		if (p.getRecursosRestantes() >=5) {
			p.mayoriaCientifica();
			
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
		actualizarEstado(EstadoPartida.ENTRE_PRODUCCIONES);
		p.cambiarFase(new ProduccionOro(p));
		p.limpiarZonaConstruccion();
		p.getFase().jugarFase();
		
	}

}
