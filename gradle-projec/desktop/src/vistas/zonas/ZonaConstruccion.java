package vistas.zonas;

import java.util.LinkedList;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import modelo.CartaDesarrollo;

public class ZonaConstruccion extends Group{
	private float cartaWidth;
	private float cartaHeigth;
	private int filas;
	private int columnas;
	private int auxCol;
	private float yGap;
	private float xGap;
	
	private LinkedList<CartaDesarrollo> enConstruccion;
	
	public ZonaConstruccion(float x, float y, float width, float height){
		this.setBounds(x, y, width, height);
		enConstruccion = new LinkedList<>();
		
		filas = 2;
		calcularTamaños();
		
	}
	private void calcularTamaños() {
		yGap = getHeight()*0.01f/filas ;
		cartaHeigth = (getHeight()-yGap-yGap*filas)/filas;
		cartaWidth = cartaHeigth*0.65f;
		columnas = Math.round(getWidth()/cartaWidth - 0.5f);
		xGap = (getWidth()-cartaWidth*columnas)/(columnas+1);
		
		
		
	}
	public void planificarConstrucccion(CartaDesarrollo c) {
		if (enConstruccion.size()>=filas*columnas) {
			filas++;
			auxCol = columnas;
			calcularTamaños();
			for (CartaDesarrollo carta : enConstruccion) {
				colocarCarta(carta);
			}
		}
		Vector2 v= c.localToActorCoordinates(this, new Vector2(0,0));

		c.setBounds(v.x, v.y, c.getWidth(), c.getHeight());
		c.remove();
		enConstruccion.add(c);
		this.addActor(c);
		colocarCarta(c);
		
	}
	public void colocarCarta(CartaDesarrollo c) {
		
		float[] posicion = getPosicion(enConstruccion.indexOf(c));
		c.addAction(Actions.parallel(Actions.sizeTo(cartaWidth, cartaHeigth,0.5f),Actions.moveTo(posicion[0], posicion[1],0.5f)));
		for (Actor r : c.getChildren()) {

			int pos = Integer.parseInt(r.getName());
			float y = cartaHeigth-(cartaWidth/7.25f)*0.2f-(((cartaWidth/7.25f)*1f*(pos+1)));
			r.addAction(Actions.parallel(Actions.sizeTo(cartaWidth/7.25f,
					cartaWidth/7.25f,0.5f),Actions.moveTo(cartaWidth*0.03f,y,0.5f)));
			
		}
	}
	
	public void reorganizar() {
		if(enConstruccion.size()<=(filas-1)*auxCol && filas>2) {
			filas--;
			calcularTamaños();
		}
		for (CartaDesarrollo c : enConstruccion) {
			colocarCarta(c);
			
		}
	}
	public void eliminarConstruccion(CartaDesarrollo c) {
		c.clearChildren();
		enConstruccion.remove(c);
		reorganizar();
		
	}
	
	private float[] getPosicion(int index) {
		float[] posicion = new float[2];
		int fila = index/columnas;
		int columna = index%columnas;
		float posX = xGap + (cartaWidth+xGap)*columna;
		float posY = getHeight()-cartaHeigth-(yGap + (cartaHeigth+yGap)*fila);
		posicion[0] = posX;
		posicion[1] = posY;
		return posicion;
	}
	public void reorganizarCartas() {
		for (CartaDesarrollo c : enConstruccion) {
			colocarCarta(c);
		}
		
	}
	public boolean empty() {
		return enConstruccion.isEmpty();
	}

}
