package model;

import actions.ArcaneBlastAction;
import actions.DefendAction;
import actions.UseItemAction;
import api.Action;
import api.ActionTarget;
import api.BattleContext;
import api.BattleView;
import api.Combatant;
import api.Item;
import api.Team;
import items.PowerStoneItem;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Shared base class for player-controlled combatants.
// TODO: if the CLI flow changes later, keep these prompts aligned with GameCLI.
public abstract class AbstractPlayer extends AbstractCombatant {
    private static Scanner sharedScanner;

    private final Inventory inventory = new Inventory();
    private Item selectedItem;

    protected AbstractPlayer(String name, int maxHp, int attack, int defense, int speed) {
        super(name, Team.PLAYER, maxHp, attack, defense, speed);
    }

    public static void setSharedScanner(Scanner scanner) {
        sharedScanner = scanner;
    }

    public final Inventory getInventory() {
        return inventory;
    }

    public final Item getSelectedItem() {
        return selectedItem;
    }

    public final void clearSelectedItem() {
        selectedItem = null;
    }

    @Override
    public Action chooseAction(BattleView battleView) {
        List<Action> availableActions = new ArrayList<>(getActions());
        if (availableActions.isEmpty()) {
            return null;
        }

        while (true) {
            System.out.println();
            System.out.println("Your Turn: " + getName());
            System.out.println("HP: " + getCurrentHp() + "/" + getMaxHp()
                    + "  ATK: " + getAttack()
                    + "  DEF: " + getDefense());
            System.out.println("Choose an action:");

            for (int i = 0; i < availableActions.size(); i++) {
                Action action = availableActions.get(i);
                String suffix = describeActionRestriction(action, battleView);
                System.out.println((i + 1) + ". " + action.getName() + suffix);
            }

            int choice = readChoice(1, availableActions.size());
            Action chosenAction = availableActions.get(choice - 1);
            clearSelectedItem();

            if (!chosenAction.canExecute(this, battleView)) {
                System.out.println("That action cannot be used right now.");
                continue;
            }

            if (chosenAction instanceof UseItemAction) {
                Item item = chooseInventoryItem(battleView);
                if (item == null) {
                    System.out.println("No usable items right now.");
                    continue;
                }
                selectedItem = item;
            }

            return chosenAction;
        }
    }

    @Override
    public ActionTarget chooseTarget(Action action, BattleView battleView, BattleContext battleContext) {
        if (action == null) {
            return null;
        }

        if (action instanceof DefendAction) {
            return new SimpleActionTarget(this, battleContext);
        }

        if (action instanceof ArcaneBlastAction) {
            return new SimpleActionTarget(battleView.getLivingOpponentsOf(this), battleContext);
        }

        if (action instanceof UseItemAction) {
            if (selectedItem == null) {
                return new SimpleActionTarget(this, battleContext);
            }
            if (selectedItem instanceof PowerStoneItem) {
                Action specialSkill = findSpecialSkill();
                if (specialSkill == null) {
                    return null;
                }
                if (specialSkill instanceof ArcaneBlastAction) {
                    return new SimpleActionTarget(battleView.getLivingOpponentsOf(this), battleContext);
                }
                return chooseSingleEnemyTarget(battleView, battleContext);
            }
            return new SimpleActionTarget(this, battleContext);
        }

        return chooseSingleEnemyTarget(battleView, battleContext);
    }

    private String describeActionRestriction(Action action, BattleView battleView) {
        int remainingTurns = getCooldownTracker().getRemainingTurns(action.getName());
        if (remainingTurns > 0) {
            return " (Cooldown: " + remainingTurns + ")";
        }
        if (action instanceof UseItemAction && !hasUsableItem(battleView)) {
            return " (No usable items)";
        }
        if (!action.canExecute(this, battleView)) {
            return " (Unavailable)";
        }
        return "";
    }

    private ActionTarget chooseSingleEnemyTarget(BattleView battleView, BattleContext battleContext) {
        List<Combatant> enemies = battleView.getLivingOpponentsOf(this);
        if (enemies.isEmpty()) {
            return null;
        }
        if (enemies.size() == 1) {
            return new SimpleActionTarget(enemies.get(0), battleContext);
        }

        System.out.println("Choose a target:");
        for (int i = 0; i < enemies.size(); i++) {
            Combatant enemy = enemies.get(i);
            System.out.println((i + 1) + ". " + enemy.getName()
                    + " HP: " + enemy.getCurrentHp() + "/" + enemy.getMaxHp());
        }
        int choice = readChoice(1, enemies.size());
        return new SimpleActionTarget(enemies.get(choice - 1), battleContext);
    }

    private boolean hasUsableItem(BattleView battleView) {
        for (Item item : inventory.getItems()) {
            if (item.canUse(this, battleView)) {
                return true;
            }
        }
        return false;
    }

    private Item chooseInventoryItem(BattleView battleView) {
        List<Item> usableItems = new ArrayList<>();
        for (Item item : inventory.getItems()) {
            if (item.canUse(this, battleView)) {
                usableItems.add(item);
            }
        }

        if (usableItems.isEmpty()) {
            return null;
        }

        System.out.println("Choose an item:");
        for (int i = 0; i < usableItems.size(); i++) {
            System.out.println((i + 1) + ". " + usableItems.get(i).getName());
        }
        int choice = readChoice(1, usableItems.size());
        return usableItems.get(choice - 1);
    }

    private Action findSpecialSkill() {
        for (Action action : getActions()) {
            if (!(action instanceof DefendAction)
                    && !(action instanceof UseItemAction)
                    && !"BasicAttack".equals(action.getName())) {
                return action;
            }
        }
        return null;
    }

    private int readChoice(int min, int max) {
        if (sharedScanner == null) {
            throw new IllegalStateException("Player scanner is not configured.");
        }

        while (true) {
            System.out.print("Enter choice: ");
            String input = sharedScanner.nextLine().trim();
            try {
                int choice = Integer.parseInt(input);
                if (choice >= min && choice <= max) {
                    return choice;
                }
            } catch (NumberFormatException ignored) {
            }
            System.out.println("Invalid choice. Please try again.");
        }
    }
}
