package com.ady.xpbooster;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by ADY on 6.5.2017.
 */

class SplashScreen implements Screen, InputProcessor {

    private boolean fun = false;
    private boolean button = false;
    private boolean capitalist = false;
    private boolean clicked50 = false;
    private boolean welcome = false;
    private NinePatch square;
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private BitmapFont font;
    private int[] number = new int[3];
    private int[] realnumber = new int[3];

    private PlayServices ply;
    private int randomnumber = 0;
    private int randomness = 1;
    private int score = 0;
    private int realscore = 0;
    private int clicked = 0;
    private float noconnection = 0f;
    private int scorecoef = 1;
    private int maxscore = 0;


    public SplashScreen(PlayServices ply) {

        this.ply = ply;
        Texture texture = new Texture(Gdx.files.internal("font0.png"), true);
        texture.setFilter(Texture.TextureFilter.MipMapLinearLinear, Texture.TextureFilter.Linear);
        font = new BitmapFont(Gdx.files.internal("font0.fnt"), new TextureRegion(texture), false);
        font.setUseIntegerPositions(false);
        Texture texture2 = new Texture(Gdx.files.internal("9patch3.png"), true);
        texture2.setFilter(Texture.TextureFilter.MipMapLinearLinear, Texture.TextureFilter.Linear);
        square = new NinePatch(texture2, 10, 10, 10, 10);
        number[0] = 0;
        number[1] = 0;
        number[2] = 0;
        realnumber[0] = 0;
        realnumber[1] = 0;
        realnumber[2] = 0;
        randomnumber = MathUtils.random(randomness, randomness * 3);
        Gdx.input.setInputProcessor(this);
        Preferences ady = Gdx.app.getPreferences("pref.dat");
        welcome = ady.getBoolean("welcome", false);
        clicked50 = ady.getBoolean("clicked", false);
        capitalist = ady.getBoolean("capitalist", false);
        button = ady.getBoolean("button", false);
        fun = ady.getBoolean("fun", false);
        maxscore = ady.getInteger("maxscore", 0);
        ply.showBannerAd();

    }

    @Override
    public void show() {
        camera = new OrthographicCamera(540f, 960f);
        camera.setToOrtho(false, 540f, 960f);
        camera.update();
        batch = new SpriteBatch();


    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        batch.setProjectionMatrix(camera.combined);
        if (ply.isSignedIn()) {
            if (!welcome) {
                ply.unlockAchievement("CgkI0sLIurUKEAIQAA");
                Preferences ady = Gdx.app.getPreferences("pref.dat");
                ady.putBoolean("welcome", true);
                ady.flush();
            }
        } else {
            ply.signIn();
        }

        font.setColor(Color.BLACK);
        batch.begin();
        font.getData().setScale(0.6f);
        font.draw(batch, "next number to add", 80.5f, 760);
        font.getData().setScale(1.6f);

        GlyphLayout ran = new GlyphLayout(font, "" + randomnumber);
        font.draw(batch, "" + randomnumber, 270 - ran.width / 2, 700);
        font.getData().setScale(1f);
        font.draw(batch, "score", 180, 350);

        ran.setText(font, "" + score);
        font.draw(batch, "" + score, 270 - ran.width / 2, 250);
        font.getData().setScale(0.5f);
        font.draw(batch, "reset", 48, 130);
        square.draw(batch, 30, 85, 120, 50);

        if (noconnection <= 0.01) {
            font.draw(batch, "watch ads", 180, 130);
        } else {
            font.setColor(0f, 0f, 0f, noconnection);
            font.draw(batch, "no wifi!", 215, 130);
            noconnection -= 0.01f;
            font.setColor(Color.BLACK);
        }
        if (scorecoef == 1) {
            font.getData().setScale(0.2f);
            font.draw(batch, "x2 score", 240, 150);
        }
        font.getData().setScale(0.5f);
        square.draw(batch, 170, 85, 200, 50);

        font.draw(batch, "high", 418, 130);
        square.draw(batch, 390, 85, 120, 50);

        font.draw(batch, "achievements", 150, 930);
        square.draw(batch, 130, 885, 275, 50);

        if (Gdx.input.justTouched()) {
            Vector3 touchPos = new Vector3(Gdx.input.getX(0), Gdx.input.getY(0), 0);
            camera.unproject(touchPos);
            int x = (int) touchPos.x;
            int y = (int) touchPos.y;
            if (x > 30 && x < 150 && y > 85 && y < 135) {
                if (!button) {
                    ply.unlockAchievement("CgkI0sLIurUKEAIQAQ");
                    Preferences ady = Gdx.app.getPreferences("pref.dat");
                    ady.putBoolean("button", true);
                    ady.flush();
                }

                ply.showInterstitialAd(null);
                clicked++;
                realscore = 0;
                realnumber[0] = 0;
                realnumber[1] = 0;
                realnumber[2] = 0;
                scorecoef = 1;
                randomness = 1;
                randomnumber = MathUtils.random(randomness, randomness * 3);
            } else if (x > 130 && x < 405 && y > 885 && y < 935) {
                if (!button) {
                    ply.unlockAchievement("CgkI0sLIurUKEAIQAQ");
                    Preferences ady = Gdx.app.getPreferences("pref.dat");
                    ady.putBoolean("button", true);
                    ady.flush();
                }
                ply.showAchievement();

            } else if (x > 390 && x < 510 && y > 85 && y < 135) {
                if (!button) {
                    ply.unlockAchievement("CgkI0sLIurUKEAIQAQ");
                    Preferences ady = Gdx.app.getPreferences("pref.dat");
                    ady.putBoolean("button", true);
                    ady.flush();
                }
                ply.showScore();
            } else if (x > 170 && x < 370 && y > 85 && y < 135) {
                if (!button) {
                    ply.unlockAchievement("CgkI0sLIurUKEAIQAQ");
                    Preferences ady = Gdx.app.getPreferences("pref.dat");
                    ady.putBoolean("button", true);
                    ady.flush();
                }
                if (isInternetAvailable()) {
                    ply.showRewardedVideo();
                } else {
                    noconnection = 1f;
                }
            }
        }


        font.getData().setScale(1f);
        // square.draw(batch, 60, 60, 60, 60);

        for (int i = 0; i < 3; i++) {

//number[i]+=i+1;
            GlyphLayout a = new GlyphLayout(font, "" + number[i]);
            font.draw(batch, "" + number[i], 90 + i * 180 - a.width / 2, 500);
            square.draw(batch, 80f + i * 180f - Math.max(a.width, 100) / 2, 420, Math.max(a.width, 100) + 15, 120);
            font.getData().setScale(0.25f);
            a.setText(font, "add to me");
            font.draw(batch, "add to me", 90 + i * 180 - a.width / 2, 528);
            font.getData().setScale(1f);
            if (number[i] < realnumber[i]) {
                number[i] += Math.max(1, (realnumber[i] - number[i]) / 30);
            }
            if (number[i] > realnumber[i]) {
                number[i] -= Math.max(1, (number[i] - realnumber[i]) / 30);
            }

            if (Gdx.input.justTouched()) {
                Vector3 touchPos = new Vector3(Gdx.input.getX(0), Gdx.input.getY(0), 0);
                camera.unproject(touchPos);
                int x = (int) touchPos.x;
                int y = (int) touchPos.y;
                if (x > 80f + i * 180f - Math.max(a.width, 100) / 2 && x < 95f + i * 180f + Math.max(a.width, 100) / 2 && y > 420 && y < 540) {
                    if (!button) {
                        ply.unlockAchievement("CgkI0sLIurUKEAIQAQ");
                        Preferences ady = Gdx.app.getPreferences("pref.dat");
                        ady.putBoolean("button", true);
                        ady.flush();
                    }
                    clicked++;
                    realnumber[i] += randomnumber;

                    if (randomness < 100) {
                        if (MathUtils.randomBoolean(0.3f)) randomness++;
                    }
                    randomnumber = MathUtils.random(Math.max(1, randomness / 2), randomness);
                    if (MathUtils.randomBoolean(0.2f)) {
                        if (Math.abs(realnumber[1] - realnumber[0]) < randomness && realnumber[1] != realnumber[0]) {
                            randomnumber = Math.abs(realnumber[1] - realnumber[0]);
                        }
                    }
                    if (MathUtils.randomBoolean(0.2f)) {
                        if (Math.abs(realnumber[2] - realnumber[0]) < randomness && realnumber[2] != realnumber[0]) {
                            randomnumber = Math.abs(realnumber[2] - realnumber[0]);
                        }
                    }
                    if (MathUtils.randomBoolean(0.2f)) {
                        if (Math.abs(realnumber[1] - realnumber[2]) < randomness && realnumber[1] != realnumber[2]) {
                            randomnumber = Math.abs(realnumber[1] - realnumber[2]);
                        }
                    }
                    System.out.println(randomness);
                }
            }

        }
        if (realnumber[0] > 0 && realnumber[0] == realnumber[1] && realnumber[1] == realnumber[2]) {
            realscore += realnumber[0] * scorecoef;
            if (!fun) {
                ply.unlockAchievement("CgkI0sLIurUKEAIQBA");
                Preferences ady = Gdx.app.getPreferences("pref.dat");
                ady.putBoolean("fun", true);
                ady.flush();
            }
            if (realscore > maxscore) {
                ply.submitScore(realscore);
                maxscore = realscore;
                Preferences ady = Gdx.app.getPreferences("pref.dat");
                ady.putInteger("maxscore", maxscore);
                ady.flush();

            }

            realnumber[0] = 0;
            realnumber[1] = 0;
            realnumber[2] = 0;
        }
        batch.end();

        if (score < realscore) {
            score += Math.max(1, (realscore - score) / 30);
        }
        if (score > realscore) {
            score -= Math.max(1, (score - realscore) / 30);
        }

        if (ply.isRewardEarned()) {
            if (!capitalist) {
                ply.unlockAchievement("CgkI0sLIurUKEAIQAg");
                Preferences ady = Gdx.app.getPreferences("pref.dat");
                ady.putBoolean("capitalist", true);
                ady.flush();
            }
            scorecoef = 2;
        }
        if (!clicked50) {
            if (clicked >= 50) {
                ply.unlockAchievement("CgkI0sLIurUKEAIQAw");
                Preferences ady = Gdx.app.getPreferences("pref.dat");
                ady.putBoolean("clicked", true);
                ady.flush();
            }
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
        square.getTexture().dispose();
        font.dispose();
        batch.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.BACK) {
            Gdx.app.exit();
            System.out.println("app killed");

        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
