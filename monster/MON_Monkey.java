package monster;

import entity.Entity;
import main.GamePanel;

public class MON_Monkey extends Entity {

  GamePanel gp;
  public static final String monName = "Monkey Boss";

  public MON_Monkey(GamePanel gp) {
    super(gp);
    this.gp = gp;

    type = type_monster;
    boss = true;
    name = monName;

    defualtSpeed = 2;
    speed = defualtSpeed;
    maxLife = 80;
    life = maxLife;
    attack = 12;
    defense = 3;
    exp = 400;
    knokBackPower = 4;

    sleep = false;
    direction = "down";

    // Tamanho do sprite (igual ao Skeleton Lord: 5 tiles)
    int size = gp.tileSize * 5;

    // Hitbox do corpo
    solidArea.x = 48;
    solidArea.y = 48;
    solidArea.width  = size - 48 * 2;
    solidArea.height = size - 48;
    solidAreaDefaultX = solidArea.x;
    solidAreaDefaultY = solidArea.y;

    // Hitbox do soco (dano em área)
    // Mesmo tamanho base do Skeleton Lord
    attackArea.width  = 170;
    attackArea.height = 170;

    // Duração do ataque (frames) – IGUAL Skeleton Lord
    motion1_duration = 25;  // wind-up (levantando o braço)
    motion2_duration = 50;  // impacto (soco, aplica dano)

    getImage();
    getAttack();
  }

  public void getImage() {

    int i = 5; // mesmo fator de escala do Skeleton Lord

    // IMPORTANTE: caminhos e nomes precisam bater com a pasta
    // res/monster/monkey/monkey_*.png

    // UP – dois frames (usa seus sprites 1 e 2)
    up1 = setup("/res/monster/monkey/monkey_up_1",
                gp.tileSize * i, gp.tileSize * i);
    up2 = setup("/res/monster/monkey/monkey_up_2",
                gp.tileSize * i, gp.tileSize * i);

    // DOWN – dois frames (usa seus sprites 1 e 2)
    down1 = setup("/res/monster/monkey/monkey_down_1",
                  gp.tileSize * i, gp.tileSize * i);
    down2 = setup("/res/monster/monkey/monkey_down_2",
                  gp.tileSize * i, gp.tileSize * i);

    // Esquerda – 3 frames
    left1 = setup("/res/monster/monkey/monkey_left_1",
                  gp.tileSize * i, gp.tileSize * i);
    left2 = setup("/res/monster/monkey/monkey_left_2",
                  gp.tileSize * i, gp.tileSize * i);
    left3 = setup("/res/monster/monkey/monkey_left_3",
                  gp.tileSize * i, gp.tileSize * i);

    // Direita – 3 frames
    right1 = setup("/res/monster/monkey/monkey_right_1",
                   gp.tileSize * i, gp.tileSize * i);
    right2 = setup("/res/monster/monkey/monkey_right_2",
                   gp.tileSize * i, gp.tileSize * i);
    right3 = setup("/res/monster/monkey/monkey_right_3",
                   gp.tileSize * i, gp.tileSize * i);
  }

// ===== SPRITES DE ATAQUE (SOCÃO) =====
public void getAttack() {

    int i = 5;

    // Esquerda – 3 frames de ataque
    attackLeft1 = setup("/res/monster/monkey/monkey_attack_left_1",
                        gp.tileSize * 2 * i, gp.tileSize * i);
    attackLeft2 = setup("/res/monster/monkey/monkey_attack_left_2",
                        gp.tileSize * 2 * i, gp.tileSize * i);
    attackLeft3 = setup("/res/monster/monkey/monkey_attack_left_3",
                        gp.tileSize * 2 * i, gp.tileSize * i);

    // Direita – 3 frames de ataque
    attackRight1 = setup("/res/monster/monkey/monkey_attack_right_1",
                         gp.tileSize * 2 * i, gp.tileSize * i);
    attackRight2 = setup("/res/monster/monkey/monkey_attack_right_2",
                         gp.tileSize * 2 * i, gp.tileSize * i);
    attackRight3 = setup("/res/monster/monkey/monkey_attack_right_3",
                         gp.tileSize * 2 * i, gp.tileSize * i);

    // Não temos sprites separados para cima/baixo,
    // então reaproveitamos os laterais (só visualmente).
    attackUp1   = attackLeft1;
    attackUp2   = attackLeft2;
    attackUp3   = attackLeft3;

    attackDown1 = attackRight1;
    attackDown2 = attackRight2;
    attackDown3 = attackRight3;
}

  @Override
  public void setAction() {

    // IA básica igual ao Skeleton Lord:
    // se estiver perto, persegue; se não, anda aleatório
    if (getTileDistance(gp.player) < 10) {
      moveTowardPlayer(60);   // recalcula a direção a cada 60 frames
    } else {
      getRandomDirection(120);
    }

    // Tenta iniciar um ataque (soco em área)
    if (attacking == false) {
      // rate = 60 -> em média 1 vez por segundo se o player estiver na área
      // straight/horizontal definem o "retângulo" de alcance à frente dele
      checkAttackOrNot(60, gp.tileSize * 7, gp.tileSize * 5);
    }
  }
}
