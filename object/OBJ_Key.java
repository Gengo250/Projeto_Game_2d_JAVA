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

    setDialogue();
  }
  public void setDialogue(){
    dialogues[0][0] = "You use the " + name + " and open the door";

    dialogues[1][0] = "NO NO NO!!!";
  }
  public boolean use(Entity entity){
    int objIndex = getDetected(entity, gp.obj, "Door");
    if(objIndex != 999){
      startDialogue(this, 0);
      gp.playeSE(3);
      gp.obj[gp.currentMap][objIndex] = null;
      return true;
    }
    else {
      startDialogue(this, 1);
       return false;
    }
   
  }
  
}
