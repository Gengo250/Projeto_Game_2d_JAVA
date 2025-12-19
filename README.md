# 🌿 Tocantins Legends
**2D Action-Adventure RPG em Java (engine própria, tilemap, IA, cutscenes, inventário e bosses)**

> Projeto de estudo e construção de uma engine 2D completa em **Java puro**, inspirado no bioma do Tocantins (cerrado + floresta), com foco em **arquitetura de jogo**, **POO**, **otimização de render** e sistemas clássicos de RPG.

---

<p align="center">
  <img src="assets/screens/Intro.png" width="420" />
</p>

## 🎮 O que é o jogo

Você controla um jovem guerreiro indígena em uma jornada para investigar uma corrupção que se espalha pela mata. Entre trilhas, ruínas e arenas, você enfrenta criaturas alteradas e bosses que exigem leitura de padrão e posicionamento.

**Destaques do gameplay**
- Exploração por **múltiplos mapas** (mundo externo, casa/mercador, dungeon/labirinto, arena de boss).
- Combate em tempo real com inimigos variados e padrões de ataque.
- Progressão por itens, loot, consumíveis e equipamentos.

---

## ✨ Principais features já no projeto

### 🧠 Engine e sistemas
- **Game Loop** (update + render) com controle de FPS
- **Tilemap** por arquivos `.txt` em `/res/maps` (mundo grande e múltiplas áreas)
- **Câmera** mundo → tela (viewport) com renderização otimizada (culling)
- **Colisão AABB** (tiles, objetos, entidades)
- **Sistema de eventos** por `EventHandler` + `EventRect` (gatilhos por tile/área)
- **Iluminação / dia-noite** com `Lighting` + `EnvironmentManager`
- **IA / Pathfinding** em `ai/` (`Node`, `PathFind`)
- **Partículas** e efeitos (feedback visual)

### 🧰 RPG (UI, inventário, comércio)
- **Inventário** + equipamentos + consumíveis
- **Merchant / Trade UI** (buy, sell, leave) com NPCs de mercador
- Itens e equipamentos (ex.: poções, escudos, espada, lanterna, etc.)
- **Sistema de save/load** (`SaveLoad`, `DataStorage`, `save.data`, `config.txt`)

### 🧟 Inimigos e bosses
- Monstros comuns (ex.: slime, orc, bat, planta carnívora)
- Boss (**Monkey Boss** com fases e ataques especiais)
- **Cutscenes** com controle de câmera, transições e música

---

## 🕹️ Controles

Os atalhos podem variar conforme o `KeyHandler.java`. Em geral:
- **WASD / Setas**: mover
- **ENTER / E**: interagir (NPC/objeto)
- **ESC**: menu/voltar

Dica: abra `main/KeyHandler.java` para ver o mapeamento exato e ajustar ao seu gosto.

---

## 🧱 Estrutura do projeto (visão rápida)

```text
📦 Projeto_Game_2d_JAVA
├─ ai/                 # Pathfinding (Node, PathFind)
├─ assets/             # Organização local de assets (opcional)
├─ data/               # Save/Load e progresso (DataStorage, SaveLoad, Progress)
├─ entity/             # Entity base, Player, NPCs, Projectile, Particle
├─ environment/        # Iluminação, ciclo de ambiente (Lighting, EnvironmentManager)
├─ main/               # Engine (GamePanel), UI, som, eventos, utilitários
├─ monster/            # Monstros e bosses (Monkey, SkeletonLord, Plantas, etc.)
├─ object/             # Itens/objetos do mundo (chaves, portas, armas, loot)
├─ tile/               # Map/Tile/TileManager
├─ tile_interactive/   # Tiles interativos (trunk, metalplate, drytree, etc.)
└─ res/                # Recursos do jogo (maps, player, npc, monster, objects, sound, tiles, ui)
```

---

## 🚀 Como rodar

### Requisitos
- **JDK 21+** recomendado (ajuste conforme seu ambiente)
- IDE: IntelliJ, VS Code (Java), Eclipse

### Rodando pela IDE
1. Importe o projeto como **Java Project**
2. Rode `main/Main.java`

### Rodando via terminal (opcional)
Se o projeto estiver usando preview features:
```bash
java --enable-preview -cp bin main.Main
```

> Se você quiser deixar “plug and play” para qualquer máquina, o próximo passo ideal é adicionar **Gradle/Maven**.

---

## 🧩 Arquitetura em poucas linhas

- `GamePanel`: loop do jogo, update, draw e controle de FPS
- `TileManager`: carrega mapas e desenha tiles
- `CollisionChecker`: colisões (tiles/objetos/entidades)
- `Entity` (base): animação, vida, hitbox, estados
- `UI`: HUD, inventário, trade, mensagens e telas
- `Sound`: música e SFX
- `CutsceneManager`: cenas, transições e câmera
- `EventHandler`: gatilhos por área (teleportes, portas, boss events)
- `SaveLoad` + `DataStorage`: persistência de progresso

---

## 🗺️ Conteúdo (atual e em expansão)

- ✅ múltiplos mapas e áreas temáticas
- ✅ NPCs (mercador, pajé, estátuas/teleportes)
- ✅ sistema de dungeon + arena de boss
- ✅ ciclo de iluminação (dia/noite) com estilo mais “azulado” à noite
- ✅ bosses com padrões e cutscenes

---

## 🎬 Cutscenes

- 🎥 [Cutscene (Introdução)](https://youtu.be/YBRMJ_EmShY)
- 🐒🔥 [Cutscene (Boss Macaco)](https://youtu.be/ictpfQeZkYM)

## 📸 Screenshots

<p align="center">
  <img src="assets/screens/01_home_interior.png" width="420" />
  <img src="assets/screens/02_world_map_press_m.png" width="420" />
</p>
<p align="center">
  <img src="assets/screens/03_status_inventory_dusk.png" width="420" />
  <img src="assets/screens/04_combat_plants_dusk.png" width="420" />
</p>
<p align="center">
  <img src="assets/screens/05_dungeon_lighting_dusk.png" width="420" />
  <img src="assets/screens/06_statue_tamandua_dialogue.png" width="420" />
</p>
<p align="center">
  <img src="assets/screens/07_puzzle_twos_complement.png" width="420" />
  <img src="assets/screens/08_boss_monkey_phase1_close.png" width="420" />
</p>
<p align="center">
  <img src="assets/screens/09_boss_monkey_beam_attack.png" width="420" />
  <img src="assets/screens/10_boss_monkey_phase2_spin.png" width="420" />
</p>
<p align="center">
  <img src="assets/screens/11_boss_dialogue_murmurio.png" width="420" />
  <img src="assets/screens/12_merchant_house_dusk.png" width="420" />
</p>
<p align="center">
  <img src="assets/screens/13_merchant_dialogue_menu.png" width="420" />
  <img src="assets/screens/14_trade_screen_buy.png" width="420" />
</p>
<p align="center">
  <img src="assets/screens/15_inventory_key_description.png" width="420" />
  <img src="assets/screens/16_overworld_dusk_minimap.png" width="420" />
</p>
<p align="center">
  <img src="assets/screens/17_fast_travel_map.png" width="420" />
  <img src="assets/screens/18_fast_travel_statue.png" width="420" />
</p>
<p align="center">
  <img src="assets/screens/19_merchant_statue_night.png" width="420" />
  <img src="assets/screens/20_hilux_night.png" width="420" />
</p>
<p align="center">
  <img src="assets/screens/21_title_screen_tocantins_legends.png" width="420" />
  <img src="assets/screens/22_credits_screen.png" width="420" />
</p>




## 🤝 Contribuição

1. Abra uma **Issue** descrevendo bug/sugestão
2. Faça um **fork** e envie um **Pull Request**

---

## 🙌 Créditos

- Base de estudo: **RyiSnow** (YouTube)  
  [![YouTube](https://img.shields.io/badge/YouTube-RyiSnow-red?logo=youtube&logoColor=white)](https://www.youtube.com/watch?v=om59cwR7psI&list=PL_QPQmz5C6WUF-pOQDsbsKbaBZqXj4qSq)  
  expandido com sistemas próprios (playlist acima)

- Código e direção: **Miguel de Castilho Gengo**  
  [![GitHub](https://img.shields.io/badge/GitHub-Gengo250-181717?logo=github&logoColor=white)](https://github.com/Gengo250)

- Sprites/SFX:  
  [![GitHub](https://img.shields.io/badge/GitHub-LuccasZibordi-181717?logo=github&logoColor=white)](https://github.com/LuccasZibordi)


---

## 📜 Licença

MIT. Veja `LICENSE`.

---

### ⭐ Se isso te ajudou
Dá uma estrela no repositório e, se você curte game-dev em Java, bora trocar ideia (issues e PRs são bem-vindos).
