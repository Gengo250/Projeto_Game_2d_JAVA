package entity;

import java.awt.Rectangle;

import main.GamePanel;
import object.OBJ_Axe;
import object.OBJ_Key;
import object.OBJ_Paper_mercador;
import object.OBJ_Potion_Red;
import object.OBJ_Shield_Blue;
import object.OBJ_Tent;
import object.OBJ_Ugabuga;

public class NPC_Merchant extends Entity{
   public NPC_Merchant(GamePanel gp){
    super(gp);

    direction = "down";
    speed = 1;

    solidArea = new Rectangle();
    solidArea.x = 8;
    solidArea.y = 16;
    solidAreaDefaultX = solidArea.x;
    solidAreaDefaultY = solidArea.y;
    solidArea.width = 32;
    solidArea.height = 32;

    getImage();
    setDialogue();
    setItems();
  }
   public void getImage(){

    up1 = setup("/res/npc/mercador_down_1", gp.tileSize, gp.tileSize);
    up2 = setup("/res/npc/mercador_down_2", gp.tileSize, gp.tileSize);
    down1 = setup("/res/npc/mercador_down_1", gp.tileSize, gp.tileSize);
    down2 = setup("/res/npc/mercador_down_2", gp.tileSize, gp.tileSize);
    left1 = setup("/res/npc/mercador_down_1", gp.tileSize, gp.tileSize);
    left2 = setup("/res/npc/mercador_down_2", gp.tileSize, gp.tileSize);
    right1 = setup("/res/npc/mercador_down_1", gp.tileSize, gp.tileSize);
    right2 = setup("/res/npc/mercador_down_2", gp.tileSize, gp.tileSize);

  }
  public void setDialogue(){
dialogues[0][0] = "Hehe, então você me encontrou.\n"
                + "Tenho ótimos itens da floresta.\n"
                + "Quer fazer negócio?";

dialogues[1][0] = "Volte sempre, guerreiro.\n"
                + "A selva sempre muda... e meu estoque também.";

dialogues[2][0] = "Você não tem moedas suficientes.\n"
                + "Precisa juntar mais antes de comprar isso.";

dialogues[3][0] = "Você não pode carregar mais nada.\n"
                + "Seu inventário está cheio.";

dialogues[4][0] = "Você não pode vender\n"
                + "um item que está equipado!";

  }
  public void setItems(){
    inventory.add(new OBJ_Potion_Red(gp));
    inventory.add(new OBJ_Key(gp));
    inventory.add(new OBJ_Axe(gp));
    inventory.add(new OBJ_Potion_Red(gp));
    inventory.add(new OBJ_Paper_mercador(gp));
    inventory.add(new OBJ_Tent(gp));
    inventory.add(new OBJ_Ugabuga(gp));
    inventory.add(new OBJ_Key(gp));
  }
  public void speak(){
    facePlayer();
    gp.gameState = gp.tradeState;
    gp.ui.npc = this;
  }
}
