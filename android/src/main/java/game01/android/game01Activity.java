package game01.android;

import playn.android.GameActivity;
import playn.core.PlayN;

import game01.core.game01;

public class game01Activity extends GameActivity {

  @Override
  public void main(){
    PlayN.run(new game01());
  }
}
