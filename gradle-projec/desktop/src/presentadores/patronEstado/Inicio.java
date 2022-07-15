package presentadores.patronEstado;


import presentadores.Presentador;

public class Inicio extends FasePartida {

	public Inicio(Presentador p) {
		super(p);
	}

	@Override
	public void jugarFase() {

	}

	@Override
	public void cambiarDeFase() {
		p.cambiarFase(new PrimeraPlanificacion(p));
		p.getFase().jugarFase();
		p.actualizarListeners();

	}

}
