package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Door_Iron extends Entity{
   public static final String objName = "Door Iron";
   GamePanel gp;
  public OBJ_Door_Iron(GamePanel gp){
    super(gp);

    this.gp = gp;
    type = type_obstacle;
    name = objName;
    down1 = setup("/res/objects/door_iron", gp.tileSize, gp.tileSize);
    collision = true;

    solidArea.x = 0;
    solidArea.y = 16;
    solidArea.width = 48;
    solidArea.height = 32;
    solidAreaDefaultX = solidArea.x;
    solidAreaDefaultY = solidArea.y;

    setDialogue();
  }
  public void setDialogue(){
    dialogues[0][0] = "It's won't budge";
  }
  public void interact(){
    startDialogue(this, 0);
  }
}
