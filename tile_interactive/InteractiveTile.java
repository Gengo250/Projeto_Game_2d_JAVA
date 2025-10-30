package tile_interactive;

import java.awt.Graphics2D;

import entity.Entity;
import main.GamePanel;

public class InteractiveTile extends Entity{
  GamePanel gp;
  public boolean destructible = false;

  public InteractiveTile(GamePanel gp, int col, int row){
    super(gp);
    this.gp = gp;
  }
  public boolean isCorrectItem(Entity entity){
    boolean isCorrectItem = false;
    return isCorrectItem;
  }

  public void playSE(){}

  public InteractiveTile getDestroyedForm(){
    InteractiveTile tile = null;
    return tile;
  }
  public void update(){
    if(invencible == true){
      invencibleCounter++;
      if(invencibleCounter > 20){
        invencible = false;
        invencibleCounter = 0;
      }
    }
  }
  public void draw(Graphics2D g2){
    int sreenX = worldX - gp.player.worldX + gp.player.sreenX;
    int screenY = worldY - gp.player.worldY + gp.player.screenY;

      if(worldX + gp.tileSize > gp.player.worldX - gp.player.sreenX && 
         worldX - gp.tileSize < gp.player.worldX + gp.player.sreenX && 
         worldY + gp.tileSize > gp.player.worldY - gp.player.screenY && 
        worldY - gp.tileSize < gp.player.worldY + gp.player.screenY){
        g2.drawImage(down1, sreenX, screenY, null);
      }  
   }
}
