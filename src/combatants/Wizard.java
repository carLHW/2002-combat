package combatants;

import actions.ArcaneBlastAction;
import actions.BasicAttackAction;
import actions.DefendAction;
import actions.UseItemAction;
import api.Combatant;
import api.Item;
import model.AbstractPlayer;

public final class Wizard extends AbstractPlayer {
    public Wizard(String name) {
        super(name, 200, 50, 10, 20);
    }
    public Action UseBasicAttack(Combatant target){
        return new BasicAttackAction(target);
    }
    public Action UseDefend(){
        return new DefendAction();
    }
    public Action UseItem(Combatant target, Item item){
        return new UseItemAction(target);
    }
    public Action UseArcaneBlast(Combatant target){
        return new ArcaneBlastAction(target);
    }
}
