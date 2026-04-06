package api;

import java.util.List;

// Support API for battle-side actions such as logging, ally lookup, and opponent lookup.
public interface BattleContext {
    void log(String message);

    List<Combatant> getLivingOpponentsOf(Combatant combatant);

    List<Combatant> getLivingAlliesOf(Combatant combatant);

    void registerDefeat(Combatant target, Combatant defeatedBy);

    int getRoundNumber();
}

