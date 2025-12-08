package entity;

import main.GamePanel;

public class NPC_MerchantStatue2 extends Entity {

    // flag pra disparar o teleporte fora do speak()
    private boolean teleportPending = false;

    public NPC_MerchantStatue2(GamePanel gp) {
    super(gp);

    type = type_npc;
    name = "Estátua Mercador";

    direction = "down";
    speed = 0;

    // Carrega o sprite grandão (3x3 tiles)
    getImage();

    // <<< IMPORTANTE: ligar colisão >>>
    collision = true;

    // Hitbox ocupando só a base da estátua (a parte que encosta no chão)
    // A estátua tem 3 tiles de altura -> base = último tile
    int baseY = gp.tileSize * 2; // 0, 1, [2] -> último "andar"

    solidArea.x = 0;                       // começa no lado esquerdo do sprite
    solidArea.y = baseY;                   // começa na base
    solidArea.width  = gp.tileSize * 2;    // ocupa toda a largura da estátua
    solidArea.height = gp.tileSize;        // só 1 tile de altura (base)

    solidAreaDefaultX = solidArea.x;
    solidAreaDefaultY = solidArea.y;
}


 private void getImage() {
    // Tamanho maior: 2 tiles de largura, 3 tiles de altura
    int statueWidth  = gp.tileSize * 3;
    int statueHeight = gp.tileSize * 3;

    // Coloque o sprite grande em: res/npc/estatua_mercador.png
    down1 = setup("/res/npc/estatua_mercador_2", statueWidth, statueHeight);

    // Mesmo sprite pra todas as direções
    up1    = down1;
    left1  = down1;
    right1 = down1;
    up2    = down1;
    down2  = down1;
    left2  = down1;
    right2 = down1;
}


   @Override
public void speak() {
    facePlayer();
    gp.player.attackCanceled = true;
    gp.ui.addMessage("Teleportando para a casa do mercador...");
    teleportPending = true;
}

@Override
public void move(String direction) {
    if (teleportPending) {
        teleportPending = false;
        gp.teleportPlayerToMap(3, 25, 21);
        gp.playMusic(18);
    }
}

    @Override
    public void setAction() {
    }
}
