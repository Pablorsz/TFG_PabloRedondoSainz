package modelo;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import modelo.persistencia.DAOCartaImperio;

public class CartaImperio extends Image {
	private static ArrayList<CartaImperio> cartas;
	private int id;
	private String nombre;
	private Recursos produccionInicial;
	private TextureRegion cartaImagen;
	private TipoPuntos tipoPuntos;
	private int puntos;
	
	public CartaImperio(int id,String nombre, String produccionInical, int puntos,int tipoPuntos ) {
		this.id = id;
		this.nombre = nombre;
		this.produccionInicial = Recursos.getLinea(produccionInical);

		this.tipoPuntos = convertirTipoPuntos(tipoPuntos);
		this.puntos = puntos;
		cartaImagen = new TextureRegion(new Texture(Gdx.files.internal(nombre+".png")));
		
		
	}
	
	public CartaImperio(CartaImperio copia) {
		this.id = copia.getId();
		this.nombre = copia.getNombre();
		this.produccionInicial = new Recursos(copia.produccionInicial);

		this.tipoPuntos = copia.getTipoPuntos();
		this.puntos = copia.getPuntos();
		cartaImagen = new TextureRegion(new Texture(Gdx.files.internal(nombre+".png")));
	}
	private TipoPuntos convertirTipoPuntos(int tipo) {
		switch (tipo) {
			case 0:
				return TipoPuntos.PLANOS;
			case 1:
				return TipoPuntos.ESTRUCTURA;
			case 2:
				return TipoPuntos.VEHICULO;
			case 3:
				return TipoPuntos.INVESTIGACION;
			case 4:
				return TipoPuntos.PROYECTO;
			case 5:
				return TipoPuntos.DESCUBRIMIENTO;
			case 6:
				return TipoPuntos.FINANCIEROS;
			case 7:
				return TipoPuntos.GENERALES;
		}
		return null;
	}
	@Override
	public void draw(Batch batch, float parentAlpha) {
	    Color color = getColor();
	    batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
	    batch.draw(cartaImagen, getX(), getY(), getWidth(), getHeight());
	    //batch.draw(carta, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());

		
	}
	public int getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}

	public Recursos getProduccionInicial() {
		return produccionInicial;
	}


	public TextureRegion getCartaImagen() {
		return cartaImagen;
	}

	public TipoPuntos getTipoPuntos() {
		return tipoPuntos;
	}

	public int getPuntos() {
		return puntos;
	}

	public static ArrayList<CartaImperio> getCartasImperio() {
		if (cartas==null) {
			cartas = DAOCartaImperio.cargarCartasImperio();
		}
		return cartas;
		
	}

	public static CartaImperio getCartaImperio(int idImperio) {
		return new CartaImperio(cartas.get(idImperio-1));
	}
	
	
}
