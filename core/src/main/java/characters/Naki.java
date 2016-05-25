package characters;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;
import playn.core.*;
import playn.core.util.Callback;
import playn.core.util.Clock;
import sprite.Sprite;
import sprite.SpriteLoader;
import game01.core.GameScreen;
import tripleplay.game.Screen;

import java.util.ArrayList;
import java.util.List;

public class Naki extends Screen {
    private Sprite sprite;
    private int spriteIndex = 0;
    private boolean hasLoaded = false;
    private Body body;
    private World world;
    private List<NakiEffect> effectList;
    public static GameScreen game = new GameScreen();

    public enum State {
        IDLEL, IDLER, WALKL, WALKR, JUMPL, JUMPR, ATTKL, ATTKR
    };

    private  State state = State.IDLEL;

    private  int e = 0;
    private  int offset = 0;

    public Naki(final World world, final float x_px, final float y_px) {
        this.world = world;
        effectList = new ArrayList<NakiEffect>();

        sprite = SpriteLoader.getSprite("images/naki.json");
        sprite.addCallback(new Callback<Sprite>() {
            @Override
            public void onSuccess(Sprite result) {
                sprite.setSprite(spriteIndex);
                sprite.layer().setOrigin(sprite.width() / 2f, sprite.height() / 2f);
                sprite.layer().setTranslation(x_px, y_px + 13f);

                body = initPhysicsBody(world,
                        GameScreen.M_PER_PIXEL * x_px - 5,
                        GameScreen.M_PER_PIXEL * y_px);
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

        GameScreen.bodies.put(body, "naki");

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(sprite.layer().width() * GameScreen.M_PER_PIXEL / 2, sprite.layer().height()* GameScreen.M_PER_PIXEL / 2);
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
                        body.applyForce(new Vec2(-50f, -850f), body.getPosition());
                    }else if(checklr == false){
                        state = State.JUMPR;
                        body.applyForce(new Vec2(50f, -850f), body.getPosition());
                    }
                }else if (event.key() == Key.A) {
                    NakiEffect effect_1;
                    if(checklr == true){
                        state = State.ATTKL;
                        effect_1 = new NakiEffect(world, body.getPosition().x / GameScreen.M_PER_PIXEL - 55,
                                                         body.getPosition().y / GameScreen.M_PER_PIXEL, 'L');
                        game.addNakiEffect(effect_1);
                    }else if(checklr == false){
                        state = State.ATTKR;
                        effect_1 = new NakiEffect(world, body.getPosition().x / GameScreen.M_PER_PIXEL + 55,
                                                         body.getPosition().y / GameScreen.M_PER_PIXEL, 'R');
                        game.addNakiEffect(effect_1);
                    }
                }
            }

            @Override
            public void onKeyUp(Keyboard.Event event) {
                if(event.key() == Key.LEFT){
                    checklr = true;
                    state = State.IDLEL;
                }else if(event.key() == Key.RIGHT){
                    checklr = false;
                    state = State.IDLER;
                }else if(event.key() == Key.UP){
                    if(checklr == true){
                        state = State.IDLEL;
                    }else if(checklr == false){
                        state = State.IDLER;
                    }
                }else if(event.key() == Key.A){
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
                (body.getPosition().x / GameScreen.M_PER_PIXEL) - 10,
                body.getPosition().y / GameScreen.M_PER_PIXEL);

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
