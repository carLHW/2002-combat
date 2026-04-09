package model;

import api.CooldownTracker;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public final class SimpleCooldownTracker implements CooldownTracker {
    private final Map<String, Integer> cooldowns = new HashMap<>();

    @Override
    public boolean isReady(String key) {
        // implement cooldown readiness
        return getRemainingTurns(key) <= 0;
    }

    @Override
    public void startCooldown(String key, int turns) {
        // implement cooldown start
        cooldowns.put(key, Math.max(0, turns));
    }

    @Override
    public void reduceCooldownOnTurnTaken() {
        // implement cooldown reduction
        Iterator<Map.Entry<String, Integer>> iterator = cooldowns.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Integer> entry = iterator.next();
            int remaining = entry.getValue();

            if (remaining > 0) {
                remaining--;
            }

            if (remaining <= 0) {
                iterator.remove();
            } else {
                entry.setValue(remaining);
            }
        }
    }

    @Override
    public int getRemainingTurns(String key) {
        // implement cooldown lookup
        return cooldowns.getOrDefault(key, 0);
    }
}
