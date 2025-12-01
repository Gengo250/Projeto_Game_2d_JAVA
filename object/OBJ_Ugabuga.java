package object;

import entity.Entity;
import entity.Player;
import main.GamePanel;

public class OBJ_Ugabuga extends Entity {
    GamePanel gp;
    public static final String objName = "Ugabuga";

    public OBJ_Ugabuga(GamePanel gp){
        super(gp);
        this.gp = gp;

        type = type_consumable;
        name = objName;
        down1 = setup("/res/objects/ugabuga", gp.tileSize, gp.tileSize);
        description = "[Ugabuga!!!]\nMáscara de ritual que te deixa insano por 15 segundos.";
        price = 35;
        stackble = true;

        setDialogue();
    }

    public void setDialogue(){
        dialogues[0][0] =
            "Você equipa a máscara Ugabuga!!\n" +
            "Invencível e causando dano em área por 15 segundos!";
    }

    @Override
    public boolean use(Entity entity){

        // só faz sentido se quem usar for o player
        if (!(entity instanceof Player)) {
            return false;
        }

        Player player = (Player) entity;

        // se já estiver ativo, não gasta o item
        if (player.ugabugaActive) {
            gp.ui.addMessage("O poder Ugabuga já está ativo!");
            return false;
        }

        startDialogue(this, 0);
        player.startUgabuga();   // liga o modo especial
        gp.playeSE(2);           // reaproveitei o som de potion; troca se quiser

        return true;
    }
}
