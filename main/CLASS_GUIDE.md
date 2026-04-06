# Class Guide

## Purpose

- Explain what each folder means
- Explain what the main classes are supposed to do
- Make the project easier to read from zero knowledge

## How To Read The Project

Read the code in this order:

1. `src/Main.java`
2. `src/ui/GameCLI.java`
3. `src/api/`
4. `src/model/`
5. `src/combatants/`
6. `src/actions/`
7. `src/effects/`
8. `src/items/`
9. `src/engine/`
10. `src/level/`

## Folder Guide

### `src/ui/`

- CLI and menu flow
- prints menus and game information
- reads player input
- passes player choices into the game system

### `src/api/`

- shared interfaces and enums
- defines the common contracts the rest of the code follows
- examples:
  - `Combatant`
  - `Action`
  - `StatusEffect`
  - `Item`
  - `TurnOrderStrategy`

### `src/model/`

- abstract base classes
- support/helper classes used by the rest of the project
- examples:
  - `AbstractCombatant`
  - `AbstractPlayer`
  - `AbstractEnemy`
  - `AbstractStatusEffect`
  - `Inventory`
  - `SimpleCooldownTracker`
  - `SimpleActionTarget`
  - `SpeedTurnOrderStrategy`

### `src/combatants/`

- concrete fighters used in battle
- examples:
  - `Warrior`
  - `Wizard`
  - `Goblin`
  - `Wolf`

### `src/actions/`

- actions that can be chosen during a turn
- examples:
  - `BasicAttackAction`
  - `DefendAction`
  - `ShieldBashAction`
  - `ArcaneBlastAction`
  - `UseItemAction`

### `src/effects/`

- temporary effects that last across turns or rounds
- examples:
  - `DefendStatusEffect`
  - `StunStatusEffect`

### `src/items/`

- concrete item classes
- examples:
  - `PotionItem`
  - `PowerStoneItem`
  - `SmokeBombItem`

### `src/engine/`

- battle control flow
- examples:
  - `BattleEngine`
  - `BattleResult`
  - `DefaultBattleContext`
  - `DefaultBattleView`

### `src/level/`

- difficulty and enemy setup
- examples:
  - `Difficulty`
  - `LevelFactory`
  - `LevelSetup`

## Main Classes

### `Main`

- starts the program
- creates the CLI and begins the game flow

### `GameCLI`

- handles player-facing text interaction
- handles:
  - player selection
  - item selection
  - difficulty selection
  - start/end screens
- should stay separate from battle logic

### `Combatant`

- main interface for anything that can fight
- both players and enemies follow this contract

### `AbstractCombatant`

- shared base class for all fighters
- holds common combat state such as:
  - HP
  - attack
  - defense
  - speed
  - actions
  - status effects
  - cooldown tracker

### `AbstractPlayer`

- shared base class for player-controlled characters
- owns `Inventory`
- can hold player-specific shared behavior

### `AbstractEnemy`

- shared base class for enemies
- mainly separates enemy structure from player structure

### `Warrior`, `Wizard`, `Goblin`, `Wolf`

- concrete game characters
- each one should define:
  - its stats
  - its available actions

### `Action`

- interface for turn actions

### `BasicAttackAction`

- normal single-target attack

### `DefendAction`

- applies the defend effect

### `ShieldBashAction`

- Warrior special skill
- deals normal attack damage and stuns the target

### `ArcaneBlastAction`

- Wizard special skill
- attacks all enemies currently in battle
- increases Wizard attack if enemies are defeated by it

### `UseItemAction`

- action used when a player chooses to use an item

### `StatusEffect`

- interface for temporary effects that persist across turns or rounds

### `AbstractStatusEffect`

- shared duration-related logic for status effects

### `DefendStatusEffect`

- temporary defense boost

### `StunStatusEffect`

- temporary effect that prevents actions

### `Item`

- interface for usable items

### `Inventory`

- stores the player's items
- handles adding, listing, and consuming items

### `PotionItem`

- heals the player

### `PowerStoneItem`

- triggers one free special-skill use without changing cooldown

### `SmokeBombItem`

- makes enemy attacks deal 0 damage for the current turn and next turn

### `CooldownTracker`

- interface for tracking cooldown state

### `SimpleCooldownTracker`

- basic cooldown implementation used by combatants

### `TurnOrderStrategy`

- interface for deciding who acts first each round

### `SpeedTurnOrderStrategy`

- sorts by speed

### `BattleEngine`

- main battle control class
- should handle:
  - round flow
  - turn order
  - action execution
  - win/lose checking
  - backup spawn

### `BattleResult`

- stores final battle outcome

### `BattleContext`

- helper interface for actions and effects to access battle services

### `BattleView`

- read-only battle information for choosing actions and targets

### `ActionTarget`

- packages the chosen target or targets for an action

### `SimpleActionTarget`

- concrete wrapper used to pass target data into actions

### `Difficulty`

- difficulty level enum

### `LevelSetup`

- stores initial enemies and backup enemies

### `LevelFactory`

- builds the enemy setup for the selected difficulty
