package data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import entity.Entity;
import main.GamePanel;
import object.OBJ_Axe;
import object.OBJ_Boots;
import object.OBJ_Chest;
import object.OBJ_Door;
import object.OBJ_Key;
import object.OBJ_Lantern;
import object.OBJ_Potion_Red;
import object.OBJ_Shield_Blue;
import object.OBJ_Shield_Wood;
import object.OBJ_Sword_Normal;
import object.OBJ_Tent;

public class SaveLoad {
  GamePanel gp;
  public SaveLoad(GamePanel gp){
    this.gp = gp;
  }
  public Entity getObject(String itemName){
    Entity obj = null;

    switch(itemName){
      case "Woodcutter's Axe": obj = new OBJ_Axe(gp); break;
      case "Key": obj = new OBJ_Key(gp); break;
      case "Boots": obj = new OBJ_Boots(gp); break;
      case "Lantern": obj = new OBJ_Lantern(gp); break;
      case "Life Potion": obj = new OBJ_Potion_Red(gp); break;
      case "Blue Shield": obj = new OBJ_Shield_Blue(gp); break;
      case "Wood Shield": obj = new OBJ_Shield_Wood(gp); break;
      case "Normal Sword": obj = new OBJ_Sword_Normal(gp); break;
      case "Tent": obj = new OBJ_Tent(gp); break;
      case "Door": obj = new OBJ_Door(gp); break;
      case "Chest": obj = new OBJ_Chest(gp); break;
    }
    return obj;
  }
 public void save(){
    try (ObjectOutputStream oos =
            new ObjectOutputStream(new FileOutputStream(new File("save.data")))) {

        DataStorage ds = new DataStorage();

        ds.level        = gp.player.level;
        ds.maxLife      = gp.player.maxLife;
        ds.life         = gp.player.life;          // <— vida atual
        ds.mana         = gp.player.mana;
        ds.maxMana      = gp.player.maxMana;
        ds.strength     = gp.player.strength;
        ds.dexterity    = gp.player.dexterity;
        ds.exp          = gp.player.exp;
        ds.nextLevelExp = gp.player.nextLevelExp;
        ds.coin         = gp.player.coin;

        // Inventário
        for (int i = 0; i < gp.player.inventory.size(); i++) {
            Entity it = gp.player.inventory.get(i);
            if (it == null) continue;
            ds.itemNames.add(it.name);
            ds.itemAmounts.add(it.amount);
        }

        // Equipamentos
        ds.currentWeaponsSlot = gp.player.getCurrentWeaponSlot();
        ds.currentShieldSlot  = gp.player.getCurrentShieldSlot();

        // Objetos do mapa (arrays denteados)
        ds.mapObjectNames     = new String [gp.maxMap][];
        ds.mapObjectWorldX    = new int    [gp.maxMap][];
        ds.mapObjectWorldY    = new int    [gp.maxMap][];
        ds.mapObjectLootNames = new String [gp.maxMap][];
        ds.mapObjectOpened    = new boolean[gp.maxMap][];

        for (int mapNum = 0; mapNum < gp.maxMap; mapNum++) {
            int len = gp.obj[mapNum].length;
            ds.mapObjectNames    [mapNum] = new String [len];
            ds.mapObjectWorldX   [mapNum] = new int    [len];
            ds.mapObjectWorldY   [mapNum] = new int    [len];
            ds.mapObjectLootNames[mapNum] = new String [len];
            ds.mapObjectOpened   [mapNum] = new boolean[len];

            for (int i = 0; i < len; i++) {
                if (gp.obj[mapNum][i] == null) {
                    ds.mapObjectNames[mapNum][i] = "NA";      // <— MARCADOR DE VAZIO
                } else {
                    ds.mapObjectNames [mapNum][i] = gp.obj[mapNum][i].name;
                    ds.mapObjectWorldX[mapNum][i] = gp.obj[mapNum][i].worldX;
                    ds.mapObjectWorldY[mapNum][i] = gp.obj[mapNum][i].worldY;
                    ds.mapObjectLootNames[mapNum][i] =
                        (gp.obj[mapNum][i].loot != null) ? gp.obj[mapNum][i].loot.name : null;
                    ds.mapObjectOpened[mapNum][i] = gp.obj[mapNum][i].opened;
                }
            }
        }

        oos.writeObject(ds);
    } catch (Exception e) {
        System.out.println("Save Exception!");
        e.printStackTrace();
    }
}

 public void load(){
    try (ObjectInputStream ois =
            new ObjectInputStream(new FileInputStream(new File("save.data")))) {

        DataStorage ds = (DataStorage) ois.readObject();

        gp.player.level        = ds.level;
        gp.player.maxLife      = ds.maxLife;
        gp.player.life         = ds.life;            // <— restaura vida
        gp.player.mana         = ds.mana;
        gp.player.maxMana      = ds.maxMana;
        gp.player.strength     = ds.strength;
        gp.player.dexterity    = ds.dexterity;
        gp.player.exp          = ds.exp;
        gp.player.nextLevelExp = ds.nextLevelExp;
        gp.player.coin         = ds.coin;

        // Inventário
        gp.player.inventory.clear();
        for (int i = 0; i < ds.itemNames.size(); i++) {
            Entity e = getObject(ds.itemNames.get(i));
            if (e != null) {
                e.amount = ds.itemAmounts.get(i);
                gp.player.inventory.add(e);
            } else {
                System.out.println("[SaveLoad] Ignorando item inválido: " + ds.itemNames.get(i));
            }
        }

        // Equipamentos (defensivo)
        if (ds.currentWeaponsSlot >= 0 && ds.currentWeaponsSlot < gp.player.inventory.size()) {
            gp.player.currenWeapon = gp.player.inventory.get(ds.currentWeaponsSlot);
        } else {
            gp.player.currenWeapon = null;
        }
        if (ds.currentShieldSlot >= 0 && ds.currentShieldSlot < gp.player.inventory.size()) {
            gp.player.currentyShield = gp.player.inventory.get(ds.currentShieldSlot);
        } else {
            gp.player.currentyShield = null;
        }
        gp.player.getAttack();
        gp.player.getDefense();
        gp.player.getAttckImage(); // (se o nome correto for getAttackImage, ajuste aqui)

        // Objetos do mapa
        for (int mapNum = 0; mapNum < gp.maxMap; mapNum++) {
            int len = gp.obj[mapNum].length;
            for (int i = 0; i < len; i++) {

                String name = (ds.mapObjectNames != null && ds.mapObjectNames[mapNum] != null)
                              ? ds.mapObjectNames[mapNum][i] : null;

                // <<< AQUI ESTAVA O BUG: checar NOME e "NA", não o loot >>>
                if (name == null || "NA".equals(name)) {
                    gp.obj[mapNum][i] = null;   // slot vazio → não recria
                    continue;
                }

                Entity obj = getObject(name);
                if (obj == null) {             // nome não mapeado? limpa slot
                    gp.obj[mapNum][i] = null;
                    continue;
                }

                obj.worldX = ds.mapObjectWorldX[mapNum][i];
                obj.worldY = ds.mapObjectWorldY[mapNum][i];

                String lootName = (ds.mapObjectLootNames != null && ds.mapObjectLootNames[mapNum] != null)
                                  ? ds.mapObjectLootNames[mapNum][i] : null;
                if (lootName != null && !"NA".equalsIgnoreCase(lootName)) {
                    obj.loot = getObject(lootName);
                }

                obj.opened = ds.mapObjectOpened[mapNum][i];
                if (obj.opened) {
                    obj.down1 = obj.image2;    // ex.: baú aberto
                }

                gp.obj[mapNum][i] = obj;
            }
        }

    } catch (Exception e) {
        System.out.println("Load Exception!");
        e.printStackTrace();   // importante pra ver qualquer resto de bug
    }
}

}
