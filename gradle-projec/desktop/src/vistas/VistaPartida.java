package vistas;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import modelo.CartaDesarrollo;
import modelo.CartaImperio;
import modelo.Recurso;
import presentadores.Presentador;
import vistas.zonas.ZonaConstruccion;
import vistas.zonas.ZonaImperio;
import vistas.zonas.ZonaPlanificacion;

public class VistaPartida implements Screen {

	private Stage stage;
	private Skin skin;

	private Presentador presentador;

	
	//Zonas
	private ZonaConstruccion zonaConstruccion;
	private ZonaPlanificacion zonaPlanificacion;
	private ZonaImperio zonaImperio;

	//Elementos
	private Label faseLabel;
	private Label estadoLabel;
	private Image mazo;
	
	//Auxiliar
	private ArrayList<CartaDesarrollo> descartar;
	private ArrayList<CartaDesarrollo> draftEleccion;
	private Group popupDraft;
	private Group groupImperio;
	
	//listeners
	private ClickListener convertirSobrantesEnKrystalliumListener;
	private ClickListener colocarKrystallium;
	private ClickListener listenerDescartarDraft;
	private ClickListener listenerSeleccionDraft;
	private ClickListener listenerSeleccionImperio;
	private ClickListener listenerColocarRecursos;
	private ClickListener listenerMostrarOpciones;
	private ClickListener listenerMayoriFinanciero;
	private ClickListener listenerMayoriaGeneral;
	private ClickListener financieroListener;
	private ClickListener krystalliumListener;
	private ClickListener generalListener;
	
	//Buttons
	private TextButton botonFlujo;
	private TextButton botonDraft;
	private TextButton reciclarBtn;
	private TextButton construirBtn;
	private TextButton descartarBtn;

	private int ALTO;
	private int ANCHO;

	
	public VistaPartida(Presentador p) {

		ALTO = Gdx.graphics.getHeight();
		ANCHO = Gdx.graphics.getWidth();
		this.presentador = p;
		presentador.setVista(this);
		stage = new Stage();
		descartar = new ArrayList<>();
		Gdx.input.setInputProcessor(stage);
		skin = new Skin(Gdx.files.internal("uiskin.json"));
		TextureRegion background = new TextureRegion(new Texture(Gdx.files.internal("fieltroAzulClaro.jpg")));
		Image i = new Image(background);
		i.setFillParent(true);
		stage.addActor(i);

		zonaConstruccion = new ZonaConstruccion(0, ALTO*0.25f, ANCHO*0.75f, ALTO*0.75f);
		zonaPlanificacion = new ZonaPlanificacion(0, 0,ANCHO*0.75f-(ALTO*0.25f*0.65f), ALTO*0.25f);
		
		
		colocarKrystallium = new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				presentador.colocarKrystalliumClicked(event);
				
			}
		};
		
		
		
		listenerDescartarDraft = new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				CartaDesarrollo c = (CartaDesarrollo)event.getTarget();
				if(!descartar.contains(c)) {
					c.setColor(0.6f,0.6f,0.6f,1);
					descartar.add(c);
				}
				
				if (descartar.size()==2) {
					descartarYRobar();
				}
				super.clicked(event, x, y);
			}
			
		};
		listenerMostrarOpciones =new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				presentador.mostrarOpcionesClicked(event);
			}
		};
		listenerSeleccionDraft = new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				botonDraft.setVisible(true);
				CartaDesarrollo c = (CartaDesarrollo)event.getTarget();
				presentador.moverAPlanificacion(c);
				c.removeListener(listenerSeleccionDraft);
				draftEleccion.remove(c);
				float tmpX = popupDraft.getX()+c.getX();
				float tmpY = popupDraft.getY()+c.getY();
				c.remove();
				c.setPosition(tmpX, tmpY);
				stage.addActor(c); 
				
				
				c.addListener(listenerMostrarOpciones);
				zonaPlanificacion.addCarta(c);
				for (CartaDesarrollo descartada : draftEleccion) {

					tmpX = popupDraft.getX()+descartada.getX();
					tmpY = popupDraft.getY()+descartada.getY();
					descartada.remove();
					descartada.setPosition(tmpX, tmpY);
					stage.addActor(descartada);
					
					descartada.addAction(Actions.sequence(Actions.parallel(
							Actions.scaleTo(0,0,1),Actions.fadeOut(0.5f)),
					Actions.removeActor()));
					presentador.descartar(descartada);
				}
				popupDraft.remove();
				draftEleccion.clear();
				presentador.actualizarAJugandoCartas();
				terminarDraft();
			}
		};
		
		listenerSeleccionImperio = new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				botonFlujo.setVisible(true);
				mazo.setVisible(true);
				CartaImperio c = (CartaImperio)event.getTarget();

				stage.addActor(c); 
				for (Actor actor : groupImperio.getChildren()) {
					CartaImperio carta = (CartaImperio) actor;
					
					carta.addAction(Actions.sequence(Actions.parallel(
							Actions.scaleTo(0,0,1),Actions.fadeOut(0.5f)),
					Actions.removeActor()));
				}
				zonaImperio.addCartaImperio(c);
				groupImperio.remove();
				presentador.cartaImperioSeleccionada(c);
				c.clearListeners();
			}
		};
		listenerColocarRecursos = new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				presentador.colocarRecursosClicked(event);
				
			}
			
		};
		
		financieroListener = new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				reciclarBtn.remove();
				construirBtn.remove();
				descartarBtn.remove();
				presentador.financieroListenerClicked(event);
			}
		};
		listenerMayoriFinanciero = new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				presentador.addFinanciero();
				event.getTarget().getParent().remove();
			}
		};
		listenerMayoriaGeneral= new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				presentador.addGeneral();
				event.getTarget().getParent().remove();
			}
		};
		krystalliumListener = new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				reciclarBtn.remove();
				construirBtn.remove();
				descartarBtn.remove();
				presentador.toggleKrystalliumPopup(event);
				
			}
		};
		generalListener = new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				reciclarBtn.remove();
				construirBtn.remove();
				descartarBtn.remove();
				presentador.colocarGeneralClicked(event);
			}
		};
		convertirSobrantesEnKrystalliumListener = new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				presentador.convertirSobrantesEnKrystalliumClicked(event);
				
			}
		};
		
		
		stage.addActor(zonaConstruccion);
		stage.addActor(zonaPlanificacion);
		
		botonFlujo = new TextButton("Nueva Planificacion", skin);
		botonFlujo.setBounds((zonaPlanificacion.getWidth()-ANCHO*0.2f)/2,
				zonaPlanificacion.getHeight()/2,ANCHO*0.2f, ALTO*0.1f);
		botonFlujo.setColor(0.86f,0.62f,0.42f,1);
		botonFlujo.setName("flujo");
		botonFlujo.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				reciclarBtn.remove();
				construirBtn.remove();
				descartarBtn.remove();
				presentador.siguienteFase();
				
			}

		});
		
		stage.addActor(botonFlujo);
		faseLabel = new Label("INICIO",skin);
		faseLabel.setBounds(zonaConstruccion.getWidth(), ALTO*0.97f , ANCHO*0.2f, ALTO*0.03f);
		stage.addActor(faseLabel);
		
		estadoLabel = new Label("",skin);
		estadoLabel.setBounds(zonaConstruccion.getWidth(), ALTO*0.94f , ANCHO*0.2f, ALTO*0.03f);
		stage.addActor(estadoLabel);
		mazo = new Image(new Texture(Gdx.files.internal("Dorso.png")));
		mazo.setBounds(zonaPlanificacion.getWidth(), 0, zonaPlanificacion.getHeight()*0.65f, zonaPlanificacion.getHeight());
		stage.addActor(mazo);
		
		
		botonDraft = new TextButton("Draft", skin);
		botonDraft.setBounds(mazo.getX(), zonaPlanificacion.getHeight()*0.33f,
				zonaPlanificacion.getHeight()*0.65f, zonaPlanificacion.getHeight()*0.33f);
		botonDraft.setColor(0.4f,0.4f,1,0.8f);
		botonDraft.setName("draft");
		activarDraft(false);
		
		botonDraft.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				reciclarBtn.remove();
				construirBtn.remove();
				descartarBtn.remove();
				presentador.botonDraftClicked(event);
			}

		});
		stage.addActor(botonDraft);
		
		reciclarBtn = new TextButton("Reciclar", skin);
		reciclarBtn.addListener(new ClickListener() {
			
			@Override
			public void clicked(InputEvent event, float x, float y) {
				try {
					CartaDesarrollo c = (CartaDesarrollo) event.getTarget().getParent().getParent();
					reciclarBtn.remove();
					construirBtn.remove();
					descartarBtn.remove();
					presentador.reciclajeClicked(c);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}

		});
		construirBtn = new TextButton("Construir", skin);
		construirBtn.setColor(0.86f,0.62f,0.41f,1);
		construirBtn.addListener(new ClickListener() {
			
			@Override
			public void clicked(InputEvent event, float x, float y) {
				try {
					CartaDesarrollo c = (CartaDesarrollo) event.getTarget().getParent().getParent();
					
					reciclarBtn.remove();
					construirBtn.remove();
					construccionClicked(c);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}

		});
		descartarBtn = new TextButton("Descartar", skin);
		descartarBtn.setColor(1,0.2f,0.2f,1);
		descartarBtn.addListener(new ClickListener() {
			
			@Override
			public void clicked(InputEvent event, float x, float y) {
				try {
					CartaDesarrollo c = (CartaDesarrollo) event.getTarget().getParent().getParent();
					descartarBtn.remove();
					zonaConstruccion.eliminarConstruccion(c);
					presentador.reciclajeClicked(c);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}

		});
		CartaImperio imperio = presentador.getCartaImeperio();
		if(imperio!=null) {
			zonaImperio = new ZonaImperio(zonaConstruccion.getWidth(), 0, ANCHO*0.25f, ALTO,presentador.getCartaImeperio());
			

			stage.addActor(zonaImperio);
		}else {
			zonaImperio = new ZonaImperio(zonaConstruccion.getWidth(), 0, ANCHO*0.25f, ALTO);
			mazo.setVisible(false);
			stage.addActor(zonaImperio);
			seleccionImperio(presentador.cargarCartasImperio(),listenerSeleccionImperio);
			
		}
		
		ArrayList<CartaDesarrollo> cartasEsenario = presentador.getCartasEscenario();
		for (CartaDesarrollo c : cartasEsenario) {
			c.setBounds(zonaPlanificacion.getWidth(), 0, zonaPlanificacion.getHeight()*0.65f, zonaPlanificacion.getHeight());
			zonaConstruccion.planificarConstrucccion(c);
		}
		
	}

	private void seleccionImperio(ArrayList<CartaImperio> cartasImperio, ClickListener listenerSeleccionImperio) {
		botonFlujo.setVisible(false);
		groupImperio = new Group();
		
		float gap = zonaImperio.getWidth()*0.05f;
		float cartaWidth = zonaImperio.getWidth();
		float cartaHeight = cartaWidth*0.65f;
		groupImperio.setBounds((ANCHO-(cartaWidth*3)-gap*4)/2,(ALTO-(cartaHeight*2)-3*gap)/2,
				(cartaWidth*3+gap*4), cartaHeight*2+gap*3);
		
		
		for (CartaImperio c : cartasImperio) {
			c.setBounds(gap+(gap+cartaWidth)*(c.getId()%3), gap+(gap+cartaHeight)*(c.getId()%2), cartaWidth, cartaHeight);
			groupImperio.addActor(c);
			c.addListener(listenerSeleccionImperio);
		}
		stage.addActor(groupImperio);
		
	}

	public void crearLabel(Actor actor, String text) {
		Table tabla = new Table();
		Label aviso = new Label(text, skin);
		//aviso.setOrigin(Align.center);
		Vector2 v= actor.localToActorCoordinates(zonaConstruccion.getParent(), new Vector2(0,0));
		

		tabla.setBounds(v.x, v.y, actor.getWidth(), actor.getHeight());
		tabla.add(aviso).width(tabla.getWidth());
		aviso.setWrap(true);

		stage.addActor(tabla);
		float t = text.length()*0.05f;
		tabla.addAction(Actions.sequence(Actions.sequence(
				Actions.moveBy(0,50*0.75f, t*0.5f),
				Actions.parallel(Actions.moveBy(0, 50*0.25f,t*0.25f),Actions.fadeOut(t*0.2f))), Actions.removeActor()));
		//aviso.remove();
		
	}

	protected void toggleDescartar(CartaDesarrollo c) {
		if (descartarBtn.getParent()==c) {
			descartarBtn.remove();
		}else {
			c.addActor(descartarBtn);
			descartarBtn.setSize(c.getWidth()*0.4f, c.getHeight()*0.14f);
			descartarBtn.setPosition(c.getWidth()-descartarBtn.getWidth()*1.02f,c.getHeight()*0.14f);
		}
	}

	protected void toggleReciclarConstruir(CartaDesarrollo c) {
		if (reciclarBtn.getParent()==c) {
			reciclarBtn.remove();
			construirBtn.remove();
		}else {
			c.addActor(reciclarBtn);
			c.addActor(construirBtn);
			reciclarBtn.setSize(c.getWidth()*0.75f, c.getWidth()*0.4f);
			reciclarBtn.setPosition(-reciclarBtn.getWidth()/2, c.getHeight());
			construirBtn.setSize(c.getWidth()*0.75f, c.getWidth()*0.4f);
			construirBtn.setPosition(c.getWidth()-construirBtn.getWidth()/2, c.getHeight());
			switch (c.getReciclaje()) {
			case ENERGIA:
				reciclarBtn.setColor(0.4f,0.4f,0.4f,1);
				break;
			case CIENCIA:
				reciclarBtn.setColor(0.2f,1f,0.2f,1);
				break;
			case ORO:
				reciclarBtn.setColor(1,1f,0f,1);
				break;
			case EXPLORACION:
				reciclarBtn.setColor(0.1f,0.9f,1f,1);
				break;
			default:
				reciclarBtn.setColor(1f,1f,1f,1);
				break;
		}
		}
	}

	public void construccionClicked(CartaDesarrollo c) {
		presentador.planificarConstuccion(c);
		zonaPlanificacion.retirarCarta(c);
		zonaConstruccion.planificarConstrucccion(c);
		c.clearListeners();
		c.addListener(listenerMostrarOpciones);
	
	}
	
	private void descartarYRobar() {
		botonDraft.setVisible(false);
		for (CartaDesarrollo c : descartar) {
			zonaPlanificacion.retirarCarta(c);
			presentador.descartar(c);
			c.addAction(Actions.sequence(Actions.parallel(
							Actions.scaleTo(0,0,1),Actions.fadeOut(0.5f)), 
					Actions.removeActor()));
		}
		descartar.clear();
		popupDraft = new Group();
		popupDraft.setBounds((ANCHO-(zonaPlanificacion.getHeight()*0.75f)*5)/2,(ALTO-zonaPlanificacion.getHeight()*1.2f)/2,
				(zonaPlanificacion.getHeight()*0.65f)*5+(zonaPlanificacion.getHeight()*0.65f)*0.6f, zonaPlanificacion.getHeight()*1.2f);
		//TextureRegion

		TextureRegion draftBackground = new TextureRegion(
				new Texture(Gdx.files.internal("fieltroAzulClaro.jpg")),(int)popupDraft.getWidth(),(int)popupDraft.getHeight());
		Image image = new Image(draftBackground);
		//image.setBounds(0, 0, popupDraft.getWidth(), popupDraft.getHeight());
		image.setFillParent(true);
		popupDraft.addActor(image);
		draftEleccion = new ArrayList<>();
		for(int i = 0; i < 5; i++) {
			CartaDesarrollo c = presentador.robar();
			draftEleccion.add(c);
			//t.addActor(c);
			//popup.addActor(c);
			c.setBounds(zonaPlanificacion.getWidth()-zonaPlanificacion.getHeight()*0.65f, 0, zonaPlanificacion.getHeight()*0.65f, zonaPlanificacion.getHeight());
			
			popupDraft.addActor(c);
			c.setPosition(c.getWidth()*0.1f+(c.getWidth()*1.1f)*i,(popupDraft.getHeight()-c.getHeight())/2);
		}
		stage.addActor(popupDraft);
		
		for (CartaDesarrollo c : draftEleccion) {
			c.addListener(listenerSeleccionDraft);
		}
		zonaPlanificacion.limpiarListeners();


	}


	public void prepararColocacionRecursos() { 
		presentador.actualizarAColocandoRecursos();
		if(presentador.getRecursosRestantes()>0) {
			presentador.prepararZonaConstruccion(listenerColocarRecursos);
			
		}else {
			//estado.setVisible(false);
			for (CartaDesarrollo c : presentador.getConstruccion()) {
				c.setColor(Color.WHITE);
				c.clearListeners();
				c.addListener(listenerMostrarOpciones);
			}

			presentador.setKrystalliumActivo(false);
			presentador.actualizarAJugandoCartas();
			if(presentador.finPlanificacion()) {
				presentador.permitirSiguienteFase(true);
				
			}
			//presentador.actualizarFase();
		}
		
	}

	public void nuevaPlanificacion(ArrayList<CartaDesarrollo> planificacion) {
		for (CartaDesarrollo c : planificacion) {
			c.addListener(listenerMostrarOpciones);
			zonaPlanificacion.addCarta(c);
		}
		activarDraft(true);

	
	}

	public void comenzarProduccion() {
		presentador.actualizarProduccion();
		
		
	}
	
	@Override
	public void show() {

	}

	@Override
	public void render(float delta) {	
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.draw();
		stage.act();
		//stage.setDebugAll(true);
		

	}

	@Override
	public void resize(int width, int height) {

		stage.getViewport().update(width, height, true);

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void hide() {

	}

	@Override
	public void dispose() {

	}


	public void actualizarInfo() {
		faseLabel.setText("Ronda: " + presentador.getNumRonda()+ " - " + presentador.getTextoFase());
		estadoLabel.setText(presentador.getTextoEstado());
		botonFlujo.setText(presentador.getBotonFase());
		
	}
	public void permitirSiguienteFase(boolean permitir) {
		
		//botonFlujo.setColor(0.3f,1,0.3f,1);
		botonFlujo.setVisible(permitir);
		
	}

	public void completarCarta(CartaDesarrollo c) {
		zonaConstruccion.eliminarConstruccion(c);
		zonaImperio.completarCarta(c);
		//c.setPosition(ANCHO-c.getWidth(), 0);
		
	}
	
	public void activarDraft(boolean permitir) {
		botonDraft.setVisible(permitir);
		
	}

	public void terminarDraft() {
		descartar.clear();
		botonDraft.setText("Draft");
		botonDraft.setColor(0.4f,0.4f,1,0.8f);
		if(presentador.getPlanificacion().size()<2) {
			botonDraft.setVisible(false);
		}
		zonaPlanificacion.changeListeners(listenerMostrarOpciones);
		for (CartaDesarrollo c : presentador.getPlanificacion()) {
			c.setColor(Color.WHITE);
			c.removeListener(listenerDescartarDraft);
		}
		
	}


	public Image crearImagenRecurso(Recurso recursoActual) {

		Texture cubo = new Texture(Gdx.files.internal("cube.png"));
		TextureRegion texReg = new TextureRegion(cubo);
		Image recursoImagen = new Image(texReg);
		switch (recursoActual) {
			case MATERIAL:
				cubo = new Texture(Gdx.files.internal("CuboBlanco.png"));
				texReg = new TextureRegion(cubo);
				recursoImagen = new Image(texReg);
				break;
			case ENERGIA:
				cubo = new Texture(Gdx.files.internal("CuboNegro.png"));
				texReg = new TextureRegion(cubo);
				recursoImagen = new Image(texReg);
				break;
			case CIENCIA:
				cubo = new Texture(Gdx.files.internal("CuboVerde.png"));
				texReg = new TextureRegion(cubo);
				recursoImagen = new Image(texReg);
				break;
			case ORO:
				cubo = new Texture(Gdx.files.internal("CuboAmarillo.png"));
				texReg = new TextureRegion(cubo);
				recursoImagen = new Image(texReg);
				break;
			case EXPLORACION:
				cubo = new Texture(Gdx.files.internal("CuboAzul.png"));
				texReg = new TextureRegion(cubo);
				recursoImagen = new Image(texReg);
				break;
			case FINANCIERO:
				Texture financiero = new Texture(Gdx.files.internal("financiero.png"));
				texReg = new TextureRegion(financiero);
				recursoImagen = new Image(texReg);
				break;
			case GENERAL:
				Texture general = new Texture(Gdx.files.internal("general.png"));
				texReg = new TextureRegion(general);
				recursoImagen = new Image(texReg);
				break;
			case KRYSTALLIUM:
				cubo = new Texture(Gdx.files.internal("CuboRojo.png"));
				texReg = new TextureRegion(cubo);
				recursoImagen = new Image(texReg);
				break;
			default:
				break;
		}
		stage.addActor(recursoImagen);
		recursoImagen.setBounds(cubo.getWidth()*0,cubo.getHeight(),cubo.getWidth(),cubo.getHeight());
		recursoImagen.setTouchable(Touchable.disabled);
		//Image recursoImagen = new Image
		return recursoImagen;
	}

	public void displayPopupKrystallium() {
		zonaImperio.displayPopupKrystallium();
		
	}

	public void mostrarDerrota() {
		zonaPlanificacion.remove();
		mazo.remove();
		Label derrota = new Label("DERROTA", skin);
		zonaConstruccion.addActor(derrota);
		derrota.setOrigin(Align.center);
		derrota.setBounds(zonaConstruccion.getWidth()/2, zonaConstruccion.getHeight()/2, 200, 200);
		
	}

	public void mostrarFinPartida(int puntos) {
		zonaPlanificacion.remove();
		mazo.remove();
		Label label = new Label("Has conseguido "+puntos+" puntos", skin);
		zonaConstruccion.addActor(label);
		label.setOrigin(Align.center);
		label.setBounds(zonaConstruccion.getWidth()/2, zonaConstruccion.getHeight()/2, 200, 200);
		String s = presentador.getRangoObtenido(puntos);
		
		Label rango = new Label("Has obtenido rango "+s, skin);
		zonaConstruccion.addActor(rango);
		rango.setOrigin(Align.center);
		rango.setBounds(zonaConstruccion.getWidth()/2, zonaConstruccion.getHeight()/2, 200, 200);
		rango.addAction(Actions.parallel(Actions.fadeIn(1),Actions.moveBy(0, -100,1)));
		
	}

	public void actualizarKrystallium(int krystallium, int progresoKrystallium) {
		zonaImperio.actualizarKrystallium(krystallium,progresoKrystallium);
		
	}
	
	public void actualizarFinancieros(int f) {

		zonaImperio.actualizarFinancieros(f);
		
	}
	public void actualizarGenerales(int g) {
		
		zonaImperio.actualizarGenerales(g);
		
	}

	public void mostrarAcciones(CartaDesarrollo c) {
		descartarBtn.remove();
		toggleReciclarConstruir(c);
		
	}

	public void mostrarDescartar(CartaDesarrollo c) {
		reciclarBtn.remove();
		construirBtn.remove();
		toggleDescartar(c);
		
	}

	public void setRecursosRestantes(int recursosRestantes) {
		estadoLabel.setText("Recursos restantes: " + recursosRestantes );
		
	}

	public void prepararDraft() {


		zonaPlanificacion.changeListeners(listenerDescartarDraft);
		botonDraft.setText("Cancelar");
		botonDraft.setColor(1f,0.4f,0.4f,0.8f);
		
	}

	public void retirarCarta(CartaDesarrollo c) {
		zonaPlanificacion.retirarCarta(c);
		
	}

	public void actualizarListeners() {
		zonaImperio.addListeners(financieroListener,krystalliumListener,generalListener,
				colocarKrystallium,convertirSobrantesEnKrystalliumListener);
		
	}

	public void seleccionarMayoria() {
		Group mayoria = new Group();
		mayoria.setBounds(botonFlujo.getX(), botonFlujo.getY(), botonFlujo.getWidth(), botonFlujo.getHeight());
		
		Image financiero = crearImagenRecurso(Recurso.FINANCIERO);
		mayoria.addActor(financiero);
		//ImageButton btfinanciero = new ImageButton(skin);
		financiero.setPosition(0, ((mayoria.getHeight()-financiero.getHeight())/2));
		Image general = crearImagenRecurso(Recurso.GENERAL);
		mayoria.addActor(general);
		general.setPosition((mayoria.getWidth()-general.getWidth()), (mayoria.getHeight()-general.getHeight())/2);
		
		stage.addActor(mayoria);
		//mayoria.addListener(listenerMayoriFinanciero);
		financiero.addListener(listenerMayoriFinanciero);
		general.addListener(listenerMayoriaGeneral);
		financiero.setTouchable(Touchable.enabled);
		general.setTouchable(Touchable.enabled);
		
	}

	public void pintarFlujo(Vector3 color) {
		botonFlujo.setColor(color.x,color.y,color.z,1);

		
	}

	public void avisoRecursosRestantes() {
		crearLabel(botonFlujo, "¡Debes colocar todos los recursos para psar de fase!");
		
	}
	
	//Pone la info debajo del boton
	public void moverInfoProduccion() {
		faseLabel.setPosition(botonFlujo.getX(), botonFlujo.getY()-faseLabel.getHeight());
		estadoLabel.setPosition(faseLabel.getX(), faseLabel.getY()-estadoLabel.getHeight());
		//faseLabel;
		
	}
	
	//Pone la info en la zona de imperio
	public void moverInfoPlanificacion() {
		faseLabel.setPosition(zonaConstruccion.getWidth(), ALTO*0.97f );
		estadoLabel.setPosition(faseLabel.getX(), faseLabel.getY()-estadoLabel.getHeight());
		
		
	}

}
