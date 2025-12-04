package monster;


import entity.Entity;
import main.GamePanel;
import object.OBJ_Bananao;
import object.OBJ_Coin_Bronze;
import object.OBJ_Heart;

public class MON_Monkey extends Entity {
    GamePanel gp;
    public static final String monName = "Macaco";

    private boolean bananaIdleLoaded = false;


    public MON_Monkey(GamePanel gp) {
        super(gp);
        this.gp = gp;

        useAttackOffsets = true;
        sleep = true;

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
        // Hitbox PROPORCIONAL ao sprite 200x200 (3x3 tiles)
        solidArea.x = 55;       // centraliza o corpo
        solidArea.y = 105;      // hitbox inicia mais perto dos pés
        solidArea.width = 90;   // largura justa do tronco
        solidArea.height = 85;  // altura proporcional do tronco
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        // Área de ataque mais ampla (boss grande)
        attackArea.width = 160;
        attackArea.height = 160;


        motion1_duration = 20;  // wind-up
        motion2_duration = 45;  // ataque ativo

        getImage();
        getAttackImage();
        setDialogue();
    }
    public void getMonkeyParado() {

        // Sprites especiais só pra CUTSCENE:
        //  - down1 / down2: macaco comendo banana (loop parado)
        //  - down3: macaco gritando (mk_beam)

        // >>> TROCA os nomes abaixo pros nomes reais dos seus arquivos (sem .png) <<<
        down1 = setup("/monster/monkey/mkb01", 200, 200);
        down2 = setup("/monster/monkey/mkb02", 200, 200);

        // sprite do grito (essa imagem aí do mk_beam.png)
        down3 = setup("/monster/monkey/mkgrito", 200, 200); // consertar mais tarde //
    }

    public void getImage() {


        if (!inRage) {
            up1 = setup("/monster/monkey/mku01", 200, 200);
            up2 = setup("/monster/monkey/mku02", 200, 200);
            up3 = setup("/monster/monkey/mku03", 200, 200);
            down1 = setup("/monster/monkey/mkd01", 200, 200);
            down2 = setup("/monster/monkey/mkd02", 200, 200);
            down3 = setup("/monster/monkey/mkd03", 200, 200);
            left1 = setup("/monster/monkey/mkl01", 200, 200);
            left2 = setup("/monster/monkey/mkl02", 200, 200);
            left3 = setup("/monster/monkey/mkl03", 200, 200);
            right1 = setup("/monster/monkey/mkr01", 200, 200);
            right2 = setup("/monster/monkey/mkr02", 200, 200);
            right3 = setup("/monster/monkey/mkr03", 200, 200);
        } else {
            // Fase rage: sprites mais agressivos (opcional)
            up1 = setup("/monster/monkey/mkrageu01", 200, 200);
            up2 = setup("/monster/monkey/mkrageu02", 200, 200);
            up3 = setup("/monster/monkey/mkrageu03", 200, 200);
            down1 = setup("/monster/monkey/mkraged01", 200, 200);
            down2 = setup("/monster/monkey/mkraged02", 200, 200);
            down3 = setup("/monster/monkey/mkraged03", 200, 200);
            left1 = setup("/monster/monkey/mkragel01", 200, 200);
            left2 = setup("/monster/monkey/mkragel02", 200, 200);
            left3 = setup("/monster/monkey/mkragel03", 200, 200);
            right1 = setup("/monster/monkey/mkrager01", 200, 200);
            right2 = setup("/monster/monkey/mkrager02", 200, 200);
            right3 = setup("/monster/monkey/mkrager03", 200, 200);
        }
    }

    public void getAttackImage() {
        

        if (!inRage) {
            attackDown1 = setup("/monster/monkey/mksu01", 300, 300);
            attackDown2 = setup("/monster/monkey/mksu02", 300, 300);
            attackLeft1 = setup("/monster/monkey/mksl01", 300, 300);
            attackLeft2 = setup("/monster/monkey/mksl02", 300, 300);
            attackLeft3 = setup("/monster/monkey/mksl03", 300, 300);
            attackRight1 = setup("/monster/monkey/mksr01", 300, 300);
            attackRight2 = setup("/monster/monkey/mksr02", 300, 300);
            attackRight3 = setup("/monster/monkey/mksr03", 300, 300);
        } //else {
           // attackDown1 = setup("/monster/monkey/rage_attack_down_1", 200, 200);
            //attackDown2 = setup("/monster/monkey/rage_attack_down_2", 200, 200);
           // attackLeft1 = setup("/monster/monkey/rage_attack_left_1", 200, 200);
            //attackLeft2 = setup("/monster/monkey/rage_attack_left_2", 200, 200);
           // attackRight1 = setup("/monster/monkey/rage_attack_right_1", 200, 200);
            //attackRight2 = setup("/monster/monkey/rage_attack_right_2", 200, 200);
        //}
    }

    public void setDialogue() {
        dialogues[0][0] = "QUEM OUSA ENTRAR NO MEU REINO?!";
        dialogues[0][1] = "ESSA BANANA É MINHA!!!";
        dialogues[0][2] = "VOCÊ VAI VIRAR COMIDA DE MACACO!";
    }

        @Override
    public void update() {

        // MODO SLEEP = macaco comendo banana parado
        if (sleep) {

            // Garante que está usando os sprites de banana
            if (!bananaIdleLoaded) {
                getMonkeyParado();   // down1 = mkb01, down2 = mkb02, down3 = grito
                direction = "down";  // fica olhando pra baixo (pra câmera)
                spriteNum = 1;
                spriteCounter = 0;
                bananaIdleLoaded = true;
            }

            // Anima só mkb01 <-> mkb02 parado
            spriteCounter++;
            if (spriteCounter > 30) { // ~0.5s a 60 FPS, ajusta se quiser mais rápido
                spriteNum = (spriteNum == 1) ? 2 : 1;
                spriteCounter = 0;
            }

            // NÃO chama super.update() aqui, pra ele não andar, não atacar, nada.

        } else {
            // Acordou: volta pro sprite normal de combate se estava em modo banana
            if (bananaIdleLoaded) {
                bananaIdleLoaded = false;
                getImage();        // recarrega sprites normais de movimento :contentReference[oaicite:1]{index=1}
                getAttackImage();  // recarrega sprites de ataque normais
            }

            // Comportamento padrão de monstro (andar, seguir player, atacar, etc.)
            super.update();
        }
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
            gp.stopMusic();
            gp.playMusic(24);
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

        gp.stopMusic();
        gp.playMusic(23);
    }
}
