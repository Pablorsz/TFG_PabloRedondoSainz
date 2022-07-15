package presentadores.patronEstado;

import com.badlogic.gdx.math.Vector3;

import modelo.Recurso;
import presentadores.Presentador;

public class ProduccionOro extends FasePartida {

	public ProduccionOro(Presentador p) {
		super(p);
		textoFase = "Produccion Oro";
		botonFlujo = "Producir Exploracion";
		estado = EstadoPartida.COLOCANDO_RECURSOS;
		recursoActual = Recurso.ORO;
		this.p.flujoColor(new Vector3(0.1f,0.9f,1f));
	}

	@Override
	public void jugarFase() {
		System.out.println("Se va a producir ORO");
		p.actualizarProduccion();
		recursosRestantes = p.getProduccion(recursoActual);
		if (p.getRecursosRestantes() >=5) {
			p.getPartida().recibirFinanciero();
			p.getVista().actualizarFinancieros(p.getPartida().getFinancieros());
			
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
		p.cambiarFase(new ProduccionExploracion(p));
		actualizarEstado(estado);
		p.limpiarZonaConstruccion();
		p.getFase().jugarFase();
		
	}

}
