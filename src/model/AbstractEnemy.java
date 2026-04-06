package model;

import api.Team;

// Shared base class for enemy combatants.
// TODO: decide whether enemy-shared behavior is needed here beyond the constructor.
public abstract class AbstractEnemy extends AbstractCombatant {
    protected AbstractEnemy(String name, int maxHp, int attack, int defense, int speed) {
        super(name, Team.ENEMY, maxHp, attack, defense, speed);
    }
}

