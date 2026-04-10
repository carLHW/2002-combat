package model;

import api.Action;
import api.ActionTarget;
import api.BattleContext;
import api.BattleView;
import api.Combatant;
import api.CooldownTracker;
import api.StatusEffect;
import api.Team;
import java.util.ArrayList;
import java.util.List;

// Shared base class for anything that can fight in battle.
// TODO: complete shared combatant logic in this class.
// TODO: review the constructor/fields if the final combat flow needs small adjustments.
public abstract class AbstractCombatant implements Combatant {
    private final String name;
    private final Team team;
    private final int maxHp;
    private final int speed;
    private final List<Action> actions = new ArrayList<>();
    private final List<StatusEffect> statusEffects = new ArrayList<>();
    private final CooldownTracker cooldownTracker = new SimpleCooldownTracker();

    private int currentHp;
    private int attack;
    private int defense;

    protected AbstractCombatant(String name, Team team, int maxHp, int attack, int defense, int speed) {
        this.name = name;
        this.team = team;
        this.maxHp = maxHp;
        this.currentHp = maxHp;
        this.attack = attack;
        this.defense = defense;
        this.speed = speed;
    }

    protected final void addAction(Action action) {
        // TODO: keep or refine this helper if the final action setup needs a different approach.
        actions.add(action);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Team getTeam() {
        return team;
    }

    @Override
    public int getMaxHp() {
        return maxHp;
    }

    @Override
    public int getCurrentHp() {
        return currentHp;
    }

    @Override
    public int getAttack() {
        return attack;
    }

    @Override
    public int getDefense() {
        return defense;
    }

    @Override
    public int getSpeed() {
        return speed;
    }

    @Override
    public boolean isAlive() {
        return currentHp > 0;
    }

    @Override
    public List<Action> getActions() {
        return List.copyOf(actions);
    }

    @Override
    public List<StatusEffect> getStatusEffects() {
        return List.copyOf(statusEffects);
    }

    @Override
    public CooldownTracker getCooldownTracker() {
        return cooldownTracker;
    }

    @Override
    public void receiveDamage(int amount) {
        // TODO: implement shared damage handling
        currentHp-= amount;
        if (currentHp < 0) {
            currentHp = 0;
        }
    }

    @Override
    public void heal(int amount) {
        // TODO: implement shared healing handling
        currentHp += amount;
        if (currentHp > maxHp) {
            currentHp = maxHp;
        }
    }

    @Override
    public void modifyAttack(int delta) {
        // TODO: implement attack modification
        attack+=delta;
    }

    @Override
    public void modifyDefense(int delta) {
        // TODO: implement defense modification
        defense+=delta;
    }

    @Override
    public void addStatusEffect(StatusEffect effect, BattleContext battleContext) {
        // TODO: implement status effect application
        statusEffects.add(effect);
        effect.onApply(this, battleContext);    
    }

    @Override
    public void removeExpiredEffects(BattleContext battleContext) {
        for (int i = statusEffects.size() - 1; i >= 0; i--) {
            StatusEffect effect = statusEffects.get(i);
            if (effect.isExpired()) {
                effect.onExpire(this, battleContext);
                statusEffects.remove(i);
            }
        }
    }

    @Override
    public boolean canAct(BattleContext battleContext) {
        if (!isAlive()) {
            return false;
        }
        for (int i = 0; i < statusEffects.size(); i++) {
            if (statusEffects.get(i).preventsAction(this, battleContext)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Action chooseAction(BattleView battleView) {
        for (int i = 0; i < actions.size(); i++) {
            Action action = actions.get(i);
            if (action.canExecute(this, battleView)) {
                return action;
            }
        }
        return null;
    }

    @Override
    public ActionTarget chooseTarget(Action action, BattleView battleView, BattleContext battleContext) {
        if (action == null) {
            return null;
        }

        switch (action.getName()) {
            case "Defend":
            case "UseItem":
                return new SimpleActionTarget(this, battleContext);
            case "ArcaneBlast":
                List<Combatant> opponents = battleView.getLivingOpponentsOf(this);
                return opponents.isEmpty() ? null : new SimpleActionTarget(opponents, battleContext);
            default:
                List<Combatant> defaultTargets = battleView.getLivingOpponentsOf(this);
                return defaultTargets.isEmpty() ? null : new SimpleActionTarget(defaultTargets.get(0), battleContext);
        }
    }
}
