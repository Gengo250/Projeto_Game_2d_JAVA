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

    sleep = false;        // começa “desligado” até ativar a luta
    direction = "down";

    // Mesma técnica de proporção do Skeleton Lord
    int size = gp.tileSize * 5;

    solidArea.x = 48;
    solidArea.y = 48;
    solidArea.width  = size - 48 * 2;
    solidArea.height = size - 48;
    solidAreaDefaultX = solidArea.x;
    solidAreaDefaultY = solidArea.y;

    attackArea.width  = 120;
    attackArea.height = 120;

    getImage();
  }

  public void getImage() {

    int i = 5; // mesmo fator de escala do Skeleton Lord

    // IMPORTANTE: caminhos e nomes precisam bater com a pasta
    // res/monster/monkey/monkey_*.png

    up1   = setup("/res/monster/monkey/monkey_up_1",
                  gp.tileSize * i, gp.tileSize * i);
    up2   = up1; // só temos 1 frame pra cima

    down1 = setup("/res/monster/monkey/monkey_down_1",
                  gp.tileSize * i, gp.tileSize * i);
    down2 = down1; // só 1 frame pra baixo também

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

  @Override
  public void setAction() {

    // Simples: se estiver perto, persegue; se não, anda aleatório
    if (getTileDistance(gp.player) < 10) {
      moveTowardPlayer(60);   // atualiza direção a cada 60 frames
    } else {
      getRandomDirection(120);
    }
  }
}
