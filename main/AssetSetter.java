package main;

import data.Progress;
import entity.NPC_BigRock;
import entity.NPC_Merchant;
import entity.NPC_OldMan;
import monster.MON_Bat;
import monster.MON_GreenSlime;
import monster.MON_Monkey;
import monster.MON_Orc;
import monster.MON_SkeletonLord;
import object.OBJ_Axe;
import object.OBJ_BlueHeart;
import object.OBJ_Chest;
import object.OBJ_Coin_Bronze;
import object.OBJ_Door;
import object.OBJ_Door_Iron;
import object.OBJ_Heart;
import object.OBJ_Key;
import object.OBJ_Lantern;
import object.OBJ_ManaCrystal;
import object.OBJ_Pickaxe;
import object.OBJ_Potion_Red;
import object.OBJ_Shield_Blue;
import object.OBJ_Tent;
import tile_interactive.IT_DestructibleWall;
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

    mapNum = 3;
    i = 0;
    gp.obj[mapNum][i] = new OBJ_Door_Iron(gp);
    gp.obj[mapNum][i].worldX = gp.tileSize*25;
    gp.obj[mapNum][i].worldY = gp.tileSize*15;
    i++;

    gp.obj[mapNum][i] = new OBJ_BlueHeart(gp);
    gp.obj[mapNum][i].worldX = gp.tileSize*25;
    gp.obj[mapNum][i].worldY = gp.tileSize*8;
    i++;
    
    

  }
  public void setNPC(){
    int mapNum = 0;
    int i = 0;

    //MAP 1 // sala do mercador
    mapNum = 1;
    i = 0;
    gp.npc[mapNum][i] = new NPC_Merchant(gp);
    gp.npc[mapNum][i].worldX = gp.tileSize*21;
    gp.npc[mapNum][i].worldY = gp.tileSize*21;
    i++;

    mapNum = 2;
    i = 0;
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



  }

  public void setMonster(){
    int mapNum = 0;
    int i = 0;

    gp.monster[mapNum][i] = new MON_Monkey(gp);
    gp.monster[mapNum][i].worldX = gp.tileSize*81;
    gp.monster[mapNum][i].worldY = gp.tileSize*11;
    i++;

    mapNum = 2;
    i++;
    

    mapNum = 3;
    i++;
    if(Progress.skeletonLordDefeated == false){
      gp.monster[mapNum][i] = new MON_SkeletonLord(gp);
      gp.monster[mapNum][i].worldX = gp.tileSize*23;
      gp.monster[mapNum][i].worldY = gp.tileSize*16;
      i++;
    }
  
    

    

  }
  public void setInteractiveTile(){
    int mapNum = 0;
    int i = 0;
    gp.iTile[mapNum][i] = new IT_DryTree(gp, 16,40);i++;
    gp.iTile[mapNum][i] = new IT_DryTree(gp, 17, 40);i++;
    gp.iTile[mapNum][i] = new IT_DryTree(gp, 18,40);i++;
    gp.iTile[mapNum][i] = new IT_DryTree(gp, 19,40);i++;
    gp.iTile[mapNum][i] = new IT_DryTree(gp, 31,12);i++;
    gp.iTile[mapNum][i] = new IT_DryTree(gp, 32, 12);i++;
    gp.iTile[mapNum][i] = new IT_DryTree(gp, 33, 12);i++;


    mapNum= 2;
    i = 0;
  /*   gp.iTile[mapNum][i] = new IT_DestructibleWall(gp, 18,30);i++;
    gp.iTile[mapNum][i] = new IT_DestructibleWall(gp, 17, 31);i++;
    gp.iTile[mapNum][i] = new IT_DestructibleWall(gp, 17,32);i++;
    gp.iTile[mapNum][i] = new IT_DestructibleWall(gp, 17,34);i++;
    gp.iTile[mapNum][i] = new IT_DestructibleWall(gp, 18,34);i++;
    gp.iTile[mapNum][i] = new IT_DestructibleWall(gp, 18, 33);i++;
    gp.iTile[mapNum][i] = new IT_DestructibleWall(gp, 10, 22);i++;
    gp.iTile[mapNum][i] = new IT_DestructibleWall(gp, 10, 24);i++;
    gp.iTile[mapNum][i] = new IT_DestructibleWall(gp, 38, 18);i++;
    gp.iTile[mapNum][i] = new IT_DestructibleWall(gp, 38, 19);i++;
    gp.iTile[mapNum][i] = new IT_DestructibleWall(gp, 38, 20);i++;
    gp.iTile[mapNum][i] = new IT_DestructibleWall(gp, 38, 21);i++;
    gp.iTile[mapNum][i] = new IT_DestructibleWall(gp, 18, 13);i++;
    gp.iTile[mapNum][i] = new IT_DestructibleWall(gp, 18, 14);i++;
    gp.iTile[mapNum][i] = new IT_DestructibleWall(gp, 22, 28);i++;
    gp.iTile[mapNum][i] = new IT_DestructibleWall(gp, 30, 28);i++;
    gp.iTile[mapNum][i] = new IT_DestructibleWall(gp, 32, 28);i++; */

    gp.iTile[mapNum][i] = new IT_MetalPlate(gp, 20, 22);i++;
    gp.iTile[mapNum][i] = new IT_MetalPlate(gp, 8, 17);i++;
    gp.iTile[mapNum][i] = new IT_MetalPlate(gp, 39, 31);i++;


  }
}
