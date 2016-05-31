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

public class Digit2 {
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

    public Digit2(final float x_px, final float y_px){
        sprite = SpriteLoader.getSprite("images/digit2.json");
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
        if(GameScreen.numberdigit2 == 0){
            state = State.NUMBER0;
        }else if (GameScreen.numberdigit2 == 1){
            state = State.NUMBER1;
        }else if (GameScreen.numberdigit2 == 2){
            state = State.NUMBER2;
        }else if (GameScreen.numberdigit2 == 3){
            state = State.NUMBER3;
        }else if (GameScreen.numberdigit2 == 4){
            state = State.NUMBER4;
        }else if (GameScreen.numberdigit2 == 5){
            state = State.NUMBER5;
        }else if (GameScreen.numberdigit2 == 6){
            state = State.NUMBER6;
        }else if (GameScreen.numberdigit2 == 7){
            state = State.NUMBER7;
        }else if (GameScreen.numberdigit2 == 8){
            state = State.NUMBER8;
        }else if (GameScreen.numberdigit2 == 9){
            state = State.NUMBER9;
        }
        e += delta;

        if(e > 150){
            switch (state) {
                case NUMBER0: offset = 0; break;
                case NUMBER1: offset = 1; break;
                case NUMBER2: offset = 2; break;
                case NUMBER3: offset = 3; break;
                case NUMBER4: offset = 4; break;
                case NUMBER5: offset = 5; break;
                case NUMBER6: offset = 6;break;
                case NUMBER7: offset = 7;break;
                case NUMBER8: offset = 8;break;
                case NUMBER9: offset = 9;break;
            }
            spriteIndex = offset + ((spriteIndex + 1) % 1);
            sprite.setSprite(spriteIndex);
            e = 0;
        }
    }
}
