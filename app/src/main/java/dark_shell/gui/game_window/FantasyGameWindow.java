package dark_shell.gui.game_window;

import dark_shell.controllers.FantasyGameController;
import dark_shell.controllers.GameOperationsController;
import dark_shell.dao.CharactersDAO;
import dark_shell.dao.CyberpunkCharacteristicsDAO;
import dark_shell.dao.FantasyCharacteristicsDAO;
import dark_shell.dao.PostApocalypseCharacteristicsDAO;
import dark_shell.dao.StatisticsDAO;
import dark_shell.models.database.Character;
import dark_shell.models.database.FantasyCharacteristic;
import dark_shell.models.fantasy.Enemy;
import dark_shell.models.fantasy.Hero;
import dark_shell.models.fantasy.Location;
import dark_shell.models.fantasy.Reward;
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

public class FantasyGameWindow extends JFrame {
    private dark_shell.models.database.Character character;
    private FantasyGameController fantasyGameController;
    private GameOperationsController gameOperationsController;

    private JPanel leftPanel;
    private JPanel centerPanel;
    private JPanel rightPanel;
    private JPanel bottomPanel;

    private JLabel level;
    private JLabel HP;
    private JLabel mana;
    private JLabel strength;
    private JLabel dexterity;
    private JLabel intelligence;
    private JLabel willpower;
    private JLabel reputation;
    private JLabel gold;
    private JLabel encumbrance;
    private JLabel experience;
    private JLabel experienceToNextLevel;
    private JLabel royalGuildFactionPoints;
    private JLabel mageGuildFactionPoints;
    private JLabel thievesGuildFactionPoints;
    private JLabel barbarianTribeFactionPoints;

    private JLabel enemyHealth;
    private JLabel enemyAttack;
    private JLabel enemyDefense;

    private Hero hero;
    private Location location;
    private long locationDifficultyLevel;
    private Enemy enemy;
    private Reward reward;
    private Boolean heroHaveInitiative;

    private static final double HP_REGEN_POWER = 0.1;
    private static final double MP_REGEN_POWER = 0.5;

    public FantasyGameWindow(Character character) {
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

        fantasyGameController = new FantasyGameController();
    }

    private void initUI() {
        Logger.getInstance().info("initializing FantasyGameWindow UI");

        setTitle("Dark Shell — Fantasy Mode");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1280, 720);
        setLocationRelativeTo(null);
        setResizable(false);

        getContentPane().setBackground(new Color(40, 20, 10));

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
        panel.setBackground(new Color(50, 25, 15));
        panel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, new Color(255, 200, 100)));
        panel.setPreferredSize(new Dimension(280, 0));

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        JLabel title = new JLabel("CHARACTER STATS");
        title.setForeground(new Color(255, 200, 100));
        title.setFont(new Font("Times New Roman", Font.BOLD, 16));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(title);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        fillHeroStats(panel);

        return panel;
    }

    private void fillHeroStats(JPanel panel) {
        FantasyCharacteristic hero = character.getFantasyCharacteristics();

        level = createStatLabel("LEVEL", hero.getLevel().toString());
        HP = createStatLabel("HP", hero.getCurrentHP() + "/" + hero.getMaxHP());
        mana = createStatLabel("MP", hero.getManaPoints().toString() + "/" + hero.getMaxManaPoints());
        strength = createStatLabel("STR", hero.getStrength().toString());
        dexterity = createStatLabel("DEX", hero.getDexterity().toString());
        intelligence = createStatLabel("INT", hero.getIntelligence().toString());
        willpower = createStatLabel("WILL", hero.getWillpower().toString());
        reputation = createStatLabel("REP", hero.getReputation().toString());
        gold = createStatLabel("GOLD", hero.getGold().toString());
        encumbrance = createStatLabel("ENC", hero.getEncumbrance().toString() + "/" + hero.getMaxEncumbrance());
        experience = createStatLabel("EXP", hero.getExperience().toString());
        experienceToNextLevel =
                createStatLabel("NEXT_LVL", hero.getExperienceToNextLevel().toString());

        royalGuildFactionPoints = createStatLabel(
                "ROYAL_GUILD_FP", hero.getRoyalGuildFactionPoints().toString());
        mageGuildFactionPoints = createStatLabel(
                "MAGE_GUILD_FP", hero.getMageGuildFactionPoints().toString());
        thievesGuildFactionPoints = createStatLabel(
                "THIEVES_GUILD_FP", hero.getThievesGuildFactionPoints().toString());
        barbarianTribeFactionPoints = createStatLabel(
                "BARBARIAN_TRIBE_FP", hero.getBarbarianTribeFactionPoints().toString());

        panel.add(level);
        panel.add(HP);
        panel.add(mana);
        panel.add(strength);
        panel.add(dexterity);
        panel.add(intelligence);
        panel.add(willpower);
        panel.add(reputation);
        panel.add(gold);
        panel.add(encumbrance);
        panel.add(experience);
        panel.add(experienceToNextLevel);
        panel.add(new JLabel(""));
        panel.add(royalGuildFactionPoints);
        panel.add(mageGuildFactionPoints);
        panel.add(thievesGuildFactionPoints);
        panel.add(barbarianTribeFactionPoints);
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
                        new GradientPaint(0, 0, new Color(40, 20, 10), 0, getHeight(), new Color(80, 40, 20));
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());

                g2.setColor(new Color(150, 120, 80));
                for (int i = 0; i <= 10; i++) {
                    g2.drawLine(
                            0, (int) (Math.random() * getHeight()), getWidth(), (int) (Math.random() * getHeight()));
                }

                g2.setColor(new Color(255, 200, 100));
                int playerW = 80;
                int playerH = 120;
                int playerX = 60;
                int playerY = getHeight() - playerH - 40;
                g2.fillRoundRect(playerX, playerY, playerW, playerH, 12, 12);
                drawGlowBorder(g2, playerX, playerY, playerW, playerH, new Color(255, 200, 100, 160));

                g2.setColor(new Color(200, 100, 50));
                int enemyW = 80;
                int enemyH = 120;
                int enemyX = getWidth() - enemyW - 60;
                int enemyY = getHeight() - enemyH - 40;
                g2.fillRoundRect(enemyX, enemyY, enemyW, enemyH, 12, 12);
                drawGlowBorder(g2, enemyX, enemyY, enemyW, enemyH, new Color(200, 100, 50, 160));
            }

            private void drawGlowBorder(Graphics2D g2, int x, int y, int w, int h, Color glow) {
                int thickness = 3;
                g2.setColor(glow);
                g2.setStroke(new BasicStroke(thickness));
                g2.drawRoundRect(x - thickness, y - thickness, w + thickness * 2, h + thickness * 2, 16, 16);
            }
        };
        panel.setBackground(new Color(40, 20, 10));
        panel.setOpaque(true);
        return panel;
    }

    private JPanel createRightPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(50, 20, 15));
        panel.setBorder(BorderFactory.createMatteBorder(0, 2, 0, 0, new Color(255, 200, 100)));
        panel.setPreferredSize(new Dimension(280, 0));

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        JLabel title = new JLabel("ENEMY STATS");
        title.setForeground(new Color(255, 200, 100));
        title.setFont(new Font("Times New Roman", Font.BOLD, 16));
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
        panel.setBackground(new Color(30, 15, 5));
        panel.setBorder(BorderFactory.createMatteBorder(2, 0, 0, 0, new Color(255, 200, 100)));

        FlowLayout layout = new FlowLayout(FlowLayout.CENTER, 40, 15);
        panel.setLayout(layout);

        JButton btnFight = new JButton("COMBAT TACTIC");
        JButton btnMagic = new JButton("MAGIC TACTIC");

        setupFantasyButton(btnFight, new Color(255, 200, 100), Color.BLACK);
        setupFantasyButton(btnMagic, new Color(100, 100, 255), Color.WHITE);

        btnFight.addActionListener(e -> {
            Logger.getInstance().info("Combat tactic selected");
            gameStartLogic(Hero.Tactics.FIGHT);
        });

        btnMagic.addActionListener(e -> {
            Logger.getInstance().info("Magic tactic selected");
            gameStartLogic(Hero.Tactics.MAGIC);
        });

        panel.add(btnFight);
        panel.add(btnMagic);
        return panel;
    }

    private void setupFantasyButton(JButton btn, Color fillColor, Color textColor) {
        btn.setForeground(textColor);
        btn.setBackground(fillColor);
        btn.setFont(new Font("Times New Roman", Font.BOLD, 18));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createLineBorder(new Color(255, 200, 100), 2));
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

    private void gameStartLogic(Hero.Tactics tactic) {
        if (hero == null) {
            initGame(tactic);
        } else {
            hero.setCurrentTactics(tactic);
        }

        if (hero.isDead()) {
            SupportFunctions.showMessage("Hero dead");
            return;
        }

        if (hero.getManaPoints() <= 0 && tactic.equals(Hero.Tactics.MAGIC)) {
            SupportFunctions.showMessage("Not enought mana");
            return;
        }

        if (enemy != null && enemy.isDead()) {
            SupportFunctions.showMessage("Hero wins");

            boolean needNewRound = answerNeedNewRound();

            if (needNewRound) {
                Logger.getInstance().info("Continue fantasy game");

                FantasyGameWindow gameWindow = new FantasyGameWindow(character);
                gameWindow.create();

                this.dispose();
            }

            return;
        }

        step();

        if (enemy != null && enemy.isDead() || hero.isDead()) {
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

    private void initGame(Hero.Tactics tactic) {
        FantasyCharacteristic fantasyHero = character.getFantasyCharacteristics();
        hero = new Hero(
                fantasyHero.getLevel(),
                fantasyHero.getCurrentHP(),
                fantasyHero.getMaxHP(),
                fantasyHero.getManaPoints(),
                fantasyHero.getMaxManaPoints(),
                fantasyHero.getStrength(),
                fantasyHero.getDexterity(),
                fantasyHero.getIntelligence(),
                fantasyHero.getWillpower(),
                fantasyHero.getReputation(),
                fantasyHero.getGold(),
                fantasyHero.getEncumbrance(),
                fantasyHero.getMaxEncumbrance(),
                fantasyHero.getExperience(),
                fantasyHero.getExperienceToNextLevel(),
                fantasyHero.getRoyalGuildFactionPoints(),
                fantasyHero.getMageGuildFactionPoints(),
                fantasyHero.getThievesGuildFactionPoints(),
                fantasyHero.getBarbarianTribeFactionPoints(),
                tactic);

        updateHeroStats(hero);

        Map<Long, Location> locationWithDifficultyLevelOfTheLocation =
                fantasyGameController.generateLocationAndCalculateDifficultyLevelOfTheLocation(hero);

        Set<Long> locationWithDifficultyLevels = locationWithDifficultyLevelOfTheLocation.keySet();
        locationDifficultyLevel =
                locationWithDifficultyLevels.stream().findFirst().get();
        location = locationWithDifficultyLevelOfTheLocation.get(locationDifficultyLevel);

        SupportFunctions.showMessage("Location difficulty level: " + locationDifficultyLevel);
    }

    private void step() {
        if (enemy == null) {
            enemy = fantasyGameController.createEnemy(locationDifficultyLevel);

            updateEnemyStats(enemy);

            SupportFunctions.showMessage("Enemy generated");

            heroHaveInitiative = fantasyGameController.calculateCombatInitiative(hero, location);

            if (heroHaveInitiative) {
                SupportFunctions.showMessage("The hero has the initiative");
            } else {
                SupportFunctions.showMessage("The enemy has the initiative");
            }
        }

        fight(heroHaveInitiative, enemy, hero);

        if (hero.isDead() || enemy.isDead()) {
            updateEnemyStats(enemy);
            updateHeroStats(hero);

            reward = fantasyGameController.finalizeGame(hero, location);

            if (reward == null) {
                SupportFunctions.showMessage("Hero dead");
            } else {
                SupportFunctions.showMessage("Hero wins");
            }
        }
    }

    private void updateEnemyStats(Enemy enemy) {
        enemyHealth.setText("HP: " + enemy.getHealth());
        enemyAttack.setText("ATK: " + enemy.getAttack());
        enemyDefense.setText("DEF: " + enemy.getDefense());
    }

    private void fight(boolean heroHaveInitiative, Enemy enemy, Hero gameHero) {
        if (heroHaveInitiative) {
            if (!gameHero.isDead() && !enemy.isDead()) {
                fantasyGameController.makeMoveHeroFirst(gameHero, enemy, locationDifficultyLevel);
            }
        } else {
            if (!gameHero.isDead() && !enemy.isDead()) {
                fantasyGameController.makeMoveEnemyFirst(gameHero, enemy, locationDifficultyLevel);
            }
        }

        updateEnemyStats(enemy);
        updateHeroStats(gameHero);
    }

    private void updateHeroStats(Hero gameHero) {
        level.setText("LEVEL: " + gameHero.getLevel());
        HP.setText("HP: " + gameHero.getCurrentHP() + "/" + gameHero.getMaxHP());
        mana.setText("MP: " + gameHero.getManaPoints() + "/" + gameHero.getMaxManaPoints());
        strength.setText("STR: " + gameHero.getStrength());
        dexterity.setText("DEX: " + gameHero.getDexterity());
        intelligence.setText("INT: " + gameHero.getIntelligence());
        willpower.setText("WILL: " + gameHero.getWillpower());
        reputation.setText("REP: " + gameHero.getReputation());
        gold.setText("GOLD: " + gameHero.getGold());
        encumbrance.setText("ENC: " + gameHero.getEncumbrance() + "/" + gameHero.getMaxEncumbrance());
        experience.setText("EXP: " + gameHero.getExperience());
        experienceToNextLevel.setText("NEXT_LVL: " + gameHero.getExperienceToNextLevel());

        royalGuildFactionPoints.setText("ROYAL_GUILD_FP: " + gameHero.getRoyalGuildFactionPoints());
        mageGuildFactionPoints.setText("MAGE_GUILD_FP: " + gameHero.getMageGuildFactionPoints());
        thievesGuildFactionPoints.setText("THIEVES_GUILD_FP: " + gameHero.getThievesGuildFactionPoints());
        barbarianTribeFactionPoints.setText("BARBARIAN_TRIBE_FP: " + gameHero.getBarbarianTribeFactionPoints());
    }

    private void saveHero() {
        if (reward != null) {
            giveReward();
        }

        long newHP = hero.getCurrentHP();
        long newMP = hero.getManaPoints();

        if (reward != null) {
            newHP = Double.valueOf(hero.getCurrentHP() + hero.getMaxHP() * HP_REGEN_POWER)
                    .intValue();
            newMP = Double.valueOf(hero.getManaPoints() + hero.getMaxManaPoints() * MP_REGEN_POWER)
                    .intValue();
        }

        if (newHP > hero.getMaxHP()) {
            newHP = hero.getMaxHP();
        }

        if (newMP > hero.getMaxManaPoints()) {
            newMP = hero.getMaxManaPoints();
        }

        long experienceToNextLevel = hero.getExperienceToNextLevel();
        long currentLevel = hero.getLevel();
        long levelUpCount = 0;

        while (hero.getExperience() > experienceToNextLevel) {
            levelUpCount++;
            experienceToNextLevel = hero.calculateExperienceToNextLevel(currentLevel + 1);
            currentLevel++;
        }

        java.util.List<Long> additionalSkillpoints = java.util.List.of(0L, 0L, 0L, 0L, 0L);

        if (levelUpCount > 0) {
            long skillPointsCount = levelUpCount * 4;

            additionalSkillpoints = chooseSkillpoints(skillPointsCount, currentLevel, locationDifficultyLevel);
        }

        long addEncumbrance = SupportFunctions.randomAtOneToFive();
        long subEncumbrance = SupportFunctions.randomAtOneToFive();
        long newEncumbrance = hero.getEncumbrance() + addEncumbrance - subEncumbrance;

        while (newEncumbrance > hero.getMaxEncumbrance() || newEncumbrance < 0) {
            addEncumbrance = SupportFunctions.randomAtOneToFive();
            subEncumbrance = SupportFunctions.randomAtOneToFive();
            newEncumbrance = hero.getEncumbrance() + addEncumbrance - subEncumbrance;
        }

        character.getFantasyCharacteristics().setLevel(currentLevel);
        character.getFantasyCharacteristics().setCurrentHP(newHP);
        character.getFantasyCharacteristics().setMaxHP(hero.getMaxHP());
        character.getFantasyCharacteristics().setManaPoints(newMP);
        character.getFantasyCharacteristics().setMaxManaPoints(hero.getMaxManaPoints());
        character.getFantasyCharacteristics().setStrength(hero.getStrength() + additionalSkillpoints.get(0));
        character.getFantasyCharacteristics().setDexterity(hero.getDexterity() + additionalSkillpoints.get(1));
        character.getFantasyCharacteristics().setIntelligence(hero.getIntelligence() + additionalSkillpoints.get(2));
        character.getFantasyCharacteristics().setWillpower(hero.getWillpower() + additionalSkillpoints.get(3));
        character.getFantasyCharacteristics().setReputation(hero.getReputation() + additionalSkillpoints.get(4));
        character.getFantasyCharacteristics().setGold(hero.getGold());
        character.getFantasyCharacteristics().setEncumbrance(newEncumbrance);
        character.getFantasyCharacteristics().setMaxEncumbrance(hero.getMaxEncumbrance());
        character.getFantasyCharacteristics().setExperience(hero.getExperience());
        character.getFantasyCharacteristics().setExperienceToNextLevel(experienceToNextLevel);

        character
                .getFantasyCharacteristics()
                .setRoyalGuildFactionPoints(hero.getRoyalGuildFactionPoints() + SupportFunctions.randomAtOneToTwenty());
        character
                .getFantasyCharacteristics()
                .setMageGuildFactionPoints(hero.getMageGuildFactionPoints() + SupportFunctions.randomAtOneToTwenty());
        character
                .getFantasyCharacteristics()
                .setThievesGuildFactionPoints(
                        hero.getThievesGuildFactionPoints() + SupportFunctions.randomAtOneToTwenty());
        character
                .getFantasyCharacteristics()
                .setBarbarianTribeFactionPoints(
                        hero.getBarbarianTribeFactionPoints() + SupportFunctions.randomAtOneToTwenty());

        updateStatistic();

        gameOperationsController.updateCharacter(character);
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

        if (character.getFantasyCharacteristics().getCurrentHP() > 0) {
            numberOfSuccesfullEvents++;
        }

        long maxLevel = character.getStatistic().getMaxLevel();

        if (character.getFantasyCharacteristics().getLevel() > maxLevel) {
            maxLevel = character.getFantasyCharacteristics().getLevel();
        }

        long maxExperience = character.getStatistic().getMaxExperience();

        if (character.getFantasyCharacteristics().getExperience() > maxExperience) {
            maxExperience = character.getFantasyCharacteristics().getExperience();
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

    private void giveReward() {
        reward.giveReward();
    }

    private java.util.List<Long> chooseSkillpoints(long skillPointsCount, long heroLevel, long locationSecurityLevel) {
        Long strength = 0L;
        Long dexterity = 0L;
        Long intelligence = 0L;
        Long willpower = 0L;
        Long reputation = 0L;

        Object[] options = {"Strength", "Dexterity", "Intelligence", "Willpower", "Reputation"};

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
                strength += (heroLevel + locationSecurityLevel) / elementsCount;
            } else if (choice.get() == 1) {
                dexterity += (heroLevel + locationSecurityLevel) / elementsCount;
            } else if (choice.get() == 2) {
                intelligence += (heroLevel + locationSecurityLevel) / elementsCount;
            } else if (choice.get() == 3) {
                willpower += (heroLevel + locationSecurityLevel) / elementsCount;
            } else if (choice.get() == 4) {
                reputation += (heroLevel + locationSecurityLevel) / elementsCount;
            }
        }

        return List.of(strength, dexterity, intelligence, willpower, reputation);
    }
}
