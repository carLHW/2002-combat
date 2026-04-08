package model;

import api.ActionTarget;
import api.BattleContext;
import api.Combatant;
import java.util.ArrayList;
import java.util.List;

public class SimpleActionTarget implements ActionTarget {
    private final List<Combatant> targets;
    private final BattleContext context;

    // Constructor for a single-target action
    public SimpleActionTarget(Combatant singleTarget, BattleContext context) {
        this.targets = new ArrayList<>();
        this.targets.add(singleTarget);
        this.context = context;
    }

    // Constructor for multiple targets (e.g., AoE attacks)
    public SimpleActionTarget(List<Combatant> multipleTargets, BattleContext context) {
        this.targets = new ArrayList<>(multipleTargets);
        this.context = context;
    }

    @Override
    public Combatant primaryTarget() {
        // Return the first target as the primary target (if the list isn't empty)
        if (targets != null && !targets.isEmpty()) {
            return targets.get(0);
        }
        return null;
    }

    @Override
    public BattleContext context() {
        return context;
    }

    @Override
    public List<Combatant> targets() {
        return targets;
    }
}