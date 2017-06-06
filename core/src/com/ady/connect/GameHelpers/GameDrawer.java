package com.ady.connect.GameHelpers;


import com.ady.connect.GameObjects.BgElement;
import com.ady.connect.GameObjects.Container;
import com.ady.connect.GameObjects.Line;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class GameDrawer {
    private Texture fingers;
    private Texture endtabletext;
    private Texture endtable;
    public Array<Container> containers = new Array<Container>();
    public Array<Line> lines = new Array<Line>();
    public Array<Vector2> posdrags = new Array<Vector2>();
    public Array<BgElement> bgelements = new Array<BgElement>();
    Array<ParticleEffect> effects = new Array<ParticleEffect>();
    private Texture bg;
    public SpriteBatch batch;
    public int restarted = 0;
    public Sprite sprbg;
    public int a = 0;
    public float hue = 360;
    private Texture linesmall;
    private float seedd = 0;
    private ParticleEffect effectline;
    public float speed = 1f;
    public boolean colorchange = true;
    public float whitebalance = 1f;
    private int effectquality = 5;
    public float sizesprite = 1.5f;
    private int spriteorder = 0;
    public Texture[] shape;
    private boolean menuagain = false;
    private Texture menutex;
    private Texture pixel;
    public boolean quality = true;
    public boolean quality2 = true;
    private boolean iscolored = false;
    private int shapenumber = 5;
    private float scorefontsize = 1f;
    private BitmapFont fontscore;
    private float backfontalpha = 0f;
    private float scoreslip = 0f;
    private Texture nomoread;
    private Texture endtableendless;
    private int fingerx;
    private int fingery;
    private float fingersize = 0.8f;
    private float fingersizechange = 0.01f;

    public GameDrawer(Texture bg, Texture rect, Texture linesmall, Texture containert) {
        shapenumber = 5;
        shape = new Texture[shapenumber];
        for (int i = 0; i < shapenumber; i++) {
            shape[i] = new Texture(Gdx.files.internal("shape" + i + ".png"));
            shape[i].setFilter(TextureFilter.Linear, TextureFilter.Linear);
        }
        fontscore = new BitmapFont(Gdx.files.internal("fontscore.fnt"));
        fontscore.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
        fontscore.setUseIntegerPositions(false);
        pixel = new Texture(Gdx.files.internal("1pix.png"));
        endtable = new Texture(Gdx.files.internal("scoreback.png"));
        endtabletext = new Texture(Gdx.files.internal("scoreback2.png"));
        nomoread = new Texture(Gdx.files.internal("nomoread.png"));
        endtableendless = new Texture(Gdx.files.internal("scorebackendless.png"));
        fingers = new Texture(Gdx.files.internal("2fingers.png"));
        fingers.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        endtableendless.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        nomoread.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        endtable.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        endtabletext.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        batch = new SpriteBatch();
        this.bg = bg;
        this.linesmall = linesmall;
        fingerx = MathUtils.random(150, 810);
        fingery = MathUtils.random(100, 440);

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < i * 2 + 3; j++) {
                BgElement temp = new BgElement(0, j * (360f / (i * 2 + 3)), 80 * i + 64f, 50f * i, 30f, (i + 5f) / 60,
                        (i + 5f) / 100);
                bgelements.add(temp);
            }
        }
        sprbg = new Sprite(shape[0]);

        effectline = new ParticleEffect();
        effectline.load(Gdx.files.internal("linepix.eft"), Gdx.files.internal(""));
        effectline.getEmitters().first().getEmission().setHigh(15f, 20f);
        effectline.getEmitters().first().getSprite().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
        effectline.start();
    }

    public boolean updateContainers(Array<Container> containers, Array<Line> lines) {
        this.containers.clear();
        this.containers.addAll(containers);
        this.lines.clear();
        this.lines.addAll(lines);
        if (containers.size > effects.size) {
            int size = effects.size;
            for (int i = 0; i < containers.size - size; i++) {
                ParticleEffect temp = new ParticleEffect();
                temp.load(Gdx.files.internal("particlepix.eft"), Gdx.files.internal(""));
                temp.getEmitters().first().getSprite().getTexture().setFilter(TextureFilter.Linear,
                        TextureFilter.Linear);
                temp.setEmittersCleanUpBlendFunction(false);

                temp.start();
                effects.add(new ParticleEffect(temp));

            }

        }

        return false;
    }

    public void changeBackground() {
        // batch.disableBlending();

        spriteorder++;
        sprbg = new Sprite(shape[spriteorder % shapenumber]);
        if (spriteorder == 100) {
            spriteorder = 0;
        }

    }

    public boolean drawBackground() {

        if (menuagain) {
            menuagain = !menuagain;
            sprbg.setTexture(menutex);

        }

        if (speed > 5)
            speed -= 0.01f;

        seedd += speed;
        if (colorchange)
            if (hue <= 360) {
                hue += 0.1f;
            } else {
                hue -= 360;
            }
        if (quality2) {
            batch.setColor(HSV_to_RGB((hue % 360), whitebalance * 240f, 30, 1f));
        } else {
            batch.setColor(Color.BLACK);
        }
        batch.setColor(HSV_to_RGB(((hue+240) % 360), whitebalance * 20f, 30, 1f));
       //batch.setColor(Color.WHITE);
        batch.begin();
        batch.draw(bg, 0, 0, 960f, 540f);
        batch.end();
        if (quality) {
            batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE);
        }
        batch.setColor(Color.WHITE);
        if (quality2) {
            // sprites
            if (quality) {
                sprbg.setColor(HSV_to_RGB((hue) % 360, whitebalance * 160f, 65, 0.05f));
            } else {
                sprbg.setColor(HSV_to_RGB((hue) % 360, whitebalance * 160f, 240, 0.03f));
            }

            batch.begin();

            for (int i = 0; i < bgelements.size; i++) {
                BgElement temp = bgelements.get(i);
                temp.update(speed, effectquality);

                sprbg.setSize(64f * temp.size * sizesprite, 64f * temp.size * sizesprite);
                sprbg.setCenter(temp.pos.x, temp.pos.y);

                sprbg.draw(batch);

            }

            for (int i = 0; i < bgelements.size; i++) {

                BgElement temp = bgelements.get(i);
                sprbg.setSize(44f * temp.size * sizesprite, 44f * temp.size * sizesprite);
                sprbg.setCenter(temp.pos.x, temp.pos.y);
                // sprbg.setRotation(temp.rotation);

                sprbg.draw(batch);

            }
            batch.end();
            if (quality) {
                batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
            }
        }
        return true;
    }

    public void changeBgsprite(Texture text) {
        menuagain = true;
        menutex = text;

    }


    public boolean drawContainers(BitmapFont font) {

        batch.begin();
        for (int i = 0; i < containers.size; i++) {
            containers.get(i).update(speed);
            effects.get(i).update(Gdx.graphics.getDeltaTime() * speed);
            effects.get(i).setPosition(containers.get(i).x, containers.get(i).y);
            if (quality || quality2) {
                float[] colors = new float[3];
                colors[0] = HSV_to_RGB(hue, whitebalance * 200, 240, 1).r;
                colors[1] = HSV_to_RGB(hue, whitebalance * 200, 240, 1).g;
                colors[2] = HSV_to_RGB(hue, whitebalance * 200, 240, 1).b;
                effects.get(i).getEmitters().first().getTint().setColors(colors);
                iscolored = false;
            } else {

                float[] colors = new float[3];
                colors[0] = 1f;
                colors[1] = 1f;
                colors[2] = 1f;
                effects.get(i).getEmitters().first().getTint().setColors(colors);

            }
            effects.get(i).draw(batch);
        }


        batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        scorefontsize = 0.6f;
        for (int i = 0; i < containers.size; i++) {

            font.setColor(Color.BLACK);

            Container temp = containers.get(i);

            font.getData().setScale((temp.valuesize - 1f) / 4f + 1f);
            GlyphLayout string = new GlyphLayout(font, temp.value + "");
            String str = new String(temp.value + "");
            // System.out.println(string.width);
            float x = temp.x - string.width / 2f;
            float y = temp.y + string.height / 2f;

            font.draw(batch, str, x + temp.stringpos.x, y + temp.stringpos.y);
            //

            if (temp.valuesize > 1f) {

                font.setColor(0, 0, 0, 0.08f / temp.valuesize);
                font.getData().setScale(temp.valuesize * 1.4f);
                GlyphLayout string2 = new GlyphLayout(font, temp.value + "");
                x = temp.x - string2.width / 2f;
                y = temp.y + string2.height / 2f;
                font.draw(batch, str, x + temp.stringpos.x, y + temp.stringpos.y);
                //
                font.setColor(0, 0, 0, 0.4f / temp.valuesize);
                if (scorefontsize < temp.valuesize * 0.9f * 0.6f) {
                    scorefontsize = temp.valuesize * 0.9f * 0.6f;
                    backfontalpha = 1f;
                    if (scoreslip == 0) {
                        scoreslip = 5f;
                    }
                }
                font.getData().setScale(temp.valuesize * 0.9f);
                string2 = new GlyphLayout(font, temp.value + "");
                x = temp.x - string2.width / 2f;
                y = temp.y + string2.height / 2f;
                font.draw(batch, str, x + temp.stringpos.x, y + temp.stringpos.y);

            }
        }
        batch.end();

        return true;

    }

    public boolean drawLines() {

        for (int i = 0; i < lines.size; i++) {
            Line temp = lines.get(i);
            temp.update(speed / 60f);
            //drawActiveLine(temp.point1, temp.point2);
            if (temp.readyct > 0) {
                drawPreLine(temp.point1, temp.point2, temp.readyct);

            }


            drawPreLine(temp.point1, temp.point2, 45f);
            batch.begin();
            if (quality) {

                float[] colors = new float[3];
                colors[0] = HSV_to_RGB(hue, whitebalance * 200, 240, 1).r;
                colors[1] = HSV_to_RGB(hue, whitebalance * 200, 240, 1).g;
                colors[2] = HSV_to_RGB(hue, whitebalance * 200, 240, 1).b;
                temp.effectline.getEmitters().first().getTint().setColors(colors);
                temp.effectline.draw(batch);

                // batch.setColor(1, 1, 1, 1);
            }
            batch.end();
        }

        return true;
    }

    public void drawActiveLine(Vector2 dragstart, Vector2 dragend) {

        if (dragstart != null && dragend != null) {
            if (quality) {

                Vector2 tempvec = new Vector2();
                tempvec = dragstart.cpy().lerp(dragend, MathUtils.randomTriangular(0f, 1f));
                effectline.setPosition(tempvec.x, tempvec.y);
                effectline.update(speed / 60f);
                float[] colors = new float[3];
                colors[0] = HSV_to_RGB(hue, whitebalance * 240, 240, 1).r;
                colors[1] = HSV_to_RGB(hue, whitebalance * 240, 240, 1).g;
                colors[2] = HSV_to_RGB(hue, whitebalance * 240, 240, 1).b;
                effectline.getEmitters().first().getTint().setColors(colors);
                batch.begin();
                effectline.draw(batch);
                batch.end();
            }
            MathUtils.random.setSeed((int) seedd);
            for (int j = 0; j < 5; j++) {
                float width = dragstart.dst(dragend);
                float anglerotate = dragend.cpy().sub(dragstart).angle();
                float gap = 70 + MathUtils.random(40);

                int src = batch.getBlendSrcFunc();
                int dst = batch.getBlendDstFunc();

                if (quality) {

                    batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE);
                    batch.setColor(HSV_to_RGB(6 * (j - 3) + hue, whitebalance * (110 + j * 30), 240, 1));

                } else {
                    if (quality2) {
                        batch.setColor(HSV_to_RGB(6 * (j - 3) + hue, whitebalance * (110 + j * 30), 240, 1));
                    } else {
                        batch.setColor(Color.WHITE);
                    }
                }

                for (int i = 0; i < width / gap + 1; i++) {
                    Vector2 temp = new Vector2(dragstart.cpy().lerp(dragend, i * gap / width));
                    Vector2 addvector = new Vector2(2 + j, 2 + j);
                    addvector.rotate(anglerotate + MathUtils.random(360f));
                    temp.add(addvector);
                    posdrags.add(temp);
                }

                for (int i = 0; i < posdrags.size - 2; i++) {
                    Vector2 a = new Vector2(posdrags.get(i));
                    Vector2 b = new Vector2(posdrags.get(i + 1));
                    Vector2 pos = a.cpy().lerp(b, 0.5f);
                    float angledrag = b.cpy().sub(a).angle();
                    batch.begin();
                    batch.draw(linesmall, pos.x - 40f, pos.y - 11f, 40f, 11f, 80f, 23f, a.dst(b) / 79,
                            MathUtils.random(1f, 1.1f) * MathUtils.randomSign(), angledrag, 0, 0, 80, 23, false, false);
                    // batch.draw(containert, a.x-3, a.y-3,5,5);
                    batch.end();
                }
                if (posdrags.size >= 2) {
                    Vector2 a = new Vector2(posdrags.get(posdrags.size - 2));
                    Vector2 b = new Vector2(posdrags.get(posdrags.size - 2).cpy().lerp(posdrags.get(posdrags.size - 1),
                            width / gap - (float) (int) (width / gap)));
                    // System.out.println(width / gap - (float) (int) (width /
                    // gap));
                    Vector2 pos = a.cpy().lerp(b, 0.5f);
                    float angledrag = b.cpy().sub(a).angle();
                    batch.begin();
                    batch.draw(linesmall, pos.x - 40f, pos.y - 11f, 40f, 11f, 80f, 23f, a.dst(b) / 79, MathUtils.random(1f, 1.1f), angledrag,
                            0, 0, 80, 23, false, false);
                    batch.end();
                }

                posdrags.clear();
                batch.setColor(Color.WHITE);
                if (quality) {
                    batch.setBlendFunction(src, dst);
                }
            }
        } else {
            // posdrags.clear();
        }

    }

    public void drawPreLine(Vector2 dragstart, Vector2 dragend, float ready) {

        float corel = 120 - ready;
        if (dragstart != null && dragend != null) {

            for (int j = 0; j < 5; j++) {
                float width = dragstart.dst(dragend);
                float anglerotate = dragend.cpy().sub(dragstart).angle();
                float gap = MathUtils.random(60, 90) + j * 4;
                MathUtils.random.setSeed((int) seedd + 131 * j);
                int src = batch.getBlendSrcFunc();
                int dst = batch.getBlendDstFunc();

                if (quality) {
                    batch.setColor(HSV_to_RGB(8 * (j - 2) + hue,
                            MathUtils.clamp(whitebalance * (150 + j * 30 - corel), 0, 240), 240, ready / 121f));
                    batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE);
                } else {
                    batch.setColor(Color.WHITE.cpy().lerp(1f, 1f, 1f, 0f, MathUtils.clamp(corel / 121f, 0f, 1f)));
                }

                for (int i = 0; i < width / gap + 1; i++) {
                    Vector2 temp = new Vector2(dragstart.cpy().lerp(dragend, i * gap / width));
                    Vector2 addvector = new Vector2(2 + j * 2 - corel / 12f, 2 + j * 2 - corel / 12f);
                    addvector.rotate(anglerotate + MathUtils.random(360f));
                    temp.add(addvector);
                    posdrags.add(temp);
                }

                for (int i = 0; i < posdrags.size - 2; i++) {
                    Vector2 a = new Vector2(posdrags.get(i));
                    Vector2 b = new Vector2(posdrags.get(i + 1));
                    Vector2 pos = a.cpy().lerp(b, 0.5f);
                    float angledrag = b.cpy().sub(a).angle();
                    batch.begin();
                    batch.draw(linesmall, pos.x - 40f, pos.y - 11f, 40f, 11f, 80f, 23f, a.dst(b) / 80f,
                            MathUtils.random(1f, 1.5f) * MathUtils.randomSign(), angledrag, 0, 0, 80, 23, false, false);

                    batch.end();
                }
                if (posdrags.size >= 2) {
                    Vector2 a = new Vector2(posdrags.get(posdrags.size - 2));
                    Vector2 b = new Vector2(posdrags.get(posdrags.size - 2).cpy().lerp(posdrags.get(posdrags.size - 1),
                            width / gap - (float) (int) (width / gap)));
                    // System.out.println(width / gap - (float) (int) (width /
                    // gap));
                    Vector2 pos = a.cpy().lerp(b, 0.5f);
                    float angledrag = b.cpy().sub(a).angle();
                    batch.begin();
                    batch.draw(linesmall, pos.x - 40f, pos.y - 11f, 40f, 11f, 80f, 23f, a.dst(b) / 80f, 1, angledrag, 0,
                            0, 80, 23, false, false);
                    batch.end();
                }

                posdrags.clear();
                batch.setColor(Color.WHITE);
                if (quality) {
                    batch.setBlendFunction(src, dst);
                }
            }
        } else {
            // posdrags.clear();
        }

    }

    public void drawScore(int score, float remaintime) {
        if (backfontalpha > 0.02f) {
            backfontalpha -= 0.02f;
        }
        if (scoreslip > 0f) {
            scoreslip -= 0.15f;
        } else {
            scoreslip = 0f;
        }

        GlyphLayout glyp = new GlyphLayout();
        batch.begin();
        if (backfontalpha > 0.6f && quality2) {
            fontscore.setColor(HSV_to_RGB(hue, whitebalance * 240, 90, backfontalpha));
            fontscore.getData().setScale(backfontalpha + 0.01f);
            glyp.setText(fontscore, "" + score);

            fontscore.draw(batch, "" + score, 870 - glyp.width / 2, 410 + glyp.height / 2);
        }

        fontscore.getData().setScale(scorefontsize);
        glyp.setText(fontscore, "" + score);
        if (quality2) {
            fontscore.setColor(HSV_to_RGB(hue, whitebalance * 240, 180, 1));
            fontscore.draw(batch, "" + score, 870 - glyp.width / 2, 410 + glyp.height / 2);
            fontscore.getData().setScale(0.6f);
            fontscore.draw(batch, "Score", 815, 530);
            fontscore.draw(batch, "Time", 40, 530);

            String str = "" + Math.round(remaintime * 10.0) / 10.0;
            glyp.setText(fontscore, str);
            fontscore.draw(batch, str, 50, 410 + glyp.height / 2);
            fontscore.setColor(HSV_to_RGB(hue, whitebalance * 240, 220, 1));

            switch (5 - (int) (scoreslip + 0.5f)) {
                case 0:
                    fontscore.draw(batch, "S", 815, 530);
                    break;
                case 1:
                    fontscore.draw(batch, "c", 835, 530);
                    break;
                case 2:
                    fontscore.draw(batch, "o", 855, 530);
                    break;
                case 3:
                    fontscore.draw(batch, "r", 876, 530);
                    break;
                case 4:
                    fontscore.draw(batch, "e", 896, 530);
                    break;

            }
        } else {
            fontscore.setColor(Color.WHITE);
            fontscore.draw(batch, "" + score, 870 - glyp.width / 2, 410 + glyp.height / 2);
            fontscore.getData().setScale(0.6f);
            fontscore.draw(batch, "Score", 815, 530);
            fontscore.draw(batch, "Time", 40, 530);
            String str = "" + Math.round(remaintime * 10.0) / 10.0;
            glyp.setText(fontscore, str);
            fontscore.draw(batch, str, 50, 410 + glyp.height / 2);
        }

        batch.end();

    }

    public void drawLevelgui(int stage) {
        batch.begin();
        fontscore.getData().setScale(0.6f);
        if (quality2) {
            fontscore.setColor(HSV_to_RGB(hue, whitebalance * 240, 180, 1));
        } else {
            fontscore.setColor(Color.WHITE);
        }
        fontscore.draw(batch, "Level", 40, 530);
        GlyphLayout glyp = new GlyphLayout(fontscore, "" + stage);
        fontscore.draw(batch, "" + stage, 70, 410 + glyp.height / 2);
        batch.end();

    }


    public int endScreen(int count, Music music, int a) {

        if (count > 60)
            count = 60;
        batch.begin();
        batch.setColor(0, 0, 0, (float) count / 60f);
        if (count % 20 == 0) {
            music.setVolume((60f - (float) count) / 60f);
        }
        batch.draw(pixel, 0, 0, 960, 540);
        batch.end();
        return count + a;

    }

    public void drawEndTable() {
        if (quality2) {
            batch.setColor(0f, 0f, 0f, 0.9f);
        } else {
            batch.setColor(0f, 0f, 0f, 0.9f);
        }
        batch.begin();

        batch.draw(endtable, 230, 120, 500, 300);//300,200
        if (quality2) {

            batch.setColor(HSV_to_RGB(hue, whitebalance * 200, 240, 1f));
        } else {
            batch.setColor(Color.WHITE);
        }

        batch.draw(endtabletext, 230, 120);
        batch.end();
    }

    public void drawEndTableEndless() {
        if (quality2) {
            batch.setColor(0f, 0f, 0f, 0.9f);
        } else {
            batch.setColor(0f, 0f, 0f, 0.9f);
        }
        batch.begin();

        batch.draw(endtable, 230, 120, 500, 300);//300,200
        if (quality2) {

            batch.setColor(HSV_to_RGB(hue, whitebalance * 200, 240, 1f));
        } else {
            batch.setColor(Color.WHITE);
        }

        batch.draw(endtableendless, 230, 120);
        batch.end();
    }

    public void drawNoMoreAds() {
        batch.begin();
        if (quality2) {

            batch.setColor(HSV_to_RGB(hue, whitebalance * 200, 240, 1f));
        } else {
            batch.setColor(Color.WHITE);
        }
        batch.draw(nomoread, 550, 140, 175, 90);
        batch.end();
    }

    public void draw2fingers() {
        batch.begin();

        if (fingersize < 0.6 || fingersize > 1) {
            fingersizechange *= -1;
            if (fingersize > 1) {
                fingerx = MathUtils.random(150, 810);
                fingery = MathUtils.random(100, 440);
            }
        }
        fingersize += fingersizechange;
        if (quality2) {

            batch.setColor(HSV_to_RGB(hue, whitebalance * 200, 240, 4f*(0.9f-fingersize)));
        } else {
            batch.setColor(Color.WHITE);
        }
        if (fingersize < 0.8) {
            batch.draw(fingers, fingerx - fingersize * 50f, fingery - fingersize * 89f, fingersize * 99f, fingersize * 177f);
        }
        batch.end();
    }

    public Color HSV_to_RGB(float h, float s, float v, float alpha) {
        int r, g, b;
        int i;
        float f, p, q, t;
        h = (h + 3600) % 360;
        s = (float) Math.max(0.0, Math.min(240.0, s));
        v = (float) Math.max(0.0, Math.min(240.0, v));
        s /= 240f;
        v /= 240f;

        h /= 60;
        i = (int) Math.floor(h);
        f = h - i;
        p = v * (1 - s);
        q = v * (1 - s * f);
        t = v * (1 - s * (1 - f));
        switch (i) {
            case 0:
                r = Math.round(255 * v);
                g = Math.round(255 * t);
                b = Math.round(255 * p);
                break;
            case 1:
                r = Math.round(255 * q);
                g = Math.round(255 * v);
                b = Math.round(255 * p);
                break;
            case 2:
                r = Math.round(255 * p);
                g = Math.round(255 * v);
                b = Math.round(255 * t);
                break;
            case 3:
                r = Math.round(255 * p);
                g = Math.round(255 * q);
                b = Math.round(255 * v);
                break;
            case 4:
                r = Math.round(255 * t);
                g = Math.round(255 * p);
                b = Math.round(255 * v);
                break;
            default:
                r = Math.round(255 * v);
                g = Math.round(255 * p);
                b = Math.round(255 * q);
        }

        return new Color(r / 255.0f, g / 255.0f, b / 255.0f, alpha);
    }

    public void dispose() {

    }


}
