package main;

import data.Progress;
import entity.NPC_BigRock;
import entity.NPC_Merchant;
import entity.NPC_MerchantStatue;
import entity.NPC_MerchantStatue2;
import entity.NPC_OldMan;
import entity.NPC_TamanduaStatue;
import entity.NPC_TamanduaStatueBlue;
import monster.MON_Bat;
import monster.MON_GreenSlime;
import monster.MON_Monkey;
import monster.MON_Orc;
import monster.MON_PlantaCarnivora;
import monster.MON_PlantaCarnivora2;
import monster.MON_PlantaCarnivora3;
import monster.MON_SkeletonLord;
import object.OBJ_Axe;
import object.OBJ_BlueHeart;
import object.OBJ_Chest;
import object.OBJ_Door_Iron;
import object.OBJ_Zarabatana;
import tile_interactive.IT_DryTree;
import tile_interactive.IT_MetalPlate;


public class AssetSetter {
  GamePanel gp;

  public AssetSetter(GamePanel gp){
    this.gp = gp;
  }

  public void setObject(){
    
    int mapNum = 0;
    int i = 0;
    
    /* gp.obj[mapNum][i] = new OBJ_Door_Iron(gp);
    gp.obj[mapNum][i].worldX = gp.tileSize*18;
    gp.obj[mapNum][i].worldY = gp.tileSize*23;
    i++; */

    mapNum = 1;
    i = 0;

    gp.obj[mapNum][i] = new OBJ_Chest(gp);
    gp.obj[mapNum][i].setLoot(new OBJ_Zarabatana(gp));
    gp.obj[mapNum][i].worldX = gp.tileSize*26;
    gp.obj[mapNum][i].worldY = gp.tileSize*11;
    i++;

    mapNum = 2;
    i = 0;
    gp.obj[mapNum][i] = new OBJ_Door_Iron(gp);
    gp.obj[mapNum][i].worldX = gp.tileSize*66;
    gp.obj[mapNum][i].worldY = gp.tileSize*56;
    i++; 

    gp.obj[mapNum][i] = new OBJ_BlueHeart(gp);
    gp.obj[mapNum][i].worldX = gp.tileSize*18;
    gp.obj[mapNum][i].worldY = gp.tileSize*47;
    i++; 


    
    

  }
  public void setNPC(){
    int mapNum = 0;
    int i = 0;

    
    gp.npc[mapNum][i] = new NPC_TamanduaStatueBlue(gp);
    gp.npc[mapNum][i].worldX = gp.tileSize * 185; 
    gp.npc[mapNum][i].worldY = gp.tileSize * 128;
    i++;

     gp.npc[mapNum][i] = new NPC_TamanduaStatueBlue(gp);
    gp.npc[mapNum][i].worldX = gp.tileSize * 117; 
    gp.npc[mapNum][i].worldY = gp.tileSize * 42;
    i++;

    gp.npc[mapNum][i] = new NPC_TamanduaStatueBlue(gp);
    gp.npc[mapNum][i].worldX = gp.tileSize * 44; 
    gp.npc[mapNum][i].worldY = gp.tileSize * 77;
    i++;

    gp.npc[mapNum][i] = new NPC_MerchantStatue(gp);
    gp.npc[mapNum][i].worldX = gp.tileSize * 191;
    gp.npc[mapNum][i].worldY = gp.tileSize * 86;
    i++;


    //MAP 1 // Casa
    mapNum = 1;
    i = 0;
    gp.npc[mapNum][i] = new NPC_OldMan(gp);
    gp.npc[mapNum][i].worldX = gp.tileSize * 22;
    gp.npc[mapNum][i].worldY = gp.tileSize * 14;
    i++;
   
    //caverna 
    mapNum = 2;
    i = 0;
    gp.npc[mapNum][i] = new NPC_TamanduaStatue(gp);
    gp.npc[mapNum][i].worldX = gp.tileSize * 73; 
    gp.npc[mapNum][i].worldY = gp.tileSize * 54; 
    i++;

    gp.npc[mapNum][i] = new NPC_BigRock(gp);
    gp.npc[mapNum][i].worldX = gp.tileSize*20;
    gp.npc[mapNum][i].worldY = gp.tileSize*25;
    i++;
    gp.npc[mapNum][i] = new NPC_BigRock(gp);
    gp.npc[mapNum][i].worldX = gp.tileSize*11;
    gp.npc[mapNum][i].worldY = gp.tileSize*18;
    i++;
    gp.npc[mapNum][i] = new NPC_BigRock(gp);
    gp.npc[mapNum][i].worldX = gp.tileSize*23;
    gp.npc[mapNum][i].worldY = gp.tileSize*14;
    i++;

    gp.npc[mapNum][i] = new NPC_MerchantStatue2(gp);
    gp.npc[mapNum][i].worldX = gp.tileSize * 43;
    gp.npc[mapNum][i].worldY = gp.tileSize * 68;
    i++;


    //mercador
    mapNum = 3;
    i = 0;

    gp.npc[mapNum][i] = new NPC_Merchant(gp);
    gp.npc[mapNum][i].worldX = gp.tileSize * 26;
    gp.npc[mapNum][i].worldY = gp.tileSize * 18;
    i++;



  }

  public void setMonster(){
    int mapNum = 0;
    int i = 0;

    gp.monster[mapNum][i] = new MON_GreenSlime(gp);
    gp.monster[mapNum][i].worldX = gp.tileSize*73;
    gp.monster[mapNum][i].worldY = gp.tileSize*179;
    i++;

    gp.monster[mapNum][i] = new MON_GreenSlime(gp);
    gp.monster[mapNum][i].worldX = gp.tileSize*77;
    gp.monster[mapNum][i].worldY = gp.tileSize*185;
    i++;

    
    gp.monster[mapNum][i] = new MON_GreenSlime(gp);
    gp.monster[mapNum][i].worldX = gp.tileSize*81;
    gp.monster[mapNum][i].worldY = gp.tileSize*183;
    i++;

    
    gp.monster[mapNum][i] = new MON_GreenSlime(gp);
    gp.monster[mapNum][i].worldX = gp.tileSize*76;
    gp.monster[mapNum][i].worldY = gp.tileSize*170;
    i++;


    gp.monster[mapNum][i] = new MON_GreenSlime(gp);
    gp.monster[mapNum][i].worldX = gp.tileSize*67;
    gp.monster[mapNum][i].worldY = gp.tileSize*117;
    i++;

    gp.monster[mapNum][i] = new MON_GreenSlime(gp);
    gp.monster[mapNum][i].worldX = gp.tileSize*76;
    gp.monster[mapNum][i].worldY = gp.tileSize*117;
    i++;

    gp.monster[mapNum][i] = new MON_GreenSlime(gp);
    gp.monster[mapNum][i].worldX = gp.tileSize*80;
    gp.monster[mapNum][i].worldY = gp.tileSize*121;
    i++;

    gp.monster[mapNum][i] = new MON_GreenSlime(gp);
    gp.monster[mapNum][i].worldX = gp.tileSize*181;
    gp.monster[mapNum][i].worldY = gp.tileSize*112;
    i++;

    gp.monster[mapNum][i] = new MON_GreenSlime(gp);
    gp.monster[mapNum][i].worldX = gp.tileSize*186;
    gp.monster[mapNum][i].worldY = gp.tileSize*115;
    i++;

    gp.monster[mapNum][i] = new MON_GreenSlime(gp);
    gp.monster[mapNum][i].worldX = gp.tileSize*193;
    gp.monster[mapNum][i].worldY = gp.tileSize*123;
    i++;

    gp.monster[mapNum][i] = new MON_GreenSlime(gp);
    gp.monster[mapNum][i].worldX = gp.tileSize*184;
    gp.monster[mapNum][i].worldY = gp.tileSize*90;
    i++;


    gp.monster[mapNum][i] = new MON_GreenSlime(gp);
    gp.monster[mapNum][i].worldX = gp.tileSize*200;
    gp.monster[mapNum][i].worldY = gp.tileSize*92;
    i++;

    gp.monster[mapNum][i] = new MON_GreenSlime(gp);
    gp.monster[mapNum][i].worldX = gp.tileSize*105;
    gp.monster[mapNum][i].worldY = gp.tileSize*66;
    i++;

    gp.monster[mapNum][i] = new MON_GreenSlime(gp);
    gp.monster[mapNum][i].worldX = gp.tileSize*101;
    gp.monster[mapNum][i].worldY = gp.tileSize*61;
    i++;

    gp.monster[mapNum][i] = new MON_GreenSlime(gp);
    gp.monster[mapNum][i].worldX = gp.tileSize*142;
    gp.monster[mapNum][i].worldY = gp.tileSize*56;
    i++;

    gp.monster[mapNum][i] = new MON_GreenSlime(gp);
    gp.monster[mapNum][i].worldX = gp.tileSize*138;
    gp.monster[mapNum][i].worldY = gp.tileSize*51;
    i++;

    gp.monster[mapNum][i] = new MON_GreenSlime(gp);
    gp.monster[mapNum][i].worldX = gp.tileSize*141;
    gp.monster[mapNum][i].worldY = gp.tileSize*49;
    i++;

    gp.monster[mapNum][i] = new MON_GreenSlime(gp);
    gp.monster[mapNum][i].worldX = gp.tileSize*137;
    gp.monster[mapNum][i].worldY = gp.tileSize*46;
    i++;




    gp.monster[mapNum][i] = new MON_Orc(gp);
    gp.monster[mapNum][i].worldX = gp.tileSize*29;
    gp.monster[mapNum][i].worldY = gp.tileSize*82;
    i++;

    gp.monster[mapNum][i] = new MON_Orc(gp);
    gp.monster[mapNum][i].worldX = gp.tileSize*136;
    gp.monster[mapNum][i].worldY = gp.tileSize*110;
    i++;

    gp.monster[mapNum][i] = new MON_Orc(gp);
    gp.monster[mapNum][i].worldX = gp.tileSize*201;
    gp.monster[mapNum][i].worldY = gp.tileSize*131;
    i++;

    gp.monster[mapNum][i] = new MON_Orc(gp);
    gp.monster[mapNum][i].worldX = gp.tileSize*213;
    gp.monster[mapNum][i].worldY = gp.tileSize*129;
    i++;

    gp.monster[mapNum][i] = new MON_Orc(gp);
    gp.monster[mapNum][i].worldX = gp.tileSize*218;
    gp.monster[mapNum][i].worldY = gp.tileSize*115;
    i++;

    gp.monster[mapNum][i] = new MON_Orc(gp);
    gp.monster[mapNum][i].worldX = gp.tileSize*106;
    gp.monster[mapNum][i].worldY = gp.tileSize*43;
    i++;

        gp.monster[mapNum][i] = new MON_Orc(gp);
    gp.monster[mapNum][i].worldX = gp.tileSize*47;
    gp.monster[mapNum][i].worldY = gp.tileSize*83;
    i++;

        gp.monster[mapNum][i] = new MON_Orc(gp);
    gp.monster[mapNum][i].worldX = gp.tileSize*57;
    gp.monster[mapNum][i].worldY = gp.tileSize*91;
    i++;

    gp.monster[mapNum][i] = new MON_PlantaCarnivora(gp);
    gp.monster[mapNum][i].worldX = gp.tileSize * 66; 
    gp.monster[mapNum][i].worldY = gp.tileSize * 88;
    i++;

    gp.monster[mapNum][i] = new MON_PlantaCarnivora(gp);
    gp.monster[mapNum][i].worldX = gp.tileSize * 102; 
    gp.monster[mapNum][i].worldY = gp.tileSize * 117;
    i++;

    gp.monster[mapNum][i] = new MON_PlantaCarnivora(gp);
    gp.monster[mapNum][i].worldX = gp.tileSize * 118; 
    gp.monster[mapNum][i].worldY = gp.tileSize * 108;
    i++;

    gp.monster[mapNum][i] = new MON_PlantaCarnivora(gp);
    gp.monster[mapNum][i].worldX = gp.tileSize * 109; 
    gp.monster[mapNum][i].worldY = gp.tileSize * 95;
    i++;

    gp.monster[mapNum][i] = new MON_PlantaCarnivora(gp);
    gp.monster[mapNum][i].worldX = gp.tileSize * 109; 
    gp.monster[mapNum][i].worldY = gp.tileSize * 109;
    i++;

    gp.monster[mapNum][i] = new MON_PlantaCarnivora(gp);
    gp.monster[mapNum][i].worldX = gp.tileSize * 45; 
    gp.monster[mapNum][i].worldY = gp.tileSize * 85;
    i++;

    gp.monster[mapNum][i] = new MON_PlantaCarnivora(gp);
    gp.monster[mapNum][i].worldX = gp.tileSize * 105; 
    gp.monster[mapNum][i].worldY = gp.tileSize * 73;
    i++;

    gp.monster[mapNum][i] = new MON_PlantaCarnivora(gp);
    gp.monster[mapNum][i].worldX = gp.tileSize * 139; 
    gp.monster[mapNum][i].worldY = gp.tileSize * 50;
    i++;

    gp.monster[mapNum][i] = new MON_PlantaCarnivora2(gp);
    gp.monster[mapNum][i].worldX = gp.tileSize * 70; 
    gp.monster[mapNum][i].worldY = gp.tileSize * 109;
    i++;

    gp.monster[mapNum][i] = new MON_PlantaCarnivora2(gp);
    gp.monster[mapNum][i].worldX = gp.tileSize * 144; 
    gp.monster[mapNum][i].worldY = gp.tileSize * 100;
    i++;

    gp.monster[mapNum][i] = new MON_PlantaCarnivora2(gp);
    gp.monster[mapNum][i].worldX = gp.tileSize * 135; 
    gp.monster[mapNum][i].worldY = gp.tileSize * 101;
    i++;
    
    gp.monster[mapNum][i] = new MON_PlantaCarnivora2(gp);
    gp.monster[mapNum][i].worldX = gp.tileSize * 125; 
    gp.monster[mapNum][i].worldY = gp.tileSize * 66;
    i++;

    gp.monster[mapNum][i] = new MON_PlantaCarnivora3(gp);
    gp.monster[mapNum][i].worldX = gp.tileSize * 38; 
    gp.monster[mapNum][i].worldY = gp.tileSize * 78;
    i++;

    
    gp.monster[mapNum][i] = new MON_PlantaCarnivora3(gp);
    gp.monster[mapNum][i].worldX = gp.tileSize * 79; 
    gp.monster[mapNum][i].worldY = gp.tileSize * 131;
    i++;

     gp.monster[mapNum][i] = new MON_PlantaCarnivora3(gp);
    gp.monster[mapNum][i].worldX = gp.tileSize * 54; 
    gp.monster[mapNum][i].worldY = gp.tileSize * 81;
    i++;

     gp.monster[mapNum][i] = new MON_PlantaCarnivora3(gp);
    gp.monster[mapNum][i].worldX = gp.tileSize * 53; 
    gp.monster[mapNum][i].worldY = gp.tileSize * 88;
    i++;

     gp.monster[mapNum][i] = new MON_PlantaCarnivora3(gp);
    gp.monster[mapNum][i].worldX = gp.tileSize * 33; 
    gp.monster[mapNum][i].worldY = gp.tileSize * 83;
    i++;

     gp.monster[mapNum][i] = new MON_PlantaCarnivora3(gp);
    gp.monster[mapNum][i].worldX = gp.tileSize * 34; 
    gp.monster[mapNum][i].worldY = gp.tileSize * 77;
    i++;

    gp.monster[mapNum][i] = new MON_PlantaCarnivora3(gp);
    gp.monster[mapNum][i].worldX = gp.tileSize * 219; 
    gp.monster[mapNum][i].worldY = gp.tileSize * 123;
    i++;

     gp.monster[mapNum][i] = new MON_PlantaCarnivora3(gp);
    gp.monster[mapNum][i].worldX = gp.tileSize * 28; 
    gp.monster[mapNum][i].worldY = gp.tileSize * 83;
    i++;

    gp.monster[mapNum][i] = new MON_PlantaCarnivora3(gp);
    gp.monster[mapNum][i].worldX = gp.tileSize * 105; 
    gp.monster[mapNum][i].worldY = gp.tileSize * 56;
    i++;

    gp.monster[mapNum][i] = new MON_PlantaCarnivora3(gp);
    gp.monster[mapNum][i].worldX = gp.tileSize * 189; 
    gp.monster[mapNum][i].worldY = gp.tileSize * 102;
    i++;

    mapNum = 2;
    i = 0;

    gp.monster[mapNum][i] = new MON_Bat(gp);
    gp.monster[mapNum][i].worldX = gp.tileSize*64;
    gp.monster[mapNum][i].worldY = gp.tileSize*90;
    i++;

      gp.monster[mapNum][i] = new MON_Bat(gp);
    gp.monster[mapNum][i].worldX = gp.tileSize*73;
    gp.monster[mapNum][i].worldY = gp.tileSize*85;
    i++;

      gp.monster[mapNum][i] = new MON_Bat(gp);
    gp.monster[mapNum][i].worldX = gp.tileSize*82;
    gp.monster[mapNum][i].worldY = gp.tileSize*89;
    i++;

    gp.monster[mapNum][i] = new MON_Bat(gp);
    gp.monster[mapNum][i].worldX = gp.tileSize*87;
    gp.monster[mapNum][i].worldY = gp.tileSize*79;
    i++;

      gp.monster[mapNum][i] = new MON_Bat(gp);
    gp.monster[mapNum][i].worldX = gp.tileSize*80;
    gp.monster[mapNum][i].worldY = gp.tileSize*77;
    i++;

    gp.monster[mapNum][i] = new MON_Bat(gp);
    gp.monster[mapNum][i].worldX = gp.tileSize*76;
    gp.monster[mapNum][i].worldY = gp.tileSize*76;
    i++;

    gp.monster[mapNum][i] = new MON_Bat(gp);
    gp.monster[mapNum][i].worldX = gp.tileSize*69;
    gp.monster[mapNum][i].worldY = gp.tileSize*71;
    i++;

    gp.monster[mapNum][i] = new MON_Bat(gp);
    gp.monster[mapNum][i].worldX = gp.tileSize*69;
    gp.monster[mapNum][i].worldY = gp.tileSize*61;
    i++;

    gp.monster[mapNum][i] = new MON_Bat(gp);
    gp.monster[mapNum][i].worldX = gp.tileSize*77;
    gp.monster[mapNum][i].worldY = gp.tileSize*27;
    i++;

    gp.monster[mapNum][i] = new MON_Monkey(gp);
    gp.monster[mapNum][i].worldX = gp.tileSize*77;
    gp.monster[mapNum][i].worldY = gp.tileSize*27;
    i++;
    

    mapNum = 3;
    i = 0;
  
  
    

    

  }
  public void setInteractiveTile(){
    int mapNum = 0;
    int i = 0;


    mapNum= 2;
    i = 0;

    gp.iTile[mapNum][i] = new IT_MetalPlate(gp, 20, 22);i++;
    gp.iTile[mapNum][i] = new IT_MetalPlate(gp, 8, 17);i++;
    gp.iTile[mapNum][i] = new IT_MetalPlate(gp, 39, 31);i++;


  }
}
