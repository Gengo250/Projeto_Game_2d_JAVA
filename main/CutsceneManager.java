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
  // INTRO
  String introStory;
  String[] introLines;
  int introLinesToShow = 0;


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
  public final int intro  = 3;
   public final int macacoDeath = 4; 


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

     introStory =
    "No coração do Tocantins, onde o cerrado encontra a floresta,\n"
  + "espíritos antigos observam tudo em silêncio.\n\n"
  + "Algo estranho começou a corromper rios, animais\n"
  + "e criaturas que antes eram pacíficas.\n\n"
  + "Sua aldeia sente que uma força sombria desperta\n"
  + "e um macaco ancestral foi tomado pela fúria.\n\n"
  + "Você é o jovem guerreiro escolhido pelo seu povo.\n"
  + "Explore a floresta, enfrente criaturas enfurecidas\n"
  + "e descubra a origem dessa corrupção.\n\n"
  + "Colete artefatos sagrados, proteja sua aldeia\n"
  + "e restaure o equilíbrio da terra dos ancestrais.";


     introLines = introStory.split("\n\n");
  }
  public void draw(Graphics2D g2){
    this.g2 = g2;

    switch(sceneNum){
      case macaco: scene_macaco(); break;
      case ending: scene_ending(); break;
      case intro: scene_intro(); break;
      case macacoDeath: scene_macacoDeath(); break;
    }
  }
public void scene_macaco(){

    // FASE 0: fecha porta, cria dummy e configura câmera usando a posição REAL do boss
 // FASE 0: fecha portas, cria dummy e configura câmera SUBINDO até (77,26)
if (scenePhase == 0) {
    gp.bossBattleON = true;

    // 1) Fecha a "parede" de portas de ferro na linha 51, colunas 76..80 (mapa 2)
    int[] doorCols = {76, 77, 78, 79, 80};
    for (int c = 0; c < doorCols.length; c++) {
        for (int i = 0; i < gp.obj[1].length; i++) {
            if (gp.obj[gp.currentMap][i] == null) {
                gp.obj[gp.currentMap][i] = new OBJ_Door_Iron(gp);
                gp.obj[gp.currentMap][i].worldX = gp.tileSize * doorCols[c];
                gp.obj[gp.currentMap][i].worldY = gp.tileSize * 51; // posição das portas
                gp.obj[gp.currentMap][i].temp   = true;
                break;
            }
        }
    }
    gp.playeSE(21); // som das portas fechando

    // 2) Cria o dummy do player na posição original
    for (int i = 0; i < gp.npc[1].length; i++) {
        if (gp.npc[gp.currentMap][i] == null) {
            gp.npc[gp.currentMap][i] = new PlayerDummy(gp);
            gp.npc[gp.currentMap][i].worldX   = gp.player.worldX;
            gp.npc[gp.currentMap][i].worldY   = gp.player.worldY;
            gp.npc[gp.currentMap][i].direction = gp.player.direction;
            break;
        }
    }

    // 3) Esconde o player real (a câmera continua seguindo ele)
    gp.player.drawing = false;

    // 4) Acha o boss macaco no mapa atual (para as fases seguintes)
    monkeyBoss = null;
    for (int i = 0; i < gp.monster[1].length; i++) {
        if (gp.monster[gp.currentMap][i] != null &&
            gp.monster[gp.currentMap][i].name == MON_Monkey.monName) {

            monkeyBoss = (MON_Monkey) gp.monster[gp.currentMap][i];
            break;
        }
    }

    // 5) Caminho da câmera:
    //    começa em (77, 49) e SOBE até (77, 26)
    monkeyCamStartCol = 77;
    monkeyCamStartRow = 49; // linha do gatilho da arena
    monkeyCamEndCol   = 77;
    monkeyCamEndRow   = 26; // posição alvo lá em cima (onde está o boss/arena)

    // posição inicial da câmera (em pixels)
    gp.player.worldX = monkeyCamStartCol * gp.tileSize;
    gp.player.worldY = monkeyCamStartRow * gp.tileSize;

    // alvo em pixels
    monkeyCamTargetX = monkeyCamEndCol * gp.tileSize;
    monkeyCamTargetY = monkeyCamEndRow * gp.tileSize;

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
  // ---------------------------------------------------------
  // CUTSCENE DA MORTE DO MACACO
  // ---------------------------------------------------------
  public void scene_macacoDeath() {

    // FASE 0 – prepara dummy do player, acha o boss e começa o fade
    if (scenePhase == 0) {

      // Guarda posição atual do player num PlayerDummy
      for (int i = 0; i < gp.npc[1].length; i++) {
        if (gp.npc[gp.currentMap][i] == null) {
          gp.npc[gp.currentMap][i] = new PlayerDummy(gp);
          gp.npc[gp.currentMap][i].worldX   = gp.player.worldX;
          gp.npc[gp.currentMap][i].worldY   = gp.player.worldY;
          gp.npc[gp.currentMap][i].direction = gp.player.direction;
          break;
        }
      }

      // Esconde o player real
      gp.player.drawing = false;

      // Acha o boss macaco no mapa atual
      monkeyBoss = null;
      for (int i = 0; i < gp.monster[1].length; i++) {
        if (gp.monster[gp.currentMap][i] != null &&
            gp.monster[gp.currentMap][i].name == MON_Monkey.monName) {
          monkeyBoss = (MON_Monkey) gp.monster[gp.currentMap][i];
          break;
        }
      }

      // Se por algum motivo não achar o boss, só aplica o XP pendente e sai
      if (monkeyBoss == null) {
        gp.player.drawing = true;
        gp.player.resolvePendingBossRewards();
        sceneNum = NA;
        scenePhase = 0;
        gp.gameState = gp.playState;
        return;
      }

      // Garante que ele está "parado" em down1
      monkeyBoss.direction = "down";
      monkeyBoss.spriteNum = 1;
      monkeyBoss.attacking = false;
      monkeyBoss.sleep = false;

      // Começa fade-in para preto (transição para a cutscene)
      alpha = 0f;
      counter = 0;

      scenePhase = 1;
    }

      // FASE 1 – tela escurece, depois move câmera para o macaco
    else if (scenePhase == 1) {

        // Transição mais lenta (~2.5s), parecida com o teleport
        int fadeDuration = (int)(gp.FPS * 2.5f);  // 60 FPS → 150 frames

        if (counter < fadeDuration) {
            alpha = (float)counter / (float)fadeDuration;
            if (alpha > 1f) alpha = 1f;

            drawBlackground(alpha);  // desenha o preto por cima de TUDO
            counter++;
        }
        else {
            // Garante que a tela está totalmente preta
            alpha = 1f;
            drawBlackground(alpha);

            // Quando estiver tudo escuro, "teleporta" a câmera pro boss
            if (monkeyBoss != null) {
                gp.player.worldX = monkeyBoss.worldX;
                gp.player.worldY = monkeyBoss.worldY;
            }

            counter = 0;
            scenePhase = 2;
        }
    }


    // FASE 2 – fade-out suave + murmúrio de dor + começa em down1
    else if (scenePhase == 2) {

      if (counter == 0) {
        // som de dor do boss (pode trocar por 25 se quiser o grito)
        gp.playeSE(6);
      }

      // Garante que começa na segunda fase (inRage) em down1
      if (monkeyBoss != null) {
        monkeyBoss.inRage = true;
        monkeyBoss.direction = "down";
        monkeyBoss.spriteNum = 1;
        monkeyBoss.getImage();
        monkeyBoss.getAttackImage();
        monkeyBoss.getBeamImages();
      }

      if (alpha > 0f) {
        drawBlackground(alpha);
        alpha -= 0.05f;
        if (alpha < 0f) alpha = 0f;
      }

      counter++;

      // depois de um tempo, começa a piscar (transição fase 2 → fase 1)
      if (counter > 30) {
        monkeyCounter = 0;
        scenePhase = 3;
      }
    }

    // FASE 3 – macaco piscando entre rage / normal por alguns segundos
    else if (scenePhase == 3) {

      if (monkeyBoss != null) {
        monkeyCounter++;

        // a cada 8 frames troca entre inRage true/false e pisca transparência
        if (monkeyCounter % 30 == 0) {
          monkeyBoss.inRage = !monkeyBoss.inRage;
          monkeyBoss.transparent = !monkeyBoss.transparent;
          monkeyBoss.getImage();
          monkeyBoss.getAttackImage();
          monkeyBoss.getBeamImages();
        }

        // depois de ~1.5s fixa na fase 1 (sem rage)
        if (monkeyCounter > 90) {
          monkeyBoss.inRage = false;
          monkeyBoss.transparent = false;
          monkeyBoss.getImage();
          monkeyBoss.getAttackImage();
          monkeyBoss.getBeamImages();

          // configura o diálogo final
          gp.ui.npc = monkeyBoss;
          monkeyBoss.dialogueSet = 1;   // usa dialogues[1][..] = fala de morte
          monkeyBoss.dialogueIndex = 0;

          scenePhase = 4;
        }
      } else {
        // fallback, se algo der errado, pula pro fim
        scenePhase = 5;
      }
    }

    // FASE 4 – diálogo: "aprovo a sua bravura... tome esta banana..."
    else if (scenePhase == 4) {
      gp.ui.drawDialogueScreen();
      // Quando o jogador terminar o diálogo,
      // o próprio UI vai incrementar scenePhase++
      // (mesma lógica usada na cutscene do boss inicial)
    }

   // FASE 5 – some, dá banana e drops direto pro inventário, aplica EXP e volta pro jogo
else if (scenePhase == 5) {

    if (monkeyBoss != null) {

        // Efeito de partícula opcional
        monkeyBoss.generatorParticule(monkeyBoss, monkeyBoss);

        // Dá recompensa de itens direto no inventário
        monkeyBoss.giveRewardsToPlayerDirect();

        // Aplica o XP e level-up que ficaram pendentes
        gp.player.resolvePendingBossRewards();

        // Marca o boss como realmente morto (GamePanel.update vai limpar)
        monkeyBoss.alive = false;
        monkeyBoss.dying = false;
    }

    // Volta o player para uma posição um pouco mais afastada do boss
    for (int i = 0; i < gp.npc[1].length; i++) {
        if (gp.npc[gp.currentMap][i] != null &&
            gp.npc[gp.currentMap][i].name != null &&
            gp.npc[gp.currentMap][i].name.equals(PlayerDummy.npcName)) {

            int dummyX = gp.npc[gp.currentMap][i].worldX;
            int dummyY = gp.npc[gp.currentMap][i].worldY;

            // Calcula a direção do player em relação ao boss
            int bossCol;
            int bossRow;
            if (monkeyBoss != null) {
                bossCol = monkeyBoss.worldX / gp.tileSize;
                bossRow = monkeyBoss.worldY / gp.tileSize;
            } else {
                bossCol = dummyX / gp.tileSize;
                bossRow = dummyY / gp.tileSize;
            }

            int playerCol = dummyX / gp.tileSize;
            int playerRow = dummyY / gp.tileSize;

            int dCol = playerCol - bossCol;
            int dRow = playerRow - bossRow;

            int offsetTiles = 2;  // QUANTO o player vai se afastar do macaco

            // Afasta mais 2 tiles na direção em que o player já estava
            if (Math.abs(dCol) >= Math.abs(dRow)) {
                if (dCol >= 0) playerCol += offsetTiles;
                else           playerCol -= offsetTiles;
            } else {
                if (dRow >= 0) playerRow += offsetTiles;
                else           playerRow -= offsetTiles;
            }

            gp.player.worldX = playerCol * gp.tileSize;
            gp.player.worldY = playerRow * gp.tileSize;
            gp.player.direction = gp.npc[gp.currentMap][i].direction;

            gp.npc[gp.currentMap][i] = null;
            break;
        }
    }

    gp.player.drawing = true;

    // Garante que a boss battle acabou e volta música da área
    gp.bossBattleON = false;
    gp.stopMusic();
    gp.playMusic(23);

    // Finaliza a cutscene
    sceneNum = NA;
    scenePhase = 0;
    gp.gameState = gp.playState;
    gp.teleportPlayerToMap(2, 19, 55);
}

  }

private void drawCurrentIntroBlock(float alpha) {
    if (introLines == null || introLines.length == 0) return;
    if (introLinesToShow < 0 || introLinesToShow >= introLines.length) return;

    String block = introLines[introLinesToShow].trim();
    // usa o 'y' atual como posição do bloco
    drawString(alpha, 28f, y, block, 34);
}

public void scene_intro() {

    // FASE 0: configura tudo só uma vez
    if (scenePhase == 0) {
        gp.stopMusic();
        gp.playMusic(0); // música da intro

        alpha   = 0f;
        counter = 0;

        introLinesToShow = 0;         // começa no primeiro bloco ("No coração...")
        y = gp.screenHeight / 2;      // centro da tela (posição inicial do parágrafo)

        scenePhase++;
    }

    // FASE 1: fade-in do fundo + primeiro bloco PARADO
    else if (scenePhase == 1) {

        if (alpha < 1f) {
            alpha += 0.02f;
            if (alpha > 1f) alpha = 1f;
        }

        drawBlackground(alpha);
        drawCurrentIntroBlock(alpha);

        // Quando a tela já estiver totalmente escura, vai para a fase de pausa
        if (alpha >= 1f) {
            counter = 0;
            scenePhase++;
        }
    }

    // FASE 2: bloco atual PARADO (tempo pra ler)
    else if (scenePhase == 2) {

        drawBlackground(1f);
        drawCurrentIntroBlock(1f);

        // espera ~3 segundos antes de começar a descer
        if (counterReached(300)) { // 300 frames ≈ 5s (se estiver em 60 FPS)
            scenePhase++;
        }
    }

    // FASE 3: bloco DESCENDO pra fora da tela
    else if (scenePhase == 3) {

        drawBlackground(1f);

        // move o bloco pra baixo (efeito "crédito de filme")
        y += 1;
        drawCurrentIntroBlock(1f);

        // quando o bloco passar bem pra baixo da tela
        if (y > gp.screenHeight + gp.tileSize * 2) {

            introLinesToShow++;  // vai pro PRÓXIMO bloco

            if (introLinesToShow >= introLines.length) {
                // ACABARAM OS BLOCOS -> vai pra transição final
                scenePhase = 4;
                counter = 0;
                alpha = 1f;
            } else {
                // ainda tem mais blocos
                // reseta posição pra aparecer de novo no meio
                y = gp.screenHeight / 2;
                counter = 0;
                scenePhase = 2; // volta pra fase parado, agora com o próximo bloco
            }
        }
    }

    // FASE 4: tela preta parada (fim da intro, antes do jogo começar)
    else if (scenePhase == 4) {

        drawBlackground(1f);

        if (counterReached(90)) { // ~1.5 segundos de tela preta
            scenePhase++;
        }
    }

    // FASE 5: fade-out do preto e libera o jogo
    else if (scenePhase == 5) {

        if (alpha > 0f) {
            drawBlackground(alpha);
            alpha -= 0.02f;
            if (alpha < 0f) alpha = 0f;
        } else {
            // acabou a intro → começa o jogo
            sceneNum   = NA;
            scenePhase = 0;

            gp.gameState = gp.playState;

            gp.stopMusic();
            gp.playMusic(27); // música da fase
        }
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

String text = "Após a batalha feroz contra o Macaco Ancestral,\n" + 
              "o jovem guerreiro enfim recuperou o tesouro sagrado da floresta.\n" +
              "Mas esse não é o fim de sua jornada.\n" +
              "A verdadeira aventura do guerreiro está apenas começando.";


      drawString(alpha, 28f, 200, text, 70);

      if(counterReached(600) == true){
        gp.playMusic(26);
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
