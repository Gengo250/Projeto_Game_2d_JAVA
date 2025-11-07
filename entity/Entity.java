package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.awt.AlphaComposite;
import java.awt.Color;

import javax.imageio.ImageIO;

import main.UtilityTool;
import main.GamePanel;

public class Entity {

  GamePanel gp;
  public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
  public BufferedImage attackUp1, attackUp2, attackDown1, attackDown2, attackLeft1, attackLeft2, attackRight1, attackRight2,
  guardUp, guardDown, guardLeft, guardRight;
  public BufferedImage image, image2, image3, image4, image5;
  public Rectangle solidArea = new Rectangle(0,0,48,48);
  public Rectangle attackArea = new Rectangle(0,0,0,0);
  public int solidAreaDefaultX, solidAreaDefaultY;
  public boolean collision = false;
  String dialogues[] = new String[20];
  public Entity attacker;
  
  //STATE
  public int worldX, worldY;
  public String direction = "down";
  public int spriteNum = 1;
  int dialogueIndex = 0;
  public boolean collisionOn = false;
  public boolean invencible = false;
  public boolean attacking = false;
  public boolean alive = true;
  public boolean dying = false;
  boolean hpBarOn = false;
  public boolean onPath = false;
  public boolean knokBack = false;
  public String knockBackDirecion;
  public boolean guarding = false;
  public boolean transparent = false;
  public boolean offBalance = false;

  //COUNTER
  public int spriteCounter = 0;
  public int actionLockCounter = 0;
  public int invencibleCounter = 0;
  public int shotAvailableCounter = 0;
  int dyingCounter = 0;
  int hpBarCounter = 0;
  int knokBackCounter = 0;
  public int guardCounter = 0;
  int offBalanceCounter = 0;


  //CHARACTER ATTIBUTES
  public String name;
  public int defualtSpeed;
  public int speed;
  public int maxLife;
  public int life;
  public int maxMana;
  public int mana;
  public int ammo;
  public int level;
  public int strength;
  public int dexterity;
  public int attack;
  public int defense;
  public int exp;
  public int nextLevelExp;
  public int coin;
  public int motion1_duration;
  public int motion2_duration;
  public Entity currenWeapon;
  public Entity currentyShield;
  public Entity currentyLight;
  public Projectile projectile;

  //ITEM ATTRIBUTES
  public ArrayList<Entity> inventory = new ArrayList<>();
  public final int maxInventorySize = 20;
  public int value;
  public int attackValue;
  public int defenseValue;
  public String description = "";
  public int useCost;
  public int price;
  public int knokBackPower = 0;
  public boolean stackble = false;
  public int amount = 1;
  public int lightRadius;
  
  //TYPE
  public int type; // 0 = player, 1 = npc , 2 = monster
  public final int type_player = 0;
  public final int type_npc = 1;
  public final int type_monster = 2;
  public final int type_sword = 3;
  public final int type_axe = 4;
  public final int type_shield = 5;
  public final int type_consumable = 6;
  public final int type_pickupOnly = 7;
  public final int type_obstacle = 8;
  public final int type_light = 9;

  public Entity(GamePanel gp){
    this.gp = gp;
  }
  public int getXdistance(Entity target){
    int xDistance = Math.abs(worldX - target.worldX);
    return xDistance;
  }
   public int getYdistance(Entity target){
    int yDistance = Math.abs(worldY - target.worldY);
    return yDistance;
  }
  public int getTileDistance(Entity target){
    int tileDistance = (getXdistance(target)+ getYdistance(target))/gp.tileSize;
    return tileDistance;
  }
  public int getGoalCol(Entity target){
    int goalCol = (target.worldX + target.solidArea.x)/gp.tileSize;
    return goalCol;
  }
  public int getGoalRow(Entity target){
     int goalRow = (gp.player.worldY + gp.player.solidArea.y)/gp.tileSize;
     return goalRow;
  }
  
  public void setAction(){}
  public void damageReaction(){
    
  }
  public int getLeftX(){
    return worldX + solidArea.x;
  }
  public int getRightX(){
    return worldX + solidArea.x + solidArea.width;
  }
  public int getTopY(){
    return worldY + solidArea.y;
  }
  public int getBottomY(){
    return worldY + solidArea.y + solidArea.height;
  }
  public int getCol(){
    return (worldX + solidArea.x)/gp.tileSize;
  }
  public int getRow(){
    return (worldY + solidArea.y)/gp.tileSize;
  }
  public void speak(){
    if(dialogues[dialogueIndex] == null){
      dialogueIndex = 0;
    }
    gp.ui.currentDialogue = dialogues[dialogueIndex];
    dialogueIndex++;
    switch(gp.player.direction){
      case "up":
        direction = "down";
        break;
      case "down":
        direction = "up";
        break;
      case "right":
        direction = "left";
        break;
      case "left":
        direction = "right";
        break;
    }
  }
  public void interact(){
    
  }
  public boolean use(Entity entity){return false;}
  public void checkDrop(){
  }
  public void dropItem(Entity droppedItem){
    for(int i = 0; i < gp.obj[1].length;i++){
      if(gp.obj[gp.currentMap][i] == null){
        gp.obj[gp.currentMap][i] = droppedItem;
        gp.obj[gp.currentMap][i].worldX = worldX; // the dead monster's worldX
        gp.obj[gp.currentMap][i].worldY = worldY;
        break;
      }
    }
  }
  public Color getParticleColor(){
    Color color = null;
    return color;
  }
  public int getParticleSize(){
    int size = 0;
    return size;
  }
  public int getParticleSpeed(){
    int speed = 0;
    return speed;
  }
  public int getParticleMaxLife(){
    int maxLife = 0;
    return maxLife;
  }
  public void generatorParticule(Entity generator, Entity target){
    Color color = generator.getParticleColor();
    int size = generator.getParticleSize();
    int speed = generator.getParticleSpeed();
    int maxLife = generator.getParticleMaxLife();

    Particule p1 = new Particule(gp, target, color, size, speed, maxLife, -2, -1);
    Particule p2 = new Particule(gp, target, color, size, speed, maxLife, 2, -1);
    Particule p3 = new Particule(gp, target, color, size, speed, maxLife, -2, 1);
    Particule p4 = new Particule(gp, target, color, size, speed, maxLife, 2, 1);
  
    gp.particleList.add(p1);
    gp.particleList.add(p2);
    gp.particleList.add(p3);
    gp.particleList.add(p4);

  }
 public void checkCollisio() {
    collisionOn = false;
    gp.cChecker.checkTile(this);
    gp.cChecker.checkObject(this, false);
    gp.cChecker.checkEntity(this, gp.npc);
    gp.cChecker.checkEntity(this, gp.monster);
    gp.cChecker.checkEntity(this, gp.iTile);
    boolean contactPlayer = gp.cChecker.checkPlayer(this);

    if(type == type_monster && contactPlayer == true){
      damagePlayer(attack);
    }


}

  public void update(){

    if(knokBack == true){
      checkCollisio();
      if(collisionOn == true){
        knokBackCounter = 0;
        knokBack = false;
        speed = defualtSpeed;
      }
      else if(collisionOn == false){
        switch(knockBackDirecion){
          case "up": worldY -= speed; break;
          case "down": worldY += speed; break;
          case "left": worldX -= speed; break;
          case "right": worldX += speed; break;
        }
      }
      knokBackCounter++;
      if(knokBackCounter == 10){
        knokBackCounter = 0;
        knokBack = false;
        speed = defualtSpeed;
      }
    }
    else if(attacking == true){
      attacking();
    }
    else {
       setAction();
       checkCollisio();
    
    //IF COLLISION IS FALSE, PLAYER CAN MOVE
     if(collisionOn == false){
      switch(direction){
        case "up": worldY -= speed; break;
        case "down": worldY += speed; break;
        case "left": worldX -= speed; break;
        case "right": worldX += speed;break;
        }
      }
      spriteCounter++;
      if(spriteCounter > 24){
        if(spriteNum == 1){
          spriteNum = 2;
        }
        else if(spriteNum == 2 ){
          spriteNum = 1;
        }
        spriteCounter = 0;
      }
    }
   
      if(invencible == true){
          invencibleCounter++;
          if(invencibleCounter > 40){
            invencible = false;
            invencibleCounter = 0;
          }
        } 
        if(shotAvailableCounter < 30){
          shotAvailableCounter++;
     }
     if(offBalance == true){
      offBalanceCounter++;
      if(offBalanceCounter > 60){
        offBalance = false;
        offBalanceCounter = 0;
      }
     }
  }
  public void checkAttackOrNot(int rate, int straight, int horizontal){
    boolean targetInRange = false;
    int xDis = getXdistance(gp.player);
    int yDis = getYdistance(gp.player);

    switch(direction){
      case "up":
        if(gp.player.worldY < worldY && yDis < straight && xDis < horizontal){
          targetInRange = true;
        } 
        break;
      case "down":
        if(gp.player.worldY > worldY && yDis < straight && xDis < horizontal){
          targetInRange = true;
        } 
       break;
      case "left": 
        if(gp.player.worldX < worldX && xDis < straight && yDis < horizontal){
          targetInRange = true;
        } 
        break;
      case "right": 
        if(gp.player.worldX > worldX && xDis < straight && yDis < horizontal){
          targetInRange = true;
        } 
        break;
    }
    if(targetInRange == true){
      //Check if it initiates an attack
      int i = new Random().nextInt(rate);
      if(i == 0){
        attacking = true;
        spriteNum = 0;
        shotAvailableCounter = 0;

      }
    }
  }
  public void checkShootOrNot(int rate, int shotInterval){
    int i = new Random().nextInt(rate);
    if(i == 0 && projectile.alive == false && shotAvailableCounter == shotInterval){
      projectile.set(worldX, worldY, direction, true, this);
      // CHECK VACANCY
      for(int ii = 0; ii < gp.projectile[1].length; ii++){
        if(gp.projectile[gp.currentMap][ii] == null){
          gp.projectile[gp.currentMap][ii] = projectile;
          break;
        }
      }
      shotAvailableCounter = 0;
    }
  }
   public void checkStartChasingOrNot(Entity target, int distance, int rate){
    if(getTileDistance(target) < distance){
      int i = new Random().nextInt(rate);
      if(i == 0){
        onPath = true;
      }
    }
  }
  public void checkStopChasingOrNot(Entity target, int distance, int rate){
    if(getTileDistance(target) > distance){
      int i = new Random().nextInt(rate);
      if(i == 0){
        onPath = false;
      }
    }
  }
  public void getRandomDirection(){
     actionLockCounter++;

     if (actionLockCounter == 120) {
            Random random = new Random();
            int i = random.nextInt(100) + 1; // pick up number from 1 to 100
            if (i <= 25) {direction = "up";}
            if (i > 25 && i <= 50) {direction = "down";}
            if (i > 50 && i <= 75) {direction = "left";}
            if (i > 75 && i <= 100) {direction = "right";}
            actionLockCounter = 0;
      }
  }
  public String getOppositeDirection(String direction){
    String oppossiteDirection = "";
    switch(direction){
      case "up": oppossiteDirection = "down"; break;
      case "down": oppossiteDirection = "up"; break;
      case "left": oppossiteDirection = "right"; break;
      case "right": oppossiteDirection = "left"; break;
    }
    return oppossiteDirection;
  }
   public void attacking(){
    spriteCounter++;

    if(spriteCounter <= motion1_duration){
      spriteNum = 1;
    }
    if(spriteCounter > motion1_duration && spriteCounter <= motion2_duration){
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

      if(type == type_monster){
        if(gp.cChecker.checkPlayer(this) == true){
          damagePlayer(attack);
        }
      } else { //Player 
         // Check monster collision with the updated worldX, worldY and solidArea 
        int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
        gp.player.damageMonter(monsterIndex, this, attack, currenWeapon.knokBackPower);

        int iTileIndex = gp.cChecker.checkEntity(this, gp.iTile);
        gp.player.damageInteractiveTile(iTileIndex);

        int projectileIndex = gp.cChecker.checkEntity(this, gp.projectile);
        gp.player.damageProjectile(projectileIndex);
      }
     

      //After cheking collision, restore the original data
      worldX = currentWordX;
      worldY = currentWordY;
      solidArea.width = solidAreaWidth;
      solidArea.height = solidAreaHeight;
    }
    if(spriteCounter > motion2_duration){
      spriteNum = 1;
      spriteCounter = 0;
      attacking = false;
    }
  }
  public void damagePlayer(int attack){
         if(gp.player.invencible == false){
          int damage = attack - gp.player.defense;
          // Get an opposite direction of this attacker
          String canGuardDirection = getOppositeDirection(direction);
          if(gp.player.guarding == true && gp.player.direction.equals(canGuardDirection)){
            // Prarry
            if(gp.player.guardCounter < 10){
              damage = 0;
              gp.playeSE(16);
              setknokBack(this, gp.player, knokBackPower);
              offBalance = true;
              spriteCounter =- 60;
            }
            damage /= 3;
            gp.playeSE(15);
          }  else {
            //Not guarding 
             gp.playeSE(6);
             if(damage < 0){
              damage = 1;
            }
          }
          if(damage != 0){
            gp.player.transparent = true;
            setknokBack(gp.player, this, knokBackPower);
          }
          setknokBack(gp.player, this, knokBackPower);
          gp.player.life -= damage;
          gp.player.invencible = true;
      }
  }
   public void setknokBack(Entity target,Entity attacker, int knokBackPower){
    this.attacker = attacker;
    target.knockBackDirecion = attacker.direction;
    target.speed += knokBackPower;
    target.knokBack = true;

  }
  public void draw(Graphics2D g2){

    BufferedImage image = null;
    int screenX = worldX - gp.player.worldX + gp.player.sreenX;
    int screenY = worldY - gp.player.worldY + gp.player.screenY;

    if(worldX + gp.tileSize > gp.player.worldX - gp.player.sreenX && 
       worldX - gp.tileSize < gp.player.worldX + gp.player.sreenX && 
       worldY + gp.tileSize > gp.player.worldY - gp.player.screenY && 
       worldY - gp.tileSize < gp.player.worldY + gp.player.screenY){

    int tempScreenX = screenX;
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
        tempScreenX = screenX - gp.tileSize;
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

        //Monster HP bar
        if(type == 2 && hpBarOn == true){

          double oneScale = (double)gp.tileSize / maxLife;
          double hpBarValue = oneScale*life;

          g2.setColor(new Color(32,35, 35)); 
          g2.fillRect(screenX - 1 , screenY - 16, gp.tileSize + 2, 12);
          g2.setColor(new Color(255, 0, 30)); //red
          g2.fillRect(screenX, screenY - 15, (int)hpBarValue, 10);

          hpBarCounter++;

          if(hpBarCounter > 600){
            hpBarCounter = 0;
            hpBarOn = false;
          }
        }
       


        if(invencible == true){
          hpBarOn = true;
          hpBarCounter = 0;
          changeAlpha(g2, 0.4F);
        }
        if(dying == true){
          dyingAnimation(g2);
        }

        g2.drawImage(image, tempScreenX, tempScreenY, null);
       
        changeAlpha(g2, 1F);
    }
  }
  public void dyingAnimation(Graphics2D g2){
    dyingCounter++;
    int i = 5;
    if(dyingCounter <= i){
      changeAlpha(g2, 0f);
    }
    if(dyingCounter > i && dyingCounter <= i*2){
      changeAlpha(g2, 1f);
    }
    if(dyingCounter > i*2 && dyingCounter <= i*3){
      changeAlpha(g2, 0f);
    }
    if(dyingCounter > i*3 && dyingCounter <= i*4){
      changeAlpha(g2, 1f);
    }
    if(dyingCounter > i*4 && dyingCounter <= i*5){
      changeAlpha(g2, 0f); 
    }
    if(dyingCounter > i*5 && dyingCounter <= i*6){
      changeAlpha(g2, 1f);
    }
    if(dyingCounter > i*6 && dyingCounter <= i*7){
      changeAlpha(g2, 0f);
    }
    if(dyingCounter > i*7 && dyingCounter <= i*8){
      changeAlpha(g2, 1f);
    }
    if(dyingCounter > i*8){
      alive = false;
    }
  }
  public void changeAlpha(Graphics2D g2, float alphaValue){
      g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue));
  }
  public BufferedImage setup(String imagePath, int width, int height){
    UtilityTool uTool = new UtilityTool();
    BufferedImage image = null;
    try{
      image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
      image = uTool.scaleImage(image, width, height);

    } catch(IOException e){
      e.printStackTrace();
    }
    return image;
  }
 public void searchPath(int goalCol, int goalRow) {
    int startCol = (worldX + solidArea.x) / gp.tileSize;
    int startRow = (worldY + solidArea.y) / gp.tileSize;

    gp.pFinder.setNode(startCol, startRow, goalCol, goalRow);
   if(gp.pFinder.search() == true){
    int nextX = gp.pFinder.pathList.get(0).col * gp.tileSize;
    int nextY = gp.pFinder.pathList.get(0).row * gp.tileSize;

    int enLeftX = worldX + solidArea.x;
    int enRighX = worldX + solidArea.x + solidArea.width;
    int enTopY = worldY + solidArea.y;
    int enBottonY = worldY + solidArea.y + solidArea.height;

    if(enTopY > nextY && enLeftX >= nextX && enRighX < nextX + gp.tileSize){
      direction = "up";
    }
     else if(enTopY < nextY && enLeftX >= nextX && enRighX < nextX + gp.tileSize){
      direction = "down";
    }
    else if(enTopY >= nextY && enBottonY < nextY + gp.tileSize){
      //left or right
      if(enLeftX > nextX){
        direction = "left";
      } 
      if(enLeftX < nextX){
        direction = "right";
        }
      }
      else if(enTopY > nextY && enLeftX > nextX){
        //up or left
        direction = "up";
        checkCollisio();
        if(collisionOn == true){
          direction = "left";
        }
      }
      else if(enTopY > nextY && enLeftX < nextX){
        //up or right
        direction = "up";
        checkCollisio();
        if(collisionOn == true){
          direction = "right";
        }
      }
      else if(enTopY < nextY && enLeftX > nextX){
        // down or left
        direction = "down";
        checkCollisio();
        if(collisionOn == true){
          direction = "left";
        }
      }
      else if(enTopY < nextY && enLeftX < nextX){
        // down or right
        direction = "down";
        checkCollisio();
        if(collisionOn == true){
          direction = "right";
        }
      }
      // IF reaches the goal, stop the search
      // int nextCol = gp.pFinder.pathList.get(0).col;
      // int nextRol = gp.pFinder.pathList.get(0).row;
      //if(nextCol == goalCol && nextRow == goalRow){
      //  OnPath = false;
      //}
    }
  }
  public int getDetected(Entity user, Entity target[][], String targetName){
    int index = 999;

    //check the surrounding object
    int nextWordX = user.getLeftX();
    int nextWordY = user.getTopY();

    switch(user.direction){
      case "up": nextWordY = user.getTopY()-1; break;
      case "down": nextWordY = user.getBottomY()+1; break;
      case "left": nextWordX = user.getLeftX()-1; break;
      case "right": nextWordX = user.getRightX()+1; break;
    }
    int col = nextWordX/gp.tileSize;
    int row = nextWordY/gp.tileSize;

    for(int i = 0; i < target[1].length; i++){
      if(target[gp.currentMap][i] != null){
        if(target[gp.currentMap][i].getCol() == col 
            && target[gp.currentMap][i].getRow() == row && target[gp.currentMap][i].name.equals(targetName)){
              index = i;
              break;
        }
      }
    }
    return index;
  }
}
