package entity;

import main.GamePanel;

public class NPC_TamanduaStatue extends Entity {

    public NPC_TamanduaStatue(GamePanel gp) {
        super(gp);

        type = type_npc;
        name = "Estátua Tamanduá";

        direction = "down";
        speed = 0;

        // hitbox simples (pode ajustar se precisar)
        solidArea.x = 8;
        solidArea.y = 16;
        solidArea.width  = 32;
        solidArea.height = 32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        getImage();
        setDialogue();
    }

    private void getImage() {
        // Coloca o arquivo Estatuav.png em: res/npc/Estatuav.png
        // (sem extensão no caminho)
        down1 = setup("/res/npc/estatuav", gp.tileSize, gp.tileSize);

        // como ela é estática, usa a mesma imagem pra todas as direções
        up1 = down1;
        left1 = down1;
        right1 = down1;
        up2 = down1;
        down2 = down1;
        left2 = down1;
        right2 = down1;
    }

    private void setDialogue() {
        dialogues[0][0] =
                "Você toca na estátua do Tamanduá-bandeira...\n" +
                "Uma energia espiritual envolve seu corpo.";

        dialogues[0][1] =
                "Vida, mana e dardos foram restaurados.\n" +
                "Seu progresso foi salvo.";
    }

    @Override
    public void speak() {
        // vira pra o player e abre a janela de diálogo (retângulo preto)
        facePlayer();
        dialogueIndex = 0;
        startDialogue(this, 0);

        // impede o ataque sair junto com o ENTER
        gp.player.attackCanceled = true;

        // --- JUNTA AS FUNÇÕES DO EVENTHANDLER AQUI ---

        // 1) Recupera status (vida, mana, tira invencibilidade etc.)
        gp.player.restoreStatus();

        // 2) Recupera projéteis (usa o mesmo “máximo” que você definiu)
        gp.player.ammo = 20; // se quiser, troca esse 20 pelo valor que você usa no HUD

        // 3) Reseta monstros igual a healingPool
        gp.aSetter.setMonster();

        // 4) Salva o jogo
        gp.saveLoad.save();

        // Mensagem rápida no HUD
        gp.ui.addMessage("Status restaurado e jogo salvo!");

        // Som da cura (mesmo SE da healingPool)
        gp.playeSE(2);
    }

    @Override
    public void setAction() {
        // vazia de propósito: estátua é totalmente estática
    }

    @Override
    public void move(String direction) {
        // não se mexe quando o player aperta ENTER
    }
}
