# Turn-Based Combat Arena Report

## a) UML Class Diagram

The detailed UML class diagram is included in the project as both Mermaid source and exported images:

- Source: `main/UML_Class_Diagram/markdown/UML-class-diagram-full.md`
- SVG export: `main/UML_Class_Diagram/svg/UML-class-diagram-full.svg`
- PNG export: `main/UML_Class_Diagram/png/UML-class-diagram-full.png`

The diagram represents the current Java implementation in `src`. It shows the boundary class (`GameCLI`), control classes (`BattleEngine`, `LevelFactory`, `DefaultBattleView`, `DefaultBattleContext`), entity/domain classes (`Combatant`, `AbstractCombatant`, `Warrior`, `Wizard`, `Goblin`, `Wolf`), actions, items, status effects, and support classes.

The main relationships shown are inheritance, interface implementation, composition, aggregation, and dependency. For example, `AbstractPlayer` and `AbstractEnemy` inherit from `AbstractCombatant`, while `Warrior` and `Wizard` inherit from `AbstractPlayer`. The `Action`, `Item`, `StatusEffect`, `TurnOrderStrategy`, `CooldownTracker`, `BattleView`, `BattleContext`, and `ActionTarget` interfaces are implemented by concrete classes. Composition is used where an object strongly owns another object, such as `AbstractPlayer` owning an `Inventory` and `AbstractCombatant` owning a `SimpleCooldownTracker`.

OO principle annotations are placed directly in the class diagram. The diagram highlights encapsulation in `AbstractCombatant`, abstraction in `Combatant`, polymorphism in `Action`, inheritance in the player branch, the Strategy pattern in `TurnOrderStrategy`, and the Factory pattern in `LevelFactory`.

## b) UML Sequence Diagram

The detailed UML sequence diagram is included as both Mermaid source and exported images:

- Source: `main/UML_Sequence_Diagram/markdown/battle-sequence-diagram.md`
- SVG export: `main/UML_Sequence_Diagram/svg/battle-sequence-diagram.svg`
- PNG export: `main/UML_Sequence_Diagram/png/battle-sequence-diagram.png`

The sequence diagram shows a complete game flow from program start to final battle result. It includes the player, `Main`, `GameCLI`, `LevelFactory`, `LevelSetup`, `BattleEngine`, `SpeedTurnOrderStrategy`, `DefaultBattleView`, `DefaultBattleContext`, combatants, actions, targets, status effects, cooldowns, and `BattleResult`.

The diagram uses loop and alternative fragments to show repeated battle rounds, per-combatant turns, player and enemy action selection, action execution, defeated target handling, status-effect application, cooldown reduction, backup enemy spawning, and final result reporting.

## c) Additional Features and Functionalities

Based on `SC2002 Turn-based Combat Assignment v3.pdf`, the following gameplay items are assignment requirements rather than additional features: Warrior, Wizard, Goblin, Wolf, Basic Attack, Defend, Use Item, Special Skill, Potion, Power Stone, Smoke Bomb, Easy / Medium / Hard difficulty, speed-based turn order, special-skill cooldown, backup spawn, victory / defeat screens, and the option to replay with the same settings, start a new game, or exit. Therefore, these are not claimed as extra features in this report.

No major extra gameplay mode is claimed beyond the assignment scope. Instead, the additional work is mainly in usability, robustness, and extensible structure around the required game.

Additional usability and robustness features include:

- Setup summary before battle: after selecting character, items, and difficulty, the CLI prints a clear summary before the battle starts.
- Input validation loops: menu choices are repeatedly requested until the player enters a valid number within the allowed range.
- Action availability feedback: player action menus show cooldown state and when item use is unavailable.
- Detailed battle logging: action logs show HP changes, damage calculation, stun messages, eliminations, item use, and backup spawn messages.

The system also includes design support functionality that improves maintainability and extensibility:

- Reusable battle context: `BattleContext` lets actions and effects log messages, register defeats, and query allies or opponents without directly controlling the engine.
- Read-only battle view: `BattleView` lets combatants and actions inspect battle state without exposing direct mutation of the battle engine.
- Replaceable turn-order strategy: `TurnOrderStrategy` allows the current speed-based ordering to be replaced later without rewriting `BattleEngine`.
- Factory-based level creation: `LevelFactory` centralizes difficulty setup so enemy-wave rules are separated from the CLI and battle loop.
- Target wrapper: `ActionTarget` and `SimpleActionTarget` support both single-target and multi-target actions through one common object.
- Item and status-effect object model: required item/effect behavior is implemented through extensible interfaces, so future items or effects can be added as new classes instead of adding large conditional blocks.

## d) Design Considerations and OO Concepts

The design separates the application into clear responsibilities. `GameCLI` handles user interaction, `BattleEngine` controls battle flow, `LevelFactory` creates enemy setups, combatant classes represent fighters, action classes represent turn behavior, item classes represent consumables, and status-effect classes represent temporary battle conditions. This separation makes the project easier to extend because new behavior can usually be added in one focused class instead of changing one large file.

Encapsulation is used throughout the model. `AbstractCombatant` keeps HP, attack, defense, speed, actions, status effects, and cooldown state private. Other classes interact with combatants through methods such as `receiveDamage`, `heal`, `modifyAttack`, `modifyDefense`, `addStatusEffect`, and `getActions`. This reduces accidental direct changes to combat state.

Abstraction is used through interfaces such as `Combatant`, `Action`, `Item`, `StatusEffect`, `TurnOrderStrategy`, `BattleView`, `BattleContext`, `ActionTarget`, and `CooldownTracker`. These interfaces define what the objects can do without forcing the rest of the system to know every implementation detail.

Polymorphism is one of the main design strengths. `BattleEngine` does not need separate code for every possible attack, item, or status effect. It can call `Action.execute(...)`, and the actual behavior depends on whether the action is `BasicAttackAction`, `ShieldBashAction`, `ArcaneBlastAction`, `DefendAction`, or `UseItemAction`. The same idea applies to `Item.use(...)` and `StatusEffect` hooks such as `onApply`, `onTurnEnd`, and `onRoundEnd`.

Inheritance is used where classes share a strong common structure. `AbstractCombatant` stores shared fighter state and behavior. `AbstractPlayer` adds inventory and player decision logic, while `AbstractEnemy` represents enemy-specific combatants. `Warrior`, `Wizard`, `Goblin`, and `Wolf` reuse this structure and mainly define stats and available actions.

The design also uses simple design patterns. `TurnOrderStrategy` follows the Strategy pattern because the battle engine depends on the turn-order interface rather than one fixed sorting rule. The current strategy is `SpeedTurnOrderStrategy`, but another strategy could be added later without rewriting the engine. `LevelFactory` follows a Factory-style design because it centralizes difficulty-based enemy creation. The action and status-effect systems also use a Command-like style, because each action object packages a behavior that can be executed by the engine.

The main trade-off is that the design has more classes than a simple procedural version. This makes the project slightly harder to understand at first, especially for a small game. However, the extra structure improves maintainability because each rule has a clear place. Another trade-off is that some classes are lightweight, such as records and simple wrappers, but they make method signatures clearer and reduce direct coupling between the engine and concrete gameplay classes.

Alternative designs were considered. One alternative was to put all battle rules directly inside `BattleEngine`, but that would make the engine too large and difficult to maintain. Another alternative was to use enums for actions and items, but that would make behavior harder to extend because each new action would require editing shared conditional logic. The current class-based design was chosen because it better supports extensibility, testing, and team-based development.

## e) Reflection

One difficulty was designing the system so that it matched the assignment rules while still being organized enough for several people to work on different parts. This was handled by separating the project into packages such as `ui`, `api`, `model`, `combatants`, `actions`, `effects`, `items`, `engine`, and `level`. This structure reduced conflicts and made ownership clearer.

Another difficulty was deciding where battle rules should live. For example, action execution, cooldown reduction, status-effect timing, target selection, and backup spawning all interact with each other. The solution was to keep the overall battle loop in `BattleEngine`, while moving specific behavior into action, item, status-effect, and support classes.

The assignment helped reinforce important OO design lessons. Encapsulation keeps object state safer. Interfaces make the system more flexible. Polymorphism reduces repeated conditional logic. Inheritance is useful when there is genuine shared state and behavior, but it should not be overused. The project also showed that diagrams are most useful when they stay aligned with the actual code, because outdated diagrams can make implementation harder instead of easier.

For further improvement, the project could add automated tests for battle rules, cooldown timing, item behavior, backup spawning, and win or lose conditions. The CLI could also be separated further from player decision logic so that the model layer has less direct console input. More battle scenarios could be added, such as additional enemies, more actions, more status effects, or alternative turn-order strategies.

## f) GitHub Repository

GitHub repository:

https://github.com/carLHW/2002-combat

## Diagram Generation Tools Used

The diagrams were written in Mermaid syntax and exported with Mermaid CLI.

- Mermaid CLI: `mmdc` version 11.12.0
- Node.js runtime used by Mermaid CLI
- PowerShell for running the export commands
- OpenJDK `javac` 17.0.2 for compiling and checking the Java source

Commands used:

```powershell
mmdc -i main\UML_Class_Diagram\markdown\UML-class-diagram-full.md -o main\UML_Class_Diagram\svg\UML-class-diagram-full.svg -b white -w 2400 -H 1800 -s 2
mmdc -i main\UML_Class_Diagram\markdown\UML-class-diagram-full.md -o main\UML_Class_Diagram\png\UML-class-diagram-full.png -b white -w 2400 -H 1800 -s 2
mmdc -i main\UML_Sequence_Diagram\markdown\battle-sequence-diagram.md -o main\UML_Sequence_Diagram\svg\battle-sequence-diagram.svg -b white -w 1800 -H 2200 -s 2
mmdc -i main\UML_Sequence_Diagram\markdown\battle-sequence-diagram.md -o main\UML_Sequence_Diagram\png\battle-sequence-diagram.png -b white -w 1800 -H 2200 -s 2
javac -d out (all Java files under src)
```
