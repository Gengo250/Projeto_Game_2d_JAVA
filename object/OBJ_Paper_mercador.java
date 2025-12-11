package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Paper_mercador extends Entity {

    public static final String objName = "Pergaminho do Mercador";

    GamePanel gp;

    public OBJ_Paper_mercador(GamePanel gp) {
        super(gp);
        this.gp = gp;

        type = type_consumable;   // para aparecer no inventário como item usável
        stackble = false;         // NÃO empilha (não estocável)
        amount = 1;               // valor simbólico, não vai ser consumido
        name = objName;

        // Ícone do item (a imagem que você mandou)
        image = setup("/res/objects/paper_mercador", gp.tileSize, gp.tileSize);
        down1 = image;

        description = "[Pergaminho do Mercador]\n"
                + "Um mapa enrolado\n"
                + "à floresta, mas só\n";
    }

    @Override
    public boolean use(Entity entity) {

        // Só funciona na casa do mercador (mapa 3)
        if (gp.currentMap == 3) {

            // Mensagem na tela
            gp.ui.addMessage("Você ativa o pergaminho do mercador!");

            // Teleporta para o mapa principal
            // mesmo ponto da saída da casa do mercador:
            // EventHandler: teleport(0, 185, 119, gp.outside);
            gp.teleportPlayerToMap(0, 185, 119);

        } else {
            // Tentou usar fora da casa do mercador
            gp.ui.addMessage("Esse pergaminho só funciona na casa do mercador.");
        }

        // IMPORTANTE: retornar FALSE para NÃO consumir o item
        // assim ele é infinito, pode usar quantas vezes quiser.
        return false;
    }
}
