package characters;

import game01.core.GameScreen;
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

import java.util.ArrayList;
import java.util.List;

public class MonsterRat {
    private Sprite sprite;
    private int spriteIndex = 0;
    private boolean hasLoaded = false;
    private Body body;
    private World world;
    private List<NakiEffect> effectList;
    public static GameScreen game = new GameScreen();
    private float movelr;
    private boolean checkmove;

    public enum State {
        WALKL,WALKR,DIEL,DIER
    };

    private State state = State.WALKL;

    private  int e = 0;
    private  int offset = 0;

    public MonsterRat(final World world, final float x_px, final float y_px) {
        this.world = world;
        sprite = SpriteLoader.getSprite("images/monsterrat.json");
        sprite.addCallback(new Callback<Sprite>() {
            @Override
            public void onSuccess(Sprite result) {
                sprite.setSprite(spriteIndex);
                sprite.layer().setOrigin(sprite.width() / 2f, sprite.height() / 2f);
                sprite.layer().setTranslation(x_px, y_px + 13f);

                body = initPhysicsBody(world,
                        GameScreen.M_PER_PIXEL * x_px,
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

        GameScreen.bodies.put(body, "monsterRat");

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
        movelr = body.getPosition().x/GameScreen.M_PER_PIXEL;
        if(movelr >= 430 && movelr <= 440){
            checkmove = true;
        }else if(movelr >= 580 && movelr <= 590){
            checkmove = false;
        }
        if(checkmove == false){
            state = State.WALKL;
            checklr = true;
            Walk(checklr);
            movelr = body.getPosition().x/GameScreen.M_PER_PIXEL;
        }else if(checkmove == true){
            state = State.WALKR;
            checklr = false;
            Walk(checklr);
            movelr = body.getPosition().x/GameScreen.M_PER_PIXEL;
        }
        e = e + delta;
        if (e > 150) {
            switch (state) {
                case WALKL: offset = 0; break;
                case WALKR: offset = 6; break;
                case DIEL:  offset = 12; break;
                case DIER:  offset = 21; break;
            }

            if(state == State.WALKL || state == State.WALKR){
                spriteIndex = offset + ((spriteIndex + 1) % 6);
                sprite.setSprite(spriteIndex);
                e = 0;
            }else if(state == State.DIEL|| state == State.DIER) {
                spriteIndex = offset + ((spriteIndex + 1) % 9);
                sprite.setSprite(spriteIndex);
                e = 0;
            /*spriteIndex = offset + ((spriteIndex + 1) % 5);
            sprite.setSprite(spriteIndex);
            e = 0;*/
            }
        }
    }

    public void paint(Clock clock) {
        if (!hasLoaded) return;
        //sprite.layer().setRotation(body.getAngle());
        sprite.layer().setTranslation(
                (body.getPosition().x / GameScreen.M_PER_PIXEL) - 10,
                body.getPosition().y / GameScreen.M_PER_PIXEL);
    }
    private boolean checklr;
    public void Walk(Boolean checklr){
        if(checklr == true){
            body.applyForce(new Vec2(-3f, 0f), body.getPosition());
        }else{
            body.applyForce(new Vec2(3f, 0f), body.getPosition());
        }
    }
}
