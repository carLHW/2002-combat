# UML Class Diagram (Current Repo)

- Purpose:
  - show the current implementation as it exists in `src`
  - include the concrete support classes that the older UML omitted
- Scope:
  - all current source classes, interfaces, records, and enums are represented
  - private helpers are included where they are important to the current structure

```mermaid
classDiagram
direction TB

class Main {
  +main(args : String[]) void
}

class GameCLI {
  -ITEM_COUNT : int
  -POTION_CHOICE : int
  -POWER_STONE_CHOICE : int
  -scanner : Scanner
  +GameCLI(scanner : Scanner)
  +start() void
  -showWelcomeScreen() void
  -choosePlayerOption() int
  -chooseDifficultyOption() int
  -chooseItemChoices() int[]
  -buildPlayer(playerChoice : int) Combatant
  -difficultyFromChoice(difficultyChoice : int) Difficulty
  -applyStartingItems(player : Combatant, itemChoices : int[]) void
  -showSetupSummary(player : Combatant, difficulty : Difficulty) void
  -showStartingItems(player : Combatant) void
  -showBattleStart() void
  -showItemOptions() void
  -createItem(choice : int) Item
  -showEndScreen(result : BattleResult, player : Combatant, engine : BattleEngine) int
  -readChoice(min : int, max : int) int
}

class Combatant {
  <<interface>>
  +getName() String
  +getTeam() Team
  +getMaxHp() int
  +getCurrentHp() int
  +getAttack() int
  +getDefense() int
  +getSpeed() int
  +isAlive() boolean
  +getActions() List~Action~
  +getStatusEffects() List~StatusEffect~
  +getCooldownTracker() CooldownTracker
  +receiveDamage(amount : int) void
  +heal(amount : int) void
  +modifyAttack(delta : int) void
  +modifyDefense(delta : int) void
  +addStatusEffect(effect : StatusEffect, battleContext : BattleContext) void
  +removeExpiredEffects(battleContext : BattleContext) void
  +canAct(battleContext : BattleContext) boolean
  +chooseAction(battleView : BattleView) Action
  +chooseTarget(action : Action, battleView : BattleView, battleContext : BattleContext) ActionTarget
}

class Action {
  <<interface>>
  +getName() String
  +canExecute(user : Combatant, battleView : BattleView) boolean
  +execute(user : Combatant, target : ActionTarget) void
}

class StatusEffect {
  <<interface>>
  +getName() String
  +onApply(target : Combatant, battleContext : BattleContext) void
  +onTurnStart(target : Combatant, battleContext : BattleContext) void
  +onTurnEnd(target : Combatant, battleContext : BattleContext) void
  +onRoundEnd(target : Combatant, battleContext : BattleContext) void
  +preventsAction(target : Combatant, battleContext : BattleContext) boolean
  +isExpired() boolean
  +onExpire(target : Combatant, battleContext : BattleContext) void
}

class Item {
  <<interface>>
  +getName() String
  +canUse(user : AbstractPlayer, battleView : BattleView) boolean
  +use(user : AbstractPlayer, target : ActionTarget) void
}

class TurnOrderStrategy {
  <<interface>>
  +sort(combatants : List~Combatant~) List~Combatant~
}

class CooldownTracker {
  <<interface>>
  +isReady(key : String) boolean
  +startCooldown(key : String, turns : int) void
  +reduceCooldownOnTurnTaken() void
  +getRemainingTurns(key : String) int
}

class BattleContext {
  <<interface>>
  +log(message : String) void
  +getLivingOpponentsOf(combatant : Combatant) List~Combatant~
  +getLivingAlliesOf(combatant : Combatant) List~Combatant~
  +registerDefeat(target : Combatant, defeatedBy : Combatant) void
  +getRoundNumber() int
}

class BattleView {
  <<interface>>
  +getRoundNumber() int
  +getLivingCombatants() List~Combatant~
  +getLivingOpponentsOf(combatant : Combatant) List~Combatant~
  +getLivingAlliesOf(combatant : Combatant) List~Combatant~
}

class ActionTarget {
  <<interface>>
  +primaryTarget() Combatant
  +targets() List~Combatant~
  +context() BattleContext
}

class Team {
  <<enumeration>>
  PLAYER
  ENEMY
}

class AbstractCombatant {
  <<abstract>>
  -name : String
  -team : Team
  -maxHp : int
  -speed : int
  -actions : List~Action~
  -statusEffects : List~StatusEffect~
  -cooldownTracker : CooldownTracker
  -currentHp : int
  -attack : int
  -defense : int
  #AbstractCombatant(name : String, team : Team, maxHp : int, attack : int, defense : int, speed : int)
  #addAction(action : Action) void
  +getName() String
  +getTeam() Team
  +getMaxHp() int
  +getCurrentHp() int
  +getAttack() int
  +getDefense() int
  +getSpeed() int
  +isAlive() boolean
  +getActions() List~Action~
  +getStatusEffects() List~StatusEffect~
  +getCooldownTracker() CooldownTracker
  +receiveDamage(amount : int) void
  +heal(amount : int) void
  +modifyAttack(delta : int) void
  +modifyDefense(delta : int) void
  +addStatusEffect(effect : StatusEffect, battleContext : BattleContext) void
  +removeExpiredEffects(battleContext : BattleContext) void
  +canAct(battleContext : BattleContext) boolean
  +chooseAction(battleView : BattleView) Action
  +chooseTarget(action : Action, battleView : BattleView, battleContext : BattleContext) ActionTarget
  -isSelfTargetAction(action : Action) boolean
  -isMultiTargetAction(action : Action) boolean
}

class AbstractPlayer {
  <<abstract>>
  -sharedScanner : Scanner
  -inventory : Inventory
  -selectedItem : Item
  #AbstractPlayer(name : String, maxHp : int, attack : int, defense : int, speed : int)
  +setSharedScanner(scanner : Scanner) void
  +getInventory() Inventory
  +getSelectedItem() Item
  +clearSelectedItem() void
  +chooseAction(battleView : BattleView) Action
  +chooseTarget(action : Action, battleView : BattleView, battleContext : BattleContext) ActionTarget
  -describeActionRestriction(action : Action, battleView : BattleView) String
  -chooseSingleEnemyTarget(battleView : BattleView, battleContext : BattleContext) ActionTarget
  -hasUsableItem(battleView : BattleView) boolean
  -chooseInventoryItem(battleView : BattleView) Item
  -findSpecialSkill() Action
  -readChoice(min : int, max : int) int
}

class AbstractEnemy {
  <<abstract>>
  #AbstractEnemy(name : String, maxHp : int, attack : int, defense : int, speed : int)
  +chooseAction(battleView : BattleView) Action
  +chooseTarget(action : Action, battleView : BattleView, battleContext : BattleContext) ActionTarget
}

class AbstractStatusEffect {
  <<abstract>>
  -remainingRounds : int
  #AbstractStatusEffect(remainingRounds : int)
  #reduceRoundsByOne() void
  #getRemainingRounds() int
  +onApply(target : Combatant, battleContext : BattleContext) void
  +onTurnStart(target : Combatant, battleContext : BattleContext) void
  +onTurnEnd(target : Combatant, battleContext : BattleContext) void
  +onRoundEnd(target : Combatant, battleContext : BattleContext) void
  +preventsAction(target : Combatant, battleContext : BattleContext) boolean
  +isExpired() boolean
  +onExpire(target : Combatant, battleContext : BattleContext) void
}

class Inventory {
  -items : List~Item~
  +addItem(item : Item) void
  +getItems() List~Item~
  +clear() void
  +hasItems() boolean
  +firstUsableItem() Optional~Item~
  +consume(item : Item) void
}

class SimpleActionTarget {
  -targets : List~Combatant~
  -context : BattleContext
  +SimpleActionTarget(singleTarget : Combatant, context : BattleContext)
  +SimpleActionTarget(multipleTargets : List~Combatant~, context : BattleContext)
  +primaryTarget() Combatant
  +context() BattleContext
  +targets() List~Combatant~
}

class SimpleCooldownTracker {
  -cooldowns : Map~String, Integer~
  +isReady(key : String) boolean
  +startCooldown(key : String, turns : int) void
  +reduceCooldownOnTurnTaken() void
  +getRemainingTurns(key : String) int
}

class SpeedTurnOrderStrategy {
  +sort(combatants : List~Combatant~) List~Combatant~
}

class Warrior {
  +Warrior(name : String)
}

class Wizard {
  +Wizard(name : String)
}

class Goblin {
  +Goblin(name : String)
}

class Wolf {
  +Wolf(name : String)
}

class BasicAttackAction {
  +getName() String
  +canExecute(user : Combatant, battleView : BattleView) boolean
  +execute(user : Combatant, target : ActionTarget) void
}

class DefendAction {
  +getName() String
  +canExecute(user : Combatant, battleView : BattleView) boolean
  +execute(user : Combatant, target : ActionTarget) void
}

class ShieldBashAction {
  +getName() String
  +canExecute(user : Combatant, battleView : BattleView) boolean
  +execute(user : Combatant, target : ActionTarget) void
}

class ArcaneBlastAction {
  +getName() String
  +canExecute(user : Combatant, battleView : BattleView) boolean
  +execute(user : Combatant, target : ActionTarget) void
}

class UseItemAction {
  +getName() String
  +canExecute(user : Combatant, battleView : BattleView) boolean
  +execute(user : Combatant, target : ActionTarget) void
}

class DefendStatusEffect {
  -defenseBonus : int
  +DefendStatusEffect(defenseBonus : int)
  +getName() String
  +onApply(target : Combatant, battleContext : BattleContext) void
  +onRoundEnd(target : Combatant, battleContext : BattleContext) void
  +onExpire(target : Combatant, battleContext : BattleContext) void
}

class StunStatusEffect {
  +StunStatusEffect(turnsToSkip : int)
  +getName() String
  +preventsAction(target : Combatant, battleContext : BattleContext) boolean
  +onTurnEnd(target : Combatant, battleContext : BattleContext) void
}

class PotionItem {
  -healAmount : int
  +PotionItem(healAmount : int)
  +getName() String
  +canUse(user : AbstractPlayer, battleView : BattleView) boolean
  +use(user : AbstractPlayer, target : ActionTarget) void
}

class PowerStoneItem {
  +getName() String
  +canUse(user : AbstractPlayer, battleView : BattleView) boolean
  +use(user : AbstractPlayer, target : ActionTarget) void
  -isSpecialSkill(action : Action) boolean
}

class SmokeBombItem {
  +getName() String
  +canUse(user : AbstractPlayer, battleView : BattleView) boolean
  +use(user : AbstractPlayer, target : ActionTarget) void
}

class SmokeBombStatusEffect {
  <<private static>>
  -DEFENSE_BONUS : int
  -SmokeBombStatusEffect()
  +getName() String
  +onApply(target : Combatant, battleContext : BattleContext) void
  +onRoundEnd(target : Combatant, battleContext : BattleContext) void
  +onExpire(target : Combatant, battleContext : BattleContext) void
}

class BattleEngine {
  -turnOrderStrategy : TurnOrderStrategy
  -combatants : List~Combatant~
  -backupSpawn : List~Combatant~
  -backupSpawned : boolean
  -roundNumber : int
  +BattleEngine(turnOrderStrategy : TurnOrderStrategy, initialCombatants : List~Combatant~, backupSpawn : List~Combatant~)
  +runBattle() BattleResult
  -isBattleActive() boolean
  -hasLivingPlayers() boolean
  -hasLivingEnemies() boolean
  -determineWinner() Team
  -spawnBackupIfNeeded(context : BattleContext) void
  +getRoundNumber() int
  +getLivingCombatants() List~Combatant~
  +getLivingOpponentsOf(combatant : Combatant) List~Combatant~
  +getLivingAlliesOf(combatant : Combatant) List~Combatant~
}

class DefaultBattleView {
  -battleEngine : BattleEngine
  +DefaultBattleView(battleEngine : BattleEngine)
  +getRoundNumber() int
  +getLivingCombatants() List~Combatant~
  +getLivingOpponentsOf(combatant : Combatant) List~Combatant~
  +getLivingAlliesOf(combatant : Combatant) List~Combatant~
}

class DefaultBattleContext {
  -battleEngine : BattleEngine
  +DefaultBattleContext(battleEngine : BattleEngine)
  +log(message : String) void
  +getLivingOpponentsOf(combatant : Combatant) List~Combatant~
  +getLivingAlliesOf(combatant : Combatant) List~Combatant~
  +registerDefeat(target : Combatant, defeatedBy : Combatant) void
  +getRoundNumber() int
}

class BattleResult {
  <<record>>
  +winner() Team
  +roundsCompleted() int
}

class Difficulty {
  <<enumeration>>
  EASY
  MEDIUM
  HARD
}

class LevelFactory {
  +createLevel(difficulty : Difficulty) LevelSetup
}

class LevelSetup {
  <<record>>
  +initialEnemies() List~Combatant~
  +backupEnemies() List~Combatant~
}

note for AbstractCombatant "Encapsulation: private combat state is changed through public methods"
note for Combatant "Abstraction: common fighter contract for players and enemies"
note for Action "Polymorphism: engine executes any Action implementation"
note for AbstractPlayer "Inheritance: Warrior and Wizard reuse player behavior"
note for TurnOrderStrategy "Strategy pattern: turn ordering can be replaced"
note for LevelFactory "Factory pattern: creates level setup from difficulty"

style Main fill:#DBEAFE,stroke:#2563EB,color:#111827
style GameCLI fill:#DBEAFE,stroke:#2563EB,color:#111827

style Combatant fill:#DCFCE7,stroke:#16A34A,color:#111827
style AbstractCombatant fill:#DCFCE7,stroke:#16A34A,color:#111827
style AbstractPlayer fill:#DCFCE7,stroke:#16A34A,color:#111827
style AbstractEnemy fill:#DCFCE7,stroke:#16A34A,color:#111827
style Warrior fill:#DCFCE7,stroke:#16A34A,color:#111827
style Wizard fill:#DCFCE7,stroke:#16A34A,color:#111827
style Goblin fill:#DCFCE7,stroke:#16A34A,color:#111827
style Wolf fill:#DCFCE7,stroke:#16A34A,color:#111827
style Team fill:#DCFCE7,stroke:#16A34A,color:#111827

style Action fill:#FEF3C7,stroke:#D97706,color:#111827
style BasicAttackAction fill:#FEF3C7,stroke:#D97706,color:#111827
style DefendAction fill:#FEF3C7,stroke:#D97706,color:#111827
style ShieldBashAction fill:#FEF3C7,stroke:#D97706,color:#111827
style ArcaneBlastAction fill:#FEF3C7,stroke:#D97706,color:#111827
style UseItemAction fill:#FEF3C7,stroke:#D97706,color:#111827
style Item fill:#FEF3C7,stroke:#D97706,color:#111827
style PotionItem fill:#FEF3C7,stroke:#D97706,color:#111827
style PowerStoneItem fill:#FEF3C7,stroke:#D97706,color:#111827
style SmokeBombItem fill:#FEF3C7,stroke:#D97706,color:#111827
style StatusEffect fill:#FEF3C7,stroke:#D97706,color:#111827
style AbstractStatusEffect fill:#FEF3C7,stroke:#D97706,color:#111827
style DefendStatusEffect fill:#FEF3C7,stroke:#D97706,color:#111827
style StunStatusEffect fill:#FEF3C7,stroke:#D97706,color:#111827
style SmokeBombStatusEffect fill:#FEF3C7,stroke:#D97706,color:#111827

style TurnOrderStrategy fill:#FCE7F3,stroke:#BE185D,color:#111827
style SpeedTurnOrderStrategy fill:#FCE7F3,stroke:#BE185D,color:#111827
style BattleEngine fill:#FCE7F3,stroke:#BE185D,color:#111827
style BattleResult fill:#FCE7F3,stroke:#BE185D,color:#111827
style Difficulty fill:#FCE7F3,stroke:#BE185D,color:#111827
style LevelFactory fill:#FCE7F3,stroke:#BE185D,color:#111827
style LevelSetup fill:#FCE7F3,stroke:#BE185D,color:#111827
style DefaultBattleView fill:#FCE7F3,stroke:#BE185D,color:#111827
style DefaultBattleContext fill:#FCE7F3,stroke:#BE185D,color:#111827

style CooldownTracker fill:#F1F5F9,stroke:#64748B,color:#111827
style BattleContext fill:#F1F5F9,stroke:#64748B,color:#111827
style BattleView fill:#F1F5F9,stroke:#64748B,color:#111827
style ActionTarget fill:#F1F5F9,stroke:#64748B,color:#111827
style Inventory fill:#F1F5F9,stroke:#64748B,color:#111827
style SimpleActionTarget fill:#F1F5F9,stroke:#64748B,color:#111827
style SimpleCooldownTracker fill:#F1F5F9,stroke:#64748B,color:#111827

Main --> GameCLI
GameCLI --> BattleEngine
GameCLI --> BattleResult
GameCLI --> LevelFactory
GameCLI --> LevelSetup
GameCLI --> Difficulty
GameCLI --> Combatant
GameCLI --> AbstractPlayer
GameCLI --> Warrior
GameCLI --> Wizard
GameCLI --> PotionItem
GameCLI --> PowerStoneItem
GameCLI --> SmokeBombItem
GameCLI --> SpeedTurnOrderStrategy

Combatant <|.. AbstractCombatant
AbstractCombatant <|-- AbstractPlayer
AbstractCombatant <|-- AbstractEnemy
AbstractPlayer <|-- Warrior
AbstractPlayer <|-- Wizard
AbstractEnemy <|-- Goblin
AbstractEnemy <|-- Wolf

Action <|.. BasicAttackAction
Action <|.. DefendAction
Action <|.. ShieldBashAction
Action <|.. ArcaneBlastAction
Action <|.. UseItemAction

StatusEffect <|.. AbstractStatusEffect
AbstractStatusEffect <|-- DefendStatusEffect
AbstractStatusEffect <|-- StunStatusEffect
AbstractStatusEffect <|-- SmokeBombStatusEffect

Item <|.. PotionItem
Item <|.. PowerStoneItem
Item <|.. SmokeBombItem

TurnOrderStrategy <|.. SpeedTurnOrderStrategy
CooldownTracker <|.. SimpleCooldownTracker
ActionTarget <|.. SimpleActionTarget
BattleView <|.. DefaultBattleView
BattleContext <|.. DefaultBattleContext

AbstractCombatant *-- "1" CooldownTracker
AbstractCombatant *-- "1" SimpleCooldownTracker
AbstractCombatant o-- "0..*" Action
AbstractCombatant o-- "0..*" StatusEffect
AbstractCombatant --> "1" Team
AbstractCombatant ..> SimpleActionTarget
AbstractCombatant ..> DefendAction
AbstractCombatant ..> ArcaneBlastAction

AbstractPlayer *-- "1" Inventory
AbstractPlayer --> "0..1" Item : selected item
AbstractPlayer ..> UseItemAction
AbstractPlayer ..> DefendAction
AbstractPlayer ..> ArcaneBlastAction
AbstractPlayer ..> PowerStoneItem
AbstractPlayer ..> SimpleActionTarget

Inventory o-- "0..*" Item
SimpleActionTarget o-- "1..*" Combatant
SimpleActionTarget --> "1" BattleContext

Warrior ..> BasicAttackAction
Warrior ..> DefendAction
Warrior ..> ShieldBashAction
Warrior ..> UseItemAction
Wizard ..> BasicAttackAction
Wizard ..> DefendAction
Wizard ..> ArcaneBlastAction
Wizard ..> UseItemAction
Goblin ..> BasicAttackAction
Wolf ..> BasicAttackAction

BasicAttackAction ..> BattleView
BasicAttackAction ..> ActionTarget
DefendAction ..> DefendStatusEffect
ShieldBashAction ..> StunStatusEffect
ArcaneBlastAction ..> ActionTarget
UseItemAction ..> AbstractPlayer
UseItemAction ..> Item
PowerStoneItem ..> Action
PowerStoneItem ..> DefendAction
PowerStoneItem ..> UseItemAction
SmokeBombItem ..> SmokeBombStatusEffect

BattleEngine *-- "1" TurnOrderStrategy
BattleEngine o-- "1..*" Combatant
BattleEngine o-- "0..*" Combatant : backup spawn
BattleEngine ..> DefaultBattleView
BattleEngine ..> DefaultBattleContext
BattleEngine --> BattleResult
BattleEngine --> Team

DefaultBattleView --> "1" BattleEngine
DefaultBattleContext --> "1" BattleEngine

BattleResult --> Team
LevelFactory --> Difficulty
LevelFactory --> LevelSetup
LevelFactory ..> Goblin
LevelFactory ..> Wolf
LevelSetup o-- "0..*" Combatant : initial enemies
LevelSetup o-- "0..*" Combatant : backup enemies
```
