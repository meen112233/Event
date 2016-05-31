package game01.core;

import Coin.*;
import characters.*;
import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.collision.shapes.EdgeShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.contacts.Contact;
import playn.core.*;
import playn.core.util.Clock;
import tripleplay.game.Screen;
import tripleplay.game.ScreenStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static playn.core.PlayN.assets;
import static playn.core.PlayN.graphics;

public class GameScreen2 extends Screen {

    public static float M_PER_PIXEL = 1 / 26.666667f;  //size of world
    public static int width = 24; // 640 px in physic unit (meter)
    public static int height = 18; // 480 px in physic unit (meter)

    private World world;
    private DebugDrawBox2D debugDraw;
    private boolean showDebugDraw = true;
    private ScreenStack ss;
    private ImageLayer bg;
    private ImageLayer pauseButton;
    private ImageLayer pauseScreen;
    private ImageLayer pResume;
    private ImageLayer pRestart;
    private ImageLayer pExit;
    private ImageLayer gameover;
    private ImageLayer goRestart;
    private ImageLayer goHome;
    private ImageLayer stage2clear;
    private ImageLayer scNext;
    private ImageLayer myhp2_2;
    private ImageLayer myhp2_1;
    private ImageLayer myhp2_0;
    private ImageLayer myhp3_0;
    private ImageLayer myhp3_1;
    private ImageLayer myhp3_2;
    private ImageLayer myhp3_3;
    private ImageLayer myhp4_0;
    private ImageLayer myhp4_1;
    private ImageLayer myhp4_2;
    private ImageLayer myhp4_3;
    private ImageLayer myhp4_4;
    private ImageLayer myhp5_0;
    private ImageLayer myhp5_1;
    private ImageLayer myhp5_2;
    private ImageLayer myhp5_3;
    private ImageLayer myhp5_4;
    private ImageLayer myhp5_5;
    private ImageLayer myhp6_0;
    private ImageLayer myhp6_1;
    private ImageLayer myhp6_2;
    private ImageLayer myhp6_3;
    private ImageLayer myhp6_4;
    private ImageLayer myhp6_5;
    private ImageLayer myhp6_6;
    private ImageLayer myhp7_0;
    private ImageLayer myhp7_1;
    private ImageLayer myhp7_2;
    private ImageLayer myhp7_3;
    private ImageLayer myhp7_4;
    private ImageLayer myhp7_5;
    private ImageLayer myhp7_6;
    private ImageLayer myhp7_7;
    private ImageLayer coin;
    private ImageLayer box1;
    private ImageLayer box4;
    private Naki2 naki2;
    private GroupLayer groupEffect2 = graphics().createGroupLayer();
    private Digit1 digit1;
    private Digit2 digit2;
    private Digit3 digit3;
    private Digit4 digit4;
    private Digit5 digit5;
    private Digit6 digit6;
    private int shoot = 0;
    private int i = -1;
    private boolean pause = false;
    private boolean pause2 = false;
    private boolean pause3 = false;
    private boolean destroy = false;
    private LevelSelectScreen lvscreen;
    private int count1 = 0;
    private int count2 = 0;
    private int maxhp = lvscreen.maxhp;
    private int myhp = lvscreen.myhp;
    private int mygold = lvscreen.mygold;
    private int temp = 0;
    private int countdigit = 1;

    public static String debugString = "";
    public static String debugStringCoin = "";
    public static HashMap<Object,String> bodies = new HashMap<Object, String>();
    public static int k = 0;
    public static int j = 0;
    public static int hpmonsterRat = 2;
    public static int hpmonsterRat2 = 2;
    public static boolean clear = false;
    public static List<NakiEffect2> effectList2;
    public static int numberdigit1 = 0;
    public static int numberdigit2 = 0;
    public static int numberdigit3 = 0;
    public static int numberdigit4 = 0;
    public static int numberdigit5 = 0;
    public static int numberdigit6 = 0;
    public static int x = 0;

    public void addNakiEffect2(NakiEffect2 effect2){
        effectList2.add(effect2);
    }
    public GameScreen2(){}
    public GameScreen2(final ScreenStack ss) {
        this.ss = ss;
        graphics().rootLayer().clear();

        Image bgImage = assets().getImage("images/gameScreen2.png");
        this.bg = graphics().createImageLayer(bgImage);

        Image myhp2_2Image = assets().getImage("images/myhp2_2.png");
        this.myhp2_2 = graphics().createImageLayer(myhp2_2Image);
        myhp2_2.setTranslation(20f ,460f);

        Image myhp2_1Image = assets().getImage("images/myhp2_1.png");
        this.myhp2_1 = graphics().createImageLayer(myhp2_1Image);
        myhp2_1.setTranslation(20f ,460f);

        Image myhp2_0Image = assets().getImage("images/myhp2_0.png");
        this.myhp2_0 = graphics().createImageLayer(myhp2_0Image);
        myhp2_0.setTranslation(20f, 460f);

        Image myhp3_3Image = assets().getImage("images/myhp3_3.png");
        this.myhp3_3 = graphics().createImageLayer(myhp3_3Image);
        myhp3_3.setTranslation(20f, 460f);

        Image myhp3_2Image = assets().getImage("images/myhp3_2.png");
        this.myhp3_2 = graphics().createImageLayer(myhp3_2Image);
        myhp3_2.setTranslation(20f, 460f);

        Image myhp3_1Image = assets().getImage("images/myhp3_1.png");
        this.myhp3_1 = graphics().createImageLayer(myhp3_1Image);
        myhp3_1.setTranslation(20f, 460f);

        Image myhp3_0Image = assets().getImage("images/myhp3_0.png");
        this.myhp3_0 = graphics().createImageLayer(myhp3_0Image);
        myhp3_0.setTranslation(20f, 460f);

        Image myhp4_4Image = assets().getImage("images/myhp4_4.png");
        this.myhp4_4 = graphics().createImageLayer(myhp4_4Image);
        myhp4_4.setTranslation(20f, 460f);

        Image myhp4_3Image = assets().getImage("images/myhp4_3.png");
        this.myhp4_3 = graphics().createImageLayer(myhp4_3Image);
        myhp4_3.setTranslation(20f, 460f);

        Image myhp4_2Image = assets().getImage("images/myhp4_2.png");
        this.myhp4_2 = graphics().createImageLayer(myhp4_2Image);
        myhp4_2.setTranslation(20f, 460f);

        Image myhp4_1Image = assets().getImage("images/myhp4_1.png");
        this.myhp4_1 = graphics().createImageLayer(myhp4_1Image);
        myhp4_1.setTranslation(20f, 460f);

        Image myhp4_0Image = assets().getImage("images/myhp4_0.png");
        this.myhp4_0 = graphics().createImageLayer(myhp4_0Image);
        myhp4_0.setTranslation(20f, 460f);

        Image myhp5_5Image = assets().getImage("images/myhp5_5.png");
        this.myhp5_5 = graphics().createImageLayer(myhp5_5Image);
        myhp5_5.setTranslation(20f, 460f);

        Image myhp5_4Image = assets().getImage("images/myhp5_4.png");
        this.myhp5_4 = graphics().createImageLayer(myhp5_4Image);
        myhp5_4.setTranslation(20f, 460f);

        Image myhp5_3Image = assets().getImage("images/myhp5_3.png");
        this.myhp5_3 = graphics().createImageLayer(myhp5_3Image);
        myhp5_3.setTranslation(20f, 460f);

        Image myhp5_2Image = assets().getImage("images/myhp5_2.png");
        this.myhp5_2 = graphics().createImageLayer(myhp5_2Image);
        myhp5_2.setTranslation(20f, 460f);

        Image myhp5_1Image = assets().getImage("images/myhp5_1.png");
        this.myhp5_1 = graphics().createImageLayer(myhp5_1Image);
        myhp5_1.setTranslation(20f, 460f);

        Image myhp5_0Image = assets().getImage("images/myhp5_0.png");
        this.myhp5_0 = graphics().createImageLayer(myhp5_0Image);
        myhp5_0.setTranslation(20f, 460f);

        Image myhp6_6Image = assets().getImage("images/myhp6_6.png");
        this.myhp6_6 = graphics().createImageLayer(myhp6_6Image);
        myhp6_6.setTranslation(20f, 460f);

        Image myhp6_5Image = assets().getImage("images/myhp6_5.png");
        this.myhp6_5 = graphics().createImageLayer(myhp6_5Image);
        myhp6_5.setTranslation(20f, 460f);

        Image myhp6_4Image = assets().getImage("images/myhp6_4.png");
        this.myhp6_4 = graphics().createImageLayer(myhp6_4Image);
        myhp6_4.setTranslation(20f, 460f);

        Image myhp6_3Image = assets().getImage("images/myhp6_3.png");
        this.myhp6_3 = graphics().createImageLayer(myhp6_3Image);
        myhp6_3.setTranslation(20f, 460f);

        Image myhp6_2Image = assets().getImage("images/myhp6_2.png");
        this.myhp6_2 = graphics().createImageLayer(myhp6_2Image);
        myhp6_2.setTranslation(20f, 460f);

        Image myhp6_1Image = assets().getImage("images/myhp6_1.png");
        this.myhp6_1 = graphics().createImageLayer(myhp6_1Image);
        myhp6_1.setTranslation(20f, 460f);

        Image myhp6_0Image = assets().getImage("images/myhp6_0.png");
        this.myhp6_0 = graphics().createImageLayer(myhp6_0Image);
        myhp6_0.setTranslation(20f, 460f);

        Image myhp7_7Image = assets().getImage("images/myhp7_7.png");
        this.myhp7_7 = graphics().createImageLayer(myhp7_7Image);
        myhp7_7.setTranslation(20f, 460f);

        Image myhp7_6Image = assets().getImage("images/myhp7_6.png");
        this.myhp7_6 = graphics().createImageLayer(myhp7_6Image);
        myhp7_6.setTranslation(20f, 460f);

        Image myhp7_5Image = assets().getImage("images/myhp7_5.png");
        this.myhp7_5 = graphics().createImageLayer(myhp7_5Image);
        myhp7_5.setTranslation(20f, 460f);

        Image myhp7_4Image = assets().getImage("images/myhp7_4.png");
        this.myhp7_4 = graphics().createImageLayer(myhp7_4Image);
        myhp7_4.setTranslation(20f, 460f);

        Image myhp7_3Image = assets().getImage("images/myhp7_3.png");
        this.myhp7_3 = graphics().createImageLayer(myhp7_3Image);
        myhp7_3.setTranslation(20f, 460f);

        Image myhp7_2Image = assets().getImage("images/myhp7_2.png");
        this.myhp7_2 = graphics().createImageLayer(myhp7_2Image);
        myhp7_2.setTranslation(20f, 460f);

        Image myhp7_1Image = assets().getImage("images/myhp7_1.png");
        this.myhp7_1 = graphics().createImageLayer(myhp7_1Image);
        myhp7_1.setTranslation(20f, 460f);

        Image myhp7_0Image = assets().getImage("images/myhp7_0.png");
        this.myhp7_0 = graphics().createImageLayer(myhp7_0Image);
        myhp7_0.setTranslation(20f, 460f);

        Image coinImage = assets().getImage("images/coin.png");
        this.coin = graphics().createImageLayer(coinImage);
        coin.setTranslation(615f, 455f);

        Image box1Image = assets().getImage("images/box1.png");
        this.box1 = graphics().createImageLayer(box1Image);
        box1.setTranslation(0f, 460f);

        Image box4Image = assets().getImage("images/box4.png");
        this.box4 = graphics().createImageLayer(box4Image);
        box4.setTranslation(87f, 400f);

        Image pauseScreenImage = assets().getImage("images/pausescreen.png");
        this.pauseScreen = graphics().createImageLayer(pauseScreenImage);
        pauseScreen.setOrigin(300f/2f , 350f/2f);
        pauseScreen.setTranslation(320f, 240f);

        Image pResumeImage = assets().getImage("images/pbutton.png");
        this.pResume = graphics().createImageLayer(pResumeImage);
        pResume.setOrigin(135f/2f, 55f/2f);
        pResume.setTranslation(320f, 160f);
        pResume.addListener(new Mouse.LayerAdapter(){
            @Override
            public void onMouseUp(Mouse.ButtonEvent event) {
                pause = false;
                layer.remove(pauseScreen);
                layer.remove(pResume);
                layer.remove(pRestart);
                layer.remove(pExit);
            }
        });

        Image pRestartImage = assets().getImage("images/pbutton.png");
        this.pRestart = graphics().createImageLayer(pRestartImage);
        pRestart.setOrigin(135f/2f, 55f/2f);
        pRestart.setTranslation(320f, 240f);
        pRestart.addListener(new Mouse.LayerAdapter(){
            @Override
            public void onMouseUp(Mouse.ButtonEvent event) {
                myhp = lvscreen.myhp;
                mygold = lvscreen.mygold;
                hpmonsterRat = 2;
                hpmonsterRat2 = 2;
                pause2 = false;
                ss.remove(ss.top());
                ss.push(new GameScreen2(ss));
            }
        });

        Image pExitImage = assets().getImage("images/pbutton.png");
        this.pExit = graphics().createImageLayer(pExitImage);
        pExit.setOrigin(135f/2f, 55f/2f);
        pExit.setTranslation(320f, 320f);
        pExit.addListener(new Mouse.LayerAdapter(){
            @Override
            public void onMouseUp(Mouse.ButtonEvent event) {
                myhp = lvscreen.myhp;
                mygold = lvscreen.mygold;
                hpmonsterRat = 2;
                hpmonsterRat2 = 2;
                ss.remove(ss.top());
                ss.push(new LevelSelectScreen(ss));
            }
        });

        Image pauseButtonImage = assets().getImage("images/pause.png");
        this.pauseButton = graphics().createImageLayer(pauseButtonImage);
        pauseButton.setTranslation(10,10);
        pauseButton.addListener(new Mouse.LayerAdapter() {
            @Override
            public void onMouseUp(Mouse.ButtonEvent event) {
                pause = true;
                layer.add(pauseScreen);
                layer.add(pResume);
                layer.add(pRestart);
                layer.add(pExit);
            }
        });

        Image gameoverImage = assets().getImage("images/gameover.png");
        this.gameover = graphics().createImageLayer(gameoverImage);
        gameover.setTranslation(0f,0f);

        Image goRestartImage = assets().getImage("images/gobutton.png");
        this.goRestart = graphics().createImageLayer(goRestartImage);
        goRestart.setTranslation(220f, 330f);
        goRestart.addListener(new Mouse.LayerAdapter(){
            @Override
            public void onMouseUp(Mouse.ButtonEvent event) {
                myhp = lvscreen.myhp;
                pause2 = false;
                ss.remove(ss.top());
                ss.push(new GameScreen2(ss));
            }
        });

        Image goHomeImage = assets().getImage("images/gobutton.png");
        this.goHome = graphics().createImageLayer(goHomeImage);
        goHome.setTranslation(355f, 330f);
        goHome.addListener(new Mouse.LayerAdapter(){
            @Override
            public void onMouseUp(Mouse.ButtonEvent event) {
                lvscreen.myhp = 2;
                lvscreen.maxhp = 2;
                lvscreen.mydamage = 1;
                lvscreen.mygold = 0;
                lvscreen.heartupgrade = 1;
                lvscreen.powerupgrade = 1;
                hpmonsterRat = 2;
                hpmonsterRat2 = 2;
                clear = false;
                ss.remove(ss.top());
                ss.push(new HomeScreen(ss));
            }
        });

        Image stage2clearImage = assets().getImage("images/stage2clear.png");
        this.stage2clear = graphics().createImageLayer(stage2clearImage);
        stage2clear.setTranslation(0f, 0f);

        Image scNextImage = assets().getImage("images/scbutton.png");
        this.scNext = graphics().createImageLayer(scNextImage);
        scNext.setTranslation(480f, 340f);
        scNext.addListener(new Mouse.LayerAdapter(){
            @Override
            public void onMouseUp(Mouse.ButtonEvent event) {
                pause3 = false;
                lvscreen.myhp = myhp;
                lvscreen.mygold = mygold;
                ss.remove(ss.top());
                ss.push(new LevelSelectScreen(ss));
            }
        });

        Vec2 gravity = new Vec2(0.0f , 10.0f);
        world = new World(gravity);
        world.setWarmStarting(true);
        world.setAutoClearForces(true);

        naki2 = new Naki2(world, 180f, 420f);
        digit1 = new Digit1(595f, 455f);
        digit2 = new Digit2(580f, 455f);
        digit3 = new Digit3(565f, 455f);
        digit4 = new Digit4(550f, 455f);
        digit5 = new Digit5(535f, 455f);
        digit6 = new Digit6(520f, 455f);

        effectList2 = new ArrayList<NakiEffect2>();

        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                Body a = contact.getFixtureA().getBody();
                Body b = contact.getFixtureB().getBody();
                //================================พลังชนแล้ว พลังหาย=======================================================
                for(NakiEffect2 effect2 : effectList2){
                    if((contact.getFixtureA().getBody() == effect2.getBody() &&
                            (bodies.get(b) == "ground" || bodies.get(b) == "left" || bodies.get(b) == "right"))
                            || (contact.getFixtureB().getBody() == effect2.getBody() &&
                            (bodies.get(a) == "ground" || bodies.get(a) == "left" || bodies.get(a) == "right"))){
                        effect2.layer().setVisible(false);
                        if(effect2.getBody() == a){
                            a.setActive(false);
                        }else if(effect2.getBody() == b){
                            b.setActive(false);
                        }
                    }else if((contact.getFixtureA().getBody() == effect2.getBody() &&
                            (bodies.get(b) == "box1" || bodies.get(b) == "box1_2" ||
                            bodies.get(b) == "box2" || bodies.get(b) == "box3" ||
                            bodies.get(b) == "box3_2")) ||
                            (contact.getFixtureB().getBody() == effect2.getBody() &&
                                    (bodies.get(a) == "box1") || bodies.get(a) == "box1_2"
                            || bodies.get(a) == "box2" || bodies.get(a) == "box3"
                            || bodies.get(a) == "box3_2")){
                        effect2.layer().setVisible(false);
                        if(effect2.getBody() == a){
                            a.setActive(false);
                        }else if(effect2.getBody() == b){
                            b.setActive(false);
                        }
                    }
                    //==================================================================================================
                }
                if ((bodies.get(a) == "naki2" && (bodies.get(b) == "ground" ||
                        bodies.get(b) == "box1" || bodies.get(b) == "box1_2" ||
                        bodies.get(b) == "box2" || bodies.get(b) == "box3" ||
                        bodies.get(b) == "box3_2")) ||
                        (bodies.get(b) == "naki2" && (bodies.get(a) == "ground" ||
                                bodies.get(a) == "box1" || bodies.get(a) == "box1_2" ||
                                bodies.get(a) == "box2" || bodies.get(a) == "box3" ||
                                bodies.get(a) == "box3_2"))) {

                    Naki2.checkjump = true;
                }
                //===========================================================================================================
                if(myhp == 0){
                    pause2 = true;
                }if((Naki2.checkmove >= 525 && Naki2.checkmove <= 600) && (Naki2.checkmove2 >= 70 && Naki2.checkmove2 <= 80)){
                    pause3 = true;
                    clear = true;
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
    }

    @Override
    public void wasShown() {
        super.wasShown();
        this.layer.add(bg);
        this.layer.add(pauseButton);
        this.layer.add(naki2.layer());
        this.layer.add(groupEffect2);
        this.layer.add(coin);
        this.layer.add(box1);
        this.layer.add(box4);

        //this.layer.add(coin);

        if (showDebugDraw) {
            CanvasImage image = graphics().createImage(
                    (int) (width / GameScreen2.M_PER_PIXEL),
                    (int) (height / GameScreen2.M_PER_PIXEL));
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
            debugDraw.setCamera(0, 0, 1f / GameScreen2.M_PER_PIXEL);
            world.setDebugDraw(debugDraw);
        }

        /*Body ground = world.createBody(new BodyDef());
        EdgeShape groundShape = new EdgeShape();
        groundShape.set(new Vec2(0, 17), new Vec2(width, 17));
        ground.createFixture(groundShape, 0.0f);
        bodies.put(ground,"ground");*/

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

        Body box1Square = world.createBody(new BodyDef());
        PolygonShape box1Shape = new PolygonShape();
        box1Shape.setAsBox(85 * M_PER_PIXEL / 2,20 * M_PER_PIXEL / 2);
        box1Square.setTransform(new Vec2(43*M_PER_PIXEL, 470*M_PER_PIXEL), 0f);
        box1Square.createFixture(box1Shape, 0.0f);
        bodies.put(box1Square, "box1");

        Body box4Square = world.createBody(new BodyDef());
        PolygonShape box4Shape = new PolygonShape();
        box4Shape.setAsBox(85 * M_PER_PIXEL / 2,80 * M_PER_PIXEL / 2);
        box4Square.setTransform(new Vec2(128*M_PER_PIXEL, 440*M_PER_PIXEL), 0f);
        box4Square.createFixture(box4Shape, 0.0f);
        bodies.put(box4Square, "box4");
    }

    @Override
    public void update(int delta) {
        if(pause2 == true){
            this.layer.add(gameover);
            this.layer.add(goRestart);
            this.layer.add(goHome);
            if(maxhp == 2){
                this.layer.add(myhp2_0);
            }else if (maxhp == 3){
                this.layer.add(myhp3_0);
            }else if (maxhp == 4){
                this.layer.add(myhp4_0);
            }else if (maxhp == 5){
                this.layer.add(myhp5_0);
            }else if (maxhp == 6){
                this.layer.add(myhp6_0);
            }else if (maxhp == 7){
                this.layer.add(myhp7_0);
            }
        }
        if(pause3 == true){
            this.layer.add(stage2clear);
            this.layer.add(scNext);
        }
        if(pause == false && pause2 == false && pause3 == false) {
            super.update(delta);
            naki2.update(delta);
            //==============================แสดงเลือด===================================================================
            if(maxhp == 2) {
                if (myhp == 2) {
                    this.layer.add(myhp2_2);
                } else if (myhp == 1) {
                    this.layer.add(myhp2_1);
                }
            }else if(maxhp == 3){
                if (myhp == 3){
                    this.layer.add(myhp3_3);
                }else if (myhp == 2){
                    this.layer.add(myhp3_2);
                }else if (myhp == 1){
                    this.layer.add(myhp3_1);
                }
            }else if(maxhp == 4){
                if (myhp == 4){
                    this.layer.add(myhp4_4);
                }else if (myhp == 3){
                    this.layer.add(myhp4_3);
                }else if (myhp == 2){
                    this.layer.add(myhp4_2);
                }else if (myhp == 1){
                    this.layer.add(myhp4_1);
                }
            }else if(maxhp == 5){
                if (myhp == 5){
                    this.layer.add(myhp5_5);
                }else if (myhp == 4){
                    this.layer.add(myhp5_4);
                }else if (myhp == 3){
                    this.layer.add(myhp5_3);
                }else if (myhp == 2){
                    this.layer.add(myhp5_2);
                }else if (myhp == 1){
                    this.layer.add(myhp5_1);
                }
            }else if(maxhp == 6){
                if (myhp == 6){
                    this.layer.add(myhp6_6);
                }else if (myhp == 5){
                    this.layer.add(myhp6_5);
                }else if (myhp == 4){
                    this.layer.add(myhp6_4);
                }else if (myhp == 3){
                    this.layer.add(myhp6_3);
                }else if (myhp == 2){
                    this.layer.add(myhp6_2);
                }else if (myhp == 1){
                    this.layer.add(myhp6_1);
                }
            }else if(maxhp == 7){
                if (myhp == 7){
                    this.layer.add(myhp7_7);
                }else if (myhp == 6){
                    this.layer.add(myhp7_6);
                }else if (myhp == 5){
                    this.layer.add(myhp7_5);
                }else if (myhp == 4){
                    this.layer.add(myhp7_4);
                }else if (myhp == 3){
                    this.layer.add(myhp7_3);
                }else if (myhp == 2){
                    this.layer.add(myhp7_2);
                }else if (myhp == 1){
                    this.layer.add(myhp7_1);
                }
            }
            //====================================== แสดงเงิน ============================================================
            temp = mygold;
            while (temp != 0){
                x = temp % 10;
                if(countdigit == 1){
                    numberdigit1 = x;
                    this.layer.add(digit1.layer());
                    countdigit++;
                }else if(countdigit == 2){
                    numberdigit2 = x;
                    this.layer.add(digit2.layer());
                    countdigit++;
                }else if(countdigit == 3){
                    numberdigit3 = x;
                    this.layer.add(digit3.layer());
                    countdigit++;
                }else if(countdigit == 4){
                    numberdigit4 = x;
                    this.layer.add(digit4.layer());
                    countdigit++;
                }else if(countdigit == 5){
                    numberdigit5 = x;
                    this.layer.add(digit5.layer());
                    countdigit++;
                }else if(countdigit == 6){
                    numberdigit6 = x;
                    this.layer.add(digit6.layer());
                    countdigit++;
                }
                temp /= 10;
            }
            if(temp == 0){
                countdigit = 1;
            }
            //==========================================================================================================
            if(naki2.checkmove2 >= 550){
                pause2 = true;
                naki2.checkmove2 = 420;
            }
            for (NakiEffect2 effect2 : effectList2) {
                effect2.update(delta);
            }
            for (NakiEffect2 effect2 : effectList2) {
                groupEffect2.add(effect2.layer());
            }
            digit1.update(delta);
            digit2.update(delta);
            digit3.update(delta);
            digit4.update(delta);
            digit5.update(delta);
            digit6.update(delta);
            world.step(0.033f, 10, 10);
        }
    }

    @Override
    public void paint(Clock clock) {
        if(pause == false) {
            super.paint(clock);
            naki2.paint(clock);
            for (NakiEffect2 effect2 : effectList2) {
                effect2.paint(clock);
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

