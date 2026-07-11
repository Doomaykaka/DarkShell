package dark_shell.utils;

import dark_shell.models.database.CyberpunkCharacteristic;
import dark_shell.models.database.FantasyCharacteristic;
import dark_shell.models.database.PostApocalypseCharacteristic;
import dark_shell.models.database.Statistic;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.*;
import java.net.URL;
import java.time.Instant;
import java.util.*;
import java.util.List;
import javax.swing.*;

public class SupportFunctions {
    public static long randomAtOneToFive() {
        Random random = new Random();
        return random.nextInt(5) + 1;
    }

    public static long randomAtOneToSix() {
        Random random = new Random();
        return random.nextInt(6) + 1;
    }

    public static long randomAtOneToEight() {
        Random random = new Random();
        return random.nextInt(8) + 1;
    }

    public static long randomAtOneToTen() {
        Random random = new Random();
        return random.nextInt(10) + 1;
    }

    public static long randomAtOneToFifteen() {
        Random random = new Random();
        return random.nextInt(15) + 1;
    }

    public static long randomAtOneToTwenty() {
        Random random = new Random();
        return random.nextInt(20) + 1;
    }

    public static dark_shell.models.database.Character createEmptyCharacter(String name) {
        Date creationDate = Date.from(Instant.now());
        CyberpunkCharacteristic cyberpunkCharacteristics = createEmptyCyberpunkCharacteristic();
        FantasyCharacteristic fantasyCharacteristics = createEmptyFantasyCharacteristic();
        PostApocalypseCharacteristic postApocalypseCharacteristics = createEmptyPostApocalypseCharacteristic();
        Statistic statistic = createEmptyStatistic();

        return new dark_shell.models.database.Character(
                name,
                creationDate,
                cyberpunkCharacteristics,
                fantasyCharacteristics,
                postApocalypseCharacteristics,
                statistic);
    }

    public static CyberpunkCharacteristic createEmptyCyberpunkCharacteristic() {
        Long level = 1L;
        Long currentHP = 100L;
        Long maxHP = 100L;
        Long infiltration = 10L;
        Long technic = 5L;
        Long cool = 8L;
        Long reputation = 3L;
        Long noise = 0L;
        Long experience = 0L;
        Long yen = 0L;
        Long experienceToNextLevel = 100L;

        return new CyberpunkCharacteristic(
                level,
                currentHP,
                maxHP,
                infiltration,
                technic,
                cool,
                reputation,
                noise,
                experience,
                yen,
                experienceToNextLevel);
    }

    public static FantasyCharacteristic createEmptyFantasyCharacteristic() {
        Long level = 1L;
        Long currentHP = 100L;
        Long maxHP = 100L;
        Long manaPoints = 80L;
        Long maxManaPoints = 80L;
        Long strength = 10L;
        Long dexterity = 10L;
        Long intelligence = 8L;
        Long willpower = 8L;
        Long reputation = 0L;
        Long gold = 0L;
        Long encumbrance = 0L;
        Long maxEncumbrance = 50L;
        Long experience = 0L;
        Long experienceToNextLevel = 150L;
        Long royalGuildFactionPoints = 0L;
        Long mageGuildFactionPoints = 0L;
        Long thievesGuildFactionPoints = 0L;
        Long barbarianTribeFactionPoints = 0L;

        return new FantasyCharacteristic(
                level,
                currentHP,
                maxHP,
                manaPoints,
                maxManaPoints,
                strength,
                dexterity,
                intelligence,
                willpower,
                reputation,
                gold,
                encumbrance,
                maxEncumbrance,
                experience,
                experienceToNextLevel,
                royalGuildFactionPoints,
                mageGuildFactionPoints,
                thievesGuildFactionPoints,
                barbarianTribeFactionPoints);
    }

    public static PostApocalypseCharacteristic createEmptyPostApocalypseCharacteristic() {
        Long level = 1L;
        Long currentHP = 100L;
        Long maxHP = 100L;
        Long attack = 10L;
        Long defence = 5L;
        Long speed = 8L;
        Long luck = 3L;
        Long gold = 0L;
        Long experience = 0L;
        Long experienceToNextLevel = 100L;

        return new PostApocalypseCharacteristic(
                level, currentHP, maxHP, attack, defence, speed, luck, gold, experience, experienceToNextLevel);
    }

    public static Statistic createEmptyStatistic() {
        Date lastPlayDate = Date.from(Instant.now());
        Long daysInGame = 1L;
        Long eventsConducted = 0L;
        Long numberOfSuccessfulEvents = 0L;
        Long maxLevel = 1L;
        Long maxExperience = 0L;

        return new Statistic(
                lastPlayDate, daysInGame, eventsConducted, numberOfSuccessfulEvents, maxLevel, maxExperience);
    }

    public static void writeContentInNewFile(File folderToSave, String name, List<String> content) {
        try {
            File file = new File(folderToSave, name);

            if (file.createNewFile()) {
                FileWriter writer = new FileWriter(file);

                for (String line : content) {
                    writer.append(line + System.lineSeparator());
                }

                writer.close();
            }
        } catch (NullPointerException | IOException e) {
            System.out.println("Error: save file problems");
        }
    }

    public static List<String> readFileContent(FileReader file) throws FileNotFoundException {
        List<String> fileContent = new ArrayList<>();

        Scanner scanner = new Scanner(file);

        while (scanner.hasNextLine()) {
            fileContent.add(scanner.nextLine());
        }

        scanner.close();

        return fileContent;
    }

    public static Rectangle getWindowBounds(int width, int height) {
        Rectangle bounds = null;

        Rectangle screenSize = getScreenSize();
        int xCenter = (int) (screenSize.getWidth() / 2);
        int yCenter = (int) (screenSize.getHeight() / 2);

        int windowX = xCenter - width / 2;
        int windowY = yCenter - height / 2;

        bounds = new Rectangle(windowX, windowY, width, height);

        return bounds;
    }

    public static Rectangle getScreenSize() {
        Rectangle screenSize = null;

        Dimension screenDimension = Toolkit.getDefaultToolkit().getScreenSize();
        screenSize = new Rectangle((int) screenDimension.getWidth(), (int) screenDimension.getHeight());

        return screenSize;
    }

    public static Image getAppIcon() {
        Image appIcon = null;

        URL appIconUrl = SupportFunctions.class.getResource("/dark_shell/images/app_icon.jpg");
        appIcon = Toolkit.getDefaultToolkit().getImage(appIconUrl);

        return appIcon;
    }

    public static ImageIcon getResourceImage(String name) {
        String path = "dark_shell/images/" + name;
        URL resourceURL = SupportFunctions.class.getClassLoader().getResource(path);

        return new ImageIcon(resourceURL);
    }

    public static JPanel getEntityWindowCheckbox(String label) {
        JPanel result = null;

        result = new JPanel();
        BoxLayout layout = new BoxLayout(result, BoxLayout.X_AXIS);
        result.setLayout(layout);

        JCheckBox input = new JCheckBox();

        result.add(new JLabel(label));
        result.add(input);

        return result;
    }

    public static void setEntityWindowCheckboxValue(JPanel panel, boolean value) {
        int checkboxIndex = 1;

        JCheckBox checkbox = (JCheckBox) panel.getComponent(checkboxIndex);
        checkbox.getModel().setSelected(value);
    }

    public static boolean getEntityWindowCheckboxValue(JPanel panel) {
        int checkboxIndex = 1;

        JCheckBox checkbox = (JCheckBox) panel.getComponent(checkboxIndex);
        return checkbox.isSelected();
    }

    public static void addButtonWithGap(JPanel panel, JButton button, int gap) {
        panel.add(button);
        panel.add(Box.createRigidArea(new Dimension(gap, gap)));
    }

    public static void showMessage(String message) {
        JOptionPane.showMessageDialog(null, message, "Information", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void addChildPanelWithGap(JPanel panel, JPanel child, int gap) {
        panel.add(child);
        panel.add(Box.createRigidArea(new Dimension(gap, gap)));
    }

    public static File chooseFile() {
        File selectedFile = null;

        JFileChooser fileChooser = new JFileChooser();
        int state = fileChooser.showOpenDialog(null);

        if (state == JFileChooser.APPROVE_OPTION) {
            selectedFile = fileChooser.getSelectedFile();
        }

        return selectedFile;
    }

    public static File saveFile() {
        File selectedFile = null;

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.showSaveDialog(null);
        selectedFile = fileChooser.getSelectedFile();

        return selectedFile;
    }

    public static MouseListener getOnClickListener(Runnable callback) {
        return new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                callback.run();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                ;
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                ;
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                ;
            }

            @Override
            public void mouseExited(MouseEvent e) {
                ;
            }
        };
    }
}
