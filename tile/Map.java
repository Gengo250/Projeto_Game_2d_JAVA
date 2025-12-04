package tile;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import main.GamePanel;

public class Map extends TileManager {
  GamePanel gp;
  BufferedImage wordMap[];
  public boolean miniMapOn = false;

  public Map(GamePanel gp){
    super(gp);
    this.gp = gp;
    createWorldMap();
  }
public void createWorldMap() {
    wordMap = new BufferedImage[gp.maxMap];

    int mapTileSize = 4; // tamanho do tile no minimapa

    for (int i = 0; i < gp.maxMap; i++) {

        // pega tamanho real; se não tiver, cai pro maxWorld
        int cols = (mapCols != null && mapCols[i] > 0) ? mapCols[i] : gp.maxWorldCol;
        int rows = (mapRows != null && mapRows[i] > 0) ? mapRows[i] : gp.maxWorldRow;

        int worldMapWidth  = mapTileSize * cols;
        int worldMapHeight = mapTileSize * rows;

        wordMap[i] = new BufferedImage(worldMapWidth, worldMapHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = (Graphics2D) wordMap[i].createGraphics();

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                int tileNum = mapTileNum[i][col][row];

                int x = mapTileSize * col;
                int y = mapTileSize * row;

                g2.drawImage(tile[tileNum].image, x, y, mapTileSize, mapTileSize, null);
            }
        }

        g2.dispose();
    }
}

private int getCurrentMapCols() {
    if (mapCols != null && mapCols[gp.currentMap] > 0) {
        return mapCols[gp.currentMap];
    }
    return gp.maxWorldCol;
}


  public void drawFullMapScreen(Graphics2D g2){
    g2.setColor(Color.black);
    g2.fillRect(0, 0, gp.screenWidth,gp.screenHeight);

    //Draw Map
    int width = 500;
    int height = 500;
    int x = gp.screenWidth/2 - width/2;
    int y = gp.screenHeight/2 - height/2;
    g2.drawImage(wordMap[gp.currentMap], x, y, width, height, null);

    // Draw Player
    int cols = getCurrentMapCols();
    double scale = (double)(gp.tileSize * cols) / width;

    int playerX = (int)(x + gp.player.worldX / scale);
    int playerY = (int)(y + gp.player.worldY / scale);
    int playerSize = (int)(gp.tileSize / scale);
    g2.drawImage(gp.player.down1, playerX, playerY, playerSize, playerSize, null);

    //Hit 
    g2.setFont(gp.ui.vRCOSD.deriveFont(20F));
    g2.setColor(Color.white);
    g2.drawString("Press M to close", 750, 550);
  }
  public void drawMiniMap(Graphics2D g2){
    if(miniMapOn == true){
      //Draw map
      int width = 150;
      int height = 150;
      int x = gp.screenWidth - width - 50;
      int y = 50;
      g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f));
      g2.drawImage(wordMap[gp.currentMap], x, y, width, height, null);

      // Draw Player
     int cols = getCurrentMapCols();
     double scale = (double)(gp.tileSize * cols) / width;

      int playerX = (int)(x + gp.player.worldX / scale);
      int playerY = (int)(y + gp.player.worldY / scale);
      int playerSize = (int)(gp.tileSize / scale);
      g2.drawImage(gp.player.down1, playerX, playerY, playerSize, playerSize, null);

      g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

    }
  }
}
