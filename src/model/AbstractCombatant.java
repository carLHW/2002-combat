package model;

import actions.ArcaneBlastAction;
import actions.DefendAction;
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
// TODO: if the team changes targeting rules later, adjust the default target selection here.
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
        if (action == null) {
            return;
        }
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
        currentHp = Math.max(0, currentHp - Math.max(0, amount));
    }

    @Override
    public void heal(int amount) {
        currentHp = Math.min(maxHp, currentHp + Math.max(0, amount));
    }

    @Override
    public void modifyAttack(int delta) {
        attack = Math.max(0, attack + delta);
    }

    @Override
    public void modifyDefense(int delta) {
        defense = Math.max(0, defense + delta);
    }

    @Override
    public void addStatusEffect(StatusEffect effect, BattleContext battleContext) {
        if (effect == null || effect.isExpired()) {
            return;
        }
        statusEffects.add(effect);
        effect.onApply(this, battleContext);
    }

    @Override
    public void removeExpiredEffects(BattleContext battleContext) {
        List<StatusEffect> expiredEffects = new ArrayList<>();
        for (StatusEffect effect : statusEffects) {
            if (effect.isExpired()) {
                effect.onExpire(this, battleContext);
                expiredEffects.add(effect);
            }
        }
        statusEffects.removeAll(expiredEffects);
    }

    @Override
    public boolean canAct(BattleContext battleContext) {
        if (!isAlive()) {
            return false;
        }
        for (StatusEffect effect : statusEffects) {
            if (!effect.isExpired() && effect.preventsAction(this, battleContext)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Action chooseAction(BattleView battleView) {
        for (Action action : getActions()) {
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

        if (isSelfTargetAction(action)) {
            return new SimpleActionTarget(this, battleContext);
        }

        if (isMultiTargetAction(action)) {
            return new SimpleActionTarget(battleView.getLivingOpponentsOf(this), battleContext);
        }

        List<Combatant> opponents = battleView.getLivingOpponentsOf(this);
        if (opponents.isEmpty()) {
            return null;
        }
        return new SimpleActionTarget(opponents.get(0), battleContext);
    }

    private boolean isSelfTargetAction(Action action) {
        return action instanceof DefendAction || "Defend".equals(action.getName());
    }

    private boolean isMultiTargetAction(Action action) {
        return action instanceof ArcaneBlastAction || "ArcaneBlast".equals(action.getName());
    }
}
