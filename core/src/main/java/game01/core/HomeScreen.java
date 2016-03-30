package game01.core;

import static playn.core.PlayN.*;
import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.Mouse;
import tripleplay.game.Screen;
import tripleplay.game.ScreenStack;
import playn.core.Mouse;
import tripleplay.ui.*;
import  tripleplay.ui.layout.*;

public class HomeScreen extends Screen {

    private ScreenStack ss;
    private final TestScreen testScreen;
    private final ImageLayer homeBg;
    private final ImageLayer startButton;

    public HomeScreen(final ScreenStack ss) {
        this.ss = ss;
        this.testScreen = new TestScreen(ss);
        Image homeBgImage = assets().getImage("images/homebg.jpg");
        this.homeBg = graphics().createImageLayer(homeBgImage);

        Image startButtonImage = assets().getImage("images/start.png");
        this.startButton = graphics().createImageLayer(startButtonImage);
        startButton.setTranslation(250,375);
        startButton.addListener(new Mouse.LayerAdapter() {
            @Override
            public void onMouseUp(Mouse.ButtonEvent event) {
                ss.push(testScreen);
            }
        });
    }

    @Override
    public  void  wasShown() {
        super.wasShown();
        this.layer.add(homeBg);
        this.layer.add(startButton);
    }
}
