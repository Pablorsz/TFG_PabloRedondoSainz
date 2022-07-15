package vistas.zonas;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import modelo.CartaDesarrollo;

public class ZonaPlanificacion extends Group{
	private float cartaWidth;
	private float cartaHeigth;
	private float yGap;
	private float xGap;
	private ArrayList<CartaDesarrollo> enPlanificacion;
	
	

	public ZonaPlanificacion(int x, int y, float width, float height) {
		enPlanificacion = new ArrayList<>();
		enPlanificacion.add(null);
		enPlanificacion.add(null);
		enPlanificacion.add(null); 
		enPlanificacion.add(null);
		enPlanificacion.add(null);
		setBounds(x, y, width, height);
		yGap = height*0.05f;
		cartaHeigth=getHeight()-2*yGap;
		cartaWidth=cartaHeigth*0.65f;
		xGap = (width-(5*cartaWidth))/6;
		
	}


	public void addCarta(CartaDesarrollo c) {
		for (CartaDesarrollo carta : enPlanificacion) {
			if(carta==null) {
				enPlanificacion.set(enPlanificacion.indexOf(carta), c);
				break;

			}
		}
		int index = enPlanificacion.indexOf(c);
		c.setBounds(getWidth(), yGap, cartaWidth,cartaHeigth);
		addActor(c);
		c.addAction(Actions.sequence(Actions.delay(index*0.4f),Actions.moveTo(xGap+(xGap+cartaWidth)*index, yGap, 1-index*0.12f)));
		
		
	}
	public void retirarCarta(CartaDesarrollo c) {
		enPlanificacion.set(enPlanificacion.indexOf(c), null);
	}
	
	public void changeListeners(ClickListener listener) {
		for (CartaDesarrollo c : enPlanificacion) {
			if(c!=null) {
				c.clearListeners();
				c.addListener(listener);				
			}
		}
	}
	
	public void clearListenersCartas() {
		for (CartaDesarrollo c : enPlanificacion) {
			c.clearListeners();
		}
		
		
	}
	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
	}
	
	
	public void limpiarListeners() {
		for (CartaDesarrollo c : enPlanificacion) {
			if (c!=null) {
				c.clearListeners();
			}
		}
	}

}
