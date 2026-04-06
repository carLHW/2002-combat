

import ui.GameCLI;
import java.util.Scanner;

public final class Main {
    private Main() {
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            GameCLI cli = new GameCLI(scanner);
            cli.start();
        }
    }
}

