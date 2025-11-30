package tile;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import main.GamePanel;
import main.UtilityTool;

public class TileManager {

  GamePanel gp;
  public Tile[] tile;
  public int mapTileNum[][][];
  boolean drawPath = true; //DEBUG AGRO = true -> ON || AGRO = false -> Off
  ArrayList<String> fileNames = new ArrayList<>();
  ArrayList<String> collsionsStatus = new ArrayList<>();

  public TileManager(GamePanel gp){
    this.gp = gp;

    //READ TILE DATA FILE
    InputStream is = getClass().getResourceAsStream("/res/maps/tiledata.txt");
    BufferedReader br = new BufferedReader(new InputStreamReader(is));

    // GETTING TILE NAMES AND COLLISION INFO FROM THE FILE
    String line;
    try{
      while((line = br.readLine()) != null){
        fileNames.add(line);
        collsionsStatus.add(br.readLine());
      }
      br.close();
    } catch(IOException e){
      e.printStackTrace();
    }

    // INTIALIZE THE TILE ARRAY BASED ON THE fileNames size
    tile = new Tile[fileNames.size()];
    getTileImage();

    //Get the maxWorldCol & Row
    is = getClass().getResourceAsStream("/res/maps/tocantins.txt");
    br = new BufferedReader(new InputStreamReader(is));

    try {
      String line2 = br.readLine();
      String maxTile[] = line2.split(" ");
      gp.maxWorldCol = maxTile.length;
      gp.maxWorldRow = maxTile.length;
      mapTileNum = new int[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];

      br.close();

    } catch (Exception e) {
      System.out.println("Exception!");
    }
   
    loadMap("/res/maps/tocantins.txt", 0);
    loadMap("/res/maps/mercador.txt", 1);
    loadMap("/res/maps/labirinto.txt", 2);
    loadMap("/res/maps/dungeonfinal.txt", 3);

  }
  public void getTileImage(){
    for(int i = 0; i < fileNames.size();i++){
      String fileName;
      boolean collision;

      //Get a file name
      fileName = fileNames.get(i);

      //Get a collision status
      if(collsionsStatus.get(i).equals("true")){
        collision = true;
      } else {
        collision = false;
      }
      setup(i, fileName, collision);
    }
  }
  public void setup(int index, String imageName, boolean collision){
    UtilityTool uTool = new UtilityTool();
    try{
        tile[index] = new Tile();

        String path = "/res/tiles/" + imageName.trim();
        InputStream in = TileManager.class.getResourceAsStream(path);
        if (in == null) {
            System.err.println("Tile faltando: " + path + " (verifique tiledata.txt e a pasta /res/tiles)");
            // placeholder transparente (ou reaproveite 000.png, se quiser)
            tile[index].image = new java.awt.image.BufferedImage(gp.tileSize, gp.tileSize, java.awt.image.BufferedImage.TYPE_INT_ARGB);
            tile[index].collision = collision;
            return;
        }

        tile[index].image = javax.imageio.ImageIO.read(in);
        tile[index].image = uTool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
        tile[index].collision = collision;

    } catch(Exception e){
        throw new RuntimeException("Falha ao carregar tile["+index+"]: " + imageName, e);
    }
}

 public void loadMap(String filePath, int map) {
  try {
    InputStream is = getClass().getResourceAsStream(filePath);
    BufferedReader br = new BufferedReader(new InputStreamReader(is));

    int row = 0;
    String line;

    while (row < gp.maxWorldRow && (line = br.readLine()) != null) {
      String[] numbers = line.split(" ");

      int colLimit = Math.min(numbers.length, gp.maxWorldCol);

      for (int col = 0; col < colLimit; col++) {
        int num = Integer.parseInt(numbers[col]);
        mapTileNum[map][col][row] = num;
      }

      // o resto da linha (col >= colLimit) fica com 0 (padrão), se existir
      row++;
    }

    br.close();

  } catch (Exception e) {
    e.printStackTrace(); // pelo menos mostra se der erro
  }
}

  public void draw(Graphics2D g2){

    int worldCol = 0;
    int worldRow = 0;
  
    while(worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow){
      
      int tileNum = mapTileNum[gp.currentMap][worldCol][worldRow];

      int worldX = worldCol * gp.tileSize;
      int worldY = worldRow * gp.tileSize;
      int screenX = worldX - gp.player.worldX + gp.player.screenX;
      int screenY = worldY - gp.player.worldY + gp.player.screenY;

      if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
         worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
         worldY + gp.tileSize > gp.player.worldY - gp.player.screenY && 
         worldY - gp.tileSize < gp.player.worldY + gp.player.screenY){

        g2.drawImage(tile[tileNum].image, screenX, screenY, null);
      }
      
      worldCol++;
    
      if(worldCol == gp.maxWorldCol){
        worldCol = 0;
        worldRow++;
      }
    }
    // DEBUG THE AGROO 
    if(drawPath == true){
        g2.setColor(new Color(255,0,0,70));
        for(int i = 0; i < gp.pFinder.pathList.size();i++){
          
          int worldX = gp.pFinder.pathList.get(i).col * gp.tileSize;
          int worldY = gp.pFinder.pathList.get(i).row * gp.tileSize;
          int screenX = worldX - gp.player.worldX + gp.player.screenX;
          int screenY = worldY - gp.player.worldY + gp.player.screenY;

          g2.fillRect(screenX, screenY, gp.tileSize, gp.tileSize);
        }
        
      }
  }
}