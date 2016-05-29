package characters;

import game01.core.GameScreen;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;
import playn.core.Layer;
import playn.core.PlayN;
import playn.core.util.Callback;
import playn.core.util.Clock;
import sprite.Sprite;
import sprite.SpriteLoader;

public class Warp {
    private Sprite sprite;
    private int spriteIndex = 0;
    private boolean hasLoaded = false;
    private Body body;

    public enum State {
        IDLE
    };

    private State state = State.IDLE;

    private  int e = 0;
    private  int offset = 0;

    public Warp(final float x_px, final float y_px){
        sprite = SpriteLoader.getSprite("images/warp.json");
        sprite.addCallback(new Callback<Sprite>() {
            @Override
            public void onSuccess(Sprite result) {
                sprite.setSprite(spriteIndex);
                sprite.layer().setOrigin(sprite.width() / 2f, sprite.height() / 2f);
                sprite.layer().setTranslation(x_px, y_px + 13f);
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

    public  void update(int delta) {
        if (hasLoaded == false) return;
        e += delta;

        if(e > 150){
            spriteIndex = offset + ((spriteIndex + 1) % 8);
            sprite.setSprite(spriteIndex);
            e = 0;
        }
    }
}
