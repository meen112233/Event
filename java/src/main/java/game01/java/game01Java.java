package game01.java;

import playn.core.PlayN;
import playn.java.JavaPlatform;

import game01.core.MyGame;

public class game01Java {

  public static void main(String[] args) {
    JavaPlatform.Config config = new JavaPlatform.Config();
    // use config to customize the Java platform, if needed
    JavaPlatform.register(config);
    PlayN.run(new MyGame());
  }
}
