package monster;


import entity.Entity;
import main.GamePanel;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.AlphaComposite;





import object.OBJ_Bananao;
import object.OBJ_Coin_Bronze;
import object.OBJ_Heart;

public class MON_Monkey extends Entity {
    GamePanel gp;
    public static final String monName = "Macaco";

    private boolean bananaIdleLoaded = false;

        // === ATAQUE DE RAIO (só lado direito) ===
    private boolean beamAttacking = false;
    private int beamCounter = 0;

    private BufferedImage beamHead;
    private BufferedImage beamBody1;
    private BufferedImage beamBody2;

    private BufferedImage cutsceneRoarFront;


    // Padrão do ataque de raio
    private static final int BEAM_WINDUP_NORMAL      = 35; // tempo "carregando" (frames)
    private static final int BEAM_WINDUP_RAGE        = 25;
    private static final int BEAM_DURATION_NORMAL    = 30; // tempo com o raio ativo
    private static final int BEAM_DURATION_RAGE      = 45;
    private static final int BEAM_RANGE_TILES_NORMAL = 9;  // alcance à frente (em tiles)
    private static final int BEAM_RANGE_TILES_RAGE   = 10;
    private static final int BEAM_HEIGHT_TILES       = 6;  // espessura vertical da área de dano
    
    // Posição da boca do macaco em relação à borda direita da hitbox
    // (valores calibrados pelo sprite atual)
    private static final int BEAM_MOUTH_OFFSET_X = 84;   // puxa um pouco à frente da hitbox
    private static final int BEAM_MOUTH_OFFSET_Y = -16;  // ***sobe*** o feixe ~55px





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
        getBeamImages();   // carrega sprites do ataque de raio
        setDialogue();
    }
   public void getMonkeyParado() {

    // Sprites especiais só pra CUTSCENE:
    //  - down1 / down2: macaco comendo banana (loop parado)
    //  - cutsceneRoarFront: macaco gritando de frente (grandão)

    down1 = setup("/monster/monkey/mkb01", 200, 200);
    down2 = setup("/monster/monkey/mkb02", 200, 200);

    // Versão "normal" se quiser usar em algum lugar
    down3 = setup("/monster/monkey/mkgrito", 200, 200);

    // Versão GRANDONA só pro grito da cutscene
    cutsceneRoarFront = setup("/monster/monkey/mkgrito", 300, 300);
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
            attackUp1 = setup("/monster/monkey/mksd01", 300, 300);
            attackUp2 = setup("/monster/monkey/mksd02", 300, 300);
            attackUp3 = setup("/monster/monkey/mksd03", 300, 300);
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

        private void getBeamImages() {

        // Boca aberta com início do raio (mesmo tamanho dos outros ataques do boss)
        beamHead = setup("/monster/monkey/mk_beam", 300, 300);

        // Segmentos do raio (vamos repetir para formar o feixe)
        int beamWidth = gp.tileSize * 3;
        int beamHeight = gp.tileSize;

        beamBody1 = setup("/monster/monkey/mkbeam01", beamWidth, beamHeight);
        beamBody2 = setup("/monster/monkey/mkbeam02", beamWidth, beamHeight);
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


        @Override
    public void setAction() {

        // Ativa rage com menos de 50% de vida
        if (!inRage && life < maxLife / 2) {
            inRage = true;
            getImage();
            getAttackImage();
            getBeamImages(); // se tiver variação de sprites na rage, recarrega aqui
            defualtSpeed += 2;
            speed = defualtSpeed;
            attack += 10;
            gp.stopMusic();
            gp.playMusic(24);
        }

        // Se já está no meio de um ataque (normal OU raio),
        // deixa o método attacking() cuidar do resto.
        if (attacking) {
            return;
        }

        // Movimento básico: persegue o player se estiver relativamente perto
        if (getTileDistance(gp.player) < 12) {
            moveTowardPlayer(60);
        } else {
            getRandomDirection(120);
        }

        // Define direção olhando pro player usando o eixo dominante
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

        int tileDist = getTileDistance(gp.player);

        // ----------------------------
        // 1) Tentativa de ataque de RAIO (somente pra DIREITA)
        // ----------------------------
        boolean playerOnRight = px > mx && Math.abs(py - my) < gp.tileSize * 3;

        if (direction.equals("right") && playerOnRight && tileDist >= 3) {

            // Fora da rage o raio é mais raro, na rage é mais frequente
            int beamRate = inRage ? 35 : 80; // quanto MENOR, mais chance de usar o raio

            if (new java.util.Random().nextInt(beamRate) == 0) {
                startBeamAttack();
                return; // nesse frame não tenta o ataque corpo-a-corpo
            }
        }

      // 2) Ataque corpo-a-corpo normal com alcance maior (boss grandão)
// ----------------------------
if (tileDist < 10) {
    int meleeRate = inRage ? 30 : 45; // um pouco mais agressivo na rage

    // Alcance "reto" (para frente) e tolerância lateral
    int straightDist  = inRage ? gp.tileSize * 7 : gp.tileSize * 5; // 5~7 tiles pra frente
    int sideTolerance = gp.tileSize * 4;                             // 4 tiles pros lados

    checkAttackOrNot(meleeRate, straightDist, sideTolerance);
}

    }

        private void startBeamAttack() {
        attacking = true;
        beamAttacking = true;
        beamCounter = 0;
        spriteCounter = 0;
        spriteNum = 1;
        direction = "right"; // garante que o raio SEMPRE sai pra direita
    }

@Override
public void attacking() {

    // 0) Ataque de RAIO usa lógica separada
    if (beamAttacking) {
        updateBeamAttack();
        return;
    }

    spriteCounter++;

    // 1) Wind-up (levantando o braço, ainda sem hitbox)
    if (spriteCounter <= motion1_duration) {
        spriteNum = 1;
        return;
    }

    // 2) Fase ativa do soco (onde realmente sai o dano)
    if (spriteCounter > motion1_duration && spriteCounter <= motion2_duration) {

        // Animação: se tiver 3 frames de ataque, usa 2 depois 3
        boolean has3AttackFrames =
                attackUp3 != null || attackDown3 != null ||
                attackLeft3 != null || attackRight3 != null;

        if (has3AttackFrames) {
            int mid = motion1_duration + (motion2_duration - motion1_duration) / 2;
            if (spriteCounter <= mid) {
                spriteNum = 2;
            } else {
                spriteNum = 3;
            }
        } else {
            spriteNum = 2;
        }

        // ---- HITBOX ESPECIAL DO MACACO (GRANDE) ----
        int currentWorldX = worldX;
        int currentWorldY = worldY;
        int solidWidth    = solidArea.width;
        int solidHeight   = solidArea.height;

        // Em vez de usar attackArea.width/height como deslocamento,
        // usamos o TAMANHO DA HITBOX do corpo.
        // Assim o ataque começa encostado no corpo e se estende pra fora.
        switch (direction) {
            case "up":
                worldY -= solidHeight;
                break;
            case "down":
                worldY += solidHeight;
                break;
            case "left":
                worldX -= solidWidth;
                break;
            case "right":
                worldX += solidWidth;
                break;
        }

        // A área de dano continua sendo 160x160 (ataque em área gigante)
        solidArea.width  = attackArea.width;
        solidArea.height = attackArea.height;

        // Como é monstro, só precisamos checar o player
        if (gp.cChecker.checkPlayer(this)) {
            damagePlayer(attack);
        }

        // Restaura posição e hitbox originais
        worldX = currentWorldX;
        worldY = currentWorldY;
        solidArea.width  = solidWidth;
        solidArea.height = solidHeight;
    }

    // 3) Fim do ataque
    if (spriteCounter > motion2_duration) {
        spriteNum = 1;
        spriteCounter = 0;
        attacking = false;
    }
}


    private void updateBeamAttack() {

        beamCounter++;
        spriteCounter++;

        int windup = inRage ? BEAM_WINDUP_RAGE : BEAM_WINDUP_NORMAL;
        int duration = inRage ? BEAM_DURATION_RAGE : BEAM_DURATION_NORMAL;

        // 1) Carregando o raio (apenas animação, sem dano)
        if (beamCounter <= windup) {
            spriteNum = 1; // usa o primeiro frame de ataque para telegraph
        }
        // 2) Raio ativo
        else if (beamCounter <= windup + duration) {
            spriteNum = 2; // mantém pose de boca aberta
            applyBeamDamage();
        }
        // 3) Fim do ataque
        else {
            beamAttacking = false;
            attacking = false;
            beamCounter = 0;
            spriteCounter = 0;
            spriteNum = 1;
        }
    }

    private void applyBeamDamage() {

      int rangeTiles = inRage ? BEAM_RANGE_TILES_RAGE : BEAM_RANGE_TILES_NORMAL;
int beamWidthPixels = rangeTiles * gp.tileSize;
int beamHeightPixels = BEAM_HEIGHT_TILES * gp.tileSize;

// ORIGEM DO RAIO = BOCA
int startX = getRightX() + BEAM_MOUTH_OFFSET_X;
int centerY = getCenterY() + BEAM_MOUTH_OFFSET_Y;

int topY = centerY - beamHeightPixels / 2;
int bottomY = centerY + beamHeightPixels / 2;
int endX = startX + beamWidthPixels;


        int px = gp.player.getCenterX();
        int py = gp.player.getCenterY();

        if (px >= startX && px <= endX && py >= topY && py <= bottomY) {
            int beamDamage = attack + (inRage ? 5 : 0); // raio bate um pouco mais forte na rage
            damagePlayer(beamDamage);
        }
    }



    public void damageReaction() {
        actionLockCounter = 0;
        // Pode adicionar animação de recuo aqui se quiser
    }

@Override
public void draw(Graphics2D g2) {

    // Ataque corpo-a-corpo pra CIMA (não é o raio)
    boolean customUpAttack   = attacking && !beamAttacking && "up".equals(direction);

   
// Grito de frente na CUTSCENE (gameState de cutscene + olhando pra baixo + frame 3)
boolean customFrontRoar  = gp.gameState == gp.cutsceneState
        && !attacking
        && !beamAttacking
        && "down".equals(direction)
        && spriteNum == 3
        && cutsceneRoarFront != null;


    // 1) Ataque pra cima (overlay especial)
    if (customUpAttack) {
        drawUpAttackOverlay(g2);

        if (beamAttacking && "right".equals(direction)) {
            drawBeamOverlay(g2);
        }
        return;
    }

    // 2) Grito de frente na cutscene
    if (customFrontRoar) {
        drawFrontRoarOverlay(g2);
        // nessa cena não tem raio junto, então podemos sair aqui
        return;
    }

    // 3) Todo o resto (andar, soco pros lados, etc.) usa draw padrão
    super.draw(g2);

    // Overlay do raio (lateral direita)
    if (beamAttacking && "right".equals(direction)) {
        drawBeamOverlay(g2);
    }
}

private void drawFrontRoarOverlay(Graphics2D g2) {

    BufferedImage image = cutsceneRoarFront != null ? cutsceneRoarFront : down3;
    if (image == null) {
        // segurança: se algo der errado, desenha do jeito padrão
        super.draw(g2);
        return;
    }

    // Mesmo ponto de referência usado no Entity.draw()
    int screenX = getScreenX();
    int screenY = getScreenY();

    // Sprites normais do macaco (andar) = 200x200
    final int BODY_SIZE = 200;

    // Queremos manter o pé no MESMO lugar da sprite normal:
    //
    // bottom normal  = screenY + BODY_SIZE
    // bottom grito   = drawY   + image.getHeight()
    // => drawY = screenY + BODY_SIZE - image.getHeight()
    int drawX = screenX;                             // não desloca pro lado
    int drawY = screenY + BODY_SIZE - image.getHeight();

    // Efeito de invencível igual ao Entity.draw()
    if (invencible) {
        g2.setComposite(
            AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f)
        );
    }

    g2.drawImage(image, drawX, drawY, null);

    // reset alpha
    g2.setComposite(
        AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f)
    );

    // HP bar / animação de morte (quase nunca usados na cutscene, mas fica consistente)
    if (invencible) {
        hpBarOn = true;
        hpBarCounter = 0;
    }
    if (dying) {
        dyingAnimation(g2);
    }
}




private void drawUpAttackOverlay(Graphics2D g2) {

    BufferedImage image = null;
    if (spriteNum == 1)      image = attackUp1;
    else if (spriteNum == 2) image = attackUp2;
    else if (spriteNum == 3 && attackUp3 != null) image = attackUp3;

    if (image == null) {
        // fallback de segurança
        super.draw(g2);
        return;
    }

    // Mesmo ponto de referência que o Entity.draw usa
    int screenX = getScreenX();
    int screenY = getScreenY();

    // As sprites de movimento do macaco foram carregadas com 200x200
    final int BODY_SIZE = 200;

    // Queremos:
    //  - MESMA "base" (pé) da sprite normal
    //  - NENHUM "salto" pro lado ou pra frente
    //
    // Bottom da sprite normal = screenY + BODY_SIZE
    // Bottom da sprite de ataque = drawY + image.getHeight()
    // => drawY = screenY + BODY_SIZE - image.getHeight()
    int drawX = screenX;                                // mantém a mesma coluna
    int drawY = screenY + BODY_SIZE - image.getHeight();// mantém o pé no mesmo lugar

    // Efeito de invencibilidade igual ao Entity.draw()
    if (invencible) {
        g2.setComposite(
            AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f)
        );
    }

    // Desenha SÓ o sprite de ataque pra cima
    g2.drawImage(image, drawX, drawY, null);

    // Reset alpha
    g2.setComposite(
        AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f)
    );

    // HP bar e animação de morte (copiado da lógica do Entity.draw)
    if (invencible) {
        hpBarOn = true;
        hpBarCounter = 0;
    }
    if (dying) {
        dyingAnimation(g2);
    }
}


private void drawBeamOverlay(Graphics2D g2) {

    // Boca aberta (beamHead) por cima do corpo
    if (beamHead != null) {

        int headX = getScreenX();
        int headY = getScreenY();

        // Mesmo offset do ataque para a direita em Entity.draw
        if (attacking && useAttackOffsets) {
            headX += -20;
            headY += -60;
        }

        g2.drawImage(beamHead, headX, headY, null);
    }

    // Durante o windup só o grito aparece (sem feixe)
    int windup = inRage ? BEAM_WINDUP_RAGE : BEAM_WINDUP_NORMAL;
    if (beamCounter <= windup || beamBody1 == null) {
        return;
    }

    int rangeTiles = inRage ? BEAM_RANGE_TILES_RAGE : BEAM_RANGE_TILES_NORMAL;
    int beamWidthPixels = rangeTiles * gp.tileSize;

    // Origem do feixe: boca (como já calibramos com BEAM_MOUTH_OFFSET_*)
    int startWorldX = getRightX() + BEAM_MOUTH_OFFSET_X;
    int centerWorldY = getCenterY() + BEAM_MOUTH_OFFSET_Y;

    int startScreenX = startWorldX - gp.player.worldX + gp.player.screenX;
    int centerScreenY = centerWorldY - gp.player.worldY + gp.player.screenY;

    // Alterna entre mkbeam01 e mkbeam02 pra animar
    BufferedImage slice = (beamBody2 != null && (beamCounter / 5) % 2 == 1)
            ? beamBody2
            : beamBody1;

    int sliceW = slice.getWidth();
    int sliceH = slice.getHeight();
    int y = centerScreenY - sliceH / 2;

    int drawn = 0;
    int x = startScreenX;

    while (drawn < beamWidthPixels) {
        g2.drawImage(slice, x, y, null);
        x += sliceW;
        drawn += sliceW;
    }
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
