package vistas;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import modelo.Escenario;
import presentadores.Presentador;

public class VistaEscenarios implements Screen {
	private Stage stage;
	private Skin skin;
	private ArrayList<Escenario> escenarios;
	private Presentador presentador;
	private int ALTO;
	private int ANCHO;
	public VistaEscenarios(Presentador presentador,ArrayList<Escenario> escenarios) {
		this.escenarios = escenarios;
		this.presentador = presentador;

		ALTO = Gdx.graphics.getHeight();
		ANCHO = Gdx.graphics.getWidth();
		stage = new Stage();
		skin = new Skin(Gdx.files.internal("uiskin.json"));

		TextureRegion background = new TextureRegion(new Texture(Gdx.files.internal("background.jpg")));
		Image fondo = new Image(background);
		fondo.setFillParent(true);
		stage.addActor(fondo);
		//Table tabla = new Table();
		//tabla.setFillParent(true);
		
		

		Group groupEscenarios= new Group();
		float gap = ALTO*0.05f;
		float escenarioWidth = ANCHO*0.18f;
		float escenarioHeight = ALTO*0.4f;
		groupEscenarios.setBounds((ANCHO-(escenarioWidth*4)-gap*3)/2,(ALTO-(escenarioHeight*2)-gap)/2,
				(escenarioWidth*4+gap*3), escenarioHeight*2+gap);
		stage.addActor(groupEscenarios);
		for (Escenario escenario : this.escenarios) {
			
			TextButton buttonEscenario = new TextButton("ESCENARIO "+escenario.getId() +":\n "+escenario.getNombre(), skin);
			buttonEscenario.setName(String.valueOf(escenario.getId()));
			int fila = 1;
			if(escenario.getId()>4) {
				fila = 0;
			}
			
			buttonEscenario.setBounds((gap+escenarioWidth)*((escenario.getId()-1)%4), (gap+escenarioHeight)*fila, escenarioWidth, escenarioHeight);
			buttonEscenario.setColor(0,	0.8f, 1, 0.85f);
			groupEscenarios.addActor(buttonEscenario);
			buttonEscenario.setOrigin(Align.center);
			buttonEscenario.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					comenzarPartida(event);
					
				}
				@Override
				public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
					event.getTarget().addAction(Actions.scaleTo(1.1f, 1.1f,0.05f));
					super.enter(event, x, y, pointer, fromActor);
				}
				
				@Override
				public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
					event.getTarget().addAction(Actions.scaleTo(1, 1,0.05f));
					super.exit(event, x, y, pointer, toActor);
				}
			});
		}
		Gdx.input.setInputProcessor(stage);
		
	}
	protected void comenzarPartida(InputEvent event) {

		this.presentador.comenzarPartida(event.getTarget().getParent().getName());
		
	}
	@Override
	public void show() {
		System.out.println("Vista escenarios show");

	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.7f, 0.7f, 0.7f, 1.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.draw();
		
	}
	
	@Override
	public void resize(int width, int height) {

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

}
