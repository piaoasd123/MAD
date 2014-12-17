package com.mygdx.screen;

import java.io.IOException;

import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.examples.chat.ChatServer;
import com.esotericsoftware.kryonet.examples.chat.Network;
import com.esotericsoftware.kryonet.examples.chat.Network.ChatMessage;
import com.esotericsoftware.kryonet.examples.chat.Network.JoinLobbyRequest;
import com.esotericsoftware.kryonet.examples.chat.Network.UpdateNames;
import com.mygdx.game.Assets;
import com.mygdx.game.Hanto;
import com.mygdx.tween.ActorAccessor;

public class PlayOption implements Screen {
	Client client;
	private Stage stage;
	private Table table;
	private TextButton buttonSinglePlayer, buttonMultiplayerLocal, buttonMultiplayerNetwork;
	private Label heading;
	private TweenManager tweenManager;
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act(delta);
		stage.draw();

		tweenManager.update(delta);
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
		table.invalidateHierarchy();
		table.setSize(width, height);
	}

	@Override
	public void show() {
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		Assets.loadMainMenuOrSettings();
		table = new Table(Assets.menuSkin);
		table.setFillParent(true);
		table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		// creating buttons
		buttonSinglePlayer = new TextButton("Singleplayer", Assets.menuSkin);
		buttonSinglePlayer.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				((Game) Gdx.app.getApplicationListener())
						.setScreen(new GameScreen());
			}
		});
		buttonSinglePlayer.pad(50);
		buttonMultiplayerLocal = new TextButton("Multiplayer Local", Assets.menuSkin);
		buttonMultiplayerLocal.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {

//				((Game) Gdx.app.getApplicationListener())
//						.setScreen(new GameScreen());
			}
		});
		buttonMultiplayerLocal.pad(50);
		
		buttonMultiplayerNetwork = new TextButton("Multiplayer Network", Assets.menuSkin);
		buttonMultiplayerNetwork.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				((Game) Gdx.app.getApplicationListener())
						.setScreen(new PlayMultiplayer());
			}
		});
		buttonMultiplayerNetwork.pad(50);
		
		// creating heading
		heading = new Label(Hanto.TITLE, Assets.menuSkin);
		heading.setFontScale(6);

		// putting stuff together
		table.add(heading);
		table.getCell(heading).spaceBottom(300);
		table.row();
		table.add(buttonSinglePlayer);
		table.getCell(buttonSinglePlayer).spaceBottom(100);
		table.row();
		table.add(buttonMultiplayerLocal);
		table.getCell(buttonMultiplayerLocal).spaceBottom(100);
		table.row();
		table.add(buttonMultiplayerNetwork);
		stage.addActor(table);

		// create animation
		tweenManager = new TweenManager();
		Tween.registerAccessor(Actor.class, new ActorAccessor());

		// heading color animation
		Timeline.createSequence()
				.beginSequence()
				.push(Tween.to(heading, ActorAccessor.RGB, 0.5f)
						.target(0, 0, 1))
				.push(Tween.to(heading, ActorAccessor.RGB, 0.5f)
						.target(0, 1, 0))
				.push(Tween.to(heading, ActorAccessor.RGB, 0.5f)
						.target(1, 0, 0))
				.push(Tween.to(heading, ActorAccessor.RGB, 0.5f)
						.target(0, 1, 1))
				.push(Tween.to(heading, ActorAccessor.RGB, 0.5f)
						.target(1, 1, 0))
				.push(Tween.to(heading, ActorAccessor.RGB, 0.5f)
						.target(1, 0, 1))
				.push(Tween.to(heading, ActorAccessor.RGB, 0.5f)
						.target(1, 1, 1)).end().repeat(Tween.INFINITY, 0)
				.start(tweenManager);

		// heading and buttons fade-in
		Timeline.createSequence()
				.beginSequence()
				.push(Tween.set(buttonSinglePlayer, ActorAccessor.ALPHA).target(0))
				.push(Tween.set(buttonMultiplayerLocal, ActorAccessor.ALPHA).target(0))
				.push(Tween.set(buttonMultiplayerNetwork, ActorAccessor.ALPHA).target(0))
				.push(Tween.from(heading, ActorAccessor.ALPHA, 0.5f).target(0))
				.push(Tween.to(buttonSinglePlayer, ActorAccessor.ALPHA, 0.25f)
						.target(1))
				.push(Tween.to(buttonMultiplayerLocal, ActorAccessor.ALPHA, 0.25f)
						.target(1))
				.push(Tween.to(buttonMultiplayerNetwork, ActorAccessor.ALPHA, 0.25f)
						.target(1)).end().start(tweenManager);

		// table fade-in
		Tween.from(table, ActorAccessor.ALPHA, 0.5f).target(0);
		Tween.from(table, ActorAccessor.Y, 0.5f)
				.target(Gdx.graphics.getHeight() / 8).start(tweenManager);

		tweenManager.update(Gdx.graphics.getDeltaTime());
	}

	@Override
	public void hide() {
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
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
