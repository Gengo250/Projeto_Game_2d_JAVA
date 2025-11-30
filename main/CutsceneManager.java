package main;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;

import entity.PlayerDummy;
import monster.MON_Monkey;
import object.OBJ_BlueHeart;
import object.OBJ_Door_Iron;

public class CutsceneManager {
  GamePanel gp;
  Graphics2D g2;
  public int sceneNum;
  public int scenePhase;
  int counter = 0;
  float alpha = 0f;
  int y;
  String endCredit;

  //Scene Number
  public final int NA = 0;
  public final int macaco = 1;
  public final int ending = 2;

  public CutsceneManager(GamePanel gp){
    this.gp = gp;
      endCredit =
      "PROGRAM\n" +
      "Miguel Gengo\n\n" +
      "ART/MUSIC\n" +
      "Luccas Zibordi\n\n" +
      "MAPS/SLIDES\n" +
      "Guilherme Mascarete\n" +
      "\n\n\n\n\n\n\n\n\n" +
      "THANK YOU FOR PLAYING!";
  }
  public void draw(Graphics2D g2){
    this.g2 = g2;

    switch(sceneNum){
      case macaco: scene_macaco(); break;
      case ending: scene_ending(); break;
    }
  }
  public void scene_macaco(){
    if(scenePhase == 0){
      gp.bossBattleON = true;

      //shut the iron door
      for(int i = 0; i < gp.obj[1].length; i++){
        if(gp.obj[gp.currentMap][i] == null){
          gp.obj[gp.currentMap][i] = new OBJ_Door_Iron(gp);
          gp.obj[gp.currentMap][i].worldX = gp.tileSize*25;
          gp.obj[gp.currentMap][i].worldY = gp.tileSize*28;
          gp.obj[gp.currentMap][i].temp = true;
          gp.playeSE(21);
          break;
        }
      }
      //Search a vacant slot for dummy
      for(int i = 0; i < gp.npc[1].length; i++){
        if(gp.npc[gp.currentMap][i] == null){
          gp.npc[gp.currentMap][i] = new PlayerDummy(gp);
          gp.npc[gp.currentMap][i].worldX = gp.player.worldX;
          gp.npc[gp.currentMap][i].worldY = gp.player.worldY;
          gp.npc[gp.currentMap][i].direction = gp.player.direction;
          break;   
        }
      }
      gp.player.drawing = false;

      scenePhase++;
    }
    if(scenePhase == 1){
      gp.player.worldY -= 2;
      if(gp.player.worldY < gp.tileSize*19){
        scenePhase++;
      }
    }
    if(scenePhase == 2){
      //search the boss
      for(int i = 0; i < gp.monster[1].length; i++){
        if(gp.monster[gp.currentMap][i] != null && 
            gp.monster[gp.currentMap][i].name == MON_Monkey.monName){
              gp.monster[gp.currentMap][i].sleep = false;
              gp.ui.npc = gp.monster[gp.currentMap][i];
              scenePhase++;
              break;
        }
      }
    }
     if(scenePhase == 3){
      // the boss speak 
      gp.ui.drawDialogueScreen();
     }
     if(scenePhase == 4){
      // Return to the player

      //search the dummy
      for(int i = 0; i < gp.npc[1].length; i++){
        if(gp.npc[gp.currentMap][i] != null && gp.npc[gp.currentMap][i].name.equals(PlayerDummy.npcName)){
          //Restore the player position
          gp.player.worldX = gp.npc[gp.currentMap][i].worldX;
          gp.player.worldY = gp.npc[gp.currentMap][i].worldY;
          //Delete the dummy
          gp.npc[gp.currentMap][i] = null;
          break;
        }
      }
      //Start drawing the player
      gp.player.drawing = true;

      //Reset 
      sceneNum = NA;
      scenePhase = 0;
      gp.gameState = gp.playState;

      //change the music battle
      gp.stopMusic();
      gp.playMusic(22);
     }
  }
  public void scene_ending(){
    if(scenePhase == 0){
      gp.stopMusic();
      gp.ui.npc = new OBJ_BlueHeart(gp);
      scenePhase++;
    }
    if(scenePhase == 1){
      //Display dialogues
      gp.ui.drawDialogueScreen();
    }
    if(scenePhase == 2){
      //Play the fanfare
      gp.playeSE(4);
      scenePhase++;
    }
    if(scenePhase == 3){
      //wait until the sound effect ends
      if(counterReached(300) == true){
        scenePhase++;
      }
    }
    if(scenePhase == 4){
      // the screen gets darker
      alpha += 0.005f;
      if(alpha > 1f){
        alpha = 1f;
      }
      drawBlackground(alpha);

      if(alpha == 1){
        alpha = 0;
        scenePhase++;
      }
    }
    if(scenePhase == 5){
      drawBlackground(1f);
      alpha += 0.005f;
      if(alpha > 1f){
        alpha = 1f;
      }

      String text = "After the fierce battle with the Skeleton Loard,\n" + 
                    "the Cearence finally found the legendary treasure\n" +
                    "But this not the end of his journey.\n" +
                    "The Cearence adventure has just begun";
      drawString(alpha, 38f, 200, text, 70);

      if(counterReached(600) == true){
        gp.playMusic(0);
        scenePhase++;
      }
    }
    if(scenePhase == 6){
      drawBlackground(1f);

      drawString(1f, 80f, gp.screenHeight/2, "TOCANTIN'S LEGENDY'S", 40);

      if(counterReached(480) == true){
        scenePhase++;
      }
    }
    if(scenePhase == 7){
      drawBlackground(1f);
      y = gp.screenHeight/2;
      drawString(1f, 38f, y, endCredit, 40);

      if(counterReached(480) == true){
          scenePhase++;
      }
    }  
    if(scenePhase == 8){
      drawBlackground(1f);

      //Scrolling the credit
      y--;
      drawString(1f, 38f, y, endCredit, 40);
      
    }
  }
  public boolean counterReached(int trarget){
    boolean counterReached = false;
    counter++;
    if(counter > trarget){
      counterReached = true;
      counter = 0;
    }
    return counterReached;
  }
  public void drawBlackground(float alpha){
    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
    g2.setColor(Color.black);
    g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
  }
  public void drawString(float alpha, float fontSize, int y, String text, int lineHeight){
     g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
     g2.setColor(Color.white);
     g2.setFont(g2.getFont().deriveFont(fontSize));

     for(String line: text.split("\n")){
      int x = gp.ui.getXforCenteredText(line);
      g2.drawString(line, x, y);
      y += lineHeight;
     }
     g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
  }
}
