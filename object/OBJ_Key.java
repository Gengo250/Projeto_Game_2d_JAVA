package object;

import entity.Entity;
import main.GamePanel;


public class OBJ_Key extends Entity{
  GamePanel gp;
  public OBJ_Key( GamePanel gp){

    super(gp);
    this.gp = gp;

    name = "Key";
    type = type_consumable;
    down1 = setup("/res/objects/chave", gp.tileSize, gp.tileSize);
    description = "[" + name + "]\nIt opens a door.";
    price = 100;
    stackble = true;
  }
  public boolean use(Entity entity){
    gp.gameState = gp.dialogueState;
    int objIndex = getDetected(entity, gp.obj, "Door");
    if(objIndex != 999){
      gp.ui.currentDialogue = "You use the " + name + " and open the door";
      gp.playeSE(3);
      gp.obj[gp.currentMap][objIndex] = null;
      return true;
    }
    else {
      gp.ui.currentDialogue = "NO NO NO!!!";
       return false;
    }
   
  }
  
}
