package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Key extends Entity {
    GamePanel gp;
    public static final String objName = "Key";

    public OBJ_Key(GamePanel gp) {

        super(gp);
        this.gp = gp;

        name = objName;
        type = type_consumable;
        down1 = setup("/res/objects/chave", gp.tileSize, gp.tileSize);
        description = "[" + name + "]\nIt opens a door.";
        price = 100;
        stackble = true;

        setDialogue();
    }

    public void setDialogue() {
        dialogues[0][0] = "You use the " + name + " and open the door";
        dialogues[1][0] = "NO NO NO!!!";
    }

    @Override
    public boolean use(Entity entity) {

        // 1) Primeiro tenta achar uma PORTA normal
        int doorIndex = getDetected(entity, gp.obj, OBJ_Door.objName);

        if (doorIndex != 999) {
            // Abre a porta
            startDialogue(this, 0);
            gp.playeSE(3);
            gp.obj[gp.currentMap][doorIndex] = null; // remove porta do mapa
            return true;
        }

        // 2) Se não tiver porta, tenta achar a HILLUX
        int hilluxIndex = getDetected(entity, gp.obj, OBJ_BlueHeart.objName);

        if (hilluxIndex != 999) {
            // NÃO remove a Hillux, só deixa ela cuidar da lógica dela
            Entity hillux = gp.obj[gp.currentMap][hilluxIndex];
            if (hillux != null) {
                hillux.interact(); // vai chamar o interact() da Hillux
                return true;
            }
        }

        // 3) Não tinha nem porta nem Hillux na frente
        startDialogue(this, 1);
        return false;
    }
}
