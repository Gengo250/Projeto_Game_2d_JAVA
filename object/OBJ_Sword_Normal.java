package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Sword_Normal extends Entity{
  public static final String objName = "Lança Tribal";
  public OBJ_Sword_Normal(GamePanel gp){
    super(gp);
    
    type = type_sword;
    name = objName;
    down1 = setup("/res/objects/lanca", gp.tileSize, gp.tileSize);
    attackValue = 1;
    attackArea.width = 40;
    attackArea.height = 40;
    description = "[" + name + "]\nAn old sword";
    price = 45;
    knokBackPower = 1;
    motion1_duration = 5;
    motion2_duration = 25;
   
  }
}
