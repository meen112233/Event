package game01.core;

import static playn.core.PlayN.*;

import characters.*;
import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.EdgeShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.dynamics.*;
import org.jbox2d.dynamics.contacts.Contact;
import playn.core.Color;
import playn.core.DebugDrawBox2D;
import org.jbox2d.common.Vec2;
import playn.core.*;
import playn.core.Image;
import playn.core.util.Clock;
import tripleplay.game.Screen;
import tripleplay.game.ScreenStack;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GameScreen extends Screen {

    public static float M_PER_PIXEL = 1 / 26.666667f;  //size of world
    public static int width = 24; // 640 px in physic unit (meter)
    public static int height = 18; // 480 px in physic unit (meter)

    private World world;
    private DebugDrawBox2D debugDraw;
    private boolean showDebugDraw = true;
    private boolean pause = false;
    private ScreenStack ss;
    private ImageLayer bg;
    private ImageLayer backButton;
    private ImageLayer box1;
    private ImageLayer box2;
    private ImageLayer box3;
    private ImageLayer box3_2;
    private ImageLayer gameover;
    private ImageLayer myhp2_2;
    private ImageLayer myhp2_1;
    private ImageLayer myhp2_0;
    private Naki naki;
    private NakiEffect nakiEffect;
    private MonsterNinja monsterNinja;
    private MonsterRat monsterRat;
    private List<NakiEffect> nakiEffectMap;
    private Warp warp;
    private int shoot = 0;
    private int i = -1;
    private static List<NakiEffect> effectList;
    private GroupLayer groupEffect = graphics().createGroupLayer();

    public static String debugString = "";
    public static String debugStringCoin = "";
    public static HashMap<Object,String> bodies = new HashMap<Object, String>();
    public static int k = 0;
    public static int j = 0;
    public static int maxhp = 2;
    public static int myhp = maxhp;

    public void addNakiEffect(NakiEffect effect){
        effectList.add(effect);
    }
    public GameScreen(){}
    public GameScreen(final ScreenStack ss) {
        this.ss = ss;
        graphics().rootLayer().clear();
        nakiEffectMap = new ArrayList<NakiEffect>();
        Image bgImage = assets().getImage("images/gameScreen.png");
        this.bg = graphics().createImageLayer(bgImage);

        /*Image coinImage = assets().getImage("images/Coin.png");
        this.coin = graphics().createImageLayer(coinImage);
        coin.setTranslation(295,215);*/
        Image box3Image = assets().getImage("images/box3.png");
        this.box3 = graphics().createImageLayer(box3Image);
        box3.setTranslation(385f, 350f);

        Image box3_2Image = assets().getImage("images/box3.png");
        this.box3_2 = graphics().createImageLayer(box3_2Image);
        box3_2.setTranslation(60f, 250f);

        Image box2Image = assets().getImage("images/box2.png");
        this.box2 = graphics().createImageLayer(box2Image);
        box2.setTranslation(300f, 140f);

        Image box1Image = assets().getImage("images/box1.png");
        this.box1 = graphics().createImageLayer(box1Image);
        box1.setTranslation(520f, 110f);

        Image gameoverImage = assets().getImage("images/gameover.png");
        this.gameover = graphics().createImageLayer(gameoverImage);
        gameover.setTranslation(0f,0f);

        Image myhp2_2Image = assets().getImage("images/myhp2_2.png");
        this.myhp2_2 = graphics().createImageLayer(myhp2_2Image);
        myhp2_2.setTranslation(20f ,460f);

        Image myhp2_1Image = assets().getImage("images/myhp2_1.png");
        this.myhp2_1 = graphics().createImageLayer(myhp2_1Image);
        myhp2_1.setTranslation(20f ,460f);

        Image myhp2_0Image = assets().getImage("images/myhp2_0.png");
        this.myhp2_0 = graphics().createImageLayer(myhp2_0Image);
        myhp2_0.setTranslation(20f, 460f);

        Image backImage = assets().getImage("images/backButton.png");
        this.backButton = graphics().createImageLayer(backImage);
        backButton.setTranslation(10,10);
        backButton.addListener(new Mouse.LayerAdapter() {
            @Override
            public void onMouseUp(Mouse.ButtonEvent event) {
                ss.remove(ss.top());
                k = 0;
            }
        });

        Vec2 gravity = new Vec2(0.0f , 10.0f);
        world = new World(gravity);
        world.setWarmStarting(true);
        world.setAutoClearForces(true);

        naki = new Naki(world, 250f, 400f);
        //monsterNinja = new MonsterNinja(world, 730f, 330f);
        //nakiEffect = new NakiEffect(world, 200f, 200f);
        warp = new Warp(560f, 80f);
        monsterRat = new MonsterRat(world, 600f, 330f);

        effectList = new ArrayList<NakiEffect>();

        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                Body a = contact.getFixtureA().getBody();
                Body b = contact.getFixtureB().getBody();
                for(NakiEffect effect : effectList){
                    if((contact.getFixtureA().getBody() == effect.getBody() &&
                            (bodies.get(b) == "ground" || bodies.get(b) == "top"
                                    || bodies.get(b) == "left" || bodies.get(b) == "right"
                                    || bodies.get(b) == "box3" || bodies.get(b) == "box3_2"
                                    || bodies.get(b) == "box2" || bodies.get(b) == "box1"))
                            || (contact.getFixtureB().getBody() == effect.getBody() &&
                            (bodies.get(a) == "ground" || bodies.get(a) == "top"
                                    || bodies.get(a) == "left" || bodies.get(a) == "right"
                                    || bodies.get(a) == "box3" || bodies.get(a) == "box3_2"
                                    || bodies.get(a) == "box2" || bodies.get(a) == "box1"))){
                        effect.layer().setVisible(false);
                        if(effect.getBody() == a){
                            a.setActive(false);
                        }else if(effect.getBody() == b){
                            b.setActive(false);
                        }
                    }/*else if((contact.getFixtureA().getBody() == effect.getBody() &&
                            bodies.get(b) == "monsterRat") ||
                            (contact.getFixtureB().getBody() == effect.getBody() &&
                            bodies.get(a) == "monsterRat")){
                        effect.layer().setVisible(false);
                        if(effect.getBody() == a){
                            a.setActive(false);
                        }else if(effect.getBody() == b){
                            b.setActive(false);
                        }
                    }*/
                }
                if(bodies.get(a) == "naki" && bodies.get(b) == "monsterRat"){
                    myhp--;
                    System.out.println(myhp);
                    a.applyForce(new Vec2(-100f, -850f), b.getPosition());
                    b.applyForce(new Vec2(100f, -100f), b.getPosition());
                    System.out.println("a = " + bodies.get(a));
                    System.out.println("b = " + bodies.get(b));
                }else if(bodies.get(a) == "monsterRat" && bodies.get(b) == "naki"){
                    myhp--;
                    System.out.println(myhp);
                    System.out.println("a = " + bodies.get(a));
                    System.out.println("b = " + bodies.get(b));
                    a.applyForce(new Vec2(100f, -100f), b.getPosition());
                    b.applyForce(new Vec2(-100f, -850f), b.getPosition());
                }
                if(myhp == 0){
                    pause = true;
                }
            }

            @Override
            public void endContact(Contact contact) {

            }

            @Override
            public void preSolve(Contact contact, Manifold manifold) {

            }

            @Override
            public void postSolve(Contact contact, ContactImpulse contactImpulse) {

            }
        });


       /* mouse().setListener(new Mouse.Adapter(){
            @Override
            public void onMouseUp(Mouse.ButtonEvent event) {
                nakiMap.add(new Naki(world, (float)event.x(), (float)event.y()));
                i++;
                for (int c = 0 ; c <= i ; c++){
                    graphics().rootLayer().add(nakiMap.get(c).layer());
                }

            }
        });*/

    }

    @Override
    public void wasShown() {
        super.wasShown();
        this.layer.add(bg);
        this.layer.add(backButton);
        this.layer.add(naki.layer());
        //this.layer.add(monsterNinja.layer());
        this.layer.add(monsterRat.layer());
        //this.layer.add(nakiEffect.layer());
        this.layer.add(warp.layer());
        this.layer.add(groupEffect);
        this.layer.add(box1);
        this.layer.add(box2);
        this.layer.add(box3);
        this.layer.add(box3_2);

        //this.layer.add(coin);

        if (showDebugDraw) {
            CanvasImage image = graphics().createImage(
                    (int) (width / GameScreen.M_PER_PIXEL),
                    (int) (height / GameScreen.M_PER_PIXEL));
            layer.add(graphics().createImageLayer(image));
            debugDraw = new DebugDrawBox2D();
            debugDraw.setCanvas(image);
            debugDraw.setFlipY(false);
            debugDraw.setStrokeAlpha(150);
            debugDraw.setFillAlpha(75);
            debugDraw.setStrokeWidth(2.0f);
            debugDraw.setFlags( debugDraw.e_shapeBit |
                    debugDraw.e_jointBit |
                    debugDraw.e_aabbBit);
            debugDraw.setCamera(0, 0, 1f / GameScreen.M_PER_PIXEL);
            world.setDebugDraw(debugDraw);
        }

        Body ground = world.createBody(new BodyDef());
        EdgeShape groundShape = new EdgeShape();
        groundShape.set(new Vec2(0, 17), new Vec2(width, 17));
        ground.createFixture(groundShape, 0.0f);
        bodies.put(ground,"ground");

        Body top = world.createBody(new BodyDef());
        EdgeShape topShape = new EdgeShape();
        topShape.set(new Vec2(0, 0), new Vec2(24, 0));
        top.createFixture(topShape, 0.0f);
        bodies.put(top, "top");

        Body left = world.createBody(new BodyDef());
        EdgeShape leftShape = new EdgeShape();
        leftShape.set(new Vec2(0, 0), new Vec2(0, 18));
        left.createFixture(leftShape, 0.0f);
        bodies.put(left, "left");

        Body right = world.createBody(new BodyDef());
        EdgeShape rightShape = new EdgeShape();
        rightShape.set(new Vec2(24, 0), new Vec2(24, 18));
        right.createFixture(rightShape, 0.0f);
        bodies.put(right, "right");

        Body box3Square = world.createBody(new BodyDef());
        PolygonShape box3Shape = new PolygonShape();
        box3Shape.setAsBox(255 * M_PER_PIXEL / 2,20 * M_PER_PIXEL / 2);
        box3Square.setTransform(new Vec2(510*M_PER_PIXEL, 360*M_PER_PIXEL), 0f);
        box3Square.createFixture(box3Shape, 0.0f);
        bodies.put(box3Square, "box3");

        Body box3_2Square = world.createBody(new BodyDef());
        PolygonShape box3_2Shape = new PolygonShape();
        box3_2Shape.setAsBox(255 * M_PER_PIXEL / 2,20 * M_PER_PIXEL / 2);
        box3_2Square.setTransform(new Vec2(190*M_PER_PIXEL, 260*M_PER_PIXEL), 0f);
        box3_2Square.createFixture(box3_2Shape, 0.0f);
        bodies.put(box3_2Square, "box3_2");

        Body box2Square = world.createBody(new BodyDef());
        PolygonShape box2Shape = new PolygonShape();
        box2Shape.setAsBox(170 * M_PER_PIXEL / 2,20 * M_PER_PIXEL / 2);
        box2Square.setTransform(new Vec2(385*M_PER_PIXEL, 150*M_PER_PIXEL), 0f);
        box2Square.createFixture(box2Shape, 0.0f);
        bodies.put(box2Square, "box2");

        Body box1Square = world.createBody(new BodyDef());
        PolygonShape box1Shape = new PolygonShape();
        box1Shape.setAsBox(85 * M_PER_PIXEL / 2,20 * M_PER_PIXEL / 2);
        box1Square.setTransform(new Vec2(560*M_PER_PIXEL, 120*M_PER_PIXEL), 0f);
        box1Square.createFixture(box1Shape, 0.0f);
        bodies.put(box1Square, "box1");



        /*Body coinCircle = world.createBody(new BodyDef());
        CircleShape coinCircleShape = new CircleShape();
        coinCircleShape.setRadius(1.0f);
        coinCircleShape.m_p.set(12f,9f);
        coinCircle.createFixture(coinCircleShape, 0.0f);*/

    }
    @Override
    public void update(int delta) {
        if(pause == true){
            this.layer.add(gameover);
            if(maxhp == 2){
                this.layer.add(myhp2_0);
            }
        }
        if(pause == false) {
            super.update(delta);
            if (myhp == 2) {
                this.layer.add(myhp2_2);
            } else if (myhp == 1) {
                this.layer.add(myhp2_1);
            }
        /*for (int c = 0 ; c <= i ; c++){
            nakiEffectMap.get(c).update(delta);
        }*/
            naki.update(delta);
            //monsterNinja.update(delta);
            monsterRat.update(delta);
            //nakiEffect.update(delta);
            warp.update(delta);
            for (NakiEffect effect : effectList) {
                effect.update(delta);
            }
            for (NakiEffect effect : effectList) {
                groupEffect.add(effect.layer());
            }
            world.step(0.033f, 10, 10);
        }
    }

    @Override
    public void paint(Clock clock) {
        if(pause == false) {
            super.paint(clock);
        /*for (int c = 0 ; c <= i ; c++){
            nakiEffectMap.get(c).paint(clock);
        }*/
            naki.paint(clock);
            //monsterNinja.paint(clock);
            monsterRat.paint(clock);
            //nakiEffect.paint(clock);
            //warp.paint(clock);
            for (NakiEffect effect : effectList) {
                effect.paint(clock);
            }
            if (showDebugDraw) {
                debugDraw.getCanvas().clear();
                debugDraw.getCanvas().setFillColor(Color.rgb(255, 255, 255));
                debugDraw.getCanvas().drawText(debugString, 100f, 50f);
                debugDraw.getCanvas().drawText(debugStringCoin, 100f, 100f);
                world.drawDebugData();
            }
        }
    }
}

