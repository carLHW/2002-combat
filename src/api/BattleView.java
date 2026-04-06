package api;

import java.util.List;

// Read-only battle view used when choosing actions and targets.
public interface BattleView {
    int getRoundNumber();

    List<Combatant> getLivingCombatants();

    List<Combatant> getLivingOpponentsOf(Combatant combatant);

    List<Combatant> getLivingAlliesOf(Combatant combatant);
}

