package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Axe extends Entity{
  public static final String objName = "Facao de Selva";
    public OBJ_Axe(GamePanel gp){
      super(gp);

      type = type_axe;
      name = objName;
      down1 = setup("/res/objects/facao", gp.tileSize, gp.tileSize);
      attackValue = 2;
      attackArea.width = 30;
      attackArea.height = 30;
      description = "[Facao de Selva]\nA bit rusty but still \ncan cut some bush's";
      price = 75;
      knokBackPower = 3;
      motion1_duration = 10;
      motion2_duration = 30;
    }
}
