# Suggested Team Split

This split is meant to keep the work practical and reduce code conflicts.

## Team Members

- Person 1: Mengrui
- Person 2: Pei Yuan
- Person 3: Yao Wen
- Person 4: Haitong
- Person 5: Carissa

## General Rule

- Mainly edit your own files.
- Avoid editing someone else's files unless the team agrees first.
- Keep shared class names and method names stable once coding starts.
- Push small commits often.

## Simple Role Summary

### Person 1: CLI, Setup, Core Design API

- Main work: menus, setup screens, core interfaces, and project coordination

### Person 2: Base Classes, Support API

- Main work: abstract base classes and support-side API files
- Most important first step: finish the abstract classes

### Person 3: Combatants, Core Actions

- Main work: Warrior, Wizard, Goblin, Wolf, and their attacks / skills

### Person 4: Items, Effects, Cooldowns

- Main work: inventory, Potion, Power Stone, Smoke Bomb, Defend effect, Stun effect, cooldown rules

### Person 5: Battle Flow, Levels, Integration

- Main work: battle loop, turn order, backup spawn, difficulty setup, win / lose condition

## File Ownership

### Person 1: CLI, Setup Flow, Core Design API, Docs

- Main responsibility:
  - user-facing setup flow
  - core design direction
  - keeping the project structure consistent
- Files:
  - `src/`
    - `Main.java`

  - `src/ui/`
    - `GameCLI.java`

  - `src/api/`
    - `Action.java`
    - `Combatant.java`
    - `Item.java`
    - `StatusEffect.java`
    - `TurnOrderStrategy.java`

  - `main/`
    - docs and UML files

  - root:
    - `README.md`

- What to implement:
  - welcome screen
  - choose player
  - choose 2 items
  - choose difficulty
  - battle start screen
  - victory / defeat screen
  - replay / new game / exit flow
  - core design-shaping interfaces

### Person 2: Base Classes, Support API

- Main responsibility:
  - shared foundation under the agreed design
- Files:
  - `src/model/`
    - `AbstractCombatant.java`
    - `AbstractEnemy.java`
    - `AbstractPlayer.java`
    - `AbstractStatusEffect.java`

  - `src/api/`
    - `ActionTarget.java`
    - `BattleContext.java`
    - `BattleView.java`
    - `CooldownTracker.java`
    - `Team.java`

- What to implement:
  - abstract/base classes first
  - shared combatant state
  - player vs enemy shared structure
  - shared status-effect base behavior
  - support API contracts if refinement is needed
- Notes:
  - abstract classes are the main implementation work here
  - support API files are mostly lightweight contract files
  - the starter structure already exists, so others can still begin in parallel

### Person 3: Combatants, Core Actions

- Main responsibility:
  - concrete characters
  - normal attacks and special skills
- Files:
  - `src/combatants/`
    - `Goblin.java`
    - `Warrior.java`
    - `Wizard.java`
    - `Wolf.java`

  - `src/actions/`
    - `ArcaneBlastAction.java`
    - `BasicAttackAction.java`
    - `DefendAction.java`
    - `ShieldBashAction.java`

- What to implement:
  - character stats
  - action lists for each character
  - damage rules
  - Warrior special skill
  - Wizard special skill
  - enemy basic attack behavior

### Person 4: Items, Inventory, Effects, Cooldowns

- Main responsibility:
  - temporary effects
  - item behavior
  - inventory and cooldown handling
- Files:
  - `src/actions/`
    - `UseItemAction.java`

  - `src/effects/`
    - `DefendStatusEffect.java`
    - `StunStatusEffect.java`

  - `src/items/`
    - `PotionItem.java`
    - `PowerStoneItem.java`
    - `SmokeBombItem.java`

  - `src/model/`
    - `Inventory.java`
    - `SimpleCooldownTracker.java`

- What to implement:
  - defend duration
  - stun duration
  - item use
  - Potion, Power Stone, Smoke Bomb logic
  - inventory storage and consumption
  - special skill cooldown tracking
- Notes:
  - some files in this part are naturally short
  - the real work is still the item logic, effect timing, inventory behavior, and cooldown behavior

### Person 5: Battle Engine, Levels, Turn Order, Integration

- Main responsibility:
  - overall battle flow
  - difficulty setup
  - integration support
- Files:
  - `src/engine/`
    - `BattleEngine.java`
    - `BattleResult.java`
    - `DefaultBattleContext.java`
    - `DefaultBattleView.java`

  - `src/level/`
    - `Difficulty.java`
    - `LevelFactory.java`
    - `LevelSetup.java`

  - `src/model/`
    - `SimpleActionTarget.java`
    - `SpeedTurnOrderStrategy.java`

- What to implement:
  - round loop
  - turn order based on speed
  - apply status effects during battle
  - backup spawn
  - win / lose condition
  - Easy / Medium / Hard setup
  - integration help
- Notes:
  - some helper records and wrappers are naturally short
  - the real work is still the battle loop, level flow, turn order logic, and integration logic

## Do Not Change These Method Names

- `BattleEngine.runBattle()`
- `Action.execute(Combatant user, ActionTarget target)`
- `Action.canExecute(Combatant user, BattleView battleView)`
- `Combatant.chooseAction(BattleView battleView)`
- `Combatant.chooseTarget(Action action, BattleView battleView, BattleContext battleContext)`
- `Item.use(AbstractPlayer user, ActionTarget target)`
- `TurnOrderStrategy.sort(List<Combatant> combatants)`

## If Someone Finishes Early

- manual testing
- bug fixing
- sequence diagram refinement
- report explanation
- integration problems
