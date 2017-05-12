package com.ady.connect;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by ADY on 6.5.2017.
 */

public class SplashScreen implements Screen {

    private OrthographicCamera camera;
    private SpriteBatch batch;
    private Texture ady;
    private float time = 0f;
    private Texture pix;
    private PlayServices ply;
    private Connect game;
    private boolean signattemp = false;

    public SplashScreen(Connect game, PlayServices ply) {
        this.game = game;
        this.ply = ply;
    }

    @Override
    public void show() {
        camera = new OrthographicCamera(960f, 540f);
        camera.setToOrtho(false, 960f, 540f);
        camera.update();
        batch = new SpriteBatch();
        ady = new Texture(Gdx.files.internal("adylogo.png"));
        pix = new Texture(Gdx.files.internal("1pix.png"));
        ady.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT
                | (Gdx.graphics.getBufferFormat().coverageSampling ? GL20.GL_COVERAGE_BUFFER_BIT_NV : 0));
        batch.setProjectionMatrix(camera.combined);
        time += Gdx.graphics.getDeltaTime()*1.5f;
        batch.setColor(1, 1, 1, MathUtils.clamp((time - 1.5f) / 1f, 0f, 1f));
        batch.begin();
        batch.draw(ady, 405, 270, 150, 150);
        batch.end();

        batch.setColor(0, 0, 0, MathUtils.clamp(time - 7f, 0f, 1f));
        batch.begin();
        batch.draw(pix, 0, 0, 960, 540);
        batch.end();
        if (isInternetAvailable()) {
            if (!ply.isSignedIn()) {
                System.out.println("Thereisaninternet");
                if (!signattemp) {
                    //signattemp = true;
                   // ply.signIn();
                }
            }
        }
        if (time > 8.5f) {
            game.setScreen(new MainMenuScreen(game, ply));
        }
    }

    public boolean isInternetAvailable() {
        try {
            final InetAddress address;
            address = InetAddress.getByName("www.google.com");
            return !address.equals("");
        } catch (UnknownHostException e) {
        }
        return false;
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
