package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import entity.Entity;
import object.OBJ_Heart;
import object.OBJ_ManaCrystal;



public class UI {

  GamePanel gp;
  Graphics2D g2;
  public Font minecraft, vRCOSD;
  BufferedImage heart_full, heart_half, heart_blank, crystal_full, crystal_blanck, coin;
  public boolean messageOn = false;
  ArrayList<String> message = new ArrayList<>();
  ArrayList<Integer> messageCounter = new ArrayList<>();
  public boolean gameFinished = false;
  public String currentDialogue = "";
  public int commandNum = 0;
  public int titleScreenState = 0; // 0: the first srcreen, 1: the second screen 
  public int playerslotCol = 0;
  public int playerslotRow = 0;
  public int npcSlotCol = 0;
  public int npcSlotRow = 0;
  int subState = 0;
  int counter = 0;
  public Entity npc;



  public UI(GamePanel gp){
    this.gp = gp;

    
    try{
      InputStream is = getClass().getResourceAsStream("/res/font/Minecraft.ttf");
      minecraft = Font.createFont(Font.TRUETYPE_FONT, is);
      is = getClass().getResourceAsStream("/res/font/VCR_OSD_MONO_1.001.ttf");
      vRCOSD = Font.createFont(Font.TRUETYPE_FONT, is);
    } catch(FontFormatException e){
      e.printStackTrace();
    } catch(IOException e){
      e.printStackTrace();
    }
    //CREATE HUD OBJECT
    Entity heart = new OBJ_Heart(gp);
    heart_full = heart.image;
    heart_half = heart.image2;
    heart_blank = heart.image3;
    Entity crystal = new OBJ_ManaCrystal(gp);
    crystal_full = crystal.image;
    crystal_blanck = crystal.image2;
    Entity bronzeCoin = new object.OBJ_Coin_Bronze(gp);
    coin = bronzeCoin.down1;
  }
  public void addMessage(String text){
    message.add(text);
    messageCounter.add(0);
  }
  public void draw(Graphics2D g2){
    this.g2 = g2;

    //g2.setFont(minecraft);
    g2.setFont(vRCOSD);
    g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
    g2.setColor(Color.white);

    //TITLE STATE
    if (gp.gameState == gp.titleState) {
      drawTitleScreen();
    }

    //PLAY STATE
    if(gp.gameState == gp.playState){
      drawPlayLife();
      drawMessage();
    }

    //PAUSE STATE  
    if(gp.gameState == gp.pauseState){
      drawPlayLife();
      drawPauseScreen();
    }
    //DIALOGUE STATE
    if(gp.gameState == gp.dialogueState){
      drawPlayLife();
      drawDialogueScreen();
    }
    //CHARACTER STATE
    if(gp.gameState == gp.characterState){
      drawCharacterScreen();
      drawInventory(gp.player, true);
    }
    //OPTIONS STATE
    if(gp.gameState == gp.optionState){
      drawOptionScreen();
    }
    // GAMEOVER STATE
    if(gp.gameState == gp.gameOverState){
      drawGameOverScreen();
    }
    // TRANSITION STATE
     if(gp.gameState == gp.transitionState){
      drawTransition();
    }
    // TRADE STATE
    if(gp.gameState == gp.tradeState){
      drawTradeScreen();
    }
    // SLEEP STATE 
     if(gp.gameState == gp.sleepState){
      drawSleepScreen();
    }
 }
 public void drawPlayLife(){


  int x = gp.tileSize/2;
  int y = gp.tileSize/2;
  int i = 0;

  //DRAW MAX LIFE 
  while(i < gp.player.maxLife/2){
    g2.drawImage(heart_blank,x, y, null);
    i++;
    x += gp.tileSize;
  }

  //RESET
   x = gp.tileSize/2;
   y = gp.tileSize/2;
   i = 0;

   //DRAW CURRENT LIFE
   while(i < gp.player.life){
    g2.drawImage(heart_half, x, y, null);
    i++;
    if(i < gp.player.life){
      g2.drawImage(heart_full, x, y, null);
    }
    i++;
    x += gp.tileSize;
   }
   //DRAW MAX MANA 
   x = (gp.tileSize/2)-5;
   y = (int) (gp.tileSize*1.5);
   i = 0;
   while(i < gp.player.maxMana){
    g2.drawImage(crystal_blanck, x, y, null);
    i++;
    x += 35;
   }

   //DRAW MANA
   x = (gp.tileSize/2)-5;
   y = (int) (gp.tileSize*1.5);
   i = 0;
   while(i < gp.player.mana){
    g2.drawImage(crystal_full, x, y, null);
    i++;
    x += 35;
   }
 }
 public void drawMessage(){
  int messageX = gp.tileSize;
  int messageY = gp.tileSize*4;
  g2.setFont(g2.getFont().deriveFont(Font.BOLD, 32F));
  for(int i = 0; i < message.size(); i++){
    if(message.get(i) != null){

      g2.setColor(Color.black);
      g2.drawString(message.get(i), messageX+2, messageY+2);

      g2.setColor(Color.white);
      g2.drawString(message.get(i), messageX, messageY);
      int couter = messageCounter.get(i) + 1;
      messageCounter.set(i, couter);
      messageY += 50;

      if(messageCounter.get(i) > 180){
        message.remove(i);
        messageCounter.remove(i);
      }

    }
  }
 }
public void drawTitleScreen() {
// --- TELA DE TÍTULO (STATE 0) SIMPLIFICADA ---
if (titleScreenState == 0) {
    // FUNDO
    g2.setColor(Color.BLACK);
    g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

    // ===== TÍTULO =====
    final int TITLE_SIZE = 72;
    g2.setFont(g2.getFont().deriveFont(Font.BOLD, TITLE_SIZE));

    String title = "TOCANTINS'S LEGENDS";
    int titleX = getXforCenteredText(title);
    int titleY = gp.tileSize * 3;

    // sombra
    g2.setColor(Color.GRAY);
    g2.drawString(title, titleX + 4, titleY + 4);

    // texto principal
    g2.setColor(Color.WHITE);
    g2.drawString(title, titleX, titleY);

    // ===== BONECO (EMBAIXO DO TÍTULO) =====
    int boyWidth  = gp.tileSize * 2;
    int boyHeight = gp.tileSize * 2;

    // gap logo abaixo do título (menor que antes)
    int gapBelowTitle = gp.tileSize / 2;

    // centraliza horizontalmente
    int boyX = (gp.screenWidth - boyWidth) / 2;

    // posiciona logo abaixo da linha de base do título
    // (usa a descida da fonte pra não colar demais)
    int boyY = titleY + g2.getFontMetrics().getDescent() + gapBelowTitle;

    g2.drawImage(gp.player.down1, boyX, boyY, boyWidth, boyHeight, null);

    // ===== MENU (ESPACAMENTO MAIS JUSTO) =====
    final int MENU_SIZE = 44;
    g2.setFont(g2.getFont().deriveFont(Font.BOLD, MENU_SIZE));

    // altura de linha mais compacta (menos "pulado")
    final int lineHeight = (int)(MENU_SIZE * 1.0);

    final int menuTopGap = (int)(gp.tileSize * 1.6);
    int y = boyY + boyHeight + menuTopGap;

    String text;
    int x;

    // NEW GAME
    text = "NEW GAME";
    x = getXforCenteredText(text);
    g2.drawString(text, x, y);
    if (commandNum == 0) g2.drawString(">", x - gp.tileSize, y);

    // LOAD GAME
    y += lineHeight;
    text = "LOAD GAME";
    x = getXforCenteredText(text);
    g2.drawString(text, x, y);
    if (commandNum == 1) g2.drawString(">", x - gp.tileSize, y);

    // QUIT
    y += lineHeight;
    text = "QUIT";
    x = getXforCenteredText(text);
    g2.drawString(text, x, y);
    if (commandNum == 2) g2.drawString(">", x - gp.tileSize, y);

    } else if (titleScreenState == 1) {
        // LIMPA A TELA TAMBÉM AQUI
        g2.setColor(Color.black);
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        // CLASS SELECTION SCREEN
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(42F));

        String text = "Select your class!";
        int x = getXforCenteredText(text);
        int y = gp.tileSize * 3;
        g2.drawString(text, x, y);

        // Fighter
        text = "Fighter";
        x = getXforCenteredText(text);
        y += gp.tileSize * 3;
        g2.drawString(text, x, y);
        if (commandNum == 0) {
            g2.drawString(">", x - gp.tileSize, y);
        }

        // Thief
        text = "Thief";
        x = getXforCenteredText(text);
        y += gp.tileSize;
        g2.drawString(text, x, y);
        if (commandNum == 1) {
            g2.drawString(">", x - gp.tileSize, y);
        }

        // Sorcerer
        text = "Sorcerer";
        x = getXforCenteredText(text);
        y += gp.tileSize;
        g2.drawString(text, x, y);
        if (commandNum == 2) {
            g2.drawString(">", x - gp.tileSize, y);
        }

        // Back
        text = "Back";
        x = getXforCenteredText(text);
        y += gp.tileSize * 2;
        g2.drawString(text, x, y);
        if (commandNum == 3) {
            g2.drawString(">", x - gp.tileSize, y);
        }
    }
}

 public void drawPauseScreen(){

  g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 80F));
  String text = "PAUSED";
  int x = getXforCenteredText(text);
  int y = gp.screenHeight/2;

  g2.drawString(text, x, y);
 }
 public void drawDialogueScreen(){

    //WINDOW
    int x = gp.tileSize*3;
    int y =  gp.tileSize/2;
    int width = gp.screenWidth - (gp.tileSize*6);
    int height = gp.tileSize*4;
    drawSubWindow(x, y, width, height);

    g2.setFont(g2.getFont().deriveFont(Font.PLAIN,25F));
    x += gp.tileSize;
    y += gp.tileSize;

    // BREAK LINE DIALOGUE
    for(String line : currentDialogue.split("\n")){
      g2.drawString(line, x, y);
      y += 40;
    } 

 }
 public void drawCharacterScreen() {
  // CREATE A FRAME
  final int frameX = gp.tileSize * 2;
  final int frameY = gp.tileSize;
  final int frameWidth  = gp.tileSize * 5 + 40; // +4 cresce para a direita
  final int frameHeight = gp.tileSize * 10 + 40;
  drawSubWindow(frameX, frameY, frameWidth, frameHeight);

  // TEXT
  g2.setColor(Color.WHITE);
  g2.setFont(g2.getFont().deriveFont(25F));

  int textX = frameX + 20;
  int textY = frameY + gp.tileSize;
  final int lineHeight = 35;

  // NAMES
  g2.drawString("Level", textX, textY);             textY += lineHeight;
  g2.drawString("Life", textX, textY);              textY += lineHeight;
  g2.drawString("Mana", textX, textY);              textY += lineHeight;
  g2.drawString("Strength", textX, textY);          textY += lineHeight;
  g2.drawString("Dexterity", textX, textY);         textY += lineHeight;
  g2.drawString("Attack", textX, textY);            textY += lineHeight;
  g2.drawString("Defense", textX, textY);           textY += lineHeight;
  g2.drawString("Exp", textX, textY);               textY += lineHeight;
  g2.drawString("Next Level", textX, textY);        textY += lineHeight;
  g2.drawString("Coin", textX, textY);              textY += lineHeight + 20;
  g2.drawString("Weapon", textX, textY);            textY += lineHeight + 20;
  g2.drawString("Shield", textX, textY);            textY += lineHeight;

  // VALUES
  final int innerBorder = 5;      // white border
  final int rightPadding = 20;    
  int tailX = frameX + frameWidth - innerBorder - rightPadding;

  // Reset textY 
  textY = frameY + gp.tileSize;

  String value;

  value = String.valueOf(gp.player.level);
  textX = getXforAlingToRightText(value, tailX);
  g2.drawString(value, textX, textY);

  value = gp.player.life + "/" + gp.player.maxLife;
  textX = getXforAlingToRightText(value, tailX);
  g2.drawString(value, textX, textY += lineHeight);

  value = gp.player.mana + "/" + gp.player.maxMana;
  textX = getXforAlingToRightText(value, tailX);
  g2.drawString(value, textX, textY += lineHeight);

  value = String.valueOf(gp.player.strength);
  textX = getXforAlingToRightText(value, tailX);
  g2.drawString(value, textX, textY += lineHeight);

  value = String.valueOf(gp.player.dexterity);
  textX = getXforAlingToRightText(value, tailX);
  g2.drawString(value, textX, textY += lineHeight);

  value = String.valueOf(gp.player.attack);
  textX = getXforAlingToRightText(value, tailX);
  g2.drawString(value, textX, textY += lineHeight);

  value = String.valueOf(gp.player.defense);
  textX = getXforAlingToRightText(value, tailX);
  g2.drawString(value, textX, textY += lineHeight);

  value = String.valueOf(gp.player.exp);
  textX = getXforAlingToRightText(value, tailX);
  g2.drawString(value, textX, textY += lineHeight);

  value = String.valueOf(gp.player.nextLevelExp);
  textX = getXforAlingToRightText(value, tailX);
  g2.drawString(value, textX, textY += lineHeight);

  value = String.valueOf(gp.player.coin);
  textX = getXforAlingToRightText(value, tailX);
  g2.drawString(value, textX, textY += lineHeight);
  
  // === ÍCONES Weapon / Shield alinhados às linhas ===
  final int iconSize = gp.tileSize;
  final int iconX = tailX - iconSize;     // borda direita alinhada com a coluna de valores
  final int ascent = g2.getFontMetrics().getAscent();
  final int extraDown = -2;                // pequeno ajuste vertical

  // Baselines das linhas (espelha o "+ 20" que você usou nos NAMES)
  final int weaponBaseline = textY + lineHeight + 13;            // 1 linha abaixo de Coin
  final int shieldBaseline = weaponBaseline + lineHeight + 13;   // 1 linha abaixo de Weapon

  final int weaponIconY = weaponBaseline - ascent + extraDown;
  final int shieldIconY = shieldBaseline - ascent + extraDown;

  if (gp.player.currenWeapon != null && gp.player.currenWeapon.down1 != null) {
      g2.drawImage(gp.player.currenWeapon.down1, iconX, weaponIconY, iconSize, iconSize, null);
  }
  if (gp.player.currentyShield != null && gp.player.currentyShield.down1 != null) {
      g2.drawImage(gp.player.currentyShield.down1, iconX, shieldIconY, iconSize, iconSize, null);
  }
}
public void drawInventory(Entity entity, boolean cursor) {
    int frameX = 0;
    int frameY = 0;
    int frameWidth = 0;
    int frameHeigh = 0;
    int slotCol = 0;
    int slotRow = 0;

    if (entity == gp.player) {
        frameX = gp.tileSize * 12;
        frameY = gp.tileSize;
        frameWidth = gp.tileSize * 6;
        frameHeigh = gp.tileSize * 5;
        slotCol = playerslotCol;
        slotRow = playerslotRow;
    } else {
        frameX = gp.tileSize * 2;
        frameY = gp.tileSize;
        frameWidth = gp.tileSize * 6;
        frameHeigh = gp.tileSize * 5;
        slotCol = npcSlotCol;
        slotRow = npcSlotRow;
    }

    // FRAME
    drawSubWindow(frameX, frameY, frameWidth, frameHeigh);

    // SLOT
    final int slotXstart = frameX + 20;
    final int slotYstart = frameY + 20;
    int slotX = slotXstart;
    int slotY = slotYstart;
    int slotSize = gp.tileSize + 3;

    // DRAW PLAYER'S ITEMS
    for (int i = 0; i < entity.inventory.size(); i++) {

        // EQUIP CURSOR
        if (entity.inventory.get(i) == entity.currenWeapon ||
            entity.inventory.get(i) == entity.currentyShield || 
            entity.inventory.get(i) == entity.currentyLight) {
            g2.setColor(new Color(240, 190, 90));
            g2.fillRoundRect(slotX, slotY, gp.tileSize, gp.tileSize, 10, 10);
        }

        g2.drawImage(entity.inventory.get(i).down1, slotX, slotY, null);

        // DISPLAY AMOUNT
        if(entity == gp.player && entity.inventory.get(i).amount > 1){
          g2.setFont(g2.getFont().deriveFont(26F));
          int amountX;
          int amountY;
          String s = "" + entity.inventory.get(i).amount;
          amountX = getXforAlingToRightText(s, slotX+44);
          amountY = slotY + gp.tileSize;

          //SHADOW
          g2.setColor(new Color(60,60,60));
          g2.drawString(s, amountX, amountY);
          // NUMBER
          g2.setColor(Color.white);
          g2.drawString(s, amountX-3, amountY-3);
        }
        slotX += slotSize;

        if (i == 4 || i == 9 || i == 14) {
            slotX = slotXstart;
            slotY += slotSize;
        }
    }

    // CURSOR
    if (cursor) {
        int cursorX = slotXstart + (slotSize * slotCol);
        int cursorY = slotYstart + (slotSize * slotRow);
        int cursorWidth = gp.tileSize;
        int cursorHeight = gp.tileSize;

        // DRAW CURSOR
        g2.setColor(Color.white);
        g2.setStroke(new BasicStroke(3));
        g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);

        // DESCRIPTION FRAME
        int dFrameX = frameX;
        int dFrameY = frameY + frameHeigh;
        int dFrameWidth = frameWidth;
        int dFrameHeight = gp.tileSize * 3;

        // DRAW DESCRIPTION TEXT
        int textX = dFrameX + 20;
        int textY = dFrameY + gp.tileSize;

        g2.setFont(g2.getFont().deriveFont(20F));

        int itemIndex = getItemIndexOnSlot(slotCol, slotRow);

        if (itemIndex < entity.inventory.size()) {
            drawSubWindow(dFrameX, dFrameY, dFrameWidth, dFrameHeight);

            for (String line : entity.inventory.get(itemIndex).description.split("\n")) {
                g2.drawString(line, textX, textY);
                textY += 32;
            }
        }
    }
}


public void drawGameOverScreen(){
    // FUNDO ESCURO
  g2.setColor(new Color(0, 0, 0, 150));
  g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

  // ===== GAME OVER =====
  String text = "GAME OVER";
  g2.setFont(g2.getFont().deriveFont(Font.BOLD, 99F));

  // posição base: um pouco acima do centro
  int y = gp.screenHeight / 2 - gp.tileSize * 2;
  int x = getXforCenteredText(text);

  // sombra
  g2.setColor(Color.black);
  g2.drawString(text, x + 5, y + 5);

  // texto principal
  g2.setColor(Color.white);
  g2.drawString(text, x, y);

  // ===== MENU =====
  g2.setFont(g2.getFont().deriveFont(50F));

  // Retry
  text = "Retry";
  x = getXforCenteredText(text);
  y += gp.tileSize * 3; // desce a partir do GAME OVER
  g2.drawString(text, x, y);
  if(commandNum == 0){
    g2.drawString(">", x-40, y);
  }

  // Quit
  text = "Quit";
  x = getXforCenteredText(text);
  y += 55;
  g2.drawString(text, x, y);
  if(commandNum == 1){
    g2.drawString(">", x-40, y);
  }

}
public void drawOptionScreen(){
  g2.setColor(Color.white);
  g2.setFont(g2.getFont().deriveFont(23F));

  //SUB WINDOW
  int frameX = gp.tileSize*6;
  int frameY = gp.tileSize;
  int frameWidth = gp.tileSize*8;
  int frameHeight = gp.tileSize*10;
  drawSubWindow(frameX, frameY, frameWidth, frameHeight);

  switch(subState){
    case 0:options_top(frameX, frameY); break;
    case 1:options_fullScreenNotification(frameX, frameY); break;
    case 2:options_control(frameX, frameY); break;
    case 3: options_endGameConfirmation(frameX, frameY); break;
  }
  gp.keyH.enterPressed = false;
}
public void options_top(int frameX, int frameY){
  int textX;
  int textY;

  //TITLE
  String text = "Options";
  textX = getXforCenteredText(text);
  textY = frameY + gp.tileSize;
  g2.drawString(text, textX, textY);

  //FULL SCREEN ON/OF
  textX = frameX + gp.tileSize;
  textY += gp.tileSize*2;
  g2.drawString("Full screen", textX, textY);
  if(commandNum == 0){
    g2.drawString(">", textX-25, textY);
    if(gp.keyH.enterPressed == true){
      if(gp.fullScreenOn == false){
        gp.fullScreenOn = true;
      }
      else if(gp.fullScreenOn == true){
        gp.fullScreenOn = false;
      }
      subState = 1;
    }
  }
  //MUSIC 
  textY += gp.tileSize;
  g2.drawString("Music", textX, textY);
  if(commandNum == 1){
    g2.drawString(">", textX-25, textY);
  }
  // SE
  textY += gp.tileSize;
  g2.drawString("Effects", textX, textY);
  if(commandNum == 2){
    g2.drawString(">", textX-25, textY);
  }
  //CONTROL
  textY += gp.tileSize;
  g2.drawString("Control", textX, textY);
  if(commandNum == 3){
    g2.drawString(">", textX-25, textY);
    if(gp.keyH.enterPressed == true){
      subState = 2;
      commandNum = 0;
    }
  }
  //END GAME
  textY += gp.tileSize;
  g2.drawString("End Game", textX, textY);
  if(commandNum == 4){
    g2.drawString(">", textX-25, textY);
    if(gp.keyH.enterPressed == true){
      subState = 3;
      commandNum = 0;
    }
  }
   //BACK
  textY += gp.tileSize*2;
  g2.drawString("Back", textX, textY);
  if(commandNum == 5){
    g2.drawString(">", textX-25, textY);
    if(gp.keyH.enterPressed == true){
      gp.gameState = gp.playState;
      commandNum = 0;
    }
  }

  //FULL SCREEN CHECK BOX 
  textX = frameX + (int)(gp.tileSize*4.5);
  textY = frameY + gp.tileSize*2 + 24;
  g2.setStroke(new BasicStroke(3));
  g2.drawRect(textX, textY, 24, 24);
  if(gp.fullScreenOn == true){
    g2.fillRect(textX, textY, 24, 24);
  }

  //MUSIC VOLUME
  textY += gp.tileSize;
  g2.drawRect(textX, textY, 120, 24);
  int volumeWidth = 24 * gp.music.volumeScale;
  g2.fillRect(textX, textY, volumeWidth, 24);

  //SE VOLUME
  textY += gp.tileSize;
  g2.drawRect(textX, textY, 120, 24);
  volumeWidth = 24 * gp.se.volumeScale;
  g2.fillRect(textX, textY, volumeWidth, 24);

  gp.config.saveConfig();
}
public void options_fullScreenNotification(int frameX, int frameY){
  int textX =frameX + gp.tileSize;
  int textY = frameY + gp.tileSize*3;

  currentDialogue = "The chage will take \neffetc after restarting \nthe game.";
  for(String line: currentDialogue.split("\n")){
    g2.drawString(line, textX, textY);
    textY += 40;
  }

  //BACK
  textY = frameY + gp.tileSize*9;
  g2.drawString("Back", textX, textY);
  if(commandNum == 0){
    g2.drawString(">", textX-25, textY);
    if(gp.keyH.enterPressed == true){
      subState = 0;
    }
  }
}
public void options_control(int frameX, int frameY){
  int textX;
  int textY;

  //TITLE
  String text = "Control";
  textX = getXforCenteredText(text);
  textY = frameY + gp.tileSize;
  g2.drawString(text, textX, textY);

  textX = frameX + gp.tileSize;
  textY += gp.tileSize;
  g2.drawString("Move", textX, textY); textY += gp.tileSize;
  g2.drawString("Confirm/Attack", textX, textY); textY += gp.tileSize;
  g2.drawString("Shoot/Cast", textX, textY); textY += gp.tileSize;
  g2.drawString("Character Screen", textX, textY); textY += gp.tileSize;
  g2.drawString("Pause", textX, textY); textY += gp.tileSize;
  g2.drawString("Options", textX, textY); textY += gp.tileSize;

  textX = frameX + gp.tileSize*6;
  textY = frameY + gp.tileSize*2;
  g2.drawString("WASD", textX, textY); textY += gp.tileSize;
  g2.drawString("ENTER", textX, textY); textY += gp.tileSize;
  g2.drawString("F", textX, textY); textY += gp.tileSize;
  g2.drawString("C", textX, textY); textY += gp.tileSize;
  g2.drawString("P", textX, textY); textY += gp.tileSize;
  g2.drawString("ESC", textX, textY); textY += gp.tileSize;

  // BACK
  textX = frameX + gp.tileSize;
  textY = frameY + gp.tileSize*9;
  g2.drawString("Back", textX, textY);
  if(commandNum == 0){
    g2.drawString(">", textX-25, textY);
    if(gp.keyH.enterPressed == true){
      subState = 0;
      commandNum = 3;
    }
  }
}
public void options_endGameConfirmation(int frameX, int frameY){
  int textX = frameX + gp.tileSize;
  int textY = frameY + gp.tileSize*3;

  currentDialogue = "Quit the game and \nreturn to the \ntitle screen?";
  for(String line: currentDialogue.split("\n")){
    g2.drawString(line, textX, textY);
    textY += 40;
  }

  // YES
  String text = "Yes";
  textX = getXforCenteredText(text);
  textY += gp.tileSize*3;
  g2.drawString(text, textX, textY);
  if(commandNum == 0){
    g2.drawString(">", textX-25, textY);
    if(gp.keyH.enterPressed == true){
      subState = 0;
      gp.gameState = gp.titleState;
      titleScreenState = 0;
      commandNum = 0;
    }
  }
  // NO
  text = "No";
  textX = getXforCenteredText(text);
  textY += gp.tileSize;
  g2.drawString(text, textX, textY);
  if(commandNum == 1){
    g2.drawString(">", textX-25, textY);
    if(gp.keyH.enterPressed == true){
      subState = 0;
      commandNum = 4;
    }
  }

}
public void drawTransition(){
  counter++;
  g2.setColor(new Color(0,0,0,counter*5));
  g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
  if(counter == 50){
    counter = 0;
    gp.gameState = gp.playState;
    gp.currentMap = gp.eHandler.tempMap;
    gp.player.worldX = gp.tileSize * gp.eHandler.tempCol;
    gp.player.worldY = gp.tileSize * gp.eHandler.tempRow;

    gp.eHandler.previusEventX = gp.player.worldX;
    gp.eHandler.previusEventY = gp.player.worldY;

  }
}
public void drawTradeScreen(){
  switch(subState){
    case 0: trade_select(); break;
    case 1: trade_buy(); break;
    case 2: trade_sell(); break;
  }
  gp.keyH.enterPressed = false;
}
public void trade_select(){
  drawDialogueScreen();
  //DRAW WINDOW
  int x = gp.tileSize*15;
  int y = gp.tileSize*4;
  int width = gp.tileSize*3;
  int height = (int)(gp.tileSize*3.5);
  drawSubWindow(x, y, width, height);

  //DRAW TEXTS
  x += gp.tileSize;
  y += gp.tileSize;
  g2.drawString("Buy", x, y);
  if(commandNum == 0){
    g2.drawString(">", x-24, y);
    if(gp.keyH.enterPressed == true){
      subState = 1;
    }
  }
  y += gp.tileSize;
  
  g2.drawString("Sell", x, y);
  if(commandNum == 1){
    g2.drawString(">", x-24, y);
    if(gp.keyH.enterPressed == true){
      subState = 2;
    }
  }
  y += gp.tileSize;

  g2.drawString("Leave", x, y);
  if(commandNum == 2){
    g2.drawString(">", x-24, y);
    if(gp.keyH.enterPressed == true){
      commandNum = 0;
      gp.gameState = gp.dialogueState;
      currentDialogue = "Come back \nanytime!";
    }
  }
  
}
public void trade_buy(){
  // DRAW PLAYER INVENTORY
  drawInventory(gp.player, false);
  // DRAW NPC INVENTORY
  drawInventory(npc, true);

  //DRAW HINT WINDOW
  int x = gp.tileSize*2;
  int y = gp.tileSize*9;
  int width = gp.tileSize*6;
  int height = gp.tileSize*2;
  drawSubWindow(x, y, width, height);
  g2.drawString("[ESC] Back", x+24, y+60);

  // DRAW PLAYER COIN WINDOW
  x = gp.tileSize*12;
  y = gp.tileSize*9;
  width = gp.tileSize*6;
  height = gp.tileSize*2;
  drawSubWindow(x, y, width, height);
  g2.drawString("Your Coint: " + gp.player.coin, x+24, y+60);

  //DRAW PRICE WINDOW
  int itemIndex = getItemIndexOnSlot(npcSlotCol, npcSlotRow);
  if(itemIndex < npc.inventory.size()){
    x = (int)(gp.tileSize*5.5);
    y = (int)(gp.tileSize*5.5);
    width = (int)(gp.tileSize*2.5);
    height = gp.tileSize;
    drawSubWindow(x, y, width, height);
    g2.drawImage(coin, x+10, y+8, 32, 32, null);
    int price = npc.inventory.get(itemIndex).price;
    String text = "" + price;
    x = getXforAlingToRightText(text, gp.tileSize*8-20);
    g2.drawString(text, x, y+34);

    //BUY AN ITEM
    if(gp.keyH.enterPressed == true){
      if(npc.inventory.get(itemIndex).price > gp.player.coin){
        subState = 0;
        gp.gameState = gp.dialogueState;
        currentDialogue = "You don't have enough coin\nyou need more coin.";
        drawDialogueScreen();
      }
      else {
        if(gp.player.canObtainItem(npc.inventory.get(itemIndex)) == true){
          gp.player.coin -= npc.inventory.get(itemIndex).price;
        }
        else {
          subState = 0;
          gp.gameState = gp.dialogueState;
          currentDialogue = "You cannot any more";
        }
      }
    }
  }
}
public void trade_sell(){
  //DRAW PLAYER INVENTORY
  drawInventory(gp.player, true);

  int x;
  int y;
  int width;
  int height;

  //DRAW HINT WINDOW
  x = gp.tileSize*2;
  y = gp.tileSize*9;
  width = gp.tileSize*6;
  height = gp.tileSize*2;
  drawSubWindow(x, y, width, height);
  g2.drawString("[ESC] Back", x+24, y+60);

  // DRAW PLAYER COIN WINDOW
  x = gp.tileSize*12;
  y = gp.tileSize*9;
  width = gp.tileSize*6;
  height = gp.tileSize*2;
  drawSubWindow(x, y, width, height);
  g2.drawString("Your Coint: " + gp.player.coin, x+24, y+60);

  //DRAW PRICE WINDOW
  int itemIndex = getItemIndexOnSlot(playerslotCol, playerslotRow);
  if(itemIndex < gp.player.inventory.size()){

    x = (int)(gp.tileSize*15.5);
    y = (int)(gp.tileSize*5.5);
    width = (int)(gp.tileSize*2.5);
    height = gp.tileSize;
    drawSubWindow(x, y, width, height);
    g2.drawImage(coin, x+10, y+8, 32, 32, null);
    int price = gp.player.inventory.get(itemIndex).price/3;
    String text = "" + price;
    x = getXforAlingToRightText(text, gp.tileSize*18-20);
    g2.drawString(text, x, y+34);

    //SELL AN ITEM
    if(gp.keyH.enterPressed == true){
      if(gp.player.inventory.get(itemIndex) == gp.player.currenWeapon ||
         gp.player.inventory.get(itemIndex) == gp.player.currentyShield){
          commandNum = 0;
          subState = 0;
          gp.gameState = gp.dialogueState;
          currentDialogue = "You can't sell \nequipped items!";
      }
      else{
        if(gp.player.inventory.get(itemIndex).amount > 1){
          gp.player.inventory.get(itemIndex).amount--;
        }
        else {
           gp.player.inventory.remove(itemIndex);
        }
        gp.player.coin += price;
      }
    }
  }
}
public void drawSleepScreen(){
  counter++;
  if(counter < 120){
    gp.eManager.lighting.filterAlpha += 0.01f;
    if( gp.eManager.lighting.filterAlpha > 1f){
       gp.eManager.lighting.filterAlpha = 1f;
    }
  }
  if(counter >= 120){
     gp.eManager.lighting.filterAlpha -= 0.01f;
     if( gp.eManager.lighting.filterAlpha <= 0){
       gp.eManager.lighting.filterAlpha = 0f;
       counter = 0;
        gp.eManager.lighting.dayState =  gp.eManager.lighting.day;
        gp.eManager.lighting.dayCounter = 0;
        gp.gameState = gp.playState;
        gp.player.getPlayerImage();
     }
  }
}
public int getItemIndexOnSlot(int slotCol, int slotRow){
  int itemIndex = slotCol + (slotRow*5);
  return itemIndex;
}
 public void drawSubWindow(int x, int y, int width, int height){
  Color c = new Color(0,0,0,210);
  g2.setColor(c);
  g2.fillRoundRect(x, y, width, height, 35, 35);

  c = new Color(255,255,255);
  g2.setColor(c);
  g2.setStroke(new BasicStroke(5));
  g2.drawRoundRect(x+5, y+5, width-10, height-10, 25, 25);

 }
 public int getXforCenteredText(String text){
  int lenght = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
  int x = gp.screenWidth/2 - lenght/2;
  return x;
 }
  public int getXforAlingToRightText(String text, int tailX){
  int lenght = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
  int x = tailX - lenght;
  return x;
 }
}
