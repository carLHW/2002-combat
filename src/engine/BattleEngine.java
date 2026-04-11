package engine;

import api.Action;
import api.ActionTarget;
import api.BattleContext;
import api.BattleView;
import api.Combatant;
import api.StatusEffect;
import api.Team;
import api.TurnOrderStrategy;
import java.util.ArrayList;
import java.util.List;

// TODO: if the final team version wants richer battle logs or result data, extend this carefully.
public final class BattleEngine {
    private final TurnOrderStrategy turnOrderStrategy;
    private final List<Combatant> combatants;
    private final List<Combatant> backupSpawn;
    private boolean backupSpawned;
    private int roundNumber;

    public BattleEngine(
            TurnOrderStrategy turnOrderStrategy,
            List<Combatant> initialCombatants,
            List<Combatant> backupSpawn
    ) {
        this.turnOrderStrategy = turnOrderStrategy;
        this.combatants = new ArrayList<>(initialCombatants);
        this.backupSpawn = new ArrayList<>(backupSpawn);
        this.backupSpawned = backupSpawn.isEmpty();
        this.roundNumber = 0;
    }

    public BattleResult runBattle() {
        BattleView view = new DefaultBattleView(this);
        BattleContext context = new DefaultBattleContext(this);

        while (isBattleActive()) {
            roundNumber++;
            context.log("");
            context.log("=== Round " + roundNumber + " ===");

            List<Combatant> turnOrder = turnOrderStrategy.sort(combatants);
            for (Combatant current : turnOrder) {
                if (!current.isAlive()) {
                    continue;
                }

                for (StatusEffect effect : current.getStatusEffects()) {
                    effect.onTurnStart(current, context);
                }

                if (!current.canAct(context)) {
                    context.log(current.getName() + " cannot act this turn.");
                } else {
                    Action chosenAction = current.chooseAction(view);
                    ActionTarget target = current.chooseTarget(chosenAction, view, context);
                    if (chosenAction != null && target != null && chosenAction.canExecute(current, view)) {
                        chosenAction.execute(current, target);
                    }
                }

                current.getCooldownTracker().reduceCooldownOnTurnTaken();
                for (StatusEffect effect : current.getStatusEffects()) {
                    effect.onTurnEnd(current, context);
                }
                current.removeExpiredEffects(context);
                spawnBackupIfNeeded(context);

                if (!isBattleActive()) {
                    break;
                }
            }

            for (Combatant combatant : new ArrayList<>(combatants)) {
                if (!combatant.isAlive()) {
                    continue;
                }
                for (StatusEffect effect : combatant.getStatusEffects()) {
                    effect.onRoundEnd(combatant, context);
                }
                combatant.removeExpiredEffects(context);
            }
        }

        return new BattleResult(determineWinner(), roundNumber);
    }

    private boolean isBattleActive() {
        return hasLivingPlayers() && hasLivingEnemies();
    }

    private boolean hasLivingPlayers() {
        for (Combatant combatant : combatants) {
            if (combatant.isAlive() && combatant.getTeam() == Team.PLAYER) {
                return true;
            }
        }
        return false;
    }

    private boolean hasLivingEnemies() {
        for (Combatant combatant : combatants) {
            if (combatant.isAlive() && combatant.getTeam() == Team.ENEMY) {
                return true;
            }
        }
        return false;
    }

    private Team determineWinner() {
        return hasLivingPlayers() ? Team.PLAYER : Team.ENEMY;
    }

    private void spawnBackupIfNeeded(BattleContext context) {
        if (backupSpawned || backupSpawn.isEmpty() || hasLivingEnemies()) {
            return;
        }

        combatants.addAll(backupSpawn);
        backupSpawned = true;

        StringBuilder message = new StringBuilder("Backup enemies appeared: ");
        for (int i = 0; i < backupSpawn.size(); i++) {
            if (i > 0) {
                message.append(", ");
            }
            message.append(backupSpawn.get(i).getName());
        }
        context.log(message.toString());
    }

    public int getRoundNumber() {
        return roundNumber;
    }

    public List<Combatant> getLivingCombatants() {
        List<Combatant> living = new ArrayList<>();
        for (Combatant combatant : combatants) {
            if (combatant.isAlive()) {
                living.add(combatant);
            }
        }
        return living;
    }

    public List<Combatant> getLivingOpponentsOf(Combatant combatant) {
        List<Combatant> opponents = new ArrayList<>();
        for (Combatant current : combatants) {
            if (current.isAlive() && current.getTeam() != combatant.getTeam()) {
                opponents.add(current);
            }
        }
        return opponents;
    }

    public List<Combatant> getLivingAlliesOf(Combatant combatant) {
        List<Combatant> allies = new ArrayList<>();
        for (Combatant current : combatants) {
            if (current.isAlive() && current.getTeam() == combatant.getTeam() && current != combatant) {
                allies.add(current);
            }
        }
        return allies;
    }
}
