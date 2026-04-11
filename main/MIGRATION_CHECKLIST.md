# Migration Checklist

This checklist explains how `src/` has been moved toward the working reference in `completed-src/` while keeping teammate-owned files visible and editable.

## Shared Base Changes

- `AbstractCombatant.java`
  - Shared HP, stat, status-effect, cooldown, and default targeting logic is now in the base class.
  - Review any combat behavior that used to assume per-class handling.

- `AbstractPlayer.java`
  - Player action choice, item choice, and target selection now live here.
  - Concrete player classes should focus on their action list and stats.

- `AbstractEnemy.java`
  - Enemy flow now relies on shared base behavior.
  - Concrete enemy classes should focus on stats and actions.

## Person 1

- `GameCLI.java`
  - Scanner is now connected to the shared player-action flow.
  - End screen now shows enemies remaining on defeat.
  - Replay and setup flow should be rechecked against the final assignment wording.

## Person 2

- `AbstractCombatant.java`
  - Confirm the shared default targeting and action-selection behavior matches the final intended design.

- `AbstractPlayer.java`
  - Confirm the player prompts and target-selection flow match the final CLI flow.

- `AbstractEnemy.java`
  - Confirm no extra shared enemy behavior is needed beyond the current base structure.

## Person 3

- `Warrior.java`
- `Wizard.java`
- `Goblin.java`
- `Wolf.java`
  - Action registration now happens in constructors instead of rebuilding action lists in `getActions()`.
  - Recheck stats and action lists against the final report/UML.

- `BasicAttackAction.java`
  - Smoke Bomb support now forces damage to `0` when the target has the effect.
  - Recheck log wording if needed.

- `ShieldBashAction.java`
  - Cooldown handling is now wired in.
  - Recheck stun timing and log wording.

- `ArcaneBlastAction.java`
  - Cooldown handling is now wired in.
  - Recheck the attack-bonus rule and log wording.

## Person 4

- `UseItemAction.java`
  - Item use now depends on the item selected through `AbstractPlayer`.
  - Recheck item-use flow if the UI changes again.

- `PowerStoneItem.java`
  - Special-skill detection now uses the action list instead of a fixed string key.
  - Recheck whether the cooldown should remain fully unchanged in every case.

- `SmokeBombItem.java`
  - Smoke Bomb is now treated as a named effect instead of a defense-buff hack.
  - Damage-nullification currently happens through `BasicAttackAction`.

- `StunStatusEffect.java`
  - Recheck whether stun should wear off on turn end or round end.

## Person 5

- `BattleEngine.java`
  - Round start, turn start/end, round end, cooldown reduction, and backup spawn are now part of the core loop.
  - Recheck the final battle flow and logging order.

- `LevelFactory.java`
  - Enemy counts now match the assignment structure more closely.
  - Recheck enemy names if the team wants friendlier labels in the final submission.

## Final Review Before Syncing

- Make sure `src/` still compiles after any teammate follow-up edits.
- Keep TODO comments until the underlying task is actually complete.
- Use `completed-src/` as a behavior reference, not as the live source tree.
