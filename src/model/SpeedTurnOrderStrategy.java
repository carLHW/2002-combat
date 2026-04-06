package model;

import api.Combatant;
import api.TurnOrderStrategy;
import java.util.List;

public final class SpeedTurnOrderStrategy implements TurnOrderStrategy {
    @Override
    public List<Combatant> sort(List<Combatant> combatants) {
        // TODO: implement speed-based sorting
        return combatants;
    }
}
