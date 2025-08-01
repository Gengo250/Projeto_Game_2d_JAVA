package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import entity.Entity;
import entity.Player;
import object.SuperObject;
import tile.TileManager;

public class GamePanel  extends JPanel implements Runnable{

  final int originalTileSize = 16; //16x16 
  final int scale = 3;

  public final int tileSize = originalTileSize * scale; //48x48
  public final int maxScreenCol = 16;
  public final int maxScreenRow = 12;
  public final int screenWidth = tileSize * maxScreenCol;
  public final int screenHeight = tileSize * maxScreenRow;

  // WORLD SETTINGS
  public final int maxWorldCol = 50;
  public final int maxWorldRow = 50;

  //FPS
  int FPS = 60;

  //SYSTEM
  TileManager tileM = new TileManager(this);
  KeyHandler keyH = new KeyHandler(this);
  Sound music = new Sound();
  Sound se = new Sound();
  public CollisionChecker cChecker = new CollisionChecker(this);
  public AssetSetter aSetter = new AssetSetter(this);
  public UI ui = new UI(this);
  Thread gameThread;
 
  //ENTITY AND OBJECT
  public Player player = new Player(this,keyH);
  public SuperObject obj[] = new SuperObject[10];
  public Entity npc[] = new Entity[10];

  //GAME STATE
  public int gameState;
  public final int playState = 1;
  public final int pauseState = 2;

  public GamePanel(){
    this.setPreferredSize(new Dimension(screenWidth, screenHeight));
    this.setBackground(Color.BLACK);
    this.setDoubleBuffered(true);
    this.addKeyListener(keyH);
    this.setFocusable(true);
  }

  public void setupGame(){

    aSetter.setObject();
    aSetter.setNPC();
    playMusic(0);
    stopMusic();
    gameState = playState;
  }

  public void startGameThread(){
    gameThread = new Thread(this);
    gameThread.start();
  }
  
  @Override
  public void run(){

    double drawInterval = 1000000000/FPS;
    double delta = 0;
    long lastTime = System.nanoTime();
    long currentTime;
    long timer = 0;
    

    while(gameThread != null){

      currentTime = System.nanoTime();

      delta += (currentTime - lastTime) / drawInterval;
      timer += (currentTime - lastTime);
      lastTime = currentTime;

      if(delta>=1){
        update();
        repaint();
        delta --;
       
      }
      if(timer >= 1000000000){
        //System.out.println("FPS: "+ drawCount);
       
        timer = 0;
      }
      
    }
  }
  public void update(){

   if(gameState == playState){
    //PLAYER
    player.update();
    //NPC
    for(int i = 0; i < npc.length;i++){
      if(npc[i] != null){
        npc[i].update();
      }
    }
   }
   if(gameState == pauseState){
    //nothing
   }
   

  }
  public void paintComponent(Graphics g){
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D)g;

    //DEBUG 
    long drawStart = 0;
    if(keyH.checkDrawTime == true){
      drawStart = System.nanoTime();
    }
    

    //TILE 
    tileM.draw(g2);

    //OBJECT
    for(int i = 0; i < obj.length; i++){
      if(obj[i] != null){
        obj[i].draw(g2, this);
      }
    }
    //NPC 
    for(int i = 0; i < npc.length; i++){
      if(npc[i] != null){
        npc[i].draw(g2);
      }
    }

    //PLAYER
    player.draw(g2);

    //UI
    ui.draw(g2);

    //DEBUG
    if(keyH.checkDrawTime == true){
    long drawEnd = System.nanoTime();
    long passed = drawEnd - drawStart;
    g2.setColor(Color.white);
    g2.drawString("Draw Time: " + passed, 10, 400);
    System.out.println("Draw Time: " + passed);
    }
    g2.dispose();
    
  }
  public void playMusic(int i){
    music.setFile(i);
    music.play();
    music.loop();

  }
  public void stopMusic(){
    music.stop();
  }
  public void playeSE(int i){
    se.setFile(i);
    se.play();
  }

  
}
