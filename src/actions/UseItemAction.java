package actions;

import api.Action;
import api.ActionTarget;
import api.BattleView;
import api.Combatant;
import api.Item;
import model.AbstractPlayer;

// TODO: if item choice UI changes later, keep this action synced with AbstractPlayer.
public final class UseItemAction implements Action {
    @Override
    public String getName() {
        return "UseItem";
    }

    @Override
    public boolean canExecute(Combatant user, BattleView battleView) {
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
        if (!(user instanceof AbstractPlayer player)) {
            return;
        }

        Item item = player.getSelectedItem();
        if (item == null) {
            return;
        }
        if (!player.getInventory().getItems().contains(item)) {
            player.clearSelectedItem();
            return;
        }

        item.use(player, target);
        player.getInventory().consume(item);
        player.clearSelectedItem();

        if (target != null && target.context() != null) {
            target.context().log(player.getName() + " used " + item.getName() + ".");
        }
    }
}
