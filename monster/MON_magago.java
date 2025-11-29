package monster;


import entity.Entity;
import main.GamePanel;
import object.OBJ_Bananao;
import object.OBJ_Coin_Bronze;
import object.OBJ_Heart;

public class MON_magago extends Entity {
    GamePanel gp;
    public static final String monName = "Macaco Rei";

    public MON_magago(GamePanel gp) {
        super(gp);
        this.gp = gp;

        type = type_monster;
        boss = true;
        name = monName;
        defualtSpeed = 2;
        speed = defualtSpeed;
        maxLife = 140;
        life = maxLife;
        attack = 20;
        defense = 10;
        exp = 600;
        knokBackPower = 10;

        // Tamanho grande (3x3 tiles)
        int size = gp.tileSize * 3;
        solidArea.x = 24;
        solidArea.y = 48;
        solidArea.width = size - 48;
        solidArea.height = size - 96;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        attackArea.width = 120;
        attackArea.height = 120;

        motion1_duration = 20;  // wind-up
        motion2_duration = 45;  // ataque ativo

        getImage();
        getAttackImage();
        setDialogue();
    }

    public void getImage() {
        int i = 3; // 3x scale

        if (!inRage) {
            up1 = setup("/res/monster/Monkey/Mku01.png", gp.tileSize * i, gp.tileSize * i);
            up2 = setup("/res/monster/Monkey/Mku02,png", gp.tileSize * i, gp.tileSize * i);
            up3 = setup("/res/monster/Monkey/Mku03,png", gp.tileSize * i, gp.tileSize * i);
            down1 = setup("/res/monster/Monkey/Mkd01.png", gp.tileSize * i, gp.tileSize * i);
            down2 = setup("/res/monster/Monkey/Mkd02.png", gp.tileSize * i, gp.tileSize * i);
            down3 = setup("/res/monster/Monkey/Mkd03.png", gp.tileSize * i, gp.tileSize * i);
            left1 = setup("/res/monster/Monkey/Mkl01.png", gp.tileSize * i, gp.tileSize * i);
            left2 = setup("/res/monster/Monkey/Mkl02.png", gp.tileSize * i, gp.tileSize * i);
            left3 = setup("/res/monster/Monkey/Mkl03.png", gp.tileSize * i, gp.tileSize * i);
            right1 = setup("/res/monster/Monkey/MKr01.png", gp.tileSize * i, gp.tileSize * i);
            right2 = setup("/res/monster/Monkey/Mkr02.png", gp.tileSize * i, gp.tileSize * i);
            right3 = setup("/res/monster/Monkey/Mkr03.png", gp.tileSize * i, gp.tileSize * i);
        } else {
            // Fase rage: sprites mais agressivos (opcional)
            up1 = setup("/res/monster/Monkey/Mkrageu01.png", gp.tileSize * i, gp.tileSize * i);
            up2 = setup("/res/monster/Monkey/Mkrageu02.png", gp.tileSize * i, gp.tileSize * i);
            up3 = setup("/res/monster/Monkey/Mkrageu03.png", gp.tileSize * i, gp.tileSize * i);
            down1 = setup("/res/monster/Monkey/Mkraged01.png", gp.tileSize * i, gp.tileSize * i);
            down2 = setup("/res/monster/Monkey/Mkraged02.png", gp.tileSize * i, gp.tileSize * i);
            down3 = setup("/res/monster/Monkey/Mkraged03.png", gp.tileSize * i, gp.tileSize * i);
            left1 = setup("/res/monster/Monkey/Mkragel01.png", gp.tileSize * i, gp.tileSize * i);
            left2 = setup("/res/monster/Monkey/Mkragel02.png", gp.tileSize * i, gp.tileSize * i);
            left3 = setup("/res/monster/Monkey/Mkragel03.png", gp.tileSize * i, gp.tileSize * i);
            right1 = setup("/res/monster/Monkey/Mkrager01.png", gp.tileSize * i, gp.tileSize * i);
            right2 = setup("/res/monster/Monkey/Mkrager02.png", gp.tileSize * i, gp.tileSize * i);
            right3 = setup("/res/monster/Monkey/Mkrager03.png", gp.tileSize * i, gp.tileSize * i);
        }
    }

    public void getAttackImage() {
        int i = 3;
        int attackWidth = gp.tileSize * 4;
        int attackHeight = gp.tileSize * 4;

        if (!inRage) {
            attackUp1 = setup("/res/monster/Monkey/attack_up_1", gp.tileSize * i, attackHeight);
            attackUp2 = setup("/res/monster/Monkey/attack_up_2", gp.tileSize * i, attackHeight);
            attackDown1 = setup("/res/monster/Monkey/attack_down_1", gp.tileSize * i, attackHeight);
            attackDown2 = setup("/res/monster/Monkey/attack_down_2", gp.tileSize * i, attackHeight);
            attackLeft1 = setup("/res/monster/Monkey/attack_left_1", attackWidth, gp.tileSize * i);
            attackLeft2 = setup("/res/monster/Monkey/attack_left_2", attackWidth, gp.tileSize * i);
            attackRight1 = setup("/res/monster/Monkey/attack_right_1", attackWidth, gp.tileSize * i);
            attackRight2 = setup("/res/monster/Monkey/attack_right_2", attackWidth, gp.tileSize * i);
        } else {
            attackUp1 = setup("/res/monster/Monkey/rage_attack_up_1", gp.tileSize * i, attackHeight);
            attackUp2 = setup("/res/monster/Monkey/rage_attack_up_2", gp.tileSize * i, attackHeight);
            attackDown1 = setup("/res/monster/Monkey/rage_attack_down_1", gp.tileSize * i, attackHeight);
            attackDown2 = setup("/res/monster/Monkey/rage_attack_down_2", gp.tileSize * i, attackHeight);
            attackLeft1 = setup("/res/monster/Monkey/rage_attack_left_1", attackWidth, gp.tileSize * i);
            attackLeft2 = setup("/res/monster/Monkey/rage_attack_left_2", attackWidth, gp.tileSize * i);
            attackRight1 = setup("/res/monster/Monkey/rage_attack_right_1", attackWidth, gp.tileSize * i);
            attackRight2 = setup("/res/monster/Monkey/rage_attack_right_2", attackWidth, gp.tileSize * i);
        }
    }

    public void setDialogue() {
        dialogues[0][0] = "QUEM OUSA ENTRAR NO MEU REINO?!";
        dialogues[0][1] = "ESSA BANANA É MINHA!!!";
        dialogues[0][2] = "VOCÊ VAI VIRAR COMIDA DE MACACO!";
    }

    public void setAction() {

        // Ativa rage com menos de 50% de vida
        if (!inRage && life < maxLife / 2) {
            inRage = true;
            getImage();
            getAttackImage();
            defualtSpeed += 2;
            speed = defualtSpeed;
            attack += 10;
            gp.playeSE(18); // som de rage
        }

        if (getTileDistance(gp.player) < 12) {
            moveTowardPlayer(60);
        } else {
            getRandomDirection(120);
        }

        // Define direção do ataque baseada na posição do player
        if (!attacking) {
            int px = gp.player.getCenterX();
            int py = gp.player.getCenterY();
            int mx = getCenterX();
            int my = getCenterY();

            int xDiff = Math.abs(px - mx);
            int yDiff = Math.abs(py - my);

            if (xDiff > yDiff) {
                direction = (px < mx) ? "left" : "right";
            } else {
                direction = (py < my) ? "up" : "down";
            }

            // Ataca se estiver perto
            if (getTileDistance(gp.player) < 3) {
                checkAttackOrNot(50, gp.tileSize * 3, gp.tileSize * 3);
            }
        }
    }

    public void damageReaction() {
        actionLockCounter = 0;
        // Pode adicionar animação de recuo aqui se quiser
    }

    public void checkDrop() {
        // Desativa boss fight
        gp.bossBattleON = false;

        // Dropa a Banana Dourada 100%
        dropItem(new OBJ_Bananao(gp));

        // Drops extras (opcional)
        for (int i = 0; i < 15; i++) {
            dropItem(new OBJ_Coin_Bronze(gp));
        }
        dropItem(new OBJ_Heart(gp));

        // Toca música de vitória
        gp.stopMusic();
        gp.playMusic(20); // música pós-boss
    }
}
