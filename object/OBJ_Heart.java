package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Heart extends Entity{

  
  GamePanel gp;

  public OBJ_Heart( GamePanel gp){
    super(gp);
    this.gp = gp;
    type = type_pickupOnly;
    name = "Heart";
    value = 6;
    
   // opcional: sprite do item no chão (usa sua arte "cheia")
    down1  = setup("/res/objects/Vida-cheia", gp.tileSize, gp.tileSize);

    // estes campos podem continuar, mas não são mais usados pela UI:
    image  = setup("/res/objects/Vida-cheia", gp.tileSize, gp.tileSize);
    image2 = setup("/res/objects/Vida-2",     gp.tileSize, gp.tileSize);
    image3 = setup("/res/objects/Vida-3",     gp.tileSize, gp.tileSize);
    image4 = setup("/res/objects/Vida-4",     gp.tileSize, gp.tileSize);
    image5 = setup("/res/objects/Vida-5",     gp.tileSize, gp.tileSize);
    image6 = setup("/res/objects/Vida-6",     gp.tileSize, gp.tileSize);
  }
  public boolean use(Entity entity){
    gp.playeSE(2);
    gp.ui.addMessage("Life +" + value);
    entity.life += value;
    return true;
  }
}