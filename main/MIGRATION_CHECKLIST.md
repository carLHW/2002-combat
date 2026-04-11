# Migration Checklist

This explains exactly what each person should do after the migration.

Use:
- `src/` as the main working version
- `completed-src/` only as a reference

Important rules:
- Do not replace whole files by copying everything from `completed-src/`.
- Only update your own assigned files.
- Keep old TODO comments unless that exact task is actually finished.
- If unsure, compare only your file with `completed-src/` and adapt the logic into the current `src/`.
- After finishing your part, compile `src/` and test the part you changed.

## Priority Right Now

- Biggest contribution gain:
  - Person 4
  - Person 5
  - Person 3
  - Person 2
  - Person 1

- Most important for finishing the assignment:
  - Person 5
  - Person 4
  - Person 3
  - Person 2
  - Person 1

- Sequence diagram is still needed, but it is not the main place to increase coding contribution.
- The main contribution growth should still come from code adaptation in each person's assigned files.

## Shared Base Changes Everyone Must Know

- `AbstractCombatant.java`
  - Shared HP logic, stat changes, status-effect handling, cooldown support, and default target logic now live here.

- `AbstractPlayer.java`
  - Player action choice, item choice, and target choice now live here.

- `AbstractEnemy.java`
  - Enemy flow now relies on shared base behavior instead of separate custom flow in every enemy class.

- `GameCLI.java`
  - CLI flow has already been adjusted to fit the migrated structure.
  - Other files must now fit this flow instead of assuming the old fully automatic flow.

## Person 1

Files:
- `GameCLI.java`

What to do:
- Recheck the setup flow against the PDF.
- Recheck the battle-start flow.
- Recheck replay flow.
- Recheck new-game flow.
- Recheck exit flow.
- Recheck victory text.
- Recheck defeat text.
- Recheck that the player action prompt still makes sense after everyone else's updates.
- Recheck that the target-choice prompt still makes sense after everyone else's updates.
- Recheck that any invalid input is handled cleanly and does not break the game flow.

What "done" means:
- The player can start a game, choose character and difficulty, finish a game, and then cleanly replay, start a new game, or exit.
- Prompt wording is understandable.
- CLI flow still matches the current engine/base flow.

What to test:
- Run one full game.
- Try invalid menu input once or twice.
- Try replay.
- Try new game.
- Try exit.

## Person 2 follow-up

Please work only on:
- `AbstractCombatant.java`
- `AbstractPlayer.java`
- `AbstractEnemy.java`

Goal:
Make the shared base flow fully solid with the migrated `src`, without changing package structure or renaming classes/methods.

What to check and improve:
- In `AbstractCombatant.java`
  - confirm damage never drops HP below 0
  - confirm healing never exceeds max HP
  - confirm attack/defense modification works cleanly
  - confirm status effects are added, checked, and removed correctly
  - confirm expired effects are removed at the right time
  - confirm `canAct(...)` correctly respects active effects
  - confirm default target selection works for single-target, self-target, and multi-target actions
- In `AbstractPlayer.java`
  - confirm player action selection works with the current action list
  - confirm item selection works with the current inventory flow
  - confirm target selection works for `BasicAttack`, `Defend`, `UseItem`, `ShieldBash`, and `ArcaneBlast`
  - confirm invalid input is handled cleanly
  - confirm action restrictions make sense when there are no usable items or when an action is on cooldown
- In `AbstractEnemy.java`
  - confirm enemy default behavior is still simple and correct
  - confirm enemy targeting is consistent and does not need duplicate logic in each enemy class

What to preserve:
- keep existing TODO comments unless that exact task is fully done
- do not rewrite unrelated files
- do not copy full files from `completed-src`

What to test:
- compile `src`
- run a battle and confirm:
  - player can choose actions
  - player can choose targets
  - enemies still act correctly

Done means:
- base classes no longer feel like partial placeholders
- player/enemy shared flow works cleanly with the current CLI and engine


## Person 3 follow-up

Please work only on:
- `Warrior.java`
- `Wizard.java`
- `Goblin.java`
- `Wolf.java`
- `BasicAttackAction.java`
- `ShieldBashAction.java`
- `ArcaneBlastAction.java`

Goal:
Make combatants and action behavior fit the migrated shared flow while keeping gameplay correct.

What to check and improve:
- In `Warrior.java`, `Wizard.java`, `Goblin.java`, `Wolf.java`
  - confirm stats match the assignment
  - confirm each combatant has the correct actions only
  - confirm action registration works properly with the current base structure
  - remove any leftover assumptions from the old fully automatic flow
- In `BasicAttackAction.java`
  - confirm damage formula is correct
  - confirm invalid/dead targets are handled safely
  - confirm log output is clear
  - confirm Smoke Bomb interaction still works correctly
- In `ShieldBashAction.java`
  - confirm damage is correct
  - confirm stun is applied correctly
  - confirm cooldown behavior matches the current engine flow
  - confirm log output and elimination handling are correct
  - confirm special skill is blocked correctly while on cooldown
- In `ArcaneBlastAction.java`
  - confirm all intended enemies are hit
  - confirm attack gain is applied correctly per kill
  - confirm elimination handling is correct
  - confirm cooldown behavior is correct
  - confirm multi-target handling still matches the current player flow
  - confirm log output for multiple enemies is understandable

What to preserve:
- keep current class names and method names
- keep TODO comments unless fully completed
- do not replace whole files from `completed-src`

What to test:
- compile `src`
- run battles using:
  - `BasicAttack`
  - `ShieldBash`
  - `ArcaneBlast`
- confirm cooldown and battle log behavior

Done means:
- combatants and actions work correctly with the new base flow
- special skills behave properly in real battle flow


## Person 4 follow-up

Please work only on:
- `UseItemAction.java`
- `PowerStoneItem.java`
- `SmokeBombItem.java`
- `StunStatusEffect.java`

Goal:
Make items and effect timing match the migrated structure and the assignment behavior as closely as possible.

What to check and improve:
- In `UseItemAction.java`
  - confirm selected-item flow works with the current player base
  - confirm item use fails cleanly if no usable item exists
  - confirm consumed items are no longer usable
  - confirm target selection still makes sense for item use
- In `PowerStoneItem.java`
  - confirm it triggers the special skill exactly once
  - confirm it does not wrongly start cooldown
  - confirm it does not wrongly reduce cooldown
  - confirm it does not wrongly overwrite an existing cooldown
  - confirm free special-skill use still follows correct target and battle flow
  - confirm log output is reasonable
- In `SmokeBombItem.java`
  - confirm enemy attacks deal exactly 0 damage for the current turn and next turn
  - confirm the effect ends at the correct time
  - confirm it matches the wording in the assignment closely enough
  - confirm interaction with normal battle flow is still correct
- In `StunStatusEffect.java`
  - confirm stun wears off at the correct time
  - confirm effect timing agrees with `canAct(...)` and engine timing
  - confirm stun does not last too short or too long

What to preserve:
- keep existing TODO comments unless fully done
- do not rewrite unrelated files
- adapt logic into current `src`, not by full copying

What to test:
- compile `src`
- use each item in battle
- confirm item consumption
- confirm Power Stone free special-skill behavior
- confirm Smoke Bomb zero-damage timing
- confirm stun timing across turns

Done means:
- item behavior is reliable
- effect timing feels correct in real battles


## Person 5 follow-up

Please work only on:
- `BattleEngine.java`
- `LevelFactory.java`
- `BattleResult.java` only if extra result data is really needed

Goal:
Finalize the integration layer so battle flow, cooldown timing, backup waves, and end conditions match the assignment.

What to check and improve:
- In `BattleEngine.java`
  - confirm round flow matches the assignment appendix closely
  - confirm turn order flow is correct
  - confirm cooldown is reduced at the correct time
  - confirm cooldown is not reduced too early or too late
  - confirm status effects are applied and expired at the correct time
  - confirm backup spawn happens at the correct moment
  - confirm backup spawn log output makes sense
  - confirm win/lose checks happen at the right time
  - confirm player choice, action execution, and effect timing all line up with the current CLI flow
  - confirm defeat output still makes sense when enemies remain alive
- In `LevelFactory.java`
  - confirm every difficulty exactly matches the assignment
  - confirm initial wave is correct for Easy, Medium, and Hard
  - confirm backup wave is correct for Easy, Medium, and Hard
  - remove any leftover older placeholder setups if still present
- In `BattleResult.java`
  - only add fields if needed to support final end-screen output cleanly

What to preserve:
- keep current package and class names
- do not rewrite unrelated files
- keep TODO comments unless the task is truly done

What to test:
- compile `src`
- run Easy
- run Medium
- run Hard
- confirm initial wave
- confirm backup wave
- confirm cooldown flow
- confirm final victory/defeat flow

Done means:
- battle loop feels complete from start to finish
- engine behavior matches the assignment closely enough


## Final Check Before Syncing

- Compile `src/` after each person's changes.
- Keep TODO comments unless that task is actually done.
- If unsure, compare only the relevant file with `completed-src/` instead of copying everything over.
- Before final submission, do one focused check on cooldown timing because that is still the biggest remaining PDF-risk area.
- Before final submission, do one focused run for:
  - player action choice
  - target choice
  - cooldown timing
  - Smoke Bomb timing
  - stun timing
  - backup spawn timing
  - victory/defeat flow
