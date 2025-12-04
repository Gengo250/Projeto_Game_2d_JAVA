package main;

import entity.Entity;
import object.*;

public class EntityGenerator {
  GamePanel gp;
  public EntityGenerator(GamePanel gp){
    this.gp = gp;
  }
  public Entity getObject(String itemName){
    Entity obj = null;

    switch(itemName){
      case OBJ_Bananao.objName: obj = new OBJ_Bananao(gp); break;
      case OBJ_Axe.objName: obj = new OBJ_Axe(gp); break;
      case OBJ_Key.objName: obj = new OBJ_Key(gp); break;
      case OBJ_Boots.objName: obj = new OBJ_Boots(gp); break;
      case OBJ_Pickaxe.objName: obj = new OBJ_Pickaxe(gp); break;
      case OBJ_Lantern.objName: obj = new OBJ_Lantern(gp); break;
      case OBJ_Potion_Red.objName: obj = new OBJ_Potion_Red(gp); break;
      case OBJ_Shield_Blue.objName: obj = new OBJ_Shield_Blue(gp); break;
      case OBJ_Shield_Wood.objName: obj = new OBJ_Shield_Wood(gp); break;
      case OBJ_Sword_Normal.objName: obj = new OBJ_Sword_Normal(gp); break;
      case OBJ_Tent.objName: obj = new OBJ_Tent(gp); break;
      case OBJ_Door.objName: obj = new OBJ_Door(gp); break;
      case OBJ_Door_Iron.objName: obj = new OBJ_Door_Iron(gp); break;
      case OBJ_Chest.objName: obj = new OBJ_Chest(gp); break;
      case object.OBJ_Paper.objName: obj = new object.OBJ_Paper(gp); break;
      case OBJ_Zarabatana.objName: obj = new OBJ_Zarabatana(gp); break;
      
      
    }
    return obj;
  }
}
