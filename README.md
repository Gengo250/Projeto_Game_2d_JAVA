
# 🌿 Tocantins Legends
**2D Action-Adventure RPG in Java (custom engine, tilemap, AI, cutscenes, inventory, and bosses)**

> A study project focused on building a complete 2D engine in **pure Java**, inspired by the Tocantins biome (savanna + forest), with emphasis on **game architecture**, **OOP**, **render optimization**, and classic RPG systems.

---

<p align="center">
  <img src="assets/screens/Intro.png" width="420" />
</p>

## 🎮 What the game is about

You control a young Indigenous warrior on a journey to investigate a corruption spreading through the forest. Across trails, ruins, and arenas, you face altered creatures and bosses that demand pattern recognition and careful positioning.

**Gameplay highlights**
- Exploration through **multiple maps** (overworld, house/merchant, dungeon/maze, boss arena).
- Real-time combat with varied enemies and attack patterns.
- Progression through items, loot, consumables, and equipment.

---

## ✨ Main features already in the project

### 🧠 Engine and systems
- **Game Loop** (update + render) with FPS control
- **Tilemap** based on `.txt` files in `/res/maps` (large world and multiple areas)
- **Camera** world → screen (viewport) with optimized rendering (culling)
- **AABB collision** (tiles, objects, entities)
- **Event system** using `EventHandler` + `EventRect` (tile/area triggers)
- **Lighting / day-night cycle** with `Lighting` + `EnvironmentManager`
- **AI / Pathfinding** in `ai/` (`Node`, `PathFind`)
- **Particles** and effects (visual feedback)

### 🧰 RPG (UI, inventory, trading)
- **Inventory** + equipment + consumables
- **Merchant / Trade UI** (buy, sell, leave) with merchant NPCs
- Items and equipment (e.g. potions, shields, sword, lantern, etc.)
- **Save/load system** (`SaveLoad`, `DataStorage`, `save.data`, `config.txt`)

### 🧟 Enemies and bosses
- Common monsters (e.g. slime, orc, bat, carnivorous plant)
- Boss (**Monkey Boss** with phases and special attacks)
- **Cutscenes** with camera control, transitions, and music

---

## 🕹️ Controls

Shortcuts may vary depending on `KeyHandler.java`. In general:
- **WASD / Arrow Keys**: move
- **ENTER / E**: interact (NPC/object)
- **ESC**: menu/back

Tip: open `main/KeyHandler.java` to see the exact key mapping and adjust it to your liking.

---

## 🧱 Project structure (quick overview)

```text
📦 Projeto_Game_2d_JAVA
├─ ai/                 # Pathfinding (Node, PathFind)
├─ assets/             # Local asset organization (optional)
├─ data/               # Save/Load and progress (DataStorage, SaveLoad, Progress)
├─ entity/             # Base Entity, Player, NPCs, Projectile, Particle
├─ environment/        # Lighting, environment cycle (Lighting, EnvironmentManager)
├─ main/               # Engine (GamePanel), UI, sound, events, utilities
├─ monster/            # Monsters and bosses (Monkey, SkeletonLord, Plants, etc.)
├─ object/             # World items/objects (keys, doors, weapons, loot)
├─ tile/               # Map/Tile/TileManager
├─ tile_interactive/   # Interactive tiles (trunk, metalplate, drytree, etc.)
└─ res/                # Game resources (maps, player, npc, monster, objects, sound, tiles, ui)
````

---

## 🚀 How to run

### Requirements

* **JDK 21+** recommended (adjust according to your environment)
* IDE: IntelliJ, VS Code (Java), Eclipse

### Running from the IDE

1. Import the project as a **Java Project**
2. Run `main/Main.java`

### Running from the terminal (optional)

If the project is using preview features:

```bash
java --enable-preview -cp bin main.Main
```

> If you want to make it truly “plug and play” for any machine, the ideal next step is to add **Gradle/Maven**.

---

## 🧩 Architecture in a few lines

* `GamePanel`: game loop, update, draw, and FPS control
* `TileManager`: loads maps and draws tiles
* `CollisionChecker`: collisions (tiles/objects/entities)
* `Entity` (base): animation, health, hitbox, states
* `UI`: HUD, inventory, trade, messages, and screens
* `Sound`: music and SFX
* `CutsceneManager`: scenes, transitions, and camera
* `EventHandler`: area triggers (teleports, doors, boss events)
* `SaveLoad` + `DataStorage`: progress persistence

---

## 🗺️ Content (current and expanding)

* ✅ multiple maps and themed areas
* ✅ NPCs (merchant, shaman, statues/teleports)
* ✅ dungeon system + boss arena
* ✅ lighting cycle (day/night) with a more “bluish” style at night
* ✅ bosses with patterns and cutscenes

---

## 🎬 Cutscenes

* 🎥 [Cutscene (Introduction)](https://youtu.be/YBRMJ_EmShY)
* 🐒🔥 [Cutscene (Monkey Boss)](https://youtu.be/ictpfQeZkYM)

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

## 🤝 Contribution

1. Open an **Issue** describing the bug/suggestion
2. Create a **fork** and submit a **Pull Request**

---

## 🙌 Credits

* Study base: **RyiSnow** (YouTube)
  [![YouTube](https://img.shields.io/badge/YouTube-RyiSnow-red?logo=youtube\&logoColor=white)](https://www.youtube.com/watch?v=om59cwR7psI&list=PL_QPQmz5C6WUF-pOQDsbsKbaBZqXj4qSq)
  expanded with custom systems (playlist above)

* Code and direction: **Miguel de Castilho Gengo**
  [![GitHub](https://img.shields.io/badge/GitHub-Gengo250-181717?logo=github\&logoColor=white)](https://github.com/Gengo250)

* Sprites/SFX:
  [![GitHub](https://img.shields.io/badge/GitHub-LuccasZibordi-181717?logo=github\&logoColor=white)](https://github.com/LuccasZibordi)

---

## 📜 License

MIT. See `LICENSE`.

---

