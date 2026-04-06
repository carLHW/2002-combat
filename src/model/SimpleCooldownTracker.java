package model;

import api.CooldownTracker;

public final class SimpleCooldownTracker implements CooldownTracker {
    @Override
    public boolean isReady(String key) {
        // TODO: implement cooldown readiness
        return true;
    }

    @Override
    public void startCooldown(String key, int turns) {
        // TODO: implement cooldown start
    }

    @Override
    public void reduceCooldownOnTurnTaken() {
        // TODO: implement cooldown reduction
    }

    @Override
    public int getRemainingTurns(String key) {
        // TODO: implement cooldown lookup
        return 0;
    }
}
