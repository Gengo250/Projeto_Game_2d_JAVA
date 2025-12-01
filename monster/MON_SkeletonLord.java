package monster;

import java.util.Random;

import data.Progress;
import entity.Entity;
import main.GamePanel;
import object.OBJ_Coin_Bronze;
import object.OBJ_Door_Iron;
import object.OBJ_Heart;
import object.OBJ_ManaCrystal;

public class MON_SkeletonLord extends Entity{
    GamePanel gp;
    public static final String monName = "Skeleton Lord";

  public MON_SkeletonLord(GamePanel gp) {
    super(gp);
    this.gp = gp;

     useAttackOffsets = false;

    type = type_monster;
    boss = true;
    name = monName;
    defualtSpeed = 1;
    speed = defualtSpeed;
    maxLife = 100;
    life = maxLife;
    attack = 13;
    defense = 4;
    exp = 500;
    knokBackPower = 5;
    sleep = true;
    // projectile = new OBJ_Rock(gp);
    int size = gp.tileSize*5;
    solidArea.x = 48;
    solidArea.y = 48;
    solidArea.width = size - 48*2;
    solidArea.height = size - 48;
    solidAreaDefaultX = solidArea.x;
    solidAreaDefaultY = solidArea.y;
    attackArea.width = 170;
    attackArea.height = 170;
    motion1_duration = 25;
    motion2_duration = 50;

    getImage();
    getAttack();
    setDialogue();
  }

  public void getImage() {

    int i = 5;
    if(inRage == false){
      up1 = setup("/res/monster/skeleton_lord/skeletonlord_up_1", gp.tileSize*i, gp.tileSize*i);
      up2 = setup("/res/monster/skeleton_lord/skeletonlord_up_2", gp.tileSize*i, gp.tileSize*i);
      down1 = setup("/res/monster/skeleton_lord/skeletonlord_down_1", gp.tileSize*i, gp.tileSize*i);
      down2 = setup("/res/monster/skeleton_lord/skeletonlord_down_2", gp.tileSize*i, gp.tileSize*i);
      left1 = setup("/res/monster/skeleton_lord/skeletonlord_left_1", gp.tileSize*i, gp.tileSize*i);
      left2 = setup("/res/monster/skeleton_lord/skeletonlord_left_2", gp.tileSize*i, gp.tileSize*i);
      right1 = setup("/res/monster/skeleton_lord/skeletonlord_right_1", gp.tileSize*i, gp.tileSize*i);
      right2 = setup("/res/monster/skeleton_lord/skeletonlord_right_2", gp.tileSize*i, gp.tileSize*i);
    }
    if(inRage == true){
      up1 = setup("/res/monster/skeleton_lord/skeletonlord_phase2_up_1", gp.tileSize*i, gp.tileSize*i);
      up2 = setup("/res/monster/skeleton_lord/skeletonlord_phase2_up_2", gp.tileSize*i, gp.tileSize*i);
      down1 = setup("/res/monster/skeleton_lord/skeletonlord_phase2_down_1", gp.tileSize*i, gp.tileSize*i);
      down2 = setup("/res/monster/skeleton_lord/skeletonlord_phase2_down_2", gp.tileSize*i, gp.tileSize*i);
      left1 = setup("/res/monster/skeleton_lord/skeletonlord_phase2_left_1", gp.tileSize*i, gp.tileSize*i);
      left2 = setup("/res/monster/skeleton_lord/skeletonlord_phase2_left_2", gp.tileSize*i, gp.tileSize*i);
      right1 = setup("/res/monster/skeleton_lord/skeletonlord_phase2_right_1", gp.tileSize*i, gp.tileSize*i);
      right2 = setup("/res/monster/skeleton_lord/skeletonlord_phase2_right_2", gp.tileSize*i, gp.tileSize*i);
    }
  }
  public void getAttack(){

    int i = 5;
    if(inRage == false){
      attackUp1 = setup("/res/monster/skeleton_lord/skeletonlord_attack_up_1", gp.tileSize*i, gp.tileSize*2*i);
      attackUp2 = setup("/res/monster/skeleton_lord/skeletonlord_attack_up_2", gp.tileSize*i, gp.tileSize*2*i);
      attackDown1 = setup("/res/monster/skeleton_lord/skeletonlord_attack_down_1", gp.tileSize*i, gp.tileSize*2*i);
      attackDown2 = setup("/res/monster/skeleton_lord/skeletonlord_attack_down_2", gp.tileSize*i, gp.tileSize*2*i);
      attackLeft1 = setup("/res/monster/skeleton_lord/skeletonlord_attack_left_1", gp.tileSize*2*i, gp.tileSize*i);
      attackLeft2 = setup("/res/monster/skeleton_lord/skeletonlord_attack_left_2", gp.tileSize*2*i, gp.tileSize*i);
      attackRight1 = setup("/res/monster/skeleton_lord/skeletonlord_attack_right_1", gp.tileSize*2*i, gp.tileSize*i);  
      attackRight2 = setup("/res/monster/skeleton_lord/skeletonlord_attack_right_2", gp.tileSize*2*i, gp.tileSize*i);
    }
    if(inRage == true){
      attackUp1 = setup("/res/monster/skeleton_lord/skeletonlord_phase2_attack_up_1", gp.tileSize*i, gp.tileSize*2*i);
      attackUp2 = setup("/res/monster/skeleton_lord/skeletonlord_phase2_attack_up_2", gp.tileSize*i, gp.tileSize*2*i);
      attackDown1 = setup("/res/monster/skeleton_lord/skeletonlord_phase2_attack_down_1", gp.tileSize*i, gp.tileSize*2*i);
      attackDown2 = setup("/res/monster/skeleton_lord/skeletonlord_phase2_attack_down_2", gp.tileSize*i, gp.tileSize*2*i);
      attackLeft1 = setup("/res/monster/skeleton_lord/skeletonlord_phase2_attack_left_1", gp.tileSize*2*i, gp.tileSize*i);
      attackLeft2 = setup("/res/monster/skeleton_lord/skeletonlord_phase2_attack_left_2", gp.tileSize*2*i, gp.tileSize*i);
      attackRight1 = setup("/res/monster/skeleton_lord/skeletonlord_phase2_attack_right_1", gp.tileSize*2*i, gp.tileSize*i);  
      attackRight2 = setup("/res/monster/skeleton_lord/skeletonlord_phase2_attack_right_2", gp.tileSize*2*i, gp.tileSize*i);
    } 
  }
  public void setDialogue(){
    dialogues[0][0] = "No one can steal my treasure!";
    dialogues[0][1] = "You will die here!";
    dialogues[0][2] = "WEALCOME TO YOUR DOOM!";
  }

  public void setAction() {

    if(inRage == false && life < maxLife/2){
      inRage = true;
      getImage();
      getAttack();
      defualtSpeed++;
      speed = defualtSpeed;
      attack *= 2;
    }

    if (getTileDistance(gp.player) < 10) {
      moveTowardPlayer(60);
    } else {
      getRandomDirection(120);
    }
      // Check if it attacks 
      if(attacking == false){
        checkAttackOrNot(60, gp.tileSize*7, gp.tileSize*5);
      }
  }

  public void damageReaction() {
    actionLockCounter = 0;

  }

  public void checkDrop() {

    gp.bossBattleON = false;
    Progress.skeletonLordDefeated = true;

    //Restore the previus music 
    gp.stopMusic();
    gp.playMusic(19);

    //remove the irons doors
    for(int i = 0; i < gp.obj[1].length;i++){
      if(gp.obj[gp.currentMap][i] != null && gp.obj[gp.currentMap][i].name.equals(OBJ_Door_Iron.objName)){
        gp.playeSE(21);
        gp.obj[gp.currentMap][i] = null;

      }
    }

    // CAST A DIE
    int i = new Random().nextInt(100) + 1;

    // SET THE MONSTER DROP
    if (i < 50) {
      dropItem(new OBJ_Coin_Bronze(gp));
    }
    if (i >= 50 && i < 75) {
      dropItem(new OBJ_Heart(gp));
    }
    if (i >= 75 && i < 100) {
      dropItem(new OBJ_ManaCrystal(gp));
    }
  }
}

