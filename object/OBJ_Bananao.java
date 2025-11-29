package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Bananao extends Entity {
    public static final String objName = "Bananao";
    public OBJ_Bananao(GamePanel gp){
        super(gp);

        type = type_axe;
        name = objName;
        down1 = setup("/res/monster/Monkey/Banana.png", gp.tileSize, gp.tileSize);
        attackValue = 2;
        attackArea.width = 30;
        attackArea.height = 30;
        description = "[Bananao]\nGrande e grossa \npode matar qualquer ser vivo";
        price = 75;
        knokBackPower = 3;
        motion1_duration = 10;
        motion2_duration = 30;
    }
}
