package presentadores.patronEstado;

import com.badlogic.gdx.math.Vector3;

import modelo.Recurso;
import presentadores.Presentador;

public class ProduccionEnergia extends FasePartida {

	public ProduccionEnergia(Presentador p) {
		super(p);
		textoFase = "Produccion Energia";
		botonFlujo = "Producir Ciencia";
		estado = EstadoPartida.COLOCANDO_RECURSOS;
		recursoActual = Recurso.ENERGIA;
		this.p.flujoColor(new Vector3(0.2f,1,0.2f));
	}

	@Override
	public void jugarFase() {
		System.out.println("Se va a producir ENERGIA");
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
			p.cambiarFase(new ProduccionCiencia(p));
			actualizarEstado(estado);
			p.limpiarZonaConstruccion();
			p.getFase().jugarFase();
		
	}

}
