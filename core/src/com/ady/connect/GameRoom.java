package com.ady.connect;

import com.ady.connect.GameHelpers.ContainerControl;
import com.ady.connect.GameHelpers.ContainerOrganizer;
import com.ady.connect.GameHelpers.GameDrawer;
import com.ady.connect.GameHelpers.InputHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
public class GameRoom implements Screen {
	public SpriteBatch batch;
	final Connect game;
	private ContainerOrganizer Organizer;
	public GameDrawer Gamedrawer;
	public BitmapFont font;
	public Music gamemusic;
	private int stage = 60;
	private ContainerControl ContainerController;
	private OrthographicCamera camera;
	private int countdown = 60;
	private int countup = 0;
	private boolean gotomenu = false;
	private long time = System.currentTimeMillis();
	private boolean musiconoff = true;
	private float remaintime = 655.161f;
	private int gamemode = 0;
	private PlayServices playServices;

	public GameRoom(Connect game, int stage) {
		this.game = game;
		this.stage = stage;
		this.time = System.currentTimeMillis();

		init();
	}

	public GameRoom(Connect game, int stage, GameDrawer Gamedrawer, Music gamemusic, int count, int gamemode,PlayServices playServices) {
		this(game, stage);
		this.playServices=playServices;
		this.Gamedrawer = Gamedrawer;

		countdown = count;

		this.gamemusic = gamemusic;

		Preferences savegame = Gdx.app.getPreferences("save.dat");

		musiconoff = savegame.getBoolean("musiconoff");
        System.out.println(""+musiconoff);
		if (musiconoff) {
			gamemusic.play();
			gamemusic.setLooping(true);
		}
		savegame.flush();
		lanit(gamemode);

	}

	public GameRoom(Connect game, int stage, GameDrawer Gamedrawer, Music gamemusic, int count, float time, int score,PlayServices playServices) {
		this(game, stage, Gamedrawer, gamemusic, count, 1,playServices);
		remaintime = time;
		gamemode = 1;// timed mode
		ContainerController.score = score;
		ContainerController.scorefake = score;

	}

	public void init() {

		font = new BitmapFont(Gdx.files.internal("font.fnt"));
		font.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		font.setUseIntegerPositions(false);
		camera = new OrthographicCamera(960f, 540f);
		camera.setToOrtho(false, 960f, 540f);
		camera.update();
		
	}

	public void lanit(int gamemode) {
		Organizer = new ContainerOrganizer(600, 400);

		if (gamemode == 0) {

			Organizer.locate(stage % 3 + stage % 2 + 4 + stage / 30, stage, stage);


		} else {
			Organizer.locate(MathUtils.random(stage / 4 + 4, stage / 3 + 7), stage * 2 + 5, MathUtils.random(20000));

		}
		ContainerController = new ContainerControl();
		ContainerController.containers.addAll(Organizer.assign());
		InputHandler inputProcessor = new InputHandler(ContainerController, camera);
		Gdx.input.setInputProcessor(inputProcessor);
	}

	@Override
	public void show() {

		System.out.println("ady" + Gamedrawer.sprbg.getTexture());
		Gamedrawer.changeBackground();
		System.out.println("a" + Gamedrawer.sprbg.getTexture());
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		Gamedrawer.batch.setProjectionMatrix(camera.combined);
		Gamedrawer.drawBackground();

		if (Gdx.graphics.getFramesPerSecond() < 47 && Gamedrawer.sizesprite > 1f) {
			Gamedrawer.sizesprite -= 0.01f;
		} else if (Gdx.graphics.getFramesPerSecond() > 55 && Gamedrawer.sizesprite < 2f) {
			Gamedrawer.sizesprite += 0.01f;
		}
		if (ContainerController.changedlist) {
			ContainerController.changedlist = false;
			Gamedrawer.updateContainers(ContainerController.containers, ContainerController.lines);
			// System.out.println("aha girdi");
		}

		// System.out.println("//////////////////////////");
		// long time = System.nanoTime();
		// long timebegin = time;

		// System.out.println("background:" + (System.nanoTime() - time) /
		// 125f);
		// time = System.nanoTime();
		Gamedrawer.drawLines(); 
		// System.out.println("lines:" + (System.nanoTime() - time) / 125f);
		// time = System.nanoTime();
		Gamedrawer.drawActiveLine(ContainerController.dragstart, ContainerController.dragend);
		// System.out.println("activeline:" + (System.nanoTime() - time) /
		// 125f);
		// time = System.nanoTime();
		Gamedrawer.drawContainers(font);
		// System.out.println("Containers:" + (System.nanoTime() - time) /
		// 125f);
		// System.out.println("diff:" + (1000000f / 60f - (System.nanoTime() -
		// timebegin) / 125f));
		if (gamemode == 0) {
			if ((Gdx.input.isKeyJustPressed(Keys.SPACE) || Gdx.input.isTouched(1)) && ContainerController.ischanged) {
				dispose();
				int currentduration = (int) ((System.currentTimeMillis() - time) / 1000f);
				Preferences savegame = Gdx.app.getPreferences("save.dat");
				currentduration += savegame.getInteger("infiniteduration", 0);
				savegame.putInteger("infiniteduration", currentduration);
				long temp = savegame.getInteger("infinitetimes");
				savegame.putInteger("infinitetimes", (int) ++temp);
				savegame.flush();
				game.setScreen(new GameRoom(game, stage, Gamedrawer, gamemusic, 0, 0,playServices));
			}
			if (Gdx.input.isKeyPressed(Keys.BACK) || Gdx.input.isKeyPressed(Keys.A)) {
				int currentduration = (int) ((System.currentTimeMillis() - time) / 1000f);
				Preferences savegame = Gdx.app.getPreferences("save.dat");
				currentduration += savegame.getInteger("infiniteduration", 0);
				savegame.putInteger("infiniteduration", currentduration);
				long temp = savegame.getInteger("infinitetimes");
				savegame.putInteger("infinitetimes", (int) ++temp);
				savegame.flush();
				dispose();
				gotomenu = true;

			}
			if (ContainerController.isallzero) {

				int currentduration = (int) ((System.currentTimeMillis() - time) / 1000f);
				Preferences savegame = Gdx.app.getPreferences("save.dat");
				currentduration += savegame.getInteger("infiniteduration", 0);
				savegame.putInteger("infiniteduration", currentduration);
				long temp = savegame.getInteger("infinitetimes");
				savegame.putInteger("infinitetimes", (int) ++temp);
                savegame.putInteger("highestlevel", stage+1);
                playServices.submitLevel(stage+1);
				savegame.flush();
				game.setScreen(new GameRoom(game, stage +1, Gamedrawer, gamemusic, 0, 0,playServices));

			}
			Gamedrawer.drawLevelgui(stage);

		} else {
			ContainerController.update(Gamedrawer.speed);
			Gamedrawer.drawScore(ContainerController.score, remaintime);
			
			if (ContainerController.isonenotzero || ContainerController.isallzero) {
				dispose();
				Preferences savegame = Gdx.app.getPreferences("save.dat");
				int currentduration = (int) ((System.currentTimeMillis() - time) / 1000f);
				currentduration += savegame.getInteger("rushduration", 0);
				savegame.putInteger("rushduration", currentduration);
				long temp = savegame.getInteger("rushtimes");
				savegame.putInteger("rushtimes", (int) ++temp);
				savegame.flush();
				if (ContainerController.isallzero) {
					remaintime += MathUtils.log(2, Organizer.numbertotals)*2;
					System.out.println("totalarti:" + (MathUtils.log(1.5f, Organizer.numbertotals)));
				} else {
					System.out.println(
							"totaleksilen--------:" + (ContainerController.endscore * 40 / Organizer.numbertotals));
					remaintime -= ContainerController.endscore * 20 / Organizer.numbertotals;
				}
				remaintime += 2f;
				game.setScreen(new GameRoom(game, stage + 1, Gamedrawer, gamemusic, 0, remaintime,
						ContainerController.getScore(),playServices));
			}

			if (Gdx.input.isKeyPressed(Keys.BACK) || Gdx.input.isKeyPressed(Keys.A)) {
				int currentduration = (int) ((System.currentTimeMillis() - time) / 1000f);
				Preferences savegame = Gdx.app.getPreferences("save.dat");
				currentduration += savegame.getInteger("rushduration", 0);
				savegame.putInteger("rushduration", currentduration);
				long temp = savegame.getInteger("rushtimes");
				savegame.putInteger("rushtimes", (int) ++temp);
				savegame.flush();
				dispose();
				gotomenu = true;

			}
			if (remaintime <= 0) {
				
				int currentduration = (int) ((System.currentTimeMillis() - time) / 1000f);
				Preferences savegame = Gdx.app.getPreferences("save.dat");
				long highscore=savegame.getLong("highscore",0l);
				int score=ContainerController.getScore();
				if(score>highscore)
				{
					savegame.putLong("highscore", (long)score);
					playServices.submitScore(score);
				}
				currentduration += savegame.getInteger("rushduration", 0);
				savegame.putInteger("rushduration", currentduration);
				long temp = savegame.getInteger("rushtimes");
				savegame.putInteger("rushtimes", (int) ++temp);
				savegame.flush();
				dispose();
				gotomenu = true;
				
				
				
				
			}
			else
			{
				remaintime -= delta;
			}
		}

		if (gotomenu) {

			if (countup > 60) {
				gamemusic.stop();
				dispose();
				game.setScreen(new MainMenuScreen(game, Gamedrawer,gamemode,playServices));

			}
			countup = Gamedrawer.endScreen(countup, gamemusic, 1);

		}

		if (countdown >= 0) {
			countdown = Gamedrawer.endScreen(countdown, gamemusic, -1);

		}
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
