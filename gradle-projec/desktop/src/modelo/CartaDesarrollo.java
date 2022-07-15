package modelo;

import java.util.ArrayList;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;

import modelo.persistencia.DAOCartaDesarrollo;

/**
 * @author Pablo
 *
 */
public class CartaDesarrollo extends Group {
	
	private static ArrayList<CartaDesarrollo> cartas;
	
	private TextureRegion texReg;
	
	private int id;
	private int idCopia;
	private String nombre;
	private TipoCarta tipo;
	private Recursos produccion;
	private TipoCarta multProduccion;
	private Recurso reciclaje;
	private Recursos recompensa;
	private TipoPuntos tipoPuntos;
	private int puntos;
	private Recursos costeConstruccion;
	private Recursos progresoConstruccion;
	private int copias;
	private EstadoCarta estado;
	private boolean cabenRecursos; 
	
	public CartaDesarrollo(int id, String nombre, int tipo,
			String costeConstruccion, String produccion,
			int multProduccion, int reciclaje, String recompensa,
			int puntos, int tipoPuntos, int copias) {
		
		this.id = id;
		this.nombre = nombre;
		this.tipo = convertirTipoCarta(tipo);
		this.costeConstruccion = convertirRecursos(costeConstruccion);
		this.produccion = convertirRecursos(produccion);
		this.multProduccion = convertirTipoCarta(multProduccion);
		this.reciclaje = convertirReciclaje(reciclaje);
		this.recompensa = convertirRecursos(recompensa);
		this.puntos = puntos;
		this.tipoPuntos = convertirTipoPuntos(tipoPuntos);
		this.copias = copias;
		progresoConstruccion = this.costeConstruccion;
		cabenRecursos = false;
		texReg = new TextureRegion(new Texture(Gdx.files.internal(nombre+".png")));
		setWidth(texReg.getRegionWidth());
		setWidth(texReg.getRegionHeight());
		
	}
	
	public CartaDesarrollo(CartaDesarrollo copiar) {		
		this.id = copiar.id;
		this.nombre = copiar.nombre;
		this.tipo = copiar.tipo;
		this.produccion = new Recursos(copiar.produccion);
		this.costeConstruccion = new Recursos(copiar.costeConstruccion);
		this.multProduccion = copiar.multProduccion;
		this.reciclaje = copiar.reciclaje;
		this.recompensa = new Recursos(copiar.recompensa);
		this.puntos = copiar.puntos;
		this.tipoPuntos = copiar.tipoPuntos;
		this.copias = copiar.copias;
		progresoConstruccion =  new Recursos(costeConstruccion);
		cabenRecursos = false;
		texReg = new TextureRegion(new Texture(Gdx.files.internal(nombre+".png")));

		setWidth(texReg.getRegionWidth());
		setWidth(texReg.getRegionHeight());
	}

	private Recurso convertirReciclaje(int reciclaje) {
		switch (reciclaje) {
		case 1:
			return Recurso.MATERIAL;
		case 2:
			return Recurso.ENERGIA;
		case 3:
			return Recurso.CIENCIA;
		case 4:
			return Recurso.ORO;
		case 5:
			return Recurso.EXPLORACION;
	}
	return null;
	}

	private Recursos convertirRecursos(String idLinea) {
		if(idLinea==null) {
			return new Recursos();
		}
		return Recursos.getLinea(idLinea);
	}
	
	private TipoCarta convertirTipoCarta(int tipo) {
		switch (tipo) {
			case 1:
				return TipoCarta.ESTRUCTURA;
			case 2:
				return TipoCarta.VEHICULO;
			case 3:
				return TipoCarta.INVESTIGACION;
			case 4:
				return TipoCarta.PROYECTO;
			case 5:
				return TipoCarta.DESCUBRIMIENTO;
		}
		return null;
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
	    batch.draw(texReg, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
	    this.setTransform(true);
	    super.draw(batch, parentAlpha);
		
	}

	
	public boolean cabenRecursos() {
		return cabenRecursos;
	}

	public void setCabenRecursos(boolean cabenRecursos) {
		this.cabenRecursos = cabenRecursos;
	}

	public int getId() {
		return id;
	}
	public String getNombre() {
		return nombre;
	}
	public TipoCarta getTipo() {
		return tipo;
	}
	public Recursos getProduccion() {
		return produccion;
	}
	public Recurso getReciclaje() {
		return reciclaje;
	}
	public Recursos getRecompensa() {
		return recompensa;
	}
	public TipoPuntos getTipoPuntos() {
		return tipoPuntos;
	}
	public Recursos getCosteConstruccion() {
		return costeConstruccion;
	}
	public int getCopias() {
		return copias;
	}


	public void crearCopia() {
		copias--;
		
	}
	public TipoCarta getMultProduccion() {
		return multProduccion;
	}

	public int getPuntos() {
		return puntos;
	}

	public Recursos getProgresoConstruccion() {
		return progresoConstruccion;
	}

	public EstadoCarta getEstado() {
		return estado;
	}

	public void setEstado(EstadoCarta estado) {
		this.estado = estado;
	}

	public int getIdCopia() {
		return idCopia;
	}

	public void setIdCopia(int idCopia) {
		this.idCopia = idCopia;
	}

	public static ArrayList<CartaDesarrollo> getCartasDesarrollo() {
		if (cartas==null) {
			cartas = DAOCartaDesarrollo.cargarCartasDesarrollo();
		}
		return cartas;
	}
	

	public void colocarRecurso(Recurso r, Image recursoImagen) {
		colocarImagenRecurso(costeConstruccion.get(r)-progresoConstruccion.get(r)+costeConstruccion.recursosHasta(Recursos.getNum(r)),
				recursoImagen);
		
		progresoConstruccion.colocarRecurso(r);
	}

	public void colocarImagenRecurso(int pos, Actor recurso) {
		System.out.println("la posicion es: "+pos);
		recurso.setName(Integer.toString(pos));
		recurso.setOrigin(Align.center);
		recurso.setSize(getWidth()/7.25f, getWidth()/7.25f);
		float y = getHeight()-recurso.getHeight()*0.2f-((recurso.getHeight()*1.0f*(pos+1)));
		recurso.setPosition(getWidth()*0.03f, y);
		this.addActor(recurso);
	}

	public static CartaDesarrollo getCarta(int i){
		return cartas.get(i);
	}
	
	@Override
	public void setColor(float r, float g, float b, float a) {
		super.setColor(r, g, b, a);
		for (Actor actor : getChildren()) {
			actor.setColor(r, g, b, a);
		}
	}
	
	@Override
	public void setColor(Color color) {
		super.setColor(color);
		for (Actor actor : getChildren()) {
			actor.setColor(color);
		}
	}



}
