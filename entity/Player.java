package entity;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;


import main.GamePanel;
import main.KeyHandler;
import object.OBJ_Fireball;
import object.OBJ_Key;
import object.OBJ_Lantern;
import object.OBJ_Shield_Wood;
import object.OBJ_Sword_Normal;


public class Player extends Entity {
  
  
  GamePanel gp;
  KeyHandler keyH;

  public final int sreenX;
  public final int screenY;
  int standCounter = 0;
  public boolean attackCanceled = false;
  public boolean lightUpdated = false;

  public Player(GamePanel gp, KeyHandler keyH){
    super(gp);

    this.gp = gp;
    this.keyH = keyH;

    sreenX = gp.screenWidth/2 - (gp.tileSize/2);
    screenY = gp.screenHeight/2 - (gp.tileSize/2);

    //SOLID AREA
    solidArea = new Rectangle();
    solidArea.x = 8;
    solidArea.y = 16;
    solidAreaDefaultX = solidArea.x;
    solidAreaDefaultY = solidArea.y;
    solidArea.width = 32;
    solidArea.height = 32;
    //ATTACK AREA
    //attackArea.width = 36;
    //attackArea.height = 36;

    setDefaultValues();
    getPlayerImage();
    getPlayerAttckImage();
    setItems();
  }

  public void setDefaultValues(){
  
    //worldX = gp.tileSize * 23;
    //worldY = gp.tileSize * 21;
    worldX = gp.tileSize * 12;
    worldY = gp.tileSize * 17;

    //gp.currentMap = 0;
    defualtSpeed = 4;
    speed = defualtSpeed;
    direction = "down";

    // PLAYER STATUS
    level = 1;
    maxLife = 6;
    life = maxLife;
    maxMana = 4;
    mana = maxMana;
    //ammo = 10;
    strength = 1; // the more strenght he has, the more damage he gives
    dexterity = 1; // the more dexterity he has, the less damage he receives
    exp = 0;
    nextLevelExp = 5;
    coin = 1000;
    currenWeapon = new OBJ_Sword_Normal(gp);
    currentyShield = new OBJ_Shield_Wood(gp);
    projectile = new OBJ_Fireball(gp);
    attack = getAttack(); // the total attack value is decided by strength and weapon
    defense = getDefense(); // the total defense value is decided by dexterity and shield
  }
  public void setDefaultPositions(){
    worldX = gp.tileSize * 24;
    worldY = gp.tileSize * 33;
    direction = "down";
  }
  public void restoreLifeAndMana(){
     life = maxLife;
     mana = maxMana;
     invencible = false;
  }
  public void setItems(){
    inventory.clear();
    inventory.add(currenWeapon);
    inventory.add(currentyShield);
    inventory.add(new OBJ_Key(gp));
    inventory.add(new OBJ_Lantern(gp));
  }
  public int getAttack(){
    return attack = strength * currenWeapon.attackValue;
  }
  public int getDefense(){
    attackArea = currenWeapon.attackArea;
    return defense = dexterity * currentyShield.defenseValue;
  }
  public void getPlayerImage(){

    up1 = setup("/res/player/indio_up_1", gp.tileSize, gp.tileSize);
    up2 = setup("/res/player/indio_up_2", gp.tileSize, gp.tileSize);
    down1 = setup("/res/player/indio_down_1", gp.tileSize, gp.tileSize);
    down2 = setup("/res/player/indio_down_2", gp.tileSize, gp.tileSize);
    left1 = setup("/res/player/indio_left_1", gp.tileSize, gp.tileSize);
    left2 = setup("/res/player/indio_left_2", gp.tileSize, gp.tileSize);
    right1 = setup("/res/player/indio_right_1", gp.tileSize, gp.tileSize);
    right2 = setup("/res/player/indio_right_2", gp.tileSize, gp.tileSize);

  }
  public void getSleepImage(BufferedImage image){
    up1 = image;
    up2 = image;
    down1 = image;
    down2 = image;
    left1 = image;
    left2 = image;
    right1 = image;
    right2 = image;
  }
  public void getPlayerAttckImage(){
    if(currenWeapon.type == type_sword){
      attackUp1 = setup("/res/player/boy_attack_up_1", gp.tileSize, gp.tileSize*2);
      attackUp2 = setup("/res/player/boy_attack_up_2", gp.tileSize, gp.tileSize*2);
      attackDown1 = setup("/res/player/boy_attack_down_1", gp.tileSize, gp.tileSize*2);
      attackDown2 = setup("/res/player/boy_attack_down_2", gp.tileSize, gp.tileSize*2);
      attackLeft1 = setup("/res/player/boy_attack_left_1", gp.tileSize*2, gp.tileSize);
      attackLeft2 = setup("/res/player/boy_attack_left_2", gp.tileSize*2, gp.tileSize);
      attackRight1 = setup("/res/player/boy_attack_right_1", gp.tileSize*2, gp.tileSize);  
      attackRight2 = setup("/res/player/boy_attack_right_2", gp.tileSize*2, gp.tileSize);
    }
    if(currenWeapon.type == type_axe){
      attackUp1 = setup("/res/player/boy_axe_up_1", gp.tileSize, gp.tileSize*2);
      attackUp2 = setup("/res/player/boy_axe_up_2", gp.tileSize, gp.tileSize*2);
      attackDown1 = setup("/res/player/boy_axe_down_1", gp.tileSize, gp.tileSize*2);
      attackDown2 = setup("/res/player/boy_axe_down_2", gp.tileSize, gp.tileSize*2);
      attackLeft1 = setup("/res/player/boy_axe_left_1", gp.tileSize*2, gp.tileSize);
      attackLeft2 = setup("/res/player/boy_axe_left_2", gp.tileSize*2, gp.tileSize);
      attackRight1 = setup("/res/player/boy_axe_right_1", gp.tileSize*2, gp.tileSize);  
      attackRight2 = setup("/res/player/boy_axe_right_2", gp.tileSize*2, gp.tileSize);
    }

  }
  
  public void update(){
    if(attacking == true){
      attacking();
    } else if(keyH.upPressed == true || keyH.downPressed == true || 
            keyH.leftPressed == true || keyH.rightPressed == true || keyH.enterPressed == true){

              if(keyH.upPressed == true){
                direction = "up";
               
              } 
              else if(keyH.downPressed == true){
                direction = "down";
               
              }
              else if(keyH.leftPressed == true){
                direction = "left";
               
              } 
              else if(keyH.rightPressed == true){
                direction = "right";
              
              }
              //CHECK TILE COLLISION
              collisionOn = false;
              gp.cChecker.checkTile(this);

              //CHECK OBJECT COLLISION
             int objIndex = gp.cChecker.checkObject(this,true);
             pickUpObject(objIndex);

             //CHECK NPC COLLISION
             int ncpIndex = gp.cChecker.checkEntity(this, gp.npc);
             interactNPC(ncpIndex);

             //CHECK MONSTER COLLISION
             int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
             contactMonster(monsterIndex);

             //CHECK INTERACTIVE TILE COLLISION
             gp.cChecker.checkEntity(this, gp.iTile);
             

             //CHECK EVENT 
             gp.eHandler.checkEvent();

              //IF COLLISION IS FALSE, PLAYER CAN MOVE
              if(collisionOn == false && gp.keyH.enterPressed == false){
                switch(direction){
                  case "up":
                    worldY -= speed;
                  break;
                  case "down":
                    worldY += speed;
                  break;
                  case "left":
                    worldX -= speed;
                  break;
                  case "right":
                    worldX += speed;
                  break;
                }
              }
              if(keyH.enterPressed == true && attackCanceled == false){
                gp.playeSE(7);
                attacking = true;
                spriteCounter = 0;
              }
              attackCanceled = false;
              gp.keyH.enterPressed = false;
            spriteCounter++;
            if (spriteCounter > 12) {
                spriteNum = (spriteNum == 1) ? 2 : 1;
                spriteCounter = 0;
            }

        } else {
            standCounter++;
            if (standCounter > 20) {
                spriteNum = 1;        
                spriteCounter = 0;
                standCounter = 0;
            }
        }
        if(gp.keyH.shotKeyPressed == true && projectile.alive == false 
        && shotAvailableCounter == 30 && projectile.haveResource(this) == true){
          // SET DEFUALT COORDINATES DIRECTION AND USER
          projectile.set(worldX, worldY, direction, true, this);

          //SUBTRACT THE COST (MANA, AMMO, ETC)
          projectile.subtractResouce(this);

         // CHECK VACANCY
         for(int i = 0; i < gp.projectile[1].length; i++){
          if(gp.projectile[gp.currentMap][i] == null){
            gp.projectile[gp.currentMap][i] =  projectile;
            break;
          }
         }

          shotAvailableCounter = 0;

          gp.playeSE(10);
        }

        //This needs to be outside of key if statement!
        if(invencible == true){
          invencibleCounter++;
          if(invencibleCounter > 60){
            invencible = false;
            invencibleCounter = 0;
          }
        } 
        if(shotAvailableCounter < 30){
          shotAvailableCounter++;
        }
        if(life > maxLife){
          life = maxLife;
    }
        if(mana > maxMana){
          mana = maxMana ;
    }
        if(life <= 0){
          gp.gameState = gp.gameOverState;
          gp.ui.commandNum = -1;
          //gp.stopMusic();
          gp.playeSE(12);
        }

  }

  public void attacking(){
    spriteCounter++;

    if(spriteCounter <= 5){
      spriteNum = 1;
    }
    if(spriteCounter > 5 && spriteCounter <= 25){
      spriteNum = 2;

      // Save the current worldX, worldY, solidArea
      int currentWordX = worldX;
      int currentWordY = worldY;
      int solidAreaWidth = solidArea.width;
      int solidAreaHeight = solidArea.height;

      // Adjust player's wordX/Y for the attackArea
      switch(direction){
        case "up":worldY -= attackArea.height;
        break;
        case "down":worldY += attackArea.height;
        break;
        case "left":worldX -= attackArea.width;
        break;
        case "right":worldX += attackArea.width;
        break;
      }
      // attackArea becomes solidArea
      solidArea.width = attackArea.width;
      solidArea.height = attackArea.height;
      // Check monster collision with the updated worldX, worldY and solidArea 
      int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
      damageMonter(monsterIndex, attack, currenWeapon.knokBackPower);

      int iTileIndex = gp.cChecker.checkEntity(this, gp.iTile);
      damageInteractiveTile(iTileIndex);

      int projectileIndex = gp.cChecker.checkEntity(this, gp.projectile);
      damageProjectile(projectileIndex);

      //After cheking collision, restore the original data
      worldX = currentWordX;
      worldY = currentWordY;
      solidArea.width = solidAreaWidth;
      solidArea.height = solidAreaHeight;
    }
    if(spriteCounter > 25){
      spriteNum = 1;
      spriteCounter = 0;
      attacking = false;
    }
  }
            
  public void pickUpObject(int i){
    if(i != 999){
      // PICKUP ONLY ITEMS
      if(gp.obj[gp.currentMap][i].type == type_pickupOnly){
        gp.obj[gp.currentMap][i].use(this);
        gp.obj[gp.currentMap][i] = null;
      }
      //OBSTACLE
      else if(gp.obj[gp.currentMap][i].type == type_obstacle){
        if(keyH.enterPressed == true){
          attackCanceled = true;
          gp.obj[gp.currentMap][i].interact();
        }
      }
      // INVENTORY ITEMS
      else{
        String text;
      if(canObtainItem(gp.obj[gp.currentMap][i]) == true){
        gp.playeSE(1);
        text = "Got a " + gp.obj[gp.currentMap][i].name + "!";
      }
      else {
        text = "Tou cannot varry any more";
      }
      gp.ui.addMessage(text);
      gp.obj[gp.currentMap][i] = null;
      }
    } 
  }

  public void interactNPC(int i){
    if(gp.keyH.enterPressed == true){
        if(i != 999){
              attackCanceled = true;
              gp.gameState = gp.dialogueState;
              gp.npc[gp.currentMap][i].speak();
      }
    
    } 
  }
  public void contactMonster(int i){
    if(i != 999){
      if(invencible == false && gp.monster[gp.currentMap][i].dying == false){
         gp.playeSE(6);

         
          int damage = gp.monster[gp.currentMap][i].attack - defense;
          if(damage < 0){
            damage = 0;
          }
         life -= damage;
         invencible = true;
      }
    }
  }
  public void damageMonter(int i, int attack, int knokBackPower){
    if( i != 999){
      if(gp.monster[gp.currentMap][i].invencible == false){
          gp.playeSE(5);

          if(knokBackPower > 0){
            knokBack(gp.monster[gp.currentMap][i], knokBackPower);
          }
          int damage = attack - gp.monster[gp.currentMap][i].defense;
          if(damage < 0){
            damage = 0;
          }
          gp.monster[gp.currentMap][i].life -= damage;
          gp.ui.addMessage(damage + " damage!");
          gp.monster[gp.currentMap][i].invencible = true;
          gp.monster[gp.currentMap][i].damageReaction();

        if(gp.monster[gp.currentMap][i].life <= 0){
          gp.monster[gp.currentMap][i].dying = true;
          gp.ui.addMessage("killed the " + gp.monster[gp.currentMap][i].name + "!");
          gp.ui.addMessage("EXP " + gp.monster[gp.currentMap][i].exp);
          exp += gp.monster[gp.currentMap][i].exp;
          checkLevelUp();
        }
      }
    }
  }
  public void knokBack(Entity entity, int knokBackPower){
    entity.direction = direction;
    entity.speed += knokBackPower;
    entity.knokBack = true;

  }
  public void damageInteractiveTile(int i){
    if(i != 999 && gp.iTile[gp.currentMap][i].destructible == true 
    && gp.iTile[gp.currentMap][i].isCorrectItem(this) == true && gp.iTile[gp.currentMap][i].invencible == false){
      
      gp.iTile[gp.currentMap][i].playSE();
      gp.iTile[gp.currentMap][i].life--;
      gp.iTile[gp.currentMap][i].invencible = true;

      //Generate particle
      generatorParticule(gp.iTile[gp.currentMap][i], gp.iTile[gp.currentMap][i]);

      if(gp.iTile[gp.currentMap][i].life == 0){
        gp.iTile[gp.currentMap][i] = gp.iTile[gp.currentMap][i].getDestroyedForm();
      }
    }
  }
  public void damageProjectile(int i){
    if(i != 999){
      Entity projectile = gp.projectile[gp.currentMap][i];
      projectile.alive =  false;
      generatorParticule(projectile, projectile);
      
    }
  }
  public void checkLevelUp(){
    if(exp >= nextLevelExp){
      level++;
      nextLevelExp = nextLevelExp*2;
      maxLife += 2;
      strength ++;
      dexterity ++;
      attack = getAttack();
      defense = getDefense();

      gp.playeSE(8);
      gp.gameState = gp.dialogueState;
      gp.ui.currentDialogue = "You are level " + level + " now!\n"
                              + "You feel stronger!";
    }
  }
  public void selectItem(){
    int itemIndex = gp.ui.getItemIndexOnSlot(gp.ui.playerslotCol, gp.ui.playerslotRow);
    if(itemIndex < inventory.size()){
      Entity selectedItem = inventory.get(itemIndex);
      if(selectedItem.type == type_sword || selectedItem.type == type_axe){
        currenWeapon = selectedItem;
        attack = getAttack();
        getPlayerAttckImage();
      }
      if(selectedItem.type == type_shield){
        currentyShield = selectedItem;
        defense = getDefense();
      }
      if(selectedItem.type == type_light){
        if(currentyLight == selectedItem){
          currentyLight = null;
        }
        else {
          currentyLight = selectedItem;
        }
        lightUpdated = true;
      }
      if(selectedItem.type == type_consumable){
        if(selectedItem.use(this) == true){
          if(selectedItem.amount > 1){
            selectedItem.amount--;
          } else {
            inventory.remove(itemIndex);
          }
        }
      }
    }
  }
  public int searchItemInInventory(String itemName){
    int itemIndex = 999;

    for(int i = 0; i < inventory.size(); i++){
      if(inventory.get(i).name.equals(itemName)){
        itemIndex = i;
        break;
      }
    }
    return itemIndex;
  }
  public boolean canObtainItem(Entity item){
    boolean canObtain = false;

    // CHECK IF STACKABLE
    if(item.stackble == true){
      int index = searchItemInInventory(item.name);
        if(index != 999){
          inventory.get(index).amount++;
          canObtain = true;
        }
        else { // new item so need to check vacancy
          if(inventory.size() != maxInventorySize){
            inventory.add(item);
            canObtain = true;
          }
        }
      }
      else { // NOT STACKABLE so check vacancy
        if(inventory.size() != maxInventorySize){
            inventory.add(item);
            canObtain = true;
        }
      }
      return canObtain;
    }
  
  public void draw(Graphics2D g2){

    BufferedImage image = null;
    int tempScreenX = sreenX;
    int tempScreenY = screenY;

    switch(direction){
      case "up":
      if(attacking == false){
         if(spriteNum == 1){image = up1;} 
         if(spriteNum == 2 ){image = up2;}
      }
      if(attacking == true){
        tempScreenY = screenY - gp.tileSize;
         if(spriteNum == 1){image = attackUp1;} 
         if(spriteNum == 2 ){image = attackUp2;}
      }
      break;

      case "down":
      if(attacking == false){
         if(spriteNum == 1){image = down1;} 
         if(spriteNum == 2 ){image = down2;}
      }
      if(attacking == true){
         if(spriteNum == 1){image = attackDown1;} 
         if(spriteNum == 2 ){image = attackDown2;}
      }
      break;

      case "left":
      if(attacking == false){
         if(spriteNum == 1){image = left1;} 
         if(spriteNum == 2 ){image = left2;}
      }
      if(attacking == true){
        tempScreenX = sreenX - gp.tileSize;
         if(spriteNum == 1){image = attackLeft1;} 
         if(spriteNum == 2 ){image = attackLeft2;}
      }
      break;

      case "right":
      if(attacking == false){
         if(spriteNum == 1){image = right1;} 
         if(spriteNum == 2 ){image = right2;}
      }
      if(attacking == true){
         if(spriteNum == 1){image = attackRight1;} 
         if(spriteNum == 2 ){image = attackRight2;}
      }
      break;
    }
    if(invencible == true){
      g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
    }
    g2.drawImage(image, tempScreenX, tempScreenY, null);

    //Reset alpha
    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

    // DEBUG 
    //g2.setFont(new Font("Arial", Font.PLAIN, 26));
    //g2.setColor(Color.white);
    //g2.drawString("Invencible: "+invencibleCounter, 10, 400);
  }

}
