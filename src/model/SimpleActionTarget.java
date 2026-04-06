package model;

import api.ActionTarget;
import api.BattleContext;
import api.Combatant;
import java.util.List;

// Small helper wrapper used to pass selected targets into an action.
// TODO: keep or refine this wrapper if the final targeting flow needs small changes.
public record SimpleActionTarget(
        Combatant primaryTarget,
        List<Combatant> targets,
        BattleContext context
) implements ActionTarget {
}
