package dark_shell;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;
import dark_shell.dao.*;
import dark_shell.gui.MenuWindow;
import dark_shell.models.database.*;
import dark_shell.models.database.Character;
import dark_shell.utils.ApplicationConfigReader;
import dark_shell.utils.HibernateConfiguration;
import dark_shell.utils.Logger;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.*;

public class App {
    private static final long SCREENSAVER_TIME = 5000;

    public static void main(String[] args) throws IOException {
        ApplicationConfigReader appConfigReader = readConfig();

        Logger.getInstance().info("App started");
        Logger.getInstance().info("Configs loaded");

        prepareDatabase(appConfigReader);
        prepareGUI(appConfigReader);

        Logger.getInstance().info("App configurated");

        startUpApplication(args);
    }

    private static ApplicationConfigReader readConfig() throws FileNotFoundException, IOException {
        return new ApplicationConfigReader();
    }

    private static void startUpApplication(String[] args) {
        MenuWindow window = new MenuWindow();
        SwingUtilities.invokeLater(() -> window.showScreensaver());

        try {
            Thread.sleep(SCREENSAVER_TIME);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        SwingUtilities.invokeLater(() -> window.showWindow());
    }

    private static void prepareDatabase(ApplicationConfigReader appConfigReader) {
        Logger.getInstance().info("Start database prepare");

        HibernateConfiguration.build(appConfigReader);
        registerEntities();

        Logger.getInstance().info("Database ready");
    }

    private static void registerEntities() {
        HibernateConfiguration.addEntity(Statistic.class);
        HibernateConfiguration.addEntity(PostApocalypseCharacteristic.class);
        HibernateConfiguration.addEntity(FantasyCharacteristic.class);
        HibernateConfiguration.addEntity(CyberpunkCharacteristic.class);
        HibernateConfiguration.addEntity(Character.class);
    }

    private static void prepareGUI(ApplicationConfigReader appConfigReader) {
        Logger.getInstance().info("Start GUI prepare");

        if (appConfigReader.getUseLAF()) {
            FlatIntelliJLaf.setup();
            try {
                if (appConfigReader.getUseDark()) {
                    UIManager.setLookAndFeel(new FlatDarkLaf());
                } else {
                    UIManager.setLookAndFeel(new FlatIntelliJLaf());
                }
            } catch (UnsupportedLookAndFeelException e) {
                Logger.getInstance().warning("GUI style setup error");
                Logger.getInstance().warning(e.getMessage());
                e.printStackTrace();
            }
        }

        Logger.getInstance().info("GUI ready");
    }
}
