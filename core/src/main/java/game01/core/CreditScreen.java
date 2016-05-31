package game01.core;

import playn.core.*;
import tripleplay.game.Screen;
import tripleplay.game.ScreenStack;

import static playn.core.PlayN.*;

public class CreditScreen extends Screen {

    private ScreenStack ss;
    private final ImageLayer bg;
    private final ImageLayer backButton;

    public CreditScreen(final ScreenStack ss) {
        this.ss = ss;

        Image bgImage = assets().getImage("images/creditscreen.png");
        this.bg = graphics().createImageLayer(bgImage);

        Image backButtonImage = assets().getImage("images/backButton.png");
        this.backButton = graphics().createImageLayer(backButtonImage);
        backButton.setTranslation(10f,10f);
    }

    @Override
    public  void  wasShown() {
        super.wasShown();
        this.layer.add(bg);
        this.layer.add(backButton);
        backButton.addListener(new Mouse.LayerAdapter(){
            @Override
            public void onMouseUp(Mouse.ButtonEvent event) {
                ss.remove(ss.top());
                ss.push(new HomeScreen(ss));
            }
        });
    }
}
