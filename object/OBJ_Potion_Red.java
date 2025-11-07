package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Potion_Red extends Entity{
  GamePanel gp;
  public static final String objName = "Life Potion";
  
   public OBJ_Potion_Red(GamePanel gp){
    super(gp);
    this.gp = gp;

    type = type_consumable;
    name = objName;
    value = 5;
    down1 = setup("/res/objects/cabaca", gp.tileSize, gp.tileSize);
    description = "[Life Posion]\nHeals your life by " + value + ".";
    price = 35;
    stackble = true;

    setDialogue();
   }
   public void setDialogue(){
      dialogues[0][0] = "You drink the " + name + "!\n" + "Your life has been recovered by " + value + ".";
   }
   public boolean use(Entity entity){
      startDialogue(this, 0);
       entity.life += value;
    if(gp.player.life > gp.player.maxLife){
       gp.player.life = gp.player.maxLife;
    }
     gp.playeSE(2);
     return true;
  
   }
}
