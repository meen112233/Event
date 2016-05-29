package game01.core;

import static playn.core.PlayN.*;

import playn.core.*;
import tripleplay.game.Screen;
import tripleplay.game.ScreenStack;
import playn.core.Mouse;

public class UpgradeScreen extends Screen {

    private ScreenStack ss;
    private final ImageLayer bg;
    private final ImageLayer backButton;
    private final ImageLayer dontbuyHeart;
    private final ImageLayer buyHeart;
    private final ImageLayer heart200;
    private final ImageLayer heart300;
    private final ImageLayer heart500;
    private final ImageLayer heart1000;
    private final ImageLayer heart1500;
    private final ImageLayer heartup0;
    private final ImageLayer heartup1;
    private final ImageLayer heartup2;
    private final ImageLayer heartup3;
    private final ImageLayer heartup4;
    private final ImageLayer heartup5;
    private final ImageLayer dontbuyPower;
    private final ImageLayer buyPower;
    private final ImageLayer power200;
    private final ImageLayer power300;
    private final ImageLayer power500;
    private final ImageLayer power1000;
    private final ImageLayer power1500;
    private final ImageLayer powerup0;
    private final ImageLayer powerup1;
    private final ImageLayer powerup2;
    private final ImageLayer powerup3;
    private final ImageLayer powerup4;
    private final ImageLayer powerup5;
    private final ImageLayer dontbuyHeal;
    private final ImageLayer buyHeal;
    private LevelSelectScreen lvscreen;
    private boolean checkbuy1 = false;
    private boolean checkbuy2 = false;
    private int gold = lvscreen.mygold;
    private int heartupgrade = lvscreen.heartupgrade;
    private int powerupgrade = lvscreen.powerupgrade;
    private int maxhp = lvscreen.maxhp;
    private int myhp = lvscreen.myhp;
    private int mydamage = lvscreen.mydamage;

    public UpgradeScreen(final ScreenStack ss) {
        this.ss = ss;

        Image bgImage = assets().getImage("images/upgradescreen.png");
        this.bg = graphics().createImageLayer(bgImage);

        Image backButtonImage = assets().getImage("images/upbutton.png");
        this.backButton = graphics().createImageLayer(backButtonImage);
        backButton.setTranslation(5f, 5f);
        backButton.addListener(new Mouse.LayerAdapter(){
            @Override
            public void onMouseUp(Mouse.ButtonEvent event) {
                lvscreen.mygold = gold;
                lvscreen.maxhp = maxhp;
                lvscreen.heartupgrade = heartupgrade;
                lvscreen.myhp = myhp;
                lvscreen.mydamage = mydamage;
                lvscreen.powerupgrade = powerupgrade;
                ss.remove(ss.top());
                ss.push(new LevelSelectScreen(ss));
            }
        });

        Image heart200Image = assets().getImage("images/200.png");
        this.heart200 = graphics().createImageLayer(heart200Image);
        heart200.setOrigin(420f/2f, 115f/2f);
        heart200.setTranslation(320f, 150f);

        Image heart300Image = assets().getImage("images/300.png");
        this.heart300 = graphics().createImageLayer(heart300Image);
        heart300.setOrigin(420f/2f, 115f/2f);
        heart300.setTranslation(320f, 150f);

        Image heart500Image = assets().getImage("images/500.png");
        this.heart500 = graphics().createImageLayer(heart500Image);
        heart500.setOrigin(420f/2f, 115f/2f);
        heart500.setTranslation(320f, 150f);

        Image heart1000Image = assets().getImage("images/1000.png");
        this.heart1000 = graphics().createImageLayer(heart1000Image);
        heart1000.setOrigin(420f/2f, 115f/2f);
        heart1000.setTranslation(320f, 150f);

        Image heart1500Image = assets().getImage("images/1500.png");
        this.heart1500 = graphics().createImageLayer(heart1500Image);
        heart1500.setOrigin(420f/2f, 115f/2f);
        heart1500.setTranslation(320f, 150f);

        Image heartup0Image = assets().getImage("images/up0.png");
        this.heartup0 = graphics().createImageLayer(heartup0Image);
        heartup0.setOrigin(420f/2f, 115f/2f);
        heartup0.setTranslation(320f, 150f);

        Image heartup1Image = assets().getImage("images/up1.png");
        this.heartup1 = graphics().createImageLayer(heartup1Image);
        heartup1.setOrigin(420f/2f, 115f/2f);
        heartup1.setTranslation(320f, 150f);

        Image heartup2Image = assets().getImage("images/up2.png");
        this.heartup2 = graphics().createImageLayer(heartup2Image);
        heartup2.setOrigin(420f/2f, 115f/2f);
        heartup2.setTranslation(320f, 150f);

        Image heartup3Image = assets().getImage("images/up3.png");
        this.heartup3 = graphics().createImageLayer(heartup3Image);
        heartup3.setOrigin(420f/2f, 115f/2f);
        heartup3.setTranslation(320f, 150f);

        Image heartup4Image = assets().getImage("images/up4.png");
        this.heartup4 = graphics().createImageLayer(heartup4Image);
        heartup4.setOrigin(420f/2f, 115f/2f);
        heartup4.setTranslation(320f, 150f);

        Image heartup5Image = assets().getImage("images/up5.png");
        this.heartup5 = graphics().createImageLayer(heartup5Image);
        heartup5.setOrigin(420f/2f, 115f/2f);
        heartup5.setTranslation(320f, 150f);

        Image dontbuyHeartImage = assets().getImage("images/dontbuy.png");
        this.dontbuyHeart = graphics().createImageLayer(dontbuyHeartImage);
        dontbuyHeart.setOrigin(420f/2f, 115f/2f);
        dontbuyHeart.setTranslation(320f, 155f);

        Image buyHeartImage = assets().getImage("images/buy.png");
        this.buyHeart = graphics().createImageLayer(buyHeartImage);
        buyHeart.setOrigin(420f/2f, 115f/2f);
        buyHeart.setTranslation(320f, 155f);
        buyHeart.addListener(new Mouse.LayerAdapter(){
            @Override
            public void onMouseUp(Mouse.ButtonEvent event) {
                if (checkbuy1 == true) {
                    if (heartupgrade == 1) {
                        if (gold >= 200) {
                            gold -= 200;
                            checkbuy1 = false;
                            checkbuy2 = false;
                            layer.remove(heart200);
                            layer.remove(heartup0);
                        }
                    } else if (heartupgrade == 2) {
                        if (gold >= 300) {
                            gold -= 300;
                            checkbuy1 = false;
                            checkbuy2 = false;
                            layer.remove(heart300);
                            layer.remove(heartup1);
                        }
                    } else if (heartupgrade == 3) {
                        if (gold >= 500) {
                            gold -= 500;
                            checkbuy1 = false;
                            checkbuy2 = false;
                            layer.remove(heart500);
                            layer.remove(heartup2);
                        }
                    } else if (heartupgrade == 4) {
                        if (gold >= 1000) {
                            gold -= 1000;
                            checkbuy1 = false;
                            checkbuy2 = false;
                            layer.remove(heart1000);
                            layer.remove(heartup3);
                        }
                    } else if (heartupgrade == 5) {
                        if (gold >= 1500) {
                            gold -= 1500;
                            checkbuy1 = false;
                            checkbuy2 = false;
                            layer.remove(heart1500);
                            layer.remove(heartup4);
                        }
                    }
                    heartupgrade++;
                    if (maxhp < 7) {
                        maxhp++;
                        myhp++;
                    }
                }
            }
        });

        Image power200Image = assets().getImage("images/200.png");
        this.power200 = graphics().createImageLayer(power200Image);
        power200.setOrigin(420f/2f, 115f/2f);
        power200.setTranslation(320f, 255f);

        Image power300Image = assets().getImage("images/300.png");
        this.power300 = graphics().createImageLayer(power300Image);
        power300.setOrigin(420f/2f, 115f/2f);
        power300.setTranslation(320f, 255f);

        Image power500Image = assets().getImage("images/500.png");
        this.power500 = graphics().createImageLayer(power500Image);
        power500.setOrigin(420f/2f, 115f/2f);
        power500.setTranslation(320f, 255f);

        Image power1000Image = assets().getImage("images/1000.png");
        this.power1000 = graphics().createImageLayer(power1000Image);
        power1000.setOrigin(420f/2f, 115f/2f);
        power1000.setTranslation(320f, 255f);

        Image power1500Image = assets().getImage("images/1500.png");
        this.power1500 = graphics().createImageLayer(power1500Image);
        power1500.setOrigin(420f/2f, 115f/2f);
        power1500.setTranslation(320f, 255f);

        Image powerup0Image = assets().getImage("images/up0.png");
        this.powerup0 = graphics().createImageLayer(powerup0Image);
        powerup0.setOrigin(420f/2f, 115f/2f);
        powerup0.setTranslation(320f, 255f);

        Image powerup1Image = assets().getImage("images/up1.png");
        this.powerup1 = graphics().createImageLayer(powerup1Image);
        powerup1.setOrigin(420f/2f, 115f/2f);
        powerup1.setTranslation(320f, 255f);

        Image powerup2Image = assets().getImage("images/up2.png");
        this.powerup2 = graphics().createImageLayer(powerup2Image);
        powerup2.setOrigin(420f/2f, 115f/2f);
        powerup2.setTranslation(320f, 255f);

        Image powerup3Image = assets().getImage("images/up3.png");
        this.powerup3 = graphics().createImageLayer(powerup3Image);
        powerup3.setOrigin(420f/2f, 115f/2f);
        powerup3.setTranslation(320f, 255f);

        Image powerup4Image = assets().getImage("images/up4.png");
        this.powerup4 = graphics().createImageLayer(powerup4Image);
        powerup4.setOrigin(420f/2f, 115f/2f);
        powerup4.setTranslation(320f, 255f);

        Image powerup5Image = assets().getImage("images/up5.png");
        this.powerup5 = graphics().createImageLayer(powerup5Image);
        powerup5.setOrigin(420f/2f, 115f/2f);
        powerup5.setTranslation(320f, 255f);

        Image dontbuyPowerImage = assets().getImage("images/dontbuy.png");
        this.dontbuyPower = graphics().createImageLayer(dontbuyPowerImage);
        dontbuyPower.setOrigin(420f/2f, 115f/2f);
        dontbuyPower.setTranslation(320f, 255f);

        Image buyPowerImage = assets().getImage("images/buy.png");
        this.buyPower = graphics().createImageLayer(buyPowerImage);
        buyPower.setOrigin(420f/2f, 115f/2f);
        buyPower.setTranslation(320f, 255f);
        buyPower.addListener(new Mouse.LayerAdapter(){
            @Override
            public void onMouseUp(Mouse.ButtonEvent event) {
                if (checkbuy1 == true) {
                    if (powerupgrade == 1) {
                        if (gold >= 200) {
                            gold -= 200;
                            checkbuy1 = false;
                            checkbuy2 = false;
                            layer.remove(power200);
                            layer.remove(powerup0);
                        }
                    } else if (powerupgrade == 2) {
                        if (gold >= 300) {
                            gold -= 300;
                            checkbuy1 = false;
                            checkbuy2 = false;
                            layer.remove(power300);
                            layer.remove(powerup1);
                        }
                    } else if (powerupgrade == 3) {
                        if (gold >= 500) {
                            gold -= 500;
                            checkbuy1 = false;
                            checkbuy2 = false;
                            layer.remove(power500);
                            layer.remove(powerup2);
                        }
                    } else if (powerupgrade == 4) {
                        if (gold >= 1000) {
                            gold -= 1000;
                            checkbuy1 = false;
                            checkbuy2 = false;
                            layer.remove(power1000);
                            layer.remove(powerup3);
                        }
                    } else if (powerupgrade == 5) {
                        if (gold >= 1500) {
                            gold -= 1500;
                            checkbuy1 = false;
                            checkbuy2 = false;
                            layer.remove(power1500);
                            layer.remove(powerup4);
                        }
                    }
                    powerupgrade++;
                    if (mydamage < 7) {
                        mydamage++;
                    }
                }
            }
        });
        Image dontbuyHealImage = assets().getImage("images/dontbuy.png");
        this.dontbuyHeal = graphics().createImageLayer(dontbuyHealImage);
        dontbuyHeal.setOrigin(420f/2f, 115f/2f);
        dontbuyHeal.setTranslation(320f, 355f);

        Image buyHealImage = assets().getImage("images/buy.png");
        this.buyHeal = graphics().createImageLayer(buyHealImage);
        buyHeal.setOrigin(420f/2f, 115f/2f);
        buyHeal.setTranslation(320f, 355f);
        buyHeal.addListener(new Mouse.LayerAdapter(){
            @Override
            public void onMouseUp(Mouse.ButtonEvent event) {
                if (checkbuy2 == true) {
                    gold -= 200;
                    myhp++;
                    checkbuy2 = false;
                }
            }
        });

    }

    @Override
    public  void  wasShown() {
        super.wasShown();
        this.layer.add(bg);
        this.layer.add(backButton);
    }

    @Override
    public void update(int delta) {
        super.update(delta);
        System.out.println(gold);
        if(heartupgrade == 1){
            this.layer.add(heart200);
            this.layer.add(heartup0);
            if(gold >= 200){
                checkbuy1 = true;
            }
        }else if(heartupgrade == 2){
            this.layer.add(heart300);
            this.layer.add(heartup1);
            if(gold >= 300){
                checkbuy1 = true;
            }
        }else if(heartupgrade == 3){
            this.layer.add(heart500);
            this.layer.add(heartup2);
            if(gold >= 500){
                checkbuy1 = true;
            }
        }else if(heartupgrade == 4){
            this.layer.add(heart1000);
            this.layer.add(heartup3);
            if(gold >= 1000){
                checkbuy1 = true;
            }
        }else if(heartupgrade == 5){
            this.layer.add(heart1500);
            this.layer.add(heartup4);
            if(gold >= 1500){
                checkbuy1 = true;
            }
        }else if(heartupgrade > 5){
            this.layer.add(heartup5);
        }

        if(powerupgrade == 1){
            this.layer.add(power200);
            this.layer.add(powerup0);
            if(gold >= 200){
                checkbuy1 = true;
            }
        }else if(powerupgrade == 2){
            this.layer.add(power300);
            this.layer.add(powerup1);
            if(gold >= 300){
                checkbuy1 = true;
            }
        }else if(powerupgrade == 3){
            this.layer.add(power500);
            this.layer.add(powerup2);
            if(gold >= 500){
                checkbuy1 = true;
            }
        }else if(powerupgrade == 4){
            this.layer.add(power1000);
            this.layer.add(powerup3);
            if(gold >= 1000){
                checkbuy1 = true;
            }
        }else if(powerupgrade == 5){
            this.layer.add(power1500);
            this.layer.add(powerup4);
            if(gold >= 1500){
                checkbuy1 = true;
            }
        }else if(powerupgrade > 5){
            this.layer.add(powerup5);
        }
        if(myhp != maxhp){
            if(gold >= 200) {
                checkbuy2 = true;
            }
        }

        if(checkbuy1 == true){
            this.layer.add(buyHeart);
            this.layer.add(buyPower);
        }else {
            this.layer.add(dontbuyHeart);
            this.layer.add(dontbuyPower);
        }
        if(checkbuy2 == true){
            this.layer.add(buyHeal);
        }else {
            this.layer.add(dontbuyHeal);
        }

    }
}

