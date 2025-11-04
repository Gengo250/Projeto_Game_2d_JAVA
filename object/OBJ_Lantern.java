package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Lantern extends Entity{
  public OBJ_Lantern(GamePanel gp){
    super(gp);

    type = type_light;
    name = "Lantern";
    down1 = setup("/res/objects/lantern", gp.tileSize, gp.tileSize);
    description = "[Lantern]\nIlluminnates your \nsurroundings";
    price = 70;
    lightRadius = 120;
    
  }
}
