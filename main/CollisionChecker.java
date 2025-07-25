package main;

import entity.Entity;

public class CollisionChecker {
  GamePanel gp;

  public CollisionChecker(GamePanel gp){
    this.gp = gp;
  }

  public void checkTile(Entity entity){
    int entityLeftWorldX = entity.worldX + entity.solidArea.x;
    int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
    int entityTopWorldY = entity.worldY + entity.solidArea.y;
    int entityBotttomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;

    int entityLeftCol = entityLeftWorldX/gp.tileSize;
    int entityRightCol = entityRightWorldX/gp.tileSize;
    int entityTopRow = entityTopWorldY/gp.tileSize;
    int entityBottomRow = entityBotttomWorldY/gp.tileSize;

    int tileNum1, tileNum2;

    switch(entity.direction){
      case "up":

       entityTopRow = (entityTopWorldY - entity.speed)/gp.tileSize;
       tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
       tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
       if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true){
          entity.collisionOn = true;
        }
      break;
      case "down":
      
      entityBottomRow = (entityBotttomWorldY + entity.speed)/gp.tileSize;
      tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
      tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
      if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true){
        entity.collisionOn = true;
      }

      break;
      case "left":
      
      entityLeftCol = (entityLeftWorldX - entity.speed)/gp.tileSize;
      tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
      tileNum2 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
      if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true){
        entity.collisionOn = true;
      }
      break;
      case "right":
      
      entityRightCol = (entityRightWorldX + entity.speed) / gp.tileSize;
      tileNum1 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
      tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow]; 
      if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
        entity.collisionOn = true;
    }
      break;
    }
  }
  public int checkObject(Entity entity, boolean player) {
    int index = 999;                          // 999 = não colidiu

    for (int i = 0; i < gp.obj.length; i++) {
        if (gp.obj[i] != null) {

            /* ---------- prepara áreas sólidas absolutas ---------- */
            // player (entity)
            entity.solidArea.x = entity.worldX + entity.solidAreaDefaultX;
            entity.solidArea.y = entity.worldY + entity.solidAreaDefaultY;

            // objeto
            gp.obj[i].solidArea.x = gp.obj[i].worldX + gp.obj[i].solidAreaDefaultX;
            gp.obj[i].solidArea.y = gp.obj[i].worldY + gp.obj[i].solidAreaDefaultY;

            /* ---------- simula o próximo passo do player ---------- */
            switch (entity.direction) {
                case "up":
                 entity.solidArea.y -= entity.speed;
                 if(entity.solidArea.intersects(gp.obj[i].solidArea)){
                  if(gp.obj[i].collision == true){
                    entity.collisionOn = true;
                  }
                  if(player == true){
                    index = i;
                  }
                 }
                case "down":
                 entity.solidArea.y += entity.speed;
                 if(entity.solidArea.intersects(gp.obj[i].solidArea)){
                  if(gp.obj[i].collision == true){
                    entity.collisionOn = true;
                  }
                  if(player == true){
                    index = i;
                  }
                 }
                case "left":
                 entity.solidArea.x -= entity.speed;
                 if(entity.solidArea.intersects(gp.obj[i].solidArea)){
                  if(gp.obj[i].collision == true){
                    entity.collisionOn = true;
                  }
                  if(player == true){
                    index = i;
                  }
                 }
                case "right":
                 entity.solidArea.x += entity.speed;
                 if(entity.solidArea.intersects(gp.obj[i].solidArea)){
                  if(gp.obj[i].collision == true){
                    entity.collisionOn = true;
                  }
                  if(player == true){
                    index = i;
                  }
                 }
            }
            /* ---------- restaura áreas sólidas ---------- */
            entity.solidArea.x  = entity.solidAreaDefaultX;
            entity.solidArea.y  = entity.solidAreaDefaultY;
            gp.obj[i].solidArea.x = gp.obj[i].solidAreaDefaultX;
            gp.obj[i].solidArea.y = gp.obj[i].solidAreaDefaultY;
        }
    }
    return index;
}
}
