package tile;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import entity.Entity;
import main.GamePanel;

public class Map extends TileManager {

    GamePanel gp;
    BufferedImage[] wordMap;
    public boolean miniMapOn = false;

    // FAST TRAVEL
    public boolean fastTravelOn = false;
    public Entity fastTravelOrigin = null;
    public List<Entity> fastTravelStatues = new ArrayList<>();
    public int fastTravelCursorIndex = 0;
    private int fastTravelTick = 0;

    public Map(GamePanel gp) {
        super(gp);
        this.gp = gp;
        createWorldMap();
    }

    public void createWorldMap() {
        wordMap = new BufferedImage[gp.maxMap];

        int mapTileSize = 4; // tamanho do tile no mini/word map

        for (int i = 0; i < gp.maxMap; i++) {

            int cols = (mapCols != null && mapCols[i] > 0) ? mapCols[i] : gp.maxWorldCol;
            int rows = (mapRows != null && mapRows[i] > 0) ? mapRows[i] : gp.maxWorldRow;

            int worldMapWidth = mapTileSize * cols;
            int worldMapHeight = mapTileSize * rows;

            wordMap[i] = new BufferedImage(worldMapWidth, worldMapHeight, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = (Graphics2D) wordMap[i].getGraphics();

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

    // ================= FAST TRAVEL API ===================

    public void startFastTravelFromStatue(Entity origin) {

        fastTravelOrigin = origin;
        fastTravelStatues.clear();

        // pega todas as estátuas deste mapa que são ponto de fast travel
        for (int i = 0; i < gp.npc[gp.currentMap].length; i++) {
            Entity e = gp.npc[gp.currentMap][i];
            if (e != null && e.fastTravelPoint) {
                fastTravelStatues.add(e);
            }
        }

        if (fastTravelStatues.isEmpty()) {
            gp.ui.addMessage("Nenhuma outra estátua neste mapa.");
            fastTravelOn = false;
            return;
        }

        fastTravelOn = true;

        // cursor começa na estátua onde o player está
        fastTravelCursorIndex = 0;
        for (int i = 0; i < fastTravelStatues.size(); i++) {
            if (fastTravelStatues.get(i) == origin) {
                fastTravelCursorIndex = i;
                break;
            }
        }

        gp.gameState = gp.mapState;
    }

    public void closeFastTravel() {
        fastTravelOn = false;
        fastTravelOrigin = null;
        fastTravelStatues.clear();
        gp.gameState = gp.playState;
    }

   public void confirmFastTravelSelection() {
    if (!fastTravelOn || fastTravelStatues.isEmpty()) {
        closeFastTravel();
        return;
    }

    Entity target = fastTravelStatues.get(fastTravelCursorIndex);
    if (target == null) {
        closeFastTravel();
        return;
    }

    int baseX = target.worldX;
    int baseY = target.worldY;

    // ordem de tentativa: em frente (baixo), cima, esquerda, direita
    int[][] offsets = {
        {0,  1},   // 1) tile abaixo da estátua
        {0, -1},   // 2) tile acima
        {-1, 0},   // 3) tile à esquerda
        {1,  0}    // 4) tile à direita
    };

    // padrão: tenta embaixo
    int teleportX = baseX;
    int teleportY = baseY + gp.tileSize;

    for (int[] off : offsets) {
        int candX = baseX + off[0] * gp.tileSize;
        int candY = baseY + off[1] * gp.tileSize;

        if (canTeleportPlayerTo(candX, candY)) {
            teleportX = candX;
            teleportY = candY;
            break;
        }
    }

    gp.player.worldX = teleportX;
    gp.player.worldY = teleportY;
    gp.player.direction = "down";

    gp.playeSE(13);
    closeFastTravel();
}
private boolean canTeleportPlayerTo(int worldX, int worldY) {

    // guarda posição original do player
    int backupX = gp.player.worldX;
    int backupY = gp.player.worldY;
    boolean backupCollision = gp.player.collisionOn;

    // testa posição candidata
    gp.player.worldX = worldX;
    gp.player.worldY = worldY;
    gp.player.collisionOn = false;

    gp.cChecker.checkTile(gp.player);
    gp.cChecker.checkObject(gp.player, false);
    gp.cChecker.checkEntity(gp.player, gp.npc);
    gp.cChecker.checkEntity(gp.player, gp.iTile);

    boolean blocked = gp.player.collisionOn;

    // volta tudo pro normal
    gp.player.worldX = backupX;
    gp.player.worldY = backupY;
    gp.player.collisionOn = backupCollision;

    return !blocked;  // true = dá pra pisar aqui
}


    // ================= DESENHO MAPA MUNDI ===================

    public void drawFullMapScreen(Graphics2D g2) {

        g2.setColor(Color.black);
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        // desenha o mapa atual em 500x500 no centro
        int width = 500;
        int height = 500;
        int x = gp.screenWidth / 2 - width / 2;
        int y = gp.screenHeight / 2 - height / 2;
        g2.drawImage(wordMap[gp.currentMap], x, y, width, height, null);

        int cols = getCurrentMapCols();
        double scale = (double) (gp.tileSize * cols) / width;

        // player como referência
        int playerX = (int) (x + gp.player.worldX / scale);
        int playerY = (int) (y + gp.player.worldY / scale);
        int playerSize = (int) (gp.tileSize / scale);
        g2.drawImage(gp.player.down1, playerX, playerY, playerSize, playerSize, null);

        g2.setFont(gp.ui.vRCOSD.deriveFont(20F));
        g2.setColor(Color.white);

        if (fastTravelOn) {
            drawFastTravelIcons(g2, x, y, width, height, scale);

            String text = "Setas/WASD: mover  ENTER: viajar  M/ESC: sair";
            int textWidth = g2.getFontMetrics().stringWidth(text);
            int textX = gp.screenWidth / 2 - textWidth / 2;
            int textY = y + height + 40;
            g2.drawString(text, textX, textY);
        } else {
            g2.drawString("Press M to close", 750, 550);
        }
    }

    private void drawFastTravelIcons(Graphics2D g2,
                                     int mapX, int mapY,
                                     int width, int height,
                                     double scale) {

        fastTravelTick++;
        int floatOffset = (int) (Math.sin(fastTravelTick / 10.0) * 4);

        int markerBaseSize = (int) (gp.tileSize / scale) + 6;
        if (markerBaseSize < 8) markerBaseSize = 8;

        g2.setFont(gp.ui.vRCOSD.deriveFont((float) markerBaseSize));

        for (int i = 0; i < fastTravelStatues.size(); i++) {
            Entity e = fastTravelStatues.get(i);
            if (e == null) continue;

            int iconX = (int) (mapX + e.worldX / scale);
            int iconY = (int) (mapY + e.worldY / scale) - markerBaseSize + floatOffset;

            // "!" flutuando
            g2.setColor(Color.black);
            g2.drawString("!", iconX + 2, iconY + 2);
            g2.setColor(Color.cyan);
            g2.drawString("!", iconX, iconY);

            // seleção atual
            if (i == fastTravelCursorIndex) {
                int boxSize = markerBaseSize + 4;
                int boxX = iconX - boxSize / 4;
                int boxY = iconY - boxSize;
                g2.setColor(Color.white);
                g2.drawRect(boxX, boxY, boxSize, boxSize);
            }
        }
    }

    // ================== MINIMAPA (igual antes) ===================

    public void drawMiniMap(Graphics2D g2) {
        if (!miniMapOn) return;

        //Draw map
        int width = 150;
        int height = 150;
        int x = gp.screenWidth - width - 50;
        int y = 50;
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f));
        g2.drawImage(wordMap[gp.currentMap], x, y, width, height, null);

        // Draw Player
        int cols = getCurrentMapCols();
        double scale = (double) (gp.tileSize * cols) / width;

        int playerX = (int) (x + gp.player.worldX / scale);
        int playerY = (int) (y + gp.player.worldY / scale);
        int playerSize = (int) (gp.tileSize / scale);
        g2.drawImage(gp.player.down1, playerX, playerY, playerSize, playerSize, null);

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }
}
