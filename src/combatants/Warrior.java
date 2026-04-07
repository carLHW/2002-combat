package combatants;

import model.AbstractEnemy;
import model.AbstractPlayer;
import actions.BasicAttackAction;
import actions.DefendAction;
import actions.ShieldBashAction;
import actions.UseItemAction;
import api.Action;
import api.Combatant;
import api.Item;

public final class Warrior extends AbstractPlayer {
    public Warrior(String name) {
        super(name, 260, 40, 20, 30);
    }
    public Action UseBasicAttack(Combatant target){
        Combatant user = this.getName();
        return new BasicAttackAction(user, target);
    }
    public Action UseDefend(){
        return new DefendAction();
    }
    public Action UseItem(Combatant target, Item item){
        return new UseItemAction(target);
    }
    public Action UseShieldBash(Combatant target){
        return new ShieldBashAction(target);
    }
}
