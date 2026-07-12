package dark_shell.gui.game_window;

import dark_shell.models.database.Character;
import dark_shell.utils.Logger;
import javax.swing.*;

public class CyberpunkGameWindow extends JFrame {
    private Character character;

    public CyberpunkGameWindow(Character character) {
        this.character = character;
    }

    public void create() {
        Logger.getInstance().info("launch");

        SwingUtilities.invokeLater(() -> setVisible(true));
    }
}
