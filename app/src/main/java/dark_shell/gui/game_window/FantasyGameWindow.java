package dark_shell.gui.game_window;

import dark_shell.models.database.Character;
import dark_shell.utils.Logger;
import javax.swing.*;

public class FantasyGameWindow extends JFrame {
    private dark_shell.models.database.Character character;

    public FantasyGameWindow(Character character) {
        this.character = character;
    }

    public void create() {
        Logger.getInstance().info("launch");

        SwingUtilities.invokeLater(() -> setVisible(true));
    }
}
