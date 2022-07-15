package presentadores.patronEstado;

import com.badlogic.gdx.math.Vector3;

import modelo.Recurso;
import presentadores.Presentador;

public class ProduccionMaterial extends FasePartida {

	public ProduccionMaterial(Presentador p) {
		super(p);
		textoFase = "Produccion Material";
		botonFlujo = "Producir Energia";
		estado = EstadoPartida.COLOCANDO_RECURSOS;
		recursoActual = Recurso.MATERIAL;
		this.p.flujoColor(new Vector3(0.4f,0.4f,0.4f));
		this.p.moverInfoProduccion();

	}

	@Override
	public void jugarFase() {
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
		}else 
			p.cambiarFase(new ProduccionEnergia(p));
			actualizarEstado(estado);
			p.limpiarZonaConstruccion();
			p.getFase().jugarFase();
				
		
		
	}

}
