package dark_shell.gui.game_window;

import dark_shell.controllers.GameOperationsController;
import dark_shell.dao.*;
import dark_shell.models.database.Character;
import dark_shell.utils.HibernateConfiguration;
import dark_shell.utils.Logger;
import javax.swing.*;

public class AdditionalGameWindows {
    private static volatile GameOperationsController gameOperationsController;

    public static void startNewGame() {
        SwingUtilities.invokeLater(() -> {
            String name = JOptionPane.showInputDialog(
                    null, "Enter the character's name:", "Character Creation", JOptionPane.QUESTION_MESSAGE);

            if (name != null && !name.trim().isEmpty()) {
                Logger.getInstance().info("Start new game");

                GameOperationsController controller = getGameOperationsController();

                selectGameSetting(controller.createNewCharacter(name));
            }
        });
    }

    public static void selectGameSetting(Character character) {
        SwingUtilities.invokeLater(() -> {
            Object[] options = {"Cyberpunk", "Fantasy", "Post apocalypse"};

            int choice = JOptionPane.showOptionDialog(
                    null,
                    "Select the game setting",
                    "Game selection",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[0]);

            if (choice == 0) {
                Logger.getInstance().info("Cyberpunk game setting chose");

                CyberpunkGameWindow gameWindow = new CyberpunkGameWindow(character);
                gameWindow.create();
            } else if (choice == 1) {
                Logger.getInstance().info("Fantasy game setting chose");

                FantasyGameWindow gameWindow = new FantasyGameWindow(character);
                gameWindow.create();
            } else if (choice == 2) {
                Logger.getInstance().info("Post apocalypse game setting chose");

                PostApocalypseGameWindow gameWindow = new PostApocalypseGameWindow(character);
                gameWindow.create();
            } else {
                return;
            }
        });
    }

    private static synchronized GameOperationsController getGameOperationsController() {
        if (gameOperationsController != null) {
            return gameOperationsController;
        }

        CharactersDAO charactersDAO = new CharactersDAO(HibernateConfiguration.getEntityManagerFactory());
        CyberpunkCharacteristicsDAO cyberpunkCharacteristicsDAO =
                new CyberpunkCharacteristicsDAO(HibernateConfiguration.getEntityManagerFactory());
        FantasyCharacteristicsDAO fantasyCharacteristicsDAO =
                new FantasyCharacteristicsDAO(HibernateConfiguration.getEntityManagerFactory());
        PostApocalypseCharacteristicsDAO postApocalypseCharacteristicsDAO =
                new PostApocalypseCharacteristicsDAO(HibernateConfiguration.getEntityManagerFactory());
        StatisticsDAO statisticsDAO = new StatisticsDAO(HibernateConfiguration.getEntityManagerFactory());

        gameOperationsController = new GameOperationsController(
                charactersDAO,
                cyberpunkCharacteristicsDAO,
                fantasyCharacteristicsDAO,
                postApocalypseCharacteristicsDAO,
                statisticsDAO);

        return gameOperationsController;
    }
}
