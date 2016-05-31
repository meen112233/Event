package game01.core;

import static playn.core.PlayN.*;

import playn.core.*;
import tripleplay.game.Screen;
import tripleplay.game.ScreenStack;
import playn.core.Mouse;

public class HomeScreen extends Screen {

    private ScreenStack ss;
    private final LevelSelectScreen levelselectScreen;
    private final CreditScreen creditScreen;
    private final ImageLayer homeBg;
    private final ImageLayer startButton;
    private final ImageLayer creditButton;

    public HomeScreen(final ScreenStack ss) {
        this.ss = ss;
        this.levelselectScreen = new LevelSelectScreen(ss);
        this.creditScreen = new CreditScreen(ss);

        Image homeBgImage = assets().getImage("images/homeScreen.png");
        this.homeBg = graphics().createImageLayer(homeBgImage);

        Image startButtonImage = assets().getImage("images/startButton.png");
        this.startButton = graphics().createImageLayer(startButtonImage);
        startButton.setTranslation(240f,180f);

        Image creditButtonImage = assets().getImage("images/credit.png");
        this.creditButton = graphics().createImageLayer(creditButtonImage);
        creditButton.setTranslation(240f, 290f);
    }

    @Override
    public  void  wasShown() {
        super.wasShown();
        this.layer.add(homeBg);
        this.layer.add(startButton);
        this.layer.add(creditButton);
        startButton.addListener(new Mouse.LayerAdapter() {
            @Override
            public void onMouseUp(Mouse.ButtonEvent event) {
                ss.remove(ss.top());
                ss.push(levelselectScreen);
            }
        });
        creditButton.addListener(new Mouse.LayerAdapter(){
            @Override
            public void onMouseUp(Mouse.ButtonEvent event) {
                ss.remove(ss.top());
                ss.push(creditScreen);
            }
        });
    }
}
