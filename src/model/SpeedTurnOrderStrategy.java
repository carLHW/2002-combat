package model;

import api.Combatant;
import api.TurnOrderStrategy;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SpeedTurnOrderStrategy implements TurnOrderStrategy {
    
    @Override
    public List<Combatant> sort(List<Combatant> combatants) {
        // Create a new list so we don't accidentally mess up the engine's base list
        List<Combatant> sortedList = new ArrayList<>(combatants);
        
        // Sort the list based on speed descending
        sortedList.sort(new Comparator<Combatant>() {
            @Override
            public int compare(Combatant c1, Combatant c2) {
                return Integer.compare(c2.getSpeed(), c1.getSpeed());
            }
        });
        
        // Return the newly sorted list
        return sortedList;
    }
}