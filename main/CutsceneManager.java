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

  // CENA DO BOSS MACACO
  MON_Monkey monkeyBoss;
  int monkeyCounter;

  // Câmera da cutscene (em tiles)
  int monkeyCamStartCol = 61;   // começamos vindo da direita
  int monkeyCamStartRow;
  int monkeyCamEndCol;
  int monkeyCamEndRow;
  int monkeyCamTargetX;
  int monkeyCamTargetY;

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

    // FASE 0: fecha porta, cria dummy e configura câmera usando a posição REAL do boss
    if (scenePhase == 0) {
        gp.bossBattleON = true;

        // Fecha a porta de ferro
        for (int i = 0; i < gp.obj[1].length; i++) {
            if (gp.obj[gp.currentMap][i] == null) {
                gp.obj[gp.currentMap][i] = new OBJ_Door_Iron(gp);
                gp.obj[gp.currentMap][i].worldX = gp.tileSize * 25;
                gp.obj[gp.currentMap][i].worldY = gp.tileSize * 28;
                gp.obj[gp.currentMap][i].temp   = true;
                gp.playeSE(21);
                break;
            }
        }

        // Procura um slot vazio para o dummy do player
        for (int i = 0; i < gp.npc[1].length; i++) {
            if (gp.npc[gp.currentMap][i] == null) {
                gp.npc[gp.currentMap][i] = new PlayerDummy(gp);
                gp.npc[gp.currentMap][i].worldX   = gp.player.worldX;
                gp.npc[gp.currentMap][i].worldY   = gp.player.worldY;
                gp.npc[gp.currentMap][i].direction = gp.player.direction;
                break;
            }
        }

        // Esconde o player real (a câmera continua centrada nele)
        gp.player.drawing = false;

        // *** AQUI: acha o boss e usa a posição dele para centralizar a câmera ***
        for (int i = 0; i < gp.monster[1].length; i++) {
            if (gp.monster[gp.currentMap][i] != null &&
                gp.monster[gp.currentMap][i].name == MON_Monkey.monName) {

                monkeyBoss = (MON_Monkey) gp.monster[gp.currentMap][i];

                int bossCol = monkeyBoss.worldX / gp.tileSize;
                int bossRow = monkeyBoss.worldY / gp.tileSize;

                // Queremos vir da direita (coluna 61) mas alinhado na mesma LINHA do boss
                monkeyCamStartCol = 61;
                monkeyCamStartRow = bossRow;

                // Alvo final: exatamente em cima do boss
                monkeyCamEndCol = bossCol;
                monkeyCamEndRow = bossRow;

                // Define posição inicial da câmera (player) em tiles → pixels
                gp.player.worldX = monkeyCamStartCol * gp.tileSize;
                gp.player.worldY = monkeyCamStartRow * gp.tileSize;

                // Define o alvo em pixels (usado na FASE 1)
                monkeyCamTargetX = monkeyCamEndCol * gp.tileSize;
                monkeyCamTargetY = monkeyCamEndRow * gp.tileSize;

                break;
            }
        }

        scenePhase++;
    }

    // FASE 1: câmera deslizando de (61, bossRow) até exatamente o tile do boss
    if (scenePhase == 1) {

        boolean reachedX = false;
        boolean reachedY = false;

        // Move na horizontal (para a ESQUERDA)
        if (gp.player.worldX > monkeyCamTargetX) {
            gp.player.worldX -= 4; // velocidade da câmera
            if (gp.player.worldX <= monkeyCamTargetX) {
                gp.player.worldX = monkeyCamTargetX;
                reachedX = true;
            }
        } else {
            reachedX = true;
        }

        // Ajuste fino no Y para garantir alinhamento perfeito com o boss
        if (gp.player.worldY > monkeyCamTargetY) {
            gp.player.worldY -= 2;
            if (gp.player.worldY <= monkeyCamTargetY) {
                gp.player.worldY = monkeyCamTargetY;
                reachedY = true;
            }
        } else if (gp.player.worldY < monkeyCamTargetY) {
            gp.player.worldY += 2;
            if (gp.player.worldY >= monkeyCamTargetY) {
                gp.player.worldY = monkeyCamTargetY;
                reachedY = true;
            }
        } else {
            reachedY = true;
        }

        // Quando chegou no alvo, passa pra próxima fase
        if (reachedX && reachedY) {
            scenePhase++;
            monkeyCounter = 0;
        }
    }

    // FASE 2: configura o boss para a animação de banana/parado
    if (scenePhase == 2) {
        if (monkeyBoss != null) {
            monkeyBoss.sleep = false;
            monkeyBoss.getMonkeyParado(); // sprites: banana1, banana2, grito
            monkeyBoss.direction = "down";
            monkeyBoss.spriteNum = 1;     // começa comendo banana
            monkeyCounter = 0;
        }
        scenePhase++;
    }

    // FASE 3: macaco PARADO comendo banana (antes do diálogo)
    if (scenePhase == 3) {
        if (monkeyBoss != null) {
            monkeyCounter++;

            // Troca entre as duas imagens de banana
            if (monkeyCounter % 20 == 0) {
                if (monkeyBoss.spriteNum == 1) monkeyBoss.spriteNum = 2;
                else                            monkeyBoss.spriteNum = 1;
            }

            // Depois de um tempo, inicia o diálogo
            if (monkeyCounter > 120) {
                gp.ui.npc = monkeyBoss;
                monkeyCounter = -1; // marca para a fase do grito
                scenePhase++;
            }
        } else {
            scenePhase++;
        }
    }

    // FASE 4: diálogo do boss
    if (scenePhase == 4) {
        gp.ui.drawDialogueScreen();
        // Quando o UI terminar o diálogo, ele faz scenePhase++ (estado de cutscene)
    }

    // FASE 5: GRITO (mk_beam) DEPOIS do diálogo
if (scenePhase == 5) {
    if (monkeyBoss != null) {
        if (monkeyCounter == -1) {
            monkeyCounter = 0;
            gp.playeSE(25); // som do grito, se tiver
            gp.startScreenShake(120, 6);
        }

        monkeyCounter++;
        monkeyBoss.spriteNum = 3; // frame do grito (mk_beam)

        if (monkeyCounter > 60) { // ~1 segundo
            scenePhase++;
        }
    } else {
        scenePhase++;
    }
}


    // FASE 6: volta pro jogador e começa a batalha
    if (scenePhase == 6) {

        // Procura o dummy para restaurar a posição original do player
        for (int i = 0; i < gp.npc[1].length; i++) {
            if (gp.npc[gp.currentMap][i] != null &&
                gp.npc[gp.currentMap][i].name.equals(PlayerDummy.npcName)) {

                gp.player.worldX = gp.npc[gp.currentMap][i].worldX;
                gp.player.worldY = gp.npc[gp.currentMap][i].worldY;
                gp.npc[gp.currentMap][i] = null;
                break;
            }
        }

        // volta a desenhar o player
        gp.player.drawing = true;

        // volta as sprites normais de andar/atacar do boss
        if (monkeyBoss != null) {
            monkeyBoss.getImage();
            monkeyBoss.getAttackImage();
        }

        // Reset da cutscene
        sceneNum   = NA;
        scenePhase = 0;
        gp.gameState = gp.playState;

        // Música da batalha
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
