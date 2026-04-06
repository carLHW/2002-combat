package model;

import api.BattleContext;
import api.Combatant;
import api.StatusEffect;

// Shared base class for effects that last across turns or rounds.
// TODO: complete the shared duration/effect behavior that concrete status effects will reuse.
public abstract class AbstractStatusEffect implements StatusEffect {
    private int remainingRounds;

    protected AbstractStatusEffect(int remainingRounds) {
        this.remainingRounds = remainingRounds;
    }

    protected final void reduceRoundsByOne() {
        if (remainingRounds > 0) {
            remainingRounds--;
        }
    }

    protected final int getRemainingRounds() {
        return remainingRounds;
    }

    @Override
    public void onApply(Combatant target, BattleContext battleContext) {
    }

    @Override
    public void onTurnStart(Combatant target, BattleContext battleContext) {
    }

    @Override
    public void onTurnEnd(Combatant target, BattleContext battleContext) {
    }

    @Override
    public void onRoundEnd(Combatant target, BattleContext battleContext) {
    }

    @Override
    public boolean preventsAction(Combatant target, BattleContext battleContext) {
        return false;
    }

    @Override
    public boolean isExpired() {
        return remainingRounds <= 0;
    }

    @Override
    public void onExpire(Combatant target, BattleContext battleContext) {
    }
}

