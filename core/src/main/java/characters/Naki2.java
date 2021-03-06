package characters;

import game01.core.GameScreen;
import game01.core.GameScreen2;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;
import playn.core.Key;
import playn.core.Keyboard;
import playn.core.Layer;
import playn.core.PlayN;
import playn.core.util.Callback;
import playn.core.util.Clock;
import sprite.Sprite;
import sprite.SpriteLoader;
import tripleplay.game.Screen;

import java.util.ArrayList;
import java.util.List;

public class Naki2 extends Screen {
    private Sprite sprite;
    private int spriteIndex = 0;
    private boolean hasLoaded = false;
    private Body body;
    private World world;
    private List<NakiEffect2> effectList2;
    public static GameScreen2 game2 = new GameScreen2();
    public static boolean checkjump = true;
    public static float checkmove;
    public static float checkmove2;

    public enum State {
        IDLEL, IDLER, WALKL, WALKR, JUMPL, JUMPR, ATTKL, ATTKR
    };

    private  State state = State.IDLEL;

    private  int e = 0;
    private  int offset = 0;

    public Naki2(final World world, final float x_px, final float y_px) {
        this.world = world;
        effectList2 = new ArrayList<NakiEffect2>();

        sprite = SpriteLoader.getSprite("images/naki.json");
        sprite.addCallback(new Callback<Sprite>() {
            @Override
            public void onSuccess(Sprite result) {
                sprite.setSprite(spriteIndex);
                sprite.layer().setOrigin(sprite.width() / 2f, sprite.height() / 2f);
                sprite.layer().setTranslation(x_px, y_px + 13f);

                body = initPhysicsBody(world,
                        GameScreen2.M_PER_PIXEL * x_px - 5,
                        GameScreen2.M_PER_PIXEL * y_px);
                hasLoaded = true;
            }

            @Override
            public void onFailure(Throwable cause) {
                PlayN.log().error("Error loading image!", cause);
            }
        });

    }

    public Layer layer() {
        return sprite.layer();
    }

    private Body initPhysicsBody(World world, float x, float y) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.DYNAMIC;
        bodyDef.position = new Vec2(0,0);
        Body body = world.createBody(bodyDef);

        GameScreen2.bodies.put(body, "naki");

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(sprite.layer().width() * GameScreen2.M_PER_PIXEL / 2, sprite.layer().height()* GameScreen2.M_PER_PIXEL / 2);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.4f;
        fixtureDef.friction = 0.1f;
        fixtureDef.restitution = 0.2f;
        body.createFixture(fixtureDef);

        body.setLinearDamping(0.2f);
        body.setTransform(new Vec2(x,y), 0f);
        return body;
    }
    public Body getBody(){ return this.body; }

    public  void update(int delta) {
        if (hasLoaded == false) return;
        checkmove = body.getPosition().x/GameScreen2.M_PER_PIXEL;
        checkmove2 = body.getPosition().y/GameScreen2.M_PER_PIXEL;
        PlayN.keyboard().setListener(new Keyboard.Adapter(){
            @Override
            public void onKeyDown(Keyboard.Event event) {
                if (event.key() == Key.LEFT) {
                    checklr = true;
                    state = State.WALKL;
                }else if (event.key() == Key.RIGHT) {
                    checklr = false;
                    state = State.WALKR;
                }else if(event.key() == Key.UP){
                    if(checklr == true){
                        state = State.JUMPL;
                        if(checkjump == true) {
                            body.applyForce(new Vec2(-50f, -850f), body.getPosition());
                        }
                    }else if(checklr == false){
                        state = State.JUMPR;
                        if(checkjump == true) {
                            body.applyForce(new Vec2(50f, -850f), body.getPosition());
                        }
                    }
                }else if (event.key() == Key.SPACE) {
                    NakiEffect2 effect_1;
                    if (checklr == true) {
                        state = State.ATTKL;
                        effect_1 = new NakiEffect2(world, body.getPosition().x / GameScreen2.M_PER_PIXEL - 55,
                                body.getPosition().y / GameScreen2.M_PER_PIXEL, "L1");
                        game2.addNakiEffect2(effect_1);
                    } else if (checklr == false) {
                        state = State.ATTKR;
                        effect_1 = new NakiEffect2(world, body.getPosition().x / GameScreen2.M_PER_PIXEL + 55,
                                body.getPosition().y / GameScreen2.M_PER_PIXEL, "R1");
                        game2.addNakiEffect2(effect_1);
                    }
                }
            }

            @Override
            public void onKeyUp(Keyboard.Event event) {
                if(event.key() == Key.LEFT){
                    checklr = true;
                    state = State.IDLEL;
                    checkjump = true;
                }else if(event.key() == Key.RIGHT){
                    checklr = false;
                    state = State.IDLER;
                    checkjump = true;
                }else if(event.key() == Key.UP){
                    checkjump = false;
                    if(checklr == true){
                        state = State.IDLEL;
                    }else if(checklr == false){
                        state = State.IDLER;
                    }
                }else if(event.key() == Key.Z){
                    if(checklr == true){
                        state = State.IDLEL;
                    }else if(checklr == false){
                        state = State.IDLER;
                    }
                }
            }
        });
        e = e + delta;
        if (e > 150) {
            switch (state) {
                case IDLEL: offset = 0; break;
                case IDLER: offset = 5; break;
                case WALKL: offset = 10; break;
                case WALKR: offset = 14; break;
                case JUMPL: offset = 18; break;
                case JUMPR: offset = 19; break;
                case ATTKL: offset = 20;break;
                case ATTKR: offset = 22;break;
            }

            if(state == State.IDLEL || state == State.IDLER){
                spriteIndex = offset + ((spriteIndex + 1) % 5);
                sprite.setSprite(spriteIndex);
                e = 0;
            }else if(state == State.WALKL || state == State.WALKR){
                spriteIndex = offset + ((spriteIndex + 1) % 4);
                sprite.setSprite(spriteIndex);
                e = 0;
            }else if(state == State.JUMPL || state == State.JUMPR){
                spriteIndex = offset + ((spriteIndex + 1) % 1);
                sprite.setSprite(spriteIndex);
                e = 0;
            }else if(state == State.ATTKL || state == State.ATTKR){
                spriteIndex = offset + ((spriteIndex + 1) % 2);
                sprite.setSprite(spriteIndex);
                e = 0;
            }else{
                sprite.setSprite(spriteIndex);
                e = 0;
            }if(spriteIndex == 20){
                state = State.IDLEL;
            }else if(spriteIndex == 22){
                state = State.IDLER;
            }
            /*spriteIndex = offset + ((spriteIndex + 1) % 5);
            sprite.setSprite(spriteIndex);
            e = 0;*/
        }
    }

    public void paint(Clock clock) {
        if (!hasLoaded) return;
        //sprite.layer().setRotation(body.getAngle());
        sprite.layer().setTranslation(
                (body.getPosition().x / GameScreen2.M_PER_PIXEL) - 10,
                body.getPosition().y / GameScreen2.M_PER_PIXEL);

        switch (state){
            case WALKL:
                checklr = true;
                Walk(checklr);
                break;
            case WALKR:
                checklr = false;
                Walk(checklr);
                break;
        }
    }

    private boolean checklr = false;
    private void Walk(boolean checklr){
        if(checklr == true){
            body.applyForce(new Vec2(-10f, 0f), body.getPosition());
        }else{
            body.applyForce(new Vec2(10f, 0f), body.getPosition());
        }
    }
}
