package api;

import java.util.List;

// Support API for passing selected targets into an action.
public interface ActionTarget {
    Combatant primaryTarget();

    List<Combatant> targets();

    BattleContext context();
}

