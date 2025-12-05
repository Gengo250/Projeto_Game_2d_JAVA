package entity;

import main.GamePanel;

public class NPC_TamanduaStatueBlue extends Entity {

    public NPC_TamanduaStatueBlue(GamePanel gp) {
        super(gp);

        this.gp = gp;
        type = type_npc;
        name = "Estátua Tamanduá Azul";

        direction = "down";
        speed = 0;

        // hitbox simples
        solidArea.x = 8;
        solidArea.y = 16;
        solidArea.width  = 32;
        solidArea.height = 32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        // marca como ponto de fast travel
        fastTravelPoint = true;

        getImage();
        setDialogue();
    }

    private void getImage() {
        // Coloca Estatuam.png em: res/npc/Estatuam.png
        down1 = setup("/res/npc/estatuam", gp.tileSize, gp.tileSize);

        // estátua é estática, usa a mesma em tudo
        up1 = down1;
        left1 = down1;
        right1 = down1;
        up2 = down1;
        down2 = down1;
        left2 = down1;
        right2 = down1;
    }

    private void setDialogue() {
        dialogues[0][0] =
            "Você toca na estátua azul do Tamanduá...\n" +
            "Um mapa espiritual se abre diante de você.";

        dialogues[0][1] =
            "Use as setas para escolher outra estátua marcada com '!'\n" +
            "e aperte ENTER para viajar.\n" +
            "ESC cancela.";
    }

  @Override
  public void speak() {
    facePlayer();
    gp.player.attackCanceled = true;

    // Se quiser sem diálogo, só abre direto o mapa mundi:
    gp.map.startFastTravelFromStatue(this);
}


    @Override
    public void setAction() {
        // estátua parada
    }

    @Override
    public void move(String direction) {
        // não anda
    }
}
