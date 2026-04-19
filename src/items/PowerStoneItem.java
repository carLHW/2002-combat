package items;

import actions.DefendAction;
import actions.UseItemAction;
import api.Action;
import api.ActionTarget;
import api.BattleView;
import api.Item;
import model.AbstractPlayer;

// if special-skill identification is refactored later, update this item too.
public final class PowerStoneItem implements Item {
    @Override
    public String getName() {
        return "Power Stone";
    }

    @Override
    public boolean canUse(AbstractPlayer user, BattleView battleView) {
        return user.getActions().stream().anyMatch(this::isSpecialSkill);
    }

    @Override
    public void use(AbstractPlayer user, ActionTarget target) {
        Action specialSkill = user.getActions().stream()
                .filter(this::isSpecialSkill)
                .findFirst()
                .orElse(null);

        if (specialSkill == null) {
            return;
        }

        int previousCooldown = user.getCooldownTracker().getRemainingTurns(specialSkill.getName());
        specialSkill.execute(user, target);
        user.getCooldownTracker().startCooldown(specialSkill.getName(), previousCooldown+1);

        if (target != null && target.context() != null) {
            target.context().log(user.getName() + " used Power Stone to trigger " + specialSkill.getName() + ".");
        }
    }

    private boolean isSpecialSkill(Action action) {
        return !(action instanceof DefendAction)
                && !(action instanceof UseItemAction)
                && !"BasicAttack".equals(action.getName());
    }
}
