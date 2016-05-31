package game01.core;

import static playn.core.PlayN.*;

import playn.core.*;
import tripleplay.game.Screen;
import tripleplay.game.ScreenStack;
import playn.core.Mouse;

public class LevelSelectScreen extends Screen{
    private ScreenStack ss;
    private final GameScreen gameScreen;
    private final ImageLayer bg;
    private final ImageLayer levelButton1;
    private final ImageLayer levelButton2;
    private final ImageLayer homeButton;
    private final ImageLayer upgradeButton;

    public static int maxhp = 2;
    public static int myhp = maxhp;
    public static int mydamage = 1;
    public static int mygold = 0;
    public static int heartupgrade = 1;
    public static int powerupgrade = 1;

    public LevelSelectScreen(final ScreenStack ss) {
        this.ss = ss;
        this.gameScreen = new GameScreen(ss);

        Image bgImage = assets().getImage("images/levelselectscreen.png");
        this.bg = graphics().createImageLayer(bgImage);

        Image levelButton1Image = assets().getImage("images/lvbutton.png");
        this.levelButton1 = graphics().createImageLayer(levelButton1Image);
        levelButton1.setTranslation(115f,110f);
        levelButton1.addListener(new Mouse.LayerAdapter() {
            @Override
            public void onMouseUp(Mouse.ButtonEvent event) {
                if(gameScreen.clear == true){
                    ss.remove(ss.top());
                    ss.push(new GameScreen(ss));
                }
                myhp = maxhp;
                ss.remove(ss.top());
                ss.push(new GameScreen(ss));
            }
        });

        Image levelButton2Image = assets().getImage("images/lvbutton.png");
        this.levelButton2 = graphics().createImageLayer(levelButton2Image);
        levelButton2.setTranslation(270f,110f);
        levelButton2.addListener(new Mouse.LayerAdapter() {
            @Override
            public void onMouseUp(Mouse.ButtonEvent event) {
                if(gameScreen.clear == true) {
                    ss.remove(ss.top());
                    ss.push(new GameScreen2(ss));
                }
            }
        });

        Image homeButtonImage = assets().getImage("images/lvbutton2.png");
        this.homeButton = graphics().createImageLayer(homeButtonImage);
        homeButton.setTranslation(240f,335f);
        homeButton.addListener(new Mouse.LayerAdapter() {
            @Override
            public void onMouseUp(Mouse.ButtonEvent event) {
                myhp = 2;
                maxhp = 2;
                mydamage = 1;
                mygold = 0;
                heartupgrade = 1;
                powerupgrade = 1;
                gameScreen.clear = false;
                ss.remove(ss.top());
                ss.push(new HomeScreen(ss));
            }
        });

        Image upgradeButtonImage = assets().getImage("images/lvbutton2.png");
        this.upgradeButton = graphics().createImageLayer(upgradeButtonImage);
        upgradeButton.setTranslation(330f,335f);
        upgradeButton.addListener(new Mouse.LayerAdapter() {
            @Override
            public void onMouseUp(Mouse.ButtonEvent event) {
                ss.remove(ss.top());
                ss.push(new UpgradeScreen(ss));
            }
        });
    }

    @Override
    public  void  wasShown() {
        super.wasShown();
        this.layer.add(bg);
        this.layer.add(levelButton1);
        this.layer.add(levelButton2);
        this.layer.add(homeButton);
        this.layer.add(upgradeButton);
    }
}
