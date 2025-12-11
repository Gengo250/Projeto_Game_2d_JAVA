package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Zarabatana extends Entity {

    GamePanel gp;
    public static final String objName = "Zarabatana";

    public OBJ_Zarabatana(GamePanel gp) {
        super(gp);
        this.gp = gp;

        type = type_zarabatana;       // usa a constante que você já criou em Entity
        name = objName;

        // Ícone do item (inventário / chão)
        // Ajusta o caminho se você salvar o PNG em outro lugar/nome
        down1 = setup("/res/objects/zarabatana", gp.tileSize, gp.tileSize);

        // Status da arma (multiplicado pela força do player em getAttack())
        attackValue = 3;              // ajusta depois se quiser mais dano
        attackArea.width = gp.tileSize;
        attackArea.height = gp.tileSize;
    
        description = "[" + name + "]\nLança dardos letáis.";
        price = 200;

        knokBackPower = 1;            // empurrãozinho básico

        // Duração das fases da animação de ataque
        motion1_duration = 5;
        motion2_duration = 20;
    }
}
