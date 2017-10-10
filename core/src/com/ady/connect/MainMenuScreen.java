package com.ady.connect;

import com.ady.connect.GameHelpers.GameDrawer;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class MainMenuScreen implements Screen {

    final Connect game;
    private SpriteBatch batch;
    private Texture texturecontainer;
    private Texture bg;
    private Texture rectbg;
    private Texture textureline;
    private Texture texturelinesmall;
    private GameDrawer Gamedrawer;
    private Texture brain;
    private Texture braing;
    private float alphabrain = 0.5f;
    private float alphabrainchange = 0.005f;
    private Texture brainb;
    private Texture clock;
    private Vector2[] iconpos = new Vector2[6];
    private Texture clockg;
    private Texture clockb;
    private Texture options;
    private Texture optionsg;
    private Texture optionsb;
    private OrthographicCamera camera;
    private Music menumusic;
    private int rotationicon = 1;
    private Rectangle rect;
    private Rectangle rect2;
    private float plussize = 50f;
    private float plussizeinc = 0.1f;
    private float plussizechange = 0.02f;
    private BitmapFont font;
    private String string = "endless";
    private BitmapFont font2;
    private float randomsign = 1f;
    public long highscore = 1485;
    public int rushtimes = 17;
    public int rushduration = 13;
    public int highestlevel = 18;
    public int infinitetimes = 16;
    public int infiniteduration = 12;
    public int stringchange = 0;
    boolean fpsAlert = false;

    public boolean soundonoff = true;
    public boolean musiconoff = true;
    private Texture leaderboard;
    private Sound soundchange;

    public String[][] getStrings() {

        String[][] string = {
                {"highscore  " + highscore, "played " + rushtimes + " times", "playtime " + rushduration + " mins"},
                {"level " + highestlevel, "played " + infinitetimes + " times",
                        "playtime " + infiniteduration + " mins"}};

        return string;
    }

    private Texture soundon;
    private Texture soundoff;
    private Rectangle rect3;

    private Texture musicon;
    private Texture musicoff;

    private Rectangle rect4;
    private Rectangle rect5;
    private Music gamemusic;
    private boolean gotoendless = false;
    private int countup;
    private int countdown = 60;
    private Texture badq;
    private Texture goodq;
    private boolean quality = true;
    private boolean quality2 = true;
    private Rectangle rect6;
    private Texture medq;
    private Texture play;
    private float showplay = 0.4f;
    private float showplaychange = 0.005f;
    private boolean gotorush = false;
    private PlayServices playServices;

    public MainMenuScreen(Connect game, PlayServices playServices) {
        this(game);
        this.playServices = playServices;


    }

    public MainMenuScreen(Connect game) {

        this.game = game;
        init();
        Gamedrawer = new GameDrawer(bg, rectbg, texturelinesmall, texturecontainer);
        Gamedrawer.speed = 0.5f;
        switch (rotationicon) {
            case 0:
                Gamedrawer.changeBgsprite(options);
                string = "options";
                break;
            case 1:
                Gamedrawer.changeBgsprite(clock);
                string = "rush";
                break;
            case 2:
                Gamedrawer.changeBgsprite(brain);
                string = "endless";
                break;

        }
        Gamedrawer.quality = quality;
        Gamedrawer.quality2 = quality2;
    }

    public MainMenuScreen(Connect game, GameDrawer Gamedrawer, int gamemode, PlayServices playServices) {
        this(game, playServices);
        this.Gamedrawer = Gamedrawer;
        this.Gamedrawer.speed = 0.5f;
        // this.Gamedrawer.changeBackground();
        rotationicon = 2 - gamemode;
        countdown = 60;
        switch (rotationicon) {
            case 0:
                Gamedrawer.changeBgsprite(options);
                string = "options";
                break;
            case 1:
                Gamedrawer.changeBgsprite(clock);
                string = "rush";
                break;
            case 2:
                Gamedrawer.changeBgsprite(brain);
                string = "endless";
                break;

        }
    }

    private void init() {
        batch = new SpriteBatch();
        texturecontainer = new Texture(Gdx.files.internal("circle69.png"));
        bg = new Texture(Gdx.files.internal("bg.png"));
        rectbg = new Texture(Gdx.files.internal("clock.png"));
        rectbg.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        texturecontainer.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        textureline = new Texture(Gdx.files.internal("line200.png"));
        texturelinesmall = new Texture(Gdx.files.internal("line40.png"), true);
        textureline.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        texturelinesmall.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        brain = new Texture(Gdx.files.internal("brain.png"));
        brainb = new Texture(Gdx.files.internal("brainb.png"));
        braing = new Texture(Gdx.files.internal("braing.png"));
        brain.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        brainb.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        braing.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        font = new BitmapFont(Gdx.files.internal("fontmenu.fnt"));
        font.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
        // font.getData().setScale(3);
        font.setUseIntegerPositions(false);
        font2 = new BitmapFont(Gdx.files.internal("fontmenuin.fnt"));
        font2.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
        // font.getData().setScale(3);
        font2.setUseIntegerPositions(false);
        clock = new Texture(Gdx.files.internal("clock.png"));
        clock.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        clockg = new Texture(Gdx.files.internal("clockg.png"));
        clockg.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        clockb = new Texture(Gdx.files.internal("clockb.png"));
        clockb.setFilter(TextureFilter.Linear, TextureFilter.Linear);

        options = new Texture(Gdx.files.internal("options.png"));
        options.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        optionsg = new Texture(Gdx.files.internal("optionsg.png"));
        optionsg.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        optionsb = new Texture(Gdx.files.internal("optionsb.png"));
        optionsb.setFilter(TextureFilter.Linear, TextureFilter.Linear);

        soundon = new Texture(Gdx.files.internal("soundon.png"));
        soundoff = new Texture(Gdx.files.internal("soundoff.png"));
        soundon.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        soundoff.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        soundchange = Gdx.audio.newSound(Gdx.files.internal("changemode.mp3"));
        musicon = new Texture(Gdx.files.internal("musicon.png"));
        musicoff = new Texture(Gdx.files.internal("musicoff.png"));
        musicon.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        badq = new Texture(Gdx.files.internal("badq.png"));
        goodq = new Texture(Gdx.files.internal("goodq.png"));
        medq = new Texture(Gdx.files.internal("medq.png"));
        play = new Texture(Gdx.files.internal("play.png"));
        play.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        goodq.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        badq.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        medq.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        musicoff.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        leaderboard = new Texture(Gdx.files.internal("leaderboard.png"));
        leaderboard.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        iconpos[0] = new Vector2(100, 440);
        iconpos[1] = new Vector2(860, 440);
        iconpos[2] = new Vector2(480, 270);
        iconpos[3] = iconpos[0].cpy();
        iconpos[4] = iconpos[1].cpy();
        iconpos[5] = iconpos[2].cpy();
        rect = new Rectangle(iconpos[3].x - 100, iconpos[3].y - 100, 200, 200);
        rect2 = new Rectangle(iconpos[4].x - 100, iconpos[4].y - 100, 200, 200);
        rect3 = new Rectangle(100, 100, 150, 150);
        rect4 = new Rectangle(710, 100, 150, 150);

        rect5 = new Rectangle(iconpos[5].x - 100, iconpos[5].y - 100, 200, 200);
        rect6 = new Rectangle(420, 40, 120, 120);
        camera = new OrthographicCamera(960f, 540f);
        camera.setToOrtho(false, 960f, 540f);
        camera.update();

        Preferences savegame = Gdx.app.getPreferences("save.dat");
        highscore = savegame.getLong("highscore", 0l);
        System.out.println(highscore + "asd");
        rushtimes = savegame.getInteger("rushtimes", 0);
        rushduration = savegame.getInteger("rushduration", 0) / 60;
        highestlevel = savegame.getInteger("highestlevel", 0);
        infinitetimes = savegame.getInteger("infinitetimes", 0);
        infiniteduration = savegame.getInteger("infiniteduration", 0) / 60;
        soundonoff = savegame.getBoolean("soundonoff", true);
        musiconoff = savegame.getBoolean("musiconoff", true);
        quality = savegame.getBoolean("quality", true);
        quality2 = savegame.getBoolean("quality2", true);
        if (rushtimes == 0 && infinitetimes == 0) {
            savegame.putBoolean("soundonoff", true);
            savegame.putBoolean("musiconoff", true);
        }
        menumusic = Gdx.audio.newMusic(Gdx.files.internal("menumusic.mp3"));
        if (musiconoff) {
            menumusic.play();
            menumusic.setLooping(true);
        }
        gamemusic = Gdx.audio.newMusic(Gdx.files.internal("gamemusic.mp3"));

    }

    @Override
    public void show() {
        // TODO Auto-generated method stub
        if (isInternetAvailable()) {
            playServices.showBannerAd();
        }
    }

    @Override
    public void render(float delta) {
        // TODO Auto-generated method stub

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT
                | (Gdx.graphics.getBufferFormat().coverageSampling ? GL20.GL_COVERAGE_BUFFER_BIT_NV : 0));
        batch.setProjectionMatrix(camera.combined);
        Gamedrawer.batch.setProjectionMatrix(camera.combined);
        Gamedrawer.drawBackground();

        if (alphabrain >= 1f) {
            alphabrainchange *= -1;
        }
        if (showplay >= 1f) {
            showplaychange *= -1;
        }
        if (showplay <= -1f) {
            showplaychange *= -1;
        }
        if (alphabrain <= 0.05f) {
            randomsign = MathUtils.randomSign();
            alphabrainchange *= -1;
            stringchange++;
        }
        if (plussizeinc >= 1f || plussizeinc <= 0.05f) {
            plussizechange *= -1;
        }
        plussizeinc += plussizechange;
        alphabrain += alphabrainchange;
        showplay += showplaychange;
        // Gamedrawer.sizesprite = 2f+alphabrain*2;

        batch.begin();
        //font.getData().setScale(1f + alphabrain / 1.5f);
        GlyphLayout glyp = new GlyphLayout(font, string);
        //font.setColor(Color.BLACK);
        //font.draw(batch, string, 480 - glyp.width / 2, 450 + glyp.height / 2 + alphabrain * 30 * randomsign);
        font.getData().setScale(1f);
        font2.getData().setScale(1f);
        glyp = new GlyphLayout(font, string);
        font.setColor(Gamedrawer.HSV_to_RGB((Gamedrawer.hue + 180) % 360, 80, 240, alphabrain));
        // font.setColor(Color.WHITE);
        font.draw(batch, string, 480 - glyp.width / 2, 450 + glyp.height / 2);
        //font.draw(batch, string, 481 - glyp.width / 2, 450 + glyp.height / 2);
        font2.setColor(Gamedrawer.HSV_to_RGB(Gamedrawer.hue, 240, 240, 1f));

        font2.draw(batch, string, 480 - glyp.width / 2, 450 + glyp.height / 2);

        batch.end();

        float huebrain = 0;
        if (rotationicon == 1) {
            huebrain = 20;
            plussize = 0f;
            batch.begin();
            String temp="endless mode";
            font.getData().setScale(0.3f);
            glyp = new GlyphLayout(font, temp);
            font.setColor(Color.WHITE);
            font.draw(batch, temp,  iconpos[0].x - glyp.width / 2,  iconpos[0].y+70);
            batch.end();
        } else if (rotationicon == 0) {
            huebrain = -20;
            plussize = 0f;
            batch.begin();
            String temp="endless mode";
            font.getData().setScale(0.3f);
            glyp = new GlyphLayout(font, temp);
            font.setColor(Color.WHITE);
            font.draw(batch, temp,  iconpos[0].x - glyp.width / 2,  iconpos[0].y+70);
            batch.end();
        } else {
            plussize = 85f + plussizeinc * 30f;

            batch.begin();
            font2.getData().setScale(0.6f);
            glyp = new GlyphLayout(font2, getStrings()[1][stringchange % 3]);
            font2.setColor(Gamedrawer.HSV_to_RGB(Gamedrawer.hue, 50, 240, alphabrain + 0.1f));
            font2.draw(batch, getStrings()[1][stringchange % 3], 480 - glyp.width / 2, 100 + glyp.height / 2);
            batch.setColor(Gamedrawer.HSV_to_RGB(Gamedrawer.hue, 240, 240, 1f));
            batch.draw(leaderboard, 100, 100, 150, 150);
            batch.end();
        }
        batch.begin();
        batch.setColor(Color.BLACK);
        // batch.draw(brainb, iconpos[0].x - 90 - plussize / 2f, iconpos[0].y - 90 - plussize / 2f, 180 + plussize,
        //         180 + plussize);
        batch.setColor(Gamedrawer.HSV_to_RGB(Gamedrawer.hue + huebrain, 240, 200, 1f - alphabrain));
        batch.draw(brain, iconpos[0].x - 90 - plussize / 2f, iconpos[0].y - 90 - plussize / 2f, 180 + plussize,
                180 + plussize);
        batch.setColor(Gamedrawer.HSV_to_RGB(Gamedrawer.hue + huebrain, 240, 200, alphabrain));
        batch.draw(braing, iconpos[0].x - 90 - plussize / 2f, iconpos[0].y - 90 - plussize / 2f, 180 + plussize,
                180 + plussize);
       // batch.setColor(Gamedrawer.HSV_to_RGB(Gamedrawer.hue + huebrain, 240, 240, showplay));

        batch.end();

        ;
        ///
        if (rotationicon == 1) {
            plussize = 100f;
        } else {
            plussize = 0f;
        }
        float hueclock = 0;
        if (rotationicon == 0) {
            hueclock = 20;
            plussize = 0;
            batch.begin();
            String temp="rush mode";
            font.getData().setScale(0.3f);
            glyp = new GlyphLayout(font, temp);
            font.setColor(Color.WHITE);
            font.draw(batch, temp, iconpos[1].x - glyp.width / 2, iconpos[1].y+80);
            batch.end();
        } else if (rotationicon == 2) {
            hueclock = -20;
            plussize = 0;
            batch.begin();
            String temp="rush mode";
            font.getData().setScale(0.3f);
            glyp = new GlyphLayout(font, temp);
            font.setColor(Color.WHITE);
            font.draw(batch, temp,  iconpos[1].x - glyp.width / 2,  iconpos[1].y+80);
            batch.end();
        } else {
            // High score
            plussize = 85f + plussizeinc * 30f;
            batch.begin();
            font2.getData().setScale(0.6f);
            glyp = new GlyphLayout(font2, getStrings()[0][stringchange % 3]);
            font2.setColor(Gamedrawer.HSV_to_RGB(Gamedrawer.hue, 50, 240, alphabrain + 0.1f));
            font2.draw(batch, getStrings()[0][stringchange % 3], 480 - glyp.width / 2, 100 + glyp.height / 2);

            batch.setColor(Gamedrawer.HSV_to_RGB(Gamedrawer.hue, 240, 240, 1f));
            batch.draw(leaderboard, 100, 100, 150, 150);
            batch.end();

        }
        batch.begin();
        batch.setColor(Color.BLACK);
        // batch.draw(clockb, iconpos[1].x - 100 - plussize / 2f, iconpos[1].y - 100 - plussize / 2f, 200 + plussize,
        //         200 + plussize);
        batch.setColor(Gamedrawer.HSV_to_RGB(Gamedrawer.hue + hueclock, 240, 200, 1f - alphabrain));
        batch.draw(clock, iconpos[1].x - 100 - plussize / 2f, iconpos[1].y - 100 - plussize / 2f, 200 + plussize,
                200 + plussize);
        batch.setColor(Gamedrawer.HSV_to_RGB(Gamedrawer.hue + hueclock, 240, 200, alphabrain));
        batch.draw(clockg, iconpos[1].x - 100 - plussize / 2f, iconpos[1].y - 100 - plussize / 2f, 200 + plussize,
                200 + plussize);

      //  batch.setColor(Gamedrawer.HSV_to_RGB(Gamedrawer.hue + hueclock, 240, 240, showplay));

        batch.end();
        if (rotationicon == 0) {
            plussize = 100f;
        } else {
            plussize = 0f;
        }

        float hueoptions = 0;
        if (rotationicon == 2) {
            hueoptions = 20;
            batch.begin();
            batch.setColor(Gamedrawer.HSV_to_RGB(Gamedrawer.hue, 240, 240, 1f));
            batch.draw(play, 710, 100, 150, 150);
            batch.end();
        } else if (rotationicon == 1) {
            hueoptions = -20;
            batch.begin();
            batch.setColor(Gamedrawer.HSV_to_RGB(Gamedrawer.hue, 240, 240, 1f));
            batch.draw(play, 710, 100, 150, 150);
            batch.end();
        } else {

            batch.begin();
            String temp = "";
            if (soundonoff) {
                batch.setColor(Gamedrawer.HSV_to_RGB(Gamedrawer.hue, 240, 240, 1f));
                batch.draw(soundon, 100, 100, 150, 150);
                temp = "sound on";
            } else {
                batch.setColor(Gamedrawer.HSV_to_RGB(Gamedrawer.hue + hueoptions, 240, 150, 0.6f));
                batch.draw(soundoff, 100, 100, 150, 150);
                temp = "sound off";
            }

            font.getData().setScale(0.4f);

            glyp = new GlyphLayout(font, temp);
            font.setColor(Color.WHITE);
            font.draw(batch, temp, 175 - glyp.width / 2, 100);

            if (musiconoff) {
                batch.setColor(Gamedrawer.HSV_to_RGB(Gamedrawer.hue + hueoptions, 240, 240, 1f));
                batch.draw(musicon, 710, 100, 150, 150);
                temp = "music on";
            } else {
                batch.setColor(Gamedrawer.HSV_to_RGB(Gamedrawer.hue + hueoptions, 240, 150, 0.6f));
                batch.draw(musicoff, 710, 100, 150, 150);
                temp = "music off";
            }
            glyp = new GlyphLayout(font, temp);
            font.setColor(Color.WHITE);
            font.draw(batch, temp, 785 - glyp.width / 2, 100);

            if (quality) {
                batch.setColor(Gamedrawer.HSV_to_RGB(Gamedrawer.hue + hueoptions, 240, 240, 1f));
                batch.draw(goodq, 420, 40, 120, 120);
                temp = "best quality";
            } else if (quality2) {
                batch.setColor(Gamedrawer.HSV_to_RGB(Gamedrawer.hue + hueoptions, 240, 240, 1f));
                batch.draw(medq, 420, 40, 120, 120);
                temp = "good quality";
            } else {
                batch.setColor(Gamedrawer.HSV_to_RGB(Gamedrawer.hue + hueoptions, 240, 240, 1f));
                batch.draw(badq, 420, 40, 120, 120);
                temp = "poor quality";
            }

            glyp = new GlyphLayout(font, temp);
            font.setColor(Color.WHITE);
            font.draw(batch, temp, 480 - glyp.width / 2, 30);
            batch.end();
        }
        batch.begin();
        batch.setColor(Color.BLACK);

        // batch.draw(optionsb, iconpos[2].x - 100 - plussize / 2f, iconpos[2].y - 100 - plussize / 2f, 200 + plussize,
        //          200 + plussize);
        batch.setColor(Gamedrawer.HSV_to_RGB(Gamedrawer.hue + hueoptions, 240, 200, 1f - alphabrain));
        batch.draw(options, iconpos[2].x - 100 - plussize / 2f, iconpos[2].y - 100 - plussize / 2f, 200 + plussize,
                200 + plussize);
        batch.setColor(Gamedrawer.HSV_to_RGB(Gamedrawer.hue + hueoptions, 240, 200, alphabrain));
        batch.draw(optionsg, iconpos[2].x - 100 - plussize / 2f, iconpos[2].y - 100 - plussize / 2f, 200 + plussize,
                200 + plussize);
        batch.end();
    if(rotationicon!=0)
    {
        batch.begin();
        String temp="options";
        font.getData().setScale(0.3f);
        glyp = new GlyphLayout(font, temp);
        font.setColor(Color.WHITE);
        font.draw(batch, temp, iconpos[2].x - glyp.width / 2, iconpos[2].y+80);
        batch.end();
    }
        if (Gdx.graphics.getFramesPerSecond() < 47 && Gamedrawer.sizesprite > 1f) {
            Gamedrawer.sizesprite -= 0.01f;
        } else if (Gdx.graphics.getFramesPerSecond() > 55 && Gamedrawer.sizesprite < 3f) {
            Gamedrawer.sizesprite += 0.01f;
        }
        if (Gdx.input.isKeyPressed(Keys.BACK)) {

            //playServices.signOut();

            Gdx.app.exit();
        }

        if (Gdx.input.justTouched()) {
            Vector3 pos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);

            camera.unproject(pos);
            if (rotationicon == 0) {
                if (rect3.contains(pos.x, pos.y)) {
                    soundonoff = !soundonoff;
                    //playServices.submitLevelEndless(15);//----------------------
                    Preferences savegame = Gdx.app.getPreferences("save.dat");
                    savegame.putBoolean("soundonoff", soundonoff);
                    savegame.flush();
                } else if (rect4.contains(pos.x, pos.y)) {
                    musiconoff = !musiconoff;
                    Preferences savegame = Gdx.app.getPreferences("save.dat");
                    savegame.putBoolean("musiconoff", musiconoff);
                    savegame.flush();
                    if (!musiconoff) {

                        menumusic.pause();
                    } else {
                        if (!menumusic.isPlaying()) {
                            menumusic.play();
                            menumusic.setLooping(true);
                        }
                    }
                } else if (rect6.contains(pos.x, pos.y)) {

                    if (quality) {
                        quality = false;
                    } else if (quality2) {
                        quality2 = false;
                        //System.out.println("asdasdasdasf");
                    } else {
                        quality = true;
                        quality2 = true;
                    }
                    Gamedrawer.quality = quality;
                    Gamedrawer.quality2 = quality2;
                    System.out.println(quality + " " + quality2);
                    Preferences savegame = Gdx.app.getPreferences("save.dat");
                    savegame.putBoolean("quality", quality);
                    savegame.putBoolean("quality2", quality2);
                    savegame.flush();
                }
            } else if (rotationicon == 1) {
                if (rect5.contains(pos.x, pos.y) || rect4.contains(pos.x,pos.y)) {
                    gotorush = true;
                } else if (rect3.contains(pos.x, pos.y)) {
                    playServices.showScore();
                }
            } else {
                if (rect5.contains(pos.x, pos.y)|| rect4.contains(pos.x,pos.y)) {
                    gotoendless = true;

                } else if (rect3.contains(pos.x, pos.y)) {
                    playServices.showLevel();
                }
            }

            System.out.println(pos.x + "-" + (pos.y));
            if (rect.contains(pos.x, pos.y)) {
                if (soundonoff) {
                    soundchange.play(0.6f);
                }
                Gamedrawer.hue += 340;
                rotationicon += 2;
                alphabrain = 0.1f;
                alphabrainchange = 0.005f;
                stringchange = 0;
                showplay = -0.5f;
            } else if (rect2.contains(pos.x, pos.y)) {
                if (soundonoff) {
                    soundchange.play(0.6f);
                }
                Gamedrawer.hue += 20;
                rotationicon += 1;
                alphabrain = 0.1f;
                alphabrainchange = 0.005f;
                stringchange = 0;
                showplay = -0.5f;
            }
            rotationicon %= 3;
            switch (rotationicon) {
                case 0:
                    Gamedrawer.changeBgsprite(options);
                    string = "options";
                    break;
                case 1:
                    Gamedrawer.changeBgsprite(clock);
                    string = "rush";
                    break;
                case 2:
                    Gamedrawer.changeBgsprite(brain);
                    string = "endless";
                    break;

            }
            System.out.println(rotationicon);
        }
        for (int i = 0; i < 3; i++) {
            if (iconpos[i].dst2(iconpos[(i + rotationicon) % 3 + 3]) > 16) {
                System.out.println(" uzak");
                iconpos[i].lerp(iconpos[(i + rotationicon) % 3 + 3], 0.3f);
            }
            else
            {
                iconpos[i].set(iconpos[(i + rotationicon) % 3 + 3]);
            }
        }

        if (gotoendless) {
            // countup++;
            playServices.hideBannerAd();
            if (countup > 60) {
                System.out.println("Endless");
                Preferences savegame = Gdx.app.getPreferences("save.dat");
                savegame.putInteger("infinitetimes", (int) ++infinitetimes);
                savegame.flush();

                System.out.println(infinitetimes);

                dispose();
                Gamedrawer.speed = 1f;
                game.setScreen(new GameRoom(this.game, highestlevel, Gamedrawer, gamemusic, 60, 0, playServices));

            }
            countup = Gamedrawer.endScreen(countup, menumusic, 1);

        } else if (gotorush) {
            playServices.hideBannerAd();
            if (countup > 60) {
                System.out.println("Rush");
                Preferences savegame = Gdx.app.getPreferences("save.dat");
                savegame.putInteger("rushtimes", (int) ++rushtimes);
                savegame.flush();
                dispose();
                Gamedrawer.speed = 1f;
                game.setScreen(new GameRoom(this.game, 0, Gamedrawer, gamemusic, 60, 15f, 0, playServices));

            }
            countup = Gamedrawer.endScreen(countup, menumusic, 1);

        }


        if (countdown >= 0) {
            countdown = Gamedrawer.endScreen(countdown, menumusic, -1);
            System.out.println(countdown + "");
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
        menumusic.stop();
        menumusic.dispose();
        batch.dispose();

        // brain.dispose();
        brainb.dispose();
        braing.dispose();
        font.dispose();
        font2.dispose();
        clock.dispose();
        clock.dispose();
        clockg.dispose();
        options.dispose();
        optionsg.dispose();
        optionsb.dispose();
        soundon.dispose();
        soundoff.dispose();
        musicon.dispose();
        musicoff.dispose();

    }

}
