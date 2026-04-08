package actions;

import api.Action;
import api.ActionTarget;
import api.BattleView;
import api.Combatant;

public final class UseItemAction implements Action {
    @Override
    public String getName() {
        return "UseItem";
    }

    @Override
    public boolean canExecute(Combatant user, BattleView battleView) {
        // TODO: check whether the player has a usable item
        if (!(user instanceof AbstractPlayer player)) {
            return false;
        }

        for (Item item : player.getInventory().getItems()) {
            if (item.canUse(player, battleView)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public void execute(Combatant user, ActionTarget target) {
        // TODO: implement UseItemAction
        if (!(user instanceof AbstractPlayer player)) {
            return;
        }
        
        Optional<Item> usableItem = player.getInventory()
                .getItems()
                .stream()
                .filter(item -> item.canUse(player, null))
                .findFirst();

        if (usableItem.isEmpty()) {
            return;
        }

        Item item = usableItem.get();
        item.use(player, target);
        player.getInventory().consume(item);

        if (target != null && target.context() != null) {
            target.context().log(player.getName() + " used " + item.getName() + ".");
       }
}
}
