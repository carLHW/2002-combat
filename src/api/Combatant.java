package api;

import java.util.List;

public interface Combatant {
    String getName();

    Team getTeam();

    int getMaxHp();

    int getCurrentHp();

    int getAttack();

    int getDefense();

    int getSpeed();

    boolean isAlive();

    List<Action> getActions();

    List<StatusEffect> getStatusEffects();

    CooldownTracker getCooldownTracker();

    void receiveDamage(int amount);

    void heal(int amount);

    void modifyAttack(int delta);

    void modifyDefense(int delta);

    void addStatusEffect(StatusEffect effect, BattleContext battleContext);

    void removeExpiredEffects(BattleContext battleContext);

    boolean canAct(BattleContext battleContext);

    Action chooseAction(BattleView battleView);

    ActionTarget chooseTarget(Action action, BattleView battleView, BattleContext battleContext);
}

