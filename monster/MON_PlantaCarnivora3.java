package monster;

import java.awt.Color;
import java.util.Random;

import entity.Entity;
import main.GamePanel;
import object.OBJ_Coin_Bronze;
import object.OBJ_Heart;
import object.OBJ_ManaCrystal;

public class MON_PlantaCarnivora3 extends Entity {

    GamePanel gp;
    // deixa a planta maior que 1 tile (48*3/2 = 72px)
    private final int SPRITE_SIZE;

    public MON_PlantaCarnivora3(GamePanel gp) {
        super(gp);
        this.gp = gp;
        this.SPRITE_SIZE = gp.tileSize * 4;    // 2x o tile (bem maior)


        useAttackOffsets = false;

        type = type_monster;
        name = "Planta Carnívora";

        // PLANTA É ESTÁTICA
        defualtSpeed = 0;
        speed = defualtSpeed;

        maxLife = 12;
        life = maxLife;
        attack = 8;
        defense = 2;
        exp = 40;
        knokBackPower = 2;

        // hitbox no "pé" da planta
        solidArea.x = SPRITE_SIZE / 4;
        solidArea.y = SPRITE_SIZE / 2;
        solidArea.width = SPRITE_SIZE / 2;
        solidArea.height = SPRITE_SIZE / 2;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

       
      // alcance da mordida (hitbox maior, pegando o dash inteiro)
      attackArea.width  = gp.tileSize ; // mais alcance na horizontal
      attackArea.height = gp.tileSize ; // mais “grossura” vertical


        motion1_duration = 20;
        motion2_duration = 50;

        getImage();
        getAttack();
    }

    // =============== SPRITES ===============

    public void getImage() {
        // sprites de idle/movimento (mas ela não “anda”)
        up1    = setup("/res/monster/planta3/planta_3_up_1",    SPRITE_SIZE, SPRITE_SIZE);
        up2    = setup("/res/monster/planta3/planta_3_up_2",    SPRITE_SIZE, SPRITE_SIZE);
        up3    = setup("/res/monster/planta3/planta_3_up_3",    SPRITE_SIZE, SPRITE_SIZE);
        down1  = setup("/res/monster/planta3/planta_3_down_1",  SPRITE_SIZE, SPRITE_SIZE);
        down2  = setup("/res/monster/planta3/planta_3_down_2",  SPRITE_SIZE, SPRITE_SIZE);
        down3  = setup("/res/monster/planta3/planta_3_down_3",  SPRITE_SIZE, SPRITE_SIZE);
        left1  = setup("/res/monster/planta3/planta_3_left_1",  SPRITE_SIZE, SPRITE_SIZE);
        left2  = setup("/res/monster/planta3/planta_3_left_2",  SPRITE_SIZE, SPRITE_SIZE);
        left3  = setup("/res/monster/planta3/planta_3_left_3",  SPRITE_SIZE, SPRITE_SIZE);
        right1 = setup("/res/monster/planta3/planta_3_right_1", SPRITE_SIZE, SPRITE_SIZE);
        right2 = setup("/res/monster/planta3/planta_3_right_2", SPRITE_SIZE, SPRITE_SIZE);
        right3 = setup("/res/monster/planta3/planta_3_right_3", SPRITE_SIZE, SPRITE_SIZE);
    }

    public void getAttack() {
        // usa o mesmo tamanho pra deixar o ataque bem visível
        attackUp1    = setup("/res/monster/planta3/planta_3_attack_up_1",    SPRITE_SIZE, SPRITE_SIZE);
        attackUp2    = setup("/res/monster/planta3/planta_3_attack_up_2",    SPRITE_SIZE, SPRITE_SIZE);
        attackUp3    = setup("/res/monster/planta3/planta_3_attack_up_3",    SPRITE_SIZE, SPRITE_SIZE);
        attackDown1  = setup("/res/monster/planta3/planta_3_attack_down_1",  SPRITE_SIZE, SPRITE_SIZE);
        attackDown2  = setup("/res/monster/planta3/planta_3_attack_down_2",  SPRITE_SIZE, SPRITE_SIZE);
        attackDown3  = setup("/res/monster/planta3/planta_3_attack_down_3",  SPRITE_SIZE, SPRITE_SIZE);
        attackLeft1  = setup("/res/monster/planta3/planta_3_attack_left_1",  SPRITE_SIZE, SPRITE_SIZE);
        attackLeft2  = setup("/res/monster/planta3/planta_3_attack_left_2",  SPRITE_SIZE, SPRITE_SIZE);
        attackLeft3  = setup("/res/monster/planta3/planta_3_attack_left_3",  SPRITE_SIZE, SPRITE_SIZE);
        attackRight1 = setup("/res/monster/planta3/planta_3_attack_right_1", SPRITE_SIZE, SPRITE_SIZE);
        attackRight2 = setup("/res/monster/planta3/planta_3_attack_right_2", SPRITE_SIZE, SPRITE_SIZE);
        attackRight3 = setup("/res/monster/planta3/planta_3_attack_right_3", SPRITE_SIZE, SPRITE_SIZE);
    }

    // =============== IA ===============

@Override
public void setAction() {

    // 1) Planta é estática, não precisa animar walk
    spriteCounter = 0;
    spriteNum = 1;

    // 2) Calcula a posição do player em relação à planta (centro da hitbox)
    int dx = gp.player.getCenterX() - getCenterX();
    int dy = gp.player.getCenterY() - getCenterY();

    // 2.1) Decide a direção que a planta VAI OLHAR
    //      (prioriza esquerda/direita se estiver "meio diagonal")
    if (Math.abs(dx) >= Math.abs(dy)) {
        direction = (dx < 0) ? "left" : "right";
    } else {
        direction = (dy < 0) ? "up" : "down";
    }

    // 3) Se não estiver atacando, vê se o player está NA FRENTE DELA
    if (!attacking) {

        int xDis = Math.abs(dx);
        int yDis = Math.abs(dy);

        // alcance (em pixels) do "cone" de detecção
        int rangeStraight   = gp.tileSize * 4; // frente
        int rangeSide       = gp.tileSize * 3; // para cima/baixo

        boolean targetInFront = false;

        switch (direction) {
            case "right":
                // player à direita, relativamente alinhado na vertical
                if (dx > 0 && xDis <= rangeStraight && yDis <= rangeSide) {
                    targetInFront = true;
                }
                break;

            case "left":
                // player à esquerda
                if (dx < 0 && xDis <= rangeStraight && yDis <= rangeSide) {
                    targetInFront = true;
                }
                break;

            case "down":
                // player abaixo
                if (dy > 0 && yDis <= rangeStraight && xDis <= rangeSide) {
                    targetInFront = true;
                }
                break;

            case "up":
                // player acima
                if (dy < 0 && yDis <= rangeStraight && xDis <= rangeSide) {
                    targetInFront = true;
                }
                break;
        }

        // 4) Se o player está na frente, rola o dado pra ver se ela ataca
        if (targetInFront) {
            int rate = 20; // quanto menor, mais frequente o ataque
            if (new Random().nextInt(rate) == 0) {
                attacking = true;
                spriteCounter = 0;
                spriteNum = 0;
                shotAvailableCounter = 0;
            }
        }
    }
}


    @Override
    public void damageReaction() {
        // nada especial, só reseta contador
        actionLockCounter = 0;
    }

    @Override
    public void checkDrop() {
        int i = new Random().nextInt(100) + 1;

        if (i < 40) {
            dropItem(new OBJ_Coin_Bronze(gp));
        } else if (i < 70) {
            dropItem(new OBJ_Heart(gp));
        } else if (i < 100) {
            dropItem(new OBJ_ManaCrystal(gp));
        }
    }

    // Partículas verdes quando leva dano
    @Override
    public Color getParticleColor() {
        return new Color(34, 177, 76);
    }

    @Override
    public int getParticleSize() {
        return 6;
    }

    @Override
    public int getParticleSpeed() {
        return 1;
    }

    @Override
    public int getParticleMaxLife() {
        return 20;
    }
}
