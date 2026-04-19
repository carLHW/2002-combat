# UML Sequence Diagram (Current Battle Flow)

- Purpose:
  - show one complete battle scenario from CLI setup to battle result
  - include the main objects involved in setup, action choice, action execution, status effects, cooldowns, backup spawn, and result return

```mermaid
sequenceDiagram
autonumber

actor Player
participant Main
participant GameCLI
participant LevelFactory
participant LevelSetup
participant BattleEngine
participant SpeedTurnOrderStrategy
participant DefaultBattleView
participant DefaultBattleContext
participant Current as Combatant
participant Action
participant ActionTarget
participant StatusEffect
participant CooldownTracker
participant BattleResult

Player->>Main: run program
Main->>GameCLI: new GameCLI(scanner)
Main->>GameCLI: start()

GameCLI->>Player: show player, item, and difficulty choices
Player-->>GameCLI: selected player, two items, difficulty
GameCLI->>LevelFactory: createLevel(difficulty)
LevelFactory-->>GameCLI: LevelSetup(initialEnemies, backupEnemies)
GameCLI->>LevelSetup: initialEnemies()
LevelSetup-->>GameCLI: initial enemy list
GameCLI->>LevelSetup: backupEnemies()
LevelSetup-->>GameCLI: backup enemy list
GameCLI->>BattleEngine: new BattleEngine(strategy, combatants, backupEnemies)
GameCLI->>BattleEngine: runBattle()

BattleEngine->>DefaultBattleView: new DefaultBattleView(this)
BattleEngine->>DefaultBattleContext: new DefaultBattleContext(this)

loop while players and enemies are alive
    BattleEngine->>DefaultBattleContext: log(round heading)
    BattleEngine->>SpeedTurnOrderStrategy: sort(combatants)
    SpeedTurnOrderStrategy-->>BattleEngine: sorted turn order

    loop for each living combatant
        BattleEngine->>Current: getStatusEffects()
        loop each active status effect
            BattleEngine->>StatusEffect: onTurnStart(Current, context)
        end

        BattleEngine->>Current: canAct(context)
        alt combatant cannot act
            Current-->>BattleEngine: false
            BattleEngine->>DefaultBattleContext: log(cannot act)
        else combatant can act
            Current-->>BattleEngine: true
            BattleEngine->>Current: chooseAction(view)
            alt player-controlled turn
                Current->>DefaultBattleView: getLivingOpponentsOf(Current)
                DefaultBattleView-->>Current: living enemies
                Current->>Player: show action menu
                Player-->>Current: selected action
            else enemy turn
                Current->>DefaultBattleView: getLivingOpponentsOf(Current)
                DefaultBattleView-->>Current: living players
                Current-->>BattleEngine: first executable action
            end

            Current-->>BattleEngine: Action
            BattleEngine->>Current: chooseTarget(action, view, context)
            Current->>DefaultBattleView: getLivingOpponentsOf(Current)
            DefaultBattleView-->>Current: possible targets
            Current-->>BattleEngine: ActionTarget
            BattleEngine->>Action: canExecute(Current, view)
            Action-->>BattleEngine: true or false
            opt action can execute
                BattleEngine->>Action: execute(Current, ActionTarget)
                Action->>ActionTarget: primaryTarget() or targets()
                ActionTarget-->>Action: selected target data
                alt attack or special skill defeats target
                    Action->>DefaultBattleContext: registerDefeat(target, Current)
                    DefaultBattleContext->>DefaultBattleContext: log(defeat message)
                else action applies effect or item
                    Action->>Current: addStatusEffect(effect, context)
                    Current->>StatusEffect: onApply(Current, context)
                end
                Action->>DefaultBattleContext: log(action result)
            end
        end

        BattleEngine->>CooldownTracker: reduceCooldownOnTurnTaken()
        BattleEngine->>Current: getStatusEffects()
        loop each active status effect
            BattleEngine->>StatusEffect: onTurnEnd(Current, context)
        end
        BattleEngine->>Current: removeExpiredEffects(context)

        opt no living initial enemies and backup not spawned
            BattleEngine->>BattleEngine: spawnBackupIfNeeded(context)
            BattleEngine->>DefaultBattleContext: log(backup enemies appeared)
        end
    end

    loop each living combatant
        BattleEngine->>StatusEffect: onRoundEnd(combatant, context)
        BattleEngine->>Current: removeExpiredEffects(context)
    end
end

BattleEngine->>BattleResult: new BattleResult(winner, roundNumber)
BattleResult-->>BattleEngine: result
BattleEngine-->>GameCLI: BattleResult
GameCLI->>Player: show victory or defeat screen
```
