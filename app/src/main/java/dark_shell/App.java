package dark_shell;

import dark_shell.controllers.GameOperationsController;
import dark_shell.dao.*;
import dark_shell.models.database.*;
import dark_shell.models.database.Character;
import dark_shell.utils.ApplicationConfigReader;
import dark_shell.utils.HibernateConfiguration;
import dark_shell.utils.Logger;
import java.io.FileNotFoundException;
import java.io.IOException;

public class App {
    public static void main(String[] args) throws FileNotFoundException, IOException {
        Logger.getInstance().info("App started");

        ApplicationConfigReader appConfigReader = readConfig();

        Logger.getInstance().info("Configs loaded");

        startUpDatabase(appConfigReader);
        startUpApplication(args);
    }

    private static ApplicationConfigReader readConfig() throws FileNotFoundException, IOException {
        return new ApplicationConfigReader();
    }

    private static void startUpApplication(String[] args) {
        // TODO

        CharactersDAO charactersDAO = new CharactersDAO(HibernateConfiguration.getEntityManagerFactory());
        CyberpunkCharacteristicsDAO cyberpunkCharacteristicsDAO =
                new CyberpunkCharacteristicsDAO(HibernateConfiguration.getEntityManagerFactory());
        FantasyCharacteristicsDAO fantasyCharacteristicsDAO =
                new FantasyCharacteristicsDAO(HibernateConfiguration.getEntityManagerFactory());
        PostApocalypseCharacteristicsDAO postApocalypseCharacteristicsDAO =
                new PostApocalypseCharacteristicsDAO(HibernateConfiguration.getEntityManagerFactory());
        StatisticsDAO statisticsDAO = new StatisticsDAO(HibernateConfiguration.getEntityManagerFactory());

        GameOperationsController gameOperationsController = new GameOperationsController(
                charactersDAO,
                cyberpunkCharacteristicsDAO,
                fantasyCharacteristicsDAO,
                postApocalypseCharacteristicsDAO,
                statisticsDAO);
    }

    private static void startUpDatabase(ApplicationConfigReader appConfigReader) {
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
}
