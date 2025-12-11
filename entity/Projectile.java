package entity;

import main.GamePanel;

public class Projectile extends Entity{
  Entity user;
    public Projectile(GamePanel gp){
      super(gp);
    }
    public void set(int wordX, int wordY, String direction, boolean alive, Entity user){
      this.worldX = wordX;
      this.worldY = wordY;
      this.direction = direction;
      this.alive = alive;
      this.user = user;
      this.life = this.maxLife;
    }
public void update() {

    // Movimento + colisão com tile (mantém o que você já tinha)
    collisionOn = false;
    gp.cChecker.checkTile(this);
    if (collisionOn) {
        alive = false;
    }

    // === SE O DONO DO PROJÉTIL É O PLAYER, ELE TEM QUE ACERTAR MONSTROS ===
    if (user == gp.player) {
        int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);

        if (monsterIndex != 999) {
            // usa exatamente o mesmo sistema de dano do player
            gp.player.damageMonter(monsterIndex, this, attack, knokBackPower);

            // partículas do dardo acertando o monstro
            generatorParticule(this, gp.monster[gp.currentMap][monsterIndex]);

            // dardo some depois de acertar
            alive = false;
        }
    }
    // === SE NÃO FOR O PLAYER, AÍ É PROJÉTIL DE INIMIGO, ACERTA O PLAYER ===
    else {
        boolean contactPlayer = gp.cChecker.checkPlayer(this);
        if (contactPlayer && !gp.player.invencible) {
            damagePlayer(attack);
            generatorParticule(this, gp.player);
            alive = false;
        }
    }

    // Movimento
    switch (direction) {
        case "up":    worldY -= speed; break;
        case "down":  worldY += speed; break;
        case "left":  worldX -= speed; break;
        case "right": worldX += speed; break;
    }

    // Vida do projétil
    life--;
    if (life <= 0) {
        alive = false;
    }

    // Animação (se você tiver)
    spriteCounter++;
    if (spriteCounter > 12) {
        spriteNum = (spriteNum == 1) ? 2 : 1;
        spriteCounter = 0;
    }
}

    public boolean haveResource(Entity user){
        boolean haveResource = false;
        return haveResource;
    }  
    public void subtractResouce(Entity user){}
}
