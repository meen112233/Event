package Coin;

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

public class Digit3 {
    private Sprite sprite;
    private int spriteIndex = 0;
    private boolean hasLoaded = false;
    private Body body;

    public enum State {
        NUMBER0, NUMBER1, NUMBER2, NUMBER3, NUMBER4, NUMBER5, NUMBER6, NUMBER7, NUMBER8, NUMBER9,
    };

    private State state = State.NUMBER0;

    private  int e = 0;
    private  int offset = 0;

    public Digit3(final float x_px, final float y_px){
        sprite = SpriteLoader.getSprite("images/digit3.json");
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
        if(GameScreen.numberdigit3 == 0){
            state = State.NUMBER0;
        }else if (GameScreen.numberdigit3 == 1){
            state = State.NUMBER1;
        }else if (GameScreen.numberdigit3 == 2){
            state = State.NUMBER2;
        }else if (GameScreen.numberdigit3 == 3){
            state = State.NUMBER3;
        }else if (GameScreen.numberdigit3 == 4){
            state = State.NUMBER4;
        }else if (GameScreen.numberdigit3 == 5){
            state = State.NUMBER5;
        }else if (GameScreen.numberdigit3 == 6){
            state = State.NUMBER6;
        }else if (GameScreen.numberdigit3 == 7){
            state = State.NUMBER7;
        }else if (GameScreen.numberdigit3 == 8){
            state = State.NUMBER8;
        }else if (GameScreen.numberdigit3 == 9){
            state = State.NUMBER9;
        }
    }
}
