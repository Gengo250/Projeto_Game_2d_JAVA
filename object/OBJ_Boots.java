package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Boots extends Entity{
  GamePanel gp;
  public static final String objName = "Boots";
   public OBJ_Boots(GamePanel gp){
    super(gp);
    name = objName;
    down1 = setup("/res/objects/boots", gp.tileSize, gp.tileSize);
    speed = 1;
    description = "Just a pair of boots.";
    price = 50;
    

  }
}
