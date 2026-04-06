package api;

// Tracks cooldown state for combat actions that cannot be used every turn.
public interface CooldownTracker {
    boolean isReady(String key);

    void startCooldown(String key, int turns);

    void reduceCooldownOnTurnTaken();

    int getRemainingTurns(String key);
}

