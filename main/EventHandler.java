package main;

import data.Progress;
import entity.Entity;

public class EventHandler {

  GamePanel gp;
  EventRect eventRect[][][];
  Entity eventMaster;

  int previusEventX, previusEventY;
  boolean canTouchEvent = true;
  int tempMap, tempCol, tempRow;

  public EventHandler(GamePanel gp) {
    this.gp = gp;
    eventMaster = new Entity(gp);

    eventRect = new EventRect[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];
    int map = 0;
    int col = 0;
    int row = 0;
    while (map < gp.maxMap && col < gp.maxWorldCol && row < gp.maxWorldRow) {

      eventRect[map][col][row] = new EventRect();
      eventRect[map][col][row].x = 23;
      eventRect[map][col][row].y = 23;
      eventRect[map][col][row].width = 2;
      eventRect[map][col][row].height = 2;
      eventRect[map][col][row].eventRectDefautX = eventRect[map][col][row].x;
      eventRect[map][col][row].eventRectDefautY = eventRect[map][col][row].y;

      col++;
      if (col == gp.maxWorldCol) {
        col = 0;
        row++;

        if (row == gp.maxWorldRow) {
          row = 0;
          map++;
        }
      }
    }
    setDialogue();
  }
  public void setDialogue(){
    eventMaster.dialogues[0][0] = "You fall into a pit!";
    eventMaster.dialogues[1][0] = "You drink the water. \nYour life and mana has been recovered.\n"
          + "(The progress has been saved)";
  }
  public void checkEvent() {

    // Check if the player character is more than 1 tile away from the last event
    int xDistance = Math.abs(gp.player.worldX - previusEventX);
    int yDistance = Math.abs(gp.player.worldY - previusEventY);
    int distance = Math.max(xDistance, yDistance);
    if (distance > gp.tileSize) {
      canTouchEvent = true;
    }
    // 23,66 -> escada labirinto 
    if (canTouchEvent == true) {
      // if(hit(27, 16, "right") == true){damagePit(gp.dialogueState);}
      if (hit(0, 23, 12, "up") == true) {healingPool(gp.dialogueState);} 
        else if (hit(0, 24, 44, "any") == true) {teleport(1, 21, 27,gp.indoor);} // to the merchant's house
        else if (hit(1, 21, 27, "any") == true) {teleport(0, 24, 44,gp.outside);} //to outside

        else if (hit(1, 21, 23, "up") == true) {speak(gp.npc[1][0]);} //mercador falando na mesa 

        else if (hit(0, 19, 26, "any") == true){teleport(2, 68, 74, gp.dungeon);} //labirinto 
        else if (hit(2, 68, 74, "any") == true){teleport(0, 19, 26, gp.outside);} //outside

        else if (hit(2, 23, 66, "any") == true){teleport(0, 86, 18, gp.outside);} //Área secreta
        else if (hit(0, 86, 19, "any") == true){teleport(2, 23, 66, gp.outside);} //volta para o labirinto

        else if (hit(2, 8, 7, "any") == true){teleport(3, 26, 41, gp.dungeon);}//B2
        else if (hit(3, 26, 41, "any") == true){teleport(2, 8, 7, gp.dungeon);}//B1
        else if (hit(3, 25, 27, "any") == true){skeletonLord();}//BOSS
       
    }

  }

  public boolean hit(int map, int col, int row, String reqDirection) {
    boolean hit = false;

    if (map == gp.currentMap) {
      gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
      gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
      eventRect[map][col][row].x = col * gp.tileSize + eventRect[map][col][row].x;
      eventRect[map][col][row].y = row * gp.tileSize + eventRect[map][col][row].y;

      if (gp.player.solidArea.intersects(eventRect[map][col][row]) && eventRect[map][col][row].eventDone == false) {
        if (gp.player.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any")) {
          hit = true;

          previusEventX = gp.player.worldX;
          previusEventY = gp.player.worldY;
        }
      }
      gp.player.solidArea.x = gp.player.solidAreaDefaultX;
      gp.player.solidArea.y = gp.player.solidAreaDefaultY;
      eventRect[map][col][row].x = eventRect[map][col][row].eventRectDefautX;
      eventRect[map][col][row].y = eventRect[map][col][row].eventRectDefautY;
    }

    return hit;
  }

  public void teleport(int map, int col, int row, int area) {
    gp.gameState = gp.transitionState;
    gp.nextArea = area;
    tempMap = map;
    tempCol = col;
    tempRow = row;
    canTouchEvent = false;
    gp.playeSE(13);

  }

  public void damagePit(int gameState) {
    gp.gameState = gameState;
    gp.playeSE(6);
    eventMaster.startDialogue(eventMaster, 0);
    gp.player.life -= 1;
    canTouchEvent = false;
  }

  public void healingPool(int gameState) {
    if (gp.keyH.enterPressed == true) {
      gp.gameState = gameState;
      gp.player.attackCanceled = true;
      gp.playeSE(2);
      eventMaster.startDialogue(eventMaster, 1);
      gp.player.life = gp.player.maxLife;
      gp.player.mana = gp.player.maxMana;
      gp.aSetter.setMonster();
      gp.saveLoad.save();

    }
  }

  public void speak(Entity entity) {
    if (gp.keyH.enterPressed == true) {
      gp.gameState = gp.dialogueState;
      gp.player.attackCanceled = true;
      entity.speak();
    }
  }
  public void skeletonLord(){
    if(gp.bossBattleON == false && Progress.skeletonLordDefeated == false){
      gp.gameState = gp.cutsceneState;
      gp.csManager.sceneNum = gp.csManager.skeletonLord;

    }
  }
}