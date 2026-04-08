package engine;

import api.Action;
import api.ActionTarget;
import api.BattleContext;
import api.BattleView;
import api.Combatant;
import api.Team;
import api.TurnOrderStrategy;
import java.util.ArrayList;
import java.util.List;

public final class BattleEngine {
    private final TurnOrderStrategy turnOrderStrategy;
    private final List<Combatant> combatants;
    private final List<Combatant> backupSpawn;
    private int roundNumber;

    public BattleEngine(
            TurnOrderStrategy turnOrderStrategy,
            List<Combatant> initialCombatants,
            List<Combatant> backupSpawn
    ) {
        this.turnOrderStrategy = turnOrderStrategy;
        this.combatants = new ArrayList<>(initialCombatants);
        this.backupSpawn = new ArrayList<>(backupSpawn);
        this.roundNumber = 0;
    }

    public BattleResult runBattle() {
        BattleView view = new DefaultBattleView(this);
        BattleContext context = new DefaultBattleContext(this);

        while (isBattleActive()) {
            roundNumber++;
            
            
            List<Combatant> turnOrder = turnOrderStrategy.sort(combatants);

            
            for (int i = 0; i < turnOrder.size(); i++) {
                Combatant current = turnOrder.get(i);
    
                
                if (!current.isAlive()) continue;

                // Flow: Choose -> Check -> Target -> Execute
                Action chosenAction = current.chooseAction(view);
                
                if (chosenAction != null && chosenAction.canExecute(current, view)) {
                    ActionTarget target = current.chooseTarget(chosenAction, view, context);
                    chosenAction.execute(current, target);
                }

                // Check if a team was wiped out mid-round
                if (!isBattleActive()) break;
            }
        }
        
        return new BattleResult(determineWinner(), roundNumber);
    }

    private boolean isBattleActive() {
        boolean playerAlive = false;
        boolean enemyAlive = false;

        for (Combatant c : combatants) {
            if (c.isAlive()) {
                if (c.getTeam() == Team.PLAYER) playerAlive = true;
                if (c.getTeam() == Team.ENEMY) enemyAlive = true;
            }
        }
        return playerAlive && enemyAlive;
    }

    private Team determineWinner() {
        for (Combatant c : combatants) {
            if (c.isAlive()) return c.getTeam();
        }
        return Team.ENEMY; // Default fallback
    }

    public int getRoundNumber() {
        return roundNumber;
    }

    public List<Combatant> getLivingCombatants() {
        List<Combatant> living = new ArrayList<>();
        for (Combatant c : combatants) {
            if (c.isAlive()) living.add(c);
        }
        return living;
    }

    public List<Combatant> getLivingOpponentsOf(Combatant combatant) {
        List<Combatant> opponents = new ArrayList<>();
        for (Combatant c : combatants) {
            if (c.isAlive() && c.getTeam() != combatant.getTeam()) {
                opponents.add(c);
            }
        }
        return opponents;
    }

    public List<Combatant> getLivingAlliesOf(Combatant combatant) {
        List<Combatant> allies = new ArrayList<>();
        for (Combatant c : combatants) {
            // Allies include everyone on the same team EXCEPT the combatant themselves
            if (c.isAlive() && c.getTeam() == combatant.getTeam() && c != combatant) {
                allies.add(c);
            }
        }
        return allies;
    }
}