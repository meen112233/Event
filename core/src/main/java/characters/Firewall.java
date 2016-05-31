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

public class Firewall {
    private Sprite sprite;
    private int spriteIndex = 0;
    private boolean hasLoaded = false;
    private Body body;
    private World world;
    private List<NakiEffect> effectList;
    public static GameScreen game = new GameScreen();
    public static float movelr;
    public static boolean checkmove = true;
    public enum State {
        IDEL
    };

    private State state = State.IDEL;

    private  int e = 0;
    private  int offset = 0;

    public Firewall(final World world, final float x_px, final float y_px) {
        this.world = world;
        sprite = SpriteLoader.getSprite("images/firewall.json");
        sprite.addCallback(new Callback<Sprite>() {
            @Override
            public void onSuccess(Sprite result) {
                sprite.setSprite(spriteIndex);
                sprite.layer().setOrigin(80f / 2f - 10f, 100f / 2f);
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
        bodyDef.type = BodyType.STATIC;
        bodyDef.position = new Vec2(0,0);
        Body body = world.createBody(bodyDef);

        GameScreen.bodies.put(body, "firewall");

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(30 * GameScreen.M_PER_PIXEL / 2, 100 * GameScreen.M_PER_PIXEL / 2);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.7f;
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
        e += delta;
        if(e > 200){
            spriteIndex = offset + ((spriteIndex + 1) % 4);
            sprite.setSprite(spriteIndex);
            e = 0;
        }
    }

    public void paint(Clock clock) {
        if (!hasLoaded) return;
        sprite.layer().setTranslation(
                (body.getPosition().x / GameScreen.M_PER_PIXEL) - 10,
                body.getPosition().y / GameScreen.M_PER_PIXEL);
    }
}
