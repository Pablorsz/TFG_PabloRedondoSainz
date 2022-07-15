package vistas.zonas;

import java.util.LinkedList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import modelo.CartaDesarrollo;
import modelo.CartaImperio;

public class ZonaImperio extends Group{
	private Group selectorKrystallium;
	private CartaImperio cartaImperio;
	private Image financiero;
	private Image general;
	private Image krystallium;
	private Label financieroLabel;
	private Label generalLabel;
	private Label krystalliumLabel;
	private Label progresoKrystalliumLabel;
	private float cartaWidth;
	private float cartaHeigth;
	private float yGap;
	private LinkedList<CartaDesarrollo> completadas;

	public ZonaImperio(float x, float y, float width, float height,CartaImperio cartaImperio) {
		completadas = new LinkedList<>();
		setBounds(x, y, width, height);
		cartaWidth= width/2;
		cartaHeigth = cartaWidth/0.65f;
		yGap=cartaHeigth*0.12f;
		
		

		this.cartaImperio = cartaImperio;
		addActor(this.cartaImperio);
		this.cartaImperio.setBounds(0,0, getWidth(),getWidth()*0.65f);
		
		crearElementos();
		crearSelectorKrystallium();
		
	}
	
	public ZonaImperio(float x, float y, float width, float height) {

		completadas = new LinkedList<>();
		setBounds(x, y, width, height);
		cartaWidth= width/2;
		cartaHeigth = cartaWidth/0.65f;
		yGap=cartaHeigth*0.12f;
		
	}
	private void crearElementos() {

		Skin skin = new Skin(Gdx.files.internal("uiskin.json"));
		
		
		Texture cubo = new Texture(Gdx.files.internal("CuboRojo.png"));
		TextureRegion texReg = new TextureRegion(cubo);


		this.krystallium= new Image(texReg);
		this.krystallium.setColor(0.8f,0.3f,0.3f,0.9f);
		this.krystallium.setName("KRYSTALLIUMPOPUP");
		
		Texture f = new Texture(Gdx.files.internal("financiero.png"));
		texReg = new TextureRegion(f);
		financiero = new Image(texReg);
		
		Texture g = new Texture(Gdx.files.internal("general.png"));
		texReg = new TextureRegion(g);
		general = new Image(texReg);
		
		

		
		financieroLabel = new Label(""+0,skin);
		krystalliumLabel= new Label(""+0,skin);
		progresoKrystalliumLabel= new Label(""+0,skin);
		generalLabel = new Label(""+0,skin);
		addActor(financiero);
		addActor(general);
		addActor(krystallium);
		addActor(financieroLabel);
		addActor(krystalliumLabel);
		addActor(progresoKrystalliumLabel);
		addActor(generalLabel);

		financiero.setBounds(cubo.getWidth()/2,(cartaImperio.getHeight()-cubo.getHeight())/2,cubo.getWidth(),cubo.getHeight());
		general.setBounds(cubo.getWidth()*1.5f,(cartaImperio.getHeight()-cubo.getHeight())/2,cubo.getWidth(),cubo.getHeight());
		krystallium.setBounds(cubo.getWidth()*2.5f,(cartaImperio.getHeight()-cubo.getHeight())/2,cubo.getWidth(),cubo.getHeight());
		
		
		
		financieroLabel.setTouchable(Touchable.disabled);
		progresoKrystalliumLabel.setTouchable(Touchable.disabled);
		krystalliumLabel.setTouchable(Touchable.disabled);
		generalLabel.setTouchable(Touchable.disabled);

		financieroLabel.setAlignment(Align.center);
		progresoKrystalliumLabel.setAlignment(Align.center);
		krystalliumLabel.setAlignment(Align.center);
		generalLabel.setAlignment(Align.center);
		
		progresoKrystalliumLabel.setBounds(getWidth()-cubo.getWidth(),0,cubo.getWidth(),cubo.getHeight());
		financieroLabel.setBounds(cubo.getWidth()/2,(cartaImperio.getHeight()-cubo.getHeight())/2,cubo.getWidth(),cubo.getHeight());
		generalLabel.setBounds(cubo.getWidth()*1.5f,(cartaImperio.getHeight()-cubo.getHeight())/2,cubo.getWidth(),cubo.getHeight());
		krystalliumLabel.setBounds(cubo.getWidth()*2.5f,(cartaImperio.getHeight()-cubo.getHeight())/2,cubo.getWidth(),cubo.getHeight());
		
		
		selectorKrystallium = new Group();
		selectorKrystallium.setBounds(krystallium.getX()-cubo.getWidth(),krystallium.getY() + cubo.getHeight(),cubo.getWidth()*3, cubo.getWidth()*2);
		
		
	}

	private void crearSelectorKrystallium() {
		TextureRegion draftBackground = new TextureRegion(
				new Texture(Gdx.files.internal("fieltroAzulClaro.jpg")),(int)selectorKrystallium.getWidth(),(int)selectorKrystallium.getHeight());
		Image image = new Image(draftBackground);
		Texture cubo = new Texture(Gdx.files.internal("CuboBlanco.png"));
		TextureRegion texReg = new TextureRegion(cubo);
		selectorKrystallium.addActor(image);
		image.setFillParent(true);
		
		Image material = new Image(texReg);
		//material.setColor(0.8f,0.8f,0.8f,1);
		material.setName("MATERIAL");


		cubo = new Texture(Gdx.files.internal("CuboNegro.png"));
		texReg = new TextureRegion(cubo);
		Image energia = new Image(texReg);
		//energia.setColor(0.4f,0.4f,0.4f,1);
		energia.setName("ENERGIA");

		cubo = new Texture(Gdx.files.internal("CuboVerde.png"));
		texReg = new TextureRegion(cubo);
		Image ciencia = new Image(texReg);
		//ciencia.setColor(0.25f,0.9f,0.25f,1);
		ciencia.setName("CIENCIA");
		

		cubo = new Texture(Gdx.files.internal("CuboAmarillo.png"));
		texReg = new TextureRegion(cubo);
		Image oro = new Image(texReg);
		//oro.setColor(1,0.95f,0.15f,1);
		oro.setName("ORO");


		cubo = new Texture(Gdx.files.internal("CuboAzul.png"));
		texReg = new TextureRegion(cubo);
		Image exploracion= new Image(texReg);
		//exploracion.setColor(0.25f,0.25f,0.9f,1);
		exploracion.setName("EXPLORACION");
 

		cubo = new Texture(Gdx.files.internal("CuboRojo.png"));
		texReg = new TextureRegion(cubo);
		Image krystallium= new Image(texReg);
		//krystallium.setColor(0.9f,0.3f,0.3f,0.9f);
		krystallium.setName("KRYSTALLIUM");
		
		selectorKrystallium.addActor(material);
		selectorKrystallium.addActor(energia);
		selectorKrystallium.addActor(ciencia);
		selectorKrystallium.addActor(oro);
		selectorKrystallium.addActor(exploracion);
		selectorKrystallium.addActor(krystallium);
		
		material.setBounds(cubo.getWidth()*0,cubo.getHeight(),cubo.getWidth(),cubo.getHeight());
		energia.setBounds(cubo.getWidth()*1,cubo.getHeight(),cubo.getWidth(),cubo.getHeight());
		ciencia.setBounds(cubo.getWidth()*2,cubo.getHeight(),cubo.getWidth(),cubo.getHeight());
		
		oro.setBounds(cubo.getWidth()*0,0,cubo.getWidth(),cubo.getHeight());
		exploracion.setBounds(cubo.getWidth()*1,0,cubo.getWidth(),cubo.getHeight());
		krystallium.setBounds(cubo.getWidth()*2,0,cubo.getWidth(),cubo.getHeight());
		
		
		
		selectorKrystallium.setVisible(false);
		//Group seleccion = new TableroRecursos(krystalluim.getX(), krystalluim.getY(), 0, 0);
		addActor(selectorKrystallium);
		//selectorKrystallium.setZIndex(1000);
		
	}

	public Image getFinanciero() {
		return financiero;
	}

	public void setFinanciero(Image financiero) {
		this.financiero = financiero;
	}

	public Image getGeneral() {
		return general;
	}

	public void setGeneral(Image general) {
		this.general = general;
	}

	public Image getKrystallium() {
		return krystallium;
	}

	public void setKrystallium(Image krystallium) {
		this.krystallium = krystallium;
	}

	public void completarCarta(CartaDesarrollo c) {

		Vector2 v= c.localToActorCoordinates(this, new Vector2(0,0));

		c.setBounds(v.x, v.y, c.getWidth(), c.getHeight());
		c.remove();
		this.addActor(c);
		c.addAction(Actions.parallel(Actions.sizeTo(cartaWidth, cartaHeigth,0.5f),
				Actions.moveTo(cartaWidth*(1-completadas.size()%2),
						cartaImperio.getHeight()+(yGap*((completadas.size()/2-(completadas.size()%2)/2))),0.5f)));
		completadas.add(c);
	}

	public void addListeners(ClickListener financieroListener, ClickListener krystalliumListener,
			ClickListener generalListener,ClickListener colocarKrystallium, ClickListener conversionKrystalliumListener) {
		financiero.addListener(financieroListener);
		krystallium.addListener(krystalliumListener);
		krystallium.addListener(colocarKrystallium);
		general.addListener(generalListener);
		cartaImperio.addListener(conversionKrystalliumListener);
		for (Actor recurso : selectorKrystallium.getChildren()) {
			recurso.addListener(colocarKrystallium);
		}
		
	}

	public void displayPopupKrystallium() {
		if (selectorKrystallium.isVisible()) {
			selectorKrystallium.setVisible(false);
		} else {
			selectorKrystallium.remove();
			addActor(selectorKrystallium);
			selectorKrystallium.setVisible(true);
		}
		
	}

	public void actualizarKrystallium(int krystallium, int progresoKrystallium) {
		krystalliumLabel.setText(""+krystallium);
		progresoKrystalliumLabel.setText(""+progresoKrystallium);
		
	}



	public void actualizarFinancieros(int f) {
		financieroLabel.setText(""+f);
		
	}

	public void actualizarGenerales(int g) {
		generalLabel.setText(""+g);
		
	}

	public void addCartaImperio(CartaImperio cartaImperio) {
		
		this.cartaImperio = cartaImperio;

		Vector2 v= cartaImperio.localToActorCoordinates(this, new Vector2(0,0));
		cartaImperio.setBounds(v.x, v.y, cartaImperio.getWidth(), cartaImperio.getHeight());
		cartaImperio.remove();
		this.addActor(cartaImperio);
		cartaImperio.addAction(Actions.moveTo(0, 0));
		crearElementos();
		crearSelectorKrystallium();
		
	}

}
