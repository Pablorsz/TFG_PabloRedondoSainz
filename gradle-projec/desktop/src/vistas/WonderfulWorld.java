package vistas;

import com.badlogic.gdx.Game;

import presentadores.Presentador;

public class WonderfulWorld extends Game{
	//private ControladorVista controladorVista;
	private Presentador presentador;
	@Override
	public void create() {
		presentador = new Presentador(this);
		this.setScreen(new VistaInicio(presentador));
		
	}
	public Presentador getPresentadorPartida() {
		return presentador;
	}	


}
