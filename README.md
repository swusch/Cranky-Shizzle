# Cranky Colonists

A **MineColonies addon mod** for **Minecraft 1.21.1** (NeoForge) that adds a **Metallurgist's Workshop** — a custom colony hut whose worker crafts Mekanism alloys using the Metallurgic Infuser.

## Features

### 🏠 Metallurgist's Workshop (Hut)
- New MineColonies building with 5 upgrade levels
- Gated behind a Technology-branch research (Level 3)
- Worker uses **Knowledge** (primary) and **Agility** (secondary) skills
- Custom block model with smithing-table aesthetic + iron chimney + redstone accents

### 👷 Metallurgist (Job)
- Dedicated colonist worker that crafts Mekanism alloys
- Only accepts recipes that use the **Metallurgic Infuser** as an intermediate crafting block
- Male and female citizen models with animated protective visor (flips down when working)

### ⚗️ Alloy Recipes
| Recipe | Inputs | Output | Intermediate |
|--------|--------|--------|-------------|
| Infused Alloy | 1× Iron Ingot + 1× Redstone | 1× Infused Alloy | Metallurgic Infuser |
| Reinforced Alloy | 1× Infused Alloy + 1× Diamond | 1× Reinforced Alloy | Metallurgic Infuser |
| Atomic Alloy | 1× Reinforced Alloy + 1× Obsidian | 1× Atomic Alloy | Metallurgic Infuser |

### 🔬 Research
- **Branch:** Technology
- **Parent:** "What Ya Need?" (Level 3)
- **Cost:** 64× Redstone + 32× Copper Ingot
- **Effect:** Unlocks the Metallurgist's Workshop building

### 📦 Hut Crafting Recipe
```
 R  I  R
 S  M  S
 R  C  R

R = Redstone
I = Iron Ingot
S = Structurize Gold Scepter
M = Metallurgic Infuser
C = Copper Ingot
```

## Dependencies

| Mod | Version | Required |
|-----|---------|----------|
| NeoForge | ≥ 21.1.216 | ✅ |
| MineColonies | ≥ 1.1.0 | ✅ |
| Structurize | ≥ 1.0.796 | ✅ |
| BlockUI | ≥ 1.0.206 | ✅ |
| Domum Ornamentum | ≥ 1.0.223 | ✅ |
| Multipiston | ≥ 1.2.58 | ✅ |
| Mekanism | ≥ 10.7.0 | Optional |

## Configuration

Configuration is available in-game via the NeoForge config GUI, or in the config file.

| Option | Default | Description |
|--------|---------|-------------|
| `metallurgist.enabled` | `true` | Enable/disable the Metallurgist Hut |
| `metallurgist.maxRecipes` | `2` | Max concurrent recipes (1–16) |
| `metallurgist.requireInfuser` | `true` | Require Metallurgic Infuser as intermediate |
| `metallurgist.requireResearch` | `true` | Require research before building |
| `general.debugLogging` | `false` | Enable verbose debug logging |

## Installation

1. Install **Minecraft 1.21.1** with **NeoForge 21.1.216+**
2. Install all required dependencies (MineColonies, Structurize, etc.)
3. Place `crankycolonists-1.0.0.jar` in your `mods/` folder
4. Launch the game

## Building from Source

### Prerequisites
- JDK 21 (recommended: [Microsoft OpenJDK](https://learn.microsoft.com/en-us/java/openjdk/download#openjdk-21))
- The dependency JARs in `libs/` (included)

### Build
```bash
./gradlew build
```
Output: `build/libs/crankycolonists-1.0.0.jar`

### Run Client (Dev)
```bash
./gradlew runClient
```

### Run Server (Dev)
```bash
./gradlew runServer
```

## Project Structure

```
CrankyColonists/
├── build.gradle                          # Build configuration
├── gradle.properties                     # Mod metadata & versions
├── settings.gradle                       # Gradle settings
├── libs/                                 # MineColonies ecosystem JARs
└── src/main/
    ├── java/com/cranky5/colonists/
    │   ├── CrankyColonists.java          # Mod entry point
    │   ├── CrankyConstants.java          # Shared constants
    │   ├── block/
    │   │   └── MetallurgistHutBlock.java # Hut block + tooltip item
    │   ├── client/
    │   │   ├── CrankyColonistsClient.java# Client-side setup
    │   │   └── model/
    │   │       ├── MaleMetallurgistModel.java
    │   │       └── FemaleMetallurgistModel.java
    │   ├── colony/building/
    │   │   ├── MetallurgistBuilding.java  # Building logic
    │   │   ├── MetallurgistBuildingView.java # Client view
    │   │   └── InfuserCraftingModule.java # Recipe filter
    │   ├── config/
    │   │   └── CrankyConfig.java          # Config spec
    │   └── registry/
    │       └── ModRegistry.java           # Unified registrations
    ├── resources/
    │   ├── META-INF/accesstransformer.cfg
    │   ├── assets/crankycolonists/        # Models, lang, blockstates
    │   └── data/                          # Recipes, loot, tags, MC data
    └── templates/
        └── META-INF/neoforge.mods.toml   # Template with property substitution
```

## Architecture Differences (vs AllTheColonists)

| Aspect | AllTheColonists | Cranky Colonists |
|--------|----------------|-----------------|
| **Registry** | Separate `ModBlocks`, `ModItems`, etc. | Unified `ModRegistry` class |
| **Crafting module** | Nested inner class in building | Standalone `InfuserCraftingModule` |
| **Config** | Flat options | Sectioned with push/pop groups |
| **Constants** | Magic strings inline | Centralized `CrankyConstants` |
| **Block item** | Standard `BlockItem` | Custom `HutBlockItem` with tooltip |
| **Naming** | "Mekanist" | "Metallurgist" (more descriptive) |
| **Block model** | 6,700-line Blockbench export | Clean vanilla-texture composite |
| **Config features** | 3 options | 5 options (+ research gate, debug) |
| **Citizen accessory** | Safety goggles | Protective visor |

## License

All Rights Reserved – Cranky5

## Feedback & Issues

Report issues on [GitHub](https://github.com/cranky5/cranky-colonists/issues)

## Versioning

- **Current:** 1.0.0
- **MC Version:** 1.21.1
- **Distribution:** CurseForge
