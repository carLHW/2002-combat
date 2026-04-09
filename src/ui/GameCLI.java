package ui;

import api.Combatant;
import api.Item;
import api.Team;
import combatants.Warrior;
import combatants.Wizard;
import engine.BattleEngine;
import engine.BattleResult;
import items.PotionItem;
import items.PowerStoneItem;
import items.SmokeBombItem;
import level.Difficulty;
import level.LevelFactory;
import level.LevelSetup;
import model.AbstractPlayer;
import model.SpeedTurnOrderStrategy;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public final class GameCLI {
    private static final int ITEM_COUNT = 2;
    private static final int POTION_CHOICE = 1;
    private static final int POWER_STONE_CHOICE = 2;
    private final Scanner scanner;

    public GameCLI(Scanner scanner) {
        this.scanner = scanner;
    }

    public void start() {
        boolean keepPlaying = true;
        boolean reusePreviousSetup = false;
        boolean hasPreviousSetup = false;
        int playerChoice = 1;
        int difficultyChoice = 1;
        int[] itemChoices = new int[ITEM_COUNT];

        while (keepPlaying) {
            showWelcomeScreen();

            if (!reusePreviousSetup || !hasPreviousSetup) {
                playerChoice = choosePlayerOption();
                itemChoices = chooseItemChoices();
                difficultyChoice = chooseDifficultyOption();
                hasPreviousSetup = true;
            }

            Combatant player = buildPlayer(playerChoice);
            applyStartingItems(player, itemChoices);
            Difficulty difficulty = difficultyFromChoice(difficultyChoice);

            showSetupSummary(player, difficulty);
            showBattleStart();

            LevelSetup setup = LevelFactory.createLevel(difficulty);
            List<Combatant> initialCombatants = new ArrayList<>();
            initialCombatants.add(player);
            initialCombatants.addAll(setup.initialEnemies());

            BattleEngine engine = new BattleEngine(
                    new SpeedTurnOrderStrategy(),
                    initialCombatants,
                    setup.backupEnemies()
            );

            BattleResult result = engine.runBattle();
            int nextStep = showEndScreen(result, player);
            keepPlaying = nextStep != 3;
            reusePreviousSetup = nextStep == 1;
        }
    }

    private void showWelcomeScreen() {
        System.out.println("====================================");
        System.out.println("   TURN-BASED COMBAT ARENA");
        System.out.println("====================================");
        System.out.println("Choose your player and difficulty.");
        System.out.println();
    }

    private int choosePlayerOption() {
        System.out.println("Choose Player:");
        System.out.println("1. Warrior  HP:260 ATK:40 DEF:20 SPD:30");
        System.out.println("   Special Skill: Shield Bash");
        System.out.println("2. Wizard   HP:200 ATK:50 DEF:10 SPD:20");
        System.out.println("   Special Skill: Arcane Blast");

        int choice = readChoice(1, 2);
        System.out.println();
        return choice;
    }

    private int chooseDifficultyOption() {
        System.out.println("Choose Difficulty:");
        System.out.println("1. Easy   - 3 Goblins");
        System.out.println("2. Medium - 1 Goblin, 1 Wolf, then 2 Wolves backup");
        System.out.println("3. Hard   - 2 Goblins, then 1 Goblin and 2 Wolves backup");

        int choice = readChoice(1, 3);
        System.out.println();
        return choice;
    }

    private int[] chooseItemChoices() {
        int[] itemChoices = new int[ITEM_COUNT];
        System.out.println("Choose 2 starting items. Duplicates are allowed.");
        for (int pick = 1; pick <= ITEM_COUNT; pick++) {
            System.out.println("Pick item " + pick + ":");
            showItemOptions();
            itemChoices[pick - 1] = readChoice(1, 3);
            System.out.println();
        }
        return itemChoices;
    }

    private Combatant buildPlayer(int playerChoice) {
        if (playerChoice == 1) {
            return new Warrior("Warrior");
        }
        return new Wizard("Wizard");
    }

    private Difficulty difficultyFromChoice(int difficultyChoice) {
        return switch (difficultyChoice) {
            case 1 -> Difficulty.EASY;
            case 2 -> Difficulty.MEDIUM;
            default -> Difficulty.HARD;
        };
    }

    private void applyStartingItems(Combatant player, int[] itemChoices) {
        if (!(player instanceof AbstractPlayer abstractPlayer)) {
            return;
        }

        abstractPlayer.getInventory().clear();
        for (int itemChoice : itemChoices) {
            abstractPlayer.getInventory().addItem(createItem(itemChoice));
        }
    }

    private void showSetupSummary(Combatant player, Difficulty difficulty) {
        System.out.println("Setup Summary");
        System.out.println("Player: " + player.getName());
        System.out.println("Difficulty: " + difficulty);
        showStartingItems(player);
    }

    private void showStartingItems(Combatant player) {
        System.out.println("Starting Inventory:");
        if (player instanceof AbstractPlayer abstractPlayer) {
            abstractPlayer.getInventory().getItems().forEach(item -> System.out.println("- " + item.getName()));
        }
        System.out.println();
    }

    private void showBattleStart() {
        System.out.println("Battle Start");
        System.out.println("Press Enter to begin battle...");
        scanner.nextLine();
    }

    private void showItemOptions() {
        System.out.println("1. Potion");
        System.out.println("2. Power Stone");
        System.out.println("3. Smoke Bomb");
    }

    private Item createItem(int choice) {
        return switch (choice) {
            case POTION_CHOICE -> new PotionItem(100);
            case POWER_STONE_CHOICE -> new PowerStoneItem();
            default -> new SmokeBombItem();
        };
    }

    private int showEndScreen(BattleResult result, Combatant player) {
        System.out.println();
        System.out.println("====================================");
        if (result.winner() == Team.PLAYER) {
            System.out.println("Victory!");
            System.out.println("Remaining HP: " + player.getCurrentHp() + "/" + player.getMaxHp());
            System.out.println("Total Rounds: " + result.roundsCompleted());
        } else {
            System.out.println("Defeat!");
            System.out.println("Rounds Survived: " + result.roundsCompleted());
        }
        System.out.println("====================================");

        System.out.println("What would you like to do next?");
        System.out.println("1. Replay with the same setup");
        System.out.println("2. Start a new game");
        System.out.println("3. Exit");
        int choice = readChoice(1, 3);
        System.out.println();
        return choice;
    }

    private int readChoice(int min, int max) {
        while (true) {
            System.out.print("Enter choice: ");
            String input = scanner.nextLine().trim();
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
