package presentadores.patronEstado;

import com.badlogic.gdx.math.Vector3;

import presentadores.Presentador;

public class Final extends FasePartida {

	public Final(Presentador p) {
		super(p);

		textoFase = "FINAL DE PARTIDA";
		botonFlujo = "MENU ESCENARIOS";
		estado= EstadoPartida.FIN;
	}

	@Override
	public void jugarFase() {
		p.finDePartida();
		this.p.flujoColor(new Vector3(1f,0.4f,0.4f));
		actualizarEstado(estado);

	}

	@Override
	public void cambiarDeFase() {
		p.mostrarEscenarios();
		p.cambiarFase(new Inicio(p));
		
		
	}

}
