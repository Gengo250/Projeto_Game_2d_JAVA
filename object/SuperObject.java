package object;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;


import main.GamePanel;
import main.UtilityTool;

public class SuperObject {
  public BufferedImage image;
  public String name;
  public boolean collision = false;
  public int worldX, worldY;
  public Rectangle solidArea = new Rectangle(0,0,48,48);
  public int solidAreaDefaultX = 0;
  public int solidAreaDefaultY = 0;
  UtilityTool uTool = new UtilityTool();

  public void draw(Graphics2D g2, GamePanel gp){

    int screenX = worldX - gp.player.worldX + gp.player.sreenX;
    int screenY = worldY - gp.player.worldY + gp.player.screenY;

    if(worldX + gp.tileSize > gp.player.worldX - gp.player.sreenX && 
       worldX - gp.tileSize < gp.player.worldX + gp.player.sreenX && 
       worldY + gp.tileSize > gp.player.worldY - gp.player.screenY && 
       worldY - gp.tileSize < gp.player.worldY + gp.player.screenY){

      g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
    }
  }
}
