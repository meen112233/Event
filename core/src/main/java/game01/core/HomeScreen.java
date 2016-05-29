package game01.core;

import static playn.core.PlayN.*;

import playn.core.*;
import tripleplay.game.Screen;
import tripleplay.game.ScreenStack;
import playn.core.Mouse;

public class HomeScreen extends Screen {

    private ScreenStack ss;
    private final LevelSelectScreen levelselectScreen;
    private final ImageLayer homeBg;
    private final ImageLayer startButton;

    public HomeScreen(final ScreenStack ss) {
        this.ss = ss;
        this.levelselectScreen = new LevelSelectScreen(ss);

        Image homeBgImage = assets().getImage("images/homeScreen.png");
        this.homeBg = graphics().createImageLayer(homeBgImage);

        Image startButtonImage = assets().getImage("images/startButton.png");
        this.startButton = graphics().createImageLayer(startButtonImage);
        startButton.setTranslation(240,250);
    }

    @Override
    public  void  wasShown() {
        super.wasShown();
        this.layer.add(homeBg);
        this.layer.add(startButton);
        startButton.addListener(new Mouse.LayerAdapter() {
            @Override
            public void onMouseUp(Mouse.ButtonEvent event) {
                ss.push(levelselectScreen);
            }
        });
        keyboard().setListener(new Keyboard.Adapter() {
            @Override
            public void onKeyUp(Keyboard.Event event) {
                if (event.key() == Key.ENTER) {
                    ss.push(levelselectScreen);
                }
            }
        });
    }
}
