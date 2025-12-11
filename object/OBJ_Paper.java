package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Paper extends Entity {

    GamePanel gp;
    public static final String objName = "Papel";

    public OBJ_Paper(GamePanel gp) {
        super(gp);
        this.gp = gp;

        type = type_consumable;
        name = objName;

        // sprite do item (papel.png em /res/objects)
        down1 = setup("/res/objects/papel", gp.tileSize, gp.tileSize);

        // descrição que aparece na janelinha do inventário
        description = "[Papel]\n(este item não é estocavel)";
        price = 0;

        // NÃO empilhável
        stackble = false;

        setDialogue();
    }
public void setDialogue() {
    dialogues[0][0] =
        "Resolva em complemento de dois (8 bits):\n"
      + "Converta o número decimal -5 para binário em 8 bits.\n"
      + "Digite apenas os 8 bits.";
}


    @Override
    public boolean use(Entity entity) {
        
        gp.ui.openPaperWindow(this);

        return false;
    }
}
