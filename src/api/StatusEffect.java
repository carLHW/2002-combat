package api;

public interface StatusEffect {
    String getName();

    void onApply(Combatant target, BattleContext battleContext);

    void onTurnStart(Combatant target, BattleContext battleContext);

    void onTurnEnd(Combatant target, BattleContext battleContext);

    void onRoundEnd(Combatant target, BattleContext battleContext);

    boolean preventsAction(Combatant target, BattleContext battleContext);

    boolean isExpired();

    void onExpire(Combatant target, BattleContext battleContext);
}

