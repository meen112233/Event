package game01.core;

import static playn.core.PlayN.*;

import characters.MonsterNinja;
import characters.Naki;
import characters.NakiEffect;
import characters.Warp;
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
    private final ScreenStack ss;
    private final ImageLayer bg;
    private final ImageLayer backButton;
    private final ImageLayer box1;
    private final ImageLayer box2;
    private final ImageLayer box3;
    private final ImageLayer box3_2;
    //private final ImageLayer coin;
    private Naki naki;
    private NakiEffect nakiEffect;
    private MonsterNinja monsterNinja;
    private List<NakiEffect> nakiEffectMap;
    private Warp warp;
    private int i = -1;
    public static HashMap<Object,String> bodies = new HashMap<Object, String>();
    public static int k = 0;
    public static int j = 0;
    public static String debugString = "";
    public static String debugStringCoin = "";
    private float nakieffect_x = 0;
    private float nakieffect_y = 0;

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

        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                Body a = contact.getFixtureA().getBody();
                Body b = contact.getFixtureB().getBody();
                if (bodies.get(a) != null){
                    j = j + 10;
                    debugString = bodies.get(a) + " contacted with " + bodies.get(b);
                    debugStringCoin = "Point : " + j;
                    b.applyForce(new Vec2(80f, -100f), b.getPosition());
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

        naki = new Naki(world, 250f, 400f);
        monsterNinja = new MonsterNinja(world, 730f, 330f);
        nakiEffect = new NakiEffect(world, 200f, 200f);
        warp = new Warp(560f, 80f);



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
        this.layer.add(monsterNinja.layer());
        this.layer.add(nakiEffect.layer());
        this.layer.add(warp.layer());
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

        EdgeShape topShape = new EdgeShape();
        topShape.set(new Vec2(0, 0), new Vec2(24, 0));
        ground.createFixture(topShape, 0.0f);

        EdgeShape leftShape = new EdgeShape();
        leftShape.set(new Vec2(0, 0), new Vec2(0, 18));
        ground.createFixture(leftShape, 0.0f);

        EdgeShape rightShape = new EdgeShape();
        rightShape.set(new Vec2(24, 0), new Vec2(24, 18));
        ground.createFixture(rightShape, 0.0f);

        Body box3Square = world.createBody(new BodyDef());
        PolygonShape box3Shape = new PolygonShape();
        box3Shape.setAsBox(255 * M_PER_PIXEL / 2,20 * M_PER_PIXEL / 2);
        box3Square.setTransform(new Vec2(510*M_PER_PIXEL, 360*M_PER_PIXEL), 0f);
        box3Square.createFixture(box3Shape, 0.0f);

        Body box3_2Square = world.createBody(new BodyDef());
        PolygonShape box3_2Shape = new PolygonShape();
        box3_2Shape.setAsBox(255 * M_PER_PIXEL / 2,20 * M_PER_PIXEL / 2);
        box3_2Square.setTransform(new Vec2(190*M_PER_PIXEL, 260*M_PER_PIXEL), 0f);
        box3_2Square.createFixture(box3_2Shape, 0.0f);

        Body box2Square = world.createBody(new BodyDef());
        PolygonShape box2Shape = new PolygonShape();
        box2Shape.setAsBox(170 * M_PER_PIXEL / 2,20 * M_PER_PIXEL / 2);
        box2Square.setTransform(new Vec2(385*M_PER_PIXEL, 150*M_PER_PIXEL), 0f);
        box2Square.createFixture(box2Shape, 0.0f);

        Body box1Square = world.createBody(new BodyDef());
        PolygonShape box1Shape = new PolygonShape();
        box1Shape.setAsBox(85 * M_PER_PIXEL / 2,20 * M_PER_PIXEL / 2);
        box1Square.setTransform(new Vec2(560*M_PER_PIXEL, 120*M_PER_PIXEL), 0f);
        box1Square.createFixture(box1Shape, 0.0f);

        /*Body coinCircle = world.createBody(new BodyDef());
        CircleShape coinCircleShape = new CircleShape();
        coinCircleShape.setRadius(1.0f);
        coinCircleShape.m_p.set(12f,9f);
        coinCircle.createFixture(coinCircleShape, 0.0f);*/

    }

    @Override
    public void update(int delta) {
        super.update(delta);
        /*for (int c = 0 ; c <= i ; c++){
            nakiEffectMap.get(c).update(delta);
        }*/
        naki.update(delta);
        monsterNinja.update(delta);
        nakiEffect.update(delta);
        warp.update(delta);
        world.step(0.033f, 10, 10);
    }

    @Override
    public void paint(Clock clock) {
        super.paint(clock);
        /*for (int c = 0 ; c <= i ; c++){
            nakiEffectMap.get(c).paint(clock);
        }*/
        naki.paint(clock);
        monsterNinja.paint(clock);
        nakiEffect.paint(clock);
        //warp.paint(clock);
        if (showDebugDraw) {
            debugDraw.getCanvas().clear();
            debugDraw.getCanvas().setFillColor(Color.rgb(255, 255, 255));
            debugDraw.getCanvas().drawText(debugString,100f,50f);
            debugDraw.getCanvas().drawText(debugStringCoin,100f,100f);
            world.drawDebugData();
        }
    }
}

