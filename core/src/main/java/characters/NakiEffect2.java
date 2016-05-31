package characters;

import game01.core.GameScreen2;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;
import playn.core.Layer;
import playn.core.PlayN;
import playn.core.util.Callback;
import playn.core.util.Clock;
import sprite.Sprite;
import sprite.SpriteLoader;

public class NakiEffect2 {
    private Sprite sprite;
    private int spriteIndex = 0;
    private boolean hasLoaded = false;
    private Body body;
    private String direction;

    public enum State {
        EFFECT
    };

    private State state = State.EFFECT;

    private  int e = 0;
    private  int offset = 0;

    public NakiEffect2(final World world, final float x_px, final float y_px, final String direction) {
        this.direction = direction;
        sprite = SpriteLoader.getSprite("images/nakieffect.json");
        sprite.addCallback(new Callback<Sprite>() {
            @Override
            public void onSuccess(Sprite result) {
                sprite.setSprite(spriteIndex);
                sprite.layer().setOrigin(sprite.width() / 2f, sprite.height() / 2f);
                sprite.layer().setTranslation(x_px, y_px + 13f);

                body = initPhysicsBody(world,
                        GameScreen2.M_PER_PIXEL * x_px,
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
        bodyDef.position = new Vec2(x,y);
        Body body = world.createBody(bodyDef);

        GameScreen2.bodies.put(body, "nakiEffect2");

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(sprite.layer().width() * GameScreen2.M_PER_PIXEL / 2, sprite.layer().height()* GameScreen2.M_PER_PIXEL / 2);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.1f;
        fixtureDef.friction = 0.8f;
        fixtureDef.restitution = 0.35f;

        body.createFixture(fixtureDef);

        body.setLinearDamping(0.2f);
        body.setTransform(new Vec2(x,y), 0f);

        if(direction == "R1"){
            body.applyForce(new Vec2(100f, -10f), body.getPosition());
        }else if(direction == "L1"){
            body.applyForce(new Vec2(-100f, -10f), body.getPosition());
        }

        return body;

    }
    public Body getBody(){ return this.body; }

    public  void update(int delta) {
        if (hasLoaded == false) return;
        e += delta;
        if(e > 150) {
            spriteIndex = offset + ((spriteIndex + 1) % 4);
            sprite.setSprite(spriteIndex);
            sprite.layer().setTranslation(
                    body.getPosition().x / GameScreen2.M_PER_PIXEL,
                    body.getPosition().y / GameScreen2.M_PER_PIXEL);
            e = 0;
        }
    }

    public void paint(Clock clock) {
        if (!hasLoaded) return;
        //sprite.layer().setRotation(body.getAngle());
        sprite.layer().setTranslation(
                (body.getPosition().x / GameScreen2.M_PER_PIXEL),
                body.getPosition().y / GameScreen2.M_PER_PIXEL);
    }
    /*private boolean checkContact = false;
    public void CheckContact(Contact contact){
        checkContact = true;
        sprite.layer().setVisible(false);
    }*/
}
