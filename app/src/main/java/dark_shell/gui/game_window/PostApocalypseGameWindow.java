package dark_shell.gui.game_window;

import dark_shell.controllers.GameOperationsController;
import dark_shell.controllers.PostApocalypseGameController;
import dark_shell.dao.CharactersDAO;
import dark_shell.dao.CyberpunkCharacteristicsDAO;
import dark_shell.dao.FantasyCharacteristicsDAO;
import dark_shell.dao.PostApocalypseCharacteristicsDAO;
import dark_shell.dao.StatisticsDAO;
import dark_shell.models.database.Character;
import dark_shell.models.database.PostApocalypseCharacteristic;
import dark_shell.models.post_apocalypse.Enemy;
import dark_shell.models.post_apocalypse.Hero;
import dark_shell.models.post_apocalypse.Location;
import dark_shell.models.post_apocalypse.Reward;
import dark_shell.utils.HibernateConfiguration;
import dark_shell.utils.Logger;
import dark_shell.utils.SupportFunctions;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import javax.swing.*;

public class PostApocalypseGameWindow extends JFrame {
    private dark_shell.models.database.Character character;
    private PostApocalypseGameController postApocalypseController;
    private GameOperationsController gameOperationsController;

    private JPanel leftPanel;
    private JPanel centerPanel;
    private JPanel rightPanel;
    private JPanel bottomPanel;

    private JLabel heroLevel;
    private JLabel heroHP;
    private JLabel heroAttack;
    private JLabel heroDefence;
    private JLabel heroSpeed;
    private JLabel heroLuck;
    private JLabel heroGold;
    private JLabel heroExperience;
    private JLabel heroExperienceToNextLevel;

    private JLabel enemyHealth;
    private JLabel enemyAttack;
    private JLabel enemyDefense;

    private Hero gameHero;
    private Location gameLocation;
    private long gameLocationDangerLevel;
    private Enemy gameEnemy;
    private Reward reward;
    private Boolean heroHaveInitiative;

    private static final double HP_REGEN_POWER = 0.1;

    public PostApocalypseGameWindow(Character character) {
        this.character = character;

        initController();
        initUI();
    }

    private void initController() {
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

        postApocalypseController = new PostApocalypseGameController();
    }

    private void initUI() {
        Logger.getInstance().info("initializing PostApocalypseGameWindow UI");

        setTitle("Dark Shell — Post-Apocalypse Mode");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1280, 720);
        setLocationRelativeTo(null);
        setResizable(false);

        getContentPane().setBackground(new Color(50, 50, 50));

        setLayout(new BorderLayout(0, 0));

        leftPanel = createLeftPanel();
        centerPanel = createCenterPanel();
        rightPanel = createRightPanel();
        bottomPanel = createBottomPanel();

        add(leftPanel, BorderLayout.WEST);
        add(centerPanel, BorderLayout.CENTER);
        add(rightPanel, BorderLayout.EAST);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private JPanel createLeftPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(40, 40, 45));
        panel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, Color.RED));
        panel.setPreferredSize(new Dimension(280, 0));

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        JLabel title = new JLabel("CHARACTER STATS");
        title.setForeground(Color.RED);
        title.setFont(new Font("Monospaced", Font.BOLD, 18));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(title);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        fillHeroStats(panel);

        return panel;
    }

    private void fillHeroStats(JPanel panel) {
        PostApocalypseCharacteristic hero = character.getPostApocalypseCharacteristics();

        heroLevel = createStatLabel("LEVEL", hero.getLevel().toString());
        heroHP = createStatLabel("HP", hero.getCurrentHP() + "/" + hero.getMaxHP());
        heroAttack = createStatLabel("ATK", hero.getAttack().toString());
        heroDefence = createStatLabel("DEF", hero.getDefence().toString());
        heroSpeed = createStatLabel("SPD", hero.getSpeed().toString());
        heroLuck = createStatLabel("LUCK", hero.getLuck().toString());
        heroGold = createStatLabel("GOLD", hero.getGold().toString());
        heroExperience = createStatLabel("EXP", hero.getExperience().toString());
        heroExperienceToNextLevel =
                createStatLabel("NEXT_LVL", hero.getExperienceToNextLevel().toString());

        panel.add(heroLevel);
        panel.add(heroHP);
        panel.add(heroAttack);
        panel.add(heroDefence);
        panel.add(heroSpeed);
        panel.add(heroLuck);
        panel.add(heroGold);
        panel.add(heroExperience);
        panel.add(heroExperienceToNextLevel);
    }

    private JLabel createStatLabel(String label, String value) {
        String text = label + ": " + value;
        JLabel l = new JLabel(text);
        l.setForeground(Color.WHITE);
        l.setFont(new Font("Dialog", Font.PLAIN, 14));
        l.setAlignmentX(Component.LEFT_ALIGNMENT);
        l.setBorder(BorderFactory.createEmptyBorder(6, 12, 6, 0));
        return l;
    }

    private JPanel createCenterPanel() {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                GradientPaint gp =
                        new GradientPaint(0, 0, new Color(50, 50, 50), 0, getHeight(), new Color(30, 30, 30));
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());

                g2.setColor(new Color(0, 100, 100, 100));
                for (int i = 0; i < 50; i++) {
                    int x1 = (int) (Math.random() * getWidth());
                    int y1 = (int) (Math.random() * getHeight());
                    int x2 = (int) (Math.random() * getWidth());
                    int y2 = (int) (Math.random() * getHeight());
                    g2.drawLine(x1, y1, x2, y2);
                }

                g2.setColor(new Color(255, 100, 0));
                int playerW = 80;
                int playerH = 120;
                int playerX = 60;
                int playerY = getHeight() - playerH - 40;
                g2.fillRoundRect(playerX, playerY, playerW, playerH, 8, 8);
                drawRustBorder(g2, playerX, playerY, playerW, playerH, new Color(255, 100, 0, 120));

                g2.setColor(new Color(150, 50, 50));
                int enemyW = 80;
                int enemyH = 120;
                int enemyX = getWidth() - enemyW - 60;
                int enemyY = getHeight() - enemyH - 40;
                g2.fillRoundRect(enemyX, enemyY, enemyW, enemyH, 8, 8);
                drawRustBorder(g2, enemyX, enemyY, enemyW, enemyH, new Color(150, 50, 50, 120));
            }

            private void drawRustBorder(Graphics2D g2, int x, int y, int w, int h, Color rust) {
                int thickness = 3;
                g2.setColor(rust);
                g2.setStroke(new BasicStroke(thickness));
                g2.drawRoundRect(x - thickness, y - thickness, w + thickness * 2, h + thickness * 2, 12, 12);
            }
        };
        panel.setBackground(new Color(50, 50, 50));
        panel.setOpaque(true);
        return panel;
    }

    private JPanel createRightPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(40, 40, 40));
        panel.setBorder(BorderFactory.createMatteBorder(0, 2, 0, 0, new Color(255, 100, 0)));
        panel.setPreferredSize(new Dimension(280, 0));

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        JLabel title = new JLabel("ENEMY STATS");
        title.setForeground(new Color(255, 100, 50));
        title.setFont(new Font("Monospaced", Font.BOLD, 18));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(title);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        fillEnemyStats(panel);

        return panel;
    }

    private void fillEnemyStats(JPanel panel) {
        enemyHealth = createStatLabel("HP", "-/-");
        enemyAttack = createStatLabel("ATK", "-");
        enemyDefense = createStatLabel("DEF", "-");

        panel.add(enemyHealth);
        panel.add(enemyAttack);
        panel.add(enemyDefense);
    }

    private JPanel createBottomPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(30, 30, 30));
        panel.setBorder(BorderFactory.createMatteBorder(2, 0, 0, 0, new Color(255, 100, 0)));

        FlowLayout layout = new FlowLayout(FlowLayout.CENTER, 40, 15);
        panel.setLayout(layout);

        JButton btnFight = new JButton("Fight");

        setupPostApocalypseButton(btnFight, new Color(0, 255, 100), Color.BLACK);

        btnFight.addActionListener(e -> {
            Logger.getInstance().info("Attack");

            gameStartLogic();
        });

        panel.add(btnFight);
        return panel;
    }

    private void setupPostApocalypseButton(JButton btn, Color fillColor, Color textColor) {
        btn.setForeground(textColor);
        btn.setBackground(fillColor);
        btn.setFont(new Font("Monospaced", Font.BOLD, 16));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btn.getModel().addChangeListener(e -> {
            if (btn.getModel().isRollover()) {
                btn.setBackground(fillColor.brighter());
            } else {
                btn.setBackground(fillColor);
            }
        });
    }

    public void create() {
        Logger.getInstance().info("launch");

        SwingUtilities.invokeLater(() -> setVisible(true));
    }

    private void gameStartLogic() {
        if (gameHero == null) {
            initGame();
        }

        if (gameHero.isDead()) {
            SupportFunctions.showMessage("Hero dead");
            return;
        }

        if (gameEnemy != null && gameEnemy.isDead()) {
            SupportFunctions.showMessage("Hero wins");

            boolean needNewRound = answerNeedNewRound();

            if (needNewRound) {
                Logger.getInstance().info("Continue post-apocalypse game");

                PostApocalypseGameWindow gameWindow = new PostApocalypseGameWindow(character);
                gameWindow.create();

                this.dispose();
            }

            return;
        }

        fightStep();

        if (gameEnemy != null && gameEnemy.isDead() || gameHero.isDead()) {
            saveHero();
        }
    }

    private boolean answerNeedNewRound() {
        boolean needNewRound = false;

        Object[] options = {"Yes", "No"};

        AtomicInteger choice = new AtomicInteger();

        choice.set(JOptionPane.showOptionDialog(
                null,
                "Continue?",
                "Game continue",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]));

        needNewRound = choice.get() == 0;

        return needNewRound;
    }

    private void initGame() {
        PostApocalypseCharacteristic hero = character.getPostApocalypseCharacteristics();
        gameHero = new Hero(
                hero.getLevel(),
                hero.getCurrentHP(),
                hero.getMaxHP(),
                hero.getAttack(),
                hero.getDefence(),
                hero.getSpeed(),
                hero.getLuck(),
                hero.getExperience(),
                hero.getGold());

        updateHeroStats(gameHero);

        Map<Long, Location> locationWithDangerLevel =
                postApocalypseController.generateLocationAndCalculateDangerLevel(gameHero);

        Set<Long> locationDangerLevels = locationWithDangerLevel.keySet();
        gameLocationDangerLevel = locationDangerLevels.stream().findFirst().get();
        gameLocation = locationWithDangerLevel.get(gameLocationDangerLevel);

        SupportFunctions.showMessage("Location danger level: " + gameLocationDangerLevel);
    }

    private void fightStep() {
        if (gameEnemy == null) {
            gameEnemy = postApocalypseController.createEnemy(gameLocationDangerLevel);

            updateEnemyStats(gameEnemy);

            SupportFunctions.showMessage("Enemy generated");

            heroHaveInitiative = postApocalypseController.calculateCombatInitiative(gameHero);

            if (heroHaveInitiative) {
                SupportFunctions.showMessage("The hero has the initiative");
            } else {
                SupportFunctions.showMessage("The enemy has the initiative");
            }
        }

        fight(heroHaveInitiative, gameEnemy);

        if (gameHero.isDead() || gameEnemy.isDead()) {
            updateEnemyStats(gameEnemy);
            updateHeroStats(gameHero);

            reward = postApocalypseController.finalizeGame(gameHero, gameLocation);

            if (reward == null) {
                SupportFunctions.showMessage("Hero dead");
            } else {
                SupportFunctions.showMessage("Hero wins");
            }
        }
    }

    private void fight(boolean heroHaveInitiative, Enemy enemy) {
        if (heroHaveInitiative) {
            if (!gameHero.isDead() && !enemy.isDead()) {
                postApocalypseController.makeMoveHeroFirst(gameHero, enemy);
            }
        } else {
            if (!gameHero.isDead() && !enemy.isDead()) {
                postApocalypseController.makeMoveEnemyFirst(gameHero, enemy);
            }
        }

        updateEnemyStats(gameEnemy);
        updateHeroStats(gameHero);
    }

    private void updateEnemyStats(Enemy enemy) {
        enemyHealth.setText("HP: " + enemy.getHealth());
        enemyAttack.setText("ATK: " + enemy.getAttack());
        enemyDefense.setText("DEF: " + enemy.getDefense());
    }

    private void updateHeroStats(Hero gameHero) {
        heroLevel.setText("LEVEL: " + gameHero.getLevel());
        heroHP.setText("HP: " + gameHero.getCurrentHP() + "/" + gameHero.getMaxHP());
        heroAttack.setText("ATK: " + gameHero.getAttack());
        heroDefence.setText("DEF: " + gameHero.getDefense());
        heroSpeed.setText("SPD: " + gameHero.getSpeed());
        heroLuck.setText("LUCK: " + gameHero.getLuck());
        heroGold.setText("GOLD: " + gameHero.getGold());
        heroExperience.setText("EXP: " + gameHero.getExperience());
        heroExperienceToNextLevel.setText("NEXT_LVL: " + gameHero.getExperienceToNextLevel());
    }

    private void saveHero() {
        if (reward != null) {
            giveReward();
        }

        long newHP = gameHero.getCurrentHP();

        if (reward != null) {
            newHP = Double.valueOf(gameHero.getCurrentHP() + gameHero.getMaxHP() * HP_REGEN_POWER)
                    .intValue();
        }

        if (newHP > gameHero.getMaxHP()) {
            newHP = gameHero.getMaxHP();
        }

        long experienceToNextLevel = gameHero.getExperienceToNextLevel();
        long currentLevel = gameHero.getLevel();
        long levelUpCount = 0;

        while (gameHero.getExperience() > experienceToNextLevel) {
            levelUpCount++;
            experienceToNextLevel = gameHero.calculateExperienceToNextLevel(currentLevel + 1);
            currentLevel++;
        }

        java.util.List<Long> additionalSkillpoints = java.util.List.of(0L, 0L, 0L, 0L);

        if (levelUpCount > 0) {
            long skillPointsCount = levelUpCount * 2;

            additionalSkillpoints = chooseSkillpoints(skillPointsCount, currentLevel, gameLocationDangerLevel);
        }

        character.getPostApocalypseCharacteristics().setLevel(currentLevel);
        character.getPostApocalypseCharacteristics().setCurrentHP(newHP);
        character.getPostApocalypseCharacteristics().setMaxHP(gameHero.getMaxHP());
        character.getPostApocalypseCharacteristics().setAttack(gameHero.getAttack() + additionalSkillpoints.get(0));
        character.getPostApocalypseCharacteristics().setDefence(gameHero.getDefense() + additionalSkillpoints.get(1));
        character.getPostApocalypseCharacteristics().setSpeed(gameHero.getSpeed() + additionalSkillpoints.get(2));
        character.getPostApocalypseCharacteristics().setLuck(gameHero.getLuck() + additionalSkillpoints.get(3));
        character.getPostApocalypseCharacteristics().setGold(gameHero.getGold());
        character.getPostApocalypseCharacteristics().setExperience(gameHero.getExperience());
        character.getPostApocalypseCharacteristics().setExperienceToNextLevel(experienceToNextLevel);

        updateStatistic();

        gameOperationsController.updateCharacter(character);
    }

    private void giveReward() {
        reward.giveReward();
    }

    private void updateStatistic() {
        LocalDate currentDate = Instant.now().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate lastPlayDate = character
                .getStatistic()
                .getLastPlayDate()
                .toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        long daysInGame = character.getStatistic().getDaysInGame();

        if (!lastPlayDate.equals(currentDate)) {
            daysInGame++;
            lastPlayDate = currentDate;
        }

        long eventsConducted = character.getStatistic().getEventsConducted() + 1;
        long numberOfSuccesfullEvents = character.getStatistic().getNumberOfSuccessfulEvents();

        if (character.getPostApocalypseCharacteristics().getCurrentHP() > 0) {
            numberOfSuccesfullEvents++;
        }

        long maxLevel = character.getStatistic().getMaxLevel();

        if (character.getPostApocalypseCharacteristics().getLevel() > maxLevel) {
            maxLevel = character.getPostApocalypseCharacteristics().getLevel();
        }

        long maxExperience = character.getStatistic().getMaxExperience();

        if (character.getPostApocalypseCharacteristics().getExperience() > maxExperience) {
            maxExperience = character.getPostApocalypseCharacteristics().getExperience();
        }

        character
                .getStatistic()
                .setLastPlayDate(Date.from(
                        lastPlayDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        character.getStatistic().setDaysInGame(daysInGame);
        character.getStatistic().setEventsConducted(eventsConducted);
        character.getStatistic().setNumberOfSuccessfulEvents(numberOfSuccesfullEvents);
        character.getStatistic().setMaxLevel(maxLevel);
        character.getStatistic().setMaxExperience(maxExperience);
    }

    private java.util.List<Long> chooseSkillpoints(long skillPointsCount, long heroLevel, long locationDangerLevel) {
        Long attack = 0L;
        Long defence = 0L;
        Long speed = 0L;
        Long luck = 0L;

        Object[] options = {"Attack", "Defence", "Speed", "Luck"};

        AtomicInteger choice = new AtomicInteger();

        for (int i = 0; i < skillPointsCount; i++) {
            choice.set(JOptionPane.showOptionDialog(
                    null,
                    "Select skillpoints",
                    "Skillpoints selection",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[0]));

            int elementsCount = 2;

            if (choice.get() == 0) {
                attack += (heroLevel + locationDangerLevel) / elementsCount;
            } else if (choice.get() == 1) {
                defence += (heroLevel + locationDangerLevel) / elementsCount;
            } else if (choice.get() == 2) {
                speed += (heroLevel + locationDangerLevel) / elementsCount;
            } else if (choice.get() == 3) {
                luck += (heroLevel + locationDangerLevel) / elementsCount;
            }
        }

        return List.of(attack, defence, speed, luck);
    }
}
