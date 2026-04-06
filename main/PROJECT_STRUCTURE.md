# Project Structure

## Purpose

- Explain what is fixed by the assignment
- Explain what is our own design choice
- Show where the UML, CLI, and sequence diagram fit into the whole project

## What Is Fixed By The Assignment

### Game Content

- Warrior
- Wizard
- Goblin
- Wolf
- Basic Attack
- Defend
- Use Item
- Special Skill
- Potion
- Power Stone
- Smoke Bomb
- Easy / Medium / Hard
- backup spawn

### Gameplay Rules

- turn-based battle
- speed-based turn order
- one action per turn
- enemies use BasicAttack only
- HP cannot go below 0
- no draw
- special skill cooldown
- backup spawn after the initial wave is fully defeated
- CLI-based game

### Deliverables

- report
- UML class diagram
- UML sequence diagram
- GitHub commit history
- required declaration forms

## What Is Our Design Choice

- `Combatant`
- `Action`
- `StatusEffect`
- `Item`
- `TurnOrderStrategy`
- `AbstractCombatant`
- `AbstractPlayer`
- `AbstractEnemy`
- `AbstractStatusEffect`
- `Inventory`
- `CooldownTracker`
- `BattleEngine`
- `LevelFactory`
- `GameCLI`

- These are the classes and interfaces we chose to organize the assignment clearly.

## Main Structure

### Boundary / UI

- `Main`
- `GameCLI`

### Entity / Domain

- `Combatant`
- `AbstractCombatant`
- `AbstractPlayer`
- `AbstractEnemy`
- `Warrior`
- `Wizard`
- `Goblin`
- `Wolf`
- `Action`
- `StatusEffect`
- `Item`
- `Inventory`
- `Difficulty`

### Control

- `BattleEngine`
- `TurnOrderStrategy`
- `LevelFactory`

### Support Classes

- `BattleContext`
- `BattleView`
- `ActionTarget`
- `DefaultBattleContext`
- `DefaultBattleView`
- `SimpleActionTarget`
- `SimpleCooldownTracker`
- `SpeedTurnOrderStrategy`
- `BattleResult`
- `LevelSetup`

- These support classes help the design work, but they are not the main idea of the system.

## Where The UML Fits

- The UML class diagram should show the main classes and interfaces clearly.
- It does not need to emphasize every helper implementation class.
- The simplified UML should mainly highlight:
  - UI
  - engine/control
  - domain/entity classes
  - key inheritance and associations

## Where The Sequence Diagram Fits

- The sequence diagram is not the whole system.
- It should show one concrete gameplay scenario clearly.
- It should focus on:
  - boundary
  - control
  - entity interactions
- For this project, the most natural control object is `BattleEngine`.
- The most natural boundary object is `GameCLI`.

## Current Repo State

- The structure is correct for the assignment.
- The repo is still in starter/skeleton form for many implementation areas.
- The current focus is:
  - finish implementation
  - keep code aligned with the UML
  - refine the sequence diagram later if implementation changes
  - prepare the report later
