package entity;
import java.awt.Rectangle;
import java.util.Random;

import main.GamePanel;


public class NPC_OldMan extends Entity{
  public NPC_OldMan(GamePanel gp){
    super(gp);

    type = type_npc;
    name = "Paje"; // ou "Velho", "Pajé", o que você quise

    direction = "down";
    speed = 1;


    solidArea = new Rectangle();
    solidArea.x = 8;
    solidArea.y = 16;
    solidAreaDefaultX = solidArea.x;
    solidAreaDefaultY = solidArea.y;
    solidArea.width = 30;
    solidArea.height = 30;
    dialogueSet = -1;
    getImage();
    setDialogue();
  }
   public void getImage(){

    up1 = setup("/res/npc/pajeu01", gp.tileSize, gp.tileSize);
    up2 = setup("/res/npc/pajeu02", gp.tileSize, gp.tileSize);
    up3 = setup("/res/npc/pajeu03", gp.tileSize, gp.tileSize);
    down1 = setup("/res/npc/pajed01", gp.tileSize, gp.tileSize);
    down2 = setup("/res/npc/pajed02", gp.tileSize, gp.tileSize);
    down3 =  setup("/res/npc/pajed03", gp.tileSize, gp.tileSize);
    left1 = setup("/res/npc/pajel01", gp.tileSize, gp.tileSize);
    left2 = setup("/res/npc/pajel02", gp.tileSize, gp.tileSize);
    right1 = setup("/res/npc/pajer01", gp.tileSize, gp.tileSize);
    right2 = setup("/res/npc/pajer02", gp.tileSize, gp.tileSize);

  }
  public void setDialogue(){
    dialogues[0][0] = "As trevas estão se espalhando\npor estas terras, jovem.";
    dialogues[0][1] = "A floresta chora em silêncio…\nrios e bichos estão sofrendo.";
    dialogues[0][2] = "Um macaco ancestral foi tomado\npela fúria da corrupção.";
    dialogues[0][3] = "Você foi escolhido.\nNão tema, caminhe com coragem.";

    dialogues[1][0] = "Se o cansaço te alcançar,\ndescansa perto da água.";
    dialogues[1][1] = "Escute o som do rio,\né a voz dos ancestrais.";
    dialogues[1][2] = "As estátuas sagradas guardam\ncaminhos de viagem rápida.";
    dialogues[1][3] = "Use a força com sabedoria,\nou virará parte da escuridão.";

  }
  public void setAction(){ 
    if(onPath == true){
      int goalCol = (gp.player.worldX + gp.player.solidArea.x)/gp.tileSize;
      int goalRow = (gp.player.worldY + gp.player.solidArea.y)/gp.tileSize;
      //int goalCol = 12; -- casa dele
      //int goalRow = 9; -- casa dele

    searchPath(goalCol, goalRow);
 
  }
  else {
    actionLockCounter++;

     if (actionLockCounter == 120) {
            Random random = new Random();
            int i = random.nextInt(100) + 1; // pick up number from 1 to 100
            if (i <= 25) {
                direction = "up";
            }
            if (i > 25 && i <= 50) {
                direction = "down";
            }
            if (i > 50 && i <= 75) {
                direction = "left";
            }
            if (i > 75 && i <= 100) {
                direction = "right";
            }
            actionLockCounter = 0;
      }
  }
       
}
  public void speak(){
   facePlayer();
   startDialogue(this, dialogueSet);
   dialogueSet++;
   if(dialogues[dialogueSet][0] == null){
    dialogueSet--;
   }
  }
}