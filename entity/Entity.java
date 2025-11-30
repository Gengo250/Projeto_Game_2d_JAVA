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
  public BufferedImage up1, up2, up3, down1, down2, down3, left1, left2, left3, right1, right2, right3;
  public BufferedImage attackUp1, attackUp2, attackUp3,
      attackDown1, attackDown2, attackDown3,
      attackLeft1, attackLeft2, attackLeft3,
      attackRight1, attackRight2, attackRight3,
      guardUp, guardDown, guardLeft, guardRight;
  public BufferedImage image, image2, image3, image4, image5, image6;
  public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
  public Rectangle attackArea = new Rectangle(0, 0, 0, 0);
  public int solidAreaDefaultX, solidAreaDefaultY;
  public boolean collision = false;
  public String dialogues[][] = new String[20][20];
  public Entity attacker;
  public Entity linkedEntity;
  public boolean temp = false;

  // STATE
  public int worldX, worldY;
  public String direction = "down";
  public int spriteNum = 1;
  public int dialogueIndex = 0;
  public int dialogueSet = 0;
  public boolean collisionOn = false;
  public boolean invencible = false;
  public boolean attacking = false;
  public boolean alive = true;
  public boolean dying = false;
  public boolean hpBarOn = false;
  public boolean onPath = false;
  public boolean knokBack = false;
  public String knockBackDirecion;
  public boolean guarding = false;
  public boolean transparent = false;
  public boolean offBalance = false;
  public Entity loot;
  public boolean opened = false;
  public boolean inRage = false;
  public boolean sleep = false;
  public boolean drawing = true;
  public boolean shotThisAttack = false;

  // COUNTER
  public int spriteCounter = 0;
  public int actionLockCounter = 0;
  public int invencibleCounter = 0;
  public int shotAvailableCounter = 0;
  int dyingCounter = 0;
  public int hpBarCounter = 0;
  int knokBackCounter = 0;
  public int guardCounter = 0;
  int offBalanceCounter = 0;

  // CHARACTER ATTIBUTES
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
  public boolean boss;

  // ITEM ATTRIBUTES
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

  // TYPE
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
  public final int type_pickaxe = 10;
  public final int type_zarabatana = 11;

  public Entity(GamePanel gp) {
    this.gp = gp;
  }

  public int getXdistance(Entity target) {
    int xDistance = Math.abs(getCenterX() - target.getCenterX());
    return xDistance;
  }

  public int getYdistance(Entity target) {
    int yDistance = Math.abs(getBottomY() - target.getCenterY());
    return yDistance;
  }

  public int getTileDistance(Entity target) {
    int tileDistance = (getXdistance(target) + getYdistance(target)) / gp.tileSize;
    return tileDistance;
  }

  public int getGoalCol(Entity target) {
    int goalCol = (target.worldX + target.solidArea.x) / gp.tileSize;
    return goalCol;
  }

  public int getGoalRow(Entity target) {
    int goalRow = (gp.player.worldY + gp.player.solidArea.y) / gp.tileSize;
    return goalRow;
  }

  public void resetCounter() {
    spriteCounter = 0;
    actionLockCounter = 0;
    invencibleCounter = 0;
    shotAvailableCounter = 0;
    dyingCounter = 0;
    hpBarCounter = 0;
    knokBackCounter = 0;
    guardCounter = 0;
    offBalanceCounter = 0;
  }

  public void setLoot(Entity loot) {
  }

  public void setAction() {
  }

  public void move(String direction) {
  }

  public void damageReaction() {
  }

  public int getScreenX() {
    int screenX = worldX - gp.player.worldX + gp.player.screenX;
    return screenX;
  }

  public int getScreenY() {
    int screenY = worldY - gp.player.worldY + gp.player.screenY;
    return screenY;
  }

  public int getLeftX() {
    return worldX + solidArea.x;
  }

  public int getRightX() {
    return worldX + solidArea.x + solidArea.width;
  }

  public int getTopY() {
    return worldY + solidArea.y;
  }

  public int getBottomY() {
    return worldY + solidArea.y + solidArea.height;
  }

  public int getCol() {
    return (worldX + solidArea.x) / gp.tileSize;
  }

  public int getRow() {
    return (worldY + solidArea.y) / gp.tileSize;
  }

 
public int getCenterX() {
    // centro da hitbox
    return worldX + solidArea.x + solidArea.width / 2;
}

public int getCenterY() {
    // centro da hitbox
    return worldY + solidArea.y + solidArea.height / 2;
}


  public void speak() {
  }

  public void facePlayer() {
    switch (gp.player.direction) {
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

  public void startDialogue(Entity entity, int setNum) {
    gp.gameState = gp.dialogueState;
    gp.ui.npc = entity;
    dialogueSet = setNum;
  }

  public void interact() {
  }

  public boolean use(Entity entity) {
    return false;
  }

  public void checkDrop() {
  }

  public void dropItem(Entity droppedItem) {
    for (int i = 0; i < gp.obj[1].length; i++) {
      if (gp.obj[gp.currentMap][i] == null) {
        gp.obj[gp.currentMap][i] = droppedItem;
        gp.obj[gp.currentMap][i].worldX = worldX; // the dead monster's worldX
        gp.obj[gp.currentMap][i].worldY = worldY;
        break;
      }
    }
  }

  public Color getParticleColor() {
    Color color = null;
    return color;
  }

  public int getParticleSize() {
    int size = 0;
    return size;
  }

  public int getParticleSpeed() {
    int speed = 0;
    return speed;
  }

  public int getParticleMaxLife() {
    int maxLife = 0;
    return maxLife;
  }

  public void generatorParticule(Entity generator, Entity target) {
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

    if (type == type_monster && contactPlayer == true) {
      damagePlayer(attack);
    }
  }

  public void update() {
    if (sleep == false) {
      if (knokBack == true) {
        checkCollisio();
        if (collisionOn == true) {
          knokBackCounter = 0;
          knokBack = false;
          speed = defualtSpeed;
        } else if (collisionOn == false) {
          switch (knockBackDirecion) {
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
        knokBackCounter++;
        if (knokBackCounter == 10) {
          knokBackCounter = 0;
          knokBack = false;
          speed = defualtSpeed;
        }
      } else if (attacking == true) {
        attacking();
      } else {
        setAction();
        checkCollisio();

        // IF COLLISION IS FALSE, PLAYER CAN MOVE
        if (collisionOn == false) {
          switch (direction) {
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
        spriteCounter++;
        if (spriteCounter > 24) {

          int maxFrame = 2;

          // Se a entidade tiver 3 frames de walk em alguma direção,
          // usamos 1 → 2 → 3 → 1
          if ((direction.equals("left") && left3 != null) ||
              (direction.equals("right") && right3 != null) ||
              (direction.equals("up") && up3 != null) ||
              (direction.equals("down") && down3 != null)) {
            maxFrame = 3;
          }

          spriteNum++;
          if (spriteNum > maxFrame) {
            spriteNum = 1;
          }

          spriteCounter = 0;
        }

      }

      if (invencible == true) {
        invencibleCounter++;
        if (invencibleCounter > 40) {
          invencible = false;
          invencibleCounter = 0;
        }
      }
      if (shotAvailableCounter < 30) {
        shotAvailableCounter++;
      }
      if (offBalance == true) {
        offBalanceCounter++;
        if (offBalanceCounter > 60) {
          offBalance = false;
          offBalanceCounter = 0;
        }
      }
    }

  }

  public void checkAttackOrNot(int rate, int straight, int horizontal) {
    boolean targetInRange = false;
    int xDis = getXdistance(gp.player);
    int yDis = getYdistance(gp.player);

    switch (direction) {
      case "up":
        if (gp.player.getCenterY() < getCenterY() && yDis < straight && xDis < horizontal) {
          targetInRange = true;
        }
        break;
      case "down":
        if (gp.player.getCenterY() > getCenterY() && yDis < straight && xDis < horizontal) {
          targetInRange = true;
        }
        break;
      case "left":
        if (gp.player.getCenterX() < getCenterX() && xDis < straight && yDis < horizontal) {
          targetInRange = true;
        }
        break;
      case "right":
        if (gp.player.getCenterX() > getCenterX() && xDis < straight && yDis < horizontal) {
          targetInRange = true;
        }
        break;
    }
    if (targetInRange == true) {
      // Check if it initiates an attack
      int i = new Random().nextInt(rate);
      if (i == 0) {
        attacking = true;
        spriteNum = 0;
        shotAvailableCounter = 0;

      }
    }
  }

  public void checkShootOrNot(int rate, int shotInterval) {
    int i = new Random().nextInt(rate);
    if (i == 0 && projectile.alive == false && shotAvailableCounter == shotInterval) {
      projectile.set(worldX, worldY, direction, true, this);
      // CHECK VACANCY
      for (int ii = 0; ii < gp.projectile[1].length; ii++) {
        if (gp.projectile[gp.currentMap][ii] == null) {
          gp.projectile[gp.currentMap][ii] = projectile;
          break;
        }
      }
      shotAvailableCounter = 0;
    }
  }

  public void checkStartChasingOrNot(Entity target, int distance, int rate) {
    if (getTileDistance(target) < distance) {
      int i = new Random().nextInt(rate);
      if (i == 0) {
        onPath = true;
      }
    }
  }

  public void checkStopChasingOrNot(Entity target, int distance, int rate) {
    if (getTileDistance(target) > distance) {
      int i = new Random().nextInt(rate);
      if (i == 0) {
        onPath = false;
      }
    }
  }

  public void getRandomDirection(int interval) {
    actionLockCounter++;

    if (actionLockCounter > interval) {
      Random random = new Random();
      int i = random.nextInt(100) + 1; // pick up number from 1 to 100
      if (i <= 25) {
        direction = "up";
      }
      if (i > 25 && i <= 50) {
        direction = "down";
      }
      if (i > 50 && i <= 75) {
        direction = "left";
      }
      if (i > 75 && i <= 100) {
        direction = "right";
      }
      actionLockCounter = 0;
    }
  }

  public void moveTowardPlayer(int interval) {
    actionLockCounter++;

    if (actionLockCounter > interval) {
      if (getXdistance(gp.player) > getYdistance(gp.player)) {
        if (gp.player.getCenterX() < getCenterX()) {
          direction = "left";
        } else {
          direction = "right";
        }
      } else if (getXdistance(gp.player) < getYdistance(gp.player)) {
        if (gp.player.getCenterY() < getCenterY()) {
          direction = "up";
        } else {
          direction = "down";
        }
      }
      actionLockCounter = 0;
    }
  }

  public String getOppositeDirection(String direction) {
    String oppossiteDirection = "";
    switch (direction) {
      case "up":
        oppossiteDirection = "down";
        break;
      case "down":
        oppossiteDirection = "up";
        break;
      case "left":
        oppossiteDirection = "right";
        break;
      case "right":
        oppossiteDirection = "left";
        break;
    }
    return oppossiteDirection;
  }

  public void attacking() {
    spriteCounter++;

    // 1) Wind-up
    if (spriteCounter <= motion1_duration) {
      spriteNum = 1;
    }

    // 2) Fase ativa do golpe (onde sai o dano)
    if (spriteCounter > motion1_duration && spriteCounter <= motion2_duration) {

      // Se a entidade tiver 3 sprites de ataque, dividimos essa fase em 2 metades:
      // primeira metade = frame 2, segunda metade = frame 3.
      boolean has3AttackFrames = attackUp3 != null || attackDown3 != null ||
          attackLeft3 != null || attackRight3 != null;

      if (has3AttackFrames) {
        int mid = motion1_duration + (motion2_duration - motion1_duration) / 2;
        if (spriteCounter <= mid) {
          spriteNum = 2;
        } else {
          spriteNum = 3;
        }
      } else {
        // Comportamento antigo (Skeleton Lord, player, etc.)
        spriteNum = 2;
      }

      // ======= CÓDIGO ORIGINAL DE HITBOX / DANO =======
      int currentWordX = worldX;
      int currentWordY = worldY;
      int solidAreaWidth = solidArea.width;
      int solidAreaHeight = solidArea.height;

      switch (direction) {
        case "up":
          worldY -= attackArea.height;
          break;
        case "down":
          worldY += attackArea.height;
          break;
        case "left":
          worldX -= attackArea.width;
          break;
        case "right":
          worldX += attackArea.width;
          break;
      }

      solidArea.width = attackArea.width;
      solidArea.height = attackArea.height;

      if (type == type_monster) {
        if (gp.cChecker.checkPlayer(this)) {
          damagePlayer(attack);
        }
      } else {
        int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
        gp.player.damageMonter(monsterIndex, this, attack, currenWeapon.knokBackPower);

        int iTileIndex = gp.cChecker.checkEntity(this, gp.iTile);
        gp.player.damageInteractiveTile(iTileIndex);

        // Só destrói projétil se NÃO for o player usando a zarabatana
        if (!(this == gp.player
            && gp.player.currenWeapon != null
            && gp.player.currenWeapon.type == type_zarabatana)) {

          int projectileIndex = gp.cChecker.checkEntity(this, gp.projectile);
          gp.player.damageProjectile(projectileIndex);
        }

      }

      worldX = currentWordX;
      worldY = currentWordY;
      solidArea.width = solidAreaWidth;
      solidArea.height = solidAreaHeight;
    }

    // 3) Fim do ataque
    if (spriteCounter > motion2_duration) {
      spriteNum = 1;
      spriteCounter = 0;
      attacking = false;
    }
  }

  public void damagePlayer(int attack) {
    if (gp.player.invencible == false) {
      int damage = attack - gp.player.defense;
      // Get an opposite direction of this attacker
      String canGuardDirection = getOppositeDirection(direction);
      if (gp.player.guarding == true && gp.player.direction.equals(canGuardDirection)) {
        // Prarry
        if (gp.player.guardCounter < 10) {
          damage = 0;
          gp.playeSE(16);
          setknokBack(this, gp.player, knokBackPower);
          offBalance = true;
          spriteCounter = -60;
        } else {
          // normal guard
          damage /= 3;
          gp.playeSE(15);
        }
      } else {
        // Not guarding
        gp.playeSE(6);
        if (damage < 0) {
          damage = 1;
        }
      }
      if (damage != 0) {
        gp.player.transparent = true;
        setknokBack(gp.player, this, knokBackPower);
      }
      setknokBack(gp.player, this, knokBackPower);
      gp.player.life -= damage;
      gp.player.invencible = true;
    }
  }

  public void setknokBack(Entity target, Entity attacker, int knokBackPower) {
    this.attacker = attacker;
    target.knockBackDirecion = attacker.direction;
    target.speed += knokBackPower;
    target.knokBack = true;

  }

  public boolean inCamera() {
    boolean inCamera = false;

    if (worldX + gp.tileSize * 5 > gp.player.worldX - gp.player.screenX &&
        worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
        worldY + gp.tileSize * 5 > gp.player.worldY - gp.player.screenY &&
        worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
      inCamera = true;
    }
    return inCamera;

  }
    public void draw(Graphics2D g2) {
        BufferedImage image = null;
        int screenX = getScreenX();
        int screenY = getScreenY();

        // Offsets manuais para corrigir sprites de ataque 300x300
        int offsetX = 0;
        int offsetY = 0;

        switch (direction) {
            case "up":
                if (attacking) {
                    offsetY = -80;  // puxa pra cima (ajuste se precisar)
                    if (spriteNum == 1) image = attackUp1;
                    else if (spriteNum == 2) image = attackUp2;
                } else {
                    if (spriteNum == 1) image = up1;
                    else if (spriteNum == 2) image = up2;
                    else if (spriteNum == 3 && up3 != null) image = up3;
                }
                break;

            case "down":
                if (attacking) {
                    offsetY = -60;  // ESSENCIAL: evita descer no ataque down
                    offsetX = -50;  // centraliza horizontalmente
                    if (spriteNum == 1) image = attackDown1;
                    else if (spriteNum == 2) image = attackDown2;
                } else {
                    if (spriteNum == 1) image = down1;
                    else if (spriteNum == 2) image = down2;
                    else if (spriteNum == 3 && down3 != null) image = down3;
                }
                break;

            case "left":
                if (attacking) {
                    offsetX = -100; // compensa o braço esticado pra esquerda
                    offsetY = -60;
                    if (spriteNum == 1) image = attackLeft1;
                    else if (spriteNum == 2) image = attackLeft2;
                    else if (spriteNum == 3 && attackLeft3 != null) image = attackLeft3;
                } else {
                    if (spriteNum == 1) image = left1;
                    else if (spriteNum == 2) image = left2;
                    else if (spriteNum == 3 && left3 != null) image = left3;
                }
                break;

            case "right":
                if (attacking) {
                    offsetX = -20; // compensa o braço esticado pra esquerda
                    offsetY = -60;
                    if (spriteNum == 1) image = attackRight1;
                    else if (spriteNum == 2) image = attackRight2;
                    else if (spriteNum == 3 && attackRight3 != null) image = attackRight3;
                } else {
                    if (spriteNum == 1) image = right1;
                    else if (spriteNum == 2) image = right2;
                    else if (spriteNum == 3 && right3 != null) image = right3;
                }
                break;
        }

        // Aplicar offsets apenas no ataque
        if (attacking) {
            screenX += offsetX;
            screenY += offsetY;
        }

        // Efeito de invencibilidade
        if (invencible) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
        }

        // DESENHA UMA ÚNICA VEZ (corrige bug de duplicação)
        g2.drawImage(image, screenX, screenY, null);

        // Reset alpha
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

        // HP Bar e dying (se necessário)
        if (invencible) {
            hpBarOn = true;
            hpBarCounter = 0;
        }
        if (dying) {
            dyingAnimation(g2);
        }
    }

  public void dyingAnimation(Graphics2D g2) {
    dyingCounter++;
    int i = 5;
    if (dyingCounter <= i) {
      changeAlpha(g2, 0f);
    }
    if (dyingCounter > i && dyingCounter <= i * 2) {
      changeAlpha(g2, 1f);
    }
    if (dyingCounter > i * 2 && dyingCounter <= i * 3) {
      changeAlpha(g2, 0f);
    }
    if (dyingCounter > i * 3 && dyingCounter <= i * 4) {
      changeAlpha(g2, 1f);
    }
    if (dyingCounter > i * 4 && dyingCounter <= i * 5) {
      changeAlpha(g2, 0f);
    }
    if (dyingCounter > i * 5 && dyingCounter <= i * 6) {
      changeAlpha(g2, 1f);
    }
    if (dyingCounter > i * 6 && dyingCounter <= i * 7) {
      changeAlpha(g2, 0f);
    }
    if (dyingCounter > i * 7 && dyingCounter <= i * 8) {
      changeAlpha(g2, 1f);
    }
    if (dyingCounter > i * 8) {
      alive = false;
    }
  }


  public void changeAlpha(Graphics2D g2, float alphaValue) {
    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue));
  }

    public BufferedImage setup(String imagePath, int width, int height) {
        try {
            // Garante "/" no início
            if (!imagePath.startsWith("/")) {
                imagePath = "/" + imagePath;
            }

            // Garante extensão .png
            String finalPath = imagePath + ".png";

            // Carrega a imagem do resource
            java.net.URL url = getClass().getResource(finalPath);

            if (url == null) {
                System.out.println("❌ ERRO: imagem não encontrada em: " + finalPath);
                return null;
            }

            BufferedImage original = javax.imageio.ImageIO.read(url);

            // Cria nova imagem redimensionada
            BufferedImage scaledImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = scaledImage.createGraphics();

            g2.drawImage(original, 0, 0, width, height, null);
            g2.dispose();

            return scaledImage;

        } catch (Exception e) {
            System.out.println("❌ Falha ao carregar imagem: " + imagePath);
            e.printStackTrace();
            return null;
        }
    }


  public void searchPath(int goalCol, int goalRow) {
    int startCol = (worldX + solidArea.x) / gp.tileSize;
    int startRow = (worldY + solidArea.y) / gp.tileSize;

    gp.pFinder.setNode(startCol, startRow, goalCol, goalRow);
    if (gp.pFinder.search() == true) {
      int nextX = gp.pFinder.pathList.get(0).col * gp.tileSize;
      int nextY = gp.pFinder.pathList.get(0).row * gp.tileSize;

      int enLeftX = worldX + solidArea.x;
      int enRighX = worldX + solidArea.x + solidArea.width;
      int enTopY = worldY + solidArea.y;
      int enBottonY = worldY + solidArea.y + solidArea.height;

      if (enTopY > nextY && enLeftX >= nextX && enRighX < nextX + gp.tileSize) {
        direction = "up";
      } else if (enTopY < nextY && enLeftX >= nextX && enRighX < nextX + gp.tileSize) {
        direction = "down";
      } else if (enTopY >= nextY && enBottonY < nextY + gp.tileSize) {
        // left or right
        if (enLeftX > nextX) {
          direction = "left";
        }
        if (enLeftX < nextX) {
          direction = "right";
        }
      } else if (enTopY > nextY && enLeftX > nextX) {
        // up or left
        direction = "up";
        checkCollisio();
        if (collisionOn == true) {
          direction = "left";
        }
      } else if (enTopY > nextY && enLeftX < nextX) {
        // up or right
        direction = "up";
        checkCollisio();
        if (collisionOn == true) {
          direction = "right";
        }
      } else if (enTopY < nextY && enLeftX > nextX) {
        // down or left
        direction = "down";
        checkCollisio();
        if (collisionOn == true) {
          direction = "left";
        }
      } else if (enTopY < nextY && enLeftX < nextX) {
        // down or right
        direction = "down";
        checkCollisio();
        if (collisionOn == true) {
          direction = "right";
        }
      }
      // IF reaches the goal, stop the search
      // int nextCol = gp.pFinder.pathList.get(0).col;
      // int nextRol = gp.pFinder.pathList.get(0).row;
      // if(nextCol == goalCol && nextRow == goalRow){
      // OnPath = false;
      // }
    }
  }

  public int getDetected(Entity user, Entity target[][], String targetName) {
    int index = 999;

    // check the surrounding object
    int nextWordX = user.getLeftX();
    int nextWordY = user.getTopY();

    switch (user.direction) {
      case "up":
        nextWordY = user.getTopY() - 1;
        break;
      case "down":
        nextWordY = user.getBottomY() + 1;
        break;
      case "left":
        nextWordX = user.getLeftX() - 1;
        break;
      case "right":
        nextWordX = user.getRightX() + 1;
        break;
    }
    int col = nextWordX / gp.tileSize;
    int row = nextWordY / gp.tileSize;

    for (int i = 0; i < target[1].length; i++) {
      if (target[gp.currentMap][i] != null) {
        if (target[gp.currentMap][i].getCol() == col
            && target[gp.currentMap][i].getRow() == row && target[gp.currentMap][i].name.equals(targetName)) {
          index = i;
          break;
        }
      }
    }
    return index;
  }
}
