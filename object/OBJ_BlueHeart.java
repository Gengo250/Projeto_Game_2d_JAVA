package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_BlueHeart extends Entity {

    GamePanel gp;
    public static final String objName = "Hillux";

    public OBJ_BlueHeart(GamePanel gp) {
        super(gp);

        this.gp = gp;

        // AGORA É OBJETO ESTÁTICO, NÃO COLETÁVEL
        type = type_obstacle;
        name = objName;

        // Usa a imagem da Hillux (ou troca pelo pergaminho se quiser)
        down1 = setup("/res/objects/hillux", gp.tileSize * 3, gp.tileSize * 2);


        // colisão igual porta
        collision = true;
        solidArea.x = 0;
        solidArea.y = 16;
        solidArea.width = 48;
        solidArea.height = 32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        setDialogues();
    }

    public void setDialogues() {
        // mensagem quando NÃO tem chave
        dialogues[0][0] = "Você precisa de uma chave para ativar isso.";
    }

    @Override
    public void interact() {

      

        // procura uma chave no inventário
        // ATENÇÃO: o nome tem que bater com o name/objName da sua chave
        int keyIndex = gp.player.searchItemInInventory("Key");

        if (keyIndex == 999) {
            // não tem chave → só mostra diálogo
            startDialogue(this, 0);
        } else {
            // TEM CHAVE → ativa o final do jogo

            // Se quiser CONSUMIR a chave, descomenta a linha abaixo:
            // gp.player.inventory.remove(keyIndex);

            // mesma lógica do BlueHeart antigo
            gp.gameState = gp.cutsceneState;
            gp.csManager.sceneNum = gp.csManager.ending;
        }
    }
}
