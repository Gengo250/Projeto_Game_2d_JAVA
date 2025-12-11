package entity;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.Color;

import main.GamePanel;
import main.KeyHandler;
import object.OBJ_Dardo;
import object.OBJ_Key;
import object.OBJ_Lantern;
import object.OBJ_Paper;
import object.OBJ_Paper_mercador;
import object.OBJ_Shield_Wood;
import object.OBJ_Sword_Normal;
import object.OBJ_Tent;
import object.OBJ_Ugabuga;
import object.OBJ_Zarabatana;

public class Player extends Entity {

  GamePanel gp;
  KeyHandler keyH;

  public final int screenX;
  public final int screenY;
  int standCounter = 0;
  public boolean attackCanceled = false;
  public boolean lightUpdated = false;
  public boolean parryActive = false;
  public int parryCounter = 0;
  public boolean ugabugaActive = false;
  public int ugabugaCounter = 0;
  public int ugabugaDuration = 60 * 15; // 15 segundos a 60 FPS
  // Buffer reutilizável para a aura Ugabuga
  private BufferedImage ugabugaAuraBuf;
  private int ugabugaAuraW = -1, ugabugaAuraH = -1;

  private boolean wasGodModeOn = false;
    // --- RECOMPENSA DE BOSS (aplicada só depois da cutscene) ---
  public int pendingBossExp = 0;
  public String pendingBossKillName = null;
  public boolean bossRewardPending = false;


  public Player(GamePanel gp, KeyHandler keyH) {
    super(gp);

    this.gp = gp;
    this.keyH = keyH;

    screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
    screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

    // SOLID AREA
    solidArea = new Rectangle();
    solidArea.x = 8;
    solidArea.y = 16;
    solidAreaDefaultX = solidArea.x;
    solidAreaDefaultY = solidArea.y;
    solidArea.width = 32;
    solidArea.height = 32;

    setDefaultValues();
  }

  public void setDefaultValues() {

    // casa
    gp.currentMap = 1;
    worldX = gp.tileSize * 26;
    worldY = gp.tileSize * 10;

    // Dungeon B2
    // gp.currentMap = 3;
    // worldX = gp.tileSize*25;
    // worldY = gp.tileSize*29;

    defualtSpeed = 4;
    speed = defualtSpeed;
    direction = "down";

    // PLAYER STATUS
    level = 1;
    maxLife = 18;
    life = maxLife;
    maxMana = 4;
    mana = maxMana;
    ammo = 20;
    strength = 1; // the more strenght he has, the more damage he gives ---> para testes colocar:
                   // 20
    dexterity = 1; // the more dexterity he has, the less damage he receives
    exp = 0;
    nextLevelExp = 5;
    pendingBossExp = 0;
    pendingBossKillName = null;
    bossRewardPending = false;
    coin = 1000;
    currenWeapon = new OBJ_Sword_Normal(gp);
    currentyShield = new OBJ_Shield_Wood(gp);
    currentyLight = null;
    projectile = new OBJ_Dardo(gp);
    attack = getAttack(); // the total attack value is decided by strength and weapon
    defense = getDefense(); // the total defense value is decided by dexterity and shield
    ugabugaActive = false;
    ugabugaCounter = 0;

    getImage();
    getAttckImage();
    getGuardImage();
    setItems();
    setDialogue();
  }

  public void setDefaultPositions() {
    switch (gp.currentMap) {
      case 0:
        worldX = gp.tileSize * 179;
        worldY = gp.tileSize * 119;
        break; // Outside
      case 1:
        worldX = gp.tileSize * 12;
        worldY = gp.tileSize * 13;
        break; // Indoor (exemplo)
      case 2:
        worldX = gp.tileSize * 71;
        worldY = gp.tileSize * 94;
        break; // caverna
      case 3:
        worldX = gp.tileSize * 25;
        worldY = gp.tileSize * 29;
        break; // Dungeon/Boss
      // adicione outros mapas se precisar
    }
    direction = "down";
  }

  public void setDialogue() {
    dialogues[0][0] = "You are level " + level + " now!\n"
        + "You feel stronger!";
  }

  public void restoreStatus() {

    life = maxLife;
    mana = maxMana;
    speed = defualtSpeed;
    invencible = false;
    transparent = false;
    attacking = false;
    guarding = false;
    knokBack = false;
    lightUpdated = true;
    ugabugaActive = false;
    ugabugaCounter = 0;
  }

  public void setItems() {
    inventory.clear();
    inventory.add(currenWeapon);
    inventory.add(currentyShield);
    inventory.add(new OBJ_Key(gp));
    inventory.add(new OBJ_Lantern(gp));
    inventory.add(new OBJ_Zarabatana(gp));
    inventory.add(new OBJ_Ugabuga(gp));
    inventory.add(new OBJ_Paper(gp));
    inventory.add(new OBJ_Paper_mercador(gp));
    inventory.add(new OBJ_Tent(gp));
  }

  public int getAttack() {
    attackArea = currenWeapon.attackArea;
    motion1_duration = currenWeapon.motion1_duration;
    motion2_duration = currenWeapon.motion2_duration;
    return attack = strength * currenWeapon.attackValue;
  }

  public int getDefense() {
    return defense = dexterity * currentyShield.defenseValue;
  }

  public int getCurrentWeaponSlot() {
    int currentWeaponSlot = 0;
    for (int i = 0; i < inventory.size(); i++) {
      if (inventory.get(i) == currenWeapon) {
        currentWeaponSlot = i;
      }
    }
    return currentWeaponSlot;
  }

  public int getCurrentShieldSlot() {
    int currentShieldSlot = 0;
    for (int i = 0; i < inventory.size(); i++) {
      if (inventory.get(i) == currentyShield) {
        currentShieldSlot = i;
      }
    }
    return currentShieldSlot;
  }

  public void getImage() {
    up1 = setup("/res/player/player_indio/indio_up_1", gp.tileSize, gp.tileSize);
    up2 = setup("/res/player/player_indio/indio_up_2", gp.tileSize, gp.tileSize);
    down1 = setup("/res/player/player_indio/indio_down_1", gp.tileSize, gp.tileSize);
    down2 = setup("/res/player/player_indio/indio_down_2", gp.tileSize, gp.tileSize);
    left1 = setup("/res/player/player_indio/indio_left_1", gp.tileSize, gp.tileSize);
    left2 = setup("/res/player/player_indio/indio_left_2", gp.tileSize, gp.tileSize);
    right1 = setup("/res/player/player_indio/indio_right_1", gp.tileSize, gp.tileSize);
    right2 = setup("/res/player/player_indio/indio_right_2", gp.tileSize, gp.tileSize);
  }

  public void getSleepImage(BufferedImage image) {
    up1 = image;
    up2 = image;
    down1 = image;
    down2 = image;
    left1 = image;
    left2 = image;
    right1 = image;
    right2 = image;
  }

  public void getAttckImage() {
    if (currenWeapon.type == type_sword) {
      attackUp1 = setup("/res/player/player_indio/indio_lanca_up", gp.tileSize, gp.tileSize * 2);
      attackUp2 = setup("/res/player/player_indio/indio_lanca_up", gp.tileSize, gp.tileSize * 2);
      attackDown1 = setup("/res/player/player_indio/indio_lanca_down", gp.tileSize, gp.tileSize * 2);
      attackDown2 = setup("/res/player/player_indio/indio_lanca_down", gp.tileSize, gp.tileSize * 2);
      attackLeft1 = setup("/res/player/player_indio/indio_lanca_left", gp.tileSize * 2, gp.tileSize);
      attackLeft2 = setup("/res/player/player_indio/indio_lanca_left", gp.tileSize * 2, gp.tileSize);
      attackRight1 = setup("/res/player/player_indio/indio_lanca_right", gp.tileSize * 2, gp.tileSize);
      attackRight2 = setup("/res/player/player_indio/indio_lanca_right", gp.tileSize * 2, gp.tileSize);
    }
    if (currenWeapon.type == type_axe) {
      attackUp1 = setup("/res/player/player_indio/indio_faca_up", gp.tileSize, gp.tileSize * 2);
      attackUp2 = setup("/res/player/player_indio/indio_faca_up", gp.tileSize, gp.tileSize * 2);
      attackDown1 = setup("/res/player/player_indio/indio_faca_down", gp.tileSize, gp.tileSize * 2);
      attackDown2 = setup("/res/player/player_indio/indio_faca_down", gp.tileSize, gp.tileSize * 2);
      attackLeft1 = setup("/res/player/player_indio/indio_faca_left_1", gp.tileSize * 2, gp.tileSize);
      attackLeft2 = setup("/res/player/player_indio/indio_faca_left_2", gp.tileSize * 2, gp.tileSize);
      attackRight1 = setup("/res/player/player_indio/indio_faca_right_1", gp.tileSize * 2, gp.tileSize);
      attackRight2 = setup("/res/player/player_indio/indio_faca_right_2", gp.tileSize * 2, gp.tileSize);
    }
    if (currenWeapon.type == type_pickaxe) {
      attackUp1 = setup("/res/player/boy_pick_up_1", gp.tileSize, gp.tileSize * 2);
      attackUp2 = setup("/res/player/boy_pick_up_2", gp.tileSize, gp.tileSize * 2);
      attackDown1 = setup("/res/player/boy_pick_down_1", gp.tileSize, gp.tileSize * 2);
      attackDown2 = setup("/res/player/boy_pick_down_2", gp.tileSize, gp.tileSize * 2);
      attackLeft1 = setup("/res/player/boy_pick_left_1", gp.tileSize * 2, gp.tileSize);
      attackLeft2 = setup("/res/player/boy_pick_left_2", gp.tileSize * 2, gp.tileSize);
      attackRight1 = setup("/res/player/boy_pick_right_1", gp.tileSize * 2, gp.tileSize);
      attackRight2 = setup("/res/player/boy_pick_right_2", gp.tileSize * 2, gp.tileSize);
    }
    if (currenWeapon.type == type_zarabatana) {
      attackUp1 = setup("/res/player/player_indio/indio_zarabatana_up", gp.tileSize, gp.tileSize);
      attackUp2 = setup("/res/player/player_indio/indio_zarabatana_up", gp.tileSize, gp.tileSize);
      attackDown1 = setup("/res/player/player_indio/indio_zarabatana_down", gp.tileSize, gp.tileSize);
      attackDown2 = setup("/res/player/player_indio/indio_zarabatana_down", gp.tileSize, gp.tileSize);
      attackLeft1 = setup("/res/player/player_indio/indio_zarabatana_left", gp.tileSize * 2, gp.tileSize);
      attackLeft2 = setup("/res/player/player_indio/indio_zarabatana_left", gp.tileSize * 2, gp.tileSize);
      attackRight1 = setup("/res/player/player_indio/indio_zarabatana_right", gp.tileSize * 2, gp.tileSize);
      attackRight2 = setup("/res/player/player_indio/indio_zarabatana_right", gp.tileSize * 2, gp.tileSize);
    }
  }

  public void getGuardImage() {

    guardUp = setup("/res/player/player_indio/indio_block_up", gp.tileSize, gp.tileSize);
    guardDown = setup("/res/player/player_indio/indio_block_down", gp.tileSize, gp.tileSize);
    guardLeft = setup("/res/player/player_indio/indio_block_left", gp.tileSize, gp.tileSize);
    guardRight = setup("/res/player/player_indio/indio_block_right", gp.tileSize, gp.tileSize);

  }

  public void getParryImage() {

    parryUp = setup("/res/player/player_indio/indio_parry_up", gp.tileSize, gp.tileSize);
    parryDown = setup("/res/player/player_indio/indio_parry_down", gp.tileSize, gp.tileSize);
    parryLeft = setup("/res/player/player_indio/indio_parry_left", gp.tileSize, gp.tileSize);
    parryRight = setup("/res/player/player_indio/indio_parry_right", gp.tileSize, gp.tileSize);

  }

  public void getUgubugaImage() {

    up1 = setup("/res/player/player_indio/indio_ugabuga_down_1", gp.tileSize, gp.tileSize);
    up2 = setup("/res/player/player_indio/indio_ugabuga_down_2", gp.tileSize, gp.tileSize);
    down1 = setup("/res/player/player_indio/indio_ugabuga_up_1", gp.tileSize, gp.tileSize);
    down2 = setup("/res/player/player_indio/indio_ugabuga_up_2", gp.tileSize, gp.tileSize);
    left1 = setup("/res/player/player_indio/indio_ugabuga_left_1", gp.tileSize, gp.tileSize);
    left2 = setup("/res/player/player_indio/indio_ugabuga_left_2", gp.tileSize, gp.tileSize);
    right1 = setup("/res/player/player_indio/indio_ugabuga_right_1", gp.tileSize, gp.tileSize);
    right2 = setup("/res/player/player_indio/indio_ugabuga_right_2", gp.tileSize, gp.tileSize);
  }

  public void update() {

    // ===== GOD MODE BUFFS =====
    boolean godMode = keyH.godModeOn;

    if (godMode) {
      // status turbinado
      attack = 50;                  // dano fixo 50
      speed = defualtSpeed * 2;     // velocidade aumentada

      // imortalidade
      invencible = true;
      transparent = false;
      invencibleCounter = 0;
      life = maxLife;
      mana = maxMana;

      // dardos (e qualquer recurso) "infinitos"
      ammo = 999;
    } else if (wasGodModeOn) {
      // acabou de desligar o god mode -> volta pro normal
      attack = getAttack();         // recalcula ataque normal
      speed = defualtSpeed;
      invencible = false;
      transparent = false;
      invencibleCounter = 0;
    }

    wasGodModeOn = godMode;
    if (knokBack == true) {
      collisionOn = false;
      gp.cChecker.checkTile(this);
      gp.cChecker.checkObject(this, true);
      gp.cChecker.checkEntity(this, gp.npc);
      gp.cChecker.checkEntity(this, gp.monster);
      gp.cChecker.checkEntity(this, gp.iTile);

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
    } else if (keyH.spacePressed == true) {
      guarding = true;
      guardCounter++;
    } else if (keyH.upPressed == true || keyH.downPressed == true ||
        keyH.leftPressed == true || keyH.rightPressed == true || keyH.enterPressed == true) {

      if (keyH.upPressed == true) {
        direction = "up";
      } else if (keyH.downPressed == true) {
        direction = "down";
      } else if (keyH.leftPressed == true) {
        direction = "left";
      } else if (keyH.rightPressed == true) {
        direction = "right";
      }

      collisionOn = false;
      gp.cChecker.checkTile(this);

      int objIndex = gp.cChecker.checkObject(this, true);
      int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
      int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);

      // IMPORTANTÍSSIMO: checar iTile para marcar collisionOn
      gp.cChecker.checkEntity(this, gp.iTile); // retorno ignorado de propósito

      pickUpObject(objIndex); // coleta/usa item
      interactNPC(npcIndex); // fala com NPC se ENTER
      contactMonster(monsterIndex); // dano por contato, etc.

      // CHECK EVENT
      if (!invencible || keyH.godModeOn){
         gp.eHandler.checkEvent();
      }
      
      // IF COLLISION IS FALSE, PLAYER CAN MOVE
      if (collisionOn == false && gp.keyH.enterPressed == false) {
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
      if (keyH.enterPressed == true && attackCanceled == false && !ugabugaActive) {

        // Som de ataque (vale pra qualquer arma)
        gp.playeSE(7);

        // Começa animação de ataque (para TODAS as armas, inclusive zarabatana)
        attacking = true;
        spriteCounter = 0;

        // Se a arma equipada for a zarabatana, ATIRA o dardo
        if (keyH.enterPressed == true && attackCanceled == false && !ugabugaActive) {

          // Som de ataque (vale pra qualquer arma)
          gp.playeSE(7);

          // Começa animação de ataque (para TODAS as armas, inclusive zarabatana)
          attacking = true;
          spriteCounter = 0;

          // Se a arma equipada for a zarabatana, ATIRA o dardo SEM DELAY
          if (currenWeapon != null && currenWeapon.type == type_zarabatana) {

            // cria um NOVO dardo a cada ENTER
            OBJ_Dardo dardo = new OBJ_Dardo(gp);

            if (dardo.haveResource(this) == true) {

              int projX = worldX;
              int projY = worldY;

              // joga o projétil um pouco pra frente do índio
              switch (direction) {
                case "up":
                  projY -= gp.tileSize / 2;
                  break;
                case "down":
                  projY += gp.tileSize / 2;
                  break;
                case "left":
                  projX -= gp.tileSize / 2;
                  break;
                case "right":
                  projX += gp.tileSize / 2;
                  break;
              }

              dardo.set(projX, projY, direction, true, this);
              dardo.subtractResouce(this); // aqui ele gasta mana ou ammo, do jeito que você configurou no OBJ_Dardo

              // coloca o dardo em um slot livre do array de projéteis
              for (int i = 0; i < gp.projectile[1].length; i++) {
                if (gp.projectile[gp.currentMap][i] == null) {
                  gp.projectile[gp.currentMap][i] = dardo;
                  break;
                }
              }
            }

          } else {
            // Outras armas = só ataque corpo a corpo normal
            shotAvailableCounter = 0;
          }
        }

      }

      attackCanceled = false;
      gp.keyH.enterPressed = false;
      guarding = false;
      guardCounter = 0;

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
      guarding = false;
      guardCounter = 0;
    }
    if (gp.keyH.shotKeyPressed == true && !ugabugaActive && projectile.alive == false
        && shotAvailableCounter == 30 && projectile.haveResource(this) == true) {
      // SET DEFUALT COORDINATES DIRECTION AND USER
      projectile.set(worldX, worldY, direction, true, this);

      // SUBTRACT THE COST (MANA, AMMO, ETC)
      projectile.subtractResouce(this);

      // CHECK VACANCY
      for (int i = 0; i < gp.projectile[1].length; i++) {
        if (gp.projectile[gp.currentMap][i] == null) {
          gp.projectile[gp.currentMap][i] = projectile;
          break;
        }
      }

      shotAvailableCounter = 0;

      gp.playeSE(10);
    }

    // This needs to be outside of key if statement!
    if (invencible == true) {

      // Se NÃO estiver em modo Ugabuga, usa o timer normal de i-frame
      if (!ugabugaActive && !keyH.godModeOn) {
        invencibleCounter++;
        if (invencibleCounter > 60) {
          invencible = false;
          transparent = false;
          invencibleCounter = 0;
        }
      }
    }
    if (shotAvailableCounter < 30) {
      shotAvailableCounter++;
    }
    if (parryActive) {
      parryCounter++;
      if (parryCounter > 15) {
        parryActive = false;
        parryCounter = 0;
      }
    }
    // ===== MODO UGABUGA =====
    if (ugabugaActive) {

      ugabugaCounter++;

      // a cada 10 frames (6x por segundo) aplica dano em área
      if (ugabugaCounter % 10 == 0) {
        applyUgabugaAreaDamage();
      }

      // fim dos 15 segundos
      if (ugabugaCounter >= ugabugaDuration) {
        endUgabuga();
      }
    }
    if (life > maxLife) {
      life = maxLife;
    }
    if (mana > maxMana) {
      mana = maxMana;
    }
    if (!keyH.godModeOn && !invencible && life <= 0) {
      gp.gameState = gp.gameOverState;
      gp.ui.commandNum = 0;
      gp.playeSE(12);
    }
  }

  public void pickUpObject(int i) {
    if (i != 999) {
      // PICKUP ONLY ITEMS
      if (gp.obj[gp.currentMap][i].type == type_pickupOnly) {
        gp.obj[gp.currentMap][i].use(this);
        gp.obj[gp.currentMap][i] = null;
      }
      // OBSTACLE
      else if (gp.obj[gp.currentMap][i].type == type_obstacle) {
        if (keyH.enterPressed == true) {
          attackCanceled = true;
          gp.obj[gp.currentMap][i].interact();
        }
      }
      // INVENTORY ITEMS
      else {
        String text;
        if (canObtainItem(gp.obj[gp.currentMap][i]) == true) {
          gp.playeSE(1);
          text = "Got a " + gp.obj[gp.currentMap][i].name + "!";
        } else {
          text = "Tou cannot varry any more";
        }
        gp.ui.addMessage(text);
        gp.obj[gp.currentMap][i] = null;
      }
    }
  }

  public void interactNPC(int i) {
    if (i != 999) {
      if (gp.keyH.enterPressed == true) {

        attackCanceled = true;
        gp.npc[gp.currentMap][i].speak();
      }
      gp.npc[gp.currentMap][i].move(direction);
    }
  }

   public void contactMonster(int i) {
    if (i != 999) {
      // Em God Mode o contato não causa dano
      if (!keyH.godModeOn && invencible == false && gp.monster[gp.currentMap][i].dying == false) {
        gp.playeSE(6);

        int damage = gp.monster[gp.currentMap][i].attack - defense;
        if (damage < 1) {
          damage = 1;
        }
        life -= damage;
        invencible = true;
        transparent = true;
      }
    }
  }


    public void damageMonter(int i, Entity attacker, int attack, int knokBackPower) {
    if (i != 999) {
      if (gp.monster[gp.currentMap][i].invencible == false) {
        gp.playeSE(5);

        if (knokBackPower > 0) {
          setknokBack(gp.monster[gp.currentMap][i], attacker, knokBackPower);
        }
        if (gp.monster[gp.currentMap][i].offBalance == true) {
          attack *= 5;
        }
        int damage = attack - gp.monster[gp.currentMap][i].defense;
        if (damage < 0) {
          damage = 0;
        }
        gp.monster[gp.currentMap][i].life -= damage;
        gp.ui.addMessage(damage + " damage!");
        gp.monster[gp.currentMap][i].invencible = true;
        gp.monster[gp.currentMap][i].damageReaction();

        // --- MORTE DO MONSTRO ---
        // --- MORTE DO MONSTRO ---
if (gp.monster[gp.currentMap][i].life <= 0) {

    Entity killed = gp.monster[gp.currentMap][i];

    // CASO ESPECIAL: BOSS MACACO → ativa cutscene de morte
    if (killed.boss && "Macaco".equals(killed.name)) {

        // evita animação de morte padrão
        killed.life = 1;
        killed.dying = false;

        // garante que ele não fique transparente ou piscando
        killed.invencible = false;
        killed.transparent = false;

        // guarda o XP para aplicar depois da cutscene
        pendingBossExp += killed.exp;
        pendingBossKillName = killed.name;
        bossRewardPending = true;

        // para a música da batalha antes da transição de tela
        gp.stopMusic();

        // entra no modo cutscene de morte do macaco
        if (gp.csManager.sceneNum != gp.csManager.macacoDeath) {
            gp.csManager.sceneNum = gp.csManager.macacoDeath;
            gp.csManager.scenePhase = 0;
            gp.gameState = gp.cutsceneState;
        }

    } else {
        // comportamento normal para qualquer outro monstro
        killed.dying = true;
        gp.ui.addMessage("killed the " + killed.name + "!");
        gp.ui.addMessage("EXP " + killed.exp);
        exp += killed.exp;
        checkLevelUp();
    }
}

      }
    }
  }

  public void damageInteractiveTile(int i) {
    if (i != 999 && gp.iTile[gp.currentMap][i].destructible == true
        && gp.iTile[gp.currentMap][i].isCorrectItem(this) == true && gp.iTile[gp.currentMap][i].invencible == false) {

      gp.iTile[gp.currentMap][i].playSE();
      gp.iTile[gp.currentMap][i].life--;
      gp.iTile[gp.currentMap][i].invencible = true;

      // Generate particle
      generatorParticule(gp.iTile[gp.currentMap][i], gp.iTile[gp.currentMap][i]);

      if (gp.iTile[gp.currentMap][i].life == 0) {
        // gp.iTile[gp.currentMap][i].checkDrop(); --> idea para usar
        gp.iTile[gp.currentMap][i] = gp.iTile[gp.currentMap][i].getDestroyedForm();
      }
    }
  }

  public void damageProjectile(int i) {
    if (i != 999) {

      Entity entity = gp.projectile[gp.currentMap][i];

      // Garante que é um projétil
      if (entity instanceof Projectile) {
        Projectile projectile = (Projectile) entity;

        // Só destrói se NÃO for um projétil disparado pelo próprio player
        if (projectile.user != this) {
          projectile.alive = false;
          generatorParticule(projectile, projectile);
        }
      }
    }
  }

  public void checkLevelUp() {
    boolean leveled = false;

    while (exp >= nextLevelExp) {
      // consome o XP do nível atual
      exp -= nextLevelExp;

      // sobe de nível
      level++;

      // escala o requisito (mantive sua lógica de dobrar)
      nextLevelExp = nextLevelExp * 2;

      // bônus de atributos
      maxLife += 6;
      strength++;
      dexterity++;
      

      leveled = true;
    }

    if (leveled) {
      // recalcula status só uma vez ao final
      attack = getAttack();
      defense = getDefense();

      gp.playeSE(8);
      gp.gameState = gp.dialogueState;
      setDialogue();
      startDialogue(this, 0);
    }
  }

    // Aplica o XP que ficou "guardado" para bosses com cutscene de morte
  public void resolvePendingBossRewards() {
    if (!bossRewardPending) return;

    if (pendingBossKillName != null) {
      gp.ui.addMessage("killed the " + pendingBossKillName + "!");
    }
    gp.ui.addMessage("EXP " + pendingBossExp);

    exp += pendingBossExp;
    checkLevelUp();

    // limpa estado
    bossRewardPending = false;
    pendingBossExp = 0;
    pendingBossKillName = null;
  }


  public void startUgabuga() {
    ugabugaActive = true;
    ugabugaCounter = 0;

    invencible = true; // fica invencível
    transparent = false; // não precisa ficar piscando
    invencibleCounter = 0; // o timer normal de i-frame fica travado

    attacking = false;
    guarding = false;
    parryActive = false;

    getUgubugaImage(); // troca sprites de movimento
    gp.ui.addMessage("UGABUGA!!!");
  }

  public void endUgabuga() {
    ugabugaActive = false;
    ugabugaCounter = 0;

    // volta pro comportamento normal
    invencible = false;
    transparent = false;
    invencibleCounter = 0;

    // recarrega sprites normais
    getImage();
    getAttckImage();
    getGuardImage();
  }

   private void applyUgabugaAreaDamage() {

    int raio = gp.tileSize * 2; // ~2 tiles de alcance

    // dano fixo do UGABUGA
    // em God Mode ele fica ainda mais forte
    int ugaDamage = keyH.godModeOn ? 50 : 25;

    for (int i = 0; i < gp.monster[gp.currentMap].length; i++) {
      Entity m = gp.monster[gp.currentMap][i];

      if (m == null || m.dying || m.invencible)
        continue;

      int centerMx = m.worldX + m.solidArea.x + m.solidArea.width / 2;
      int centerMy = m.worldY + m.solidArea.y + m.solidArea.height / 2;

      int centerPx = worldX + solidArea.x + solidArea.width / 2;
      int centerPy = worldY + solidArea.y + solidArea.height / 2;

      int dx = centerMx - centerPx;
      int dy = centerMy - centerPy;

      int dist2 = dx * dx + dy * dy;
      if (dist2 <= raio * raio) {
        // DANO FIXO EM ÁREA DO UGABUGA (independente da arma/attack do player)
        damageMonter(i, this, ugaDamage, 0);
      }
    }
  }


  public void selectItem() {
    int itemIndex = gp.ui.getItemIndexOnSlot(gp.ui.playerslotCol, gp.ui.playerslotRow);
    if (itemIndex < inventory.size()) {
      Entity selectedItem = inventory.get(itemIndex);
      if (selectedItem.type == type_sword || selectedItem.type == type_axe || selectedItem.type == type_pickaxe
          || selectedItem.type == type_zarabatana) {
        currenWeapon = selectedItem;
        attack = getAttack();
        getAttckImage();
      }
      if (selectedItem.type == type_shield) {
        currentyShield = selectedItem;
        defense = getDefense();
      }
      if (selectedItem.type == type_light) {
        if (currentyLight == selectedItem) {
          currentyLight = null;
        } else {
          currentyLight = selectedItem;
        }
        lightUpdated = true;
      }
      if (selectedItem.type == type_consumable) {
        if (selectedItem.use(this) == true) {
          if (selectedItem.amount > 1) {
            selectedItem.amount--;
          } else {
            inventory.remove(itemIndex);
          }
        }
      }
    }
  }

  public int searchItemInInventory(String itemName) {
    int itemIndex = 999;

    for (int i = 0; i < inventory.size(); i++) {
      if (inventory.get(i).name.equals(itemName)) {
        itemIndex = i;
        break;
      }
    }
    return itemIndex;
  }

  public boolean canObtainItem(Entity item) {
    boolean canObtain = false;
    Entity newItem = gp.eGenerator.getObject(item.name);

    // CHECK IF STACKABLE
    if (newItem.stackble == true) {
      int index = searchItemInInventory(newItem.name);
      if (index != 999) {
        inventory.get(index).amount++;
        canObtain = true;
      } else { // new item so need to check vacancy
        if (inventory.size() != maxInventorySize) {
          inventory.add(newItem);
          canObtain = true;
        }
      }
    } else { // NOT STACKABLE so check vacancy
      if (inventory.size() != maxInventorySize) {
        inventory.add(newItem);
        canObtain = true;
      }
    }
    return canObtain;
  }

  private void drawUgabugaAura(Graphics2D g2, BufferedImage img, int x, int y) {
    if (img == null) return;

    int w = img.getWidth();
    int h = img.getHeight();

    // (re)cria o buffer se tamanho mudou
    if (ugabugaAuraBuf == null || w != ugabugaAuraW || h != ugabugaAuraH) {
        ugabugaAuraW = w; ugabugaAuraH = h;
        ugabugaAuraBuf = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
    }

    // cores arco-íris animadas (gira 1x por segundo)
    float phase = (ugabugaCounter % 60) / 60.0f;
    Color rainbow = Color.getHSBColor(phase, 1.0f, 1.0f);

    // pinta o buffer com a cor (com alpha) e usa a alpha do sprite como máscara
    Graphics2D gg = ugabugaAuraBuf.createGraphics();
    gg.setComposite(AlphaComposite.Src);
    gg.setColor(new Color(rainbow.getRed(), rainbow.getGreen(), rainbow.getBlue(),
                          (int)(0.35f * 255))); // intensidade da aura
    gg.fillRect(0, 0, w, h);

    // mantém só onde o sprite tem pixels (usa a alpha do sprite)
    gg.setComposite(AlphaComposite.DstIn);
    gg.drawImage(img, 0, 0, null);
    gg.dispose();

    // ligeiro “inchaço” pra parecer glow
    int pad = 2;
    g2.drawImage(ugabugaAuraBuf, x - pad, y - pad, w + 2*pad, h + 2*pad, null);
}


  public void draw(Graphics2D g2) {

    BufferedImage image = null;
    int tempScreenX = screenX;
    int tempScreenY = screenY;

    switch (direction) {
      case "up":
        if (attacking == false) {
          if (spriteNum == 1) {
            image = up1;
          }
          if (spriteNum == 2) {
            image = up2;
          }
        }

        if (attacking == true) {

          int offsetY = 0;

          // Para espada, machado, etc. (sprites 2 tiles de altura) mantém o deslocamento
          if (currenWeapon == null || currenWeapon.type != type_zarabatana) {
            offsetY = gp.tileSize;
          }

          tempScreenY = screenY - offsetY;

          if (spriteNum == 1) {
            image = attackUp1;
          }
          if (spriteNum == 2) {
            image = attackUp2;
          }
        }

        if (guarding == true) {
          if (parryActive && parryUp != null) {
            image = parryUp;
          } else {
            image = guardUp;
          }
        }
        break;

      case "down":
        if (attacking == false) {
          if (spriteNum == 1) {
            image = down1;
          }
          if (spriteNum == 2) {
            image = down2;
          }
        }
        if (attacking == true) {
          if (spriteNum == 1) {
            image = attackDown1;
          }
          if (spriteNum == 2) {
            image = attackDown2;
          }
        }
        if (guarding == true) {
          if (parryActive && parryDown != null) {
            image = parryDown;
          } else {
            image = guardDown;
          }
        }
        break;

      case "left":
        if (attacking == false) {
          if (spriteNum == 1) {
            image = left1;
          }
          if (spriteNum == 2) {
            image = left2;
          }
        }
        if (attacking == true) {
          tempScreenX = screenX - gp.tileSize;
          if (spriteNum == 1) {
            image = attackLeft1;
          }
          if (spriteNum == 2) {
            image = attackLeft2;
          }
        }
        if (guarding == true) {
          if (parryActive && parryLeft != null) {
            image = parryLeft;
          } else {
            image = guardLeft;
          }
        }
        break;

      case "right":
        if (attacking == false) {
          if (spriteNum == 1) {
            image = right1;
          }
          if (spriteNum == 2) {
            image = right2;
          }
        }
        if (attacking == true) {
          if (spriteNum == 1) {
            image = attackRight1;
          }
          if (spriteNum == 2) {
            image = attackRight2;
          }
        }
        if (guarding == true) {
          if (parryActive && parryRight != null) {
            image = parryRight;
          } else {
            image = guardRight;
          }
        }
        break;
    }
    if (transparent == true) {
      g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
    }
    if (drawing == true) {
      g2.drawImage(image, tempScreenX, tempScreenY, null);
          // 2) aura no formato do sprite (apenas quando ugabugaActive)
    if (ugabugaActive) {
        // aura por cima do sprite; se preferir por baixo, mova esta chamada
        // para ANTES do drawImage do sprite
        drawUgabugaAura(g2, image, tempScreenX, tempScreenY);
      }
    }

    // Reset alpha
    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

    // DEBUG
    // g2.setFont(new Font("Arial", Font.PLAIN, 26));
    // g2.setColor(Color.white);
    // g2.drawString("Invencible: "+invencibleCounter, 10, 400);
  }

  public void startRespawnIFrames() {
    invencible = true;
    transparent = true; // Efeito visual opcional que você já usa
    invencibleCounter = 0; // O update() faz o countdown e desliga sozinho
  }

}
