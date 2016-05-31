package game01.core;

import static playn.core.PlayN.*;

import Coin.*;
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
    private ImageLayer stage1clear;
    private ImageLayer scNext;
    private ImageLayer box1;
    private ImageLayer box1_2;
    private ImageLayer box2;
    private ImageLayer box3;
    private ImageLayer box3_2;
    private ImageLayer treasurebox;
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
    private Naki naki;
    private MonsterRat monsterRat;
    private MonsterRat2 monsterRat2;
    private Warp warp;
    private Firewall firewall;
    private GroupLayer groupEffect = graphics().createGroupLayer();
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
    private int count3 = 0;
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
    public static boolean clear = true;
    public static List<NakiEffect> effectList;
    public static int numberdigit1 = 0;
    public static int numberdigit2 = 0;
    public static int numberdigit3 = 0;
    public static int numberdigit4 = 0;
    public static int numberdigit5 = 0;
    public static int numberdigit6 = 0;
    public static int x = 0;

    public void addNakiEffect(NakiEffect effect){
        effectList.add(effect);
    }
    public GameScreen(){}
    public GameScreen(final ScreenStack ss) {
        this.ss = ss;
        graphics().rootLayer().clear();

        Image bgImage = assets().getImage("images/gameScreen.png");
        this.bg = graphics().createImageLayer(bgImage);

        Image box3Image = assets().getImage("images/box3.png");
        this.box3 = graphics().createImageLayer(box3Image);
        box3.setTranslation(385f, 350f);

        Image box3_2Image = assets().getImage("images/box3.png");
        this.box3_2 = graphics().createImageLayer(box3_2Image);
        box3_2.setTranslation(10f, 300f);

        Image box2Image = assets().getImage("images/box2.png");
        this.box2 = graphics().createImageLayer(box2Image);
        box2.setTranslation(230f, 150f);

        Image box1Image = assets().getImage("images/box1.png");
        this.box1 = graphics().createImageLayer(box1Image);
        box1.setTranslation(520f, 110f);

        Image box1_2Image = assets().getImage("images/box1.png");
        this.box1_2 = graphics().createImageLayer(box1_2Image);
        box1_2.setTranslation(10f, 200f);

        Image treasureboxImage = assets().getImage("images/treasurebox.png");
        this.treasurebox = graphics().createImageLayer(treasureboxImage);
        treasurebox.setTranslation(10f, 155f);

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
                ss.push(new GameScreen(ss));
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
                hpmonsterRat = 2;
                hpmonsterRat2 = 2;
                pause2 = false;
                ss.remove(ss.top());
                ss.push(new GameScreen(ss));
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

        Image stage1clearImage = assets().getImage("images/stage1clear.png");
        this.stage1clear = graphics().createImageLayer(stage1clearImage);
        stage1clear.setTranslation(0f, 0f);

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

        naki = new Naki(world, 180f, 420f);
        warp = new Warp(560f, 80f);
        firewall = new Firewall(world, 390f, 95f);
        monsterRat = new MonsterRat(world, 600f, 330f);
        monsterRat2 = new MonsterRat2(world, 40f, 280f);
        digit1 = new Digit1(595f, 455f);
        digit2 = new Digit2(580f, 455f);
        digit3 = new Digit3(565f, 455f);
        digit4 = new Digit4(550f, 455f);
        digit5 = new Digit5(535f, 455f);
        digit6 = new Digit6(520f, 455f);

        effectList = new ArrayList<NakiEffect>();

        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                Body a = contact.getFixtureA().getBody();
                Body b = contact.getFixtureB().getBody();
                //================================พลังชนแล้ว พลังหาย=======================================================
                for(NakiEffect effect : effectList){
                    if((contact.getFixtureA().getBody() == effect.getBody() &&
                            (bodies.get(b) == "ground" || bodies.get(b) == "left" || bodies.get(b) == "right"))
                            || (contact.getFixtureB().getBody() == effect.getBody() &&
                            (bodies.get(a) == "ground" || bodies.get(a) == "left" || bodies.get(a) == "right"))){
                        effect.layer().setVisible(false);
                        if(effect.getBody() == a){
                            a.setActive(false);
                        }else if(effect.getBody() == b){
                            b.setActive(false);
                        }
                    }else if((contact.getFixtureA().getBody() == effect.getBody() &&
                            (bodies.get(b) == "box1" || bodies.get(b) == "box1_2" ||
                            bodies.get(b) == "box2" || bodies.get(b) == "box3" ||
                            bodies.get(b) == "box3_2")) ||
                            (contact.getFixtureB().getBody() == effect.getBody() &&
                                    (bodies.get(a) == "box1") || bodies.get(a) == "box1_2"
                            || bodies.get(a) == "box2" || bodies.get(a) == "box3"
                            || bodies.get(a) == "box3_2")){
                        effect.layer().setVisible(false);
                        if(effect.getBody() == a){
                            a.setActive(false);
                        }else if(effect.getBody() == b){
                            b.setActive(false);
                        }
                    }else if((contact.getFixtureA().getBody() == effect.getBody() &&
                            (bodies.get(b) == "monsterRat"))){
                        effect.layer().setVisible(false);
                        a.setActive(false);
                        hpmonsterRat -= lvscreen.mydamage;
                        if(hpmonsterRat <= 0) {
                            mygold += 50;
                            b.setActive(false);
                            monsterRat.layer().setVisible(false);
                        }
                    }else if(contact.getFixtureB().getBody() == effect.getBody() &&
                            bodies.get(a) == "monsterRat") {
                        effect.layer().setVisible(false);
                        b.setActive(false);
                        hpmonsterRat -= lvscreen.mydamage;
                        if (hpmonsterRat <= 0) {
                            mygold += 50;
                            a.setActive(false);
                            monsterRat.layer().setVisible(false);
                        }
                    }else if((contact.getFixtureA().getBody() == effect.getBody() &&
                            (bodies.get(b) == "monsterRat2"))){
                        effect.layer().setVisible(false);
                        a.setActive(false);
                        hpmonsterRat2 -= lvscreen.mydamage;
                        if(hpmonsterRat2 <= 0) {
                            mygold += 50;
                            b.setActive(false);
                            monsterRat2.layer().setVisible(false);
                        }
                    }else if(contact.getFixtureB().getBody() == effect.getBody() &&
                            bodies.get(a) == "monsterRat2") {
                        effect.layer().setVisible(false);
                        b.setActive(false);
                        hpmonsterRat2 -= lvscreen.mydamage;
                        if (hpmonsterRat2 <= 0) {
                            mygold += 50;
                            a.setActive(false);
                            monsterRat2.layer().setVisible(false);
                        }
                    }
                }
                //======================================================================================================
                //==============================โดน monster แล้วเลือดลด===================================================
                if(bodies.get(a) == "naki" && bodies.get(b) == "monsterRat"){
                    myhp--;
                    a.applyForce(new Vec2(-100f, -850f), a.getPosition());
                    if(monsterRat.checkmove == true){
                        monsterRat.setCheckmove(false);
                    }else{
                        if(monsterRat.movelr >= 440 && monsterRat.movelr <= 450)
                            monsterRat.setCheckmove(true);
                    }
                }else if(bodies.get(a) == "monsterRat" && bodies.get(b) == "naki"){
                    myhp--;
                    b.applyForce(new Vec2(-100f, -850f), b.getPosition());
                    if(monsterRat.checkmove == true){
                        monsterRat.setCheckmove(false);
                    }else{
                        if(monsterRat.movelr >= 440 && monsterRat.movelr <= 450)
                            monsterRat.setCheckmove(true);
                    }
                }if(bodies.get(a) == "naki" && bodies.get(b) == "monsterRat2"){
                    myhp--;
                    a.applyForce(new Vec2(100f, -850f), a.getPosition());
                    if(monsterRat2.checkmove == true){
                        if(!(monsterRat2.movelr >= 200 && monsterRat2.movelr <= 250))
                            monsterRat2.setCheckmove(false);
                    }else{
                        monsterRat2.setCheckmove(true);
                    }
                }else if(bodies.get(a) == "monsterRat2" && bodies.get(b) == "naki") {
                    myhp--;
                    b.applyForce(new Vec2(100f, -850f), b.getPosition());
                    if (monsterRat2.checkmove == true) {
                        if(!(monsterRat2.movelr >= 200 && monsterRat2.movelr <= 250))
                            monsterRat2.setCheckmove(false);
                    } else {
                        monsterRat2.setCheckmove(true);
                    }
                    //======================================================================================================
                }
                //=================================เช็คกระโดด================================================================
                if ((bodies.get(a) == "naki" && (bodies.get(b) == "ground" ||
                        bodies.get(b) == "box1" || bodies.get(b) == "box1_2" ||
                        bodies.get(b) == "box2" || bodies.get(b) == "box3" ||
                        bodies.get(b) == "box3_2")) ||
                        (bodies.get(b) == "naki" && (bodies.get(a) == "ground" ||
                                bodies.get(a) == "box1" || bodies.get(a) == "box1_2" ||
                                bodies.get(a) == "box2" || bodies.get(a) == "box3" ||
                                bodies.get(a) == "box3_2"))) {

                    Naki.checkjump = true;
                }
                //===========================================================================================================
                //=====================เก็บของ==================================================================================
                if(bodies.get(a) == "naki" && bodies.get(b) == "treasure"){
                    mygold += 200;
                    b.setActive(false);
                    treasurebox.setVisible(false);
                }else if(bodies.get(a) == "treasure" && bodies.get(b) == "naki"){
                    mygold += 200;
                    a.setActive(false);
                    treasurebox.setVisible(false);
                }
                //==========================================================================================================
                if(bodies.get(a) == "naki" && bodies.get(b) == "firewall"){
                    myhp--;
                    a.applyForce(new Vec2(-300f, -850f), b.getPosition());
                }else if(bodies.get(a) == "firewall" && bodies.get(b) == "naki"){
                    myhp--;
                    b.applyForce(new Vec2(-300f, -850f), b.getPosition());
                }
                if(myhp == 0){
                    pause2 = true;
                }if((Naki.checkmove >= 525 && Naki.checkmove <= 600) && (Naki.checkmove2 >= 70 && Naki.checkmove2 <= 80)){
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
        this.layer.add(naki.layer());
        //this.layer.add(monsterNinja.layer());
        this.layer.add(monsterRat.layer());
        this.layer.add(monsterRat2.layer());
        //this.layer.add(nakiEffect.layer());
        this.layer.add(warp.layer());
        this.layer.add(firewall.layer());
        this.layer.add(groupEffect);
        this.layer.add(box1);
        this.layer.add(box1_2);
        this.layer.add(box2);
        this.layer.add(box3);
        this.layer.add(box3_2);
        this.layer.add(coin);
        if(clear == false) {
            this.layer.add(treasurebox);
        }
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
        box3_2Square.setTransform(new Vec2(140*M_PER_PIXEL, 310*M_PER_PIXEL), 0f);
        box3_2Square.createFixture(box3_2Shape, 0.0f);
        bodies.put(box3_2Square, "box3_2");

        Body box2Square = world.createBody(new BodyDef());
        PolygonShape box2Shape = new PolygonShape();
        box2Shape.setAsBox(170 * M_PER_PIXEL / 2,20 * M_PER_PIXEL / 2);
        box2Square.setTransform(new Vec2(315*M_PER_PIXEL, 160*M_PER_PIXEL), 0f);
        box2Square.createFixture(box2Shape, 0.0f);
        bodies.put(box2Square, "box2");

        Body box1Square = world.createBody(new BodyDef());
        PolygonShape box1Shape = new PolygonShape();
        box1Shape.setAsBox(85 * M_PER_PIXEL / 2,20 * M_PER_PIXEL / 2);
        box1Square.setTransform(new Vec2(560*M_PER_PIXEL, 120*M_PER_PIXEL), 0f);
        box1Square.createFixture(box1Shape, 0.0f);
        bodies.put(box1Square, "box1");

        Body box1_2Square = world.createBody(new BodyDef());
        PolygonShape box1_2Shape = new PolygonShape();
        box1_2Shape.setAsBox(85 * M_PER_PIXEL / 2,20 * M_PER_PIXEL / 2);
        box1_2Square.setTransform(new Vec2(55*M_PER_PIXEL, 210*M_PER_PIXEL), 0f);
        box1_2Square.createFixture(box1_2Shape, 0.0f);
        bodies.put(box1_2Square, "box1_2");

        if(clear == false) {
            Body treasureboxSquare = world.createBody(new BodyDef());
            PolygonShape treasureboxShape = new PolygonShape();
            treasureboxShape.setAsBox(45 * M_PER_PIXEL / 2, 38 * M_PER_PIXEL / 2);
            treasureboxSquare.setTransform(new Vec2(34 * M_PER_PIXEL, 174 * M_PER_PIXEL), 0f);
            treasureboxSquare.createFixture(treasureboxShape, 0.0f);
            bodies.put(treasureboxSquare, "treasure");
        }
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
            this.layer.add(stage1clear);
            this.layer.add(scNext);
        }
        if(pause == false && pause2 == false && pause3 == false) {
            super.update(delta);
            naki.update(delta);
            monsterRat.update(delta);
            monsterRat2.update(delta);
            warp.update(delta);
            firewall.update(delta);
            count3++;
            if(count3 == 50){
                firewall.layer().setVisible(false);
                firewall.getBody().setActive(false);
            }else if(count3 == 120){
                firewall.layer().setVisible(true);
                firewall.getBody().setActive(true);
                count3 = 0;
            }
            //====================== monster เกิด =======================================================================
            if(hpmonsterRat <= 0) {
                count1++;
                if(count1 == 500){
                    setBorn();
                }
            }
            if(hpmonsterRat2 <= 0) {
                count2++;
                if (count2 == 500) {
                    setBorn();
                }
            }
            //==========================================================================================================
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
            for (NakiEffect effect : effectList) {
                effect.update(delta);
            }
            for (NakiEffect effect : effectList) {
                groupEffect.add(effect.layer());
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
        if(pause == false ) {
            super.paint(clock);
            naki.paint(clock);
            monsterRat.paint(clock);
            monsterRat2.paint(clock);
            firewall.paint(clock);
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
    public void setBorn(){
        if(count1 == 500) {
            monsterRat.layer().setVisible(true);
            monsterRat.getBody().setActive(true);
            hpmonsterRat = 2;
            count1 = 0;
        }else if(count2 == 500){
            monsterRat2.layer().setVisible(true);
            monsterRat2.getBody().setActive(true);
            hpmonsterRat2 = 2;
            count2 = 0;
        }
    }
}

