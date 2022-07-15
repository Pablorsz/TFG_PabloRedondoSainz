package vistas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import presentadores.Presentador;

public class VistaInicio implements Screen{

	private Stage stage;
	private Image jugar;
	private Texture jugarImage;
	private Presentador presentador;
	final int ALTO = Gdx.graphics.getHeight();
	final int ANCHO = Gdx.graphics.getWidth();
	
	
	
	public VistaInicio(Presentador presentador) {
		this.presentador= presentador;

		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		
		TextureRegion background = new TextureRegion(new Texture(Gdx.files.internal("background.jpg")));
		Image fondo = new Image(background);
		fondo.setFillParent(true);
		stage.addActor(fondo);
		
		jugarImage = new Texture(Gdx.files.internal("jugar.png")); 
		jugar = new Image(new TextureRegion(jugarImage));
		float jugarHeight = ALTO*0.25f;
		float jugarWidth = jugarHeight*(jugarImage.getWidth()/jugarImage.getHeight());
		jugar.setBounds((ANCHO-jugarWidth)/2,(ALTO-jugarHeight)/2, jugarWidth,jugarHeight);
		jugar.setOrigin(Align.center);
		
		
		jugar.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {

				mostrarEscenarios();
			}
			
			@Override
			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
				System.out.println("enter");
				event.getTarget().addAction(Actions.scaleTo(1.1f, 1.1f,0.05f));
				super.enter(event, x, y, pointer, fromActor);
			}
			
			@Override
			public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
				System.out.println("exit");
				event.getTarget().addAction(Actions.scaleTo(1, 1,0.05f));
				super.exit(event, x, y, pointer, toActor);
			}
		});
		stage.addActor(jugar);
		
		
		
		
		
	}
	protected void mostrarEscenarios() {
		presentador.mostrarEscenarios();
		
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
	}	

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
