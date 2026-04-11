# Migration Checklist

This explains what changed in `src/` and what each person still needs to update on their side.

Use:
- `src/` as the main working version
- `completed-src/` only as a reference

## Shared Base Changes

- `AbstractCombatant.java`
  - Shared HP/stat/status/cooldown/default target logic is now in the base class.

- `AbstractPlayer.java`
  - Player action choice, item choice, and target choice now live here.

- `AbstractEnemy.java`
  - Enemy flow now relies on shared base behavior.

## Person 1

- `GameCLI.java`
  - Recheck the loading screen, replay flow, and end screen wording against the PDF.
  - Recheck that player input flow still feels correct after everyone else updates their files.

## Person 2

- `AbstractCombatant.java`
  - Recheck the base damage/heal/stat logic.
  - Recheck default target selection and whether any edge cases need cleanup.
  - Recheck status-effect apply/remove timing with the engine loop.

- `AbstractPlayer.java`
  - Recheck player action menu flow.
  - Recheck target selection for single-target, defend, item use, and Arcane Blast.
  - Recheck whether any prompt wording or action restrictions should change.

- `AbstractEnemy.java`
  - Recheck whether enemy default behavior is enough or if a small shared helper is still needed.

## Person 3

- `Warrior.java`
- `Wizard.java`
- `Goblin.java`
- `Wolf.java`
  - Recheck stats against the assignment.
  - Recheck action lists against the final design.
  - Make sure constructor-based action registration is correct and complete.

- `BasicAttackAction.java`
  - Recheck damage formula and log wording.
  - Recheck Smoke Bomb interaction with the latest item/effect logic.

- `ShieldBashAction.java`
  - Recheck stun timing.
  - Recheck cooldown behavior against the appendix examples.
  - Recheck log output and elimination handling.

- `ArcaneBlastAction.java`
  - Recheck cooldown behavior against the appendix examples.
  - Recheck attack gain per kill.
  - Recheck log output for multiple enemies and eliminations.

## Person 4

- `UseItemAction.java`
  - Recheck that selected-item flow works correctly with the new player base.
  - Recheck item availability once items are consumed.

- `PowerStoneItem.java`
  - Recheck that it triggers the special skill once.
  - Recheck that it does not start or change cooldown incorrectly.
  - Recheck target behavior when the free skill use is triggered.

- `SmokeBombItem.java`
  - Recheck that enemy attacks deal exactly `0` damage for the current turn and next turn.
  - Recheck whether the current effect placement is the cleanest way to represent that rule.

- `StunStatusEffect.java`
  - Recheck whether stun should wear off on turn end or round end to match the PDF examples.

## Person 5

- `BattleEngine.java`
  - Recheck the round flow against the PDF appendix.
  - Recheck when cooldown is reduced.
  - Recheck when status effects are applied/expired.
  - Recheck backup spawn timing and log output.
  - Recheck win/lose checks after turns and spawns.

- `LevelFactory.java`
  - Recheck exact enemy setups against the assignment table.
  - Recheck whether enemy names should stay as-is or be simplified.

## Final Check Before Syncing

- Compile `src/` after each person’s changes.
- Keep TODO comments unless that task is actually done.
- If unsure, compare only the relevant file with `completed-src/` instead of copying everything over.
