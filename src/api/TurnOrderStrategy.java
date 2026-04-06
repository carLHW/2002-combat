package api;

import java.util.List;

public interface TurnOrderStrategy {
    List<Combatant> sort(List<Combatant> combatants);
}

