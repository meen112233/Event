package game01.core;

import static playn.core.PlayN.*;

import character.Zealot;
import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.Mouse;
import tripleplay.game.Screen;
import tripleplay.game.ScreenStack;

public class TestScreen extends Screen {

    private final ScreenStack ss;
    private final ImageLayer bg;
    private Zealot z;
    //private final ImageLayer backButton;


    public TestScreen(final ScreenStack ss) {
        this.ss = ss;

        Image bgImage = assets().getImage("images/bg.png");
        this.bg = graphics().createImageLayer(bgImage);

       /* Image backImage = assets().getImage("images/back.png");
        this.backButton = graphics().createImageLayer(backImage);
        backButton.setTranslation(10,10);
        backButton.addListener(new Mouse.LayerAdapter() {
            @Override
            public void onMouseUp(Mouse.ButtonEvent event) {
                ss.remove(ss.top());
            }
        });*/
        z = new Zealot(560f, 400f);
    }

    @Override
    public  void  wasShown() {
        super.wasShown();
        this.layer.add(bg);
        //this.layer.add(backButton);
        this.layer.add(z.layer());
    }
    @Override
    public void update(int delta) {
        super.update(delta);
        z.update(delta);
    }
}